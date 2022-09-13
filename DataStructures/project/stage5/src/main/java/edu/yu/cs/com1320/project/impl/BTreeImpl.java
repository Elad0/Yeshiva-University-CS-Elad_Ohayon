package edu.yu.cs.com1320.project.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.yu.cs.com1320.project.BTree;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage5.impl.DocumentPersistenceManager;



public class BTreeImpl<Key extends Comparable<Key>, Value> implements edu.yu.cs.com1320.project.BTree<Key, Value>{
	private int n; //number of key-value pairs in the B-tree
	private Node root;
	private int height;
	private Node leftMostExternalNode;
	private final static int MAX=4;
	private DocumentPersistenceManager gary;
	
	   class Entry<Key,Value>{
	    private Key key;
	    private Value val;
	    private Node child;

	    private Entry(Key key, Value val, Node child){
	        this.key = key;
	        this.val = val;
	        this.child = child;
	    }
	}
	  class Node{
			
		   private int entryCount; // number of entries
	       private Entry<?,?>[] entries= new Entry[BTreeImpl.MAX];
	       private Node next;
	       private Node previous;

		
		private Node(int k){
			 this.entryCount=k;
			 this.next=null;
		}



		private Object getNext() {
			return this.next;
		}

		private void setPrevious(Node previous) {
        
            this.previous = previous;
        }



		private void setNext(Node next) {
			this.next=next;
		}
		
		
		}

	  
	public BTreeImpl() {
		 this.root = new Node(0);
		this.leftMostExternalNode=this.root;
		this.height=0;
		this.gary=null;
	}
	
	
	@Override
    public Value get(Key k){
        if (k == null){
            throw new IllegalArgumentException();
        }
        
        Entry entry = this.get(this.root, k, this.height);
        
        if(entry != null){
        	Value val =(Value)entry.val;
           // return (Value)entry.val;
           if(k instanceof URI && val instanceof Document) {
        	   if(	((Document)val).getDocumentTxt()!= null &&	(((Document)val).getDocumentTxt().equals(((URI)k).toString()+"@#$0"))) {
        		   try {
					Document docu=this.gary.deserialize((URI) k);
					
					docu.setLastUseTime(System.nanoTime());
					
         		  this.gary.delete((URI) k); 
         		  this.put(k, (Value) docu);		//replace the refernce to disk with actual doc now
         		  return (Value) docu;	//TODO or return val 
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
        	   }
           }          
        }
        return val;   
    }
        return null;
	}
	
	private Entry get(Node currentNode, Key key, int height){
	        Entry[] entries = currentNode.entries;

	        //current node is external (i.e. height == 0)
	        if (height == 0) {
	        
	            for (int j = 0; j < currentNode.entryCount; j++) {
	            
	                if(isEqual(key, (Key) entries[j].key)){
	                {
	                    //found desired key. Return its value
	                    return entries[j];
	                }
	            }
	            }
	            //didn't find the key
	            return null;
	        } 
	            else
	        {
	            for (int j = 0; j < currentNode.entryCount; j++)
	            {
	                //if (we are at the last key in this node OR the key we
	                //are looking for is less than the next key, i.e. the
	                //desired key must be in the subtree below the current entry),
	                //then recurse into the current entry�s child
	                if (j + 1 == currentNode.entryCount || less(key, (Key) entries[j + 1].key))
	                {
	                    return this.get(entries[j].child, key, height - 1);
	                }
	            }
	            //didn't find the key
	            return null;
	        }
	    }
	        
    private boolean less(Key k1, Key k2){
    	
        return k1.compareTo(k2) < 0;
    }

    private boolean isEqual(Key k1, Key k2){
        return k1.equals(k2);
    }        

	@Override
	public Value put(Key key, Value val)	//to delete use put(key, null)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("argument key to put() is null");
        }
        //if the key already exists in the b-tree, simply replace the value
        Entry alreadyThere = this.get(this.root, key, this.height);
        if(alreadyThere != null)
        {
        	Value oldVal= (Value) alreadyThere.val;	//Find the old val
        	if(val!=null && oldVal!=null) {
        	if(key instanceof URI && val instanceof Document) {
        		 if(((Document)alreadyThere.val).getDocumentTxt()!= null &&	(((Document)alreadyThere.val).getDocumentTxt().equals(((URI)key).toString()+"@#$0") && ((Document) oldVal).getKey().equals((URI)key))) {
         		   
        	try {
     
				this.gary.delete((URI) key);
				
			} catch (IOException e) {
			
			}
         		   
         	   }
        	}
        	}
            alreadyThere.val = val; //replace the old value with the new
            return oldVal;	//return the value that was removed
        }

        Node newNode = this.put(this.root, key, val, this.height);
        this.n++;
        if (newNode == null)
        {
            return val;
        }

        //split the root:
        //Create a new node to be the root.
        //Set the old root to be new root's first entry.
        //Set the node returned from the call to put to be new root's second entry
        Node newRoot = new Node(2);
        newRoot.entries[0] = new Entry(this.root.entries[0].key, null, this.root);
        newRoot.entries[1] = new Entry(newNode.entries[0].key, null, newNode);
        this.root = newRoot;
        //a split at the root always increases the tree height by 1
        this.height++;
        
        
        
        
		return val;
    }
	
	
	  private Node put(Node currentNode, Key key, Value val, int height)
	    {
	        int j;
	        Entry newEntry = new Entry(key, val, null);

	        //external node
	        if (height == 0)
	        {
	            //find index in currentNode�s entry[] to insert new entry
	            //we look for key < entry.key since we want to leave j
	            //pointing to the slot to insert the new entry, hence we want to find
	            //the first entry in the current node that key is LESS THAN
	            for (j = 0; j < currentNode.entryCount; j++)
	            {
	                if (less(key, (Key) currentNode.entries[j].key))
	                {
	                    break;
	                }
	            }
	        }

	        // internal node
	        else
	        {
	            //find index in node entry array to insert the new entry
	            for (j = 0; j < currentNode.entryCount; j++)
	            {
	                //if (we are at the last key in this node OR the key we
	                //are looking for is less than the next key, i.e. the
	                //desired key must be added to the subtree below the current entry),
	                //then do a recursive call to put on the current entry�s child
	                if ((j + 1 == currentNode.entryCount) || less(key,  (Key) currentNode.entries[j + 1].key))
	                {
	                    //increment j (j++) after the call so that a new entry created by a split
	                    //will be inserted in the next slot
	                    Node newNode = this.put(currentNode.entries[j++].child, key, val, height - 1);
	                    if (newNode == null)
	                    {
	                        return null;
	                    }
	                    //if the call to put returned a node, it means I need to add a new entry to
	                    //the current node
	                    newEntry.key = newNode.entries[0].key;
	                    newEntry.val = null;
	                    newEntry.child = newNode;
	                    break;
	                }
	            }
	        }
	        //shift entries over one place to make room for new entry
	        for (int i = currentNode.entryCount; i > j; i--)
	        {
	            currentNode.entries[i] = currentNode.entries[i - 1];
	        }
	        //add new entry
	        currentNode.entries[j] = newEntry;
	        currentNode.entryCount++;
	        if (currentNode.entryCount < BTreeImpl.MAX)
	        {
	            //no structural changes needed in the tree
	            //so just return null
	            return null;
	        }
	        else
	        {
	            //will have to create new entry in the parent due
	            //to the split, so return the new node, which is
	            //the node for which the new entry will be created
	            return this.split(currentNode, height);
	        }
	    }
	  
	  private Node split(Node currentNode, int height)
	    {
	        Node newNode = new Node(BTreeImpl.MAX / 2);
	        //by changing currentNode.entryCount, we will treat any value
	        //at index higher than the new currentNode.entryCount as if
	        //it doesn't exist
	        currentNode.entryCount = BTreeImpl.MAX  / 2;
	        //copy top half of h into t
	        for (int j = 0; j < BTreeImpl.MAX / 2; j++)
	        {
	            newNode.entries[j] = currentNode.entries[BTreeImpl.MAX  / 2 + j];
	        }
	        //external node
	        if (height == 0)
	        {
	            newNode.setNext((Node) currentNode.getNext());
	            newNode.setPrevious(currentNode);
	            currentNode.setNext(newNode);
	        }
	        return newNode;
	    }

	@Override
	public void moveToDisk(Key k) throws Exception {
		
		Value val=this.get(k);
		if(k instanceof URI && val instanceof Document) {
		this.gary.serialize((URI)k, (Document)val);
		Document t=new DocumentImpl((URI) k, ((URI)k).toString()+"@#$0", null);
		this.put(k, (Value) t);
		}
		
		
	}

	@Override
	public void setPersistenceManager(PersistenceManager<Key, Value> pm) {
		this.gary=(DocumentPersistenceManager) pm;
		
		
	
		
	}


}
