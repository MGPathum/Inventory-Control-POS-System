package com.mycompany.inventorycontrolsystem.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility for BCrypt password hashing and verification.
 * BCrypt is preferred over plain SHA-256 because it is salted and slow
 * by design, making brute-force attacks impractical.
 */
public final class PasswordUtil {

    private static final int BCRYPT_ROUNDS = 12;  // cost factor (higher = slower)

    private PasswordUtil() {}

    /**
     * Hashes a plain-text password using BCrypt.
     *
     * @param plainPassword the raw password from the user
     * @return the BCrypt hash string (60 characters)
     */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    /**
     * Verifies a plain-text password against a stored BCrypt hash.
     *
     * @param plainPassword the raw password attempt
     * @param storedHash    the hash stored in the database
     * @return true if the password matches
     */
    public static boolean verify(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) return false;
        try {
            return BCrypt.checkpw(plainPassword, storedHash);
        } catch (IllegalArgumentException e) {
            // storedHash is not a valid BCrypt hash (e.g. legacy SHA-256)
            return false;
        }
    }
}
