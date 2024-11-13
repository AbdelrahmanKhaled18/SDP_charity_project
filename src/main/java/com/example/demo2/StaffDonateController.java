package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffDonateController {


    public TextField donationAmountField;

    @FXML
    private void DonateWithCreditCardStaff(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffCreditCardInfo.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Credit Card Payment");
        stage.show();
    }

    @FXML
    private void DonateWithfawryStaff(ActionEvent event) throws IOException {
        Stage paymentStage = new Stage();
        paymentStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
        paymentStage.setTitle("Payment Code");

        // Content of the popup window
        Label paymentLabel = new Label("Your Payment Code: ABC12345");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> paymentStage.close());

        VBox layout = new VBox(10, paymentLabel, closeButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(layout, 250, 150);
        paymentStage.setScene(scene);
        paymentStage.showAndWait();
    }


}
