package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloController {


    @FXML
    public void RegisterPage(javafx.scene.input.MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Register Page");
        stage.show();
    }



    @FXML
    private void HomePage(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Home");
        stage.show();
    }


    @FXML
    private void StartPage(javafx.scene.input.MouseEvent event) throws IOException {
        // Load the StartPage.fxml file again
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Charity");
        stage.show();
    }


    //Volunteer Button in Home page
    @FXML
    private void handleVolunteerButton(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerReservation.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteer Reservation");
        stage.show();
    }


    @FXML
    private void DonateWithCreditCard(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreditCardInfo.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Credit Card Payment");
        stage.show();
    }


    @FXML
    private void DonateWithfawry(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FawryPayment.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Fawry Payment");
        stage.show();
    }


    //Next button in Card Info
    @FXML
    public void handleDonateButton(ActionEvent event) {
        // Implement your payment processing logic here

        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Payment completed successfully!");

        alert.showAndWait();
    }


    @FXML
    public void VolunteerAcceptance(ActionEvent event) {
        // Implement your payment processing logic here

        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reservation Completed successfully!");

        alert.showAndWait();
    }




    /*Staff pages*/



    @FXML
    public void StaffHomePage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffHomePage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Home Page");
        stage.show();

    }


    @FXML
    private void DonateWithCreditCardStaff(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreditCardInfoStaff.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Credit Card Payment");
        stage.show();
    }

    @FXML
    private void DonateWithfawryStaff(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FawryPaymentStaff.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Fawry Payment");
        stage.show();
    }

    @FXML
    public void viewVolunteersButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewVolunteer.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Volunteer View Page");
        stage.show();

    }


    @FXML
    public void addEventButton(ActionEvent actionEvent) throws IOException {
        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Event added successfully!");

        alert.showAndWait();
    }


    @FXML
    public void addVolunteerButton(ActionEvent actionEvent) throws IOException {
        // Display success notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Volunteer Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Volunteer added successfully!");

        alert.showAndWait();
    }

    @FXML
    public void addEventVolunteer(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEventandVolunteer.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Add Event/Volunteer Page");
        stage.show();
    }


}