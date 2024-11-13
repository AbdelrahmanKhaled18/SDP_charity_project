package com.example.demo2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ViewEventsVolunteersController {

    public ListView volunteersListView;
    public ComboBox eventSelectionBox;

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
}
