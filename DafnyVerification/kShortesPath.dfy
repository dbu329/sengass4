class Vertex
{ 
	var name: int;
	var edges_to: seq<Vertex>;
	var count: int; //The number of shortest paths to this Vertex
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
	
	/*
	constructor (f:Vertex, t:Vertex, c:int)
	requires c >= 0;
	modifies this;
	{
		cost := c;
		from := f;
		to := t;
	}
	*/
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

