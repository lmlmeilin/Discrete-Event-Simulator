class Stats {

    Stats() {

    }

    public double addWaitTime(double time) { // time served after wait
        double totalWaitTime = 0;
        totalWaitTime += time;
        return totalWaitTime;
    }

    public double minusWaitTime(double time) { // time waited
        double totalWaitTime = 0;
        totalWaitTime += time;
        return totalWaitTime; 
        
    }

    public int numServed() {
        int numServed = 0;
        numServed += 1;
        return numServed;
    }

    public int numLeft() {
        int numLeft = 0; 
        numLeft += 1;
        return numLeft;
    }
}

