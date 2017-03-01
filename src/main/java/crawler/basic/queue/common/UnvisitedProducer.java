package crawler.basic.queue.common;

/**
 * Created by magneto on 17-3-1.
 */
public interface UnvisitedProducer<T> {
    void addUnvisited(T o);
}
