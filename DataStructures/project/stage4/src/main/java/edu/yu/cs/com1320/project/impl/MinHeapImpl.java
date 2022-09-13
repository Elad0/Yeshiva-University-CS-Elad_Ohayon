package edu.yu.cs.com1320.project.impl;

import java.util.NoSuchElementException;

import edu.yu.cs.com1320.project.MinHeap;



public class MinHeapImpl <E extends Comparable<E>> extends MinHeap<E> {
    
	public MinHeapImpl() {
	
		super.elements = (E[]) new Comparable[10];
	}
	
	@Override
	public void reHeapify(Comparable element) {
		
		int k= this.getArrayIndex(element);
		
		if(k==-1) {
			return;
		}
		downHeap(k);
		
		upHeap(k);
	
		
		
		
	/*while(k>1 && isLess(k, k/2 )) {	//k>=1
			this.swap(k/2, k);
			k=k/2;
		}*/
		/*//while(k>1 && isGreater(k, k/2 )) {	//verify this		//problem with is greater
			while(k>1 && isGreater(k/2, k )) {	
			this.swap(k, k/2);
			k=k/2;}
		*/
		
		
		}
	

	@Override
	protected int getArrayIndex(Comparable element) {
	
		for(int i=0; i<super.elements.length; i++) {
			if(super.elements[i]!=null) {
			
				if(super.elements[i].equals(element)) {	//extra code delete if not necessart
					return i;
				}
				
			
				
			}
			
		}
		
		return -1;
	}

	@Override
	protected void doubleArraySize() {
		E[] newArray=(E[]) new Object[super.elements.length*2];
		for(int i=0; i<super.elements.length; i++) {
			newArray[i]=super.elements[i];
			
		}
		super.elements=newArray;
	}
	 

}
