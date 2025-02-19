package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditandRemoveTaskController implements Initializable {

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    private final ObservableList<Task> taskList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeTable();
        loadTasks();
    }

    private void initializeTable() {

        taskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));


        tasksTable.setEditable(true);
        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        taskColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setName(event.getNewValue());
            Task.updateTask(task);
        });

        descriptionColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setDescription(event.getNewValue());
            Task.updateTask(task);
        });


        tasksTable.setItems(taskList);
    }

    private void loadTasks() {
        try {
            ArrayList<Task> tasks = Task.retrieveAllTasks();
            if (tasks != null) {
                tasks.forEach(task -> System.out.println(task.getName() + ": " + task.getDescription()));
                taskList.clear();
                taskList.addAll(tasks);
            } else {
                System.err.println("Failed to load tasks: No tasks retrieved.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while loading tasks: " + e.getMessage());
        }
    }


    @FXML
    private void deleteSelectedTask() {
        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasksTable.getItems().remove(selectedTask);
            Task.deleteTask(selectedTask.getId());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Task Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a task to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void goToViewVolunteers(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffVolunteersList.fxml"));
        Parent nextPageRoot = loader.load();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteers List");
        stage.show();
    }
}
