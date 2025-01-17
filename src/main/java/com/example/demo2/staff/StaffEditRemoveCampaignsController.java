package com.example.demo2.staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Campaign;

import java.io.IOException;
import java.util.Optional;

public class StaffEditRemoveCampaignsController {
    @FXML
    private TableView<Campaign> CampaignTable;

    @FXML
    private TableColumn<Campaign, String> taskColumn;

    @FXML
    private TableColumn<Campaign, String> collectedAmountColumn;

    @FXML
    private TableColumn<Campaign, String> descriptionColumn;

    private ObservableList<Campaign> campaigns;

    @FXML
    public void initialize() {
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        collectedAmountColumn.setCellValueFactory(new PropertyValueFactory<>("collectedAmount"));
        campaigns = FXCollections.observableArrayList(Campaign.retrieveAllCampaigns());
        CampaignTable.setItems(campaigns);
    }

    @FXML
    private void goToIntroPage(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/StaffIntroPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Home Page");
        stage.show();
    }

    @FXML
    private void deleteCampaign() {
        Campaign selectedCampaign = CampaignTable.getSelectionModel().getSelectedItem();
        if (selectedCampaign == null) {
            showAlert("Error", "No campaign selected", "Please select a campaign", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Campaign");
        confirmationAlert.setHeaderText("Are you sure you want to delete this campaign?");
        confirmationAlert.setContentText(selectedCampaign.getTitle());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Campaign.deleteCampaign(selectedCampaign.getId());
            campaigns.remove(selectedCampaign);
        }
    }

    @FXML
    private void editCampaign() throws IOException {
        Campaign selectedCampaign = CampaignTable.getSelectionModel().getSelectedItem();
        if (selectedCampaign == null) {
            showAlert("Error", "No campaign selected", "Please select a campaign to edit.", Alert.AlertType.ERROR);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/StaffCreate_UpdateCampaign.fxml"));
        Parent editPageRoot = loader.load();

        StaffCreateCampaignController controller = loader.getController();
        controller.prepopulateWithData(selectedCampaign, true);

        Stage stage = new Stage();
        stage.setScene(new Scene(editPageRoot));
        stage.setTitle("Edit Campaign");
        stage.show();
    }


    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
