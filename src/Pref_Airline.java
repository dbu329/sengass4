import java.util.Comparator;

public class Pref_Airline implements Comparator<FlightPlan> {
	
	@Override
	public int compare(FlightPlan f1, FlightPlan f2) {
		return f1.getTotalFreqFlierPoints() - f2.getTotalFreqFlierPoints();
	}
	
}
