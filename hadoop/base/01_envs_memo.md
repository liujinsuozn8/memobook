* yum groupinstall "GNOME Desktop" "Graphical Administration Tools"
* 虚拟机设定
    1. 配置虚拟机的网关和IP
        * CentOS和Windows这两台机子通过虚拟网关互联，网关之间相同，IP和网关不能相同
        * VMware-虚拟网络编辑器
            * Vmnet8 **网关 192.168.abc.000**
        * 控制面板网络连接 VMnet8的IPv4配置
            * IP: 192.168.abc.xxx
            * 子网掩码:255.255.255.0
            * **默认网关:192.168.abc.000**
            * 首选DNS服务器地址:192.168.abc.000

    2. vim /etc/udev/rules.d/70-persistent-net.rules ？？？？
    3. 设置固定IP
        * `ip addr`或`ifconfig` 查看网卡名 ensXX
        * 更改设定
            * `vim /etc/sysconfig/network-scripts/ifcfg-ensXX`
                * ONBOOT=yes
            * 重启
            * 设置固定IP
                * BOOTPROTO=static
                * IPADDR0=192.168.abc.100 （IP）
                * PREFIX=24
                * GATEWAY0=192.168.abc.000 (和网关相同)
                * DNS=192.168.abc.000 (和网关相同)
        * 重启网络服务 `service network restart`
    4. 配置主机名
        * vim /etc/hostname
    5. 配置主机映射
        * vim /etc/sysconfig/network
            * NETWORKING=yes  
            * HOSTNAME=name
        * vim /etc/hosts
            * 添加一行映射: 192.168.abc.100 name
    6. 赋予用户root权限
        * vim /etc/sudoers
        * 添加：`用户名 ALL=(ALL)  ALL`
    7. windows 下配置映射？？？？
        * c/windows/system32/drivers/etc/host
            
* 创建文件夹 /opt/software/，/opt/module/
    * softeware 存储jar包
    * sudo mkdir software/ sudo mkdir module
    * 设定用户组 sudo chown 用户名:用户名 software/ module/
* 导入jar包(tar.gz)到 sofrware
    * jar=jdk(8以上) + hadoop
* 安装jdk包
    * 解压：tar -zxvf jar... -C /opt/module/
    * 获取解压路径 pwd
    * 配置环境变量 
        * sudo vim /etc/profile
        * 跳转到末尾 shift+g
        * 到下一行 o
        * 添加配置
            * ##JAVA_HOME
            * export JAVA_HOME=..jdk
            * export PATH=$PATH:$JAVA_HOME/bin
        * source /etc/profile
        * 检查java版本 java -version
* 安装hadoop
    * 解压：tar -zxvf hadoop... -C /opt/module/
    * 获取解压路径：pwd 
    * 配置环境变量
        * ##HADOOP_HOME
        * export HADOOP_HOME=...
        * export PATH=$PATH:$HADOOP_HOME/bin
        * export PATH=$PATH:$HADOOP_HOME/sbin （复制快捷键 yy + p）