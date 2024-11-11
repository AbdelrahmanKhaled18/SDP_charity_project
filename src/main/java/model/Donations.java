package model;

import java.util.Date;

public class Donations {

    private String donorId ;
    private double amount;
    private Date date;
    private String method;

    private Person person;

    public Donations(double amount, Date date, String method, String donorId, Person person) {

        this.amount=amount;
        this.date = date;
        this.method = method;
        this.donorId=donorId;
        this.person=person;


    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void update (String message){

        System.out.println("Update donation: " + message);
    }


}
