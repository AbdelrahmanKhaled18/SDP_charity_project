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
    public String login() {
        if (!validateUserInput()) return "failed";
        ArrayList<Staff> staffMembers = Staff.retrieveAllStaff();
        ArrayList<Volunteer> volunteers = Volunteer.retrieveAllVolunteers();
        for (Staff staff : staffMembers) {
            if (staff.getPhoneNumber().equals(phoneNumber) && staff.getPassword().equals(password)
                    && staff.getUserType() == Person.UserType.staff) {
                return "staff";
            }
        }
        for (Volunteer volunteer : volunteers) {
            if (volunteer.getPhoneNumber().equals(phoneNumber) && volunteer.getPassword().equals(password)
                    && volunteer.getUserType() == Person.UserType.volunteer) {
                return "volunteer";

            }
        }
        return "failed";
    }
}
