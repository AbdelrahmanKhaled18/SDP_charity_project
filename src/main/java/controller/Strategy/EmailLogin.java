package controller.Strategy;

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
    public String login() {

        ArrayList<Staff> staffMembers = Staff.retrieveAllStaff();
        ArrayList<Volunteer> volunteers = Volunteer.retrieveAllVolunteers();
        for (Staff staff : staffMembers) {
            if (staff.getEmail().equals(email) && staff.getPassword().equals(password)
                    && staff.getUserType() == Person.UserType.staff) {
                return "staff";
            }
        }
        for (Volunteer volunteer : volunteers) {
            if (volunteer.getEmail().equals(email) && volunteer.getPassword().equals(password)
                    && volunteer.getUserType() == Person.UserType.volunteer) {

                return "volunteer";

            }
        }
        return "failed";
    }
}
