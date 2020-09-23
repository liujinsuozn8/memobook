<span id="catalog"></span>

<span style='font-size:18px'>目录---HDFS</span>

- [HDFS说明](#HDFS说明)
- [HDFS组成架构](#HDFS组成架构)
- [HDFS文件块大小](#HDFS文件块大小)
- [HDFS的副本数](#HDFS的副本数)
- [HDFS的shell命令](#HDFS的shell命令)
- [HDFS的客户端操作](#HDFS的客户端操作)
    - [参考代码与工程搭建](#参考代码与工程搭建)
    - [HDFS_API操作](#HDFS_API操作)
        - [HDFS_API的基本使用流程](#HDFS_API的基本使用流程)
        - [HDFS_API---文件操作](#HDFS_API---文件操作)
    - [HDFS的IO流操作](#HDFS的IO流操作)
        - [HDFS的IO流操作的本质](#HDFS的IO流操作的本质)
        - [HDFS的IO流---基本操作流程](#HDFS的IO流---基本操作流程)
        - [HDFS的IO流---文件的上传与下载](#HDFS的IO流---文件的上传与下载)
        - [HDFS的IO流---定位读取](#HDFS的IO流---定位读取)
- [HDFS配置的优先级](#HDFS配置的优先级)
- [HDFS的数据流](#HDFS的数据流)
    - [HDFS写数据的流程](#HDFS写数据的流程)
    - [DN的选择方式](#DN的选择方式)
    - [HDFS读数据的流程](#HDFS读数据的流程)
- [NN与2NN](#NN与2NN)
    - [元数据如何保存](#元数据如何保存)
    - [NN和2NN的工作流程](#NN和2NN的工作流程)
    - [NN和2NN的注意事项](#NN和2NN的注意事项)
    - [FsImage、Edits解析](#FsImage、Edits解析)
    - [CheckPoint](#CheckPoint)
    - [CheckPoint--2NN检查点设置](#CheckPoint--2NN检查点设置)
    - [NN的故障处理](#NN的故障处理)
        - [处理方式1---拷贝2NN的数据](#处理方式1---拷贝2NN的数据)
        - [处理方法2---在NN的守护进程下拷贝2NN的数据](#处理方法2---在NN的守护进程下拷贝2NN的数据)
    - [集群安全模式](#集群安全模式)
    - [NN的多目录配置](#NN的多目录配置)
- [DN](#DN)
    - [DN中每个Block的内容](#DN中每个Block的内容)
    - [DN的工作机制](#DN的工作机制)
    - [DN如何保证每个Block的数据完整性](#DN如何保证每个Block的数据完整性)
    - [掉线时限参数设置](#掉线时限参数设置)
    - [动态增删节点DN](#动态增删节点DN)
        - [方式1--克隆节点](#方式1--克隆节点)
        - [方式2--添加白名单](#方式2--添加白名单)
        - [方法3--添加黑名单](#方法3--添加黑名单)
    - [DN多目录配置](#DN多目录配置)
- [HDFS2.X的特性](#HDFS2.X的特性)
    - [集群间数据拷贝](#集群间数据拷贝)
    - [小文件存档](#小文件存档)
    - [回收站](#回收站)
    - [快照管理](#快照管理)
- [](#)


# HDFS说明
[top](#catalog)
- HDFS 是一种: **分布式文件管理系统中**
    - HDFS 是一个文件系统，用于存储文件，通过**目录树**来定位
    - HDFS 是分布式的
        - 由多个服务器组合实现
        - 每个服务器都有各自的角色
- HDFS 的应用场景
    - 适合一次写入，多次读取，<span style='color:red'>不支持文件修改</span>
    - 适合做数据分析，不适合做网盘

- HDFS 的优点
    - 高容错性
        - 数据自动保存多个副本，通过增加副本提高容错
        - 如果某一副本丢失，可以自动恢复
    - 适合处理大数据
        - 数据规模：GB，TB，PB
        - 文件规模：百万以上的文件数量
    - 可在廉价机器上构建
- HDFS 的缺点
    - 不适合低延时数据访问，如毫秒级的数据存储
    - 无法高效存储<span style='color:red'>大量小文件</span>
        - 浪费 DN 的容量
            - 每个 DN 的默认大小为128M，存储小文件会浪费很多存储
        - 浪费 NN
            - 会导致 NN 存储大量的 文件目录 和 块信息，会耗尽 NN 的内存
            - 每个文件会占用150B的 NN 内存
        - 增加寻址时间
            - <span style='color:red'>小文件存储的寻址时间会超过读取时间，违反了HDFS的设计目标</span>
    - 不至支持**并发的写入**
        - 一个文件只能有一个线程写，不允许多个线程同时写
    - 不至支持**文件随机修改**
        - **仅支持增加数据**，不支持文件的随机修改

# HDFS组成架构
[top](#catalog)
- HDFS 架构图
    - ![HDFS_Architecture](imgs/HDFS_Architecture.png)
- 架构中的组件及其功能
    - NameNode。即: Master，管理者
        - 管理HDFS的名称空间
        - 配置副本策略
        - 管理数据块映射信息（即各个数据块存在哪里）
        - 处理客户端读写请求
    - DataNode。即: slave
        - 存储实际的数据块
        - 执行数据块的读/写操作
        - **NameNode下达命令，DataNode执行实际操作**
    - client。客户端
        - 文件切分，上传文件时，**将文件切分成数据块**
        - 与 NN 交互，获取文件的位置信息
        - 与 DN 交互，读/写数据
        - 提供管理HDFS的命令，如NameNode的格式化
        - 提供访问HDFS的命令，如增删改查
    - Secondary NameNode
        - 2NN 不是 NN 的热备，当 NN 挂掉的时候，不能马上替换 NN 并提供服务
        - 主要功能
            1. 辅助 NN，分担其工作量，如定期合并Fsimage(镜像文件)和Edits(编辑日志)，并推送到 NN
            2. 紧急情况下，可以辅助恢复 NN

# HDFS文件块大小
[top](#catalog)
- HDFS中文件在物理上是分块存储的。
- 块的大小
    - 在集群中是 `128M`
    - 在本地执行时，是 `32M`

- `128M` 的计算方法
    - 若寻址时间为10ms
    - 寻址时间为传输时间的%1时，则传输时间为: `10ms/0.01=1000ms=1s`
    - 目前磁盘的传输速率普遍为 `100M/s`，则一个数据块的大小为: `1s*100M=100M`
    - 为了匹配常规的存储规范，设置为 128M
- <span style='color:red'>HDFS块大小的设置主要取决于磁盘的传输速率</span>
    - 如果数据块太小，会增加寻址时间，即程序一直在搜索数据块的开始位置
    - 如果数据块太大，数据传输时间增大(从服务器到客户端)，阻碍了其他操作的执行

- 块的大小可以进行配置
    - 配置参数: `dfs.blocksize`
    - 如果磁盘性能好，数据块可以更大

# HDFS的副本数
[top](#catalog)
- hdfs 默认副本数为 `3`
- 如果只有一台机器，副本数量=3，**在实际运行时只会有一份数据**
    - 当机器增加时，才会在增加的机器上进行备份
    - 该操作是自动控制的
- 可以通过 HDFS shell 的 `-setrep` 命令对某个文件的副本数据进行设置


# HDFS的shell命令
[top](#catalog)
- 基本语法：
    - `bin/hadoop fs 具体命令`，包含 `bin/hdfs dfs`
    - `bin/hdfs dfs 具体命令`， dfs是fs的实现类

- 命令

    |指令                       |功能|
    |---------------------------|------------------------------------------|
    |-help 指令名                   |查看帮助|
    |-ls                            |显示信息|
    |-cat                           |显示文件内容|
    |-tail                          |显示文件的末尾|
    |-chgrp,-chmod,-chown           |修改权限|
    |-appendToFile 本地路径 HDFS路径 |追加一个文件到已经存在的文件末尾|
    |-put,-copyFromLocal            |从本地文件系统拷贝文件到HDFS|
    |-get,-copyToLocal              |从HDFS拷贝文件到本地: `hdfs dfs -copyToLocal HDFS路径 ./`|
    |-cp                            |从**HDFS的一个路径**拷贝文件到另一个路径|
    |-mv                            |在HDFS中移动文件|
    |-getmerge                      |从HDFS合并下载多个文件。如打包下载日志文件|
    |                               |`hdfs dfs -getmerge xxx/- ./yyy` 将HDFS`xxx/`下的文件合并下载到当前目录的`yyy`|
    |-rm                            |删除文件或文件夹|
    |-mkdir                         |在HDFS上创建目|
    |-rmdir                         |删除HDFS的空目录|
    |-du                            |统计文件大。`-h` 以直观的方式显示结果|
    |-tail                          |显示文件的末尾|
    |-setrep 副本数量 HDFS路径       |设置HDFS中文件的副本数量|

- `-setrep` 指令的注意事项
    - 指令的设定值只会保存在 NN 的**元数据**中，具体有多少个，需要根据实际节点数决定
    - **web端**显示的永远都是是**设定值**
    - 如果 `设定值 > 可用节点数`，则 `副本数=节点数`
        - 如: `可用节点数=3`，`-setrep 10`
            - 执行 `-setrep` 后只有 3 个备份，因为只有 3 个节点
            - 每增加一个节点，则添加一个备份，直到 10 为止
    - 如果 `设定值 < 默认值`，会使集群中的某些节点删除备份文件
    - 文件副本的存放位置
        - `./data/tmp/dfs/data/current/BP-xxx/current/finalized/subdir0/subdir0`
        - 该目录下的非`.meta`即为副本文件

# HDFS的客户端操作
## 参考代码与工程搭建
[top](#catalog)
- 参考代码
    - [src/myhd/hdfs](src/myhd/hdfs)

- 在 windows 搭建操作HDFS的工程
    1. 配置环境变量: `JAVA_HOME`，`HADOOP_HOME`
    2. 添加maven配置
        - 参考: [src/myhd/pom.xml](src/myhd/pom.xml)

- 设置操作 HDFS 的用户
    - 方式1 : 设置 `VM options`
        - 直接设置启动参数: `-DHADOOP_USER_NAME=用户名`
            - 在 idea 中设定启动参数
                - Run-->Edit Configurations-->Application-->Configuration-->VM options
        - 在代码中设置 `VM options`
            ```java
            System.setProperty("HADOOP_USER_NAME", "用户名");
            ```
    - 方式2: 直接在 HDFS API 中设置操作用户
        ```java
        FileSystem fs = FileSystem.get(new URI("..."), conf, "用户名");
        ```

## HDFS_API操作
### HDFS_API的基本使用流程
[top](#catalog)
- 参考代码
    - [src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/hdfsapi/Mkdir.java](src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/hdfsapi/Mkdir.java)
- 基本流程
    1. 使用 `Configuration` 设置配置信息
        - 必须创建一个实例对象，如果不做设置，则使用默认配置
    2. 使用 `FileSystem` 创建客户端对象
    3. 通过 `FileSystem` 实例对象，操作 HDFS API
    4. 操作完成后**关闭资源**
- 代码内容
    ```java
    // 创建目录
    @Test
    public void testMksdir02() throws IOException, URISyntaxException, InterruptedException {
        // 0. 设置配置信息
        Configuration conf = new Configuration();

        // 1. 直接在获取客户端对象时，指定要访问的地址和用户名
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"),conf, "hduser");

        // 2. 在hdfs上创建路径
        fs.mkdirs(new Path("/user/idea/testdir02"));

        // 3. 关闭资源
        fs.close();

        System.out.println("over");
    }
    ```

### HDFS_API---文件操作
[top](#catalog)
- 参考代码
    - [src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/hdfsapi/FileAction.java](src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/hdfsapi/FileAction.java)
- 文件的上传、下载、删除
    - 上传: 拷贝本地文件到 hdfs
        ```java
        @Test
        public void copyFromLocal() throws URISyntaxException, IOException, InterruptedException {
            // 0. 设置配置信息
            // 1. 获取客户端对象
            // 2. 拷贝本地文件到服务器
            fs.copyFromLocalFile(
                    new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/FileAction/fileAction_file01.txt"),
                    new Path("/user/idea/testdir")
            );

            // 3. 关闭资源
        }
        ```
    - 下载。默认会生成 crc 校验文件
        ```java
        // 
        @Test
        public void copyToLocalFile() throws URISyntaxException, IOException, InterruptedException {
            // 0. 设置配置信息
            // 1. 获取客户端对象
            // 2. 下载文件，参数为： 服务器文件目录、本地文件目录
            fs.copyToLocalFile(
                    new Path("/user/idea/testdir/fileAction_file01.txt"),
                    new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/FileAction/fileDownload/")
            );

            // 3. 关闭资源
        }
        ```

    - 下载的重载方法。不使用 crc 校验文件
        ```java
        @Test
        public void copyToLocalFileNotCRC() throws URISyntaxException, IOException, InterruptedException {
            // 0. 设置配置信息
            // 1. 获取客户端对象
            // 2. 下载文件，参数为： 服务器文件目录、本地文件目录
            fs.copyToLocalFile(
                    false, // 不删除原文件
                    new Path("/user/idea/testdir/fileAction_file01.txt"),
                    new Path("src/main/resources/com/ljs/myhd/hdfs/hdfsapi/FileAction/fileDownload/"),
                    true    // 不使用crc
            );

            // 3. 关闭资源
        }
        ```
    - 删除文件
        ```java
        @Test
        public void deleteFile() throws URISyntaxException, IOException, InterruptedException {
            // 0. 设置配置信息
            // 1. 获取客户端对象
            // 2. 删除文件
            fs.delete(
                    new Path("/user/idea/testdir/fileAction_file01.txt"),
                    true    // true 可以删除目录
            );

            // 3. 关闭资源
            fs.close();
        }
        ```

- 重命名 hdfs 上保存的文件
    ```java
    @Test
    public void rename() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        // 1. 获取客户端对象
        // 2. 删除文件
        fs.rename(
                new Path("/user/idea/testdir/fileAction_file01.txt"),
                new Path("/user/idea/testdir/fileAction_file01_rename.txt")
        );

        // 3. 关闭资源
    }
    ```

- 查看文件的相关信息
    ```java
    @Test
    public void fileInfo() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        // 1. 获取客户端对象

        // 2. 获取文件信息
        // 参数: 路径，是否递归获取所有信息
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while (listFiles.hasNext()) {
            LocatedFileStatus status = listFiles.next();

            // 输出文件名称、长度、权限、分组
            System.out.println(status.getPath().getName());
            System.out.println(status.getLen());
            System.out.println(status.getPermission());
            System.out.println(status.getGroup());

            // 输出文件的块信息
            BlockLocation[] blockLocations = status.getBlockLocations();
            for (BlockLocation b : blockLocations) {
                String[] hosts = b.getHosts();

                // 输出文件的块所在的主机
                for (String host : hosts) {
                    System.out.println(host);
                }
            }

            System.out.println("-------------------------------------");
        }

        // 3. 关闭资源
    }
    ```
- 判断路径是文件还是文件夹
    ```java
    @Test
    public void pathType() throws URISyntaxException, IOException, InterruptedException {
        // 0. 设置配置信息
        // 1. 获取客户端对象

        // 2. 获取路径信息
        FileStatus[] fileStatuses = fs.listStatus(new Path("/user/idea/testdir/"));
        for (FileStatus status : fileStatuses) {
            // 判断是文件还是目录
            if (status.isFile()) {
                System.out.println("file" + status.getPath().getName());
            } else {
                System.out.println("dir" + status.getPath().getName());
            }
        }

        // 3. 关闭资源
    }
    ···

## HDFS的IO流操作
### HDFS的IO流操作的本质
[top](#catalog)
- HDFS的IO流操作 本质上就是: <span style='color:red'>输入输出流之间的互相拷贝</span>
- 和普通IO流操作不同的是: 在输入输出流中，至少有一个是 hdfs 文件的流
- 不同的业务，流之间的拷贝方式不同
    - 可能是全部拷贝
    - 可能是部分拷贝，如拷贝某个大小的数据块

### HDFS的IO流---基本操作流程
[top](#catalog)
- 参考代码
    - [src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/customizeIO/CustomizeIO.java](src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/customizeIO/CustomizeIO.java)

- 基本操作流程
    ```java
    @Test
    public void upload() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hd02:9000"), conf, "hduser");

        // 2. 创建流
        // 获取输入流: 可以是本地文件，可以是hdfs上的文件
        // 获取输出流: 可以是本地文件，可以是hdfs上的文件

        // 3. 流的拷贝
        // 参数: 输入流、输出流、配置对象
        IOUtils.copyBytes(fis, fos, conf);

        // 4. 关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);

        // 5. 关闭资源
        fs.close();
    }
    ```

### HDFS的IO流---文件的上传与下载
[top](#catalog)
- 参考代码
    - [src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/customizeIO/CustomizeIO.java](src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/customizeIO/CustomizeIO.java)
- 上传文件
    ```java
    @Test
    public void upload() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象

        // 2. 创建流
        // 获取输入流，本地路径
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream("com/ljs/myhd/hdfs/customizeIO/customizeIO_file01.txt");
        // 获取输出流，hdfs路径
        FSDataOutputStream fos = fs.create(new Path("/user/input/idea/customizeIO_file01.txt"));

        // 3. 流的拷贝
        IOUtils.copyBytes(fis, fos, conf);

        // 4. 关闭流
        // 5. 关闭资源
    }
    ```
- 下载文件
    ```java
    @Test
    public void download() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象
        // 2. 创建流
        // 获取输入流，hdfs路径
        FSDataInputStream fis = fs.open(new Path("/user/input/idea/customizeIO_file01.txt"));
        // 获取输出流，本地路径
        FileOutputStream fos = new FileOutputStream("src/main/resources/com/ljs/myhd/hdfs/customizeIO/customizeIO_file01_download.txt");

        // 3. 流的拷贝
        IOUtils.copyBytes(fis, fos, conf);

        // 4. 关闭流
        // 5. 关闭资源
    }
    ```

### HDFS的IO流---定位读取
[top](#catalog)
- 参考代码
    - [src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/customizeIO/CustomizeIO.java](src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/customizeIO/CustomizeIO.java)

- 定位读取，读取第一个 128M 的数据块
    ```js
    @Test
    public void downloadPart1() throws URISyntaxException, IOException, InterruptedException {
        // 1. 获取配置对象 和 文件系统对象

        // 2. 创建流
        // 获取输入流，hdfs路径
        FSDataInputStream fis = fs.open(new Path("/user/input/idea/hadoop-2.7.2.tar.gz"));
        // 获取输出流，本地路径
        FileOutputStream fos = new FileOutputStream("src/main/resources/com/ljs/myhd/hdfs/customizeIO/hadoop-2.7.2.tar.gz-part01");

        // 3. 流的拷贝
        // 创建缓冲区
        byte[] buf = new byte[1024];

        // 只读取1块，从头开始读取，读取 128M 的内容
        for (int i = 0; i < 1024 * 128; i++) {
            fis.read(buf);
            fos.write(buf);
        }

        // 4. 关闭流
        // 5. 关闭资源
    }
    ```
- 定位读取，从指定位置开始读取
    - 通过 `FSDataInputStream.seek(byte数)`来跳过指定长度的数据
    - 代码内容
        ```java
        @Test
        public void downloadPart2() throws URISyntaxException, IOException, InterruptedException {
            // 1. 获取配置对象 和 文件系统对象

            // 2. 创建流
            // 获取输入流，hdfs路径
            FSDataInputStream fis = fs.open(new Path("/user/input/idea/hadoop-2.7.2.tar.gz"));
            // 获取输出流，本地路径
            FileOutputStream fos = new FileOutputStream("src/main/resources/com/ljs/myhd/hdfs/customizeIO/hadoop-2.7.2.tar.gz-part02");

            // 3. 流的拷贝
            // 跳过第1块，即前128M
            fis.seek(1024*1024*128);

            // 读取剩余的所有数据
            IOUtils.copyBytes(fis, fos, conf);

            // 4. 关闭流
            // 5. 关闭资源
        }
        ```

# HDFS配置的优先级
[top](#catalog)
- 优先级顺序---升序

    |配置位置|配置方法|
    |-|-|
    |代码中的设定|`configuration.set("属性名", "属性值");`|
    |工程目录下的 `hdfs-site.xml`|在工程的 `src/main/resources` 下添加 `hdfs-site.xml`|
    |`etc/hadoop/hdfs-site.xml` 中的配置||
    |hdfs 的默认配置||

- 参考代码
    - [src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/hdfsapi/ConfigPriority.java](src/myhd/hdfs/src/main/java/com/ljs/myhd/hdfs/hdfsapi/ConfigPriority.java)

# HDFS的数据流
## HDFS写数据的流程
[top](#catalog)
- HDFS 写数据的流程图
    - ![HDFSWriteFile](./imgs/HDFS_WriteFile.png)
- 流程说明
    1. 将文件分块，然后准备按顺序上传
    2. client 与 NN 交互

        |No|交互方向            |交互内容|
        |--|--------------------|----------------------|
        |1|-                    |client 创建 `Distributed FileSystem`， 与 NN 的交互|
        |2|`client-->NN`        |客户端请求上传文件 X|
        |3|`NN-->client`        |NN 进行文件检查<br>如果文件存在则error; 如果不存在，则通知客户端可以上传文件|
        |4|`client-->NameNode`  |客户端请求上传第一个 `Block (0-128M)`，向 NN 请求 DN|
        |5|`NameNode-->client`  |NN 返回 n 个可用的 DN_ID，如: DN_1, DN_2..., DN_N<br>`n`，表示副本数|

    3. client 与 所有可用的 DN 交互

        |No|交互方向                                    |交互内容|
        |--|--------------------------------------------|----------------------|
        |1 |                                            |client 创建**输出流**: `FSDataOutputStream`，与 DN 交互，准备进行写第一个 `Block (0-128M)`
        |2 |`client--> DN_1 --> DN_2 --> ... --> DN_N`  |客户端**依次**向每个可用 DN 发送消息，请求建立 `Block` 传输通道|
        |3 |`DN_N --> ... --> DN_2 ---> DN_1 --> client`|n 个 DN 依次应答、建立通道|
        |4 |`client--> DN_1 --> DN_2 --> ... --> DN_N`  |以 `Packet` 为单位分割文件|
        |  |                                            |客户端先从磁盘读取数据并缓存到内存|
        |  |                                            |数据先上传到 DN_1，DN_1 又将数据传给 DN_2，DN_2 又将数据传给 DN_N|
        |  |                                            |各 DN 每传一个 packet，就会放入一个 `应答队列` 等待应答|
        |5 |`DN_N --> ... --> DN_2--> DN_1 --> client`  |上传完成后，从后向前，依次响应。然后**清空内存**|

    4. 重复 2、3，直到上传完所有的 Block
    5. `client --> NN`，`Distributed FileSystem` 对象通知 NN 数据传输完成

## DN的选择方式
[top](#catalog)
- **可用的 DN 的计算依据**
    - 节点距离: **距离接近**的n个节点
        - 计算方式: **两个节点，到达最近的共同祖先的距离总和**，距离小的就是距离接近的节点
        - 每个节点到自身的距离为0
    - 负载情况: 寻找**负载压力小**的节点

- DN节点如何选择---**机架感知**
    - 以 3 个副本为例
        1. 第一个副本
            - 保存位置
                - 如果是在集群的某个节点上操作，则保存在这个 client 所在的节点上
                - 如果 client 在集群外，随机选择一个节点
            - 功能: <span style='color:red'>保证存储，并且IO的时间最短</span>
        2. 第二个副本
            - 保存位置
                - 和第一个副本**在相同机架上**的**不同**的**随机节点**
            - 功能: <span style='color:red'>备份</span>
        3. 第三个副本
            - 保存位置
                - 不同机架上的随机节点
            - 功能: <span style='color:red'>提高安全性，但是IO慢</span>

## HDFS读数据的流程
[top](#catalog)
- HDFS 写数据的流程图
    - ![HDFS_ReadFile.png](./imgs/HDFS_ReadFile.png)
- 流程说明
    1. client 与 NN 交互

        |No|交互方向         |交互内容|
        |--|----------------|----------------------------------------------|
        |1 |                |client 创建 `Distributed FileSystem` 与 NN 交互|
        |2 |`client --> NN` |客户端请求下载文件X|
        |3 |`NN --> client` |返回目标文件的元数据(每个block在哪些节点上)|

    2. client 与 DN 交互

        |No|交互方向         |交互内容|
        |--|----------------|----------------------------------------------|
        |1 |                |client 创建 `FSDataInputStream` 与 DN 交互|
        |2 |`client --> DN_距离最近`                |向距离最近的节点发送请求，准备读取数据|
        |3 |`DN_距离最近 --> client`                |读取之后关闭流资源，将数据返回客户端|
        |4 |                |按照 2、3 的步骤，读取剩余的 Block|
        | |                ||

    3. 传输完成后关闭FSDataOutputStream所使用的资源

- DN 与 client 的数据传输方式
    - DN 从磁盘读取数据，以 Packet 为单位做校验
    - 客户端以 Packet 为单位接受数据
        1. 先写入缓存
        2. 然后再写入目标文件
    - client 会将读取的各个block拼接成一个文件
    - 读取各个block<span style='color:red'>不一定是一个并行的过程</span>
        - 如果存储各block的节点分布在不同的位置，则一个一个读
        - 如果节点集中在一起，则一次性都读取出来


# NN与2NN
## 元数据如何保存
[top](#catalog)
- NN的 `元数据` 如何保存？
    - 基本上尽量将元数据保存在**内存**，来**提高访问效率**
        - 如果存在磁盘中，因为经常需要进行**随机访问**来响应客户请求，效率比较低
    - 如何解决断电内存消失的问题
        - FsImage: 元数据在**磁盘**中的备份
            - 不会和内存中的元数据同时更新
        - Edits文件: 编辑日志
            - 通过Edits文件来防止断电后导致的数据丢失
            - 只进行追加操作，效率很高
            - 每当元数据有更新或者添加元数据时
                1. 将更新内容追加到Edits中
                2. 修改内存中的元数据
        - 内存记录=Edit(编辑日志)+FsImage(镜像文件)
    - Edit、FsImage 的定期合并
        - 如果数据都写到 Edit 文件中，会导致
            - **文件过大、效率降低**
            - 重启服务时，恢复元数据的时间过长
        - 为了防止 Edit 文件过大，需要: **定期合并 Edit + FsImage**，生成**新的元数据**

- <span style='color:red'>通过 2NN 来合并 FsImage + Edit <span>
    - 不能用 NN 来合并 FsImage + Edit
        - 在 NN 上合并效率比较低，因为 NN 还需要和client交互，处理文件上传/下载的请求
    - **为了合并 FsImage + Edit，专门引入了 2NN 节点**

## NN和2NN的工作流程
[top](#catalog)
- NN和2NN的工作流程图
    - ![HDSF_NN_2NN.png](./imgs/HDSF_NN_2NN.png)
- 流程说明
    1. 第一阶段---启动 NN
        1. 第一次 NN 格式化后，会创建 FsImage + Edits
        2. 如果不是第一次，会加载 FsImage + Edits 到**内存**
        3. client ---> NN，上传、删除、追加文件内容会修改 NN 中的元数据
        4. NN 将日志记录到 Edits 中
        5. **日志写完之后，NN再修改内存中的元数据**
    2. 第二阶段 --- 2NN 定期合并 FsImage + Edits

        |No|交互方向         |交互内容|
        |--|----------------|----------------------------------------------|
        |1 |`2NN --> NN`    |2NN 发送请求，询问 NN **是否需要** `CheckPoint`|
        |2 |`NN --> 2NN`    |将是否需要 `CheckPoint` 的结果返回给 2NN|
        |3 |`2NN --> NN`    |如果需要，2NN 发送请求，请求执行 `CheckPoint`|
        |4 |`NN`            |NN 滚动 Edits。New_Edits 继续记录 NN 中发生的更新，Old_Edits 准备发送给 2NN|
        |5 |`NN --> 2NN`    |NN 将 Old_Edits 和 FsImage 发送给 2NN|
        |6 |`2NN`           |2NN 将 Old_Edits 和 FsImage 加载到内存，然后**合并**，生成新的镜像文件: `fsImage.chkpoint`|
        |7 |`2NN --> NN`    |将 `fsImage.chkpoint` 发送给 NN|
        |8 |`NN`            |NN 将 `fsImage.chkpoint` 重新命名为 `fsImage`|

- 合并之后，内存中的内容= New_Edits + FsImage
    - NN 中不会产生新的合并，因为 NN 没有断电一直在更新
    - 合并后的状态相当于: `元数据 = New_Edits + Old_Edits + Old_FsImage`

## NN和2NN的注意事项
[top](#catalog)
- 2NN 合并 fsImage+edits 时，需要 和 NN 相同的内存
- 为了减小不必要的**内存占用** 和 **内存竞争**，2NN 和 NN **尽量分布在不同的节点上**

## FsImage、Edits解析
[top](#catalog)
- fsImage
    - HDFS文件系统元数据的一个**永久性检查点**，其中包含HDFS文件系统的所有目录和文件inode的序列化信息
    - fsimage中不记录数据块所对应的 DataNode，在集群启动后要求 DataNode 上报数据快信息，并且间隔一段时间再次上报
- edits
    - 存放HDFS文件系统的所有更新操作的路径，文件系统客户端执行的所有写操作首先会被记录到edits文件中

- `seen_txid文件`
    - 保存一个数字，等于最新日志：`edits_inprogress_xxxxx`中的数字
    - **通过该文件，使 NameNode 来确定每次开机启动时合并哪些Edits**
- fsImage 和 edits 的保存目录
    - NN：`data/tmp/dfs/name/current`
        - 在该目录下会比2NN目录下多一个最新的日志：`edits_inprogress_xxxxx`
    - 2NN：`data/tmp/dfs/namesecondary/current`
- `hdfs oiv`指令 查看镜像文件
    - `hdfs oiv -p XML -i fsImage文件`，通过xml方式查看镜像文件
    - `hdfs oiv -p XML -i fsImage文件 -o 输出文件`，通过xml方式查看镜像文件，并输出到指定文件
- `hdfs oev`指令 查看edits文件
    - `hdfs oev -p XML -i fsImage文件`，通过xml方式查看镜像文件
    - `hdfs oev -p XML -i fsImage文件 -o 输出文件`，通过xml方式查看镜像文件，并输出到指定文件

## CheckPoint--2NN检查点设置
[top](#catalog)
- CheckPoint的触发条件
    1. **定时时间到了**
    2. **Edits中的数据满了(默认100万条)**

- CheckPoint的触发条件的**默认配置**
    - 时间设置-配置 `hdfs-default.xml`
        - 2NN 默认每隔 1 小时执行一次
            ```xml
            <property>
                <name>dfs.namenode.checkpoint.period</name>
                <value>3600</value> <!--时间间隔-->
            </property>
            ```
    - 容量设置-配置 `hdfs-default.xml`
        - 默认1分钟检查一次，1百万次操作后，2NN执行一次合并
            ```xml
            <property>
                <name>dfs.namenode.checkpoint.txns</name>
                <value>1000000</value>
                <description>操作动作次数</description>
            </property>

            <property>
                <name>dfs.namenode.checkpoint.check.period</name>
                <value>60</value>
                <description> 1分钟检查一次操作次数</description>
            </property >
            ```
    - 如何检查到了 1 百万次？
        - 每隔`dfs.namenode.checkpoint.check.period`时间去检查一次

## NN的故障处理
### 处理方式1---拷贝2NN的数据
[top](#catalog)
- 主要处理方式
    - 出现异常后，直接从 2NN 的服务器上拷贝元数据
- 处理流程
    1. 准备 NN 的故障环境
        - 准备：集群启动状态下，关闭NN进程
            - `kill -9 NameNode_jps进程编号`
        - 删除 NameNode 存储的数据
            - `rm -rf /opt/module/hadoop-2.7.2/data/tmp/dfs/name *`
    2. 拷贝2NN的数据到NN的name目录下
        - `scp -r user@2NN:/opt/module/hadoop-2.7.2/data/tmp/dfs/namesecondary/*  user@NN:/opt/module/hadoop-2.7.2/data/tmp/dfs/name`
    3. 重启 NN
        - sbin/hadoop-daemon.sh start namenode
    4. 启动后会先进入安全模式，然后才能使用

### 处理方法2---在NN的守护进程下拷贝2NN的数据
[top](#catalog)
- 主要处理方式
    - 使用 `-importCheckpoint` 选项启动 NN 的守护进程，从而将 2NN 中数据拷贝到 NN目录中
- 处理流程
    1. 准备 NN 的故障环境
        - 修改配置 `hdfs-site.xml`
            ```xml
            <!-- 修改checkpoint的检查时间（缩短检查时间，保证及时备份） -->
            <property>
                <name>dfs.namenode.checkpoint.period</name>
                <value>120</value>
            </property>

            <!-- 设置namenode的保存路径 -->
            <property>
                <name>dfs.namenode.name.dir</name>
                <value>/opt/module/hadoop-2.7.2/data/tmp/dfs/name</value>
            </property>
            ```
        - 修改后分发到各个节点
        - 强制关闭 NN: `kill -9 NN_jps进程编号`
        - 删除NameNode的数据
            - `rm -rf /opt/module/hadoop-2.7.2/data/tmp/dfs/name/*`
    2. 如果 2NN 和 NN 不再一个主机节点上，需要：
        1. 将2NN数据拷贝到NN数据的同级目录下，`namesecondary` 完整拷贝
        2. 在 2NN 服务器下，删除拷贝后的 2NN 目录下的 `in_use.lock` 文件
    3. 使用 NN 的`守护进程`导入检查点数据
        - `bin/hdfs namenode -importCheckpoint`
        - 一段时间之后可以使用 `ctrl+c` 来关闭程序，或者等待自动关闭
        - 守护进程启动时， NN 也是启动的; 关闭守护进程后 NN 被关闭
    4. 重启NN
        - `sbin/hadoop-daemon.sh start namenode`

## 集群安全模式
[top](#catalog)
- 集群在安全模式下，不能执行写操作；集群启动完成后，自动退出安全模式
- NN **启动时**的安全模式
    - NN 启动时的操作
        1. NN 启动时，载入FsImage、并执行Edits中的操作
        2. 最终在内存中建立文件系统的元数据映像
        3. 然后创建一个新FsImage和空Edits
    - 在启动过程中，NN一直运行在安全模式，文件系统对于客户端是只读的
- DN的在安全模式中的处理
    - 在 NN 的安全模式下，`DN ---> NN` 发送最新的块列表信息
    - NN 了解到足够多的块信息之后，才能高效运行文件系统
- 如何**退出**安全模式
    - **满足最 `小副本条件`，NN在30秒之后退出安全模式**
    - `最小副本条件`: 整个文件系统 99.9% 的块满足最小副本级别
    - 退出策略的优点
        - 不需要集群中的所有节点都启动才能正常启动，只要满足最小副本条件即可视作正常启动
        - 可以防止某些节点损坏而影响整个集群的启动
        - 默认: `dfs.replication.min=1`，即每个文件至少有副本=1
- <span style='color:red'>对于一个刚刚格式化的系统，因为系统内没有任何块，所以不会进入安全模式</span>

- 指令操作

    |指令|功能|备注|
    |-|-|-|
    |`bin/hdfs dfsadmin -safemode get`|查看安全模式|还可以从 `50070` 下的 `Overview` 下的 `Summary` 来查看安全模式的状态|
    |`bin/hdfs dfsadmin -safemode enter`|进入安全模式|客户端是只读的，**无法执行写操作，可以进行读操作**<br>web端的 50070 也会同步显示|
    |`bin/hdfs dfsadmin -safemode leave`|离开安全模式||
    |`bin/hdfs dfsadmin -safemode wait`|等待安全模式结束|安全模式启动时，使用wait指令来阻塞后续执行|
    ||||


- `bin/hdfs dfsadmin -safemode wait` 的使用示例
    1. 开启两个命令行窗口
    2. 在窗口A 中启动安全模式: `bin/hdfs dfsadmin -safemode enter`
    3. 在窗口B 创建一个 shell 并执行
        ```sh
        # 1. 阻塞并等待安全模式结束
        hdfs dfsadmin -safemode wait
        # 2. 安全模式结束后，立刻执行上传指令
        hdfs dfs -put /opt/module/hadoop-2.7.2/README.txt /
        ```
    4. 在窗口A 中立刻安全模式: `bin/hdfs dfsadmin -safemode leave`
    5. 窗口B 被阻塞的 shell 继续执行，会将文件上传到 hdfs


## NN的多目录配置
[top](#catalog)
- 将 NN 的**本地目录** 配置成多个，且每个目录**保存相同的内容**，可以增加可靠性
- 多目录配置的本质
    - 就是 NN 的数据备份
    - 每个目录存放的数据相同，保证可靠性
- 多目录配置: `hdfs-site.xml`
    ```xml
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>file:///${hadoop.tmp.dir}/dfs/name1,file:///${hadoop.tmp.dir}/dfs/name2</value>
    </property>
    ```
- 配置多目录需要
    1. 停止集群
    2. 删除 `data` 和 `logs` 目录，并**重新格式化 NN**
    3. 重启 NN


# DN
## DN中每个Block的内容
[top](#catalog)
- 数据
- 元数据
    - 数据长度
    - 校验和，CheckSum
        - 负责DataNode数据的完整性
    - 时间戳

## DN如何保证每个Block的数据完整性
[top](#catalog)
- 当 DN 读取 Block 时，会计算 `校验和`
- 如果读取 Block 时计算的校验和 与 Block 创建时的不同，说明 Block 已经损坏
- DN 会对 Block 进行周期性校验

## DN的工作机制
[top](#catalog)
- 工作机制图
    ![HDFS_DN.png](./imgs/HDFS_DN.png)
- 流程说明

    |No  |方向|处理内容|
    |----|-----------------|-|
    |1   |`DN-->NN`             |DN 启动后向 NN 进行注册|
    |2   |`NN-->DN`             |NN 将 DN 的注册信息写入元数据，然后通知 DN 是否注册成功|
    |3   |`DN-->NN * 每周期`    |每周期(1小时)，DN 向 NN 上报所有块的信息|
    |4.1 |`DN-->NN * 心跳帧`    |心跳检测，每 3s 一次，向 NN 发送心跳包，并返回 NN 给 DN 的命令|
    |4.2 |`NN-->DN * 心跳最大延迟`    |心跳检测，超过 `10分钟 + 30s` 没有收到 DN 的心跳包后，则认为该节点不可用|

- 心跳包的返回结果
    - 会返回 NN 给 DN 需要执行的命令，如:
        - 复制块数据到另一台机器
        - 删除某个数据块
- 超过 10 分钟没有收到心跳包，则认为节点不可用

## 掉线时限参数设置
[top](#catalog)
- 如何判定 DN 节点失效
    1. DN进程死亡
    2. 网络故障造成 DN 和 NN 无法通信
- NN 如何判断 DN 失效
    - 根据心跳包来判断
    - 偶尔未接受到心跳包，NN不会立刻判定失效的节点
    - 在一段时间内，一直没有收到心跳包，会判定DN节点失效，这段时间称为**超时时长**
    - HDFS默认的超时时长为：**10分钟 + 30秒**
        - 计算公式：
            - `timeout =2 * dfs.namenode.heartbeat.recheck-interval + 10 * dfs.heartbeat.interval`
            - 默认：dfs.namenode.heartbeat.recheck-interval=5分钟
            - 默认：dfs.heartbeat.interval=3秒
- 配置信息 `hdfs-site.xml`
    ```xml
    <property>
        <name>dfs.namenode.heartbeat.recheck-interval</name>
        <!-- 单位: 毫秒 -->
        <value>300000</value>
    </property>
    <property>
        <name>dfs.heartbeat.interval</name>
        <!-- 单位: 秒 -->
        <value>3</value>
    </property>
    ```

## 动态增删节点DN
### 方式1--克隆节点
[top](#catalog)
- 添加流程
    1. 克隆一个正在使用的节点
    2. 删除data/和logs/目录
    3. 启动 DN 和 NM
- 这样添加是直接使用克隆节点的信息来进行的
- 如果克隆节点的信息泄漏，可能会导致数据被放到未知的节点上

### 方式2--添加白名单
[top](#catalog)
- 白名单内的节点都允许访问 NN，不在白名单的节点会**退出**
- <span style='color:red'>黑名单和白名单不能同时出现同一个主机名称，否则会出现异常</span>
- 添加流程
    1. 在: `etc/hadoop` 下创建 `dfs.hosts` 文件
        - 在文件内添加主机名称，一行一个
        - 文件中不能有其他文字和空格
    2. 在 `hdfs-site.xml` 配置文件中增加 `dfs.hosts` 的路径
        ```xml
        <property>
            <name>dfs.hosts</name>
            <value>/opt/module/hadoop-2.7.2/etc/hadoop/dfs.hosts</value>
        </property>
        ```
    3. 将配置分文件分发到个节点
    4. 刷新节点，**在一台机器上执行即可**
        - 刷新NN: `hdfs dfsadmin -refreshNodes`
        - 刷新RM: `yarn rmadmin -refreshNodes`
- 负载均衡处理
    - 使用白名单后，如果有节点下线，可能会导致各个节点的负载不均衡
    - 可以用命令实现集群的再平衡: `sbin/start-blancer.sh`

### 方法3--添加黑名单
[top](#catalog)
- 黑名单中的节点会**强制退出**
- <span style='color:red'>黑名单和白名单不能同时出现同一个主机名称，否则会出现异常</span>
- 添加流程
    1. 在 `etc/hadoop` 下创建 `dfs.hosts.exclude` 文件
        - 在文件中添加主机名
    2. 在 `hdfs-site.xml` 配置文件中增加 `dfs.hosts.exclude` 属性
        ```xml
        <property>
            <name>dfs.hosts.exclude</name>
            <value>/opt/module/hadoop-2.7.2/etc/hadoop/dfs.hosts.exclude</value>
        </property>
        ```
    3. 将配置文件分发到各个节点
    4. 刷新节点，**在一台机器上执行即可**
        - 刷新NN: `hdfs dfsadmin -refreshNodes`
        - 刷新RM: `yarn rmadmin -refreshNodes`
- 执行黑名单时的节点状态
    - `decommission in progress`，表示节点**正在退役中**
        - 此时会将当前节点的块数据拷贝到其他节点
    - `decommission`，表示退役完成
        - 即数据块已经拷贝完毕
- 无法退出的情况
    - 当 `服役的节点数 < 数据副本数` 时，无法退役
    - 这种情况<span style='color:red'>需要修改副本数</span>后，才能退役
- 负载均衡处理
    - 使用黑名单后，如果有节点下线，可能会导致各个节点的负载不均衡
    - 可以用命令实现集群的再平衡: `sbin/start-blancer.sh`

## DN多目录配置
[top](#catalog)
- DN 多目录配置的特点
    - 每个目录存储的数据**不相同**
    - 目录中存储的<span style='color:red'>不是数据副本，与 NN 不同</span>
- 配置流程
    1. 在hdfs-site.xml中添加配置
        ```xml
        <property>
            <name>dfs.datanode.data.dir</name>
            <!-- 指定多个 DN 目录，用 `,` 分隔 -->
            <value>file:///${hadoop.tmp.dir}/dfs/data1,file:///${hadoop.tmp.dir}/dfs/data2</value>
        </property>
        ```
    2. 停止集群
    3. 删除 NN 下的 data、logs 目录
    4. 重新格式化 NN
    5. 重启集群

# HDFS2.X的特性
## 集群间数据拷贝
[top](#catalog)
- 实现方式1: scp 实现
    - 推push：scp -r hello.txt root@YYY:/xxxxx
    - 拉pull：scp -r root@YYY:/xxxxx hello.txt
    - 中介：scp -r root@YYY01:/xxxxx root@YYY02:/zzzz
        - 从 `YYY01` 拷贝到 `YYY02`
    - 当两个远程主机没有配置ssh的情况下，可以使用
- 实现方式2: hadoop 的 `distcp` 命令，实现集群间的递归数据复制
    - `bin/hadoop distcp hdfs://主机名1:9000/.... hdfs://主机名2:9000/....`

## 小文件存档
[top](#catalog)
- 用 HDFS 存储小文件的问题
    - HDFS 上的每个文件均按块存储，每个块的元数据存储在 NN 的内存中
    - 当 HDFS 上存在大量小文件时，可能会耗尽 NN 的内存

- <span style='color:red'>文件所需磁盘容量与数据块无关</span>
    - 如小文件大小为1M，虽然该文件占用1个块 128M，但实际上使用的容量还是1M

- HAR文件 / HDFS存档文件 解决存储小文件
    - HAR文件/HDFS存档文件
        - 一个更高效的文件存档工具，**允许对文件进行透明的访问**
        - 该文件内部是多个小文件
        - 将 HAR 文件存入HDFS块后，**对内**是**多个小文件**，**对于NN是一个整体**，减少了NN的内存
- `多个小文件 ---> HAR文件` 本质上是一个 **MapReduce的过程**
- HAR文件的创建方法
    1. 启动yarn `start-yarn.sh`
    2. 将多个小文件保存到某个目录下，作为 `打包目录`
    3. 执行打包指令
        - `hadoop archive -archiveName <归档文件名>.har -p 打包目录 存储目录`
        - 将打包目录打包成 HAR 文件，并输出到存储目录下
        - 归档文件必须是: `*.har`
        - 执行指令时，存储目录必须**不存在**

- 使用har文件的时候需要最使用har协议，即har://
    - 直接查看
        - `hadoop fs -ls -R har:///路径/xx.har`
    - 解压
        - `hadoop fs -cp har:///路径/xx.har/* 解压路径`

## 回收站
[top](#catalog)
- 回收站的功能
    - 可以将删除的文件在不超时的情况下，恢复原数据，防止误删、备份的作用
- 回收站**默认是关闭的**，因为Hadoop的数据多用于存储与分析
- 开启回收站
    - 配置 `core-site.xml` 的两个参数
        1. `fs.trash.interval=0`
            - 表示文件在回收站的**保留时间**
            - **默认值** `0`， 表示禁用回收站
        2. `fs.trash.checkpoint.interval=0`
            - 表示检查回收站的间隔时间
            - 如果为0，则该设置**等于** `fs.trash.interval`
    - 参数要求: `fs.trash.checkpoint.interval <= fs.trash.interval`

- 回收站目录 ?????
    - 执行删除后会在HDFS上生成文件夹：`/user/用户名/.Trash`
    - 修改访问垃圾回收站的用户名称
    - 进入垃圾回收站用户名称，默认是dr.who，可以通过配置来修改用户
    - 配置core-site.xml
        ```xml
        <property>
            <name>hadoop.http.staticuser.user</name>
            <value>userid</value>
        </property>
        ```

- 恢复数据
    - 使用 `hadoop fs mv`，将回收站中的数据移动到别的目录下
- 清空回收站
    - `hadoop fs -expunge`
    - 清空的操作只是将所有文件打了一个包，仍然是时间到了之后自动删除

## 快照管理
[top](#catalog)
- 快照的本质
    - 相当于对目录做一个备份
    - 不会立即复制所有文件，而是指向同一个文件
    - 当写入发生时，会产生新文件，来**记录文件的增减**
- 操作指令

    |指令|功能|
    |-|-|
    |`hdfs dfsadmin -allowSnapshot 路径`|开启指定目录的快照功能|
    |`hdfs dfsadmin -disallowSnapshot 路径`|禁用目录的快照功能，默认是禁用|
    |`hdfs dfs -createSnapshot 路径`|对目录创建快照<br>默认使用当前的系统时间作为该快照的名称|
    |`hfs dfs -createSnapshot 路径 名称`|指定名称创建快照|
    |`hfs dfs -renameSnapshot 路径 旧名称 新名称`|重命名快照|
    |`hdfs lsSnapshottableDir`|列出当前用户所有可快照目录|
    |`hdfs snapshotDiff 路径1 路径2`|比较两个快照目录的不同之处，使用`.`代表当前路径|
    |`hdfs dfs -deleteSnapshot <path> <snapshotName >`|删除快照|

- 创建快照之后，会在目录下增加一个隐藏目录：`.snapshot/`
    - 该目录下的文件内容同样可以进行拷贝等操作，来进行目录的恢复


[top](#catalog)