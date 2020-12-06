package com.ljs.learn.kafkaapi.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

// 消费者重置 Offset
public class ResetOffsetConsumer {
    public static void main(String[] args) {
        // 1. 配置参数
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // 重置offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 需要设置一个新的消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "newGroup");

        // 2. 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 3. 订阅主题
        consumer.subscribe(Arrays.asList("mytest"));

        // 直接做 close 会导致 消费者立即停止，所以需要在一个轮询中获取数据
        while(true) {
            // 4. 获取数据
            // 需要设置轮询时，发现kafka没有数据时的等待时间
            ConsumerRecords<String, String> records = consumer.poll(100);

            // 5. 解析数据
            // ConsumerRecords 是一个迭代器，可以进行迭代
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.key() + ":" + record.value());
            }
        }
    }
}
