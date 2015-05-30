import java.util.ArrayList;


public class FlightPlan {

	private ArrayList<Flight> flightPlan;
	
	public FlightPlan(ArrayList<Flight> flights) {
		this.flightPlan = flights;
	}
	
	public String toString() {
		return this.flightPlan.toString();
	}

	public int getTotalCost() {
		int cost = 0;
		for(Flight f : flightPlan){
			cost += f.getCost();
		}
		return cost;
	}

	public int getTravelTime() {
		int time = 0;
		for(Flight f : flightPlan){
			time += f.getTravelTime();
		}
		return time;
	}

	public int getTotalFreqFlierPoints() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
