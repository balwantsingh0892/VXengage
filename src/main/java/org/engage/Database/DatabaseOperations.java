package org.engage.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.*;

public class DatabaseOperations {

    public static String executeQuery(String sql, DatabaseConnection.DatabaseType dbType) {
        String columnValue = null;
        try (Connection connection = DatabaseConnection.getConnection(dbType);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    columnValue = resultSet.getString(i);
                    System.out.println(columnName + ": " + columnValue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnValue;
    }

    public static void executeInsertUpdateDelete(String sql, DatabaseConnection.DatabaseType dbType) {
        try (Connection connection = DatabaseConnection.getConnection(dbType);
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(sql);
            System.out.println("Rows affected: " + rowsAffected);

            SQLWarning warning = statement.getWarnings();
            while (warning != null) {
                System.err.println("SQL Warning:");
                System.err.println("State  : " + warning.getSQLState());
                System.err.println("Message: " + warning.getMessage());
                System.err.println("Error  : " + warning.getErrorCode());
                warning = warning.getNextWarning();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dynamicValueInsertUpdateDelete(String sql, DatabaseConnection.DatabaseType dbType, String dynamicValue) {
        String dynamicSql = sql.replace("?", dynamicValue);
        try (Connection connection = DatabaseConnection.getConnection(dbType);
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(dynamicSql);
            System.out.println("Rows affected: " + rowsAffected);

            SQLWarning warning = statement.getWarnings();
            while (warning != null) {
                System.err.println("SQL Warning:");
                System.err.println("State  : " + warning.getSQLState());
                System.err.println("Message: " + warning.getMessage());
                System.err.println("Error  : " + warning.getErrorCode());
                warning = warning.getNextWarning();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* public static Map<String, String> executeQueryMultipleColumns(String sql, DatabaseConnection.DatabaseType dbType) {
        Map<String, String> columnValues = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection(dbType);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    columnValues.put(columnName, columnValue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnValues;
    }*/

    public static List<Map<String, String>> executeQueryAndGetRows(String sql, DatabaseConnection.DatabaseType dbType) {
        List<Map<String, String>> rows = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection(dbType);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, String> columnValues = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    columnValues.put(columnName, columnValue);
                }
                rows.add(columnValues);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

}
