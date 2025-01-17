package com.example.demo2.volunteer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.DesignPatterns.command.DonationInvoker;
import model.DesignPatterns.command.MakeDonationCommand;
import model.DesignPatterns.decorator.BasicPayment;
import model.DesignPatterns.decorator.ExtraFeesDecorator;
import model.DesignPatterns.strategy.UserLoginContext;
import model.DesignPatterns.template.Donation;
import model.DesignPatterns.template.MoneyDonation;

import java.io.IOException;
import java.util.Date;

public class VolunteerDonateController {

    public javafx.scene.control.TextField donationAmount;
    private DonationInvoker donationInvoker = new DonationInvoker();
    UserLoginContext instance = UserLoginContext.getInstance();

    @FXML
    private void DonateWithFawry(javafx.event.ActionEvent event) throws IOException {
        try {
            // Validate input
            String amountText = donationAmount.getText();
            if (amountText.isEmpty() || Double.parseDouble(amountText) <= 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid donation amount.");
                return;
            }

            // Parse the amount
            double originalAmount = Double.parseDouble(amountText);

            // Create a basic payment instance
            BasicPayment basicPayment = new BasicPayment();
            basicPayment.paymentAmount = originalAmount;

            // Decorate the payment with extra fees
            ExtraFeesDecorator decoratedPayment = new ExtraFeesDecorator(basicPayment);
            decoratedPayment.setServiceFee(originalAmount * 0.05);

            // Calculate the final amount
            double finalAmount = decoratedPayment.pay();

            // Show donation details in a dialog
            showDonationDetailsDialog(originalAmount, finalAmount);

            // Create a donation instance with the decorated amount
            Donation donation = new MoneyDonation(new Date(), UserLoginContext.getInstance().getLoggedInUser().getId(), finalAmount);

            // Create and execute the donation command
            MakeDonationCommand donationCommand = new MakeDonationCommand(donation);
            donationInvoker.executeCommand(donationCommand);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Donation Success", "Thank you for your donation of $" + finalAmount + "!");
            donationAmount.setText(""); // Clear the text field
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a numeric donation amount.");
        }
    }


    @FXML
    private void applyDiscount(javafx.event.ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Discount Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Discount Applied!");
        alert.showAndWait();

    }

    @FXML
    private void undoDonation(javafx.event.ActionEvent event) {
        try {
            // Check if there is any command in the history
            if (!donationInvoker.hasCommands()) {
                showAlert(Alert.AlertType.ERROR, "Error", "No donations found to undo.");
                return;
            }

            // Retrieve the last command (the last executed donation)
            MakeDonationCommand lastCommand = (MakeDonationCommand) donationInvoker.getLastCommand();
            Donation lastDonation = lastCommand.getDonation(); // Get the donation from the command

            // If the donation is MoneyDonation, set the amount in the text field before undoing
            if (lastDonation instanceof MoneyDonation) {
                MoneyDonation moneyDonation = (MoneyDonation) lastDonation;
                donationAmount.setText(String.valueOf(moneyDonation.getAmount()));
            }

            donationInvoker.unExecuteLastCommand();

            // Notify the user
            showAlert(Alert.AlertType.INFORMATION, "Success", "Donation successfully undone.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred while undoing the donation.");
        }
    }


    @FXML
    private void goToVolunteerIntro(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/VolunteerIntroPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showDonationDetailsDialog(double originalAmount, double finalAmount) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Donation Details");
        dialog.setHeaderText("Review Your Donation Details");
        dialog.setContentText(
                "Original Donation Amount: $" + originalAmount + "\n" +
                        "Extra Fees (5%): $" + (finalAmount - originalAmount) + "\n" +
                        "Final Donation Amount: $" + finalAmount
        );
        dialog.showAndWait();
    }


}
