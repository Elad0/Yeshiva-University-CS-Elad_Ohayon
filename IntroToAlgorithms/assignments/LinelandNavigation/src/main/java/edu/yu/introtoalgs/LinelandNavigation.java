package edu.yu.introtoalgs;


import java.util.LinkedList;

import java.util.Queue;


/** Defines the API for the LinelandNavigation assignment: see the requirements
 * document for more information.
 *
 * Students MAY NOT change the constructor signature.  My test code will only
 * invoke the API defined below.
 *
 * @author Avraham Leff
 */

public class LinelandNavigation {
	
	class Moves{
		protected int position;
		protected int moves;
		public Moves(int position, int moves) {
			this.position=position;
			this.moves=moves;
					
		}
	}
	
	
	
	private int m;
	private int g;
	protected int[] board;
	private boolean unSolvable=false;
  public final static int MAX_FORWARD = 1_000_000;

  /** Constructor.  When the constructor completes successfully, the navigator
   * is positioned at position 0.
   *
   * @param g a positive value indicating the minimim valued position for a
   * successful navigation (a successful navigation can result in a position
   * that is greater than g).  The value of this parameter ranges from 1 to
   * MAX_FORWARD (inclusive).
   * @param m a positive integer indicating the exact number of positions that
   * must always be taken in a forward move. The value of this parameter cannot
   * exceed MAX_FORWARD.
   * @throws IllegalArgumentException if the parameter values violate the
   * specified semantics.
   */
  public LinelandNavigation(final int g, final int m) {
	  if(m>MAX_FORWARD|| g<1 || g>MAX_FORWARD) {
		  throw new IllegalArgumentException();
	  }
    this.m=m;
    this.g=g;
    this.board= new int[MAX_FORWARD+1+m];
    this.board[g]=1;
  }

  /** Adds a mined line segment to Lineland (an optional operation).
   *
   * NOTE: to simplify computation, assume that no two mined line segments can
   * overlap with one another, even at their end-points.  You need not test for
   * this (although if it's easy to do so, consider sprinkling asserts in your
   * code).
   *
   * @param start a positive integer representing the start (inclusive)
   * position of the line segment
   * @param end a positive integer represending the end (inclusive) position of
   * the line segment, must be greater or equal to start, and less than
   * MAX_FORWARD.
   */
  public void addMinedLineSegment(final int start, final int end) {
    if(start<1 || !(end>=start) || end>MAX_FORWARD) {
    	throw new IllegalArgumentException();
    }
    
    if(end-start+1>=m && start<g-m-1) {
    	this.unSolvable=true;
    }
    
    if(this.unSolvable	|| start>g+m+1) {
    	return;
    }
    
    for(int i=start; i<= end; i++) {
    	if(this.board[i]==-1) {
    		throw new IllegalArgumentException();	//Overlap detected
    	}
    	this.board[i]=-1;
    }
    
  
  }


  /** Determines the minimum number of moves needed to navigate from position 0
   * to position g or greater, where forward navigation must exactly m
   * positions at a time and backwards navigation can be any number of
   * positions.
   *
   * @return the minimum number of navigation moves necessary, or "0" if no
   * navigation is possible under the specified constraints.
   */
  public final int solveIt() {
	Queue<Moves> bfs=  new LinkedList(); 
	
	int[] positionsMarked= new int[1_000_000+m+1];
	
    if(this.unSolvable) {
    	return 0;
    }

   int moves=Integer.MAX_VALUE;
    
   bfs.add(new Moves(0, 0));

    while(!bfs.isEmpty()) {
    Moves curr=bfs.remove();
    	
    	if(this.board[curr.position+m]!=-1) {
    	
    		if(curr.position<g && positionsMarked[curr.position+m]!=1) {
    			
    			bfs.add(new Moves(curr.position+m, curr.moves+1));
    			positionsMarked[curr.position+m]=1;
    		}		
    		
    
    		 if(curr.position>=g) {
    		    	moves=Math.min(moves, curr.moves);
    	    		
    	    	}
    		
    	}
    	
    	else {
    		
    		for(int i=0; i<m; i++) {
    			if(curr.position-i<0) {
    				break;
    			}
    			if(positionsMarked[curr.position-i]!=1 && this.board[curr.position-i]!=-1) {
    				bfs.add(new Moves(curr.position-i, curr.moves+1));
    			}
    			positionsMarked[curr.position-i]=1;
    		}
    		 if(curr.position>=g) {
    			 moves=Math.min(moves, curr.moves);
    	    	
    	    	}
    	}
    	

    }
 
    
  
  if(moves==Integer.MAX_VALUE) {
	  return 0;
  }
  
    return moves;
  }
} // LinelandNavigation