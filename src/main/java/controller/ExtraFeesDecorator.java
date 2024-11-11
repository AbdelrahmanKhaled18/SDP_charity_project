package controller;

public class ExtraFeesDecorator extends PaymentDecorator{
    private double serviceFee;
    public ExtraFeesDecorator(IPayment payment) {
        paymentRef = payment;
    }
    @Override
    public double pay() {
        return paymentRef.pay() + serviceFee;
    }
}
