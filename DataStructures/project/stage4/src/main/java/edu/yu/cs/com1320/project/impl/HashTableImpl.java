package edu.yu.cs.com1320.project.impl;

public class HashTableImpl<Key, Value> implements edu.yu.cs.com1320.project.HashTable<Key, Value>{
	
	
	 class Node<Key, Value>{
		private Node next;
		private Value data;
		private Key key;
		private Node head;
		
	
		private Node(Key key, Value data) {
			this.key=key;
			this.data=data;
			this.next=null;
			this.head=null;

		}
	}
	

	 
	 
	 
private Node<?,?>[] HashTable;
private int size;
private int elemAmount;

	public HashTableImpl(){
		this.HashTable= new Node[5];
		this.size=5;
		this.elemAmount=0;
	}
	
	@Override
	public Value get(Key k) {
		int index=hashFunction(k);
			if(this.HashTable[index]!=null) {
				Node pointer=this.HashTable[index];
				while(pointer!=null) {
					if(pointer.key.equals(k)) {
						return (Value) pointer.data;
					}
					pointer=pointer.next;
				}
			}
		
	
		return null;
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
			
		else{
			
			while	(current.next!=null&& (!current.next.key.equals(k))	) {
			current=current.next;
					
		}
		if(current.next!=null) {
			
			 oldValue=(Value) current.next.data;
			current.next.data=v;		//might need to do current instead
		}
		else if (current.next==null){
			
		Node newNode= new Node(k,v);
		current.next=newNode;
		//System.out.println(current.next.data);
		}
			}
			return oldValue;}
	
	@Override
	public Value put(Key k, Value v) {
		if(v==null) {
			this.elemAmount--;
		}
		else if(v!=null && this.get(k)==null){
			this.elemAmount++;
		}
		
		if(this.elemAmount>= this.HashTable.length*.75) {
			
			
			this.HashTable=newHashTable();
		}
	
		int index=hashFunction(k);
		

		Value oldValue=null;
		
		if(v==null) {
			
		return standardPut(k,v);
		
		}
		
		
		
	if(this.HashTable[index]==null) { //make sure to account for a document being overwritten

		
		
		return putIndexNull(k, v);
		}
	
	else if(this.HashTable[index]!=null){
	
	return standardPut(k,v);
	}
	return oldValue;
	
	}
	
	
	
	private Node<?, ?>[] newHashTable() {

HashTableImpl temp = new HashTableImpl();
		temp.setnewArrayLength(this.HashTable.length*2);

	for(int i=0; i<this.HashTable.length; i++) {
		if(this.HashTable[i]!=null) {
			Node pointer=this.HashTable[i];
			while(pointer!=null) {
				temp.put(pointer.key, pointer.data);
				pointer=pointer.next;
			}
		}
	}


	
	this.size=temp.size();
		return temp.getHashTable();
	}

	
	
	private int hashCode(Key k) {
		final int prime = 31;
		int result = 1;
		result = prime * result + k.hashCode();
		
		return result;
	}

	private void setnewArrayLength(int len) {
		this.size=len;
		this.HashTable=new Node[len];
	}
	private int hashFunction(Key k) {
			return (hashCode(k) & 0x7fffffff) % this.HashTable.length;
		
	}
	
	
	private Node<?,?>[] getHashTable(){
		return this.HashTable;
	}
	
	
	
	private int size() {
		return this.size;
	}
	

}
