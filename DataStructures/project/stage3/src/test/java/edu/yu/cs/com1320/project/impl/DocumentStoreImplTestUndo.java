package edu.yu.cs.com1320.project.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.stage3.DocumentStore;
import edu.yu.cs.com1320.project.stage3.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage3.impl.DocumentStoreImpl;
class DocumentStoreImplTestUndo {

	@Test
	void testDeleteAllPrefixUndo() throws URISyntaxException, IOException {	//put documents the store and then delete them based off prefix passed
		DocumentStoreImpl larry= new DocumentStoreImpl();
		  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
		  String textString1 = "PIE";
		  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
		  
		  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
		  String textString2 = "PIE PIE";
		  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
		  
		  URI textUri3 = new URI("http://edu.yu.cs/com1320/txt3");
		  String textString3 = "PIE  donkey";
		  DocumentImpl textDocument3 = new DocumentImpl(textUri3, textString3);

		  URI textUri4 = new URI("http://edu.yu.cs/com1320/txt4");
		  String textString4 = "PIE PIE PIE PIE";
		  DocumentImpl textDocument4 = new DocumentImpl(textUri4, textString4);
		  
		  URI textUri5 = new URI("http://edu.yu.cs/com1320/txt5");
		  String textString5 = " p p p p p p pie foo ";
		  DocumentImpl textDocument5 = new DocumentImpl(textUri5, textString5);
		  
		  
		  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);

		  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);

		 larry.putDocument(new ByteArrayInputStream(textString5.getBytes()),textUri5, DocumentStore.DocumentFormat.TXT);
	 larry.putDocument(new ByteArrayInputStream(textString5.getBytes()),textUri5, DocumentStore.DocumentFormat.TXT);
		 
		 larry.putDocument(new ByteArrayInputStream(textString3.getBytes()),textUri3, DocumentStore.DocumentFormat.TXT);
	
	  larry.putDocument(new ByteArrayInputStream(textString4.getBytes()),textUri4, DocumentStore.DocumentFormat.TXT);
		
		  System.out.println(larry.getDocument(textUri5));
	
		 assert( larry.deleteAllWithPrefix("piE").size()==5);
	
		 
		//System.out.println(larry.deleteDocument(textUri5)) ;
		 System.out.println(larry.getDocument(textUri5));

		

larry.undo(); 

System.out.println(larry.getDocument(textUri5));

		//larry.undo();
		
		for(Document docu: larry.searchByPrefix("pi")) {
			System.out.println(docu.getKey());
		}
	
	}
	
	@Test
	void testDeleteAllPrefixUndowithURI() throws URISyntaxException, IOException {	//put documents the store and then delete them based off prefix passed
		DocumentStoreImpl larry= new DocumentStoreImpl();
		  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
		  String textString1 = "PIE";
		  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
		  
		  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
		  String textString2 = "PIE PIE";
		  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
		  
		  URI textUri3 = new URI("http://edu.yu.cs/com1320/txt3");
		  String textString3 = "PIE  donkey";
		  DocumentImpl textDocument3 = new DocumentImpl(textUri3, textString3);

		  URI textUri4 = new URI("http://edu.yu.cs/com1320/txt4");
		  String textString4 = "PIE PIE PIE PIE";
		  DocumentImpl textDocument4 = new DocumentImpl(textUri4, textString4);
		  
		  URI textUri5 = new URI("http://edu.yu.cs/com1320/txt5");
		  String textString5 = " pie foo ";
		  DocumentImpl textDocument5 = new DocumentImpl(textUri5, textString5);
		  
		  
		  URI textUri6 = new URI("http://edu.yu.cs/com1320/txt6");
		  String textString6 = "  foo ";
		  DocumentImpl textDocument6 = new DocumentImpl(textUri6, textString6);
		  
		  
		  
		  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);

		  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);

		 larry.putDocument(new ByteArrayInputStream(textString5.getBytes()),textUri5, DocumentStore.DocumentFormat.TXT);
	 larry.putDocument(new ByteArrayInputStream(textString5.getBytes()),textUri5, DocumentStore.DocumentFormat.TXT);
		 
		 larry.putDocument(new ByteArrayInputStream(textString3.getBytes()),textUri3, DocumentStore.DocumentFormat.TXT);
	
	  larry.putDocument(new ByteArrayInputStream(textString4.getBytes()),textUri4, DocumentStore.DocumentFormat.TXT);
	  larry.putDocument(new ByteArrayInputStream(textString6.getBytes()),textUri6, DocumentStore.DocumentFormat.TXT);
	  
	  
		 
		  assert( larry.deleteAllWithPrefix("piE").size()==5); 		//test undo(uri) when first and in middle
		  


System.out.println(larry.getDocument(textUri1));	//should be null
System.out.println(larry.getDocument(textUri4));	//should be null

System.out.println(larry.getDocument(textUri6)+"not null");	//notnull
larry.undo(textUri4);

System.out.println(larry.getDocument(textUri1));
	assert(larry.search("pie").size()==1);

	
	}
	
@Test
void simpleTestDeleteDocument() throws URISyntaxException, IOException {
	DocumentStoreImpl larry= new DocumentStoreImpl();
	  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
	  String textString1 = "PIE";
	  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
	  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);
	  
	  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
	  String textString2 = "fh";
	  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
	  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);
	  
	  
	  assert(larry.search("pi").size()==0);
	  assert(larry.search("pie").size()==1);
	  assert(larry.searchByPrefix("pi").size()==1);
	  assert(larry.getDocument(textUri1).equals(textDocument));
	  
	  larry.deleteDocument(textUri1);
	  assert(larry.search("pie").size()==0);
	  assert(larry.searchByPrefix("pi").size()==0);
	  assert(larry.getDocument(textUri1)==null);
	  larry.undo(textUri1);
	  
	  assert(larry.search("pi").size()==0);
	  assert(larry.search("pie").size()==1);
	  assert(larry.searchByPrefix("pi").size()==1);
	  assert(larry.getDocument(textUri1).equals(textDocument));
	  
}
@Test 
void simpleTestDeleteAll() throws URISyntaxException, IOException {
	DocumentStoreImpl larry= new DocumentStoreImpl();
	  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
	  String textString1 = "PIE";
	  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
	  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);
	  
	  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
	  String textString2 = "pi";
	  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
	  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);
	  
	  
	  assert(larry.search("pi").size()==1);
	  assert(larry.search("pie").size()==1);
	  assert(larry.searchByPrefix("pi").size()==2);
	  assert(larry.getDocument(textUri1).equals(textDocument));
	  
	larry.deleteAll("pie");
	  assert(larry.search("pie").size()==0);
	  assert(larry.searchByPrefix("pi").size()==1);
	  assert(larry.getDocument(textUri1)==null);
	  larry.undo(textUri1);
	  
	  assert(larry.search("pi").size()==1);
	  assert(larry.search("pie").size()==1);
	  assert(larry.searchByPrefix("pi").size()==2);
	  assert(larry.getDocument(textUri1).equals(textDocument));
}

@Test 
void simpleTestDeleteAllPrefix() throws URISyntaxException, IOException {
	DocumentStoreImpl larry= new DocumentStoreImpl();
	  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
	  String textString1 = "PIE";
	  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
	  larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT);
	  
	  URI textUri2 = new URI("http://edu.yu.cs/com1320/txt2");
	  String textString2 = "pi";
	  DocumentImpl textDocument2 = new DocumentImpl(textUri2, textString2);
	  larry.putDocument(new ByteArrayInputStream(textString2.getBytes()),textUri2, DocumentStore.DocumentFormat.TXT);
	  
	  
	  assert(larry.search("pi").size()==1);
	  assert(larry.search("pie").size()==1);
	  assert(larry.searchByPrefix("pi").size()==2);
	  assert(larry.getDocument(textUri1).equals(textDocument));
	  
	larry.deleteAllWithPrefix("p");
	  assert(larry.search("pie").size()==0);
	  assert(larry.searchByPrefix("p").size()==0);
	  assert(larry.getDocument(textUri1)==null);
	  larry.undo();
	  
	  assert(larry.search("pi").size()==1);
	  assert(larry.search("pie").size()==1);
	  assert(larry.searchByPrefix("pi").size()==2);
	  assert(larry.getDocument(textUri1).equals(textDocument));
	 assert(larry.getDocument(textUri2).equals(textDocument2));
	
}


@Test 
void putDocumentTests() throws URISyntaxException, IOException{
	DocumentStoreImpl larry= new DocumentStoreImpl();
	  URI textUri1 = new URI("http://edu.yu.cs/com1320/txt");
	  String textString1 = "PIE";
	  DocumentImpl textDocument = new DocumentImpl(textUri1, textString1);
	  assert(larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT)==0);
	
	assert(((DocumentImpl)larry.getDocument(textUri1)).equals(textDocument));
	assert(larry.search("Pie").size()==1);
	assert(larry.searchByPrefix("p").size()==1);
	
	larry.undo(textUri1);
	assert(larry.getDocument(textUri1)==null);
	System.out.println(larry.getDocument(textUri1));
	assert(larry.search("Pie").size()==0);
	assert(larry.searchByPrefix("p").size()==0);
	
	
	assert(larry.putDocument(new ByteArrayInputStream(textString1.getBytes()),textUri1, DocumentStore.DocumentFormat.TXT)==0);
	
	assert(larry.search("Pie").size()==1);
	assert(larry.searchByPrefix("p").size()==1);
	assert(larry.putDocument(null,textUri1, DocumentStore.DocumentFormat.TXT)==textDocument.hashCode());
	
	assert(larry.search("Pie").size()==0);
	assert(larry.searchByPrefix("p").size()==0);
	assert(larry.getDocument(textUri1)==null);
	
	
	larry.undo();
	assert(larry.search("Pie").size()==1);
	assert(larry.searchByPrefix("p").size()==1);
	
	assert(((DocumentImpl)larry.getDocument(textUri1)).equals(textDocument));
	larry.deleteDocument(textUri1);
	assert(larry.getDocument(textUri1)==null);
	assert(larry.search("Pie").size()==0);
	assert(larry.searchByPrefix("p").size()==0);
	larry.undo();
	assert(larry.search("Pie").size()==1);
	assert(larry.searchByPrefix("p").size()==1);
	
	
	
}

}
