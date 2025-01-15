package model.DesignPatterns.strategy;

import model.Person;
import model.Staff;
import model.Volunteer;

import java.util.ArrayList;

public class UserNameLogin implements ILogin {
    private String username;
    private String password;


    public UserNameLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean validateUserInput() {
        return username != null && password != null;
    }

    @Override
    public String login() {
        if (!validateUserInput()) return "failed";
        ArrayList<Staff> staffMembers = Staff.retrieveAllStaff();
        ArrayList<Volunteer> volunteers = Volunteer.retrieveAllVolunteers();
        for (Staff staff : staffMembers) {
            if (staff.getName().equals(username) && staff.getPassword().equals(password)
                    && staff.getUserType() == Person.UserType.staff) {
                return "staff";
            }
        }
        for (Volunteer volunteer : volunteers) {
            if (volunteer.getName().equals(username) && volunteer.getPassword().equals(password)
                    && volunteer.getUserType() == Person.UserType.volunteer) {
                return "volunteer";

            }
        }
        return "failed";
    }
}
