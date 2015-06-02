import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class KBestFirstSearch {
	
	private FlightMap graph;
	
	public KBestFirstSearch(FlightMap graph) {
		this.graph = graph;
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
	
	public List<Path> search(Query q) {
		List<Comparator<Path>> preferences = q.getPreferences();
		
		// nodes (we're searching through) = 'flight'
		// path (is a list of flights) aka = 'flightplan'
		
		// originating city.
		String start = q.getOrigin();
		String finish = q.getDestination();
		//System.out.println("want to find an flight path for: " + start + "-->" + finish);
		//String airlineToUse = preferences.airLinePreference();
		String airlineToUse = q.getAirlinePreference();
		
		//creates a valid comparator given the preferences of the current Query q
		//QueueComparator myComparator = new QueueComparator(preferences.getPrefList());
		
		// Creates a new Priority Queue using the new comparator created above
		// Essentially the 'toVisit' list
		PriorityQueue<Path> b = new PriorityQueue<Path>(10, new MultiComparator<Path>(preferences));
		
		//create a new flight from null -> origin
		Flight fake = new Flight(q.getDepartureTime(), null, start, 0, "None", 0);

		//Stores the count of shortest paths to EACH city.
		// Then set the count for all of these cities to ZERO
		HashMap<String, Integer> numShortestPaths = new HashMap<String, Integer>();
		for (String s : graph.getCities()) {
			numShortestPaths.put(s, 0);
		}

//		System.out.println("numShortestPaths (HashMap) : " + numShortestPaths);

//		System.out.println();
		
		// P is not just a flight plan, pretty much the 'curr' in our other searches
		// HashSet<FlightPlan> P = new HashSet<FlightPlan>();
		Path u;
		for (Flight flight : graph.getNeighbours(fake)) {
			u = new Path();
			u.addFlight(flight);
			b.offer(u);
		}
		//System.out.println(fake);
//		System.out.println("dummy flight plan" + u);
		//b.offer(u);
		
		// Need a set/list of the paths that we found from start to finish
		// big 'P' in Wikipedia
		List<Path> pathsToFinish = new ArrayList<Path>();

//		System.out.println("Number of paths to find: " + q.getNumToDisplay());
//		System.out.println("numShortestPaths.get(" + finish + ") = " + numShortestPaths.get(finish));
		
		while (!b.isEmpty() &&  numShortestPaths.get(finish) < q.getNumToDisplay()) 
			/* 
			 * decreases (q.getNumToDisplay - numShortestPaths.get(finish) );
			 * 
			 * modifies  *something;
			 * 
			 * When (q.getNumToDisplay - numShortestPaths.get(finish) <= 0 or the list 'b' 
			 * is empty then the negation of the guard will ensure that the loop terminates
			 */
		{
//			System.out.print("\n-----New Loop: B Contains:\n");
			for (Path fp:b) {
//				System.out.println("\t"+fp + " Cost="+fp.getTotalCost()+ " Time="+fp.getTotalTime() + "airline" + fp.getAirlineTime());
			}
			//gets a flightPlan(our path) from the priority queue (already sorted to preferences)
			// also removes itself from the top of the priority queue
			u = b.poll();
			
			/*
			 * FlightPlan u = cheapest path in set b
			 * The shortest path from src -> u increases by one
			 */

//			System.out.println("just popped off: " + u);
			// the shortest path from src -> 'u' increases by one
//			System.out.println("current city = " + u.getCurrentCity());
//			System.out.println("num shortest paths to 'u' = " + numShortestPaths.get(u.getCurrentCity()));
			numShortestPaths.put(u.getCurrentCity(), numShortestPaths.get(u.getCurrentCity())+1);
			
			// if the city of the current city == finish city, 
			if (u.getCurrentCity().equals(finish)) 
				/*
				 * decreases (q.getNumToDisplay - numShortestPaths.get(finish) ) 
				 */
			{
				pathsToFinish.add(u);
			}
			
			// if the QUOTA hasn't been fulfilled...
			if (numShortestPaths.get(u.getCurrentCity()) <= q.getNumToDisplay()) 
				/*
				 * assert (q.getNumToDisplay - numShortestPaths.get(u.currentCity()) >= 0
				 *
				 */
			{
				// for every neighbour of 'u', get the neighbours of the current city...
//				System.out.println(myMap.getNeighbours(u.getLastFlight()).size());
				for (Flight f : graph.getNeighbours(u.getLastFlight())) 
				/*
				 * modifies b
				 * ensures |b| = |b| + |u.getNeighbours|
				 */
				
				{
//					System.out.println("adding neighbour");
					Path path = new Path(u.getFlights());
					//newPlan.incrementAirlineTime(u.getAirlineTime(airlineToUse));
					path.addFlight(f);
					//if (airlineToUse.equals(f.getAirline())) {
						//newPlan.incrementAirlineTime(f.getDuration());
					//}
					b.offer(path);
				}
			}
			
			
		}
		System.out.println("Paths Found:");
		for (Path pl: pathsToFinish) {
			System.out.println(pl+" Cost:"+pl.getCost() +" Travel Time:" + pl.getTotalTime()+ " Airline Hours Used" + pl.getAirlineTime(airlineToUse));
		}

		
		/*List<Answer> answerList = new ArrayList<Answer>();
		for (Path pl2 : pathsToFinish) {
			Answer a = new Answer(pl2);
			answerList.add(a);
		}*/
		
		//QueryAnswerPair queryAnswerPair = new QueryAnswerPair(q, answerList);

//		System.out.println("#######################################");
		return pathsToFinish;
	}

}
