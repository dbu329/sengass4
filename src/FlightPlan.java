import java.util.ArrayList;

// WHAT YOU WANT TO RETURN TO THE CUSTOMER

public class FlightPlan {
	
	private ArrayList<Flight> listOfFlights;
	private int totalCost;
	private int totalTime;
	private int totalFreq;
	
	
	public FlightPlan(ArrayList<Flight> flights) {
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

	//TODO: Is there allowed to be DIFFERENT flight carriers in a single flightplan?
	public int getTotalFreqFlierPoints() {
		return -1;
	}
	
}
