package edu.yu.introtoalgs;

public class BigON5Test extends BigOMeasurable{
	public BigON5Test() {
		
	}
	public static void main (String args[]) {
		
	}
	@Override
	public void execute() {
		int[]a= new int[n];
		//for(int i=0; i<n; i++) {
			//a[i]=(int) (Math.random()*49+1);
		//}
	
		int N = a.length;
		 int cnt = 0;
		 for (int i = 0; i < N; i++) {
		 for (int j = i+1; j < N; j++) {
		 for (int k = j+1; k < N; k++) {
		for( int z=k+1; z<N; z++) {
		for( int o=z+1; o<N; o++) {
		 if (a[i] + a[j] + a[k] +a[z]+a[o]== 0) {
		 cnt++;
		 }
		 }
		 }
		 }
		 }
		
		 }
	}

		
			}
												
			
		
		
	

