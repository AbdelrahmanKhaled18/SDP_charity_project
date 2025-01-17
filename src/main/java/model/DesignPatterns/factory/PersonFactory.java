package model.DesignPatterns.factory;

import model.*;
import model.DesignPatterns.template.Donation;

import java.util.ArrayList;
import java.util.Date;

public class PersonFactory {
    // Fields that are always necessary
    private final String userType;
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final String email;
    private final String password;
    private final String nationalId;
    private final Date dateOfBirth;
    private final boolean isActive;
    private final Address address;

    // id is optional in both types in
    private int id;

    // These lists can be empty in both types
    private ArrayList<Donation> donationHistory;
    private ArrayList<Task> assignedTasks;

    // This list is unique to volunteer and can be empty
    private ArrayList<Skill> skills;

    // Fields are required for staff
    private String position;
    private String department;


    public PersonFactory(String userType, String name, String gender, String phoneNumber, String email, String password,
                         String nationalId, Date dateOfBirth, boolean isActive, Address address) {
        this.userType = userType;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.address = address;

        id = -1;
        donationHistory = new ArrayList<>();
        assignedTasks = new ArrayList<>();
        skills = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDonationHistory(ArrayList<Donation> donationHistory) {
        this.donationHistory = donationHistory;
    }

    public void setAssignedTasks(ArrayList<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Person buildPerson() {
        if (userType.equals("staff")) {
            if (id == -1) {
                return new Staff(name, gender, phoneNumber, email, password,
                        nationalId, dateOfBirth, isActive, address, donationHistory,
                        assignedTasks, position, department);
            }

            return new Staff(id, name, gender, phoneNumber, email, password,
                    nationalId, dateOfBirth, isActive, address, donationHistory,
                    assignedTasks, position, department);
        }

        if (userType.equals("volunteer")) {
            if (id == -1) {
                return new Volunteer(name, gender, phoneNumber, email, password,
                        nationalId, dateOfBirth, isActive, address, donationHistory,
                        assignedTasks, skills);
            }

            return new Volunteer(id, name, gender, phoneNumber, email, password,
                    nationalId, dateOfBirth, isActive, address, donationHistory,
                    assignedTasks, skills);
        }

        return null;
    }
}
