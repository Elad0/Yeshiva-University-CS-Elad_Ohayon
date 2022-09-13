package edu.yu.cs.com1320.project.stage4.impl;

import java.net.URI;
import java.util.*;

import edu.yu.cs.com1320.project.stage4.Document;


public class DocumentImpl implements edu.yu.cs.com1320.project.stage4.Document {
	
private String text;
private byte[] binaryData;
private URI uri;
private long lastUseTime;

private String cleanedtxt;
private Hashtable<String, Integer> words;

public DocumentImpl(URI uri, String txt){
	
	if(txt==null || txt.equals("")|| uri==null) {
		throw new IllegalArgumentException();
	}
	this.cleanedtxt=null;
	this.uri=uri;
	this.text=txt;
	this.words=new Hashtable<String,Integer>();
	this.binaryData=null;
	this.lastUseTime=0;
	this.getWords();
	this.setWordCount();
	this.addPrefixestoHashTable();
}

public DocumentImpl(URI uri, byte[] binaryData) {
	if(binaryData.length==0 ||uri==null) {
		throw new IllegalArgumentException();
	}
	this.lastUseTime=0;
	this.words=null;
	this.uri=uri;
	this.binaryData=binaryData;
	this.text=null;
}

	@Override
	public String getDocumentTxt() {
		return this.text;
	}

	@Override
	public byte[] getDocumentBinaryData() {
		if(this.binaryData==null) {
			return null;}
		else {
			byte[] clone= new byte[this.binaryData.length];
			
		for( int i=0; i<this.binaryData.length; i++) {
				clone[i]=this.binaryData[i];
			}
			
	
		return clone;
	}
	}

	@Override
	public URI getKey() {
		return this.uri;
	}
	
	 @Override
	public boolean equals(Object obj) {
		 if((obj==null && this!=null)	||(obj!=null && this==null)	) {
			 return false;
		 }
		 DocumentImpl other = (DocumentImpl) obj;
		 return this.hashCode()==other.hashCode();
		 
	}
		@Override
		public int hashCode() {
			int result = uri.hashCode();
			 result = 31 * result + (text != null ? text.hashCode() : 0);
			 result = 31 * result + Arrays.hashCode(binaryData);
			 return result;

		}

		@Override
		public int wordCount(String word) {
			
			if(this.words==null) {
				return 0;
			}
			
			if(this.words.get(word.toUpperCase())==null) {
				
				return 0;
			}
			return this.words.get(word.toUpperCase());
		}

		@Override
		public Set<String> getWords() {
			
			Set<String> words= new HashSet<String>();
			if(this.text==null) {
				return words;
			}
			String[] letters=this.text.split("");
			StringBuilder stripped= new StringBuilder();
			for(String h: letters) {
				if(isAlphaNumericandSpace(h)) {
					stripped.append(h.toUpperCase());
				}	
			}
			
			String cleanedSentence= stripped.toString();
			this.cleanedtxt=cleanedSentence;
			
			StringTokenizer tok= new StringTokenizer(cleanedSentence);
			while(tok.hasMoreTokens()) {
				String bonkey=tok.nextToken();
				words.add(bonkey.toUpperCase());
				
			}
			
			return words;
		}
		
		
		private void setWordCount(){
		int count=0;
		String[] words=this.cleanedtxt.split(" ");
	//	StringTokenizer tok= new StringTokenizer(this.cleanedtxt);
		for(String bonkey: this.getWords()) {
			for(String h: words) {
				if(h.equals(bonkey)) {
					count++;
				}
			}
			
			this.words.put(bonkey.toUpperCase(), count);

			count=0;
		}
		
	}

		private boolean isValidDouble(String str) {
			try {
				Double.parseDouble(str);
				return true;
			}catch(NumberFormatException e) {
				return false;
			}
		}
		
		private boolean isAlphaNumericandSpace(String h) { //checks if a given letter is alphanumeric or a space
			char c=h.toUpperCase().charAt(0);
			if(c==32|| isValidDouble(h)||	(c>=65 && c<=90) ) {
				return true;
			}
			return false;
		}
				
		private void addPrefixestoHashTable(){
		StringBuilder allPrefixes= new StringBuilder();
		String[] words=this.cleanedtxt.split(" ");
		for(int i=0; i<words.length; i++) {
			for(int j=0; j<words[i].length()+1; j++) {
				if(j==0) {
					allPrefixes.append(words[i].substring(0, j));
				}
				else {
					allPrefixes.append(words[i].substring(0, j)+" ");
				}
				
			}
		}
			
		for(String prefix: Arrays.asList(allPrefixes.toString().split(" "))){
			if(this.words.get(("qaqzx"+prefix).toUpperCase())==null) {
				this.words.put(("qaqzx"+prefix).toUpperCase(),1);
			}
			else{
				this.words.put(("qaqzx"+prefix).toUpperCase(), this.words.get(("qaqzx"+prefix).toUpperCase())+1);
			}
		}
		
		
		}

		@Override
		public long getLastUseTime() {
			return this.lastUseTime;
		}

		@Override
		public void setLastUseTime(long timeInNanoseconds) {
			this.lastUseTime=timeInNanoseconds;
			
		}
		
		@Override
		public int compareTo(Document o) {
			//System.out.println(	this.getLastUseTime()			+ " "+o.getLastUseTime()+  "last use time of other document"+ (this.getLastUseTime()-o.getLastUseTime()));
		
			long num=  this.getLastUseTime()-o.getLastUseTime();
					if(num >0) {
						return 1;
					}
			if(num<0) {
				return -1;
			}
			return 0;
		}

		


}