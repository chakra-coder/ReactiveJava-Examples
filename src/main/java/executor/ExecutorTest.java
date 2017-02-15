package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kishore.computation.ThreadPrimeCalculation;

public class ExecutorTest {

	public static void main(String[] args) throws Exception {
		final int poolSize = 100;
		final ExecutorService service = Executors.newFixedThreadPool(poolSize);
		int batch = 10;
		while(true){
			while(batch <= 200){
				batch += 10;
				for(int i = 1; i <= batch; i++){
					service.submit(new ThreadPrimeCalculation(batch));
				}
				Thread.sleep(1000);
			}
			System.out.println("Resetting Batch, Cooling Down !!!");
			batch = 0;
			Thread.sleep(10000);
		}

	}

}
