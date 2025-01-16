package model.DesignPatterns.command;

import model.DatabaseConnection;
import model.DesignPatterns.template.Donation;
import model.DesignPatterns.template.MoneyDonation;
import model.DesignPatterns.template.InKindDonation;
import model.Person;
import java.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MakeDonationCommand implements ICommand {
    private Donation donation;


    public MakeDonationCommand(Donation donation) {
        this.donation = donation;
    }

    @Override
    public void execute() {
        if (donation instanceof MoneyDonation) {
            executeMoneyDonation();
        } else if (donation instanceof InKindDonation) {
            executeInKindDonation();
        } else {
            System.out.println("Invalid donation type.");
        }
    }

    private void executeMoneyDonation() {
        String command = "INSERT INTO donation (amount, date, person_id) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, ((MoneyDonation) donation).getAmount());
            statement.setDate(2, new java.sql.Date(donation.getDate().getTime()));
            statement.setInt(3, donation.getPersonId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    donation.setId(rs.getInt(1));
                }
                insertMoneyDonation((MoneyDonation) donation);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeInKindDonation() {
        String command = "INSERT INTO donation (date, person_id) VALUES(?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new java.sql.Date(donation.getDate().getTime()));
            statement.setInt(2, donation.getPersonId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    donation.setId(rs.getInt(1));
                }

                insertInKindDonation((InKindDonation) donation);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertMoneyDonation(MoneyDonation moneyDonation) {
        String command = "INSERT INTO money_donation (donation_id, amount) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, moneyDonation.getId());
            statement.setDouble(2, moneyDonation.getAmount());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertInKindDonation(InKindDonation inKindDonation) {
        String command = "INSERT INTO in_kind_donation (donation_id, quantity, type, address_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, inKindDonation.getId());
            statement.setInt(2, inKindDonation.getQuantity());
            statement.setString(3, inKindDonation.getType());
            statement.setInt(4, inKindDonation.getAddress().getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unExecute() {
        if (donation.getId() > 0) {
            if (donation instanceof MoneyDonation) {
                deleteMoneyDonation();
            } else if (donation instanceof InKindDonation) {
                deleteInKindDonation();
            } else {
                System.out.println("Invalid donation type.");
            }
        } else {
            System.out.println("Donation does not exist or has not been inserted.");
        }
    }

    private void deleteMoneyDonation() {
        String command = "DELETE FROM money_donation WHERE donation_id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donation.getId());
            statement.executeUpdate();
            deleteDonation();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteInKindDonation() {
        String command = "DELETE FROM in_kind_donation WHERE donation_id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donation.getId());
            statement.executeUpdate();
            deleteDonation();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteDonation() {
        String command = "DELETE FROM donation WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, donation.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

