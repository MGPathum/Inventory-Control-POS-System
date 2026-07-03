package com.mycompany.inventorycontrolsystem.dao.impl;

import com.mycompany.inventorycontrolsystem.dao.SupplierDAO;
import com.mycompany.inventorycontrolsystem.db.DatabaseConnection;
import com.mycompany.inventorycontrolsystem.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link SupplierDAO}.
 */
public class SupplierDAOImpl implements SupplierDAO {

    
    private static final String SQL_INSERT =
        "INSERT INTO suppliers (company_name, contact_person, email, phone, " +
        "address, city, country, tax_number, is_active) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE suppliers SET company_name=?, contact_person=?, email=?, phone=?, " +
        "address=?, city=?, country=?, tax_number=?, is_active=? " +
        "WHERE supplier_id=?";

    private static final String SQL_DEACTIVATE =
        "UPDATE suppliers SET is_active=0 WHERE supplier_id=?";

    private static final String SQL_DELETE =
        "DELETE FROM suppliers WHERE supplier_id=?";

    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM suppliers WHERE supplier_id=?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM suppliers ORDER BY company_name";

    private static final String SQL_FIND_ACTIVE =
        "SELECT * FROM suppliers WHERE is_active=1 ORDER BY company_name";

    private static final String SQL_SEARCH =
        "SELECT * FROM suppliers " +
        "WHERE is_active=1 AND (company_name LIKE ? OR contact_person LIKE ? OR email LIKE ?) " +
        "ORDER BY company_name";

    private Connection conn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public int insert(Supplier supplier) {
        try (PreparedStatement ps = conn().prepareStatement(
                SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString (1, supplier.getCompanyName());
            ps.setString (2, supplier.getContactPerson());
            ps.setString (3, supplier.getEmail());
            ps.setString (4, supplier.getPhone());
            ps.setString (5, supplier.getAddress());
            ps.setString (6, supplier.getCity());
            ps.setString (7, supplier.getCountry() != null ? supplier.getCountry() : "Malaysia");
            ps.setString (8, supplier.getTaxNumber());
            ps.setBoolean(9, supplier.isActive());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    supplier.setSupplierId(id);
                    System.out.println("Supplier inserted with ID={}");
                    return id;
                }
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    @Override
    public boolean update(Supplier supplier) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_UPDATE)) {
            ps.setString (1,  supplier.getCompanyName());
            ps.setString (2,  supplier.getContactPerson());
            ps.setString (3,  supplier.getEmail());
            ps.setString (4,  supplier.getPhone());
            ps.setString (5,  supplier.getAddress());
            ps.setString (6,  supplier.getCity());
            ps.setString (7,  supplier.getCountry());
            ps.setString (8,  supplier.getTaxNumber());
            ps.setBoolean(9,  supplier.isActive());
            ps.setInt    (10, supplier.getSupplierId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deactivate(int supplierId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_DEACTIVATE)) {
            ps.setInt(1, supplierId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to deactivate supplier ID={}");
            return false;
        }
    }

    @Override
    public boolean delete(int supplierId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, supplierId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete supplier ID={}");
            return false;
        }
    }

    @Override
    public Optional<Supplier> findById(int supplierId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, supplierId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find supplier ID={}");
        }
        return Optional.empty();
    }

    @Override
    public List<Supplier> findAll() {
        List<Supplier> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Supplier> findActive() {
        List<Supplier> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ACTIVE);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Supplier> search(String keyword) {
        List<Supplier> list = new ArrayList<>();
        String like = "%" + keyword + "%";
        try (PreparedStatement ps = conn().prepareStatement(SQL_SEARCH)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Supplier search failed for keyword: {}");
        }
        return list;
    }

    private Supplier mapRow(ResultSet rs) throws SQLException {
        Supplier s = new Supplier();
        s.setSupplierId    (rs.getInt    ("supplier_id"));
        s.setCompanyName   (rs.getString ("company_name"));
        s.setContactPerson (rs.getString ("contact_person"));
        s.setEmail         (rs.getString ("email"));
        s.setPhone         (rs.getString ("phone"));
        s.setAddress       (rs.getString ("address"));
        s.setCity          (rs.getString ("city"));
        s.setCountry       (rs.getString ("country"));
        s.setTaxNumber     (rs.getString ("tax_number"));
        s.setActive        (rs.getBoolean("is_active"));
        return s;
    }
}
