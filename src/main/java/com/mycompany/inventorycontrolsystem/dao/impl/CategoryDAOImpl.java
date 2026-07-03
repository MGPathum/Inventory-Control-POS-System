package com.mycompany.inventorycontrolsystem.dao.impl;

import com.mycompany.inventorycontrolsystem.dao.CategoryDAO;
import com.mycompany.inventorycontrolsystem.db.DatabaseConnection;
import com.mycompany.inventorycontrolsystem.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link CategoryDAO}.
 */
public class CategoryDAOImpl implements CategoryDAO {

    
    private static final String SQL_INSERT =
        "INSERT INTO categories (parent_id, category_name, description, is_active) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE categories SET parent_id=?, category_name=?, description=?, is_active=? " +
        "WHERE category_id=?";

    private static final String SQL_DELETE =
        "DELETE FROM categories WHERE category_id=?";

    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM categories WHERE category_id=?";

    private static final String SQL_FIND_ALL =
        "SELECT * FROM categories ORDER BY category_name";

    private static final String SQL_FIND_ACTIVE =
        "SELECT * FROM categories WHERE is_active=1 ORDER BY category_name";

    private Connection conn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public int insert(Category category) {
        try (PreparedStatement ps = conn().prepareStatement(
                SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            if (category.getParentId() != null) {
                ps.setInt(1, category.getParentId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString (2, category.getCategoryName());
            ps.setString (3, category.getDescription());
            ps.setBoolean(4, category.isActive());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    category.setCategoryId(id);
                    System.out.println("Category inserted with ID={}");
                    return id;
                }
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    @Override
    public boolean update(Category category) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_UPDATE)) {
            if (category.getParentId() != null) {
                ps.setInt(1, category.getParentId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString (2, category.getCategoryName());
            ps.setString (3, category.getDescription());
            ps.setBoolean(4, category.isActive());
            ps.setInt    (5, category.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(int categoryId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete category ID={}");
            return false;
        }
    }

    @Override
    public Optional<Category> findById(int categoryId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find category ID={}");
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Category> findActive() {
        List<Category> list = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ACTIVE);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setCategoryId  (rs.getInt    ("category_id"));
        c.setCategoryName(rs.getString ("category_name"));
        c.setDescription (rs.getString ("description"));
        c.setActive      (rs.getBoolean("is_active"));
        int parentId = rs.getInt("parent_id");
        if (!rs.wasNull()) c.setParentId(parentId);
        return c;
    }
}
