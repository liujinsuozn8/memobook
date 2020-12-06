package com.ljs.learn.kafkaapi.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

// 拦截器: 统计成功和失败的消息数
public class CounterInterceptor implements ProducerInterceptor {
    // 添加统计数量的属性
    private int successCount = 0;
    private int errorCount = 0;

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        // 返回原始消息
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        // 统计数量
        if (exception == null){
            // 发送成功
            successCount++;
        } else {
            // 发送失败
            errorCount++;
        }
    }

    @Override
    public void close() {
        // 结束时，输出数量
        System.out.println("successCount = " + successCount);
        System.out.println("errorCount = " + errorCount);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
