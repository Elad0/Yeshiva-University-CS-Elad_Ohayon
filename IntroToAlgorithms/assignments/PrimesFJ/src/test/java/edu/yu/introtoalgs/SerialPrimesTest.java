package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SerialPrimesTest {

	@Test
	void test() {
		SerialPrimes tst= new SerialPrimes();
	
	assertEquals(tst.nPrimesInRange(2, 25),9);
	assertEquals(2,tst.nPrimesInRange(2, 3));
	assertEquals(4,tst.nPrimesInRange(2, 10));
	assertEquals(25,tst.nPrimesInRange(2, 100));
	assertEquals(168,tst.nPrimesInRange(2, 1000));
	assertEquals(1229,tst.nPrimesInRange(2, 10000));
	assertEquals(1229,tst.nPrimesInRange(2, 10000));
	assertEquals(9592,tst.nPrimesInRange(2, 100000));
	assertEquals(78498,tst.nPrimesInRange(2, 1000000));
	assertEquals(664579,tst.nPrimesInRange(2, 10000000)); // 10,000,000
	
	
	
//		assertEquals(24739954287740860L,tst.nPrimesInRange(2, 1000000000000000000L));

		
	

	}

}
