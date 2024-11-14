package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
    public ListView volunteersListView;


    //Next button in Card Info

    /*Staff pages*/


    @FXML
    public void StaffHomePage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewEvents&Volunteers.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Home Page");
        stage.show();

    }


    @FXML
    public void viewVolunteersButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffViewVolunteers.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteer View Page");
        stage.show();

    }


}