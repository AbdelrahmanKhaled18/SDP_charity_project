package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DesignPatterns.template.Donation;
import model.DesignPatterns.template.MoneyDonation;

import java.io.IOException;
import java.util.Date;

public class StaffDonateController {


    public TextField donationAmountField;

    @FXML
    private void DonateWithfawryStaff(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffPaymentConfirmation.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Add Event/Volunteer Page");
        stage.show();
    }
    @FXML
    private void goToStaffIntro(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffIntroPage.fxml"));
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
    private void UndoDonation(javafx.event.ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Undo Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Donation Deleted!");
        alert.showAndWait();

    }

}
