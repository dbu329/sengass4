import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Query {
	private Calendar departureTime; // date as well, might need to change
	private String origin;
	private String destination;
	private Preferences preferences;
	private int numToDisplay;
	
	public Query(Calendar time, String start, String end,
				 ArrayList<String> order, int amount) {
		departureTime = time; // might have to change to clone
		origin = start;
		destination = end;
		preferences = new Preferences(order);
		numToDisplay = amount;
	}
	
	// should we split into get start year, month, date, time? yes I've Split
	public Calendar getDepartureTime() {
		return departureTime;
	}

	public int getDay() {
		return departureTime.get(Calendar.DATE);
	}
	
	public int getMonth() {
		return departureTime.get(Calendar.MONTH);
	}
	
	public int getYear() {
		return departureTime.get(Calendar.YEAR);
	}
	
	public int getHour() {
		return departureTime.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getMinute() {
		return departureTime.get(Calendar.MINUTE);
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public Preferences getPreferences() {
		return preferences;
	}
	
	public int getNumToDisplay() {
		return numToDisplay;
	}
	
	
	public void print() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setTimeZone(departureTime.getTimeZone());
		System.out.print("["+dateFormat.format(departureTime.getTime()) +",");
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		timeFormat.setTimeZone(departureTime.getTimeZone());
		System.out.print(timeFormat.format(departureTime.getTime()) +",");
		//System.out.print(getHour() + ":" + getMinute() + ",");
		
		System.out.print(getOrigin() + "," + getDestination()+",("	);
		
		for (int i = 0; i < this.getPreferences().length(); i++) {
			String s = preferences.get(i);
			System.out.print(s);
			if (i != preferences.length()-1) {
				System.out.print(",");
			}
		}
		System.out.println("),"+this.getNumToDisplay()+"]");
//		System.out.print(getTravelTime() + ","+getAirline()+"," + getCost()+"]");
	}
}
