import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// WHAT YOU WANT TO RETURN TO THE CUSTOMER

public class FlightPlan {
	
	private ArrayList<Flight> listOfFlights; 
	
	private int totalCost;	//total price of the flightplan
	private int totalTime;	//total minutes spent on this flight plan overall. 
	                         // Assuming that it includes the 1 hour layovers
	private int totalAirline; // aka total minutes spent on airline of preference
	
	/**
	 * Constructs a new FlightPlan. The list of flights is empty
	 */
	public FlightPlan() {
		totalAirline = 0;
		listOfFlights = new ArrayList<Flight>();
	}
	
	/**
	 * Constructs a new FlightPlan. The listOfFlights is given a copy
	 * of the arrayList 'flights' being passed in.
	 * @param flights
	 */
	public FlightPlan(ArrayList<Flight> flights) {
		totalAirline = 0;
		listOfFlights = new ArrayList<Flight>(flights); //this passes flights by value, not by reference
	}
	
	/**
	 * Adds a new flight to the FlightPlan path.
	 * @param f 
	 */
	public void addFlight(Flight f) {
//		if (getLastFlight() != null && getLayoverTime(getLastFlight(), f) < 60) {
//			return;
//		}
		this.listOfFlights.add(f);
	}
	
	/**
	 * Increments the total time spent on an Airline by minutes 'm' KBestFirstSearch.java
	 * knows what is the preferred Airline
	 * @param m
	 */
	public void incrementAirlineTime(int m) {
		totalAirline += m;
	}
	
	/**
	 * Calculates and returns the totalCost of this FlightPlan
	 * @return
	 */
	public int getTotalCost() {
		int cost = 0;
		for(Flight f : listOfFlights){
			cost += f.getCost();
		}
		return cost;
	}
	
	//TODO we need to figure out a way to get Travel Time which includes delay times
	// 		which is not necessarily an hour, could be more.
	public int getTotalTime() {
		int time = 0;
		Flight prevFlight = null;
		for(Flight f : listOfFlights){
			time += f.getTravelTime();
			time += getLayoverTime(prevFlight, f); //get layover time deals with null first time
			prevFlight = f;
		}
		return time;
	}
	
	// Is there allowed to be DIFFERENT flight carriers in a single flightplan? Yes
	/**
	 * Gets the total minutes spent on the Airline.
	 * @return
	 */
	public int getAirlineTime() {
		return totalAirline;
	}

	/**
	 * Gets the Current City the FlightPlan is at
	 * @return
	 */
	public String getCurrentCity() {
		int last = listOfFlights.size() - 1;
		return listOfFlights.get(last).getDestination();
	}
	
	/**
	 * Puts this FlightPlan to a String
	 */
	public String toString() {
		return this.listOfFlights.toString();
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
		Calendar arriveDateA = a.getTime(); // date arriving at destination in A
		
		Date dateA = arriveDateA.getTime();
		Date dateB = b.getTime().getTime();
		
		//get the difference between the end of flight a, and the start of flight b
		long diff = dateB.getTime() - (dateA.getTime() + a.getTravelTime()*60*1000);
		// difference in minutes.don't ask me how i got this. edit* ask me how i got this
		long diffMinutes = diff/ (60*1000); 
//		System.out.println("difference in minutes:"+diffMinutes);
		return (int) diffMinutes;
	}
	
	//Gets the LAST flight in the list of flights taken by this flightPlan.
	public Flight getLastFlight() {
		int last = listOfFlights.size() - 1;
		if (listOfFlights.size() > 0) {
			return listOfFlights.get(last);
		}
		return null;
	}

}
