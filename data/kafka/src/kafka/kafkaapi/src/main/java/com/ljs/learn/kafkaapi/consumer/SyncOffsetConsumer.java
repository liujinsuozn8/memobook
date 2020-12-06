package com.ljs.learn.kafkaapi.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

// 同步提交 Offset
public class SyncOffsetConsumer {
    public static void main(String[] args) {
        // 1. 配置参数
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // 关闭自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 需要设置一个新的消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "syncGroup");

        // 2. 创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 3. 订阅主题
        consumer.subscribe(Arrays.asList("mytest"));

        while(true) {
            // 4. 获取数据
            ConsumerRecords<String, String> records = consumer.poll(100);

            // 5. 解析数据
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.key() + ":" + record.value());
            }

            // 6. 同步提交，直到提交成功
            consumer.commitSync();
        }
    }
}
