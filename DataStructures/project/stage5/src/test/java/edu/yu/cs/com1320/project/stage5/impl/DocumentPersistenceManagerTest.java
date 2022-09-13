package edu.yu.cs.com1320.project.stage5.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class DocumentPersistenceManagerTest {

	//serialize and deserialize work when basedir=null
	
	//test when base directory is not null
	@Test
	void SerializeandDeserialize() throws Exception {
		DocumentPersistenceManager gary= new DocumentPersistenceManager(null);
		
		URI uri1 = new URI("http://www.yu.edu/documents/docabcdef");
		 String txt1 = "BonkeyDoo";
	     DocumentImpl doc1= new DocumentImpl(uri1,txt1, null);   	
		gary.serialize(uri1, doc1);
		String path=getPath(uri1);
		
		Path loc = Paths.get(path);
		assert(Files.exists(loc));
		DocumentImpl docu=(DocumentImpl) gary.deserialize(uri1);
		assert(docu.equals(doc1));
		assert(gary.delete(uri1)==true);
		assertFalse(gary.delete(uri1));
		assertFalse(Files.exists(loc));
		
		
	}

	
	private String getPath(URI uri) {
		File baseDir=null;
		 String path =uri.toString();
	  	   path=path.replace("https://", "");
	  	   path=path.replace("http://", "");
	  	path=baseDir+"/"+path+".json";
	  	if(baseDir==null) {
	  	path=path.substring(5, path.length());
	  	}
	  	
	  	return path;
	}
}
