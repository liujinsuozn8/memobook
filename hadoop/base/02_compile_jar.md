* 编译Hadoop源码
    * 使用root账户进行操作 
    * 目的：想让hadoop支持某些额外的功能，将其编译并添加到lib/native
    * 前期准备  
        * 联网
            * ping www.baidu.com
            * 如果无法连接 外网，添加如下配置
                * vim /etc/resolv.conf
                * nameserver 网关
        * 准备jar包
            * hadoop-2.7.2-src.tar.gz
            * jdk-8u144-linux-x64.tar.gz
            * apache-ant-1.9.9-bin.tar.gz（build工具，打包用的）
            * apache-maven-3.0.5-bin.tar.gz
            * protobuf-2.5.0.tar.gz（序列化框架）
        * 新建路径：/opt/module, /opt/software
        * 将jar包移动到/opt/software中
    * jar包装
        * 解压jdk
            * tar -zxv -f jdk... -C /opt/module/
            * 配置java_home
        * 解压maven
            * tar -zxv -f maven... -C /opt/module/
            * 配置maven_home
            * 可以使用阿里云镜像
        * 解压ant
        * 安装 glibc-headers
            * yum install glibc-headers
        * 安装 g++
            * yum install gcc-c++
        * 安装make
            * yum install make
        * 安装cmake
            * yum install cmake
        * 解压protobuf
            * tar -zxv -f protobuf -C /opt/module/
        * 安装openssl库
            * yum install openssl-devel
        * 安装ncurses-devel库
            * yum install ncurses-devel
    * 编译源码
        * 解压hadoop源码到/opt/目录
        * 进入hadoop的解压目录
        * 使用maven进行编译打包
            * mvn package -Pdist,native -DskipTests -Dtar
        * 打包的结果保存在：/opt/hadoop-2.7.2-src/hadoop-dist/target