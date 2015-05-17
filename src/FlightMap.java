
import java.text.SimpleDateFormat;
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			sdf.setTimeZone(f.getTime().getTimeZone());
			//System.out.print(sdf.format(f.getTime().getTime()));
			
    		System.out.println("\tDate:"+f.getDay()+"/"+(f.getMonth()+1)+"/"+f.getYear()+" at "
    				+ sdf.format(f.getTime().getTime()));
    		System.out.println("\tArriving:"+f.getDestination() +" in "+f.getTravelTime()+" minutes");
			i++;
    	}
	}

	
	
}
