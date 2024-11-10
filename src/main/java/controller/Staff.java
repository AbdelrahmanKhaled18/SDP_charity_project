package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Staff extends Person{

    private String position ;
    private String department;
    private int workingHours;


    public Staff(String name, String phoneNumber, String email, String nationalId, String gender, Date dateOfBirth , String position,String department, int workingHours ) {
        super(name, phoneNumber, email, nationalId, gender, dateOfBirth);

        this.position=position;
        this.department=department;
        this.workingHours=workingHours;

    }

    public void assignTask(String task){

        super.getAssignedTasks().add(task);
        System.out.println("Task assigned: " + task);

    }

    public List<String> getAssignedTasks(){
        return super.getAssignedTasks();
    }


}
