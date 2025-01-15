package model.DesignPatterns.command;

import model.DatabaseConnection;
import model.Donation;
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

        String command = "INSERT INTO donation (amount, date, person_id) VALUES(?, ?, ?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, donation.getAmount());
            statement.setDate(2, new java.sql.Date(donation.getDate().getTime()));
            statement.setInt(3, donation.getPersonId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    donation.setId(rs.getInt(1));
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void unExecute() {

        if (donation.getId() > 0) {
            String command = "DELETE FROM donation WHERE id=?";
            Connection conn = DatabaseConnection.getInstance().getConnection();
            try {
                PreparedStatement statement = conn.prepareStatement(command);
                statement.setInt(1, donation.getId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Donation deleted successfully.");
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Donation does not exist or has not been inserted.");
        }
    }

    }

