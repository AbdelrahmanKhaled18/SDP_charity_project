package model;

import java.util.ArrayList;
import java.util.Date;

public abstract class Person {

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String nationalId;
    private String gender;
    private Date dateOfBirth;
    private boolean isActive;
    private ArrayList<Donation> donationHistory;
    private ArrayList<Task> assignedTasks;
    private Address address;

    public Person(String name, String phoneNumber, String email, String nationalId, String gender, Date dateOfBirth) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalId = nationalId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isActive = false;
        donationHistory = new ArrayList<>();
        assignedTasks = new ArrayList<>();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {

        return isActive;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void updateContactInfo(String email, String phoneNumber) {
        setEmail(email);
        setPhoneNumber(phoneNumber);

    }


    public void activateAccount() {

        this.isActive = true;

    }

    public void deactivateAccount() {

        this.isActive = false;

    }

    public void makeDonations(double amount, String method) {
/*
        Donation donations = new Donation(amount, new Date(), method, this.nationalId, this);
        donationHistory.add(donations);
        System.out.println("Donation made successfully");
*/

    }

    public ArrayList<Donation> getDonationHistory() {
        return donationHistory;
    }

    public ArrayList<Task> getAssignedTasks() {

        return assignedTasks;
    }


    public static Person retrievePerson(int id) {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
