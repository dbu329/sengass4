import java.util.Comparator;

public class Pref_Cost implements Comparator<FlightPlan> {

	@Override
	public int compare(FlightPlan f1, FlightPlan f2) {
		return f1.getTotalCost() - f2.getTotalCost();
	}

}
