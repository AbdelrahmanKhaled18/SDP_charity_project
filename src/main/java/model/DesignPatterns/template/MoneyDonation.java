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
        return(deleteMoneyDonation(moneyDonation));
    }

    private static boolean deleteMoneyDonation(MoneyDonation donation) {
        if (donation.campaignId != 0) {
            if (!Campaign.modifyCampaignCollectedAmountAndNotify(donation.campaignId, - donation.amount)) {
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
        if (donation.campaignId != 0) {
            if (!Campaign.modifyCampaignCollectedAmountAndNotify(donation.campaignId, donation.amount)) {
                return false;
            }
        }

        if (!Donation.createDonation(donation)){
            return false;
        }

        String command = "INSERT INTO money_donation (`donation_id`, `amount`, campaign_id) VALUES(?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement donationTypeStatement = conn.prepareStatement("UPDATE donation SET donation_type='money' WHERE id=?");
            donationTypeStatement.setInt(1, donation.getId());
            donationTypeStatement.executeUpdate();
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, donation.getId());
            statement.setDouble(2, donation.getAmount());
            statement.setInt(3, donation.getCampaignId());
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
}
