package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrimesFJTest {

	//@Test
	void test() {
		PrimesFJ tst= new PrimesFJ();
		assertEquals(27,tst.nPrimesInRange(2, 103));
		
	assertEquals(tst.nPrimesInRange(2, 25),9);	
		assertEquals(2,tst.nPrimesInRange(2, 3));
		assertEquals(4,tst.nPrimesInRange(2, 10));
		assertEquals(25,tst.nPrimesInRange(2, 100));
	
		assertEquals(168,tst.nPrimesInRange(2, 1000));
	
		assertEquals(1229,tst.nPrimesInRange(2, 10000));
		
		assertEquals(9592,tst.nPrimesInRange(2, 100000));
		assertEquals(78498,tst.nPrimesInRange(2, 1000000));
		assertEquals(664579,tst.nPrimesInRange(2, 10000000));
		assertEquals(5761455L,tst.nPrimesInRange(2, 100000000));
		//assertEquals(50847534L,tst.nPrimesInRange(2, 1000000000));
	//	assertEquals(455052511L,tst.nPrimesInRange(2, 10000000000L));
		
	}
	
//	@Test
	void testHighest(){
		long num=800000000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	
	@Test
	void testHighest1(){
		long num=80000000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	@Test
	void testHighest2(){
		long num=40000000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	@Test
	void testHighest3(){
		long num=20000000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	@Test
	void testHighest4(){
		long num=10000000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	@Test
	void testHighest5(){
		long num=5000000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	@Test
	void testHighest6(){
		long num=2500000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	@Test
	void testHighest7(){
		long num=1250000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	//@Test
	void testHighestThreshold(){
		long num=100000000;
		PrimesFJ tst= new PrimesFJ();
		long st=System.currentTimeMillis();
		tst.nPrimesInRange(2, num);
		long end=System.currentTimeMillis();
		long fjDiff=end-st;
		
		SerialPrimes nxt= new SerialPrimes();
		
		st=System.currentTimeMillis();
	//nxt.nPrimesInRange(2, num);
	end=System.currentTimeMillis();
	long serDiff=end-st;
	
	
	System.out.println("It took FJPrimes: "+ fjDiff+ " it took SerialPrimes: "+ serDiff+ " the ratio of improvement is: "+ serDiff/(double)fjDiff);
	}
	


}
