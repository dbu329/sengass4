import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.util.HashSet;

public class FlightMap {
	
	HashSet<Flight> edges = new HashSet<Flight>();
	
	public static void main(String[] args) {
		System.out.println("Seng Asscheduler:");
		FlightMap newMap = new FlightMap(args);
	}
	
	public FlightMap(String[] args) {
		readFlightData(args[0]);
//		readQueryData(args[1]);
	}
	
	private void readQueryData(String string) {
		
	}

	private void readFlightData(String file) {
		Scanner sc = null;
		try
		{
		    sc = new Scanner(new FileReader(file)); 
     	    while (sc.hasNextLine()) {
     	    	Flight myFlight = new Flight();
     	    	String line = sc.nextLine();
     	    	line = line.replaceAll("\\s", "");

//     	    	for (int i = 0; i < tokens.length; i++)
//     	    		System.out.print("("+tokens[i]+")");
     	    	System.out.println();
     	    	//if the values are not in multiples of seven, must be missing something.
     	    	if (!verifyFlightData(line)) {
     	    		System.out.println("incorrectly formatted flight data");
     	    		break;
     	    	}
     	    	
     	    	line = line.replaceAll("\\[", "");
     	    	line = line.replaceAll("\\]", ",");
     	    	String delims = "[,]+";
     	    	String tokens[] = line.split(delims);
	    		for (int i = 0; i < tokens.length; i += 7) {
	    			//first set date
//	    			String[] dateTokens = tokens[i].split("/");
	    			
//	    			myFlight.setDate(day, month, year);
	    			
	    			//then set time
	    			//then set origin and dest
	    			//then setflight time
	    			//set airline
	    			//set ff points
	    		}
     	    	
     	    }
		
		}
		catch (FileNotFoundException e) {System.out.println("File not Found");}
		finally
		{
		    if (sc != null) sc.close();
		}  
	}
	
//	private boolean addFlight(String[] values) {
//		
//	}
	
		
//	public static void main(String[] args) {
//		
//		File flightsFile = new File(args[0]); //flight data file
//		File queriesFile = new File(args[1]); //query file
//		Scanner parser = null;
//		
//		//process flight data file section
//		try
//		{
//			//Assuming we're not meant to go line by line 
//			//and we're meant to comb the file
//			//by regexing for flights
//			
//			parser = new Scanner(flightsFile);
//			Pattern flightContainer = Pattern.compile("\\s*\\[.*?\\]\\s*");
//			
//			while(parser.hasNext(flightContainer)){
//				String flightString = parser.next(flightContainer);
//				flightString.replaceAll("[\\s\\[\\]]", "");
//				
//				String[] flightData = flightString.split(",");
//                for (String s : flightData) {
//                    System.out.print("("+s+")");
//                }
//				
//				if(!flightVerified(flightData)){
//					System.out.println("incorrectly formatted flight data");
//				}else{
//					//next phase: adding all the info to the FlightMap
//				}
//			}
//		}
//		catch(FileNotFoundException e){
//			System.out.println("Flight data file not found.");
//		}
//	}
//	
	//Keep in mind the order of information in our Flight
	//Flight -> [ Date, Time, Name, Name, Duration, Name, Number ]
	private boolean verifyFlightData(String data){
		boolean valid = true;
		
		//First Make Sure all the Required Brackets Are there
		if (!isValidBrackets(data))
			valid = false;
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
					//if has 3 tokens, then check if they are all integers
					for (String s:tmpTokens) {
						if (!isValidNumber(s)) {
							valid = false;
						}
					}
				}
				
				//2. check Time is valid
				tmpTokens = dataArray[i+1].split("[:]");
				if (tmpTokens.length != 2) {
					valid = false;
				} else {
					for (String s:tmpTokens) {
						if (!isValidNumber(s))
							valid = false;
					}
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
	
}
