package model.DesignPatterns.state;

import model.Campaign;

import java.util.Date;

public class ActivateState implements CampaignState {
    @Override
    public void NextState(Campaign campaign) {
        if (campaign.getCollectedAmount() >= campaign.getGoalAmount() ||
                campaign.getEndDate().after(new Date())) {
            campaign.setCampaignState(new CompletedState());
        }
    }
}
