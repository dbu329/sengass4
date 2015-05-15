import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class TravelPlan {
	FlightMap myFlightMap;
	//Query myQueryList;
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: FlightMap flightsFile queryFile");
		} else {
			System.out.println("Seng Asscheduler:");
			TravelPlan newPlan = new TravelPlan(args);
		}
	}
	
	public TravelPlan(String[] args) {
		myFlightMap = new FlightMap();
		readFlightData(args[0]);
		readQueryData(args[1]);
	}
	
	private void readQueryData(String file) {
//		Scanner sc = null;
//		try
//		{
//		    sc = new Scanner(new FileReader(file)); 
//     	    while (sc.hasNextLine()) {
//     	    	
//     	    }
//		}
//		catch (FileNotFoundException e) {System.out.println("File not Found");}
//		finally
//		{
//		    if (sc != null) sc.close();
//		}  
	}

	/**
	 * Contains two checks. First uses the function verifyFlightData, this is to check formatting in
	 * general. Fail the program and returns "incorrectly formatted flight data". However, in the case
	 * of invalid date/time entries, it will ignore adding that flight, and just output the flight .
	 * If passing first verification and second, it will be added to FlightMap.
	 * details for which it has failed on
	 * @param file
	 */
	private void readFlightData(String file) {
		Scanner sc = null;
		try
		{
		    sc = new Scanner(new FileReader(file)); 
		    boolean correctFormat = true;
     	    while (sc.hasNextLine()) {
     	    	String line = sc.nextLine();
     	    	line = line.replaceAll("\\s", "");
     	    	correctFormat = true;
     	    	//check that the data in String line is valid
     	    	if (!verifyFlightData(line)) {
     	    		System.out.println("incorrectly formatted flight data");
     	    		correctFormat = false;
     	    		//break;
     	    	}else{
	     	    	//prepare line for insertion into warzone
	     	    	line = line.replaceAll("\\[", "");
	     	    	line = line.replaceAll("\\]", ","); //replace end bracket by comma
	     	    	String delims = "[,]+";             //split string line, by comma
	     	    	String lineTokens[] = line.split(delims);
		    		for (int i = 0; i < lineTokens.length; i += 7) {
		    			Flight myFlight = new Flight();
		    			//first set date
		    			String[] tmpTokens = lineTokens[i].split("/");
		    			// problem: have to print out the flight details of the flight
		    			// with invalid date and/or time. However can't test for this at the end
		    			// because setting Calendar day to, say, 56, will give 26 and it automatically
		    			// increments the month. Decision: don't use Calendar (probs not), or store variables
		    			// aside before sending them into the setDate functions.
		    			// like this: int day = Integer.parseInt(tmpTokens[0]); test for invalid, thenpass in
		    			myFlight.setDate(Integer.parseInt(tmpTokens[0]), 
		    							 Integer.parseInt(tmpTokens[1])-1, //month is minus one cos Calendar.
		    							 Integer.parseInt(tmpTokens[2]));
	
		    			//then set time
		    			tmpTokens = lineTokens[i+1].split("[:]");
		    			myFlight.setTime(Integer.parseInt(tmpTokens[0]), 
		    				           	 Integer.parseInt(tmpTokens[1]));
		    			//then set origin and dest
		    			myFlight.setOrigin(lineTokens[i+2]);
		    			myFlight.setDestination(lineTokens[i+3]);
		    			//then setflight time
		    			myFlight.setTravelTime(Integer.parseInt(lineTokens[i+4]));
		    			//set airline
		    			myFlight.setAirline(lineTokens[i+5]);
		    			//set cost
		    			myFlight.setCost(Integer.parseInt(lineTokens[i+6]));
		    			
		    			myFlightMap.addFlight(myFlight);
		    		}
     	    	}
     	    }
     	    myFlightMap.printEdges();
		}
		catch (FileNotFoundException e) {System.out.println("File not Found");}
		finally
		{
		    if (sc != null) sc.close();
		}  
	}
	
	//Keep in mind the order of information in our Flight
	//Flight -> [ Date, Time, Name, Name, Duration, Name, Number ]
	private boolean verifyFlightData(String data){
		String original = data;
		boolean valid = true;
		boolean validDateTime = true;
		//First Make Sure all the Required Brackets Are there
		if (!isValidBrackets(data))	valid = false;
		//prepare data, then split data into tokens
		data = data.replaceAll("\\s", ""); // get rid of spaces
		data = data.replaceAll("\\[", ""); // get rid of [ 
		data = data.replaceAll("\\]", ",");// replace [ with ,
		String[] dataArray = data.split("[,]"); // split data by commaa (,)
	
		if (dataArray.length % 7 != 0) {
			valid = false;
		} else {
			for (int i = 0; i < dataArray.length-1;i+=7) {
				//1. check Date is valid
				String[] tmpTokens = dataArray[i].split("/");
					//if date doesn't contain 3 tokens then definitely not in layout of DD/MM/YYYY
				if (tmpTokens.length != 3) {
					valid = false;
				} else {
					//This can be replaced with the NumberFormatException
					/*
					//if has 3 tokens, then check if they are all integers
					for (String s:tmpTokens) {
						if (!isValidNumber(s)) {
							valid = false;
						}
					}*/
					
					try
					{
						//Now check validity of each integer
						
						int day = Integer.parseInt(tmpTokens[0]);
						int month = Integer.parseInt(tmpTokens[1]);
						int year = Integer.parseInt(tmpTokens[2]);
						//According to spec, only need to consider 1/1/2000-31/12/2500
						if(year < 2000 || year > 2500){
							valid = false;
							validDateTime = false;
						}else{
							validDateTime = validDayMonth(day, month, year);
						}
					}
					catch(NumberFormatException e)
					{
						validDateTime = false;
					}
					
				}
				
				//2. check Time is valid
				tmpTokens = dataArray[i+1].split("[:]");
				if (tmpTokens.length != 2) {
					validDateTime = false;
				} else {
					//This can be replaced with the NumberFormatException
					/*for (String s:tmpTokens) {
						if (!isValidNumber(s)) validDateTime = false;
					}*/
					
					try
					{
						int hour = Integer.parseInt(tmpTokens[0]);
						int min = Integer.parseInt(tmpTokens[1]);
						
						//Follows 24hr time where midnight is the 0th hour
						if(min < 0 || hour < 0 || min > 59 || hour > 23){
							validDateTime = false;
						}
					}
					catch(NumberFormatException e)
					{
						validDateTime = false;
					}
				}
				
				if(!validDateTime){
					valid = false;
					System.out.println("Invalid date/time in entry: " + original);
				}
				
				//3. check origin and destination is valid?
				if (!isValidName(dataArray[i+2]))
						valid = false;
				if (!isValidName(dataArray[i+3]))
					valid = false;
				//4.check duration is valid?
				if (!isValidNumber(dataArray[i+4])) 
					valid = false;
				//5. check Airline is valid
				if (!isValidName(dataArray[i+5]))
					valid = false;
				//6. check ff points is valid
				if (!isValidNumber(dataArray[i+6])) 
					valid = false;
			}
		}
		return valid;
	}
	
	//currently a name with a space in it is VALID
	private boolean isValidName(String name) {
		if (!name.matches("[A-Z][a-z]+")) {
			return false;
		}
		return true;
	}
	
	
	private boolean isValidNumber(String str) {
		if (!str.matches("\\d+")) {
			return false;
		}
		return true;
	}
	
	private boolean isValidBrackets(String data) {
		
		data = data.replaceAll("\\s", ""); // get rid of spaces
		int startCount = 0, commaCount = 0, endCount = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '[') {
				startCount++;
			} else if (data.charAt(i) == ',') {
				commaCount++;
			} else if (data.charAt(i) == ']') {
				if (commaCount == 6 && startCount-1 == endCount) {
					endCount++;
					commaCount = 0;
				}
			}
		}
		if (startCount != endCount || commaCount != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validDayMonth(int d, int m, int y){
		boolean validDate = true;
		
		if(m < 1 || m > 12 || d < 1 || d > 31){
			validDate = false;
		}else{
			if(m == 2){
				if(d > 28){
					if(d == 29 && !isLeapYear(y)) validDate = false;
				}
			}else{
				//April, June, September November only have 30 days
				if(d == 31 && (m == 4 || m == 6 || m == 9 || m == 11)){
					validDate = false;
				}
			}
		}
		
		return validDate;
	}
	
	private boolean isLeapYear(int y){
		if(y % 4 != 0){
			return false;
		}else if(y % 100 != 0){
			return true;
		}else if(y % 400 != 0){
			return false;
		}else{
			return true;
		}
	}
}
