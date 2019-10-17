* 目录结构：
    1. **bin**：存放对Hadoop相关服务（HDFS,YARN）进行操作的脚本
    2. **etc目录**：Hadoop的配置文件目录，存放Hadoop的配置文件
    3. lib目录：存放Hadoop的本地库（对数据进行压缩解压缩功能）
    4. **sbin目录**：存放启动或停止Hadoop相关服务的脚本 (所有的启动命令)
    5. share目录：存放Hadoop的依赖jar包、文档、和官方案例
    6. include 其他代码的头文件
* 常用端口号
    * 9000 HDFS中NameNode的地址 
    * 50090 辅助节点的主机地址（SecondaryNameNode）(web端口)
    * 50070 查看HDFS （web端口）
    * 8088 查看MapReduce
    * 10020 历史服务器端地址
    * 19888 历史服务器web端地址
# 三种运行模式
* 本地模式
    * 创建文件目录：mkdir /opt/modules/input
    * 拷贝输入文件：cp etc/hadoop/*.xml input/
    * 执行MapReduce程序：`bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar grep input output 'dfs[a-z.]+'`
* 伪分布式模式
    * 需要启动的服务
        * namenode (HDFS)
        * datanode (HDFS)
        * resourcemanager (YARN)
        * nodemanager (YARN)
        * historyserver (历史服务器)
    * HDFS部分
        * 修改配置：/opt/module/hadooXXXX/etc/hadoop/
            * hadoop-env.sh
                * 手动配置JAVA_HOME
            * core-site.xml
                * 配置Namenode地址
                * 文件的存储目录
            * hdfs-site.xml
                * hdfs 默认副本数=3
                * 如果只有一台机器，副本数量=3，则实际运行时只会有一份数据；当机器增加时，才会在增加的机器上进行备份；这个操作时自动控制的
        * 格式化NameNode
            * 格式化之前，需要清空数据
                * 关闭进程 namenode, datanode
                * 删除目录：data/， logs/
                * 进行格式化
        * 启动集群
            * 启动NameNode
                * sbin/hadoo-daemon.sh start namenode
            * 启动DataNode
                * sbin/hadoo-daemon.sh start datan ode
        * 从外部访问web端口：192.168.abc.xxx:50070
            * IP查看： cat /etc/hosts
            * **一定要关闭防火墙**
                * 切换到root
                * 查看防火墙状态：firewall-cmd --state
                * 关闭防火墙：systemctl stop firewalld.service
        * 创建输出数据目录
            * bin/hdfs dfs -mkdir -p /user/ljs/input
        * 拷贝本地数据到集群
            * bin/hdsf dfs -put wcinput/wc.input /user/ljs/input
        * 执行exmple，执行前不能有输出目录：/user/ljs/output
        * 执行后观察结果
            * bin/hdfs dfs -cat /user/ljs/output/p*
            * 或者直接通过web端进行下载
                * window10 需要配置hosts IP和用户的映射
    * YARN部分
        * 配置
            * 配置yarn-env.sh
                * 手动配置JAVA_HOME
            * 配置yarn-site.xml
                * 配置reuduce获取数据的方式
                * 指定YARN的ResourceManager的地址
            * 配置mapred-env.sh
                * 手动配置JAVA_HOME
            * 配置mapred-site.xml
                * 将mapred-site.xml.template变为mapred-site.xml
                * 再添加配置
                    * 指定MR在YARN上运行 
        * 启动集群
            * 启动ResourceManager和NodeManager 
                * sbin/yarn-daemon.sh start resoucemanager
                * sbin/yarn-daemon.sh start nodemanager
    * 历史服务器 部分
        * 配置mpa  red-site.xml
            * 添加历史服务器端地址
            * 添加历史服务器web端地址
        * 启动（不需要重启 在端口号19888进行跳转）
            * sbin/mr-jobhistory-daemon.sh start historyserver
    * 日志（日志聚集）部分
        * 开启日志需要重启 nodemanager，resourcemanager，historyserver
        * 配置yarn-site.xml
            * 日志聚集功能yarn.log-aggregation-enable
            * 日志保留时间

* 完全分布式
    * 三台虚拟机：002，003，004
    * 服务器之间进行数据拷贝
        * scp -r 当前用户的文件 目的用户@主机名:路径
        * rsync 远程同步工具
        * xsync 集群分发脚本
    * 集群配置
        * HDFS
            * NameNode 和 SecondaryNameNode 的内存比例为1:1，所以这两者不能放在同一台机器上（如果放在一台机器，各用n/2容量，会影响性能）
        * YARN
            * ResourceManager要避开NameNode、SecondaryNameNode，所以要单独存放
    * 集群单节点启动（002）
        * 初次启动初始化（仅初始化002）
            * bin/hdfs namenode -format
        * 分别启动namenode、datanode
            * 002=namenode,datanode
            * 003=datanode
            * 004=datanode
    * 群起集群
        * **shh免密连接**
            * 在002中生成密钥
                * 为什么要配置免密连接
                    * namenode 需要和其他的节点进行通讯
                * 生成密钥
                    * cd $HOME/.ssh
                    * ssh-keygen -t rsa  
                        * 生成两个文件：id_rsa(私钥)，id_rsa.pub(公钥)
                * 拷贝公钥到Authorized_keys (002，003，004)
                    * 如果没有.ssh文件夹
                        * ssh localhost
                    * 拷贝公钥
                        * ssh-copy-id myhd003/myhd004 
            * 在003中生成密钥
                * 为什么要配置免密连接
                    * resourcemanager 需要和其他的节点进行通讯
                * 拷贝公钥到Authorized_keys (002，003，004)
            * 在002中配置root用户的密钥到 002，003，004
                * 为什么要配置免密连接
                    * namenode 需要和其他的节点进行通讯
        * 配置slaves
            * localhost删除，增加002、003、004
            * 配置后分发到各子节点：003、004
        * 启动集群
            * 启动HDFS（在002启动）：sbin/start-dfs.sh
            * 启动YARN（在003启动）：sbin/start-yarn.sh
    * 集群的基本测试
        * 上传文件
    * 集群时间同步
        * linux定时任务
            * crontab
                * option
                    * e=编辑定时任务
                    * l=查询crontab任务
                    * r=删除当前用户所有的crontab任务
                * 重启crond服务
                    * service crond restart
                * `* * * * * 指令`
                    * 1* = 一个消失中的第几分钟(0-59)
                    * 2* = 一天中的第几个小时(0-23)
                    * 3* = 一个月中的第几天(0-31)
                    * 4* = 一年当中的第几月(1-12)
                    * 5* = 一周中的周几(0-7, 0\7都表示周日)
                * 特殊符号
                    * `*` = 任何时间，如第一个`*`代表一小时中每分钟都执行一次
                    * `,` = 不连续的时间，比如`0 8,12,16 * * *`，代表每天的：8点0分，12点0分，16点0分 都执行一次命令
                    * `～` = 连续的时间范围，比如`0 5 * * 1-6`，代表周一到周六的5点0分执行命令
                    * `*/n` = 代表每隔多久执行一次，比如`*/10 * * * *`，代表每隔10分钟执行一边命令
        * 时间同步的方式：找一台机器，作为时间服务器，所有的机器与这台集群时间机器进行定时的同步
