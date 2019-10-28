* HDFS_shell指令:
    * `bin/hadoop fs` / `bin/hdfs dfs`
    * shell命令
        * `-help 指令名` 查看帮助
        * -ls, 显示信息
        * -cat, 显示文件内容
        * -tail, 显示文件的末尾
        * -chgrp,-chmod,-chown ,修改权限
        * -moveFromLocal, 从本地剪切文件，粘贴到HDFS
        * -appendToFile, 追加一个文件到已经存在的文件末尾
        * -put,-copyFromLocal, 从本地文件系统拷贝文件到HDFS
        * -get,-copyToLocal, 从HDFS拷贝文件到本地
            * `hadoop fd -copyToLocal HDFS路径 ./` 将HDFS上的文件拷贝到当前目录
        * -cp, 从HDFS的一个路径拷贝文件到另一个路径
        * -mv, 在HDFS中移动文件
        * -getmerge, 从HDFS合并下载多个文件：
            * `hadoop fs -getmerge xxx/* ./yyy`
        * -rm, 删除文件或文件夹 
        * -mkdir, 在HDFS上创建目录
        * -rmdir, 删除空文件夹
        * -du, 统计文件大小
            * `-h` 以直观的方式显示结果
        * `-setrep 副本数量 HDFS文件路径`, 设置HDFS中文件的副本数量

* 查看镜像文件
    * `hdfs oiv -p XML -i fsImage文件 -o 输出文件`
* 查看日志文件
    * `hdfs oev -p XML -i fsImage文件 -o 输出文件`
* 安全模式
    * 查看:`hdfs dfsadmin -safemode get`
    * 阻塞/等待:`hdfs dfsadmin -safemode wait`
    * 进入:`hdfs dfsadmin -safemode enter`
    * 离开:`hdfs dfsadmin -safemode leave`
* NN故障处理
    * 导入检查点数据: `bin/hdfs namenode -importCheckpoint`
* 黑名单/白名单
    * 刷新NameNode：`hdfs dfsadmin -refreshNodes`
    * 刷新ResourceManager：`yarn rmadmin -refreshNodes`
    * 集群再平衡:`sbin/start-blancer.sh`
* 节点间的递归数据复制：`bin/hadoop distcp hdfs://主机名1:9000/.... hdfs://主机名2:9000/....`
* HDR文件:
    * 小文件转换HDR文件：`hadoop archive -archiveName xxx.har -p 打包目录 未存在的存储目录`
    * 解压HDR文件：`hadoop fs -cp har:///路径/xx.har/* 解压路径`
    * 查看HDR文件中的内容：`* hadoop fs -ls -R har:///路径/xx.har`
                    
