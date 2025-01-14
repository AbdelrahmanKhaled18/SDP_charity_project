package model;

import java.util.Date;

public class MoneyDonation extends Donation{

    private String currencyType;

    public MoneyDonation(double amount, Date date, int personId, String currencyType) {
        super(amount, date, personId);
        this.currencyType = currencyType;
    }

    @Override
    boolean validateDonation(Donation donation) {
        return donation.getAmount() > 1;
    }

    @Override
    boolean saveDonation(Donation donation) {
        //calling createDonation for moneyDonation
        return false;
    }
}
