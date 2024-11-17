package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class RegisterController {
    public String selectedGender;
    public String selectedRole;
    Date dateOfBirth;
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
    public TextField nationalId;
    @FXML
    private RadioButton genderMale;
    @FXML
    private RadioButton genderFemale;
    @FXML
    private RadioButton genderOther;
    @FXML
    private RadioButton StaffRadioButton;
    @FXML
    private RadioButton VolunteerRadioButton;
    @FXML
    private TextField RegisterPhoneNumber;
    @FXML
    private DatePicker RegisterDateOfBirth;

    public void initialize() {
        genderMale.setOnAction(event -> handleGenderSelection(genderMale));
        genderFemale.setOnAction(event -> handleGenderSelection(genderFemale));
        genderOther.setOnAction(event -> handleGenderSelection(genderOther));
        StaffRadioButton.setOnAction(event -> handleRoleSelection(StaffRadioButton));
        VolunteerRadioButton.setOnAction(event -> handleRoleSelection(VolunteerRadioButton));
    }

    private void handleRoleSelection(RadioButton selectedRadioButton) {
        StaffRadioButton.setSelected(selectedRadioButton == StaffRadioButton);
        VolunteerRadioButton.setSelected(selectedRadioButton == VolunteerRadioButton);
        selectedRole = selectedRadioButton.getText();
    }

    private void handleGenderSelection(RadioButton selectedRadioButton) {
        genderMale.setSelected(selectedRadioButton == genderMale);
        genderFemale.setSelected(selectedRadioButton == genderFemale);
        genderOther.setSelected(selectedRadioButton == genderOther);
        selectedGender = selectedRadioButton.getText();
    }

    @FXML
    private void LoginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent nextPageRoot = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextPageRoot));
        stage.setTitle("Login Page");
        stage.show();
    }


    @FXML
    private void getDate(ActionEvent event) {
        LocalDate birthDate = RegisterDateOfBirth.getValue();
        dateOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    private void RegisterUser(ActionEvent event) throws IOException {
        Address staffAddress = Address.retrieveAddress(5);


        if (Objects.equals(selectedRole, "Staff")) {
            Staff staff = new Staff(
                    RegisterFirstname.getText() + RegisterLastname.getText(),
                    selectedGender,
                    RegisterPhoneNumber.getText(),
                    RegisterEmail.getText(),
                    RegisterNewpassword.getText(),
                    nationalId.getText(),
                    dateOfBirth,
                    true,
                    staffAddress,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Person.UserType.staff,
                    selectedRole,
                    "Operations" //Default Staff Department
            );
            Staff.createStaff(staff);
        } else {
            Volunteer volunteer = new Volunteer(
                    RegisterFirstname.getText() + RegisterLastname.getText(),
                    selectedGender,
                    RegisterPhoneNumber.getText(),
                    RegisterEmail.getText(),
                    RegisterNewpassword.getText(),
                    nationalId.getText(),
                    dateOfBirth,
                    true,
                    staffAddress,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Person.UserType.staff,
                    new ArrayList<>()
            );
            Volunteer.createVolunteer(volunteer);
        }
    }


}
