package com.mycompany.inventorycontrolsystem.dao.impl;

import com.mycompany.inventorycontrolsystem.dao.ProductDAO;
import com.mycompany.inventorycontrolsystem.db.DatabaseConnection;
import com.mycompany.inventorycontrolsystem.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link ProductDAO}.
 *
 * <p>All SELECT queries join {@code categories}, {@code suppliers}, and
 * {@code inventory_stock} to populate the transient display fields on
 * the {@link Product} model in a single round-trip.
 *
 * <p>INSERT also creates the matching {@code inventory_stock} row
 * (quantity = 0) within the same transaction so the stock ledger is
 * always consistent.
 */
public class ProductDAOImpl implements ProductDAO {
    private static final String BASE_SELECT =
        "SELECT p.*, " +
        "       c.category_name, " +
        "       COALESCE(s.company_name, 'N/A') AS supplier_name, " +
        "       COALESCE(st.quantity_on_hand, 0) AS quantity_on_hand " +
        "FROM products p " +
        "JOIN categories c      ON p.category_id  = c.category_id " +
        "LEFT JOIN suppliers s  ON p.supplier_id  = s.supplier_id " +
        "LEFT JOIN inventory_stock st ON p.product_id = st.product_id ";

    private static final String SQL_INSERT =
        "INSERT INTO products (category_id, supplier_id, product_code, product_name, " +
        "description, unit, cost_price, selling_price, tax_rate, reorder_level, " +
        "barcode, image_path, is_active) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_STOCK =
        "INSERT INTO inventory_stock (product_id, quantity_on_hand) VALUES (?, 0)";

    private static final String SQL_UPDATE =
        "UPDATE products SET category_id=?, supplier_id=?, product_code=?, " +
        "product_name=?, description=?, unit=?, cost_price=?, selling_price=?, " +
        "tax_rate=?, reorder_level=?, barcode=?, image_path=?, is_active=? " +
        "WHERE product_id=?";

    private static final String SQL_DEACTIVATE =
        "UPDATE products SET is_active=0 WHERE product_id=?";

    private static final String SQL_DELETE =
        "DELETE FROM products WHERE product_id=?";

    // POS lookups must only return active products — a deactivated product
    // must never be sold even if the cashier scans its barcode.
    private static final String SQL_FIND_BY_ID =
        BASE_SELECT + "WHERE p.product_id=? AND p.is_active=1";

    private static final String SQL_FIND_BY_CODE =
        BASE_SELECT + "WHERE p.product_code=? AND p.is_active=1";

    // Separate query used when the caller explicitly needs a product
    // regardless of its active state (e.g. the Products management screen).
    private static final String SQL_FIND_BY_ID_ANY =
        BASE_SELECT + "WHERE p.product_id=?";

    private static final String SQL_FIND_ALL =
        BASE_SELECT + "WHERE p.is_active=1 ORDER BY p.product_name";

    private static final String SQL_SEARCH =
        BASE_SELECT +
        "WHERE p.is_active=1 AND (p.product_name LIKE ? OR p.product_code LIKE ?) " +
        "ORDER BY p.product_name";

    private static final String SQL_LOW_STOCK =
        BASE_SELECT +
        "WHERE p.is_active=1 AND st.quantity_on_hand <= p.reorder_level " +
        "ORDER BY st.quantity_on_hand";

    private static final String SQL_BY_CATEGORY =
        BASE_SELECT + "WHERE p.is_active=1 AND p.category_id=? ORDER BY p.product_name";

    private Connection conn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public int insert(Product product) {
        Connection c = conn();
        try {
            c.setAutoCommit(false);

            int generatedId;
            try (PreparedStatement ps = c.prepareStatement(
                    SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt        (1,  product.getCategoryId());
                if (product.getSupplierId() != null) {
                    ps.setInt(2, product.getSupplierId());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setString     (3,  product.getProductCode());
                ps.setString     (4,  product.getProductName());
                ps.setString     (5,  product.getDescription());
                ps.setString     (6,  product.getUnit());
                ps.setBigDecimal (7,  product.getCostPrice());
                ps.setBigDecimal (8,  product.getSellingPrice());
                ps.setBigDecimal (9,  product.getTaxRate());
                ps.setInt        (10, product.getReorderLevel());
                ps.setString     (11, product.getBarcode());
                ps.setString     (12, product.getImagePath());
                ps.setBoolean    (13, product.isActive());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        c.rollback();
                        return -1;
                    }
                    generatedId = keys.getInt(1);
                    product.setProductId(generatedId);
                }
            }

            // Create the matching stock row at zero quantity
            try (PreparedStatement ps = c.prepareStatement(SQL_INSERT_STOCK)) {
                ps.setInt(1, generatedId);
                ps.executeUpdate();
            }

            c.commit();
            return generatedId;

        } catch (SQLException e) {
            e.printStackTrace(); // prints full SQL error to NetBeans Output tab
            try { c.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return -1;
        } finally {
            try { c.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    @Override
    public boolean update(Product product) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_UPDATE)) {
            ps.setInt        (1,  product.getCategoryId());
            if (product.getSupplierId() != null) {
                ps.setInt(2, product.getSupplierId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString     (3,  product.getProductCode());
            ps.setString     (4,  product.getProductName());
            ps.setString     (5,  product.getDescription());
            ps.setString     (6,  product.getUnit());
            ps.setBigDecimal (7,  product.getCostPrice());
            ps.setBigDecimal (8,  product.getSellingPrice());
            ps.setBigDecimal (9,  product.getTaxRate());
            ps.setInt        (10, product.getReorderLevel());
            ps.setString     (11, product.getBarcode());
            ps.setString     (12, product.getImagePath());
            ps.setBoolean    (13, product.isActive());
            ps.setInt        (14, product.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deactivate(int productId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_DEACTIVATE)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to deactivate product ID={}");
            return false;
        }
    }

    @Override
    public boolean delete(int productId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete product ID={}");
            return false;
        }
    }

    @Override
    public Optional<Product> findById(int productId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find active product ID={}");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByIdAny(int productId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_ID_ANY)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find product (any status) ID={}");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByCode(String productCode) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_CODE)) {
            ps.setString(1, productCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find product by code={}");
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();
        String like = "%" + keyword + "%";
        try (PreparedStatement ps = conn().prepareStatement(SQL_SEARCH)) {
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Product search failed for keyword: {}");
        }
        return list;
    }

    @Override
    public List<Product> findLowStock() {
        List<Product> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_LOW_STOCK);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_BY_CATEGORY)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find products for category ID={}");
        }
        return list;
    }

    /**
     * Queries the highest numeric suffix among all PROD-NNNN codes and
     * returns the next value zero-padded to 4 digits.
     *
     * <p>Uses {@code product_id DESC LIMIT 1} as the ordering key (not
     * lexicographic sort on the code string) so that "PROD-0010" correctly
     * beats "PROD-0009" even if string ordering would disagree.
     *
     * <p>Falls back to "PROD-0001" when no PROD- prefixed codes exist.
     */
    @Override
    public String generateNextProductCode() {
        // Select the code from the most-recently inserted PROD- prefixed row.
        // ORDER BY product_id DESC ensures insertion order, not string order.
        final String SQL =
            "SELECT product_code FROM products " +
            "WHERE product_code LIKE 'PROD-%' " +
            "ORDER BY product_id DESC LIMIT 1";

        try (PreparedStatement ps = conn().prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastCode = rs.getString("product_code"); // e.g. "PROD-0004"
                // Extract the numeric suffix after "PROD-"
                String suffix = lastCode.substring(5);          // "0004"
                int nextNumber = Integer.parseInt(suffix) + 1;
                return String.format("PROD-%04d", nextNumber);  // "PROD-0005"
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // Last code has a non-numeric suffix — fall through to default
            System.out.println("Could not parse numeric suffix from last PROD- code; using PROD-0001");
        }

        return "PROD-0001"; // table empty or no PROD- codes exist
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId    (rs.getInt        ("product_id"));
        p.setCategoryId   (rs.getInt        ("category_id"));
        p.setProductCode  (rs.getString     ("product_code"));
        p.setProductName  (rs.getString     ("product_name"));
        p.setDescription  (rs.getString     ("description"));
        p.setUnit         (rs.getString     ("unit"));
        p.setCostPrice    (rs.getBigDecimal ("cost_price"));
        p.setSellingPrice (rs.getBigDecimal ("selling_price"));
        p.setTaxRate      (rs.getBigDecimal ("tax_rate"));
        p.setReorderLevel (rs.getInt        ("reorder_level"));
        p.setBarcode      (rs.getString     ("barcode"));
        p.setImagePath    (rs.getString     ("image_path"));
        p.setActive       (rs.getBoolean    ("is_active"));

        int supplierId = rs.getInt("supplier_id");
        if (!rs.wasNull()) p.setSupplierId(supplierId);

        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) p.setCreatedAt(created.toLocalDateTime());

        // Joined fields
        p.setCategoryName  (rs.getString("category_name"));
        p.setSupplierName  (rs.getString("supplier_name"));
        p.setQuantityOnHand(rs.getInt   ("quantity_on_hand"));

        return p;
    }
}
