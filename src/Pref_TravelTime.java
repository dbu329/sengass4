import java.util.Comparator;

public class Pref_TravelTime implements Comparator<FlightPlan> {

	@Override
	public int compare(FlightPlan f1, FlightPlan f2) {
		return f1.getTravelTime() - f2.getTravelTime();
	}
	
}