class Serve extends Event { //start serving when previous customer done
    private final Server server;

    Serve(Customer customer, double time, Server server) {
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
        Server s = this.server.serveWaitingCustomer(getCust());
        double serviceTime = getCust().getServiceTime().get();
        s = s.setFreeTime(serviceTime + getTime());
        return new Pair<Server, Event> (s, new Done(getCust(), serviceTime + getTime(), s));
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
    public int numServed() {
        Stats stats = new Stats();
        return stats.numServed(); 
    } 

    
    @Override
    public String toString() {
        return String.format("%.3f %d serves by %d\n", getTime(), 
            getCust().getCustID(), this.server.getServerId());
    }

}