package org.engage.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection cisConnection;
    private static Connection mariaDbConnection;

    public enum DatabaseType {
        CIS,
        MariaDB
    }

    public static Connection getConnection(DatabaseType dbType) {
        switch (dbType) {
            case CIS:
                if (cisConnection == null || isConnectionClosed(cisConnection)) {
                    cisConnection = initializeConnection(dbType);
                }
                return cisConnection;
            case MariaDB:
                if (mariaDbConnection == null || isConnectionClosed(mariaDbConnection)) {
                    mariaDbConnection = initializeConnection(dbType);
                }
                return mariaDbConnection;
            default:
                throw new IllegalArgumentException("Unsupported database type");
        }
    }

    private static boolean isConnectionClosed(Connection connection) {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    private static Connection initializeConnection(DatabaseType dbType) {
        Connection connection = null;
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new IOException("Unable to find database.properties");
            }
            Properties prop = new Properties();
            prop.load(input);

            String url, username, password, driver;
            switch (dbType) {
                case CIS:
                    driver = prop.getProperty("db.driver");
                    url = prop.getProperty("db.url");
                    username = prop.getProperty("db.username");
                    password = prop.getProperty("db.password");
                    break;
                case MariaDB:
                    driver = prop.getProperty("db.mariadb.driver");
                    url = prop.getProperty("db.mariadb.url");
                    username = prop.getProperty("db.mariadb.username");
                    password = prop.getProperty("db.mariadb.password");
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported database type");
            }
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(DatabaseType dbType) {
        try {
            switch (dbType) {
                case CIS:
                    if (cisConnection != null && !cisConnection.isClosed()) {
                        cisConnection.close();
                        cisConnection = null;
                    }
                    break;
                case MariaDB:
                    if (mariaDbConnection != null && !mariaDbConnection.isClosed()) {
                        mariaDbConnection.close();
                        mariaDbConnection = null;
                    }
                    break;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
