<span id="catalog"></span>

### 目录
- [JS模块化概述](#JS模块化概述)
    - [JS模块化的基本知识](#JS模块化的基本知识)
    - [模块化演进的细节](#模块化演进的细节)
- [模块化规范](#模块化规范)
    - [模块化规范的基本知识](#模块化规范的基本知识)
    - [CommonJS规范](#CommonJS规范)
        - [CommonJS规范说明](#CommonJS规范说明)
        - [CommonJS服务器端实现-Nodejs开发方式](#CommonJS服务器端实现-Nodejs开发方式)
        - [CommonJS浏览器端实现-Browserify开发方式](#CommonJS浏览器端实现-Browserify开发方式)
    - [AMD规范](#AMD规范)
        - [AMD规范说明](#AMD规范说明)
        - [AMD实现-RequireJS开发方式](#AMD实现-RequireJS开发方式)
    - [ES6规范](#ES6规范)
        - [ES6规范说明](#ES6规范说明)
        - [ES6浏览器端实现](#ES6浏览器端实现)
- [](#)


# JS模块化概述
## JS模块化的基本知识
[top](#catalog)
- 什么是JS模块化
    - 将一个复杂的JS代码依据一定的规则，封装成几个块文件，并组合在一起使用
    - 块内部的数据与实现是**私有的**，只**向外部暴露**一些**接口**，来与其他模块**通信**

- JS模块化的演进方式
    1. 全局function模式
        - 全部代码写在一个js文件中
    2. Namespace模式：封装代码
        - 将一些关联的数据与方法添加到一个对象中
        - 减少了全局对象（global、window）对象上的变量数目
        - 缺点
            - 本质是对象，仍然可以通过 `对象.属性` 的方式操作，不安全
    3. IIFE模式：匿名闭包
        - 可以指定需要暴露给外部使用的内容
        - 私有内容可以设置在闭包区域中，无法通过 `对象.属性` 的方式操作
        - 提升了安全性
    4. IIFE模式增强：在IIFE的基础上，注入依赖
        - 将依赖内容作为IIFE的参数传入
        - 这是现代模块实现的基石
    5. 使用模块化规范管理页面中引入的js文件

- 为什么要做模块化？
    - 降低复杂度
    - 进一步解耦
    - 易于部署

- 模块化的好处
    - 避免命名冲突
    - 功能分离
    - 按需加载模块
    - 提高复用性
    - 提高可维护性（业务、功能上的可维护性）

- 模块化的问题
    - 请求多
        - 页面中引入的js文件大大增加，导致页面需要发送大量请求
        - 之前需要引入1个js文件，模块之后可能会引入多个
    - 依赖模糊
        - 如果模块之间有依赖，则引入的顺序不能错，否则模块无法生效
    - 维护难度增加（整体页面上的维护）

## 模块化演进的细节
[top](#catalog)
1. 全局function模式
    - 参考代码
        - [src/evolution/01_global_function_model/base.html](src/evolution/01_global_function_model/base.html)
        - [src/evolution/01_global_function_model/base.js](src/evolution/01_global_function_model/base.js)

    - 全部代码写在一个js文件中: `base.js`
        ```js
        // 模块中的变量、函数等，都被添加到了全局作用域
        let msg = "abcd"
        function foo(){
            console.log(msg)
        }
        ```
    - 在页面中引入，可以在页面中直接修改模块中的数据
        ```html
        <!-- 引入js模块 -->
        <script type="text/javascript" src="base.js"></script>
        <script>
            // 调用js模块
            foo()
            // 输出: abcd

            // 修改模块中的变量
            msg="123456"
            foo()
            // 输出: 123456
        </script>
        ```

2. Namespace模式：封装代码
    - 参考代码
        - [src/evolution/02_namespace_model/base.html](src/evolution/02_namespace_model/base.html)
        - [src/evolution/02_namespace_model/base.js](src/evolution/02_namespace_model/base.js)
    - 将一些关联的数据与方法添加到一个对象中
        ```js
        let obj = {
            msg:"qwert",
            foo(){
                console.log(this.msg)
            }
        }
        ```
    - 在页面中引入并使用。仍然可以修改对象中的数据
        ```html
        <!-- 引入js模块 -->
        <script type="text/javascript" src="base.js"></script>
        <script>
            // 通过对象调用方法
            obj.foo()
            // 输出: qwert

            // 可以在外部修改变量的属性
            obj.msg = "asdfgh"
            obj.foo()
            // 输出: asdfgh
        </script>
        ```

3. IIFE模式：匿名闭包
    - 参考代码
        - [src/evolution/03_IIFE_model/base.html](src/evolution/03_IIFE_model/base.html)
        - [src/evolution/03_IIFE_model/base.js](src/evolution/03_IIFE_model/base.js)

    - 通过IIFE将可用内容导入window对象
        ```js
        (function(){
            let msg = "asdfgh"
            function foo(){
                console.log(msg)
            }

            // 创建对象来封装暴露给外部使用的内容
            window.module3 = {foo}
        })()
        ```
    - 在页面中引入，但是无法修改对象内部的数据
        ```html
        <script type="text/javascript" src="base.js"></script>
        <script type="text/javascript">
            // 调用模块中的方法
            module3.foo()
            // 输出: asdfgh

            // 无法修改变量
            module3.msg = 'zxcvbn'
            module3.foo()
            // 输出: asdfgh
        </script>
        ```

4. IIFE模式增强：在IIFE的基础上，注入依赖
    - 参考代码
        - [src/evolution/04_IIFE_up_model/base.html](src/evolution/04_IIFE_up_model/base.html)
        - [src/evolution/04_IIFE_up_model/base.js](src/evolution/04_IIFE_up_model/base.js)
        - [src/evolution/04_IIFE_up_model/others.js](src/evolution/04_IIFE_up_model/others.js)

    - 创建一个依赖项：`others.js`
        ```js
        (function (window){
            let msg = "this is other"
            function show(){
                console.log(msg)
            }

            window.other03 = {show} // 将方法封装到对象中暴露给外部使用
        })(window) //注入依赖
        ```
    - `base.js`依赖于`others.js`
        ```js
        (function(window, other){
            let msg="model4"

            function foo(){
                console.log(msg)
                // 调用外部依赖的功能
                other.show()
            }

            window.module4 = foo
        })(window, other03) //注入依赖
        ```
    - 在页面中引入
        ```html
        <!-- others.js 和 base.js 有依赖关系，需要按顺序引入js文件-->
        <script type="text/javascript" src="others.js"></script>
        <script type="text/javascript" src="base.js"></script>
        <script type="text/javascript">
            // 调用 base.js中的方法
            module4()
            // 输出:
            // module4
            // this is other
        </script>
        ```

5. 使用模块化规范管理页面中引入的js文件

# 模块化规范
## 模块化规范的基本知识
[top](#catalog)
- 已有的模块化规范
    - ES6
    - CommonJS
    - AMD
    - CMD （不常用）

- 规范的应用位置
    - 服务器端实现: 最终的代码通过指令运行在服务器上
    - 浏览器端实现: 最终的代码通过浏览器运行在页面中

## CommonJS规范
### CommonJS规范说明
[top](#catalog)
- 一个文件一个模块
- 加载方式
    - 在服务端：运行时以同步的方式加载
    - 在浏览器端：模块需要**提前编译打包**处理
        - 防止页面长时间等待
        - 对于模块引入方式: `var 包名 = require('模块标识')`，前端页面无法识别`require`关键字，需要提前编译
- 语法
    - 导出模块:
        - 导出方式
            - `exports.xxx = value`，可以多次设置
            - `module.exports = {...}`，多次设置会覆盖
        - 模块导出的本质: 设置`exports`对象
    - 引入模块: `require('模块标识')`


### CommonJS服务器端实现-Nodejs开发方式
[top](#catalog)
- 开发方式
    - 开发模块
    - 在 `app.js` 中聚合模块
    - 通过 node 指令来调用  `app.js`

- 参考开发示例: [src\commonjs_sample\nodejs](src\commonjs_sample\nodejs)
- 示例
    1. 目录结构
        ```
        nodejs
        ├─ app.js
        ├─ package.json
        └─ module
            ├─ module1.js
            ├─ module2.js
            └─ module3.js
        ```

    2. 编写模块
        - 参考代码
            - [src/commonjs_sample/nodejs/module/module1.js](src/commonjs_sample/nodejs/module/module1.js)
            - [src/commonjs_sample/nodejs/module/module2.js](src/commonjs_sample/nodejs/module/module2.js)
            - [src/commonjs_sample/nodejs/module/module3.js](src/commonjs_sample/nodejs/module/module3.js)
        - 代码内容
            1. `module1`，导出一个对象
                ```js
                module.exports = {
                    msg: "module1",
                    foo(){
                        console.log(this.msg)
                    }
                }
                ```
            2. `module2`，导出一个函数
                ```js
                module.exports = function(){
                    console.log("module2")
                }
                ```
            3. `module3`，导出两个函数和一个数组
                ```js
                exports.foo = function(){
                    console.log("modules3 foo")
                }
                exports.bar = function(){
                    console.log("modules3 bar")
                }

                exports.arr = [4,1,2,5,4,3,1,2]
                ```

    3. 聚合模块并进行调用
        - 参考代码
            - [src/commonjs_sample/nodejs/app.js](src/commonjs_sample/nodejs/app.js)
        - 代码内容
            ```js
            let unip = require("uniq")
            // 聚合其他模块
            let module1 = require("./module/module1")
            let module2 = require("./module/module2")
            let module3 = require("./module/module3")

            // 调用模块
            module1.foo()
            module2()
            module3.foo()
            module3.bar()

            // 调用外部的包uniq
            let result = unip(module3.arr)
            console.log(result)

            // 输出:
            // module1
            // module2
            // modules3 foo
            // modules3 bar
            // [ 1, 2, 3, 4, 5 ]
            ```
    4. 使用node指令执行: `node app.js`

### CommonJS浏览器端实现-Browserify开发方式
[top](#catalog)
- 开发方式
    1. 使用 npm 安装 `browserify`
    2. 创建目录结构
    3. 编写模块
    4. 在 `app.js` 中聚合模块
    5. 使用 `browserify` 对代码进行编译和打包
        - 指令: `browserify <app.js路径> -o <bundle.js输出路径>`
    6. 在页面中引入 `app.js` 编译后生成的文件路径
    7. 启动页面，自动执行js

- 参考开发示例: [src\commonjs_sample\browserify](src\commonjs_sample\browserify)

- 示例
    1. 使用 npm 安装 Browserify。并且需要**同时进行全局和局部安装**
        - 全局安装: `npm i browserify -g`
        - 局部安装: `npm i browserify --save-dev`
            - 只添加到开发依赖
            - 编译后供浏览器使用时，就不需要该模块了

    2. 创建目录结构
        ```
        browserify
          ├─ index.html
          ├─ package.json
          └─ js
              ├─ dist              打包生成文件的目录build (非必须，也可以用build)
              └─ src               源码目录
                   ├─ module1.js
                   ├─ module2.js
                   ├─ module3.js
                   └─ app.js       应用文件
        ```

    3. 编写模块
        - 参考代码
            - [src/commonjs_sample/browserify/js/src/module1.js](src/commonjs_sample/browserify/js/src/module1.js)
            - [src/commonjs_sample/browserify/js/src/module2.js](src/commonjs_sample/browserify/js/src/module2.js)
            - [src/commonjs_sample/browserify/js/src/module3.js](src/commonjs_sample/browserify/js/src/module3.js)
        - 代码内容
            1. `module1`，导出一个对象
                ```js
                module.exports = {
                    msg: "module1",
                    foo(){
                        console.log(this.msg)
                    }
                }
                ```
            2. `module2`，导出一个函数
                ```js
                module.exports = function(){
                    console.log("module2")
                }
                ```
            3. `module3`，导出两个函数和一个数组
                ```js
                exports.foo = function(){
                    console.log("modules3 foo")
                }
                exports.bar = function(){
                    console.log("modules3 bar")
                }

                exports.arr = [4,1,2,5,4,3,1,2]
                ```

    4. 聚合模块并进行调用
        - 参考代码
            - [src/commonjs_sample/browserify/js/src/app.js](src/commonjs_sample/browserify/js/src/app.js)
        - 代码内容
            ```js
            let module1 = require("./module1")
            let module2 = require("./module2")
            let module3 = require("./module3")

            module1.foo()
            module2()
            module3.foo()
            module3.bar()
            ```

    5. **对js代码进行编译和打包**
        - `browserify js/src/app.js -o js/dist/bundle.js`

    6. 在 `index.js` 中引入编译后的文件
        - 参考代码
            - [src/commonjs_sample/browserify/index.html](src/commonjs_sample/browserify/index.html)
        - 代码内容
            ```html
            <!-- 无法直接引入 app.js，浏览器无法识别require关键字 -->
            <!-- <script type="text/javascript" src="./js/src/app.js"></script> -->
            <!-- 引入 app.js 编译后生成的文件 -->
            <script type="text/javascript" src="./js/dist/bundle.js"></script>
            ```

    7. 启动页面
        - 控制台输出结果
            ```
            module1
            module2
            modules3 foo
            modules3 bar
            ```

## AMD规范
### AMD规范说明
[top](#catalog)
- AMD规范专门用于浏览器端
- AMD规范使用 `RequireJS` 来实现，所以 `RequireJS` 和 AMD规范在概念上相同
- 模块的加载是异步的

- 语法
    - 导出模块
        - 导出没有依赖的模块
            ```js
            define(function(){
                return 导出模块
            })
            ```
        - 导出有依赖的模块
            ```js
            define(['module1', 'module2'], function(m1, m2){
                // 有几个依赖，函数就有几个参数。依赖将会自动注入
                return 导出模块
            })
            ```
    - 引入模块
        - 参考: https://requirejs.org/docs/api.html#jsfiles
        - 引入方式
            ```js
            requirejs.config({
                baseUrl: 'js/lib', //模块搜索的起点
                paths: {    // 模块--路径 映射
                    模块名: '模块路径(不加 .js)'
                }
            });

            // 方式1
            require(['module1', 'module2'], function(m1, m2){
                // 使用 m1, m2
            })

            // 方式2
            requirejs(['module1', 'module2'], function(m1, m2){
                // 使用 m1, m2
            })
            ```

        - 一般会将引入的代码添加到IIFE中
            ```js
            (function(
                requirejs.config({
                    baseUrl: 'js/lib', //模块搜索的起点
                    paths: {    // 模块--路径 映射
                        模块名: '模块路径(不加 .js)'
                    }
                });
                requirejs(['module1', 'module2'], function(m1, m2){
                    // 使用 m1, m2
                })
            ))()
            ```
        - 对于第三方模块的配置方法相同，也需要在requirejs的配置中添加模块名与路径的映射关系
        - 模块引入后 `requirejs` 将会自动检查依赖关系

- 页面引入语法
    ```html
    <script data-main="自定义js文件的路径" src="require.js的保存路径"></script>
    ```

### AMD实现-RequireJS开发方式
[top](#catalog)
1. 下载 `require.js`
    - 官网: `https://requirejs.org/`
2. 将 `require.js` 导入到 `js/libs/require.js`
3. 目录结构
    ```
    requirejs
      ├─ index.html
      └─ js
          ├─ main.js            主模块（也可以用app.js）
          ├─ libs               js库
          │    └─ require.js
          └─modules
               ├─ module1.js    自定义模块
               └─ module2.js    自定义模块
    ```
4. 编写模块
    - 参考代码
        - [src/amd_sample/requirejs/js/modules/module1.js](src/amd_sample/requirejs/js/modules/module1.js)
        - [src/amd_sample/requirejs/js/modules/module2.js](src/amd_sample/requirejs/js/modules/module2.js)
    - 代码内容
        1. `module1.js`，定义没有依赖的模块
            ```js
            define(function() {
                let msg = "this is m1"
                function show(){
                    console.log(msg)
                }

                return {show}
            });
            ```
        2. `module2.js`，定义一个有依赖的模块
            ```js
            define(['module1'], function(module1) {
                let msg = "this is m2"
                function foo(){
                    console.log(msg)
                    module1.show()
                }

                return {foo}
            });
            ```
5. 聚合模块
    - 参考代码
        - [src/amd_sample/requirejs/js/main.js](src/amd_sample/requirejs/js/main.js)
    - 代码内容
        ```js
        // 聚合模块

        (function(){
            requirejs.config({
                paths: {
                    module1: './modules/module1',
                    module2: './modules/module2'
                }
            });

            requirejs(['module2'], function(module2){
                module2.foo()
            })
        })()
        ```
6. 在页面中引入模块
    - 参考代码
        - [src/amd_sample/requirejs/index.html](src/amd_sample/requirejs/index.html)
    - 代码内容
        ```html
        <script data-main="js/main.js" src="js/libs/require.js"></script>
        ```

7. 启动页面
    - 输出内容
        ```
        this is m2
        this is m1
        ```

## ES6规范
### ES6规范说明
[top](#catalog)
- 依赖的模块需要打包处理
    - 对于低版本的浏览器，需要将ES6的语法转换成浏览器可以识别的版本
- 语法
    - 导出模块: `export`
        - 3种导出方式
            1. 多次导出
                ```js
                export function foo(){...}
                export function bar(){...}
                export let obj = {...}
                ```
            2. 统一导出，统一导出相当于多次导出
                ```js
                function foo(){...}
                function bar(){...}
                let obj = {...}
                export {foo, bar, obj}
                ```
            3. 混合导出：多次导出和统一导出可以组合使用
                ```js
                function foo(){...}
                function bar(){...}
                export {foo, bar}

                export let obj = {...}
                ```
            4. 默认导出
                - 可以导出任意数据类型
                - 导出什么，引入时就接收到什么
                - 一个模块只能使用一次默认导出。多次默认导出会报错
                - 写法
                    ```js
                    export default {...}
                    ```

    - 引入模块: `import`
        - 模块的标识：与`require("模块的标识")`相同
            - 本地模块使用地址，以`./`、`../`开头
            - 第三方模块、内置模块，使用模块名

        - 引入默认导出的内容
            ```js
            import 模块名 from "模块标识"
            ```
        - 引入其他导出方式的内容。必须使用**解构赋值**的方式，才能获取模块中导出的内容
            ```js
            import {模块内容1, 模块内容2,...} from "模块标识"
            ```
        - 全部引入
            ```js
            import * as 别名 from "模块标识"
            // 调用方法
            别名.属性
            ```
        - 使用别名解决命名冲突
            ```js
            import {a as A1} from "模块标识1"
            import {a as A2} from "模块标识2"
            ```

- 实现方式
    - 浏览器端：使用Babel转换，然后再使用 Browserify 编译打包
    - 服务器端：使用Babel转换，然后使用转换后的js模块

### ES6浏览器端实现
[top](#catalog)
- 使用Babel可以将ES6编译为ES5的代码
- 使用Browserify将js代码编译打包
- 开发方式
    1. 安装必要的依赖包

        |需要的包|指令|说明|
        |-|-|-|
        |babel-cli|`npm i babel-cli -g`|babel命令行接口，只需要全局安装，使babel的命令可以使用|
        |babel-preset-es2015|`npm i babel-preset-es2015 --save-dev`|只添加开发依赖，运行页面时不需要|
        |browserify|`npm i browserify -g`<br>`npm i browserify --save-dev`|需要**同时进行全局和局部安装**

    2. 创建 `.babelrc` 文件，并在文件内部指定代码转换的目标
        - babel执行前会读取该配置文件，获取代码转换的目标版本
        - 文件内容
            ```
            {
                "presets": ["es2015", "xxx", ....=]
            }
            ```
    3. 创建开发目录
    4. 编写模块
    5. 聚合模块
        - 如果模块中的内容是统一导出，或多次导出时，需要使用解构赋值的方式来引入： `import {xxx, yyy} from "模块路径"`
    6. 编译代码
        1. `babel <代码目录> -d <编译结果目录>`
            - 使用 Babel 将**所有的**ES6代码编译为ES5代码（但是会包含CommonJS语法）
            - 不指定具体编译哪一个模块的代码，而是全部编译
            - 编译结果目录 可以不创建，babel指令会自动创建
        2. `browserify <babel编译结果文件> -o <bundle.js输出路径>`
            - babel编译后虽然已经可以使用ES5，但是内部仍然有 CommonJS语法，需要使用browserify进行转换
    7. 在页面中引入编译结果js文件
    8. 启动页面

- 参考开发示例: [src/es6_sample/babel_browserify](src/es6_sample/babel_browserify)

- 示例
    1. 安装必要的依赖包

    2. 创建 `.babelrc` 文件，并在文件内部指定代码转换的目标
        - 文件内容
            ```
            {
                "presets": ["es2015"]
            }
            ```

    3. 创建开发目录
        ```
        babel_browserify
          ├─ js
          │   ├─ dist               保存browserify的编译打包结果
          │   ├─ lib                保存babel编译的结果
          │   └─ src
          │       ├─ main.js        聚合模块
          │       ├─ module1.js     自定义模块
          │       ├─ module2.js
          │       └─ module3.js
          ├─ .babelrc               babel配置文件
          ├─ index.html             页面
          └─ package.json
        ```

    4. 编写模块
        - 参考代码
            - [src/es6_sample/babel_browserify/js/src/module1.js](src/es6_sample/babel_browserify/js/src/module1.js)
            - [src/es6_sample/babel_browserify/js/src/module2.js](src/es6_sample/babel_browserify/js/src/module2.js)
            - [src/es6_sample/babel_browserify/js/src/module3.js](src/es6_sample/babel_browserify/js/src/module3.js)
            - [src/es6_sample/babel_browserify/js/src/module4.js](src/es6_sample/babel_browserify/js/src/module4.js)
        - 代码内容
            - `module1.js`
                ```js
                // 多次导出
                export function foo(){
                    console.log("this is mdoule1 foo")
                }

                export function bar(){
                    console.log("this is mdoule1 bar")
                }

                export let arr = [1, 2, "eee", 55]
                ```
            - `module2.js`
                ```js
                // 混合导出
                function foo(){
                    console.log("this is module2 foo")
                }

                function bar(){
                    console.log("this is module2 bar")
                }

                export {foo, bar}

                export let obj = {name:"testNamestr"}
                ```
            - `module3.js`
                ```js
                // 默认导出
                export default {
                    msg:"default msg",
                    foo(){
                        console.log("this is module3 default")
                    }
                }
                ```
            - `module4.js`
                ```js
                // 混合导出
                function fun(){
                    console.log("this is module4 fun")
                }

                function fun2(){
                    console.log("this is module4 fun2")
                }

                export {fun, fun2}
                ```

    5. 聚合模块
        - 参考代码
            - [src/es6_sample/babel_browserify/js/src/main.js](src/es6_sample/babel_browserify/js/src/main.js)
        - 代码内容
            ```js
            // 无法通过模块名引入
            // import module1 from "./module1"
            // import module2 from "./module2"

            // console.log(module1,module2)

            // 使用解构赋值的方式来引入
            import {foo as foo1, bar as bar1} from "./module1"
            // 使用别名解决命名冲突
            import {foo as foo2, bar as bar2, obj} from "./module2"
            import module3 from "./module3"
            import * as module4 from "./module4"

            foo1()
            bar1()
            foo2()
            bar2()
            console.log(obj)
            module3.foo()
            module4.fun()
            module4.fun2()
            ```

    6. 编译代码
        - `babel js/src -d js/lib`
        - `browserify js/lib/main.js -o js/dist/bundle.js`

    7. 在页面中引入编译结果js文件
        - 参考代码
            - [src/es6_sample/babel_browserify/index.html](src/es6_sample/babel_browserify/index.html)
        - 代码内容
            ```html
            <script type="text/javascript" src="js/dist/bundle.js"></script>
            ```

    8. 启动页面
        - 输出结果
            ```
            this is mdoule1 foo
            this is mdoule1 bar
            this is module2 foo
            this is module2 bar
            {name: "testNamestr"}
            this is module3 default
            this is module4 fun
            this is module4 fun2
            ```

[top](#catalog)
