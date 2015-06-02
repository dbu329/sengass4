import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path {
	
	private List<Flight> flights; 
	
	private int cost; // total price of flights
	private int duration; // duration of path in minutes
	private Map<String, Integer> airlineTime; // duration in air spent with each airline
	
	/**
	 * Constructs a new Path. The list of flights is empty
	 */
	public Path() {
		duration = 0;
		flights = new ArrayList<Flight>();
		airlineTime = new HashMap<String, Integer>();
	}
	
	/**
	 * Constructs a new Path. The flights is given a copy
	 * of the arrayList 'flights' being passed in.
	 * @param flights
	 */
	public Path(List<Flight> flights) {
		duration = 0;
		this.flights = new ArrayList<Flight>(flights); // clone the given flights
		airlineTime = new HashMap<String, Integer>();
	}
	
	/**
	 * Adds a new flight to the FlightPlan path.
	 * @param f 
	 */
	public void addFlight(Flight flight) {
		flights.add(flight);
		duration += flight.getDuration();
		String airline = flight.getAirline();
		if (airlineTime.get(airline) == null) {
			airlineTime.put(airline, 0);
		}
		// Increment the total flight time for the airline of the given flight 
		airlineTime.put(airline, airlineTime.get(airline) + flight.getDuration());
		cost += flight.getCost();
	}

	public int getCost() {
		return cost;
	}
	
	//TODO we need to figure out a way to get Travel Time which includes delay times
	// 		which is not necessarily an hour, could be more.
	public int getTotalTime() {
		int time = 0;
		Flight prevFlight = null;
		for(Flight f : flights){
			time += f.getDuration();
			time += getLayoverTime(prevFlight, f); //get layover time deals with null first time
			prevFlight = f;
		}
		return time;
	}

	/**
	 * Gets the total minutes spent on the airline
	 * @return
	 */
	public int getAirlineTime(String airline) {
		return airlineTime.containsKey(airline) ? airlineTime.get(airline) : 0;
	}

	/**
	 * Gets the current city the path is up to
	 * @return
	 */
	public String getCurrentCity() {
		int last = flights.size() - 1;
		return flights.get(last).getDestination();
	}

	@Override
	public String toString() {
		return flights.toString();
	}
	
	/**
	 * Gets the layover time between two flights. Returns exact minutes.
	 *  Can be under 60 minutes.
	 * @param a
	 * @param b
	 * @return an integer representing the layover time in minutes
	 */
	private int getLayoverTime(Flight a, Flight b) {
		if (a == null || b == null)
			return 0;
		
		Date dateA = a.getDate().getTime();
		Date dateB = b.getDate().getTime();
		
		//get the difference between the end of flight a, and the start of flight b
		long diff = dateB.getTime() - (dateA.getTime() + a.getDuration()*60*1000);
		// difference in minutes.don't ask me how i got this. edit* ask me how i got this
		long diffMinutes = diff/ (60*1000); 
//		System.out.println("difference in minutes:"+diffMinutes);
		return (int) diffMinutes;
	}

	public Flight getLastFlight() {
		Flight last = null;
		int lastIndex = flights.size() - 1;
		if (lastIndex >= 0) {
			last = flights.get(lastIndex);
		}
		return last;
	}

	public List<Flight> getFlights() {
		return flights;
	}
}
