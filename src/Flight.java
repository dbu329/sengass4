import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Flight {

	private Calendar date;
	private String origin;
	private String destination;
	private int travelTime; // minutes
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setTimeZone(date.getTimeZone());
//		System.out.println("setting time: " + sdf.format(date.getTime()));
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
	
	/**
	 * Returns the whole date as Calendar.
	 * @return
	 */
	public Calendar getTime () {
		return date;
	}
	
	public void print() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		dateFormat.setTimeZone(date.getTimeZone());
		System.out.print("[" + dateFormat.format(date.getTime()) + ",");
		//System.out.print("["+getDay() + "/" + getMonth() + "/" + getYear() + ",");
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		timeFormat.setTimeZone(date.getTimeZone());
		System.out.print(timeFormat.format(date.getTime()) + ",");
		//System.out.print(getHour() + ":" + getMinute() + ",");
		
		System.out.print(getOrigin() + "," + getDestination()+",");
		System.out.print(getTravelTime() + ","+getAirline()+"," + getCost()+"]");
	}
	
	public String toString() {
		return this.origin + "->" + this.destination;
	}
	

}
