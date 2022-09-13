package edu.yu.cs.com1320.project.stage5.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;

class DocumentStoreImplTest5 {

	@Test
	void binaryDoc() throws IOException, URISyntaxException {
	//test moving to disk and getting back a binary document
		//test bringing a document back from memory that will delete another
}
	
	@Test
	void removeFromMemory() throws Exception {	//test removing a document from memory to disk but it is still in trie
		 
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";

	  
	         
	        //init possible values for doc2
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	String txt2 = "bo bo bo bo bo bo bo ";

	        //init possible values for doc3
	URI uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
	        String txt3 = "Bo BO BO ";

	        //init possible values for doc4
	        URI uri4 = new URI("http://edu.yu.cs/com1320/project/doc4");
	         String txt4 = "BonkeyDoo BonkeyDoo Gary larry perry";
	         
	         DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	         DocumentImpl doc2= new DocumentImpl(uri2,txt2, null);
	         DocumentImpl doc3= new DocumentImpl(uri3,txt3, null);
	         DocumentImpl doc4= new DocumentImpl(uri4,txt4, null);
			
			 DocumentStoreImpl store = new DocumentStoreImpl();
			 

		        store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
		        store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2, DocumentStore.DocumentFormat.TXT);		//do the undo but with document 4 and 3/2
		        store.putDocument(new ByteArrayInputStream(txt3.getBytes()),uri3, DocumentStore.DocumentFormat.TXT);
		        store.putDocument(new ByteArrayInputStream(txt4.getBytes()),uri4, DocumentStore.DocumentFormat.TXT);
		        
		
		        store.setMaxDocumentCount(3);
		        
		      
		    assert(store.searchByPrefix("Bo").size()==4);
		    

		  
		        store.deleteDocument(uri1);
		        assert(store.searchByPrefix("Bo").size()==3);
		 
		        assert(store.getDocument(uri1)==null);
		        store.deleteDocument(uri2);
		        assert(store.getDocument(uri2)==null);
		    
		          
	}
	
	@Test
	void testReplaceDocument() throws Exception {
		
		 DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";

	  
	         
	        //init possible values for doc2
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	String txt2 = "Bo";
	DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	DocumentImpl doc2= new DocumentImpl(uri1,txt2, null);
    store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
    
	assert(store.getDocument(uri1).equals(doc1));
	store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
	assert(store.getDocument(uri1).equals(doc2));
	store.undo();
	assert(store.getDocument(uri1).equals(doc1));
	}
	
	
	@Test void voidtestnewconstructor() throws Exception{
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	         
	         Map<String, Integer> wordCount= new HashMap<String, Integer>();
	         wordCount.put("H", 1000);
	         wordCount.put("ER", 10000);
	         
	         DocumentImpl test= new DocumentImpl(uri1, txt1, wordCount);
	         assertTrue(test.getWordMap().equals(wordCount));
	         
	}
	
	
	@Test
	void testDocumentRemovedFromMemoeryNowInDisk() throws Exception {
		 DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	         store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
	  store.setMaxDocumentCount(0);
	  File file= new File(createPath(uri1));
      assert(file.exists());
    
	
	
	         
	}
	
	@Test
	void testDocumentInDiskThenDeleted() throws Exception {
		 DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	     	String txt2 = "Bo";
	         store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
	  store.setMaxDocumentCount(0);
	  File file= new File(createPath(uri1));
      assert(file.exists());
    
      store.setMaxDocumentCount(10);
      

      store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
      assert(store.getDocument(uri1).getDocumentTxt().equals(txt2));
  
     assertFalse(file.exists());
       
	         
	}
	
	
	@Test
	void testDocumentInDiskThenBroughtBack() throws Exception {
		 DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	     	String txt2 = "Bo";
	      DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	        
	  store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
	  store.setMaxDocumentCount(0);
	  File file= new File(createPath(uri1));
      assert(file.exists());
    
      store.setMaxDocumentCount(1000000);
      assert(store.getDocument(uri1).equals(doc1));
      assertFalse(file.exists());

      assert(store.getDocument(uri1).equals(doc1));
	}
	
	@Test
	void testDocumentInDiskThenBroughtBackNONNULLBASEDIR() throws Exception {
		File baseDir= new File(("C:\\Users\\eohay\\OneDrive\\College Work\\Git Stuffs\\Ohayon_Elad_9042333899\\DataStructures\\project\\stage5\\barry"));
		 DocumentStoreImpl store = new DocumentStoreImpl(baseDir);
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	     	String txt2 = "Bo";
	      DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	        
	  store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
	  store.setMaxDocumentCount(0);
	  
	 	String path=uri1.getSchemeSpecificPart();
    	path=path.substring(1,path.length());
    	/* String path =uri.toString();
  	   path=path.replace("https://", "");
  	   path=path.replace("http://", "");
  	   
  	*/path=baseDir+"/"+path+".json";
  	if(baseDir==null) {
  	path=path.substring(6, path.length());
  	}
  	
	  File file= new File(path);
      assert(file.exists());
    
      store.setMaxDocumentCount(1000000);
      assert(store.getDocument(uri1).equals(doc1));
      assertFalse(file.exists());
   
      assert(store.getDocument(uri1).equals(doc1));
	}
	
	
	@Test
	void searchTest() throws Exception {
		DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	         URI uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
		        String txt3 = "lkj";

	         
	         store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
	         store.setMaxDocumentCount(0);
	       
	         store.setMaxDocumentCount(1);
	         store.putDocument(new ByteArrayInputStream(txt3.getBytes()),uri3, DocumentStore.DocumentFormat.TXT);
	         store.search("bO");
	      
	         assertTrue(new File(createPath(uri3)).exists());
	         
	}
	
	@Test void undoreplaceDocinDIsk() throws Exception {
		 DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";

	  
	         
	        //init possible values for doc2
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	String txt2 = "Bo";
	DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	DocumentImpl doc2= new DocumentImpl(uri1,txt2, null);
   store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
   
	assert(store.getDocument(uri1).equals(doc1));

	store.setMaxDocumentCount(0);
	store.setMaxDocumentCount(1);

	 
	assertTrue(new File(createPath(uri1)).exists());
	
	store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);

	 
	assertFalse(new File(createPath(uri1)).exists());
	assert(store.getDocument(uri1).equals(doc2));
	
	
	store.undo();
	
	}
	
	@Test void docinDiskBroughtBack() throws Exception {
		 DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	         
	         URI uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
		        String txt3 = "lkj";
		       DocumentImpl doc3= new DocumentImpl(uri3, txt3, null);
		       DocumentImpl doc1= new DocumentImpl(uri1, txt1, null);
		        store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
		        store.setMaxDocumentCount(1);
		        assert(store.getDocument(uri1)!=null);
		        store.putDocument(new ByteArrayInputStream(txt3.getBytes()),uri3, DocumentStore.DocumentFormat.TXT);
		
		        File f =new File(createPath(uri1));
		        assert(f.exists());
		        assert(store.getDocument(uri3).equals(doc3));
		        store.undo();
		         
		        assert(store.getDocument(uri3)==null);
		        assert(!f.exists());
		        assert(store.getDocument(uri1).equals(doc1));
		        
		   
	}
	
	@Test void undoreplacedocinDisk() throws Exception {
		DocumentStoreImpl store = new DocumentStoreImpl();
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "Bo Bo Bo Bo ";
	String txt2 = "Bo";
	DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	DocumentImpl doc2= new DocumentImpl(uri1,txt2, null);
  store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
  
	assert(store.getDocument(uri1).equals(doc1));
	store.setMaxDocumentBytes(0);
	store.setMaxDocumentBytes(66666660);
	assertTrue(new File(createPath(uri1)).exists());
	 
	store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
	 
	assertFalse(new File(createPath(uri1)).exists());
	assert(store.getDocument(uri1).equals(doc2));
	store.undo();
	
	 
	
	assertTrue(new File(createPath(uri1)).exists());
	assert(store.getDocument(uri1).equals(doc1));
	 
	assertFalse(new File(createPath(uri1)).exists());
	}
	
	

	/*public String createPath(URI uri) {
		 File baseDir=null;
		  String path =uri.toString();
		   path=path.replace("https://", "");
		   path=path.replace("http://", "");
		path=baseDir+"/"+path+".json";
		if(baseDir==null) {
		path=path.substring(5, path.length());
		}
		return path;
	}*/
	
public String createPath(URI uri) {
	File baseDir=null;
	String path=uri.getSchemeSpecificPart();
	path=path.substring(1,path.length());
	/* String path =uri.toString();
	   path=path.replace("https://", "");
	   path=path.replace("http://", "");
	   
	*/path=baseDir+"/"+path+".json";
	if(baseDir==null) {
	path=path.substring(6, path.length());
	}

	return path;
}


@Test
void UndoinDiskWithPutNull() throws Exception {
	DocumentStoreImpl store = new DocumentStoreImpl();
	  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
       String txt1 = "Bo Bo Bo Bo ";
       store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1, DocumentStore.DocumentFormat.TXT);
       Document doc1= new DocumentImpl(uri1,txt1, null);
       assert(store.getDocument(uri1).equals(doc1));
       store.setMaxDocumentCount(0);
       store.setMaxDocumentCount(10);
       assert(new File(createPath(uri1)).exists());
        
       store.putDocument(null,uri1, DocumentStore.DocumentFormat.TXT);
       assertFalse(new File(createPath(uri1)).exists());
       assert(store.getDocument(uri1)==null);
       store.undo();
       assertFalse(new File(createPath(uri1)).exists());
       assert(store.getDocument(uri1).equals(doc1));
       
}
}
	

