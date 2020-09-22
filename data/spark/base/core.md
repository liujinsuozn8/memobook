<span id="catalog"></span>

<span style='font-size:18px'>目录---spark核心编程<span>

- [基本示例--WordCount](#基本示例--WordCount)
- [创建spark上下文对象](#创建spark上下文对象)
- [Spark中的三大基本数据结构](#Spark中的三大基本数据结构)
- [RDD](#RDD)
    - [RDD的基本概念](#RDD的基本概念)
    - [RDD在Yarn下的工作原理](#RDD在Yarn下的工作原理)
    - [为什么RDD中只有逻辑没有数据](#为什么RDD中只有逻辑没有数据)
    - [RDD的核心属性](#RDD的核心属性)
    - [RDD的创建](#RDD的创建)
    - [RDD算子](#RDD算子)
- [分区](#分区)
    - [默认并行度的计算](#默认并行度的计算)
    - [分区数的计算](#分区数的计算)
    - [makeRDD的数据分区方式--源码分析](#makeRDD的数据分区方式--源码分析)
    - [textFile的数据分区方式--源码分析](#textFile的数据分区方式--源码分析)
        - [textFile--默认最小分区数的计算](#textFile--默认最小分区数的计算)
        - [textFile--实际的分区数量与读取内容的切分](#textFile--实际的分区数量与读取内容的切分)
    - [分区的注意事项](#分区的注意事项)
    - [RDD与分区](#RDD与分区)
- [shuffle操作](#shuffle操作)
- [RDD--转换算子](#RDD--转换算子)
    - [转换算子--value型](#转换算子--value型)
        - [map](#map)
        - [mapPartitions--分区数据导入计算节点](#mapPartitions--分区数据导入计算节点)
        - [mapPartitionsWithIndex--分区数据导入计算节点](#mapPartitionsWithIndex--分区数据导入计算节点)
        - [map和mapPartitions的区别](#map和mapPartitions的区别)
        - [flatMap--集合扁平化](#flatMap--集合扁平化)
        - [glom--分区数据导入内存](#glom--分区数据导入内存)
        - [groupBy--分组](#groupBy--分组)
        - [filter--过滤](#filter--过滤)
        - [sample--抽样](#sample--抽样)
        - [distance--去重](#distance--去重)
        - [coalesce--收缩合并分区](#coalesce--收缩合并分区)
        - [repartition--重新分区、扩大分区](#repartition--重新分区、扩大分区)
        - [coalesce和repartition的区别](#coalesce和repartition的区别)
        - [sortBy--排序](#sortBy--排序)
    - [转换算子--双Value类型](#转换算子--双Value类型)
    - [转换算子--kv类型](#转换算子--kv类型)
        - [kv类型的使用前提](#kv类型的使用前提)
        - [mapValues--遍历value](#mapValues--遍历value)
        - [partitionBy--指定分组方式](#partitionBy--指定分组方式)
        - [自定义分区器](#自定义分区器)
        - [reduceByKey](#reduceByKey)
        - [groupByKey](#groupByKey)
        - [groupByKey与reduceByKey的异同](#groupByKey与reduceByKey的异同)
        - [aggregateByKey](#aggregateByKey)
        - [foldByKey](#foldByKey)
        - [combineByKey](#combineByKey)
        - [reduceByKey、aggregateByKey、combineByKey、foldByKey的异同](#reduceByKey、aggregateByKey、combineByKey、foldByKey的异同)
        - [sortByKey](#sortByKey)
        - [leftOutJoin--左联](#leftOutJoin--左联)
        - [rightOuterJoin--右联](#rightOuterJoin--右联)
        - [cogroup](#cogroup)
    - [如何选择改变分区的转换算子](#如何选择改变分区的转换算子)
    - [转换算子--实际应用](#转换算子--实际应用)
    - [RDD的两种去重方式--distinct、reduceByKey](#RDD的两种去重方式--distinct、reduceByKey)
- [RDD--行动算子](#RDD--行动算子)
    - [行动算子与转换算子的区别](#行动算子与转换算子的区别)
    - [行动算子](#行动算子)
    - [行动算子的作业执行原理](#行动算子的作业执行原理)
- [各算子特性](#各算子特性)
    - [包含shuffle操作的算子](#包含shuffle操作的算子)
    - [会产生数据倾斜的算子](#会产生数据倾斜的算子)
    - [可能会产生性能问题的算子](#可能会产生性能问题的算子)
- [RDD序列化](#RDD序列化)
- [RDD依赖关系](#RDD依赖关系)
- [RDD的阶段划分](#RDD的阶段划分)
- [RDD任务划分](#RDD任务划分)
- [RDD持久化](#RDD持久化)
    - [Cache缓存](#Cache缓存)
    - [shuffle中的自动缓存](#shuffle中的自动缓存)
    - [CheckPoint检查点--提升计算稳定性](#CheckPoint检查点--提升计算稳定性)
    - [CheckPoint什么时候执行--源码分析](#CheckPoint什么时候执行--源码分析)
    - [Cache与CheckPoint的区别](#Cache与CheckPoint的区别)
- [RDD文件读写](#RDD文件读写)
- [Accumulator--累加器](#Accumulator--累加器)
    - [累加器说明](#累加器说明)
    - [累加器的基本使用方法](#累加器的基本使用方法)
    - [自定义累加器](#自定义累加器)
    - [累加器的执行原理](#累加器的执行原理)
- [Broadcast--广播变量](#Broadcast--广播变量)
- [](#)

# 基本示例--WordCount
[top](#catalog)
- scala 开发思路版本
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/wordcount/WordCount.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/wordcount/WordCount.scala)
    - 代码内容
        ```scala
        // 1. 准备 本地开发 spark 环境
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        // 2. 建立和 spark 的连接
        val sc = new SparkContext(sparkConf)

        // 3. 实现业务操作
        // 3.1 读取指定目录下的多个数据文件
        val fileRDD: RDD[String] = sc.textFile("input")

        // 3.2 将读取的内容进行扁平化操作，即切分单词
        //    fileRDD.flatMap(line=>{line.split(" ")});
        val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))

        // 3.3 将分词后的数据进行分组，并计算每个分组中的数量
        // (word, Iterator) => (word, count)
        val groupRDD: RDD[(String, Iterable[String])] = wordRDD.groupBy(word => word)
        val mapRDD: RDD[(String, Int)] = groupRDD.map(x=>(x._1, x._2.size))

        // 3.5 【采集】聚合后的结果打印到控制台
        val wordCountArray: Array[(String, Int)] = mapRDD.collect()
        println(wordCountArray.mkString(","))

        // 4. 关闭连接
        sc.stop();
        ```
- spark 开发思路版本
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/wordcount/WordCount.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/wordcount/WordCount.scala)
    - 与 scala 开发思路的不同点
        - 通过 `reduceByKey` 同时进行分组与求和
        - 在执行前需要将数据转换为 `(key, 1)` 的格式，即求和前与求和结果的格式相同，就可以**在分组的同时求和**
    - 代码内容
        ```scala
        // 1. 准备 本地开发 spark 环境
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        // 2. 建立和 spark 的连接
        val sc = new SparkContext(sparkConf)

        // 3. 实现业务操作
        // 3.1 读取指定目录下的多个数据文件
        val fileRDD: RDD[String] = sc.textFile("input")

        // 3.2 将读取的内容进行扁平化操作，即切分单词
        val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))

        // 3.3 将分词后的数据进行结构转换，转换成和最终的计算结果相同的格式
        // word => (word, 1)
        val mapRDD: RDD[(String, Int)] = wordRDD.map(word =>(word, 1))

        // 3.4 将转换结构后的数据根据数据进行分组聚合
        // 替代 group、map
        // 即分组的同时做聚合，从key 直接的到 （key，count）
        // 根据数据的key分组，并统计value
        val wordToSumRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_+_)

        // 3.5 采集聚合后的结果打印到控制台
        val wordCountArray: Array[(String, Int)] = wordToSumRDD.collect()
        println(wordCountArray.mkString(","))

        // 4. 关闭连接
        sc.stop();
        ```

# 创建spark上下文对象
[top](#catalog)
- 示例
    ```scala
    // 创建配置对象
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("testMemo")
    // 创建spark上下文对象，建立运行环境与 spark 的连接
    val sc = new SparkContext(conf)

    // 计算操作...

    // 关闭上下文对象
    sc.stop();
    ```
- `new SparkConf()` 创建一个spark配置对象
    - 空参构造器会使用默认配置
- `conf.setMaster("local")` 用于设置任务的运行环境
    - `local` 表示运行在本地环境
        - `local[*]`，表示使用本地机器的所有 cpu 核
        - `local[N]`，表示使用本地机器的 N 个 cpu 核
        - `local`，表示只使用本地机器的一个 cpu 核
    - `yarn` 表示在 Yarn 中运行
- `setAppName("testMemo")` 设置当前任务的名字
- `sc = new SparkContext(conf)` 建立 运行环境 和 spark 的连接，并返回一个 spark 上下文对象

# Spark中的三大基本数据结构
[top](#catalog)
- spark 三大基本数据结构

    |数据结构   |说明|
    |----------|-|
    |RDD       |弹性分布式数据集|
    |累加器     |分布式共享<span style='color:red'>只写</span>变量  |
    |广播变量   |分布式共享<span style='color:red'>只读</span>变量|

- spark 为了进行高并发和高吞吐的数据处理，封装了这 3 种数据结构
- 三种数据结构有各自的适用场景
    - ?????

# RDD---Resilient_Distributed_Dataset---弹性分布式数据集
## RDD的基本概念
[top](#catalog)
- RDD 是 spark 中**最基本**的数据处理模型
- RDD 是什么?
    - 一种用于数据处理的 `数据模型 + 数据结构`，可以存储计算逻辑
    - `数据模型`，表示数据的高度抽象
- RDD 的本质
    - 一个抽象类
    - spark 中提供了多种 `RDD` 的不同实现
- RDD 的特性
    - 可分区性
        - 可以对需要计算的数据进行分区
        - **分区的目的**是为了**提高消费能力**，更**适合并行计算**
    - 分布式并行计算
        - 数据可以分布在集群的不同节点上
        - 可分区就是为了能够并行计算
    - 弹性
        - 弹性存储: 计算时，数据可以在内存、磁盘之间自动切换
        - 弹性容错: 数据丢失时，可以自动回复
        - 弹性计算: 计算异常时，可以重新计算
        - 弹性分区: 可以根据需要重新分区
    - 数据抽象性
        - RDD是一个抽象类，需要子类提供实现
    - RDD<span style='color:red'>不保存数据，只保存计算逻辑</span>
    - <span style='color:red'>不可变性</span>
        - 因为 RDD 只保存计算逻辑，无法随意更改
        - 如果需要改变，必须生成新的 RDD，并在新的 RDD 中封装新的逻辑
            - 下面的程序会生成两个 RDD， 每个RDD都保持这各自的逻辑
            ```scala
            val mapRDD: RDD[(String, Int)] = wordRDD.map(word =>(word, 1))

            val wordToSumRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_+_)
            ```
    - 延迟加载
        - 构建逻辑时，不会执行
        - 执行 `行动算子` 时，才会开始计算

- RDD 的本质是**装饰器**
    - 每次生成新的 RDD 时，都会存储旧的 RDD，并在其基础上封装新的功能
    - 类似与Java 中 IO 包下的多层封装

## RDD的核心属性
[top](#catalog)
- 分区列表: 获取分区结果，是并行计算的核心
    ```scala
    protected def getPartitions: Array[Partition]
    ```
- 分区计算函数，计算数据的分区方式
    ```scala
    def compute(split: Partition, context: TaskContext): Iterator[T]
    ```
- RDD之间的依赖关系
    - RDD 内只有计算逻辑，多个RDD组合时，就需要建立多个 RDD 的依赖关系
        ```scala
        protected def getDependencies: Seq[Dependency[_]] = deps
        ```
- 分区器（可选）
    - 当数据是 key-value 类型时，可以设置分区器，自定义数据的分区
        ```scala
        @transient val partitioner: Option[Partitioner] = None
        ```
- （执行计算的）首选位置（可选）
    - 计算数据时，可以根据 executor 节点的状态选择不同的节点进行计算
        ```scala
        protected def getPreferredLocations(split: Partition): Seq[String] = Nil
        ```
    - 本地化级别
        - ?????

## RDD在Yarn下的工作原理
[top](#catalog)
- 工作流程
    1. 启动yarn
        - [图](?????)
    2. spark向yarn申请资源、创建调度节点和计算节点
        - [图](?????)
    3. 计算分区
        - [图](?????)
    4. 根据分区划分成不同的任务
        - [图](?????)
    5. 调度节点将任务根据计算节点状态，发送到对应的计算节点进行计算
        - [图](?????)
- RDD 在整个流程中用于: 封装逻辑，并生成 task 发送给 executor 节点执行计算

## 为什么RDD中只有逻辑没有数据
[top](#catalog)
- 一个任务的基本执行过程
    1. driver 分区，创建 task
    2. driver 将 task <span style='color:red'>通过网络</span>传输给集群中其他集群上的 executor
    3. executor 接收到 task 后，从 driver 出拉取数据，开始执行计算
- 如果 RDD 读取了数据:
    1. 重复的读数据
        - 在 driver 上，会读取一次数据，封装到 task
        - task 传给 executor 后，executor还要再次从 task 中读取数据
        - 造成了数据的重复读取，会降低性能
    2. driver 端读取数据，再封装到 task 时，浪费了 driver 端的内存，会造成 driver 端的数据冗余
        - 不如让 executor 直接拉取，没有过多的内存消耗
- <span style='color:red'>没有读取数据导致的其他问题</span>
    - 因为没有真正的读取文件，所以无法预知如何分区、将哪些数据放到哪个executro中执行，**所以需要提前规划分区**

## RDD的创建
[top](#catalog)
1. 调用前一个 RDD 的算子，创建新的 RDD
2. 从文件、目录、通配符目录、hdfs目录创建
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromFile.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromFile.scala)
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromFilePart.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromFilePart.scala)
    - 创建方式
        ```scala
        // 使用本地文件创建
        val fileRDD: RDD[String] = sc.textFile("./input/word03.txt")
        // 使用本地目录创建
        val fileRDD: RDD[String] = sc.textFile("./input")
        // 使用通配符目录
        val fileRDD: RDD[String] = sc.textFile("./input/word*")
        // 使用 hdfs 目录创建
        val fileRDD: RDD[String] = sc.textFile("hdfs:///......")
        ```
    - 创建时可以用**第二个参数**指定分区数
        ```scala
        val fileRDD: RDD[String] = sc.textFile("./input/word03.txt", 分区数)
        ```
    - 实际的分区数量
        - 参考: [分区数的计算](#分区数的计算)

3. 从内存（集合对象）中创建 RDD
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemory.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemory.scala)
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemoryPart.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemoryPart.scala)
    - 创建方式
        ```scala
        // 准备数据，可以是任意类型的数据
        val list = List(1,2,3,4)

        // 创建方式1：并行，可以指定分区数
        val rdd: RDD[Int] = sc.parallelize(list, 分区数)

        // 创建方式2：直接创建，底层调用的仍然是 parallelize
        val rdd: RDD[Int] = sc.makeRDD(list, 分区数)
        ```

    - 创建时可以用**第二个参数**指定分区数
    - 实际的分区数量
        - 参考: [分区数的计算](#分区数的计算)

## RDD算子
[top](#catalog)
- RDD算子就是 RDD的方法。RDD的方法与其他对象的方法不同，所以叫算子
- 算子分类
    - 转换算子
        - 转换算子就是将旧的RDD通过方法转换成新的RDD
    - 行动算子
        - 开始执行计算
- 转换算子的类型
    - Value 类型
    - 双 Value 类型
    - Key-Value 类型

# 分区
## 默认并行度的计算
[top](#catalog)
- 能够**并行计算的任务数量**称为`并行度`
- 并行度的计算方式
    - 如果配置的参数: `spark.default.parallelism`，则使用该参数作为并行度
    - 如果没有配置参数
        - 本地环境下
            - 使用 `new SparkConf().setMaster(...)` 时设置的 CPU 核心数
                - `setMaster("local")`，表示单核
                - `setMaster("local[N]")`，表示 N 核
                - `setMaster("local[*]")`，表示当前环境中的所有可用核数
            - 相当于
                ```js
                并行度 = "spark.default.parallelism" || 环境中的可用核数
                ```
        - 分布式环境下
            - ?????

- 源码分析
    1. TaskScheduler
        ```scala
        def defaultParallelism: Int = {
            assertNotStopped()
            taskScheduler.defaultParallelism    // 获取默认并行度
        }
        ```
    2. TaskSchedulerImpl
        ```scala
        // 获取默认并行度
        override def defaultParallelism(): Int = backend.defaultParallelism()
        ```
    3. LocalSchedulerBackend--本地运行的并行度
        ```scala
        private[spark] trait SchedulerBackend {
            def defaultParallelism(): Int
        }
        ```
        ```scala
        // LocalSchedulerBackend
        // 获取 spark 的默认参数
        // 如果没有使用默认参数，会使用 totalCores 机器的总核数
        // 这里的 conf 就是通过 new SparkConf 创建的配置对象
        override def defaultParallelism(): Int =
            scheduler.conf.getInt("spark.default.parallelism", totalCores)
        ```

## 分区数的计算
[top](#catalog)
- 计算的前提，需要获取到默认并行度
    - `"spark.default.parallelism" || totalCores`
        - totalCores 可以是 `setMaster("local[*]")` 中指定的核数

- 不同的 RDD 创建方式，有不同的分区计算方式
    - makeRDD
        - 分区数 = `手动设置的分区数 || 并行度`
    - textfile
        - 从文件创建的 RDD，需要根据文件大小，**对文件进行切割**
        - 文件切割的实现由 `hadoop` 的 `FileInputFormat` 提供
        - 计算方式
            1. 获取 `默认最小分区数` = `min(并行度, 2)`
                - 相当于: `min("spark.default.parallelism", 环境中的可用核数, 2)`
            2. 分区数 = `文件长度 / 默认最小分区数 + if ((文件长度 % 默认最小分区数) / 默认最小分区数) > 0.1 { 加 1 }`

## makeRDD的数据分区方式--源码分析
[top](#catalog)
- 参考代码
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemoryPart.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemoryPart.scala)
- 入口点：一种特殊情况，5个元素，分 3 份
    - `sc.makeRDD(List(1, 2, 3, 4, 5), 3)`
    - 分区结果
        ```
        0 --> (0,1)
        1 --> (1,3)
        2 --> (3,5)
        ```
- 数据分割的位置
    - 每个分区的起始位置: `((第i个分区 * 数据长度) / 分区数).toInt`
    - 每个分区的终止位置: `(((第i个分区 + 1) * 数据长度) / 分区数).toInt`
- 源码分析
    1. SparkContext
        ```scala
        def makeRDD[T: ClassTag](
            seq: Seq[T],
            // 默认按照并行度划分
            numSlices: Int = defaultParallelism): RDD[T] = withScope {
            // 1. 调用并行处理
            parallelize(seq, numSlices)
        }

        // 1. 调用并行处理
        def parallelize[T: ClassTag](
            seq: Seq[T],
            numSlices: Int = defaultParallelism): RDD[T] = withScope {
        assertNotStopped()
        // 2. 创建新的 RDD
        new ParallelCollectionRDD[T](this, seq, numSlices, Map[Int, Seq[String]]())
        }
        ```
    2. ParallelCollectionRDD **伴生类**，重写 `RDD` 的方法
        ```scala
        private[spark] class ParallelCollectionRDD[T: ClassTag](
            sc: SparkContext,
            @transient private val data: Seq[T],
            numSlices: Int,
            locationPrefs: Map[Int, Seq[String]])
            extends RDD[T](sc, Nil) {

        // 获取分区
        override def getPartitions: Array[Partition] = {
            // 3. 调用 `ParallelCollectionRDD.slice` 切分数据
            // 两个参数正好是，调用 makeRDD 时的参数：
            // - List(1,2,3,4,5)
            // - 3
            val slices = ParallelCollectionRDD.slice(data, numSlices).toArray
            slices.indices.map(i => new ParallelCollectionPartition(id, i, slices(i))).toArray
        }
        }
        ```
    3. ParallelCollectionRDD **伴生对象**，计算数据的切分方式，并执行数据切分
        ```scala
        private object ParallelCollectionRDD {
        def slice[T: ClassTag](seq: Seq[T], numSlices: Int): Seq[Seq[T]] = {
            // 如果切片数量小于 1 ，则抛出异常
            if (numSlices < 1) {
                throw new IllegalArgumentException("Positive number of partitions required")
            }
            // 4.2 调用 positions(5, 3)，获取数据的切分方式
            // 用分区数创建 (0 until 3)
            // 0  start = (0 * 5) / 3 = 0, end = ((0+1) * 5) / 3 = 1  --> (0,1)
            // 1  start = (1 * 5) / 3 = 1, end = ((1+1) * 5) / 3 = 3  --> (1,3)
            // 2  start = (2 * 5) / 3 = 3, end = ((2+1) * 5) / 3 = 5  --> (3,5)
            def positions(length: Long, numSlices: Int): Iterator[(Int, Int)] = {
            (0 until numSlices).iterator.map { i =>
                val start = ((i * length) / numSlices).toInt
                val end = (((i + 1) * length) / numSlices).toInt
                (start, end)
            }
            }
            seq match {
            case r: Range =>
                // 其他处理...
            case nr: NumericRange[_] =>
                // 其他处理...
            // 4. 成功匹配 List(1,2,3,4,5)
            case _ =>
                // 4.1 List(1,2,3,4,5)--> Array(1,2,3,4,5)
                val array = seq.toArray // To prevent O(n^2) operations for List etc
                // 4.2 调用 positions(5, 3)，获取每个分组的起始与终止位置
                positions(array.length, numSlices).map { case (start, end) =>
                    // 4.3 根据范围对数组进行切分
                    array.slice(start, end).toSeq
                }.toSeq
            }
        }
        }
        ```

## textFile的数据分区方式--源码分析
### textFile--默认最小分区数的计算
[top](#catalog)
- 参考代码
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromFile.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromFile.scala)

- SparkContext
    ```scala
    def textFile(
        path: String,
        // 第二参数: 最小分区数量
        minPartitions: Int = defaultMinPartitions): RDD[String] = withScope {
      assertNotStopped()
      hadoopFile(path, classOf[TextInputFormat], classOf[LongWritable], classOf[Text],
      minPartitions).map(pair => pair._2.toString).setName(path)
    }

    // 第二参数与默认并行度进行比较，去最小值
    // 默认并行度的计算: scheduler.conf.getInt("spark.default.parallelism", totalCores)
    // 默认情况下可以理解为: math.min(totalCores, 2)
    def defaultMinPartitions: Int = math.min(defaultParallelism, 2)
    ```

### textFile--实际的分区数量与读取内容的切分
[top](#catalog)
- 参考代码
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemoryPart.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/create/RDDFromMemoryPart.scala)

- spark 使用是hadoop的 `FileInputFormat` 来读取文件
- 实际分区数量 = `文件长度 / 默认最小分区数 + if ((文件长度 % 默认最小分区数) / 默认最小分区数) > 0.1 { 加 1 }`
- **具体的分区数量是计算出来的，不一定等于最小分区数**
- 数据的读取 = 优先按行读取 + 偏移量计算
    - 如: `@@` 表示换行符，共10byte
        ```
        1@@
        2@@
        3@@
        4
        ```
    - 10 byte 分成 4 个区
        - 10 / 4 = 2， 余 2。余数是 2 的1倍，**所以分成 5 个区**
        - 分区与分区预期要读取的范围，**左右范围都是闭合**的，如果开头的某些字节被读取过，会跳过
            ```
            0 [0, 2]
            1 [2, 4]
            2 [4, 6]
            3 [6, 8]
            4 [8, 10]
            ```
    - 实际读取时，会优先按照行读取，所以会有偏移量的计算
        - 数据的偏移量，从 0 开始
            ```
            1@@ = 012
            2@@ = 345
            3@@ = 678
            4   = 9
            ```
        - 读取结果
            ```
            分区1: 1@@  读取 0、1、2
            分区2: 2@@  读取 2、3，因为 2 已经被分区1读完了，所以不读。优先读取一行，所以会读取: 3、4、5
            分区3: 3@@  读取 4、5、6，因为 4、5已经被分区2读完了，所以不读。读 6 并优先读取一行，所以会读: 6、7、8
            分区4: 空白  读取 6、7、8，但是都被分区3读完了，所以是空白
            分区5: 9    读取 8、9、10，8已经被分区4读完了，只读到: 9
            ```
- **读取范围是闭合**的检验
    - 检验代码
        ```scala
        val fileRDD3: RDD[String] = sc.textFile("./input/word04.txt", 2)
        fileRDD3.saveAsTextFile("output03")
        ```
    - 数据
        ```
        1@@ => 012
        234 => 345
        ```
    - 分区数量: 6 byte / 2 = 3
        - 分两个区，每个区3byte
        - 读取范围
            ```
            分区1: [0, 3]
            分区2: [3, 4]
            ```
    - 读取的结果
        ```
        分区1: 1@@234，读取[0, 3]=1@@2，但是2是新的行，需要读取一整行，所以读取了: 1@@234
        分区2: 空白，所有内容已经被分区1读完了
        ```

## 分区的注意事项
[top](#catalog)
- 分区数需要合理设置，**分区数过多会增加调度成本**
- 每个分区的执行顺序是不确定的，**分区的顺序与计算顺序无关**
- 各任务、分区应该是互不干扰的
- 分区数 和 并行度 不是一个概念
    - 并行执行的任务数量**不是**分区数
    - <span style='color:red'>不在创建 RDD 时指定分区数的情况下</span>
        1. `分区数 = 并行度`
        2. 设置并行度就是在设置分区数量
- **在资源不足时**，分区数量不等于 executor 数量，会按照资源量处理

## RDD与分区
[top](#catalog)
- 执行时：一个 分区对应 一个 task，一个 task 运行在一个 executor，一个运行结果生成一个结果文件
- 分区的执行顺序
    - 分区内先计算，分区间无效并等待合并结果
    - **分区内**
        - 数据，按照顺序执行
        - **一条数据**的所有逻辑全部**执行完毕**后，**才会执行下一条**数据
    - 分区间
        - 各分区的数据没有执行顺序，并且无需等待，互不影响
        - 如果有分区执行完毕，则结果会进行合并
- 转换后的算子，默认情况下，分区数量不变
    - 即后一个rdd的分区沿用前一个rdd的分区

# shuffle操作
[top](#catalog)
- shuffle 的本质
    - 将上游的分区数据打乱、重新组合，然后传给下游 RDD 的分区
- shuffle 操作一般是**面向整个数据集的**，而不是某个数据分区，所以**需要等待所有数据**

- shuffle 操作**是在磁盘上完成的**
    - shuffle 操作<span style='color:red'>无法在内存中完成</span>
    - 因为不知道需要等待多长时间、数据量有多大
    - 等待数据时，数据会越来越多，**如果是在内存中的等待，可能会导致内存不足**
        - 即在内存中等待，可能有任务失败的风险，所以要移动到磁盘中保持
- shuffle造成的等待是双重的
    1. 处理上游数据时，等待所有数据的过程中需要落盘
    2. shuffle 结束后，下游需要重新读取数据

- shuffle 会将一个计算阶段拆分成两个
    - 因为 shuffle 造成的等待，会导致整个计算过程无法作为一个整体运行，而是**被拆分成二个阶段**
    - task 也会被查分成两个
    - 会导致 shuffle 时， 分区数 * 2
        - 即前阶段一个分区，后阶段一个分区

- 通过 shuffle 重组数据，可以提高分区利用率
    - 一部分算子可能会导致数据倾斜，如某些分区为空
    - 通过 shuffle 重组数据，可以利用这些分区

- 一个方法的性能主要看shuffle的性能
    - 因为 shuffle 需要落盘
    - 落盘是因为需要等待其他分区的数据，形成新的分区，然后才能继续计算
    - 数据量大，则速度慢
    - 数据量小，则速度快

- 如何提高 shuffle 的性能?
    - 基本思路: **减少 shuffle 过程中的落盘数据，减少IO操作**
    - 实现方式
        - 在进入 shuffle 之前，在分区内部，对数据进行聚合，降低数据量
    - 如: 在分组聚合操作中，可以将 `groupByKey` 改为 `reduceByKey`

# RDD算子


## 开发RDD的注意事项
[top](#catalog)
- 逻辑的封装
    - 逻辑之间可能会存在关系，但是不应该在一个RDD中封装大量逻辑
    - 应该对逻辑进行拆解

# RDD--转换算子
## 转换算子--value型
### map
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 与scala的map类似
- 示例： 从日志文件中获取用户请求的 url
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/TestDemoApacheLog.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/TestDemoApacheLog.scala)
    - 代码内容
        ```scala
        // 从服务器日志数据apache.log中获取用户请求URL资源路径
        val fileRDD: RDD[String] = sc.textFile("./input/demo/apache.log")
        fileRDD.map(_.split(" ")(6))    // 切分数据并获取第 7 个字段
          .collect()
          .foreach(println)
        ```

### mapPartitions--分区数据导入计算节点
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 将**待处理的数据**以分区为单位发送到**计算节点**进行处理
        - 可以进行任意的处理????
    - 即: 将数据封装到 task 中
- 回调函数的参数是迭代器，返回值也是迭代器
    - 如果返回值不是迭代器，可以手动封装: `List(...).iterator`
- 示例: 获取每个分区的最大值
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDMapPartitions.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDMapPartitions.scala)
    - 代码内容
        ```scala
        // 获取每个数据分区的最大值
        // 参数是迭代器，结果也是迭代器
        val rdd3: RDD[Int] = sc.makeRDD(List(1, 4, 3, 2, 5), 3)
          .mapPartitions(
            iter => {
              List(iter.max).iterator   // 手动分装一个迭代器
            }
          )
        println(rdd3.collect().mkString(","))
        ```

### mapPartitionsWithIndex--分区数据导入计算节点
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 将 **待处理的数据**、**分区索引** 以分区为单位发送到**计算节点**

- **分区索引从0开始**

- 示例: 返回第N个分区
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDMapPartitionsWithIndex.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDMapPartitionsWithIndex.scala)
    - 代码内容
        ```scala
        val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)
        // 3. 获取第二个分区的数据
        val rdd3 = rdd.mapPartitionsWithIndex(
          (idx, iter) => {
            if (idx == 1) {
              iter
            } else {
              Nil.iterator // 不需要的分区，返回空集合的迭代器
            }
          }
        )
        println(rdd3.collect().mkString(","))
        ```

- 如果要舍弃某个分区，**需要返回空集合的迭代器**: `Nil.iterator`，使程序可以正常编译

### map和mapPartitions的区别
[top](#catalog)
- map
    - 每次处理的数据量
        - 每次只处理一条数据
    - map 是全量数据操作，不能丢失数据

- mapPartitions
    - 每次处理的数据量
        - 每次将一个分区的数据当做一个整体进行处理
    - 如果一个分区的数据没有处理完，那么**所有的数据**都不会释放，即使前面已经处理完的数据也不会释放
        - 容易导致内存溢出
        - <span style='color:red'>当内存足够大时，推荐使用 mapPartitions</span>

- `map` 不适用，需要 `mapPartitions` 替代的场景
    - 处理内部有文件读取、数据库连接等操作
    - 因为map会应用与每一条数据，所以每一条都会创建连接，产生了大量的冗余操作
    - 这种情况应该使用 `mapPartitions`，只在每个分区中，做一次连接

### flatMap--集合扁平化
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 集合扁平化处理
    - 如 `((1,2), (3,4))` --> `(1, 2, 3, 4)`
- 回调函数中应该返回一个**可迭代对象** ?????
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDFlatMap.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDFlatMap.scala)

    - 代码内容
        1. 将二维数组转换成 一维数组
            ```scala
            val rdd: RDD[List[Int]] = sc.makeRDD(
              List(
                List(2, 4, 6), List(1, 3, 5)
              )
            )

            println(rdd.flatMap(list => list).collect().mkString(","))
            ```
        2. 将多维数组和值类型混合的数组转换为一维数组
            ```scala
            // 将 Any 类型的集合进行扁平化操作
            val rddX: RDD[Any] = sc.makeRDD(
              List(
                List(1, 2), "X", List(5, 6) // 同时包含值数据和数组
              )
            ).flatMap {
              case list: List[_] => list    // 匹配数组，直接返回
              case x => List(x)             // 匹配值数据，封装成数组
            }
            println(rddX.collect().mkString(","))
            ```

### glom--分区数据导入内存
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 将每个分区内的**数据转换为**相同类型的**内存数组**，分区不变
- 如果数据量大可能会有性能问题，或内存溢出
- 示例: 计算所有分区最大值，并求和
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDGlom.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDGlom.scala)

    - 代码内容
        ```scala
        // 将分区数据转换为数组
        val rdd: RDD[Int] = sc.makeRDD(List(1,3,5,2,4,6), 3)
        val rdd2: RDD[Array[Int]] = rdd.glom()
        // 输出每个分区的所有数据
        rdd2.foreach(arr=>println(arr.mkString(",")))

        // 计算所有分区最大值，并求和
        // 方式1，使用 glom 实现
        val d: Double = rdd.glom().map(_.max).sum()
        println(d)

        // 方式2，使用 mapPartitions
        println(rdd.mapPartitions(
          iter => {
            List(iter.max).iterator // 返回当前分区的最大值
          }
        ).sum())
        ```

### groupBy--分组
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 将数据根据指定的规则进行分组
        - 回调函数的返回值就是分组的key
    - **分区数默认不变**，但是会有 **shuffle**
    - 可以通过第二参数设置分组后的分区数，防止数据不均匀
- 分组后的结果
    - 分组后，**一个组只在一个分区中。一个分区中可以包含多个组**
    - 返回值为 `List[tuple]`
        - tuple 的第 1 个元素，表示分组的key
        - tuple 的第 2 个元素，表示相同key组成的可迭代集合

- 示例: 奇数偶数分组
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDGroupBy.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDGroupBy.scala)
    - 代码内容
        ```scala
        // 0. 数据分成3个区
        val rdd: RDD[Int] = sc.makeRDD(List(1,3,5,2,4,6), 3)
        // 1. 数据分组，一共两个组
        val rdd2: RDD[(Int, Iterable[Int])] = rdd.groupBy(x => x % 2)

        // 2. 检查数据分组后的分区数
        // 一共两个分组，3个数据分区，【产生了数据分区数据不均匀的情况】
        println(s"分组后的数据分区数: ${rdd2.glom().collect().length}")

        // 3. 将分组后的分区保持到文件
        rdd2.saveAsTextFile("./output/groupby")

        // 4. 改变 groupby 后的分区数 3--> 2
        rdd.groupBy(
           _ % 2,
          2
        ).saveAsTextFile("./output/groupby02")
        ```

### filter--过滤
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 过滤数据
    - 过滤后，分区数不变，但是分区内的数据可能不均衡，可能会出现数据倾斜
- 示例: 获取服务器日志中 2015/5/17 的请求路径
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/TestDemoFilterApacheLog.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/TestDemoFilterApacheLog.scala)
    - 代码内容
        ```scala
        val fileRDD: RDD[String] = sc.textFile("./input/demo/apache.log")
        fileRDD.map(_.split(" "))
          .filter(_(3).startsWith("17/05/2015")) // 过滤第4个字段为指定日期的日志
          .map(x=>(x(3), x(6)))
          .collect()
          .foreach(x=>println(s"${x._1}, ${x._2}"))
        ```

### sample--抽样
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 根据指定的规则从数据集中抽取数据
- 参数说明
    - withReplacement
        - 抽取之后是否放回，即是否可以重复抽取
    - fraction
        - 在不放回的情况下的抽取几率
        - 在放回的情况下，重复抽取的次数

### distance--去重
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 去重
    - 可以通过第二参数修改分区数量
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDDistance.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDDistance.scala)
    - 代码内容
        ```scala
        val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,1,2,4))
        println(rdd.distinct().collect().mkString(","))
        ```

### coalesce--收缩合并分区
[top](#catalog)
- 函数签名
    - [](?????)
- coalesce(分区数量, shuffle=false)
- 功能
    - 将分区<span style='color:red'>收缩合并</span>到指定数量
    - 处理过程中没有 shuffle
- 使用场景
    - 数据分区不合理时，可以缩减分区
    - 根据数据量缩减分区。用于大数据集过滤后，提高小数据集的执行效率
        - 当**存在过多的小任务时**，可以通过coalesce方法，收缩合并分区，**减少分区的个数，减小任务调度成本**

- 缩减分区时，数据不会打乱重新组合，即 <span style='color:red'>没有 shuffle</span>

- coalesce 方法默认**无法增加**分区数
    - 因为默认没有 shuffle，所以扩大分区是没有意义的
        - 没有 shuffle，即使扩大了分区，新的分区中也没有数据
    - 如果想要扩大分区，必须使用第二参数: `shuffle=true`，开启 shuffle
        - 为了更直观的表示，应该使用: [repartition](#repartition)
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDCoalesce.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDCoalesce.scala)
    - 代码内容
        ```scala
        // 1.
        // 数据分区
        // [1,1,1] [2,2,2]
        // 过滤之后
        // [] [2,2,2]
        // 过滤之后，会产生数据倾斜
        val rdd: RDD[Int] = sc.makeRDD(List(1, 1, 1, 2, 2, 2), 2)
          .filter(x => {
            x % 2 == 0
          })
        rdd.collect().foreach(println)
        rdd.saveAsTextFile("./output/RDDCoalesce")

        // 2. 缩减分区为 1
        rdd.coalesce(1)
            .saveAsTextFile("./output/RDDCoalesce02")
        ```

### repartition--重新分区、扩大分区
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 扩大分区数
    - 让数据更加均衡，但是不能完美的解决数据倾斜

- 底层会调用 [coalesce](#coalesce)，`shuffle = true`
    ```scala
    def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
        coalesce(numPartitions, shuffle = true)
    }
    ```
- 分区数可以从 多-->少、少-->多
    - 无论如果变化，**都会触发 shuffle 操作，重新组合数据**

### coalesce和repartition的区别
[top](#catalog)
- <span style='color:red'>扩大分区使用 repartition，缩减分区使用 coalesce</span>
- coalesce
    - 默认没有 shuffle
    - 无法增加分区，只能减少
- repartition
    - 默认有 shuffle
    - 可以向任意方向调整分区数

### sortBy--排序
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 按照回调函数的执行结果对数据排序
    - 默认为升序排列
- 默认情况下，排序后的 分区数 与排序前**相同**
- 排序后可能会有数据倾斜
- 底层在 sortByKey 中调用了 RangePartitioner 来设置分区
    - ?????
- <span style='color:red'>sortBy 多字段排序</span>
  - 可以将待排序的字段整理成 tuple: `(p1, p2, p3)`
  - sortBy 会依次比较 tuple 中的每个字段
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDSortBy.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/value/RDDSortBy.scala)
    - 代码内容
        ```scala
        val rdd: RDD[Int] = sc.makeRDD(List(1,2,3,1,2,4))
        // 升序排列
        println(rdd.sortBy(x => x).collect().mkString(","))

        // 降序排列
        println(rdd.sortBy(x => x, false).collect().mkString(","))
        ```

## 转换算子--双Value类型
[top](#catalog)
- union，并集
    - 数据合并，分区也会叠加
    - 只能处理相同类型的数据
- intersection，交集
    - 保留最大的分区数，数据会被shuffle
    - 只能处理相同类型的数据
- subtract，差集
    - 数据会被shuffle
    - 以调用 subtract 的rdd的分区为主，并且分区数量 = 当前rdd的数量
    - 只能处理相同类型的数据
- zip
    - 分区数不变
    - 正常执行的条件
        - 2 个rdd的分区数相同、分区中的数量不同
        - 2 个rdd的各分区中的数据量相同
    - 可以链接不同类型的数据
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/doublevalue/RDDBase.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/doublevalue/RDDBase.scala)
    - 代码内容
        ```scala
        val rdd1 = sc.makeRDD(List(1,2,3,4), 2)
        val rdd2 = sc.makeRDD(List(3,4,5,6), 2)

        // 并集
        println(rdd1.union(rdd2).collect().mkString(","))

        // 交集
        println(rdd1.intersection(rdd2).collect().mkString(","))

        // 差集
        println(rdd1.subtract(rdd2).collect().mkString(","))

        // zip
        println(rdd1.zip(rdd2).collect().mkString(","))
        ```

## 转换算子--kv类型
### kv类型的使用前提
[top](#catalog)
- spark中很多方法是基于key进行操作，数据应该为 `tuple` 类型
- 如果数据类型是 kv 类型，spark 会用隐式转换为 RDD 补充一些功能
- kv类型操作都由 `PairRDDFunction` 提供
    - RDD 的伴生对象中，提供了隐式函数，可以将 `RDD[K,V]` 转换为 `PairRDDFunction`

### mapValues--遍历value
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - key 不变，只遍历遍历并更新value

### partitionBy--指定分组方式
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 根据指定的`分区器对象`对数据重新分区
- spark提供的`分区器对象`
    - HashPartitioner
        - **spark的默认分区器**
    - RangePartitioner
        - **需要数据能够比较大小**，才能取得范围
        - 一般与排序有关

    - 分区器的抽象类: `Partitioner`
        ```scala
        abstract class Partitioner extends Serializable {
          // 指定分区数量
          def numPartitions: Int
          // 根据key获取分区编号
          // 参数: 数据的key，返回值: 分区的编号
          def getPartition(key: Any): Int
        }
        ```
    - `HashPartitioner`
        - 分区规则
            - 将当前数据的key的 `hashcode` 与 分区数 进行取余操作
        - 源码分析
            ```scala
            class HashPartitioner(partitions: Int) extends Partitioner {
              require(partitions >= 0, s"Number of partitions ($partitions) cannot be negative.")

              // 分区数 = 传入的参数 = 手动设置的分区数
              def numPartitions: Int = partitions
              // 根据key，返回分区号
              def getPartition(key: Any): Int = key match {
                // 如果 key 是 null，返回 0
                case null => 0
                // 1. 如果 key 不是 null，计算 key 的分区
                case _ => Utils.nonNegativeMod(key.hashCode, numPartitions)
              }
            ```
            ```scala
            // Utils
            def nonNegativeMod(x: Int, mod: Int): Int = {
              // 2. 计算分区
              // key.hashCode % numPartitions，执行取余操作
              val rawMod = x % mod
              rawMod + (if (rawMod < 0) mod else 0)
            }
            ```
- 如果第二次分区的分区器和当前RDD的分区器一样如何处理 ?
    - 如果和当前分区器相同，不会再次执行分区器。会被跳过
    - 源码分析
        ```scala
        // PairRDDFunctions
        def partitionBy(partitioner: Partitioner): RDD[(K, V)] = self.withScope {
          if (keyClass.isArray && partitioner.isInstanceOf[HashPartitioner]) {
            throw new SparkException("HashPartitioner cannot partition array keys.")
          }
          // 1. == 会调用 equals 方法
          if (self.partitioner == Some(partitioner)) {
            // 3. 如果两个分区器相同，则直接返回当前 RDD，不做处理
            self
          } else {
            // 4. 如果不同，则创建新的RDD
            new ShuffledRDD[K, V, V](self, partitioner)
          }
        }
        ```
        ```scala
        // HashPartitioner
        override def equals(other: Any): Boolean = other match {
        case h: HashPartitioner =>
          // 2. 对于 hash 分区器，如果分区数相同，则两个分区器相同
          h.numPartitions == numPartitions
        case _ =>
          false
        }
        ```
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDHashPartitionBy.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDHashPartitionBy.scala)
    - 代码内容
        ```scala
        // 创建 3 个分区
        val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("d", 4)), 1)
        // 使用分区器，将数据重新分成两个区
        val rdd1: RDD[(String, Int)] = rdd.partitionBy(new HashPartitioner(2))
        ```

### 自定义分区器
[top](#catalog)
- 自定义步骤
    1. 继承抽象类 Partitioner
    1. 实现 `getPartition` 方法
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDCustomise.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDCustomise.scala)
    - 代码内容
        - 定义分区对象
            ```scala
            ```
        - 使用自定义分区
            ```scala
            val rdd: RDD[(String, String)] = sc.makeRDD(
            List(
                ("aaa", "msga1"), ("aaa", "msga2"), ("aaa", "msga3"), ("aaa", "msga4"),
                ("bbb", "msgb1"), ("bbb", "msgb2"),
                ("ccc", "msgc1"), ("ccc", "msgc2"), ("ccc", "msgc3")
            ),
            1
            )
            // 使用自定义分区
            val rdd2 = rdd.partitionBy(new MyPartitioner(4))
            .mapPartitionsWithIndex(
                (index, datas) => {
                datas.map(d => (index, d))
                }
            )
            rdd2.collect().foreach(println)
            ```

### reduceByKey
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 按照key对value做聚合
    - 在分组内会提前进行一次聚合，然后再执行 shuffle
- 包含 shuffle
- 可以在聚合后改变分区数
    - 因为聚合后，可能会产生数据倾斜
    - 有些分区的数据可能会减少或情况
    - 改变分区有利于资源分配
- <span style='color:red'>如果reduceByKey之后再做一个reduceByKey是没有意义的</span>
    - 因为数据已经分组，不需要在分组
    - spark会判断为无意义操作，不会导致新的阶段
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDReduceByKey.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDReduceByKey.scala)
    - 代码内容
        ```scala
        val rdd: RDD[(String, Int)] = sc.makeRDD(
          List(
            ("a", 1), ("b", 2), ("a", 3), ("b", 4), ("c", 2)
          )
        )

        // 按照 key 分组聚合
        val rdd1: RDD[(String, Int)] = rdd.reduceByKey(_+_)
        rdd1.collect().foreach(println)
        ```

### groupByKey
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 对key进行分组,
    - 返回一个 tuple: `(用于分组的key, 分组后相同key的value集合)`
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDGroupByKey.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDGroupByKey.scala)
    - 代码内容
        ```scala
        // 用 groupByKey实现分组求和
        val rdd: RDD[(String, Int)] = sc.makeRDD(
          List(
            ("a", 1), ("b", 2), ("a", 3), ("b", 4), ("c", 2)
          )
        )

        // 按 key 分组
        val rdd2: RDD[(String, Iterable[Int])] = rdd.groupByKey()
        // 求和
        rdd2.map {
          case (key, iter) => (key, iter.sum)
        }.collect().foreach(println)
        ```

### groupByKey与reduceByKey的异同
[top](#catalog)
- groupByKey、reduceByKey 都有 shuffle 操作
    - 即: **对一个分区的数据分组后，不能继续执行后续计算，需要等待其他分区的数据全部到达之后，才能执行后续计算**
- shuffle 操作时的数据量
    - groupByKey，全部数据
    - reduceByKey，聚合后的数据

- reduceByKey 的预聚合操作
    - 预聚合操作
        - 先在分区中做分组聚合，然后在执行 shuffle，降低了shuffle 的数据量
    - 因为有预聚，所以 **reduceByKey 的性能更高**

- 具体使用哪个，需要使用场景
    - 如果分组后不需要聚合，就只能用 **groupByKey**

### aggregateByKey
[top](#catalog)
- 功能
    - 分别指定分区内、分区间的计算规则
- 函数说明
    - 函数签名
        ```scala
        def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U,
            combOp: (U, U) => U): RDD[(K, U)]
        ```
    - `zeroValue` 表示计算的初始值
        - 类似于 scala 的 `floatLeft(x)(func)` 中的起始数据 `x`
    - `seqOp(U, V) => U` 表示**分区内**的计算规则，U 是前一个结果，V是这次迭代的数据
    - `combOp(U, U) => U` 表示**分区间**的计算规则，第一个 U 是前一个结果，第二个 U 是这次迭代的数据

- 使用场景: 分区内、分区间的计算规则不同
    - 实现方式: 函数的柯里化

- **如果分区内、分区间的计算规则相同，相当于 reduceByKey**
- 简化写法
    ```scala
    // 1. 用 aggregateByKey 的基本写法
    rdd.aggregateByKey(0)(
      (x, y) => x + y,
      (x, y) => x + y
    ).collect().foreach(println)

    // 2. 简化写法
    rdd.aggregateByKey(0)(
      _+_,
      _+_
    ).collect().foreach(println)
    ```
- 示例: 先获取各分区内**各key**的最大值，然后在分区间对这些最大值求和
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDAggregateByKey.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDAggregateByKey.scala)

    - 代码内容
        ```scala
        // 将分区内相同key取最大值，分区间相同key求和
        // 分区预期
        // 分区1: ("a", 2), ("c", 3)
        // 分区2: ("b", 4), ("c", 6)
        // 计算结果
        // ("a", 2), ("c", 9), ("b", 4)
        val rdd: RDD[(String, Int)] = sc.makeRDD(
          List(
            ("a", 1), ("a", 2), ("c", 3),
            ("b", 4), ("c", 2), ("c", 6)
          ),
          2
        )

        // 该计算的本质问题: 分区内、分区间的计算规则不同
        val rdd2: RDD[(String, Int)] = rdd.aggregateByKey(0)(
          // 分区内: 相同key取最大值
          (x, y) => math.max(x, y),
          // 分区间: 相同key求和
          (x, y) => x + y
        )

        rdd2.collect().foreach(println)
        ```

### foldByKey
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 当**分区内、分区间**的**计算规则相同**时，可以使用 `foldByKey` 替代 `aggregateByKey`
- 示例: 分组求和
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDFoldByKey.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDFoldByKey.scala)

    - 代码内容
        ```scala
        // 分区预期
        // 分区1: ("a", 2), ("c", 3)
        // 分区2: ("b", 4), ("c", 6)
        // 计算结果
        // ("a", 2), ("c", 9), ("b", 4)
        val rdd: RDD[(String, Int)] = sc.makeRDD(
          List(
            ("a", 1), ("a", 2), ("c", 3),
            ("b", 4), ("c", 2), ("c", 6)
          ),
          2
        )

        // aggregateByKey 实现
        // 1. 用 aggregateByKey 的基本写法
        rdd.aggregateByKey(0)(
          (x, y) => x + y,
          (x, y) => x + y
        ).collect().foreach(println)

        // 2. 简化写法
        rdd.aggregateByKey(0)(
          _+_,
          _+_
        ).collect().foreach(println)

        // foldByKey 实现
        // 3. 分区内、分区间计算规则相同，使用 foldByKey 简化
        rdd.foldByKey(0)(_+_).collect().foreach(println)
        ```

### combineByKey
[top](#catalog)
- 功能
    - 与 aggregateByKey 类似，可以指定分区内、分区间的计算方式
    - 需要指定初始数据的结构
- 函数说明
    - 函数签名
        ```scala
        def combineByKey[C](
          createCombiner: V => C,
          mergeValue: (C, V) => C,
          mergeCombiners: (C, C) => C): RDD[(K, C)]
        ```
    - 参数
        - `createCombiner: V => C`，表示将计算的第一个数据如何转换结构
        - `mergeValue: (C, V) => C`，表示分区内的计算规则
        - `mergeCombiners: (C, C) => C): RDD[(K, C)]`，分区间的计算规则
- 适用场景
    - 输入的数据结构不符合计算规则

- 示例: 分组求平均值
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDCombineByKey.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDCombineByKey.scala)
    - 代码内容
        ```scala
        // 求每个key的平均值
        // 分组
        // ("a", 88), ("b", 95), ("a", 91)
        // ("b", 93), ("a", 95), ("b", 98)
        // 计算方式
        // 88 -> (88, 1) + 91 -> (179, 2) + 95 -> (274, 3)
        // 然后计算: 274 / 3
        // 为了能够正常计算，需要在第一次计算之前，调整数据格式:
        // (a, 88) -> (a, (88, 1))
        val rdd: RDD[(String, Int)] = sc.makeRDD(
          List(
            ("a", 88), ("b", 95), ("a", 91),
            ("b", 93), ("a", 95), ("b", 98)
          ),
          2
        )

        // 使用 combineByKey 实现
        val rdd2: RDD[(String, (Int, Int))] = rdd.combineByKey(
          v => (v, 1),
          // 分区内规则: 这里 t 表示 (88，1) 这类的格式
          (t: (Int, Int), v) => (t._1 + v, t._2 + 1),
          // 分区间规则: 即多个 (总计, 数量) 之间的求和
          (t1: (Int, Int), t2: (Int, Int)) => (t1._1 + t2._1, t1._2 + t2._2)
        )
        // 计算均值
        rdd2.map{
          case (key, (total, count))=>{
            (key, total/count)
          }
        }.collect().foreach(println)
        ```

## reduceByKey、aggregateByKey、combineByKey、foldByKey的异同
[top](#catalog)
- <span style='color:red'>4 种算子的 都是在避免 groupByKey 的 shuffle 落盘操作导致的性能问题</span>
- 4 种算子都可以设置分区数，在底层执行 shuffle 时，可以重新组合数据
- 4 种算子的**底层逻辑相同**，都是 `combineByKeyWithClassTag`，只是参数不同
- `reduceByKey`
    - 不会处理第一个 value
    - **分区内、分区间的计算规则相同**
    - 源码分析
        ```scala
        // PairRDDFunctions
        combineByKeyWithClassTag[V]((v: V) => v, func, func, partitioner)
        ```
- `aggregateByKey`
    - 初始值和第一个value使用分区内计算规则进行计算
    - 源码分析
        ```scala
          def aggregateByKey[U: ClassTag](zeroValue: U, partitioner: Partitioner)(seqOp: (U, V) => U,
              combOp: (U, U) => U): RDD[(K, U)] = self.withScope {
            // Serialize the zero value to a byte array so that we can get a new clone of it on each key
            val zeroBuffer = SparkEnv.get.serializer.newInstance().serialize(zeroValue)
            val zeroArray = new Array[Byte](zeroBuffer.limit)
            zeroBuffer.get(zeroArray)

            lazy val cachedSerializer = SparkEnv.get.serializer.newInstance()
            val createZero = () => cachedSerializer.deserialize[U](ByteBuffer.wrap(zeroArray))

            val cleanedSeqOp = self.context.clean(seqOp)
            // 准备第一个值
            combineByKeyWithClassTag[U]((v: V) => cleanedSeqOp(createZero(), v),
              cleanedSeqOp, combOp, partitioner)
          }
        ```
- `foldByKey`
    - 初始值和第一个value使用分区内计算规则进行计算
    - 源码分析
        ```scala
          def foldByKey(
              zeroValue: V,
              partitioner: Partitioner)(func: (V, V) => V): RDD[(K, V)] = self.withScope {
            val zeroBuffer = SparkEnv.get.serializer.newInstance().serialize(zeroValue)
            val zeroArray = new Array[Byte](zeroBuffer.limit)
            zeroBuffer.get(zeroArray)

            // When deserializing, use a lazy val to create just one instance of the serializer per task
            lazy val cachedSerializer = SparkEnv.get.serializer.newInstance()
            val createZero = () => cachedSerializer.deserialize[V](ByteBuffer.wrap(zeroArray))

            val cleanedFunc = self.context.clean(func)
            // 准备第一个值
            combineByKeyWithClassTag[V]((v: V) => cleanedFunc(createZero(), v),
              // 分区内、分区间的计算规则相同，都使用 cleanedFunc 进行计算
              cleanedFunc, cleanedFunc, partitioner)
          }
        ```
- `combineByKey`
    - 没有初始值，但是会对分组的第一个数据调用 `createCombiner` 来修改数据格式

### sortByKey
[top](#catalog)
- 功能
    - 用 key 排序
    - 可以对自定义类排序，但是**必须实现** `Ordered`、`Serializable`
- 函数说明
    - 函数签名
        - [](?????)
    - 参数
        - true = 升序，false = 降序
        - 排序后的分区数
- 排序后可能会有数据倾斜
- 示例: 对自定义类排序
    - 参考代码
        - [src\spark-learn\spark-core\src\main\scala\com\ljs\learn\sparkcore\rdd\operator\kv\RDDSortByKey.scala](src\spark-learn\spark-core\src\main\scala\com\ljs\learn\sparkcore\rdd\operator\kv\RDDSortByKey.scala)
    - 代码内容
        1. 默认对 key 排序
            ```scala
            val rdd: RDD[(String, Int)] = sc.makeRDD(
              List(
                ("a", 1), ("c", 3), ("b", 2),
              ),
              2
            )

            rdd.sortByKey(true).collect().foreach(println)
            ```
        2. 对自定义类排序
            - 自定义类
                ```scala
                // 自定义key
                // 需要实现 Ordered、Serializable
                class Person(pid:Int) extends Ordered[Person] with Serializable {
                  var id:Int = pid
                  // 设置排序方式
                  override def compare(that: Person): Int = {
                    if (id > that.id){
                      1
                    } else {
                      -1
                    }
                  }

                  override def toString: String = {
                    s"person_id:$id"
                  }
                }
                ```
            - 对自定义类排序
                ```scala
                val rdd2: RDD[(Person, Int)] = sc.makeRDD(
                  List(
                    (new Person(345), 1),
                    (new Person(123), 2),
                    (new Person(234), 3)
                  )
                )
                rdd2.sortByKey(true).collect().foreach(println)
                ```

### join
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 在类型为 `(K,V)`和 `(K,W)` 的RDD上调用，连接数据，并返回 `(K,(V,W))` 格式的数据
    - 如果某个 key 无法连接，会被忽略
    - 如果 key 有重复的，会进行多次连接
- join的问题
    - join 中包含 shuffle 和 笛卡尔积，性能不好，**尽量不要用**
    - 因为需要关联不同分区中的数据，所以需要有 shuffle
- 示例
    - ?????

### leftOutJoin--左联
[top](#catalog)
- 类似与sql的左联
- ?????

### rightOuterJoin--右联
[top](#catalog)
- 类似与sql的右联
- ?????

### cogroup
[top](#catalog)
- 函数签名
    - [](?????)
- 功能
    - 在类型为 `(K,V)`和 `(K,W)` 的RDD上调用，连接数据，并返回 `(K,(Iterable<V>, Iterable<W>))` 格式的数据
    - 会现将当前分区内数据分组，然后在进行左联 + 右联
        - ?????

- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDCogroup.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/operator/kv/RDDCogroup.scala)
    - 代码内容
        ```scala
        val rdd1: RDD[(String, Int)] = sc.makeRDD(
          List(("a", 1), ("b", 2), ("a", 3), ("c", 4), ("d", 5))
        )

        val rdd2: RDD[(String, Int)] = sc.makeRDD(
          List(("a", 4), ("b", 5), ("a", 6), ("c", 7), ("e", 8))
        )

        val rdd3: RDD[(String, (Iterable[Int], Iterable[Int]))] = rdd1.cogroup(rdd2)

        rdd3.collect().foreach(println)
        ```

## 如何选择改变分区的转换算子
[top](#catalog)
- 只关注于分区的多少
    - `filter --> coalesce`
        - 过滤之后使用 `coalesce` 缩减分区数据
    - `repartition`
        - 增加、减少分区
        - 通过 shuffle 来重新组合数据，需要注意性能
- 决定数据如何分类分区
    - `partitionBy`
    - `groupByKey(partitioner)`
- 只能决定数据如何分类，无法决定分区
    - groupBy

## 转换算子--实际应用
[top](#catalog)
- 统计出**每个省份**中，**点击数量**排名Top3 的 **广告**
- 数据格式 agent.log
    - 时间戳，省份，城市，用户，广告
    - 中间字段使用空格分隔
- 实现
    - 参考代码
        - [src\spark-learn\spark-core\src\main\scala\com\ljs\learn\sparkcore\rdd\operator\demo\TestDemoAgentLog.scala](src\spark-learn\spark-core\src\main\scala\com\ljs\learn\sparkcore\rdd\operator\demo\TestDemoAgentLog.scala)
    - 代码内容
        ```scala    val fileRDD = sc.textFile("./input/demo/agent.log")

        // - 统计出**每个省份****每个广告**被**点击数量**排名的Top3
        // - agent.log：时间戳，省份，城市，用户，广告，中间字段使用空格分隔
        // 每个省份 + 广告
        // (省份,广告) COUNT
        fileRDD.map(x => {
          val fields = x.split("\\s+")
          // ((省份, 广告), 数量)
          val a = ((fields(1), fields(4)), 1)
          a
        }).reduceByKey(_ + _)   // 以 (省份, 广告) 作为key进行分组求和
          .map {
            case ((p, a), c) => {   // 修改数据结构: (省份, (广告, 数量))
              (p, (a, c))
            }
          }
          .groupByKey()             // 根据省份分组
          .mapValues(               // 获取每个省份中 top3 的广告
            rows => {               // 通过 mapValues 只处理每个分组中的 List((广告, 数量))
              // 用数量为广告升序排序
              rows.toList.sortWith(
                (left, right) => left._2 > right._2
              ).take(3) // 获取 top3
            }
          )
          .collect()
          .foreach(println)
        ```

## RDD的两种去重方式--distinct、reduceByKey
[top](#catalog)
- 去重方式1: 使用转换算子: `rdd.distinct()`
- 去重方式2: 使用 `reduceByKey`
    - 参考 `distinct()` 的源码
    - 源码内容
        ```scala
        // abstract class RDD
        def distinct(): RDD[T] = withScope {
          distinct(partitions.length)
        }

        def distinct(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
          // 1. 将所有数据转换为 (x, null) 数据结构
          // 2. 将所有数据分组求和，(x, null) + (x, null) 仍然得到 (x, null)
          //    - 产生了去重的效果
          // 3. 从数据结构中取出 key
          map(x => (x, null)).reduceByKey((x, y) => x, numPartitions).map(_._1)
        }
        ```
    - 主要的实现方式
        - 利用 `(xxx, null)` 在聚合时，结果仍然为 `(xxx, null)` 的特性去重
        - 聚合后通过 `map`，只取出 key 完成去重操作

# RDD--行动算子
## 行动算子与转换算子的区别
[top](#catalog)
- 行动算子不会再产生新的RDD，或转换RDD，而是**触发作业的执行**
- 转换算子只负责创建逻辑，即功能的包装，不负责执行。由行动算子负责执行
- 行动算子执行后，可以获取到执行结果

## 行动算子
[top](#catalog)
- reduce
    - 聚集RDD中的所有元素，先聚合分区内数据，再聚合分区间数据
- collect
    - 以数组Array的形式，采集并返回数据集的所有元素
    - collect方法会将所有分区计算的结果拉取到当前结点的内存中，可能会出现内存溢出
- count
    - 返回数据集中的数量
- first
    - 返回RDD中的第一个元素
- take(N)
    - 获取前N个数据
- takeOrdered(N)
    - 排序后，返回获取前N个数据组成的数组
- sum 求和
    - 缺点: 计算结果是 Double 类型
- aggregate(初始值)(计算规则,计算规则)
    - 聚合
    - 会参与分区间、分区内的计算
    - 初始值会赋值给每一个分区内的计算，**以及分区间的计算**
        - 初始值一共会使用 `分区数 + 1` 次
- fold(初始值)(计算规则)
    - aggregate 的简化版 
    - 初始值会赋值给每一个分区内的计算，**以及分区间的计算**
        - 初始值一共会使用 `分区数 + 1` 次
- countByKey
    - 适用于 `(k, v)` 类型的数据
    - 计算每个key的数量
    - 返回一个map
- countByValue
    - 统计每个数据的数量，适用于普通数据
    - 返回一个map
- save
    - 方法
        - saveAsTextFile(path)，将数据集处理后的结保存为文本文件
        - saveAsObjectFile，
        - saveAsSequenceFile
            - 只能处理 `(k, v)` 类型的数据
    - 保存路径可以是本地路径，也可以是hdfs
    - 参考
        - /spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDSaveAs.scala

- foreach
    - 在不同的计算结点对各分区的数据进行分布式遍历
- 示例
    - 参考代码
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDBase.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDBase.scala)
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDForeach.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDForeach.scala)
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDSaveAs.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDSaveAs.scala)
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDTakeOrdered.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/actionop/RDDTakeOrdered.scala)

## 行动算子的作业执行原理
[top](#catalog)
- 行动算子如何执行作业
    - 行动算子在执行时，先产生 `Job` 对象，然后提交 `Job` 对象
- `collect()` 执行作业的流程分析
    1. `RDD`，在 `collect()` 内，
        ```scala
        def collect(): Array[T] = withScope {
          // sc 就是上下文环境对象 SparkContext
          // 1. 调用 runJob 开始执行作业
          val results = sc.runJob(this, (iter: Iterator[T]) => iter.toArray)
          Array.concat(results: _*)
        }
        ```
    2. `SparkContext`
        ```scala
        def runJob[T, U: ClassTag](
            rdd: RDD[T],
            func: (TaskContext, Iterator[T]) => U,
            partitions: Seq[Int],
            resultHandler: (Int, U) => Unit): Unit = {
          // 其他处理...
          // 2. 真正的执行任务
          dagScheduler.runJob(rdd, cleanedFunc, partitions, callSite, resultHandler, localProperties.get)
          progressBar.foreach(_.finishAll())
          rdd.doCheckpoint()
        }
        ```
    3. `DAGScheduler`
        ```scala
        def runJob[T, U](
            rdd: RDD[T],
            func: (TaskContext, Iterator[T]) => U,
            partitions: Seq[Int],
            callSite: CallSite,
            resultHandler: (Int, U) => Unit,
            properties: Properties): Unit = {
          val start = System.nanoTime
          // 3. 提交任务
          val waiter = submitJob(rdd, func, partitions, callSite, resultHandler, properties)
          // 其他操作...
        }

        def submitJob[T, U](
            rdd: RDD[T],
            func: (TaskContext, Iterator[T]) => U,
            partitions: Seq[Int],
            callSite: CallSite,
            resultHandler: (Int, U) => Unit,
            properties: Properties): JobWaiter[U] = {
          // 4. 提交操作，发送一个消息
          eventProcessLoop.post(JobSubmitted(
            jobId, rdd, func2, partitions.toArray, callSite, waiter,
            SerializationUtils.clone(properties)))
          waiter
        }

        // 5. 处理提交事件
        private[scheduler] def handleJobSubmitted(jobId: Int,
            finalRDD: RDD[_],
            func: (TaskContext, Iterator[_]) => _,
            partitions: Array[Int],
            callSite: CallSite,
            listener: JobListener,
            properties: Properties) {
          // 6. 创建 Job
          val job = new ActiveJob(jobId, finalStage, callSite, listener, properties)
          clearCacheLocs()
          logInfo("Got job %s (%s) with %d output partitions".format(
            job.jobId, callSite.shortForm, partitions.length))
          logInfo("Final stage: " + finalStage + " (" + finalStage.name + ")")
          logInfo("Parents of final stage: " + finalStage.parents)
          logInfo("Missing parents: " + getMissingParentStages(finalStage))

          val jobSubmissionTime = clock.getTimeMillis()
          jobIdToActiveJob(jobId) = job
          activeJobs += job
          finalStage.setActiveJob(job)
          val stageIds = jobIdToStageIds(jobId).toArray
          val stageInfos = stageIds.flatMap(id => stageIdToStage.get(id).map(_.latestInfo))
          // 7. 开始执行
          listenerBus.post(
            SparkListenerJobStart(job.jobId, jobSubmissionTime, stageInfos, properties))
          submitStage(finalStage)
        }
        ```

# 各算子特性
## 包含shuffle操作的算子
[top](#catalog)
- <span style='color:red'>包含 shuffle 的算子默认是有缓存的，为了提高效率</span>
- 转换算子--value型
    - groupBy
    - distance ?????
    - repartition

- 转换算子--kv型
    - groupByKey
    - reduceByKey
    - aggregateByKey
    - combineByKey
    - foldByKey
    - join
    - leftOutJoin
    - rightOuterJoin

## 会产生数据倾斜的算子
[top](#catalog)
- 转换算子--value型
    - groupBy
    - filter
    - distance ?????

- 转换算子--kv型
    - groupByKey
    - sortByKey

## 可能会产生性能问题的算子
[top](#catalog)
- mapPartitionsWithIndex
  - 因为会在内存中持有数据，直到所有处理完成
- mapPartitions
  - 因为会在内存中持有数据，直到所有处理完成
- glom
  - 将数据全部转换为内存数组，数据量大时，会有问题

# RDD序列化
[top](#catalog)
- 算子之外的的程序在Driver端执行，算子内的程序在 Executor 中执行
- 为了使闭包中使用的外部变量可以在 Executor 中使用，外部变量**必须可序列化**
- spark的闭包检测
    - spark的算子操作都是闭包，所以闭包有可能包含外部的变量
    - 如果包含外部变量，就一定要保证外部变量可序列化
    - spark在提交作业之前，会对闭包内的变量进行检测，检测能否序列化
- Spark2.0开始支持另外一种Kryo序列化机制
    - Kryo速度是Serializable的10倍
    - 当RDD在Shuffle数据的时候，简单数据类型、数组和字符串类型已经在Spark内部使用Kryo来序列化
- 使用Kryo序列化，需要继承 `Serializable` 接口，并在spark中进行注册额
    - ?????

# RDD依赖关系
[top](#catalog)
- 为什么需要依赖关系
    - **用来做容错处理**，包括
        - 重新计算
        - 恢复丢失的数据分区
    - 当某个节点的计算失败，框架会尝试重新计算
        - 因为 RDD 不保存数据，所以需要从头开始读取数据并计算
    - 为了能够重新计算，需要知道
        1. 数据的来源
        2. 数据执行了哪些运算

- 依赖关系本质就是:**RDD之间的关系**

- RDD 窄依赖: `org.apache.spark.OneToOneDependency`
    - 表示父RDD 的 **每一个** Partition 最多被一个子RDD的一个Partition使用
    - 即: RDD 之间是一对一关系，分区数据也一一对应，<span style='color:red'>不会 shuffle</span>
        ```scala
        class OneToOneDependency[T](rdd: RDD[T]) extends NarrowDependency
        ```
- RDD 宽依赖: `org.apache.spark.ShuffleDependency`
    - 表示 父RDD 的Partition 可能会被多个子RDD的Partition依赖，<span style='color:red'>会导致shuffle</span>
        ```scala
        class ShuffleDependency[K: ClassTag, V: ClassTag, C: ClassTag](...) extends Dependency[Product2[K, V]]
        ```
    - 依赖关系图
        - [依赖关系图](?????)
        - 数据被打乱并重新组合
        - 子RDD的分区1 可能会依赖于 父RDD的分区1和分区2
        - 子RDD的分区2 也可能会依赖于 父RDD的分区1和分区2
        - 此时的对应关系将变成 N:N 的依赖关系

- 宽依赖、窄依赖区别
    - 只看父RDD的每个Partition被多少个子RDD使用
    - 如果父RDD中出现某个Partition被多个子RDD使用，则为宽依赖
    - 如果父RDD中所有Partition都只被一个子RDD使用，则为窄依赖

- 示例
    1. 检查依赖关系
        - 参考代码
            - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/dependencies/RDDDebug.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/dependencies/RDDDebug.scala)
        - 代码内容
            - ?????
    2. 检查依赖对象
        - 参考代码
            - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/dependencies/RDDDependencies.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/dependencies/RDDDependencies.scala)
        - 代码内容
            ```scala
            // 1. 创建RDD: ParallelCollectionRDD
            val rdd1: RDD[String] = sc.makeRDD(
              List("aaa bbb ccc", "bbb ccc ddd"),
              2
            )
            println(rdd1.dependencies)
            // 输出:
            // List()

            // 2. 创建RDD: MapPartitionsRDD
            // 形成依赖关系: MapPartitionsRDD --> ParallelCollectionRDD
            val rdd2: RDD[String] = rdd1.flatMap(_.split(" "))
            println(rdd2.dependencies)
            /*
                输出:
                List(org.apache.spark.OneToOneDependency@77774571)

                一对一关系，两个RDD的分区数据一一对应，不会shuffle
            */

            // 3. 创建RDD: MapPartitionsRDD
            // 形成依赖关系: MapPartitionsRDD --> MapPartitionsRDD --> ParallelCollectionRDD
            val rdd3: RDD[(String, Int)] = rdd2.map((_, 1))
            println(rdd3.dependencies)
            /*
                输出:
                List(org.apache.spark.OneToOneDependency@59dc36d4)
                一对一关系，两个RDD的分区数据一一对应，不会shuffle
            */

            // 4. 创建RDD: ShuffledRDD
            // 形成依赖关系: ShuffledRDD --> MapPartitionsRDD --> MapPartitionsRDD --> ParallelCollectionRDD
            val rdd4: RDD[(String, Int)] = rdd3.reduceByKey(_+_)
            println(rdd4.dependencies)
            /*
                输出:
                    List(org.apache.spark.ShuffleDependency@45e9b12d)

                    数据被打乱并重新组合
                    New_RDD的分区1 可能会依赖与Old_RDD的分区1和分区2
                    New_RDD的分区2 也可能会依赖与Old_RDD的分区1和分区2
                    此时的对应关系将变成 N:N 的依赖关系
            */
            ```

# RDD的阶段划分
[top](#catalog)
- 只要运行一个 spark 程序，就会有一个阶段 stage
- RDD的阶段划分，表示一个完整的作业分被 shuffle 分成几个部分
    - shuffle 会将一个完整的流程分割成多段，每一段就是一个RDD的阶段
    - 因为 shuffle 会落盘，所以前一个阶段执行完后，下一个阶段必须重新读取数据才能继续执行，导致了阶段的划分
- 如果计算过程中存在 shuffle 操作，就应该划分阶段
    - task数 = task * 2
        - 前一个阶段有N个任务，后一个阶段也会有N个任务
    - 各阶段的读写
        - 前一个阶段会写数据，后一个阶段会读数据
- RDD的阶段划分最终会形成 DAG，即一个有向无环图
    - DAG 会记录 RDD 的转换过程和任务的阶段，可以在 spark 历史记录中查看
        - ?????
- spark中，`阶段数 = (shuffle) 宽依赖数 + 1`
    - 因为宽依赖中包含 shuffle，所以可以通过宽依赖数来判断
- 通过对阶段划分的分析，可以辅助提升性能
    - 本质是: 减少不必要的 shuffle 落盘操作

- 两种阶段类型
    - `ResultStage`
    - `ShuffleMapStage`
        - `ShuffleDependency` 会被封装为 `ShuffleMapStage`
        - 每一个 shuffle 操作都会将前面多个阶段封装成新的 `ShuffleMapStage`
            - 整体上一个阶段可以视为多层嵌套的结构

- 阶段划分的源码分析
    1. 执行 `rdd.reduceByKey(_+_)`，reduceByKey 内部包含 shuffle，会划分阶段
    2. `DAGScheduler`
        ```scala
        private[scheduler] def handleJobSubmitted(jobId: Int,
            finalRDD: RDD[_],
            func: (TaskContext, Iterator[_]) => _,
            partitions: Array[Int],
            callSite: CallSite,
            listener: JobListener,
            properties: Properties) {
          var finalStage: ResultStage = null
          try {
            // 1. 创建当前阶段
            finalStage = createResultStage(finalRDD, func, partitions, jobId, callSite)
          } catch {
            // 其他操作...
          }
          // 其他操作...
        }

        private def createResultStage(
            rdd: RDD[_],
            func: (TaskContext, Iterator[_]) => _,
            partitions: Array[Int],
            jobId: Int,
            callSite: CallSite): ResultStage = {
          checkBarrierStageWithDynamicAllocation(rdd)
          checkBarrierStageWithNumSlots(rdd)
          checkBarrierStageWithRDDChainPattern(rdd, partitions.toSet.size)
          // 2. 尝试获取父阶段
          val parents = getOrCreateParentStages(rdd, jobId)
          val id = nextStageId.getAndIncrement()
          // 8. 创建当前阶段，即不考虑其他因素，如果要运行程序就必须有一个阶段
          // 创建阶段时，将父阶段作为参数传入
          // 所以子阶段包含父阶段
          val stage = new ResultStage(id, rdd, func, partitions, parents, jobId, callSite)
          stageIdToStage(id) = stage
          updateJobIdStageIdMaps(jobId, stage)
          stage
        }

        // 2. 尝试获取父阶段
        private def getOrCreateParentStages(rdd: RDD[_], firstJobId: Int): List[Stage] = {
          // 4. 获取宽依赖
          getShuffleDependencies(rdd).map { shuffleDep =>
            // 5. 将宽依赖转换为父阶段
            getOrCreateShuffleMapStage(shuffleDep, firstJobId)
          }.toList
          // 7. 将赴阶段返回
        }

        // 4. 获取宽依赖，并转换
        private[scheduler] def getShuffleDependencies(
            rdd: RDD[_]): HashSet[ShuffleDependency[_, _, _]] = {
          // 创建父阶段集合
          val parents = new HashSet[ShuffleDependency[_, _, _]]
          val visited = new HashSet[RDD[_]]
          // 4.1 创建一个栈
          val waitingForVisit = new ArrayStack[RDD[_]]
          // 4.2 将 RDD 放入栈
          waitingForVisit.push(rdd)
          // 4.3 迭代栈，因为放入了一个rdd，所以一定会迭代一次
          while (waitingForVisit.nonEmpty) {
            // 4.4 取出rdd
            val toVisit = waitingForVisit.pop()
            if (!visited(toVisit)) {
              visited += toVisit
              // 4.5 如果 rdd 没有 被访问过，则获取该rdd的依赖关系
              toVisit.dependencies.foreach {
                case shuffleDep: ShuffleDependency[_, _, _] =>
                  // 4.6 如果是宽依赖，则将宽依赖对象添加到父阶段集合中
                  parents += shuffleDep
                case dependency =>
                  waitingForVisit.push(dependency.rdd)
              }
            }
          }
          // 4.7 返回父阶段
          parents
        }

        // 5. 将宽依赖转换为父阶段
        private def getOrCreateShuffleMapStage(
            shuffleDep: ShuffleDependency[_, _, _],
            firstJobId: Int): ShuffleMapStage = {
          shuffleIdToMapStage.get(shuffleDep.shuffleId) match {
            case Some(stage) =>
              stage

            // 5.1 如果父阶段不存在
            case None =>
              getMissingAncestorShuffleDependencies(shuffleDep.rdd).foreach { dep =>
                if (!shuffleIdToMapStage.contains(dep.shuffleId)) {
                  // 6. 用宽依赖对象创建父阶段
                  createShuffleMapStage(dep, firstJobId)
                }
              }
              createShuffleMapStage(shuffleDep, firstJobId)
          }
        }

        // 6. 用宽依赖对象创建父阶段
        def createShuffleMapStage(shuffleDep: ShuffleDependency[_, _, _], jobId: Int): ShuffleMapStage = {
          val rdd = shuffleDep.rdd
          checkBarrierStageWithDynamicAllocation(rdd)
          checkBarrierStageWithNumSlots(rdd)
          checkBarrierStageWithRDDChainPattern(rdd, rdd.getNumPartitions)
          val numTasks = rdd.partitions.length
          val parents = getOrCreateParentStages(rdd, jobId)
          val id = nextStageId.getAndIncrement()
          // 6. 用宽依赖创建父阶段
          val stage = new ShuffleMapStage(
            id, rdd, numTasks, parents, jobId, rdd.creationSite, shuffleDep, mapOutputTracker)
          // 其他处理...
        }
        ```

# RDD任务划分
[top](#catalog)
- 4 种任务

    |任务       |说明|
    |-----------|-|
    |Application|初始化一个 `SparkContext` 即生成一个Application|
    |Job        |一个 行动算子 就会生成一个Job（通过 `runJob` 方法生成）|
    |Stage      |Stage等于宽依赖(ShuffleDependency)的个数加1|
    |Task       |一个Stage阶段中，最后一个RDD的分区个数就是Task的个数|

- Application->Job->Stage->Task每一层都是 **1对n** 的关系
    - [关系图](?????)
    - 在代码中是反向包含的，形成一种嵌套关系
- 任务划分过程---源码分析
    1. `DAGScheduler`
        ```scala
        private[scheduler] def handleJobSubmitted(jobId: Int,
            finalRDD: RDD[_],
            func: (TaskContext, Iterator[_]) => _,
            partitions: Array[Int],
            callSite: CallSite,
            listener: JobListener,
            properties: Properties) {
          var finalStage: ResultStage = null
          try {
            // 1. 创建 Stage
            finalStage = createResultStage(finalRDD, func, partitions, jobId, callSite)
          } catch {
            // 其他操作...
          }
          // Job submitted, clear internal data.
          barrierJobIdToNumTasksCheckFailures.remove(jobId)

          // 2. 创建job
          val job = new ActiveJob(jobId, finalStage, callSite, listener, properties)
          clearCacheLocs()
          logInfo("Got job %s (%s) with %d output partitions".format(
            job.jobId, callSite.shortForm, partitions.length))
          logInfo("Final stage: " + finalStage + " (" + finalStage.name + ")")
          logInfo("Parents of final stage: " + finalStage.parents)
          logInfo("Missing parents: " + getMissingParentStages(finalStage))

          val jobSubmissionTime = clock.getTimeMillis()
          jobIdToActiveJob(jobId) = job
          activeJobs += job
          // 3. 将 job 放入 stage
          finalStage.setActiveJob(job)
          val stageIds = jobIdToStageIds(jobId).toArray
          val stageInfos = stageIds.flatMap(id => stageIdToStage.get(id).map(_.latestInfo))
          listenerBus.post(
            SparkListenerJobStart(job.jobId, jobSubmissionTime, stageInfos, properties))
          // 4. 提交 stage
          submitStage(finalStage)
        }
        ```
        ```scala
        private def submitStage(stage: Stage) {
          val jobId = activeJobForStage(stage)
          if (jobId.isDefined) {
            logDebug(s"submitStage($stage (name=${stage.name};" +
              s"jobs=${stage.jobIds.toSeq.sorted.mkString(",")}))")
            if (!waitingStages(stage) && !runningStages(stage) && !failedStages(stage)) {
              // 5. 判断当前阶段有没有父阶段
              val missing = getMissingParentStages(stage).sortBy(_.id)
              logDebug("missing: " + missing)
              if (missing.isEmpty) {
                logInfo("Submitting " + stage + " (" + stage.rdd + "), which has no missing parents")
                // 7. 如果当前阶段是第一个阶段,则提交任务
                submitMissingTasks(stage, jobId.get)
              } else {
                // 6. 如果有父阶段，则【递归调用当前方法】，提交所有父阶段
                // 任务阶段不会特别多，所以递归不太可能出问题
                for (parent <- missing) {
                  submitStage(parent)   // 递归调用当前方法
                }
                waitingStages += stage
              }
            }
          } else {
            abortStage(stage, "No active job for stage " + stage.id, None)
          }
        }
        ```
        ```scala
        // 7. 如果当前阶段是第一个阶段,则提交任务
        private def submitMissingTasks(stage: Stage, jobId: Int) {
            logDebug("submitMissingTasks(" + stage + ")")

            // 7.1 获取分区数量序列
            // 最终返回一个Range: (0, 1, 2,..., 最后一个rdd分区数-1)
            val partitionsToCompute: Seq[Int] = stage.findMissingPartitions()

            // Use the scheduling pool, job group, description, etc. from an ActiveJob associated
            // with this Stage
            val properties = jobIdToActiveJob(jobId).properties

            // 其他处理...

            // 7.2 获取任务集合
            val tasks: Seq[Task[_]] = try {
              val serializedTaskMetrics = closureSerializer.serialize(stage.latestInfo.taskMetrics).array()
              // 7.3 匹配参数中的 stage
              stage match {
                // 如果是 宽依赖
                case stage: ShuffleMapStage =>
                  stage.pendingPartitions.clear()
                  // 7.4 迭代所有【分区】，并创建任务: ShuffleMapTask
                  // 即 一个 分区 一个 task
                  partitionsToCompute.map { id =>
                    val locs = taskIdToLocations(id)
                    val part = partitions(id)   // 7.5 根据分区id获取分区
                    stage.pendingPartitions += id
                    // 7.6 创建任务: ShuffleMapTask，创建时需要包含 stage信息，和该 task 负责的 分区
                    new ShuffleMapTask(stage.id, stage.latestInfo.attemptNumber,
                      taskBinary, part, locs, properties, serializedTaskMetrics, Option(jobId),
                      Option(sc.applicationId), sc.applicationAttemptId, stage.rdd.isBarrier())
                  }

                // 如果是窄依赖
                case stage: ResultStage =>
                  partitionsToCompute.map { id =>
                    val p: Int = stage.partitions(id)
                    val part = partitions(p)
                    val locs = taskIdToLocations(id)
                    // 7.6 创建任务: ResultTask，创建时需要包含 stage信息，和该 task 负责的 分区
                    new ResultTask(stage.id, stage.latestInfo.attemptNumber,
                      taskBinary, part, locs, id, properties, serializedTaskMetrics,
                      Option(jobId), Option(sc.applicationId), sc.applicationAttemptId,
                      stage.rdd.isBarrier())
                  }
              }
            // 其他处理
          }
        ```
    2. `ShuffleMapStage`，获取分区数量序列
        ```scala
        // numPartitions 就是当前阶段中最后一个 rdd 的分区数
        val numPartitions = rdd.partitions.length

        // 获取分区数量序列
        override def findMissingPartitions(): Seq[Int] = {
          mapOutputTrackerMaster
            // 根据依赖查找分区数
            .findMissingPartitions(shuffleDep.shuffleId)
            // 如果未找到，则创建一个 Range
            .getOrElse(0 until numPartitions)
        }
        ```

# RDD持久化
## Cache缓存
[top](#catalog)
- 为什么需要缓存？
    - 主要目录: 防止 RDD 复用时导致的**重复计算**
    - RDD中是不保存数据的，如果多个RDD需要使用另一个RDD的数据，必须重新计算，效率低
    - 所以需要将一些重复性比较高，比较耗时的计算结果缓存起来，提高效率，防止**重复执行**
- 缓存方法
    ```scala
    rdd.cache()
    // 继续执行后续的算子
    rdd.map(...)
    ```
- spark 如何执行缓存？
    - RDD 通过Cache或者Persist方法缓存前面的计算结果
    - 默认情况下会把数据以**序列化的形式**缓存在**JVM的堆内存**中
- 什么时候执行缓存
    - 调用方法时，只是增加了 RDD 中的依赖，不会立刻缓存
        - cache操作不会切断、删除依赖关系，如果发生错误，需要**重新计算**
        - cache操作在行动算子执行后，会在依赖关系中增加和缓存相关的依赖
    - 行动算子启动任务后，执行到相应阶段后，RDD的计算结果才会被缓存在计算节点的内存中，供其他处理使用
- 缓存执行的细节
    - cache 会调用 persist 方法存储数据
        - persist方法在持久化数据时，会采用不同的存储级别对数据进行持久化操作
        - cache 的默认缓存级别为 `MEMORY_ONLY`，只存储在内存中
    - 默认的缓存是存储在Executor端的内存中，如果**内存不够用如何处理**？
        - executor 可以将内存的数据进行整理，然后**可以丢弃数据**
        - 即使发生了数据丢失，也只会重新计算当前 executor 上的数据，**不会全部重新计算**

- 源码分析
    ```scala
    abstract class RDD[T: ClassTag](){
      // 1. 缓存底层调用 persist，返回当前RDD
      def cache(): this.type = persist()
      // 2. 调用 persist 的重载方法
      // 存储级别为: MEMORY_ONLY，
      def persist(): this.type = persist(StorageLevel.MEMORY_ONLY)
    }
    ```

- 为什么 `cache()` 不返回也能使用
    - `def cache(): this.type = persist()` 该方法返回的就是原始的 RDD

- 参考
    - 不使用缓存
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/NoCache.scala](#src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/NoCache.scala)
    - 使用缓存
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/CheckPoint.scala](#src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/CheckPoint.scala)
    - 代码内容
        ```scala
        val rdd = sc.makeRDD(List(1,2,3,4))
        val rdd1 = rdd.map(x=>{
          println("rdd1 map")
          (x, 1)
        })

        // 缓存计算结果
        // 可以不创建新的RDD
        // rdd1.cache()
        // 可以在新的 RDD 基础上继续操作
        val rdd2: RDD[(Int, Int)] = rdd1.cache()
        println(rdd2.toDebugString) // 输出依赖关系
        // (12) MapPartitionsRDD[1] at map at CachePersist.scala:13 [Memory Deserialized 1x Replicated]
        // |   ParallelCollectionRDD[0] at makeRDD at CachePersist.scala:12 [Memory Deserialized 1x Replicated]

        // 第一次执行 map 计算
        println(rdd2.collect().mkString(","))
        println(rdd2.toDebugString) // 输出依赖关系
        // 在RDD依赖中添加了缓存依赖
        // (12) MapPartitionsRDD[1] at map at CachePersist.scala:13 [Memory Deserialized 1x Replicated]
        // |        CachedPartitions: 12; MemorySize: 352.0 B; ExternalBlockStoreSize: 0.0 B; DiskSize: 0.0 B
        // |   ParallelCollectionRDD[0] at makeRDD at CachePersist.scala:12 [Memory Deserialized 1x Replicated]

        // 使用缓存中的 map 结果
        rdd2.saveAsTextFile("./output/CachePersist")
        ```

## shuffle中的自动缓存
[top](#catalog)
- spark 会自动对一些 shuffle 操作的中间数据做持久化操作
- 缓存的目的
    - 避免当一个节点的 shuffle失败时，重新计算整个输入
- 开发时，如果需要重用数据，还是应该调用 `persist` 或 `cache`

## CheckPoint检查点--提升计算稳定性
[top](#catalog)
- 通过 `rdd.checkpoint()` 将数据保存到文件中
- 使用 checkpoint 功能之前，需要<span style='color:red'>设置检查点的保存目录</span>
    - 可以保存到: 本地目录、hdfs目录
    - 设置保存目录
        ```scala
        sparkContext.setCheckpointDir("保存目录")
        ```
- 应该将比较耗时，比较重要的数据保存到分布式文件系统中
- 当 RDD 的依赖过长是，容易出错，可以设置检查点。出错时从检查点重做，减少开销
- 检查点为了保证数据的准确行，**在执行时，会启动新的job，重新计算**一次
    - **当计算比较耗时时，性能还是不好**
- <span style='color:red'>为了提高性能，checkpoint 应该和 cache 一起使用</span>
    - 使用方法
        ```scala
        // 1. 创建缓存
        val cacheRDD: RDD[(Int, Int)] = rdd1.cache()

        // 2. 在缓存的基础上创建检查点，会直接从缓存中取数据
        cacheRDD.checkpoint()
        ```
    - 先创缓存，在缓存的基础上再创建检查点
    - 检查点**启动的新job**后，**不会重新计算**，会直接**从缓存中获取数据**
- <span style='color:red'>检查点会切断依赖关系</span>
    - 如果数据丢失，不会重新计算，而是从检查点开始重新计算
    - 因为检查点会将数据保存到文件中，数据相对比较安全，不容易丢失
    - 虽然切断了依赖关系，但是也同时生成了**新的数据源**

- 示例
    - 只使用 checkpoint
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/CheckPoint.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/CheckPoint.scala)
- 示例
    - 同时使用 checkpoint 和 cache
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/CacheAndCheckPoint.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/cache/CacheAndCheckPoint.scala)
    - 代码内容
        ```scala
        // 1. 设置检查点保存目录
        sc.setCheckpointDir("./output/checkpoint")

        val rdd = sc.makeRDD(List(1,2,3,4))
        val rdd1 = rdd.map(x=>{
          println("rdd1 map")
          (x, 1)
        })

        // 2. 创建缓存
        val cacheRDD: RDD[(Int, Int)] = rdd1.cache()

        // 3. 在缓存的基础上创建检查点，会直接从缓存中取数据
        cacheRDD.checkpoint()
        println(cacheRDD.toDebugString)
        // 在原始的 RDD 后添加了 CacheAndCheckPoint
        // (12) MapPartitionsRDD[1] at map at CacheAndCheckPoint.scala:15 [Memory         Deserialized 1xReplicated]
        // |   ParallelCollectionRDD[0] at makeRDD at CacheAndCheckPoint.scala:14         [MemoryDeserialized 1x Replicated]

        // 第一次执行 map 计算
        println(rdd1.collect().mkString(","))
        println(cacheRDD.toDebugString)
        // 在原始的 RDD 后添加了 CacheAndCheckPoint
        // (12) MapPartitionsRDD[1] at map at CacheAndCheckPoint.scala:15 [Memory         Deserialized 1x Replicated]
        // |        CachedPartitions: 12; MemorySize: 352.0 B;         ExternalBlockStoreSize: 0.0 B; DiskSize: 0.0 B
        // |   ReliableCheckpointRDD[2] at collect at CacheAndCheckPoint.scala:28         [Memory Deserialized 1x Replicated]
        //       依赖关系被切断

        // 第二次执行 map 计算
        println(rdd1.collect().mkString(" "))
        println(cacheRDD.toDebugString)
        // 在原始的 RDD 后添加了 CacheAndCheckPoint
        // (12) MapPartitionsRDD[1] at map at CacheAndCheckPoint.scala:15 [Memory         Deserialized 1x Replicated]
        // |        CachedPartitions: 12; MemorySize: 352.0 B;         ExternalBlockStoreSize: 0.0 B; DiskSize: 0.0 B
        // |   ReliableCheckpointRDD[2] at collect at CacheAndCheckPoint.scala:28         [Memory Deserialized 1x Replicated]
        //       依赖关系被切断
        ```

## CheckPoint什么时候执行--源码分析
[top](#catalog)
- checkpoint 在什么时候执行？
    - 在运行作业之后执行
- 启动时间--源码分析
  - `RDD`，调用`rdd.checkpoint()`时的动作
    ```scala
    def checkpoint(): Unit = RDDCheckpointData.synchronized {
      // 1. 如果没有通过 sc.setCheckpointDir 设置检查点目录，会抛出异常
      if (context.checkpointDir.isEmpty) {
        throw new SparkException("Checkpoint directory has not been set in the SparkContext")
      } else if (checkpointData.isEmpty) {
        // 2. 只封装了一个属性，没有做其他操作
        checkpointData = Some(new ReliableRDDCheckpointData(this))
      }
    }
    ```
  - `SparkContext`，由行动算子触发的 `runJob` 方法。**启动完普通job后，再执行 checkpoint**
    ```scala
    def runJob[T, U: ClassTag](
        rdd: RDD[T],
        func: (TaskContext, Iterator[T]) => U,
        partitions: Seq[Int],
        resultHandler: (Int, U) => Unit): Unit = {
      if (stopped.get()) {
        throw new IllegalStateException("SparkContext has been shutdown")
      }
      val callSite = getCallSite
      val cleanedFunc = clean(func)
      logInfo("Starting job: " + callSite.shortForm)
      if (conf.getBoolean("spark.logLineage", false)) {
        logInfo("RDD's recursive dependencies:\n" + rdd.toDebugString)
      }
      // 1. 先执行job
      dagScheduler.runJob(rdd, cleanedFunc, partitions, callSite, resultHandler, localProperties.get)
      progressBar.foreach(_.finishAll())
      // 2. 再执行 checkpoint
      rdd.doCheckpoint()
    }
    ```
- 为什么会checkpoint会创建新的job？
    1. SparkContext
        ```scala
        def runJob[T, U: ClassTag](
            rdd: RDD[T],
            func: (TaskContext, Iterator[T]) => U,
            partitions: Seq[Int],
            resultHandler: (Int, U) => Unit): Unit = {
          // 其他处理...
          // 0. 先执行job
          dagScheduler.runJob(rdd, cleanedFunc, partitions, callSite, resultHandler, localProperties.get)
          progressBar.foreach(_.finishAll())
          // 1. 再执行 checkpoint
          rdd.doCheckpoint()
        }
        ```
    2. `RDD`
        ```scala
        private[spark] def doCheckpoint(): Unit = {
          RDDOperationScope.withScope(sc, "checkpoint", allowNesting = false, ignoreParent = true) {
            if (!doCheckpointCalled) {
              doCheckpointCalled = true
              if (checkpointData.isDefined) { // 如何设置了检查点
                if (checkpointAllMarkedAncestors) {
                  // TODO We can collect all the RDDs that needs to be checkpointed, and then checkpoint
                  // them in parallel.
                  // Checkpoint parents first because our lineage will be truncated after we
                  // checkpoint ourselves
                  dependencies.foreach(_.rdd.doCheckpoint())
                }
                // 2. 执行检查点操作
                checkpointData.get.checkpoint()
              } else {
                dependencies.foreach(_.rdd.doCheckpoint())
              }
            }
          }
        }
        ```
    3. `RDDCheckpointData`
        ```scala
        final def checkpoint(): Unit = {
          // Guard against multiple threads checkpointing the same RDD by
          // atomically flipping the state of this RDDCheckpointData
          RDDCheckpointData.synchronized {
            if (cpState == Initialized) {
              cpState = CheckpointingInProgress
            } else {
              return
            }
          }

          val newRDD = doCheckpoint() // 3.执行检查点操作

          // Update our state and truncate the RDD lineage
          RDDCheckpointData.synchronized {
            cpRDD = Some(newRDD)
            cpState = Checkpointed
            rdd.markCheckpointed()
          }
        }
        ```
    4. `ReliableRDDCheckpointData`
        ```scala
        protected override def doCheckpoint(): CheckpointRDD[T] = {
          // 4. 创建新的 RDD，用于向文件输出检查点的数据
          val newRDD = ReliableCheckpointRDD.writeRDDToCheckpointDirectory(rdd, cpDir)
          // 其他处理
        }
        ```
    5. `ReliableCheckpointRDD`
        ```scala
        def writeRDDToCheckpointDirectory[T: ClassTag](
            originalRDD: RDD[T],
            checkpointDir: String,
            blockSize: Int = -1): ReliableCheckpointRDD[T] = {
          val checkpointStartTimeNs = System.nanoTime()

          val sc = originalRDD.sparkContext

          // Create the output path for the checkpoint
          val checkpointDirPath = new Path(checkpointDir)
          val fs = checkpointDirPath.getFileSystem(sc.hadoopConfiguration)
          if (!fs.mkdirs(checkpointDirPath)) {
            throw new SparkException(s"Failed to create checkpoint path $checkpointDirPath")
          }

          // Save to file, and reload it as an RDD
          val broadcastedConf = sc.broadcast(
            new SerializableConfiguration(sc.hadoopConfiguration))

          // 5. 执行一个新的任务，需要重新计算，所以需要 cache辅助
          sc.runJob(originalRDD,
            writePartitionToCheckpointFile[T](checkpointDirPath.toString, broadcastedConf) _)

          // 其他处理...
        }
        ```

## Cache与CheckPoint的区别
[top](#catalog)
- 区别

    ||Cache缓存|Checkpoint|
    |-|-|-|
    |是否切断依赖|不切断<br>只保存数据|切断|
    |存储位置|磁盘、内存等，可靠性低|HDFS等容错、高可用的文件系统，可靠性高|

# RDD文件读写
[top](#catalog)
- spark 支持的文件格式
    - text文件
    - csv文件
    - sequence文件
        - hadoop用来存储二进制 k-v 对的平面文件
    - object文件
        - java序列化数据文件
- spark 支持的文件系统
    - 本地文件系统
    - HDFS
    - HBASE
    - 数据库
- 文件的读写方法
    - `text` 文本文件
        ```scala
        // 写
        rdd.saveAsTextFile("文件路径")
        // 读
        sc.textFile("文件路径")
        ```
    - `object` 文件。读取时，需要指定数据的泛型，需要与保存时的泛型相同
        ```scala
        // 写
        rdd.saveAsObjectFile("文件路径")
        // 读，需要标注泛型的类型
        sc.objectFile[String]("文件路径")
        ```
    - `sequenceFile` 文件。读取时，需要指定数据的泛型，需要与保存时的泛型相同
        ```scala
        // 写
        rdd.saveAsSequenceFile("文件路径")
        // 读，需要标注泛型的类型
        sc.sequenceFile[String, Int]("文件路径")
        ```
- 示例
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/file/ReadAndWrite.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/rdd/file/ReadAndWrite.scala)
    - 代码内容
        ```scala
        val rdd: RDD[String] = sc.makeRDD(List("aaa","bbb", "ccc", "ddd"), 2)

        // 1.1 text文件保存
        rdd.map(_+"_xxxx").saveAsTextFile("./output/ReadAndWrite/files01")
        // 1.2 text文件读取
        sc.textFile("./output/ReadAndWrite/files01/part*")
            .collect()
            .foreach(println)

        // 2.1 object 文件保存
        rdd.map(_+"_yyy").saveAsObjectFile("./output/ReadAndWrite/files02")
        // 2.2 object 文件读取
        sc.objectFile[String]("./output/ReadAndWrite/files02/part*")
          .collect()
          .foreach(println)

        // 3.1 seq 文件保存
        rdd.map((_, 1)).saveAsSequenceFile("./output/ReadAndWrite/files03")
        // 3.2 seq 文件读取
        sc.sequenceFile[String, Int]("./output/ReadAndWrite/files03/part*")
          .collect()
          .foreach(println)
        ```

# Accumulator--累加器
## 累加器说明
[top](#catalog)
- 累加器: 分布式共享**只写**变量
- 累加器的功能可以理解为
    - 数值的增加
    - 数据的累加、累积
- 适用场景
    - 替换某些包含 shuffle 的算子
        - 如reduce只进行求和，可以替换为累加器，避免 shuffle 导致的性能问题

## 累加器的基本使用方法
[top](#catalog)
- 不使用累加器求和
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/acc/UnuseAcctoReduce.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/acc/UnuseAcctoReduce.scala)

- 普通变量**无法**在子算子的闭包中做累加
    - 因为普通变量可以序列化，但是**无法返回 driver 端**
    - 示例
        ```scala
        var sum = 0
        rdd.foreach(
          c=>{
            // 任务启动后，会将 sum 序列化并传给 executor
            sum += c  // 此处的 sum 是 executor 端的数据
            // 计算后，spark无法将 sum 返回给 driver，所以无法更新 driver 端的数据
          }
        )
        ```

- 累加器的使用流程
    1. 创建累加器
    2. 注册（自定义）累加器
    3. 在算子中使用累加器
    4. 从累加器中获取结果

- spark内的执行流程
    1. 将累加器变量注册到spark中
    2. 执行计算时，spark会将累加器发送到executor
    3. 计算完成后，executor 会将累加器的计算结果返回到driver端
    4. driver端获取到**多个累加器的结果**，然后两两**合并的得到执行结果**

- 使用累加器求和
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/acc/Acc.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/acc/Acc.scala)
    - 代码内容
        ```scala
        // 2. 使用累加器完成数据的累加
        // 声明累加器变量
        val sum02: LongAccumulator = sc.longAccumulator("sum02")

        // 使用累加器求和
        rdd.foreach(
          c=>{
            sum02.add(c)
          }
        )
        // 获取累加器的结果
        println(s"sum02=${sum02.value}")
        ```

## 自定义累加器
[top](#catalog)
- 参考
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/acc/AccCustomise.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/acc/AccCustomise.scala)

- 定义累加器
    ```scala
      // 自定义累加器
    // 1. 继承 AccumulatorV2，定义泛型 `[IN, OUT]`
    //  IN 累加器的输入值的类型
    //  OUT 累加器的输出值的类型
    // 统计单词数量
    class WordCountAccumulator extends AccumulatorV2[String, mutable.Map[String,   Int]] {
      // 创建存储 wordcount 的集合
      val wcMap = mutable.Map[String, Int]()

      // 判断累加器是否为初始化状态
      override def isZero: Boolean = {
        wcMap.isEmpty
      }

      // 复制累加器
      override def copy(): AccumulatorV2[String, mutable.Map[String, Int]] = {
        new WordCountAccumulator
      }

      // 重置累加器
      override def reset(): Unit = {
        // 清空map
        wcMap.clear()
      }

      // 向累加器中增加数据
      override def add(v: String): Unit = {
        wcMap.update(v, wcMap.getOrElse(v, 0) + 1)
      }

      // 由 driver 端调用，合并当前和其他累加器
      override def merge(other: AccumulatorV2[String, mutable.Map[String, Int]]):   Unit = {
        // 以当前累加器为初始化值，遍历其他的累加器
        other.value.foldLeft(wcMap) {
          case (map, (k, v)) => {
            map(k) = map.getOrElse(k, 0) + v
            map
          }
        }
      }

      // 返回累加器的值
      override def value: mutable.Map[String, Int] = {
        wcMap
      }
    }
    ```

- 使用自定义累加器
    ```scala
    val rdd = sc.makeRDD(
      List("aaa bbb ccc", "bbb ccc ddd")
    )

    // 1. 创建累加器
    val acc = new WordCountAccumulator
    // 2. 注册累加器
    sc.register(acc)
    // 3. 使用累加器
    rdd.flatMap(_.split(" ")).foreach(
      word => {
        acc.add(word)
      }
    )

    // 4. 获取累加器的结果
    println(acc.value)
    ```

## 累加器的执行原理
[top](#catalog)
- 累加器中: `isZero`、`copy`、`reset` 的调用
    - 这三个方法在哪里被调用？
        - 在 `AccumulatorV2.writeReplace` 方法内，会调用 isZero、copy、reset
        - 这三个方法是连续调用的，调用顺序:
            1. copy
            2. reset
            3. isZero
        - 在**对象序列化时**，java 会调用 `AccumulatorV2.writeReplace` 方法
        - 源码分析
            ```scala
            // AccumulatorV2
            // Called by Java when serializing an object
            final protected def writeReplace(): Any = {
              if (atDriverSide) {
                if (!isRegistered) {
                  throw new UnsupportedOperationException(
                    "Accumulator must be registered before send to executor")
                }
                // 1. 创建并初始化对象
                val copyAcc = copyAndReset()
                // 2. 调用 `isZero`，检查是否为空
                assert(copyAcc.isZero, "copyAndReset must return a zero value copy")
                // 其他处理
            }

            def copyAndReset(): AccumulatorV2[IN, OUT] = {
              // 1.1 调用 copy，创建一个新的对象
              val copyAcc = copy()
              // 1.2 调用 `reset`，重置这个对象
              copyAcc.reset()
              copyAcc
            }
            ```
    - 什么时候执行序列化 ?
        - 在 driver 端将累加器发送到 executor 端时，会触发序列化
        - 源码分析: 检查对象是否存在 `writeReplace` 方法
            ```scala
            // RDD
            def foreach(f: T => Unit): Unit = withScope {
              // 1. 每个行动算子开始执行前，都需要分析闭包中的变量，并执行序列化
              val cleanF = sc.clean(f)
              sc.runJob(this, (iter: Iterator[T]) => iter.foreach(cleanF))
            }
            ```
            ```scala
            // SparkContext
            private[spark] def clean[F <: AnyRef](f: F, checkSerializable: Boolean = true): F = {
              // 2. 分析闭包
              ClosureCleaner.clean(f, checkSerializable)
              f
            }
            ```
            ```scala
            // ClosureCleaner
            def clean(
                closure: AnyRef,
                checkSerializable: Boolean = true,
                cleanTransitively: Boolean = true): Unit = {
              // 2. 分析闭包
              clean(closure, checkSerializable, cleanTransitively, Map.empty)
            }

            private def clean(
                func: AnyRef,
                checkSerializable: Boolean,
                cleanTransitively: Boolean,
                accessedFields: Map[Class[_], Set[String]]): Unit = {
                // 其他处理...

              if (checkSerializable) {
                // 3. 如果需要序列化，需要确保可以序列化
                ensureSerializable(func)
              }
            }

            private def ensureSerializable(func: AnyRef) {
              try {
                if (SparkEnv.get != null) {
                  // 4. 调用 serialize() 方法来序列化
                  SparkEnv.get.closureSerializer.newInstance().serialize(func)
                }
              } catch {
                case ex: Exception => throw new SparkException("Task not serializable", ex)
              }
            }
            ```
            ```scala
            // JavaSerializerInstance

            override def serialize[T: ClassTag](t: T): ByteBuffer = {
              val bos = new ByteBufferOutputStream()
              val out = serializeStream(bos)
              // 5. 创建输出流，并写出需要序列化的对象
              out.writeObject(t)
              out.close()
              bos.toByteBuffer
            }
            ```
            ```scala
            // JavaSerializationStream
            def writeObject[T: ClassTag](t: T): SerializationStream = {
              try {
                // 5. 创建输出流，并写出需要序列化的对象
                objOut.writeObject(t)
              } catch {
                // 其他处理...
            }
            ```
            ```java
            // java.io.ObjectOutputStream
            public final void writeObject(Object obj) throws IOException {
                if (enableOverride) {
                    writeObjectOverride(obj);
                    return;
                }
                try {
                    // 6. 向外写出
                    writeObject0(obj, false);
               // 其他处理...
            }

            private void writeObject0(Object obj, boolean unshared)
                    throws IOException
            {
                try {
                    // 其他处理...

                    for (;;) {
                        // REMIND: skip this check for strings/arrays?
                        Class<?> repCl;
                        desc = ObjectStreamClass.lookup(cl, true);
                        // 7. 检查对象是否存在 `writeReplace` 方法
                        if (!desc.hasWriteReplaceMethod() ||
                            // 10. 如果有，则通过 `invokeWriteReplace` 方法来调用 `writeReplace`
                            (obj = desc.invokeWriteReplace(obj)) == null ||
                            (repCl = obj.getClass()) == cl)
                        {
                            break;
                        }
                        cl = repCl;
                    }
                // 其他处理...
            }
            ```
            ```java
            // ObjectStreamClass
            boolean hasWriteReplaceMethod() {
                requireInitialized();
                // 8. 返回 `writeReplace` 方法是否存在
                return (writeReplaceMethod != null);
            }

            private ObjectStreamClass(final Class<?> cl) {
                // 其他处理...
                // 9. 通过反射获取方法对象
                writeReplaceMethod = getInheritableMethod(
                    cl, "writeReplace", null, Object.class);
            }
            ```
- 什么时候合并？**计算任务完成之后的处理，由 driver 负责合并累加器**
  ```scala
    // DAGScheduler
    // 1. 计算任务完成之后的处理
    private[scheduler] def handleTaskCompletion(event: CompletionEvent) {
      // 其他处理...
      event.reason match {
        case Success =>
          task match {
            case rt: ResultTask[_, _] =>
              val resultStage = stage.asInstanceOf[ResultStage]
              resultStage.activeJob match {
                case Some(job) =>
                  // Only update the accumulator once for each result task.
                  if (!job.finished(rt.outputId)) {
                    updateAccumulators(event) // 2. 更新累加器
                  }
                case None => // Ignore update if task's job has finished.
              }
            case _ =>
              updateAccumulators(event)
          }
        case _: ExceptionFailure | _: TaskKilled => updateAccumulators(event)
        case _ =>
      }
      postTaskEnd(event)

    }
    ```
    ```scala
    // DAGScheduler
    private def updateAccumulators(event: CompletionEvent): Unit = {
      val task = event.task
      val stage = stageIdToStage(task.stageId)

      event.accumUpdates.foreach { updates =>
        val id = updates.id
        try {
          // Find the corresponding accumulator on the driver and update it
          val acc: AccumulatorV2[Any, Any] = AccumulatorContext.get(id) match {
            case Some(accum) => accum.asInstanceOf[AccumulatorV2[Any, Any]]
            case None =>
              throw new SparkException(s"attempted to access non-existent accumulator $id")
          }
          // 调用 merge，【合并】各 executor的结果
          acc.merge(updates.asInstanceOf[AccumulatorV2[Any, Any]])
          // To avoid UI cruft, ignore cases where value wasn't updated
          if (acc.name.isDefined && !updates.isZero) {
            stage.latestInfo.accumulables(id) = acc.toInfo(None, Some(acc.value))
            event.taskInfo.setAccumulables(
              acc.toInfo(Some(updates.value), Some(acc.value)) +: event.taskInfo.accumulables)
          }
        } catch {
          case NonFatal(e) =>
            // Log the class name to make it easy to find the bad implementation
            val accumClassName = AccumulatorContext.get(id) match {
              case Some(accum) => accum.getClass.getName
              case None => "Unknown class"
            }
            logError(
              s"Failed to update accumulator $id ($accumClassName) for task ${task.partitionId}",
              e)
        }
      }
    }
    ```

# Broadcast--广播变量
[top](#catalog)
- 广播变量：分布式共享只读变量
- 广播变量的作用
    - 为了解决 join 的性能问题，可以将数据独立出来，防止shuffle
    - 数据独立出来后，会导致数据在**每一个task中都复制一份**
        - spark对数据的默认操作
            - 算子的闭包中的数据，通过序列化复制到每一个 task
        - 当 executor 中有多个 task时
            1. 每个 task 都有一个数据副本
            2. **会产生大量的冗余**
            3. 如果独立数据比较大，会影响性能
    - 为了解决task中的冗余副本，可以使用`广播变量`
        - 将数据保存到 executor 的内存中
        - 所有 task 共享一份数据

- 使用广播变量后，task获取数据的流程
    1. 向 executorA 的内存中获取数据
    2. 如果内存中没有，则从 driver 拉取数据
    3. 将数据返回给 task，task在将数据返回给 executorA
    4. 之后其他task再访问数据时，直接访问 task 内存中的数据
    5. 如果另一个 executorB 也需要相同的数据，但是数据量比较大
        - 检查同一个机架上的 executorA 是否获取过数据
            - 如果 executorA 有，则从 executorA 中拉取数据，减少传输时间
            - 如果没有，只能从 driver 拉取
        - 直接从driver获取，可能会消耗更多的时间
- task 获取变量的流程图
    - [流程图](?????)
- 示例
    1. join的性能问题
        - 参考
            - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/broadcast/NoBCtoJoin.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/broadcast/NoBCtoJoin.scala)
        - 代码内容
            ```scala
            val rdd1 = sc.makeRDD(List(("a",1), ("b",2), ("c",3) ))
            val rdd2 = sc.makeRDD(List(("a",1), ("b",2), ("c",3) ))

            // join会有笛卡尔乘积效果，数据量会急剧增多
            // 还包含shuffle操作，性能会非常低
            val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)
            joinRDD.collect().foreach(println)
            ```
    2. 将可序列化的数据独立出来，在算子的闭包中使用
        - **独立数据的容量比较大时，会消耗性能**
        - 参考
            - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/broadcast/ParamToJoin.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/broadcast/ParamToJoin.scala)
        - 代码内容
            ```scala
            // 1. 不使用 两个 rdd 来完成连接，来避免 shuffle
            val rdd1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))
            val list = List(("a", 1), ("b", 2), ("c", 3))

            // 连接的本质
            // ("a", 1) --> ("a", (1, 4))

            // 2. 在map中遍历 list
            val rdd2: RDD[(String, (Int, Int))] = rdd1.map {
              case (word, c1) => {
                var c2 = 0
                for ((w, v) <- list if w == word) {
                  c2 = v
                }
                (word, (c1, c2))
              }
            }

            rdd2.collect().foreach(println)
            ```
    3. 使用广播变量**替代独立数据**
        - 参考
            - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/broadcast/BCToJoin.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/broadcast/BCToJoin.scala)
        - 代码内容
            ```scala
            // 1. 不使用 两个 rdd 来完成连接，来避免 shuffle
            val rdd1 = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))
            val list = List(("a", 1), ("b", 2), ("c", 3))

            // 创建广播变量
            val bcList: Broadcast[List[(String, Int)]] = sc.broadcast(list)

            val rdd2: RDD[(String, (Int, Int))] = rdd1.map {
              case (word, c1) => {
                var c2 = 0
                // 通过 value 属性，获取广播变量中的数据
                for ((k, v) <- bcList.value if word == k) {
                  c2 = v
                }

                (word, (c1, c2))
              }
            }

            rdd2.collect().foreach(println)
            ```

[top](#catalog)
