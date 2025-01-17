package model.DesignPatterns.state;

import model.Campaign;
import model.DesignPatterns.proxy.CampaignStateManagementProxy;
import model.DesignPatterns.strategy.UserLoginContext;
import model.Staff;

public class PendingAcceptanceState implements CampaignState {
    @Override
    public void NextState(Campaign campaign) {
        CampaignStateManagementProxy proxyInstance;
        proxyInstance = new CampaignStateManagementProxy(campaign);
        boolean isAuthorized = proxyInstance.manageCampaigns((Staff) UserLoginContext.getInstance().getLoggedInUser());
        if (isAuthorized) {
            campaign.setCampaignState(new PlannedState());
        }
    }
}
