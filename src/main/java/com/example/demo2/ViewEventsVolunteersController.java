package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Skill;
import model.Task;
import model.Volunteer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ViewEventsVolunteersController {

    public ListView volunteersListView;
    public ComboBox TaskSelectionBox;
    public Button viewVolunteers;

    ObservableList<Task> tasksList = FXCollections.observableArrayList();
    ObservableList<Volunteer> volunteerlist = FXCollections.observableArrayList();

    public void initialize() {
    TaskSelectionBox.setItems(tasksList);

        TaskSelectionBox.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? "" : task.getName());
            }
        });

        tasksList.addAll(Task.retrieveAllTasks());


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
    private void EditAndRemoveTasksButton(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit&RemoveTasks.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }
}
