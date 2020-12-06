package com.ljs.learn.kafkaapi.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UseInterceptorProducer {
    public static void main(String[] args) {
        // 1. 设置配置
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.24.101.1:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 按顺序添加拦截器
        List<String> interceptors = new ArrayList<>(2);
        interceptors.add("com.ljs.learn.kafkaapi.interceptor.TimeInterceptor");
        interceptors.add("com.ljs.learn.kafkaapi.interceptor.CounterInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);


        // 2. 创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        // 3. 发送消息
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("mytest", "interceptor---" + i));
        }

        // 4. 关闭生产者
        producer.close();
    }
}
