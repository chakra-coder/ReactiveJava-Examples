package reactor.examples;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ConnectableFluxExample {

	public static void main(String[] args) {
		ConnectableFlux<Integer> connectableFlux = Flux.range(0, 10).publish();

		connectableFlux.subscribe(System.out::println);

		connectableFlux.subscribe(System.out::println);

		connectableFlux.connect();

	}

}
