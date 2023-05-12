package edu.yu.da.tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.yu.da.DamConstruction;

class DamConstructionTest {
		//TESTING SOLVEIT
	  	
	  	@Test 
	  	void testSolveWithSingleDam() {
	  		DamConstruction dc = new DamConstruction(new int[] {50}, 100);
	  		assertEquals(100, dc.solve());
	  	}
	  	
	  	@Test 
	  	void testSolveDemo() {
	  		int[] inputArray= new int[] {750,250,500};
	  		Arrays.sort(inputArray);
	  		DamConstruction dc = new DamConstruction(inputArray, 1000);
	  		assertEquals(2000, dc.solve());
	  	}
	  	
		
		@Test 
	  	void testCostDemo2() {
			int[] dams = new int[]{20, 40, 80};
	  		DamConstruction dc = new DamConstruction(dams, 120);
	  		assertEquals(240, dc.solve());
	  	}
		@Test 
	  	void testCostDemo3() {
			int[] dams = new int[]{50,100};
	  		DamConstruction dc = new DamConstruction(dams, 200);
	  		assertEquals(300, dc.solve());
	  	}
		@Test 
	  	void testCostDemo4() {
			int[] dams = new int[]{50,100,150};
	  		DamConstruction dc = new DamConstruction(dams, 250);
	  		assertEquals(500, dc.solve());
	  	}
		@Test 
	  	void testCostDemo5() {
			int[] dams = new int[]{50, 75, 100, 125, 150};
	  		DamConstruction dc = new DamConstruction(dams, 300);
	  		assertEquals(650, dc.solve());
	  	}
		@Test
		void testCostDemo6() {
			int[] dams = new int[]{10, 20, 30, 40, 50};
			DamConstruction dc = new DamConstruction(dams, 250);
	  		assertEquals(370, dc.solve());
		}
		@Disabled
		@Test
		void testQuirkyDemo6() {
			int[] dams = new int[]{30, 50, 10, 40, 20};
			DamConstruction dc = new DamConstruction(dams, 250);
	  		assertEquals(370, dc.solve());
		}
		
		@Disabled
		@Test
		void testQuirkyTestDemo7() {
			 int[] dams = new int[]{50, 100, 75, 40};
			 DamConstruction dc = new DamConstruction(dams, 300);
			 assertEquals(495, dc.solve());
		}
		
		@Test 
		void testCostDemo7() {
			 int[] dams = new int[]{40,50,75,100};
			 DamConstruction dc = new DamConstruction(dams, 300);
			 assertEquals(495, dc.solve());
		}
		   
	  
		//TESTING COST
		@Test 
	  	void testCostDemo() {
	  		int[] inputArray= new int[] {750,250,500};
	  		DamConstruction dc = new DamConstruction(inputArray, 1000);
	  		assertEquals(1000+750+500, dc.cost(new int[] {750,250,500}));
	  	}
		  @Test 
		  	void testCutDemo1() {
				int[] dams = new int[]{50,100,150};
		  		DamConstruction dc = new DamConstruction(dams, 250);
		  		assertEquals(600, dc.cost(dams));
		  	}
		  
		  @Test 
		  	void testCutDemo12() {
				int[] dams = new int[]{100,50,150};
		  		DamConstruction dc = new DamConstruction(dams, 250);
		  		assertEquals(500, dc.cost(dams));
		  	}
		 
		@Test
	  	void testCutDemo2() {
	  		int[] dams = new int[]{10, 20, 30, 40, 50};
	  		DamConstruction dc = new DamConstruction(dams, 250);
	  		assertEquals(250+240+230+220+210, dc.cost(dams));
	  	}
		@Test
		void testCutDemo21() {
	  		int[] dams = new int[]{50, 40, 30, 20, 10};
	  		DamConstruction dc = new DamConstruction(dams, 250);
	  		assertEquals(250+50+40+30+20, dc.cost(dams));
	  	}
		@Test 
		void testCutDemo22(){
			int[] dams = new int[]{30, 50, 10, 40, 20};
	  		DamConstruction dc = new DamConstruction(dams, 250);
	  		assertEquals(250+220+30+20+20, dc.cost(dams));
		}
	  	
		//Error Testing
		   @Test
		    void testEmptyDamLocations() {
		        assertThrows(IllegalArgumentException.class, () -> new DamConstruction(new int[] {}, 10));
		    }
		   	@Test
		   void negativeDamLocation() {
		        assertThrows(IllegalArgumentException.class, () -> new DamConstruction(new int[] {0}, 10));
		        assertThrows(IllegalArgumentException.class, () -> new DamConstruction(new int[] {-1}, 10));
		        assertEquals(new DamConstruction(new int[] {1}, 10).solve(),10);
		    }
		   	
		    @Test
		    void testToolargeDamLocation() {
		        assertThrows(IllegalArgumentException.class, () -> new DamConstruction(new int[] {1,2,3,10}, 10));
		    }
	

	  }



