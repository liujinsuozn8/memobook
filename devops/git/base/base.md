- 参考
    - https://git-scm.com/book/zh/v2
    - https://abcops.cn/archives/458

<span id="catalog"></span>

### 目录
- [安装](#安装)
- [版本控制](#版本控制)
- [git简介](#git简介)
- [git配置](#git配置)
- [创建git仓库](#创建git仓库)
- [git底层概念及底层命令](#git底层概念及底层命令)
    - [区域与对象](#区域与对象)
    - [git对象](#git对象)
    - [树对象](#树对象)
    - [提交对象](#提交对象)
- [git高层命令-基本crud命令](#git高层命令-基本crud命令)
    - [crud命令命令概述](#crud命令命令概述)
    - [status指令及文件状态](#status指令及文件状态)
    - [add指令](#add指令)
    - [commit指令](#commit指令)
    - [diff指令](#diff指令)
    - [mv指令](#mv指令)
    - [log指令](#log指令)
- [git高层命令-分支操作](#git高层命令-分支操作)
- [](#)
- [](#)
- [总结](#总结)


# 安装
[top](#catalog)
- 参考：https://git-scm.com/book/zh/v2/起步-安装-Git
- 两种安装方式
    1. 通过包管理命令安装，**但是版本可能会滞后**
        - linux
            ```
            sudo dnf install git-all -y
            ```
        - Ubuntu
            ```
            sudo apt install git-all
            ```
    2. 源码安装
        1. 安装 Git 依赖的库：autotools、curl、zlib、openssl、expat 和 libiconv
            ```
            sudo dnf install dh-autoreconf curl-devel expat-devel gettext-devel \
            openssl-devel perl-devel zlib-devel
            ```
        2. 为了添加文档的多种格式（doc、html、info），需要以下附加的依赖
            ```
            sudo dnf install asciidoc xmlto docbook2X
            ```
        3. 如果使用基于 RPM 的发行版
            ```
            sudo dnf install getopt
            ```
        4. 如果使用 Fedora/RHEL/RHEL衍生版，解决二进制文件名的不同
            - 检查目标文件是否存在
                ```
                /usr/bin/db2x_docbook2texi
                ```
            - 如果该文件不存在需要安装.如果不安装,执行`make all doc info`是会报错：`docbook2x-texi: command not found`
                ```
                rpm -Uvh http://dl.fedoraproject.org/pub/epel/7/x86_64/Packages/d/docbook2X-0.8.8-17.el7.x86_64.rpm
                ```
            - 解决二进制文件名的不同
                ```
                sudo ln -s /usr/bin/db2x_docbook2texi /usr/bin/docbook2x-texi
                ```
        5. 从git上下载某个版本的镜像文件:`git-XXXXXX.tar.gz`
            - https://github.com/git/git/releases

        6. 编译并安装
            ```
            tar -zxf git-XXXXXX.tar.gz
            cd git-XXXXXX
            make configure
            ./configure --prefix=/usr
            make all doc info
            make install install-doc install-html install-info
            ```
        7. 安装后会在 `/usr/bin/` 目录下生成几个可执行文件
            ```
            [root@ea284d43453a bin]# ll /usr/bin/ |grep git
            -rwxr-xr-x 130 root root   15847808 Apr  4 07:28 git
            -rwxr-xr-x   2 root root     162731 Apr  4 07:28 git-cvsserver
            -rwxr-xr-x 130 root root   15847808 Apr  4 07:28 git-receive-pack
            -rwxr-xr-x   2 root root    9284840 Apr  4 07:28 git-shell
            -rwxr-xr-x 130 root root   15847808 Apr  4 07:28 git-upload-archive
            -rwxr-xr-x 130 root root   15847808 Apr  4 07:28 git-upload-pack
            -rwxr-xr-x   1 root root     353477 Apr  4 07:28 gitk
            ```

- 查看 git 版本
    ```
    git version
    ```

- 配置个人用户的用户名和邮箱（参考：[git配置](#git配置)）
    ```
    git config --global user.name "ljs"
    git config --global user.email "@qq.com"
    ```


# 版本控制
[top](#catalog)
- 什么是版本控制
    - 版本控制是一种记录文件内容变化，以便将来查阅特定版本修订情况的系统
- 使用版本控制可以：
    - 回滚文件
    - 比较文件变化细节

- 两种版本控制系统
    - 集中化的版本控制系统，如：SVN
    - 分布式的版本控制系统，如：Git、BItKeeper
            
- 两种版本控制系统的比较

    ||集中化|分化式|
    |-|-|-|
    |**管理位置**|单一的中央服务器|本地、服务器|
    |**如何管理**|<ul><li>连接服务器，通过客户端来操作</li><li>每个人都可以一定程度上看到其他人的工作内容</li><li>管理员可以控制每个开发者的权限</li></ul>|创建代码仓库的**完整镜像**，可以在本地和远程仓库中进行版本管理|
    |**如何保存版本差异**|保存版本与版本之间的差异内容，即文件快照|保存索引|
    |**存储特点**|占用的磁盘空间小，但是<label style="color:red">回滚速度慢</label>|占用的磁盘空间大，但是<label style="color:red">回滚速度快</label>|
    |**单点故障处理**|<ol><li>网络故障 <ul><li>出现网络故障时，会导致无法提交文件操作，影响开发进度</li></ul> </li><li>磁盘故障<ul><li>如果磁盘故障时，没即时备份，可能会导致数据丢失</li><li>存在丢失整个项目历史记录的风险</li></ul></li></ol>|<ol><li>网络故障<ul><li>可以继续开发，通过本地镜像来管理</li></ul></li><li>磁盘故障<ul><li>出现磁盘故障时，可以用其他客户端的镜像进行恢复</li></ul></li></ol>|
    |**多项目协作**|-|可以和不同的远端代码仓库进行交互，可以和不同项目的人进行相互协作|

# git简介
[top](#catalog)
- git是世界上最先进的分布式版本控制系统
- git的特点
    - 分支快速切换
    - 容量小（压缩）
    - 设计简单
    - 完全分布式
    - 支持非线性开发模式，允许上千个并行开发的分支
    - 有能力高效管理类似Linux内核一样的超大规模项目

# git配置
## git配置的基本知识
[top](#catalog)
- `git config`：git通过该命令来配置或读取工作环境变量
- git提供了三个文件来存放不同级别的配置，<label style="color:red">高级别会覆盖低级别的相同配置</label>

    |级别|文件路径|适用范围|指令选项|
    |-|-|-|-|
    |1|`/etc/gitconfig`|对当前系统中所有用户都适用的配置|`git config --system`|
    |2|`~/.gitconfig`|只适用于当前用户的|`git config --global`|
    |3|`.git/config`|当前项目的git目录中的配置文件，只针对当前项目有效|`git config`|

## git配置的crud操作
[top](#catalog)
- crud操作
    - 查看所有配置内容
        - `git config [--system/global] --list`
        - `git config [--system/global] -l`
    - 添加/更改配置
        - `git config [--system/global] 配置名 "配置值"`
            - 配置值最好用 `""` 括起来，防止特殊字符导致的异常
    - 删除配置
        - `git config --global --unset "配置名"`


- 为复杂的指令添加别名：`git config [--system/global] alias.指令别名 "指令内容，去掉开头的git"`
    - 通过：`git 指令别名`，来调用指令
    - 示例：为查看详细分支图的指令设置别名
        ```
        git config --global alias.hpic \
        "log --oneline --decorate --graph --all"
        ```

- 设置用户名和邮箱
    ```
    git config --global user.name "ljs"
    git config --global user.email "xxx@qq.com"
    ```


# 创建git仓库
[top](#catalog)
- `git init`，创建仓库
    - 需要在项目目录下执行该指令
    - 执行后会生成一个隐藏目录：`.git`，即**版本库**
        - 在项目目录下进行的任何操作都会记录到`.git`中
- `.git`目录下的内容
    - 目录

        |目录|描述|
        |-|-|
        |<label style="color:red">objects</label>|存储所有数据内容|
        |<label style="color:red">refs</label>|存储指向数据的提交对象指针（分支）|
        |hooks|目录包含客户端或服务端的钩子脚本，即执行某些git操作前的一些操作|
        |info|包含一个全局性排除文件|
        |logs|保存日志信息|

    - 文件

        |文件|描述|
        |-|-|
        |<label style="color:red">HEAD</label>|表示当前正在使用的分支|
        |<label style="color:red">index</label>|保存暂存区信息|
        |config|包含当前项目所使用的配置信息|
        |description|仓库的描述信息|

# git底层概念及底层命令
## 区域与对象
[top](#catalog)
- 区域
    - git的3种区域
        - 工作区
        - 暂存区
        - 版本区
    - 区域的一般使用流程：工作区--> 暂存区--> 版本区

- 对象
    - git的三种对象
        - git对象
        - 树对象
        - 提交对象

    - git对象，对应文件版本
    - 树对象，对应项目快照
    - 提交对象，对应项目版本

## git对象
[top](#catalog)
- git对象的本质是：blob类型的键值对
- git对象表示的是文件的一次版本

- git的核心部分是一个简单的键值对数据库
    - 可以向该数据库插入任意类型的内容，包括数据和文件等等，git会返回一个键值
    - 通过键值可以在任意时刻再次检索该内容
    - 所有的数据都保存在`.git/objects`目录下，即版本区
    - <label style="color:red">git对象的操作都是在本地数据库进行的，即工作区与版本区产生交互，不涉及暂存区</label>

- git对象/键值对的读写指令
    - `echo 'test01' | git hash-object -w --stdin`
        - 用途：向数据库写入内容，并返回键值
            - 可以写入数据，也可以写入文件内容
        - 选项说明
            - `-w`，执行指令后，存储数据对象；如果不使用则只返回键值
                - 执行后会在`./.git/objects/hash值前两位/hash值剩余位` 路径创建压缩文件
                - 使用 `cat` 指令会打印出乱码
            - `--stdin`，从标准输入读取内容；如果不指定，需要在命令末尾给出特定文件的路径
        - 选项组合用法
            - `git hash-object -w 文件路径`，用于存储文件
            - `git hash-object 文件路径`，用于返回对应文件的键值
        - 指令的返回值
            - 40个字符的检验和，是一个`SHA-1`哈希值

    - `git cat-file [option] 键值`
        - 用途：根据键值拉取数据
        - 选项说明
            - `-p`，自动判断内容的类型，并显示格式友好的内容
            - `-t`，查看对象类型

- 简单的版本控制
    1. 创建文件：`echo "SDFFE XCcvb  fbef" > my01.txt`
    2. 创建git对象/键值对：`git hash-object -w my01.txt`，生成一个键值
    3. 检查git对象/键值对的内容：`git cat-file -p 2的键值`
    4. 修改文件：echo "xcvcbvb ythytj" >> my01.txt 
    5. 重新保存键值对：`git hash-object -w my01.txt`，生成一个**新的键值**
    6. 检查git对象/键值对的内容：`git cat-file -p 5的键值`

- 只使用 `git hash-object` 和 `git cat-file` 做版本的问题
    1. 无法记住文件的每一个版本对于的SHA-1值
    2. 一个键值对只能代表一个文件的一个版本，不能代表整个项目的快照
    3. 文件名没有被保存，只保存了文件的内容

## 树对象
[top](#catalog)
- 树对象的本质是：tree类型的键值对，也是一种git对象
- 树对象表示的是项目的一次快照，暂存区中的所有内容都会保存到树对象中

- git使用类似uninx文件系统的方式存储内容。所有内容以树对象和git对象的形式存储
    - 树对象表示unix的目录
    - git对象表示文件内容

- 树对象的特点
    - 组织多个文件
    - 一个树对象可以包含另一个树对象
    - 一个树对象包含了一条或多条记录，每条记录含有一个指向git对象或者子树对象的 SHA-1 指针、文件mode、类型、文件信息

- 树对象的操作指令
    - `git upate-index [option] 文件名`，为文件创建一个暂存区
        - 执行后会将暂存区的信息保存到: `.git/index`文件中
        - 选项说明
            - `--add`
                - 如果某个文件不再暂存区，第一次使用时需要添加该属性
                - 第一次使用时，如果使用了：`git undate-index -add 文件名`，没有附加`-cacheinfo 键值`，则会自动执行两步操作
                    1. `git hash-object -w 文件名`，为文件生成git对象
                    2. `git update-index --add --cacheinfor 1生成的键值 文件名`，将git对象添加到暂存区
            - `--cacheinfo 文件模式 键值`
                - 将文件的git对象添加到暂存区，即当前文件已经位于git数据库中
            
        - 文件模式
            - 6位数字表示
            - 前三位表示文件类型
                - 100 表示文件
                - 120 表示符号连接
            - 后三位表示ugo用户的rwx权限
        - 对于同一个文件
            - 第一次使用需要附加 `--add` 选项，在暂存区创建该文件的快照
            - 后续使用时，不需要附加 `--add` 选项，需要使用相同的文件名和新的键值
            - 执行之后会更新暂存区中的文件版本，即新版本覆盖旧版本
                - 通过`git ls-files -s`，看到的只有最新版本
        - 常用组合
            - `git update-index -add new.txt`

    - `git write-tree`，生成树对象，即创建一个当前暂存区的快照
        - 执行后创建一个git对象，并返回一个键值
        - <label style="color:red">执行后不会清空暂存区</label>
    - `git read-tree [选项] 另一个树对象hash值`，将一个树对象加入当前最新的树对象
        - 执行后需要执行：`git write-tree`来创建新的树对象
        - 选项说明
            - `-prefix=bak`

- 树对象的问题
    - 每个树对象代表了暂存区不同阶段的快照，都有自己的SHA-1值
    - 重用树对象时，必须记住每个对象的版本内容及SHA-1值，非常繁琐

- 示例
    - 示例内容
        1. 创建文件 `my01.txt` 的git对象，添加到暂存区，然后生成树对象
            1. 为文件 `my01.txt` 创建第一个版本git对象
                ```
                root@ea284d43453a testpag]# git hash-object -w my01.txt 
                6623d299f5de2adfb57f23f579f35a3cb6e7b30f
                ```
            2. 为文件创建暂存区
                ```
                [root@ea284d43453a testpag]# git update-index --add \
                 --cacheinfo 100755 6623d299f5de2adfb57f23f579f35a3cb6e7b30f my01.txt 
                ```
            3. 检查暂存区内容，与创建的内容一致
                ```
                [root@ea284d43453a testpag]# git ls-files -s
                100755 6623d299f5de2adfb57f23f579f35a3cb6e7b30f 0       my01.txt
                ```
            4. 检查 obejct 目录下的git对象，只有 `my01.txt` 文件的git对象
                ```
                [root@ea284d43453a testpag]# find .git/objects/ -type f
                .git/objects/66/23d299f5de2adfb57f23f579f35a3cb6e7b30f
                [root@ea284d43453a testpag]# 
                ```
            5. 对暂存区进行快照
                ```
                [root@ea284d43453a testpag]# git write-tree
                d1b27968fb9a42f524dd6889bdb405eb20c95009
                ```
            6. 检查`write-tree`的键值类型
                ```
                [root@ea284d43453a testpag]# git cat-file -t d1b27968fb9a42f524dd6889bdb405eb20c95009
                tree
                ```
            7. 检查 obejct 目录下的git对象，包含 `my01.txt` 文件和工作区的两个git对象
                ```
                [root@ea284d43453a testpag]# find .git/objects/ -type f
                .git/objects/d1/b27968fb9a42f524dd6889bdb405eb20c95009
                .git/objects/66/23d299f5de2adfb57f23f579f35a3cb6e7b30f
                ```
        2. 修改 `my01.txt` 文件，并更新暂存区
            1. 修改 `my01.txt` 文件，生成新的git对象
                ```
                [root@ea284d43453a testpag]# echo "version v2" >> my01.txt
                [root@ea284d43453a testpag]# git hash-object -w my01.txt 
                06521ff6147d10f913cf7b6984592c7dea618dbb
                ```
            2. 检查 obejct 目录下的git对象，增加了一个新的git对象文件
                ```
                [root@ea284d43453a testpag]# find .git/objects/ -type f
                .git/objects/d1/b27968fb9a42f524dd6889bdb405eb20c95009
                .git/objects/06/521ff6147d10f913cf7b6984592c7dea618dbb
                .git/objects/66/23d299f5de2adfb57f23f579f35a3cb6e7b30f
                ```
            3. 更新暂存区
                ```
                [root@ea284d43453a testpag]# git update-index \
                --cacheinfo 100755  06521ff6147d10f913cf7b6984592c7dea618dbb my01.txt
                ```
            4. 检查暂存区
                ```
                [root@ea284d43453a testpag]# git ls-files -s
                100755 06521ff6147d10f913cf7b6984592c7dea618dbb 0       my01.txt
                [root@ea284d43453a testpag]# 
                ```
        3. 添加新的文件 `my02.txt`，并加入暂存区
            1. 创建新文件 `my02.txt`
                ```
                [root@ea284d43453a testpag]# echo "version v1 :weretysdfg" > my02.txt
                ```
            2. 直接将 `my02.txt` 添加到暂存区，有git自动创建git对象
                ```
                [root@ea284d43453a testpag]# git update-index --add my02.txt
                ```
            3. 检查生成的git对象
                ```
                [root@ea284d43453a testpag]# find .git/objects/ -type f
                .git/objects/d1/b27968fb9a42f524dd6889bdb405eb20c95009
                .git/objects/0c/fcc731008852cee8ab9398d4db19a32d7529e4
                .git/objects/06/521ff6147d10f913cf7b6984592c7dea618dbb
                .git/objects/66/23d299f5de2adfb57f23f579f35a3cb6e7b30f
                ```
            4. 检查暂存区
                ```
                [root@ea284d43453a testpag]# git ls-files -s
                100755 06521ff6147d10f913cf7b6984592c7dea618dbb 0       my01.txt
                100644 0cfcc731008852cee8ab9398d4db19a32d7529e4 0       my02.txt
                ```
        4. 生成 `my02.txt` 和 新版 `my01.txt` 的树对象
            1. 创建树对象
                ```
                [root@ea284d43453a testpag]# git write-tree
                4197ea266921c903ff40d3b1f1b095edb3e1487e
                [root@ea284d43453a testpag]# 
                ```
            2. 检查git对象
                ```
                [root@ea284d43453a testpag]# find .git/objects/ -type f
                .git/objects/d1/b27968fb9a42f524dd6889bdb405eb20c95009
                .git/objects/41/97ea266921c903ff40d3b1f1b095edb3e1487e
                .git/objects/0c/fcc731008852cee8ab9398d4db19a32d7529e4
                .git/objects/06/521ff6147d10f913cf7b6984592c7dea618dbb
                .git/objects/66/23d299f5de2adfb57f23f579f35a3cb6e7b30f
                [root@ea284d43453a testpag]# 
                ```
        5. 将第一个树对象添加到第二个树对象(当前最新的树对象)中
            1. 将第一个树对象添加到第二个树对象
                ```
                [root@ea284d43453a testpag]# git read-tree --prefix=bak d1b27968fb9a42f524dd6889bdb405eb20c95009
                [root@ea284d43453a testpag]# git write-tree
                d5f79d82cf046ee2a06095ad28f041ef6ec057b7
                [root@ea284d43453a testpag]# 
                ```
            2. 检查暂存区的内容，添加了：my01.txt version_v1 的git对象
                ```
                [root@ea284d43453a testpag]# git ls-files -s
                100755 6623d299f5de2adfb57f23f579f35a3cb6e7b30f 0       bak/my01.txt
                100755 06521ff6147d10f913cf7b6984592c7dea618dbb 0       my01.txt
                100644 0cfcc731008852cee8ab9398d4db19a32d7529e4 0       my02.txt
                [root@ea284d43453a testpag]# 
                ```
            3. 检查git对象，生成了一个新的git对象：d5f79d82cf046ee2a06095ad28f041ef6ec057b7
                ```
                [root@ea284d43453a testpag]# find .git/objects/ -type f
                .git/objects/d1/b27968fb9a42f524dd6889bdb405eb20c95009
                .git/objects/41/97ea266921c903ff40d3b1f1b095edb3e1487e
                .git/objects/0c/fcc731008852cee8ab9398d4db19a32d7529e4
                .git/objects/06/521ff6147d10f913cf7b6984592c7dea618dbb
                .git/objects/66/23d299f5de2adfb57f23f579f35a3cb6e7b30f
                .git/objects/d5/f79d82cf046ee2a06095ad28f041ef6ec057b7
                [root@ea284d43453a testpag]# 
                ```
            4. 检查树对象中的内容: 包含两个文件最新版本的git对象，和新加入的树对象
                ```
                [root@ea284d43453a testpag]# git cat-file -p d5f79d82cf046ee2a06095ad28f041ef6ec057b7
                040000 tree d1b27968fb9a42f524dd6889bdb405eb20c95009    bak
                100755 blob 06521ff6147d10f913cf7b6984592c7dea618dbb    my01.txt
                100644 blob 0cfcc731008852cee8ab9398d4db19a32d7529e4    my02.txt
                [root@ea284d43453a testpag]# 
                ```

    - 操作结果分析
        - 生成的git对象
            ```
            d1b27968fb9a42f524dd6889bdb405eb20c95009 my01.txt v1 的树对象 : 树对象01
            6623d299f5de2adfb57f23f579f35a3cb6e7b30f my01.txt v1 的git对象
            06521ff6147d10f913cf7b6984592c7dea618dbb my01.txt v2 的git对象
            0cfcc731008852cee8ab9398d4db19a32d7529e4 my02.txt v1 的git对象
            4197ea266921c903ff40d3b1f1b095edb3e1487e my02.txt v1、my01.txt v2的树对象 : 树对象02
            d5f79d82cf046ee2a06095ad28f041ef6ec057b7 树对象02<--树对象01 : 树对象03
            ```
        - git对象、树对象的结果关系
            - 树对象02中只有；my01.txt v2、my02.txt v1
            - 向树对象02中加入树对象01后生成了树对象03
            ```
            树对象03
            ┌──────────────────────────────────────┐
            │          树对象02─────────────┐       │
            │             │                │       │
            │     ┌───────┴──────┐         │       │
            │     │              │         V       │
            │ my01.txt v2   my02.txt v1   bak      │
            │                              │       │
            │                            树对象02   │
            │                              │       │
            │                          my01.txt v1 │
            └──────────────────────────────────────┘
            ```

## 提交对象
[top](#catalog)
- 提交对象的本质是：commit类型的键值对，也是一种git对象
- 提交象表示的是项目的一个版本
- **提交对象是链式的，创建条对象时必须要有父提交对象的 SHA-1 值**
- 因为每个提交对象都包含当前版本的全部内容(保存在树对象中)，所以切换版本时，只需要**指定提交对象的 SHA-1 值，就可以快速切换**

- 提交对象的操作指令
    - `git commit-tree 树对象的SHA-1值 [option]`
        - 创建一个提交对象
        - 选项说明
            - `-p 父提交对象的SHA-1值`
                - 第一个版本可以不用指定，后续的版本必须指定父提交对象，才能完成一个完成的版本链
            - `-m 提交信息`
            - `-F 文件名`，从指定文件中读取提交信息
        - <label style="color:red">执行后不会清空暂存区</label>

- 示例，接：[树对象](#树对象)
    - 示例内容
        1. 创建树对象01的提交对象
            1. 创建提交对象
                ```
                [root@ea284d43453a testpag]# git commit-tree d1b27968fb9a42f524dd6889bdb405eb20c95009 -m "first commit"
                dd292f24bebab06712cd00fa7ecbcd9193ef243b
                [root@ea284d43453a testpag]# 
                ```
            2. 查看提交对象的内容：树对象01的SHA-1、用户信息、提交信息
                ```
                [root@ea284d43453a testpag]# git cat-file -p dd292f24bebab06712cd00fa7ecbcd9193ef243b
                tree d1b27968fb9a42f524dd6889bdb405eb20c95009
                author ljs <---------@qq.com> 1586149714 +0000
                committer ljs <---------710419489@qq.com> 1586149714 +0000

                first commit
                [root@ea284d43453a testpag]# 
                ```
        2. 创建树对象02的提交对象
            1. 创建提交对象
                ```
                [root@ea284d43453a testpag]# git commit-tree 4197ea266921c903ff40d3b1f1b095edb3e1487e \
                -p dd292f24bebab06712cd00fa7ecbcd9193ef243b -m "second commit"
                77253a978b58f132feeb5318b134e9eae950cdbf
                [root@ea284d43453a testpag]# 
                ```
            2. 查看提交对象的内容：树对象02的SHA-1、父提交对象的SHA-1，用户信息、提交信息
                ```
                [root@ea284d43453a testpag]# git cat-file -p 77253a978b58f132feeb5318b134e9eae950cdbf
                tree 4197ea266921c903ff40d3b1f1b095edb3e1487e
                parent dd292f24bebab06712cd00fa7ecbcd9193ef243b
                author ljs <---------@qq.com> 1586150151 +0000
                committer ljs <---------@qq.com> 1586150151 +0000

                second commit
                [root@ea284d43453a testpag]# 
                ```
        3. 创建树对象03的提交对象
            1. 创建提交对象
                ```
                [root@ea284d43453a testpag]# git commit-tree d5f79d82cf046ee2a06095ad28f041ef6ec057b7 \
                -p 77253a978b58f132feeb5318b134e9eae950cdbf -m "third commit"
                6b759319f2e2ba73ddb174474c96cbf1339c9d91
                [root@ea284d43453a testpag]# 
                ```
            2. 查看提交对象的内容：树对象03的SHA-1、父提交对象的SHA-1，用户信息、提交信息
                ```
                [root@ea284d43453a testpag]# git cat-file -p 6b759319f2e2ba73ddb174474c96cbf1339c9d91
                tree d5f79d82cf046ee2a06095ad28f041ef6ec057b7
                parent 77253a978b58f132feeb5318b134e9eae950cdbf
                author ljs <710419489@qq.com> 1586150457 +0000
                committer ljs <710419489@qq.com> 1586150457 +0000

                third commit
                [root@ea284d43453a testpag]# 
                ```
    - 各对象之间的关系
        ```
        ┌─────────────┐
        │　提交对象03　 ├───>> 树对象03 ───────────┬─────────bak──┐
        │third commit │                         │              │
        └────┬────────┘                         │              │
             │                                  │              │
             │                                  ├────────────┐ │
             V                                  │            │ │
        ┌─────────────┐                         V            │ │
        │　提交对象02　 ├───>> 树对象02 ─┬───>> my01.txt v2     │ │
        │second commit│               └───>> my02.txt v1 <<──┘ │
        └────┬────────┘                                        │
             │                                                 │
             │                                                 │
             V                                                 │
        ┌─────────────┐                                        │
        │　提交对象01　 ├───>> 树对象01 ───>> my01.txt v1         │    
        │first commit │         ^                              │
        └─────────────┘         ^                              │
                                └──────────────────────────────┘
        ```

# git高层命令-基本crud命令
## crud命令命令概述
[top](#catalog)
- 所有高层命令都是由多个底层命令组合而成
- git操作的最基本流程
    1. 创建工作目录：`git init`
    2. 修改工作区文件（工作目录下的文件）
    3. `git add 文件路径`，将修改后的文件放入暂存区（工作区-->版本区-->暂存区）
    4. `git commit -m 提交信息`
- 一次git提交只会有一个树对象、一个提交对象、多个git对象。如果是删除文件，则目标文件的git对象会从暂存区删除

-  crud命令

    |指令|说明|
    |-|-|
    |`git status`|[status指令](#status指令及文件状态)|
    |`git add 文件路径`|[add指令](#add指令)|
    |`git commit -m 提交信息`|[commit指令](#commit指令)|
    |`git diff`|[diff指令](#diff指令)|
    |`git mv 旧文件名 新文件名`|[mv指令](#mv指令)|
    |`git rm 文件路径`|删除文件|
    |`git log`|[log指令](#log指令)|

## status指令及文件状态
[top](#catalog)
- 指令：`git status`
    - 检查工作目录下文件的状态

- 工作目录下所有文件都有两种状态：`已跟踪`、`未跟踪`
    - `已跟踪` 状态文件
        - 即已经纳入版本管理的文件，在上次的项目快照中存在记录（树对象中有记录）
        - 这些文件的实际状态可能是
            - 已提交
            - 已修改
            - 已暂存
    - `未跟踪` 状态文件
        - 所有未纳入版本管理的文件，在上次的项目快照中不存在记录（树对象中没有记录），也不在当前暂存区（没有执行`git update-index`/`git add`）

- 使用git时的文件状态变化
    - ![file_status_lifecycle](imgs/lifecycle/file_status_lifecycle.jpg)
    - 文件状态

        |标识|含义|
        |-|-|
        |untracked|未跟踪|
        |unmodified|未修改|
        |modified|已修改|
        |staged|已暂存|


- 使用: `git status`指令后，提示信息中的内容
    - `Untracked files`: 表示文件未跟踪
        ```
        Untracked files:
        (use "git add <file>..." to include in what will be committed)
                xxxx.txt
        ```
    - `Changes to be committed`: 表示文件都已暂存，但是未提交
        ```
        Changes to be committed:
        (use "git rm --cached <file>..." to unstage)
                new file:   XXXX
        ```

- 特殊情况：文件已跟踪，且未提交时，再次修改文件，`git status`指令会显示文件同时是已修改和未暂存的状态
    - 控制台信息
        ```
        On branch master

        No commits yet

        Changes to be committed:
        (use "git rm --cached <file>..." to unstage)
                new file:   file01.txt

        Changes not staged for commit:
        (use "git add <file>..." to update what will be committed)
        (use "git restore <file>..." to discard changes in working directory)
                modified:   file01.txt
        ```
    - 原因分析
        - git只暂存了执行`git add`指令时的版本，所以`Changes to be committed`中会有文件的提示
            - 如果此时提交，**将会提交未修改的版本，而不是已修改的版本**
        - 因为文件尚未提交，只有暂存区有，**修改后的版本和暂存区的版本无法相互比较**，所以修改后的文件，仍然被认为是未暂存的状态
        - 对于这种情况需要**重新执行** `git add`，来更新暂存区，否则将会提交修改前的版本

## add指令
[top](#catalog)
- 用途：将新文件和已修改文件添加到暂存区
- 指令：`git add 文件路径`
    - 如果路径是一个目录，则会递归执行
    - `git add ./`，将当前目录下的所有文件放入暂存区

- add指令执行后，数据在git区域中的流动
    - 表面：工作区-->暂存区
    - <label style="color:red">底层：工作区-->版本区-->暂存区</label>

- 指令的工作流程
    1. 检测有哪些文件发生了修改
    2. 为**每个**发生修改的文件创建git对象：工作区-->版本区
        - 即执行：`git hash-object -w 文件名`
    3. 将git对象添加到暂存区：版本区-->暂存区
        - 即执行：`git update-index [--add] --cacheinfo 文件mode 键值 文件名`

- add指令在底层是**增量式**的，而不是覆盖式的
    - <label style="color:red">所以git是绝对安全的，即使暂存区的数据没有被提交、或丢失，都可以从版本区找到对应的内容</label>


## commit指令
[top](#catalog)
- 用途：将暂存区的记录提交到版本区
- 指令：`git commit [option]`
    - 选项说明
        - `-m 提交信息`，添加版本信息
            - 如果不使用 `-m` 参数，将会自动启动vi编辑器
        - `-a`，跳过暂存区，直接提交所有已跟踪已修改的文件
            - 使用`-a`之后，在已跟踪的文件中搜索所有已更改的文件，自动进行暂存并提交
- commit指令执行后，数据在git区域中的流动
    - 表面：暂存区-->版本区
    - 底层：
        ```
        暂存区-->创建树对象-->版本区-->取出树对象-->创建提交对象-->版本区
        ```
- 指令的工作流程
    1. 为当前暂存区创建一个快照，即树对象
        - 即执行：`git write-tree`
        - 执行后将树对象保存到了版本区中
    2. 为新的树对象创建一个提交对象
        - 即执行：`git commit-tree 树对象的SHA-1值 [-p 父提交对象的SHA-1值] -m 提交信息`
        - 执行后从版本区获取树对象，然后创建提交对象，再保存到版本区中

- 一次git提交只会有一个树对象、一个提交对象、多个git对象。如果是删除文件，则目标文件的git对象会从暂存区删除

## diff指令
[top](#catalog)
- 用途：比较文件的修改内容
- 指令：`git diff [文件路径]`
- `diff`指令的两种比较方式
    - 工作区版本与暂存区版本进行差异比较：`git diff [文件路径]`
    - 暂存区与版本区进行差异比较
        - `git diff --cached [文件路径]`
        - `git diff --staged [文件路径]`（需要git 1.6.1以上）

## mv指令
[top](#catalog)
- 用途：为工作区路径重命名
- 指令：`git mv 旧路径(from) 新路径(to)`
- 运行`git mv`指令相当于执行下面3条指令
    1. `mv from to`
    2. `git rm from`
    3. `git add to`
- mv指令的执行流程
    1. 修改工作目录的文件名
    2. 将修改添加到暂存区
- 如果重命名前，文件有其他修改但是未暂存，在重命名后需要重新执行add指令
    - 示例：将：file01.txt 重命名为 newfile.txt，但是工作区的修改内容未暂存
        ```
        [root@ea284d43453a testpag02]# git status
        On branch master
        Changes to be committed:
        (use "git restore --staged <file>..." to unstage)
                renamed:    file01.txt -> newfile.txt

        Changes not staged for commit:
        (use "git add <file>..." to update what will be committed)
        (use "git restore <file>..." to discard changes in working directory)
                modified:   newfile.txt

        [root@ea284d43453a testpag02]# git diff --cacheinfo
        ```

## log指令
[top](#catalog)
- 用途：显示提交历史记录
- 指令：`git log`，指令默认输出git对象中的相关内容
- 通过下面两种参数来使每次修改的内容输出到一行
    - `git log --pretty=oneline`
    - `git log --oneline`，输出时提交对象的 SHA-1 值之后显示前几位
- 查看详细的分支历史
    - `git log --oneline --decorate --graph --all`

# git高层命令-分支操作
## 分支操作的概述
[top](#catalog)
- git的分支模型极其高效和轻量，切换速度快
- 分支的本质
    - git分支的本质：**指向提交对象的可移动指针**
    - 创建分支的本质：创建一个可移动的指针，并指向某一个提交对象
    - 切换分支的本质：让 `HEAD` 切换为不同的指针，来指向不同的提交对象
        - 类似与指向指针的指针
    - 分支提交的本质：每次有新的提交时，`HEAD`与分支一起向前移动
- 涉及分支操作的两个文件/目录
    - `refs`，存储指向数据的提交对象指针（分支）
        - 每个分支一个文件文件，文件中保存的是版本区中git对象的 SHA-1 值
    - `HEAD`，表示当前正在使用的分支
        - 该文件中保存的是`refs`目录下的一个地址
            - 如:当前指向`master`分支，文件中的内容是
                ```
                [root@ea284d43453a testpag02]# cat ./.git/HEAD
                ref: refs/heads/master
                [root@ea284d43453a testpag02]# 
                ```
        - 如`git log --oneline`指令结果中会出现`HEAD`指向的内容
            ```
            [root@ea284d43453a testpag02]# git log --oneline
            f73fc14 (HEAD -> master) update  <--- 表示当前指向 master 分支
            6c107f4 rename
            bec001a file01.txt second update
            [root@ea284d43453a testpag02]# 
            ```

- 显示分支列表：`git branch`
- 创建分支：
    - `git branch 分支名`
        - 创建一个分支，并指向当前版本的提交对象
        - 该指令只负责创建分支，但是不会切换到新分支
    - `git branch 分支名 提交对象的SHA-1值`
        - 创建一个分支，并指向指定的提交对象，即切换到指定的版本
- 切换分支：`git checkout 分支名`
- 删除分支：
    - `git branch -d 分支名`
        - 删除分支时，需要切换到其他分支才能进行删除
        - 删除分支后，分支下的提交信息也会删除，但是所有的对象不会删除
    - `git branch -D 分支名`，强制删除分支
        - 如果分支没有合并到master，需要使用该指令来强制删除分支
- 查看所有分支的最后一次提交：`git branch -v`
- 查看详细的分支历史：`git log --oneline --decorate --graph --all`

# 总结
[top](#catalog)
- 要点
    - `git write-tree`、`git commit-tree` 都不会清空暂存区
    - `git add`的实际区域流动：工作区-->版本去--暂存区
    - `git commit`的实际区域流动：
        ```
        暂存区-->创建树对象-->版本区-->取出树对象-->创建提交对象-->版本区
        ```
    - 分支的本质
        - git分支的本质：**指向提交对象的可移动指针**
        - 创建分支的本质：创建一个可移动的指针，并指向某一个提交对象
        - 切换分支的本质：让 `HEAD` 切换为不同的指针，来指向不同的提交对象
            - 类似与指向指针的指针
        - 分支提交的本质：每次有新的提交时，`HEAD`与分支一起向前移动

- 配置
    - 3种配置
        |级别|文件路径|适用范围|指令选项|
        |-|-|-|-|
        |1|`/etc/gitconfig`|对当前系统中所有用户都适用的配置|`git config --system`|
        |2|`~/.gitconfig`|只适用于当前用户的|`git config --global`|
        |3|`.git/config`|当前项目的git目录中的配置文件，只针对当前项目有效|`git config`|

    - `git config [--system/global] --list`，查看所有配置内容
    - `git config [--system/global] -l`，查看所有配置内容
    - `git config [--system/global] 配置名 "配置值"`，添加/更改配置
    - `git config --global --unset "配置名"`，删除配置
    - `git config [--system/global] alias.指令别名 "指令内容，去掉开头的git"`，为复杂的指令添加别名
- 底层命令
    - git对象/键值对的读写指令
        - `echo 'test01' | git hash-object -w --stdin`
        - `git cat-file [option] 键值`
    - 暂存区：
        - `git ls-files -s`，查看暂存区
        - 更新暂存区
            - `git update-index --add 文件名`，第一次添加，自动创建git对象并添加到暂存区
            - `git update-index --add --cacheinfo 文件模式 键值 文件名`，直接将文件对应的git对象添加到暂存区
            - `git update-index --cacheinfo 文件模式 键值 文件名`，更新暂存区的某个文件
            - `git update-index 文件名`，自动创建文件当前版本的git对象，并更新到暂存区
    - 树对象
        - `git write-tree`
        - `git red-treee [选项] 另一个树对象hash值`，将一个树对象加入当前最新的树对象
            - 执行后需要执行：`git write-tree`来创建新的树对象
    - 提交对象
        - `git commit-tree 树对象的SHA-1值 [-p 父提交对象的SHA-1值] [-m 提交信息]`

- 版本crud命令
    - `git status`，检查状态
    - `git init`，初始化git项目
    - `git add 文件路径`
    - `git commit -m 提交信息`
    - `git commit -a -m 提交信息`，跳过暂存区提交
    - `git diff [路径]`，工作区与暂存区差异比较
    - `git diff --staged [路径]`，暂存区与版本区差异比较
    - `git mv 旧文件名 新文件名`，暂存区文件重命名
    - `git mv 文件路径`，删除文件
    - `git log`，输出提交历史
        - `git log --pretty=oneline`
        - `git log --oneline`，输出时提交对象的 SHA-1 值之后显示前几位
        - `git log --oneline --decorate --graph --all`，查看详细的分支历史

- 分支命令
    - `git branch`，显示分支列表
    - `git branch 分支名`，创建分支
    - `git branch 分支名 提交对象的SHA-1值`，创建一个分支，并指向指定的版本
    - `git checkout 分支名`，切换分支
    - `git branch -d 分支名`，删除分支
    - `git branch -D 分支名`，强制删除分支
    - `git branch -v`，查看所有分支的最后一次提交
    - `git log --oneline --decorate --graph --all`，查看详细的分支历史
