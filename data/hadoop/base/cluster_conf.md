<span id="catalog"></span>

<span style='font-size:18px'>目录---hadoop的目录结构、集群配置、常用端口号</span>

- [目录结构](#目录结构)
- [常用端口号](#常用端口号)
- [三种类型的配置](#三种类型的配置)
- [集群配置方式1---本地模式](#集群配置方式1---本地模式)
- [集群配置方式2---伪分布式模式](#集群配置方式2---伪分布式模式)
    - [伪分布式模式与完全分布模式的区别](#伪分布式模式与完全分布模式的区别)
    - [伪分布式模式---需要启动的服务](#伪分布式模式---需要启动的服务)
    - [伪分布式模式---核心配置](#伪分布式模式---核心配置)
    - [伪分布式模式---HDFS配置](#伪分布式模式---HDFS配置)
    - [伪分布式模式---YARN配置](#伪分布式模式---YARN配置)
    - [伪分布式模式---MapReduce配置](#伪分布式模式---MapReduce配置)
    - [伪分布式模式---启动伪分布式集群](#伪分布式模式---启动伪分布式集群)
        - [启动伪分布式集群---启动HDFS](#启动伪分布式集群---启动HDFS)
        - [启动伪分布式集群---启动Yarn](#启动伪分布式集群---启动Yarn)
    - [伪分布式模式---历史服务器](#伪分布式模式---历史服务器)
    - [伪分布式模式---开启日志聚集](#伪分布式模式---开启日志聚集)
- [集群配置方式3---完全分布式](#集群配置方式3---完全分布式)
    - [完全分布式---虚拟机的配置](#完全分布式---虚拟机的配置)
    - [完全分布式---集群规划](#完全分布式---集群规划)
    - [完全分布式---xsync---集群间分发脚本](#完全分布式---xsync---集群间分发脚本)
    - [完全分布式---各组件的配置位置及配合方式](#完全分布式---各组件的配置位置及配合方式)
    - [完全分布式---核心配置](#完全分布式---核心配置)
    - [完全分布式---HDFS配置](#完全分布式---HDFS配置)
    - [完全分布式---YARN配置](#完全分布式---YARN配置)
    - [完全分布式---MapReduce配置](#完全分布式---MapReduce配置)
    - [完全分布式---手动启动每个单节点](#完全分布式---手动启动每个单节点)
    - [完全分布式---群起集群](#完全分布式---群起集群)
        - [配置shh免密连接](#配置shh免密连接)
        - [配置集群的slave机器](#配置集群的slave机器)
        - [启动集群](#启动集群)
        - [集群测试](#集群测试)
    - [集群时间同步](#集群时间同步)
- [需要注意的问题](#需要注意的问题)
- [](#)

# 目录结构
[top](#catalog)

|目录|保存内容|
|-|-|
|**bin**|Hadoop相关服务的操作脚本，如 HDFS、YARN 的操作|
|**etc目录**|配置文件目录，默认配置都保存在 `etc/hadoop/` 目录下|
|lib目录|Hadoop的本地库，包含数据的压缩/解压缩功能处理|
|**sbin目录**|保存启动/停止Hadoop相关服务的脚本|
|share目录|存放Hadoop的依赖jar包、文档、和官方案例|
|include|其他代码的头文件|

# 常用端口号
[top](#catalog)

|端口号|功能|所处位置|
|-|-|-|
|9000|HDFS中 `NameNode`的地址|服务器|
|50070|查看 `HDFS`|web端口|
|50090|辅助节点 `SecondaryNameNode` 的主机地址|web端口|
|8088|查看 `MapReduce`|服务器|
|10020|历史服务器端地址|服务器|
|19888|历史服务器web端地址|web端口|

- 使用web端口访问前，需要关闭防火墙
    - 查看防火墙状态 `firewall-cmd --state`
    - 关闭防火墙: `systemctl stop firewalld.service`
    - 如果是集群，需要关闭所有虚拟机的防火墙

# 三种类型的配置
[top](#catalog)
- 所有配置的保存目录: `/opt/module/hadoop-2.7.2/etc/hadoop/`
- 配置文件分两类
    - 默认配置文件: `xxx-defauls.xml`
    - 自定义配置文件: `xxx-site.xml`
    - 环境配置: `xxx-env.sh`
- 需要配置的是**自定义配置文件**，包括
    - core-site.xml
    - hdfs-site.xml
    - yarn-site.xml
    - mapred-site.xml
- <span style='color:red'>环境配置都需要配置 `JAVA_HOME`</span>

# 集群配置方式1---本地模式
[top](#catalog)
- 本地模式不需要特殊配置直接使用即可
- 创建文件目录
    - `mkdir /opt/module/input`
- 拷贝输入文件
    - `cp etc/hadoop/*.xml input/`
- 执行hadoop提供的示例程序---单词过滤
    1. 切换到 hadoop 目录
        - `cd /opt/module/hadoop-2.7.2`
    2. 执行之前需要检查有没有输出目录 `output/`，如果有则删除
        - 否则会抛出 `FileAlreadyExistException` 异常
    3. 创建input目录
        - `mkdir input`
    4. 拷贝xml文件到 `input` 目录下
        - `cp etc/hadoop/input/*.xml input/`
    5. 执行程序
        - `bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar grep input output 'dfs[a-z.]+'`
    6. 查看执行结果
        - `cat output/part-r-00000`
- 执行hadoop提供的示例程序--单词统计
    1. 切换到 hadoop 目录
        - `cd /opt/module/hadoop-2.7.2`
    2. 创建 `wcinput` 目录
        - `mkdir wcinput`
    3. 创建单词文件
        - `touch wcinput/wc.input`
        - 并添加一些单词
    4. 执行程序
        - `hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar wordcount wcinput wcoutput`
    5. 检查执行结果
        - `cat wcoutput/part-r-00000`

# 集群配置方式2---伪分布式模式
## 伪分布式模式与完全分布模式的区别
[top](#catalog)
- 配置相同，但是只有一台服务器

## 伪分布式模式---需要启动的服务
[top](#catalog)
- 需要启动的服务

    |服务|所属的组件|
    |-|-|
    |namenode|HDFS|
    |datanode|HDFS|
    |resourcemanager|YARN|
    |nodemanager|YARN|
    |historyserver<br>（可以不启动）|历史服务器|

## 伪分布式模式---核心配置
[top](#catalog)
- <span style='color:red'>修改 `core-site.xml` 必须重启</span>
- 参考: [src/cluster_conf/dummy_cluster/core-site.xml](src/cluster_conf/dummy_cluster/core-site.xml)
- `core-site.xml`
    ```xml
    <!-- 指定HDFS中NameNode的地址，hd01 是主机名 -->
    <property>
    <name>fs.defaultFS</name>
        <value>hdfs://hd01:9000</value>
    </property>

    <!-- 指定Hadoop运行时产生文件的存储目录，即 data 目录 -->
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/opt/module/hadoop-2.7.2/data/tmp</value>
    </property>
    ```

## 伪分布式模式---HDFS配置
[top](#catalog)
- 参考: [src/cluster_conf/dummy_cluster/hdfs](src/cluster_conf/dummy_cluster/hdfs)

- `hadoop-env.sh`，手动配置JAVA_HOME
    ```sh
    export JAVA_HOME=/opt/module/jdk1.8.0_144
    ```
- `hdfs-site.xml`
    - 配置内容
        ```xml
        <!-- 指定HDFS副本的数量 -->
        <property>
            <name>dfs.replication</name>
            <value>1</value>
        </property>
        ```
    - hdfs 默认副本数为 `3`
    - 如果只有一台机器，副本数量=3，**在实际运行时只会有一份数据**
        - 当机器增加时，才会在增加的机器上进行备份
        - 该操作是自动控制的

## 伪分布式模式---YARN配置
[top](#catalog)
- 参考: [src/cluster_conf/dummy_cluster/yarn](src/cluster_conf/dummy_cluster/yarn)

- `yarn-env.sh`，yarn启动设置，需要手动配置JAVA_HOME
    ```sh
    export JAVA_HOME=/opt/module/jdk1.8.0_144
    ```

- `yarn-site.xml`，yarn配置
    ```xml
    <!-- Reducer 获取数据的方式 -->
    <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
    </property>

    <!-- 指定 YARN 的 ResourceManager 的地址，hd01 是主机名 -->
    <property>
    <name>yarn.resourcemanager.hostname</name>
    <value>hd01</value>
    </property>
    ```

## 伪分布式模式---MapReduce配置
[top](#catalog)
- 参考: [src/cluster_conf/dummy_cluster/mapreduce](src/cluster_conf/dummy_cluster/mapreduce)

- `mapred-env.sh`，mapreduce启动配置，需要手动配置JAVA_HOME
    ```sh
    export JAVA_HOME=/opt/module/jdk1.8.0_144
    ```
- `mapred-site.xml`，mapreduce配置
    - 备份 `mapred-site.xml.template`， 并命名为: `mapred-site.xml`
    - 添加配置
        ```xml
        <!-- 指定 MapReduce 计算操作运行在 YARN 上 -->
        <property>
                <name>mapreduce.framework.name</name>
                <value>yarn</value>
        </property>
        ```

## 伪分布式模式---启动伪分布式集群
### 启动伪分布式集群---启动HDFS
[top](#catalog)
1. 格式化NameNode
    1. 切换到 hadoop 目录
        - `cd /opt/module/hadoop-2.7.2`
    2. 格式化NameNode
        - 格式化之前，需要清空数据。清理步骤
            1. 关闭进程: `namenode`，`datanode`
            2. 删除目录: `data/`、`logs/`
            3. 执行格式化
        - 格式化指令
            - `bin/hdfs namenode -format`
2. 启动 HDFS
    1. 先启动 `NameNode`
        - `sbin/hadoop-daemon.sh start namenode`
        - 用 `jps` 检查是否包含名为 `NameNode` 的进程
    2. 再启动 `DataNode`
        - `sbin/hadoop-daemon.sh start datanode`
        - 用 `jps` 检查是否包含名为 `DataNode` 的进程

3. 从外部访问 HDFS 的 web 端口
    - 访问方式
        1. IP + 端口: `192.168.abc.xxx:50070`
        2. `hosts` 文件中配置的名字: `名字:50070`
            - 需要在web端的及其上配置主机映射
    - **一定要关闭防火墙**
        - 切换到 `root`
        - 查看防火墙状态 `firewall-cmd --state`
        - 关闭防火墙: `systemctl stop firewalld.service`

4. 执行 hdfs 操作，检查 hdfs 是否可用

    |指令|功能|
    |-|-|
    |创建输出数据目录|`hdfs dfs -mkdir -p /user/input`|
    |拷贝本地数据到集群|`hdsf dfs -put wcinput/wc.input /user/input`|
    |查看文件列表|`hdfs dfs -ls  /user/input`|
    |输出文件内容|`hdfs dfs -cat  /user/input/wc.input`|

### 启动伪分布式集群---启动Yarn
[top](#catalog)
1. 启动 `ResourceManager` 和 `NodeManager`
    - `sbin/yarn-daemon.sh start resourcemanager`
    - `sbin/yarn-daemon.sh start nodemanager`

2. 用jsp检查是否有: `resourcemanager`, `nodemanager` 的进程

3. 使用 hadoop 示例程序测试是否启动成功
    1. 切换到 hadoop 目录
        - `/opt/module/hadoop-2.7.2
    2. 创建单词文件: `wc.input`，并上传到 hdfs: `/user/input/wc.input`
    2. 执行 `wordcount` 示例程序
        - `hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar wordcount /user/input/wc.input /user/input/wcoutput`
        - 执行前不能有输出目录：`/user/output`

## 伪分布式模式---历史服务器
[top](#catalog)
- 参考: [src/cluster_conf/dummy_cluster/yarn/mapred-site.xml](#src/cluster_conf/dummy_cluster/yarn/mapred-site.xml)

- 配置 `mapred-site.xml`
    ```xml
    <!-- 历史服务器端地址，hd01 是主机名 -->
    <property>
    <name>mapreduce.jobhistory.address</name>
    <value>hd01:10020</value>
    </property>
    <!-- 历史服务器web端地址，hd01 是主机名 -->
    <property>
        <name>mapreduce.jobhistory.webapp.address</name>
        <value>hd01:19888</value>
    </property>
    ```
- 启动服务
    - `sbin/mr-jobhistory-daemon.sh start historyserver`
    - 不需要重启其他服务，在端口号19888进行跳转

## 伪分布式模式---开启日志聚集
[top](#catalog)
1. 开启日志前，**先关闭** yarn 上的相关服务，包括
    - nodemanager
    - resourcemanager
    - historyserver
2. 配置`yarn-site.xml`
    ```xml
    <!-- 日志聚集功能使能 -->
    <property>
    <name>yarn.log-aggregation-enable</name>
            <value>true</value>
    </property>

    <!-- 日志保留时间设置7天 -->
    <property>
    <name>yarn.log-aggregation.retain-seconds</name>
    <value>604800</value>
    </property>
    ```
3. 修改完配置后，重启: nodemanager、resourcemanager、historyserver

# 集群配置方式3---完全分布式
## 完全分布式---虚拟机的配置
[top](#catalog)
- 需要三台虚拟机：hd02、hd03、hd04
- 虚拟机配置
    - 修改每一台机器的网络配置，来固定 IP
        - `vim /etc/sysconfig/network-scripts/ifcfg-ensXX`
        - 需要修改固定IP: `IPADDR0`
        - 三台主机的IP需要互不相同
    - 修改主机名: `vim /etc/hostname`
    - 配置主机映射名: `vim /etc/sysconfig/network`
        - 修改 `hostname`
    - 添加**其他主机**的主机映射: `vim /etc/hosts`
    - 修改后重启机器

## 完全分布式---集群规划
[top](#catalog)
- 集群规划

    ||hd02|hd03|hd04|
    |-|-|-|-|
    |HDFS|NameNode<br>DataNode|DataNode|SecondaryNameNode<br>DataNode|
    |YARN|NodeManager|ResourceManager<br>NodeManager|NodeManager|

- 规划方式
    - hd02 负责: hdfs 的 `NameNode`
    - hd03 负责: yarn 的 `ResourceManager`
    - hd04 负责: hdfs 的 `SecondaryNameNode`
    - 同时每个机器都是一个 `DataNode` 和 `NodeManager`
    - 即
        - hdfs 上的规划
            - 1 个 `NameNode`: hd02
            - 1 个 `SecondaryNameNode`: hd04
            - 3 个 `DataNode`: hd02、hd03、hd04
        - yarn 上的规划
            - 一个 `ResourceManager`: hd03
            - 三个 `NodeManager`: hd02、hd03、hd04

- HDFS 规划的注意事项
    - **NameNode 和 SecondaryNameNode 的内存比例为1:1，这两者不能放在同一台机器上**
        - 如果放在一台机器上，NN、2NN 各占用 `n/2` 的容量，会影响性能
- YARN 规划的注意事项
    - `ResourceManager` 要避开 `NameNode`、`SecondaryNameNode`，所以要单独存放

## 完全分布式---xsync---集群间分发脚本
[top](#catalog)
- 可以进行服务器间数据拷贝的指令
    - scp
        - 推送: `scp -r 当前用户的文件 目的用户@主机名:路径`
            - 从当前用户的文件推送到目标主机上
        - 拉取: `scp -r 目的用户@主机名:路径 当前用户的文件`
            - 从目标主机上拉取当前用户的文件推
    - rsync 远程同步工具
        - 比scp要快，只更新差异文件
        - `rsync -rvl 当前用户的文件 目的用户@主机名:路径`
            - -r，递归
            - -v，显示复制过程
            - -l，拷贝符号连接
        - 如果没有需要手动安装: `yum -y install rsync`
- 集群间分发脚本: `xsync`
    - 脚本应该放在 `$HOME/bin` 目录下，这样可以在任意目录下直接使用
    - `xsync` 代码内容
        ```sh
        #!/bin/bash
        #1 获取输入参数个数，如果没有参数，直接退出
        pcount=$#
        if((pcount==0)); then
        echo no args;
        exit;
        fi

        #2 获取文件名称
        p1=$1
        fname=`basename $p1`
        echo fname=$fname

        #3 获取上级目录到绝对路径
        pdir=`cd -P $(dirname $p1); pwd`
        echo pdir=$pdir

        #4 获取当前用户名称
        user=`whoami`

        #5 循环，从 hd03 开始 到 hd04
        for((host=3; host<5; host++)); do
                echo ------------------- hadoop$host --------------
                rsync -rvl $pdir/$fname $user@hd0$host:$pdir
        done
        ```

## 完全分布式---各组件的配置位置及配合方式
[top](#catalog)
- 所有配置的保存目录: `/opt/module/hadoop-2.7.2/etc/hadoop/`

- 集群中包含的配置
    - core-site.xml
    - hdfs
        - hadoop-env.sh
        - hdfs-site.xml
    - yarn
        - yarn-env.sh
        - yarn-site.xml
    - mapreduce
        - mapred-env.sh
        - mapred-site.xml
    - slaves

- 整个集群需要 slave 和 site 配置文件相结合才能工作

- 各个组件的配置位置
    - 2NN 在 `hdfs-site.xml` 中配置
    - NN 在 `core-site.xml` 中配置
    - RM 在 `yarn-site.xml` 中配置


- 集群配置的配合方式
    - core-site.xml、hdfs、yarn、mapreduce，负责整个Hadoop的运行
    - 整个集群中有哪些机器由 slaves 负责

## 完全分布式---核心配置
[top](#catalog)
- <span style='color:red'>修改 `core-site.xml` 必须重启</span>
- 参考: [src/cluster_conf/real_cluster/core-site.xml](src/cluster_conf/real_cluster/core-site.xml)

- `core-site.xml`
    ```xml
    <!-- 指定HDFS中NameNode的地址，hd02 是主机名 -->
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://hd02:9000</value>
    </property>

    <!-- 指定Hadoop运行时产生文件的存储目录 -->
    <property>
            <name>hadoop.tmp.dir</name>
            <value>/opt/module/hadoop-2.7.2/data/tmp</value>
    </property>
    ```

## 完全分布式---HDFS配置
[top](#catalog)
- 参考: [src/cluster_conf/real_cluster/hdfs](src/cluster_conf/real_cluster/hdfs)

- `hadoop-env.sh`，手动配置JAVA_HOME
    ```sh
    export JAVA_HOME=/opt/module/jdk1.8.0_144
    ```
- `hdfs-site.xml`
    ```xml
    <!-- 指定HDFS副本的数量 -->
    <property>
            <name>dfs.replication</name>
            <value>3</value>
    </property>

    <!-- 指定Hadoop辅助名称节点主机配置 -->
    <property>
        <name>dfs.namenode.secondary.http-address</name>
        <value>hd04:50090</value>
    </property>
    ```

## 完全分布式---YARN配置
[top](#catalog)
- 参考: [src/cluster_conf/real_cluster/yarn](src/cluster_conf/real_cluster/yarn)

- n-env.sh`，yarn启动设置，需要手动配置JAVA_HOME
    ```sh
    export JAVA_HOME=/opt/module/jdk1.8.0_144
    ```
- `yarn-site.xml`，yarn配置
    ```xml
    <!-- Reducer获取数据的方式 -->
    <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
    </property>

    <!-- 指定YARN的ResourceManager的地址，hd03 是主机名 -->
    <property>
            <name>yarn.resourcemanager.hostname</name>
            <value>hd03</value>
    </property>
    ```

## 完全分布式---MapReduce配置
[top](#catalog)
- 参考: [src/cluster_conf/real_cluster/mapreduce](src/cluster_conf/real_cluster/mapreduce)

- `mapred-env.sh`，mapreduce启动配置，需要手动配置 JAVA_HOME
    ```sh
    export JAVA_HOME=/opt/module/jdk1.8.0_144
    ```
- `mapred-site.xml`，mapreduce配置
    - 备份 `mapred-site.xml.template`， 并命名为: `mapred-site.xml`
    - 添加配置
        ```xml
        <!-- 指定 MapReduce 计算操作运行在 YARN 上 -->
        <property>
                <name>mapreduce.framework.name</name>
                <value>yarn</value>
        </property>
        ```

## 完全分布式---手动启动每个单节点
[top](#catalog)
- 集群单节点启动，即: **手动的、一个一个结点启动**
- 启动之前需要删除所有机器的`data/`、`log/` 目录
- 初次启动时，需要初始化 `NameNode`
    - 按照集群规划，只初始化hd02
    - `bin/hdfs namenode -format`

- 启动namenode、datanode
    - 002=NN、DN
    - 003=DN
    - 004=2NN、DN
- 启动: resourcemanager、nodemanager
    - 002=NM
    - 003=RM、NM
    - 004=NM

## 完全分布式---群起集群
### 配置shh免密连接
[top](#catalog)
- 免密登录的原理
    1. serverA 生成 公钥A 和 私钥A
    2. 将 公钥A 拷贝到 serverB 中
    3. 在 serverA 通过 ssh 访问 serverB
    4. 使用 **私钥A** 加密数据，然后发送给 serverB
    5. serverB 接受到数据后，使用 **公钥A** 解密数据
    6. 处理结束后，serverB 将响应数据用 **公钥A** 加密，然后发送给 serverA
    7. serverA 接受到响应时，用 **私钥A** 解密
- 免密登录的示意图
    ```
                私钥A 加密
        serverA ────────>>> serverB
            ^                   │
            ^                   │
            │                   │
            │                   │
　　　　私钥A 解密           公钥A 解密
            │                   │
            │                   V
            │                   V
　　    serverA <<<──────── serverB
                公钥A 加密
    ```

- **为什么要配置免密连接**？
    - namenode 需要和其他的节点进行远程通讯
    - 防止在通信过程中的秘密输入操作

- 配置普通用户的免密登录
    1. 在 `hd02` 中生成密钥
        1. 切换目录: `cd $HOME/.ssh`
            - 如果没有 `.ssh` 目录，需要执行: `ssh localhost`，或者手动创建: `mkdir $HOME/.ssh`
        2. 生成密钥: `ssh-keygen -t rsa`
            - 执行后按 3 次回车
            - 会生成两个文件
                - 私钥: `id_rsa`
                - 公钥: `id_rsa.pub`
        3. 拷贝公钥到: hd02、hd03、hd04
            - 拷贝指令
                - `ssh-copy-id 目标主机名`
            - <span style='color:red'>为什么需要拷贝一份到当前机器？</span>
                - 因为自己远程登录自己也需要输入密码
    2. 在 `hd03` 中生成密钥
        1. 密钥生成参考 `hd02` 的生成过程
        2. 将公钥拷贝到: hd02、hd03、hd04

    3. 在  `hd04` 中生成密钥
        1. 密钥生成参考 `hd02` 的生成过程
        2. 将公钥拷贝到: hd02、hd03、hd04

- 配置 root 用户的免密登录
    - 在 `hd02`、`hd03` 上配置 root 用户到 hd02、hd03、hd04 的免密登录
    - 许多操作需要root权限

- 密钥生成之后通过 `ssh 用户名@主机名` 的方式来测试密钥是否生成成功
    - 成功时，远程登录不需要密码

### 配置集群的slave机器
[top](#catalog)
- 修改配置: `/opt/module/hadoop-2.7.2/etc/hadoop/slaves`
- 配置中默认会有一个 `localhost`
    - 将localhost删除，增加: hd02, hd03, hd04
- 配置后分发到: hd02, hd03, hd04

### 启动集群
[top](#catalog)
- 第一次启动时，需要在 `hd02` 格式化 `NameNode`
    - `bin/hdfs namenode -format`
- 在 hd02 启动/停止 `HDFS`
    - `sbin/start-dfs.sh`、`sbin/stop-dfs.sh`
- 在 hd03 启动/停止 `YARN`
    - `sbin/start-yarn.sh`、`sbin/stop-yarn.sh`
    - <span style='color:red'>必须在 `yarn-site.xml` 中 `yarn.resourcemanager.hostname` 指定的机器上启动yarn</span>

### 集群测试
[top](#catalog)
- 如果要向服务器<span style='color:red'>上传文件，需要先关闭所有结点的防火墙</span>
    - 无法在未关闭防火墙的结点上备份
- 上传的文件将保存在
    - `data/tmp/dfs/data/current/<block pool id>/current/finalized/subdir0/subdir0/块文件名`

## 集群时间同步
[top](#catalog)
- linux的定时任务
    - crontab
        - option
            - e=编辑定时任务
            - l=查询crontab任务
            - r=删除当前用户所有的crontab任务
        - 重启crond服务
            - `service crond restart`
        - `* * * * * 指令`
            - 1* = 一个消失中的第几分钟(0-59)
            - 2* = 一天中的第几个小时(0-23)
            - 3* = 一个月中的第几天(0-31)
            - 4* = 一年当中的第几月(1-12)
            - 5* = 一周中的周几(0-7, 0\7都表示周日)
        - 特殊符号
            - `*` = 任何时间，如第一个`*`代表一小时中每分钟都执行一次
            - `,` = 不连续的时间，比如`0 8,12,16 * * *`，代表每天的：8点0分，12点0分，16点0分 都执行一次命令
            - `～` = 连续的时间范围，比如`0 5 * * 1-6`，代表周一到周六的5点0分执行命令
            - `*/n` = 代表每隔多久执行一次，比如`*/10 * * * *`，代表每隔10分钟执行一边命令
- 时间同步的方式
    1. `hd02` 作为时间同步的目标服务器
    2. `hd03`、`hd04` 每个一段时间，获取 `hd02` 的主机时间
- 配置时间同步
    - <span style='color;red'>都需要用 root 用户操作</span>
    - 配置 `hd02`，时间同步的目标服务器
        1. 检查ntp是否安装: `rpm -qa | grep ntp`
            - 结果中需要有: `ntpdata`、`ntp` 两个包
            - 安装指令: `yum -y install ntp`
        2. 修改 `ntp` 配置文件: `/etc/ntp.conf`
            - 授权 `192.168.XXX.0-192.168.XXX.255` 网段上的所有机器可以从这台机器上查询和同步时间
                ```sh
                restrict 192.168.XXX.0 mask 255.255.255.0 nomodify notrap
                ```
            - 删除可以访问的互联网时间，只是用局域网时间
                ```sh
                # server 0.centos.pool.ntp.org iburst
                # server 1.centos.pool.ntp.org iburst
                # server 2.centos.pool.ntp.org iburst
                # server 3.centos.pool.ntp.org iburst
                ``` 
            - 添加本地时间，当该节点丢失网络连接，可以使用本地时间作为时间服务器
                ```sh
                server 127.127.1.0
                fudge 127.127.1.0 stratum 10
                ```
        3. 修改 `/etc/sysconfig/ntpd`
            ```sh
            # 增加以下配置，让硬件时间与系统时间一起同步
            SYNC_HWCLOCK=yes
            ```
        4. 重新启动ntpd服务
            - `service ntpd status`
            - `service ntpd start`
        5. 设置ntpd服务开机启动
            - `chkconfig ntpd on`
    - 其他机器配置
        - 其他机器也需要安装 `ntp`
        - 配置定时任务
            - `crontab -e`
            - `*/10 * * * * /usr/sbin/ntpdate hd02`
                - 表示每隔10分钟，向hd02同步一次时间


# 需要注意的问题
[top](#catalog)
- 不能随意格式化 `NameNode`
    - `NameNode` 和 `DataNode` 都有对应的集群id
        - 两者只有相互对应，才能进行管理
    - 每次格式化 `NameNode` 后，会产生新的集群id
        1. 导致 `NameNode` 和 `DataNode` 的集群id不一致
        2. 集群将无法找到过去保存的数据
    - 为了统一 `NameNode` 和 `DataNode` 的集群id，在格式化 `NameNode`前，必须删除 `data` 和 `log` 目录
        - data目录设置: hadoop.tmp.dir
        - log目录设置: ?????

- 查看 `NameNode` 和 `DataNode` 的集群id
    - `NameNode`
        ```sh
        cat data/tmp/dfs/name/current/VERSION
        ```
    - `DataNode`
        ```sh
        cat data/tmp/dfs/data/current/VERSION
        ```

[top](#catalog)