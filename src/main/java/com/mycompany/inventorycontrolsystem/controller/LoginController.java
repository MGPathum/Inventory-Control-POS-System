package com.mycompany.inventorycontrolsystem.controller;

import com.mycompany.inventorycontrolsystem.dao.UserDAO;
import com.mycompany.inventorycontrolsystem.db.DAOFactory;
import com.mycompany.inventorycontrolsystem.model.User;
import com.mycompany.inventorycontrolsystem.util.PasswordUtil;

import java.util.Optional;

/**
 * Business logic for user authentication.
 *
 * <p>The GUI calls {@link #authenticate(String, String)} and receives a
 * fully-populated {@link User} on success, or an {@link AuthException}
 * on failure — keeping all error-branching logic out of the view layer.
 */
public class LoginController {

    
    private final UserDAO userDAO;

    public LoginController() {
        this.userDAO = DAOFactory.getUserDAO();
    }

    /**
     * Authenticates a user by username and plain-text password.
     *
     * @param username  the typed username (case-sensitive)
     * @param password  the plain-text password
     * @return the authenticated {@link User} model
     * @throws AuthException if credentials are invalid, account is inactive,
     *                       or the database cannot be reached
     */
    public User authenticate(String username, String password) throws AuthException {
        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            throw new AuthException("Username and password are required.");
        }

        System.out.println("Login attempt for username='{}'");

        Optional<User> userOpt = userDAO.findByUsername(username.trim());

        if (userOpt.isEmpty()) {
            throw new AuthException("Invalid username or password.");
        }

        User user = userOpt.get();

        if (!user.isActive()) {
            throw new AuthException("Your account has been deactivated. Contact the administrator.");
        }

        boolean passwordMatches = PasswordUtil.verify(password, user.getPasswordHash());

        if (!passwordMatches) {
            throw new AuthException("Invalid username or password.");
        }

        // Success — update the last_login timestamp asynchronously (best-effort)
        userDAO.updateLastLogin(user.getUserId());

        return user;
    }

    /**
     * Thrown when authentication fails for any reason.
     * The message is safe to display directly in the GUI.
     */
    public static class AuthException extends Exception {
        public AuthException(String message) { super(message); }
    }
}
