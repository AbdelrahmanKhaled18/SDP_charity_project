package model.DesignPatterns.observer;

import java.util.ArrayList;

public class CampaignTargetObserver implements IObserver, ISubject {
    private ISubject ISubjectRef;
    private double currentCollectedAmount;
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();

    public CampaignTargetObserver(ISubject subject) {
        this.ISubjectRef = subject;
        ISubjectRef.registerObservers(this);
    }

    @Override
    public void update(double collectedAmount) {
        if (collectedAmount != currentCollectedAmount) {
            currentCollectedAmount = collectedAmount;
            notifyObservers();
        }
    }

    @Override
    public void registerObservers(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObservers(IObserver observer) {
        int index = observers.indexOf(observer);
        if (index >= 0) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(currentCollectedAmount);
        }
    }
}
