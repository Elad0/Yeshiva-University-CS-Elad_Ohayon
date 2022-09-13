package edu.yu.introtoalgs;

public class BigON3 extends BigOMeasurable{
	public BigON3() {
		
	}
	public static void main (String args[]) {
		
	}
	@Override
	public void execute() {
		int[]a= new int[n];
		//for(int i=0; i<n; i++) {
			//a[i]=(int) (Math.random()*49+1);
		//}
		//System.out.println(n);
		int N = a.length;
		 int cnt = 0;
		 for (int i = 0; i < N; i++) {
		 for (int j = i+1; j < N; j++) {
		 for (int k = j+1; k < N; k++) {
		 if (a[i] + a[j] + a[k] == 0) {
		 cnt++;
		 }
		 }
		 }
		 }
	
	}

		
			}
												
			
		
		
	

