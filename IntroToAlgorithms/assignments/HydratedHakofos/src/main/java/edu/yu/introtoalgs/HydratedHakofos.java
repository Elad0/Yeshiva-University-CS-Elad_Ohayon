package edu.yu.introtoalgs;
import java.util.*;
/** Defines the API for the HydratedHakofos assignment: see the requirements
 * document for more information.
 *
 * Students MAY NOT change the constructor signature.  My test code will only
 * invoke the API defined below.
 *
 * @author Avraham Leff
 */

public class HydratedHakofos {
  public HydratedHakofos() {
    // fill in to taste
  }

  /** Determines whether or not a table exists such that hakofos, beginning
   * from this table, can successfully traverse the entire circuit without
   * getting dehydrated ("negative water level").  If multiple tables are
   * valid, return the lowest-numbered table (numbered 1..n).  Otherwise,
   * return the sentinel value of "-1".
   *
   * @param waterAvailable an array, indexed from 0..n-1, such that the ith
   * element represents the amount of water available at the ith table.
   * @param waterRequired an array, indexed from 0..n-1, such that the ith
   * element represents the amount of water required to travel from the ith
   * table to the next table.
   * @return a number from 1..n or -1 as appropriate.
   *
   * NOTE: if the client supplies arrays of differing lengths, or if the arrays
   * are null, or empty, or if the client supplies non-positive values in
   * either of these arrays, the result is undefined.  In other words: you
   * don't have to check for these conditions (unless you want to prevent
   * errors during development).
   */
  public int doIt(int[] waterAvailable, int[] waterRequired) {
	  if(waterAvailable.length!=waterRequired.length) {
		  throw new IllegalArgumentException();
	  }
	  
	  int lowest=1;
	  
	  try { 
     int pointer=lowest;
     int keyWaterAvalible=0;
     int keyWaterRequired=0;
     int waterLevel=0;
     boolean loopedAroundOnce=false;
     while(true) {
    	 
    	 if(keyWaterAvalible==waterAvailable.length|| keyWaterRequired==waterRequired.length 	) {	//((keyWaterRequired==pointer ||keyWaterAvalible==pointer)	&& loopedAroundOnce
    		 if(loopedAroundOnce) {
    			 if(waterLevel>=0) {
    				 return lowest;
    			 }
    			 else {

    				 return -1;
    			 }
    		 }
    		 keyWaterAvalible=0;
    		 keyWaterRequired=0;
    		  loopedAroundOnce=true;
    	 }
    	 
    	 
    	 	waterLevel+=waterAvailable[keyWaterAvalible];
    	 	waterLevel-=waterRequired[keyWaterRequired];
    		keyWaterAvalible++;
    	 	keyWaterRequired++;
    	 if(waterLevel<0) {
    		 lowest=keyWaterAvalible+1;
    		 pointer=keyWaterAvalible+1;
    	 }
    	 
     }
     
      

	  }
	  catch(Exception e) {
		 throw new IllegalArgumentException();

	  }
  } // doIt

  
  
}