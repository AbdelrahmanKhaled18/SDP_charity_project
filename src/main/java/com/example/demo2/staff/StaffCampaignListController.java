package com.example.demo2.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Campaign;

import java.io.IOException;
import java.util.ArrayList;

public class StaffCampaignListController {
    @FXML
    private ListView<String> campaignList;

    @FXML
    private void goToStaffIntro(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/StaffIntroPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }

    @FXML
    public void initialize() {
        ArrayList<Campaign> campaigns = Campaign.retrieveAllCampaigns();

        ObservableList<String> observableCampaigns = FXCollections.observableArrayList();
        for (Campaign campaign : campaigns) {
            observableCampaigns.add(campaign.getTitle() + " (" + campaign.getCampaignState() + ")");
        }
        campaignList.setItems(observableCampaigns);
    }

    @FXML
    private void retrieveCampaigns(javafx.event.ActionEvent event) throws IOException {
        initialize();
    }

    @FXML
    private void editRemoveCampaign(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/Edit_RemoveCampaigns.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteering");
        stage.show();
    }
}
