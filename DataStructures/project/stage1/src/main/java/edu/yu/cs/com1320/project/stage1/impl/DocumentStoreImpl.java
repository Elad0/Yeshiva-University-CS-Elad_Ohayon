package edu.yu.cs.com1320.project.stage1.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage1.Document;



//stage 1









public class DocumentStoreImpl implements  edu.yu.cs.com1320.project.stage1.DocumentStore{


	
	
	
	
	private HashTableImpl HashTable;
	
	
	private String formats;
	
	public DocumentStoreImpl() {
		this.HashTable=new HashTableImpl();
		
	}
	  
	
	@Override
	public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
		if(uri==null) {
			throw new IllegalArgumentException();
		}
		
		
		if(input==null) {
			int hashOldDoc= ((DocumentImpl) oldDoc(uri)).hashCode();
			if(deleteDocument(uri)==false) {
				return 0;
			}
			
			else {
				
				deleteDocument(uri);
				return hashOldDoc;
			}
		}
		
		if(format==null) {
			throw new IllegalArgumentException();
		}
		
		
		DocumentImpl docu;

	if(format==DocumentFormat.BINARY) {
		byte[] binarydoc=input.readAllBytes();
		
		 docu= new DocumentImpl(uri, binarydoc);
	}
	else{
		String txtdoc=new String(input.readAllBytes());
		 docu= new DocumentImpl(uri, txtdoc);
	}
	

		if(this.HashTable.get(uri)==null) {
			this.HashTable.put(uri, docu);
			return 0;
		}
		else {
			return ((DocumentImpl)this.HashTable.put(uri,  docu)).hashCode();
		}

	}

	
	
	
	
	
	
	@Override
	public Document getDocument(URI uri) {
		return (Document) this.HashTable.get(uri);
		
	}

	@Override
	public boolean deleteDocument(URI uri) {
		if(this.HashTable.get(uri)==null) {
			return false;
		}
		else {
			this.HashTable.put(uri, null);
			return true;
		}
		
	
	}
	
	private Document oldDoc(URI uri) {
		return (Document) this.HashTable.get(uri);
	}













}
