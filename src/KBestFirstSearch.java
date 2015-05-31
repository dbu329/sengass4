import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class KBestFirstSearch {
	
	private FlightMap myMap;
	
	public KBestFirstSearch(FlightMap fm) {
		myMap = fm;
	}

/*
method search(from:Vertex, to:Vertex, g:Graph, k: int) returns (P: set<set<Edge>>) 
requires from in g && to in g && closed(g); 
requires k > 0;
modifies set v:Vertex | v in g; 
ensures |P| == k; //where P is a set of k Paths and a Path is a set of Edges

decreases * ; // This is needed here to allow decreases * on the loop 
{
	var P := {} //Set of shortest paths
	var B := [] //Heap data structure containing paths 
	forall u:Vertex | u in g{ u.count := 0;	}
	from.path_from_root := new Edge.Init(from, from, 0); 
	B := B + [from.path_from_root];

	while B != [] && to.count < k
	decreases * //ignore termination for now
	{
		//let path P_u = cheapest path in B where u is a vertex
		//let C = total cost of P_u
		//B = B - P_u
		//u.count++
		if(u == to)
		{
			P := P + [P_u];
		}
		
		if(u.count <= k)
		{
			forall v:Vertex | v in u.adjacent 
			{
				P_v = P_u + [v];
				B = B + P_v;
			}
		}
	}
}
*/
	
	public QueryAnswerPair search(Query q) {
		Preferences queryPreferences = q.getPreferences();
		
		// nodes (we're searching through) = 'flight'
		// path (is a list of flights) aka = 'flightplan'
		
		// originating city.
		String start = q.getOrigin();
		String finish = q.getDestination();
		String airlineToUse = queryPreferences.airLinePreference();
		//creates a valid comparator given the preferences of the current Query q
		
		QueueComparator myComparator = new QueueComparator(queryPreferences.getPrefList());
		
		//Creates a new Priority Queue using the new comparator created above
		// Essentially the 'toVisit' list
		PriorityQueue<FlightPlan> b  = new PriorityQueue<FlightPlan>(10,myComparator);
		//HashMap<FlightPlan, Integer> b = new HashMap<FlightPlan, Integer>();

		//Stores the count of shortest paths to EACH city.
		// Then set the count for all of these cities to ZERO
		HashMap<String, Integer> numShortestPaths = new HashMap<String, Integer>();
		for (String s : myMap.getAllLocations()) {
			numShortestPaths.put(s, 0);
		}
		
		// P is not just a flight plan, pretty much the 'curr' in our other searches
		//HashSet<FlightPlan> P = new HashSet<FlightPlan>();
		FlightPlan p = new FlightPlan();
		
		//Need a set/list of the paths that we found from start to finish
		List<FlightPlan> pathsToFinish = new ArrayList<FlightPlan>();
			
		//TODO setup the first flight to add to flightPlan. Which should be a bogus flight
		//  	based on the query. (its costs are all zero and shit, origin = null
		//		destination = start (the variable defined above)
		
		System.out.println("Number of paths to find" + q.getNumToDisplay());
		while (b.isEmpty() &&  numShortestPaths.get(finish) < q.getNumToDisplay()) {
			//gets a flightPlan(our path) from the priority queue (already sorted to preferences)
			// also removes itself from the top of the priority queue
			p = b.poll();
			// Then increment the count of 'shortest cost' path to the current city
			String currentLocation = p.getCurrentCity();
			int pathsCountToCurrent = numShortestPaths.get(currentLocation);
			pathsCountToCurrent += 1;
			// And update it on the main HashMap holding all these counts
			numShortestPaths.put(currentLocation, pathsCountToCurrent);
			
			//if we found our destination, then add it to the list called pathsToFinish
			if (currentLocation.equals(finish)) {
				pathsToFinish.add(p);
			}
			
			//NOW, if the count(pathsCountToCurrent) of shortest cost to currentLocation is 
			//still lower or equal to the number of shortest paths to find, we go through neighbours
			if (pathsCountToCurrent <= q.getNumToDisplay()) {
				//First get the list of neighbours from the current Location
				//This is assuming that the list of NeighBour flights is all VALID
				Flight lastFlight = p.getLastFlight();
				ArrayList<Flight> neighbourFlights = myMap.getNeighbours(lastFlight);
				
				//now for each Flight in neighbourFlights, we make a new FlightPlan, concatenate 
				// the new Flight to FlightPlan p, remember to update totalAirline flight time value
				// if we have flown on the preferred airline (in variable 'airlineToUse')
				//then insert the new FlightPlan into the priorityqueue b. where it will sort itself
				
				//for loop
				
				
			}
			
			//String currCity = p.getCurrentCity()
			//List<Flight> = myMap.getAdjacent(currCity);
			// if the current flightplan's 
			
				//	if (flighttoneighbour.getAirline = airlineTOUse)
				//	a.incTotalFreq(a);
			
			
		}
		
		QueryAnswerPair queryAnswerPair = new QueryAnswerPair(q, null);
		
		return queryAnswerPair;
	}
	
	
	
}
