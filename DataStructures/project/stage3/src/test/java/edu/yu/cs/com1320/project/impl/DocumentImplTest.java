package edu.yu.cs.com1320.project.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.stage3.impl.DocumentImpl;

class DocumentImplTest {

/*	@Test
	void  isAlphaNumericandSpace() throws URISyntaxException {
		URI textUri = new URI("http://edu.yu.cs/com1320/txt");
      //String textString = " lots This is This text content  text    fart it it Lots Lots of it fart fart. content it is is This";
		  String textString = "Pie thiS PIE This is text content. Of[]@ Lots  it.";
		  DocumentImpl textDocument = new DocumentImpl(textUri, textString);
	
			URI BytbinaryUri = new URI("http://edu.yu.cs/com1320/binary");
	        byte[] binaryData = ("hi is a PDF, brought to you by Adobe.".getBytes());
	        DocumentImpl binarydoc= new DocumentImpl(BytbinaryUri, binaryData);
	        
	        
	        
	   
		for(String word: textDocument.getWords()) {
			//if(word.equals("CONTENT")) {
		System.out.println(word+ " "+textDocument.wordCount(word));
			//}
			//System.out.println(word);
		}
		
		 
	}*/
	
	@Test 
		void TestBinary() throws URISyntaxException{
		
		
		URI BytbinaryUri = new URI("http://edu.yu.cs/com1320/binary");
        byte[] binaryData = ("hi is a PDF, brought to you by Adobe.".getBytes());
        DocumentImpl binarydoc= new DocumentImpl(BytbinaryUri, binaryData);
        System.out.println(binarydoc.getWords());
        
	}

}
