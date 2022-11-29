package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class XenoHematologyTest {

	
	
@Test void sg() {
	XenoHematology gary= new XenoHematology(20);
	gary.setCompatible(1, 3);
	assertTrue(gary.areCompatible(1, 3));
	assertFalse(gary.areCompatible(4, 3));
	
	gary.setCompatible(3, 4);
	assertTrue(gary.areCompatible(1, 4));
	assertFalse(gary.areIncompatible(1, 3));
	
	gary.setCompatible(8, 9);
	assertFalse(gary.areIncompatible(1, 8));
}
	@Test
	void test() {
		int max=(int) Math.pow(2, 27);
		XenoHematology gary= new XenoHematology(20);	//TODO test find when elements not in order -> WORKS
		gary.setCompatible(1, 3);
	
		
	//	assertTrue(gary.areCompatible(1, 3));
		//assertFalse(gary.areCompatible(1, 5));
		//assertFalse(gary.areIncompatible(1, 5));
		gary.setCompatible(3, 5);	
		
		gary.setCompatible(4, 6);
		
gary.setCompatible(8, 10);
	
		assertFalse(gary.areCompatible(3, 4));
		assertFalse(gary.areIncompatible(3, 4));
		
		assertFalse(gary.areIncompatible(1, 5));
		assertTrue(gary.areCompatible(3, 5));
		
		gary.setIncompatible(1, 5);
		assertFalse(gary.areIncompatible(1, 5));
		assertTrue(gary.areCompatible(3, 5));
		assertTrue(gary.areCompatible(1, 5));
		
		gary.setIncompatible(9, 5);
		gary.setCompatible(5, 9);
		
		assertFalse(gary.areCompatible(9, 5));
	
		
		//System.out.println(gary.haveData);
		//System.out.println(gary.dataBetween);
		
		assertTrue(gary.areIncompatible(9, 5));
		assertTrue(gary.areIncompatible(9, 3));
		
		gary.setIncompatible(16, 9);
		assertTrue(gary.areIncompatible(16, 9));
		assertTrue(gary.areCompatible(3, 16));
		gary.setIncompatible(16, 7);
		gary.setIncompatible(9, 7);
		
	
	}
	
	//also test adding in 2 new vals
@Test
	void realTest1() {
		XenoHematology t1= new XenoHematology(10);
	t1.setCompatible(1, 2);
	assertTrue(t1.areCompatible(1, 2));
	t1.setIncompatible(1, 2);
	assertTrue(t1.areCompatible(1, 2));
	assertFalse(t1.areIncompatible(1, 2));
	t1.setCompatible(1, 5);
	assertTrue(t1.areCompatible(5, 2));
	assertTrue(t1.areCompatible(5, 2));
	assertFalse(t1.areIncompatible(6, 8));
				//If you set 2 comptaible and then incompatible then test fails
	
				//can use a list of sets to put in values that havent been decided yet
	t1.setCompatible(6, 8);
//	System.out.println(t1.set[0]);
//	System.out.println(t1.set[1]);
	
	t1.setIncompatible(1, 8);
	assertTrue(t1.areIncompatible(1, 8));
	assertFalse(t1.areCompatible(1, 8));	//redo program where make new group everytime. basically since only 2 options then if not in List means no data hence -> incompatible
	
	
	assertTrue(t1.areIncompatible(1,6));
	
	assertFalse(t1.areCompatible(1, 6));
	
	assertTrue(t1.areIncompatible(5, 8));
	assertFalse(t1.areCompatible(5, 6));
	
	t1.setCompatible(8, 6);
	

	

	assertFalse(t1.areCompatible(5, 6));
	assertFalse(t1.areCompatible(1, 8));
assertTrue(t1.areIncompatible(6, 2));
t1.setIncompatible(6, 7);
	
	
	
	}

@Test void TestCompatibility2ThingsNoData(){
	XenoHematology hm= new XenoHematology(50);
	assertFalse(hm.areIncompatible(6, 7));		//Test compatib
	assertFalse(hm.areCompatible(7, 6));
	assertFalse(hm.areIncompatible(7, 6));
	assertFalse(hm.areCompatible(6, 7));
	assertTrue(hm.areCompatible(5, 5));
	assertFalse(hm.areIncompatible(5, 5));
	
}

@Test void basicCompatibilityTest(){
	XenoHematology hm= new XenoHematology(50);
hm.setCompatible(5, 6);
assertTrue(hm.areCompatible(5, 6));
assertFalse(hm.areIncompatible(5, 6));
}


@Test void basicIncompatibilityTest(){
	XenoHematology hm= new XenoHematology(50);
hm.setIncompatible(5, 6);
assertTrue(hm.areIncompatible(5, 6));
assertFalse(hm.areCompatible(5, 6));
}

@Test void NotenoughDataForOne(){
	XenoHematology hm= new XenoHematology(50);
hm.setCompatible(5, 6);
assertFalse(hm.areIncompatible(5, 8));
assertFalse(hm.areCompatible(8, 5));
}

@Test void MergeCompatiabilityTest(){
	XenoHematology hm= new XenoHematology(50);
hm.setCompatible(5, 6);
hm.setCompatible(7, 8);
hm.setCompatible(9, 7);
assertTrue(hm.areCompatible(8, 9));
hm.setCompatible(7, 33);
assertTrue(hm.areCompatible(33, 8));
assertTrue(hm.areCompatible(33, 9));
assertFalse(hm.areCompatible(5, 33));
assertFalse(hm.areCompatible(5, 33));
assertFalse(hm.areIncompatible(7, 33));
assertFalse(hm.areIncompatible(8, 33));
hm.setCompatible(5, 8);
assertTrue(hm.areCompatible(5, 33));
assertTrue(hm.areCompatible(5, 7));
assertTrue(hm.areCompatible(6, 9));
assertFalse(hm.areIncompatible(5, 6));
assertFalse(hm.areIncompatible(5, 33));
}
@Test void InCompatiabilityTest(){
	XenoHematology hm= new XenoHematology(50);
hm.setCompatible(5, 6);
hm.setIncompatible(5, 9);
assertTrue(hm.areIncompatible(5, 9));
assertFalse(hm.areCompatible(5, 9));

hm.setCompatible(9, 29);
assertTrue(hm.areIncompatible(6, 29));
assertFalse(hm.areCompatible(5, 29));

assertTrue(hm.areCompatible(9, 29));

hm.setIncompatible(29, 10);
hm.setCompatible(10, 49);
assertTrue(hm.areCompatible(5, 49));
assertTrue(hm.areIncompatible(49, 9));
}

@Test void mergeTestBoth() {
	XenoHematology hm= new XenoHematology(50);
	hm.setCompatible(9, 10);
	hm.setCompatible(49, 48);
	hm.setCompatible(19, 12);
	hm.setCompatible(34, 22);
	
	assertTrue(hm.areCompatible(9, 10));
	assertTrue(hm.areCompatible(19, 12));
	assertFalse(hm.areIncompatible(49, 48));
	
	assertFalse(hm.areCompatible(12, 49));
	assertFalse(hm.areIncompatible(19, 48));
	
	
	hm.setCompatible(9, 49);
	assertTrue(hm.areCompatible(9, 10));
	assertTrue(hm.areCompatible(49, 10));
	assertTrue(hm.areCompatible(48, 10));
	assertTrue(hm.areCompatible(49, 9));
	assertTrue(hm.areCompatible(48, 9));
	assertFalse(hm.areIncompatible(49, 9));
	
	hm.setCompatible(19, 34);
	
	hm.setIncompatible(34, 9);
	assertTrue(hm.areIncompatible(22, 48));
	assertTrue(hm.areIncompatible(34, 9));
	assertFalse(hm.areCompatible(22, 48));
	assertFalse(hm.areCompatible(34, 48));
	
	hm.setCompatible(19, 34);
	assertTrue(hm.areIncompatible(22, 48));
	assertTrue(hm.areIncompatible(34, 9));
	assertFalse(hm.areCompatible(22, 48));
	assertFalse(hm.areCompatible(34, 48));
	
	
	hm.setCompatible(37, 38);
	hm.setCompatible(38, 41);
	hm.setCompatible(9, 41);
	assertTrue(hm.areCompatible(37, 10));
	assertFalse(hm.areIncompatible(37, 48));
	assertTrue(hm.areIncompatible(38, 22));
	
	
}


@Test void exceptions() {
	XenoHematology hm= new XenoHematology(50);
	assertThrows(IllegalArgumentException.class, ()->hm.setCompatible(-1, 3) );
	assertThrows(IllegalArgumentException.class, ()->hm.setIncompatible(-1, -1) );
	assertThrows(IllegalArgumentException.class, ()->hm.areIncompatible(-9, 1) );
	assertThrows(IllegalArgumentException.class, ()->hm.areCompatible(8, -1) );
	
	assertThrows(IllegalArgumentException.class, ()->hm.setCompatible(50, 3) );
	assertThrows(IllegalArgumentException.class, ()->hm.setIncompatible(-1, 51) );
	assertThrows(IllegalArgumentException.class, ()->hm.areIncompatible(50, 1) );
	assertThrows(IllegalArgumentException.class, ()->hm.areCompatible(80, -1) );
}

@Test void DooDooTest() {
	XenoHematology hm= new XenoHematology(50);
	hm.setCompatible(1, 2);
	hm.setCompatible(3, 4);
	hm.setIncompatible(1, 4);
	hm.setIncompatible(8, 3);
	assertTrue(hm.areCompatible(8, 1));
}
@Test void DooDooTest2() {
	XenoHematology hm= new XenoHematology(50);
	hm.setIncompatible(1, 2);
	hm.setIncompatible(4, 2);
	hm.setCompatible(2, 8);
	assertTrue(hm.areIncompatible(1, 8));
}

}
