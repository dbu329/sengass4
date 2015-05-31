import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class KBestFirstSearch {
	
	public KBestFirstSearch() {
		
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
		
		// nodes (we're searching through) = 'flight'
		// path (is a list of flights) aka = 'flightplan'
		
		// originating city.
		String start = q.getOrigin();
		String dest = q.getDestination();
		
		//creates a valid comparator given the preferences of the current Query q
		Preferences queryPreferences = q.getPreferences();
		
		QueueComparator myComparator = new QueueComparator(queryPreferences.getPrefList());
		
		//Creates a new Priority Queue using the new comparator created above
		// Essentially the 'toVisit' list
		PriorityQueue<FlightPlan> b  = new PriorityQueue<FlightPlan>(myComparator);
		
		// 
		HashSet<FlightPlan> P = new HashSet<FlightPlan>();
		
		//HashMap<FlightPlan, Integer> b = new HashMap<FlightPlan, Integer>();
		
		HashMap<Flight, Integer> numShortestPaths = new HashMap<Flight, Integer>();
		
		

		
		int count = 0;
		
		
		System.out.println(q.getNumToDisplay());
		
		while (b.isEmpty() &&  count < q.getNumToDisplay()) {
			count++;
		}
		
		QueryAnswerPair queryAnswerPair = new QueryAnswerPair(q, null);
		
		return queryAnswerPair;
	}
	
	
	
}
