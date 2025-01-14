package model;

import java.util.Date;

public class InKindDonation extends Donation {

    private String Type;
    private int quantity;

    public InKindDonation(double amount, Date date, int personId, String Type, int quantity) {
        super(0.0, date, personId);
        this.Type = Type;
        this.quantity = quantity;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    boolean validateDonation(Donation donation) {
        if (!(donation instanceof InKindDonation)) {
            return false;
        }

        InKindDonation inKindDonation = (InKindDonation) donation;

        return inKindDonation.getQuantity() > 0 && ("food".equalsIgnoreCase(inKindDonation.getType()) || "clothes".equalsIgnoreCase(inKindDonation.getType()) || "medical supplies".equalsIgnoreCase(inKindDonation.getType()));
    }

    @Override
    boolean saveDonation(Donation donation) {
        ////calling createDonation for inKindDonation
        return false;
    }
}
