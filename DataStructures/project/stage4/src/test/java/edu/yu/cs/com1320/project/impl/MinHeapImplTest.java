package edu.yu.cs.com1320.project.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage4.Document;

class MinHeapImplTest {

	@Test
	void testHowisGreaterWorks() throws URISyntaxException {
		MinHeapImpl test= new MinHeapImpl();
		
		
		 URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
		  String textString1 = "PIE";
		  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
		  
		  textDocument.setLastUseTime(1);
		  
		  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
		  String textString2 = "PIE PIE";
		  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
		  
		  textDocument2.setLastUseTime(2);
		 
		  URI textUri3 = new URI("http://edu.yu.cs/com1320/txt3");
		  String textString3 = "PIE Pie pie pie pie pie pie  donkey";
		  DocumentImpl textDocument3 = new DocumentImpl(textUri3, textString3);
		  
		  textDocument3.setLastUseTime(3);
		  
		  
		  
		  URI textUri4 = new URI("http://edu.yu.cs/com1320/txt4");
		  String textString4 = "PIE Pie pie pie pie pie pie  donkey";
		  DocumentImpl textDocument4 = new DocumentImpl(textUri4, textString4);
		  
		  textDocument4.setLastUseTime(4);
		  
		 
		  
		  test.insert(textDocument);  
		  test.insert(textDocument2);  
		  test.insert(textDocument3);  
		  test.insert(textDocument4); 
		
		  
			 textDocument.setLastUseTime(100000);
			  textDocument2.setLastUseTime(00);
			  textDocument3.setLastUseTime(999);
				textDocument4.setLastUseTime(10000000);
		  test.reHeapify(textDocument);
	
		 
		  
		/*  
		  for(int i=0; i<test.elements.length; i++) {
				if(test.elements[i]!=null) {
				System.out.println(((Document) test.elements[i]).getKey());
				}
				else {
					System.out.println("null");
				}
			}
		  */
		  
		  System.out.println("____________________________");
		  
		  
		  System.out.println(((DocumentImpl) test.remove()).getKey());
		  System.out.println(((DocumentImpl) test.remove()).getKey());
		  System.out.println(((DocumentImpl) test.remove()).getKey());
		  System.out.println(((DocumentImpl) test.remove()).getKey());
	}
	
	/*@Test void testd() {
		MinHeapImpl<Integer> test= new MinHeapImpl<>();
		Integer one=1;
		Integer two=2;
		Integer three=3;
		Integer four=4;
		Integer five=5;
		
		test.insert(one);
		test.insert(two);
		test.insert(three);
		test.insert(four);
		test.insert(five);
	
	System.out.println(test.remove());
	System.out.println(test.remove());
	System.out.println(test.remove());
	System.out.println(test.remove());
		assert(test.isEmpty());
		
		
	}*/
}
	
	
	
	
	

