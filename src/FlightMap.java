import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;



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
		    Flight savedValues = new Flight();
     	    while (sc.hasNextLine()) {
     	    	Flight myFlight = new Flight();
     	    	String line = sc.nextLine();
     	    	line.replaceAll(" ", "");
     	    	String delims = "[,]+";
     	    	String tokens[] = line.split(delims);
     	    	System.out.println(tokens.length);
     	    	if (tokens.length % 7 != 0) {
     	    		System.out.println("incorrectly formatted flight data"+tokens.length);
//     	    		break;
     	    	}
	    		for (int i = 0; i < tokens.length; i++) {
	 	    		//straight away start analysing
	 	    		
	 	    		if (tokens[i].indexOf('[') >= 0) {
	 	    			//initiate start, if reached end of tokens, automatically
	 	    			//read in the next line, and continues along the current FlightData
	 	    		} 
	    		}
     	    	for (int i = 0; i < tokens.length; i++)
     	    		System.out.print("("+tokens[i]+")");
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
//	//Keep in mind the order of information in our Flight
//	//Flight -> [ Date, Time, Name, Name, Duration, Name, Number ]
//	private static boolean flightVerified(String[] dataArray){
//		
//		
//		
//		return true;
//	}
	
	
}
