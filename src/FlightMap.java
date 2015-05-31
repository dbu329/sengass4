import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlightMap {
	
	List<Flight> edges = new ArrayList<Flight>();
	
	public void addFlight(Flight myFlight) {
		edges.add(myFlight);
	}
	
	// test function
	public void printEdges() {
		int i = 1;
		for (Flight f:edges) {
			System.out.println("Flight #"+i + " " + f.getAirline());
			System.out.println("\tCost: $"+f.getCost());
			System.out.println("\tDeparting: "+f.getOrigin());
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			sdf.setTimeZone(f.getTime().getTimeZone());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateFormat.setTimeZone(f.getTime().getTimeZone());
			
    		System.out.print("\tDate: " + dateFormat.format(f.getTime().getTime()));
    		System.out.println(" at "+ sdf.format(f.getTime().getTime()));
    		System.out.println("\tArrives "+f.getDestination() +" in "+f.getTravelTime()+" minutes");

			i++;
    	}
	}
	
	public ArrayList<Flight> getNeighbours(String fStr) {
		ArrayList<Flight> toReturn = new ArrayList<Flight>();
		
		for (Flight f : edges) {
			if (f.getOrigin().equals(fStr)) {
				toReturn.add(f);
			}
		}
		
		return toReturn;
	}
	
	public String toString() {
		String toReturn = "";
		Iterator<Flight> fItr = edges.iterator();
		while (fItr.hasNext()) {
			Flight f = fItr.next();
			if (fItr.hasNext()) {
				toReturn +=  f.toString() + ", ";
			} else {
				toReturn += f.toString() ;
			}
		}
		
		return toReturn;
	}
}
