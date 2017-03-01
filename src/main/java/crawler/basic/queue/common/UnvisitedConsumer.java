package crawler.basic.queue.common;

/**
 * Created by magneto on 17-3-1.
 */
public interface UnvisitedConsumer<T> {
    T getUnvisited();
    boolean unvisitedEmpty();
}
