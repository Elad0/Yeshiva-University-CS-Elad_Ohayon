package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage3.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.function.Function;




public class DocumentStoreImpl implements edu.yu.cs.com1320.project.stage3.DocumentStore{

	 
	
	
	private TrieImpl trie;
	private HashTableImpl HashTable;
	private StackImpl commandStack;
	
	
	public DocumentStoreImpl() {
		this.HashTable=new HashTableImpl();
		this.trie= new TrieImpl<Document>();
		this.commandStack=new StackImpl<Undoable>();
	}
	  
	
	
	private boolean putDocument(Document docu, URI uri) {	//for undoing only
		if(this.HashTable.get(uri)==null && docu==null) {
			deleteDocumentFromTrie(uri);
			
		}
		else if(docu!=null){
			putDocumentinTrie(docu);
		}
		
		if(docu==null && uri!=null) {
			deleteDocumentFromTrie(uri);
		}
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
			putDocumentinTrie(docu);
	}
	

	
		if(this.HashTable.get(uri)==null) {

			Function<URI, Boolean> f= URI->putDocument(null, uri);			//before it was null so the undo should make the document null	
			GenericCommand c= new GenericCommand(uri, f);
			this.commandStack.push(c);
			
			this.HashTable.put(uri, docu);
			return 0;
		}
		
		else {
			DocumentImpl oldDoc=(DocumentImpl) this.HashTable.put(uri, docu);
			deleteDocumentFromTrie(oldDoc.getKey());
			putDocumentinTrie(docu);
			Function<URI, Boolean> f= URI->putDocument(oldDoc, uri);		//put in the old documentinstead
					
			GenericCommand c= new GenericCommand(uri, f);
			this.commandStack.push(c);
			
			return oldDoc.hashCode();
		}

	}

	
	private void putDocumentinTrie(Document docu) {
	
		for(String word: docu.getWords()) {
			this.trie.put(word, docu);
		}
		
	
	}
	
	
	@Override
	public Document getDocument(URI uri) {
		return (Document) this.HashTable.get(uri);
		
	}

	@Override
	public boolean deleteDocument(URI uri) {
		
		
		if(this.HashTable.get(uri)==null) {

			Function<URI, Boolean> func= (URI)->false;
		GenericCommand c = new GenericCommand(uri, func);
		this.commandStack.push(c);
		
			return false;
		}
		else {
			if(getDocument(uri).getDocumentTxt()!=null) {
			deleteDocumentFromTrie(uri);
			}
			DocumentImpl oldDoc=(DocumentImpl) this.HashTable.put(uri, null);
			
			
				Function<URI, Boolean> func= (URI)->putDocument(oldDoc, uri);
			GenericCommand c = new GenericCommand(uri, func);
			this.commandStack.push(c);
			
			
			return true;
		}
		

	}
	
	private DocumentImpl oldDoc(URI uri) {
		return (DocumentImpl) this.HashTable.get(uri);
	}
	
	@Override
	public void undo()  throws IllegalStateException{
		
		if(this.commandStack.size()==0) {
			throw new IllegalStateException();
		}
		
		if(this.commandStack.peek() instanceof CommandSet) {
		
			((CommandSet) this.commandStack.pop()).undoAll();
			
		}
		
		else {
			
			
			 ((Undoable) this.commandStack.pop()).undo();
		}
		
	
	}

	
	private void errors1(URI uri) {

		if(uri==null) {
			throw new IllegalArgumentException();
		}
		if(this.commandStack.size()==0) {
			throw new IllegalStateException();
		}
	}
	
	@Override 
	public void undo(URI uri) throws IllegalStateException { 
		StackImpl tempStack= new StackImpl<Undoable>();
		
		errors1(uri);
		
	while(this.commandStack.size()!=0) {
		
		
		if(this.commandStack.peek() instanceof CommandSet) {
			if(((CommandSet)this.commandStack.peek()).containsTarget(uri)) {
				break;}}
		
		else if(this.commandStack.peek() instanceof GenericCommand) {
			
			if(((GenericCommand)this.commandStack.peek()).getTarget().equals(uri)) {
			
				break;}}
		tempStack.push(this.commandStack.pop());
			
		}
	
	boolean type=false;
	if(this.commandStack.peek() instanceof CommandSet) {
		type=true;
		if((((CommandSet)this.commandStack.peek()).containsTarget(uri))==false) {
			throw new IllegalStateException();
		}
	}
	
	else {
	
		
		if((((GenericCommand)this.commandStack.peek()).getTarget().equals(uri))==false) {
			throw new IllegalStateException();
		}
	}
	
		if(type==false) {
			((GenericCommand) this.commandStack.pop()).undo();
		}
	
		else {
					Iterator iter=((CommandSet)this.commandStack.peek()).iterator();
			while(iter.hasNext()) {
				Object o=iter.next();	
				if(	((GenericCommand)o).getTarget().equals(uri)) {
					((GenericCommand)o).undo();
				}
				}
		}
		
		
		if(tempStack.size()>0) {			//Put everything back into the commandstack
			while(tempStack.size()>0) {
				if(tempStack.peek() instanceof CommandSet) {
					if(((CommandSet)tempStack.peek()).size()==0) {
						tempStack.pop();
					}
				}
			else {
				this.commandStack.push(tempStack.pop());
			}
			}
		}
		
	}


	@Override
	public List<Document> search(String keyword) {
		
		
		List<Document> bonkey = Collections.emptyList();
		
		String keywordPrime= keyword.toUpperCase();
		//create a comparator here
		
		
		
		
			bonkey=this.trie.getAllSorted(keywordPrime, (Object two, Object one)->((Document) one).wordCount(keywordPrime)-((Document) two).wordCount(keywordPrime));
	//	bonkey.sort((Document two, Document one)->one.wordCount(keywordPrime)-two.wordCount(keywordPrime));
		
		
		return bonkey;
		
	}



	@Override
	public List<Document> searchByPrefix(String keywordPrefix) {
		List<Document> bonkey = Collections.emptyList();
		
		String keywordPrefixQ=(("QaQzx") +keywordPrefix).toUpperCase();
		
		
		
			bonkey=this.trie.getAllWithPrefixSorted(keywordPrefix, (Object two, Object one)->((Document) one).wordCount(keywordPrefixQ)-((Document) two).wordCount(keywordPrefixQ));
			
			
			return bonkey;
			
	}



	@Override
	public Set<URI> deleteAll(String keyword) {
		Set<URI> bonkey= new HashSet();
		
		List<Document> larry=this.trie.getAllSorted(keyword, (Object two, Object one)->((Document) one).wordCount(keyword)-((Document) two).wordCount(keyword));
		
		if(larry.size()==0) {
			 return Collections.emptySet();
		 }
		
		CommandSet<URI> command= new CommandSet<>();
		
		 for(Document docu: larry) {
			 this.deleteDocumentFromTrie(docu.getKey());	//remove document from trie
			 this.deleteDocument(docu.getKey());	//remove document from Hashtable
			 bonkey.add(docu.getKey());	//add document to the set of deleted documents
			 Function<URI, Boolean> f= URI->putDocument(docu, docu.getKey());
			command.addCommand(new GenericCommand<URI>(docu.getKey(), f		))	;	//put the command of undoing the deleting into the commandset
		 }
		 this.commandStack.push(command);
		 
		return bonkey;
	}



	@Override
	public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
		Set<URI> bonkey= new HashSet();
		
		List<Document> 	larry=this.trie.getAllWithPrefixSorted(keywordPrefix, (Object two, Object one)->((Document) one).wordCount(keywordPrefix)-((Document) two).wordCount(keywordPrefix));
		
		
		 if(larry.size()==0) {
			 return Collections.emptySet();
		 }
	CommandSet<URI> command= new CommandSet<>();
	 Function<URI, Boolean> f;
		 for(Document docu: larry) {
			 this.deleteDocumentFromTrie(docu.getKey());	//remove document from trie
			 this.deleteDocument(docu.getKey());	//remove document from Hashtable
			 bonkey.add(docu.getKey());	//add document to the set of deleted documents
			 
			 f= URI->putDocument(docu, docu.getKey());
			command.addCommand(new GenericCommand<URI>(docu.getKey(), f		))	;	//put the command of undoing the deleting into the commandset
		 }
		 
		 this.commandStack.push(command);
		 
		
		return bonkey;
	}
	
	private void deleteDocumentFromTrie(URI uri) {
	
		for(String h: getDocument(uri).getWords()) {
			
			this.trie.delete(h,  getDocument(uri));
		}
		

	
	}




}
