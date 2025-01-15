package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import model.DesignPatterns.IObserver;
import model.DesignPatterns.VolunteerObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        RealCampaign realCampaign = new RealCampaign(1 , new Staff(
                "Mohaemd Khaled",
                "Male",
                "01050209620",
                "Testing@gmail.com",
                "123",
                "12345678910",
                new Date(),
                true,
                new Address("Cairo"),
                new ArrayList<>(),
                new ArrayList<>(),
                Person.UserType.staff,
                "Alo",
                "Operations" //Default Staff Department
        ));
//        IObserver volunteerObserver = new VolunteerObserver(realCampaign , "medo20333883@gmail.com");
//        IObserver newVolunteerObserver = new VolunteerObserver(realCampaign , "abdalrahmank2000@gmail.com");
//        realCampaign.notifyObservers();
        launch(args);
    }
}