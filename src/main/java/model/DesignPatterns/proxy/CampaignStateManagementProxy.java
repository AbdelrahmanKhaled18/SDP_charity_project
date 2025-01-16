package model.DesignPatterns.proxy;

import model.Campaign;
import model.Staff;

import java.util.List;

public class CampaignStateManagementProxy implements IProxy {

    private Campaign campaign;
    private List<String> allowedPositions = List.of("Manager", "Admin");


    @Override
    public boolean manageCampaigns(Staff staff) {
        if (allowedPositions.contains(campaign.getCreator())) {
            return true;
            //System.out.println("Pending Confirmation From Higher Authority Staff Member: " + staff.getPosition() + " is pending confirmation" + campaign.getTitle());
        } else {
            return false;
        }
    }

}