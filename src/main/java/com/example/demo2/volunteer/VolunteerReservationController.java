package com.example.demo2.volunteer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class VolunteerReservationController {

    public TextArea VolunteerExperience;
    public ChoiceBox ShiftPreference;
    public DatePicker DateAvailable;
    public TextField volunteerEmailaddress;
    public TextField VolunteerPhonenumber;
    public TextField volunteerFullname;

    @FXML
    public void VolunteerAcceptance(ActionEvent event) {
        // Implement your payment processing logic here

        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reservation Completed successfully!");

        alert.showAndWait();
    }

    @FXML
    private void VolunteerPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/VolunteerViewAssignedTasks.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }

}
