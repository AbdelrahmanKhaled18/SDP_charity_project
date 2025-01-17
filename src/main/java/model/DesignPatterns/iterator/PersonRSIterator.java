package model.DesignPatterns.iterator;

import model.*;
import model.DesignPatterns.factory.PersonFactory;
import model.DesignPatterns.template.Donation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class PersonRSIterator implements Iterator<Person> {
    private final ResultSet resultSet;

    public PersonRSIterator(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public boolean hasNext() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Person next() {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String gender = resultSet.getString("gender");
            String phoneNumber = resultSet.getString("phone_number");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String nationalId = resultSet.getString("national_id");
            Date dateOfBirth = resultSet.getDate("date_of_birth");
            boolean isActive = resultSet.getBoolean("is_active");
            int addressId = resultSet.getInt("address_id");
            Address address = (addressId > 0) ? Address.retrieveAddress(addressId) : null;
            String userType = resultSet.getString("user_type");
            ArrayList<Donation> donations = Donation.retrievePersonDonations(id);
            ArrayList<Task> tasks = Task.retrievePersonTasks(id);

            PersonFactory pf = new PersonFactory(userType, name, gender, phoneNumber, email, password,
                    nationalId, dateOfBirth, isActive, address);
            pf.setDonationHistory(donations);
            pf.setAssignedTasks(tasks);

            // Determine if the person is a staff or volunteer
            if (userType.equals("staff")) {
                // Fetch staff-specific fields
                String position = resultSet.getString("staff_position");
                String department = resultSet.getString("staff_department");
                pf.setPosition(position);
                pf.setDepartment(department);

            } else if (userType.equals("volunteer")) {
                // Fetch volunteer-specific fields
                ArrayList<Skill> skills = Volunteer.retrieveVolunteerSkills(id);
                pf.setSkills(skills);
            }

            return pf.buildPerson();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
