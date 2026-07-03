-- ============================================================
-- ONE-TIME SETUP: Fix the admin password hash
-- ============================================================
-- The schema seed script used SHA2() to hash the admin password.
-- The application uses BCrypt (via jBCrypt), which is incompatible
-- with SHA-256 hashes.  LoginController.authenticate() will always
-- reject the old hash — this is the correct, expected behaviour.
--
-- HOW TO FIX (takes 2 minutes):
--
-- STEP 1 — Generate the correct BCrypt hash:
--   In NetBeans, right-click PasswordHashGenerator.java → Run File.
--   It prints a hash and a ready-to-run SQL statement.
--   The default password it hashes is "admin123" for username "admin".
--   Edit the two constants at the top of that file to use a different
--   username or password.
--
-- STEP 2 — Run the printed SQL in MySQL Workbench:
--   USE inventory_db;
--   UPDATE users
--   SET    password_hash = '<paste the printed hash here>'
--   WHERE  username = 'admin';
--
-- STEP 3 — Verify:
--   SELECT username, password_hash FROM users WHERE username = 'admin';
--   The hash column should start with "$2a$12$" (BCrypt format).
--
-- After completing Steps 1-3, the Login screen will accept
-- admin / admin123 (or whatever password you chose).
-- ============================================================

USE inventory_db;

-- Paste the hash printed by PasswordHashGenerator here, then execute:
UPDATE users
SET    password_hash = 'REPLACE_WITH_HASH_FROM_PasswordHashGenerator'
WHERE  username      = 'admin';
