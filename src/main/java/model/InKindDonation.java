package model;

import java.util.Date;

public class InKindDonation extends Donation {

    private String type;
    private int quantity;

    public InKindDonation(Date date, int personId, String Type, int quantity) {
        super(date, personId);
        this.type = Type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
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
