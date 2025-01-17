package com.example.demo2.staff;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Campaign;
import model.DesignPatterns.strategy.UserLoginContext;
import model.Staff;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StaffCreateCampaignController {
    Date sDate;
    Date eDate;
    private boolean isEdit = false;
    private Campaign existingCampaign;
    @FXML
    private Button createCampaignButton;
    @FXML
    private TextField campaignTitle;
    @FXML
    private TextArea campaignDescription;
    @FXML
    private TextField goalAmount;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    @FXML
    private void goToStaffIntro(ActionEvent event) throws IOException {
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
    private void saveOrUpdateCampaign(ActionEvent event) throws IOException {
        if (isEdit && existingCampaign != null) {
            existingCampaign.setTitle(campaignTitle.getText());
            existingCampaign.setDescription(campaignDescription.getText());
            existingCampaign.setGoalAmount(Double.parseDouble(goalAmount.getText()));
            existingCampaign.setStartDate(sDate);
            existingCampaign.setEndDate(eDate);

            Campaign.updateCampaign(existingCampaign);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campaign Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Campaign updated successfully!");
            alert.showAndWait();
        } else {
            Campaign newCampaign = new Campaign(
                    Staff.retrieveStaff(UserLoginContext.getInstance().getLoggedInUser().getId()),
                    campaignTitle.getText(),
                    campaignDescription.getText(),
                    Double.parseDouble(goalAmount.getText()),
                    sDate,
                    eDate
            );
            newCampaign.executeHandleState();
            Campaign.createCampaign(newCampaign);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campaign Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Campaign created successfully!");
            alert.showAndWait();
        }
    }

    @FXML
    private void getDate(ActionEvent event) {
        Object source = event.getSource();
        if (source == startDate) {
            LocalDate startDateValue = startDate.getValue();
            sDate = Date.from(startDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else if (source == endDate) {
            LocalDate endDateValue = endDate.getValue();
            eDate = Date.from(endDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }

    public void prepopulateWithData(Campaign campaign, boolean isEditMode) {
        this.isEdit = isEditMode;
        this.existingCampaign = campaign;

        if (isEdit) {
            createCampaignButton.setText("Update Campaign");
        }

        if (isEditMode && campaign != null) {
            campaignTitle.setText(campaign.getTitle());
            campaignDescription.setText(campaign.getDescription());
            goalAmount.setText(String.valueOf(campaign.getGoalAmount()));

            if (campaign.getStartDate() != null) {
                LocalDate startDateValue = campaign.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                startDate.setValue(startDateValue);
                sDate = campaign.getStartDate();
            }

            if (campaign.getEndDate() != null) {
                LocalDate endDateValue = campaign.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                endDate.setValue(endDateValue);
                eDate = campaign.getEndDate();
            }
        }
    }
}
