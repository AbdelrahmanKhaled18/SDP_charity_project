package model.DesignPatterns.template;

import java.sql.*;
import java.util.Date;

import model.Campaign;
import model.DatabaseConnection;

public class MoneyDonation extends Donation {

    private double amount;
    private int campaignId;

    public MoneyDonation(Date date, int personId, double amount) {
        super(date, personId);
        this.amount = amount;
    }

    public MoneyDonation(int id, Date date, int personId, double amount) {
        this(date, personId, amount);
        setId(id);
    }

    public MoneyDonation(Date date, int personId, double amount, int campaignId) {
        super(date, personId);
        this.amount = amount;
        this.campaignId = campaignId;
    }

    public MoneyDonation(int id, Date date, int personId, double amount, int campaignId) {
        this(date, personId, amount, campaignId);
        setId(id);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
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

    @Override
    public boolean undoDonation(Donation donation) {
        if (!(donation instanceof MoneyDonation)) {
            return false;
        }

        MoneyDonation moneyDonation = (MoneyDonation) donation;
        return (deleteMoneyDonation(moneyDonation));
    }

    private static boolean deleteMoneyDonation(MoneyDonation donation) {
        if (donation.campaignId != 0) {
            if (!Campaign.modifyCampaignCollectedAmountAndNotify(donation.campaignId, -donation.amount)) {
                return false;
            }
        }

        String deleteMoneyDonationCommand = "DELETE FROM money_donation WHERE donation_id=?";
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

    public static boolean createMoneyDonation(MoneyDonation donation) {
        if (!Donation.createDonation(donation)) {
            return false;
        }

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            // Update donation type
            PreparedStatement donationTypeStatement = conn.prepareStatement("UPDATE donation SET donation_type='money' WHERE id=?");
            donationTypeStatement.setInt(1, donation.getId());
            donationTypeStatement.executeUpdate();
            donationTypeStatement.close();

            // Prepare command for inserting money donation
            String command;
            if (donation.campaignId != 0) {
                command = "INSERT INTO money_donation (`donation_id`, `amount`, `campaign_id`) VALUES(?, ?, ?)";
            } else {
                command = "INSERT INTO money_donation (`donation_id`, `amount`) VALUES(?, ?)";
            }

            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donation.getId());
            statement.setDouble(2, donation.getAmount());
            if (donation.campaignId != 0) {
                statement.setInt(3, donation.campaignId);
            }
            int rowsAffected = statement.executeUpdate(); // Count rows affected
            statement.close();

            // If rows were affected, the operation was successful
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
