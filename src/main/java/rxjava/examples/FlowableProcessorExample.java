package rxjava.examples;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.processors.AsyncProcessor;

public class FlowableProcessorExample {
	
	private static final int size = 900;

	public static void main(String[] args) {
		AsyncProcessor<Integer> asyncProcessor = AsyncProcessor.create();
		
		final ExecutorService executorService = Executors.newFixedThreadPool(size / (Runtime.getRuntime().availableProcessors() * 2));
		
		final List<Integer> list = IntStream.range(10, 1000000).mapToObj(Integer::valueOf).collect(Collectors.toList());
		
		

	}

}
