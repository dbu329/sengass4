import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	/**
	 * Passed in a flight. Finds the neighbours of the destination of the flight. Convenient as
	 * it also contains all the necessary date values, and is ready to be used with the function
	 * getLayoverTime(Flight a, Flight b)
	 * @param lastFlight
	 * @return
	 */
	public ArrayList<Flight> getNeighbours(Flight myFlight) {
		ArrayList<Flight> toReturn = new ArrayList<Flight>();
		String fStr = myFlight.getDestination();
		for (Flight f : edges) {
			
			if (f.getOrigin().equals(fStr)) {
//				System.out.println("looking at "+f +"dest:"+fStr + "origin:"+myFlight.getOrigin());
//				System.out.println("\t the layover time of this flight is:"+getLayoverTime(myFlight, f));
				// if the layover time is larger than 60 minutes, then add the flight
				// as a neighbouring flight of fStr. Check out getLayOverTime.
				if (myFlight.getOrigin() == null || getLayoverTime(myFlight, f) >= 60) {
//					System.out.println("add");
					toReturn.add(f);
				}
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
	
	public Set<String> getAllLocations() {
		Set<String> list = new HashSet<String>();
		for (Flight f: edges) {
//			if (!list.contains(f.getOrigin()))
				list.add(f.getOrigin());
//			if (!list.contains(f.getDestination()))
				list.add(f.getDestination());
		}
		return list;
	}
	
	/**
	 * Gets the layover time between two flights. Returns exact minutes.
	 * It is possible to be a negative number. Can use this to our advantage, as long as
	 * returned minutes is >= 60, then the two flights are connected.
	 * @param a
	 * @param b
	 * @return an integer representing the layover time in minutes
	 */
	private int getLayoverTime(Flight a, Flight b) {
		if (a == null || b == null)
			return 0;
		Calendar arriveDateA = a.getTime(); // date arriving at destination in A
		Calendar arriveDateB = b.getTime();
//		System.out.print("A:");a.print();
//		System.out.print("B:");b.print();
//		System.out.println();
		arriveDateA.set(Calendar.MILLISECOND, 0);
		arriveDateB.set(Calendar.MILLISECOND, 0);
		Date dateA = arriveDateA.getTime();
		Date dateB = b.getTime().getTime();
		
		//get the difference between the end of flight a, and the start of flight b
		long diff = dateB.getTime() - (dateA.getTime() + a.getTravelTime()*60*1000);
		// difference in minutes.don't ask me how i got this. edit* ask me how i got this
		long diffMinutes = (diff + (60*1000) - 1)/ (60*1000);
//		System.out.println("difference in minutes:"+diffMinutes);
		return (int) diffMinutes;
	}

}
