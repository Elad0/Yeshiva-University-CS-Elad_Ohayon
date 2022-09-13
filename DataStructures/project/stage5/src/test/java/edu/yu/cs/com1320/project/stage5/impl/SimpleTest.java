package edu.yu.cs.com1320.project.stage5.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.BTree;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.stage5.DocumentStore;

class SimpleTest {

	@Test
	void test() throws Exception {
		BTree bonk= new BTreeImpl();
		bonk.setPersistenceManager(new DocumentPersistenceManager(null));
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";

	  
	         
	        //init possible values for doc2
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	String txt2 = "bo bo bo bo bo bo bo ";

	        //init possible values for doc3
	URI uri3 = new URI("http://edu.yu.cs/com1320/doc3");
	        String txt3 = "Bo BO BO ";

	         
	         DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	         DocumentImpl doc2= new DocumentImpl(uri2,txt2, null);
	         DocumentImpl doc3= new DocumentImpl(uri3,txt3, null);
	         bonk.put(uri1, doc1);
	         bonk.put(uri2,doc2);
	         bonk.put(uri3, doc3);
	         bonk.moveToDisk(uri1);
	         bonk.moveToDisk(uri2);
	         bonk.moveToDisk(uri3);
	         
         bonk.get(uri1);
         bonk.get(uri2);
      //   bonk.get(uri3);	        
		        
		        
		        
	         
	         
	}

}
