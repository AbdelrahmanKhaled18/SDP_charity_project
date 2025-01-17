package model.DesignPatterns.state;

import model.Campaign;

import java.util.Date;

public class PlannedState implements CampaignState {
    @Override
    public void NextState(Campaign campaign) {
        if (campaign.getStartDate().before(new Date()))
            campaign.setCampaignState(new ActivateState());
    }
}
