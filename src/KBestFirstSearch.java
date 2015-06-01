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
	
	//TODO: A prelimiary check to determine whether a query's start and finish is in the
	// list of all possible locations. if not, return EMPTY STRING.
	
	public QueryAnswerPair search(Query q) {
		Preferences queryPreferences = q.getPreferences();
		
		// nodes (we're searching through) = 'flight'
		// path (is a list of flights) aka = 'flightplan'
		
		// originating city.
		String start = q.getOrigin();
		String finish = q.getDestination();
		System.out.println("want to find an flight path for: " + start + "-->" + finish);
		String airlineToUse = queryPreferences.airLinePreference();
		
		//creates a valid comparator given the preferences of the current Query q
		QueueComparator myComparator = new QueueComparator(queryPreferences.getPrefList());
		// Creates a new Priority Queue using the new comparator created above
		// Essentially the 'toVisit' list
		PriorityQueue<FlightPlan> b = new PriorityQueue<FlightPlan>(10,myComparator);
		
		Flight fake = new Flight();
		fake.setDate(q.getDay(), q.getMonth(), q.getYear());
		fake.setDestination(start);
		fake.setOrigin(null);
		fake.setTime(q.getHour(), q.getMinute());
		fake.setAirline("None");
		fake.setCost(0);
		fake.setTravelTime(0);

		//Stores the count of shortest paths to EACH city.
		// Then set the count for all of these cities to ZERO
		HashMap<String, Integer> numShortestPaths = new HashMap<String, Integer>();
		for (String s : myMap.getAllLocations()) {
			numShortestPaths.put(s, 0);
		}
		
//		System.out.println("numShortestPaths (HashMap) : " + numShortestPaths);
		
//		System.out.println();
		
		// P is not just a flight plan, pretty much the 'curr' in our other searches
		// HashSet<FlightPlan> P = new HashSet<FlightPlan>();
		FlightPlan u = new FlightPlan();
		u.addFlight(fake);
//		System.out.println("dummy flight plan" + u);
		b.offer(u);
		
		// Need a set/list of the paths that we found from start to finish
		// big 'P' in Wikipedia
		List<FlightPlan> pathsToFinish = new ArrayList<FlightPlan>();
		
//		System.out.println("Number of paths to find: " + q.getNumToDisplay());
//		System.out.println("numShortestPaths.get(" + finish + ") = " + numShortestPaths.get(finish));
		
		while (!b.isEmpty() &&  numShortestPaths.get(finish) < q.getNumToDisplay()) {
//			System.out.print("\n-----New Loop: B Contains:\n");
			for (FlightPlan fp:b) {
//				System.out.println("\t"+fp + " Cost="+fp.getTotalCost()+ " Time="+fp.getTotalTime() + "airline" + fp.getAirlineTime());
			}
			//gets a flightPlan(our path) from the priority queue (already sorted to preferences)
			// also removes itself from the top of the priority queue
			u = b.poll();
//			System.out.println("just popped off: " + u);
			// the shortest path from src -> 'u' increasesby one
//			System.out.println("current city = " + u.getCurrentCity());
//			System.out.println("num shortest paths to 'u' = " + numShortestPaths.get(u.getCurrentCity()));
			numShortestPaths.put(u.getCurrentCity(), numShortestPaths.get(u.getCurrentCity())+1);
			
			// if the city of the current city == finish city, 
			if (u.getCurrentCity().equals(finish)) {
				pathsToFinish.add(u);
			}
			
			// if the QUOTA hasn't been fulfilled...
			if (numShortestPaths.get(u.getCurrentCity()) <= q.getNumToDisplay()) {
				// for every neighbour of 'u', get the neighbours of the current city...
//				System.out.println(myMap.getNeighbours(u.getLastFlight()).size());
				for (Flight f : myMap.getNeighbours(u.getLastFlight())) {
//					System.out.println("adding neighbour");
					FlightPlan newPlan = new FlightPlan(u.getListOfFlights());
					newPlan.incrementAirlineTime(u.getAirlineTime());
					newPlan.addFlight(f);
					if (airlineToUse.equals(f.getAirline())) {
						newPlan.incrementAirlineTime(f.getTravelTime());
					}
					b.offer(newPlan);
				}
			}
			
			
		}
		System.out.println("Paths Found:");
		for (FlightPlan pl: pathsToFinish) {
			System.out.println(pl+" Cost:"+pl.getTotalCost() +" Travel Time:" + pl.getTotalTime()+ " Airline Hours Used" + pl.getAirlineTime() );
		}

		
		ArrayList<Answer> answerList = new ArrayList<Answer>();
		for (FlightPlan pl2 : pathsToFinish) {
			Answer a = new Answer(pl2);
			answerList.add(a);
		}
		
		QueryAnswerPair queryAnswerPair = new QueryAnswerPair(q, answerList);
		
//		System.out.println("#######################################");
		return queryAnswerPair;
	}
	

	
}
