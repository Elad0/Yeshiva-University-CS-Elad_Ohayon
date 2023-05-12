package edu.yu.da;

/** Defines the API for specifying and solving the FindMinyan problem (see the
 * requirements document).  Also defines an inner interface, and uses it as
 * part of the ArithmeticPuzzleI API definition.
 *
 * Students MAY NOT change the public API of this class, nor may they add ANY
 * constructor.
 *
 * @author Avraham Leff
 */

import java.util.*;

public class FindMinyan {
	

	class Edge implements Comparable<Edge>{
		protected final int v; // one vertex
		protected final int w; // the other vertex
		private final double weight; // edge weight
	 
	 public Edge(int v, int w, double weight){
		 this.v = v;
		 this.w = w;
		 this.weight = weight;
	 }
	 
	 public double weight() {
		 return weight; }
	 
	 public int either(){ 
		 return v; 
	 }
	 
public int other(int vertex){
	 	if (vertex == v) {
	 		return w;
	 	}
	 	else if (vertex == w) {
	 		return v;
	 	}
	 	else {
	 		throw new RuntimeException("Inconsistent edge");
	 	}
	 }

	 public int compareTo(Edge that){
		 if (this.weight() < that.weight()) {
			 return -1;
		 }
		 else if (this.weight() > that.weight()) {
			 return +1;
		 }
		 else {
			 return 0;
		 }
	 }
	
}
	
	class SedgewickEdgeWeightedGraph{
		
			protected final int V; // number of vertices
			protected int E; // number of edges
			private List<Edge>[] adj; // adjacency lists
			
		 public SedgewickEdgeWeightedGraph(int V){
			 this.V = V;
			 this.E = 0;
			 adj = (List<Edge>[]) new ArrayList[V+1];
			 
		 for (int v = 0; v < V; v++) {
			 adj[v] = new ArrayList<Edge>();
		 }
		 }
		 
	
		 public void addEdge(Edge e){
			 int v = e.either(), w = e.other(v);
			 
			 List<Edge> compare=null;
			 if(adj[v].size()<adj[w].size()) {
				 compare=adj[v];
			 }
			 else {
				 compare=adj[w];
			 }
			 
			 for(Edge z: compare) {
				 if((z.w==e.w && z.v==e.v) || (z.w==e.v && z.v==e.w)) {
					 throw new IllegalArgumentException();
				 }
			 }
			 
		 	adj[v].add(e);
		 	adj[w].add(new Edge(w,v,e.weight()));
		 	E++;
		 }
		 public List<Edge> adj(int v)
		 { 
			 return adj[v];
		 }
		 
	}
	
	class BFSUnit{
		protected Edge e;	//Edge this points to
		protected int[] marked;	//array to track already marked to avoid cycles
		protected boolean hitMinyan;
		protected double accumulatedWeight;
		
		public BFSUnit(Edge e, int v, boolean pathHasMinyan, double accumulatedWeight, int[] prevMarked) {	
			this.e=e;
			this.marked=prevMarked;
			this.marked[e.v]++;
			if(this.marked[e.w]==0) {
				this.marked[e.v]--;
			}
			this.marked[e.w]++;
			
			this.hitMinyan=pathHasMinyan;
			this.accumulatedWeight=accumulatedWeight+ e.weight;
	
			
		}
	}
	
	class minRoute{
			 private  Map<Integer,Integer> destinationReached;
			 private int minRoute;
			 public minRoute() {
				 this.destinationReached= new HashMap();
				 this.minRoute=Integer.MAX_VALUE;
			 }
			 public void addRoute(BFSUnit u) {
				if(this.destinationReached.containsKey((int)u.accumulatedWeight)) {
					this.destinationReached.replace((int) u.accumulatedWeight, this.destinationReached.get((int)u.accumulatedWeight)+1);
				}
				else {
					this.destinationReached.put((int)u.accumulatedWeight, 1);
				}
				
				 this.minRoute=Math.min(minRoute, (int)u.accumulatedWeight);
			 }
			 
			 
			 public int countReached() {
				 if(shortestDist()==Integer.MAX_VALUE) {
					return 0;
				 }
				 
				 return this.destinationReached.get(this.minRoute);
			 }
			 
			 public int shortestDist() {
				 return this.minRoute;
			 }
		 }
	  
	
private int cityCount;
private Set<Integer> hasMinyan;
private SedgewickEdgeWeightedGraph highways;
private int numSolutions=0;
private int lowSolution=0;
private boolean solveItCalled;

/** Constructor: clients specify the number of cities involved in the
   * problem.  Cities are numbered 1..n, and for convenience, the "start" city
   * is labelled as "1", and the goal city is labelled as "n".
   *
   * @param nCities number of cities, must be greater than 1.
   */
  public FindMinyan(final int nCities) {
	  this.cityCount=nCities;
	  
	  if(this.cityCount<=1) {
		  throw new IllegalArgumentException();
	  }
	  this.hasMinyan= new HashSet();
	  
	  this.highways= new SedgewickEdgeWeightedGraph(this.cityCount+1);
	  this.solveItCalled=false;
  }

  /** Defines a highway leading (bi-directionally) between two cities, of
   * specified duration.
   *
   * @param city1 identifies a 1 <= city <= n, must differ from city2
   * @param city2 identifies a 1 <= city <= n, must differ from city1
   * @param duration the bi-directional duration of a trip between the two
   * cities on this highway, must be non-negative
   */
  public void addHighway(final int city1, final int duration, final int city2) {
	  if(city1<1 || city1==city2 || city1>this.cityCount) {
		  throw new IllegalArgumentException();
	  }
	  
	  if(duration<0) {
		  throw new IllegalArgumentException();
	  }
	  
	  if(city2<1 || city1==city2 || city2>this.cityCount) {
		  throw new IllegalArgumentException();
	  }
	  
	  this.highways.addEdge(new Edge(city1, city2, duration));
	  
    // fill me in!
  }

  /** Specifies that a minyan can be found in the specified city.
   *
   * @param city identifies a 1 <= city <= n
   */
  public void hasMinyan(final int city) {
	  if(city<1 || city>this.cityCount) {
		  throw new IllegalArgumentException();
	  }
	  
	  this.hasMinyan.add(city);
	  
    // fill me in!
  }

  /** Find a solution to the FindMinyan problem based on state specified by the
   * constructor, addHighway(), and hasMinyan() API.  Clients access the
   * solution through the shortestDuration() and nShortestDurationTrips() APIs.
   */
  public void solveIt() {
	
	  
	  minRoute route= new minRoute();
	  
	  Queue<BFSUnit> bfs= new LinkedList<BFSUnit>();
	  
	 for(Edge e: this.highways.adj(1)) {
		 int toadd[] = new int[this.cityCount+1];
		 bfs.add(new BFSUnit(e, this.cityCount, this.hasMinyan.contains(e.w), 0, toadd));
		 toadd[1]++;
	 }
	 
	 while(!bfs.isEmpty()) {
		 BFSUnit barry= bfs.remove();
		 
		 for(Edge e: this.highways.adj(barry.e.w)) {
			if((barry.marked[e.v]<2|| barry.marked[e.w]<2) && barry.accumulatedWeight<route.shortestDist()) { //TODO try &&
				
				bfs.add(new BFSUnit(e, this.cityCount, this.hasMinyan.contains(e.w)||barry.hitMinyan, barry.accumulatedWeight, Arrays.copyOf(barry.marked, barry.marked.length)));
				

			}
		 }
		 if(barry.hitMinyan && barry.e.w==this.cityCount) {
			 
		
		
			 route.addRoute(barry);
		 }
	 }
	 this.lowSolution=route.shortestDist();
	 this.numSolutions=route.countReached();
	 }
	
	  
  
  
  /** Returns the duration of the shortest trip satisfying the FindMinyan
   * constraints.  
   *
   * @return duration of the shortest trip, undefined if client hasn't
   * previously invoked solveIt().
   */
  public int shortestDuration() {
	 if(this.lowSolution==Integer.MAX_VALUE && this.solveItCalled==false) {
		 return 0;
	 }
    return this.lowSolution;
  }

  /** Returns the number of distinct trips that satisfy the FindMinyan
   * constraints.
   * 
   * @return number of shortest duration trips, undefined if client hasn't
   * previously invoked solveIt()..
   */
  public int numberOfShortestTrips() {
	  if(this.numSolutions==Integer.MAX_VALUE && this.solveItCalled==false) {
			 return 0;
		 }
	 
    return this.numSolutions;
  }

  

  
} // FindMinyan