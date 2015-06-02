import java.util.Calendar;


public class Flight {

	private Calendar date;
	private String origin;
	private String destination;
	private int duration; // minutes
	private String airline;
	private int cost;
	
	public Flight(Calendar date, String origin, String destination, int duration, String airline, int cost) {
		//date = Calendar.getInstance();
		this.date = date;
		this.origin = origin;
		this.destination = destination;
		this.duration = duration;
		this.airline = airline;
		this.cost = cost;
	}
	
	/*public void setDate(int day, int month, int year) {
		date.set(Calendar.DAY_OF_MONTH, day);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);
	}*/
	
	/*public void setTime(int hour, int minute) {
		date.set(Calendar.HOUR_OF_DAY, hour);
		date.set(Calendar.MINUTE, minute);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setTimeZone(date.getTimeZone());
//		System.out.println("setting time: " + sdf.format(date.getTime()));
	}*/
	
	public Calendar getDate() {
		return date;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}

	public int getDuration() {
		return duration;
	}

	public String getAirline() {
		return airline;
	}

	public int getCost() {
		return cost;
	}
	
	/*public void print() {
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
	}*/
	
	@Override
	public String toString() {
		return String.format("%s -> %s", origin, destination);
	}

}
