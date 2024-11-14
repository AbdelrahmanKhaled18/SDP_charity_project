package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public abstract class Person {

    private int id;
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private String nationalId;
    private Date dateOfBirth;
    private boolean isActive;
    private Address address;

    private ArrayList<Donation> donationHistory;

    private ArrayList<Task> assignedTasks;

    public Person(String name, String gender, String phoneNumber, String email, String nationalId, Date dateOfBirth,
                  boolean isActive, Address address, ArrayList<Donation> donationHistory, ArrayList<Task> assignedTasks) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.address = address;
        this.donationHistory = donationHistory;
        this.assignedTasks = assignedTasks;
    }

    public Person(int id, String name, String gender, String phoneNumber, String email, String nationalId, Date dateOfBirth,
                  boolean isActive, Address address, ArrayList<Donation> donationHistory, ArrayList<Task> assignedTasks) {
        this(name, gender, phoneNumber, email, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Donation> getDonationHistory() {
        return donationHistory;
    }

    public void setDonationHistory(ArrayList<Donation> donationHistory) {
        this.donationHistory = donationHistory;
    }

    public ArrayList<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(ArrayList<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public void assignTask(Task task) {
        assignedTasks.add(task);
    }

    public void activateAccount() {
        this.isActive = true;
    }

    public void deactivateAccount() {
        this.isActive = false;
    }

    public void updateContactInfo(String email, String phoneNumber) {
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public void makeDonation(double amount) {
        Donation donation = new Donation(amount, new Date(), id);
        donationHistory.add(donation);
    }


    public static boolean createPerson(Person person) {
        String command = "INSERT INTO person (`name`, `gender`, `phone_number`, `email`, `national_id`," +
                "`date_of_birth`, `is_active`, `address_id`) VALUES(?,?,?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getName());
            statement.setString(2, person.getGender());
            statement.setString(3, person.getPhoneNumber());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getNationalId());
            statement.setDate(6, new java.sql.Date(person.getDateOfBirth().getTime()));
            statement.setBoolean(7, person.isActive());
            statement.setInt(8, person.getAddress().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            boolean success = false;
            if (rs.next()) {
                person.setId(rs.getInt(1));
                success = true;
            }
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }


    public static boolean updatePerson(Person person) {
        String command = "UPDATE person SET name=?, gender=?, phone_number=?, email=?, national_id=?," +
                "date_of_birth=?, is_active=?, address_id=? WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, person.getName());
            statement.setString(2, person.getGender());
            statement.setString(3, person.getPhoneNumber());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getNationalId());
            statement.setDate(6, new java.sql.Date(person.getDateOfBirth().getTime()));
            statement.setBoolean(7, person.isActive());
            statement.setInt(8, person.getAddress().getId());
            statement.setInt(9, person.getId());
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deletePerson(int personId) {
        String command = "DELETE FROM person WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }


    public static ArrayList<Task> retrievePersonTasks(int personId) {
        String command = "SELECT task.id AS id, task.name AS name, task.description AS description " +
                "FROM assigned_tasks INNER JOIN task ON assigned_tasks.task_id = task.id " +
                "WHERE assigned_tasks.person_id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Task> tasks = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                tasks.add(new Task(id, name, description));
            }
            statement.close();
            return tasks;
        } catch (SQLException e) {
            return null;
        }
    }


    public static boolean deletePersonTask(int personId, int taskId) {
        String command = "DELETE FROM assigned_tasks WHERE person_id=? AND task_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            statement.setInt(2, taskId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean addPersonTask(int personId, int taskId) {
        String command = "INSERT INTO assigned_tasks ('person_id', 'task_id') VALUES (?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            statement.setInt(2, taskId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }
}
