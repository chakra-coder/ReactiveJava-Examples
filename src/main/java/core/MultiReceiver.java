package core;

import java.util.Iterator;

/**
 * A component that is linked to N upstreams producers.
 */
public interface MultiReceiver {

	/**
	 * Return the connected sources of data.
	 *
	 * @return the connected sources of data
	 */
	Iterator<?> upstreams();

	/**
	 * @return the number of upstreams
	 */
	default long upstreamCount() {
		return -1L;
	}
}
