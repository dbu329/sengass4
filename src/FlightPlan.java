import java.util.ArrayList;

// WHAT YOU WANT TO RETURN TO THE CUSTOMER

public class FlightPlan {
	
	private ArrayList<Flight> listOfFlights;
	
	private int totalCost;	//total price of the flightplan
	private int totalTime;	//total minutes spent on this flight plan overall. 
	                         // Assuming that it includes the 1 hour layovers
	private int totalAirline; // aka total minutes spent on airline of preference
	
	public FlightPlan() {
		totalCost = 0;
		totalTime = 0;
		totalAirline = 0;
	}
	
	public FlightPlan(ArrayList<Flight> flights) {
		totalCost = 0;
		totalTime = 0;
		totalAirline = 0;
		this.listOfFlights = flights;
	}
	
	public String toString() {
		return this.listOfFlights.toString();
	}

	public int getTotalCost() {
		int cost = 0;
		for(Flight f : listOfFlights){
			cost += f.getCost();
		}
		return cost;
	}
	
	public int getTravelTime() {
		int time = 0;
		for(Flight f : listOfFlights){
			time += f.getTravelTime();
		}
		return time;
	}
	
	public void inctotalAirline(int a) {
		totalAirline += a;
	}

	//TODO: Is there allowed to be DIFFERENT flight carriers in a single flightplan?
	public int getAirlineTime() {
		return -1;
	}
	
	// adds a flight.
	public void addFlight(Flight f) {
		this.listOfFlights.add(f);
	}
	
}
