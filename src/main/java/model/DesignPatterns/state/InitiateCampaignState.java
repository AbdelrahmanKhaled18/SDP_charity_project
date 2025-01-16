package model.DesignPatterns.state;

import model.Campaign;
import model.DesignPatterns.proxy.CampaignStateManagementProxy;

public class InitiateCampaignState implements CampaignState {

    CampaignStateManagementProxy proxyInstance;

    @Override
    public void NextState(Campaign campaign) {
        boolean isAuthorized = proxyInstance.manageCampaigns(campaign.getCreator());

        if (isAuthorized) {
            campaign.setCampaignState(new PendingAcceptanceState());
        } else {
            campaign.setCampaignState(new PlannedState());
        }
    }
}