package rxjava.examples;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;

public class Example02 {

	public static void main(String[] args) {
		Observable<Integer> source1 = Observable.just(10, 20, 30);
		Observable<Integer> source2 = Observable.fromIterable(IntStream.range(100, 120)
				.mapToObj(x -> Integer.valueOf(x)).collect(Collectors.toList()));
		
		
		Observable<Integer> finalSource = Observable.merge(source1, source2);
		
		finalSource.map(x -> x / 2).subscribe(System.out::println);
		
		source1 = source1.map(x -> x / 2);
		source2 = source2.map(x -> x / 2);
		
		finalSource = Observable.merge(source1, source2);
		
		System.out.println("Test 2 ---------");
		
		finalSource.subscribe(System.out::println);
		
	}

}
