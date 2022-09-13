package edu.yu.cs.com1320.project.stage2;


import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore.DocumentFormat;
import edu.yu.cs.com1320.project.stage2.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage2.impl.DocumentStoreImpl;

class DocumentStoreImplTest {

	@Test
	void testUndoMostRecent_and_one_element_in_stack() throws URISyntaxException, IOException {
		DocumentStoreImpl store= new DocumentStoreImpl();
		URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
        
        DocumentImpl docu= new DocumentImpl(uri1, txt1);
		store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);

		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
		assert(store.getDocument(uri1).hashCode()==docu.hashCode());
		store.undo(uri1);
		assert(store.getDocument(uri1)==null);

		
		
		//assert(((DocumentImpl)store.getDocument(uri1))!=docu);
		
		//System.out.println(store.getDocument(uri1).getDocumentTxt());

}
	@Test
	void testUndoMostRecent_and_1ormore_elements()throws URISyntaxException, IOException {
		DocumentStoreImpl store= new DocumentStoreImpl();
		
		URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
        
        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
        String txt2 = "Text for doc2. A plain old String.";
        
  
        
        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
        DocumentImpl docu= new DocumentImpl(uri1, txt1);
        
		store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
		store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY);
		
		
		
		assert(((DocumentImpl)store.getDocument(uri2)).equals(docu2));
		store.undo();
		assert(store.getDocument(uri2)==null);
		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
		store.undo();
		assert(store.getDocument(uri1)==null);
		
	}
	@Test
	void testUndoAlreadyExists() throws URISyntaxException, IOException { //put in document 1 for the uri, then replace it with document 2 and then undo the replacement so it should equal doc1
		URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
        String txt2 = "Text for doc2. A plain old String.";
        DocumentStoreImpl store= new DocumentStoreImpl();
        
        DocumentImpl docu= new DocumentImpl(uri1, txt1);
        DocumentImpl docu2= new DocumentImpl(uri1, txt2);
        
		store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
		
		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
		store.putDocument(new ByteArrayInputStream(txt2.getBytes()), uri1,  DocumentFormat.TXT);
		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu2));
		//System.out.println(store.getDocument(uri1).getDocumentTxt());
		store.undo();
		//System.out.println(store.getDocument(uri1).getDocumentTxt());
		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
		store.undo();
		assert(store.getDocument(uri1)==null);
		assert(store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT)==0);
		assert(store.putDocument(new ByteArrayInputStream(txt2.getBytes()), uri1,  DocumentFormat.TXT)==docu.hashCode());
		assert(store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT)==docu2.hashCode());
	}
	
	@Test void UndoURIPutDocument() throws URISyntaxException, IOException{
		 DocumentStoreImpl store= new DocumentStoreImpl();
		URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
        
        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
        String txt2 = "Text for doc2. A plain old String.";
        
  
        
        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
        DocumentImpl docu= new DocumentImpl(uri1, txt1);
        
		store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
		store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY);
		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
		store.undo(uri2);
		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
		store.undo();
		assert(store.getDocument(uri1)==null);
		//assert(((DocumentImpl)store.getDocument(uri2)).equals(docu2));
		//store.undo();
		
		assert(store.getDocument(uri2)==null);
		
	}
	
	@Test
	void UndoURIDeleteDocument() throws IOException, URISyntaxException {
		
		 DocumentStoreImpl store= new DocumentStoreImpl();
			URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
	        
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	        String txt2 = "Text for doc2. A plain old String.";
	        
	  
	        
	        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
	        DocumentImpl docu= new DocumentImpl(uri1, txt1);
	        
			store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
			store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY);
			assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
			store.deleteDocument(uri1);
			assert(store.getDocument(uri1)==null);
			
			store.undo();
			//assert(store.getDocument(uri1)==null);
			
		assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
		store.undo();
			assert(store.getDocument(uri2)==null);
	}
	@Test
	void Undo_Delete_In_Middle() throws URISyntaxException, IOException {
		 DocumentStoreImpl store= new DocumentStoreImpl();
			URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
	        
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	        String txt2 = "Text for doc2. A plain old String.";
	        
	  
	        
	        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
	        DocumentImpl docu= new DocumentImpl(uri1, txt1);
	        
			store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
			store.deleteDocument(uri1);
			store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY);
			
			
			assert(store.getDocument(uri1)==null);
			store.undo();
			assert(store.getDocument(uri2)==null);
			store.undo();
			assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
			
			
			
			store.undo();
			//assert(store.getDocument(uri1)==null);
			
			assert(store.getDocument(uri1)==null);
		
			assert(store.getDocument(uri2)==null);
	}
	
	@Test void Undo_Delete_Using_URI_IN_Middle() throws URISyntaxException, IOException {
		 DocumentStoreImpl store= new DocumentStoreImpl();
			URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
	        
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	        String txt2 = "Text for doc2. A plain old String.";
	        
	  
	        
	        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
	        DocumentImpl docu= new DocumentImpl(uri1, txt1);
	        
			store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
			store.deleteDocument(uri1);
			store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY);
			
			
			assert(store.getDocument(uri1)==null);
			store.undo(uri1);
			assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));

			store.undo();
			assert(store.getDocument(uri2)==null);
			store.undo();
			assert(store.getDocument(uri1)==null);
	}
	@Test
	void Delete_PUTDOCU_NUll() throws URISyntaxException, IOException {
		 DocumentStoreImpl store= new DocumentStoreImpl();
			URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
	        
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	        String txt2 = "Text for doc2. A plain old String.";
	        
	  
	        
	        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
	        DocumentImpl docu= new DocumentImpl(uri1, txt1);
	        
		
			store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY);
			store.putDocument(null,uri2, DocumentFormat.BINARY);
			store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
			
			assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
			//assert(((DocumentImpl)store.getDocument(uri2)).equals(docu2));
			assert(!((DocumentImpl)store.getDocument(uri1)).equals(docu2));
		
			
			assert(store.getDocument(uri2)==null);
			store.undo(uri2);
			
			assert(((DocumentImpl)store.getDocument(uri2)).equals(docu2));
		
		
		
		
	}
	
	@Test
	void simplePut() throws URISyntaxException, IOException {
		 DocumentStoreImpl store= new DocumentStoreImpl();
			URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
	        
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	        String txt2 = "Text for doc2. A plain old String.";
	        
	  
	        
	        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
	        DocumentImpl docu= new DocumentImpl(uri1, txt1);

			assert(store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY)==0);
			assert(store.putDocument(null,uri2, DocumentFormat.BINARY)==docu2.hashCode());
	}
	@Test
	void duplicateURIINUNDO() throws URISyntaxException, IOException{
		 DocumentStoreImpl store= new DocumentStoreImpl();
			URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	        String txt1 = "This is the text of doc1, in plain text. No fancy file format - just plain old String";
	        
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	        String txt2 = "Text for doc2. A plain old String.";
	        
	  
	        DocumentImpl docu= new DocumentImpl(uri1, txt1);
	        DocumentImpl docu2= new DocumentImpl(uri2, txt2.getBytes());
	        DocumentImpl docu3= new DocumentImpl(uri2, txt2);
	        
	        store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentFormat.TXT);
	        store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri1, DocumentFormat.TXT);
	        store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentFormat.BINARY);
	    	assert((store.getDocument(uri1).getDocumentTxt()).equals(txt2));
	    	store.undo(uri1);
	    	assert(((DocumentImpl)store.getDocument(uri1)).equals(docu));
	    	
	    	store.undo(uri1);
	    	assert(store.getDocument(uri1)==null);
	    	
	    	
	    	store.undo();
	    	assert(store.getDocument(uri2)==null);
	    	System.out.println("Bdonkey");
	    	
	}
	
	
	
}
