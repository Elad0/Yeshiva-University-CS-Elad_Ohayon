package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage4.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Function;




public class DocumentStoreImpl implements edu.yu.cs.com1320.project.stage4.DocumentStore{

	 
	//after document is changed it needs to be reheapified
	
	private TrieImpl trie;
	private HashTableImpl HashTable;
	private StackImpl commandStack;
	private MinHeapImpl<Document> MinHeap;
	
	private int documentCountLimit;
	private int documentByteLimit;
	
	private int documentCount;
	private int documentByteCount;
	//test document count
	
	public DocumentStoreImpl() {
		this.HashTable=new HashTableImpl();
		this.trie= new TrieImpl<Document>();
		this.commandStack=new StackImpl<Undoable>();
		this.MinHeap=new MinHeapImpl<>();
		
		this.documentByteLimit=50000;
		this.documentCountLimit=50000;
		this.documentCount=0;
		this.documentByteCount=0;
	}
	  
//to delete just move a document to the bottom of the heap
	
	private boolean putDocument(Document docu, URI uri) {	//for undoing only
		
		if(this.HashTable.get(uri)==null && docu==null) {
			deleteDocumentFromTrie(uri);
			
			
		}
		
		if(docu==null && uri!=null) {
			deleteDocumentFromTrie(uri);
			
			this.documentByteCount=	this.documentByteCount-getDocumentBytes((DocumentImpl) this.HashTable.get(uri));	//get bytes
			this.documentCount--;
		}
		else if(docu!=null){
			this.MinHeap.insert(docu);
			this.documentCount++;	
			this.documentByteCount=this.documentByteCount+getDocumentBytes((DocumentImpl) this.HashTable.get(uri));
			docu.setLastUseTime(System.nanoTime());
			this.MinHeap.reHeapify(docu);
			putDocumentinTrie(docu);
		}
		
		if(this.documentByteCount>this.documentByteLimit || this.documentCount>=this.documentCountLimit) {
			removeMinDocument();
		}
		
		
		this.HashTable.put(uri,  docu);
		
			
		
		return true;
	
		
	}
	
	
	private void removeMinDocument(){
		Document docu=(Document) this.MinHeap.remove();
		this.documentCount--;
		this.documentByteCount=this.documentByteCount-getDocumentBytes((DocumentImpl) docu);
		if(docu.getDocumentTxt()==null) {
			this.HashTable.put(docu.getKey(),null);
			removeFromStack(docu);
		}
		else {
			deleteDocumentFromTrie(docu.getKey());
			this.HashTable.put(docu.getKey(),null);
			removeFromStack(docu);
		}

		
	}
	
	private void removeFromStack(Document docu) {
		
		StackImpl temp=new StackImpl<Undoable>();
		
		
		while(this.commandStack.size()>0) {
			
			if(this.commandStack.peek() instanceof GenericCommand) {
		
				if(((GenericCommand) this.commandStack.peek()).getTarget().equals(docu.getKey())) {
					this.commandStack.pop();
				}
				else {
					temp.push(commandStack.pop());
				}
			}
		
			if(this.commandStack.peek() instanceof CommandSet) {
				CommandSet<URI> command= new CommandSet<>();
				
				if(((CommandSet) this.commandStack.peek()).containsTarget(docu.getKey())) {	//infinite loop here
				
						Iterator bonk=((CommandSet) this.commandStack.peek()).iterator();
						
						while(bonk.hasNext()) {
							GenericCommand g= (GenericCommand) bonk.next();
							if(!g.getTarget().equals(docu.getKey())) {
								command.addCommand(g);
							}
						}
					if(command.size()>0) {
					temp.push(command);
					commandStack.pop();
					}
				}
				else {
					temp.push(commandStack.pop());
				}
			}
			
		}
		while(temp.size()>0) {
			this.commandStack.push(temp.pop());
		}
}
	
	
	
	
	
	private int getDocumentBytes(DocumentImpl docu) {
		if(docu==null) {
			return 0;
		}
		if(docu.getDocumentBinaryData()==null) {
			
			if(docu.getDocumentTxt().getBytes().length>this.documentByteLimit) {
				throw new IllegalArgumentException();
			}
			
			return docu.getDocumentTxt().getBytes().length;
		}
		
		else {
			if(docu.getDocumentBinaryData().length>this.documentByteLimit) {
				throw new IllegalArgumentException();
			}
			return docu.getDocumentBinaryData().length;
		}
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
		 this.documentByteCount=	this.documentByteCount+docu.getDocumentBinaryData().length;
	}
	else{
		String txtdoc=new String(input.readAllBytes());
		 docu= new DocumentImpl(uri, txtdoc);
		 this.documentByteCount=	this.documentByteCount+docu.getDocumentTxt().getBytes().length;
			
	}
	docu.setLastUseTime(System.nanoTime());
	this.MinHeap.insert(docu);
	
	
	if(this.documentByteCount>this.documentByteLimit || this.documentCount>=this.documentCountLimit) {
		removeMinDocument();
	}
	putDocumentinTrie(docu);
		
	this.documentCount++;
	
		if(this.HashTable.get(uri)==null) {
			Function<URI, Boolean> f= URI->putDocument(null, uri);			//before it was null so the undo should make the document null	
			GenericCommand c= new GenericCommand(uri, f);
			this.commandStack.push(c);
			
			this.HashTable.put(uri, docu);
			
			this.MinHeap.reHeapify(docu);
			return 0;
		}
		
		else {
			DocumentImpl oldDoc=(DocumentImpl) this.HashTable.put(uri, docu);
			
			deleteDocumentFromTrie(oldDoc.getKey());
			putDocumentinTrie(docu);
			Function<URI, Boolean> f= URI->putDocument(oldDoc, uri);		//put in the old documentinstead
					
			GenericCommand c= new GenericCommand(uri, f);
			this.commandStack.push(c);
			docu.setLastUseTime(System.nanoTime());
			this.MinHeap.reHeapify(docu);
			return oldDoc.hashCode();
		}

	}

	
	private void putDocumentinTrie(Document docu) {
		docu.setLastUseTime(System.nanoTime());
		this.MinHeap.reHeapify(docu);
		for(String word: docu.getWords()) {
			this.trie.put(word, docu);
		}
		
	
	}
	
	
	@Override
	public Document getDocument(URI uri) {				//after new last use time. document is not reheaped properly
		DocumentImpl docu=(DocumentImpl) this.HashTable.get(uri);
		
		
		if(docu!=null) {
	docu.setLastUseTime(System.nanoTime());
		this.MinHeap.reHeapify(docu);
		}
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
			this.documentCount--;
			this.documentByteCount=	this.documentByteCount-getDocumentBytes((DocumentImpl) this.HashTable.get(uri));
				

		
		
			
			if(((Document) this.HashTable.get(uri)).getDocumentTxt()!=null) {
			deleteDocumentFromTrie(uri);
			}
			
			deleteDocumentFromHeap(uri);
			
			
			
			DocumentImpl oldDoc=(DocumentImpl) this.HashTable.put(uri, null);
			
			
				Function<URI, Boolean> func= (URI)->putDocument(oldDoc, uri);
			GenericCommand c = new GenericCommand(uri, func);
			this.commandStack.push(c);
			
			Document docu=(Document) this.HashTable.get(uri);
	
			return true;
		}
		

	}
	
	private void deleteDocumentFromHeap(URI uri) {
		DocumentImpl toDelete=(DocumentImpl) this.HashTable.get(uri);
		toDelete.setLastUseTime(-100);
		this.MinHeap.reHeapify(toDelete);
		this.MinHeap.remove();
	}
	
	private DocumentImpl oldDoc(URI uri) {
		Document docu=(Document) this.HashTable.get(uri);
		docu.setLastUseTime(System.nanoTime());
		this.MinHeap.reHeapify(docu);
		return (DocumentImpl) this.HashTable.get(uri);
	}
	
	@Override
	public void undo()  throws IllegalStateException{
		
		if(this.commandStack.size()==0) {
			throw new IllegalStateException();
		}
		
		if(this.commandStack.peek() instanceof CommandSet) {
		
			int j=((CommandSet) this.commandStack.peek()).size();
			((CommandSet) this.commandStack.pop()).undoAll();
			
			int k=0;
			while(k<j) {
				this.commandStack.pop();
				k++;
			}
			
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
	
		if(this.commandStack.size()==0) {
			throw new IllegalStateException();
		}
		if((((GenericCommand)this.commandStack.peek()).getTarget().equals(uri))==false) {
		//	System.out.println("hello");
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
			while(tempStack.size()!=0) {
				if(tempStack.peek() instanceof CommandSet) {
					
					if(((CommandSet)tempStack.peek()).size()==0) {
						
						tempStack.pop();
					}
				}
				this.commandStack.push(tempStack.pop());
				
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
	if(!bonkey.isEmpty()) {
		for(Document docu: bonkey) {
		
			docu.setLastUseTime(System.nanoTime());
			this.MinHeap.reHeapify(docu);
			
		}
	}
		return bonkey;
		
	}



	@Override
	public List<Document> searchByPrefix(String keywordPrefix) {
		List<Document> bonkey = Collections.emptyList();
		
		String keywordPrefixQ=(("QaQzx") +keywordPrefix).toUpperCase();
		
		
		
			bonkey=this.trie.getAllWithPrefixSorted(keywordPrefix, (Object two, Object one)->((Document) one).wordCount(keywordPrefixQ)-((Document) two).wordCount(keywordPrefixQ));
			
			if(!bonkey.isEmpty()) {
				for(Document docu: bonkey) {
					//docu.setLastUseTime(System.nanoTime());
					((Document) this.HashTable.get(docu.getKey())).setLastUseTime(System.nanoTime());
					this.MinHeap.reHeapify(docu);
				}
			}
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
			 this.deleteDocument(docu.getKey());			
			
			
			
			 bonkey.add(docu.getKey());	//add document to the set of deleted documents
			 Function<URI, Boolean> f= URI->putDocument( docu, docu.getKey());
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
			 
			 f= URI->putDocument( docu, docu.getKey());
			command.addCommand(new GenericCommand<URI>(docu.getKey(), f		))	;	//put the command of undoing the deleting into the commandset
		 }
		 
		 this.commandStack.push(command);
		 
		
		return bonkey;
	}
	
	private void deleteDocumentFromTrie(URI uri) {

		for(String h: ((Document) this.HashTable.get(uri)).getWords()) {
			
			this.trie.delete(h, ((Document) this.HashTable.get(uri)));
		}
		

	
	}

	@Override
	public void setMaxDocumentCount(int limit) {
		this.documentCountLimit=limit;
		while(this.documentCount>this.documentCountLimit) {
			removeMinDocument();
		
	}
	}

	@Override
	public void setMaxDocumentBytes(int limit) {
		this.documentByteLimit=limit;
		while(this.documentByteCount>this.documentByteLimit) {
			removeMinDocument();
		
	}
	}


}
