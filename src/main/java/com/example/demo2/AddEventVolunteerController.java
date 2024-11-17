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
        selectTaskCombobox.setItems(tasksList);

        selectTaskCombobox.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? "" : task.getName());
            }
        });
        tasksList.addAll(Task.retrieveAllTasks());

        selectVolunteerCombobox.setItems(volunteerlist);

        selectVolunteerCombobox.setCellFactory(lv -> new ListCell<Volunteer>() {
            @Override
            protected void updateItem(Volunteer volunteer, boolean empty) {
                super.updateItem(volunteer, empty);
                setText(empty || volunteer == null ? "" : volunteer.getName());
            }
        });

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

        boolean success = Volunteer.addPersonTask(selectedvolunteer.getId(),selectedtask.getId());

        if (!success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Volunteer Error");
            alert.setHeaderText(null);
            alert.setContentText("Error in adding volunteer");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Volunteer Success");
        alert.setHeaderText(null);
        alert.setContentText("Volunteer added successfully");
        alert.showAndWait();
        return;


    }



}
