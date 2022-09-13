package edu.yu.cs.com1320.project.stage2.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;

import java.nio.file.Path;


public class DocumentImpl implements edu.yu.cs.com1320.project.stage2.Document {
	
private String text;
private byte[] binaryData;
private URI uri;



public DocumentImpl(URI uri, String txt){
	
	if(txt==null || txt.equals("")|| uri==null) {
		throw new IllegalArgumentException();
	}
	
	this.uri=uri;
	this.text=txt;
	
	this.binaryData=null;
}

public DocumentImpl(URI uri, byte[] binaryData) {
	if(binaryData.length==0 ||uri==null) {
		throw new IllegalArgumentException();
	}
	
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
	
	 


}
