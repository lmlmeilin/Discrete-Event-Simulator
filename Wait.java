class Wait extends Event {
    private final Server server;

    Wait(Customer customer, double time, Server server) {
        super(customer,time);
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
        return new Pair<Server, Event> (server, new ContinueWait(getCust(), server.getServerFreeTime() , server));
    }


    @Override
    public double minusWaitTime() {
        Stats stats = new Stats();
        return stats.minusWaitTime(getTime());
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
        return String.format("%.3f %d waits at %d\n", getTime(), 
            getCust().getCustID(), this.server.getServerId()); 
    }
}