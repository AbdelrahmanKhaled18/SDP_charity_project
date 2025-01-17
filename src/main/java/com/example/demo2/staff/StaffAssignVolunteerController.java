package com.example.demo2.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Campaign;
import model.Person;
import model.Task;
import model.Volunteer;

import java.io.IOException;
import java.util.ArrayList;

public class StaffAssignVolunteerController {
    ArrayList<Volunteer> volunteers;
    ArrayList<Task> tasks;
    @FXML
    private ComboBox selectTask;
    @FXML
    private ComboBox selectVolunteer;

    @FXML
    public void initialize() {
        tasks = Task.retrieveAllTasks();
        volunteers = Volunteer.retrieveAllVolunteers();
        ObservableList<String> observableTasks = FXCollections.observableArrayList();
        ObservableList<String> observableVolunteers = FXCollections.observableArrayList();
        for (Task task : tasks) {
            observableTasks.add(task.getName());
        }
        selectTask.setItems(observableTasks);

        for (Volunteer volunteer : volunteers) {
            observableVolunteers.add(volunteer.getName());
        }
        selectVolunteer.setItems(observableVolunteers);
    }

    @FXML
    private void goToStaffIntro(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/StaffIntroPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }

    @FXML
    private void assignVolunteer(javafx.event.ActionEvent event) throws IOException {
        int selectedVolunteerIndex = selectVolunteer.getSelectionModel().getSelectedIndex();
        int selectedTaskIndex = selectTask.getSelectionModel().getSelectedIndex();

        Person.addPersonTask(selectedVolunteerIndex + 1, selectedTaskIndex + 1);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Volunteer Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Volunteer Assigned!");
        alert.showAndWait();

    }

    @FXML
    private void viewVolunteersPage(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/StaffVolunteersList.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }


}
