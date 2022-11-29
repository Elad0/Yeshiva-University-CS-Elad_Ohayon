package edu.yu.introtoalgs;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import junit.runner.Version;


//-Djava/compiler=none
class BigOItTest {
		@Test
	 void BIGOConstantTest() {
			System.out.println(Version.id());
		BigOIt h= new BigOIt();
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigOConstantTest", 900)+"			O of Constant ");
	
	}
	
	@Test
	  void BIGONLogNTest() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigOLogNTest", 1000)+"			O of Log N");

	}

	@Test void timeout(){
		BigOIt h= new BigOIt();
		System.out.println(Version.id());
		 
	}
	
	
	
	@Test
	  void testON() {
	
		BigOIt h= new BigOIt();
			
System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigONTest",1000 )+"			0 of N");
	
	}
	@Test
	  void testON2() {
		
		BigOIt h= new BigOIt();
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON2Test", 4000)+"			O of N^2");
	
	}
	
	
	@Test
	  void testON3() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON3Test", 40000)+"			O of N^3");
	
	}
	
		@Test
	  void BIGON4Test() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON4Test", 40000)+"			O of N^4");
		
		
		
		
	}
	
	@Test
		  void BIGON5Test() {
			BigOIt h= new BigOIt();
			
			System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON5Test", 40000)+"				O of N^5");
			
				
					//System.out.println(" ON5 HERE"+ h.calRatio("edu.yu.introtoalgs.BigON5Test", 3000));
				
				
		
		}

	/*@Test
	void sandbox() {
		BigOIt h= new BigOIt();
		List<Long> data = new ArrayList<Long>();
		data.add((long) .75);
		data.add((long) 1);
		data.add((long) 1);
		data.add((long) 1);
		data.add((long) 1);
		data.add((long) 1);
		data.add((long) 1);
		data.add((long) 1);
		data.add((long) 1);
		System.out.println(h.calRatio(data, 2));
	}*/


}
