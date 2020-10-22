<span id="catalog"></span>

<span style='font-size:18px'>目录---spark-streaming<span>

- [SparkStreaming说明](#SparkStreaming说明)
- [maven依赖](#maven依赖)
- [wordcount分析](#wordcount分析)
- [创建DStream---stream的数据源](#创建DStream---stream的数据源)
    - [从RDD队列采集](#从RDD队列采集)
    - [从目录采集数据](#从目录采集数据)
    - [自定义采集](#自定义采集)
    - [kafka数据源](#kafka数据源)
- [DStream转换](#DStream转换)
    - [无状态转换操作](#无状态转换操作)
    - [有状态转化操作](#有状态转化操作)
- [](#)
- [](#)


# SparkStreaming说明
[top](#catalog)
- spark-streaming 的处理方式
    - 无法做到真正的流处理
    - 是一种微批次，准实时的处理
    - 处理时，将一个`采集周期`内的数据作为一个整体
- 支持的数据源
    - Kafka
    - Flume
    - tcp 套接字等
- 计算结果可以保存在 hdfs、数据库等存储中
- DStream
    - SparkStreaming 使用 `离散化流` discretized stream， DStream 作为抽象表示
    - DStream 是SparkStreaming 的基础抽象，表示持续性的数据流和经过各种Spark原语操作后的结果数据流
    - DStream 与 RDD
        - DStream 就是对 RDD 在实时数据处理场景的一种封装
        - 在内部，每个 `采集周期` 数据都作为 RDD 存在，即**每个采集周期都会生成一个RDD**
        - **DStream 就是由这些RDD组成组成的序列，所以是离散化的**
        - 数据的处理也是按照 RDD 为单位进行的
- 计算过程由 `Spark Engine` 完成
    ```
    input data                  batches of            batcher of
    stream       ------>        input data  ------->  processed data
                Spark Streaming            Spark Engine
    ```

- SparkStreaming架构图
    - ![图](?????)

# maven依赖
[top](#catalog)
```xml
<dependency>
    <groupId>org.apache.spark</groupId>
    <artifactId>spark-streaming_2.12</artifactId>
    <version>2.4.5</version>
</dependency>
```

# wordcount分析
[top](#catalog)
- 使用 netcat 向 9999 端口连续发送数据: `nc -l 9999`，并统计单词数
- 参考
    - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/wordcount/WordCount.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/wordcount/WordCount.scala)

- 实现内容
    ```scala
    object WordCount {
      def main(args: Array[String]): Unit = {
        // 1. 创建环境
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streaming")
        // 设置 SparkStreaming 的采集周期
        // new StreamingContext(conf, Duration(3000))
        val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

        // 2. 使用 netcat 发送数据:
        // MAC: nc -l 9999
        // 从socket 按行获取数据
        val socketDS: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)

        // 3. 计算单词数
        val wordDS: DStream[String] = socketDS.flatMap(_.split(" "))
        wordDS.map((_, 1))
          .reduceByKey(_+_)
          .print()  // print 将会周期性的输出

        // 关闭
        // driver 程序在执行 streaming 的过程中不能结束
        // ssc.stop()

        // 4. 启动采集器
        // 采集器在正常情况下启动后不应该停止，除非特殊情况
        ssc.start()
        // 等待采集器结束
        // 将会一直阻塞当前线程
        ssc.awaitTermination()
      }
    }
    ```

- 运行时，至少需要两个核
    - 一个负责采集数据
    - 一个负责执行计算
- **接收器**是一个**需要长期运行的任务**，所以不能通过 `ssc.stop` 直接关闭

# 创建DStream---stream的数据源
## 从RDD队列采集
[top](#catalog)
- 一般在测试时使用
    - 测试时，通过 `ssc.queueStream(queueOfRDDs)` 创建 DStream，每一个推送到这个队列中的RDD，都会作为一个DStream处理。
- 参考
    - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/create/QueueDStream.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/create/QueueDStream.scala)
- 代码内容
    ```scala
    // 需求：循环创建几个RDD，将RDD放入队列。通过SparkStream创建Dstream，计算WordCount
    def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setMaster("local[*]").setAppName("queueds")
      val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

      // 创建队列，类型为 RDD
      val queue = new mutable.Queue[RDD[String]]()
      // 生成队列式的Stream
      val queueDS: InputDStream[String] = ssc.queueStream(queue)

      queueDS.print()

      // 启动任务
      ssc.start()

      // 创建一个 RDD，并作为队列的数据源
      while(true){
        val rdd: RDD[String] = ssc.sparkContext.makeRDD(List("aaa", "bbb","ccc"))
        // 将 RDD 推入队列，会作为采集周期的数据
        queue.enqueue(rdd)
        Thread.sleep(1000)
      }

      ssc.awaitTermination()
    }
    ```

## 从目录采集数据
[top](#catalog)
- 一般只会处理**新添加的数据**，包括添加文件，或向已有文件末尾添加数据
- 这种方式非常不好测试
- 参考
    - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/create/TextFileDStream.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/create/TextFileDStream.scala)
- 代码内容
    ```scala
    def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setMaster("local[*]").setAppName("queueds")
      val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

      // 监控目录 in，如果有新的文件，会产生 DStream
      val dirDS: DStream[String] = ssc.textFileStream("in")
      dirDS.print()

      ssc.start()
      ssc.awaitTermination()
    }
    ```

## 自定义采集
[top](#catalog)
- 实现方式
    1. 继承 `Receiver` 类，并实现 `onStart`、`onStop`
        2. 在 ssc 中，注册采集器: `ssc.receiverStream`
- 参考
    - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/create/CustomiseDStream.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/create/CustomiseDStream.scala)
- 代码内容
    1. 自定义数据采集器
        ```scala
        // 自定义数据采集器，参考实现: SocketReceiver
        class MyReceive(host: String, port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {
          private var socket: Socket = _
          // 启动采集器
          override def onStart(): Unit = {
            // 建立 socket 连接
            socket = new Socket(host, port)
                                  new Thread("Socket Receiver") {
              setDaemon(true)
              override def run() { receive() }
            }.start()
          }
                     def receive(): Unit = {
            val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
                       var s:String = null
                       while (true){
              s = reader.readLine()
              if (s != null){
                // 调用抽象类的方法，将数据保存到框架内部
                store(s)
              }
            }
          }
          // 停止采集器
          override def onStop(): Unit = {
            synchronized {
              if (socket != null) {
                socket.close()
                socket = null
              }
            }
          }
        }
        ```
    2. 注册自定义数据源，并启动任务
        ```scala
        object CustomiseDStream {
          def main(args: Array[String]): Unit = {
            val conf = new SparkConf().setMaster("local[*]").setAppName("dstream")
            val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

            // 注册自定义数据源
            val ds: ReceiverInputDStream[String] = ssc.receiverStream(
              new MyReceive("localhost", 9999)
            )

            ds.print()

            ssc.start()
            ssc.awaitTermination()
          }
        ```

## kafka数据源
[top](#catalog)
- ?????

# DStream转换
## 无状态转换操作
[top](#catalog)
- 无状态转化操作
    - 每个时间窗口的数据之间没有关联
    - RDD 操作只对当前采集周期的数据有效
    - 无状态转化操作就是把简单的RDD转化操作应用到每个批次上，也就是转化DStream中的每一个RDD。部分无状态转化操作列在了下表中。注意，针对键值对的DStream转化操作(比如 reduceByKey())要添加import StreamingContext._才能在Scala中使用

- `transform`
    - 就是对 DStream 做一次转换
    - 该函数每个采集周期调度一次
    - 示例
        - 参考
            - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/nostate/Transform.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/nostate/Transform.scala)
        - 代码内容
            ```scala
            val ds: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)

            // 修改 RDD 让字符串 * 2
            val newDS: DStream[String] = ds.transform(
              // rdd 每隔一个时间窗口就会在 driver 端生成，
              // 所以内部的代码会重复执行
              rdd => {
                // 在 Driver 端执行，可以重复执行
                rdd.map(
                  // 算子内的代码在 Executor 端执行
                  data => {
                    data * 2
                  }
                )
              }
            )
            ```

- join
    - 两个流做join
    - 要求两个流的采集周期一致，这样才能同时触发计算
    - 计算过程就是对流中的 RDD 做 join，与两个 RDD 的 join 效果相同

## 有状态转化操作
[top](#catalog)
- `updateStateByKey`
    - 用于维护多个采集周期中的状态
    - 用于 KV 形式的 DStream
    - 处理结果是一个新的 DStream
    - 使用前提
        1. 定义状态，状态可以是任意的数据类型
        2. 定义状态更新函数，表示之前的状态和当前数据如何合并
        3. 需要配置 `checkpoint` 目录，使用检查点保存状态
    - 示例
        - 参考
            - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/hasstate/UpdateStateByKey.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/hasstate/UpdateStateByKey.scala)
        - 代码内容
            ```scala
            def main(args: Array[String]): Unit = {
              // 创建环境
              val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streaming")
              val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

              val ds = ssc.socketTextStream("localhost", 9999)
              // 1. 有状态操作的计算中间结果需要保存到检查点，需要设置检查点路径
              ssc.sparkContext.setCheckpointDir("./checkpoint/UpdateStateByKey/")

              // 2. 有状态的目录: 保存将每个时间窗口的计算结果，
              // 在下一次采集中继续使用
              ds.flatMap(_.split(" "))
                .map((_, 1L))           // 转换成 KV 形式
                .updateStateByKey[Long](
                  // seq: 相同 key 的 value 集合
                  // buffer: 缓冲区中相同 key 的数据，第一次处理时，会为空
                  (seq: Seq[Long], buffer: Option[Long]) => {
                    // 重新计算结果
                    val newBuffer = buffer.getOrElse(0L) + seq.sum
                    // 更新缓冲区
                    Option(newBuffer)
                  }
                )
                .print()

              ssc.start()
              ssc.awaitTermination()
            }
            ```

- 窗口计算
    - `.window(窗口范围, 滑动步长)`
    - 几个概念
        - 窗口的`范围`
            - **应该是采集周期的整数倍**
            - 表示一次计算使用的数据范围，即使用多少个采集周期的数据
        - 窗口的`滑动步长`
            - **应该是采集周期的整数倍**
            - 表示`计算周期`，即多少个采集周期计算一次
            - 默认的滑动步长 = 采集周期
    - 示例
        - 参考
            - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/hasstate/WindowCustomiseSlide.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/hasstate/WindowCustomiseSlide.scala)
        - 代码内容
            ```scala
            def main(args: Array[String]): Unit = {
                // 创建环境
                val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streaming")
                // 3s 一个采集周期
                val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))

                val ds = ssc.socketTextStream("localhost", 9999)
                val mapDS = ds.map((_, 1))

                // 将多个采集周期作为计算的整体
                // 窗口的范围、滑动步长 应该是采集周期的整数倍
                // 窗口的计算周期 = 窗口的滑动步长

                // 9s 表示 3 个采集周期一起计算
                // 6s 即两个采集周期计算一次
                val windowDS: DStream[(String, Int)] = mapDS.window(Seconds(9), Seconds(6))
                val reduceDS: DStream[(String, Int)] = windowDS.reduceByKey(_+_)

                reduceDS.print()

                ssc.start()
                ssc.awaitTermination()
            }
            ```
- `reduceByKeyAndWindow(func, invFunc, 窗口范围, 滑动步长)`
    - window 的问题
        - 如果 `滑动步长 < 窗口大小`，用于计算的数据会产生重叠
        - 对于大规模数据，重复计算会浪费性能
    - `reduceByKeyAndWindow` 的计算策略
        - 不会重复计算重叠部分的数据
        - 调用 `func`，**添加**当前窗口范围内，未参与过计算的采集周期数据
        - 调用 `invFunc`，**减去**上一个计算结果中已经离开重叠部分的采集周期数据
    - 默认滑动步长为： 一个采集周期
    - 使用前提
        - 需要配置 `checkpoint` 目录，保存计算的中间结果
    - 示例
        - 参考
            - [src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/hasstate/ReduceByKeyAndWindow.scala](src/spark-learn/spark-streaming/src/main/scala/com/ljs/learn/sparkstreaming/state/hasstate/ReduceByKeyAndWindow.scala)
        - 代码内容
            ```scala
            def main(args: Array[String]): Unit = {
              // 创建环境
              val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streaming")
              val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
              // 有状态操作的计算中间结果需要保存到检查点，需要设置检查点路径
              ssc.sparkContext.setCheckpointDir("./checkpoint/ReduceByKeyAndWindow/")

              val ds = ssc.socketTextStream("localhost", 9999)
              val mapDS = ds.filter(_ != "").map(datas => ("sum", datas.toInt))

              val windowDS = mapDS.reduceByKeyAndWindow(
                (x, y)=> {  // 添加新的数据
                  println(f"x=$x, y=$y")
                  x + y
                },
                (a, b)=> {  // 删除已经离开重叠区域的数据
                  println(f"a=$a, b=$b")
                  a - b
                },
                Seconds(9)
              )

              windowDS.print()

              ssc.start()
              ssc.awaitTermination()
            }
            ```


# DStream输出
[top](#catalog)

# 关闭Stream
[top](#catalog)
- Stop方法一般不会在main线程中执行


[top](#catalog)