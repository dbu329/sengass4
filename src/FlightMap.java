import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;



public class FlightMap {
	HashSet<Flight> edges = new HashSet<Flight>();
	
	public static void main(String[] args) {
		System.out.println("Seng Asscheduler:");
		FlightMap newMap = new FlightMap(args);
	}
	
	public FlightMap(String[] args) {
		readFlightData(args[0]);
		readQueryData(args[1]);
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
     	    	String delims = "[,]";
     	    	String tokens[] = line.split(delims);
     	    	if (tokens.length % 7 != 0) {
     	    		System.out.println("incorrectly formatted flight data");
     	    		break;
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
	
	
}
