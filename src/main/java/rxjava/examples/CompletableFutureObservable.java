package rxjava.examples;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureObservable {

    private static final int size = 900;

    public static void main(String[] args) throws Exception {

        test3();
        //test2();
        //test1();
    }

    private static void test3() throws Exception {
        long start = System.currentTimeMillis();

        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);

        final Flowable<Integer> flowable = Flowable.range(1, 10000000);

        flowable.window(10)
                .doOnNext(x -> System.out.println("For Item - : " + x))
                .flatMap(x -> x.subscribeOn(Schedulers.from(executorService))
                        .map(Long::valueOf)
                        .map(e -> e + 10)
                        .map(e -> e / 2)
                        .map(e -> Math.sqrt(Double.valueOf(e))), Runtime.getRuntime().availableProcessors() + 1)
                //.onBackpressureBuffer(10)
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

    private static void test2() throws Exception {
        long start = System.currentTimeMillis();

        final IntStream list = IntStream.range(1, 10000000);

        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);

        list.forEach(element -> {
            System.out.println("For Element - " + element);

            CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> element, executorService);

            f1.thenApplyAsync(Long::valueOf, executorService)
                    .thenApplyAsync(x -> x + 10, executorService)
                    .thenApplyAsync(x -> x / 2, executorService)
                    .thenApplyAsync(Math::sqrt, executorService)
                    .whenComplete((res, err) -> {
                        System.out.println(Thread.currentThread().getId());
                        System.out.println("Result Value - " + res);
                    });
        });

        executorService.shutdown();

        long end = System.currentTimeMillis();

        System.out.println("Time Taken - " + (end - start));
    }

    private static void test1() throws Exception {
        long start = System.currentTimeMillis();

        final List<Integer> list = IntStream.range(1, 1000000).boxed().collect(Collectors.toList());

        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);

        for (final Integer element : list) {

            System.out.println("For Element - " + element);

            CompletableFuture<Single<Integer>> f1 = CompletableFuture.supplyAsync(() -> Single.just(element), executorService);

            f1.thenApplyAsync(f -> f.map(Long::valueOf), executorService)
                    .thenApplyAsync(f -> f.map(x -> x + 10), executorService)
                    .thenApplyAsync(f -> f.map(x -> x / 2), executorService)
                    .thenApplyAsync(f -> f.map(Math::sqrt), executorService)
                    .whenComplete((res, err) -> {
                        System.out.println(Thread.currentThread().getId());
                        res.subscribe(resultValue -> System.out.println("Result Value - " + resultValue));
                    });

            Thread.sleep(100);
        }

        executorService.shutdown();

        long end = System.currentTimeMillis();

        System.out.println("Time Taken - " + (end - start));
    }

}
