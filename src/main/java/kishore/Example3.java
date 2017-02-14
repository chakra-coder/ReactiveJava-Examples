package kishore;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class Example3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectableFlux<Integer> connectableFlux = Flux.range(0, 10).publish();
		
		connectableFlux.subscribe(System.out::println);
		
		connectableFlux.subscribe(System.out::println);
		
		connectableFlux.connect();
		
		
		
	}

}
