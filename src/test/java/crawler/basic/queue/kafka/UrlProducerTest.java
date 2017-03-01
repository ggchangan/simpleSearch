package crawler.basic.queue.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by magneto on 17-3-1.
 */
public class UrlProducerTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));

        producer.close();
    }

    @Test
    public void testAddUnvisited() throws Exception {
        String topic = "test";
        UrlProducer urlProducer = new UrlProducer(topic);
        String urlPrefix = "www.baidu.com";
        for (int i=0; i<100; i++) {
            String url = urlPrefix + i;
            urlProducer.addUnvisited(url);
        }
        urlProducer.close();
    }
}
