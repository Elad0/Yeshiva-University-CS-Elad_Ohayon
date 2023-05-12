package edu.yu.da;

import java.util.*;
/** Defines the API for specifying and solving the DetectTerrorist problem (see
 * the requirements document).
 *
 * Students MAY NOT change the public API of this class, nor may they add ANY
 * constructor.
 *
 * @author Avraham Leff
 */
class storeStuff {
	int start=0;
	int end=0;
	int weight=0;
	public storeStuff(int start, int end, int weight) {
		this.start=start;
		this.end=end;
		this.weight=weight;
	}
}

public class DetectTerrorist {
	
private int[] passengers;
private int terrorist=-1;
private int timesHit=0;
  /** Constructor: represents passengers to be detected as an array in which
   * the ith value is the weight of the ith passenger.  After the constructor
   * completes, clients can invoke getTerrorist() for a O(1) lookup cost.
   *
   * @param passengers an array of passenger weights, indexed 0..n-1.  All
   * passengers that are not terrorists have the same weight: that weight is
   * greater than the weight of the terrorist.  Exactly one passenger is a
   * terrorist.
   */
  public DetectTerrorist(final int[] passengers) {
	  this.passengers=passengers;
	  storeStuff low=this.divideInto2();

	  if(low==null) {
		  this.terrorist=-1;
	  }
	  else {
this.terrorist=low.start;
if(this.timesHit==this.passengers.length-2 && this.passengers.length>3) {
	
	this.terrorist=-1;
}
	  }
    // fill me in!
  }   // constructor
  

/** Returns the index of the passenger who has been determined to be a
   * terrorist.
   *
   * @return the index of the terrorist element.
   */
  public int getTerrorist() {
    return this.terrorist;
  }
  
  private storeStuff divideInto2() {
	  if(this.passengers.length<=2) {
		  return null;
	  }
	  storeStuff dib= div(0,this.passengers.length-1);
	  storeStuff gary= null;
	  
	  if(this.passengers.length==3) {
		  gary=new storeStuff(0, 2,this.passengers[0]+this.passengers[2]);
		 if(dib.start==1 && gary.weight==dib.weight) {
			 
			 this.terrorist=-1;
			 return null;
		 }
	  }
	 
	 return dib;
  }
  private storeStuff div(int start, int end) {
	  if(end-start==1) {
		  return new storeStuff(start, end, this.passengers[start]+this.passengers[end]);
	  }
	  
	  int mid = start + (end - start)/2;
	 storeStuff one= div(start,mid);
	 storeStuff two= div(mid,end);
	  if(one.weight<two.weight) {
		 
		  return one;
	  }
	  if(one.weight==two.weight) {
		  timesHit++;
		  if(one.end==two.start) {

			  return new storeStuff(one.end, one.end, one.weight);
			  
		  }
		  
	  }
		 if(two.end==this.passengers.length-1&& one.weight>two.weight) {
			 return new storeStuff(two.end, two.end, two.weight);
		 }
		 return two;
  }

} // DetectTerrorist