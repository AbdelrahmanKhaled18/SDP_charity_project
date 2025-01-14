package model.DesignPatterns;

public class BasicPayment implements IPayment {
    public double paymentAmount;

    @Override
    public double pay() {
        return paymentAmount;
    }
}
