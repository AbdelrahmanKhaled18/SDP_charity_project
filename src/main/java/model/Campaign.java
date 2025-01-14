package model;

import java.util.ArrayList;
import java.util.Date;

public interface Campaign {

    void createCampaign(String title, double goalAmount, Date startDate, Date endDate);
    void updateCampaign(RealCampaign details);
    void deleteCampaign(String campaignId);
    void addDonation(double amount);
    void assignVolunteer(Volunteer volunteer);
    ArrayList<Volunteer> getAssignedVolunteers();
    // void assignBeneficiary(Beneficiary beneficiary);
    // List<Beneficiary> getBeneficiaries();
}
