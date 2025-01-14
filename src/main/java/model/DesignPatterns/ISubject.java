package model.DesignPatterns;

public interface ISubject {
     void registerObservers(IObserver observer);
     void removeObservers(IObserver observer);
     void notifyObservers();
}
