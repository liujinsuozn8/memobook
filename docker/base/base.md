<span id="catalog"></span>

### 目录
- [基本概念](#基本概念)
- [Docker的组成](#docker的组成)
    - [架构图](#架构图)
    - [Docker的3元素](#docker的3元素)
- [Docker安装](#docker安装)
- [Docker运行原理](#Docker运行原理)
    - [为什么Docker比虚拟机快](#为什么Docker比虚拟机快)
    - [Docker是如何工作的](#Docker是如何工作的)
- [Docker命令](#docker命令)
    - [帮助命令](#帮助命令)
    - [镜像命令](#镜像命令)
    - [容器命令](#容器命令)
- [Docker镜像原理](#Docker镜像原理)
- [](#)
- [](#)
- [](#)
- [](#)
- [](#)

# 基本概念
[top](#catalog)
- 环境配置比较麻烦，费时费力，需要从根本上解决问题
    - 安装的时候，把原始环境一模一样的复制过来，利用Docker消除开发和运维之间的问题
    - 以前交代码，现在交环境
- Docker的定位
    - 是什么：
        - Docker是一种**Linux容器**
        - 从Docker组成来讲，它是一个`容器运行载体`或`管理引擎`
        - Docker**解决了运行环境和配置问题，方便做持续集成并有助于整体发布的容器虚拟化技术**
        - 可以理解为：一个精细版的Linux系统
    - 能做什么：
        - 开发/运维DevOps
            - 更简单的系统运维
        - 一次开发，到处运行
        - 更快的交付和部属
        - 更便捷的升级和扩缩容
        - 更高效的计算资源利用
    - 优势
        - 更轻量：基于**容器虚拟化**，仅包含业务运行所需的runtime环境
            - 如CentOs/Ubuntu**基础镜像**仅需170M，宿主机可以部属100～1000个
        - 更高效
            - 无操作系统虚拟化开销
            - 计算上，轻量无额外开销
            - 存储：系统盘、数据盘
            - 网络：使用宿主机网络，NS隔离
        - 更敏捷、更灵活
            - 分层的存储和包管理、devops理念
            - 支持多种网络配置

- 虚拟机和Linux容器的区别
    - 虚拟机
        - 在架构上
            - 虚拟机是在一个操作系统里运行另一个操作系统，如Windows中使用镜像创建Linux系统
            - 应用程序无法感知部属在哪里--真实机器or虚拟机，因为虚拟机和真实机器看起来相同
            - 对于底层系统虚拟机就是一个普通文件，如果不需要就可以删掉，对其他部分没有影响
            - 通过虚拟机，可以使应用程序、操作系统、硬件三者的关系不变
        - 在虚拟化方式上
            - 直接虚拟出一套硬件，然后在其上运行一个完整的操作系统，并在该系统上运行所需的应用程序
        - 在使用效率上
            - 资源占用多
            - 使用的冗余步骤多
            - 虚拟机的启动慢，是分钟级的
    - Linux容器，Linux Containers，LXC
        - 在架构上
            - Linux容器不是模拟一个完整的操作系统，而是**对进程进行隔离**
            - 容器与虚拟机不同，**不需要绑定一整套操作系统**，只需要软件工作所需的库资源和设置，**系统因此变得高效轻量，并且可以部属在任何环境中**
                - 库资源和设置可以放到仓库中，共用
            - 通过容器，可以将软件运行的所有资源打包到一个隔离的容器中
            - 每个容器之间相互隔离，每个容器都有自己的文件系统，容器之间进程不会相互影响，能够区分计算资源
        - 在虚拟化方式上
            - 容器内的应用进程直接运行在宿主内核，容器没有自己的内核，也没有进行硬件虚拟，所以容器更加轻量
        - 在使用效率上
            - 资源占用少
            - 启动快，是秒级的

- Docker包含的两方面技术
    - **镜像技术:镜像即应用**
        - 镜像的**内容**包括
            - 运行文档
            - 配置环境
            - 运行环境
            - 运行依赖包
            - 操作系统发行版本
            - 内核

# Docker的组成
## 架构图
[top](#catalog)
- Docker架构图????????????06-04-11
- 架构组成
    - client ，命令终端
    - docker_host，Docer主机，宿主机
    - repository，仓库
- 需要时，直接从仓库`pull`到本地，即`images`

## Docker的3元素
[top](#catalog)
- 3元素
    - 镜像`image`
        - 可以理解为Java中的类
        - **将应用程序和配置依赖打包成一个可交付的运行环境，这个运行环境就是镜像`image`**
        - 一个**只读模板**
        - `image`可以用来创建Docker `container`，一个镜像可以创建多个个`container`
    - 容器`container`
        - 可以理解为Java中的对象
        - 由`images`的创建的一个**运行实例**，它运行一种服务(数据存储、web等))
        - `container`是**独立运行的一个或一组应用**
        - `container`可以被启动、开始、停止、删除，每个容器都是互相隔离的、保证安全的平台
        - `container`可以看作:一个简易版Linux环境(包括root权限、进程空间、用户空间、网络空间) + 运行在环境中的应用程序
        - **`container`和`images`几乎一样，都是一堆层的统一视角，唯一区别在于`container`的最上面那一层是可读可写的**
    - 仓库`repository`
        - `repository`是集中式存放`image`文件的场所
        - 仓库`repository`和仓库注册服务器`registry`是有区别的，仓库注册服务器上往往存放多个仓库，每个仓库中又包含了多个镜像，每个镜像有不同的表情(tag)
        - 仓库分为公开仓库`Public`和私有仓库`Private`两种形式
        - 最大的公开仓库:`Docker Hub`(https://hub.docker.com)
- **3元素如何合作**
    - 打包好的运行环境是`image`，`image`可以保持在`repository`，可以从`repository`拉取，Docker通过`image`可以创建多个`container`

# Docker安装
[top](#catalog)
- 对系统的要求
    - CentOs版本
        - CentOS7(64-bit)，系统内核版本为`3.10`以上
        - CentOS6.5(64-bit) 或更高版本，系统内核版本为`2.6.32-431`以上
    - 查看当前内核版本:`uname -r`
    - 查看安装的CentOS版本信息:`lsb_release -b`
- 安装步骤
    - CentOS6.5
        1. yum install -y epel-release
            - Docker使用EPEL发布，RHEL系的OS首先要确保已经持有EPEL仓库，否则先检查OS的版本，然后安装相应的EPEL包
        2. yum install -y docker-io
        3. 安装后的配置文件:`/etc/sysconfig/docker`
            - 配置镜像加速地址
        4. 启动docker后台服务:`service docker start`
        5. `docker version`验证
        6. 验证镜像:`ps -ef | grep docker`

    - CentOS7
        - 按照部分相同
        - 创建配置文件:`/etc/sysconfig/docker/docker.json`
            - 添加加速器内容
        - `systemctl daemon-reload`
        - `systemctl restart docker`
        
- 镜像加速
    - 阿里云镜像
        - dev.aliyun.com
        - 注册一个阿里云开发平台
        - 获得加速器地址链接
            - 登录阿里云
            - 左侧选择`镜像加速器`-->右侧选择系统-->获取加速地址
        - 配置本级Docker运行镜像加速器
            - `/etc/sysconfig/docker`
                - `other_args=--registry-mirror=https://加速地址.mirror.aliyuncs`

- `hello-world`测试
    - `hello-world`测试是`Docker Hub`提供的一个测试镜像
    -  启动docker，测试运行`hello-world`
        - `ducker run hello-world`

# Docker运行原理
## Docker是如何工作的
[top](#catalog)
- Docker是一个`Client-Server`结构的系统
- Docker的守护进程运行在主机上，通过Socket连接客户端访问，守护进程从客户端接收命令并管理运行在主机上的容器

## 为什么Docker比虚拟机快
- docker有比虚拟机更少的抽象层
    - docker不需要`Hypervisor`实现硬件资源虚拟化，运行在docker容器上的程序直接使用的都是实际物理机的硬件资源，所以docker在CPU、内存利用率上更有优势
- docker利用的是宿主机的内核，而不需要虚拟机`GuestOS`
    - 新建一个虚拟机时，虚拟机软件需要加载`GuestOS`，新建过程是分钟级别的
        - 需要引寻、加载操作系统内核返回比较费时费资源的过程
    - 当新建一个容器时，docker直接利用操作系统内核，没有了硬件负担，省略了加载操作系统内核的步骤

- Docker和虚拟机的特性

    ||Docker|虚拟机|
    |-|-|-|
    |操作系统|与宿主机共享OS|宿主机OS上运行虚拟机OS|
    |存储大小|镜像小，便于存储与传输|镜像庞大(vmdk、vdi等)|
    |运行性能|几乎无额外性能损失|操作系统额外的CPU、内存消耗|
    |移植性|轻便、灵活，适应于Linux|笨重，与虚拟化技术耦合度高|
    |硬件亲和性|面向软件开发|面向硬件运维|
    |部属速度|快速，秒级|较慢，10s以上|

# Docker命令
## 帮助命令
[top](#catalog)
- `docker version`:查看版本信息
- `docker info`:当前docker中的信息
- `docker --help`

## 镜像命令
[top](#catalog)
- 列出**本地主机**上的镜像：`docker images [options]`
    - 列出的内容
        - REPOSITORY:镜像在仓库中的名称
        - TAG:镜像的标签
            - 默认是`latest`
        - IMAGE ID:镜像ID，相当于镜像的`主键`
        - CREATED:镜像创建时间
        - SIZE:镜像大小
    - 同一个仓库源可以有多个TAG，代表这个仓库源的不同版本
    - 使用`REPOSITORY:TAG`来定义不同的镜像
        - 如果不指定镜像的标签，docker将默认使用：`xxxx:latest`镜像
    - 常用参数
        - `-a`，列出本地所有的镜像,包括**中间映像层**`<none>`
        - `-q`，只显示镜像ID
        - `--digests`，显示镜像的摘要信息
        - `--no-trunc`，显示完整的镜像信息
- 检索镜像：`docker search [options] 镜像名`
    - 从`https://hub.docker.com/`查询镜像
        - 与镜像加速配置无关
    - 标题列`OFFICIAL`表示是否是官方版本
    - options
        - `--no-trunc`，显示完整的镜像描述
        - `-s 数量`，列出收藏数不小于指定值的镜像
        - `--automated`，只列出`automated build`类型的镜像
- 拉取镜像：`docker pull 镜像名[:TAG]`
    - `docker pull 镜像名`等于`docker pull 镜像名:latest`
- 删除镜像：`docker rmi [options] 镜像ID[:TAG]`
    - options
        - `-f`,强制删除
    - 删除单个镜像：`docker rmi xxx`
    - 删除多个镜像：`docker rmi xxx yyy`
    - 删除全部镜像：`docker rmi -f $(docker images -q)`
        - 将所有`image`的ID作为参数传递给`docker rmi`指令，来完成全部删除

## 容器命令
[top](#catalog)
- 基本指令
    - **新建**并**启动**容器：`docker run [options] image [command][arg...]`
        - 常用`options`
            - `--name 容器的新名字`，为容器指定一个名称
            - `-d`，后台运行容器，并返回容器ID，即启动守护式容器
            - **`-i`，以交互模式运行容器，通常与`-t`同时使用**
            - **`-t`，为容器重新分配一个伪输入终端，通常与`-i`同时使用**
            - `-P`，随机端口映射
            - `-p`，指定端口映射，有以下4种格式
                - `id:hostPort:containerPort`
                - `id::containerPort`
                - `hostPort:containerPort`
                - `containerPort`
        - 常用参数组合
            - `docker run -it --name NewName 镜像名`，交互式模式创建并启动容器
    - 列出当前所有正在运行的容器：`docker ps [options]`
        - 列出的内容
            - CONTAINER ID:容器ID
            - IMAGE：创建容器的镜像ID
            - COMMAND：以什么命令格式进入容器
            - STATUS：当前容器的状态
                - UP:正在运行
                - Exit:退出
            - PORTS：??????????
            - NAMES:
                - 如果使用了`--name`,会显示指定的名字；如果没有使用，docker会随机分配一个容器名
        - 常用`options`
            - `-a`:列出全部**已有容器**
            - `-l`:显示最近创建的容器
            - `-n m`:显示最近创建的m个容器
            - **`-q`:静默模式，只显示容器编号**
            - `--no-trunc`，不截断输出

    - 退出容器
        - 退出方法1：`exit`
            - 容器停止退出
            - 直接在容器的交互式终端中使用
        - 退出方式2：`ctrl+P+Q`
            - 离开但不关闭当前容器
            - 使用后会退回宿主机

    - 启动**已有容器**:`docker start 容器名/容器ID`
    - 重启**已有容器**:`docker restart 容器名/容器ID`
    - 停止正在运行的容器:`docker stop 容器名/容器ID`
    - 强制停止正在运行的容器:`docker kill 容器名/容器ID`
    - 删除已停止的容器:`docker rm 容器名/容器ID`
    - 删除多个容器:
        - `docker rm -f $(docker ps -a -q)`
        - `docker ps -a -q | xargs docker rm`

- 容器管理指令
    - 查看日志指令:`docker logs -f -t -tail m 容器ID`
        - `-t`，加入时间
        - `-f`，跟随最新的日志打印
        - `-tail`，数字显示最后`m`条
    - 查看容器内部正在执行的进程:`docker top 容器名/容器ID`
    - 查看容器内部细节:`docker inspect 容器ID`
        - 通过json串来描述
    - 进入正在运行的容器并以命令行交互:
        - `docker attach 容器ID`
            - 直接进入指定容器，不会启动新的进程
        - `docker exec [-dit] 容器 指令 指令参数`
            - 在容器中打开新的终端，并且可以启动新的进程
            - 可以在容器外部执行指令
            - `options`
                - `-d` :分离模式: 在后台运行
                - `-i` :即使没有附加也保持STDIN 打开
                - `-t` :分配一个伪终端
            - `docker exec -it 容器 /bin/bash`，也可以进入终端

- 从容器内部拷贝文件到宿主机上
    - `docker cp 容器ID：容器内路径 宿主机路径`

- 守护式启动
    - `docker run -d 镜像名`
    - 守护式启动的容器，执行`docker ps -a`，会发现容器已经退出
    - Docker的机制：**Docker容器后台运行，就必须有一个前台进程**
        - 如果没有前台进程，后他进程启动后，会认为自己没有任务可做，直接kill
        - 容器运行的命令如果不是那些一直挂起的命令(如top，tail)，就会自动退出的

- 示例：下载一个centos
    - `docker pull centos`
    - 交互式模式启动：`docker run -it centos`
        - 通过选项`-it`启动后，命令终端自动切换到`centos`容器的根目录
        - 可以使用：`ps -ef`，查看进程
        - 离开但不关闭当前`centos`容器：`ctrl+P+Q`

# Docker镜像原理
[top](#catalog)



[top](#catalog)
- xxx
    - 安装
    - 配置加速
    - 测试helloworld
    - 测试docker命令


- hello-world:latest

- `run`做了什么
    - 在本地找`image`
        - 如果没有从`Hub`查找
            - 如果找到，将镜像下载到本地
            - 如果没有，则执行失败
        - 如果本地有，则直接使用
    - 使用`image`创建容器实例，并运行

# 总结
[top](#catalog)
- 启动docker:`service docker start`
- 镜像命令
    - 增:`docker pull 镜像名[:TAG]`
    - 删:`docker rmi [options] 镜像ID[:TAG]`
    - 改: ---
    - 查:
        - 查Hub:`docker search [options] 镜像名`
        - 查本地:`docker images [options]`
- 容器命令
    - 新建+启动:`docker run [options] image [command][arg...]`
        - 常用:`docker run -it --name NewName 镜像名`
    - 查:`docker ps [options]`
    - 退出:`exit`
    - 暂离:`ctrl+P+Q`
    - 启动**已有容器**:`docker start 容器名/容器ID`
    - 重启**已有容器**:`docker restart 容器名/容器ID`
    - 停止正在运行的容器:`docker stop 容器名/容器ID`
    - 强制停止正在运行的容器:`docker kill 容器名/容器ID`
    - 删除已停止的容器:`docker rm 容器名/容器ID`
    - 删除多个容器:
        - `docker rm -f $(docker ps -aq)`
        - `docker ps -aq | xargs docker rm`
    - 查看日志指令:`docker logs -f -t -tail m 容器ID`
    - 查看容器内部正在执行的进程:`docker top 容器名/容器ID`
    - 查看容器内部细节:`docker inspect 容器ID`
    - 直接进入指定容器:`docker attach 容器ID`
    - 在外部执行指令`docker exec [-dit] 容器 指令 指令参数`
    - 从容器内部拷贝文件到宿主机上:`docker cp 容器ID：容器内路径 宿主机路径`



- 列出**本地主机**上的镜像：

    - 检索镜像：
    - 拉取镜像：
    - 删除镜像：

[top](#catalog)
