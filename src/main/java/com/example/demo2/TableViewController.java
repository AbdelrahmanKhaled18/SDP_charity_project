package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Task;

import java.net.URL;
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
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
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
        // Example: Load tasks from the database
        taskList.clear();
        taskList.addAll(Task.retrieveAllTasks()); // Replace with your database call
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

}
