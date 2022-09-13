package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage5.Document;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Function;




public class DocumentStoreImpl implements edu.yu.cs.com1320.project.stage5.DocumentStore{

	 
	//after document is changed it needs to be reheapified
	
	private TrieImpl<URI> trie;
	private StackImpl commandStack;
	private MinHeapImpl<DummyDocument> MinHeap;
	private BTreeImpl<URI,Document> BTree;	//incase it doesnt work change to uri document
	private HashMap<URI, Boolean> inDisk;
	
	
	private int documentCountLimit;
	private int documentByteLimit;
	
	private int documentCount;
	private int documentByteCount;
	//test document count
	
	 class  DummyDocument implements Comparable<DummyDocument> {
		private URI dummyUri=null;
		private long dummylastUseTime;
		
		private DummyDocument(URI dummy) {
			this.dummyUri=dummy;
			this.dummylastUseTime=0;
		}
		
		private void setDummyLastUseTime(long lastused) {
			this.dummylastUseTime=lastused;
		}
		
		private URI getDummyURI() {
			return this.dummyUri;
		}
		
		private long getDummyLastUseTime() {
			return this.dummylastUseTime;
		}

		@Override
		public int compareTo(DummyDocument o) {

		
			long num=  this.getDummyLastUseTime()-o.getDummyLastUseTime();
					if(num >0) {
						return 1;
					}
			if(num<0) {
				return -1;
			}
			return 0;
		}
		
		@Override
		public boolean equals(Object o) {
			
			DummyDocument other= (DummyDocument) o;
			if(other.getDummyURI().equals(this.getDummyURI())) {
				return true;
			}
			return false;
			
		}
		}
	
	
	public DocumentStoreImpl() {
		
		this.trie= new TrieImpl<URI>();
		this.commandStack=new StackImpl<Undoable>();
		this.MinHeap=new MinHeapImpl<>();
		this.BTree= new BTreeImpl<>();
		this.inDisk=new HashMap();
	this.BTree.setPersistenceManager(new DocumentPersistenceManager(null));
		
		this.documentByteLimit=50000;
		this.documentCountLimit=50000;
		this.documentCount=0;
		this.documentByteCount=0;
	}
	
public DocumentStoreImpl(File baseDir) {
		
		this.trie= new TrieImpl<URI>();
		this.commandStack=new StackImpl<Undoable>();
		this.MinHeap=new MinHeapImpl<>();
		this.BTree= new BTreeImpl<>();
		this.inDisk=new HashMap();
	this.BTree.setPersistenceManager(new DocumentPersistenceManager(baseDir));
		
		this.documentByteLimit=50000;
		this.documentCountLimit=50000;
		this.documentCount=0;
		this.documentByteCount=0;
	}
	  
//to delete just move a document to the bottom of the heap
	
private boolean inDisk(URI uri) {	//TODO change to private
	if(this.inDisk.get(uri)!=null) {
		return true;
	}
	else {
		return false;
	}
}







	private boolean putDocument(Document docu, URI uri, Document removed) {	//for undoing only

		if(this.BTree.get(uri)==null && docu==null) {
			deleteDocumentFromTrie(uri);
			
			
		}
		
		if(docu==null && uri!=null) {
			deleteDocumentFromTrie(uri);
			
			this.documentByteCount=	this.documentByteCount-getDocumentBytes((DocumentImpl) this.BTree.get(uri));	//get bytes
			this.documentCount--;
		}
		else if(docu!=null){
			DummyDocument dummy= new DummyDocument(docu.getKey());
			
			this.documentCount++;	
			this.documentByteCount=this.documentByteCount+getDocumentBytes((DocumentImpl) this.BTree.get(uri));
			
		
			long time= System.nanoTime();
			docu.setLastUseTime(time);
		dummy.setDummyLastUseTime(time);
		
	
			putDocumentinTrie(docu);
			
			this.MinHeap.insert(dummy);
			this.MinHeap.reHeapify(dummy);

		}
			
		
	this.BTree.put(uri,  docu);	
	
	if(removed!=null ) {
		
	
	if(	docu!=null &&	((DocumentImpl)removed).equals(docu)) {
		this.inDisk.put(removed.getKey(), true);
		try {
			this.BTree.moveToDisk(removed.getKey());
		} catch (Exception e) {
			
		}
		
	}
	else {	
	if(this.inDisk(removed.getKey())) {
		this.inDisk.remove(removed.getKey());
		moveBackFromDisk(removed);
		
		this.documentCount++;	
		this.documentByteCount=this.documentByteCount+getDocumentBytes((DocumentImpl) this.BTree.get(removed.getKey()));
		
	}
	
	}
	
}
		
		
		
		if(this.documentByteCount>this.documentByteLimit || this.documentCount>=this.documentCountLimit) {
			removeMinDocument();
		}
		
		
	
		
			
		
		return true;
	
		
	}
	

	
	
	private boolean moveBackFromDisk(Document docu) {
		this.getDocument(docu.getKey());
		this.inDisk.remove(docu.getKey());
		return true;
	}
	
	
	private Document removeMinDocument(){
		Document docu=(Document) this.BTree.get( this.MinHeap.remove().getDummyURI());
		
		
		this.documentCount--;
		this.documentByteCount=this.documentByteCount-getDocumentBytesnoexception((DocumentImpl) docu);
		if(docu!=null) {
		try {
			this.BTree.moveToDisk(docu.getKey());
			
			this.inDisk.put(docu.getKey(), true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
		removeFromStack(docu);
		
	return docu;	
		
	/*if(docu.getDocumentTxt()==null) {
			//this.BTree.put(docu.getKey(),null);
			
			 try {
				this.BTree.moveToDisk(docu.getKey());
				this.inDisk.put(docu.getKey(), true);
			} catch (Exception e) {
			
				
			}
			
			
			removeFromStack(docu);
		}
		else {
			//deleteDocumentFromTrie(docu.getKey());
			//this.BTree.put(docu.getKey(),null);
			try {
				this.BTree.moveToDisk(docu.getKey());
				this.inDisk.put(docu.getKey(), true);
			} catch (Exception e) {
		
		
			}
			removeFromStack(docu);
		}*/

		
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
	
	private int getDocumentBytesnoexception(DocumentImpl docu) {
		if(docu==null) {
			return 0;
		}
		if(docu.getDocumentBinaryData()==null) {
			
			
			
			return docu.getDocumentTxt().getBytes().length;
		}
		
		else {
			return docu.getDocumentBinaryData().length;
		}
	}
	
	
	
	@Override
	public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {
		if(uri==null) {
			throw new IllegalArgumentException();
		}
		if(input==null) {
			
			if(this.BTree.get(uri)==null) {
			
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
		 docu= new DocumentImpl(uri, txtdoc, null);
		 this.documentByteCount=	this.documentByteCount+docu.getDocumentTxt().getBytes().length;
			
	}
	
	DummyDocument dummy= new DummyDocument(docu.getKey());
	long time=System.nanoTime();
	docu.setLastUseTime(time);
	dummy.setDummyLastUseTime(time);
	this.MinHeap.insert(dummy);
	Document temp=null;
	if(this.documentByteCount>this.documentByteLimit || this.documentCount>=this.documentCountLimit) {
		temp=removeMinDocument();
	}
Document removed=temp;
	this.MinHeap.reHeapify(dummy);
	putDocumentinTrie(docu);
		
	this.documentCount++;
	
	
		if(this.BTree.get(uri)==null) {
			Function<URI, Boolean> f= URI->putDocument(null, uri, removed);			//before it was null so the undo should make the document null	
			GenericCommand c= new GenericCommand(uri, f);
			this.commandStack.push(c);
			
			this.BTree.put(uri, docu);
			
			
			return 0;
		}
	

		else {
			boolean r=false;
			DocumentImpl oldDoc=(DocumentImpl) this.BTree.put(uri, docu);
			if(this.inDisk(oldDoc.getKey())) {
				r=true;
				this.inDisk.remove(oldDoc.getKey());
			}
			
			deleteDocumentFromTrie(oldDoc.getKey());
			putDocumentinTrie(docu);
			Function<URI, Boolean> f= URI->putDocument(oldDoc, uri, removed);		//put in the old documentinstead
		if(r) {
		 f= URI->putDocument(oldDoc, uri, oldDoc);	
		
		}
		
			GenericCommand c= new GenericCommand(uri, f);
			this.commandStack.push(c);
			 time=System.nanoTime();
			docu.setLastUseTime(time);
			dummy.setDummyLastUseTime(time);
			this.MinHeap.insert(dummy);
			return oldDoc.hashCode();
		}

	}

	
	private void putDocumentinTrie(Document docu) {
		
		 DummyDocument dummy= new DummyDocument(docu.getKey());
		 long time=System.nanoTime();
	
		docu.setLastUseTime(time);
		ReHeapifyWithLastTime(docu.getKey() , time);
		
		for(String word: docu.getWords()) {
			this.trie.put(word, docu.getKey());
		}
		
	
	}
	
	
	@Override
	public Document getDocument(URI uri) {				//after new last use time. document is not reheaped properly
		DocumentImpl docu=(DocumentImpl) this.BTree.get(uri);
		if(docu==null) {
			return null;
		}
	 	   
		if(	this.inDisk.get(uri)!=null		) {	//bring from disk
			docu=(DocumentImpl) this.BTree.get(uri);	//get URI again this time actual document
		
			this.inDisk.remove(uri);	//no longer in disk so update HashMap
			
			this.documentCount++;
			this.documentByteCount=	this.documentByteCount+getDocumentBytes((DocumentImpl) this.BTree.get(uri));
			
			if(this.documentByteCount>this.documentByteLimit || this.documentCount>=this.documentCountLimit) {
				removeMinDocument();
			}
		
		
			DummyDocument dummy= new DummyDocument(docu.getKey());
			long time=System.nanoTime();
			docu.setLastUseTime(time);
			dummy.setDummyLastUseTime(time);
			this.MinHeap.insert(dummy);
			this.MinHeap.reHeapify(dummy);
		
			
			return docu;
					
		}
		
		if(docu!=null) { 
			long time=System.nanoTime();
		docu.setLastUseTime(time);
		ReHeapifyWithLastTime(docu.getKey() , time);
		
	
		}
		return (Document) this.BTree.get(uri);
		
	}
	
	
	
	

	@Override
	public boolean deleteDocument(URI uri) {


		
		if(this.BTree.get(uri)==null) {

			Function<URI, Boolean> func= (URI)->false;
		GenericCommand c = new GenericCommand(uri, func);
		this.commandStack.push(c);
		
			return false;
		}
		else {
			this.documentCount--;
			this.documentByteCount=	this.documentByteCount-getDocumentBytes((DocumentImpl) this.BTree.get(uri));
				

		
		
			
			if(((Document) this.BTree.get(uri)).getDocumentTxt()!=null) {
			deleteDocumentFromTrie(uri);
			}
			
		if(this.inDisk(uri)==false) {
			
			deleteDocumentFromHeap(uri);
		}
		if(this.inDisk(uri)) {
			this.inDisk.remove(uri);

		}
			
			DocumentImpl oldDoc=(DocumentImpl) this.BTree.put(uri, null);
		
			
				Function<URI, Boolean> func= (URI)->putDocument(oldDoc, uri, null);
			GenericCommand c = new GenericCommand(uri, func);
			this.commandStack.push(c);
		
	
			return true;
		}
		

	}
	
	private void deleteDocumentFromHeap(URI uri) {
		DocumentImpl toDelete=(DocumentImpl) this.BTree.get(uri);
		//probably here where a new document is deleted
					//could just loop until it equals the right one
		
		DummyDocument dummy= new DummyDocument(uri);
	
		toDelete.setLastUseTime(-100);
		ReHeapifyWithLastTime(uri , -100);
//		dummy.setDummyLastUseTime(-100);
//		this.MinHeap.reHeapify(dummy);
		
		this.MinHeap.remove();
		
	}
	
	private DocumentImpl oldDoc(URI uri) {
		Document docu=(Document) this.BTree.get(uri);
		DummyDocument dummy =new DummyDocument(uri);
		long time=System.nanoTime();
		docu.setLastUseTime(time);
		
//		dummy.setDummyLastUseTime(time);
//		this.MinHeap.reHeapify(dummy);
		ReHeapifyWithLastTime(docu.getKey() , time);	
		return (DocumentImpl) this.BTree.get(uri);
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
		
		
		List<URI> bonkey = Collections.emptyList();
		
		String keywordPrime= keyword.toUpperCase();
		//create a comparator here
		
		
		ArrayList<Document> toReturn = new ArrayList<>();
		
			bonkey=this.trie.getAllSorted(keywordPrime, (URI two, URI one)->((Document) this.BTree.get(one)).wordCount(keywordPrime)-((Document)this.BTree.get(two)).wordCount(keywordPrime));
			
	//	bonkey.sort((Document two, Document one)->one.wordCount(keywordPrime)-two.wordCount(keywordPrime));
	if(!bonkey.isEmpty()) {
		for(URI uri: bonkey) {
		Document docu=this.getDocument(uri);
		long time= System.nanoTime();
			docu.setLastUseTime(time);
			ReHeapifyWithLastTime(docu.getKey() , time);
			toReturn.add(docu);
		}
	}
		return toReturn;
		
	}



	@Override
	public List<Document> searchByPrefix(String keywordPrefix) {
		List<URI> bonkey = Collections.emptyList();
		ArrayList<Document> toReturn = new ArrayList<>();
		String keywordPrefixQ=(("QaQzx") +keywordPrefix).toUpperCase();
		
		
		
			//bonkey=this.trie.getAllWithPrefixSorted(keywordPrefix, (Object two, Object one)->((Document) one).wordCount(keywordPrefixQ)-((Document) two).wordCount(keywordPrefixQ));
			bonkey=this.trie.getAllWithPrefixSorted(keywordPrefix, (URI two, URI one)->((Document) this.BTree.get(one)).wordCount(keywordPrefixQ)-((Document)this.BTree.get(two)).wordCount(keywordPrefixQ));
			
		
			if(!bonkey.isEmpty()) {
				for(URI uri: bonkey) {
				Document docu=this.getDocument(uri);
				long time=System.nanoTime();
				
					docu.setLastUseTime(time);
					ReHeapifyWithLastTime(docu.getKey() , time);
					toReturn.add(docu);
			
				}
			}
				return toReturn;
				
			}



	@Override
	public Set<URI> deleteAll(String keyword) {
		Set<URI> bonkey= new HashSet();
		
		List<URI> larry=this.trie.getAllSorted(keyword, (URI two, URI one)->((Document) this.BTree.get(one)).wordCount(keyword)-((Document)this.BTree.get(two)).wordCount(keyword));
		
		if(larry.size()==0) {
			 return Collections.emptySet();
		 }
		
		CommandSet<URI> command= new CommandSet<>();
		
		 for(URI uri: larry) {
			 Document docu=(Document) this.BTree.get(uri);
			 this.deleteDocumentFromTrie(docu.getKey());	//remove document from trie
			 this.deleteDocument(docu.getKey());			
			
			
			
			 bonkey.add(docu.getKey());	//add document to the set of deleted documents
			 Function<URI, Boolean> f= URI->putDocument( docu, docu.getKey(), null);
			command.addCommand(new GenericCommand<URI>(docu.getKey(), f		))	;	//put the command of undoing the deleting into the commandset
		 }
		 this.commandStack.push(command);
		 
		return bonkey;
	}



	@Override
	public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
		Set<URI> bonkey= new HashSet();
		
		//List<Document> 	larry=this.trie.getAllWithPrefixSorted(keywordPrefix, (Object two, Object one)->((Document) one).wordCount(keywordPrefix)-((Document) two).wordCount(keywordPrefix));
		List<URI> larry=this.trie.getAllWithPrefixSorted(keywordPrefix, (URI two, URI one)->((Document) this.BTree.get(one)).wordCount(keywordPrefix)-((Document)this.BTree.get(two)).wordCount(keywordPrefix));
		
		 if(larry.size()==0) {
			 return Collections.emptySet();
		 } 
	CommandSet<URI> command= new CommandSet<>();
	 Function<URI, Boolean> f;
		 for(URI uri: larry) {
			 Document docu=(Document) this.BTree.get(uri);	
						
			 this.deleteDocumentFromTrie(docu.getKey());	//remove document from trie
			 this.deleteDocument(docu.getKey());	//remove document from Hashtable
			 bonkey.add(docu.getKey());	//add document to the set of deleted documents
			 
			 f= URI->putDocument( docu, docu.getKey(), null);
			command.addCommand(new GenericCommand<URI>(docu.getKey(), f		))	;	//put the command of undoing the deleting into the commandset
		 }
		 
		 this.commandStack.push(command);
		 
		
		return bonkey;
	}
	
	private void deleteDocumentFromTrie(URI uri) {

		for(String h: ((Document) this.BTree.get(uri)).getWords()) {
			
			this.trie.delete(h, uri);
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

	private void ReHeapifyWithLastTime(URI uri, long time) { //only to be used when the document is already in the minheap
		DummyDocument dum = new DummyDocument(uri);
		
MinHeapImpl<DummyDocument> temp= new MinHeapImpl<>();
		
		while(dum!=null) {
			try { dum=this.MinHeap.remove();}
			catch(NoSuchElementException e) {
				break;
			}
			if(dum.getDummyURI().equals(uri)) {
				dum.setDummyLastUseTime(time);
			}
			temp.insert(dum);
			temp.reHeapify(dum);
		}
		
		this.MinHeap=temp;
	}

}
