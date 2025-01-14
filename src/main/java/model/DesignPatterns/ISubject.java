package model.DesignPatterns;

public interface ISubject {
     void registerObservers(IObserver x);
     void removeObservers(IObserver x);
     void notifyObservers();
}
