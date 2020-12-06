package com.ljs.learn.kafkaapi.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

// 异步发送API，不使用回调函数的生产者
public class MyProducer {
    public static void main(String[] args) {
        // 1. 创建 producer 的配置信息
        Properties properties = new Properties();

        // 指定需要连接的kafka集群
        // properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // ack 应答级别
        // properties.put("acks","all");
        properties.put(ProducerConfig.ACKS_CONFIG,"all");
        // 重试次数
        properties.put("retries", "2");
        // 批次的大小，16k
        properties.put("batch.size", 16384);
        // 等待的时间
        properties.put("linger.ms", 1);
        // RecordAccumulator的缓冲区大小
        properties.put("buffer.memory", 33554432);
        // kv的序列化类
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 2. 创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 3. 生产者发送数据
        for (int i = 0; i < 10; i++) {
            // 生产者对象已经固定了类型，所以 ProducerRecord 可以省略类型
            producer.send(new ProducerRecord<>("mytest", "testdata" + i));
        }

        // 4. 关闭资源
        producer.close();
    }
}
