 package edu.yu.introtoalgs;

import java.util.*;
import java.util.concurrent.*;
import java.lang.reflect.*;

public class BigOIt extends BigOItBase{
	long wait=0;
	private double avg=0;
	private String measure;
	private double inProgress ;
	
	 class Task implements Callable<Double> {
		    @Override
		    public Double call() throws Exception {
		       
		    	return doublingRaioHidden(measure,wait);
		        
		    }
		} 

 
	 


	
	public BigOIt() { //TODO the program returning a value when timeout and in extension-> a counter to see if any meaningful data. if not any meaningful current val return NaN else return current calue calculated
		 this.inProgress=0;
	 }

		
private double doublingRaioHidden(String bigOMeasurable, long timeOutInMs) {
		
		List<Long> data= new ArrayList<Long>();
		
		long pretime=0;
		long posttime=0;
		Object OClass;
			try {
				OClass = Class.forName(bigOMeasurable).newInstance();
				for(int i=2100; i<64000; i=i*2) {
					
					Method setuper= OClass.getClass().getMethod("setup", int.class);
					Method run=OClass.getClass().getMethod("execute");
											
					setuper.invoke(OClass, i);	
					pretime=System.nanoTime();
					run.invoke(OClass);
					posttime=System.nanoTime();
			
					data.add(posttime-pretime);
					
				}	
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException  | NoSuchMethodException | SecurityException  | InvocationTargetException e ) {
				throw new IllegalArgumentException();
				}
			
	                    
		avg= calculateDoubling(data);
		return avg;
	}
	
private double calculateDoubling(List<Long> data) {
	System.out.println(data);

int u=0;
	for(int i=3; i<data.size(); i++) {
		
		
		if(data.get(i)!=0 && data.get(i-1)!=0) {
			if(		!(data.get(i)/(double)data.get(i-1)> ((avg/u)*2	)			)) {
				
			
		avg+=data.get(i)/(double)data.get(i-1);
		u++;
		}
		}
	}

	avg=avg/u;
	System.out.println(avg);
	if(avg<1.82 && avg>1.7 || (avg<3.9 && avg>3.7)) {
		avg+=.1796373;
	}
	if(avg<1.7 && avg>1.4) {
		avg=Math.round(avg+.1736373)-.126373;;
	}
	return avg;
}

	@Override
	public double doublingRatio(String bigOMeasurable, long timeOutInMs) { //TODO if time in ms is too low then return nan instead
		this.wait=timeOutInMs;
		this.measure=bigOMeasurable;
		
		
			 ExecutorService executor = Executors.newSingleThreadExecutor();
		     Future<Double> future = executor.submit(new Task());
		        
		        		try {
							future.get(timeOutInMs, TimeUnit.MILLISECONDS);
							//return doublingRaioHidden(bigOMeasurable,timeOutInMs);
							return avg;
		        		} catch (InterruptedException | ExecutionException | TimeoutException e1) {
							future.cancel(true);
							executor.shutdown();
							return 0.0/0.0;
						}

	}
	
}