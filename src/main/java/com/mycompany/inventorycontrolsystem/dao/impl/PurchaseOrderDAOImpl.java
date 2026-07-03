package com.mycompany.inventorycontrolsystem.dao.impl;

import com.mycompany.inventorycontrolsystem.dao.PurchaseOrderDAO;
import com.mycompany.inventorycontrolsystem.db.DatabaseConnection;
import com.mycompany.inventorycontrolsystem.model.PurchaseOrder;
import com.mycompany.inventorycontrolsystem.model.PurchaseOrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link PurchaseOrderDAO}.
 *
 * <h3>Transaction strategy for {@code receiveOrder}</h3>
 * <ol>
 *   <li>Load all PO items from the DB.</li>
 *   <li>For each item: increment {@code inventory_stock.quantity_on_hand}
 *       by {@code quantity_ordered} (uses INSERT � ON DUPLICATE KEY UPDATE
 *       so it works even if the stock row doesn't exist yet).</li>
 *   <li>Update PO header status ? RECEIVED and set received_date = TODAY.</li>
 *   <li>COMMIT or ROLLBACK atomically.</li>
 * </ol>
 */
public class PurchaseOrderDAOImpl implements PurchaseOrderDAO {    private static final String SQL_INSERT_HEADER =
        "INSERT INTO purchase_orders (supplier_id, user_id, po_number, order_date, " +
        "expected_date, status, total_amount, notes) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_ITEM =
        "INSERT INTO purchase_order_items (po_id, product_id, quantity_ordered, unit_cost) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE_STATUS =
        "UPDATE purchase_orders SET status=? WHERE po_id=?";

    private static final String SQL_RECEIVE =
        "UPDATE purchase_orders SET status='RECEIVED', received_date=CURDATE() WHERE po_id=?";

    // Upsert: add to existing stock row, or create a new one
    private static final String SQL_INCREMENT_STOCK =
        "INSERT INTO inventory_stock (product_id, quantity_on_hand) VALUES (?, ?) " +
        "ON DUPLICATE KEY UPDATE quantity_on_hand = quantity_on_hand + VALUES(quantity_on_hand)";

    private static final String SQL_UPDATE_RECEIVED_QTY =
        "UPDATE purchase_order_items SET quantity_received = quantity_ordered WHERE po_id=?";

    private static final String SQL_FIND_BY_ID =
        "SELECT po.*, s.company_name AS supplier_name, u.full_name AS raised_by_name " +
        "FROM purchase_orders po " +
        "JOIN suppliers s ON po.supplier_id = s.supplier_id " +
        "JOIN users u     ON po.user_id     = u.user_id " +
        "WHERE po.po_id=?";

    private static final String SQL_FIND_ALL =
        "SELECT po.*, s.company_name AS supplier_name, u.full_name AS raised_by_name " +
        "FROM purchase_orders po " +
        "JOIN suppliers s ON po.supplier_id = s.supplier_id " +
        "JOIN users u     ON po.user_id     = u.user_id " +
        "ORDER BY po.order_date DESC";

    private static final String SQL_FIND_BY_STATUS =
        "SELECT po.*, s.company_name AS supplier_name, u.full_name AS raised_by_name " +
        "FROM purchase_orders po " +
        "JOIN suppliers s ON po.supplier_id = s.supplier_id " +
        "JOIN users u     ON po.user_id     = u.user_id " +
        "WHERE po.status=? ORDER BY po.order_date DESC";

    private static final String SQL_FIND_ITEMS =
        "SELECT poi.*, p.product_code, p.product_name " +
        "FROM purchase_order_items poi " +
        "JOIN products p ON poi.product_id = p.product_id " +
        "WHERE poi.po_id=?";

    private static final String SQL_NEXT_NUMBER =
        "SELECT LPAD(COALESCE(MAX(CAST(SUBSTRING_INDEX(po_number,'-',-1) AS UNSIGNED)),0)+1,5,'0') " +
        "AS next_seq FROM purchase_orders WHERE po_number LIKE ?";

    private Connection conn() {
        return DatabaseConnection.getInstance().getConnection();
    }
    @Override
    public int insertWithItems(PurchaseOrder po) {
        Connection c = conn();
        try {
            c.setAutoCommit(false);

            int poId;
            try (PreparedStatement ps = c.prepareStatement(
                    SQL_INSERT_HEADER, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt   (1, po.getSupplierId());
                ps.setInt   (2, po.getUserId());
                ps.setString(3, po.getPoNumber());
                ps.setDate  (4, Date.valueOf(
                    po.getOrderDate() != null ? po.getOrderDate()
                                              : java.time.LocalDate.now()));
                if (po.getExpectedDate() != null) {
                    ps.setDate(5, Date.valueOf(po.getExpectedDate()));
                } else {
                    ps.setNull(5, Types.DATE);
                }
                ps.setString     (6, po.getStatus().name());
                ps.setBigDecimal (7, po.getTotalAmount());
                ps.setString     (8, po.getNotes());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) { c.rollback(); return -1; }
                    poId = keys.getInt(1);
                    po.setPoId(poId);
                }
            }

            // Insert line items
            try (PreparedStatement ps = c.prepareStatement(SQL_INSERT_ITEM)) {
                for (PurchaseOrderItem item : po.getItems()) {
                    ps.setInt        (1, poId);
                    ps.setInt        (2, item.getProductId());
                    ps.setInt        (3, item.getQuantityOrdered());
                    ps.setBigDecimal (4, item.getUnitCost());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            c.commit();
            return poId;

        } catch (Exception e) {
            e.printStackTrace();
            try { c.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return -1;
        } finally {
            try { c.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    @Override
    public boolean updateStatus(int poId, PurchaseOrder.Status status) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_UPDATE_STATUS)) {
            ps.setString(1, status.name());
            ps.setInt   (2, poId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update PO status for ID={}");
            return false;
        }
    }
    @Override
    public boolean receiveOrder(int poId) {
        System.out.println("Receiving PO ID=" + poId);
        Connection c = conn();
        try {
            c.setAutoCommit(false);

            // Load items for this PO
            List<PurchaseOrderItem> items = new ArrayList<>();
            try (PreparedStatement ps = c.prepareStatement(SQL_FIND_ITEMS)) {
                ps.setInt(1, poId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrderItem item = new PurchaseOrderItem();
                        item.setProductId      (rs.getInt("product_id"));
                        item.setQuantityOrdered(rs.getInt("quantity_ordered"));
                        items.add(item);
                    }
                }
            }

            if (items.isEmpty()) {
                System.err.println("PO has no items: " + poId);
                c.rollback();
                return false;
            }

            // Increment inventory_stock for each item
            try (PreparedStatement ps = c.prepareStatement(SQL_INCREMENT_STOCK)) {
                for (PurchaseOrderItem item : items) {
                    ps.setInt(1, item.getProductId());
                    ps.setInt(2, item.getQuantityOrdered());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Mark all items as fully received
            try (PreparedStatement ps = c.prepareStatement(SQL_UPDATE_RECEIVED_QTY)) {
                ps.setInt(1, poId);
                ps.executeUpdate();
            }

            // Update PO header: RECEIVED + received_date = today
            try (PreparedStatement ps = c.prepareStatement(SQL_RECEIVE)) {
                ps.setInt(1, poId);
                ps.executeUpdate();
            }

            c.commit();
            System.out.println("PO " + poId + " received.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { c.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { c.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    @Override
    public Optional<PurchaseOrder> findById(int poId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, poId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PurchaseOrder po = mapRow(rs);
                    loadItems(po);
                    return Optional.of(po);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find PO ID={}");
        }
        return Optional.empty();
    }

    @Override
    public List<PurchaseOrder> findAll() {
        List<PurchaseOrder> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<PurchaseOrder> findByStatus(PurchaseOrder.Status status) {
        List<PurchaseOrder> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_STATUS)) {
            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find POs by status={}");
        }
        return list;
    }
    @Override
    public String generatePoNumber() {
        String yearPrefix = "PO-" + java.time.Year.now().getValue() + "-";
        try (PreparedStatement ps = conn().prepareStatement(SQL_NEXT_NUMBER)) {
            ps.setString(1, yearPrefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return yearPrefix + rs.getString("next_seq");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return yearPrefix + System.currentTimeMillis();
    }
    private PurchaseOrder mapRow(ResultSet rs) throws SQLException {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoId       (rs.getInt   ("po_id"));
        po.setSupplierId (rs.getInt   ("supplier_id"));
        po.setUserId     (rs.getInt   ("user_id"));
        po.setPoNumber   (rs.getString("po_number"));
        po.setNotes      (rs.getString("notes"));
        po.setTotalAmount(rs.getBigDecimal("total_amount"));

        Date orderDate = rs.getDate("order_date");
        if (orderDate != null) po.setOrderDate(orderDate.toLocalDate());

        Date expectedDate = rs.getDate("expected_date");
        if (expectedDate != null) po.setExpectedDate(expectedDate.toLocalDate());

        Date receivedDate = rs.getDate("received_date");
        if (receivedDate != null) po.setReceivedDate(receivedDate.toLocalDate());

        String statusStr = rs.getString("status");
        if (statusStr != null) {
            try { po.setStatus(PurchaseOrder.Status.valueOf(statusStr)); }
            catch (IllegalArgumentException e) { po.setStatus(PurchaseOrder.Status.PENDING); }
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) po.setCreatedAt(createdAt.toLocalDateTime());

        po.setSupplierName(rs.getString("supplier_name"));
        po.setRaisedByName(rs.getString("raised_by_name"));
        return po;
    }

    private void loadItems(PurchaseOrder po) throws SQLException {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ITEMS)) {
            ps.setInt(1, po.getPoId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PurchaseOrderItem item = new PurchaseOrderItem();
                    item.setPoItemId        (rs.getInt        ("po_item_id"));
                    item.setPoId            (po.getPoId());
                    item.setProductId       (rs.getInt        ("product_id"));
                    item.setQuantityOrdered (rs.getInt        ("quantity_ordered"));
                    item.setQuantityReceived(rs.getInt        ("quantity_received"));
                    item.setUnitCost        (rs.getBigDecimal ("unit_cost"));
                    item.setProductCode     (rs.getString     ("product_code"));
                    item.setProductName     (rs.getString     ("product_name"));
                    po.addItem(item);
                }
            }
        }
    }
}


