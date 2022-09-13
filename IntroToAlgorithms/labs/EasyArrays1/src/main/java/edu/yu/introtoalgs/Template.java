package edu.yu.introtoalgs;

public class Template extends TemplateBaseClass{

	@Override
	public int removeArrayElement(int[] numbers, int val) {
		String temp[]= new String[numbers.length];
		int retval=0;
		String noVal="";
		for(int n=0, i=0; n<numbers.length; n++) {
			if(numbers[n]!=val) {
				//noVal+=numbers[n];
				temp[i]=""+numbers[n];
				i++;
				retval++;
			}
		}
		for(int i=0; i<temp.length; i++) {
			if(temp[i]!=null) {
				numbers[i]=Integer.parseInt(temp[i]);
			}
			else if(temp[i]==null) {
				numbers[i]=val+3;
			}
		}
		
		return retval;
	}

}
