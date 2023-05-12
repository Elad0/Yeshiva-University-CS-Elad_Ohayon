package edu.yu.da;

/** Defines the API for specifying and solving the OverfullGranaries problem
 * (see the requirements document).
 *
 * Students MAY NOT change the public API of this class, nor may they add ANY
 * constructor.

 * @author Avraham Leff
 */


import java.util.*;

public class OverfullGranaries {
	class Edge implements Comparable<Edge>{
		
		protected final int v; // one vertex
		protected final int w; // the other vertex
		private  double capacity; // edge weight
		public double flow=0;
		public Edge residual=null;
		
	 public Edge(int v, int w, double capacity){
		 this.v = v;
		 this.w = w;
		 this.capacity = capacity;
	 }
	 
	 public double capacity() {
		 return capacity; }
	 
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
		 if (this.capacity() < that.capacity()) {
			 return -1;
		 }
		 else if (this.capacity() > that.capacity()) {
			 return +1;
		 }
		 else {
			 return 0;
		 }
	 }
	 
	 private double remainingFlow() {

		 return this.capacity-this.flow;
	 }
	 
	 public void addFlow(double delta) {
		  this.flow+=delta;
		  this.residual.capacity=this.capacity;
		  this.residual.flow-=delta;
		 
	}
	 
	
}
	
	public class ResidualGraph{
		
			protected int V; // number of vertices
			protected int E; // number of edges
			private List<Edge>[] adj; // adjacency lists
			
			public ResidualGraph(int V){
			 this.V = V;
			 this.E = 0;
			 adj = (List<Edge>[]) new ArrayList[V+1];
			// residualEdges = (List<Edge>[]) new ArrayList[V+1];
			 
		 for (int v = 0; v < V; v++) {
			 adj[v] = new ArrayList<Edge>();
		//	 residualEdges[v] = new ArrayList<Edge>();
		 }
		 }
		 
	
		 public void addEdge(Edge e){
			 int v = e.v, w = e.w;
			e.residual= new Edge(w, v, e.capacity);
			e.residual.flow=e.capacity;
			if(adj[e.w]==null) {
				adj[e.w]=new ArrayList<Edge>();
			}
		 	adj[e.w].add(e);
		 	E++;
		 }
		 

		 
		 public List<Edge> adj(int v)
		 { 
			 return adj[v];
		 }
		 
		 public void doubleArray() {
			 this.V=this.V*2;
			 List<Edge>[] newArr= (List<Edge>[]) new ArrayList[V+1];
			 
			 for(int i=0; i<this.adj.length; i++) {
				 newArr[i]=this.adj[i];
			 }
			 this.adj=newArr;
		 }
	
	}
	
	class EdmondKarp {
		private int source;
		private int sink;
		private ResidualGraph graf;
		private Edge[] edgeTo;
		private boolean[] visited;

		public EdmondKarp(ResidualGraph g, int source, int sink) {
			this.sink = sink;
			this.source = source;
			this.graf = g;
			this.edgeTo = new Edge[graf.V];
			this.visited = new boolean[graf.V];
		}

		private double maxFlow() {
			double maxFlow = 0;

			while (hasAugmentingPath()) {
				double bottle = Double.POSITIVE_INFINITY;

				// find the bottleneck capacity
				for (int v = sink; v != source; v = edgeTo[v].other(v)) {
					bottle = Math.min(bottle, edgeTo[v].remainingFlow());
				}

				// augment flow
				for (int v = sink; v != source; v = edgeTo[v].other(v)) {
					edgeTo[v].addFlow(bottle);
				}

				// add flow to max flow
				maxFlow += bottle;
			}

			return maxFlow;
		}

		private boolean hasAugmentingPath() {
			Arrays.fill(visited, false);
			Queue<Integer> q = new LinkedList<Integer>();
			q.offer(source);
			visited[source] = true;
			while (!q.isEmpty()) {
				int v = q.poll();
				
				if(this.graf.adj(v)==null) {
					continue;
				}
				for (Edge e : graf.adj(v)) {
					int w = e.other(v);
					if (!visited[w] && e.remainingFlow() > 0) {
						visited[w] = true;
						edgeTo[w] = e;
						q.offer(w);
					}
				}
			}
			return visited[sink];
		}
	}
	
  /** Represents the 10_000 bushels of grain that must be moved from the
   * overfull granaries to the underfull granaries
   */
  public final static double BUSHELS_TO_MOVE = 10_000;
  
  private final int additionalSourceVertex;
  private final int additionalSinkVertex;
  private String[] underFull;
  private String[] overFull;
  private int count=2;
  private Set<String> underFullSet= new HashSet<>();
  private Set<String> overFullSet= new HashSet<>();
  
  private boolean solveItInvoked=false;
  
  private Map<String, Integer> mapOutGranaries= new HashMap<>();
  private Map<Integer, String> mapVertexNumberToString= new HashMap<>();
  
  public ResidualGraph graph=null;

/** Constructor.
   *
   * @param X labelling of the overfull granaries, must contain at least one
   * element and no duplicates.  No element of X can be an element of Y.
   * @param Y labelling of the underfull granaries, must contain at least one
   * element and no duplicates.  No element of Y can be an element of X.
   */
  public OverfullGranaries(final String[] X, final String[] Y) {
    if(X.length<1 || Y.length<1 ) {
    	throw new IllegalArgumentException();
    }
    this.overFull=X;
    this.underFull=Y;
  
   // this.graph= new SedgewickEdgeWeightedGraph(X.length>Y.length? 2*X.length: 2*Y.length);
    
    this.graph= new ResidualGraph(X.length+Y.length+2);
    additionalSourceVertex=0;
    additionalSinkVertex=1;
    
    
    for(String str: this.overFull) {
    	this.overFullSet.add(str);
    	mapOutGranaries.put(str, count);
    	this.mapVertexNumberToString.put(count, str);
    	count++;
    }
    
    for(String str: this.underFull) {
    	if(this.overFullSet.contains(str)) {
    		throw new IllegalArgumentException();
    	}
    	this.underFullSet.add(str);
    	mapOutGranaries.put(str, count);
    	this.mapVertexNumberToString.put(count, str);
    	count++;
    }
    if(count>= this.graph.V*.75) {
    	this.graph.doubleArray();
    }
    
  }

  /** Specifies that an edge exists from the specified src to the specified
   * dest of specified capacity.  It is legal to invoke edgeExists between
   * nodes in X, between nodes in Y, from a node in X to a node in Y, or for
   * src and dest to be hitherto unknown nodes.  The method cannot specify a
   * node in Y to be the src, nor can it specify a node in X to be the dest.
   *
   * @param src must contain at least one character
   * @param dest must contain at least one character, can't equal src
   * @param capacity must be greater than 0, and is specified implicitly to be
   * "bushels per hour"
   */
  public void edgeExists(final String src, final String dest, final int capacity)
  {
    if(src.isBlank() || src.isEmpty() || dest.isBlank() || dest.isEmpty() || capacity<=0 || src.equals(dest)) {
    	throw new IllegalArgumentException();
    }
    
    if(this.overFullSet.contains(dest) && this.underFullSet.contains(src)) {
    	throw new IllegalArgumentException();
    }
    int srcMappedOut=this.mapOutGranaries.getOrDefault(src,-1);
    int destMappedOut=this.mapOutGranaries.getOrDefault(dest,-1);
    
    if(srcMappedOut==-1) {
    	this.mapOutGranaries.put(src, count);
    	this.mapVertexNumberToString.put(count, src);
    	srcMappedOut=count;
    	count++;
    	
    }
    
    if(destMappedOut==-1) {
    	this.mapOutGranaries.put(dest, count);
    	this.mapVertexNumberToString.put(count, dest);
    	destMappedOut=count;
    	count++;
    }
    
    if(count>= this.graph.V*.75) {
    	this.graph.doubleArray();
    }
    
//    System.out.println("src= "+ src+ " dest= "+ dest);
//    
//    System.out.println("destMappedOut= "+ destMappedOut);
//    System.out.println("srcMappedOut= "+ srcMappedOut);
    Edge e= new Edge(destMappedOut, srcMappedOut, capacity);
    this.graph.addEdge(e);

    
    
  }

  /** Solves the OverfullGranaries problem.
   *
   * @return the minimum number hours neeed to achieve the goal of moving
   * BUSHELS_TO_MOVE number of bushels from the X granaries to the Y granaries
   * along the specified road map.
   * @note clients may only invoke this method after all relevant edgeExists
   * calls have been successfully invoked.
   */
  public double solveIt() {
	  Integer source= this.additionalSourceVertex;
	  Integer sink=this.additionalSinkVertex;
	 
	  
	 //Attach dummy verticies
	  
	  //add dummy source and connect all verticies in X to it
	  attachDummySourceVertex();
	  
	  //add dummy sink vertex and connect all verticies in Y to it
	  attachDummySinkVertex();
	  
	  
	  EdmondKarp fordCar= new EdmondKarp(this.graph,source, sink);
	  
	  this.solveItInvoked=true;
	  return BUSHELS_TO_MOVE/fordCar.maxFlow();
  }

  /** Return the names of all vertices in the X side of the min-cut, sorted by
   * ascending lexicographical order.
   *
   * @return only the names of the vertices in the X side of the min-cut
   * @note clients may only invoke this method after solveIt has been
   * successfully invoked.  Else throw an ISE.
   */
  public List<String> minCut() {
	  
	  SortedSet<String> toReturn= new TreeSet<>((s1, s2)->s1.compareTo(s2));
	  
	  if(!solveItInvoked) {
		  throw new IllegalStateException();
	  }
	  
	  //TO determine mincut, run bfs and see all nodes that are reachable from source. Note that reachable means the capacity isnt full
	  
	  Queue<Integer> bfsQueue= new LinkedList<>();
	  Set<Integer> added= new HashSet<>();
	  bfsQueue.offer(this.additionalSourceVertex);
	  
	  while(!bfsQueue.isEmpty()) {
		  int vertex=bfsQueue.poll();
		  for(Edge e: this.graph.adj(vertex) ) {
			  
			  int v=e.v;
			  if(e.remainingFlow()>0 ) {
				  if(!added.contains(v)) {
					  bfsQueue.offer(v); 
				  }
				 
				  added.add(v);
				  
				  toReturn.add(this.mapVertexNumberToString.get(v));
			  }
		  }
		  
	  }
	  
   return new ArrayList<>(toReturn);
  }
  
  
  /**
   * attach all verticies in x to a dummy source
   * @param source
   * @param destination
   */
  private void attachDummySourceVertex() {
	  for(String vertex: this.overFull) {
		  this.graph.addEdge(new Edge(this.mapOutGranaries.get(vertex),this.additionalSourceVertex, Double.POSITIVE_INFINITY));
	  }
  }
  
  private void attachDummySinkVertex() {
	  for(String vertex: this.underFull) {
		  this.graph.addEdge(new Edge(this.additionalSinkVertex,this.mapOutGranaries.get(vertex), Double.POSITIVE_INFINITY));
	  }
  }
  
  

} // OverfullGranaries