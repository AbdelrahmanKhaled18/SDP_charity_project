package model;

import java.sql.*;

public class database_conn {

    private static database_conn instance;
    private Connection conn;

    private database_conn() {
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

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = conn.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query) {
        try {
            Statement statement = conn.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static database_conn getInstance() {
        if (instance == null) {
            instance = new database_conn();
        }
        return instance;
    }
}