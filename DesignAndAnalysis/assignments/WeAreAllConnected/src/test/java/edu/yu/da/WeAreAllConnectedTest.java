package edu.yu.da;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import edu.yu.da.WeAreAllConnected.Segment;
import edu.yu.da.WeAreAllConnectedBase.SegmentBase;

class WeAreAllConnectedTest {

		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void microTest1() {
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113)};
		
		SegmentBase Segment= new Segment(3, 1, 4);
		final SegmentBase[] possibilities = {Segment, new Segment(3,1,10), new Segment(3,1,50), new Segment(3,1,5), new Segment(1,3,5)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,Segment);
		}
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void OneSegmentReducesButAnotherReducesMoreFirstConnected() {
		
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113)};
		
		SegmentBase Segment= new Segment(0, 2, 6);
		final SegmentBase[] possibilities = {Segment, new Segment(3,1,109), new Segment(3,1,50), new Segment(3,1,576), new Segment(1,3,95), new Segment(3, 1, 20)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,Segment);
		}
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void OneSegmentReducesButAnotherReducesMoreLastConnected() {
		
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113)};
		
		SegmentBase Segment= new Segment(3, 1, 5);
		final SegmentBase[] possibilities = {new Segment(0,2,6 ), new Segment(3,1,109), new Segment(3,1,50), new Segment(3,1,576), new Segment(1,3,95),Segment };

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,Segment);
		}
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void AddNewCityOrShorter() {
		
		final int n = 5;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113),new Segment(3, 4, 20000) };
		
		SegmentBase Segment= new Segment(3, 4,1000 );
		final SegmentBase[] possibilities = {Segment, new Segment(3,1,10), new Segment(3,1,50), new Segment(3,1,4), new Segment(1,3,5)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,Segment);
		
		}
		
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void OneSegmentReducesButAnotherReducesMoreAddingNewSegment() {
		
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113)};
		
		SegmentBase Segment= new Segment(3, 1, 4);
		final SegmentBase[] possibilities = {Segment, new Segment(3,1,23), new Segment(3,1,50), new Segment(3,1,587), new Segment(1,3,5)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,Segment);
		}
		
		
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void noImprovementandConnectionExists() {
		
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113)};
		
		final SegmentBase[] possibilities = {new Segment(0, 1, 2721), new Segment(1, 2, 3843), new Segment(2, 3, 114)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,null);
		}
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void noImprovementandNewDirectConnection() {
		
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113)};
		
		final SegmentBase[] possibilities = {new Segment(3,1,4000)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,null);
		}
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void test1route() {
		
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2720), new Segment(1, 2, 3842), new Segment(2, 3, 113)};
		SegmentBase sbs= new Segment(3,1,3954);
		
		final SegmentBase[] possibilities = {sbs};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sbs,sb);
		}
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void diffbetweenmstandsptNull() {
		
		final int n = 3;
		final SegmentBase[] current = {new Segment(0, 1, 2), new Segment(1, 2, 1), new Segment(0, 2, 2)};
		
		final SegmentBase[] possibilities = {new Segment(2,1,4000)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,null);
		}

		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void AddingNewVertex() {
		
		final int n = 4;
		final SegmentBase[] current = {new Segment(0, 1, 2), new Segment(0, 2, 20)};
		Segment ans= new Segment(2,3,4000);
		final SegmentBase[] possibilities = {ans, new Segment(1,2,1)};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,ans);

		
	}
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void StraightGraphSkipFirst() {
		
		final int n = 5;
		final SegmentBase[] current = {new Segment(0, 1, 200), new Segment(2, 1, 10), 
				new Segment(2, 3, 2000), new Segment(4, 3, 27)};
		
		SegmentBase sg= new Segment(2, 4, 23);
		final SegmentBase[] possibilities = {new Segment(0,3,2), sg};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,sg);
		
		}
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void StraightGraphSkipLast() {
		
		final int n = 5;
		final SegmentBase[] current = {new Segment(0, 1, 200), new Segment(2, 1, 10), 
				new Segment(2, 3, 2000), new Segment(4, 3, 27)};
		
		
		SegmentBase g= new Segment(0,3,2);
		
		final SegmentBase[] possibilities = {new Segment(2,4,67), g};

		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		assertEquals(sb,g);
		
		}
		
		/* ========================================
		 * Tests with the structure 0>1 0>2 1>3
		 * ========================================
		 */
		
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void testLarry() {
		
		final int n = 4;
		final SegmentBase[] current = {new SegmentBase(0,1,3), new SegmentBase(0,2,7), new SegmentBase(3,1,6)};
		
		SegmentBase answer= new Segment(0,3,2);
		
		
		final SegmentBase[] possibilities = {new Segment(2,3,3), answer, new Segment(0,3,9),new Segment(3,0,10)};
		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		
		assertEquals(sb,answer);
		
		}
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void testLarry2() {
		
		final int n = 4;
		final SegmentBase[] current = {new SegmentBase(0,1,3), new SegmentBase(0,2,7), new SegmentBase(3,1,6)};
		
		SegmentBase answer= new Segment(2,3,2);
		
		
		final SegmentBase[] possibilities = {answer, new Segment(0,3,9),new Segment(3,0,10),new Segment(0,3,2)};
		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		
		assertEquals(sb,answer);
		
		}
		@Test
		@Timeout(value=500, unit = TimeUnit.MILLISECONDS)
		 void testLarry3() {
		
		final int n = 4;
		final SegmentBase[] current = {new SegmentBase(0,1,3), new SegmentBase(0,2,7), new SegmentBase(3,1,6)};
		
		SegmentBase answer= new Segment(0,3,8);
		
		
		final SegmentBase[] possibilities = {answer, new Segment(0,3,8),new Segment(3,0,10),new Segment(0,3,27)};
		final WeAreAllConnectedBase waac = new WeAreAllConnected();
		SegmentBase sb=waac.findBest(n, Arrays.asList(current), Arrays.asList(possibilities));
		
		assertEquals(sb,answer);
		
		}
		
		@Test
		void testEquality() {
			SegmentBase gt= new Segment(3,4,10);
			SegmentBase tg= new SegmentBase(4,3,10);
			assertEquals(gt,tg);
			
			
			SegmentBase hu= new Segment(4,3,10);
			SegmentBase notequal= new Segment(4, 3, 11);
			
			assertEquals(hu, gt);
			
			
			gt=new Segment(3, 4, 11);
			
			assertFalse(gt.equals(tg));
			assertFalse(notequal.equals(tg));
			
			assertFalse(notequal.equals(hu));
			
		}
		
		
		
	
}

