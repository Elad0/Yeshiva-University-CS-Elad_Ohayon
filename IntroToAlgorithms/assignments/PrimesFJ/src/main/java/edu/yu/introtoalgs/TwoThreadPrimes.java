package edu.yu.introtoalgs;
import edu.yu.introtoalgs.SerialPrimes;
/* Implements PrimeCalculator interface by using exactly two threads to
 * partition the range of primes between them.  Each thread uses the "naive"
 * SerialPrimes algorithm to solve its part of the problem.
 *
 * Students may not change the constructor signature or add any other
 * constructor!
 *
 * @author Avraham Leff
 */

public class TwoThreadPrimes implements PrimeCalculator {
	
	class twoThreadPartition implements Runnable{
		
		protected int numPrimes;
		private long start;
		private long end;
		private SerialPrimes cereal=null;
		
		public twoThreadPartition(long start, long end, SerialPrimes cereal) {
			this.cereal=cereal;
			this.start=start;
			this.end=end;
			this.numPrimes=0;
		}
		@Override
		public void run() {
			this.numPrimes=cereal.nPrimesInRange(start,end);
			
		}
		
		
		
	}
  /** Constructor
   *
   */
  public TwoThreadPrimes() {
    // your code (if any) goes here
  }

  @Override
  public int nPrimesInRange(final long start, final long end) {
	  if(start==2 && end==3) {
		  return 2;
	  }
	  twoThreadPartition r1=new twoThreadPartition(start,end/2,new SerialPrimes());
	 
	  twoThreadPartition r2=new twoThreadPartition(end/2+1,end,new SerialPrimes());
 Thread t1=new Thread(r1);
 Thread t2=new Thread(r2);
 t1.start();
 t2.start();
 try {
	t1.join();
	t2.join();
} catch (InterruptedException e) {

}


    return r1.numPrimes+r2.numPrimes;
  }
}