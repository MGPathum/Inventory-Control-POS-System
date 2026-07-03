package com.mycompany.inventorycontrolsystem;

import com.mycompany.inventorycontrolsystem.controller.LoginController;
import com.mycompany.inventorycontrolsystem.util.PasswordUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link LoginController.AuthException} and
 * {@link PasswordUtil} hashing/verification logic.
 *
 * <p>These tests do NOT require a database connection — they validate only
 * the business rules that live in the controller and utility layer.
 */
@DisplayName("Login Business Logic Tests")
class LoginControllerTest {

    // ── PasswordUtil tests ────────────────────────────────────────────────

    @Test
    @DisplayName("BCrypt hash is not equal to the plain-text password")
    void testHashIsNotPlainText() {
        String plain = "Admin@123";
        String hash  = PasswordUtil.hash(plain);
        assertNotEquals(plain, hash, "Hash should not equal plain-text");
    }

    @Test
    @DisplayName("Correct password verifies against its hash")
    void testCorrectPasswordVerifies() {
        String plain = "SecurePass99!";
        String hash  = PasswordUtil.hash(plain);
        assertTrue(PasswordUtil.verify(plain, hash),
            "Correct password should verify as true");
    }

    @Test
    @DisplayName("Wrong password does not verify against a hash")
    void testWrongPasswordFails() {
        String hash = PasswordUtil.hash("RealPassword1");
        assertFalse(PasswordUtil.verify("WrongPassword", hash),
            "Wrong password should not verify");
    }

    @Test
    @DisplayName("Null password returns false from verify (no exception)")
    void testNullPasswordReturnsFalse() {
        String hash = PasswordUtil.hash("SomePassword");
        assertFalse(PasswordUtil.verify(null, hash),
            "Null password should return false gracefully");
    }

    @Test
    @DisplayName("Null hash returns false from verify (no exception)")
    void testNullHashReturnsFalse() {
        assertFalse(PasswordUtil.verify("SomePassword", null),
            "Null hash should return false gracefully");
    }

    @Test
    @DisplayName("Two hashes of the same password are different (salted)")
    void testHashesAreSalted() {
        String plain = "SamePassword";
        String hash1 = PasswordUtil.hash(plain);
        String hash2 = PasswordUtil.hash(plain);
        assertNotEquals(hash1, hash2,
            "BCrypt should produce different hashes due to random salt");
        // But both should still verify correctly
        assertTrue(PasswordUtil.verify(plain, hash1));
        assertTrue(PasswordUtil.verify(plain, hash2));
    }

    @Test
    @DisplayName("Legacy non-BCrypt hash returns false (does not throw)")
    void testLegacyHashReturnsFalse() {
        // Simulate a SHA-256 hash stored from the old SQL seed
        String legacySha256Hash =
            "c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec";
        assertFalse(PasswordUtil.verify("Admin@123", legacySha256Hash),
            "A non-BCrypt hash should return false, not throw an exception");
    }

    // ── AuthException message tests ───────────────────────────────────────

    @Test
    @DisplayName("AuthException carries the correct message")
    void testAuthExceptionMessage() {
        String msg = "Invalid username or password.";
        LoginController.AuthException ex = new LoginController.AuthException(msg);
        assertEquals(msg, ex.getMessage());
    }
}
