# 关闭与启动

- 阻塞式启动
    
    ```
    kafka-server-start.sh /opt/module/kafka/config/server.properties 
    ```

- 守护进程启动
    ```
    kafka-server-start.sh -daemon /opt/module/kafka/config/server.properties 
    ```

- 停止kafka
    ```
    kafka-server-stop.sh /opt/module/kafka/config/server.properties 
    ```

# topic操作

- 查看当前服务器的所有topic

    ```sh
    kafka-topics.sh --list --zookeeper zk01:2181
    ```

- 创建topic，需要指定分区数，副本数

    ```sh
    kafka-topics.sh --create \
    --zookeeper zk01:2181 \
    --topic mytest \
    --partitions 2 \
    --replication-factor 2
    ```

- 删除topic

    ```sh
    kafka-topics.sh --delete --zookeeper zk01:2181 --topic mytest
    ```

- 查看topic的详细信息

    ```sh
    kafka-topics.sh --describe \
    --topic mytest \
    --zookeeper zk01:2181
    ```

- 修改分区数

    ```sh
    kafka-topics.sh --alert \
    --topic mytest \
    --partitions 6 \
    --zookeeper zk01:2181
    ```

# 生产者与消费者

- 创建一个控制台生产者

    ```sh
    kafka-console-producer.sh --topic mytest --broker-list zk01:9092
    ```

- 创建一个控制台消费者

    - 通过zookeeper来启动，将数据保存到zookeeper，<span style='color:red'>已经过时</span>

        ```sh
        # 只获取最新的消息
        kafka-console-consumer.sh --topic mytest --zookeeper zk01:2181
        
        # `--from-beginning` 从头开始获取过去的所有的消息
        kafka-console-consumer.sh --topic mytest --zookeeper zk01:2181 --from-beginning
        ```

    - 通过 broker 启动，将数据保存到kafka内部。kafka新版本推荐使用

        ```sh
        # 只获取最新的消息
        kafka-console-consumer.sh --topic mytest --bootstrap-server zk01:9092
        
        # --bootstrap-server 应该使用 server.properties
        # listeners=PLAINTEXT://xxx:9092
        # 如果没有设置listeners，可以使用 zookeeper 结点
        # `--from-beginning` 从头开始获取过去的所有的消息
        kafka-console-consumer.sh --topic mytest --bootstrap-server zk01:9092 --from-beginning
        ```

        
