package com.example.demo2;

import controller.Strategy.EmailLogin;
import controller.Strategy.MobileNumberLogin;
import controller.Strategy.UserLoginContext;
import controller.Strategy.UserNameLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public TextField LoginEmail;
    public PasswordField LoginPassword;
    public Button countinueLoginButton;
    UserLoginContext userLoginContext;

    private String currentStrategy = "EmailLogin";

    @FXML
    public void initialize() {
        // Default strategy: EmailLogin
        userLoginContext = new UserLoginContext(new EmailLogin("", ""));
        LoginEmail.setPromptText("Enter your email address"); // Default placeholder
    }



    @FXML
    public void RegisterPage(MouseEvent event) throws IOException {

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
    private void IntroPage(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerIntroPage.fxml"));
        Parent nextPageRoot = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene to the new page
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Credit Card Payment");
        stage.show();
    }

    @FXML
    private void setPhoneNumberStrategy(ActionEvent event) {
        // Change to PhoneNumberLogin strategy
        currentStrategy = "MobileNumberLogin";
        LoginEmail.setPromptText("Enter your phone number");
    }

    @FXML
    private void setUsernameStrategy(ActionEvent event) {
        // Change to UserNameLogin strategy
        currentStrategy = "UserNameLogin";
        LoginEmail.setPromptText("Enter your username");
    }


    @FXML
    private void performLogin(ActionEvent event) throws IOException {

        // Dynamically set the strategy based on the current input
        switch (currentStrategy) {
            case "MobileNumberLogin":
                userLoginContext.setLoginStrategyRef(new MobileNumberLogin(LoginEmail.getText(), LoginPassword.getText()));
                break;
            case "UserNameLogin":
                userLoginContext.setLoginStrategyRef(new UserNameLogin(LoginEmail.getText(), LoginPassword.getText()));
                break;
            default:
                // Default to EmailLogin if no button was pressed
                userLoginContext.setLoginStrategyRef(new EmailLogin(LoginEmail.getText(), LoginPassword.getText()));
                break;
        }

        String answer = userLoginContext.authenticate();
        System.out.println(answer);
        if (answer.equals("staff")) {
            System.out.println("staff");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffIntroPage.fxml"));
            Parent nextPageRoot = loader.load();
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the new page
            stage.setScene(new Scene(nextPageRoot));
            stage.setTitle("Staff Page");
            stage.show();

        } else if (answer.equals("volunteer")) {
            System.out.println("volu");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerIntroPage.fxml"));
            Parent nextPageRoot = loader.load();
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the new page
            stage.setScene(new Scene(nextPageRoot));
            stage.setTitle("Volunteer Page");
            stage.show();


        } else {
            System.out.println("Login failed!");
            LoginEmail.clear();
            LoginPassword.clear();
            LoginEmail.setPromptText("Invalid credentials. Try again!");
        }

    }


}
