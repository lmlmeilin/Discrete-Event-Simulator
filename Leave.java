class Leave extends Event {
    Leave(Customer customer, double time) {
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
    public boolean haveNext() { 
        return false;
    }

    @Override
    public Event updateEvent() {
        return this;
    }

    @Override
    public int numLeft() {
        Stats stats = new Stats();
        return stats.numLeft();
    }

    @Override
    public String toString() {
        return String.format("%.3f %d leaves\n", getTime(), getCust().getCustID());
    }
}

