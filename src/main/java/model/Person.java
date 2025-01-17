package model;

import model.DesignPatterns.iterator.PersonRSIterator;
import model.DesignPatterns.template.Donation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public abstract class Person extends Entity {
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private String password;
    private String nationalId;
    private Date dateOfBirth;
    private boolean isActive;
    private Address address;

    private ArrayList<Donation> donationHistory;
    private ArrayList<Task> assignedTasks;

    public Person(String name, String gender, String phoneNumber, String email, String password, String nationalId,
                  Date dateOfBirth, boolean isActive, Address address,
                  ArrayList<Donation> donationHistory, ArrayList<Task> assignedTasks) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.address = address;
        this.donationHistory = donationHistory;
        this.assignedTasks = assignedTasks;
    }

    public Person(int id, String name, String gender, String phoneNumber, String email, String password,
                  String nationalId, Date dateOfBirth, boolean isActive, Address address,
                  ArrayList<Donation> donationHistory, ArrayList<Task> assignedTasks) {
        this(name, gender, phoneNumber, email, password, nationalId, dateOfBirth, isActive, address, donationHistory, assignedTasks);
        setId(id);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    protected static boolean createPerson(Person person) {
        String command = "INSERT INTO person (`name`, `gender`, `phone_number`, `email`, `password`, `national_id`," +
                "`date_of_birth`, `is_active`, `address_id`) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getName());
            statement.setString(2, person.getGender());
            statement.setString(3, person.getPhoneNumber());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getPassword());
            statement.setString(6, person.getNationalId());
            statement.setDate(7, new java.sql.Date(person.getDateOfBirth().getTime()));
            statement.setBoolean(8, person.isActive());
            statement.setInt(9, person.getAddress().getId());
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
        String command = "UPDATE person SET name=?, gender=?, phone_number=?, email=?, password=?, " +
                "national_id=?, date_of_birth=?, is_active=?, address_id=? WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, person.getName());
            statement.setString(2, person.getGender());
            statement.setString(3, person.getPhoneNumber());
            statement.setString(4, person.getEmail());
            statement.setString(5, person.getPassword());
            statement.setString(6, person.getNationalId());
            statement.setDate(7, new java.sql.Date(person.getDateOfBirth().getTime()));
            statement.setBoolean(8, person.isActive());
            statement.setInt(9, person.getAddress().getId());
            statement.setInt(10, person.getId());
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

    public static ArrayList<Person> retrievePersonsByTaskId(int taskId) {
        String query = "SELECT p.id, p.name, p.gender, p.phone_number, p.email, p.national_id, p.date_of_birth, " +
                "p.is_active, p.user_type, p.address_id, " +
                "s.position AS staff_position, s.department AS staff_department " +
                "FROM person p " +
                "LEFT JOIN staff s ON p.id = s.person_id " +
                "INNER JOIN assigned_tasks at ON p.id = at.person_id " +
                "WHERE at.task_id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        ArrayList<Person> persons = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            PersonRSIterator rsIterator = new PersonRSIterator(rs);
            while (rsIterator.hasNext()) {
                persons.add(rsIterator.next());
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public static boolean addPersonTask(int personId, int taskId) {
        // Use correct column names from the database schema
        String command = "INSERT INTO assigned_tasks (person_id, task_id) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId); // Bind person_id
            statement.setInt(2, taskId); // Bind task_id

            int rowsInserted = statement.executeUpdate(); // Execute the query
            statement.close();
            return rowsInserted > 0; // Return true if insertion was successful
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return false if there was an error
        }
    }


    public static Person retrievePersonByEmailAndPassword(String email, String password) {
        String command = "SELECT * FROM person WHERE email=? AND password=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            PersonRSIterator rsIterator = new PersonRSIterator(rs);
            if (!rsIterator.hasNext()) {
                return null;
            }
            Person person = rsIterator.next();
            statement.close();
            return person;
        } catch (SQLException e) {
            return null;
        }
    }


    public static Person retrievePersonByPhoneAndPassword(String phone, String password) {
        String command = "SELECT * FROM person WHERE phone_number=? AND password=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, phone);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            PersonRSIterator rsIterator = new PersonRSIterator(rs);
            if (!rsIterator.hasNext()) {
                return null;
            }
            Person person = rsIterator.next();
            statement.close();
            return person;
        } catch (SQLException e) {
            return null;
        }
    }
}
