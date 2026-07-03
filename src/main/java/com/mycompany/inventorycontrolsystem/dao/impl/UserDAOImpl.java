package com.mycompany.inventorycontrolsystem.dao.impl;

import com.mycompany.inventorycontrolsystem.dao.UserDAO;
import com.mycompany.inventorycontrolsystem.db.DatabaseConnection;
import com.mycompany.inventorycontrolsystem.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link UserDAO}.
 *
 * <p>All queries use {@link PreparedStatement} to prevent SQL injection.
 * Timestamps are handled as {@link Timestamp} ↔ {@link LocalDateTime}.
 */
public class UserDAOImpl implements UserDAO {
    private static final String SQL_INSERT =
        "INSERT INTO users (role_id, username, password_hash, full_name, email, phone, is_active) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE users SET role_id=?, full_name=?, email=?, phone=?, is_active=? " +
        "WHERE user_id=?";

    private static final String SQL_DEACTIVATE =
        "UPDATE users SET is_active=0 WHERE user_id=?";

    private static final String SQL_DELETE =
        "DELETE FROM users WHERE user_id=?";

    private static final String SQL_FIND_BY_ID =
        "SELECT u.*, r.role_name FROM users u " +
        "JOIN roles r ON u.role_id = r.role_id " +
        "WHERE u.user_id=?";

    private static final String SQL_FIND_BY_USERNAME =
        "SELECT u.*, r.role_name FROM users u " +
        "JOIN roles r ON u.role_id = r.role_id " +
        "WHERE u.username=?";

    private static final String SQL_FIND_ALL =
        "SELECT u.*, r.role_name FROM users u " +
        "JOIN roles r ON u.role_id = r.role_id " +
        "ORDER BY u.full_name";

    private static final String SQL_UPDATE_LAST_LOGIN =
        "UPDATE users SET last_login=NOW() WHERE user_id=?";

    private static final String SQL_UPDATE_PASSWORD =
        "UPDATE users SET password_hash=? WHERE user_id=?";

    private Connection conn() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public int insert(User user) {
        try (PreparedStatement ps = conn().prepareStatement(
                SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt   (1, user.getRoleId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPhone());
            ps.setBoolean(7, user.isActive());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    user.setUserId(id);
                    System.out.println("User inserted with ID={}");
                    return id;
                }
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    @Override
    public boolean update(User user) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_UPDATE)) {
            ps.setInt    (1, user.getRoleId());
            ps.setString (2, user.getFullName());
            ps.setString (3, user.getEmail());
            ps.setString (4, user.getPhone());
            ps.setBoolean(5, user.isActive());
            ps.setInt    (6, user.getUserId());
            boolean result = ps.executeUpdate() > 0;
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deactivate(int userId) {
        System.out.println("Deactivating user ID={}");
        try (PreparedStatement ps = conn().prepareStatement(SQL_DEACTIVATE)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to deactivate user ID={}");
            return false;
        }
    }

    @Override
    public boolean delete(int userId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete user ID={}");
            return false;
        }
    }

    @Override
    public Optional<User> findById(int userId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find user by ID={}");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_BY_USERNAME)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Failed to find user by username={}");
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) users.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean updateLastLogin(int userId) {
        try (PreparedStatement ps = conn().prepareStatement(SQL_UPDATE_LAST_LOGIN)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update last_login for user ID={}");
            return false;
        }
    }

    @Override
    public boolean updatePassword(int userId, String newPasswordHash) {
        System.out.println("Updating password for user ID={}");
        try (PreparedStatement ps = conn().prepareStatement(SQL_UPDATE_PASSWORD)) {
            ps.setString(1, newPasswordHash);
            ps.setInt   (2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update password for user ID={}");
            return false;
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId      (rs.getInt    ("user_id"));
        u.setRoleId      (rs.getInt    ("role_id"));
        u.setUsername    (rs.getString ("username"));
        u.setPasswordHash(rs.getString ("password_hash"));
        u.setFullName    (rs.getString ("full_name"));
        u.setEmail       (rs.getString ("email"));
        u.setPhone       (rs.getString ("phone"));
        u.setActive      (rs.getBoolean("is_active"));

        Timestamp lastLogin = rs.getTimestamp("last_login");
        if (lastLogin != null) u.setLastLogin(lastLogin.toLocalDateTime());

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) u.setCreatedAt(createdAt.toLocalDateTime());

        return u;
    }
}
