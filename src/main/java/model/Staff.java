package model;

import model.DesignPatterns.iterator.PersonRSIterator;
import model.DesignPatterns.template.Donation;

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

    public Staff(String name, String gender, String phoneNumber, String email, String password, String nationalId,
                 Date dateOfBirth, boolean isActive, Address address, ArrayList<Donation> donationHistory,
                 ArrayList<Task> assignedTasks, String position, String department) {
        super(name, gender, phoneNumber, email, password, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks);
        this.position = position;
        this.department = department;
    }

    public Staff(int id, String name, String gender, String phoneNumber, String email, String password, String nationalId,
                 Date dateOfBirth, boolean isActive, Address address, ArrayList<Donation> donationHistory,
                 ArrayList<Task> assignedTasks, String position, String department) {
        super(id, name, gender, phoneNumber, email, password, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks);
        this.position = position;
        this.department = department;
    }

    public static boolean createStaff(Staff staff) {
        if (!Person.createPerson(staff))
            return false;

        String command = "INSERT INTO staff (`person_id`, `position`, `department`) VALUES (?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement userTypeStatement = conn.prepareStatement("UPDATE person SET user_type='staff' WHERE id=?");
            userTypeStatement.setInt(1, staff.getId());
            userTypeStatement.executeUpdate();
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

            PersonRSIterator rsIterator = new PersonRSIterator(rs);
            if (!rsIterator.hasNext()) {
                return null;
            }
            Staff staff = (Staff) rsIterator.next();
            statement.close();
            return staff;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Staff> retrieveAllStaff() {
        String command = "SELECT * FROM person JOIN staff on person.id = staff.person_id";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();

            PersonRSIterator rsIterator = new PersonRSIterator(rs);
            ArrayList<Staff> staffMembers = new ArrayList<>();
            while (rsIterator.hasNext()) {
                staffMembers.add((Staff) rsIterator.next());
            }
            statement.close();
            return staffMembers;
        } catch (SQLException e) {
            return null;
        }
    }
}
