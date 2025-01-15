package model.DesignPatterns.command;

import model.Donation;
import model.Person;

public class MakeDonationCommand implements ICommand {
    private Donation donation;


    public MakeDonationCommand(Donation donation) {
        this.donation = donation;
    }

    @Override
    public void execute() {

    }

    @Override
    public void unExecute() {

    }
}
