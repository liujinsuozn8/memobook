- [常用应用安装](#常用应用安装)
    - [安装mysql](#安装mysql)
    - [安装redis](#安装redis)

## 自定义centos
[top](#catalog)
- Base镜像：scratch
- 自定义1，功能
    - 设定启动目录`/tmp`
    - 安装vim编辑器
    - 安装ifconfig查看网络配置

    ```
    FROM centos
    ENV mypath /tmp
    WORKDIR $mypath

    RUN yum -y install vim
    RUN yum -y install net-tools

    EXPOSE 80
    CMD /bin/bash
    ```
- 自定义2，功能：查询ip
    - 通过执行:`curl http://ip.cn`来查询ip
    - 镜像启动命令:`docker run -it 镜像名`
        - myip
            ```
            FROM centos
            RUN yum -y install curl
            CMD ["curl", "-s", "http://ip.cn"]
            ```
    - 由于内部使用了`CMD`执行命令，导致无法在启动时传递`curl`指令的其他参数，使用`ENTRYPOINT`来改写
        - myip
            ```
            FROM centos
            RUN yum -y install curl
            ENTRYPOINT ["curl", "-s", "http://ip.cn"]
            ```
- 自定义3：父镜像、子镜像
    - 父镜像:myip_parent
        ```
        FROM centos
        RUN yum -y install curl
        ENTRYPOINT ["curl", "-s", "http://ip.cn"]
        ONBUILD RUN echo "this is parent image"
        ```
    - 子镜像
        ```
        FROM myip_parent
        RUN yum -y install curl
        ENTRYPOINT ["curl", "-s", "http://ip.cn"]
        ```

## 自定义tomcat9
[top](#catalog)
- 创建编译目录
- 将tomcat、jdk包拷贝到编译目录
- 编写Dockerfile文件
    ```
    FROM centos
    # maintainer xxx
    
    # 将tomcat和jdk添加到容器中
    ADD tomcat /usr/local/
    ADD jdk /usr/local/

    # 安装vim
    RUN yum -y install vim

    # 设置工作目录
    ENV MYPATH /usr/local
    WORKDIR $MYPATH

    # 将tomcat和jdk添加到容器中
    ENV JAVA_HOME /usr/local/jdk???????
    ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
    ENV CATALINA_HOME /usr/local/apache-tomcat- ????????
    ENV CATALINA_BASE /usr/local/apache-tomcat- ???????
    ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin

    #监听端口
    EXPOSE 8080
    #启动时运行tomcat
    #ENTRYPOINT["/usr/local/apache-tomcat-?????/bin/startup.sh"]
    #CMD["/usr/local/apache-tomcat-?????/bin/catalina.sh", "run"]
    CMD /usr/local/apache-tomcat-?????/bin/startup.sh && tail -F /usr/local/apache-tomcat-?????/bin/logs/catalina.out
    ```
- 编译:`docker build -t mytomcat9`
- 新建并启动容器
    - `docker run -d -p 8888:8080 --name mytomcat901 -v /.../test:/usr/local/apache-tomcat-????/webadds/test -v /.../tomcat9log/:/usr/local/apache-tomcat-????/logs mytomcat9`
        - 创建两个容器卷


# 常用应用安装
## 安装mysql
[top](#catalog)
- 拉取mysql镜像???????????????
- 创建容器 
    - `docker run -d -p 3333:3306 --name mysql -v /???/mysql/conf:/etc/mysql/conf.d -v /???/mysql/logs:/logs -v /???/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1234 mysql:版本号`
- 执行数据库备份
    - `docker exec 容器ID sh -C 'mysqldump --all-databases -u root -p1234' >/tmp/all.sql`

## 安装redis
[top](#catalog)
- 拉取???????????????
- 创建容器
    - `docker run -d -p 6379:6379 -v /???/myredis/data:/data -v /???/myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf redis:版本号 redis-server /usr/local/etc/redis/redis.conf --appendonly yes`
    - `docker run -d -p 6379:6379 --name redis6379 -v /Users/liujinsuo/mydocker/myvolumes/myredis/testredis:/data -v /Users/liujinsuo/mydocker/myvolumes/myredis/commonRedis/redis6379.conf:/usr/local/etc/redis/redis.conf -v /Users/liujinsuo/mydocker/myvolumes/myredis/logfile:/var/run  redis:latest  redis-server /usr/local/etc/redis/redis.conf`
- 在宿主机的数据卷上添加配置文件
    - 添加基本配置，但是要注释`binds`部分，不绑定到本机
- 测试：连接redis
    - `docker exec -it 容器ID redis-cli`
- 测试：持久化文件生成
    - 执行`shutdown`
    - 在`/data`容器卷内，存在文件`appendonly.aof`
