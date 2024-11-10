import java.util.ArrayList;

public class NotificationService implements ISubject{

    public ArrayList<IObserver> listOfObservers;

    public NotificationService()
    {
        listOfObservers=new ArrayList<IObserver>();
    }

    @Override
    public void registerObservers(IObserver x) {
        listOfObservers.add(x);
    }

    @Override
    public void removeObservers(IObserver x) {
        int index = listOfObservers.indexOf(x);
        if(index >=0){
            listOfObservers.remove(x);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < listOfObservers.size(); i++) {
            IObserver observer =  listOfObservers.get(i);
            observer.update();
        }
    }


}
