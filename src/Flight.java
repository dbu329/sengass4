import java.sql.Time;
import java.util.Calendar;
import java.util.Date;


public class Flight {

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
		date.set(Calendar.HOUR_OF_DAY, hour);
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
	
	public int getDay() {
		return date.get(Calendar.DATE);
	}
	
	public int getMonth() {
		return date.get(Calendar.MONTH);
	}
	
	public int getYear() {
		return date.get(Calendar.YEAR);
	}
	
	public int getHour() {
		return date.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getMinute() {
		return date.get(Calendar.MINUTE);
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public int getTravelTime() {
		return travelTime;
	}
	
	public String getAirline() {
		return airline;
	}
	
	public int getCost() {
		return cost;
	}
}
