package model.DesignPatterns.command;

import model.DatabaseConnection;
import model.DesignPatterns.template.Donation;

public class MakeDonationCommand implements ICommand {
    private Donation donation;


    public MakeDonationCommand(Donation donation) {
        this.donation = donation;
    }

    @Override
    public void execute() {
        donation.performDonation(donation);
    }


    @Override
    public void unExecute() {
        donation.undoDonation(donation);
    }

    public Donation getDonation() {
        return donation;
    }

}