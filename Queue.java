class Queue {
    private final int qmax;
    private final ImList<Customer> customerQ;

    Queue(int qmax, ImList<Customer> customerQ) {
        this.qmax = qmax;
        this.customerQ = customerQ;
    }

    public ImList<Customer> getQueueList() {
        return this.customerQ;
    }

    public int getQMax() {
        return this.qmax;
    }

    public int getCurrentQ() {
        return this.customerQ.size();
    }

    public String toString() {
        return this.getQueueList().toString();
    }
}
