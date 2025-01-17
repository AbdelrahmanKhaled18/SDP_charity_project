package model.DesignPatterns.proxy;

import model.Campaign;
import model.Staff;

import java.util.List;

public class CampaignStateManagementProxy implements IProxy {

    private Campaign campaign;
    private List<String> allowedPositions = List.of("Manager", "Admin");

    public CampaignStateManagementProxy(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public boolean manageCampaigns(Staff staff) {
        if (allowedPositions.contains(campaign.getCreator())) {
            return true;
        } else {
            return false;
        }
    }

}