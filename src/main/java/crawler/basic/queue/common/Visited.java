package crawler.basic.queue.common;

/**
 * Created by magneto on 17-3-1.
 */
public interface Visited<T> {
    void addVisited(T o);
    boolean visited(T o);
}
