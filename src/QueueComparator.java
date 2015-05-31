import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class QueueComparator implements Comparator<Flight> {

	private List<Comparator<Flight>> myComparators;
	
	public QueueComparator(ArrayList<String> prefList) {
		myComparators = new ArrayList<Comparator<Flight>>();
		
		for (String pref: prefList) {
			if (pref.equals("Cost")) { 
				myComparators.add(new Pref_Cost());
			} else if (pref.equals("Time")) { 
				myComparators.add(new Pref_TravelTime());
			} else { 
				//must be an airline name
				myComparators.add(new Pref_Airline());
			}
		}
	}
	
	@Override
	public int compare(Flight o1, Flight o2) {
		int comparatorAnswer = 0; 

		for (Comparator<Flight> c:myComparators) {
			comparatorAnswer = c.compare(o1, o2);
			if (comparatorAnswer != 0) {
				break;
			}
			
		}
		return comparatorAnswer;
	}

	
	
}
