import java.util.Comparator;

public class TravelTimePreference implements Comparator<Path> {

	public int compare(Path p1, Path p2) {
		return p1.getTotalTime() - p2.getTotalTime();
	}

}