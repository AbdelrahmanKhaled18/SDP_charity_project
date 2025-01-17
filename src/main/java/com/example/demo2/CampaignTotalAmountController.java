package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Campaign;
import model.DesignPatterns.observer.IObserver;
import model.DesignPatterns.strategy.UserLoginContext;
import model.Volunteer;

import java.io.IOException;
import java.util.ArrayList;

public class CampaignTotalAmountController implements IObserver {
    private Campaign currentCampaign;
    private ArrayList<Campaign> retrievedCampaigns;

    @FXML
    private ComboBox<String> campaignToView;

    @FXML
    private Label totalAmount;

    @FXML
    public void initialize() {
        currentCampaign = null;
        retrievedCampaigns = Campaign.retrieveAllCampaigns();
        ObservableList<String> observableCampaigns = FXCollections.observableArrayList();
        for (Campaign campaign : retrievedCampaigns) {
            observableCampaigns.add(campaign.getTitle());
        }
        campaignToView.setItems(observableCampaigns);
    }


    @FXML
    private void viewCampaign(javafx.event.ActionEvent event) throws IOException {
        if (currentCampaign != null) {
            currentCampaign.stop();
            currentCampaign.removeObservers(this);
        }
        int selectedCampaignIndex = campaignToView.getSelectionModel().getSelectedIndex();
        Campaign campaign = retrievedCampaigns.get(selectedCampaignIndex);
        campaign.registerObservers(this);
        campaign.start();
    }


    @FXML
    private void gotoMainPage(javafx.event.ActionEvent event) throws IOException {
        String pageTitle;
        FXMLLoader loader;
        if (UserLoginContext.getInstance().getLoggedInUser() instanceof Volunteer) {
            loader = new FXMLLoader(getClass().getResource("VolunteerIntroPage.fxml"));
            pageTitle = "Volunteer";
        } else {
            loader = new FXMLLoader(getClass().getResource("StaffIntroPage.fxml"));
            pageTitle = "Staff";
        }

        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle(pageTitle);
        stage.show();
    }

    @Override
    public void update(double currentCollectedAmount) {
        totalAmount.setText(String.format("%.2f", currentCollectedAmount));
    }
}
