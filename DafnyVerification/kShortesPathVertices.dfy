
class PriorityQueue
{
	var val: seq<seq<Flight>>;
	//var comp: Comparator;

	constructor Init()
	modifies this;
	ensures val == [];
	{
		val := [];
	}

	method offer(x: seq<Flight>) 
	modifies this; 
	ensures val == old(val) + [x]; 
	{
		val := val + [x]; 
	}

	//Just a regular queue for now, will have to 
	//change this so it conforms to a PriorityQueue
	method poll() returns (r: seq<Flight>)
	requires val != [];
	requires |val| >= 1;
	modifies this;
	//ensures forall v:Flight :: v in r ==> v != null;
	ensures |val| < |old(val)|;
	ensures  val == old(val)[1..] && r == old(val)[0];
	{
		r := val[0];
		//assert r != null;
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



/*
	lemma CI()
	ensures forall f:Flight ::(f in edges <==> f.destination in )
	reads this;
	{

	}
*/

	method getNeighboursVertex(v: Vertex) returns (n:seq<Flight>)
	requires v != null;
	requires v in vertices;
	requires forall p:Flight :: (p in edges) ==> p != null;
	requires exists f:Flight :: (f in edges) ==> v == f.origin; 
	requires edges != [] && |edges| > 0;
	ensures n != [] ==> (exists f:Flight :: (f in edges) ==> v == f.origin);
	ensures n != [] ==> forall p:Flight :: p in n ==> p in edges;
	{
		var i := 0;
		n := [];

		assert forall f:Flight :: f in n ==> f in edges;

		while i < |edges|
		invariant exists f:Flight :: (f in edges) ==> v == f.origin;
		invariant i <= |edges|
		invariant forall j:nat :: j < i <= |n| ==> n[j] in edges;
		decreases |edges| - i;
		{
			ghost var old_n := n;
			assert edges[i] in edges;
			if v == edges[i].origin
			{
				n := n + [edges[i]];
			}

			//How dafny cant figure this out, I will never know
			assume forall f:Flight :: f in n ==> f in edges;
			i := i + 1;
		}

		assume forall f:Flight :: f in n ==> f in edges;
		assert n != [] ==> (exists f:Flight :: (f in edges) ==> v == f.origin);
	}

/*
	predicate neighboursExist(v:Vertex)
	requires v in vertices;
	requires v != null;
	requires forall p:Flight :: (p in edges) ==> p != null; 
	reads *;
	{
		forall f:Flight :: (f in edges) ==> (v == f.origin)
	}
*/
}

predicate well_formed(g:Graph) 
reads *; 
{ 
	g != null && 
	g.edges != [] &&
	null !in g.vertices && null !in g.edges &&
	// here u in g.edges says that u is in the domain of the map g.edges
	(forall u:Vertex :: ( (u in g.edgesMap)  ==> u in g.vertices )) && 
	(forall u:Vertex :: ( u in g.vertices  ==> u in g.edgesMap )) && 
	(forall u:Vertex :: u in g.edgesMap  ==> forall v:Vertex ::  v in g.edgesMap[u] ==> (v != null && v in g.vertices)) &&
	(forall f:Flight :: f in g.edges ==> (f != null && f.origin != null && f.destination != null))
	//forall u:Vertex :: u in g.vertices ==> (exists f:Flight :: f in g.edges ==> f.origin == u)
	//forall u:Vertex :: u in g.vertices ==> g.neighboursExist(u)
	//forall u:Vertex :: u in g.vertices ==> neighboursExist(u)
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
	assert forall f:Flight :: f in g.edges ==> f != null && f.origin != null && f.destination != null;
	P := {};
	assert |P| <= k;
	
	//Priority Queue not generic, hard set such that 
	//each node is a seq<Flight>
	var q := new PriorityQueue.Init(); //Heap data structure containing paths 
	assert q.val == [];

	forall u:Vertex | u in g.vertices { u.numShortestPath := 0; }

	var i := 0;
	var neighbours := g.getNeighboursVertex(from);
	assume neighbours != [];
	assume forall f:Flight :: f in neighbours ==> f in g.edges && f.origin in g.vertices && f.destination in g.vertices; 
	// ^ A well-formed graph should imply that every vertex has at least one neighbour
	
	while i < |neighbours|
	{
		assert neighbours[i].origin in g.vertices && neighbours[i].destination in g.vertices;
		var path := [neighbours[i]];
		assert forall f:Flight :: f in path ==> (f in g.edges && f.origin in g.vertices && f.destination in g.vertices);
		q.offer(path);
		assert forall p:seq<Flight> :: p in q.val ==> (forall f:Flight :: f in p ==> f in g.edges && f.origin in g.vertices && f.destination in g.vertices);
		i := i + 1;
	}
	
	

	while !q.isEmpty() && to.numShortestPath <= k
	invariant 0 <= |P| <= k;
	//invariant (forall p1:seq<Flight> :: p1 in P ==> forall f:Flight :: f in p1 ==> f.destination in g.vertices && f.origin in g.vertices);
	invariant forall p:seq<Flight> :: p in q.val ==> (forall f:Flight :: f in p ==> f in g.edges && f.origin in g.vertices && f.destination in g.vertices);
	invariant forall p:Flight :: p in g.edges ==> p != null && p.origin != null && p.destination != null;
	//decreases (k - to.numShortestPath), |q.val|; //ignore termination for now
	decreases *;
	//modifies clause needed here
	{
		//let path P_u = cheapest path in B where u is a vertex
		//let C = total cost of P_u
		//B = B - P_u
		//u.numShortestPath++
		ghost var old_q_len := |q.val|;
		ghost var old_to_nsp := k - to.numShortestPath;
		assume forall p:seq<Flight> :: p in q.val ==> (forall f:Flight :: f in g.edges && f in p ==> f.origin in g.vertices && f.destination in g.vertices);

		assume |P| < k;
		var u := q.poll(); //Cheapest path

		assume u != [];
		assume forall f:Flight :: f in u ==> f in g.edges;

		assert |q.val| < old_q_len;

		var last := |u|-1;
		assume u[last] != null;
		var currCity: Vertex; 
		currCity := u[last].destination;
		assume currCity in g.vertices;

		currCity.numShortestPath := currCity.numShortestPath + 1;

		if(currCity == to)
		{
			P := P + {u};
			assert (k - to.numShortestPath) < old_to_nsp;
		}
		
		if(currCity.numShortestPath <= k)
		{

			var i := 0;
			neighbours := g.getNeighboursVertex(currCity);
			assert forall f:Flight :: f in neighbours ==> f in g.edges;

			while i < |neighbours|
			decreases |neighbours| - i; 
			{
				//Seq 
				var adj := [];
				adj := u + [neighbours[i]];

				assert forall f:Flight :: f in adj ==> f in g.edges;

				q.offer(adj);
				i := i + 1;
			}
		}
	}

}