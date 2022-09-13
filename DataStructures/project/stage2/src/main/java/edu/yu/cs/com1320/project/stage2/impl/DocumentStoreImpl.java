package edu.yu.cs.com1320.project.stage2.impl;

import edu.yu.cs.com1320.project.Command;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage2.Document;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.function.Function;




public class DocumentStoreImpl implements  edu.yu.cs.com1320.project.stage2.DocumentStore{


	
	
	
	
	private HashTableImpl HashTable;
	private StackImpl commandStack;
	
	private String formats;
	
	public DocumentStoreImpl() {
		this.HashTable=new HashTableImpl();
		this.commandStack=new StackImpl<Command>();
		
	}
	  
	
	
	private boolean putDocument(Document docu, URI uri) {

		this.HashTable.put(uri,  docu);
		
		return true;
	
		
	}
	
	
	
	@Override
	public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
		if(uri==null) {
			throw new IllegalArgumentException();
		}
	
		
		
		if(input==null) {
			
			if(this.HashTable.get(uri)==null) {
				return 0;
			}
			
			else {
				int hashOldDoc= ((DocumentImpl) oldDoc(uri)).hashCode();
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

			Function<URI, Boolean> f= URI->putDocument(null, uri);				
			Command c= new Command(uri, f);
			this.commandStack.push(c);
			
			this.HashTable.put(uri, docu);
			return 0;
		}
		
		else {
			DocumentImpl oldDoc=(DocumentImpl) this.HashTable.put(uri, docu);
			
			Function<URI, Boolean> f= URI->putDocument(oldDoc, uri);
					
			Command c= new Command(uri, f);
			this.commandStack.push(c);
			
			return oldDoc.hashCode();
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
			
			DocumentImpl oldDoc=(DocumentImpl) this.HashTable.put(uri, null);
			
			
				Function<URI, Boolean> func= (URI)->putDocument(oldDoc, uri);
			Command c = new Command(uri, func);
			this.commandStack.push(c);
			
			//this.HashTable.put(uri, null);
			return true;
		}
		

	}
	
	private DocumentImpl oldDoc(URI uri) {
		return (DocumentImpl) this.HashTable.get(uri);
	}
	
	public void undo() {
		if(this.commandStack.size()==0) {
			throw new IllegalStateException();
		}
	((Command) this.commandStack.pop()).undo();
	}
	
	public void undo(URI uri) {
	StackImpl tempStack= new StackImpl<Command>();
	
	
	if(uri==null) {
		throw new IllegalArgumentException();
	}
	if(this.commandStack.size()==0) {
		throw new IllegalStateException();
	}
	
	
	while(this.commandStack.size()>0&&	!(((Command) this.commandStack.peek()).getUri().equals(uri))			) {
		
		tempStack.push(this.commandStack.pop());
	}
	
	((Command) this.commandStack.pop()).undo();
	
	if(tempStack.size()>0) {
		while(tempStack.size()>0) {
			this.commandStack.push(tempStack.pop());
		}
	}
	
	}




}
