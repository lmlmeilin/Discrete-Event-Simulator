class Arrive extends Event {
    Arrive(Customer customer, double time) {
        super(customer, time);
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
        if (!server.serverAvail(getCust())) {
            if (server.queueNotFull()) {
                server = server.addCustomer(getCust());
                return new Pair<Server, Event>(server, new Wait(getCust(), getTime(), server));
            } else {
                return new Pair<Server, Event>(server, 
                    new Leave(getCust(), getTime()));
            }
        }
        return new Pair<Server, Event>(server,  
            new Serve(getCust(), getTime(), server));
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
        return String.format("%.3f %d arrives\n", getTime(), getCust().getCustID());
    }
}