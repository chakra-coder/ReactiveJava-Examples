package reactor.examples;

import common.util.CommonUtil;

public final class WorkQueuePrimeCalculation {
	
	private final long longMax;

	public WorkQueuePrimeCalculation(long max) {
		longMax = max;
	}
	
	public void run(){
		CommonUtil.runPrime(longMax);
	}

	
}