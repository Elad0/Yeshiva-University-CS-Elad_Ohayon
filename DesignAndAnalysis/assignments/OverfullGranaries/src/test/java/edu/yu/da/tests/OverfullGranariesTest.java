package edu.yu.da.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.yu.da.OverfullGranaries;


//TODO go through tests and add support for min cut
//TODO add some more tests with multiple verticies meaning shouldn't x be automatically added since they are all sources
class OverfullGranariesTest {
	private static double bushels= 10_000;
	
	@Test
	void BasicTest() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"X"}, new String[] {"A","B"});
			
		og.edgeExists("X", "A", 115);
		og.edgeExists("B", "A", 11);
		
		assertEquals(og.solveIt(),bushels/115);
		assertEquals(og.minCut(),(new ArrayList<String>(Arrays.asList(new String[] {"X"}))));

	}
	
	@Test
	void maxFlow2Test() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"X"}, new String[] {"A","B"});
			
		og.edgeExists("X", "A", 115);
		og.edgeExists("X", "B", 30);
		og.edgeExists("B", "A", 11);
		
		assertEquals(og.solveIt(),bushels/(115+30));
	}
	
	@Test void sequentialMaxFlowTest(){
		OverfullGranaries og= new OverfullGranaries(new String[] {"X","Y","Z","AA"}, new String[] {"A","B"});
		og.edgeExists("X", "Y", 10);
		og.edgeExists("Y", "Z", 10);
		og.edgeExists("Z", "AA", 10);
		og.edgeExists("AA", "A", 1);
		assertEquals(og.solveIt(), bushels);
		
	}
	
	@Test void sequentialMaxFlowTestButShortCutToReduceTime(){
		OverfullGranaries og= new OverfullGranaries(new String[] {"X","Y","Z","AA"}, new String[] {"A","B"});
		og.edgeExists("X", "Y", 10);
		og.edgeExists("Y", "Z", 10);
		og.edgeExists("Z", "AA", 10);
		og.graph.doubleArray();
		og.edgeExists("AA", "A", 1);
		og.edgeExists("Z", "B", (int) bushels);
		assertEquals(og.solveIt(), bushels/(bushels+1));
	}
	
	@Test void addUnkownNodePassesThroughITTest(){
		OverfullGranaries og= new OverfullGranaries(new String[] {"A", "B", "C"}, new String[] {"D","E","F"});
		og.edgeExists("D", "E", 10);
		og.edgeExists("A", "Z", 20);
		og.edgeExists("Z", "D", 20);
		
		assertEquals(og.solveIt(), bushels/20);
	}
	
	@Test void addUnknownNodeNotConnectedTest(){
		OverfullGranaries og= new OverfullGranaries(new String[] {"A", "B", "C"}, new String[] {"D","E","F"});
		og.edgeExists("A", "NONE", 10000);
		og.edgeExists("A", "D", 10);
		assertEquals(og.solveIt(), bushels/10);
	}
	
	@Test 
	void slidesMaxFlowTest() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"S"}, new String[] {"T"});
		og.edgeExists("S", "A", 10);
		og.edgeExists("S", "B", 5);
		og.edgeExists("S", "C", 15);
	
		og.edgeExists("A", "B", 4);
		og.edgeExists("B", "C", 4);
		
		og.edgeExists("C", "F", 16);
		og.edgeExists("B", "E", 8);
		og.edgeExists("A", "D", 9);
		
		og.edgeExists("F", "B", 6);
		og.edgeExists("A", "E", 15);
		
		og.edgeExists("D", "E", 15);
		og.edgeExists("E", "F", 15);
		
		og.edgeExists("D", "T", 16000);
		og.edgeExists("E", "T", 10);
		og.edgeExists("F", "T", 10);
		
		assertEquals(og.solveIt(), bushels/28);
		
		assert(og.minCut().equals(new ArrayList<String>(Arrays.asList(new String[] {"B","C","F","S"}))));
		
	}
	
	@Test
		void slides2Test() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"S"}, new String[] {"T"});
		og.edgeExists("S", "D", 100);
		og.edgeExists("S", "E", 100);
		
		og.edgeExists("E", "T", 100);
		og.edgeExists("D", "T", 100);
		
		og.edgeExists("D", "E", 1);
		
		assertEquals(og.solveIt(), bushels/	200);
		assertEquals(og.minCut(),(new ArrayList<String>(Arrays.asList(new String[] {"S"}))));
		
		
	}
	
	@Test 
	void Verticies3AllFLowingIntoSources() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"X","Y","Z"}, new String[] {"A","B","C"});
		og.edgeExists("X", "A", 11);
		og.edgeExists("Y", "B", 13);
		og.edgeExists("Z", "C", 26);
		assertEquals(og.solveIt(), bushels/(11+13+26));
		assertEquals(og.minCut(),(new ArrayList<String>(Arrays.asList(new String[] {"X","Y","Z"}))));
	}
	
	@Test 
	void Verticies3AllFLowingIntoSourcesYFLowToEachOTher() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"X","Y","Z"}, new String[] {"A","B","C"});
		og.edgeExists("X", "A", 11);
		og.edgeExists("Y", "B", 13);
		og.edgeExists("Z", "C", 26);
		
		og.edgeExists("A", "B", 10);
		
		og.edgeExists("C", "A", 110);
		assertEquals(og.solveIt(), bushels/(11+13+26));
		assertEquals(og.minCut(),new ArrayList<String>(Arrays.asList(new String[] {"X","Y","Z"})));
	}
	
	@Test 
	void randomTest() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"A","B","C"}, new String[] {"X","Y","Z"});
		og.edgeExists("A", "1", 3);
		og.edgeExists("A", "2", 3);
		og.edgeExists("B", "2", 3);
		og.edgeExists("B", "3", 2);
		og.edgeExists("C", "3", 2);
		og.edgeExists("C", "4", 1);
		
		og.edgeExists("1", "5", 2);
		og.edgeExists("1", "2", 2);
		og.edgeExists("2", "5",3);
		
		og.edgeExists("2", "3", 3);
		
		og.edgeExists("3", "6", 1);
		og.edgeExists("3", "4", 2);
		og.edgeExists("4", "6", 1);
		
		
		og.edgeExists("5", "X", 1);
		og.edgeExists("5", "Y", 2);
		og.edgeExists("6", "Y", 1);
		og.edgeExists("6", "Z", 2);
		
		assertEquals(og.solveIt(),bushels/5);
		
	}
	
	@Test 
	void linearTestMiddleMan() {
		OverfullGranaries og= new OverfullGranaries(new String[] {"A"}, new String[] {"Y"});
		og.edgeExists("A", "X", 30);
		og.edgeExists("A", "Z", 5);
		
		og.edgeExists("X", "Z", 30);
		
		og.edgeExists("X", "Y", 6);
		og.edgeExists("Z", "Y", 20);
		
		assertEquals(og.solveIt(), bushels/26);
		
		assertEquals(og.minCut(), new ArrayList<String>(Arrays.asList(new String[] {"A","X","Z"})));
		
	}
	@Test
	void demoTest() {
		
		   String[] X = new String[]{"A","B","C"};
	        String[] Y = new String[]{"D","E"};
	        OverfullGranaries ofg = new OverfullGranaries(X, Y);
	        ofg.edgeExists("B", "F", 2000);
	        ofg.edgeExists("F", "D", 3000);
	        ofg.edgeExists("C", "E", 5000);
	        assertEquals(ofg.solveIt(),1.4285714285714286);
	        assertEquals(ofg.minCut(), new ArrayList<String>(Arrays.asList(new String[] {"A","B","C"})));
	}
}
