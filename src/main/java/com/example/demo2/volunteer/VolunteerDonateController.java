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
import model.DesignPatterns.template.Donation;
import model.DesignPatterns.template.MoneyDonation;

import java.io.IOException;
import java.util.Date;

public class VolunteerDonateController {

    public javafx.scene.control.TextField donationAmount;
    private DonationInvoker donationInvoker = new DonationInvoker();

    @FXML
    private void DonateWithFawry(javafx.event.ActionEvent event) throws IOException {
        try {
            // Validate input
            String amountText = donationAmount.getText();
            if (amountText.isEmpty() || Double.parseDouble(amountText) <= 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid donation amount.");
                return;
            }

            // Create a donation instance
            double amount = Double.parseDouble(amountText);
            Donation donation = new MoneyDonation(new Date(), 3, amount);

            // Create and execute the donation command
            MakeDonationCommand donationCommand = new MakeDonationCommand(donation);

            donationInvoker.executeCommand(donationCommand);

            showAlert(Alert.AlertType.INFORMATION, "Donation Success", "Thank you for your donation of $" + amount + "!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a numeric donation amount.");
        }
    }


    @FXML
    private void applyDiscount(javafx.event.ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Discount Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Discount Applied!");
        alert.showAndWait();

    }
    @FXML
    private void undoDiscount(javafx.event.ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Undo Donation");
        alert.setHeaderText(null);
        alert.setContentText("Donation Deleted!");
        alert.showAndWait();

    }

    @FXML
    private void goToVolunteerIntro(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerIntroPage.fxml"));
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

}
