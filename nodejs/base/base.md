<span id="catalog"></span>

### 目录
- [Nodejs安装](#Nodejs安装)
- [Nodejs简介](#Nodejs简介)
- [CommonJS规范](#CommonJS规范)
- [包package](#包package)
    - [包的基本知识](#包的基本知识)
    - [包管理工具npm及其指令](#包管理工具npm及其指令)
    - [包管理工具cnpm](#包管理工具cnpm)
    - [npm换源](#npm换源)
    - [包的搜索与导入流程](#包的搜索与导入流程)
    - [package.json](#package.json)
    - [依赖中版本号的标识符](#依赖中版本号的标识符)
- [Nodejs的指令](#Nodejs的指令)
- [Node中的模块化](#Node中的模块化)
    - [Node的模块化特性](#Node的模块化特性)
    - [模块的标识与分类](#模块的标识与分类)
    - [Node模块的使用方法](#Node模块的使用方法)
- [Nodejs变量](#Nodejs变量)
- [Buffer缓冲区](#Buffer缓冲区)
    - [Buffer的基本知识](#Buffer的基本知识)
    - [Buffer的使用方法](#Buffer的使用方法)
- [文件系统-fs模块](#文件系统-fs模块)
    - [fs模块](#fs模块)
    - [同步和异步调用](#同步和异步调用)
    - [简单文件写入](#简单文件写入)
    - [流式文件写入](#流式文件写入)
    - [流式文件读取](#流式文件读取)
    - [通过管道进行流式读写](#通过管道进行流式读写)
    - [fs中的其他方法](#fs中的其他方法)
- [supervisor--nodejs的自启动工具](#supervisor--nodejs的自启动工具)
- [服务器应用开发](#服务器应用开发)
    - [HTTP模块](#HTTP模块)
    - [url模块](#url模块)
- [其他](#其他)
    - [事件绑定方法](#事件绑定方法)
- [](#)
- [](#)

# Nodejs安装
- windows安装
    - zip包安装
        - 安装流程
            1. 下载zip包
                - 官网下载页面：https://nodejs.org/en/download/
                - 选择：Windows Binary (.zip)
            2. 在某个目录解压zip包
            3. 添加环境变量，两个目录
                1. nodejs的根目录
                    - `/node-v12.16.3-win-x64`
                2. node-global 目录
                  - `/node-v12.16.3-win-x64/node-global`
        - 不设置环境变量，直接使用 cmd 指令设置临时的环境变量
            ```bat
            echo off
            set path=%path%;<nodejs根目录>;<nodejs-global目录>
            cmd /k echo.
            ```
- mac安装
    - ?????

- Linux安装
    - ?????

- docker
    - ?????

# Nodejs简介
[top](#catalog)
- 什么是Nodejs
    - Node.js是一个能在服务器端运行js的js运行环境
    - Node.js是开源的、跨平台的
    - Node.js大部分模块都用JavaScript编写
    - Node.js使用V8引擎运行js代码，使用时间驱动、非阻塞、异步I/O模型等技术来提高性能，可优化程序的传输量和规模

- Nodejs与原生js
    - Nodejs只是ES标准的一种实现，所以不包含js中的 DOM 和 BOM 对象
    - Nodejs可以使用JS的所有内建对象
    - Nodejs无法使用 DOM 和 BOM 对象，但是可以使用 `console`、`setTimeout()`、`setInterval()`
- Node编写的服务器都是单线程的服务器
    - Node处理请求时是单线程的，但是在后台拥有一个I/O线程

- Nodejs的高并发处理更节省资源
    - Java、PHP、.net等服务器端语言
        - 每个客户都创建一个新的线程，每个线程需要耗费大约2MB内存
        - 一个8GB内存的服务器，同时连接的最大用户数为4千个左右
    - Node.js
        - 只使用一个线程
        - 任何用户连接时，触发一个内部事件，通过**非阻塞I/O**、**事件循环机制**，使得宏观上也呈现为并行处理
        - 一个8GB内存的服务器，同时连接的最大用户数为4万个以上

- Node.js可实现的一些功能
    - 动态网站
    - 接口
    - 跨平台app开发
    - 桌面应用
    - 云直播
    - 云计算平台
    - 区块链开发
    - 即时通讯


# CommonJS规范
[top](#catalog)
- ECMAScript标准的缺陷
    - 没有模块系统
    - 标准库较少
    - 没有标准接口
    - 缺乏管理系统
- CommonJS规范是为了弥补当前JS在管理上的缺陷
- CommonJS对模块的定义
    - 模块引用
    - 模块定义
    - 模块标识

# 包package
## 包的基本知识
[top](#catalog)
- CommonJs的包规范允许将一组相关的模块组合到一起，形成一组完整的工具
- CommonJs包规范的组成
    1. 包结构
        - 用于组织保证的各种文件
    2. 包描述文件
        - 描述包的相关信息，以供外部读取分析

- 包的本质：一个压缩文件，解压后能还原为目录
- CommonJS对包结构的规定

    |文件|描述|
    |-|-|
    |**package.json**|描述文件，该文件中不能写注释|
    |bin|可执行的二进制文件|
    |lib|js代码|
    |doc|文档|
    |test|单元测试|


## 包管理工具npm及其指令
[top](#catalog)
- Node Packge Manager
- CommonJS包规范是理论，npm是其中一种实践
- node通过npm与第三方模块形成了一个很好的生态系统
- npm的用途
    - 第三方模块的发布、安装、依赖
- 安装node时，会自动安装npm
- npm常用管理指令

    |指令|描述|备注|
    |-|-|-|
    |`npm -v`|查看版本||
    |`npm search <包名>`|搜索包||
    |`npm install <包名>`|下载包，并保存在当前工程的node_module目录|简写：`npm i 包名`|
    |`npm install <包名>@<版本号>`|下载指定版本的包||
    |`npm install <包名> 本地文件路径`|从本地安装某个包||
    |`npm install <包名> -g`|全局模式安装包|一般都用来安装一些工具|
    |`npm install <包名> --save`|安装包，依赖添加到：`dependencies`||
    |`npm install <包名> --save-dev`|安装包，依赖添加到：`devDependencies`||
    |`npm install <包名> -D`|安装包，依赖添加到：`devDependencies`||
    |`npm install`|下载当前项目的依赖包||
    |`npm uninstall <包名>`|卸载当前工程下的某个包||
    |`npm remove <包名>`|删除包|简写：`npm r 包名`|
    |`npm init`|初始化nodejs的开发环境||
    |`npm list`|查看当前工程下已安装的包||

- `npm install` 指令的注意事项
    - 执行时为了保证正确安装，需要在执行指令的目录下存在 `package.json` 文件
    - 安装的所有包，都保存在 `node_modules` 目录下
- `npm init` 指令的注意事项
    - `name`，输入项目名时，不能有大写字母
    - `entry point`，包的入口文件，默认为 index.js
        - 指定某个文件作为程序的入口

- npm下载的包保存在哪里?
    - 通过npm下载的包都会保存到node_modules目录下
    - global模式下载的包会保存在环境变量路径的node_modules目录下

- npm包的引用
    - 通过npm下载的直接通过包名引入即可

## 包管理工具cnpm
[top](#catalog)
- npm直接下载比较慢，也有可能无法访问远程仓库
- 使用国内镜像替代原始服务
- 安装 cnpm 指令
    ```sh
    npm install -g cnpm --registry=https://registry.npm.taobao.org
    ```

- cnpm与npm换源
    - 使用 cnpm **全局安装**一些**指令或插件**时，cnpm肯出现无法解决的bug
    - 更推荐通过 [npm换源](#npm换源) 的方式，重设镜像地址，并仍使用 npm

## npm换源
[top](#catalog)
- 相关指令

    |指令|描述|备注|
    |-|-|-|
    |`npm config set registry <地址>`|设置镜像源||
    |`npm config get registry`|设置镜像源|默认为: https://registry.npmjs.org/|
    |`npm install <包名> --registry=地址`|指定镜像源来安装包||

- 设置阿里镜像
    ```sh
    npm config set registry https://registry.npm.taobao.org
    ```

## 包的搜索与导入流程
[top](#catalog)
- 几种导入内容
    - 第三方模块导入，如果导入的不是核心某块，或者 `./` 开头的模块，即第三方模块
        1. 定位包目录
            - 首先在当前 package 的`node_modules`目录下中寻找，如果有则**定位到包目录**
            - 如果没有，则搜索上一级目录的`node_modules`。如果有则**定位到包目录**
            - 如果没有再向上一级目录搜索
            - 一直到搜索到磁盘根目录，如果依然没有，则报错
        2. 定位导入的入口文件
            - 如果包含 `package.json`，则定位到 `main` 属性定义的 js 文件
            - 如果不包含 `package.json`，则默认定位到包目录下的 `index.js` 文件
        3. 读取入口文件，并导入包

    - 如果是通过 `./` 导入
        - 如果是js文件，则直接导入
        - 如果是路径
            1. 定位到指定目录
            2. 默认会定位到目录下 `index.js` 文件
            3. 读取文件并导入模块

- nodejs是如何导入 node_module 目录下的包？
    - nodejs通过各包下的`index.js`文件来导入包
    - 如果没有 `index.js` 文件，会根据 `package.json` 文件中 `main` 属性指定的文件作为入口来导入包

## package.json
[top](#catalog)
- 通过 `npm init` 来生成该文件
- package.json 定义了当前项目需要的包信息，以及项目的配置信息
- 文件说明
    ```json
    {
        "name": "abcd",         // 项目名
        "version": "1.0.0",     // 项目版本
        "description": "",      // 项目描述
        "main": "demo01.js",    // 当前项目的导入入口
        "scripts": {
            "test": "echo \"Error: no test specified\" && exit 1"
        },
        "author": "",           // 作者信息
        "license": "ISC",       // 版权信息
        "dependencies": {
            "模块": "0.0.3"     // 正式依赖
        },
        "devDependencies": {    // 开发依赖

        }
    }
    ```

## 依赖中版本号的标识符
[top](#catalog)
- 每次执行 `npm install <包名>`时，都会安装最新版本，可以通过版本标识符来规定版本的范围
- 标识符说明

    |标识符|说明|
    |-|-|
    |`^a.b.c`|第一位版本号不变，后面两位取最新|
    |`~a.b.c`|前两位版本号不变，最后一位取最新|
    |`*a.b.c`|每次安装都安装最新的版本|
    |`a.b.c`|版本固定，即指定版本安装|

# Nodejs的指令
[top](#catalog)
- nodejs指令

    |指令|功能|示例|
    |-|-|-|
    |`node xxx.js`|指令指定路径下的js文件|`node ./test.js`|
    |`node -e <js代码>`|执行js代码|`node -e 'let a=2; let b=3; console.log(a + b);'`|
    |`node -c xxx.js`|检查代码语法|`node -c ./test.js`<br>`echo . | node -c -`|
    |`node --check xxx.js`|与`node -c`功能相同||
    ||||

# Node中的模块化
## Node的模块化特性
[top](#catalog)
- Node中的模块化特性
    - 一个js文件就是一个模块
    - 每个js文件中的js代码都会独立运行在一个函数中，而不是全局作用域
    - 因为每个js文件运行时的特殊性，一个模块中的变量和函数无法在其他模块中访问

## 模块的标识与分类
[top](#catalog)
- 什么是模块的标识
    - 模块标识就是模块的名字
    - 在js文件中通过模块的标识来引入并使用模块

- 模块标识的类型
    1. 下载的包/系统固有的包
        - 符合驼峰命名法的字符串
    2. 本地的内容
        - 相对路径：**必须以 `.`、`..` 开头**
        - 绝对路径
        - 路径结尾不需要 `.js` 扩展名
- 3类模块
    - 核心模块
        - 由Node引擎提供的模块
        - 核心模块的标识：模块的名字
    - npm下载的模块
        - 核心模块的标识：模块的名字
    - 文件模块
        - 用户自定义的模块
        - 核心模块的标识：文件的路径

## Node模块的使用方法
[top](#catalog)
- 模块的使用方法
    - 引用其他模块
        - `var 模块名 = require("模块的标识")`
        - 执行后使用 `模块别名` 来引用模块下的内容

    - 向外部暴露模块内部数据
        - 将数据设置为 `exports` 变量的属性
            ```js
            export.数据别名 = 变量/函数
            ```

- 模块执行分析
    - 每个js文件运行前都会自动添加外层函数
        ```js
        function (exports, require, module, __filename, __dirname){
            //
        }
        ```
    - 参数含义
        - exports : 用来将模块内部的数据暴露到外部
        - require : 一个函数，用来引用外部的模块
        - module : 代表但前模块本身
            - exports就是module的属性，即 exports === module.export
            - 内部的属性可以通过 exports，或者 module.exports 向外暴露
        - __filename : 当前模块的完整路径
        - __dirname : 当前模块所在目录的路径
    - module.export, export 之间的区别
        - 通过 module.export 可以以对象字面量的方式同时暴露多个变量/函数
            ```js
            module.exports = {a:"...", b:"xxxx", c:xxx}
            ```
        - 无法直接通过个 exports 来设置，因为设置后只是改变了 export对象指向，但是没有修改 module.exports 的对象指向

## Nodejs变量
[top](#catalog)
- 全局对象 `global`
    - node中的全局变量是`global`，并且没有 window对象，
    - 在全局作用域中创建的变量/函数都会作为 `global` 的属性保存

- `__dirname`
    - 代表当前文件所在的目录的路径


# Buffer缓冲区
## Buffer的基本知识
[top](#catalog)
- 什么是Buffer缓冲区
    - 内存中的一段**连续区域**
    - 在结构上，Buffer类似与数组
    - Buffer的元素是16进制的**两位数**
    - buffer的一个元素表示内存中的一个字节（8bit）
    - 实际使用时，Buffer由底层的 c++ 程序来申请

- Buffer的作用
    - 创建内存空间
    - 专门用来存取二进制数据

- js有数组为什么还需要Buffer？
    - js数组的性能比较低
    - js的数组无法保存二进制数据，需要使用Buffer
        - 二进制数据包括：视频、mp3、文件等

- Buffer保存数据的细节
    - Buffer一旦创建，大小就不能再改变
    - 每个元素的取值范围：00---ff
    - 保存的数据超过 ff 时，只会保留数据的低8位数据，其他的舍去

- Buffer数据的形式
    - 在内存中是二进制
    - 打印到控制台或页面时，会自动转换为10进制数
    - 可以通过 `Buffer元素.toString(进制)` 的方式转换为指定的进制

## Buffer的使用方法
[top](#catalog)
- 使用Buffer是node中内置的，不需要引入直接使用即可
- Buffer下的所有**构造函数**都**不推荐使用**

- 类数组操作
    - 可以通过**索引**来为每个元素赋值
    - 可以使用 for 遍历

- Buffer的基本操作

    |操作|说明|
    |-|-|
    |`Buffer.from(字符串 [, 字符编码]))`|按照字符编码，将字符串转换为Buffer|
    |`Buffer.toString( [进制/字符编码] )`|将Buffer转换为字符串|
    |`Buffer.alloc(size)`|，创建指定长度的Buffer，并将内存中的数据清空|
    |`Buffer.allocUnsafe(size)`|创建指定长度的Buffer，但是不清空内存数据。直接使用可能会影响其他数据，但是性能更好|

- 示例
    - 参考代码
        - [src/buffer/base.js](src/buffer/base.js)
    - 代码内容
        ```js
        // 1. 将字符串转换为Buffer
        var str = "hello world"
        var buf = Buffer.from(str)
        console.log(str.length) // 返回字符个数
        //输出： 11
        console.log(buf)
        //输出： <Buffer 68 65 6c 6c 6f 20 77 6f 72 6c 64>
        console.log(buf.length) // 返回占用内存的大小
        //输出： 11
        console.log(buf.toString()) // 将Buffer转换为String
        //输出： hello world

        // 2. 创建指定长度的Buffer

        // 2.1 不推荐使用构造器
        // var buf02 = new Buffer(1024);
        // console.log(buf02.length)

        // 2.2
        var buf02 = Buffer.alloc(10)
        buf02[0] = 0x68
        buf02[1] = 0x65
        buf02[2] = 0x6c
        console.log(buf02[1]) // 直接打印输出二进制
        console.log(buf02[1].toString(16)) // 直接打印输出二进制输出

        // Buffer遍历
        for(var i=0; i<buf02.length; i++){
            console.log(buf02[i])
        }

        // 3. allocUnsafe
        var buf0301 = Buffer.alloc(10)
        var buf0302 = Buffer.allocUnsafe(10)
        console.log(buf0301)
        // 输出：<Buffer 00 00 00 00 00 00 00 00 00 00>
        console.log(buf0302)
        // 输出：<Buffer 88 43 cd 02 00 00 00 00 77 3e>

        // 4. 不同编码的字符转换
        console.log(Buffer.from('中', 'utf-8').toString('base64'))
        console.log(Buffer.from('5Lit', 'base64').toString())
        ```

# 文件系统-fs模块
## fs模块
[top](#catalog)
- Node通过`fs模块`和文件系统进行交互
- fs模块 提供了一些API来进行文件的读写等操作
- fs模块是node的核心模块，不需要下载，直接引入即可
    ```js
    var fs = require("fs")
    ```

## 同步和异步调用
[top](#catalog)
- node中的同步与异步
    - fs模块中的大部分操作都有两种可选形式
        - 同步（函数以Sync结尾）
        - 异步（函数中包含一个回调函数作为参数）
    - 同步、异步的区别
        - 同步文件系统**会阻塞**程序的执行
        - 异步文件系统**不会阻塞**程序的执行，程序操作完后，通过回调函数将结果返回

- 文件写入的步骤
    1. 打开文件
    2. 写入数据
    3. 保存并关闭文件

- 同步文件写入的方法
    - 打开文件：`fs.openSync(path, flags[, mode])`
        - 参数说明
            - path， 文件路径
            - flags， 文件操作类型：r、w等
            - mode， 设置文件的操作权限
        - 返回值
            - 文件描述符，可以通过文件描述符来操作文件

    - 写入文件：`fs.writeSync(fd, string [, position, encoding])`
        - 参数说明
            - fd，文件描述符
            - string，写入的数据
            - position，写入的起始位置
            - encoding，写入时使用的字符编码，默认为 `utf-8`

    - 关闭文件：`fs.closeSync(fd)`
        - 文件使用完之后，应该立刻关闭文件，以节省内存
    - 示例
        - 参考代码
            - [src/fs/sync.js](src/fs/sync.js)
        - 代码内容
            ```js
            var fs = require("fs")
            // 打开文件
            var fd = fs.openSync("hello.txt", "w+")
            // 写入文件
            fs.writeSync(fd, "write:hello world")
            // 关闭文件
            fs.closeSync(fd)
            ```

- 异步文件写入的方法
    - 异步方法都没有返回值，都是通过回调函数的参数返回。如果直接返回就变成同步方法了
    - 打开文件：`fs.open(path, flags[, mode], callback)`
        - 该函数没有返回值
        - 回调函数
            - 两个参数
                1. 错误对象
                    - 没有错误为null
                2. 文件描述符
    - 写入文件：`fs.write(fd, string [, position, encoding], callback)`
        - 回调函数
            - 参数
                1. 错误对象
                2. 写入的字节数
                3. 写入的内容
    - 关闭文件：`fs.close(fd, callback)`
        - 回调函数
            - 参数
                1. 错误对象
    - 示例
        - 参考代码
            - [src/fs/async.js](src/fs/async.js)
        - 代码内容
            ```js
            var fs = require("fs")
            // 打开文件
            var fd = fs.open("hello.txt", "w+", function(e, fd){
                if (e){
                    console.log(e)
                    return
                }

                // 写入文件
                fs.write(fd, "write:hello world by async", function(e){
                        if (!e){
                        console.log("writed")
                        // 关闭文件
                        fs.close(fd, function(){console.log("closed")})
                    }else {
                        console.log(e)
                    }
                })
            })
            ```

## 简单文件写入
[top](#catalog)
- 同步方法：`fs.writeFileSync(file, data[, options])`
- 异步方法：`fs.writeFile(file, data[, options], callback)`
- 参数说明
    - file，文件路径
    - data，写入的数据
    - options，选项，可以对写入进行设置
        - encoding，文字编码，默认为 `utf-8`
        - mode，文件模式，默认为`0o666`
        - flag，文件操作方式，默认为 `w`
- 回调函数
    - 参数
        - 错误对象

- 示例
    - 参考代码
        - [src/fs/writeFile.js](src/fs/writeFile.js)
    - 代码内容
        ```js
        var fs = require("fs")
        fs.writeFileSync("helloX.txt", "writeFileSync:hello world")

        fs.writeFile("helloY.txt", "writeFile:hello world", {flag:"a"}, function(e){
            e ? console.log(e) : console.log("writed and closed")
        })
        ```

## 流式文件写入
[top](#catalog)
- 同步写入、异步写入、简单文件写入 **都不适合大文件的写入**
    - 性能比较差
    - 容易造成内存溢出
- 流式文件写入
    - 流失写入本身也属于一种异步写入方式
    - 持续性的写入方式

- 流式写入的基本方法
    - 创建可写入流对象：`fs.createWriteStream(path [, option])`
        - 参数说明
            - path，文件路径
            - options，选项，可以对写入进行设置
                - encoding，文字编码，默认为 `utf-8`
                - mode，文件模式，默认为`0o666`
                - flag，文件操作方式，默认为 `w`
    - 监听流打开和流关闭事件
        - 监听方式
            - `流对象.once("open", callback)`
            - `流对象.once("close", callback)`
        - 流的打开和关闭只有一次，所以使用 `once` 来绑定一次性事件性能更好
        - 因为流的异步属性，所以无法直接通过对象上的方法来判断流的打开/关闭。一般会通过监听事件来判断流的打开/关闭
    - 关闭流
        - 两种关闭方式，分别负责流的两端
            - `流对象.end()`
                - 等待数据输出结束，并关闭数据输出端
                - 结束后，自动执行 `close()`
            - `流对象.close()`
                - 关闭被写入端
        - 流写入本身是异步的，如果直接执行 `close()` 方法，很可能数据还未输出完就被关闭了
        - 一般会使用 `end()`，等待数据输出完毕再关闭流对象

- 示例
    - 参考代码
        - [src/fs/writeStream.js](src/fs/writeStream.js)
    - 代码内容
        ```js
        var fs = require("fs")

        // 创建可写流
        var stream = fs.createWriteStream("helloZ.txt")

        // 通过open和close事件来监听流的打开与关闭
        // 两个事件只会触发一次，所以使用once绑定事件性能更好
        stream.once("open", function(){
            console.log("stream opened")
        })

        stream.once("close", function(){console.log("stream closed")})

        // 写入数据
        stream.write("qwerrtyyuui")
        stream.write("asdfgh")
        stream.write("zxcvb")

        // 关闭流
        stream.end()
        ```

## 简单文件读取
[top](#catalog)
- 同步读取：`fs.readFileSync(path [, option])`
- 异步读取：`fs.readFile(path [, option], callback)`
    - 回调函数
        - 参数说明
            1. 错误对象
            2. buffer对象
- 返回值和callback中，**读取结果都是buffer**，通过buffer来提升读取结果的通用性

- 示例
    - 参考代码
        - [src/fs/readFile.js](src/fs/readFile.js)
    - 代码内容
        ```js
        var fs = require("fs")

        var buf = fs.readFileSync("xxxx.png")
        console.log(buf)

        // 读取文件并写入到新文件中
        fs.readFile("xxxx.png", function(e, buf){
            if (e){
                console.log(e)
            } else {
                fs.writeFile("yyy.png", buf, function(){
                    console.log("writed")
                })
            }
        })
        ```

## 流式文件读取
[top](#catalog)
- 同步读取、异步读取、简单文件读取 **都不适合大文件的读取**
    - 性能比较差
    - 容易造成内存溢出
- 流式文件读取适用于大文件的读取，可以分多次将文件读取到内存
- 流式读取方法
    - 创建可读流对象:`fs.createReadStream(path [, option])`
        - 参数说明
            - path，文件路径
            - options，选项，可以对写入进行设置
                - encoding，文字编码，默认为 `utf-8`
                - mode，文件模式，默认为`0o666`
                - flag，文件操作方式，默认为 `r`
    - 监听流打开和流关闭事件
        - 监听方式
            - `流对象.once("open", callback)`
            - `流对象.once("close", callback)`
        - 流的打开和关闭只有一次，所以使用 `once` 来绑定一次性事件性能更好
        - 因为流的异步属性，所以无法直接通过对象上的方法来判断流的打开/关闭。一般会通过监听事件来判断流的打开/关闭
    - 读取数据
        - 可读流绑定一个 `data` 事件，读取的数据会传到回到函数的参数中
            `流对象.on("data", function(data){...})`
        - `data` 事件绑定完毕，流对象会自动开始读取数据
        - 每次默认最多读取 65536 个字节
        - 读取完毕会自动关闭流

- 示例
    - 参考代码
        - [src/fs/readStream.js](src/fs/readStream.js)
    - 代码内容
        ```js
        var fs = require("fs")

        // 创建读取流和写入流
        var reader = fs.createReadStream("xxxx.png")
        var writer = fs.createWriteStream("zzzz.png")

        // 监听写入流的打开与关闭
        writer.once("open", function(){console.log("writer opened")})
        writer.once("close", function(){console.log("writer closed")})

        // 监听读取流的打开与关闭
        reader.once("open", function(){console.log("reader opened")})
        reader.once("close", function(){
            console.log("reader closed")
            // 当读取流关闭时，关闭写入流
            writer.end()
        })

        // 读取数据
        reader.on("data", function(data){
            // 进行数据的互写
            writer.write(data)
        })
        ```

## 通过管道进行流式读写
[top](#catalog)
- 直接使用读取流和写入流除了输入输入操作，还要监听打开和关闭事件，比较繁琐，可以通过流对象中的管道来处理
- 管道的使用方法
    1. 创建读取流
    2. 创建写入流
    3. `读取流.pipe(写入流)` 调用读取流的管道方法向写入流中输出数据

- 示例
    - 参考代码
        - [src/fs/pipe.js](src/fs/pipe.js)
    - 代码内容
        ```js
        var fs = require("fs")
        var reader = fs.createReadStream("xxxx.png")
        var writer = fs.createWriteStream("aaaa.png")
        reader.pipe(writer)
        ```

## fs中的其他方法
[top](#catalog)
- 一些方法

    |操作|方法|
    |-|-|-|
    |检查路径是否存在|`fs.existsSync(path)`||
    |获取文件信息|`fs.stat(path, callback)`|返回一个 `fs.Stat` 对象|
    |获取文件信息|`fs.statSync(path)`|返回一个 `fs.Stat` 对象|
    |删除文件|`fs.unlink(path, callback)`||
    |删除文件|`fs.unlinkSync(path)`||
    |读取目录结构|`fs.readdir(path [, options], callback)`|返回一个字符串数组|
    |读取目录结构|`fs.readdirSync(path [, options])`|返回一个字符串数组|
    |截断文件|`fs.truncate(path, len, callback)`|将文件修改为指定的字节大小|
    |截断文件|`fs.truncateSync(path, len)`|将文件修改为指定的字节大小|
    |创建目录|`fs.mkdir(path [, mode], callback)`||
    |创建目录|`fs.mkdirSync(path [, mode])`||
    |删除目录|`fs.remdir(path, callback)`||
    |删除目录|`fs.remdirSync(path)`||
    |重命名|`fs.rename(oldPath, newPath, callback)`||
    |重命名|`fs.renameSync(oldPath, newPath)`||

- 监视文件的更改：`fs.watchFile(filename [, options], listener)`
    - 当文件发生变化时，执行listener
    - 时间间隔太短会过多消耗性能
    - option
        - persistent 布尔型，默认为true
        interval 整型，默认5s
    - listener中两个参数
        - curr 当前文件状态，fs.Stat对象
        - prev 修改前文件状态，fs.Stat对象

# upervisor--nodejs的自启动工具
[top](#catalog)
- 自启动工具: upervisor 是什么？
    - upervisor 会一直watch应用下的所有文件，当发现文件被修改时，就重新载入文件，进行自动部署
    - 使用 upervisor 后，每次修改后不需要手动重启，便于服务器应用的开发与调试
- 安装 supervisor
    ```shell
    npm install -g supervisor
    ```
- 使用 `supervisor` 代替 `npm` 来执行js文件

# 服务器应用开发
## HTTP模块
[top](#catalog)
- Nodejs开发与其他语言开发服务器应用的区别
    - 对于其他服务器端语言，编写后端的代码时需要配合HTTP服务器软件才能处理客户端请求
        - 如Apache、Nginx等等
    - 对于Node.js，创建一个应用时，就已经实现了整个HTTP服务器

- 创建一个http服务
    1. 引入http模块。使用const声明，使外部无法修改
        ```js
        const http = require("http")
        ```
    2. 创建一个http服务，并监听端口
        ```js
        // 创建一个http服务，监听8081端口
        http.createServer((request, response)=>{
            // ...
        }).listen(8081)
        ```
    3. 通过 `request`, `response` 来操作请求与响应

- `request` 的操作
    - `request.url`，获取请求中的url
    - `request.method`，获取请求中的方式（GET、POST等方式）

- `response` 的操作
    - `res.writeHead(ResultCode, 响应头)` 设置响应头与状态码
    - `res.write("...")` 向页面中输出一段内容
        - 输出内容可以是html片段，也可以是一段字符串
        - 如何输出内容中包含中文，需要先写入一条`<meta>`片段，来标识页面的字符集
            ```js
            res.write("<meta charset='utf-8'>")
            ```
    - `res.end( ["str"] )` 结束响应
        - 向客户端发送请求时，必须有该代码，否则客户端将一直处于等待的状态
        - 可以向页面写入一段字符串

- 示例
    - 参考代码
        - [src/server/http/demo01.js](src/server/http/demo01.js)
    - 代码内容及说明
        ```js
        // 使用const声明，使外部无法修改
        const http = require("http")

        /*
            创建web服务
            (request, response)=>{}
                request：浏览器发送的请求数据
                response：发送给浏览器的响应
        */
        http.createServer((req, res)=>{
            console.log(req.url) // 获取url
            // 设置响应头
            // 200表示成功，文件类型是html，字符集是utf-8
            res.writeHead(200, {"Content-type":"text/html;charset='utf-8"})

            // 向页面写入html头，标识html的字符编码，来防止中文乱码
            res.write("<meta charset='utf-8'>")
            // 向页面输出一段内容
            res.write("this is nodejs\n")
            // 输出包含中文字符的内容
            res.write("this is 中\n")

            // 结束响应，这行代码必须写
            // 结束的同时，可以向页面输出一段内容
            res.end("this is end")
        }).listen(8081)// 监听端口

        console.log("server run as http://127.0.0.1:8081")
        ```
    - 启动后，在浏览器输入地址向服务器发送请求
        - localhost:8081
        - 127.0.0.1:8081
    - 当请求时，`console.log(req.url)`会将url输出到控制台
        - localhost:8081，输出 `/`
        - localhost:8081/abc，输出 `/abc`
        - localhost:8081/abc?a=111&b=qqq，输出 `/abc?a=111&b=qqq`

## url模块
[top](#catalog)
- 使用方法

    |方法|功能|备注|
    |-|-|-|
    |`url.parse(url [,queryToObj])`|解析url|<ul><li>`queryToObj`<ul><li>默认为false</li><li>`queryToObj = false`，请求参数被截取为字符串</li><li>`queryToObj = true`，请求参数被转换为一个js对象</li></ul></li> </ul>|
    |`url.format(urlObject)`|将对象转换为url||
    |`url.resolve(from, to)`|添加或者替换地址||

- 使用方法说明
    - `url.parse(url, false)`
        - 代码内容
            ```js
            const url = require("url")
            const result = url.parse("localhost:8081/abc?a=111&b=qqq", false)
            console.log(result)
            ```
        - 输出结果
            ```js
            Url {
            protocol: 'localhost:',
            slashes: null,
            auth: null,
            host: '8081',
            port: null,
            hostname: '8081',
            hash: null,
            search: '?a=111&b=qqq',
            // 请求参数被截取为字符串
            query: 'a=111&b=qqq',
            pathname: '/abc',
            path: '/abc?a=111&b=qqq',
            href: 'localhost:8081/abc?a=111&b=qqq'
            }
            ```
    - `url.parse(url, true)`
        - 代码内容
            ```js
            const url = require("url")
            const result = url.parse("localhost:8081/abc?a=111&b=qqq", true)
            console.log(result)
            ```
        - 输出结果
            ```js
            Url {
            protocol: 'localhost:',
            slashes: null,
            auth: null,
            host: '8081',
            port: null,
            hostname: '8081',
            hash: null,
            search: '?a=111&b=qqq',
            // 请求参数被转换为对象
            query: [Object: null prototype] { a: '111', b: 'qqq' },
            pathname: '/abc',
            path: '/abc?a=111&b=qqq',
            href: 'localhost:8081/abc?a=111&b=qqq'
            }
            ```
    - 输出请求内容
        - 示例代码
            ```js
            const query = url.parse("localhost:8081/abc?a=111&b=qqq", true).query
            console.log(`a=${query.a}, b=${query.b}`)
            ```
        - 输出结果
            ```
            a=111, b=qqq
            ```

- 示例：结合 http 模块，解析客户端请求
    - 参考代码
        - [src/server/url/demo01.js](src/server/url/demo01.js)
    - 代码内容
        ```js
        // 使用const声明，使外部无法修改
        const http = require("http")
        const url = require("url")

        // 创建一个http服务
        http.createServer((req, res)=>{
            res.writeHead(200, {"Content-type":"text/html;charset='utf-8'"})

            // 解析url
            // 去除 chrome的默认请求：/favicon.ico
            if (req.url != '/favicon.ico'){
                const query = url.parse(req.url, true).query
                // 输出请求内容
                let queryStr = ''
                for (let p in query){
                    queryStr += `${p} = ${query[p]},`
                }

                queryStr = queryStr.replace(/,*$/, '')
                console.log(queryStr)
            }

            res.end("this is end")
        }).listen(8081)// 监听端口
        ```

# 其他
## 事件绑定方法
[top](#catalog)
- on: 绑定的事件一直有效
- once: 绑定一次性事件
