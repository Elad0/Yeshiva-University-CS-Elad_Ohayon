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

public class BTKeysAtSameLevel extends BigOMeasurable{
  /** Constructor
   */
  public BTKeysAtSameLevel() {
    // fill this in!
  }
  

  public List<List<Integer>> compute(final String treeInStringRepresentation) {
  int digit=1;
  boolean error=false;
	  if(treeInStringRepresentation== null ) {
		  throw new IllegalArgumentException();
	  }
	  List<List<Integer>> rep= new ArrayList<List<Integer>>(100);
		if(treeInStringRepresentation.isEmpty() || treeInStringRepresentation.isBlank()) {
			return rep;
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
	  
	   rep= new ArrayList<List<Integer>>(closeparentcount);
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
				 if(index>6) {
				 rep.add(new ArrayList<Integer>(129));
				 }
				 else {
					 rep.add(new ArrayList<Integer>((int)Math.pow(2, index)+1));
				 }
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
		 
		 if(	!isOpenParen(processed[i])	&& !isCloseParen(processed[i]) && !isValidInt(processed[i])) {
			 throw new IllegalArgumentException();
		 }
		 if(index<0 || index>openparencount || digit==2|| error) {
			 throw new IllegalArgumentException();
		 }
	  }
	  
  return rep;
    // I suggest substituting a better implementation!
   
  } // compute

  private void verifyChildren(char[] processed) {
	  List<String> childCount= new ArrayList<String>(processed.length);
	
	  char c='4';
	  int index=1;
	  int parenCount=0;
	  childCount.add("I");
	  for(int i=1; i<processed.length; i++) {
		  if(isValidInt(processed[i])) {
			  
			 
			  if(childCount.size()-1<index && ArrayIndexisNull(index, childCount)==-1) {
				  childCount.add((String.valueOf(processed[i])));
 
			  }
			   if(ArrayIndexisNull(index, childCount)==1 && childCount.get(index)!=null) {
				  
				  childCount.set(index, childCount.get(index)+"I");
				//  System.out.println(index+" "+ processed[i]);
				  if(childCount.get(index).length()>2&& index<4) {
				
					  throw new IllegalArgumentException();
				  }
			  }
		  }
			  
		
		  if(isOpenParen(processed[i])) {
			  index++;
			  parenCount++;
		  }
		  
		  if(isCloseParen(processed[i])) {
			  index--;
			  parenCount--;
		  }
		 

	
		  if(parenCount==0 ) {
			  childCount=new ArrayList<String>(processed.length);
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


@Override
public void execute() {
	String start="1";
	StringBuilder k= new StringBuilder();
	StringBuilder close = new StringBuilder();
	k.append(start);
	for(int i=0; i<n; i++) {
		k.append("(1");
		close.append(")");
	}
	k.append(close);
	start=k.toString();
	compute(start);
}
  
  
  
  
  
  
  
}   