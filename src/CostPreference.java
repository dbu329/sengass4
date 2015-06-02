import java.util.Comparator;

public class CostPreference implements Comparator<Path> {

	public int compare(Path p1, Path p2) {
		return p1.getCost() - p2.getCost();
	}

}
