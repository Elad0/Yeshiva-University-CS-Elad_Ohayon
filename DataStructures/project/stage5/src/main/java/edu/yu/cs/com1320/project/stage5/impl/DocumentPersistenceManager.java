package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;
import com.google.gson.Gson;
import java.io.*;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * created by the document store and given to the BTree via a call to BTree.setPersistenceManager
 */
public class DocumentPersistenceManager implements PersistenceManager<URI, Document> {
private File baseDir;


    public DocumentPersistenceManager(File baseDir){
    	if(baseDir!=null) {
    	this.baseDir=baseDir;
    	}
    
    }

    @Override
    public void serialize(URI uri, Document val) throws IOException {
    	
    Gson gson=new Gson();
   String json=gson.toJson(val);
   
  String path=this.getPath(uri);

   File file= new File(path);
 

	File Parentlocation= new File(file.getParent());
	if (!Parentlocation.exists()) {
		
		
		Parentlocation.mkdirs();
	}

	file.createNewFile();



FileWriter JSONfile = new FileWriter(path);

	   JSONfile.write(json); 
	   JSONfile.close();
    	
    }

    @Override
    public Document deserialize(URI uri) throws IOException {
    	Gson gson = new Gson();
    	  String path=this.getPath(uri);


 Reader reader = Files.newBufferedReader(Paths.get(path));

Document docu=gson.fromJson(reader, DocumentImpl.class);
    		    reader.close(); 		    
return docu;

    }

    @Override
    public boolean delete(URI uri) throws IOException {
    	
    	String path =getPath(uri);
    
    	
    	boolean deleted= Files.deleteIfExists(Paths.get(path));
    	
    	deleteDirectory(new File(getPath(uri)));
    	return deleted;
    	/* File file= new File(path);  
    	 File dir=new File(file.getParent());
    	 List<String> check=Arrays.asList(dir.list());
    	 
    	 if(check.isEmpty()) {
    		 dir.delete();
    	 }
    	 return del;*/
    }
    
    private void deleteDirectory(File file) {
   if(file.getParent()==null){
	   return;
   }
    	File dir= new File(file.getParent());
    	
    	if(!dir.exists()) {
    		
    		return;
    	}
    	
    if(dir.isDirectory()) {
    	
    	if(Arrays.asList(dir.listFiles()).isEmpty()) {
    		dir.delete();
    		 deleteDirectory(dir);
    
    }	
    
    	    	
    	   	
    } 
    
    
    }
    
    private String getPath(URI uri) {
    	String path=uri.getSchemeSpecificPart();
    	path=path.substring(1,path.length());
    	/* String path =uri.toString();
  	   path=path.replace("https://", "");
  	   path=path.replace("http://", "");
  	   
  	*/path=this.baseDir+"/"+path+".json";
  	if(this.baseDir==null) {
  	path=path.substring(6, path.length());
  	}
  
  	return path;
    }
}