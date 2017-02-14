package kishore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import reactor.core.publisher.BlockingSink;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.publisher.WorkQueueProcessor;

/**
 * Created by Skanda on 2/12/2017.
 */
public class Example2 {

	
	final Consumer<Integer> consumer = new Consumer<Integer>() {

		@Override
		public void accept(Integer t) {
			System.out.println(t);
			
		}
	};


    public static void main(String[] args) throws Throwable {
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 20).subscribe(System.out::println);
        Mono<Long> count = Flux.just(1, 2, 3, 4, 5).count();
        MonoProcessor<Long> monoProcessor = count.subscribe();
        System.out.println(monoProcessor.block());
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        WorkQueueProcessor<Integer> emitter = WorkQueueProcessor.create(executorService);
        emitter.log();
        final BlockingSink<Integer> blockingSink = emitter.connectSink();
        for(int k = 0; k < 10; k++) {
            System.out.println("K = "+k);
            emitter.subscribe( v -> System.out.println("Subscriber - 1 - "));
            emitter.subscribe( v -> System.out.println("Subscriber - 2 - "+v));
            emitter.subscribe( v -> System.out.println("Subscriber - 3 - "+v));
            for (int i = k; i < k+10000; i++) {
                while (!blockingSink.emit(i).isOk()) {
                    if (blockingSink.hasFailed()) {
                        throw blockingSink.getError();
                    }
                }
            }
        }
        final BlockingSink.Emission finish = blockingSink.finish();
        /*if(finish.isOk() || finish.isBackpressured() || finish.isFailed()){
            executorService.shutdown();
        }*/



    }

}
