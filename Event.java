class Event implements Comparable<Event> {
    protected final Customer customer;
    protected final double time;

    Event(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }

    public Customer getCust() {
        return this.customer;
    }

    public double getTime() {
        return this.time; 
    }

    public Pair<Server, Event> nextEvent(Server server) {
        return new Pair<Server, Event>(server, this);
    }

    public Event updateEvent() {
        return this;
    }

    public boolean haveNext() {
        return false;
    }

    public int numServed() {
        return 0; 
    } 

    public double addWaitTime() {
        return 0;
    }

    public double minusWaitTime() {
        return 0;
    }

    public int numLeft() {
        return 0;
    }

    @Override
    public int compareTo(Event event) { //compare customer events
        double diffCustID = this.customer.getCustID() - event.customer.getCustID(); 
        double diffTime = this.getTime() - event.getTime();  
        if (diffTime < 0) {
            return -1;
        } else if (diffTime > 0) {
            return 1;
        } else if (diffTime == 0) {
            if (diffCustID < 0) {
                return -1;
            } else {
                return 1;
            } 
        }
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

}