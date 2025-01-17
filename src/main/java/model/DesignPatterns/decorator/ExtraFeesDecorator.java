package model.DesignPatterns.decorator;

public class ExtraFeesDecorator extends PaymentDecorator{
    private double serviceFee;
    public ExtraFeesDecorator(IPayment payment) {
        paymentRef = payment;
    }
    @Override
    public double pay() {
        return paymentRef.pay() + serviceFee;
    }

    // Setter for serviceFee
    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }


}
