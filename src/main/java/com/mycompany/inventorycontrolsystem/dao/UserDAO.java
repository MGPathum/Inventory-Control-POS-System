package com.mycompany.inventorycontrolsystem.dao;

import com.mycompany.inventorycontrolsystem.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object contract for {@link User} entities.
 * All JDBC implementations live in {@link UserDAOImpl}.
 */
public interface UserDAO {

    /** Persist a new user record. Returns the generated user_id. */
    int insert(User user);

    /** Update an existing user's details (does NOT change password). */
    boolean update(User user);

    /** Soft-delete: sets is_active = 0. */
    boolean deactivate(int userId);

    /** Hard-delete (use with caution). */
    boolean delete(int userId);

    /** Find by primary key. */
    Optional<User> findById(int userId);

    /** Find by username (for login). */
    Optional<User> findByUsername(String username);

    /** Return all users joined with their role name. */
    List<User> findAll();

    /** Update the last_login timestamp for the given user. */
    boolean updateLastLogin(int userId);

    /** Change a user's hashed password. */
    boolean updatePassword(int userId, String newPasswordHash);
}
