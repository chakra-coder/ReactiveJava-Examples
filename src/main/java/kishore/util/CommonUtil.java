package kishore.util;

import org.slf4j.*;

public class CommonUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);
	private static volatile long count;
	
	public static void runPrime(final long longMax) {
		long primeCount, primeMax = 0;
		int index = 0;
		while (index < 30000) {
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
		count += 1;
		System.out.println("Count - "+count);
		LOGGER.info("Count - "+count);
	}

}
