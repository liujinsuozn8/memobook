<span id="catalog"></span>

<span style='font-size:18px'>目录---hadoop的虚拟机环境设置</span>

- [安装图形界面](#安装图形界面)
- [linux虚拟机设定](#linux虚拟机设定)
- [安装JDK和hadoop](#安装JDK和hadoop)
- [](#)

# 安装图形界面
[top](#catalog)
- 为了提高虚拟机的启动速度，最好不要安装图形界面
- yum groupinstall "GNOME Desktop" "Graphical Administration Tools"

# linux虚拟机设定
[top](#catalog)
1. 配置虚拟机的网关和IP
    - CentOS7 和 Windows这两台机子通过虚拟网关互联，网关之间相同，IP和网关不能相同
    - VMware--> 编辑--> 虚拟网络编辑器
        - NAT模式
        - Vmnet8 **网关 192.168.abc.000**
    - 控制面板网络连接 VMnet8的IPv4配置
        - IP: 192.168.abc.xxx
        - 子网掩码:255.255.255.0
        - **默认网关:192.168.abc.000**
        - 首选DNS服务器地址:192.168.abc.000

2. 设置固定IP
    - 在root用户下操作
    - `ip addr` 或 `ifconfig` 查看网卡名 `ensXX`
        - 一般是 `ens33`
    - 更改设定
        - `vim /etc/sysconfig/network-scripts/ifcfg-ensXX`
        - 需要配置的项目
            - ONBOOT=yes
            - BOOTPROTO=static
            - IPADDR0=192.168.abc.100 （IP）
            - PREFIX=24
            - GATEWAY0=192.168.abc.000 (和网关相同)
            - DNS=192.168.abc.000 (和网关相同)
    - 重启网络服务 `service network restart`
3. 配置主机名
    - `vim /etc/hostname`
4. 配置主机映射
    - `vim /etc/sysconfig/network`
        - NETWORKING=yes
        - HOSTNAME=主机名
    - `vim /etc/hosts`
        - 添加一行映射: `192.168.abc.100 主机名`
5. 赋予用户root权限
    - `vim /etc/sudoers`
    - 添加：`用户名 ALL=(ALL)  ALL`
6. windows 下配置映射
    - `c/windows/system32/drivers/etc/host`
    - 需要设置 主机名 和 ip 的映射，否则可能无法正常连接
7. 外网连接测试
    - `ping www.baidu.com`
    - 如果无法连接，将 `/etc/sysconfig/network-scripts/ifcfg-ensXX` 中的DNS改为
        ```sh
        DNS1=8.8.8.8
        DNS2=8.8.8.4
        ```
8. yum 换源
    - `curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo`

# 安装JDK和hadoop
[top](#catalog)
1. 创建目录
    1. 两个目录

        |目录|功能|
        |-|-|
        |`sudo mkdir /opt/software`|存储jar包|
        |`sudo mkdir /opt/module`|存储解压结果|

    2. 设定用户组
        - `sudo chown 用户名:用户名 software/ module/`
        - 不设置默认所有者都是`root`
2. 导入jar包(tar.gz)到 sofrware
    - jar=jdk(8以上) + hadoop
3. 安装jdk包
    1. 解压
        - `tar -zxvf jar... -C /opt/module/`
    2. 配置环境变量
        - `sudo vim /etc/profile`
        - 跳转到末尾，并添加配置
            ```sh
            ##JAVA_HOME
            export JAVA_HOME=..jdk
            export PATH=$PATH:$JAVA_HOME/bin
            ```
    3. 重新加载配置: `source /etc/profile`
    4. 检查java版本: `java -version`
4. 安装hadoop
    1.  解压
        - `tar -zxvf hadoop... -C /opt/module/`
    2. 配置环境变量
        ```sh
        ##HADOOP_HOME
        export HADOOP_HOME=...
        export PATH=$PATH:$HADOOP_HOME/bin
        export PATH=$PATH:$HADOOP_HOME/sbin
        ```
    3. 重新加载配置: `source /etc/profile`
    4. 检查能否使用 `hadoop`

[top](#catalog)