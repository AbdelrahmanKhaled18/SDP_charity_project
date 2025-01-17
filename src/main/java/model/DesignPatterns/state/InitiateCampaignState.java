package model.DesignPatterns.state;

import model.Campaign;
import model.DesignPatterns.proxy.CampaignStateManagementProxy;

public class InitiateCampaignState implements CampaignState {

    @Override
    public void NextState(Campaign campaign) {
        CampaignStateManagementProxy proxyInstance;
        proxyInstance = new CampaignStateManagementProxy(campaign);
        boolean isAuthorized = proxyInstance.manageCampaigns(campaign.getCreator());
        if (isAuthorized) {
            campaign.setCampaignState(new PlannedState());
        } else {
            campaign.setCampaignState(new PendingAcceptanceState());
        }
    }
}