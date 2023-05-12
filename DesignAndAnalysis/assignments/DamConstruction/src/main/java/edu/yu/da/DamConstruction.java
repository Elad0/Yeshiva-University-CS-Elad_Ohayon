package edu.yu.da;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;

/** Defines the API for specifying and solving the DamConstruction problem (see
 * the requirements document).
 *
 * Students MAY NOT change the public API of this class, nor may they add ANY
 * constructor.
 *
 * @author Avraham Leff
 */

public class DamConstruction {
	private int riverEnd=0;
	private int[] damLocations;
	/** Constructor
   *
   * @param Y y-positions specifying dam locations, sorted by ascending
   * y-values.  Client maintains ownership of this parameter.  Y must contain
   * at least one element.
   * @param riverEnd the y-position of the river's end (a dam was previously
   * constructed both at this position and at position 0 and no evaluation will be
   * made of their construction cost): all values in Y are both greater than 0
   * and less than riverEnd.
   * @note students need not verify correctness of either parameter.  On the
   * other hand, for your own sake, I suggest that you add these (easy to do)
   * "sanity checks".
   */
  public DamConstruction(final int Y[], final int riverEnd) {
	  this.riverEnd=riverEnd;
	  this.damLocations=Y;
	  
    if( this.damLocations.length<1 || this.damLocations[0]<=0 || this.damLocations[this.damLocations.length-1]>=riverEnd) {
    	throw new IllegalArgumentException();
    }
  
    
    
  } // constructor

  /** Solves the DamConstruction problem, returning the minimum possible cost
   * of evaluating the environmental impact of dam construction over all
   * possible construction sequences.
   *
   * @return the minimum possible evaluation cost.
   */
  
  
  //TODO -> write tests and then optimize using treeset
  public int solve() {
	  List<Integer> dams = new ArrayList<Integer>();
	  dams.add(0);
	  for(int dam: this.damLocations) {
		  dams.add(dam);
	  }
	  dams.add(riverEnd);
	  int[][] damsBuilt = new int[this.damLocations.length+2][this.damLocations.length+2];
	  



	  for (int i = dams.size(); i >= 0; i--) {
		  
	        for (int j = i + 1; j < this.damLocations.length+2; j++) {
	        	
	        	
	        	
	            for (int k = i + 1; k < j; k++) {
	            	int pre=Integer.MAX_VALUE;
	            	if(damsBuilt[i][j]!=0) {
	            		pre=damsBuilt[i][j];
	            	}
	            	damsBuilt[i][j]=Math.min(pre,dams.get(j) - dams.get(i) + damsBuilt[i][k] + damsBuilt[k][j]);
	            }
	               
	        }
	  }
	    return damsBuilt[0][this.damLocations.length+1];   

    



  }



  /** Returns the cost of applying the dam evaluation decisions in the
   * specified order against the dam locations and river end state supplied to
   * the constructor.
   *
   * @param evaluationSequence elements of the Y parameter supplied in the
   * constructor, possibly rearranged such that the ith element represents the
   * y-position that is to be the ith dam evaluated for the WPA.  Thus: if Y =
   * {2, 4, 6}, damDecisions may be {4, 6, 2}: this method will return the cost
   * of evaluating the entire set of y-positions when dam evaluation is done
   * first for position "4", then for position "6", finally for position "2".
   * @return the cost of dam evaluation for the entire sequence of dam
   * positions when performed in the specified order.
   * @fixme This method is conceptually a static method because it doesn't
   * depend on the optimal solution produced by solve().  OTOH: the
   * implementation does require access to both the Y array and "river end"
   * information supplied to the constructor.
   * @note the implementation of this method is (almost certainly) not the
   * dynamic programming algorithm used in solve().  This method is part of the
   * API to stimulate your thinking as you work through this assignment and to
   * exercise your software engineering muscles.
   * @notetoself is this assignment too easy without an API for returning the
   * "optimal evaluation sequence"?
   */
  public int cost(final int[] evaluationSequence) {
	  TreeSet<Integer> myboiLarry= new TreeSet<>();
	  myboiLarry.add(0);
	  myboiLarry.add(riverEnd);
	  int cost=0;
	 
	  for(int dam: evaluationSequence) { 
		  myboiLarry.add(dam);
		  cost+=myboiLarry.higher(dam)-myboiLarry.lower(dam);
		
	  }

	
    return cost;
  }
} // class