public class NotifyByEmailObserver implements IObserver{
    private ISubject ISubjectRef;
    private String email;

    public NotifyByEmailObserver(ISubject x, String email) {
        this.ISubjectRef = x;
        this.email = email;
        ISubjectRef.registerObservers(this);
    }

    @Override
    public void update() {
        System.out.println("This is an email sent to: " + email);
    }
}
