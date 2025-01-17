package com.example.demo2.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Person;
import model.Task;
import model.Volunteer;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class StaffVolunteerList {

    public ListView volunteersListView;
    public ComboBox TaskSelectionBox;
    public Button viewVolunteers;

    ObservableList<Task> tasksList = FXCollections.observableArrayList();
    ObservableList<Volunteer> volunteerlist = FXCollections.observableArrayList();

    public void initialize() {
        // Load tasks into the observable list
        tasksList.addAll(Task.retrieveAllTasks());

        // Bind tasksList to the ComboBox
        TaskSelectionBox.setItems(tasksList);

        // Set the cell factory for displaying the name of the task in the drop-down list
        TaskSelectionBox.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? "" : task.getName());
            }
        });

        // Set the button cell factory to display the selected task's name in the ComboBox
        TaskSelectionBox.setButtonCell(new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? "" : task.getName());
            }
        });
    }

    @FXML
    private void viewVolunteers(ActionEvent event) {
        Task selectedtask = (Task)TaskSelectionBox.getSelectionModel().getSelectedItem();
        volunteersListView.setItems(volunteerlist);

        volunteersListView.setCellFactory(lv -> new ListCell<Volunteer>() {
            @Override
            protected void updateItem(Volunteer volunteer, boolean empty) {
                super.updateItem(volunteer, empty);
                setText(empty || volunteer == null ? "" : volunteer.getName());
            }
        });

      //  volunteerlist.add(); //method takes the selected task and retrieves all volunteers associated with this task


    }


    @FXML
    private void AddEventVolunteersButton(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEventandVolunteer.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }

    @FXML
    private void viewVolunteersByTask(javafx.event.ActionEvent event) {
        // Get the selected task from the dropdown menu
        Task selectedTask = (Task) TaskSelectionBox.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            // Retrieve the persons assigned to the selected task
            ArrayList<Person> assignedVolunteers = Person.retrievePersonsByTaskId(selectedTask.getId());

            // Populate the ListView with the retrieved volunteers
            volunteersListView.getItems().clear();
            for (Person person : assignedVolunteers) {
                volunteersListView.getItems().add(person.getName());
            }
        } else {
            // Handle case where no task is selected
            volunteersListView.getItems().clear();
            volunteersListView.getItems().add("No task selected!");
        }
    }

    @FXML
    private void EditAndRemoveTasksButton(javafx.event.ActionEvent event) throws IOException {
        // Use the correct path relative to the package
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit_RemoveTasks.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Edit and Remove Tasks");
        stage.show();
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
