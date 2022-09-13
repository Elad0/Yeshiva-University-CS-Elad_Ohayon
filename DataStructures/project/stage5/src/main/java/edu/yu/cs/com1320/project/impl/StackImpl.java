package edu.yu.cs.com1320.project.impl;


public class StackImpl<T> implements edu.yu.cs.com1320.project.Stack<T>{
	
	class Node<T>{
	
	private T data;
	private Node next;
	
	
	private Node(T action){
		
		this.data=action;
		this.next=null;
	}
	}
	
	private Node<T> Stack;
	private int size;
	private Node head;
	
	public StackImpl() {
		this.Stack= null;
		this.head=null;
		this.size=0;
	}
	
	@Override
	public void push(T element) {
		
		
		Node newNode= new Node(element);
		
		if(this.size==0) {
			this.head=newNode;
			
		}
		else {
			
			newNode.next=this.head;
			this.head=newNode;
		}
		
		this.size++;
	}

	@Override
	public T pop() {
		if (this.head==null){
			return null;
		}
		this.size=this.size-1;
	
		T toReturn= (T) this.head.data;
		this.head=this.head.next;
		return toReturn;
	}

	@Override
	public T peek() {
		if(this.size==0) {
			return null;
		}
		else {
		return (T) this.head.data;
		}
	}

	@Override
	public int size() {
		
		return this.size;
	}

}
