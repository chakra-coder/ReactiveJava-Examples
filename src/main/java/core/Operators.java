package core;

import org.reactivestreams.Subscriber;

public abstract class Operators {
	
	public static void complete(final Subscriber<?> subscriber){
		subscriber.onSubscribe(EmptySubscription.INSTANCE);
		subscriber.onComplete();
	}
	
	
	enum EmptySubscription implements Fuseable.QueueSubscription<Object> {
		INSTANCE;

		@Override
		public void cancel() {
			// deliberately no op
		}

		@Override
		public void clear() {
			// deliberately no op
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public Object poll() {
			return null;
		}

		@Override
		public void request(long n) {
			// deliberately no op
		}

		@Override
		public int requestFusion(int requestedMode) {
			return Fuseable.NONE; // can't enable fusion due to complete/error possibility
		}

		@Override
		public int size() {
			return 0;
		}
	}

}
