import java.util.function.Supplier;

class Customer { 
    private final int custID; 
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;

    Customer(int custID, double arrivalTime, Supplier<Double> serviceTime) { 
        // new Customer(1, 0.500, 1.000)
        this.custID = custID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getCustID() {
        return this.custID;
    }
   
    public double getArrivalTime() {
        return this.arrivalTime;
    } 

    public Supplier<Double> getServiceTime() {
        return this.serviceTime;
    }

    public String toString() {
        return getCustID() + "";
    }

}
