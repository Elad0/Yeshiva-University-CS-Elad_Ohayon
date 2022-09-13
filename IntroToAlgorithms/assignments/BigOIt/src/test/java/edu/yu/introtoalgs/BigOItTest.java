package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BigOItTest {
	@Test
	void BIGOConstantTest() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigOConstant", 40000)+"		O of Constant ");
	
	}
	
	@Test
	void BIGONLogNTest() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigOLogN", 40000)+"		O of Log N");
	
	}

	@Test
	void testON() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON", 40000)+"		0 of N");
	
	}
	@Test
	void testON2() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON2", 40000)+"		O of N^2");
	
	}
	@Test
	void testON3() {
		BigOIt h= new BigOIt();
		
		System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON3", 40000)+"		O of N^3");
	
	}
	
	//@Test
	void BIGON4Test() {
		BigOIt h= new BigOIt();
		
	//	System.out.println(h.doublingRatio("edu.yu.introtoalgs.BigON4", 40000)+"		O of N^4");
	
	}
	
	


}
