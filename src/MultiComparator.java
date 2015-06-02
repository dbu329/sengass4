import java.util.Comparator;
import java.util.List;


public class MultiComparator<T> implements Comparator<T> {

	private final List<Comparator<T>> comparators;
	
	public MultiComparator(List<Comparator<T>> comparators) {
		this.comparators = comparators;
	}

	public int compare(T o1, T o2) {
		int result = 0;
		for (Comparator<T> c : comparators) {
			result = c.compare(o1, o2);
			if (result != 0) { // if o1 is different to o2
				break;
			}
			// otherwise, move on to next comparator if they're equal
		}
		return result;
	}
}
