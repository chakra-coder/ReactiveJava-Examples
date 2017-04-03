package rxjava.examples.job;

import java.util.stream.IntStream;

/**
 * Created by Skanda on 4/2/2017.
 */
public class Input {

    public Iterable<Integer> getIntegerIterable() {
        return () -> IntStream.range(1, 1000).boxed().iterator();
    }

}
