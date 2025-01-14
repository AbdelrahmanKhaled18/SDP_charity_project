package model;

import model.DesignPatterns.IObserver;
import model.DesignPatterns.ISubject;

import java.util.ArrayList;
import java.util.Date;


public class RealCampaign implements Campaign, ISubject {
    public enum CampaignStatus {
        INITIATED,
        STARTED,
        FINISHED
    }

    private String campaignId;
    private String title;
    private String description;
    private double goalAmount;
    private double collectedAmount;
    private Date startDate;
    private Date endDate;

    private CampaignStatus status;
    private ArrayList<Volunteer> assignedVolunteers = new ArrayList<>();
    private ArrayList<IObserver> observers = new ArrayList<>();
    private Staff creator;
//    private List<Beneficiary> beneficiaries = new ArrayList<>();

    public RealCampaign(String campaignId, Staff creator) {
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

    public void updateCampaign(RealCampaign details) {
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

    public void registerObservers(IObserver x) {
        observers.add(x);
    }

    public void removeObservers(IObserver x) {
        int index = observers.indexOf(x);
        if (index >= 0) {
            observers.remove(x);
        }
    }

    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }
}
