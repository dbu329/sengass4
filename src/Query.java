import java.util.ArrayList;
import java.util.Calendar;


public class Query {
	private Calendar departureTime; // date as well, might need to change
	private String origin;
	private String destination;
	private String[] preferences;
	private int numToDisplay;
	private ArrayList<State> finalStates;
	
	public Query(Calendar time, String start, String end,
				 String[] order, int amount) {
		departureTime = time; // might have to change to clone
		origin = start;
		destination = end;
		preferences = new String[3];
		preferences = order; // again might have to clone
		numToDisplay = amount;
	}
	
	// should we split into get start year, month, date, time?
	public Calendar getDepartureTime() {
		return departureTime;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public String[] getPreferences() {
		return preferences;
	}
	
	public int getNumToDisplay() {
		return numToDisplay;
	}
	
	public ArrayList<State> getFinalStates() {
		return finalStates;
	}
	
	public void addFinalState(State state) {
		finalStates.add(state);
	}
}
