package model.DesignPatterns.observer;

public class CampaignTargetObserver implements IObserver {
    private ISubject ISubjectRef;

    public CampaignTargetObserver(ISubject subject) {
        this.ISubjectRef = subject;
        ISubjectRef.registerObservers(this);
    }

    @Override
    public void update(double currentCollectedAmount) {
        System.out.println(currentCollectedAmount);
    }
}
