package edu.yu.cs.com1320.project.stage5.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;

class BTreeImplTest {

	@Test
	void putTest() throws URISyntaxException {
		BTreeImpl bonk= new BTreeImpl();
		
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "BonkeyDoo";

	  
	         
	        //init possible values for doc2
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	String txt2 = "BonkeyDoo larry";

	        //init possible values for doc3
	URI uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
	        String txt3 = "BonkeyDoo Gary";

	        //init possible values for doc4
	        URI uri4 = new URI("http://edu.yu.cs/com1320/project/doc4");
	         String txt4 = "BonkeyDoo BonkeyDoo Gary larry perry";
	         
	         DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	         DocumentImpl doc2= new DocumentImpl(uri2,txt2, null);
	         DocumentImpl doc3= new DocumentImpl(uri3,txt3, null);
	         DocumentImpl doc4= new DocumentImpl(uri4,txt4, null);
		
	
		bonk.put(uri1, doc1);
		bonk.put(uri2, doc2);
		bonk.put(uri3, doc3);
		bonk.put(uri4, doc4);
	
	assert(bonk.get(uri1).equals(doc1));
	assert(bonk.get(uri2).equals(doc2));
	assert(bonk.get(uri3).equals(doc3));
	assert(bonk.get(uri4).equals(doc4));
	assert(!bonk.get(uri4).equals(doc3));
		
	}
	
	@Test
	void movetoDiskTestTXTDOC() throws Exception {	//check document was moved to disk-> meaning also a file was created with json data
		BTreeImpl bonk= new BTreeImpl();
		bonk.setPersistenceManager(new DocumentPersistenceManager(null));
		File baseDir=null;
		URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        String txt1 = "BonkeyDoo";

    	String path=uri1.getSchemeSpecificPart();
    	path=path.substring(1,path.length());
    	path=baseDir+"/"+path+".json";
  	if(baseDir==null) {
  	path=path.substring(6, path.length());
  	}
   	
		  
	  
	         
	  
	         
	         DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	         bonk.put(uri1, doc1);
	         assert(bonk.get(uri1).equals(doc1));
	         bonk.moveToDisk(uri1);
	        assert(bonk.get(uri1).equals(doc1));
	         
	      
	         
	        
	       
	}
	
	@Test
	void movetoDiskTestBINARYDOC() throws Exception {	//check document was moved to disk-> meaning also a file was created with json data
		BTreeImpl bonk= new BTreeImpl();
		bonk.setPersistenceManager(new DocumentPersistenceManager(null));
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "BonkeyDoo";
File baseDir=null;

	         
	         DocumentImpl doc1= new DocumentImpl(uri1, txt1.getBytes());
	         bonk.put(uri1, doc1);
	         assert(bonk.get(uri1).equals(doc1));
	         bonk.moveToDisk(uri1);

	     	String path=uri1.getSchemeSpecificPart();
	     	path=path.substring(1,path.length());
	     	path=baseDir+"/"+path+".json";
	   	if(baseDir==null) {
	   	path=path.substring(6, path.length());
	   	}
	    	
	 		
	    	
	         File file= new File(path);
	         assert(file.exists());
	         assert(bonk.get(uri1).equals(doc1));
	       assert(!file.exists());
	         
	        
	       
	}
	
	@Test 
	void putTestOnDocumentinDisk() throws Exception {
		BTreeImpl bonk= new BTreeImpl();
		bonk.setPersistenceManager(new DocumentPersistenceManager(null));
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "BonkeyDoo";

	  
	         
	        //init possible values for doc2
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	String txt2 = "BonkeyDoo larry";

	        //init possible values for doc3
	URI uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
	        String txt3 = "BonkeyDoo Gary";

	        //init possible values for doc4
	        URI uri4 = new URI("http://edu.yu.cs/com1320/project/doc4");
	         String txt4 = "BonkeyDoo BonkeyDoo Gary larry perry";
	         File baseDir=null;
	         DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	         DocumentImpl doc2= new DocumentImpl(uri2,txt2, null);
	         bonk.put(uri1, doc1);
	         assert(bonk.get(uri1).equals(doc1));
       
		     	String path=uri1.getSchemeSpecificPart();
		     	path=path.substring(1,path.length());
		     	path=baseDir+"/"+path+".json";
		   	if(baseDir==null) {
		   	path=path.substring(6, path.length());
		   	}
		       
	         File file= new File(path);
	         assert(!file.exists());
	         
	         bonk.moveToDisk(uri1);
	         assert(file.exists());
	         bonk.put(uri1, doc2);
	         assertFalse(file.exists());
	         assert(bonk.get(uri1).equals(doc2));
	}
	
	@Test
	void getOldValueTest() {
		BTreeImpl Larry= new BTreeImpl();
		Larry.put(1, "one");
		assert(Larry.put(1, "Juan").equals("one"));
	}
	
	@Test 
	void movetoDiskTestTXTDOCBASEDIRNOTNULL() throws Exception {	//check document was moved to disk-> meaning also a file was created with json data
		BTreeImpl bonk= new BTreeImpl();
		File baseDir=new File("C:\\Users\\eohay\\OneDrive\\College Work\\Git Stuffs\\Ohayon_Elad_9042333899\\DataStructures\\project\\stage5\\barry");
		bonk.setPersistenceManager(new DocumentPersistenceManager(	baseDir		));
	
		URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
        String txt1 = "BonkeyDoo";

        String path=uri1.getSchemeSpecificPart();
	       path=path.substring(1,path.length());
   	DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);
	         bonk.put(uri1, doc1);
	         assert(bonk.get(uri1).equals(doc1));
	         bonk.moveToDisk(uri1);
	         
	       
	        assert(bonk.get(uri1).equals(doc1));
	         
	      
	         
	        
	       
	}
	
	
	
	@Test
	void movetoDiskTestBINARYDOCBASEDIRNOTNULL() throws Exception {	//check document was moved to disk-> meaning also a file was created with json data
		BTreeImpl bonk= new BTreeImpl();
		File baseDir=new File("C:\\Users\\eohay\\OneDrive\\College Work\\Git Stuffs\\Ohayon_Elad_9042333899\\DataStructures\\project\\stage5\\barry");
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "BonkeyDoo";
	 		bonk.setPersistenceManager(new DocumentPersistenceManager(baseDir));

	         
	         DocumentImpl doc1= new DocumentImpl(uri1, txt1.getBytes());
	         bonk.put(uri1, doc1);
	         assert(bonk.get(uri1).equals(doc1));
	         bonk.moveToDisk(uri1);
	         

	     	String path=uri1.getSchemeSpecificPart();
	     	path=path.substring(1,path.length());
	     	path=baseDir+"/"+path+".json";
	   	File file= new File(path);
	        assert(file.exists());
	        
	        assert(bonk.get(uri1).equals(doc1));
	       assert(!file.exists());
	         
	        
	       
	}
	
	//@Test 
	void putTestOnDocumentinDiskBASEDIENOTNULL() throws Exception {
		BTreeImpl bonk= new BTreeImpl();
		File baseDir=new File("C:\\Users\\eohay\\OneDrive\\College Work\\Git Stuffs\\Ohayon_Elad_9042333899\\DataStructures\\project\\stage5\\barry");
		bonk.setPersistenceManager(new DocumentPersistenceManager(		baseDir	));
		  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
	         String txt1 = "BonkeyDoo";

	  
	         
	        //init possible values for doc2
	        URI uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
	String txt2 = "BonkeyDoo larry";

	        //init possible values for doc3
	URI uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
	        String txt3 = "BonkeyDoo Gary";

	        //init possible values for doc4
	        URI uri4 = new URI("http://edu.yu.cs/com1320/project/doc4");
	         String txt4 = "BonkeyDoo BonkeyDoo Gary larry perry";
	         DocumentImpl doc1= new DocumentImpl(uri1,txt1,null);
	         DocumentImpl doc2= new DocumentImpl(uri2,txt2,null);
	         bonk.put(uri1, doc1);
	         
	       
	         String path=uri1.getSchemeSpecificPart();
		       path=path.substring(1,path.length());
		    	
		    	
	         File file= new File(path);
	         assert(!file.exists());
	         
	         bonk.moveToDisk(uri1);
	         assert(file.exists());
	         bonk.put(uri1, doc2);
	         assertFalse(file.exists());
	         assert(bonk.get(uri1).equals(doc2));
	}
	
	
	
@Test
void putinDiskandBringBack() throws Exception {
	BTreeImpl bonk= new BTreeImpl();
	File baseDir=new File("C:\\Users\\eohay\\OneDrive\\College Work\\Git Stuffs\\Ohayon_Elad_9042333899\\DataStructures\\project\\stage5\\barry");
	bonk.setPersistenceManager(new DocumentPersistenceManager(		baseDir	));
	  URI uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
         String txt1 = "BonkeyDoo";
         
         
         
         String path=uri1.getSchemeSpecificPart();
	       path=path.substring(2,path.length());
	       path=baseDir+"/"+path+".json";
       File file= new File(path);
       
   	System.out.println(path);
         DocumentImpl doc1= new DocumentImpl(uri1,txt1,null);
         bonk.put(uri1, doc1);
         assertFalse(file.exists());
         bonk.moveToDisk(uri1);
         assertTrue(file.exists());
         bonk.get(uri1);
         assertFalse(file.exists());
         
	    	
	    
	    	
         
         
}
	
	
	//@Test
	void putTest2() throws URISyntaxException {
		BTreeImpl st= new BTreeImpl();
		
		
	     // WrongBTree<Integer, String> st = new WrongBTree<Integer, String>();
	        st.put(1, "one");
	        st.put(2, "two");
	        st.put(3, "three");
	        st.put(4, "four");
	        st.put(5, "five");
	        st.put(6, "six");
	        st.put(7, "seven");
	        st.put(8, "eight");
	        st.put(9, "nine");
	        st.put(10, "ten");
	        st.put(11, "eleven");
	        st.put(12, "twelve");
	        st.put(13, "thirteen");
	        st.put(14, "fourteen");
	        st.put(15, "fifteen");
	        st.put(16, "sixteen");
	        st.put(17, "seventeen");
	        st.put(18, "eighteen");
	        st.put(19, "nineteen");
	        st.put(20, "twenty");
	        st.put(21, "twenty one");
	        st.put(22, "twenty two");
	        st.put(23, "twenty three");
	        st.put(24, "twenty four");
	        st.put(25, "twenty five");
	        st.put(26, "twenty six");
	
	        assert(st.get(26).equals("twenty six"));
	        
	        assert(st.get(16).equals("sixteen"));
	
	}
	
	
	

}
