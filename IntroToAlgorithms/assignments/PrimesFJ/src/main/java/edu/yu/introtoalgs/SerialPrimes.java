package edu.yu.introtoalgs;


/** Implements PrimeCalculator using a "naive" serial computation.
 *
 * Students may not change the constructor signature or add any other
 * constructor!
 *
 * @author Avraham Leff
 */

public class SerialPrimes implements PrimeCalculator {

  /** Constructor
   *
   */
  public SerialPrimes() {
    // your code (if any) goes here
  }

  @Override
  public int nPrimesInRange(final long start, final long end) {
	  if(start<=1 || end>Long.MAX_VALUE|| start>end) {
		  throw new IllegalArgumentException();
	  }
	  
long num=start;
int primesInRange=0;

 while(num<=end) {
	 if(isPrime(num)){
		 primesInRange++;
	 }
	 if(primesInRange<0) {
		 throw new IllegalArgumentException();
	 }

	 num++;
 }

    return primesInRange;
  }
  
  public boolean isPrime(long num) {
	  
	  if(num==2) {
		  return true;
	  }

	  long st=2;
	long end=(long) Math.ceil(Math.sqrt(num));
	  while(st<=end){
		  
		  
		  
		  
		  if(num%st==0) {
			  return false;
		  }
		  
		
		  st=st+1;

	  }
	  return true;
  }
  
}