package com.example.demo2.volunteer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class VolunteerCreditCardInfoController {


    public TextField volunteerCardnumber;
    public TextField volunteerCardholderName;
    public TextField volunteerCardCVV;

    @FXML
    public void NextButtonVolunteerCardInfo(ActionEvent event) {
        // Implement your payment processing logic here

        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Payment completed successfully!");

        alert.showAndWait();
    }


    @FXML
    private void DonatePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/VolunteerDonate.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Donating");
        stage.show();
    }

}
