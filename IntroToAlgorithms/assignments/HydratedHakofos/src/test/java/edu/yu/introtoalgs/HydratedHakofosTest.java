package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

class HydratedHakofosTest {

	@Test
	void TestFIRSTTableSuccessIncrementingValues() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,90,10,70}, new int[] {0,0,0,0,100,14});
		assert(k==1);
	
		
	}
	@Test
	void TESTFAIL() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,90,10,0}, new int[] {0,0,0,0,100,14});
		assert(k==-1);
	
		
	}
	@Test
	void TESTINVALIDSETUP() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,90,10,70}, new int[] {0,0,0,0,1000,14});
		assert(k==-1);
	}
	@Test
	void TESTINVALIDSETUPd() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,700,200,90,10,70}, new int[] {0,0,0,0,1000,14});
		assert(k==1);
	}
	@Test
	void TESTIdenticalArrays() {	//TODO add more cases
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {10,90,80,70,60}, new int[] {10,90,80,70,60});	//TODO what if both arrays are exactly indentical? should be first table no?
		assert(k==1);
	}
	@Test
	void invalidatEND() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,0,0}, new int[] {0,0,0,0,10});
		assert(k==-1);
	}
	@Test
	void invalidatENfsfD() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,0,10}, new int[] {0,0,0,0,0});
		assert(k==1);
	}
	@Test
	void ValidEND() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,0,11}, new int[] {0,0,0,10,0});
		assert(k==5);
	}
	@Test
	void SecondTableValid() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,1000,0,0,0}, new int[] {0,100,0,0,0});
		
		assert(k==1);
		 k=sm.doIt(new int[] {0,1000,0,0,0}, new int[] {10,0,0,0,0});

		assert(k==2);
		
	}
	@Test
	void TESTFIRSTTABLEVALIDADDINGWATER() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {90,10,0,0,0}, new int[] {0,100,0,0,0});
		assertEquals(1,k);
		
	}
	@Test
	void anotherTest() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,100,90,80,70}, new int[] {0,100,90,80,70});
		assert(k==1);
	}
	@Test
	void Table4Test() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,8000,0}, new int[] {0,100,90,80,70});
		assert(k==4);

	}
	
	@Test
	void ZeroTest() {
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,0,0,0,0}, new int[] {0,0,0,0,0});
		assert(k==1);

	}
	@Test
	void WaterAvalibleShorter() {
		HydratedHakofos sm= new HydratedHakofos();
		assertThrows(IllegalArgumentException.class, ()->sm.doIt(new int[] {0,10}, new int[] {0,0,0,0,0}) );
		
	}
	@Test
	void WaterRequiredShorter() {
		HydratedHakofos sm= new HydratedHakofos();
		assertThrows(IllegalArgumentException.class, ()->sm.doIt(new int[] {0,10}, new int[] {0}) );
		assertThrows(IllegalArgumentException.class, ()->sm.doIt(new int[] {0,10}, new int[] {0,30,90}) );
		
	
	}
	@Test
	void nullArray1() {
		HydratedHakofos sm= new HydratedHakofos();
		assertThrows(IllegalArgumentException.class, ()->sm.doIt(new int[] {}, new int[] {}) );
	
	}

	@Test
	void nullArray3() {
		HydratedHakofos sm= new HydratedHakofos();
		int[] waterReq=new int[50];
		waterReq[0]=9;
		waterReq[1]=10;
		int[] waterp=new int[50];
		waterp[10]=93;
		waterp[1]=104;
		int k=sm.doIt(waterReq, waterp);
		assertEquals(-1,k);
		k=sm.doIt(waterp, waterReq);
		assertEquals(2,k);
	
	
}
	@Test void works(){
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {0,1,2,2,5,4,5}, new int[] {0,1,2,3,4,5,4});
		assertEquals(7,k);
	}
	@Test void fails(){
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {1,1,2,2,5,3,5}, new int[] {0,1,2,3,4,5,4});
		assertEquals(7,k);
	}
	@Test void theory(){
		HydratedHakofos sm= new HydratedHakofos();
		int k=sm.doIt(new int[] {1,1,2,2}, new int[] {1,1,20,2});
		assertEquals(-1,k);
	}

	
}
