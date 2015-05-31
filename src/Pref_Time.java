import java.util.Comparator;

public class Pref_Time implements Comparator<Flight> {

	@Override
	public int compare(Flight o1, Flight o2) {
		return o1.getTravelTime() - o2.getTravelTime();
	}
}