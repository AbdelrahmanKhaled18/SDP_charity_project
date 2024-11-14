package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Staff extends Person {

    private String position;
    private String department;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Staff(String name, String gender, String phoneNumber, String email, String nationalId, Date dateOfBirth,
                 boolean isActive, Address address, ArrayList<Donation> donationHistory, ArrayList<Task> assignedTasks,
                 String position, String department) {
        super(name, gender, phoneNumber, email, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks);
        this.position = position;
        this.department = department;
    }

    public Staff(int id, String name, String gender, String phoneNumber, String email, String nationalId, Date dateOfBirth,
                 boolean isActive, Address address, ArrayList<Donation> donationHistory, ArrayList<Task> assignedTasks,
                 String position, String department) {
        super(id, name, gender, phoneNumber, email, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks);
        this.position = position;
        this.department = department;
    }

    public static boolean createStaff(Staff staff) {
        if (!Person.createPerson(staff))
            return false;

        String command = "INSERT INTO staff (`person_id`, `position`, `department`) VALUES (?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, staff.getId());
            statement.setString(2, staff.getPosition());
            statement.setString(3, staff.getDepartment());
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }


    public static boolean updateStaff(Staff staff) {
        boolean changesMade = Person.updatePerson(staff);

        String command = "UPDATE staff SET position=?, department=? WHERE person_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, staff.getPosition());
            statement.setString(2, staff.getDepartment());
            statement.setInt(3, staff.getId());
            changesMade |= statement.executeUpdate() > 0;
            statement.close();
            return changesMade;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deletePersonFromStaff(int personId) {
        String command = "DELETE FROM staff WHERE person_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public static Staff retrieveStaff(int personId) {
        String command = "SELECT * FROM person JOIN staff on person.id = staff.person_id WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Staff> staffList = getStaffFromRS(rs);
            Staff staff = null;
            if (!staffList.isEmpty())
                staff = staffList.getFirst();
            statement.close();
            return staff;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Staff> retrieveAllPersons() {
        String command = "SELECT * FROM person";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Staff> staffMembers = getStaffFromRS(rs);
            statement.close();
            return staffMembers;
        } catch (SQLException e) {
            return null;
        }
    }

    private static ArrayList<Staff> getStaffFromRS(ResultSet rs) throws SQLException {
        ArrayList<Staff> staffMembers = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String nationalId = rs.getString("national_id");
            Date dateOfBirth = new Date(rs.getDate("date_of_birth").getTime());
            boolean isActive = rs.getBoolean("is_active");
            int addressId = rs.getInt("address_id");
            Address address = Address.retrieveAddress(addressId);
            ArrayList<Donation> donationHistory = Donation.retrievePersonDonations(id);
            ArrayList<Task> assignedTasks = Person.retrievePersonTasks(id);
            String position = rs.getString("position");
            String department = rs.getString("department");
            staffMembers.add(new Staff(id, name, gender, phoneNumber, email, nationalId, dateOfBirth, isActive,
                    address, donationHistory, assignedTasks, position, department));
        }
        return staffMembers;
    }
}
