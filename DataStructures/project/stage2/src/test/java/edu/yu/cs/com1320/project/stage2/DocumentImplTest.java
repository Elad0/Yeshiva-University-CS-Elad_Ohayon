package edu.yu.cs.com1320.project.stage2;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import edu.yu.cs.com1320.project.stage2.impl.DocumentImpl;

class DocumentImplTest {

	@Test
	void hashcodeTest() throws URISyntaxException {
		
		URI textUri = new URI("http://edu.yu.cs/com1320/txt");
        String textString = "This is text content. Lots of it.";
		  DocumentImpl textDocument = new DocumentImpl(textUri, textString);
		  int hash=textDocument.hashCode();
		  System.out.println(hash);
		  assert(hash==-1643885098);
	}
	
	@Test
	void hashcodeTestBinary() throws URISyntaxException {
		
		URI BytbinaryUri = new URI("http://edu.yu.cs/com1320/binary");
        byte[] binaryData = ("This is a PDF, brought to you by Adobe.".getBytes());
    
		  DocumentImpl binarydoc= new DocumentImpl(BytbinaryUri, binaryData);
		  int hash=binarydoc.hashCode();
		  
		  assert(hash==-667769682);
	}

}
