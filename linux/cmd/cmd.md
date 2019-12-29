- 内容来源于：
    - https://www.bilibili.com/video/av37383358
    - https://www.bilibili.com/video/av33608430
- 测试内容：`cp /etc/passwd /tmp/passwd`

<span id="catalog"></span>

### 目录
- [find搜索文件](#find搜索文件)
- [grep](#grep)
- [sed](#sed)
- [xargs参数替换](#xargs参数替换)
- [](#)

# find搜索文件
[top](#catalog)
- 语法
    - `find [option]... [查找路径][查找条件][处理动作]`
        - **默认会使用递归搜索**
- 查找路径：
    - 可以指定具体目标路径
    - 默认为当前目录
- 查找条件
    - 默认为找出指定路径下的所有文件
    - 文件时间分类
        - 建立/权限/属性改变的时间=ctime
        - 最近一次读取时间=atime
        - 最近修改时间=mtime
    - 基本查找条件

        |指定类别|参数|含义|示例|
        |-|-|-|-|
        |搜索层级|`-maxdepth level`|最大搜索目录深度，目录为第1级|`find /etc/ -maxdepth 1 -name passwd`<br/>`find /etc/ -maxdepth 2 -name passwd`|
        |搜索层级|`-mindepth level`|最小搜索目录深度||
        |搜索层级|`-maxdepth x -mindepth x`|指定搜索深度为x|`find /etc/ -maxdepth 2 -mindepth 2 -name passwd`，**只查找第2层的目录**|
        |文件名|`-name "文件名称"`|默认为文件名的精确匹配<br/>可以使用通配符：`*`, `?`, `[]`, `[^]`<br/>使用通配符时，最好将字符串用`""`扩起来||
        |文件名|`-iname "文件名称"`|查找时不区分文件名字母大小写||
        |inode|`-inum n`|按inode号查找||
        |inode|`-samefile name`|查找相同inode号的文件，即找硬链接||
        |正则表达式|`-regex "pattern"`|通过`pattern`来匹配整个文件路径字符串||
        |连接数|`-links n`|查找连接数为n的文件||
        |属主|`-user username`|查找属主为指定用户(UID)的文件|`find -user root -name "*.sh" -ls`，查找当前目录下root用户的`.sh`文件|
        |属主|`-uid userID`|查找属主为指定UID号的文件||
        |属主|`-nouser`|查找没有属主的文件||
        |属组|`-group grpname`|查找属组为指定组(GID)的文件||
        |属组|`-gid groupID`|查找属组为指定GID号的文件||
        |属组|`-nogroup`|查找没有属组的文件||
        |文件类型|`-type f`|普通文件|`find -type f`|
        |文件类型|`-type d`|目录文件|`find / -maxdepth 1 -type d -ls`|
        |文件类型|`-type l`|符号链接文件||
        |文件类型|`-type s`|套接字文件||
        |文件类型|`-type b`|块设备文件||
        |文件类型|`-type c`|字符设备文件||
        |文件类型|`-type p`|管道文件||
        |**空文件**|`-type f -empty`|空文件|`find / -maxdepth 1 -type f -empty -ls`，查找根目录下的空文件|
        |**空目录**|`-type d -empty`|空目录|`find / -maxdepth 1 -type d -empty -ls`，查找根目录下的空目录|
        |排除路径|`-path "xxxx" -a -prune`|排除指定路径<br/>排除目录时，目录最后不能加路径分隔符`/`|`find /etc/ -path "/etc/systemd" -a -prune -o -name "*.conf"`，排除目录`/etc/systemd`，并查找`conf`文件|
        |排除路径|`\( -path "xxxx" -o -path "yyyy" \) -a -prune`|排除多个路径<br/>**括号前后需要有空格**|`find /etc \( -path "/etc/systemd" -o -path "/etc/security" \) -a -prune -o -name "*.conf"`|
        |文件大小|`-size [+|-]n(ckMG)`|1. `n`的范围:`(n-1单位, n单位]`<br/>2. `+n`的范围:`(n单位, 无穷)`<br/>3. `-n`的范围:`[0, n-1单位]`<br/><br/>`-size 1k =(0k,1k]`和`-size 1024k =(1023k,1024k]`是不同的|`find -size 1k`|
        |时间戳|`-atime|mtine|ctime [+|-]n`|以**天**为单位<br/>1. `n`的范围:`[n,n+1)`<br/>2. `+n`的范围:`[#+1, 无穷)`<br/>3. `-n`的范围:`[0,n)`<br/>||
        |时间戳|`-amin|mmin|cmin [+|-]n`|以**分钟**为单位<br/>1. `n`的范围:`[n,n+1)`<br/>2. `+n`的范围:`[#+1, 无穷)`<br/>3. `-n`的范围:`[0,n)`<br/>|`find -mmin -1`，查找1分钟之内被修改过的文件|
        |用户权限|`-perm [/|-]MODE`|根据权限查找<br/>1. `MODE`:表示权限的**精确匹配**<br/>2. `/MODE`:任何一类对象的权限中只要有一位能匹配即可<br/>3. `-MODE`:每一类对象都必须**同时包含**指定权限<br/>`0`表示不关注||
        
- 组合条件
    - `-a`，与
    - `-o`，或
    - `-not`，非
    - 德摩根定律
        - `!A -a !B` = `!\(A -o B\)`
        - `!A -o !B` = `!\(A -a B\)`
            
- 处理动作

    |指令|含义|示例|
    |-|-|-|
    |`-print`|默认的动作处理，显示到屏幕||
    |`-ls`|以长格式显示，类似于：`ls -l`|`find -type f -ls`|
    |`-delete`|删除查找到的文件，**删除时没有提示**||
    |`-fls file`|将查找到的所有文件的长格式文件选项保存到指定文件中||
    |`-ok command {} \;`|对查找到的每个文件执行指令`command`；每个文件执行命令之前，都会交互式要求用户确认<br/>`{}`代表`find`指令的每一个查询结果<br/>如果使用`-ok`则必须以`\;`结尾，且`\;`前必须有一个空格|`find -name "p*" -ok rm {} \;`|
    |`-exec command {} \;`|对查找到的每个文件执行指令`command`；对每个文件直接执行指令，不会询问<br/>如果使用`-exec`则必须以`\;`结尾，且`\;`前必须有一个空格|`find -name "p*" -exec mv {} \;`，查询并改名|

- 应用
    - 备份配置文件
        - `find -name "*.conf" -exec cp {} {}.bk \;` 
    - 在`/data`目录下查找权限是644的`*.sh`文件，并添加执行权限
        - `find /data -type f -perm 644 -name "*.sh" -exec chmod 755 {} \;`
    - 查看`/home`下的所有目录
        - `find /home -type d -ls`
    - 查找其他用户可写的文件
        - `find -type f -perm -002 -ls`

- 书写方法
    1. 确定查找类型`-type`
    2. 确定查找目标的权限
    2. 确定其他查找细节
    3. 确定细节之间组合的逻辑方式
    4. 确定对查询结果的执行指令

# grep
[top](#catalog)
- grep [options] pattern file
    - pattern的内容
        - 检索目标：`grep root /etc/passwd`
        - 引用变量的值：`grep $USER /etc/passwd`
        - 引用指令执行结果：
            ```
            grep `whoami` /etc/passwd
            grep $(whoami) /etc/passwd
            ```
        - 使用正则表达式：`echo xcvx good sgofsdf rytr|grep -E "go+"`
    - 不在结果中显示颜色的几种方法
        - `\grep root /etc/passwd`
        - `'grep' root /etc/passwd`
        - `"grep" root /etc/passwd`
        - `/bin/grep root /etc/passwd`
- options
    - 常用参数
        - `-v`，显示非目标结果，即取反/过滤
        - `-n`，在结果前显示行号
            - `grep -v "^$" xxxx`，显示非空行
            - `grep -v "^[[:space:]]*$" passwd`
        - `-i`，忽略大小写
        - `-E`，扩展grep，即egrep
        - `-q`，静默执行
        - `-e`，多个匹配，相当于`or`
        - `-w`，匹配整个单词
        - `-F`，使用`fgrep`，不使用正则表达式

    - 其他参数
        - `-o`，只输出匹配到的内容
        - `-A`，After，显示匹配字符串及其后n行的数据
        - `-a`，在二进制文件中，以文本文件的方式搜索数据
        - `-B`，Before，显示匹配字符串及其前n行的数据
        - `-C`，Context，显示匹配字符串及其前后各n行数据
        - `-c`，计算包含结果的**行数** 
        - `--color=auto`，对匹配到的文本着色显示
            - `\grep root --color=auto /etc/passwd`
            - **系统默认有颜色，是使用了`alias`添加了命令别名**
                - `alias grep='grep --color=auto'`

# sed
[top](#catalog)
- sed简介
    - stream editor 行编辑器
    - sed是一种流编辑器，一次处理一行内容
    - 功能：主要用来自动编辑一个或多个文件，简化对文件的反复操作，编写转换程序
    - 处理过程
        1. 把当前处理的行存储在**临时缓冲区**中，称为`模式空间(pattren space)`
        2. 用sed命令处理缓冲区中的内容
        3. 处理完成后，将缓冲区的内容送往屏幕
        4. 处理下一行，知道文件末尾
            - 如果没有使用如：`-D`等特殊命令，会先清空`模式空间`，但不会清空`保留空间`，再执行下一行
- 语法
    - `sed [option]... 'script' inputfile...`
        - 如果没有输入`-i`选项，则所有的操作都是预览，不会修改文件
    - 常用选项
        - `-n`，不输出模式空间到屏幕，即不自动打印
            - `sed -n '1p' passwd`，只打印第一行
                - 如果不加`-n`，会输出全部内容，并且**第一行输出两次**
                    - 第一行时，会执行`1p`打印一次，然后将缓冲区的内容发送到屏幕，所以第一行会打印两次
                    - 剩余行，无法执行`1p`处理，直接将缓冲区的内容发送到屏幕，所以剩余行也会打印一次
        - `-e`，执行多个指令
            - `seq 1 10|sed -e '3,5s#$#aaaa#g' -e '4,7s#^#bbb#g'`，3-5行的行尾添加`aaaa`，4-7行的行首添加`bbb`
        - `-f /path/script_file`，从指定文件中读取编辑脚本
        - `-r`，支持使用扩展正则表达式
        - `-i`，直接修改文件内容
        - `-i.后缀名`，备份源文件(源文件名.后缀名)，然后修改
            - `sed -i.bak '2d' passwd`，将`passwd`文件备份为`passwd.bak`，然后删除第二行并保存到`passwd`中
    - `script`中的编辑命令

        |命令|含义|示例|
        |-|-|-|
        |`d`|删除模式空间匹配的行，并立即启用下一轮循环|`sed '3d' passwd`，不输出第3行，但是输出其他行|
        |`p`|打印当前模式空间内容，追加到默认输出之后|`sed -n '1p' passwd`，打印第一行|
        |`a[\]text`|在指定行后面**追加**文本，支持使用`\n`实现多行追加|`seq 1 3|sed '2a\hello'`，在第二行后面添加一个`hello`|
        |`i[\]text`|在行前面**插入**文本|`seq 1 3|sed '2i\hello'`，在第二行前面添加一个`hello`|
        |`c[\]text`|**替换**行为单行或多行文本|`seq 1 3|sed '2c\hello'`，将第二行替换为`hello`|
        |`w /path/file`|保存模式匹配的行至指定文件|`sed '/root/w w1.txt' passwd`，将包含`root`的行保存到`w1.txt`文件中|
        |`r /path/file`|读取指定文件的文本只模式空间中**匹配到的行后**|`sed '/root/r w1.txt' passwd`|
        |`=`|为模式空间中的行打印行号|`sed -n '3,5{=;p}' passwd`，打印3-5行及其行号，行号和内容之间会有换行|
        |`!`|模式空间中匹配行取反处理|`seq 1 10|sed '2!d'`，对2取反，删除2以外的行|
        |`s/str1/str2/[gi]`<br/>`s@str1@str2@[gi]`<br/>`s#str1#str2#[gi]`|`str2`替换`str1`<br/>如果不加`g`则只替换第一个，加`g`则替换所有<br/>`i`表示忽略大小写|1. `sed 's/root/raat/' passwd`<br/>2. `ed 's/root/raat/g' passwd`<br/>3. `sed 's/root/raat/gi' passwd`<br>4. `sed -r 's/\<root\>/rooter/gi' passwd`，将所有root替换成rooter，已经替换成rooter的忽略|

    - 指定处理位置
        - `n-m`，从第n行开始，每次处理下面的第m行
                - 打印奇数行：`seq 1 10|sed -n '1~2p'`
                - 打印偶数行：`seq 1 10|sed -n '2~2p'`
        - `n,m`，只处理n到m行的内容
    - 关键字搜索
        - `sed -n '/关键字/p' file`
            - `sed -n '/root/p' passwd`

- 应用
    - 修改日期格式 
        - `echo "09/Oct/2024:04:037:35"|sed -r 's@([0-9]{2})/([A-Z][a-z]{2})/([0-9]{4}):(.*)@\1 \2 \3 \4@g'`，替换日期中的`/`和`:`
    - 在行尾添加字符串
        - `seq 1 10|sed '5,8s#$#abcd#g'`，使用`$`代表行尾，只修改5至8行
    - 在行首添加字符串
        - `seq 1 10|sed '5,8s#^#abcd#g'`，使用`^`代表行尾，只修改5至8行
    - 从n行处理到最后一行
        - `seq 1 10|sed '5,$s#^#abcd#g'`，使用`$`代表最后一行 
    - 在同一行输出行号和内容
        - `sed -n '{=;p}' passwd | xargs -n2`
    - 取多个不连续的行
        - `sed -n '1p;3p;5p' passwd`
        - `sed -n -e '1p' -e '3p' -e '5p' passwd`
    - **构造命令并执行**
        - 通过sed提取关键字，反向应用并构造指令，再交给bash统一执行
        - `seq 1 10|sed -r 's#(.*)#echo \1#'|bash`
    -  提取IP
        - `ifconfig|sed -nr 's#.*inet (.*)  netmask.*#\1#p'`
    - 交换passwd文件中第一项和最后一项的位置
        - `sed -nr 's#([^:]+)(:.*:)(.*)$#\3 \2 \1#p' passwd`

# xargs参数替换
[top](#catalog)
- 必须使用xargs的场景
    - 由于很多命令不支持管道`|`来传递参数，所以使用xargs命令
    - 有些命令**不能接受过多的参数**，命令执行可能会失败，如：`touch f{1..1000000}`，xargs可以解决
- xargs用于产生某个命令的参数，xargs可以读入stdin的数据，并且以**空格符**或**回车符**将stdin的数据分隔成为arguments

- 语法
    - cmd|xargs newcmd
- 应用
    - 分解过多的参数
        - `echo f{1..10} | xargs -n1 echo`

[top](#catalog)


- sort
- uniq
- seq 1 10
- chkconfig
- dd if=/dev/zero of=/data/f1 bs=1 count=1023
- stat 文件 列出文件状态
- df -i