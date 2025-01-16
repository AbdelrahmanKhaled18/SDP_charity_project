package model.DesignPatterns.template;

import java.sql.*;
import java.util.Date;

import model.Address;
import model.DatabaseConnection;

public class InKindDonation extends Donation {

    private String type;
    private int quantity;
    private Address address;

    public InKindDonation(Date date, int personId, String type, int quantity, Address address) {
        super(date, personId);
        this.type = type;
        this.quantity = quantity;
        this.address = address;
    }

    public InKindDonation(int id, Date date, int personId, String type, int quantity, Address address) {
        this(date, personId, type, quantity, address);
        setId(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    boolean validateDonation(Donation donation) {

        if (!(donation instanceof InKindDonation)) {
            return false;
        }

        InKindDonation inKindDonation = (InKindDonation) donation;

        if (inKindDonation.getQuantity() <= 0) {
            return false;
        }

        String type = inKindDonation.getType();
        if (type == null || type.isEmpty() || type.matches("\\d+")) {
            return false;
        }

        return true;
    }

    @Override
    boolean saveDonation(Donation donation) {
        return createInKindDonation((InKindDonation) donation);
    }

    @Override
    public boolean undoDonation(Donation donation) {
        if (!(donation instanceof InKindDonation)) {
            return false;
        }

        InKindDonation inKindDonation = (InKindDonation) donation;
        return deleteInKindDonation(inKindDonation);
    }

    private static boolean deleteInKindDonation(InKindDonation donation) {
        String deleteMoneyDonationCommand = "DELETE FROM in_kind_donation WHERE donation_id=?";
        String deleteDonationCommand = "DELETE FROM donation WHERE id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement moneyDonationStatement = conn.prepareStatement(deleteMoneyDonationCommand);
            moneyDonationStatement.setInt(1, donation.getId());
            moneyDonationStatement.executeUpdate();
            moneyDonationStatement.close();
            PreparedStatement donationStatement = conn.prepareStatement(deleteDonationCommand);
            donationStatement.setInt(1, donation.getId());
            donationStatement.executeUpdate();
            donationStatement.close();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public static boolean createInKindDonation(InKindDonation donation) {
        if (!Donation.createDonation(donation))
            return false;

        String command = "INSERT INTO in_kind_donation (`donation_id`, `quantity`, `type`, address_id) VALUES(?,?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement donationTypeStatement = conn.prepareStatement("UPDATE donation SET donation_type='in_kind' WHERE id=?");
            donationTypeStatement.setInt(1, donation.getId());
            donationTypeStatement.executeUpdate();
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, donation.getId());
            statement.setInt(2, donation.getQuantity());
            statement.setString(3, donation.getType());
            statement.setInt(4, donation.getAddress().getId());
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


    public static boolean updateInKindDonation(InKindDonation donation) {
        boolean changesMade = Donation.updateDonation(donation);
        String command = "UPDATE in_kind_donation SET quantity=?, type=?, address_id=? WHERE donation_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donation.getQuantity());
            statement.setString(2, donation.getType());
            statement.setInt(3, donation.getAddress().getId());
            statement.setInt(4, donation.getId());
            changesMade |= statement.executeUpdate() > 0;
            statement.close();
            return changesMade;
        } catch (SQLException e) {
            return false;
        }
    }
}
