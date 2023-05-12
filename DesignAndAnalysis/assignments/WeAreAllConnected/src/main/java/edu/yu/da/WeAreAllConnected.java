package edu.yu.da;


import java.util.Arrays;
import java.util.List;

import edu.yu.da.WeAreAllConnectedBase.SegmentBase;
import edu.yu.da.WeAreAllConnectedBase;



public class WeAreAllConnected extends WeAreAllConnectedBase {
	public static class Segment extends SegmentBase{
		 int x=0;
		 int y=0;
		int duration=0;
		
		public Segment(int x, int y, int duration) {
			super(x, y, duration);
			
			if(x==y || duration<0) {
				throw new IllegalArgumentException();
			}
			
			this.x=x;
			this.y=y;
			this.duration=duration;
		}


		@Override
		public boolean equals(Object other) {
			if (!(other instanceof SegmentBase)) {
				
				return false;
			}
			SegmentBase sg= (SegmentBase)other;
			
			if(sg.x==this.x && sg.y==this.y && this.duration==sg.duration) {
				return true;
			}
			if(sg.y==this.x && sg.x==this.y && this.duration == sg.duration) {
				return true;
			}
			
			return false;
			
		}

		
			
	}
	
	class WarshallSolution{
		class disTo{
			private int disTo[][];
			private long weight=0;
			private int duplicate[][];
			
			public disTo(List<SegmentBase> current, int n) {
				int defualtDist = 1000000000;
				int[][] dist = new int[n][n];
				this.duplicate= new int[n][n];
				
				for (int i = 0; i < n; i++) {
					Arrays.fill(dist[i], defualtDist);
					dist[i][i] = 0;
				}
				        

				for (SegmentBase e : current) {
					int u=e.x;
					int v=e.y;
					dist[u][v] = e.duration;
					dist[v][u] = e.duration; 
				}

			    for (int k = 0; k < n; k++) {
			    	for (int i = 0; i < n; i++) {
			    		for (int j = 0; j < n; j++) {
			    			dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
			    	
			    		}
			    	}
			    }
			    
			    this.disTo=dist;
			    
			    //Calculate weight and build duplicate array for later use
			    long weight=0;
			    for(int i=0; i<this.disTo.length; i++) {
			    	for(int j=0; j<this.disTo[i].length; j++) {
			    		weight+=disTo[i][j];
			    		this.duplicate[i][j]=disTo[i][j];
			    	}
			    }
			   
			    this.weight=weight;
			}

			public long totalDuration() {
				return this.weight;
			}


		}


		
		private int n=0;
		private List<SegmentBase> current;
		private List<SegmentBase> possibilities;
		private disTo distance;
		
		public WarshallSolution(int n, List<SegmentBase> current, List<SegmentBase> possibilities) {
			this.n=n;
			this.current=current;
			this.possibilities=possibilities;
			this.distance=new disTo(current, n);
		}
		
		public long weight() {
			return this.distance.totalDuration();
		}
		
		
		
		
	}
	
	public WeAreAllConnected() {
		super();
		
	}

	
	
	
	/** Given a list of the current communication system's segments and a list of
 	* possible segments that can be added to the current system, select exactly
 	* one segment from the set of possibilities to be added to the current
 	* communication system.  You may assume that all segments supplied by the
 	* client satisfy Segment semantics.
 	*
 	* @param n the ids of all cities referenced by SegmentBase instances lie in
 	* the range 0..n-1 (inclusive).
 	* @param current the current communication system defined as a list of
 	* segments.  The client maintains ownership of this parameter.
 	* @param possibilities one possible segment will be selected from this list
 	* to be added to the current communication system.  The client maintains
 	* ownership of this parameter.
	* @return the segment from the set of possibilities that provides the best
 	* improvement in the total duration of the current system.  Total duration
 	* is defined as the sum of the durations between all pairs of cities x and y
 	* in the current system.  If more than one segment qualifies, return any of
 	* those possibilities.
 	*/
	@Override
	public SegmentBase findBest(int n, List<SegmentBase> current, List<SegmentBase> possibilities){
		WarshallSolution ws= new WarshallSolution(n, current, possibilities);
		int distTo[][]= ws.distance.disTo;
		int original[][]= ws.distance.duplicate;
		
	     long currWeight=ws.weight();
	     SegmentBase toAdd=null;
	     
	        for(SegmentBase addition: possibilities) {
	        	
	        	long newWeight=0;
	        	int v=addition.x;
	        	int w=addition.y;
	        	
	        	if(distTo[addition.x][addition.y]<addition.duration) {
	        		continue;
	        	}
	        	if(distTo[v][w]==1000000000) {
	        		//This case shouldnt happen
	        		return addition;
	        	}
	        	else {
	
	        		for(int i=0; i<distTo.length; i++) {
	        			for(int j=0; j<distTo[i].length; j++) {

	        				if(i==j) {
	        					continue;
	        				}
	        				
	        				int update1=0;
	        				int update2=0;
	        				if(distTo[i][v]!=1000000000 && distTo[j][w]!=1000000000) {
	        					update1=distTo[i][v]+addition.duration+distTo[j][w];
	        					
	        					if(update1<distTo[i][j]) {
	        						
	        						distTo[i][j]=update1;
	        						distTo[j][i]=update1;
	        						
	        						
	        					}
	        					
	        				}
	        				
	        				else if(distTo[i][w]!=1000000000 && distTo[j][v]!=1000000000) {
	        					update2=distTo[i][w]+addition.duration+distTo[j][v];
	        					
	        					if(update2<distTo[i][j]) {
	        						distTo[i][j]=update2;
	        						distTo[j][i]=update2;
	        						
	        					}
	        					
	        					
	        				}
	        				}
	        		}	
	        		
	        	}
	        	
	        //	newWeight=calWeight(distTo, current, addition);
	        	
	        	for(int i=0; i<distTo.length; i++) {
	        		for(int j=0; j<distTo[i].length; j++) {
	        		newWeight+=distTo[i][j];
	        		distTo[i][j]=original[i][j];
	        		}
	        	}
	        
	        	
	        	
	        	if(newWeight<currWeight) {
	        		currWeight=newWeight;
	        		toAdd=addition;
	        		
	        	}
	    
	        }

		return toAdd;

	}






	
}
