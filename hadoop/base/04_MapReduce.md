<span id="catalog"></span>
- [基本使用](#基本使用)
- [hadoop序列化](#hadoop序列化)
- [MR框架原理](#MR框架原理)
    - [可以自定义的部分](#可以自定义的部分)
    - [InputFormat数据输入](#inputformat数据输入)
        - [切片与MapTask并行度决定机制](#切片与maptask并行度决定机制)
        - [Job提交流程](#job提交流程)
        - [切片源码解析](#切片源码解析)
        - FileInputFormat
            - [切片机制](#fileinputformat切片机制)
            - [实现类](#fileinputformat实现类)
    - [MR工作流程](#mr工作流程)
    - [shuffle机制](#shuffle机制)
        - [partitioner分区](#partitioner分区)
        - [WritableComparable排序](#writablecomparable排序)
            - [基本概念](#基本概念)
            - [排序的分类](#排序的分类)
            - [自定义排序WritableComparable](#自定义排序writablecomparable)
            - [区内排序](#区内排序)
        - [Combiner合并](#combiner合并)

# 基本使用
[top](#catalog)
* MapReduce定义
    * 一个分布式运算程序的编程框架，是开发基于Hadoop的**数据分析应用**的核心框架
    * 核心功能：将**业务逻辑代码**和**自带默认组件**做和成一个完整的**分布式运算程序**，并发的运行在Hadoop集群上

* MapReduce优缺点
    * 优点
        1. MapReduce易于编程
            * 简单的实现一些接口，就可以完成一个分布式程序
        2. 良好的扩展性
            * 通过增加机器来扩展计算能力
        3. 高容错性
            * 可运行在廉价的PC上
        4. 适合PB级以上的海量数据的离线处理
    * 缺点
        1. 不擅长实时计算
            * 无法在秒级内返回结果
        2. 不擅长流式计算
            * MapReduce的输入数据集是静态的，不能动态变化。MapReduce自身的设计特点决定了数据源必须是静态的
        3. 不擅长DAG（有向图）的计算
            * 每个MapReduce作业的输出结果都会写入磁盘，造成大量的磁盘IO，导致性能低下，所以不擅长处理这种多个程序相互依赖的操作

* MapReduce核心思想
    * 核心编程思想: ![MR_thought.png](./imgs/MR_thought.png)
    * **MapReduce程序分为两个阶段:Map 和 Reduce**
    * MapReduce编程模型只能包含**一个Map阶段和一个Reduce阶段**，如果用户的业务逻辑非常复杂，那就只能多个MapReduce程序串联，串行运行
    * 统计单次次数的流程
        1. Map阶段，对各个数据块执行并发的MapTask，完全并行运行，互不干扰
            * MapTask
                * 读取数据:一行一行读数据，并按行进行处理
                * 切分数据:按照空格切分数据
                * 数据kv化:将数据转换成key-value键值对：{单词:次数}
                * 保存:将所有的KV对中的单词按照单词首字母分成两个分区写到磁盘
                    * 分区1：a-p
                    * 分区2：q-z,
        2. Reduce阶段的并发RedeceTask，完全并行互不干扰，但它们的数据依赖于上一阶段的所有MapTask并发实例的输出
            * 统计a-p单词的从各MapTask的分区1**拉取数据**，处理后将结果输出到文件
            * 统计q-z单词的从各MapTask的分区2**拉取数据**，处理后将结果输出到文件

* MapTask如何工作？
* ReduceTask如何工作？
* MapTask如何控制分区、排序等？
* MapTask和ReduceTask之间如何衔接？

* MapReduce进程：MR程序在分布式运行时有三类实例进程
    * MrAppMaster：负责整个程序的过程调度及状态协调
        * 它是一个Job资源的控制者
        * 协调CPU等资源
        * 如何开启Map和Reduce阶段的处理
    * MapTask：负责Map阶段的整个数据流处理流程
    * ReduceTask：负责Reduce阶段的整个数据流处理流程

* 常用数据序列化类型

|java类型|hadoop writable类型|
|-|-|
|boolean|BooleanWritable|
|byte|ByteWritable|
|int|IntWritable|
|float|FloatWritable|
|long|LongWritable|
|double|DOubleWritable|
|**String**|**Text**|
|map|MapWritable|
|array|ArrayWritable|

* MapReduce编程规范
    * 程序分为三个部分：Mapper，Reducer，Driver
    * Mapper阶段
        1. **继承**父类Map
        2. Mapper的输入数据都是KV形式，KV的数据类型可以自定义
        3. 业务逻辑写在一个map()方法中
        4. Mapper的输出数据都是KV形式，KV的数据类型可以自定义
        5. map()方法对每一个<K,V>只调用一次
    * Reducer阶段
        1. **继承**父类Reduce
        2. Reduce的输入数据类型 = Mapper的输出数据类型，也是KV
        3. 业务逻辑写在reduce()方法中
        4. ReduceTask进程对每一组相同k的<K,V>组调用一次reduce()方法
    * Driver阶段
        * 相当与YARN集群的客户端，用于提交整个程序到YARN集群
        * 提交的内容是封装了MapReduce程序相关运行参数的job对象

* Mapper类组成
    * Context:操作过程中使用的上下文内容
    * setup:执行开始时调用(初始化程序)
    * map:应用程序重写
    * cleanup:操作结束后的处理
    * run:串联所有方法
        * setup
        * while{map}
        * cleanup
* Reducer类 组成
    * Context:操作过程中使用的上下文内容
    * setup:执行开始时调用(初始化程序)
    * reduce:应用程序重写
    * cleanup:操作结束后的处理
    * run：串联所有方法
        * setup
        * while{reduce}
        * cleanup

* WordCount案例
    * Mapper
        1. Mapper将输入的文本内容转换成String
        2. 根据空格切分单词
        3. 将单词输出为：`<单词, 1>`
            * 此时不对结果进行合并，只创建形如`<单词, 1>`的kv对象
    * Reducer
        1. 汇总各个Key的个数
            * 在该阶段进行kv的聚合，计算每个单词出现的次数
        2. 输出该key的总次数
    * Driver
        1. 获取配置信息，获取job对象实例
        2. 指定本程序的jar包所在的本地路径
        3. 将Mapper/Reducer的业务类关联到job
        4. 指定Mapper输出数据的kv类型
        5. 指定最终输出的数据的kv类型
        6. 指定job的输入原始文件所在的目录
        7. 指定job的输出结果所在目录
            * **输出路径必须不能存在**
        8. 提交作业到集群中 
            * 对于输出结果：如果正常执行结束则job返回true，此时系统会输出0；如果正常执行结束则job返回false，此时系统会输出1

* 将MR程序放到服务器上运行
    * 添加MAVEN配置
        ```
        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>2.3.2</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                    </configuration>
                </plugin>
                <plugin>
                    <artifactId>maven-assembly-plugin </artifactId>
                    <configuration>
                        <descriptorRefs>
                            <descriptorRef>jar-with-dependencies</descriptorRef>
                        </descriptorRefs>
                        <archive>
                            <manifest>
                                <mainClass>driver类的主类名</mainClass>
                            </manifest>
                        </archive>
                    </configuration>
                    <executions>
                        <execution>
                            <id>make-assembly</id>
                            <phase>package</phase>
                            <goals>
                                <goal>single</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
        ```
    * maven install 打包
    * 放到服务器上
    * 执行：`hadoop jar jar包路径 driver类的主类名 集群上的输入数据路径 集群上未存在的输出数据路径`

# hadoop序列化
[top](#catalog)
* 为什么不用java的序列化
    * java的Serializable是一个重量级的序列化框架
    * 一个对象被序列化后，会附带很多额外的信息：校验信息，header，继承体系等，不便于在网络中高效传输
* hadoop的序列化特点
    * 紧凑：高效使用存储空间
    * 快速：读写数据的额外开销小
    * 可扩展：随着通信协议的升级而可升级
    * 互操作：支持多语言的交互
* 自定义bean对象实现序列化接口：Writable
    * 实现步骤
        1. 必须实现Writable接口
        2. 反序列化时，需要反射调用空参构造函数，所以必须有空参构造
        3. 重写序列化方法
        4. 重写反序列化方法
        5. 序列化与反序列化的顺序一致
            * 序列化过程相当于对队列的操作，所以两端的处理顺序必须一致
        6. 如果要把结果显示在文件中，需要重写toString()，可用`\t`分开，方便后续使用
        7. 如果需要自定义的bean放在key中传输，还需要实现Comparable接口，因为MapReduce框架中的Shuffle过程要求key必须能排序
* **序列化中使用到的变量，在序列化之前必须有值，不能是空值，否则在序列化时会引发异常**
    
# MR框架原理
## 可以自定义的部分
[top](#catalog)
* NumReduceTasks
    * ReduceTask数量，也可以理解为分区的数量。每个MapTask任务结束后都会产生对应数量的分区文件
* FileInputFormat 负责切片、读取数据、对外提供key、vlaue的读取
    * 数据的读取有RecordReader的实现类负责
* FileOutputFormat 负责数据的输出
* Partitioner 设定分区方式
    * 需要配合NumReduceTasks使用
* WritableComparable 负责排序

## InputFormat数据输入
### 切片与MapTask并行度决定机制
[top](#catalog)
* MapTask的并行度决定Map阶段的任务处理并发度，会影响到整个Job的处理速度
    * 如1G数据，启动8个MapTask，每个处理128M数据；1K数据就不能启动8个MapTask
* MapTask并行度决定机制
    * 数据块：Block是HDFS物理上把数据分成一块一块
    * 数据切片：数据切片只是在逻辑上对输入进行分片，不会对磁盘数据做切片存储
        * 如将数据划分为0~128，把0～64的数据放到一个集合中进行处理，65～128放到另一个集合中
    * 实例：处理300M的数据
        1. 按照100M来对数据进行切片，会跨节点导致产生IO传输消耗
        ![MP_100MData_Cut.png](./imgs/MP_100MData_Cut.png)
        2. 按照128M来对数据进行切片，不会产生IO传输消耗，效率是最高的
        ![MP_300MData_Cut.png](./imgs/MP_300MData_Cut.png)
    * 机制
        1. 一个Job的Map阶段的并行度由客户端在提交Job时的**切片数决定**
        2. 每一个切片分配一个MapTask并行实例处理
        3. **默认情况下，切片大小=BlockSize**
        4. 切片时不考虑数据集整体，而是逐个针对每一个文件单独切片
            * 即多个文件的情况，每个文件单独切片，不考虑整体

### Job提交流程
[top](#catalog)
* job提交流程：![MR_Submit_Job_Flow.png](./imgs/MR_Submit_Job_Flow.png)
* 最终需要提交的信息
    1. 切片信息
    2. xml配置
    3. jar包

### 切片源码解析
[top](#catalog)
* 主要参考类：`org.apache.hadoop.mapreduce.lib.input.FileInputFormat.class`
* 本地运行时，默认数据块大小为32M；在yarn集群上使用128M
    * yarn中，hadoop1.x使用64M，hadoop2.x使用128M
* 切片流程：
    1. 找到数据存储的目录
    2. 开始遍历目录下的每一个文件
    3. 对于每一个文件进行切片
        * 获取当前文件大小
        * 计算适用于该文件的切片大小
            * 默认情况下=块大小
        * 进行切片
            * 0-splitSize，splitSize-2*splitSize,....
            * **每次必须判断切完剩下的部分是否大于切片的1.1倍，如果不大于则不切分**
        * 将切片信息写到一个切片规划文件中
        * 切片处理在`public List<InputSplit> getSplits(JobCOntext job)`中完成
            * InputSplit只记录切片的元数据信息：起始位置、长度、所在的节点列表等
    4. 提交切片规划文件到yarn，**yarn上的MrAppMaster就可以根据切片规划文件计算MapTask个数**
* 代码分析
    1. 切片大小的计算()
        ```java
        public List<InputSplit> getSplits(JobCOntext job) throws IOException{
            ...
            if (this.isSplitable(job, path)){
                // 获取块大小
                long blockSize = file.getBlockSize(); //本地=32，yarn=128
                // 计算适用于当前文件的切片大小
                long splitSize = this.computeSplitSize(blockSize, minSize, maxSize);
            }
        }
        protected long computeSplitSize(long blockSize, long minSize, long maxSize){
            return Math.max(minSize, Math.min(maxSize, blockSize))
        }
        // 先计算：最大值和块大小的小值，再计算和最小值中的大值
        // minSize=1，maxSize=9223372036854775807
        // 默认情况下=块大小
        ```
    2. 判断是否对数据文件进行切片
        ```java
        // 在自定义的InputFormat类中，可以通过重写该方法来禁止对文件进行切片
        protected boolean isSplitable(JobContext context, Path filename) {return true;}

        // 在获取切片的方法中会调用isSplitable
        public List<InputSplit> getSplits(JobCOntext job) throws IOException{
            ...
            if (this.isSplitable(job, path)){
                ...
            }
        }
        ```
    3. 如何对数据进行切片
        ```java
        public List<InputSplit> getSplits(JobCOntext job) throws IOException{
            ...
            // 即数据文件的长度必须是切片大小的1.1倍以上时才进行切片
            // 一边且一般与切片大小进行比较
            for(bytesRemaining = length; (double)bytesREmaining/(double)splitSize > 1.1D; bytesRemaining -= splitSize){
                blkIndex = this.getBlockIndex(blkLocations, length - bytesRemaining);
                splits.add(this.makeSplit(path, length - bytesRemaining, splitSize, blkLocations[blkIndex].getHosts(), blkLocations[blkIndex].getCachedHosts()));
            }
        }
        ```
        * 例如在yarn集群上，一个**129M的文件分为2个block存储，切片时只有1个切片(因为129/128<1.1)**

### FileInputFormat
#### RecordReader
* FileInputFormat通过在内部创建RecordReader来读取文件
* RecordReader负责读取文件，并可以从中获取key，value

#### FileInputFormat切片机制
[top](#catalog)
* 切片机制
    1. 简单的按照文件的内容长度进行切片
    2. 切片大小，默认等于Block
        * 本地=32M，yarn=128M，老版本yarn=64M
    3. 每个文件单独切片，切片时不考虑数据集整体

* FileInputFormat切片大小的参数配置
    * 计算公式：`Math.max(minSize, Math.min(maxSize, blockSize))`
        * mapreduce.input.fileinputformat.split.minsize=1
        * mapreduce.input.fileinputformat.split.maxsize=Long.MAXValue
    * 切片大小设置
        * 让切片变小：设置`maxSize<blockSize`
            * Math.min(maxSize, blockSize) = maxSize
            * Math.max(minSize, maxSize) = maxSize
        * 让切片变大：设置`minSize>blockSize`
            * Math.min(maxSize, blockSize) = blockSize
            * Math.max(minSize, blockSize) = minSize
    * 获取切片信息API
        ```java
        // 获取切片信息API
        String name = inputSplit.getPath().getName();
        // 根据文件类型获取切片信息
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        ```
#### FileInputFormat实现类
[top](#catalog)
* 可以输入的数据类型包括：基于行的日志、二进制文件、数据库表。**不同的数据类型，MP需要不同的，FileInputFormat实现类来处理**

* FileInputFormat常见的接口实现类：
    * TextInputFormat
    * KeyValueTextInputFormat
    * NLineInputFormat
    * CombineTextInputFormat
    * 自定义Format

* TextInputFormat
    * TextInputFormat是默认的FileInputFormat实现类
    * 按行读取每条记录
    * key=当前行在整个文件中的起始字节偏移量，LongWritable类型
    * value=这一行的内容，**不包括终止符(换行符和回车符)**，Text类型
    * 解析实例
        ```
        //文件内容
        abceefg
        rythf
        ewruj
        
        //解析结果
        (0,abceefg)
        (8,rythf)
        (14,ewruj)
        ```
* KeyValueTextInputFormat
    * 每一行均为一条记录，被分隔符分割为key、balue
    * 可以通过在driver类中设定分割符，默认分隔符是`\t`
        ```java
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, "\t");
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        ```
* NLineInputFormat
    * 不按blockSize来切片，而是按照指定的行数N来划分
    * 切片数=文件总行数/N，如果不整除，则加1
    * key、value和TextInputFormat相同，key=起始位置的偏移量，value=N行文件内容
    * 在driver类中使用NLineInputFormat
        ```java
        NLineInputFormat.setNumLinesPerSplit(job,3);
        job.setInputFormatClass(NLineInputFormat);
        ```
* 自定义InputFormat
    * 自定义步骤
        1. 自定义一个类继承FileInputFormat
        2. 改写RecordReader，实现一次读取一个完整文件封装为KV
        3. 输出是使用SequenceFileOutputFormat输出合并文件
    * 自定义处理小文件
        * 处理步骤
            1. 自定义一个类继承FileInputFormat
                * 重写isSplitable()方法，返回false不可切割
                    * 即不对小文件进行切片
                * 重写createRecordReader()，创建自定义的RecordReader对象，并初始化 
            2. 改写RecordReader，实现一次读取一个完整文件封装为KV
                * 采用IO流一次读取一个文件输出到Value中
                * 因为设置了不可切片，最终把所有文件都分装到了value中
                * 设置文件路径信息+名称，并设置key
            3. 设置Driver
                ```java
                //设置输入的inputFsormat
                job.setInputFormatClass(WholeFileInputFormat.class);
                //设置输出的outputFormat
                job.setOutputFormatClass(SequenceFileOutputFormat.class);
                ```
        * 处理后，将小文件合并成一个文件，数据类型是KV

* SequenceFile文件
    * SequenceFile文件是Hadoop用来存储二进制形式的key-value对的文件格式
    * SequenceFile里面存储着多个文件，存储的形式为：`key=文件路径+名称，value=文件内容`

### CombineTextInputFormat切片机制
[top](#catalog)
* 应用
    * 默认的切片机制是对每个文件进行切片，无论文件多小，都单独切片交给一个MapTask。**如果存在大量小文件，会产生大量MapTask，降低处理效率**
    * CombineTextInputFormat用于处理小文件，可以将多个小文件从**逻辑上规划到一个切片中，将多个小文件交给一个MapTask处理
* 虚拟存储切片最大值设置
    ```java
    CombineTextInputFormat.setMaxInputSplitSize(job,4194304) //4M
    ```
    * 需要根据实际小文件的大小情况来设置具体的值
* 切片机制 ![MR_CombineTextInputFormat.png](./imgs/MR_CombineTextInputFormat.png)
    * 在切片过程中，会一直合并虚拟存储过程中的分块，直到合并的大小大于切片最大值，才开始下一个切片

* 使用方法
    * 在Dirver类中添加代码：
        ```java
        // 如果不设置InputFormat，它默认用的是TextInputFormat.class
        job.setInputFormatClass(CombineTextInputFormat.class);

        //虚拟存储切片最大值设置4m
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
        ```

## MR工作流程
[top](#catalog)
![MR_Working_Map.png](./imgs/MR_Working_Map.png)
![MR_Working_Reduce.png](./imgs/MR_Working_Reduce.png)
* 基本流程
    1. 放置待处理文件
    2. 客户端submit()前，对待处理文件进行切片
    3. submit()，提交3种信息
        * 切片信息
        * 配置信息
        * 如果在集群上运行，还需要上传运行所需jar包
    4. 计算MapTask的数量(切片数量)
    5. 获取数据，默认使用`TextInputFormat`，可以在Driver类中进行设定
        ```java
        job.setInputFormatClass(XXX.class);
        ```
    6. 执行Mapper类中的运算
    7. 将执行结果`<k,v>`写入一个环形缓冲区
    8. 使用的缓冲区>80%，对数据进行分区，并在每个分区内部，对字典按照key执行**快速排序**
    9. 将排序后的分区数据溢出到文件
        * 不同的分区写到不同的文件
        * 可能会写多次
    10. 将每个分区下的多个文件进行Merge，并执行**归并排序**
    11. 合并
    12. MrappMaster：所有MapTask任务完成后，启动相应数量的RduceTask，并告知ReduceTask处理哪个分区的数据
        * 有几个分区就有几个ReduceTask
    13. 每个ReduceTask将对应分区的数据从各个MapTask中下载到本地磁盘，并将下载到的文件进行合并，使用归并排序
    14. reduce从归并的文件中一次读取一组kv
    15. 分组
    16. 使用OutputFormat将结果写入文件，默认使用TextOutputFormat

## shuffle机制
### partitioner分区
[top](#catalog)
* 默认Partitioner分区
    ```java
    public class HashPartitioner<K, V> extends Partitioner<K,V>{
        public int getPartition(K key, V value, int numReduceTasks){
            return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
        }
    }
    ```
    * 计算方法：(key.hashCode() & Integer.MAX_VALUE) % numReduceTasks
    * 默认分区根据key的hashCode对numReduceTasks取模得到，用户无法控制key存储到哪个分区
    * 在driver中设定分区数量
        ```java
        job.setNumReduceTasks();
        ```
* 自定义Partitions
    * 自定义步骤
        1. 自定义类继承Partitioner，重写getPartition()方法
            * key、value的类型与Mapper的输出向对应
        2. 在driver中设定自定义partitioner
            ```java
            job.setPartitionerClass(CustomPartitioner.class);
            ```
        3. 自定义Partitioner后，要根据自定义Partitioner的逻辑设置响应数量的ReduceTask
            ```java
            job.setNumReduceTasks();
            ```

* 如果`NumReduceTask > getPartition`的结果数，会额外产生几个空文件：`part-r-xxxxx`
* 如果`1<NumReduceTask<getPartition`，有写数据不知道该放在哪个文件中，会引发异常
* 如果`NumReduceTask=1`，则不管MapTask端有多少个分区文件，最终都会交给一个ReduceTask，最终只会产生一个结果文件`part-r-00000`
* **分区号必须从0开始，并逐一累加**

### WritableComparable排序
#### 基本概念
[top](#catalog)
* 排序是MR框架中最重要的操作之一
* MapTask、ReduceTask都会对数据按照key进行排序。
    * 该操作属于Hadoop的默认行为
    * 任何应用程序中的数据均会被排序，而不管逻辑上是否需要
* 默认对key进行排序，使用快速排序
* MapTask的排序
    * MapTask会将处理结果暂时放到环形缓冲区中
    * **当环形缓冲区使用率达到阀值后，再对缓冲区中的数据进行一次快速排序**，并将这些数据写到磁盘上，当数据处理完毕后，会**对磁盘上所有文件进行归并排序**
* 在Map阶段至少需要对数据的key进行一次排序，对所有文件进行一次排序
* ReduceTask的排序 
    * 从每个MapTask上**远程拷贝相应的数据文件**，如果文件大小超过一定阀值，则溢写到磁盘上，否则存储在内存中
    * 如果磁盘上文件数目达到一定阀值，则进行一次**归并排序**，生成一个更大的文件
    * 如果内存中的数据超过一定阀值，则进行一次合并后将数据溢写到磁盘上？？？
    * 当所有数据拷贝完毕后，Reduce同一对内存和磁盘上的所有数据进行一次归并排序

#### 排序的分类
[top](#catalog)
* 部分排序
    * MR根据输入记录的**键**对数据集排序，保证输出的**每个文件内部有序**
* 全排序
    * 最终输出结果只有一个文件，且文件内部有序
    * 实现方式是：**只设置一个ReduceTask**
        * 此方法在处理大型文件是效率及低，因为一台机器处理文件，完全丧失了MapReduce所提供的并行架构
* 辅助排序：(GroupingCOmparator分组)
    * 在Reduce端对key进行分组
    * **在接收的key为bean对象时**，想让一个或几个字段相同(全部字段比较不同)的key进入到同一个reduce方法时，可以采用分组排序
* 二次排序
    * 在自定义排序过程中，如果compareTo中的判断条件为两个即为二次排序
    * compareTo中有3个条件，为三次排序
#### 自定义排序WritableComparable
[top](#catalog)
* bean对象作为key传输时，需要实现WritableComparable接口重写compareTo方法来实现排序
#### 区内排序
[top](#catalog)
* 在每个分区内进行排序
* WritableComparable和Partitioner进行配合

### Combiner合并