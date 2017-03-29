package reactor.examples;

import reactor.core.publisher.BlockingSink;
import reactor.core.publisher.BlockingSink.Emission;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Skanda on 2/11/2017.
 */
public class EmitterProcessorExample {

	public static void main(String[] args) throws Throwable {
		final ExecutorService executorService = Executors.newFixedThreadPool(10);
		EmitterProcessor<Integer> emitter = EmitterProcessor.create();
		final BlockingSink<Integer> blockingSink = emitter.connectSink();
		for (int k = 0; k < 100; k++) {
			emitter.publishOn(Schedulers.fromExecutor(executorService)).subscribe(System.out::println);
			for (int i = 1; i <= 10; i++) {
				while (!blockingSink.emit(i).isOk()) {
					if (blockingSink.hasFailed()) {
						throw blockingSink.getError();
					}
				}
			}
		}
		Emission finish = blockingSink.finish();
		if (finish.isOk() || finish.isFailed()) {
			executorService.shutdown();
		}
	}
}
