package kishore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import kishore.computation.WorkQueuePrimeCalculation;
import reactor.core.Disposable;
import reactor.core.publisher.WorkQueueProcessor;

/**
 * 
 * @author kkishore
 * 
 * Dynamically Add/Remove subscribers
 *
 */
public class WorkQueueProcessorDynamicSubscribers {
	
	private List<Disposable> disposableList = new ArrayList<>();
	private int size = disposableList.size();
	private final int weight_value = 2;
	
	private static final Consumer<WorkQueuePrimeCalculation> primeConsumer = new Consumer<WorkQueuePrimeCalculation>() {

		@Override
		public void accept(WorkQueuePrimeCalculation t) {
			t.run();
		}
		
	};
	
	public static void main(String[] args) throws Exception {
		WorkQueueProcessorDynamicSubscribers e4 = new WorkQueueProcessorDynamicSubscribers();
		final int poolSize = 100;
		final ExecutorService service = Executors.newFixedThreadPool(poolSize);
		final WorkQueueProcessor<WorkQueuePrimeCalculation> processor = WorkQueueProcessor.create(service);
		int batch = 10;
		while(true){
			while(batch <= 200){
				e4.updateSubscribers(processor, batch, poolSize, processor.downstreamCount());
				System.out.println("N - Subscribers - "+processor.downstreamCount());
				batch += 10;
				for(int i = 1; i <= batch; i++){
					processor.onNext(new WorkQueuePrimeCalculation(i));
				}
				Thread.sleep(1000);
			}
			System.out.println("Resetting Batch, Cooling Down !!!");
			batch = 0;
			Thread.sleep(10000);
		}
	}
	
	private void updateSubscribers(final WorkQueueProcessor<WorkQueuePrimeCalculation> processor, final int batch, final int poolSize, final long nSubscribers) throws Exception{
		System.out.println("\n\n");
		System.out.println("N - Subscribers - "+nSubscribers);
		if(nSubscribers == 0){
			int threshold = (poolSize / batch) + 1;
			addSubscribers(processor, threshold);
		}else{
			if(nSubscribers >= poolSize || nSubscribers >= (poolSize/2)){
				System.out.println("Deleting PoolSize");
				int remove = Math.abs((int)nSubscribers - poolSize);
				if(remove > this.size){
					System.out.println("Setting Remove Size as Size - "+this.size);
					remove = remove - this.size;
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
				if(nSubscribers < (batch/weight_value)){
					System.out.println("Batch is Higer !!!");
					int threshold = batch / 10;
					if(threshold > poolSize){
						threshold = 0;
					}else{
						threshold = threshold <= 0 ? 1 : threshold;
					}
					addSubscribers(processor, threshold);
				}
			}
		}
	}
	
	private void addSubscribers(final WorkQueueProcessor<WorkQueuePrimeCalculation> processor, int threshold){
		threshold = threshold > 0 ? threshold : 1;
		for(int i = 1; i <= threshold; i++){
			Disposable subscribe = processor.subscribe(primeConsumer);
			disposableList.add(subscribe);
		}
		this.size = disposableList.size();
	}

}
