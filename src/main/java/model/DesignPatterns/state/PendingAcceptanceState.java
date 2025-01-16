package model.DesignPatterns.state;

import model.Campaign;

public class PendingAcceptanceState implements CampaignState {
    @Override
    public void NextState(Campaign campaign) {
        campaign.setCampaignState(new PlannedState());
    }
}
