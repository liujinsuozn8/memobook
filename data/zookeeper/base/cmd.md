- 启动server
    - `zkServer.sh start`，启动zookeeper
    - `zkServer.sh status`， 查看 server 的状态
    - `zkServer.sh stop`，停止server
- 启动客户端
    1. 启动: `zkCli.sh`
    2. 检查 Znode: `ls /`
    3. 退出: `quit`

- client端的指令

    |指令|功能|
    |-|-|
    |`create /结点路径 "数据"`|创建持久结点|
    |`create -e /结点路径 "数据"`|创建临时结点|
    |`create -s /结点路径 "数据"`|创建带序号的结点|
    |`get /结点路径`|从结点中获取数据|
    |`set /结点路径 "新的数据"`|修改结点数据|
    |`get /结点路径 watch`|监听结点**数据**的变化<br>只能监听一次<br>监听成功后，如果再次监听，需要重新执行指令|
    |`ls /结点路径 watch`|监听结点下路径的变化<br>只能监听一次<br>监听成功后，如果再次监听，需要重新执行指令|
    |`delete /结点路径`|删除结点|
    |`rmr /结点路径`|递归删除结点|
    |`stat /结点路径`|查看结点状态|