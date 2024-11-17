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

public class TableViewController implements Initializable {

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    private final ObservableList<Task> taskList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the table and load tasks
        initializeTable();
        loadTasks();
    }

    private void initializeTable() {
        // Set up the columns
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Make columns editable
        tasksTable.setEditable(true);
        taskColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Edit event handling
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

        // Bind task list to the table
        tasksTable.setItems(taskList);
    }

    private void loadTasks() {
        try {
            ArrayList<Task> tasks = Task.retrieveAllTasks();
            if (tasks != null) {
                System.out.println("Retrieved tasks: " + tasks.size());
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
            System.out.println("Please select a task to delete.");
        }
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
