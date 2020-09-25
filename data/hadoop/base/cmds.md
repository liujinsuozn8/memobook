<span id="catalog"></span>

<span style='font-size:18px'>目录---hadoop操作指令</span>

- [启动、关闭集群](#启动、关闭集群)
- [HDFS的shell命令](#HDFS的shell命令)
- [执行hadoop程序](#执行hadoop程序)
- [安全模式](#安全模式)
- [NN故障处理](#NN故障处理)
- [启动黑名单、白名单](#启动黑名单、白名单)
- [节点间数据复制](#节点间数据复制)
- [查看镜像、日志文件](#查看镜像、日志文件)
- [HAR文件处理](#HAR文件处理)
- [](#)

# 启动、关闭集群
[top](#catalog)
- 如果已经配置好了hadoop的环境变量，则目录前缀 `bin/`、`sbin/` 可以省略

- 格式化NameNode: `bin/hdfs namenode -format`

- 手动 启动/停止 HDFS 组件
    - `sbin/hadoop-daemon.sh <start/stop> namenode`
    - `sbin/hadoop-daemon.sh <start/stop> datanode`
    - `sbin/hadoop-daemon.sh <start/stop> secondarynamenode`

- 手动 启动/停止 YARN 组件
    - `sbin/yarn-daemon.sh <start/stop> resourcemanager`
    - `sbin/yarn-daemon.sh <start/stop> nodemanager`

- 群起集群
    - 启动/停止 `HDFS` 所有组件: `sbin/start-dfs.sh`、`sbin/stop-dfs.sh`
    - 启动/停止 `YARN` 所有组件: `sbin/start-yarn.sh`、`sbin/stop-yarn.sh`

- 启动/停止历史服务器: `sbin/mr-jobhistory-daemon.sh <start/stop> historyserver`

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

# 执行hadoop程序
[top](#catalog)
- `hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar wordcount /user/input/wc.input /user/input/wcoutput`
- 执行前不能有输出目录: `/user/output`

# 安全模式
[top](#catalog)

|指令|功能|
|-|-|
|`hdfs dfsadmin -safemode get`|查看|
|`hdfs dfsadmin -safemode wait`|阻塞、等待安全模式退出|
|`hdfs dfsadmin -safemode enter`|进入|
|`hdfs dfsadmin -safemode leave`|退出安全模式|

# NN故障处理
[top](#catalog)
- 导入检查点数据: `bin/hdfs namenode -importCheckpoint`


# 启动黑名单、白名单
[top](#catalog)
- 刷新NameNode：`hdfs dfsadmin -refreshNodes`
- 刷新ResourceManager：`yarn rmadmin -refreshNodes`
- 集群再平衡:`sbin/start-blancer.sh`


# 节点间数据复制
[top](#catalog)
- 节点间的递归数据复制：`bin/hadoop distcp hdfs://主机名1:9000/.... hdfs://主机名2:9000/....`

# 查看镜像、日志文件
[top](#catalog)
- 查看镜像文件
    - `hdfs oiv -p XML -i fsImage文件 -o 输出文件`
- 查看日志文件
    - `hdfs oev -p XML -i fsImage文件 -o 输出文件`

# HAR文件处理
[top](#catalog)
- 小文件转换HAR文件：`hadoop archive -archiveName xxx.har -p 打包目录 未存在的存储目录`
- 解压HAR文件到HDFS第二名某个目录：`hadoop fs -cp har:///路径/xx.har/* 解压路径`
- 查看HAR文件中的内容：`hadoop fs -ls -R har:///路径/xx.har`


