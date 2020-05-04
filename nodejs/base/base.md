<span id="catalog"></span>

### 目录
- [Nodejs简介](#Nodejs简介)
- [CommonJS规范与模块化](#CommonJS规范与模块化)
- [包package](#包package)
- [npm](#npm)
- [](#)
- [](#)
- [](#)


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
    - 

# CommonJS规范与模块化
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

- Node中的模块化特性
    - 一个js文件就是一个模块
    - 每个js文件中的js代码都会独立运行在一个函数中，而不是全局作用域
    - 因为每个js文件运行时的特殊性，一个模块中的变量和函数无法在其他模块中访问

- 模块的使用方法
    - 向外部暴露模块内部数据的方法：将数据设置为 `exports` 变量的属性
        ```js
        export.数据别名 = 变量/函数
        ```

    - 引用其他模块的方法： `var 模块名 = require("文件路径")`
        - 文件路径如果使用相对路径，则**必须以 `.` 或 `..` 开头**
        - 执行后使用 `模块别名` 来引用模块下的内容

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

- 模块的标识
    - 模块标识就是模块的名字，就是传递给 `require()` 方法的参数
    - 模块标识的类型过
        - 符合驼峰命名法的字符串
        - 以 `.`、`..` 开头的相对路径
        - 绝对路径

- 两类模块
    - 核心模块
        - 由Node引擎提供的模块
        - 核心模块的标识：模块的名字
    - npm下载的模块
        - 核心模块的标识：模块的名字
    - 文件模块
        - 用户自定义的模块
        - 核心模块的标识：文件的路径

# 包package
[top](#catalog)
- CommonJs的包规范允许将一组相关的模块组合到一起，形成一组完整的工具
- CommonJs包规范的组成
    1. 包结构
        - 用于组织保证的各种文件
    2. 包描述文件
        - 描述包的相关信息，以供外部读取分析

- 包结构
    - 包的本质：一个压缩文件，解压后能还原为目录
    - 符合规范的包结构
        
        |文件|描述|
        |-|-|
        |**package.json**|描述文件，该文件中不能写注释|
        |bin|可执行的二进制文件|
        |lib|js代码|
        |doc|文档|
        |test|单元测试|

# npm
[top](#catalog)
- Node Packge Manager
- CommonJS包规范是理论，npm是其中一种实践
- node通过npm与第三方模块形成了一个很好的生态系统
- npm的用途
    - 第三方模块的发布、安装、依赖
- 安装node时，会自动安装npm
- npm常用指令

    |指令|描述|备注|
    |-|-|-|
    |`npm -v`|查看版本||
    |`npm search 包名`|搜索包||
    |`npm install 包名`|在当前目录中安装包|简写：`npm i 包名`|
    |`npm install 包名 -g`|全局模式安装包|一般都用来安装一些工具|
    |`npm install 包名 --save`|安装包，并添加到依赖中||
    |`npm install`|下载当前项目的依赖包||
    |`npm remove 包名`|删除包|简写：`npm r 包名`|
    |`npm init`|初始化nodejs的开发环境||
    ||||

- `npm install` 指令的注意事项
    - 执行时为了保证正确安装，需要在执行指令的目录下存在 `package.json` 文件
    - 安装的所有包，都保存在 `node_modules` 目录下
- `npm init` 指令的注意事项
    - `name`，输入项目名时，不能有大写字母
    - `entry point`，包的入口文件，默认为 index.js
        - 指定某个文件作为程序的入口


# node中的对象
## 全局对象global
[top](#catalog)
- node中的全局变量是`global`，并且没有 window对象，
- 在全局作用域中创建的变量/函数都会作为 `global`的属性保存