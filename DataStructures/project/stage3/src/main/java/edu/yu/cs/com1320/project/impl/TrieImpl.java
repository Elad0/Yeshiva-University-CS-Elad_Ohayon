package edu.yu.cs.com1320.project.impl;

import java.util.*;



//change Node to public and val to private


public class TrieImpl<Value> implements edu.yu.cs.com1320.project.Trie<Value>{
	private static final int alphabetSize = 256; // extended ASCII
	
	private Node root; 

	
	
	
	public class Node<Value>{
		
		public Value[] val;
		private Node[] links= new Node[TrieImpl.alphabetSize];
		
		private Node() {
		val= (Value[]) new Object[5];
		}
	}
	public TrieImpl() {
		this.root=null;
		
	}

	@Override
	public void put(String key, Value val)
	{

		if(key==null) {
			throw new IllegalArgumentException();
		}
		
		else
		{
			this.root = put(this.root, key, val, 0);
		}
	}

	private Node put(Node x, String key, Value val, int d)
	{
		//create a new node
		if (x == null)
		{
			
			x = new Node();
			
		}		
			
		//we've reached the last node in the key,
		//set the value for the key and return the node
		if(x.val[(int) Math.round(x.val.length*(.75)-1)]!=null) {
			x.val=resize((Value[]) x.val);
		}
		
		if (d == key.length())
		{
			boolean skip=false;
			int y=0;
			while(y<x.val.length&&x.val[y]!=null) {
				
		
				if(x.val[y].equals(val)) {
				skip=true;
				
				}
				
			y++;	
			}
			if(skip==false) {
			x.val[y]=val;	//put the value in the next non empty spot in the array which holds the values(Documents)
			}
			return x;
		}
		
		//proceed to the next node in the chain of nodes that
		//forms the desired key
		char c = Character.toUpperCase(key.charAt(d));
		x.links[c] = this.put(x.links[c], key, val, d+1);
		
		return x;
	}	


	private Value[] resize(Value[] arr) {
		
		Value[] tempArray= (Value[]) new Object[arr.length*2];
		int u=0;
		while(u<arr.length) {
			tempArray[u]=arr[u];
			u++;
		}
		return tempArray;
	}

	
	@Override
	public List<Value> getAllSorted(String key, Comparator<Value> comparator) {
		if(comparator==null) {
			throw new IllegalArgumentException();
			 
		 }
		
		List<Value> bdonk = Collections.emptyList();
		
		Node x = this.get(this.root, key, 0);
		if (x == null)
		{
			return bdonk;
		}

		
		
		
	int y=0;
	while (y<x.val.length&&x.val[y]!=null ) {
		y++;
	}
	Value[] tempArray= (Value[]) new Object[y];
	
	
	
	
	int z=0;
	while(z<x.val.length&&x.val[z]!=null) {
		//System.out.println(x.val[z]);
		tempArray[z]=(Value) x.val[z];
		z++;
	}
	
	  bdonk= (List<Value>)Arrays.asList(tempArray);
	  
	 Collections.sort(bdonk, comparator);
			return bdonk;
	}

	
	
	
	
	
	
	private Node get(String key) {
		return get(this.root, key,0);
	}
	
	
	
	private Node get(Node x, String key, int d)
	{
		//link was null - return null, indicating a miss
		if (x == null)
		{
			return null;
		}
		//we've reached the last node in the key,
		//return the node
		if (d == key.length())
		{
			return x;
		}
		//proceed to the next node in the chain of nodes that
		//forms the desired key
		char c = Character.toUpperCase(key.charAt(d));
		return this.get(x.links[c], key, d + 1);
	}

	@Override
	public Set<Value> deleteAll(String key) {
		
		HashSet<Value> toReturn= new HashSet<Value>();
		
		Node target=get(this.root, key, 0);
		
		for(int i=0; i<target.val.length; i++) {
			if(target.val[i]!=null) {
				toReturn.add((Value) target.val[i]);
			}
		}
		
		target.val=(Value[]) new Object[target.val.length];
		clean(key);
		return toReturn;
	}
	
	


	
	@Override
	public List<Value> getAllWithPrefixSorted(String prefix, Comparator<Value> comparator) {
		
		 if(comparator==null) {
				throw new IllegalArgumentException();
				 
			 }
	
		Node target=get(this.root, prefix,0);
		
		StackImpl<String> toReturn= new StackImpl<String>();
				toReturn.push(prefix);
		
		List<Value> bdonk=Collections.emptyList();
		 
		 StringBuilder o=new StringBuilder();
		 o.append(prefix);
		 
		 if(target!=null) {
			 getAllChildren(target, o, toReturn);
			 
			 StackImpl<Value> temp= new StackImpl();
			 
				temp=convertKeytoValue(toReturn);
			 Value[] array=  (Value[]) new Object[temp.size()];
			 
			 int u=0;
			 while(temp.size()>0) {

					array[u]=temp.pop();
					u++;
			 }
			Set<Value> bdonkey= new HashSet<Value>();
		for(Value j: array) {
			bdonkey.add(j);
		}
			 
				
			 bdonk= (List<Value>)Arrays.asList(bdonkey.toArray());
			 //bdonk= (List<Value>)Arrays.asList(array);
			//bdonk = bdonk.stream().distinct().collect(Collectors.toList());
			
		
			 
			
			 Collections.sort(bdonk, comparator);
		 
		 }
		 return bdonk;
		 
	}

	private void getAllChildren(Node x, StringBuilder prefix, StackImpl<String> toReturn) {	//change toreturn type to value if errors
	if(x!=null) {
		for(int i=0; i<x.val.length; i++) {
			if(x.val[i]!=null) {
				//toReturn.push((String) prefix.toString());
			}
		}
	
		for (char c = 0; c < this.alphabetSize; c++) {
			if(x.links[c]!=null){
				//add child's char to the string
				prefix.append(c);
				
				this.getAllChildren(x.links[c], prefix, toReturn);
				toReturn.push((String) prefix.toString());
				prefix.deleteCharAt(prefix.length() - 1);
			
		}
		
		
	}
	}
	}

	@Override
	public Set<Value> deleteAllWithPrefix(String prefix) {
		
		Set<Value> toReturn= new HashSet<Value>();
		
		StackImpl<String> to= new StackImpl<String>();
		
		Node target=get(this.root, prefix,0);
		StringBuilder key= new StringBuilder();
		key.append(prefix);
		getAllChildren(target,key,to);
		to.push(prefix);
		StackImpl<Value> values= new StackImpl<Value>();
		values=convertKeytoValue(to);
		while(values.size()>0) {
			toReturn.add(values.pop());
			
		}
		
		Node parent=get(this.root, prefix.substring(0, prefix.length()-1),0);
		if(parent!=null) {
		char c=prefix.toUpperCase().charAt(prefix.length()-1);
		parent.links[c]=null;
		}
		clean(prefix);
			return toReturn;
			
		
		
		
}
	private StackImpl convertKeytoValue(StackImpl<String> toReturn) {
		 

		 StackImpl<Value> temp= new StackImpl();
		 
		 while(toReturn.size()>0) {
			 if(toReturn.peek()!=null) {
				 Node h=get(this.root, toReturn.pop(),0);
				 	
				if(h!=null) {
					for(int i=0; i<h.val.length; i++) {
						if(h.val[i]!=null) {
							temp.push((Value) h.val[i]);
						}
					
				}
				}
			 }
		 }
		
		return temp;
	}

	
@Override
	public Value delete(String key, Value val) {
		Value v=null;
		Node target=get(root, key, 0);
		if(target!=null) {
		for(int i=0; i<target.val.length; i++) {
			if(target.val[i]!=null) {
				if(((Value)target.val[i]).equals(val)) {
					v=(Value) target.val[i];
					target.val[i]=null;
				}
			}
		}
		}
		clean(key);
		return v;
		
		
	}
	
private void clean(String keyword) {
	Node target=get(this.root, keyword,0);
	if(target==null) {
		return;
	}
	if(keyword.length()==0) {
		return;
	}
	boolean empty=true;
	for(int i=0; i<target.val.length; i++) {
		if(target.val[i]!=null) {
			empty =false;
			break;
		}
	}

	if(empty==true) {
		target=get(this.root,keyword.substring(0, keyword.length()-1),0);
		//target.links[keyword.charAt(keyword.length()-1)]=null;
		target=null;
	}
	
	 clean(keyword.substring(0,keyword.length()-1));
}
/*private void clean(String keyword) {
}*/
	

}
