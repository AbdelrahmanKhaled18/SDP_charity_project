package model.DesignPatterns.observer;

public interface ISubject {
     void registerObservers(IObserver observer);
     void removeObservers(IObserver observer);
     void notifyObservers();
}
