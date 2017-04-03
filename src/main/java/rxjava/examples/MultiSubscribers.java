package rxjava.examples;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Skanda on 3/29/2017.
 */
public class MultiSubscribers {

    public static void main(String[] args) {

        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        final Observable<Integer> observable = Observable.create(subscriber -> {
            System.out.println("Create");
            subscriber.onNext(20);
            subscriber.onNext(30);
            subscriber.onComplete();
        });

        System.out.println("Starting");

        observable.subscribe(x -> {
            System.out.println(x);
            System.out.println("Thread - "+Thread.currentThread().getId());
        });
        System.out.println("Exit");

        System.out.println("Thread Example");

        observable.observeOn(Schedulers.from(executorService)).map(x -> {
            System.out.println("Thead Map - " + Thread.currentThread().getId());
            return x * x;
        }).subscribeOn(Schedulers.from(executorService)).subscribe(x -> {
            System.out.println(x);
            System.out.println("Thread - " + Thread.currentThread().getId());
        });

        /*final Observable<Integer> concurrent = observable.subscribeOn(Schedulers.from(executorService));

        concurrent.observeOn(Schedulers.from(executorService)).map(x -> {
            System.out.println("Thead Map - "+Thread.currentThread().getId());
            return x * x;
        });

        concurrent.subscribe(x -> {
            System.out.println(x);
            System.out.println("Thread - "+Thread.currentThread().getId());
        });

        concurrent.subscribe(x -> {
            System.out.println(x);
            System.out.println("Thread - "+Thread.currentThread().getId());
        });*/
    }
}
