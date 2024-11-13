package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddEventVolunteerController {

    public TextField volunteerNameField;
    public ComboBox eventChoiceBoxForVolunteer;
    public DatePicker eventDateField;
    public TextField eventNameField;

    @FXML
    public void addEventButton(ActionEvent actionEvent) throws IOException {
        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Event added successfully!");

        alert.showAndWait();
    }

    @FXML
    public void addVolunteerButton(ActionEvent actionEvent) throws IOException {
        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Volunteer Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Volunteer added successfully!");

        alert.showAndWait();
    }



}
