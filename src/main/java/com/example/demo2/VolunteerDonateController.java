package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Donation;

import java.io.IOException;
import java.util.Date;

public class VolunteerDonateController {

    public javafx.scene.control.TextField donationAmount;

    @FXML
    private void DonateWithfawry() throws IOException {

        double Amount = Double.parseDouble(donationAmount.getText());
        boolean success = Donation.createDonation(new Donation(Amount,new Date(),1));

        if (!success) {
            Stage paymentStage = new Stage();
            paymentStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            paymentStage.setTitle("Payment Code");

            // Content of the popup window
            Label paymentLabel = new Label("Donation failed");
            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> paymentStage.close());

            VBox layout = new VBox(10, paymentLabel, closeButton);
            layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

            Scene scene = new Scene(layout, 250, 150);
            paymentStage.setScene(scene);
            paymentStage.showAndWait();
        }

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
