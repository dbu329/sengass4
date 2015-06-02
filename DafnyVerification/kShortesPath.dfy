/*class Comparator
{
	method compare(f1: FlightPlan, f2: FlightPlan)
	requires f1 != 
	{

	}
}*/


class PriorityQueue<T>
{
	var val: seq<T>;
	//var comp: Comparator;

	constructor Init()
	modifies this;
	ensures val == [];
	{
		val := [];
	}

	method offer(x:T) 
	modifies this; 
	ensures val == old(val) + [x]; 
	{
		val := val + [x]; 
	}

	//Just a regular queue for now, will have to 
	//change this so it conforms to a PriorityQueue
	method poll() returns (r: T)
	requires val != [];
	ensures |val| < |old(val)|
	ensures  val == old(val)[1..] && r == old(val)[0]
	modifies this;
	{
		r := val[0];
		val := val[1..];
	}

	function method isEmpty() :bool 
	reads this; 
	{
		val == []   
	}
}

class Vertex
{ 
	var name: int;
	var edges_to: seq<Vertex>;
	var numShortestPath: int; //The number of shortest paths to this Vertex
	var path_from_root : seq<Edge>; //Needed for K-shortest algorithm 

	/*
	constructor (n:int)
	modifies this;
	ensures forall v:Vertex :: v in  edges_to ==> v != null ; 
		{ name := n; 
		  edges_to := []; 
		  colour := White ;  
			  path_from_root := []; 
		} 
	*/ 
}

class Edge
{
	var cost: int;
	var from: Vertex;
	var to: Vertex;
	
	
	constructor (f:Vertex, t:Vertex, c:int)
	requires c >= 0;
	modifies this;
	{
		cost := c;
		from := f;
		to := t;
	}
	
}

class FlightPlan
{
	var list: seq<Edge>;
	var totalCost: int;
	var totalTime: int;
	var totalAirline: int;

	constructor ()
	modifies this;
	ensures list == []
	{
		list := [];
	}
}

class Graph
{
	var vertices: set<Vertex>;
	var edges: set<Edge>;
}

// we use a function method here rather than predicate, since Dafny 
// does not like predicate in one of the contexts of usage below. 
function method closed(g:Graph):bool
reads g; 
{ null !in g && forall v:Vertex :: v in g.vertices ==> (forall i:nat :: i< |v.edges_to| ==> v.edges_to[i] in g.vertices) }  





function method path(from:Vertex,to:Vertex, p:seq<Vertex>,g:set<Vertex>) :bool
reads p,g; 
//requires forall v:Vertex :: v in g ==> v != null ;
//requires closed(g) && from in g && to in g ; 
{  closed(g) && from in g && to in g && 
  |p| > 0 && 
  (forall v:Vertex :: v in p ==> v in g)  && 
from == p[0] && to == p[|p|-1] && forall n:nat :: (n<|p|-1 ==> p[n+1] in p[n].edges_to) } 

lemma path_extend(from:Vertex,to:Vertex,next:Vertex,p:seq<Vertex>,g:set<Vertex>)
requires closed(g) && from in g && to in g && next in g ; 
requires path(from,to,p,g); 
requires next in to.edges_to ; 
ensures path(from,next,p+[next],g); 
{
// Dafny knows this already 
} 




method search(from:Vertex, to:Vertex, g:Graph, k: int) returns (P: set<set<Edge>>) 
requires from in g && to in g && closed(g); 
requires k > 0;
modifies set v:Vertex | v in g; 
ensures |P| == k; //where P is a set of k Paths and a Path is a set of Edges

decreases * ; // This is needed here to allow decreases * on the loop 
{
	var P :seq<FlightPlan> //Set of shortest paths
	P := [];
	var B := new PriorityQueue<FlightPlan>.Init(); //Heap data structure containing paths 
	forall u:Vertex | u in g{ u.numShortestPath := 0; }
	from.path_from_root := new Edge.Init(from, from, 0); 
	B.offer(from.path)

	while !B.isEmpty() && to.numShortestPath < k
	decreases *;//(k - to.numShortestPath), B//ignore termination for now
	//invariant here
	//modifies clause needed here
	{
		//let path P_u = cheapest path in B where u is a vertex
		//let C = total cost of P_u
		//B = B - P_u
		//u.numShortestPath++

		var u := B.poll(); //Cheapest path
		var c := u.totalCost;
		u.numShortestPath := u.numShortestPath + 1;

		if(u == to)
		{
			P := P + [u];
		}
		
		if(u.numShortestPath <= k)
		{
			forall v:Vertex | v in u.adjacent 
			{
				P_v = P_u + [v];
				B.offer(P_v);
			}
		}
	}
}

