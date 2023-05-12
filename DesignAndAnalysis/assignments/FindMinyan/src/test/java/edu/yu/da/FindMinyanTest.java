package edu.yu.da;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;



class FindMinyanTest {


		@Test
		void testFindMinyanConstructorInvalidArgs() {
			assertThrows(IllegalArgumentException.class,()-> new FindMinyan(1));
			
		}
		
		@Test 
		void testAddHighWayArgs() {
			FindMinyan gary= new FindMinyan(10);
			
			assertThrows(IllegalArgumentException.class,()-> gary.addHighway(0, 8, 9));	//city1 less than 1
			assertThrows(IllegalArgumentException.class,()-> gary.addHighway(10, 9, 10));	//city1 equal city 2
			assertThrows(IllegalArgumentException.class,()-> gary.addHighway(11, 10, 9));	//city1 greater than n
			
			assertThrows(IllegalArgumentException.class,()-> gary.addHighway(2, 8, 0));	//city2 less than 1
			assertThrows(IllegalArgumentException.class,()-> gary.addHighway(10, 9, 10));	//city2 equal city1
			assertThrows(IllegalArgumentException.class,()-> gary.addHighway(8, 10,11));	//city2 greater than n
			
			assertThrows(IllegalArgumentException.class,()-> gary.addHighway(5, -1, 6)); //duration is nonnegative
			
			}		
		
		
		@Test void TestBFs() {
			FindMinyan gary= new FindMinyan(15);
			gary.addHighway(1, 5, 3);
			gary.addHighway(3,10, 4);
			gary.addHighway(1, 20, 2);
			gary.addHighway(5, 1000, 1);
			gary.addHighway(4, 140, 2);
			gary.addHighway(8, 10000, 1);
		
			gary.hasMinyan(8);
			gary.hasMinyan(3);
			gary.hasMinyan(6);
			
			gary.addHighway(8, 10, 15);
			gary.addHighway(4, 10, 15);
			gary.solveIt();
			assertEquals(25,gary.shortestDuration());
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		@Test void TestBFs2() {
			FindMinyan gary= new FindMinyan(15);
			gary.addHighway(1, 5, 3);
			gary.addHighway(3,10, 4);
			gary.addHighway(1, 20, 2);
			gary.addHighway(5, 1000, 1);
			gary.addHighway(4, 140, 2);
			gary.addHighway(8, 15, 1);
			gary.addHighway(8, 10, 15);
			gary.hasMinyan(8);
			gary.hasMinyan(3);
			gary.hasMinyan(6);
			gary.addHighway(4, 10, 15);
			gary.solveIt();
			assertEquals(25,gary.shortestDuration());
			assertEquals(2,gary.numberOfShortestTrips());
		}
		
		@Test void TestBFS3EvenPathsOneLonger() {
			FindMinyan gary= new FindMinyan(7);
			gary.addHighway(1, 1, 3);
			gary.addHighway(1, 1, 2);
			gary.addHighway(1, 0, 4);
			
			gary.addHighway(2, 1, 7);
			gary.addHighway(3, 1, 7);
			gary.addHighway(5, 0, 7);
			
			gary.addHighway(4, 2, 5);
			
			gary.hasMinyan(4);
			gary.solveIt();
			assertEquals(2,gary.shortestDuration());
			assertEquals(3,gary.numberOfShortestTrips());

		}
		
		
		
		@Test void TestChoosesShortestPath() {
			FindMinyan gary= new FindMinyan(4);
			gary.addHighway(1, 3, 2);
			gary.addHighway(1, 9, 3);
			gary.addHighway(3, 100000, 4);
			gary.addHighway(2, 10, 4);
			
			gary.hasMinyan(3);
			
			gary.solveIt();
			assertEquals(31, gary.shortestDuration());
			assertEquals(1,gary.numberOfShortestTrips());
			
		}
		
		
		@Test void TestBackTrack() {
			FindMinyan gary= new FindMinyan(4);
			gary.addHighway(1, 1000, 2);
			gary.addHighway(1, 9, 3);
			gary.addHighway(3, 1, 4);
			gary.hasMinyan(2);
			
			gary.solveIt();
			assertEquals(2010, gary.shortestDuration());
			assertEquals(1,gary.numberOfShortestTrips());
			
		}

		
		
		
		@Test void NoSolutionTestDueToNoMinyan() {
			FindMinyan gary= new FindMinyan(4);
			gary.addHighway(1, 1000, 2);
			gary.addHighway(1, 9, 3);
			gary.addHighway(3, 1, 4);
			gary.solveIt();
			assertEquals(0, gary.shortestDuration());
			assertEquals(0,gary.numberOfShortestTrips());
			
		}
		
		@Test void NeedtoBackTrackOnceInBeginningEvenThoughClearPath() {
			FindMinyan gary= new FindMinyan(6);
			gary.addHighway(1, 1, 2);
			gary.addHighway(1, 2, 4);
			gary.addHighway(1, 1, 3);
			gary.addHighway(3, 300, 6);
			gary.addHighway(4, 7, 6);
			gary.hasMinyan(2);
			gary.hasMinyan(3);
			gary.solveIt();
			assertEquals(11, gary.shortestDuration());
			assertEquals(2,gary.numberOfShortestTrips());
		}
		
		@Test void NeedPossibleBackTrackButDOntUseIt() {
			FindMinyan gary= new FindMinyan(6);
			gary.addHighway(1, 1000, 2);
			gary.addHighway(1, 22423, 4);
			gary.addHighway(1, 50, 3);
			gary.addHighway(3, 300, 6);
			gary.addHighway(4, 73, 6);
			gary.hasMinyan(2);
			gary.hasMinyan(3);
			gary.solveIt();
			assertEquals(350, gary.shortestDuration());
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		
		@Test void TESTALLTHESame() {
			FindMinyan gary= new FindMinyan(6);
			gary.addHighway(1, 1, 2);
			gary.addHighway(1, 1, 4);
			gary.addHighway(1, 1, 3);
			gary.addHighway(3, 1, 6);
			gary.addHighway(4, 1, 6);
			gary.hasMinyan(4);
			gary.hasMinyan(3);
			gary.solveIt();
			assertEquals(2, gary.shortestDuration());
			assertEquals(2,gary.numberOfShortestTrips());
		}
		
		@Test void TESTALLTHESame2() {
			FindMinyan gary= new FindMinyan(6);
			gary.addHighway(1, 1, 2);
			gary.addHighway(1, 1, 4);
			gary.addHighway(1, 1, 3);
			gary.addHighway(3, 1, 6);
			gary.addHighway(4, 1, 6);
			gary.addHighway(2, 1, 6);
			gary.hasMinyan(4);
			gary.hasMinyan(3);
			gary.hasMinyan(2);
			gary.solveIt();
			assertEquals(2, gary.shortestDuration());
			assertEquals(3,gary.numberOfShortestTrips());
		}
		
		
		
		@Test void haveToPassThroughOnePoint() {
			FindMinyan gary= new FindMinyan(6);
			gary.addHighway(1, 2, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 7, 5);
			gary.addHighway(4, 8, 5);
			
			gary.addHighway(5, 4, 6);
			gary.addHighway(4, 1, 6);
			gary.hasMinyan(5);
			
			gary.solveIt();
			assertEquals(13, gary.shortestDuration());
			assertEquals(1,gary.numberOfShortestTrips());
			
			
		}
		
		@Test void haveToPassThroughOnePoin12() {
			FindMinyan gary= new FindMinyan(6);
			gary.addHighway(1, 2, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 7, 5);
			gary.addHighway(4, 8, 5);
			
			gary.addHighway(5, 4, 6);
			gary.addHighway(4, 2, 6);
			gary.hasMinyan(5);
			
			gary.hasMinyan(4);
			gary.solveIt();
			assertEquals(6, gary.shortestDuration());
			assertEquals(1,gary.numberOfShortestTrips());
			
			
		}
		
		
		@Test void haveToPassThroughOnePoin13() {
			FindMinyan gary= new FindMinyan(6);
			gary.addHighway(1, 2, 2);
			gary.addHighway(1, 34, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 7, 5);
			gary.addHighway(4, 8, 5);
			
			gary.addHighway(5, 4, 6);
			gary.addHighway(4, 20, 6);
			gary.hasMinyan(5);
			
			gary.solveIt();
			assertEquals(16, gary.shortestDuration());
			assertEquals(2,gary.numberOfShortestTrips());
			
			
		}
		
		
		
		
		@Test void testBackTrackMiddle() {
			FindMinyan gary= new FindMinyan(8);
			gary.addHighway(1, 2, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 7, 5);
			
			gary.addHighway(5, 43333, 8);
			gary.addHighway(2, 18, 8);
			
			
			gary.hasMinyan(5);
			gary.solveIt();
			
			assertEquals(38, gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		
		@Test void testBackTrackMiddle2() {
			FindMinyan gary= new FindMinyan(8);
			gary.addHighway(1, 20, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 7, 5);
			
			gary.addHighway(5, 43333, 8);
			gary.addHighway(2, 18, 8);
			
			
			gary.hasMinyan(5);
			gary.solveIt();
			
			assertEquals(38, gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		@Test void testBackTrackMiddle6() {
			FindMinyan gary= new FindMinyan(8);
			gary.addHighway(1, 20, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 710, 5);
			
			gary.addHighway(5, 43333, 8);
			gary.addHighway(2, 18, 8);
			
			
			gary.hasMinyan(3);
			gary.solveIt();
			
			assertEquals(26+18, gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		
		
		@Test void testBackTrackMiddle3() {
			FindMinyan gary= new FindMinyan(8);
			gary.addHighway(1, 2, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 6, 5);
			
			gary.addHighway(5, 43333, 8);
			gary.addHighway(2, 18, 8);
			
			
			gary.hasMinyan(5);
			gary.solveIt();
			
			assertEquals(37, gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		@Test void testBackTrackMiddle4() {
			FindMinyan gary= new FindMinyan(8);
			gary.addHighway(1, 2, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 7, 5);
			
			gary.addHighway(5, 43333, 8);
			gary.addHighway(2, 138, 8);
			gary.addHighway(4, 10, 8);
			
			
			gary.hasMinyan(4);
			gary.hasMinyan(5);
			gary.solveIt();
			
			assertEquals(14, gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		
		@Test void testBackTrackMiddle5() {
			FindMinyan gary= new FindMinyan(8);
			gary.addHighway(1, 2, 2);
			gary.addHighway(1, 3, 3);
			gary.addHighway(1, 4, 4);
			
			gary.addHighway(2, 10, 5);
			gary.addHighway(3, 79, 5);
			
			gary.addHighway(5, 43333, 8);
			gary.addHighway(2, 138, 8);
			gary.addHighway(4, 10, 8);
			
			
		
			gary.hasMinyan(5);
			gary.solveIt();
			
			assertEquals(38, gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		
		@Test void notesTest1() {
			FindMinyan gary= new FindMinyan(5);
			
			gary.addHighway(1, 10, 2);
			gary.addHighway(1, 10, 5);
			gary.addHighway(2, 10, 3);
			gary.hasMinyan(3);
			
			
			gary.solveIt();
			assertEquals(50, gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
			
		}
		
		@Test void notesTest2() {
			FindMinyan gary= new FindMinyan(5);
			
			gary.addHighway(1, 10, 2);
			gary.addHighway(1, 10, 5);
			gary.addHighway(2, 10, 3);
			gary.addHighway(1, 1, 3);
			gary.hasMinyan(3);
			
			
			gary.solveIt();
			assertEquals(12,gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
			
		}
		
		@Test void havingtoBackTrack() {
			FindMinyan gary= new FindMinyan(5);
			
			gary.addHighway(1, 10, 2);
			gary.addHighway(2, 10, 3);
			gary.addHighway(3, 10, 5);
			gary.addHighway(4, 10, 5);
			gary.addHighway(1, 30, 5);
			gary.hasMinyan(4);
			
			
			gary.solveIt();
			assertEquals(50,gary.shortestDuration()); 
			assertEquals(2,gary.numberOfShortestTrips());
			
		}
		
		@Test void havingtoBackTrackWith0() {
			FindMinyan gary= new FindMinyan(5);
			
			gary.addHighway(1, 100, 4);
			gary.addHighway(1, 1, 5);
			gary.addHighway(1, 3, 2);
			gary.addHighway(2, 7, 3);
			gary.addHighway(3, 10, 4);
			gary.addHighway(3, 0, 5);
			gary.hasMinyan(4);
			
			
			gary.solveIt();
			assertEquals(21,gary.shortestDuration()); 
			assertEquals(1,gary.numberOfShortestTrips());
			
		}
		
		@Test void singleLengthPath() {
			FindMinyan gary= new FindMinyan(5);
			gary.addHighway(1, 30, 2);
			gary.addHighway(2, 1, 3);
			gary.addHighway(3, 5, 4);
			gary.addHighway(4, 110, 5);
			
			gary.hasMinyan(4);
			gary.hasMinyan(2);
			gary.solveIt();
			assertEquals(146,gary.shortestDuration());
			assertEquals(1,gary.numberOfShortestTrips());
		}
		
		
	@Test void failAddingDuplicate() {
		FindMinyan gary= new FindMinyan(17);
		gary.addHighway(1, 10, 9);
		assertThrows(IllegalArgumentException.class,()-> gary.addHighway(9, 8, 1));	

	}
	@Test void testTriangleStructure() {
		FindMinyan gary= new FindMinyan(4);
		
		gary.addHighway(1,0, 2);
		gary.addHighway(2, 4, 3);
		gary.addHighway(1, 0, 3);
		
			gary.hasMinyan(3);
		
		gary.addHighway(3, 4, 4);
		gary.addHighway(2, 0, 4);
		
		gary.solveIt();
		assertEquals(0, gary.shortestDuration());
		assertEquals(1, gary.numberOfShortestTrips());
		
	}
		
		//TODO add support for needing to backtrack -> allowing to loop once
			//TODO add support for not calling addhighway twice on same stuff
			//TODO improve performance by not adding states that go back that dont have minyan
			//TODO check if you can reach end city, hit minyan and come back

		
		@Test
		void c() {
			if(System.out.printf("hello")==null){
				
			}
				
			
		}
		
		


	}

	


