package com.ljs.learn.kafkaapi.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

// 异步发送API，使用自定义分区
public class PartitionProducer {
    public static void main(String[] args) {
        // 1. 设置配置
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 2. 关联自定义分区
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.ljs.learn.kafkaapi.partition.CustomizePartition");

        // 3. 创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 4. 发送消息
        for (int i = 0; i < 10; i++) {
            producer.send(
                    new ProducerRecord<>("mytest", Integer.toString(i % 3), "customize---" + i),
                    (metaData, e) -> {
                        if (e == null) {
                            System.out.println("offset=" + metaData.offset() + ", partition=" + metaData.partition());
                        } else {
                            e.printStackTrace();
                        }
                    }
            );
        }

        // 5. 关闭生产者
        producer.close();
    }
}
