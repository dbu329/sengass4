
/*
 * the output is a list of queries and the associated answers, where each answer is a list of entries of the form
( ( FlightPlan ); Number; Number; Number )
representing a flight plan (a sequence of flights), together with three numbers representing, respectively: 
	1. the total cost
	2. the total travel time 
	3. the total frequent flier hours earned on the airline specifed in the query.
 */

public class Answer {
	
	FlightPlan flightPlan;
	int totalCost;
	int travelTime;
	int totalFreqFlierPoints;
	
	Answer(FlightPlan flightPlan) {
		this.flightPlan = flightPlan;
		this.totalCost = flightPlan.getTotalCost();
		this.travelTime = flightPlan.getTotalTime();
		this.totalFreqFlierPoints = flightPlan.getAirlineTime();
	}
	
	public String toString() {
		return "" + flightPlan + ", " +  totalCost + ", " + travelTime + ", " + totalFreqFlierPoints;
	}
	

}
