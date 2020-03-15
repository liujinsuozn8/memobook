<span id="catalog"></span>

### 目录
- [相关说明](相关说明)
- 安装步骤
    - [检查Centos的版本](#检查Centos的版本)
    - [yum安装gcc相关工具](#yum安装gcc相关工具)
    - [卸载旧版本的docker](#卸载旧版本的docker)
    - [设置稳定的镜像仓库](#设置稳定的镜像仓库)
    - [更新yum软件包索引](#更新yum软件包索引)
    - [安装docker社区版](#安装docker社区版)
    - [启动docker](#启动docker)
    - [测试docker](#测试docker)
    - [配置镜像加速](#配置镜像加速)
    - [卸载](#卸载)
    - [](#)
- [](#)


# 相关说明
[top](#catalog)
- docker社区版 在centos7上的安装流程
- <label style="color:red">安装的版本：Docker v19.03</label>
- 基本流程参考：
    - 官网说明：https://docs.docker.com/install/linux/docker-ce/centos/
    - [/devops/docker/base/base.md#Docker安装](/devops/docker/base/base.md#Docker安装)


# 安装步骤
## 检查Centos的版本
[top](#catalog)
- 对系统的要求
    - CentOS7(64-bit)，系统内核版本为`3.10`以上
- `cat /etc/redhat-release`

## yum安装gcc相关工具
[top](#catalog)
- `yum -y install gcc`
- `yum -y install gcc-c++`
- 检查gcc版本：`gcc -v`
- **实测这一步可以省略**

## 卸载旧版本的docker 
[top](#catalog)
```
yum remove docker \
    docker-client \
    docker-client-latest \
    docker-common \
    docker-latest \
    docker-latest-logrotate \
    docker-logrotate \
    docker-engine
```

## 安装需要的软件包
[top](#catalog)
- `yum install -y yum-utils device-mapper-persistent-data lvm2`
- 执行内容
    ```
    [ljs@myhd001 ~]$ sudo yum install -y yum-utils \
    >   device-mapper-persistent-data \
    >   lvm2
    已加载插件：fastestmirror, langpacks
    Loading mirror speeds from cached hostfile
    * base: mirrors.aliyun.com
    * extras: mirrors.tuna.tsinghua.edu.cn
    * updates: mirror.lzu.edu.cn
    正在解决依赖关系
    --> 正在检查事务
    ---> 软件包 device-mapper-persistent-data.x86_64.0.0.7.3-3.el7 将被 升级
    ---> 软件包 device-mapper-persistent-data.x86_64.0.0.8.5-1.el7 将被 更新
    ---> 软件包 lvm2.x86_64.7.2.02.180-8.el7 将被 升级
    ---> 软件包 lvm2.x86_64.7.2.02.185-2.el7_7.2 将被 更新
    --> 正在处理依赖关系 lvm2-libs = 7:2.02.185-2.el7_7.2，它被软件包 7:lvm2-2.02.185-2.el7_7.2.x86_64 需要
    ---> 软件包 yum-utils.noarch.0.1.1.31-50.el7 将被 升级
    ---> 软件包 yum-utils.noarch.0.1.1.31-52.el7 将被 更新
    --> 正在检查事务
    ---> 软件包 lvm2-libs.x86_64.7.2.02.180-8.el7 将被 升级
    ---> 软件包 lvm2-libs.x86_64.7.2.02.185-2.el7_7.2 将被 更新
    --> 正在处理依赖关系 device-mapper-event = 7:1.02.158-2.el7_7.2，它被软件包 7:lvm2-libs-2.02.185-2.el7_7.2.x86_64 需要
    --> 正在检查事务
    ---> 软件包 device-mapper-event.x86_64.7.1.02.149-8.el7 将被 升级
    ---> 软件包 device-mapper-event.x86_64.7.1.02.158-2.el7_7.2 将被 更新
    --> 正在处理依赖关系 device-mapper-event-libs = 7:1.02.158-2.el7_7.2，它被软件包 7:device-mapper-event-1.02.158-2.el7_7.2.x86_64 需要
    --> 正在处理依赖关系 device-mapper = 7:1.02.158-2.el7_7.2，它被软件包 7:device-mapper-event-1.02.158-2.el7_7.2.x86_64 需要
    --> 正在检查事务
    ---> 软件包 device-mapper.x86_64.7.1.02.149-8.el7 将被 升级
    --> 正在处理依赖关系 device-mapper = 7:1.02.149-8.el7，它被软件包 7:device-mapper-libs-1.02.149-8.el7.x86_64 需要
    ---> 软件包 device-mapper.x86_64.7.1.02.158-2.el7_7.2 将被 更新
    ---> 软件包 device-mapper-event-libs.x86_64.7.1.02.149-8.el7 将被 升级
    ---> 软件包 device-mapper-event-libs.x86_64.7.1.02.158-2.el7_7.2 将被 更新
    --> 正在检查事务
    ---> 软件包 device-mapper-libs.x86_64.7.1.02.149-8.el7 将被 升级
    ---> 软件包 device-mapper-libs.x86_64.7.1.02.158-2.el7_7.2 将被 更新
    --> 解决依赖关系完成

    依赖关系解决

    =================================================================================================================================================================================================================
    Package                                                         架构                                     版本                                                   源                                         大小
    =================================================================================================================================================================================================================
    正在更新:
    device-mapper-persistent-data                                   x86_64                                   0.8.5-1.el7                                            base                                      423 k
    lvm2                                                            x86_64                                   7:2.02.185-2.el7_7.2                                   updates                                   1.3 M
    yum-utils                                                       noarch                                   1.1.31-52.el7                                          base                                      121 k
    为依赖而更新:
    device-mapper                                                   x86_64                                   7:1.02.158-2.el7_7.2                                   updates                                   294 k
    device-mapper-event                                             x86_64                                   7:1.02.158-2.el7_7.2                                   updates                                   190 k
    device-mapper-event-libs                                        x86_64                                   7:1.02.158-2.el7_7.2                                   updates                                   189 k
    device-mapper-libs                                              x86_64                                   7:1.02.158-2.el7_7.2                                   updates                                   322 k
    lvm2-libs                                                       x86_64                                   7:2.02.185-2.el7_7.2                                   updates                                   1.1 M

    事务概要
    =================================================================================================================================================================================================================
    升级  3 软件包 (+5 依赖软件包)

    总计：3.9 M
    Downloading packages:
    Running transaction check
    Running transaction test
    Transaction test succeeded
    Running transaction
    正在更新    : 7:device-mapper-1.02.158-2.el7_7.2.x86_64                                                                                                                                                   1/16 
    正在更新    : 7:device-mapper-libs-1.02.158-2.el7_7.2.x86_64                                                                                                                                              2/16 
    正在更新    : 7:device-mapper-event-libs-1.02.158-2.el7_7.2.x86_64                                                                                                                                        3/16 
    正在更新    : 7:device-mapper-event-1.02.158-2.el7_7.2.x86_64                                                                                                                                             4/16 
    正在更新    : 7:lvm2-libs-2.02.185-2.el7_7.2.x86_64                                                                                                                                                       5/16 
    正在更新    : device-mapper-persistent-data-0.8.5-1.el7.x86_64                                                                                                                                            6/16 
    正在更新    : 7:lvm2-2.02.185-2.el7_7.2.x86_64                                                                                                                                                            7/16 
    正在更新    : yum-utils-1.1.31-52.el7.noarch                                                                                                                                                              8/16 
    清理        : yum-utils-1.1.31-50.el7.noarch                                                                                                                                                              9/16 
    清理        : 7:lvm2-2.02.180-8.el7.x86_64                                                                                                                                                               10/16 
    清理        : 7:lvm2-libs-2.02.180-8.el7.x86_64                                                                                                                                                          11/16 
    清理        : 7:device-mapper-event-1.02.149-8.el7.x86_64                                                                                                                                                12/16 
    清理        : 7:device-mapper-event-libs-1.02.149-8.el7.x86_64                                                                                                                                           13/16 
    清理        : 7:device-mapper-1.02.149-8.el7.x86_64                                                                                                                                                      14/16 
    清理        : 7:device-mapper-libs-1.02.149-8.el7.x86_64                                                                                                                                                 15/16 
    清理        : device-mapper-persistent-data-0.7.3-3.el7.x86_64                                                                                                                                           16/16 
    验证中      : 7:device-mapper-libs-1.02.158-2.el7_7.2.x86_64                                                                                                                                              1/16 
    验证中      : yum-utils-1.1.31-52.el7.noarch                                                                                                                                                              2/16 
    验证中      : device-mapper-persistent-data-0.8.5-1.el7.x86_64                                                                                                                                            3/16 
    验证中      : 7:lvm2-2.02.185-2.el7_7.2.x86_64                                                                                                                                                            4/16 
    验证中      : 7:lvm2-libs-2.02.185-2.el7_7.2.x86_64                                                                                                                                                       5/16 
    验证中      : 7:device-mapper-event-1.02.158-2.el7_7.2.x86_64                                                                                                                                             6/16 
    验证中      : 7:device-mapper-event-libs-1.02.158-2.el7_7.2.x86_64                                                                                                                                        7/16 
    验证中      : 7:device-mapper-1.02.158-2.el7_7.2.x86_64                                                                                                                                                   8/16 
    验证中      : device-mapper-persistent-data-0.7.3-3.el7.x86_64                                                                                                                                            9/16 
    验证中      : 7:lvm2-2.02.180-8.el7.x86_64                                                                                                                                                               10/16 
    验证中      : yum-utils-1.1.31-50.el7.noarch                                                                                                                                                             11/16 
    验证中      : 7:lvm2-libs-2.02.180-8.el7.x86_64                                                                                                                                                          12/16 
    验证中      : 7:device-mapper-1.02.149-8.el7.x86_64                                                                                                                                                      13/16 
    验证中      : 7:device-mapper-libs-1.02.149-8.el7.x86_64                                                                                                                                                 14/16 
    验证中      : 7:device-mapper-event-1.02.149-8.el7.x86_64                                                                                                                                                15/16 
    验证中      : 7:device-mapper-event-libs-1.02.149-8.el7.x86_64                                                                                                                                           16/16 

    更新完毕:
    device-mapper-persistent-data.x86_64 0:0.8.5-1.el7                               lvm2.x86_64 7:2.02.185-2.el7_7.2                               yum-utils.noarch 0:1.1.31-52.el7                              

    作为依赖被升级:
    device-mapper.x86_64 7:1.02.158-2.el7_7.2     device-mapper-event.x86_64 7:1.02.158-2.el7_7.2     device-mapper-event-libs.x86_64 7:1.02.158-2.el7_7.2     device-mapper-libs.x86_64 7:1.02.158-2.el7_7.2    
    lvm2-libs.x86_64 7:2.02.185-2.el7_7.2        

    完毕！
    [ljs@myhd001 ~]$ 
    ```

## 设置稳定的镜像仓库
[top](#catalog)
- **只有设定了仓库，才能继续获取docker资源并安装和更新**
**可以重复设置，新的地址会覆盖旧的地址**
- 设置及确认方式
    1. 两种地址设置
        - 直接使用官方镜像地址，改地址可能无法连接，最好使用镜像加速地址
            - `yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo`
        - 使用过aliyun镜像
            - `yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo`
    2. 执行后查看设置结果：`cat /etc/yum.repos.d/docker-ce.repo`

- 使用官方镜像的执行内容
    - 执行内容
        ```
        [ljs@myhd001 ~]$ sudo yum-config-manager \
        >     --add-repo \
        >     https://download.docker.com/linux/centos/docker-ce.repo
        已加载插件：fastestmirror, langpacks
        adding repo from: https://download.docker.com/linux/centos/docker-ce.repo
        grabbing file https://download.docker.com/linux/centos/docker-ce.repo to /etc/yum.repos.d/docker-ce.repo
        repo saved to /etc/yum.repos.d/docker-ce.repo
        [ljs@myhd001 ~]$ 
        ```
    - `/etc/yum.repos.d/docker-cd.repo`文件的内容
        ```
        [root@myhd001 ljs]# cat /etc/yum.repos.d/docker-ce.repo 
        [docker-ce-stable]
        name=Docker CE Stable - $basearch
        baseurl=https://download.docker.com/linux/centos/7/$basearch/stable
        enabled=1
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-stable-debuginfo]
        name=Docker CE Stable - Debuginfo $basearch
        baseurl=https://download.docker.com/linux/centos/7/debug-$basearch/stable
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-stable-source]
        name=Docker CE Stable - Sources
        baseurl=https://download.docker.com/linux/centos/7/source/stable
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-edge]
        name=Docker CE Edge - $basearch
        baseurl=https://download.docker.com/linux/centos/7/$basearch/edge
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-edge-debuginfo]
        name=Docker CE Edge - Debuginfo $basearch
        baseurl=https://download.docker.com/linux/centos/7/debug-$basearch/edge
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-edge-source]
        name=Docker CE Edge - Sources
        baseurl=https://download.docker.com/linux/centos/7/source/edge
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-test]
        name=Docker CE Test - $basearch
        baseurl=https://download.docker.com/linux/centos/7/$basearch/test
        enabled=1
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-test-debuginfo]
        name=Docker CE Test - Debuginfo $basearch
        baseurl=https://download.docker.com/linux/centos/7/debug-$basearch/test
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-test-source]
        name=Docker CE Test - Sources
        baseurl=https://download.docker.com/linux/centos/7/source/test
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-nightly]
        name=Docker CE Nightly - $basearch
        baseurl=https://download.docker.com/linux/centos/7/$basearch/nightly
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-nightly-debuginfo]
        name=Docker CE Nightly - Debuginfo $basearch
        baseurl=https://download.docker.com/linux/centos/7/debug-$basearch/nightly
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg

        [docker-ce-nightly-source]
        name=Docker CE Nightly - Sources
        baseurl=https://download.docker.com/linux/centos/7/source/nightly
        enabled=0
        gpgcheck=1
        gpgkey=https://download.docker.com/linux/centos/gpg
        [root@myhd001 ljs]# 

        ```

## 更新yum软件包索引
[top](#catalog)
- `yum makecache fast`

## 安装docker社区版
[top](#catalog)
- `yum install docker-ce docker-ce-cli containerd.io`
    - 安装过程中需要手动确认安装内容

- 安装过程
    ```
    [ljs@myhd001 ~]$ sudo yum install docker-ce docker-ce-cli containerd.io
    已加载插件：fastestmirror, langpacks
    Loading mirror speeds from cached hostfile
    * base: mirrors.aliyun.com
    * extras: mirrors.tuna.tsinghua.edu.cn
    * updates: mirror.lzu.edu.cn
    docker-ce-stable                                                                                                                                                                          | 3.5 kB  00:00:00     
    正在解决依赖关系
    --> 正在检查事务
    ---> 软件包 containerd.io.x86_64.0.1.2.13-3.1.el7 将被 安装
    --> 正在处理依赖关系 container-selinux >= 2:2.74，它被软件包 containerd.io-1.2.13-3.1.el7.x86_64 需要
    ---> 软件包 docker-ce.x86_64.3.19.03.8-3.el7 将被 安装
    ---> 软件包 docker-ce-cli.x86_64.1.19.03.8-3.el7 将被 安装
    --> 正在检查事务
    ---> 软件包 container-selinux.noarch.2.2.107-3.el7 将被 安装
    --> 解决依赖关系完成

    依赖关系解决

    =================================================================================================================================================================================================================
    Package                                               架构                                       版本                                                源                                                    大小
    =================================================================================================================================================================================================================
    正在安装:
    containerd.io                                         x86_64                                     1.2.13-3.1.el7                                      docker-ce-stable                                      23 M
    docker-ce                                             x86_64                                     3:19.03.8-3.el7                                     docker-ce-stable                                      25 M
    docker-ce-cli                                         x86_64                                     1:19.03.8-3.el7                                     docker-ce-stable                                      40 M
    为依赖而安装:
    container-selinux                                     noarch                                     2:2.107-3.el7                                       extras                                                39 k

    事务概要
    =================================================================================================================================================================================================================
    安装  3 软件包 (+1 依赖软件包)

    总下载量：87 M
    安装大小：363 M
    Is this ok [y/d/N]: y
    Downloading packages:
    (1/4): container-selinux-2.107-3.el7.noarch.rpm                                                                                                                                           |  39 kB  00:00:00     
    warning: /var/cache/yum/x86_64/7/docker-ce-stable/packages/containerd.io-1.2.13-3.1.el7.x86_64.rpm: Header V4 RSA/SHA512 Signature, key ID 621e9f35: NOKEY                     ] 2.9 MB/s |  22 MB  00:00:22 ETA 
    containerd.io-1.2.13-3.1.el7.x86_64.rpm 的公钥尚未安装
    (2/4): containerd.io-1.2.13-3.1.el7.x86_64.rpm                                                                                                                                            |  23 MB  00:00:06     
    (3/4): docker-ce-cli-19.03.8-3.el7.x86_64.rpm                                                                                                                                             |  40 MB  00:00:09     
    (4/4): docker-ce-19.03.8-3.el7.x86_64.rpm                                                                                                                                                 |  25 MB  00:00:23     
    -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    总计                                                                                                                                                                             3.7 MB/s |  87 MB  00:00:23     
    从 https://download.docker.com/linux/centos/gpg 检索密钥
    导入 GPG key 0x621E9F35:
    用户ID     : "Docker Release (CE rpm) <docker@docker.com>"
    指纹       : 060a 61c5 1b55 8a7f 742b 77aa c52f eb6b 621e 9f35
    来自       : https://download.docker.com/linux/centos/gpg
    是否继续？[y/N]：y
    Running transaction check
    Running transaction test
    Transaction test succeeded
    Running transaction
    正在安装    : 2:container-selinux-2.107-3.el7.noarch                                                                                                                                                       1/4 
    正在安装    : containerd.io-1.2.13-3.1.el7.x86_64                                                                                                                                                          2/4 
    正在安装    : 1:docker-ce-cli-19.03.8-3.el7.x86_64                                                                                                                                                         3/4 
    正在安装    : 3:docker-ce-19.03.8-3.el7.x86_64                                                                                                                                                             4/4 
    验证中      : containerd.io-1.2.13-3.1.el7.x86_64                                                                                                                                                          1/4 
    验证中      : 1:docker-ce-cli-19.03.8-3.el7.x86_64                                                                                                                                                         2/4 
    验证中      : 3:docker-ce-19.03.8-3.el7.x86_64                                                                                                                                                             3/4 
    验证中      : 2:container-selinux-2.107-3.el7.noarch                                                                                                                                                       4/4 

    已安装:
    containerd.io.x86_64 0:1.2.13-3.1.el7                                  docker-ce.x86_64 3:19.03.8-3.el7                                  docker-ce-cli.x86_64 1:19.03.8-3.el7                                 

    作为依赖被安装:
    container-selinux.noarch 2:2.107-3.el7                                                                                                                                                                         

    完毕！
    [ljs@myhd001 ~]$ 


    [ljs@myhd001 ~]$ sudo systemctl start docker


    [ljs@myhd001 ~]$ ps -ef |grep docker
    root       6795      1  0 16:31 ?        00:00:00 /usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
    ljs        7081   4922  0 16:32 pts/1    00:00:00 grep --color=auto docker
    [ljs@myhd001 ~]$ 
    ```

## 启动docker
[top](#catalog)
- `systemctl start docker`

## 测试docker
[top](#catalog)
- `docker run hello-world`
    - 初次使用时会自动从仓库拉去测试镜像，并创建容器来进行测试
- `docker version` 测试版本
    ```
    [root@myhd001 ljs]# docker version
    Client: Docker Engine - Community
    Version:           19.03.8
    API version:       1.40
    Go version:        go1.12.17
    Git commit:        afacb8b
    Built:             Wed Mar 11 01:27:04 2020
    OS/Arch:           linux/amd64
    Experimental:      false

    Server: Docker Engine - Community
    Engine:
    Version:          19.03.8
    API version:      1.40 (minimum version 1.12)
    Go version:       go1.12.17
    Git commit:       afacb8b
    Built:            Wed Mar 11 01:25:42 2020
    OS/Arch:          linux/amd64
    Experimental:     false
    containerd:
    Version:          1.2.13
    GitCommit:        7ad184331fa3e55e52b890ea95e65ba581ae3429
    runc:
    Version:          1.0.0-rc10
    GitCommit:        dc9208a3303feef5b3839f4323d9beb36df0a9dd
    docker-init:
    Version:          0.18.0
    GitCommit:        fec3683
    [root@myhd001 ljs]# 
    ```

## 配置镜像加速
[top](#catalog)
- `mkdir -p /etc/docker`
    - 在`Docker v19.03`版本中该目录默认存在
- `vim /etc/docker/daemon.json`
    ```
    #阿里云镜像
    {
        "registry-mirrors":["https://{用户编码}.mirror.aliyuncs.com"]
    }
    ```
- `systemctl daemon-reload`
- `systemctl restart docker`

## 卸载
[top](#catalog)
- `systemctl stop docker`
- `yum remove docker-ce`
- `rm -rf /var/lib/docker`
