package model.DesignPatterns;

public class DiscountsDecorator extends PaymentDecorator{
    private double DiscountAmount;
    @Override
    public double pay() {
        return paymentRef.pay() + DiscountAmount;
    }
}
