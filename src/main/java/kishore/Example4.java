package kishore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import kishore.computation.PrimeCalculator;
import reactor.core.Disposable;
import reactor.core.publisher.WorkQueueProcessor;

public class Example4 {
	
	private List<Disposable> disposableList = new ArrayList<>();
	private int size = disposableList.size();
	private int previousBatchSize = 0;
	
	private static final Consumer<PrimeCalculator> primeConsumer = new Consumer<PrimeCalculator>() {

		@Override
		public void accept(PrimeCalculator t) {
			t.run();
		}
		
	};
	
	public static void main(String[] args) throws Exception {
		Example4 e4 = new Example4();
		final int poolSize = 25;
		final ExecutorService service = Executors.newFixedThreadPool(poolSize);
		final WorkQueueProcessor<PrimeCalculator> processor = WorkQueueProcessor.create(service);
		int batch = 10;
		while(true){
			while(batch <= 100){
				e4.updateSubscribers(processor, batch, poolSize, processor.downstreamCount());
				System.out.println("N - Subscribers - "+processor.downstreamCount());
				batch += 10;
				for(int i = 10; i < 50; i++){
					processor.onNext(new PrimeCalculator(i, batch));
				}
				Thread.sleep(1000);
			}
			System.out.println("Resetting Batch, Cooling Down !!!");
			batch = 0;
			Thread.sleep(10000);
		}
	}
	
	private void updateSubscribers(final WorkQueueProcessor<PrimeCalculator> processor, final int batch, final int poolSize, final long nSubscribers) throws Exception{
		System.out.println("\n\n");
		System.out.println("N - Subscribers - "+nSubscribers);
		if(nSubscribers == 0){
			int threshold = (poolSize / batch) + 1;
			addSubscribers(processor, threshold);
			this.previousBatchSize = batch;
		}else{
			if(nSubscribers >= poolSize || batch < nSubscribers){
				System.out.println("Deleting PoolSize");
				int remove = Math.abs((int)nSubscribers - batch);
				remove = remove + remove;
				if(remove > this.size){
					System.out.println("Setting Remove Size as Size - "+this.size);
					remove = this.size;
				}
				final List<Disposable> subscriberList = disposableList.subList(0, remove-1);
				for(final Disposable disposable : disposableList){
					disposable.dispose();
				}
				this.disposableList = subscriberList;
				this.size = disposableList.size();
				System.out.println("Giving Think Time to process tasks in Unsubscribed Processors");
				Thread.sleep(1000);
			}else{
				if(batch > previousBatchSize){
					int threshold = batch / 10;
					threshold = threshold < 0 ? 1 : threshold;
					addSubscribers(processor, (int)(threshold - nSubscribers));
				}
				this.previousBatchSize = batch;
			}
		}
	}
	
	private void addSubscribers(final WorkQueueProcessor<PrimeCalculator> processor, int threshold){
		threshold = threshold > 0 ? threshold : 1;
		for(int i = 1; i <= threshold; i++){
			Disposable subscribe = processor.subscribe(primeConsumer);
			disposableList.add(subscribe);
		}
		this.size = disposableList.size();
	}

}
