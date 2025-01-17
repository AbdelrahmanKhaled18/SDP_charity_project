package com.example.demo2.volunteer;

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

import java.io.IOException;
import java.util.Date;

public class VolunteerInKindDonateController {

    @FXML
    private TextField typeOfDonation;

    @FXML
    private Spinner<Integer> amountofDonation;

    private DonationInvoker donationInvoker = new DonationInvoker();


    @FXML
    public void initialize() {
        // Initialize the Spinner with a value factory starting at 0
        SpinnerValueFactory<Integer> valueFactory = new
                SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        amountofDonation.setValueFactory(valueFactory);
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

    @FXML
    private void inKindDonation(javafx.event.ActionEvent event) {

        String type = typeOfDonation.getText();
        Integer quantity = amountofDonation.getValue();

        if (type == null || type.isEmpty()) {
            showAlert("Validation Error", "Please enter a valid donation type.");
            return;
        }

        if (quantity == null || quantity <= 0) {
            showAlert("Validation Error", "Please enter a valid quantity greater than 0.");
            return;
        }

        // Get the logged-in user ID
        int personId = UserLoginContext.getInstance().getLoggedInUser().getId();
        if (personId == 0) {
            showAlert("Error", "Unable to identify the logged-in user. Please log in again.");
            return;
        }

        // Create InKindDonation object
        Donation donation = new InKindDonation(new Date(), personId, type, quantity,
                UserLoginContext.getInstance().getLoggedInUser().getAddress());

        // Execute the donation using the command pattern
        MakeDonationCommand command = new MakeDonationCommand(donation);
        donationInvoker.executeCommand(command);

        // Show success message
        showAlert("Success", "In-kind donation has been recorded successfully!");
    }


    @FXML
    private void UndoinKindDonation(javafx.event.ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Undo Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Donation Deleted!");
        alert.showAndWait();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
