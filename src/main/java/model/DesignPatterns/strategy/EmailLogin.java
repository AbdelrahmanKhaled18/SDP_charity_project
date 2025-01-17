package model.DesignPatterns.strategy;

import model.Person;
import model.Staff;
import model.Volunteer;

import java.util.ArrayList;

public class EmailLogin implements ILogin {
    private String email;
    private String password;

    public EmailLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean validateUserInput() {
        if (email == null || password == null)
            return false;

        if (!email.contains("@") && !email.contains("."))
            return false;
        return true;
    }

    @Override
    public Person login() {
        if (!validateUserInput()) return null;
        return Person.retrievePersonByEmailAndPassword(email, password);
    }
}
