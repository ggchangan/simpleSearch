package crawler.basic.util;

import java.util.HashMap;

/**
 * Created by magneto on 17-1-17.
 */
public class TrieNode {
    char c;
    boolean isLeaf;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();

    public TrieNode(){}
    public TrieNode(char c) {
        this.c = c;
    }

    public static void main(String[] args) {
        TrieNode t = new TrieNode();
        System.out.println(t.isLeaf);
    }
}
