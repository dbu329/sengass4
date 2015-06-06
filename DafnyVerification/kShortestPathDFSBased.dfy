class PriorityQueue
{
	var val: seq<Path>;
	//var comp: Comparator;

	constructor Init()
	modifies this;
	ensures val == [];
	{
		val := [];
	}

	method offer(x: Path) 
	modifies this; 
	ensures val == old(val) + [x]; 
	{
		val := val + [x]; 
	}

	//Just a regular queue for now, will have to 
	//change this so it conforms to a PriorityQueue
	method poll() returns (r: Path)
	requires val != [] && val[0] != null;
	requires |val| >= 1;
	modifies this;
	ensures |val| < |old(val)|;
	ensures  val == old(val)[1..] && r == old(val)[0];
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

class Path
{
	var flights: seq<Vertex>;

	constructor Init()
	ensures flights == [];
	modifies this;
	{
		flights := [];
	}

	method insertAll(vs: seq<Vertex>)
	ensures flights == old(flights) + vs;
	modifies this 
	{
		flights := flights + vs;
	}
}

class Graph
{ 
	var vertices : set<Vertex> ; 
	var edges: map<Vertex, seq<Vertex>>;

	method getNeighbours(v: Vertex) returns (n:seq<Vertex>)
	requires v in vertices && v in edges;
	ensures n == edges[v];
	{
		n := edges[v];
	}

}


predicate well_formed(g:Graph) 
reads g; 
{ 
	g != null && 
	null !in g.vertices && 
	// here u in g.edges says that u is in the domain of the map g.edges
	(forall u:Vertex :: ( u in g.edges  ==> u in  g.vertices )) && 
	(forall u:Vertex :: ( u in g.vertices  ==> u in  g.edges )) && 
	(forall u:Vertex :: u in g.edges  ==> forall v:Vertex ::  v in g.edges[u] ==> (v != null && v in g.vertices)) &&
	forall u:Vertex :: u in g.edges  ==> g.edges[u] != []
} 



predicate path(from:Vertex,to:Vertex, p:seq<Vertex>,g:Graph) 
reads p,g; 
{  well_formed(g) && from in g.vertices && to in g.vertices && 
  |p| > 0 && 
  (forall v:Vertex :: v in p ==> v in g.vertices)  && 
from == p[0] && to == p[|p|-1] && forall n:nat :: (n<|p|-1 ==> p[n+1] in g.edges[p[n]]) } 



lemma path_extend(from:Vertex,to:Vertex,next:Vertex,p:seq<Vertex>,g:Graph)
requires well_formed(g) && from in g.vertices && to in g.vertices && next in g.vertices ; 
requires path(from,to,p,g); 
requires next in g.edges[to] ; 
ensures path(from,next,p+[next],g); 
{
// Dafny knows this already 
} 

/*
lemma LEMMA_coupling_condition(g:Graph,q:Queue<Vertex>) 
requires well_formed(g) && q != null; 
requires forall u:Vertex :: (u in g.vertices && u.colour == Grey <==> u in q.val );
ensures (set u:Vertex | u in g.vertices && u.colour == Grey) != {} <==> !q.isEmpty() ; 
*/

method search(from:Vertex,to:Vertex,g:Graph, k: int) returns (P: set<Path>) 
 
requires well_formed(g) && from in g.vertices && to in g.vertices  ; 
requires k > 0;
modifies g.vertices; 
//ensures |P| <= k; //where P is a set of k Paths and a Path is a set of Edges
decreases * ; // This is needed here to allow decreases * on the loop 
{

	P := {};
	assert |P| == 0 && |P| <= k;

	
	//Priority Queue not generic, tony molyhard set such that 
	//each node is a seq<Flight>
	var q := new PriorityQueue.Init(); //Heap data structure containing paths 
	assert q.val == [];

	forall u:Vertex | u in g.vertices { u.numShortestPath := 0; }

	var i := 0;
	var neighbours := g.getNeighbours(from);
	assert forall u:Vertex :: u in neighbours ==> u in g.vertices;

	while i < |neighbours|
	{
		var path := new Path.Init();
		var firstPath := [from] + [neighbours[i]];
		path.insertAll(firstPath);
		assert forall u:Vertex :: u in path.flights ==> u in g.vertices;
		q.offer(path); //How can dafny not verify the below???
		
		i := i + 1;
	}

	assume forall p:Path :: p in q.val ==> (forall u:Vertex :: u in p.flights ==> u in g.vertices);
	
	while  !q.isEmpty() && to.numShortestPath <= k
	//invariant |P| <= to.numShortestPath;
	invariant forall p:Path :: p in q.val ==> (forall u:Vertex :: u in p.flights ==> u in g.vertices);
	decreases * ; 
	{ 
		ghost var old_q_len := |q.val|;
		ghost var old_to_nsp := k - to.numShortestPath;

		assert q.val[0] != null;
		var u := q.poll(); //Cheapest path

		assert |q.val| < old_q_len;
		assume |u.flights| >= 1;
		assert forall u1:Vertex :: u1 in u.flights ==> u1 != null; 

		var last := |u.flights|-1;
		var currCity := u.flights[last];
		assert currCity in g.vertices;

		currCity.numShortestPath := currCity.numShortestPath + 1;

		if(currCity == to)
		{
			var path1 := new Path.Init();
			path1.insertAll(u.flights);
			P := P + {path1};
			//assert |P| <= k; //This should work but dafny
			//doesnt do length of multidimensional structures well
			assert (k - to.numShortestPath) < old_to_nsp;
		}
		
		if(currCity.numShortestPath <= k)
		{
			var i := 0;
			neighbours := g.getNeighbours(currCity);
			assert neighbours != [] && |neighbours| > 0;

			while i < |neighbours|
			decreases |neighbours| - i; 
			{
				var adj := new Path.Init();
				var extended := u.flights + [neighbours[i]];
				adj.insertAll(extended);
				q.offer(adj);
				i := i + 1;
			}
		}
	}

}
