public class NotifyBySMSObserver implements IObserver{
    private ISubject ISubjectRef;
    private String phoneNumber;

    public NotifyBySMSObserver(ISubject x, String phoneNumber) {
        this.ISubjectRef = x;
        this.phoneNumber = phoneNumber;
        ISubjectRef.registerObservers(this);
    }

    @Override
    public void update() {
        System.out.println("This is an SMS message sent to phone number: " + phoneNumber);
    }
}
