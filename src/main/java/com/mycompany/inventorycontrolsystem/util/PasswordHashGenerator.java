package com.mycompany.inventorycontrolsystem.util;

/**
 * One-time utility — run this class directly in NetBeans (right-click →
 * Run File) to generate a valid BCrypt hash for any password and print
 * the exact SQL UPDATE statement you need to paste into MySQL Workbench.
 *
 * DELETE this file (or just never ship it) after you have run the SQL once.
 */
public class PasswordHashGenerator {

    public static void main(String[] args) {
        String username = "Pathum";
        String password = "88222006V";

        String hash = PasswordUtil.hash(password);

        System.out.println("=".repeat(65));
        System.out.println("  BCrypt hash for password: \"" + password + "\"");
        System.out.println("=".repeat(65));
        System.out.println(hash);
        System.out.println();
        System.out.println("Run this SQL once in MySQL Workbench:");
        System.out.println("-".repeat(65));
        System.out.println("USE inventory_db;");
        System.out.println("UPDATE users");
        System.out.println("SET    password_hash = '" + hash + "'");
        System.out.println("WHERE  username = '" + username + "';");
        System.out.println("-".repeat(65));
        System.out.println();
        System.out.println("Verify it worked:");
        System.out.println("SELECT username, password_hash FROM users WHERE username = '"
                + username + "';");
        System.out.println("=".repeat(65));
    }
}
