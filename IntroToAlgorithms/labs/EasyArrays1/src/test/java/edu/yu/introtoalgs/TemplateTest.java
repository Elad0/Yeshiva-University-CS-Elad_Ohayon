package edu.yu.introtoalgs;

import static org.junit.Assert.*;

import org.junit.Test;

public class TemplateTest {

	@Test
	public void basicTest() {
		int arr[]= {1,2,3,4,5,5,6,7,8,9,10};
		
		
		//Template t= new Template();
		EasyArrays1 t=new EasyArrays1();
	//	System.out.println(t.removeArrayElement(arr, 0));
		int non=t.removeArrayElement(arr, 5);
		for(int c: arr) {
			System.out.println(c);
		}
		System.out.println("Retval = "+ non);
	}

}
