package model;

import java.sql.*;
import java.util.ArrayList;

public class Address {
    private int id;
    private String name;
    private Address parentAddress;

    public Address(String name) {
        this.name = name;
    }

    public Address(int id, String name) {
        this(name);
        this.id = id;
    }

    public Address(String name, Address parentAddress) {
        this(name);
        this.parentAddress = parentAddress;
    }


    public Address(int id, String name, Address parentAddress) {
        this(name, parentAddress);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(Address parentAddress) {
        this.parentAddress = parentAddress;
    }


    public static boolean createAddress(Address address) {
        String command = "INSERT INTO address (`name`, `parent_id`) VALUES(?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, address.getName());
            if (address.getParentAddress() != null) {
                statement.setInt(2, address.getParentAddress().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            boolean success = false;
            if (rs.next()) {
                address.setId(rs.getInt(1));
                success = true;
            }
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateAddress(Address address) {
        String command = "UPDATE address SET name=?, parent_id= ? WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, address.getName());
            if (address.getParentAddress() != null) {
                statement.setInt(2, address.getParentAddress().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(3, address.getId());
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteAddress(int addressId) {
        String command = "DELETE FROM address WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, addressId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Address retrieveAddress(int addressId) {
        String command = "SELECT * FROM address WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, addressId);
            ResultSet rs = statement.executeQuery();
            Address address = null;
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Integer parentId = rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null; // Handle null parent_id
                Address parentAddress = null;
                if (parentId != null) {
                    parentAddress = retrieveAddress(parentId); // Recursive call
                }
                address = new Address(id, name, parentAddress);
            }
            statement.close();
            return address;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ArrayList<Address> retrieveAllAddresses() {
        String command = "SELECT * FROM address";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Address> addresses = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int parentId = rs.getInt("parent_id");
                if (parentId != 0) {
                    addresses.add(new Address(id, name, retrieveAddress(parentId)));
                }
                addresses.add(new Address(id, name));
            }
            return addresses;
        } catch (SQLException e) {
            return null;
        }
    }
}