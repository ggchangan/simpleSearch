package crawler.basic.queue.common;

/**
 * Created by magneto on 17-3-1.
 */
public interface Unvisited<T> extends UnvisitedProducer<T>, UnvisitedConsumer<T>{
    void addUnvisited(T o);
    T getUnvisited();
    boolean unvisitedEmpty();
}
