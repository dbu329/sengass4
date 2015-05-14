import java.sql.Time;
import java.util.Calendar;
import java.util.Date;


public class Flight {
	private int id;
	private Calendar date;
	private String origin;
	private String destination;
	private int travelTime;
	private String airline;
	private int cost;
	
	public Flight() {
		date = Calendar.getInstance();
	}
	
	public void setDate(int day, int month, int year) {
		date.set(Calendar.DAY_OF_MONTH, day);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);
	}
	
	public void setTime(int hour, int minute) {
		date.set(Calendar.HOUR, hour);
		date.set(Calendar.MINUTE, minute);
		
	}
	
	public void setOrigin(String loc) {
		origin = loc;
	}
	
	public void setDestination(String loc) {
		destination = loc;
	}
	
	public void setTravelTime(int time) {
		travelTime = time;
	}
	
	public void setAirline(String name) {
		airline = name;
	}
	
	public void setCost(int c) {
		cost = c;
	}
}
