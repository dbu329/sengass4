import java.util.Comparator;

public class Pref_Cost implements Comparator<Flight> {

	@Override
	public int compare(Flight f1, Flight f2) {
		return f1.getCost() - f2.getCost();
	}

}
