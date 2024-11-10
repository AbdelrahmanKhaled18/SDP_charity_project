public interface ISubject {
    public void registerObservers(IObserver x);
    public void removeObservers(IObserver x);
    public void notifyObservers();
}
