package crawler.basic.queue.local;

import crawler.basic.queue.common.Unvisited;
import crawler.basic.queue.common.Visited;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Queue;

public class LinkQueue implements Unvisited<String>, Visited<String> {
	private Set visitedUrl = new HashSet();
	private Queue unVisitedUrl = new PriorityQueue();


	public int getVisitedNumber() {
		return visitedUrl.size();
	}

	@Override
	public void addUnvisited(String url) {
		if (url != null && !url.trim().equals("")
			&& !visitedUrl.contains(url)
			&& !unVisitedUrl.contains(url))
			unVisitedUrl.add(url);
	}

	@Override
	public boolean unvisitedEmpty() {
		return unVisitedUrl.isEmpty();
	}

	@Override
	public String getUnvisited() {
		return unVisitedUrl.poll().toString();
	}

	@Override
	public void addVisited(String url) {
		visitedUrl.add(url);
	}

	@Override
	public boolean visited(String url) {
		return visitedUrl.contains(url);
	}
}
