package crawler.basic.queue.common;

/**
 * Created by magneto on 17-3-1.
 */
public interface Unvisited<T> {
    void addUnvisited(T o);
    T getUnvisited();
    boolean unvisitedEmpty();
}
