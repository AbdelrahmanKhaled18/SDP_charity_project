package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Volunteer extends Person{

    private ArrayList<Skill> skills;


    public Volunteer(String name, String phoneNumber, String email, String nationalId, String gender, Date dateOfBirth) {
        super(name, phoneNumber, email, nationalId, gender, dateOfBirth);

        this.skills = new ArrayList<>();
    }


    public void addskill(Skill skill){
        skills.add(skill);
        System.out.println("Added skill: " + skill);

    }

    public void addRole(){


    }
}
