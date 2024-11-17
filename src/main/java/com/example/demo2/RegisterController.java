package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class RegisterController {
    @FXML
    public TextField RegisterFirstname;
    @FXML
    public TextField RegisterLastname;
    @FXML
    public TextField RegisterEmail;
    @FXML
    public PasswordField RegisterNewpassword;
    @FXML
    public PasswordField RegisterReEnterPassword;
    @FXML
    private RadioButton StaffRadioButton;
    @FXML
    private RadioButton VolunteerRadioButton;


    @FXML
    private void IntroPage(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerIntoPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Credit Card Payment");
        stage.show();
    }

    @FXML
    private void LoginPage(ActionEvent event) throws IOException {
        // Load the StartPage.fxml file again
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Login Page");
        stage.show();
    }


    @FXML
    private void RegisterUser(ActionEvent event) throws IOException {


    }

}
