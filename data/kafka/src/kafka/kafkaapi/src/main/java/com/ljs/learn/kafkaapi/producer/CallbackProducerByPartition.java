package com.ljs.learn.kafkaapi.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

// 异步发送API，指定发送的分区
public class CallbackProducerByPartition {
    public static void main(String[] args) {
        // 1. 设置配置
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 2. 创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        // 3. 发送消息
        for (int i = 0; i < 10; i++) {
            producer.send(
                    // 设置数据发送的分区
                    new ProducerRecord<>("mytest", 0,"testData","callback---" + i),
                    (metaData, e) -> {
                        if (e == null) {
                            System.out.println("offset=" + metaData.offset() + ", partition=" + metaData.partition());
                        } else {
                            e.printStackTrace();
                        }
                    }
            );
        }

        // 4. 关闭生产者
        producer.close();
    }
}
