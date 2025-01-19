package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DesignPatterns.command.DonationInvoker;
import model.DesignPatterns.command.MakeDonationCommand;
import model.DesignPatterns.strategy.UserLoginContext;
import model.DesignPatterns.template.Donation;
import model.DesignPatterns.template.InKindDonation;
import model.Volunteer;

import java.io.IOException;
import java.util.Date;

public class InKindDonationController {

    @FXML
    private TextField typeOfDonation;

    @FXML
    private Spinner<Integer> amountofDonation;

    private DonationInvoker donationInvoker = new DonationInvoker();


    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new
                SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        amountofDonation.setValueFactory(valueFactory);
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

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle(pageTitle);
        stage.show();
    }

    @FXML
    private void inKindDonation(javafx.event.ActionEvent event) {

        String type = typeOfDonation.getText();
        Integer quantity = amountofDonation.getValue();

        if (type == null || type.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid donation type.");
            return;
        }

        if (quantity == null || quantity <= 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid quantity greater than 0.");
            return;
        }


        int personId = UserLoginContext.getInstance().getLoggedInUser().getId();
        if (personId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to identify the logged-in user. Please log in again.");
            return;
        }


        Donation donation = new InKindDonation(new Date(), personId, type, quantity,
                UserLoginContext.getInstance().getLoggedInUser().getAddress());


        MakeDonationCommand command = new MakeDonationCommand(donation);
        donationInvoker.executeCommand(command);


        showAlert(Alert.AlertType.INFORMATION, "Success", "In-kind donation has been recorded successfully!");
        amountofDonation.getValueFactory().setValue(0);
        typeOfDonation.setText("");

    }


    @FXML
    private void UndoinKindDonation(javafx.event.ActionEvent event) {
        try {

            if (!donationInvoker.hasCommands()) {
                showAlert(Alert.AlertType.ERROR, "Error", "No donations found to undo.");
                return;
            }


            MakeDonationCommand lastCommand = (MakeDonationCommand) donationInvoker.getLastCommand();
            Donation lastDonation = lastCommand.getDonation();


            if (lastDonation instanceof InKindDonation) {
                InKindDonation inKindDonation = (InKindDonation) lastDonation;


                typeOfDonation.setText(inKindDonation.getType());
                amountofDonation.getValueFactory().setValue(inKindDonation.getQuantity());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "The last donation is not an in-kind donation.");
                return;
            }


            donationInvoker.unExecuteLastCommand();


            showAlert(Alert.AlertType.INFORMATION, "Success", "In-kind donation successfully undone.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred while undoing the donation.");
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
