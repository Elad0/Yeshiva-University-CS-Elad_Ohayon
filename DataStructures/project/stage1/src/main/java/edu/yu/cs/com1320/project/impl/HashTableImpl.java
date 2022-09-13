package edu.yu.cs.com1320.project.impl;


public class HashTableImpl<Key, Value> implements edu.yu.cs.com1320.project.HashTable<Key, Value>{
	
	
	class Node<Key, Value>{
		private Node next;
		private Value data;
		private Key key;
		private Node head;
		
		Node last;
		private Node(Key key, Value data) {
			this.key=key;
			this.data=data;
			this.next=null;
			this.head=null;

		}
	}
	

private Node<?,?>[] HashTable;


	public HashTableImpl(){
		this.HashTable= new Node[5];
	
	}
	
	@Override
	public Value get(Key k) {
		int index=hashFunction(k);
	if(this.HashTable[index]==null) {
		return null;
	}
		if(k.equals(this.HashTable[index].key)	|| k==this.HashTable[index].key	) {
			return (Value) this.HashTable[index].data;
		}
		
		if(k!=  this.HashTable[index].key) {//might need to cast as key
	
			Node pointer=this.HashTable[index].head;
			
			
			while(pointer.next!=null&& (pointer.next.key!=k	|| !pointer.next.key.equals(k))	) {
				pointer=pointer.next;
				
			}
			if(pointer.next!=null) {
				return (Value) pointer.next.data;
			}
		}
		
		return null;
	}

	
	private Value putNull(Key k, Value v) {
	
		int index=hashFunction(k);

		Value oldValue=null;
		if(this.HashTable[index]==null) {
			return null;
		}
		Node pointer=this.HashTable[index].head;
		
		if(pointer.key.equals(k)	|| pointer.key==k		) {
			oldValue=(Value) pointer.data;
			pointer.data=null;
			return oldValue;
		}
		
		while(pointer.next!=null&& pointer.next.key!=k) {
			
			pointer=pointer.next;
		}

		if(pointer.next!=null) {
			oldValue=(Value) pointer.next.data;
			pointer.next=pointer.next.next;}
		
		else {
			pointer.data=null;
			
		}
		
		return oldValue;
	}
	
	private Value putIndexNull(Key k, Value v) {
		int index=hashFunction(k);
		Node newNode= new Node(k,v);
		this.HashTable[index]=newNode;
		newNode.head=newNode;
			return null;
	}
	
	private Value standardPut(Key k, Value v) {
		int index=hashFunction(k);
		Value oldValue=null;
		Node current=this.HashTable[index].head;
		
		if(current.key.equals(k)) {
			oldValue=(Value) current.data;
			current.data=v;
			return oldValue;
	}
			if(current.key==k) {
			
			oldValue=(Value) current.data;
			current.data=v;
			return oldValue;
		}
		
		else{
			
			while	(current.next!=null&& (!current.next.key.equals(k) ||	current.next.key!=k)	) {
			current=current.next;
					
		}
		if(current.next!=null) {
			
			 oldValue=(Value) current.next.data;
			current.next.data=v;		//might need to do current instead
		}
		else {
			
		Node newNode= new Node(k,v);
		current.next=newNode;
		//System.out.println(current.next.data);
		}
			}
			return oldValue;}
	
	@Override
	public Value put(Key k, Value v) {
		int index=hashFunction(k);
		Value oldValue=null;
		
		if(v==null) {
	return putNull(k,v);
		
		}
		
		
		
	if(this.HashTable[index]==null) { //make sure to account for a document being overwritten
		return putIndexNull(k, v);
		}
	
	else if(this.HashTable[index]!=null){
	return standardPut(k,v);
	}
	return oldValue;
	
	}
	
	private int hashCode(Key k) {
		final int prime = 31;
		int result = 1;
		result = prime * result + k.hashCode();
		
		return result;
	}




	private int hashFunction(Key k) {
		return (hashCode(k) & 0x7fffffff) % this.HashTable.length;
		
		
	}
	

}
