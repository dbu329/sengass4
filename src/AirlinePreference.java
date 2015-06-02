import java.util.Comparator;

public class AirlinePreference implements Comparator<Path> {

	private String airline;

	public AirlinePreference(String airline) {
		this.airline = airline;
	}

	public int compare(Path p1, Path p2) {
		return p2.getAirlineTime(airline) - p1.getAirlineTime(airline);
	}

}