package model;

import java.util.Date;

public class MoneyDonation extends Donation{

    private double amount;

    public MoneyDonation(Date date, int personId, double amount) {
        super(date, personId);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    boolean validateDonation(Donation donation) {
        if (!(donation instanceof MoneyDonation)) {
            return false;
        }

        MoneyDonation moneyDonation = (MoneyDonation) donation;

        return moneyDonation.getAmount() > 1;
    }

    @Override
    boolean saveDonation(Donation donation) {
        //calling createDonation for moneyDonation
        return false;
    }
}
