import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class FlightMap {
	
	public static void main(String[] args) {
		
		File flightsFile = new File(args[0]); //flight data file
		File queriesFile = new File(args[1]); //query file
		Scanner parser = null;
		
		//process flight data file section
		try
		{
			//Assuming we're not meant to go line by line 
			//and we're meant to comb the file
			//by regexing for flights
			
			parser = new Scanner(flightsFile);
			Pattern flightContainer = Pattern.compile("\\s*\\[.*?\\]\\s*");
			
			while(parser.hasNext(flightContainer)){
				String flightString = parser.next(flightContainer);
				flightString.replaceAll("[\\s\\[\\]]", "");
				
				String[] flightData = flightString.split(",");
				
				if(!flightVerified(flightData)){
					System.out.println("incorrectly formatted flight data");
				}else{
					//next phase: adding all the info to the FlightMap
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Flight data file not found.");
		}
	}
	
	//Keep in mind the order of information in our Flight
	//Flight -> [ Date, Time, Name, Name, Duration, Name, Number ]
	private static boolean flightVerified(String[] dataArray){
		
		
		
		return true;
	}
}
