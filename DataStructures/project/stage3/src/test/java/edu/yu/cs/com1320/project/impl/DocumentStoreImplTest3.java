package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.stage3.impl.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.stage2.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage3.*;
class DocumentStoreImplTest3 {

	@Test
	void searchTest() throws URISyntaxException, IOException {
		System.out.println("Search Test Begins");
		
		
		
		DocumentStoreImpl larry=  new DocumentStoreImpl();
			  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
			  String textString1 = "PIE";
			  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
			  
			  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
			  String textString2 = "PIE PIE";
			  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
			  
			  URI textUri3 = new URI("http://edu.yu.cs/com1320/txt3");
			  String textString3 = "PIE Pie pie pie pie pie pie  donkey";
			  DocumentImpl textDocument3 = new DocumentImpl(textUri3, textString3);

			  URI textUri4 = new URI("http://edu.yu.cs/com1320/txt4");
			  String textString4 = "PIE PIE PIE PIE";
			  DocumentImpl textDocument4 = new DocumentImpl(textUri4, textString4);
			  
			  URI textUri5 = new URI("http://edu.yu.cs/com1320/txt5");
			  String textString5 = "PIE PIE PIE Pie Pie ";
			  DocumentImpl textDocument5 = new DocumentImpl(textUri5, textString5);
			  
			  
			  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);
	
			  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);

			 larry.putDocument(new ByteArrayInputStream(textString5.getBytes()),textUri5, DocumentStore.DocumentFormat.TXT);
			 larry.putDocument(new ByteArrayInputStream(textString5.getBytes()),textUri5, DocumentStore.DocumentFormat.TXT);
			 
			  larry.putDocument(new ByteArrayInputStream(textString3.getBytes()),textUri3, DocumentStore.DocumentFormat.TXT);
		
			  larry.putDocument(new ByteArrayInputStream(textString4.getBytes()),textUri4, DocumentStore.DocumentFormat.TXT);
			
		
			  assert(larry.getDocument(textUri4).equals(textDocument4));
			  assert(!larry.getDocument(textUri3).equals(textDocument4));
			 assert(larry.getDocument(textUri3).equals(textDocument3));
			  
				
			  
			  for(Document docu: larry.search("pie")) {
				 System.out.println(docu.getKey());
			  }
			  System.out.println("Search Test Ends");
	}
	
	
	@Test
	void testgetAllPrefixSorted() throws URISyntaxException, IOException {
		System.out.println("test get all prefix sorted begins");
		DocumentStoreImpl larry=  new DocumentStoreImpl();
		  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
		  String textString1 = "PI Pio pita pilo";
		  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
		  
		  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
		  String textString2 = "PIE PiA";
		  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
		  
		  URI textUri3 = new URI("http://edu.yu.cs/com1320/txt3");
		  String textString3 = "Pietapus";
		  DocumentImpl textDocument3 = new DocumentImpl(textUri3, textString3);

		  URI textUri4 = new URI("http://edu.yu.cs/com1320/txt4");
		  String textString4 = "pieranous pierex piepie";
		  DocumentImpl textDocument4 = new DocumentImpl(textUri4, textString4);
		  
		  URI textUri5 = new URI("http://edu.yu.cs/com1320/txt5");
		  String textString5 = "PIE PIE pie pie pie pi pia";
		  DocumentImpl textDocument5 = new DocumentImpl(textUri5, textString5);
		  
		  
		  URI textUri6 = new URI("http://edu.yu.cs/com1320/txt6");
		  String textString6 = "doodoo ";
		  DocumentImpl textDocument6 = new DocumentImpl(textUri6, textString6);
		  
		  
		  larry.putDocument(new ByteArrayInputStream(textString6.getBytes()),textUri6, DocumentStore.DocumentFormat.TXT);
		  
		  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);
		 
		  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);
		  larry.putDocument(new ByteArrayInputStream(textString5.getBytes()),textUri5, DocumentStore.DocumentFormat.TXT);
		  larry.putDocument(new ByteArrayInputStream(textString3.getBytes()),textUri3, DocumentStore.DocumentFormat.TXT);
		  larry.putDocument(new ByteArrayInputStream(textString4.getBytes()),textUri4, DocumentStore.DocumentFormat.TXT);
		 
	
			  
		  
		  assert(larry.getDocument(textUri4).equals(textDocument4));
		  assert(!larry.getDocument(textUri3).equals(textDocument4));
		  assert(larry.getDocument(textUri3).equals(textDocument3));
		  
		  assert(larry.searchByPrefix("Pi")!=null);
		  assert(larry.searchByPrefix("Pied")==Collections.EMPTY_LIST); 
		  
		  //System.out.println(larry.searchByPrefix("pie"));
		  
	//textDocument3.addPrefixestoHashTable();
		  
		  for(Document docu: larry.searchByPrefix("pi")) {
			  System.out.println(docu.getKey()); 		//order should be 5,1,4,2 3
		  }
		 System.out.println(larry.getDocument(textUri5).wordCount("PIe"));
		  
		 System.out.println("test get all prefix sorted ends");
	}
	
	@Test
	void deleteAllTest() throws URISyntaxException, IOException{
		System.out.println("test deleteall begins");
		DocumentStoreImpl larry=  new DocumentStoreImpl();
		  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
		  String textString1 = "PIE eee Tree";
		  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
		 
		  
		  
		  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
		  String textString2 = "PIE tree dino";
		  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
		  
		  
		  
		  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);
		  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);
		  
		//  System.out.println(larry.searchByPrefix("pie"));
		//  System.out.println(larry.searchByPrefix("fart"));
		//  System.out.println(larry.searchByPrefix("tree"));
		  
		  assert(larry.search("tree").size()==2);
		  assert(larry.search("DinO").size()==1);
		for(URI docu:  larry.deleteAll("eee")) {
			System.out.println(docu);	//should be uri1
		}
		
				assert(larry.search("tree").size()==1);
		  
		  for(Document docu:  larry.search("dino")) {
				System.out.println(docu.getKey());
			}
		  
		  for(URI docu:  larry.deleteAll("dino")) {
				System.out.println(docu);
			}
		  assert(larry.search("tree").size()==0);
		  
	
		assert( larry.search("tree").equals(Collections.EMPTY_LIST));
		assert(larry.deleteAll("tree").equals(Collections.EMPTY_SET));
		System.out.println("test deleteall ends"); 
	}
	

	
	@Test
	void testDeleteAllWithPrefix() throws URISyntaxException, IOException {
		System.out.println("New test");
			DocumentStoreImpl larry=  new DocumentStoreImpl();
			  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
			  String textString1 = "PIE eee Tree";
			  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
			 
			  
			  
			  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
			  String textString2 = "PIE tree dino";
			  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
			  
			  
			  
			  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);
			  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);
			  
			//  System.out.println(larry.searchByPrefix("pie"));
			//  System.out.println(larry.searchByPrefix("fart"));
			//  System.out.println(larry.searchByPrefix("tree"));
			  
			  assert(larry.search("tree").size()==2);
			  assert(larry.search("DinO").size()==1);
			for(URI docu:  larry.deleteAllWithPrefix("eee")) {
				System.out.println(docu);	//should be uri1
			}
					assert(larry.search("tree").size()==1);
			  
			  for(Document docu:  larry.search("dino")) {
					System.out.println(docu.getKey());
				}
			  
			  for(URI docu:  larry.deleteAllWithPrefix("dino")) {
					System.out.println(docu);
				}
			  assert(larry.search("tree").size()==0);
			  
		
			assert( larry.search("tree").equals(Collections.EMPTY_LIST));
			assert( larry.deleteAllWithPrefix("tree").equals(Collections.EMPTY_SET));
			System.out.println("test ends");
	}
	
	
	
}
