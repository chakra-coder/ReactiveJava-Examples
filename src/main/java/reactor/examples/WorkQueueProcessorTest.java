package reactor.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import reactor.core.publisher.BlockingSink;
import reactor.core.publisher.WorkQueueProcessor;

/**
 * Created by Skanda on 2/12/2017.
 * 
 * WorkQueueProcessor with multiple Subscribers
 */
public class WorkQueueProcessorTest {

	public static void main(String[] args) throws Throwable {
		final ExecutorService executorService = Executors.newFixedThreadPool(32);
		WorkQueueProcessor<Integer> emitter = WorkQueueProcessor.create(executorService);
		emitter.log();
		final BlockingSink<Integer> blockingSink = emitter.connectSink();
		for (int k = 0; k < 10; k++) {
			System.out.println("K = " + k);
			emitter.subscribe(v -> System.out.println("Subscriber - 1 - "));
			emitter.subscribe(v -> System.out.println("Subscriber - 2 - " + v));
			emitter.subscribe(v -> System.out.println("Subscriber - 3 - " + v));
			for (int i = k; i < k + 10000; i++) {
				while (!blockingSink.emit(i).isOk()) {
					if (blockingSink.hasFailed()) {
						throw blockingSink.getError();
					}
				}
			}
		}
		blockingSink.finish();
	}

}
