package com.mycompany.inventorycontrolsystem.db;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Thread-safe Singleton that manages a single shared JDBC {@link Connection}.
 *
 * <p>Design decisions:
 * <ul>
 *   <li>Uses the <em>initialization-on-demand holder</em> idiom — thread-safe
 *       without synchronisation overhead on every {@code getConnection()} call.</li>
 *   <li>Reads all credentials from {@code database.properties} on the classpath
 *       so no credentials are hard-coded in source files.</li>
 *   <li>Automatically re-opens a stale/closed connection when
 *       {@code getConnection()} is called.</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *   Connection conn = DatabaseConnection.getInstance().getConnection();
 * </pre>
 */
public final class DatabaseConnection {

        private static final String PROPS_FILE = "/database.properties";

    private final String url;
    private final String username;
    private final String password;

    private Connection connection;
    private static final class Holder {
        static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    /** Returns the single application-wide instance. */
    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }
    private DatabaseConnection() {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream(PROPS_FILE)) {
            if (is == null) {
                throw new RuntimeException(
                    "database.properties not found on classpath. " +
                    "Ensure it exists in src/main/resources/");
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database.properties", e);
        }

        // Load the JDBC driver explicitly (required for some older environments)
        try {
            Class.forName(props.getProperty("db.driver",
                                            "com.mysql.cj.jdbc.Driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found on classpath", e);
        }

        this.url      = props.getProperty("db.url");
        this.username = props.getProperty("db.username");
        this.password = props.getProperty("db.password");

        System.out.println("DatabaseConnection initialised – URL: {}");
    }

    /**
     * Returns an open, valid {@link Connection}.
     * Re-connects automatically if the current connection is closed or null.
     *
     * @return a live JDBC connection to {@code inventory_db}
     * @throws RuntimeException if the connection cannot be established
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connect();
        }
        return connection;
    }

    /**
     * Explicitly closes the underlying connection.
     * Call this only when the application is shutting down.
     */
    public void close() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("JDBC connection closed gracefully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }

    /**
     * Tests whether the database is reachable.
     *
     * @param timeoutSeconds how long to wait for a ping response
     * @return {@code true} if the connection is valid
     */
    public boolean testConnection(int timeoutSeconds) {
        try {
            return getConnection().isValid(timeoutSeconds);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(
                "Unable to connect to database. Check database.properties and " +
                "ensure MySQL Server is running.\nCause: " + e.getMessage(), e);
        }
    }
}
