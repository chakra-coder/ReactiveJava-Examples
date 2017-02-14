package kishore.computation;

public final class PrimeCalculator {
	private final long longMax;
	private final long batch;

	public PrimeCalculator(long max, long batch) {
		longMax = max;
		this.batch = batch;
	}

	public void run() {
		long primeCount, primeMax = 0;
		int index = 0;
		while (index < 1000000) {
			long count = 0;
			long max = 0;
			for (long i = 3; i <= longMax; i++) {
				boolean isPrime = true;
				for (long j = 2; j <= i / 2 && isPrime; j++) {
					isPrime = i % j > 0;
				}
				if (isPrime) {
					count++;
					max = i;
				}
			}
			index += 1;
			primeCount = count;
			primeMax = max;
			//System.out.println("Prime Count - " + primeCount + ", Max - " + primeMax);
		}
	}
}