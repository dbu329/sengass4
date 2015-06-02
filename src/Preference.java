import java.util.Comparator;

public interface Preference extends Comparator<Path> {

	@Override
	public int compare(Path p1, Path p2);

}
