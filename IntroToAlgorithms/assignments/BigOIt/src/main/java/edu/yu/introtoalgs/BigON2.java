package edu.yu.introtoalgs;

public class BigON2 extends BigOMeasurable{
	public BigON2() {
		
	}
	public static void main (String args[]) {
		
	}
	@Override
	public void execute() {
		boolean b=false;
		//Integer[] array= new Integer[super.n+1];
		int[] array= new int[n];
		for(int i=0; i<n; i++) {
			array[i]=(int) (Math.random()*49+1);
		}
		
		int counter=0;
		for(int k=0; k<array.length; k++) {
				for(int l=0; l<array.length; l++) {
					if(array[l]+array[k]==0) {
						counter++;
					}
		
			}
		
			}
			}
												
			
		}
		
	

