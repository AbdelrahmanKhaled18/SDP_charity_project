package model;

import java.util.Date;

public class MoneyDonation extends Donation{

    private String currencyType;
    private double amount;

    public MoneyDonation(Date date, int personId, String currencyType,double amount) {
        super(date, personId);
        this.currencyType = currencyType;
        this.amount = amount;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
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
