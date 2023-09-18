import java.util.Comparator; 

class EventComp implements Comparator<Event> {
    public int compare(Event a, Event b) {
        return a.compareTo(b);
    }
}
