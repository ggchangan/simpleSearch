package crawler.basic.queue.kafka;

import crawler.basic.queue.common.UnvisitedConsumer;

/**
 * Created by magneto on 17-3-1.
 */
public class UrlConsumer implements UnvisitedConsumer<String> {
    @Override public String getUnvisited() {
        return null;
    }

    @Override public boolean unvisitedEmpty() {
        return false;
    }
}
