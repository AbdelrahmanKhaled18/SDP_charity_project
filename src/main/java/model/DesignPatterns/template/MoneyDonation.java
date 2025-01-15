package model.DesignPatterns.template;

import java.sql.*;
import java.util.Date;
import model.DatabaseConnection;

public class MoneyDonation extends Donation{

    private double amount;

    public MoneyDonation(Date date, int personId, double amount) {
        super(date, personId);
        this.amount = amount;
    }

    public MoneyDonation(int id, Date date, int personId, double amount) {
        this(date, personId, amount);
        setId(id);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    boolean validateDonation(Donation donation) {
        if (!(donation instanceof MoneyDonation)) {
            return false;
        }

        MoneyDonation moneyDonation = (MoneyDonation) donation;

        return moneyDonation.getAmount() > 1;
    }

    @Override
    boolean saveDonation(Donation donation) {
        return createMoneyDonation((MoneyDonation) donation);
    }

    public static boolean createMoneyDonation(MoneyDonation donation) {
        if (!Donation.createDonation(donation))
            return false;

        String command = "INSERT INTO money_donation (`donation_id`, `amount`) VALUES(?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, donation.getId());
            statement.setDouble(2, donation.getAmount());
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


    public static boolean updateInKindDonation(MoneyDonation donation) {
        boolean changesMade = Donation.updateDonation(donation);
        String command = "UPDATE money_donation SET amount=? WHERE donation_id=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement donationTypeStatement = conn.prepareStatement("UPDATE donation SET donation_type='money' WHERE id=?");
            donationTypeStatement.setInt(1, donation.getId());
            donationTypeStatement.executeUpdate();
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setDouble(1, donation.getAmount());
            statement.setInt(2, donation.getId());
            changesMade |= statement.executeUpdate() > 0;
            statement.close();
            return changesMade;
        } catch (SQLException e) {
            return false;
        }
    }
}
