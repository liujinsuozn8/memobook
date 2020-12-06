package com.ljs.learn.kafkaapi.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

// 同步发送
public class SyncProducer {
    public static void main(String[] args) {
        // 1. 创建 producer 的配置信息
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 2. 创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 3. 生产者发送数据
        for (int i = 0; i < 10; i++) {
            Future<RecordMetadata> mytest = producer.send(new ProducerRecord<>("mytest", "syncdata" + i));
            // 阻塞线程完成同步发送
            try {
                mytest.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 4. 关闭资源
        producer.close();
    }
}
