package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


class WealthTransferTest {

	
@Test
	void demoTest() {	//Test Depth of 1 with different varieties passed
		WealthTransfer t= new WealthTransfer(10);
		t.intendToTransferWealth(1, 3, 50, false);
		t.intendToTransferWealth(1, 4, 50, false);
		t.setRequiredWealth(3, 100);
		t.setRequiredWealth(4, 100);
		assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,200.0000);
	}

@Test
void demoTestWith1Donatingto3Children() {	//Test Depth of 1 with different varieties passed and 3 children
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 38, false);
	t.intendToTransferWealth(1, 3, 22, false);
	t.intendToTransferWealth(1, 4, 40, false);
	t.setRequiredWealth(2, 1000);
	t.setRequiredWealth(3, 9500);
	t.setRequiredWealth(4, 100);
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,43181.8182);

}

@Test
void demoTestWith1Donatingto4Children() {	//Test Depth of 1 with different varieties passed and 3 children
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 17, false);
	t.intendToTransferWealth(1, 3, 21, false);
	t.intendToTransferWealth(1, 4, 44, false);
	t.intendToTransferWealth(1, 5, 18, false);
	t.setRequiredWealth(2, 100);
	t.setRequiredWealth(3, 1000);
	t.setRequiredWealth(4, 2100);
	t.setRequiredWealth(5, 100);
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d, 4772.7273);
}
@Test
void test2Pairs() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 3, 25, false);
	t.intendToTransferWealth(1, 4, 75, false);
	
	t.intendToTransferWealth(3, 6, 99, false);
	t.intendToTransferWealth(3, 9, 1, false);
	
	t.setRequiredWealth(6, 100);
	t.setRequiredWealth(9, 250);
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d, 100000.0000);
}

@Test
void test2PairsWhere4RecievesToo() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 3, 75, false);
	t.intendToTransferWealth(1, 4, 25, false);
	
	t.intendToTransferWealth(3, 6, 60, false);
	t.intendToTransferWealth(3, 9, 40, false);
	
	t.setRequiredWealth(6, 100);
	t.setRequiredWealth(9, 250);
	t.setRequiredWealth(4, 209);
	
	double amnt=836;
	assert(amnt*.25>=209); //for 4
	assert(amnt*.60*.75>100); //6
	assert(amnt*.40*.75>250); //9
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d, 836);
}


@Test
void test2Pairstest2() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 3, 35, false);
	t.intendToTransferWealth(1, 4, 65, false);
	
	t.intendToTransferWealth(3, 6, 45, false);
	t.intendToTransferWealth(3, 9, 55, false);
	
	
	
	t.setRequiredWealth(6, 100);
	t.setRequiredWealth(9, 350);
	
	t.intendToTransferWealth(4, 8, 100, false);
	t.setRequiredWealth(8, 2000);
	
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,3076.9231);
}

@Test
void Mirroedtest2Pairstest2() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 3, 65, false);
	t.intendToTransferWealth(1, 4, 35, false);
	t.intendToTransferWealth(3, 6, 45, false);
	t.intendToTransferWealth(3, 9, 55, false);
	
	
	
	t.setRequiredWealth(6, 100);
	t.setRequiredWealth(9, 350);
	
	t.intendToTransferWealth(4, 8, 100, false);
	t.setRequiredWealth(8, 30);
	
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,979.0210);
}


@Test
void test2PairsCompleteTree() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 3, 50, false);
	t.intendToTransferWealth(1, 4, 50, false);
	
	t.intendToTransferWealth(3, 6, 45, false);
	t.setRequiredWealth(6, 70);
	
	t.intendToTransferWealth(3, 9, 55, false);
	t.setRequiredWealth(9, 110);
	
	t.intendToTransferWealth(4, 5, 10, false);
	t.setRequiredWealth(5, 450);
	
	t.intendToTransferWealth(4, 8, 90, false);
	t.setRequiredWealth(8, 230);
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d, 9000.0000);
}

@Test
void testDepth3RandomShape() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 3, 50, false);
	t.intendToTransferWealth(1, 4, 50, false);
	
	t.intendToTransferWealth(3, 6, 45, false);
	t.setRequiredWealth(6, 70);
	
	t.intendToTransferWealth(3, 9, 55, false);
	t.setRequiredWealth(9, 110);
	
	t.intendToTransferWealth(4, 5, 10, false);
	t.setRequiredWealth(5, 13);
	
	t.intendToTransferWealth(4, 8, 90, false);

	
	t.intendToTransferWealth(8, 10, 18, false);
	t.setRequiredWealth(10, 517);
	
	t.intendToTransferWealth(8, 7, 82, false);
	t.setRequiredWealth(7, 100);
	
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d, 6382.7160);
}
@Test
void testWealthDoubledTest1(){
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 5, 100, true);
	t.setRequiredWealth(5, 49);
	assertEquals(7,t.solveIt());
}


/**
 * Test doubling wealthtransfered when one child doubled
 */
@Test
void testWealthDoubledTest2(){
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 5, 75, true);
	t.setRequiredWealth(5, 1000);
	
	t.intendToTransferWealth(1, 3, 25, false);
	t.setRequiredWealth(3, 1);
	assertEquals(42.16370213557839,t.solveIt());
	
	//Test to make sure in right order
	t=new WealthTransfer(10);
	t.intendToTransferWealth(1, 5, 50, true);
	t.intendToTransferWealth(1, 6, 50, false);
	t.setRequiredWealth(5, 625);
	assert(t.solveIt()==50);
}


/**
 * Test doubling wealthtransfered when both child doubled
 */
@Test
void testWealthDoubledTest3(){
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 5, 73, true);
	t.setRequiredWealth(5, 1);
	
	t.intendToTransferWealth(1, 3, 27, true);
	t.setRequiredWealth(3, 1);
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,3.7037);
}

@Test
void testWealthDoubledTest4(){	//Same as test 3, diff numbers
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 5, 48, true);
	t.setRequiredWealth(5, 189);
	
	t.intendToTransferWealth(1, 3, 52, true);
	t.setRequiredWealth(3, 134);
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,28.6411);
}

@Test 
/* SETUP VARIATIONS OF WEALTH SQUARED */
void testWealthDoubledTestTreeDepthGreatterThan1(){
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 36, false);
	t.intendToTransferWealth(1, 3, 64, true);
	
	t.intendToTransferWealth(2, 4, 40, false);
	t.setRequiredWealth(4,3);
	
	t.intendToTransferWealth(2, 9, 60, false);
	t.setRequiredWealth(9,37);
	
	t.intendToTransferWealth(3, 5, 33, false);
	t.setRequiredWealth(5,243);
	
	t.intendToTransferWealth(3, 6, 67, false);
	t.setRequiredWealth(6,36);
	
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,171.2963);
}

@Test 
void testWealthDoubledTestTreeDepthGreatterThan12(){
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 36, true);
	t.intendToTransferWealth(1, 3, 64, true);
	
	t.intendToTransferWealth(2, 4, 40, true);
	t.setRequiredWealth(4,3);
	
	t.intendToTransferWealth(2, 9, 60, true);
	t.setRequiredWealth(9,37);
	
	t.intendToTransferWealth(3, 5, 33, true);
	t.setRequiredWealth(5,243);
	
	t.intendToTransferWealth(3, 6, 67, true);
	t.setRequiredWealth(6,36);
	
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,10.7390);
}

@Test 
void testWealthDoubledTestTreeDepthGreatterThan1df2(){ //same as above but doube true
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 3, 100, true);
	t.intendToTransferWealth(3, 10, 100, true);
	t.setRequiredWealth(10, 20);
	


	
	assertEquals((double)Math.round(t.solveIt() * 10000d) / 10000d,2.1147);
}

@Test void testThrowsIAEWhenOver100() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 90, false);
	t.intendToTransferWealth(1, 3, 10, false);
	
	try{
		t.intendToTransferWealth(1, 4, 10, false);
		fail("No Exception");}
	catch(IllegalArgumentException e) {
	}
			
}

@Test void testThrowsIAEWhenAlreadyhavepreviousIntendtoTransfer() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 90, false);
	try{
		t.intendToTransferWealth(1, 2, 10, false);
		fail("No Exception");}
			catch(IllegalArgumentException e) {
		}
}

@Test void testThrowsIAEWhenDonorHasRequiredWealth() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 90, false);
	try{
		t.setRequiredWealth(1, 10);
		fail("No Exception");}
			catch(IllegalArgumentException e) {
		}
}

@Test void testThrowsIAEWhenDonorHasRequiredWealth2() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 90, false);
t.setRequiredWealth(3, 3);
	try{
		t.intendToTransferWealth(3, 7, 10, false);
		fail("No Exception");}
			catch(IllegalArgumentException e) {
		}
}

@Test void testThrowsIAE2Parents() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 90, false);

	try{
		t.intendToTransferWealth(3, 2, 10, false);
		fail("No Exception");}
			catch(IllegalArgumentException e) {
		}
}

@Test void testDoesNotConnecttoONE() {
	WealthTransfer t= new WealthTransfer(10);
	t.intendToTransferWealth(1, 2, 100, false);
	t.intendToTransferWealth(2, 4, 100, false);
	t.intendToTransferWealth(7, 8, 100, false);

	t.setRequiredWealth(4, 30);
	t.setRequiredWealth(8, 100);
	try{
	t.solveIt();
		fail("No Exception");}
			catch(IllegalStateException e) {
		}
}

@Test
@Timeout(400)
 void setRequiredWealthSpecifiesMinimumNotExactAmount() {
	
	try {
		 final int populationSize = 3;
	
		 
		 final WealthTransfer wt = new WealthTransfer(populationSize);
		 wt.intendToTransferWealth(1, 2, 50, false);
		 wt.intendToTransferWealth(1, 3, 50, false);
		 wt.setRequiredWealth(2, 40);
		 wt.setRequiredWealth(3, 60);
	
		 final double solution = wt.solveIt();
	assertEquals(solution,120);
		
		 
}
	 catch (Exception e) {

		fail(e.toString()); }
	
}

@Test
@Timeout(400)
 void funnyTest() {
	WealthTransfer larry= new WealthTransfer(10);
	larry.intendToTransferWealth(1, 2, 67, false);
	larry.intendToTransferWealth(1, 3, 33, false);
	
	larry.intendToTransferWealth(3, 6, 76, false);
	larry.intendToTransferWealth(3, 7, 24, false);
	larry.intendToTransferWealth(2, 8, 90, false);
	larry.intendToTransferWealth(2, 9, 10, false);
	
	larry.setRequiredWealth(6, 200); //797.448
	larry.setRequiredWealth(7, 50); // 631.3131
	larry.setRequiredWealth(9, 45); //671.64
	larry.setRequiredWealth(8, 450); //746.268
	
	double amnt=797.4482;
	assert(amnt*.76*.33>200); //for 6
	assert(amnt*.24*.33>50); //for 5
	assert(amnt*.10*.67>45);// for 9
	assert(amnt*.90*.67>450);// for 8
	
	assertEquals((double)Math.round(larry.solveIt() * 10000d) / 10000d,797.4482);
}

@Test 
void callSetRequiredWithNoTransfer() {
	WealthTransfer t= new WealthTransfer(9);
	t.intendToTransferWealth(1, 5, 10, false);
	t.intendToTransferWealth(1, 4, 90, false);
	try {
	t.setRequiredWealth(9, 100);
	System.out.println(t.solveIt());
	fail("No exception thrown");
}
	catch(IllegalStateException e) {
		
	}
}


@Test 
void TestcallSetRequiredWithNoTransfer() {
	WealthTransfer t= new WealthTransfer(9);
	t.intendToTransferWealth(1, 5, 10, false);
	t.intendToTransferWealth(1, 4, 90, false);
	t.intendToTransferWealth(7, 8, 100, false);
	t.intendToTransferWealth(8, 9, 100, false);
	t.setRequiredWealth(9, 100);
	try {
		t.solveIt();
	fail("No exception thrown");
}
	catch(IllegalStateException e) {
		
	}
}

}
