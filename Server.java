import java.util.function.Supplier;

class Server { 
    private final int serverId;
    private final double serverFreeTime;
    private final Queue queue;
    private final Supplier<Double> restTimes;
    
    Server(int serverId, double serverFreeTime, Queue queue, Supplier<Double> restTimes) {
        this.serverId = serverId;
        this.serverFreeTime = serverFreeTime;
        this.queue = queue;
        this.restTimes = restTimes;
    }

    public int getServerId() { 
        return this.serverId; 
    }

    public Queue getQueue() {
        return this.queue;
    }

    public double getServerFreeTime() {
        return this.serverFreeTime;
    }

    public Supplier<Double> getRestTime() {
        return this.restTimes;
    }

    public boolean serverAvail(Customer cust) { 
        return this.serverFreeTime <= cust.getArrivalTime();
    }

    public Server serveWaitingCustomer(Customer cust) {
        ImList<Customer> waitingCust = this.getQueue().getQueueList();
        for (Customer customer : waitingCust) {
            if (customer == cust) {
                int index = waitingCust.indexOf(cust);
                waitingCust = waitingCust.remove(index);
            }            
        }
        return new Server(this.serverId, this.serverFreeTime, 
            new Queue(this.getQueue().getQMax(), waitingCust), this.restTimes);
    }

    public Server addCustomer(Customer cust) { 
        ImList<Customer> customers = this.getQueue().getQueueList();
        if (customers.size() < this.getQueue().getQMax()) {
            customers = customers.add(cust);
            return new Server(this.serverId, this.serverFreeTime, 
                new Queue(this.getQueue().getQMax(), customers), this.restTimes);    
        } else {
            return this; // will not add anymore if Queue is full
        }
    }

    public Server setFreeTime(double time) {
        return new Server(this.serverId, time, this.queue, this.restTimes);

    }

    public boolean queueNotFull() {
        return this.getQueue().getCurrentQ()  <  this.getQueue().getQMax();
    }

    public String toString() {
        return String.format("%d %.3f %s", this.getServerId(), 
            this.getServerFreeTime(), this.getQueue().toString());
    }

}