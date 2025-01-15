package model;

import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection instance;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private static Connection conn;

    public static Connection getConnection() {
        return conn;
    }

    private DatabaseConnection() {
        if (instance == null) {
            String username = "admin";
            String password = "123";
            String url = "jdbc:mysql://localhost:3306/charity_db";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace(); // Print stack trace for detailed error info
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC driver not found. Please add the driver to your project.");
            }
        }
    }
}