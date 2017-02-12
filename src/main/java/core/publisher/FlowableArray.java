package core.publisher;

import core.Fuseable;
import core.MultiReceiver;
import core.Operators;
import core.Producer;
import core.Trackable;

import org.reactivestreams.Subscriber;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * Created by Skanda on 2/12/2017.
 */
final public class FlowableArray<T> extends Flowable<T> implements Fuseable{

    final T[] array;

    @SafeVarargs
    public FlowableArray(T... array) {
        this.array = Objects.requireNonNull(array, "Array is empty");
    }


    public void subscribe(Subscriber<? super T> subscriber) {
    	if(array.length == 0){
    		Operators.complete(subscriber);
    		return;
    	}
    }
    
    static final class ArraySubscriber<T> implements Producer, Trackable, MultiReceiver, SynchronousSubscription<T>{
    	
    	
    	final Subscriber<? super T> actual;

		final T[] array;

		int index;

		volatile boolean cancelled;

		volatile long requested;
		
		@SuppressWarnings("rawtypes")
		static final AtomicLongFieldUpdater<ArraySubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ArraySubscriber.class, "requested");

		public ArraySubscriber(Subscriber<? super T> actual, T[] array) {
			this.actual = actual;
			this.array = array;
		}

		@Override
		public T poll() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void request(long n) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator<?> upstreams() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object downstream() {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }

}
