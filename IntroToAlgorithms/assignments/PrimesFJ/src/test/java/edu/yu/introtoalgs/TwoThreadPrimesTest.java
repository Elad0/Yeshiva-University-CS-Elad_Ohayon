package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TwoThreadPrimesTest {

	void test() {
		TwoThreadPrimes tst= new TwoThreadPrimes();
	SerialPrimes base= new SerialPrimes();
	assertEquals(tst.nPrimesInRange(2, 25),9);

		
		
		
		
		
		
		
	assertEquals(2,tst.nPrimesInRange(2, 3));
	assertEquals(4,tst.nPrimesInRange(2, 10));
	assertEquals(25,tst.nPrimesInRange(2, 100));
	assertEquals(168,tst.nPrimesInRange(2, 1000));
	assertEquals(1229,tst.nPrimesInRange(2, 10000));
	assertEquals(1229,tst.nPrimesInRange(2, 10000));
	assertEquals(9592,tst.nPrimesInRange(2, 100000));
	assertEquals(78498,tst.nPrimesInRange(2, 1000000));
	assertEquals(664579,tst.nPrimesInRange(2, 10000000));



	


		
	

	}
	
	
	@Test void tst06(){
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 156250

);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 156250

);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ duration/(double)reg);
	
	}
	
	
	@Test void tst05(){
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 312500
);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 312500
);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ duration/(double)reg);
	
	}
	
	
	@Test void tst04(){
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 625000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 625000);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ duration/(double)reg);
	
	}
	
	
	@Test void tst03(){
		System.err.println("1250000 ");
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 1250000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 1250000);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ duration/(double)reg);
	
	}
	
	
	@Test void tst02(){
		System.out.println("2500000");
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 2500000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 2500000);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ reg/(double)duration);
	
	}
	
	
	
	@Test void tst01(){
		System.out.println("5000000");
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 5000000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 5000000);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ reg/(double)duration);
	
	}
	
	
	
	@Test void tst0(){
		System.out.println("10000000");
		SerialPrimes base= new SerialPrimes();
		long nor=System.currentTimeMillis();
		base.nPrimesInRange(2, 10000000);
		long endg=System.currentTimeMillis();
		long reg=endg-nor;
		
		
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 10000000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ reg/(double)duration);
	
	}
	
	
	
	
	@Test void tst1(){
		System.out.println("20000000");
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 20000000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 20000000);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ reg/(double)duration);
	
	}
	
	@Test void tst2(){
	System.out.println("40000000");
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 40000000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	SerialPrimes base= new SerialPrimes();
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 40000000);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ reg/(double)duration);
	
	}
	
	@Test void tst3(){System.out.println("80000000");
		SerialPrimes base= new SerialPrimes();
		TwoThreadPrimes tst= new TwoThreadPrimes();
		long start=System.currentTimeMillis();
	tst.nPrimesInRange(2, 80000000);
	long end=System.currentTimeMillis();
	long duration=end-start;
	
	long nor=System.currentTimeMillis();
	base.nPrimesInRange(2, 80000000);
	long endg=System.currentTimeMillis();
	long reg=endg-nor;
	System.out.println("It took TwoThread: "+ duration+ " it took SerialPrimes: "+ reg+ " the ratio of improvement is: "+ reg/(double)duration);
	
	}
	


}
