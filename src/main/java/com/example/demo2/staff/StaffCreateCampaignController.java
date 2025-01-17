package com.example.demo2.staff;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Address;
import model.Campaign;
import model.Staff;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class StaffCreateCampaignController {
    Date sDate;
    Date eDate;
    @FXML
    private TextField campaignTitle;
    @FXML
    private TextArea campaignDescription;
    @FXML
    private TextField goalAmount;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

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
    private void createCampaign(javafx.event.ActionEvent event) throws IOException {
        Campaign campaign = new Campaign(
                new Staff(2,"Alice Johnson",
                        "Female",
                        "1122334455",
                        "alice.johnson@example.com",
                        "password789",
                        "34567890123456",
                        new Date(),
                        true,
                        new Address("Cairo"),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        "Manager",
                        "Human Resources"
                ) // Will be changed to current loggedIn user through UserLoginContext
                , campaignTitle.getText(), campaignDescription.getText(),
                Double.parseDouble(goalAmount.getText()), sDate, eDate);
        Campaign.createCampaign(campaign);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Campaign Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Campaign Created!");
        alert.showAndWait();

    }

    @FXML
    private void assignTaskButton(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffCreateTask.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }

    @FXML
    private void getDate(ActionEvent event) {
        Object source = event.getSource();
        if (source == startDate) {
            LocalDate birthDate = startDate.getValue();
            sDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else if (source == endDate) {
            LocalDate endDateValue = endDate.getValue();
            eDate = Date.from(endDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }


}
