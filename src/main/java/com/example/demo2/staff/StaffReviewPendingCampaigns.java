package com.example.demo2.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Campaign;

import java.io.IOException;
import java.util.ArrayList;

public class StaffReviewPendingCampaigns {
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
        ArrayList<Integer> originalIndices = new ArrayList<>();
        ObservableList<String> observableCampaigns = FXCollections.observableArrayList();
        for (int i = 0; i < campaigns.size(); i++) {
            Campaign campaign = campaigns.get(i);
            if ("PendingAcceptanceState".equals(campaign.getCampaignState())) {
                observableCampaigns.add(campaign.getTitle() + " (" + campaign.getCampaignState() + ")");
                originalIndices.add(i);
            }
        }
        campaignList.setItems(observableCampaigns);
        campaignList.getProperties().put("originalIndices", originalIndices);
    }

    @FXML
    private void acceptCampaignIdea(javafx.event.ActionEvent event) throws IOException {
        int selectedCampaign = campaignList.getSelectionModel().getSelectedIndex();

        if (selectedCampaign == -1) {
            showAlert("Error", "No campaign selected", "Please select a campaign to edit.", Alert.AlertType.ERROR);
            return;
        }
        ArrayList<Integer> originalIndices = (ArrayList<Integer>) campaignList.getProperties().get("originalIndices");
        int originalIndex = originalIndices.get(selectedCampaign);
        System.out.println(originalIndex + 1);
        Campaign campaign = Campaign.retrieveCampaign(originalIndex + 1);
        campaign.executeHandleState();
        campaign.executeHandleState();
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
