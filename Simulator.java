import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int qmax;
    private final ImList<Pair<Double,Supplier<Double>>> inputTimes;
    private final Supplier<Double> restTimes; 

    Simulator(int numOfServers, int qmax, ImList<Pair<Double,Supplier<Double>>> inputTimes, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.qmax = qmax;
        this.inputTimes = inputTimes;
        this.restTimes = restTimes;
    }

    ImList<Server> listServers() { // servers with no queue
        ImList<Server> servers = new ImList<>();
        Supplier<Double> restTimes = this.restTimes;
        for (int i = 1; i <= this.numOfServers; i++) {
            servers = servers.add(new Server(i, 0, new Queue(this.qmax, new ImList<>()), restTimes));
        }
        return servers;
    }

    public ImList<Event> arriveEvents() {
        ImList<Event> events = new ImList<>();
        int count = 1;        
        for (Pair<Double, Supplier<Double>> pair : this.inputTimes) {
            Pair<Double, Supplier<Double>> timings = pair;
            double arrivalTime = timings.first();
            Supplier<Double> serviceTime = timings.second();
            Customer cust = new Customer(count, arrivalTime, serviceTime);
            Event arrive = new Arrive(cust, arrivalTime);
            events = events.add(arrive);
            count++;
        }
        return events;
    }
    
    public PQ<Event> pqArriveEvents() {
        ImList<Event> events = this.arriveEvents();
        PQ<Event> pq = new PQ<Event>(new EventComp());
        for (Event event: events) {
            pq = pq.add(event);
        }
        return pq;
    }

    public Pair<Server, Event> processor(ImList<Server> servers, Event event) {
        Server s = new Server(0, 0, new Queue(0, new ImList<>()), this.restTimes);
        Pair<Server, Event> serverEvent = new Pair<Server, Event>(s, event);
        for (Server server: servers) { // arrive serve done
            if (server.serverAvail(event.getCust())) {
                event = event.updateEvent();
                return event.nextEvent(server); 
                
            }
        }

        for (Server server: servers) { // arrive wait continuewait serve done
            if (server.queueNotFull()) {
                event = event.updateEvent();
                return event.nextEvent(server);
                
            }
        }

        for (Server server: servers) { // arrive leave
            event = event.updateEvent();
            return event.nextEvent(server);
        }

        return serverEvent;
    }

    public String simulate() {
        int numServed = 0;
        double totalWaitTime = 0;
        double avgWaitingTime = 0; 
        int numLeft = 0;
        String stats = "";

        String string = "";
        PQ<Event> pq = new PQ<Event>(new EventComp());
        pq = this.pqArriveEvents();
        ImList<Server> servers = this.listServers();

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pr = pq.poll();
            Event eventpolled = pr.first();
            pq = pr.second();
            string += eventpolled.toString();

            Pair<Server, Event> pair = processor(servers, eventpolled);
            Server newServer = pair.first();
            Event newEvent = pair.second();

            if (eventpolled.haveNext()) {
                pq = pq.add(newEvent);
            }

            servers = servers.set(newServer.getServerId() - 1, newServer);
            
            numServed += eventpolled.numServed();
            totalWaitTime += eventpolled.addWaitTime();
            totalWaitTime -= eventpolled.minusWaitTime();
            avgWaitingTime = totalWaitTime / numServed; 
            numLeft += eventpolled.numLeft();
            
            stats = String.format("[%.3f %d %d]", avgWaitingTime, numServed, numLeft);
        }
        return string + stats;
    }
}





