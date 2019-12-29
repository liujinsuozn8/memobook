<span id="catalog"></span>

### 目录
- [压缩工具](#压缩工具)
- [tar](#tar)
- [cpio](#cpio)
- [zip](#zip)
- [xz](#xz)
- [bzip2](#bzip2)
- [gzip](#gzip)
- [compress](#compress)
---

# 压缩工具
[top](#catalog)
- 图形工具：file-roller
- compress/uncompress:.Z
- gzip/gunzip:.gz
- bzip2/bunzip2:.bz2
- xz/unxz:.xz
- zip/unzip
- tar
- cpio

# tar
[top](#catalog)
- **用于打包**，生成`*.tar`、`*.tar.gz/tgz`、`*.tar.xz`，`*.tar.bz2`文件
- 语法
    - `tar [option] XXX.tar.gz 打包的内容(可以包含多个)`
- option
    - -c 产生.tar打包文件
    - -v 执行时显示详细信息
    - -f 指定压缩后的文件名
    - -x 解压缩.tar文件
    - -p 保持原有属性
    - -T 指定引导打包的文本文件，文件中包含需要打包的目录列表
    - -X 排除不参与打包的文件列表，文件中包含不需要打包的目录列表
    - 压缩类型，不使用时只能生成`*.tar` 
        - -z，用于`zip`，生成`*.tar.gz`
        - -j，用于`bizp2`，生成`*.tar.bz2`
        - -J，用于`xz`，生成`*.tar.xz`

- 打包常用组合
    - -zcvf 压缩文件为指定文件名， 同时输出压缩信息
        - 可以压缩多个文件，也可以直接压缩目录
    - -xvf 解压缩文件，可以不用指定压缩类型，都可以解压
        - `-xvf *.tar.gz` 直接解压到当前目录
        - `-xvf *.tar.gz -C 指定目录` 直接解压到指定目录，如果指定目录不存在会报错

- 分割tar包
    - 拆分语法：`split -b size [-d] 包名 前缀名`
        - `-d`执行后会生成:`前缀名00`，`前缀名01`....`前缀名nn`个文件
        - 不加`-d`，执行后会生成:`前缀名aa`，`前缀名ab`....`前缀名zz`个文件
    - 合并语法：`cat 前缀* >包名`
- 应用
    - 打包：`tar -cvf xxx.tar xxxx`
    - 打`tar.gz`包：`tar -zcvf xxx.tar.gz xxxx`
    - 打`tar.xz`包：`tar -Jcvf xxx.tar.xz xxxx`
    - 打`tar.bz2`包：`tar -jcvf xxx.tar.bz2 xxxx`
    - 排除目录：`tar -zcvf xxx.tar.gz -T fileList -X fileList`
    - 解压：`tar -zxvf xxx.tar.gz`
    - 解压到指定目录：`tar -zxvf xxx.tar.gz -C xxxx`

# cpio
[top](#catalog)
- 复制文件
- cpio通过重定向的方式将文件进行打包备份，还原恢复的工具，它可以解压以`.cpio`、`.tar`结尾的文件
- 语法
    - cpio [option] > 文件名/设备名，打包
    - cpio [option] < 文件名/设备名，解包
- option
    |参数|含义|示例|
    |-|-|-|
    |`-o`|将文件拷贝打包成文件或者将文件输出到设备上||
    |`-i`|解包，将打包文件解压或将设备上的备份还原到系统||
    |`-t`|预览，查看文件内容或输出到设备上的文件内容||
    |`-v`|显示打包过程中的文件名称||
    |`-d`|解包生成目录，在cpio还原时，自动的建立目录||
    |`-c`|一种交心的存储方式||
    |`-p`|运行copy-pass模式，从标准输入中读取要复制的文件列表||
- 应用
    - 打包:`find 目录 |cpio -ov > xxx.cpio`
        - 打包时，使用绝对路径，则解压时也自动使用绝对路径
        - 打包时，使用相对路径，则解压时也自动使用相对路径
    - 预览内容：`cpio -tv < xxx.cpio`
    - 解包：`cpio -idv < xxx.cpio`
    - 从列表文件中复制文件：`cpio -p 目录/ < 列表文件`

# zip
[top](#catalog)
- 打包压缩：`zip`
    - `zip -r 压缩结果 源路径`，压缩目录
    - `zip 压缩结果 源路径`，压缩文件
    - `cat xxx | zip yyy -`，压缩xxx为yyy.zip 
- 解压缩
    - `unzip xxx.zip`
        - `-d 指定解压后的存放目录`， 将文件解压到指定目录

# xz
[top](#catalog)
- 文件后缀：`.xz`
- 语法

    |语法|含义|
    |-|-|
    |`xz [option]... file...`|压缩单个文件|
    |`unxz`|解压缩|
    |`xzcat`|不解压直接查看文件内容|

- option

    |参数|含义|示例|
    |-|-|-|
    |`-k`|保留源文件，可以用于压缩/解压缩|`xz -k test.txt`|
    |`-d`|解压缩||
    |`-#`|`#=1-9`，压缩比，默认为6||

- 应用
    - 压缩：`xz xxx`
    - 压缩并保留文件：`xz -k xxx`，``
    - 解压缩：
        - `xz -d xxx.xz`
        - `unxz xxx.xz`
    - 解压缩并保留文件：
        - `xz -dk xxx.xz`
        - `unxz -k xxxx.xz`
    - 设定压缩比：`xz -k -9 xxx`
    - 直接查看压缩文件内容：`xzcat xxx.xz`

# bzip2
[top](#catalog)
- 文件后缀：`.bz2`
- 压缩比比`gzip`大 
- 语法

    |语法|含义|
    |-|-|
    |`bzip2 [option]... file...`|压缩单个文件|
    |`bunzip2`|解压文件|
    |`bzcat`|不显示解压缩的前提下查看文本文件的内容|

- option
    |参数|含义|示例|
    |-|-|-|
    |`-k`|保留源文件，可以用于压缩/解压缩||
    |`-d`|解压缩||
    |`-#`|`#=1-9`，压缩比，默认为9||

- 应用
    - 压缩：`bzip2 xxx`
    - 压缩并保留文件：`bzip2 -k xxx`
    - 解压缩：
        - `bzip2 -d xxx.bz2`
        - `bunzip2 xxx.bz2`
    - 解压缩并保留文件：
        - `bzip2 -dk xxx.bz2`
        - `bunzip2 -k xxx.bz2`
    - 设定压缩比：`bzip2 -k -9 xxx`
    - 直接查看压缩文件内容：`bzip2 xxx.bz2`

# gzip
[top](#catalog)
- 文件后缀：`.gz`
- 语法

    |语法|含义|
    |-|-|
    |`gizp [option]... file...`|压缩单个文件，**压缩后不会保留源文件**|
    |`gunzip file`|解压不保留源文件|
    |`zcat xxx.gz > xxx`|解压文件|

- option
    
    |参数|含义|示例|
    |-|-|-|
    |`-d`|解压缩，相当于`gunzip`|`gzip -d xxx.gz`|
    |`-c`|将压缩或解压缩的结果输出至标准输出，不会保留源文件|`gzip -dc xxx.gz > yyy`，解压并保留源文件<br/>`gzip -c xxx > xxx.gz`，压缩并保留源文件|
    |`-#`|`#=1-9`，指定压缩比，值越大，压缩比越大<br/>默认使用的压缩比是6|`gzip -4 xxx`|

- 应用
    - 压缩：`gzip xxx`
    - 压缩并保留源文件：`gzip -c xxx > xxx.gz`
    - 解压缩：
        - `gzip -d xxx.gz`
        - `gunzip file`
    - 解压缩并保留源文件：
        - `gzip -dc xxx.gz > xxx`
        - `gunzip -c test.txt.gz > text.txt`
        - `zcat xxx.gz > xxxx`
    - 设定压缩级别：`gzip -9 xxx`
    - 直接查看压缩文件内容：`zcat xxx.gz`

# compress
[top](#catalog)
- 语法

    |语法|含义|
    |-|-|
    |`compress [-dfvcVr] [-b maxbits] [file ...]`|压缩单个文件|
    |`uncompress file`|解压缩|
    |`zcat file.Z > file`|解压缩，默认会将结果显示到屏幕上，所以使用重定向来保存|

- option
    |参数|含义|示例
    |-|-|-|
    |`-d`|解压缩，相当于`uncompress`|`compress -d file.Z`|
    |`-c`|将结果输出只标准输出，**不删除源文件**|`compress -c file > file.Z`|
    |`-v`|显示详情||

- 压缩比太差，很少用

[top](#catalog)