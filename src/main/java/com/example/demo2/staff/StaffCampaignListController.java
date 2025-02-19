package com.example.demo2.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Campaign;
import model.DesignPatterns.proxy.CampaignStateManagementProxy;
import model.DesignPatterns.strategy.UserLoginContext;
import model.Staff;

import java.io.IOException;
import java.util.ArrayList;

public class StaffCampaignListController {
    @FXML
    private ListView<String> campaignList;

    @FXML
    private Button showPendingCampaignButton;

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
        if (((Staff) UserLoginContext.getInstance().getLoggedInUser()).getPosition().equals("Manager")) {
            showPendingCampaignButton.setVisible(true);
        }
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

    @FXML
    private void openPendingReviewCampaigns(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/ReviewPendingCampaigns.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Review Pending Campaigns");
        stage.show();
    }

    @FXML
    private void registerStaffToCampaign() {
        int selectedCampaign = campaignList.getSelectionModel().getSelectedIndex();
        if (selectedCampaign == -1) {
            showAlert("Error", "No campaign selected", "Please select a campaign to edit.", Alert.AlertType.ERROR);
            return;
        }
        Campaign.addCampaignVolunteer(selectedCampaign + 1, UserLoginContext.getInstance().getLoggedInUser().getId());
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
