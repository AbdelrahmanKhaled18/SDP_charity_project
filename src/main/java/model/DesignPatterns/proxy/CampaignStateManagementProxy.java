package model.DesignPatterns.proxy;

import model.Campaign;
import model.Person;
import model.Staff;

import java.util.List;

public class CampaignStateManagementProxy implements IProxy {

    private Campaign campaign;
    private List<String> allowedPositions = List.of("Manager", "Admin");

    public CampaignStateManagementProxy(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public boolean manageCampaigns() {
        if (allowedPositions.contains(campaign.getCreator().getPosition())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean manageCampaigns(Person person) {
        if (person instanceof Staff) {
            if (allowedPositions.contains(((Staff) person).getPosition())) {
                return true;
            }
        }
        return false;
    }


}