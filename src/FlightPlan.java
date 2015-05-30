import java.util.ArrayList;


public class FlightPlan {

	private ArrayList<Flight> flightPlan;
	
	public FlightPlan(ArrayList<Flight> flights) {
		this.flightPlan = flights;
	}
	
	public String toString() {
		return this.flightPlan.toString();
	}
	
}
