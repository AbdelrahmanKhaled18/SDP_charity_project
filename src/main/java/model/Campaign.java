package model;

import model.DesignPatterns.IObserver;
import model.DesignPatterns.ISubject;

import java.util.ArrayList;
import java.util.Date;


public class Campaign implements  ISubject {
    public enum CampaignStatus {
        INITIATED,
        STARTED,
        FINISHED
    }

    private int campaignId;
    private String title;
    private String description;
    private double goalAmount;
    private double collectedAmount;
    private Date startDate;
    private Date endDate;

    private CampaignStatus status;
    private ArrayList<Volunteer> assignedVolunteers = new ArrayList<>();
    private ArrayList<IObserver> observers;
    private Staff creator;
//    private List<Beneficiary> beneficiaries = new ArrayList<>();

    public void registerObservers(IObserver observer) {
        observers.add(observer);
    }

    public void removeObservers(IObserver observer) {
        int index = observers.indexOf(observer);
        if (index >= 0) {
            observers.remove(observer);
        }
    }

    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(collectedAmount);
        }
    }

    public Campaign(Staff creator) {
        observers = new ArrayList<>();
        this.creator = creator;
        this.status = CampaignStatus.INITIATED;
    }

    public Campaign(int campaignId, Staff creator) {
        observers = new ArrayList<>();
        this.campaignId = campaignId;
        this.creator = creator;
        this.status = CampaignStatus.INITIATED;
    }
    public void createCampaign(String title, double goalAmount, Date startDate, Date endDate) {
        this.title = title;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = CampaignStatus.STARTED;
    }

    public void updateCampaign(Campaign details) {
        this.title = details.title;
        this.goalAmount = details.goalAmount;
        this.startDate = details.startDate;
        this.endDate = details.endDate;
        this.description = details.description;
    }

    public void deleteCampaign(String campaignId) {
        this.status = CampaignStatus.FINISHED;
    }

    public void addDonation(double amount) {
        this.collectedAmount += amount;
    }

    public void assignVolunteer(Volunteer volunteer) {
        assignedVolunteers.add(volunteer);
    }

    public ArrayList<Volunteer> getAssignedVolunteers() {
        return assignedVolunteers;
    }
}
