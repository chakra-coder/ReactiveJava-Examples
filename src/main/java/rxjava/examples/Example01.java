package rxjava.examples;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class Example01 {

	public static void main(String[] args) {
		final Observable<Integer> source = Observable.create(s -> {
			s.onNext(10);
			s.onNext(100);
			s.onNext(10000);
			s.onComplete();
		});
		
		source.map(x -> x + 1).subscribe(System.out::println);
		
		Single<Integer> single = Single.just(10).map(x -> x + 10).map(x -> x * x);
		single.subscribe(System.out::println);
		
		Single<Integer> s1 = Single.just(10);
		
		new Fun<>(s1).apply(x -> x + 10).apply(x -> x * x).getSingle().subscribe(System.out::println);
	}
	
	static class Fun<T> {
		
		private final Single<T> single;

		public Fun(final Single<T> single) {		
			this.single = single;
		}
		
		public <R> Fun<R> apply(final Function<T, R> fun){
			return new Fun<>(single.map(fun));
		}

		public Single<T> getSingle() {
			return single;
		}		
	}
}
