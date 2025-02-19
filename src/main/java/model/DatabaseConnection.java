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

    private Connection conn;

    public Connection getConnection() {
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
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}