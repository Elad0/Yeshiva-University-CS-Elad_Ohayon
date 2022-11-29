package edu.yu.introtoalgs;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.*;
import edu.yu.introtoalgs.SerialPrimes;
/* Implements PrimeCalculator interface with Java's Fork/Join parallelism
 * framework.  The implementation must determine what threshold value to use,
 * but should favor thresholds that produce good results for "end" values of
 * (at least) hundreds of millions).
 *
 * Students may not change the constructor signature or add any other
 * constructor!
 *
 * @author Avraham Leff
 */

public class PrimesFJ implements PrimeCalculator {

class ForkJoinPrimeCount extends RecursiveTask<Integer>{
	protected int nPrimes;
	protected int thres;
	protected long start;
	protected long end;
	private SerialPrimes helper;
	
	ForkJoinPrimeCount(int thres, long start, long end, SerialPrimes help){
		this.thres=thres;
		this.start=start;
		this.end=end;
		this.nPrimes=0;
		this.helper=help;
	}

	@Override
	protected Integer compute() {
		if(this.end-this.start<=this.thres) {
			this.nPrimes=this.helper.nPrimesInRange(start, end);
		//	System.out.println(this.nPrimes+ " start is "+ this.start+ " the end is "+ end);
			return this.nPrimes;
		}
		else {
			ForkJoinPrimeCount left = new ForkJoinPrimeCount(this.thres,this.start, (this.start+this.end)/2, new SerialPrimes());
			ForkJoinPrimeCount right = new ForkJoinPrimeCount(this.thres,(this.start+this.end)/2+1, this.end, new SerialPrimes());
			left.fork();
			final Integer rightAnswer = right.compute() ;
			final Integer leftAnswer = left.join();
			return rightAnswer+leftAnswer;
			
		
	}
		
		
		
}
}

  public PrimesFJ() {
   
  }

  @Override
  public int nPrimesInRange(final long start, final long end) {
	  final int threshold=8000;
  ForkJoinTask<Integer> tsk= new ForkJoinPrimeCount(threshold,start, end, new SerialPrimes());
  int coreCount=Runtime.getRuntime().availableProcessors();
   ForkJoinPool fjPool = new ForkJoinPool(coreCount);
  int numPrimes = fjPool.invoke(tsk);
  fjPool.shutdown();
  return numPrimes;


  }
}   // PrimesFJ