package crawler.basic.queue.kafka;

import crawler.basic.queue.common.UnvisitedProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * Created by magneto on 17-3-1.
 */
public class UrlProducer implements UnvisitedProducer<String> {
    private static Properties props = new Properties();
    private Producer<String, String> producer = new KafkaProducer<>(props);
    private String topic;

    public UrlProducer(String topic) {
        this.topic = topic;
    }

    static {
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    @Override
    public void addUnvisited(String url) {
        ProducerRecord<String, String> urlRecord = new ProducerRecord<>(topic, url, url);
        producer.send(urlRecord);
    }

    public void close() {
        producer.close();
    }
}
