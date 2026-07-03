package com.mycompany.inventorycontrolsystem.dao.impl;

import com.mycompany.inventorycontrolsystem.dao.SalesInvoiceDAO;
import com.mycompany.inventorycontrolsystem.db.DatabaseConnection;
import com.mycompany.inventorycontrolsystem.model.InvoiceItem;
import com.mycompany.inventorycontrolsystem.model.SalesInvoice;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link SalesInvoiceDAO}.
 *
 * <h3>Transaction strategy for {@code insertWithItems}</h3>
 * <ol>
 *   <li>Disable auto-commit on the shared connection.</li>
 *   <li>Insert the {@code sales_invoices} header row.</li>
 *   <li>For each {@link InvoiceItem}: insert into {@code invoice_items}.</li>
 *   <li>For each {@link InvoiceItem}: decrement {@code inventory_stock.quantity_on_hand}.</li>
 *   <li>On any failure: {@code ROLLBACK} — the DB is left untouched.</li>
 *   <li>On success: {@code COMMIT}.</li>
 * </ol>
 * Re-enabling auto-commit in the {@code finally} block ensures other DAO
 * calls are not accidentally affected.
 */
public class SalesInvoiceDAOImpl implements SalesInvoiceDAO {
    private static final String SQL_INSERT_HEADER =
        "INSERT INTO sales_invoices (user_id, invoice_number, invoice_date, customer_name, " +
        "customer_phone, subtotal, tax_amount, discount_amount, total_amount, " +
        "amount_paid, change_amount, payment_method, status, notes) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_ITEM =
        "INSERT INTO invoice_items (invoice_id, product_id, quantity, unit_price, " +
        "discount_pct, tax_rate, line_total) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DECREMENT_STOCK =
        "UPDATE inventory_stock SET quantity_on_hand = quantity_on_hand - ? " +
        "WHERE product_id=? AND quantity_on_hand >= ?";

    private static final String SQL_VOID =
        "UPDATE sales_invoices SET status='VOID' WHERE invoice_id=?";

    private static final String SQL_FIND_BY_ID =
        "SELECT si.*, u.full_name AS cashier_name " +
        "FROM sales_invoices si JOIN users u ON si.user_id = u.user_id " +
        "WHERE si.invoice_id=?";

    private static final String SQL_FIND_BY_NUMBER =
        "SELECT si.*, u.full_name AS cashier_name " +
        "FROM sales_invoices si JOIN users u ON si.user_id = u.user_id " +
        "WHERE si.invoice_number=?";

    private static final String SQL_FIND_ALL =
        "SELECT si.*, u.full_name AS cashier_name " +
        "FROM sales_invoices si JOIN users u ON si.user_id = u.user_id " +
        "ORDER BY si.invoice_date DESC";

    private static final String SQL_FIND_BY_DATE_RANGE =
        "SELECT si.*, u.full_name AS cashier_name " +
        "FROM sales_invoices si JOIN users u ON si.user_id = u.user_id " +
        "WHERE DATE(si.invoice_date) BETWEEN ? AND ? " +
        "ORDER BY si.invoice_date DESC";

    private static final String SQL_FIND_BY_USER =
        "SELECT si.*, u.full_name AS cashier_name " +
        "FROM sales_invoices si JOIN users u ON si.user_id = u.user_id " +
        "WHERE si.user_id=? ORDER BY si.invoice_date DESC";

    private static final String SQL_FIND_ITEMS =
        "SELECT ii.*, p.product_code, p.product_name " +
        "FROM invoice_items ii JOIN products p ON ii.product_id = p.product_id " +
        "WHERE ii.invoice_id=?";

    private static final String SQL_NEXT_NUMBER =
        "SELECT LPAD(COALESCE(MAX(CAST(SUBSTRING_INDEX(invoice_number,'-',-1) AS UNSIGNED)),0)+1,5,'0') " +
        "AS next_seq FROM sales_invoices WHERE invoice_number LIKE ?";

    private Connection conn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public int insertWithItems(SalesInvoice invoice) {
        Connection c = conn();
        try {
            c.setAutoCommit(false);
            int result = insertWithItemsInternal(invoice, c);
            c.commit();
            return result;
        } catch (SQLException e) {
            try { c.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return -1;
        } finally {
            try { c.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    public int insertWithItems(SalesInvoice invoice, Connection conn) throws SQLException {
        return insertWithItemsInternal(invoice, conn);
    }
    
    private int insertWithItemsInternal(SalesInvoice invoice, Connection c) throws SQLException {

            // 1. Insert header
            int invoiceId;
            try (PreparedStatement ps = c.prepareStatement(
                    SQL_INSERT_HEADER, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt        (1,  invoice.getUserId());
                ps.setString     (2,  invoice.getInvoiceNumber());
                ps.setTimestamp  (3,  Timestamp.valueOf(
                    invoice.getInvoiceDate() != null
                        ? invoice.getInvoiceDate()
                        : LocalDateTime.now()));
                ps.setString     (4,  invoice.getCustomerName());
                ps.setString     (5,  invoice.getCustomerPhone());
                ps.setBigDecimal (6,  invoice.getSubtotal());
                ps.setBigDecimal (7,  invoice.getTaxAmount());
                ps.setBigDecimal (8,  invoice.getDiscountAmount());
                ps.setBigDecimal (9,  invoice.getTotalAmount());
                ps.setBigDecimal (10, invoice.getAmountPaid());
                ps.setBigDecimal (11, invoice.getChangeAmount());
                ps.setString     (12, invoice.getPaymentMethod());
                ps.setString     (13, invoice.getStatus() != null ? invoice.getStatus() : "PAID");
                ps.setString     (14,  invoice.getNotes());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) { return -1; }
                    invoiceId = keys.getInt(1);
                    invoice.setInvoiceId(invoiceId);
                }
            }

            // 2. Insert each line item + 3. Decrement stock
            try (PreparedStatement psItem = c.prepareStatement(SQL_INSERT_ITEM);
                 PreparedStatement psStock = c.prepareStatement(SQL_DECREMENT_STOCK)) {

                for (InvoiceItem item : invoice.getItems()) {
                    // Insert line item
                    psItem.setInt        (1, invoiceId);
                    psItem.setInt        (2, item.getProductId());
                    psItem.setInt        (3, item.getQuantity());
                    psItem.setBigDecimal (4, item.getUnitPrice());
                    psItem.setBigDecimal (5, item.getDiscountPct());
                    psItem.setBigDecimal (6, item.getTaxRate());
                    psItem.setBigDecimal (7, item.getLineTotal());
                    psItem.addBatch();

                    // Decrement stock — conditional on sufficient quantity
                    psStock.setInt(1, item.getQuantity());
                    psStock.setInt(2, item.getProductId());
                    psStock.setInt(3, item.getQuantity()); // guard: qty_on_hand >= sold_qty
                    int[] stockUpdates = { psStock.executeUpdate() };
                    if (stockUpdates[0] == 0) {
                        throw new SQLException("Insufficient stock for product_id=" + item.getProductId());
                    }
                }
                psItem.executeBatch();
            }

            return invoiceId;
    }

    @Override
    public boolean voidInvoice(int invoiceId) {
        System.out.println("Voiding invoice ID={}");
        try (PreparedStatement ps = conn().prepareStatement(SQL_VOID)) {
            ps.setInt(1, invoiceId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to void invoice ID={}");
            return false;
        }
    }

    @Override
    public Optional<SalesInvoice> findById(int invoiceId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SalesInvoice inv = mapRow(rs);
                    loadItems(inv);
                    return Optional.of(inv);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find invoice ID={}");
        }
        return Optional.empty();
    }

    @Override
    public Optional<SalesInvoice> findByNumber(String invoiceNumber) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_NUMBER)) {
            ps.setString(1, invoiceNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SalesInvoice inv = mapRow(rs);
                    loadItems(inv);
                    return Optional.of(inv);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to find invoice by number={}");
        }
        return Optional.empty();
    }

    @Override
    public List<SalesInvoice> findAll() {
        List<SalesInvoice> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<SalesInvoice> findByDateRange(LocalDate from, LocalDate to) {
        List<SalesInvoice> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_DATE_RANGE)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find invoices by date range {}-{}");
        }
        return list;
    }

    @Override
    public List<SalesInvoice> findByUserId(int userId) {
        List<SalesInvoice> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_USER)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find invoices for user ID={}");
        }
        return list;
    }

    @Override
    public String generateInvoiceNumber() {
        String yearPrefix = "INV-" + java.time.Year.now().getValue() + "-";
        try (PreparedStatement ps = conn().prepareStatement(SQL_NEXT_NUMBER)) {
            ps.setString(1, yearPrefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return yearPrefix + rs.getString("next_seq");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Fallback: timestamp-based
        return yearPrefix + System.currentTimeMillis();
    }

    @Override
    public int getLastInvoiceId() throws SQLException {
        // Uses the correct PK column name: invoice_id
        final String sql = "SELECT COALESCE(MAX(invoice_id), 0) AS last_id FROM sales_invoices";
        try (PreparedStatement ps = conn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int lastId = rs.getInt("last_id");
                System.out.println("Last invoice_id from DB: {}");
                return lastId;
            }
        }
        return 0;
    }

    private SalesInvoice mapRow(ResultSet rs) throws SQLException {
        SalesInvoice inv = new SalesInvoice();
        inv.setInvoiceId     (rs.getInt        ("invoice_id"));
        inv.setUserId        (rs.getInt        ("user_id"));
        inv.setInvoiceNumber (rs.getString     ("invoice_number"));
        inv.setCustomerName  (rs.getString     ("customer_name"));
        inv.setCustomerPhone (rs.getString     ("customer_phone"));
        inv.setSubtotal      (rs.getBigDecimal ("subtotal"));
        inv.setTaxAmount     (rs.getBigDecimal ("tax_amount"));
        inv.setDiscountAmount(rs.getBigDecimal ("discount_amount"));
        inv.setTotalAmount   (rs.getBigDecimal ("total_amount"));
        inv.setAmountPaid    (rs.getBigDecimal ("amount_paid"));
        inv.setChangeAmount  (rs.getBigDecimal ("change_amount"));
        inv.setPaymentMethod (rs.getString     ("payment_method"));
        inv.setStatus        (rs.getString     ("status"));
        inv.setNotes         (rs.getString     ("notes"));
        inv.setCashierName   (rs.getString     ("cashier_name"));

        Timestamp ts = rs.getTimestamp("invoice_date");
        if (ts != null) inv.setInvoiceDate(ts.toLocalDateTime());

        return inv;
    }

    private void loadItems(SalesInvoice invoice) throws SQLException {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ITEMS)) {
            ps.setInt(1, invoice.getInvoiceId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InvoiceItem item = new InvoiceItem();
                    item.setItemId      (rs.getInt        ("item_id"));
                    item.setInvoiceId   (invoice.getInvoiceId());
                    item.setProductId   (rs.getInt        ("product_id"));
                    item.setQuantity    (rs.getInt        ("quantity"));
                    item.setUnitPrice   (rs.getBigDecimal ("unit_price"));
                    item.setDiscountPct (rs.getBigDecimal ("discount_pct"));
                    item.setTaxRate     (rs.getBigDecimal ("tax_rate"));
                    item.setLineTotal   (rs.getBigDecimal ("line_total"));
                    item.setProductCode (rs.getString     ("product_code"));
                    item.setProductName (rs.getString     ("product_name"));
                    invoice.addItem(item);
                }
            }
        }
    }
}
