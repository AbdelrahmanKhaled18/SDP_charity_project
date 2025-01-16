package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffInKindDonateController {

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
    private void inKindDonation(javafx.event.ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Donation Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Donation Confirmed!");
        alert.showAndWait();

    }

    @FXML
    private void UndoinKindDonation(javafx.event.ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Undo Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Donation Deleted!");
        alert.showAndWait();

    }

}
