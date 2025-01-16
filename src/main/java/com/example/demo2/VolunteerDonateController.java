package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DesignPatterns.template.Donation;
import model.DesignPatterns.template.MoneyDonation;

import java.io.IOException;
import java.util.Date;

public class VolunteerDonateController {

    public javafx.scene.control.TextField donationAmount;

    @FXML
    private void DonateWithFawry(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerPaymentConfirmation.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
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

}
