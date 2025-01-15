package model.DesignPatterns.decorator;

public abstract class PaymentDecorator implements IPayment {
    IPayment paymentRef;
    public abstract double pay();
}
