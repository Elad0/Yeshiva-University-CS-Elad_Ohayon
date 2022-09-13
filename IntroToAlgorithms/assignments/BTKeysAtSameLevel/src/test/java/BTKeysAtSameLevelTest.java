import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.yu.introtoalgs.BTKeysAtSameLevel;

public class BTKeysAtSameLevelTest {

	










						//Tests category 1 check complete tree of depth 1->3
	@Test
	public void computeTest11() {	//test given tree which is complete with 2 nodes
		List<List<Integer>> expected= new ArrayList<List<Integer>>();
		List<Integer> temp= new ArrayList<Integer>();
		temp.add(1);
		expected.add(new ArrayList<Integer>(temp));
		temp.remove(0);
		temp.add(3);
		temp.add(2);
		expected.add(new ArrayList<Integer>(temp));
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		final String gary="1(3)(2)";
		List<List<Integer>>  g=barry.compute(gary);
		System.out.println(g+ " returned value");
		System.out.println(expected+ " expected value");
		assertTrue(g.equals(expected));

}
	
	//Tests category 1 check complete tree of depth 1->3
@Test
public void computeTest14() {	//test given tree which is complete with 2 nodes

System.out.println("");
System.out.println("");
System.out.println("");

BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
final String gary="1(2(3)(4))(5(2)(3))";
System.out.println(barry.compute(gary));

System.out.println("");
System.out.println("");
System.out.println("");

}
@Test
public void demoTest() {
	 final String treeInStringRepresentation = "1(2)(3)";
	final List<List<Integer>> actual = new BTKeysAtSameLevel().compute(treeInStringRepresentation);
	final List<List<Integer>> expected = new ArrayList<>();
	expected.add(new ArrayList<Integer>(List.of(1)));
	 expected.add(new ArrayList<Integer>(List.of(2, 3)));
	assertEquals("incorrect 'binary tree keys at same depth'",expected, actual);
}


	@Test
	public void computeTest12() {	//test given tree which has 3 nodes each the child of the other
		System.out.println("test given tree which has 3 nodes each the child of the other");
		List<List<Integer>> expected= new ArrayList<List<Integer>>();
		List<Integer> temp= new ArrayList<Integer>();
		temp.add(1);
		expected.add(new ArrayList<Integer>(temp));
		temp.remove(0);
		temp.add(3);
		expected.add(new ArrayList<Integer>(temp));
		temp.remove(0);
		temp.add(2);
		expected.add(new ArrayList<Integer>(temp));
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		final String gary="1(3(2))";
		List<List<Integer>>  g=barry.compute(gary);
		System.out.println(g+ " returned value");
		System.out.println(expected+ " expected value");
		assertTrue(g.equals(expected));
		System.out.println("computeTest 1.2 ends");

}
	@Test
	public void computeTest13() {	//test given tree which has 2 children and then 1 left child has 1 child and right has 2
		System.out.println("test given tree which has 2 children and then 1 left child has 1 child and right has 2");
		List<List<Integer>> expected= new ArrayList<List<Integer>>();
		List<Integer> temp= new ArrayList<Integer>();
		temp.add(1);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		temp.add(3);
		temp.add(2);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		temp.add(4);
		temp.add(9);
		temp.add(7);
		expected.add(new ArrayList<Integer>(temp));
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		final String gary="1(3(4))(2(9)(7))";
		List<List<Integer>>  g=barry.compute(gary);
		System.out.println(g+ " returned value");
		System.out.println(expected+ " expected value");
		assertTrue(g.equals(expected));
		System.out.println("Compute test13 ends");

}
	
	@Test
	public void basicTest() {
		List<List<Integer>> expected= new ArrayList<List<Integer>>();
		List<Integer> temp= new ArrayList<Integer>();
		temp.add(1);
		expected.add(new ArrayList<Integer>(temp));
		temp.remove(0);
		temp.add(2);
		temp.add(3);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		temp.add(4);
		temp.add(5);
		expected.add(new ArrayList<Integer>(temp));
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		final String gary="1(2(4)(5))(3)";
		assertEquals(expected,barry.compute(gary));
	}
	
	@Test
	public void basicTest2() {
		List<List<Integer>> expected= new ArrayList<List<Integer>>();
		List<Integer> temp= new ArrayList<Integer>();
		temp.add(2);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		temp.add(2);
		temp.add(3);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		temp.add(1);
		temp.add(2);
		temp.add(3);
		temp.add(4);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		temp.add(5);
		temp.add(1);
		temp.add(2);
		temp.add(3);
		temp.add(4);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		temp.add(1);
		temp.add(8);
		temp.add(1);
		temp.add(4);
		temp.add(5);
		expected.add(new ArrayList<Integer>(temp));
		temp= new ArrayList<Integer>();
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		// String gary="2(2(1(5(1)(8))(1(1)2)))(3(3(2))(4(3)(4(4)(5))))";
		
									//4
		String gary="2(2(1(5(1)(8))(1(1)))(2))(3(3(2))(4(3)(4(4)(5))))";
		assertEquals(expected,barry.compute(gary));
	}
	
	@Test public void errorhjfhjk() {
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		final String gary="1(2(3(8)(7))(4))(5(6(7)(2))(7(7)(2)))";
		 barry.compute(gary);
	}
	
	
	@Test 
	public void errorTest11() {	//check if error thrown when have 3 children to a node
		System.out.println("check if error thrown when have 3 children to a node");
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		//final String gary="1(3(4))(2(9)(7))";
		final String gary="1(2)(3)(4)";
		
		try {
			
			System.out.println( barry.compute(gary));
			fail("Exception not thrown");
		}catch(IllegalArgumentException e) {
			
		}
		System.out.println("Error test 11 ends");
	}
	
	
	@Test 
	public void errorTest12() {	//check if error thrown when have 2 numbers next to eachother
		System.out.println("check if error thrown when have 2 numbers next to eachother. Tests for double digits everywhere");
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		final String gary="12(3(4))(2(9)(7))";
		
		try {
			barry.compute(gary);
			fail("did not throw exception");
		}catch(IllegalArgumentException e) {
			
		}
		
		System.out.println("error test ends");
	}
	
	@Test 
	public void errorTest13() {	//check if error thrown when have 2 numbers next to eachother
		System.out.println("check if error thrown when have 2 numbers next to eachother. Tests for double digits everywhere");
		BTKeysAtSameLevel barry= new BTKeysAtSameLevel();
		// String gary="1(3(4))(2(9)(4)(7))";
		String gary="1(2(9)(4)(7))";
		try {
			barry.compute(gary);
			fail("did not throw exception");
		}catch(IllegalArgumentException e) {
			
		}
		
		System.out.println("error test ends");
	}
	
	
	
	//test with number at end randomly
	
	
}
