package model.DesignPatterns.strategy;

import model.Person;
import model.Staff;
import model.Volunteer;

import java.util.ArrayList;

public class MobileNumberLogin implements ILogin {
    private String phoneNumber;
    private String password;

    public MobileNumberLogin(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public boolean validateUserInput() {

        if (phoneNumber == null || password == null) return false;
        if (phoneNumber.length() != 11) return false;
        try {
            Integer.parseInt(phoneNumber);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    @Override
    public Person login() {
        if (!validateUserInput()) return null;
        return Person.retrievePersonByPhoneAndPassword(phoneNumber, password);
    }
}
