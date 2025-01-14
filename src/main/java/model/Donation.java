package model;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

abstract class Donation {
    private int id;
    private double amount;
    private Date date;
    private int personId;

    public Donation(double amount, Date date, int personId) {
        this.amount = amount;
        this.date = date;
        this.personId = personId;
    }

    public Donation(int id, double amount, Date date, int personId) {
        this(amount, date, personId);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }


    public Boolean performDonation(Donation donation) {

        if (!validateDonation(donation)) {
            return false;
        }

        if (saveDonation(donation)) {
            notifyDonor();
            return true;
        }

        return false;
    }

    abstract boolean validateDonation(Donation donation);
    abstract boolean saveDonation(Donation donation);
    public void notifyDonor(){}



    public static boolean createDonation(Donation donation) {
        String command = "INSERT INTO donation (`amount`, `date`, `person_id`) VALUES(?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, donation.getAmount());
            statement.setDate(2, new java.sql.Date(donation.getDate().getTime()));
            statement.setInt(3, donation.getPersonId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            boolean success = false;
            if (rs.next()) {
                donation.setId(rs.getInt(1));
                success = true;
            }
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateDonation(Donation donation) {
        String command = "UPDATE donation SET amount=?, date=?, person_id=? WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setDouble(1, donation.getAmount());
            statement.setDate(2, new java.sql.Date(donation.getDate().getTime()));
            statement.setInt(3, donation.getPersonId());
            statement.setInt(4, donation.getId());
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteDonation(int donationId) {
        String command = "DELETE FROM donation WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donationId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Donation retrieveDonation(int donationId) {
        String command = "SELECT * FROM donation WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donationId);
            ResultSet rs = statement.executeQuery();
            Donation donation = null;
            if (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                Date date = new Date(rs.getDate("date").getTime());
                int personId = rs.getInt("person_id");
                donation = new Donation(id, amount, date, personId);
            }
            statement.close();
            return donation;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Donation> retrievePersonDonations(int personId) {
        String command = "SELECT * FROM donation WHERE person_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Donation> donations = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                Date date = new Date(rs.getDate("date").getTime());
                donations.add(new Donation(id, amount, date, personId));
            }
            statement.close();
            return donations;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Donation> retrieveAllDonations() {
        String command = "SELECT * FROM donation";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Donation> donations = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                Date date = new Date(rs.getDate("date").getTime());
                int personId = rs.getInt("person_id");
                donations.add(new Donation(id, amount, date, personId));
            }
            statement.close();
            return donations;
        } catch (SQLException e) {
            return null;
        }
    }
}
