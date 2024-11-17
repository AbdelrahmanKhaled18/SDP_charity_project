package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Person;
import model.Task;
import model.Volunteer;

import java.io.IOException;

public class AddEventVolunteerController {


    public TextField TaskNameField;
    public Button addTaskButton;
    public ComboBox selectTaskCombobox;
    public TextField taskDescription;
    public ComboBox selectVolunteerCombobox;

    ObservableList<Task> tasksList = FXCollections.observableArrayList();
    ObservableList<Volunteer> volunteerlist = FXCollections.observableArrayList();

    public void initialize() {
        // Set items for task ComboBox
        selectTaskCombobox.setItems(tasksList);

        // Use custom cell factory for Task ComboBox to display task names
        selectTaskCombobox.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? "" : task.getName()); // Display task name or empty string
            }
        });

        // Update displayed value in the ComboBox after selection
        selectTaskCombobox.setButtonCell(new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? "" : task.getName());
            }
        });

        // Populate tasks list
        tasksList.addAll(Task.retrieveAllTasks());

        // Set items for volunteer ComboBox
        selectVolunteerCombobox.setItems(volunteerlist);

        // Use custom cell factory for Volunteer ComboBox to display volunteer names
        selectVolunteerCombobox.setCellFactory(lv -> new ListCell<Volunteer>() {
            @Override
            protected void updateItem(Volunteer volunteer, boolean empty) {
                super.updateItem(volunteer, empty);
                setText(empty || volunteer == null ? "" : volunteer.getName()); // Display volunteer name or empty string
            }
        });

        // Update displayed value in the ComboBox after selection
        selectVolunteerCombobox.setButtonCell(new ListCell<Volunteer>() {
            @Override
            protected void updateItem(Volunteer volunteer, boolean empty) {
                super.updateItem(volunteer, empty);
                setText(empty || volunteer == null ? "" : volunteer.getName());
            }
        });

        // Populate volunteers list
        volunteerlist.addAll(Volunteer.retrieveAllVolunteers());
    }



    @FXML
    public void addTaskButton(ActionEvent actionEvent) throws IOException {

        String taskName = TaskNameField.getText();
        String description = taskDescription.getText();

        boolean success = Task.createTask(new Task(taskName, description));

        if (!success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task Creation Error");
            alert.setHeaderText(null);
            alert.setContentText("Error in creating task");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Creation Success");
        alert.setHeaderText(null);
        alert.setContentText("Task created successfully");
        alert.showAndWait();


    }

    @FXML
    public void addVolunteerButton(ActionEvent actionEvent) throws IOException {

        Task selectedtask = (Task)selectTaskCombobox.getSelectionModel().getSelectedItem();
        Volunteer selectedvolunteer = (Volunteer)selectVolunteerCombobox.getSelectionModel().getSelectedItem();

        if (selectedtask == null || selectedvolunteer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select both a task and a volunteer.");
            alert.showAndWait();
            return;
        }

        System.out.println("Selected Task ID: " + selectedtask.getId());
        System.out.println("Selected Volunteer ID: " + selectedvolunteer.getId());

        boolean success = Person.addPersonTask(selectedvolunteer.getId(), selectedtask.getId());

        if (!success) {
            System.out.println("Failed to add volunteer to task.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Volunteer Error");
            alert.setHeaderText(null);
            alert.setContentText("Error in adding volunteer.");
            alert.showAndWait();
            return;
        }

        System.out.println("Volunteer added successfully.");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Volunteer Success");
        alert.setHeaderText(null);
        alert.setContentText("Volunteer added successfully.");
        alert.showAndWait();
    }

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


}
