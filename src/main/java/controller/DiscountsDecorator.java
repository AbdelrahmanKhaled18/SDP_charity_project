package controller;

public class DiscountsDecorator extends PaymentDecorator{
    private double DiscountAmount;
    @Override
    public double pay() {
        return paymentRef.pay() + DiscountAmount;
    }
}
