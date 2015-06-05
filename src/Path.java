import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path {
	
	private List<Flight> flights; //initialises empty list of Flights 
	
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
	 * Constructs a new Path. The new Path adds each of the new Flights
	 * in to itself, which also calculates cost, duration and airlineTimes
	 * automatically, since it makes use of the addFlight(Flight) function.
	 * @param flights
	 */
	public Path(List<Flight> newFlights) {
		cost = 0;
		duration = 0;
		flights = new ArrayList<Flight>();
		airlineTime = new HashMap<String, Integer>();
		for (Flight fl: newFlights) {
			addFlight(fl);
		}
	}
	
	/**
	 * Adds a new flight to the FlightPlan path.
	 * @param f 
	 */
	public void addFlight(Flight flight) {
		//calculate duration BEFORE adding the new flight, as calculation of duration makes use
		// of knowing the getLastFlight() function
		duration += flight.getDuration();
		duration += getLayoverTime(this.getLastFlight(), flight);
		//Now add the new flight on
		flights.add(flight);
		String airline = flight.getAirline();
		if (airlineTime.get(airline) == null) {
			airlineTime.put(airline, 0);
		}
		// Increment the total flight time for the airline of the given flight 
		airlineTime.put(airline, airlineTime.get(airline) + flight.getDuration());
		cost += flight.getCost();
	}

	/**
	 * Gets the total cost of this Path
	 * @return
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Gets the total time taken for this path
	 * @return
	 */
	public int getTotalTime() {
		return duration;
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
		StringBuilder myStr = new StringBuilder();
		int size = 0;
		for (Flight f : flights) {
			size++;
			myStr.append(f);
			if (size != flights.size())
				myStr.append("\n       ");
		}
		//return flights.toString();
		return myStr.toString();
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
		Calendar arriveDateA = a.getDate();
		Calendar arriveDateB = b.getDate();
		//sets the Calendar Milliseconds to zero so that there won't be any rounding errors
		arriveDateA.set(Calendar.MILLISECOND, 0);
		arriveDateB.set(Calendar.MILLISECOND, 0);
		
		Date dateA = arriveDateA.getTime();
		Date dateB = arriveDateB.getTime();

		//get the difference between the end of flight a, and the start of flight b
		long diff = dateB.getTime() - (dateA.getTime() + a.getDuration()*60*1000);
		// difference in minutes.don't ask me how i got this. edit* ask me how i got this
		long diffMinutes = diff/ (60*1000); 
//		System.out.println("difference in minutes:"+diffMinutes);
		return (int) diffMinutes;
	}

	/**
	 * Returns the last Flight on this Path
	 * @return
	 */
	public Flight getLastFlight() {
		Flight last = null;
		int lastIndex = flights.size() - 1;
		if (lastIndex >= 0) {
			last = flights.get(lastIndex);
		}
		return last;
	}

	/**
	 * Gets a list of the flights of this path.
	 * @return
	 */
	public List<Flight> getFlights() {
		return flights;
	}

}
