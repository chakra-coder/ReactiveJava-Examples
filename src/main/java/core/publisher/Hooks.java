package core.publisher;

import org.reactivestreams.Publisher;

import java.util.function.Function;

/**
 * Created by Skanda on 2/12/2017.
 */
public abstract class Hooks {


    static volatile OnOperatorCreate<?>  onOperatorCreate;


    public static final class OperatorHook<T> {

    }


    final static class OnOperatorCreate<T> implements Function<Publisher<T>, Publisher<T>>{


        final Function<? super OperatorHook<T>, ? extends OperatorHook<T>> hook;

        OnOperatorCreate(Function<? super OperatorHook<T>, ? extends OperatorHook<T>> hook) {
            this.hook = hook;
        }


        public Publisher<T> apply(Publisher<T> publisher) {
            return null;
        }

    }


}
