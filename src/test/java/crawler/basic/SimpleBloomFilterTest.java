package crawler.basic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by magneto on 17-3-1.
 */
public class SimpleBloomFilterTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() throws Exception {
        String email = "zhangren1989@gmail.com";
        SimpleBloomFilter filter = new SimpleBloomFilter();
        assertFalse(filter.contains(email));

        filter.add(email);
        assertTrue(filter.contains(email));

    }

}
