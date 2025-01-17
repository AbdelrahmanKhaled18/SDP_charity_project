package model.DesignPatterns.template;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

import model.DatabaseConnection;
import model.Entity;
import model.Address;

public abstract class Donation extends Entity {
    private Date date;
    private int personId;

    public Donation(Date date, int personId) {
        this.date = date;
        this.personId = personId;
    }

    public Donation(int id, Date date, int personId) {
        this(date, personId);
        setId(id);
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

    public abstract boolean undoDonation(Donation donation);

    public void notifyDonor() {
    }


    public static boolean createDonation(Donation donation) {
        String command = "INSERT INTO donation (`date`, `person_id`) VALUES(?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new java.sql.Date(donation.getDate().getTime()));
            statement.setInt(2, donation.getPersonId());
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
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateDonation(Donation donation) {
        String command = "UPDATE donation SET date=?, person_id=? WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setDate(1, new java.sql.Date(donation.getDate().getTime()));
            statement.setInt(2, donation.getPersonId());
            statement.setInt(3, donation.getId());
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
        String command = "SELECT * FROM donation " +
                "LEFT JOIN money_donation ON donation.id = money_donation.donation_id " +
                "LEFT JOIN in_kind_donation ON donation.id = in_kind_donation.donation_id " +
                "WHERE id=?";

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donationId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Donation> donationList = getDonationFromRS(rs);
            Donation donation = null;
            if (!donationList.isEmpty())
                donation = donationList.getFirst();
            statement.close();
            return donation;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Donation> retrievePersonDonations(int personId) {
        String command = "SELECT * FROM donation " +
                "LEFT JOIN money_donation ON donation.id = money_donation.donation_id " +
                "LEFT JOIN in_kind_donation ON donation.id = in_kind_donation.donation_id " +
                "WHERE person_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Donation> donations = getDonationFromRS(rs);
            statement.close();
            return donations;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Donation> retrieveAllDonations() {
        String command = "SELECT * FROM donation " +
                "LEFT JOIN money_donation ON donation.id = money_donation.donation_id " +
                "LEFT JOIN in_kind_donation ON donation.id = in_kind_donation.donation_id ";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Donation> donations = getDonationFromRS(rs);
            statement.close();
            return donations;
        } catch (SQLException e) {
            return null;
        }
    }


    private static ArrayList<Donation> getDonationFromRS(ResultSet rs) throws SQLException {
        ArrayList<Donation> donations = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            Date date = new Date(rs.getDate("date").getTime());
            int personId = rs.getInt("person_id");

            String donationType = rs.getString("donation_type");
            Donation donation;
            if (donationType.equals("money")) {
                double amount = rs.getDouble("amount");
                donation = new MoneyDonation(id, date, personId, amount);
            } else { // type = "in_kind"
                int quantity = rs.getInt("quantity");
                String type = rs.getString("type");
                int addressId = rs.getInt("address_id");
                Address address = Address.retrieveAddress(addressId);
                donation = new InKindDonation(id, date, personId, type, quantity, address);
            }
            donations.add(donation);
        }
        return donations;
    }
}

