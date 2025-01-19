package com.example.demo2;

import model.DesignPatterns.strategy.EmailLogin;
import model.DesignPatterns.strategy.MobileNumberLogin;
import model.DesignPatterns.strategy.UserLoginContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

        userLoginContext = UserLoginContext.getInstance();
        userLoginContext.setLoginStrategyRef(new EmailLogin("", ""));
        LoginEmail.setPromptText("Enter your email address");
    }


    @FXML
    public void RegisterPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        Parent nextPageRoot = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Register Page");
        stage.show();
    }


    @FXML
    private void setPhoneNumberStrategy(ActionEvent event) {

        currentStrategy = "MobileNumberLogin";
        LoginEmail.setPromptText("Enter your phone number");
    }




    @FXML
    private void performLogin(ActionEvent event) throws IOException {


        if (currentStrategy.equals("MobileNumberLogin")) {
            userLoginContext.setLoginStrategyRef(new MobileNumberLogin(LoginEmail.getText(), LoginPassword.getText()));
        } else {

            userLoginContext.setLoginStrategyRef(new EmailLogin(LoginEmail.getText(), LoginPassword.getText()));
        }


        String answer = userLoginContext.authenticate();
        if (answer.equals("staff")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffIntroPage.fxml"));
            Parent nextPageRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(new Scene(nextPageRoot));
            stage.setTitle("Staff Page");
            stage.show();

        } else if (answer.equals("volunteer")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VolunteerIntroPage.fxml"));
            Parent nextPageRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(nextPageRoot));
            stage.setTitle("Volunteer Page");
            stage.show();
        } else {
            LoginEmail.clear();
            LoginPassword.clear();
            LoginEmail.setPromptText("Invalid credentials. Try again!");
        }
    }
}
