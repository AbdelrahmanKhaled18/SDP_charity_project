package controller;

import model.Address;
import model.Donations;
import model.Staff;
import model.Volunteer;

import java.util.Date;

public class Test {

    public static void main (String [] args){

        Address address = new Address("123 Main St", 1, null);
        Staff staff = new Staff("John Doe", "123-456-7890", "john@example.com", "A123456", "Male", new Date(), "Manager", "Sales", 40);

        staff.makeDonations(50.9 , "credit card");

        staff.setAddress(address);
        System.out.println("Staff Address: " + staff.getAddress().getName());

        staff.activateAccount();
        System.out.println("Staff active status: " + staff.isActive());

        System.out.println("Donation History for Staff:");
        for(Donations donation : staff.getDonationHistory()){
            System.out.println(" Amount: " + donation.getAmount() + ", Payment Method: " + donation.getMethod());
        }


        Volunteer volunteer = new Volunteer("John Doe", "123-456-7890","john@example.com", "A123456", "Male", new Date());

        volunteer.isActive();
        System.out.println("Volunteer active status: " + volunteer.isActive());
    }
}
