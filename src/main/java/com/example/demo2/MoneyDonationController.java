package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Campaign;
import model.DesignPatterns.command.DonationInvoker;
import model.DesignPatterns.command.MakeDonationCommand;
import model.DesignPatterns.decorator.BasicPayment;
import model.DesignPatterns.decorator.ExtraFeesDecorator;
import model.DesignPatterns.strategy.UserLoginContext;
import model.DesignPatterns.template.Donation;
import model.DesignPatterns.template.MoneyDonation;
import model.Person;
import model.Volunteer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MoneyDonationController {

    public javafx.scene.control.TextField donationAmount;
    private DonationInvoker donationInvoker = new DonationInvoker();
    private ArrayList<Campaign> retrievedCampaigns;
    @FXML
    private ComboBox<String> selectcampaignComboBox;

    @FXML
    public void initialize() {
        retrievedCampaigns = Campaign.retrieveAllCampaigns();
        ObservableList<String> observableCampaigns = FXCollections.observableArrayList();
        for (Campaign campaign : retrievedCampaigns) {
            observableCampaigns.add(campaign.getTitle());
        }
        selectcampaignComboBox.setItems(observableCampaigns);
    }

    @FXML
    private void DonateWithFawry(javafx.event.ActionEvent event) throws IOException {
        try {
            Donation donation;
            int selectedCampaignIndex = selectcampaignComboBox.getSelectionModel().getSelectedIndex();


            String amountText = donationAmount.getText();
            if (amountText.isEmpty() || Double.parseDouble(amountText) <= 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid donation amount.");
                return;
            }

            double originalAmount = Double.parseDouble(amountText);


            BasicPayment basicPayment = new BasicPayment();
            basicPayment.paymentAmount = originalAmount;


            ExtraFeesDecorator decoratedPayment = new ExtraFeesDecorator(basicPayment);
            decoratedPayment.setServiceFee(originalAmount * 0.05);


            double finalAmount = decoratedPayment.pay();


            showDonationDetailsDialog(originalAmount, finalAmount);


            if (selectedCampaignIndex >= 0) {
                Campaign campaign = retrievedCampaigns.get(selectedCampaignIndex);
                campaign.addDonation(originalAmount);
                donation = new MoneyDonation(new Date(), UserLoginContext.getInstance().getLoggedInUser().getId(), finalAmount, campaign.getId());
            } else {
                donation = new MoneyDonation(new Date(), UserLoginContext.getInstance().getLoggedInUser().getId(), finalAmount);
            }

            MakeDonationCommand donationCommand = new MakeDonationCommand(donation);
            donationInvoker.executeCommand(donationCommand);


            showAlert(Alert.AlertType.INFORMATION, "Donation Success", "Thank you for your donation of $" + finalAmount + "!");
            donationAmount.setText("");
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

            if (!donationInvoker.hasCommands()) {
                showAlert(Alert.AlertType.ERROR, "Error", "No donations found to undo.");
                return;
            }


            MakeDonationCommand lastCommand = (MakeDonationCommand) donationInvoker.getLastCommand();
            Donation lastDonation = lastCommand.getDonation();


            if (lastDonation instanceof MoneyDonation) {
                MoneyDonation moneyDonation = (MoneyDonation) lastDonation;
                donationAmount.setText(String.valueOf(moneyDonation.getAmount()));
            }

            donationInvoker.unExecuteLastCommand();


            showAlert(Alert.AlertType.INFORMATION, "Success", "Donation successfully undone.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred while undoing the donation.");
        }
    }


    @FXML
    private void backToMainPage(javafx.event.ActionEvent event) throws IOException {
        String pageTitle;
        FXMLLoader loader;
        if (UserLoginContext.getInstance().getLoggedInUser() instanceof Volunteer) {
            loader = new FXMLLoader(getClass().getResource("VolunteerIntroPage.fxml"));
            pageTitle = "Volunteer";
        } else {
            loader = new FXMLLoader(getClass().getResource("StaffIntroPage.fxml"));
            pageTitle = "Staff";
        }

        Parent nextPageRoot = loader.load();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle(pageTitle);
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
