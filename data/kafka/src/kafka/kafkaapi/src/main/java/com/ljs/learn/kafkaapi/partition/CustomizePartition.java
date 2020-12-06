package com.ljs.learn.kafkaapi.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

// 自定分区器
public class CustomizePartition implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取 topic 中的分区数量
        Integer integer = cluster.partitionCountForTopic(topic);

        // 计算分区
        return key.toString().hashCode() % integer;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
