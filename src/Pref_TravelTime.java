import java.util.Comparator;

public class Pref_TravelTime implements Comparator<Flight> {

	@Override
	public int compare(Flight f1, Flight f2) {
		return f1.getTravelTime() - f2.getTravelTime();
	}
	
}