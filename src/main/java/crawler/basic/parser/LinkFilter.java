package crawler.basic.parser;

public interface LinkFilter {
	public boolean accept(String url);
}

