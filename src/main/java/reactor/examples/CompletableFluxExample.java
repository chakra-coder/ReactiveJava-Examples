package reactor.examples;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Skanda on 4/2/2017.
 */
public class CompletableFluxExample {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);

        final Flux<Integer> flux = Flux.range(1, 10000000);

        flux.windowTimeout(10, Duration.ofSeconds(10))
                .doOnNext(x -> System.out.println("For Item - : " + x))
                .flatMapSequential(x -> x.publishOn(Schedulers.fromExecutor(executorService))
                                .map(Long::valueOf)
                                .map(e -> e + 10)
                                .map(e -> e / 2)
                                .map(e -> Math.sqrt(Double.valueOf(e)))
                        , Runtime.getRuntime().availableProcessors() + 1)
                .doOnComplete(() -> {
                    long end = System.currentTimeMillis();
                    executorService.shutdown();
                    System.out.println("Time Taken - " + (end - start));
                })
                .subscribe(res -> {
                    System.out.println("Thread Id - " + Thread.currentThread().getId());
                    System.out.println("Result Value - " + res);
                });

    }
}
