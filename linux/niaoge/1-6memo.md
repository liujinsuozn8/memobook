* 关机操作
    * 根指令：systemctl
        * 通过这个指令来调用几个关机的指令：shutdowm，halt，poweroff，reboot
    * 关机前
        * who
        * netstat -al
        * ps -aux
        * sync 保存
    * 关机
        * shutdown -h now 'msg'
        * shutdown -h +time 'msg'
        * shutdown -h date 'msg'
        * poweroff
        * halt 系统停止，屏幕保留
    * 重启
        * shutdown -r now/+time/date 'msg'
        * reboot
    * 等待关机操作时停止关机
        * shutdown -c
    * 吓人操作
        * shutdown -k 'msg'
* 权限
    * 权限修改
        * chgrp [-R] 文件或目录
            * 改变所属组
            * 目标组名在/etc/group文件内必须存在
        * chown [-R] 用户[:用户组] 文件或目录
            * 改变拥有者
                * 用户:用户组
                * 可以用:或.来分割用户和用户组
                * 用户在/etc/passwd中必须存在
                * 只修改用户组：chown .用户组 目标  
        * chmod [-R] xyz 文件或目录
            * 改变文件的权限，SUID、SGID、SBIT等特性
            * xyz：权限的数值表示
                * r=4,w=2,x=1
                * 0表示该部分没有权限
            * xyz：权限的符号表示
                * 可以使用 +-=
                * u=user,g=group,o=other, a=all
                * chmod u=xxx, g=xxx, o=xxx 文件或目录
                * ugo可以连写来一起设定，如ug=xxx
                * 统一增加，减少某个权限
                    * chmode a-xxx 文件或目录
                    * chmode a+xxx 文件或目录
    * 权限对于文件的意义
        * r:可读，w:写入/编辑/新增/修改，x:可执行（与文件内容有关，与文件名无关）
    * 权限对于目录的意义
        * r:可以读取目录结构列表，即可以查询该目录下的文件名数据，可以使用ls显示文件名列表
        * w:可以改变目录结构列表
            * 可创建目录、文件
            * 可删除目录、文件（忽略文件的权限，即仅属于其他人的文件或目录也可以删除）
            * 可改名
            * 更改目录、文件的位置
        * x:用户是否可以进入该目录成为工作目录
    * 对于文件夹至少需要r-x的权限才能进入
* Linux文件种类
    *  `-`
        * 纯文本，二进制文件，数据格式文件
    * d：目录
    
    * l：快捷方式
    * 设置与装置文件(device)
        * b：区块设备文件，存储一些文件，以提供系统随机存取的接口设备，即**硬盘或软盘**(/dev/sda)
        * c：字符设备文件，串行端口的接口设备，如鼠标键盘。一次性读取，不能够截断输出
    * s：（sockets）资料接口文件，可以使用这种文件进行数据沟通
    * p：(FIFO,pipe)数据传输文件，用来解决多个程序同时存取一个文件造成的错误
* FHS文件标准
    ||可分享|不可分享|
    |-|-|-|
    |不可变|/usr(各种软件)|/etc(配置文件)|
    |不可变|/opt(第三方软件)|/boot(开机与核心文件)
    |可变|/var/mail(邮箱)|/var/run(程序相关)|
    |可变|/var/spool/new(新闻)|/var/lock(程序相关)|
* 三层目录树架构
    * /：与开机有关
        * 与开机/还原/系统修复有关
        * 不要放在非常大的分区槽内，分区越大越容易发生问题
        * 根目录越小越好，不与应用程序放在一个分区内
    * /usr：与软件安装/执行有关
    * /var：与系统运行有关
* 文件、目录管理
    * 打印环境变量：echo $PATH
    * 通过命令行添加环境变量：PATH="${PATH}:..."
    * mkdir 创建目录
        * -p 递归创建目录
        * -m 创建目录时指定权限 ： mkdir -m 777 xxx
    * rmdir 删除目录
        * -p 递归删除空目录
        * 只能删除空目录
    * cp 文件拷贝（可以拷贝多个）
        * cp [-adfilprsu] 来源 新文件
        * cp [option] source1 source2... 目录
        * f 强制执行
        * s 创建文件的快捷方式
        * l 建立硬式连接，而不是复制文件
        * r 递归拷贝，常用于目录的拷贝
        * d 拷贝链接
        * a = -dr --preserve=all
        * p 同时拷贝文件的权限
        * u 更新，或不存在时复制
    * rm 删除文件/目录
        * rm [-fir] 文件/目录
        * f 强制
        * i 提示信息
        * r 递归删除
    * mv 移动文件或更新
        * mv [-fiu] source destination
        * mv [option] source1 source2... 目录
        * f 强制
        * i 提示
        * u 目标较新时移动
    * dirname 从路径中取目录
    * basename 从路径中取文件名
* 文件的三种时间
    * (mtime)modification time=文件内容改变的时间
    * (ctime)status time=权限属性更新的时间，状态时间（这个时间无法被复制
    ）
    * (atime)access time=文件被取用时的时间
    * 通过ls来查看时间
        * ls -l 默认时mtime
        * ls -l --time=atime
        * ls -l --time=ctime
* 查看文件内容
    * 一次性全部显示
        * cat(正)
            * n 加行号
            * b 非空白行加行号
            * E 显示末尾的换行符
            * v 列出一些特殊字符
            * T tab键通过^I的方式显示
            * A=vET 
        * tac(倒)
        * nl 添加行号打印 （默认忽略空白行）
            * -b a 全部加行号 = cat -n
            * -b t 非空白行加行号 = cat -b
            * n ln 列行号在屏幕最左边
            * n rn 列行号在编号字段中右对齐
            * n rz 有对齐，且在前面补0
            * w 数字，行号字段所占的数字
    * 翻页显示
        * more
            * 空格=下一页
            * 回车=下一行
            * /字符串=向下搜索 （不好用）
            * b=往前翻页
            * :f=显示当前文件的文件名和当前行号
        * less
            * 空格，翻页键翻页
            * /字符串 向下搜索
            * ?字符串 向上搜索
            * g 回到第一行， G回到最后一行
    * 部分显示
        * head [-n 行数]=前面n行
            * 默认10行
            * 行数是负数时，不显示最后面的那些行
        * tail [-n 数字]=后面n行
            * +XXX=XXX行～末尾的所有行
            * -f 持续侦测，对于会有数据写入的文件，有新数据时都会显示
        * 查看 a～b行
            * head -n b file | tail +a
    * 非纯文本文档
        * od [-t TYPE]
            * type:
                * a=默认字符
                * c=ASC字符
                * d[size] 10进制输出，每个整数占用size byte
                * f[size] 浮点数输出
                * o[size] 8进制输出
                * x[size] 16进制输出
            * od -t oCc file = 文件8进制输出，然后转换成ASC码
    * 修改文件时间，创建新文件
        * touch [] file
            * a=只修改access time
            * c=只修改文件的时间，如果不存在则不创建
            * d=修改的目标日期，可以用 --date="日期或时间"
            * m=只修改mtime
            * -t YYYYMMDDhhmm=修改的目标日期

* 文件/目录的默认权限与隐藏权限
    * 文件的预设权限 umask
        * umaks -S 查看详细的权限设定
        * 文件和目录的预设权限时不同的，
            * 文件不可执行 rw-rw-rw-
            * 目录rwxrwxrwx
        * 预设结果为4位数字，第一位为特殊权限，后三位为用户/组/其他权限，每个权限的值=在基本权限上那些被拿掉的权限
        * umask xxx (这里是三位数字) 来直接修改预设权限
    * 隐藏权限
        * chattr [+-=] []
            * +-= 增加，删除，重设
            * a=只能增加，不能删改，root使用
            * i=无法操作，无法做链接，root使用
            * S=同步修改文件
            * s=完全删除
            * u=删除时，内容还存在磁盘中，可以复原
            * c=自动压缩、解压缩
        * lsattr [] 文件/目录
            * a=隐藏文件的属性也显示
            * d=如果接目录，则只显示目录本身的属性
            * R=子目录数据也一起显示
* 文件的特殊权限（s/t）
    * 三种特殊权限的前提：拥有对应位置上的x权限
    * s替代所属**用户**的x=Set UID = SUID = 在执行时产生权限变化
        * 仅对**二进制文件**有效，其他类型文件和目录无效
        * 执行者需要有x权限
        * 本权限仅在执行该程序的过程中有效
        * **执行者将具有该程序拥有者的权限**
    * s替代所属**组**的x=Set GID = SGID = 在执行时产生权限变化
        * 对二进制程序、目录有效
        * 执行者需要有x权限
        * 执行者在执行过程中会获得程序所属组的支持
        * 设置在目录上的意义
            * 若用户具有rx权限，该用户能进入此目录
            * 在该目录下，用户的有效组会变成该目录的所属组
            * 若用户在此目录下有w权限，则新建文件的组，与该目录的所属组相同
    * Sticky Bit = SBIT
        * t = 用于**其他位置**的权限
        * 只对目录有效
        * 对于目录内的文件，只有创建者有删除的权限，
            * 如/tmp，它的权限是drwxrwxrwt，对于任何用户，都可以在该目录下创建/修改文件，但只有拥有者自己才能删除
    * 权限设定
        * 数字法
            * chmod Yxxx，在所有权限前面增加Y
            * Y的类别
                * 4 SUID
                * 2 SGID
                * 1 SBIT
            * 当没有对应位置的x权限时，值会变成大写：S/TS/T
        * 符号法：
            * SUID=u+s/rwxs
            * SGID=g+s/rwxs
            * SBIT=o+t/rwxt
* 查看文件类型
    * file 文件
* 指令和文件的搜索
    * 脚本文件名的搜索(指令的搜索)
        * which [-a] command 
            * **只在PATH中记录的目录下进行搜索**
            * command=完整的执行文件名
            * 默认只显示找到的第一个
            * a=显示所有可以找到的指令
    * 文档名的搜索
        * whereis [-bmsu l] 文件或目录
            * 在一些特定目录下进行搜索（包括/bin、/sbin、/usr/share/man）
            * l=列出将会搜索的目录
            * b=只搜索二进制文件
            * m=只在说明文件目录manual下搜素
            * s=只找source来源文件
            * u=搜索bms以外的其他特殊文件
        * locate [ir clS] keyword + updatedb
            * 在数据库 /var/lib/mlocate/ 里面搜索数据
                * 改文件每隔默认的时间段进行更新（每个distribution都不同），如果在更新前进行搜索有可能找不到
                * 可以通过updatedb来手动更新。它会读取/etc/updatedb.conf，然后搜索硬盘，执行后需要等待一段时间
            * i=忽略大小写的差异
            * r=后接正则表达式
            * c=只输出数量
            * l 行数n=只输出n行结果
            * S=输出locate所使用的数据库文件的相关信息，包括该数据库记录的文件/目录数量等
        * find [PATH] [option] [action]
            1. 与时间有关的选项
                * -mtime n=n天之前的【24小时之内】，更改过的文件（-n-1, -n）
                    * 查询过去一天/24之内发生变化的文件=find / -time 0
                * -mtime +n=n天之前【不包含第n天】，更改过的文件 (-n-1, now)
                * -mtime -n=n天之内【包含n天】，更改过的文件 (-n, now)
                * -newer 列出比目标文件日期更加新的文件
                    * find XXXX -newer YYYYY
            2. 与使用者有关的选项
                * -uid n=n是用户的账号ID，账户ID记录在/etc/passwd，账户名和数字相对应
                * -gid n=n是组ID，组ID记录在/etc/group中
                * -user name=name是使用者的账户名称
                * -group name=name是组名
                * -nouser =寻找不在/etc/passwd中的用户（某个用户被删除时会发生）
                * -nogroup = 寻找不在/etc/group中的组(尤其是自行安装软件时，可能会没有组名；某个组被删除时会发生)
            3. 与文件权限及文件名有关的选项
                * -size [+-]SIZE=搜索比指定SIZE还要大或小的文件
                    * SIZE的单位
                        * c=B，k=KB，
                * -name filename =搜索文件名为filename的文件
                * -type TYPE = 搜索指定类型的文件
                    * f=正规文件，b/c=装置文件，d=目录，l=链接，s=socket，p=FIFO等
                * -perm 可以找出文件的特殊权限
                    * -perm mode=权限相等的文件（权限=mode）
                    * -perm -mode=权限比mode大的文件（权限>=mode）
                    * -perm /mode= 权限包含在mode中的文件(权限<=mode)
            4. 多目录搜索：find path1 path2 -XXXX
            5. 在搜索后执行额外的动作
                * find PATH -exec command {} \;
                    * -exec command {} \;
                    * command 中不可以使用命令别名
                    * {} 中将导入find的搜索结果
                    * -exec 。。。\; 这之间的语句被识别位额外操作
                    * [;]在bash环境中有特殊含义，所以适应\;
                    * 例如：find /usr/bin -perm 7000 -exec ls -l {} \;
            6. find中可以使用通配符来搜索文件名
                * find /etc -name '*xxx*'
            7. 逻辑运算
                * -a 并且
                * ! 取反
                * -o 或者
                
* 文件操作指令与其所需的最小权限
    * 用户能进入某目录
        * cd，权限=x
        * ls，权限=rx
    * 用户读取目录内的某个文件
        * cat等，more，less
        * 目录，权限=x
        * 文件，权限=r
    * 用户修改某个文件
        * 目录，权限=x
        * 文件，权限=rw （先读取，再修改）
    * 让用户在目录下可以创建文件
        * 目录，权限=wx （能进入，能做修改）
    * 让用户进入目录并执行目录下的某个指令
        * 目录，权限=x
        * 文件，权限=x
* 几个特殊目录
    * `.` 当前目录
    * `..` 上一层目录
    * `-` 前一个工作目录 ？？？？
    * `~` 当前用户的家目录
    * `~account` 指定用户的家目录
