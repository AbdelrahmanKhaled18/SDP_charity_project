package model.DesignPatterns.state;

import model.Campaign;

public interface CampaignState {
    void NextState(Campaign campaign);
}
