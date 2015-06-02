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
	
	public void addFlight(Flight flight) {
		edges.add(flight);
	}

	public void printEdges() {
		int i = 1;
		for (Flight flight : edges) {
			System.out.println("Flight #"+i + " " + flight.getAirline());
			System.out.println("\tCost: $" + flight.getCost());
			System.out.println("\tDeparting: " + flight.getOrigin());
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			sdf.setTimeZone(flight.getDate().getTimeZone());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateFormat.setTimeZone(flight.getDate().getTimeZone());
			
    		System.out.print("\tDate: " + dateFormat.format(flight.getDate()));
    		System.out.println(" at "+ sdf.format(flight.getDate()));
    		System.out.println("\tArrives "+flight.getDestination() +" in "+flight.getDuration()+" minutes");

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
	public List<Flight> getNeighbours(Flight flight) {
		List<Flight> neighbours = new ArrayList<Flight>();
		for (Flight f : edges) {
			if (f.getOrigin().equals(flight.getDestination())) {
				// if the layover time is larger than 60 minutes, then add the flight
				// as a neighbouring flight of fStr. Check out getLayOverTime.
				if (flight.getOrigin() == null || getLayoverTime(flight, f) >= 60) {
					neighbours.add(f);
				}
			}
		}
		return neighbours;
	}
	
	@Override
	public String toString() {
		// Use the inbuilt List.toString which calls toString on each element
		return edges.toString().replace("[", "").replace("]", "");
	}
	
	public Set<String> getCities() {
		Set<String> cities = new HashSet<String>();
		for (Flight flight : edges) {
			cities.add(flight.getOrigin());
			cities.add(flight.getDestination());
		}
		return cities;
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
		Calendar arriveDateA = a.getDate(); // date arriving at destination in A
		Calendar arriveDateB = b.getDate();
		arriveDateA.set(Calendar.MILLISECOND, 0);
		arriveDateB.set(Calendar.MILLISECOND, 0);
		Date dateA = arriveDateA.getTime();
		Date dateB = arriveDateB.getTime();
		
		//get the difference between the end of flight a, and the start of flight b
		long diff = dateB.getTime() - (dateA.getTime() + a.getDuration()*60*1000);
		// difference in minutes.don't ask me how i got this. edit* ask me how i got this
		long diffMinutes = (diff + (60*1000) - 1)/ (60*1000);
		return (int) diffMinutes;
	}

}