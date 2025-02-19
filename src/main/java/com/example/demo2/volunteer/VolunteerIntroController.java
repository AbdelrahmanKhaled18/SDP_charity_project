package com.example.demo2.volunteer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VolunteerIntroController {

    @FXML
    private void VolunteerInfoPage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/VolunteerReservation.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }

    @FXML
    private void DonateMoneyPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/MoneyDonation.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Donating");
        stage.show();
    }


    @FXML
    private void DonateInKindPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/InKindDonation.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Donating");
        stage.show();
    }

    @FXML
    private void viewCampaignFunds(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/TotalCampaignAmount.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Donating");
        stage.show();
    }

    @FXML
    private void logout(javafx.event.ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/LoginPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Add Event/Volunteer Page");
        stage.show();
    }
}
