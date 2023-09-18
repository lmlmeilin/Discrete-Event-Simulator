class Done extends Event { 
    private final Server server;

    Done(Customer customer, double time, Server server) {
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
        if (!this.server.getQueue().getQueueList().isEmpty()) {
            return new Pair<Server, Event> (this.server, new Serve(this.server.getQueue().getQueueList().get(0),
                 this.server.getServerFreeTime(), this.server)); 
        } 
        return new Pair<Server, Event> (server, new Event(getCust(), getTime())); 
    }


    @Override  
    public double addWaitTime() {
        if (!this.server.getQueue().getQueueList().isEmpty()) {
            Stats stats = new Stats();
            return stats.addWaitTime(this.server.getServerFreeTime()); 
        } 
        else {
            return 0; 
        }
    }


    @Override
    public Event updateEvent() {
        double restTime = this.server.getRestTime().get();
        return new Done(getCust(), getTime() + restTime, this.server.setFreeTime(getTime() + restTime));
    }

    @Override
    public boolean haveNext() { 
        return true;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d done serving by %d\n",
            getTime(), getCust().getCustID(), this.server.getServerId());
    }
}
