package model.DesignPatterns.proxy;

import model.Person;
import model.Staff;

public interface IProxy {
    boolean manageCampaigns();

    boolean manageCampaigns(Person person);
}