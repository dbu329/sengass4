
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
	var numShortestPath: nat;

	function method equals(v1: Vertex) :bool
	requires v1 != null;
	reads *;
	{
		v1.name == this.name
	}
}


class Flight
{
	var origin: Vertex;
	var destination: Vertex;
	var cost: int;

	constructor Init(o: Vertex, d: Vertex, c: int)
	requires o != null && d !=null;
	requires cost >= 0;
	modifies this;
	{
		origin := o;
		destination := d;
		cost := c;
	}
}

class Graph
{ 
	var vertices : set<Vertex> ; 
	ghost var edgesMap: map<Vertex, set<Vertex>>;
	var edges: seq<Flight>;


	/*predicate noNull()
	reads *;
	{
		(forall u:Vertex :: u in vertices ==> u != null) &&
		(forall p:Flight :: p in edges ==> p != null && p.origin != null && p.destination != null)
	}*/

	method getNeighbours(f: Flight) returns (n:seq<Flight>)
	//requires noNull();
	requires f != null && f.destination != null && f.origin != null;
	requires f.destination in vertices;
	requires f in edges;
	requires forall p:Flight :: p in edges && p != null
	//ensures forall p:Flight :: p in n ==> f.destination == p.origin;
	{
		var i := 0;
		n := [];
		while i < |edges|
		invariant i <= |edges|
		invariant forall j:nat :: j < i ==> edges[j] != null;
		{
			if f.destination == edges[i].origin
			{
				n := n + [edges[i]];
			}

			i := i + 1;
		}
	}

	method getNeighboursVertex(v: Vertex) returns (n:seq<Flight>)
	//requires noNull();
	requires v != null;
	requires v in vertices;
	requires v in edgesMap;
	requires forall p:Flight :: (p in edges) ==> p != null;
	//ensures forall p:Flight :: p in n ==> v == p.origin;
	{
		var i := 0;
		n := [];
		while i < |edges|
		invariant i <= |edges|
		//invariant forall j:nat :: j < i ==> n[j] != null && n[j].origin != null;
		decreases |edges| - i;
		{
			if v == edges[i].origin
			{
				n := n + [edges[i]];
			}

			i := i + 1;
		}
	}

}

predicate well_formed(g:Graph) 
reads *; 
{ 
	g != null && 
	null !in g.vertices && 
	// here u in g.edges says that u is in the domain of the map g.edges
	(forall u:Vertex :: ( (u in g.edgesMap)  ==> u in g.vertices )) && 
	(forall u:Vertex :: ( u in g.vertices  ==> u in g.edgesMap )) && 
	(forall u:Vertex :: u in g.edgesMap  ==> forall v:Vertex ::  v in g.edgesMap[u] ==> (v != null && v in g.vertices) )&&
	 forall f:Flight :: f in g.edges ==> (f != null && f.origin != null && f.destination != null)
}



predicate path(from:Vertex,to:Vertex, p:seq<Vertex>,g:Graph) 
reads *; 
{  
	well_formed(g) && from in g.vertices && to in g.vertices && 
	|p| > 0 && 
	(forall v:Vertex :: v in p ==> v in g.vertices)  && 
	from == p[0] && to == p[|p|-1] && forall n:nat :: (n<|p|-1 ==> p[n+1] in g.edgesMap[p[n]]) 
}

lemma path_extend(from:Vertex,to:Vertex,next:Vertex,p:seq<Vertex>,g:Graph)
requires well_formed(g) && from in g.vertices && to in g.vertices && next in g.vertices ; 
requires path(from,to,p,g); 
requires next in g.edgesMap[to] ; 
ensures path(from,next,p+[next],g); 
{
// Dafny knows this already 
}




method search(from:Vertex, to:Vertex, g:Graph, k: int) returns (P: set<seq<Flight>>) 
requires well_formed(g) && from in g.vertices && to in g.vertices; 
requires k > 0;
modifies set v:Vertex | v in g.vertices; 
ensures |P| <= k; //where P is a set of k Paths and a Path is a set of Edges

decreases * ; // This is needed here to allow decreases * on the loop 
{
	//var fake := new Flight.Init(from, from, 0);

	//var P :seq<FlightPlan>; //Set of shortest paths
	P := {};
	var q := new PriorityQueue<seq<Flight>>.Init(); //Heap data structure containing paths 
	forall u:Vertex | u in g.vertices { u.numShortestPath := 0; }
	
	var i := 0;
	assert from in g.vertices;
	assert forall p:Flight :: p in g.edges ==> p != null && p.origin != null && p.destination != null;
	var neighbours := g.getNeighboursVertex(from);
	while i < |neighbours|
	{
		//var path := new Path.Init();
		//path.addFlight(neighbours[i]);
		var path := [neighbours[i]];
		q.offer(path);
		i := i + 1;
	}
	/*
	//q.offer(from.getPaths);

	while !B.isEmpty() && to.numShortestPath < k
	decreases *;//(k - to.numShortestPath), B//ignore termination for now
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

			var i := 0;
			while i < |u.adjacent|
			decreases |u.adjacent| - i; 
			{
				var v := u.adjacent[i];
				var adj := new Path.Init(u.getFlights());
				i := i + 1;
			}
		}

	}
*/
}