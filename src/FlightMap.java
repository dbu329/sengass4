
import java.util.HashSet;

public class FlightMap {
	
	HashSet<Flight> edges = new HashSet<Flight>();
	
	public void addFlight(Flight myFlight) {
		edges.add(myFlight);
	}
	
	// test function
	public void printEdges() {
		int i = 1;
		for (Flight f:edges) {
			System.out.println("Flight #"+i + " " + f.getAirline());
			System.out.println("\tCost: $"+f.getCost());
			System.out.println("\t Departing:"+f.getOrigin());
    		System.out.println("\tDate:"+f.getDay()+"/"+(f.getMonth()+1)+"/"+f.getYear()+" at "
    				+ f.getHour() + ":" + f.getMinute());
    		System.out.println("\tArriving:"+f.getDestination() +" in "+f.getTravelTime()+" minutes");
			i++;
    	}
	}

	
	
}
