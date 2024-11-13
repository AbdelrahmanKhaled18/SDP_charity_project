package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffCreditCardInfoController {


    public TextField StaffCardnumber;
    public TextField staffCardholderName;
    public TextField staffCardCVV;
    public DatePicker staffCardExpiryDate;

    @FXML
    public void NextButtonStaffCardInfo(ActionEvent event) {
        // Implement your payment processing logic here

        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Payment completed successfully!");

        alert.showAndWait();
    }



    public void StaffDonatePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffDonate.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Add Event/Volunteer Page");
        stage.show();
    }
}
