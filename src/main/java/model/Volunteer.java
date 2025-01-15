package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Volunteer extends Person {

    private ArrayList<Skill> skills;

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public Volunteer(String name, String gender, String phoneNumber, String email, String password, String nationalId,
                     Date dateOfBirth, boolean isActive, Address address, ArrayList<Donation> donationHistory,
                     ArrayList<Task> assignedTasks, String userType, ArrayList<Skill> skills) {
        super(name, gender, phoneNumber, email, password, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks, userType);
        this.skills = skills;
    }

    public Volunteer(int id, String name, String gender, String phoneNumber, String email, String password, String nationalId,
                     Date dateOfBirth, boolean isActive, Address address, ArrayList<Donation> donationHistory,
                     ArrayList<Task> assignedTasks, String userType, ArrayList<Skill> skills) {
        super(id, name, gender, phoneNumber, email, password, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks, userType);
        this.skills = skills;
    }


    public static boolean deleteVolunteerSkill(int personId, int skillId) {
        String command = "DELETE FROM volunteer_skills WHERE person_id=? AND skill_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            statement.setInt(2, skillId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean addVolunteerSkill(int personId, int skillId) {
        // Corrected query: Removed single quotes around column names
        String command = "INSERT INTO volunteer_skills (person_id, skill_id) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId); // Set the person_id parameter
            statement.setInt(2, skillId); // Set the skill_id parameter
            boolean success = statement.executeUpdate() > 0; // Execute and check if rows were affected
            statement.close(); // Close the statement
            return success; // Return success status
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return failure status
        }
    }


    public static ArrayList<Skill> retrieveVolunteerSkills(int personId) {
        String command = "SELECT skill.id AS id, skill.name AS name, skill.description AS description " +
                "FROM volunteer_skills INNER JOIN skill ON volunteer_skills.skill_id = skill.id " +
                "WHERE volunteer_skills.person_id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Skill> skills = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                skills.add(new Skill(id, name, description));
            }
            statement.close();
            return skills;
        } catch (SQLException e) {
            return null;
        }
    }


    public static boolean createVolunteer(Volunteer volunteer) {
        if (!Person.createPerson(volunteer))
            return false;

        String command = "INSERT INTO staff (`person_id`, `position`, `department`) VALUES (?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try {
            PreparedStatement userTypeStatement = conn.prepareStatement("UPDATE person SET user_type='volunteer' WHERE id=?");
            userTypeStatement.setInt(1, volunteer.getId());
        }

        catch (SQLException e) {
            return false;
        }

        for (Skill skill : volunteer.getSkills()) {
            if (Volunteer.addVolunteerSkill(volunteer.getId(), skill.getId()))
                return false;
        }
        return true;
    }

    public static Volunteer retrieveVolunteer(int personId) {
        String command = "SELECT * FROM person WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Volunteer> volunteerList = getVolunteerFromRS(rs);
            Volunteer volunteer = null;
            if (!volunteerList.isEmpty())
                volunteer = volunteerList.getFirst();
            statement.close();
            return volunteer;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Volunteer> retrieveAllVolunteers() {
        String command = "SELECT * FROM person WHERE id NOT IN (SELECT person_id FROM staff)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Volunteer> volunteers = getVolunteerFromRS(rs);
            statement.close();
            return volunteers;
        } catch (SQLException e) {
            return null;
        }
    }

    private static ArrayList<Volunteer> getVolunteerFromRS(ResultSet rs) throws SQLException {
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String nationalId = rs.getString("national_id");
            Date dateOfBirth = new Date(rs.getDate("date_of_birth").getTime());
            boolean isActive = rs.getBoolean("is_active");
            int addressId = rs.getInt("address_id");
            Address address = Address.retrieveAddress(addressId);
            ArrayList<Donation> donationHistory = Donation.retrievePersonDonations(id);
            ArrayList<Task> assignedTasks = Person.retrievePersonTasks(id);
            String userType = rs.getString("user_type");
            ArrayList<Skill> skills = Volunteer.retrieveVolunteerSkills(id);
            volunteers.add(new Volunteer(id, name, gender, phoneNumber, email, password, nationalId, dateOfBirth, isActive,
                    address, donationHistory, assignedTasks, userType, skills));
        }
        return volunteers;
    }
}
