package edu.yu.introtoalgs;

import java.util.ArrayList;
/** Defines the API for the BTKeysAtSameLevel assignment: see the requirements
 * document for more information.
 *
 * Students MAY NOT change the constructor signature.  My test code will only
 * invoke the API defined below.
 *
 * @author Avraham Leff
 */
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;

public class BTKeysAtSameLevel {
  /** Constructor
   */
  public BTKeysAtSameLevel() {
    // fill this in!
  }
  

  /** Given a String representation of a binary tree whose keys are Integers,
   * computes a List whose elements are List of keys at the same level (or
   * depth) from the root.  The top-level List is ordered by increasing
   * distance from the root so that the first List element consists of the
   * root's key, followed by the keys of all the root's immediate children,
   * etc.  At a given level, the List is ordered from left to right.  See the
   * requirements doc for an example.
   *
   * The String representation of the binary tree is defined as follows.  Keys
   * consist of a single integer value, from 0 to 9.  The string consists only
   * of parentheses and single digit integers: it must begin with an integer
   * (the value of the root node) followed by zero, one or two pairs of
   * parentheses. A pair of parentheses represents a child binary tree with
   * (recursively) the same structure. If a given node only has one child, that
   * child will be the left child node of the parent.
   * 
   * Note: the "empty" tree is represented by the empty string, and this method
   * should therefore return an empty List.
   *
   * @param treeInStringRepresentation a binary tree represented in the
   * notation defined above.
   * @return a List whose elements are Lists of the tree's (integer) key
   * values, ordered in increasing distance from the root.  Each List element
   * contains the keys at a given level, ordered from left to right.
   * @throws IllegalArgumentException if the String is null, or doesn't
   * correspond to a valid String representation of a binary tree as defined
   * above.
   */
  public List<List<Integer>> compute(final String treeInStringRepresentation) {
  List<List<Integer>> rep= new ArrayList<List<Integer>>();
  int digit=1;
  boolean error=false;
	  if(treeInStringRepresentation== null ) {
		  throw new IllegalArgumentException();
	  }
	  char[] processed= treeInStringRepresentation.toCharArray();

	  int openparencount=0;
	  int closeparentcount=0;
	  
	  for(char c: processed) {
		  if(isOpenParen(c)) {
			  openparencount++;
		  }
		  if(isCloseParen(c)) {
			  closeparentcount++;
		  }
		 
	  }
	
	  
	  if(!isValidInt(processed[0])|| closeparentcount!=openparencount ) {
		  throw new IllegalArgumentException();
	  }
	  verifyChildren(processed);
	  List<Integer> temp= new ArrayList<Integer>();
	  temp.add((Integer.parseInt(String.valueOf(processed[0]))));
	  rep.add(temp);
	  int index=0;
	  if(processed.length>2) {
		  if(!isValidInt(processed[2])) {
			  throw new IllegalArgumentException();
		  }
	  }
		  
	  for(int i=1; i<processed.length; i++) {
		  
		 if(isValidInt(processed[i])) {
			digit++;
			 if(index>=rep.size()) {
				 rep.add(new ArrayList<Integer>());
			 }
			 if(rep.get(index).size()+1>Math.pow(2, index)) {
				 
				 error=true;
			 }
		 rep.get(index).add(Integer.parseInt(String.valueOf(processed[i])));
		 }
		 if(processed[i]=='(') {
			 digit=0;
			 index+=1;
		 }
		 if(processed[i]==')') {
			 digit=0;
			 index-=1;
		 }
		
		 if(index<0 || index>openparencount || digit==2|| error) {
			 throw new IllegalArgumentException();
		 }
	  }
	  
  return rep;
    // I suggest substituting a better implementation!
   
  } // compute

  private void verifyChildren(char[] processed) {
	  List<String> childCount= new ArrayList<String>();
	  
	
	  char c='4';
	  int index=1;
	  int parenCount=0;
	  childCount.add("I");
	  for(int i=1; i<processed.length; i++) {
		  if(isValidInt(processed[i])) {
			  
			 
			  if(childCount.size()-1<index && ArrayIndexisNull(index, childCount)==-1) {
				  childCount.add((String.valueOf(processed[i])));

				// System.out.println(processed[i]+ " new val added and index is "+ index+ " size is "+ childCount.size()+ " original is now "+ childCount);

			  
			  }
			   if(ArrayIndexisNull(index, childCount)==1 && childCount.get(index)!=null) {
				  
				  childCount.set(index, childCount.get(index)+"I");
				//  System.out.println(index+" "+ processed[i]);
				  if(childCount.get(index).length()>2&& index<4) {
				
					  throw new IllegalArgumentException();
				  }
			  }
			  
			
		  
		  
			/*  if(ArrayIndexisNull(index,childCount)==-1) {
				 
				  childCount.add("");
			  }
			  if(ArrayIndexisNull(index,childCount)==0) {
				  childCount.set(index,"I");
			  }
			  if(ArrayIndexisNull(index,childCount)==1) {
				  childCount.set(index, childCount.get(index)+"I");
				  System.out.println(childCount);
				  if(childCount.get(index).length()>2) {
						 System.out.println(processed[i]+" "+i);
						  throw new IllegalArgumentException();
					  }
			  }*/
		  }
			  
		
		  if(isOpenParen(processed[i])) {
			  index++;
			  parenCount++;
		  }
		  
		  if(isCloseParen(processed[i])) {
			  index--;
			  parenCount--;
		  }
		 
//System.out.println(childCount);
	
		  if(parenCount==0 ) {
			 // System.out.println("paren is zero " );
			  childCount=new ArrayList<String>();
			  childCount.add("I");
			  index=1;
		  }
		  
	  }
  }
	
 


 //return 0 if index is null
  // return 1 if it is not null
  //return -1 if exception because list isnt that large
 private int ArrayIndexisNull(int index, List<String> childCount) {
	 try {
		  if(childCount.get(index)==null) {
			  return 0;
		  }
		  if(childCount.get(index).equals("")) {
			  return 0;
		  }
		  if(childCount.get(index)!=null) {
			  return 1;
		  }
	 }catch(IndexOutOfBoundsException e) {
		 return -1;
	 }
return -1;
	 
 }
  private boolean isValidInt(char c) {
	try {
		int i= Integer.parseInt(String.valueOf(c));
		return true;
	}	catch (NumberFormatException e) {
		return false;
	}

}

private boolean isOpenParen(char c) {
	if(c!=40) {
		return false;
	}
	return true;
}

private boolean isCloseParen(char c) {
	if(c!=41) {
		return false;
	}
	return true;
}
  
  
  
  
  
  
  
}   // class