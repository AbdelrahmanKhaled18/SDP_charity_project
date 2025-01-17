package com.example.demo2.volunteer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Skill;
import model.Task;
import model.Volunteer;

import java.io.IOException;


public class VolunteerViewAssignedTasksController {


    public ListView SkillsList;
    public Button editSkillsButton;
    public ComboBox skillsChoice;
    public ListView AssignedTasks;


    ObservableList<Skill> comboBoxItems = FXCollections.observableArrayList();
    ObservableList<Skill> skillsListItems = FXCollections.observableArrayList();
    ObservableList<Task> AssignedTaskList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        skillsChoice.setItems(comboBoxItems);

         skillsChoice.setCellFactory(lv -> new ListCell<Skill>() {
            @Override
            protected void updateItem(Skill skill, boolean empty) {
                super.updateItem(skill, empty);
                setText(empty || skill == null ? "" : skill.getName());
            }
        });

        comboBoxItems.addAll(Skill.retrieveAllSkills());

        SkillsList.setItems(skillsListItems);
        SkillsList.setCellFactory(lv -> new ListCell<Skill>() {
            @Override
            protected void updateItem(Skill skill, boolean empty) {
                super.updateItem(skill, empty);
                setText(empty || skill == null ? "" : skill.getName());
            }
        });

        skillsListItems.addAll(Skill.retrievePersonSkills(1));



        AssignedTasks.setItems(AssignedTaskList);
        AssignedTasks.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setText(empty || task == null ? "" : task.getName());
            }
        });

        AssignedTaskList.addAll(Task.retrieveAllTasks());

    }

    public void addSkillButton(javafx.event.ActionEvent actionEvent) {

        Skill skill = (Skill) skillsChoice.getSelectionModel().getSelectedItem();
        boolean success = Volunteer.addVolunteerSkill(1,skill.getId());

        if (success) {
            skillsListItems.add(skill);
            return;
        }
        Stage SkillStage = new Stage();
        SkillStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
        SkillStage.setTitle("Error");

        // Content of the popup window
        Label paymentLabel = new Label("An error occurred");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> SkillStage.close());

        VBox layout = new VBox(10, paymentLabel, closeButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(layout, 250, 150);
        SkillStage.setScene(scene);
        SkillStage.showAndWait();
    }

    public void removeSkillButton(javafx.event.ActionEvent actionEvent) {

        Skill SelectedSkill = (Skill) SkillsList.getSelectionModel().getSelectedItem();
        

        boolean success = Volunteer.deleteVolunteerSkill(1,SelectedSkill.getId());
        if (success) {
            skillsListItems.remove(SelectedSkill);
            return;
        }
        Stage SkillDelete = new Stage();
        SkillDelete.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
        SkillDelete.setTitle("Error");

        // Content of the popup window
        Label paymentLabel = new Label("An error occurred");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> SkillDelete.close());

        VBox layout = new VBox(10, paymentLabel, closeButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Scene scene = new Scene(layout, 250, 150);
        SkillDelete.setScene(scene);
        SkillDelete.showAndWait();
        

    }

    @FXML
    private void goToVolunteerIntro(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerIntroPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }


}
