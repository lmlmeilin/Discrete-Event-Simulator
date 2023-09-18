class ContinueWait extends Event {
    private final Server server;

    ContinueWait(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override 
    public Customer getCust() {
        return super.customer;
    }

    @Override 
    public double getTime() {
        return super.time;
    }

    @Override
    public Pair<Server, Event> nextEvent(Server server) {
        server = this.server;
        if (server.getQueue().getQueueList().size() > 1) {
            Customer cust = server.getQueue().getQueueList().get(0); 
            return new Pair<Server, Event> (server, new Serve(cust, server.getServerFreeTime() , server));
        }
        else {
            return new Pair<Server, Event> (server, new Event(getCust(), getTime()));
        }
    }

    @Override
    public double addWaitTime() {
        if (this.server.getQueue().getQueueList().size() > 1) {
            Stats stats = new Stats();
            return stats.addWaitTime(this.server.getServerFreeTime());
        }
        else {
            return 0;
        }
    }

    @Override
    public Event updateEvent() {
        return this; 
    }

    @Override
    public boolean haveNext() {
        return true;
    }

    @Override
    public String toString() {
        return "";
    }

}

