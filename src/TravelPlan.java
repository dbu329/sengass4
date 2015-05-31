import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class TravelPlan {
	
	FlightMap myFlightMap;
	ArrayList<Query> queryList;
	
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
		queryList = new ArrayList<Query>();
		readFlightData(args[0]);
		readQueryData(args[1]);
		findAnswers(queryList);
	}


	/**
	 * Reads the query input, checking whether it is of valid format. If the format is valid but
	 * the values are not valid, then that query will be skipped. If the format is invalid then
	 * "incorrectly formatted query data" will be printed and program will exit. (?)
	 * @param file query input file
	 */
	private void readQueryData(String file) {
		Scanner sc = null;
		try	{
			sc = new Scanner(new FileReader(file)); 
     	    while (sc.hasNextLine()) {
     	    	String line = sc.nextLine();
     	    	line = line.replaceAll("\\s", "");
     	    	// check queries have valid format
     	    	if (!verifyQueryFormat(line)) {
     	    		System.out.println("incorrectly formatted query data");
     	    		System.exit(0);
     	    	}
     	    	// edit line to split string into tokens
     	    	line = line.replaceAll("\\[", "");
     	    	line = line.replaceAll("\\]", ",");
     	    	line = line.replaceAll("\\(", "");
     	    	line = line.replaceAll("\\)", "");
     	    	String delims = "[,]+";
     	    	String lineTokens[] = line.split(delims);
     	    	parseValidQuery(lineTokens);
     	    }
     	    
//     	    for (Query q : queryList) {
//     	    	q.print();
//     	    }
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		} finally {
		    if (sc != null) sc.close();
		}
	}

	private boolean verifyQueryFormat(String data) {
		// String data is the current line from the file
		boolean isValid = true;
		//First Make Sure all the Required Brackets Are there
		if (!isValidBracketsQuery(data)) isValid = false;
		//prepare data, then split data into tokens
		data = data.replaceAll("\\s", ""); // get rid of spaces
		data = data.replaceAll("\\[", ""); // get rid of [ 
		data = data.replaceAll("\\]", ",");// replace ] with ,
    	data = data.replaceAll("\\(", ""); // get rid of (
    	data = data.replaceAll("\\)", ""); // get rid of )
		String[] dataArray = data.split("[,]"); // split data by commaa (,)
	
		if (dataArray.length % 8 != 0) {
			isValid = false;
		} else {
			for (int i = 0; i < dataArray.length-1;i+=8) {
				//1. check Date is valid
				String[] tmpTokens = dataArray[i].split("/");
				//if date doesn't contain 3 tokens then definitely not in layout of DD/MM/YYYY
				if (tmpTokens.length != 3) {
					isValid = false;
				} else {
					try {
						Integer.parseInt(tmpTokens[0]);
						Integer.parseInt(tmpTokens[1]);
						Integer.parseInt(tmpTokens[2]);
					} catch(NumberFormatException e) {
						isValid = false;
					}
				}
				//2. check Time is valid
				tmpTokens = dataArray[i+1].split("[:]");
				if (tmpTokens.length != 2) {
					isValid = false;
				} else {
					try {
						Integer.parseInt(tmpTokens[0]);
						Integer.parseInt(tmpTokens[1]);
					} catch(NumberFormatException e) {
						isValid = false;
					}
				}
				//3. check origin and destination is valid?
				if (!isValidName(dataArray[i+2])) 
					isValid = false;
				if (!isValidName(dataArray[i+3]))
					isValid = false;
				//4.check PreferenceType is valid? from i+4 to i+6
				// daf is dis? No mention of error messages in the spec
				// Do we actually assume that Query passed in is correct then? 
				// If we DO do a error message for Query, are we meant to make sure
				// that the preferenceTypes contain one of each from 
				// (Cost, Time and "airline"?)
				// 	Furthermore would that be an incorrect format error? or incorrect
				//  preference error or something?
				//TODO TODO TODO TODO TODO TODO
				//TODO TODO TODO TODO TODO TODO TODO
				//TODO TODO TODO TODO TODO TODO TODO TODO
				//TODO TODO TODO TODO TODO TODO TODO TODO TODO
				//TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
				//5. check number of plans to return is valid
				if (!isValidNumber(dataArray[i+7])) 
					isValid = false;
			}
		}
		return isValid;
	}

	private boolean isValidBracketsQuery(String data) {
		//Query [Date, Time, Name, Name, (PreferenceType,PreferenceType,PreferenceType), Number]
		data = data.replaceAll("\\s", ""); // get rid of spaces
		int startCount = 0, commaCount = 0, endCount = 0;
		int roundStart = 0, roundEnd = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '[') {
				startCount++;
			} else if (data.charAt(i) == '(') {
				if (commaCount != 4 || startCount-1 != roundStart) {
					return false;
				}
				roundStart++;
			} else if (data.charAt(i) == ')') {
				if (roundStart -1 != roundEnd || commaCount != 6) {
					return false;
				}
				roundEnd++;
			} else if (data.charAt(i) == ',') {
				commaCount++;
			} else if (data.charAt(i) == ']') {
				if (commaCount == 7 && startCount-1 == endCount && roundStart == roundEnd) {
					endCount++;
					commaCount = 0;
				} else {
					System.out.println("fucked up commcount:"+commaCount+"startCount" + startCount + "endCount:"+endCount);
					System.out.println("roundStart"+roundStart+"roundEnd"+roundEnd);
					return false;
				}
			}
		}
		if (startCount != endCount || commaCount != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	// e.g. Valid Query:
	// [Date, Time, Name, Name, (PrefType1, PrefType2, PrefType3), numToDisplay]
	/**
	 * Parses the query given as an array of String tokens, adding to the queryList
	 * iff the data is valid, otherwise prints the respective error message to screen and continues
	 * adding as many valid queries as supplied
	 * @precondition each set of tokens representing 1 query (8 tokens) are in a valid format.
	 * @param lineTokens array of tokens from the input queries
	 */
	private void parseValidQuery(String[] lineTokens) {
		for (int i = 0; i < lineTokens.length; i += 8) {
			//get the date, splitting first token with delimiter '/'
			String[] tmpTokens = lineTokens[i].split("/");
			int day = Integer.parseInt(tmpTokens[0]);
			int month = Integer.parseInt(tmpTokens[1])-1; //months from 0 to 11
			int year = Integer.parseInt(tmpTokens[2]);
			// then gets the hour and minutes
			tmpTokens = lineTokens[i+1].split("[:]");
			int hour = Integer.parseInt(tmpTokens[0]);
			int min = Integer.parseInt(tmpTokens[1]);
			//check validity of date and time values
			if (!validDateTime(day, month ,year, hour, min)){
				System.out.print("Invalid date/time in entry: ");
				// re-generate the input line
				System.out.println("[" + day + "/"+ month + "/" + year + ", " + 
								   hour + ":" + min + ", " + lineTokens[i+2] + 
								   ", " + lineTokens[i+3] + ", (" + lineTokens[i+4] +
								   ", " + lineTokens[i+5] + ", " + lineTokens[i+6] + 
								   "), " + lineTokens[i+7] + "]");
				continue;
			} 
			// set up Calendar object
			Calendar dateTime = Calendar.getInstance();
			dateTime.set(Calendar.DAY_OF_MONTH, day);
			dateTime.set(Calendar.MONTH, month);
			dateTime.set(Calendar.YEAR, year);
			dateTime.set(Calendar.HOUR_OF_DAY, hour);
			dateTime.set(Calendar.MINUTE, min);
			
			ArrayList<String> prefOrder = new ArrayList<String>();
			
			prefOrder.add(lineTokens[i+4]);
			prefOrder.add(lineTokens[i+5]);
			prefOrder.add(lineTokens[i+6]);

			int numToDisplay = Integer.parseInt(lineTokens[i+7]);

			System.out.println(prefOrder);
			
			Query newQuery = new Query(dateTime, lineTokens[i+2], lineTokens[i+3],
									   prefOrder, numToDisplay);
			queryList.add(newQuery);
		}
	}
	
	/**
	 * Contains two checks. First uses the function verifyFlightFormat, this is to check formatting in
	 * general. Fail the program and returns "incorrectly formatted flight data". However, in the case
	 * of invalid date/time entries, it will ignore adding that flight, and just output the flight .
	 * If passing first verification and second, it will be added to FlightMap.
	 * details for which it has failed on
	 * @param file
	 */
	private void readFlightData(String file) {
 
			String line = fileToString(file);
		    boolean correctFormat = true;

 	    	line = line.replaceAll("\\s", "");
 	    	
 	    	//check that the all flights(one or more) in the current line has
 	    	// valid format. If format is not valid, quit program
 	    	if (verifyFlightFormat(line) == false) {
 	    		System.out.println("incorrectly formatted flight data");
 	    		correctFormat = false;
 	    		//break;
 	    	}else{
     	    	//Edit line, in order to be able to split into string tokens.
     	    	line = line.replaceAll("\\[", "");
     	    	line = line.replaceAll("\\]", ","); //replace end bracket by comma
     	    	String delims = "[,]+";             //split string line, by comma
     	    	String lineTokens[] = line.split(delims);
     	    	//move through one OR more flights in the current line, and add
     	    	//them flights. Already proved above the format was correct.
     	    	parseValidFlights(lineTokens);
 	    	}
     	    
     	    if (!correctFormat) {
     	    	System.exit(0);
     	    }
//     	    myFlightMap.printEdges();
	}
	
	/**
	 * Given a array of string tokens containing one OR more flights (from the current line),
	 * parses each flight, and if it contains invalid date/time, print it, and skip it.
	 * PRE-CONDITION: the lineTokens are already verified to be of valid format (before
	 * they were split into lineTokens).
	 * @param lineTokens
	 */
	private void parseValidFlights(String[] lineTokens) {
		for (int i = 0; i < lineTokens.length; i += 7) {
			Flight myFlight = new Flight();
			//first gets date, below line splits the first token(the date) by /,
			String[] tmpTokens = lineTokens[i].split("/");
			int day = Integer.parseInt(tmpTokens[0]);
			int month = Integer.parseInt(tmpTokens[1])-1; //months from 0 to 11
			int year = Integer.parseInt(tmpTokens[2]);
			// then gets the hour and minutes
			tmpTokens = lineTokens[i+1].split("[:]");
			int hour = Integer.parseInt(tmpTokens[0]);
			int min = Integer.parseInt(tmpTokens[1]);
			
			myFlight.setDate(day, month, year);
			myFlight.setTime(hour, min); 
			myFlight.setOrigin(lineTokens[i+2]);
			myFlight.setDestination(lineTokens[i+3]);
			myFlight.setTravelTime(Integer.parseInt(lineTokens[i+4]));
			myFlight.setAirline(lineTokens[i+5]);
			myFlight.setCost(Integer.parseInt(lineTokens[i+6]));
			//check validity of date and time values
			if (!validDateTime(day,month,year, hour, min)){
				System.out.print("Invalid date/time in entry: ");
				printInvalidDateTime(myFlight,day, month, year, hour, min);
				continue;
			} 
			myFlightMap.addFlight(myFlight);
		}
	}
	
	//Keep in mind the order of information in our Flight
	//Flight -> [ Date, Time, Name, Name, Duration, Name, Number ]
	private boolean verifyFlightFormat(String data){
		// String data is the current line from the file
		boolean isValid = true;
		//First Make Sure all the Required Brackets Are there
		if (!isValidBracketsFlights(data))	isValid = false;
		//prepare data, then split data into tokens
		data = data.replaceAll("\\s", ""); // get rid of spaces
		data = data.replaceAll("\\[", ""); // get rid of [ 
		data = data.replaceAll("\\]", ",");// replace [ with ,
		String[] dataArray = data.split("[,]"); // split data by commaa (,)
	
		if (dataArray.length % 7 != 0) {
//		if (!isValidBrackets(data)) {
			isValid = false;
		} else {
			for (int i = 0; i < dataArray.length-1;i+=7) {
				//1. check Date is valid
				String[] tmpTokens = dataArray[i].split("/");
				//if date doesn't contain 3 tokens then definitely not in layout of DD/MM/YYYY
				if (tmpTokens.length != 3) {
					isValid = false;
				} else {
					try {
						Integer.parseInt(tmpTokens[0]);
						Integer.parseInt(tmpTokens[1]);
						Integer.parseInt(tmpTokens[2]);
					} catch(NumberFormatException e) {
						isValid = false;
					}
				}
				//2. check Time is valid
				tmpTokens = dataArray[i+1].split("[:]");
				if (tmpTokens.length != 2) {
					isValid = false;
				} else {
					try {
						Integer.parseInt(tmpTokens[0]);
						Integer.parseInt(tmpTokens[1]);
					} catch(NumberFormatException e) {
						isValid = false;
					}
				}
				//3. check origin and destination is valid?
				if (!isValidName(dataArray[i+2])) 
					isValid = false;
				if (!isValidName(dataArray[i+3]))
					isValid = false;
				//4.check duration is valid?
				if (!isValidNumber(dataArray[i+4])) 
					isValid = false;
				//5. check Airline is valid
				if (!isValidName(dataArray[i+5]))
					isValid = false;
				//6. check cost is valid
				if (!isValidNumber(dataArray[i+6])) 
					isValid = false;
			}
		}
		return isValid;
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
	
	private boolean isValidBracketsFlights(String data) {
		//makes sure that the brackets are there and inbetween each open and
		//close bracket, there will be 6 commas to divide it all
		//Flight -> [ Date, Time, Name, Name, Duration, Name, Number ]
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
				} else {
					return false;
				}
			}
		}
		if (startCount != endCount || commaCount != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validDateTime(int day, int month, int year, int hour, int min){
		// The year must be between 2000 ad 2500, and all dates are valid in between these years.
		boolean validDate = true;
		if(month < 1 || month > 12 || day < 1 || day > 31){
			validDate = false;
		}else{
			if(month == 2){
				if(day > 28){
					if(day == 29 && !isLeapYear(year)) validDate = false;
				}
			}else{
				//April, June, September November only have 30 days
				if(day == 31 && (month == 4 || month == 6 || month == 9 || month == 11)){
					validDate = false;
				}
			}
		}
		if (year < 2000 || year > 2500)
			validDate = false;
		if (min < 0 || hour < 0 || min > 59 || hour > 23)
			validDate = false;
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

	private void printInvalidDateTime(Flight myFlight, int day, int month, int year, int hour, int min) {
			System.out.format("[%02d/%02d/%02d,", day, month, year);
			System.out.format("%02d:%02d,", hour, min);
			System.out.print(myFlight.getOrigin() + "," + myFlight.getDestination());
			System.out.print(myFlight.getTravelTime() + ",");
			System.out.print(myFlight.getAirline()+",");
			System.out.println(myFlight.getCost()+"]");
	}
	
	private String fileToString(String filePath){
		
		String text = "";
		try
		{
			Scanner sc = new Scanner(new FileReader(filePath));
			while(sc.hasNextLine()){
				text = text + sc.nextLine();
			}
		} catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
			
		return text;
	}
	
	// for every query, return a query answer list, which contains the query and the answer
	private ArrayList<QueryAnswerPair> findAnswers(ArrayList<Query> queryList) {
		ArrayList<QueryAnswerPair> queryAnswerList = new ArrayList<QueryAnswerPair>();
		
		KBestFirstSearch kbfs = new KBestFirstSearch(myFlightMap);
		
		for (Query q : queryList) {
			queryAnswerList.add(kbfs.search(q));
		}
		

		return queryAnswerList;
	}

	
}
