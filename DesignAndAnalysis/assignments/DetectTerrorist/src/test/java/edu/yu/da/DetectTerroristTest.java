package edu.yu.da;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DetectTerroristTest {

	@Test
	void test() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{1,2,2,2,2,2,2});
	assertEquals(0, dt.getTerrorist());
	}
	@Test
	void test1() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,1,2,2,2});
	assertEquals(3, dt.getTerrorist());
	}

	@Test
	void test2() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,2,2,2,1});
	assertEquals(6, dt.getTerrorist());
	}
	
	@Test
	void test3() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});
	assertEquals(5, dt.getTerrorist());
	}
	@Test
	void test4() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,2,1,2,2});
	assertEquals(4, dt.getTerrorist());
	}
	@Test
	void test5() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,1,2,2,2,2});
	assertEquals(2, dt.getTerrorist());
	}
	@Test
	void test6() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,1,2,2,2,2,2});
	assertEquals(1, dt.getTerrorist());
	}

	@Test
	void test41() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{1,2,2,2});
	assertEquals(0, dt.getTerrorist());
	}
	@Test
	void test42() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,1,2,2});
	assertEquals(1, dt.getTerrorist());
	}
	@Test
	void test43() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,1,2});
	assertEquals(2, dt.getTerrorist());
	}
	@Test
	void test44() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,0});
	assertEquals(3, dt.getTerrorist());
	}
	@Test
	void test45() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,2});
	assertEquals(-1, dt.getTerrorist());
	}
	@Test
	void test55() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,2,2});
	assertEquals(-1, dt.getTerrorist());
	}
	
	@Test
	void testrandomLength() {
		int i= (int) (Math.random()*90000);
		int[] arr= new int[i];
		int k= (int) (Math.random()*90000);
		 while(k>i) {
			  k= (int) (Math.random()*90000);
		 }
		arr[k]=-1;
	DetectTerrorist dt= new DetectTerrorist(arr);
	assertEquals(k, dt.getTerrorist());
	}
	
	@Test
	void testsecondtolast() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2});
	assertEquals(20, dt.getTerrorist());
	}

	@Test
	void testarrlength3Mid() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,1,2});
	assertEquals(1, dt.getTerrorist());
	}
	@Test
	void testarrlength3Node() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2});
	assertEquals(-1, dt.getTerrorist());
	}
	@Test
	void testarrlength3Start() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{1,2,2});
	assertEquals(0, dt.getTerrorist());
	}
	@Test
	void testarrlength3End() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,1});
	assertEquals(2, dt.getTerrorist());
	}
	@Test
	void noTerrorist() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});
	assertEquals(-1, dt.getTerrorist());
	}
	@Test
	void testarrlen() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2});
	assertEquals(-1, dt.getTerrorist());
	}
	
	//@Test
	void testidk() {
	DetectTerrorist dt= new DetectTerrorist(new int[]{2,1});
	assertEquals(1, dt.getTerrorist());
	}


}
