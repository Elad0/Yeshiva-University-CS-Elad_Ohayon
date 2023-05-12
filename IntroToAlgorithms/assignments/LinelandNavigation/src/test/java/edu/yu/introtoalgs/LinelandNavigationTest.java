package edu.yu.introtoalgs;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.StackWalker.StackFrame;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LinelandNavigationTest {

	@Test
	void testAddMineField() {
		LinelandNavigation gary= new LinelandNavigation(100,10);
		gary.addMinedLineSegment(4, 10);
		
		for(int i=4; i<=10; i++) {
			assert(gary.board[i]==-1);
		}
		}



@Test 
void testnoMinesAtAll() {
	LinelandNavigation gary= new LinelandNavigation(100,10);
	assertEquals(100/10, gary.solveIt());
}

@Test 
void testnoMinesInPath() {
	LinelandNavigation gary= new LinelandNavigation(100,10);
	gary.addMinedLineSegment(5, 5);
	gary.addMinedLineSegment(3, 4);
	assertEquals(100/10, gary.solveIt());
}

@Test 
void testPathLongerThanCanJump() {
	LinelandNavigation gary= new LinelandNavigation(100,6);
	gary.addMinedLineSegment(1, 7);
	assertEquals(0, gary.solveIt());
	
	LinelandNavigation larry= new LinelandNavigation(10000,600);
	larry.addMinedLineSegment(99, 900);
	assertEquals(0, larry.solveIt());
}

@Test
void testCanMakeitWithComplexPaths() {
	LinelandNavigation gary= new LinelandNavigation(101,5);
	gary.addMinedLineSegment(3, 4);
	gary.addMinedLineSegment(7, 10);
	gary.addMinedLineSegment(13, 15);
	assertEquals(22,gary.solveIt());
}

@Test
void testtooLongpathThanCanJump2() {
	LinelandNavigation gary= new LinelandNavigation(100,5);
	gary.addMinedLineSegment(1, 5);
	gary.addMinedLineSegment(7, 11);
	assertEquals(0,gary.solveIt());
}

@Test
void testlotsofminesbutstraightpath() {
	LinelandNavigation gary= new LinelandNavigation(50, 13);
	gary.addMinedLineSegment(1, 12);
	gary.addMinedLineSegment(14, 24);
gary.addMinedLineSegment(27, 37);
gary.addMinedLineSegment(40, 42);
gary.addMinedLineSegment(43, 44);
	assertEquals(Math.ceil(50/13.0),gary.solveIt());
}

@Test
void testBecomesinValidinMiddle() {
	LinelandNavigation gary= new LinelandNavigation(1000, 13);
	gary.addMinedLineSegment(1, 11);
	gary.addMinedLineSegment(13, 24);
	gary.addMinedLineSegment(25, 26);
	assertEquals(0,gary.solveIt());
}

@Test
void testBecomesinValidinMiddle2() {
	LinelandNavigation gary= new LinelandNavigation(17, 2);
gary.addMinedLineSegment(1,1);
gary.addMinedLineSegment(3, 3);
gary.addMinedLineSegment(4, 4);
	assertEquals(0,gary.solveIt());
}

@Test
void testhavingTomoveBackTest() {
	LinelandNavigation gary= new LinelandNavigation(70, 11); 
	gary.addMinedLineSegment(12, 13); 
	gary.addMinedLineSegment(10, 10);
	gary.addMinedLineSegment(21, 24);
	gary.addMinedLineSegment(31, 39);
	gary.addMinedLineSegment(52, 57); //should move to position 47
	

	assertEquals(8,gary.solveIt());	

}

@Test
void testhavingTomoveBackTestCantGoBack() {
	LinelandNavigation gary= new LinelandNavigation(70, 11);
	gary.addMinedLineSegment(12, 13);
	gary.addMinedLineSegment(10, 10);
	gary.addMinedLineSegment(21, 24);
	gary.addMinedLineSegment(31, 40);
	gary.addMinedLineSegment(52, 57); 
	

	assertEquals(0,gary.solveIt());	

}


@Test
void testhavingTomoveBackTestErrorIntoWhatMovingInto() {
	LinelandNavigation gary= new LinelandNavigation(70, 11);
	gary.addMinedLineSegment(12, 13);
	gary.addMinedLineSegment(10, 10);
	gary.addMinedLineSegment(21, 24);
	gary.addMinedLineSegment(31, 40);
	
	
	gary.addMinedLineSegment(19, 19);
	assertEquals(0,gary.solveIt());

}






@Test
void testhavingTomoveBackTestButFail() {
	LinelandNavigation gary= new LinelandNavigation(70, 11);
	gary.addMinedLineSegment(1, 10);
	gary.addMinedLineSegment(21, 22);
	assertEquals(0,gary.solveIt());
	

}

@Test
void testhavingTomoveBackTestButFail2() {
	LinelandNavigation gary= new LinelandNavigation(70, 2);
	gary.addMinedLineSegment(5, 5);
	gary.addMinedLineSegment(8, 8);
	assertEquals(0,gary.solveIt());
	

}

@Test
void testhavingTomoveBackTestButWorks2() {
	LinelandNavigation gary= new LinelandNavigation(70, 2);
	gary.addMinedLineSegment(5, 5);
	gary.addMinedLineSegment(7, 7);
	assertEquals(35,gary.solveIt());
	

}


@Test
void testhavingTomoveBackTestButAlreadyat0() {
	LinelandNavigation gary= new LinelandNavigation(70, 11);
	gary.addMinedLineSegment(11, 12);
	gary.addMinedLineSegment(21, 22);
	assertEquals(0,gary.solveIt());

}
@Test
void testBombsAtGButWorksBecauseItJumpsOver() {
	LinelandNavigation gary= new LinelandNavigation(99,12);
	gary.addMinedLineSegment(99, 99);
	assertEquals(9,gary.solveIt());
}

@Test
void JumpEqualExactly() {
	LinelandNavigation gary= new LinelandNavigation(99,99);
	assertEquals(1,gary.solveIt());
}

@Test
void JumpEqualExactlyAndMineField() {
	LinelandNavigation gary= new LinelandNavigation(99,99);
	gary.addMinedLineSegment(99, 99);
	assert(gary.solveIt()==0);
}



@Test
void testBombsAtGButFailsSinceItCannotBack() {
	LinelandNavigation gary= new LinelandNavigation(21, 3);
	gary.addMinedLineSegment(21, 21);
	gary.addMinedLineSegment(1, 2); 
	gary.addMinedLineSegment(16, 17);
	assertEquals(0,gary.solveIt());
	
}


@Test
void testBombsAtGButWorksBecauseItCanMoveBack() {
	LinelandNavigation gary= new LinelandNavigation(21, 3);
	gary.addMinedLineSegment(21, 21);
	gary.addMinedLineSegment(1, 2);
	gary.addMinedLineSegment(7, 8);
	assertEquals(9, gary.solveIt());
	
}
@Test
void testRandom() {
	LinelandNavigation gary= new LinelandNavigation(21, 3);
	gary.addMinedLineSegment(21, 21);
	gary.addMinedLineSegment(1, 2);
	gary.addMinedLineSegment(7, 8);
	gary.addMinedLineSegment(50, 1000);
	assertEquals(9, gary.solveIt());
	
}


@Test
void testReallyLongMineFieldNoImpact() {
	LinelandNavigation gary= new LinelandNavigation(21, 3);
	gary.addMinedLineSegment(22, 1000);
	assertEquals(7,gary.solveIt());
}

@Test
void reallyLongMineOneAfterGandBeyondButCanBAck() {
	LinelandNavigation barry= new LinelandNavigation(100,12);
	barry.addMinedLineSegment(101, 900);
	assertEquals(10, barry.solveIt());
}

@Test
void reallyLongMineOneAfterGandBeyondButCanBAckIntoOneOpen() {
	LinelandNavigation barry= new LinelandNavigation(100,12);
	barry.addMinedLineSegment(102, 900);
	barry.addMinedLineSegment(100, 100);
	assertEquals(10, barry.solveIt());
}


@Test
void reallyLongMineOneAfterGandBeyondCANNOTBack() {
	LinelandNavigation barry= new LinelandNavigation(100,12);
	barry.addMinedLineSegment(102, 900);
	barry.addMinedLineSegment(100, 100);
	barry.addMinedLineSegment(89, 99);
	assertEquals(0, barry.solveIt());
	
}

@Test
void MiscTest() {
	LinelandNavigation barry= new LinelandNavigation(100,12);
	barry.addMinedLineSegment(102, 900);
	barry.addMinedLineSegment(100, 100);
	barry.addMinedLineSegment(1000000, 1000000);
	assertEquals(10, barry.solveIt());
}


@Test
void testreallyLongMinefieldBreakEarly() {
	LinelandNavigation barry= new LinelandNavigation(100,12);
	barry.addMinedLineSegment(1, 900);
	assertEquals(0, barry.solveIt());
}
@Test
void cantMoveBackWards() {
	LinelandNavigation barry= new LinelandNavigation(12,12);
	barry.addMinedLineSegment(12, 12);
	assertEquals(0, barry.solveIt());
}


@Test void testOneMill() {
	LinelandNavigation gary= new LinelandNavigation(1000000,100);
	assertEquals(gary.solveIt(),1_000_000/100);
}
@Test void dontGoBelowZero() {
	LinelandNavigation gary= new LinelandNavigation(100,25);
	gary.addMinedLineSegment(25, 25);
	assertEquals(0,gary.solveIt());
	
	LinelandNavigation barry= new LinelandNavigation(20,3); 
	barry.addMinedLineSegment(6, 6); 
	barry.addMinedLineSegment(8, 8);
	barry.addMinedLineSegment(10, 10);
	assertEquals(barry.solveIt(), 0);
	
}

@Test void testRn() {
	LinelandNavigation h= new LinelandNavigation(40, 8); 
	h.addMinedLineSegment(16, 16);
	h.addMinedLineSegment(23,23);
	h.addMinedLineSegment(40, 40);
	h.addMinedLineSegment(6, 6);
	h.addMinedLineSegment(5, 5);
h.addMinedLineSegment(20, 20);
	

	
	assertEquals(h.solveIt(), 7);
	
}


//Correct Program so that each time we have to move from a mine we add m-1 positions to a queue and contain algorithim on those


}