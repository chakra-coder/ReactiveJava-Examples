package core.publisher;

import org.reactivestreams.Publisher;

/**
 * Created by Skanda on 2/12/2017.
 */
public abstract class Flowable<T> implements Publisher<T> {

    @SafeVarargs
	public static <T> Flowable<T> of(T... values){
        return null;
    }

    public static <T> Flowable<T> fromArray(T[] values){
        if(values.length == 0){

        }

        if(values.length == 1){

        }

        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected static <T> Flowable<T> onAssembly(final Flowable<T> source){
        Hooks.OnOperatorCreate hook = Hooks.onOperatorCreate;
        if(hook == null) {
            return source;
        }
        return (Flowable<T>) hook.apply(source);
    }

}
