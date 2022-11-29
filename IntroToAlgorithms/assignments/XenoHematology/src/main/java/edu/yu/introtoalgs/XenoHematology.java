package edu.yu.introtoalgs;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/** Defines the API for the XenoHematology assignment: see the requirements
 * document for more information.
 *
 * Students MAY NOT change the constructor signature.  My test code will only
 * invoke the API defined below.
 *
 * @author Avraham Leff
 */

//TODO if have time optimize with arrays


public class XenoHematology {

	class IncompatibleTree{
		private int size;
		protected Node root;
		protected Set<Integer> numweHave;
		protected Set<Integer> thinsweDontMatch;
		class Node{
			protected int val;
			protected Node right;
			protected Node left;
			
			public Node(int val) {
				this.val=val;
				this.right=null;
				this.left=null;
			}
			
			public void setRight(Node next) {
				this.right=next;
			}
			
			public Node getRight() {
				return this.right;
			}
			public void setLeft(Node next) {
				this.left=next;
			}
			
			public Node getLeft() {
				return this.left;
			}
			
		}
		
		public IncompatibleTree() {
			this.numweHave=new HashSet();
			this.thinsweDontMatch=new HashSet();
			this.root=null;
			this.size=0;
		}
		
		public void addNumWeHave(int k) {
			this.size+=1;
			this.numweHave.add(k);
		}
		public void dontMatch (int k) {
			this.size+=1;
			this.thinsweDontMatch.add(k);
		}
		
		public boolean merge(IncompatibleTree other) {	//returns true if "this" is larger
			if(this.size>other.size) {
				this.numweHave.addAll(other.numweHave);
				this.size=this.numweHave.size();
				this.thinsweDontMatch.addAll(other.thinsweDontMatch);
				this.size+=this.thinsweDontMatch.size();
				return true;
			}
			
			else {
				other.numweHave.addAll(this.numweHave);
				other.size=other.numweHave.size();
				other.thinsweDontMatch.addAll(this.thinsweDontMatch);
				other.size+=other.thinsweDontMatch.size();
				return false;
			}
			
		}
		
		
	}
	
	 class WeightedQuickUnionUF{	//textbook code
		private int[] id; // parent link (site indexed)
	 	private int[] sz; // size of component for roots (site indexed)
	 	private int count; // number of components
	private WeightedQuickUnionUF(int N) {
		count = N;
		id = new int[N];
		//for (int i = 0; i < N; i++) id[i] = i;
		sz = new int[N];
		/*for (int i = 0; i < N; i++) 
	 	}*/
		for(int i=0; i<N; i++) {
			id[i]=i;
			sz[i] = 1;
		}
		
	}
	
	 public int count(){
		 
		return count; 
	}
	 
	 public boolean connected(int p, int q){
		 return find(p) == find(q);
		 }
	 
	 private int find(int p){ // Follow links to find a root.
	 while (p != id[p]) {
		 p = id[p];
	 }
	 return p;
	 }
	 
	 public void union(int p, int q){
	 int i = find(p);
	 int j = find(q);
	 if (i == j) {
		 return;
	 }
	 // Make smaller root point to larger one.
	 if (sz[i] < sz[j]) {
		 id[i] = j; sz[j] += sz[i];
	 }
	 else { 
		 id[j] = i; sz[i] += sz[j];
		 }
	 count--;
	 }
	 
	 }
	 
	protected WeightedQuickUnionUF un;
	protected WeightedQuickUnionUF noco;
	protected IncompatibleTree incop;
	private final int populationSize;
	protected Set<Integer> haveData;
	protected Set<IncompatibleTree> incomptaible;
	/** Constructor: specifies the size of the xeno population.
   *
   * @param populationSize a non-negative integer specifying the number of
   * aliens in the xeno population.  Members of the population are uniquely
   * identified by an integer 0..populationSize -1.
   */
  public XenoHematology(final int populationSize) {
	    this.populationSize=populationSize;
	    this.un= new WeightedQuickUnionUF(this.populationSize);
	    this.noco=new WeightedQuickUnionUF(this.populationSize);
	    this.incop=new IncompatibleTree();
	    this.incomptaible=new HashSet();
	    this.haveData=new HashSet();
	 
    // fill me in!
  } // constructor

  /** Specifies that xeno1 and xeno2 are incompatible.  Once specified
   * as incompatible, the pair can never be specified as being
   * "compatible".  In that case, don't throw an exception, simply
   * treat the method invocation as a "no-op".  A xeno is always
   * compatible with itself, is never incompatible with itself:
   * directives to the contrary should be treated as "no-op"
   * operations.
   *
   * Both parameters must correspond to a member of the population.
   *
   * @param xeno1 non-negative integer that uniquely specifies a member of the
   * xeno population, differs from xeno2
   * @param xeno2 non-negative integer that uniquely specifies a member of the
   * xeno population.
   * @throws IllegalArgumentException if the supplied values are
   * incompatible with the @param Javadoc.
   */
  public void setIncompatible(int xeno1, int xeno2) {
	 
	  if(xeno1<0 || xeno1>this.populationSize-1 || xeno2<0 || xeno2>this.populationSize-1) {
		  throw new IllegalArgumentException();
	  }
	  if(xeno1==xeno2) {
		  return;
	  }
	  if(this.un.connected(xeno1, xeno2)) {
		
		  return;
	  }
	  if(areIncompatible(xeno1, xeno2)) {	//already incompativle
		  return;
	  }
	  this.noco.union(xeno1, xeno2);
	  IncompatibleTree xeno1Pointer=null;
	  IncompatibleTree xeno2Pointer=null;
	  
	  for(IncompatibleTree tr: this.incomptaible) {
		  if(tr.numweHave.contains(xeno1)) {
			xeno1Pointer=tr;
		  }
		  if(tr.numweHave.contains(xeno2)) {
				xeno2Pointer=tr;
			  }
	  }
	  addNonCompatible(xeno1Pointer, xeno1, xeno2Pointer, xeno2);

	/**  if(xeno1Pointer!=null && xeno2Pointer==null) {
		  xeno2Pointer= new IncompatibleTree();
		  xeno2Pointer.addNumWeHave(xeno2);
		  xeno1Pointer.dontMatch(xeno2);
		  xeno2Pointer.thinsweDontMatch.addAll(xeno1Pointer.numweHave);
		  xeno2Pointer.size+=xeno1Pointer.numweHave.size();
			this.incomptaible.add(xeno2Pointer);
			this.incomptaible.add(xeno1Pointer);
		
	  }
	  else if(xeno1Pointer==null && xeno2Pointer!=null) {
		  xeno1Pointer= new IncompatibleTree();
		  xeno1Pointer.addNumWeHave(xeno1);
		  xeno2Pointer.dontMatch(xeno1);
		  
		  xeno1Pointer.thinsweDontMatch.addAll(xeno2Pointer.numweHave);
		  xeno1Pointer.size+=xeno2Pointer.numweHave.size();
			this.incomptaible.add(xeno2Pointer);
			this.incomptaible.add(xeno1Pointer);
		
	  }
	  
	  
	  else if(xeno1Pointer==null && xeno2Pointer==null) {
		  xeno1Pointer= new IncompatibleTree();
		  xeno1Pointer.addNumWeHave(xeno1);
		  xeno2Pointer= new IncompatibleTree();
		  xeno2Pointer.addNumWeHave(xeno2);
			xeno1Pointer.dontMatch(xeno2);
			xeno2Pointer.dontMatch(xeno1);
			
	  }
	  
	  else if(xeno1Pointer!=null && xeno2Pointer!=null) {
		  xeno1Pointer.thinsweDontMatch.addAll(xeno2Pointer.numweHave);
		  xeno1Pointer.size+=xeno2Pointer.numweHave.size();
		  
		  xeno2Pointer.thinsweDontMatch.addAll(xeno1Pointer.numweHave);
		  xeno2Pointer.size+=xeno1Pointer.numweHave.size();
			this.incomptaible.add(xeno2Pointer);
			this.incomptaible.add(xeno1Pointer);
		  
	  }*/


	
/**	if(this.haveData.contains(xeno1) && !this.haveData.contains(xeno2)) {	//we have data on xeno1 and not 2 so put 2 in the other set
		for(Set<Integer> sets: this.set) {
			if(!sets.contains(xeno1)) {
				sets.add(xeno2);
				
			}
		}
	}
	
	if(!this.haveData.contains(xeno1) && this.haveData.contains(xeno2)) {	//we have data on xeno2 and not 1 so put one in the other set
	
		
		for(Set<Integer> sets: this.set) {
			if(!sets.contains(xeno2)) {
				sets.add(xeno1);
			}
		}
	}
	
	if(!this.haveData.contains(xeno1) && !this.haveData.contains(xeno2)) {	//data on neither so put them in diff set
		Set<Integer> c = new HashSet();
		c.add(xeno1);
		this.set.add(c);
		c=new HashSet();
		c.add(xeno2);
		this.set.add(c);
	}
	**/
	this.haveData.add(xeno1);
	this.haveData.add(xeno2);
	
	
}

private void addNonCompatible(IncompatibleTree xeno1Pointer, int xeno1, IncompatibleTree xeno2Pointer, int xeno2) {
	  if(xeno1Pointer!=null && xeno2Pointer==null) {
		  xeno2Pointer= new IncompatibleTree();
		  xeno2Pointer.addNumWeHave(xeno2);
		  xeno1Pointer.dontMatch(xeno2);
		  xeno2Pointer.thinsweDontMatch.addAll(xeno1Pointer.numweHave);
		  xeno2Pointer.size+=xeno1Pointer.numweHave.size();
			this.incomptaible.add(xeno2Pointer);
			this.incomptaible.add(xeno1Pointer);
		
	  }
	  else if(xeno1Pointer==null && xeno2Pointer!=null) {
		  xeno1Pointer= new IncompatibleTree();
		  xeno1Pointer.addNumWeHave(xeno1);
		  xeno2Pointer.dontMatch(xeno1);
		  
		  xeno1Pointer.thinsweDontMatch.addAll(xeno2Pointer.numweHave);
		  xeno1Pointer.size+=xeno2Pointer.numweHave.size();
			this.incomptaible.add(xeno2Pointer);
			this.incomptaible.add(xeno1Pointer);
		
	  }
	  
	  
	  else if(xeno1Pointer==null && xeno2Pointer==null) {
		  xeno1Pointer= new IncompatibleTree();
		  xeno1Pointer.addNumWeHave(xeno1);
		  xeno2Pointer= new IncompatibleTree();
		  xeno2Pointer.addNumWeHave(xeno2);
			xeno1Pointer.dontMatch(xeno2);
			xeno2Pointer.dontMatch(xeno1);
			this.incomptaible.add(xeno2Pointer);
			this.incomptaible.add(xeno1Pointer);
			
	  }
	  
	  else if(xeno1Pointer!=null && xeno2Pointer!=null) {
		  xeno1Pointer.thinsweDontMatch.addAll(xeno2Pointer.numweHave);
		  xeno1Pointer.size+=xeno2Pointer.numweHave.size();
		  
		  xeno2Pointer.thinsweDontMatch.addAll(xeno1Pointer.numweHave);
		  xeno2Pointer.size+=xeno1Pointer.numweHave.size();
			this.incomptaible.add(xeno2Pointer);
			this.incomptaible.add(xeno1Pointer);
		  
	  }
	  
 for(Integer k: xeno1Pointer.thinsweDontMatch) {
		  this.un.union(k, xeno2);
	  }
 
for(Integer k: xeno2Pointer.thinsweDontMatch) {
	  this.un.union(k, xeno1);
 }
}
  
private void addCompatible(IncompatibleTree xeno1Pointer, IncompatibleTree xeno2Pointer, int xeno1, int xeno2) {
	if(xeno1Pointer!=null && xeno2Pointer!=null) {
		boolean which=xeno1Pointer.merge(xeno2Pointer);
		if(which) {
			this.incomptaible.remove(xeno2Pointer);
		}
		else {
			this.incomptaible.remove(xeno1Pointer);
		}
	}
	
	if(xeno1Pointer==null && xeno2Pointer!=null) {
		xeno2Pointer.addNumWeHave(xeno1);
		this.incomptaible.add(xeno2Pointer);
	}
	if(xeno1Pointer!=null && xeno2Pointer==null) {
		xeno1Pointer.addNumWeHave(xeno2);
		this.incomptaible.add(xeno1Pointer);
	}
	if(xeno1Pointer==null && xeno2Pointer==null) {
		xeno1Pointer= new IncompatibleTree();
		xeno1Pointer.addNumWeHave(xeno2);
		xeno1Pointer.addNumWeHave(xeno1);
		this.incomptaible.add(xeno1Pointer);
	}
	
	
	
	
	
	
	
}

/** Specifies that xeno1 and xeno2 are compatible.  Once specified
   * as compatible, the pair can never be specified as being
   * "incompatible".  In that case, don't throw an exception, simply
   * treat the method invocation as a "no-op".  A xeno is always
   * compatible with itself, is never incompatible with itself:
   * directives to the contrary should be treated as "no-op"
   * operations.
   *
   * Both parameters must correspond to a member of the population.
   *
   * @param xeno1 non-negative integer that uniquely specifies a member of the
   * xeno population.
   * @param xeno2 non-negative integer that uniquely specifies a member of the
   * xeno population
   * @throws IllegalArgumentException if the supplied values are
   * incompatible with the @param Javadoc.
   */

  public void setCompatible(int xeno1, int xeno2) {	//this works but now need to avoid setting compatible what was declared uncompatible
	  if(xeno1<0 || xeno1>this.populationSize-1 || xeno2<0 || xeno2>this.populationSize-1) {
		  throw new IllegalArgumentException();
	  }
	  
if(xeno1==xeno2) {
	return;
}

		if(areCompatible(xeno1, xeno2)) {
		
		return;
		}
		if(this.noco.connected(xeno1, xeno2)) {
			return;
		}
	
	un.union(xeno1, xeno2);
	
	  IncompatibleTree xeno1Pointer=null;
	  IncompatibleTree xeno2Pointer=null;
	
	  
	  for(IncompatibleTree tr: this.incomptaible) {
		  if(tr.numweHave.contains(xeno1)) {
			xeno1Pointer=tr;
		  }
		  if(tr.numweHave.contains(xeno2)) {
				xeno2Pointer=tr;
			  }
	  }
	  addCompatible(xeno1Pointer,xeno2Pointer,xeno1,xeno2);
	
	this.haveData.add(xeno1);
	this.haveData.add(xeno2);
	

	
	
	 
	 
  }

  /** Returns true iff xeno1 and xeno2 are compatible from a hematology
   * perspective, false otherwise (including if we don't know one way or the
   * other).  Both parameters must correspond to a member of the population.
   *
   * @param xeno1 non-negative integer that uniquely specifies a member of the
   * xeno population, differs from xeno2
   * @param xeno2 non-negative integer that uniquely specifies a member of the
   * xeno population
   * @return true iff compatible, false otherwise
   * @throws IllegalArgumentException if the supplied values are
   * incompatible with the @param Javadoc
   */
  public boolean areCompatible(int xeno1, int xeno2) {
	  if(xeno1<0 || xeno1>this.populationSize-1 || xeno2<0 || xeno2>this.populationSize-1) {
		  throw new IllegalArgumentException();
	  }
	  
	if(xeno2==xeno1) {
		return true;
	}
	
	if(!this.haveData.contains(xeno1) || !this.haveData.contains(xeno2)) {
		
		return false;
	}
	
	return un.connected(xeno1,xeno2);
	 
            
  }

  /** Returns true iff xeno1 and xeno2 are incompatible from a hematology
   * perspective, false otherwise (including if we don't know one way or the
   * other).  Both parameters must correspond to a member of the population.
   *
   * @param xeno1 non-negative integer that uniquely specifies a member of the
   * xeno population, differs from xeno2
   * @param xeno2 non-negative integer that uniquely specifies a member of the
   * xeno population
   * @return true iff compatible, false otherwise
   * @throws IllegalArgumentException if the supplied values are
   * incompatible with the @param Javadoc.
   */
  public boolean areIncompatible(int xeno1, int xeno2) {
	  
  if(xeno1<0 || xeno1>this.populationSize-1 || xeno2<0 || xeno2>this.populationSize-1) {
		  throw new IllegalArgumentException();
	  }
	
	if(!this.haveData.contains(xeno1) || !this.haveData.contains(xeno2)) {
		return false;
	
	}

	  
	if(xeno2==xeno1) {
		
		return false;
	}
	
	if(areCompatible(xeno2, xeno1)) {
		
		return false;
	}
	
	for(IncompatibleTree tr: this.incomptaible) {
		if(tr.numweHave.contains(xeno1) && tr.thinsweDontMatch.contains(xeno2)) {
			return true;
		}
		if(tr.numweHave.contains(xeno2) && tr.thinsweDontMatch.contains(xeno1)) {
			return true;
		}
	}

	
	return false;
	
	
	
	
  }
} // XenoHematology