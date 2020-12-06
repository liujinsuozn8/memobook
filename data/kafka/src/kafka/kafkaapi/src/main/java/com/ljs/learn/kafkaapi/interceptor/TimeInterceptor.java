package com.ljs.learn.kafkaapi.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

// 拦截器: 为消息附加时间戳
public class TimeInterceptor implements ProducerInterceptor {
    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        // 在 value 前附加时间戳
        String newValue = System.currentTimeMillis() + ":" + record.value();
        // 用 record 创建一个新的 ProducerRecord 对象，并返回
        return new ProducerRecord(record.topic(), record.partition(), record.key(), newValue);
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
