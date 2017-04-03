package rxjava.examples.job;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Skanda on 4/2/2017.
 */
public class FlowableExample {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);


    public static void main(String[] args) throws Exception {

        //noinspection InfiniteLoopStatement
        while (true) {

            final Flowable<Integer> source = Flowable.create(sub -> {
                for (int i = 0; i < 100; i++) {
                    sub.onNext(i);
                }
                sub.onComplete();
            }, BackpressureStrategy.DROP);

            source.window(10)
                    .doOnNext(x -> System.out.println("For Item - : " + x))
                    .observeOn(Schedulers.from(EXECUTOR_SERVICE))
                    .flatMap(x -> x.subscribeOn(Schedulers.from(EXECUTOR_SERVICE))
                            .map(Long::valueOf)
                            .map(e -> e + 10)
                            .map(e -> e / 2)
                            .map(e -> Math.sqrt(Double.valueOf(e)))
                            .onErrorReturn(error -> 1.0), Runtime.getRuntime().availableProcessors() + 1)
                    .onBackpressureLatest()
                    .subscribe(res -> {
                        System.out.println("Thread Id - " + Thread.currentThread().getId());
                        System.out.println("Result Value - " + res);
                    });

            Thread.sleep(1000);
        }

    }

}
