<span id="catalog"></span>

### 目录---Vue基本知识与Vue指令
- [Vuejs概述](#Vuejs概述)
- [Vuejs安装](#Vuejs安装)
- [helloworld程序](#helloworld程序)
    - [helloworld](#helloworld)
    - [列表展示示例](#列表展示示例)
    - [计数器示例](#计数器示例)
- [vue中的MVVM](#vue中的MVVM)
- [Vue的生命周期?????](#Vue的生命周期)
- [Vue基本指令](#Vue基本指令)
    - [Vue插值指令](#Vue插值指令)
        - [Mustache语法](#Mustache语法)
        - [v-once指令--一次性渲染](#v-once指令--一次性渲染)
        - [v-html指令--渲染html字符串](#v-html指令--渲染html字符串)
        - [v-text指令--绑定数据](#v-text指令--绑定数据)
        - [v-pre指令--显示原始代码](#v-pre指令--显示原始代码)
        - [v-cloak指令--临时属性遮盖](#v-cloak指令--临时属性遮盖)
    - [v-bind--绑定属性](#v-bind--绑定属性)
        - [v-bind的基本用法](#v-bind的基本用法)
        - [通过对象语法绑定class](#通过对象语法绑定class)
        - [通过数组语法绑定class](#通过数组语法绑定class)
        - [通过对象语法绑定style](#通过对象语法绑定style)
        - [通过数组语法绑定style](#通过数组语法绑定style)
        - [值绑定--v-bind在表单项中的应用](#值绑定--v-bind在表单项中的应用)
    - [v-on--事件监听](#v-on--事件监听)
        - [v-on的基本用法](#v-on的基本用法)
        - [v-on的修饰符](#v-on的修饰符)
    - [条件判断指令](#条件判断指令)
        - [条件判断指令的基本使用](#条件判断指令的基本使用)
        - [条件判断指令示例-登录切换](#条件判断指令示例-登录切换)
    - [v-show--控制元素渲染](#v-show--控制元素渲染)
    - [v-for--遍历数组和对象](#v-for--遍历数组和对象)
        - [v-for的基本使用方法](#v-for的基本使用方法)
        - [可以进行响应式更新的数组方法](#可以进行响应式更新的数组方法)
        - [v-for列表展示示例](#v-for列表展示示例)
    - [v-model--双向绑定](#v-model--双向绑定)
        - [v-model的基本用法](#v-model的基本用法)
        - [v-model的实现原理](#v-model的实现原理)
        - [v-model与radio结合使用](#v-model与radio结合使用)
        - [v-model与checkbox结合使用](#v-model与checkbox结合使用)
        - [v-model与select结合使用](#v-model与select结合使用)
        - [v-model--修饰符](#v-model--修饰符)
    - [Vue基本指令--综合示例--图书购物车](#Vue基本指令--综合示例--图书购物车)
- [Vue-options中的选项](#Vue-options中的选项)
    - [常用options选项](#常用options选项)
    - [computed--计算属性](#computed--计算属性)
        - [计算属性的简介](#计算属性的简介)
        - [简写计算属性的使用示例](#简写计算属性的使用示例)
        - [计算属性getter和setter的示例](#计算属性getter和setter的示例)
        - [计算属性的缓存](#计算属性的缓存)
    - [filters--过滤器](#filters--过滤器)

# Vuejs概述
[top](#catalog)
- Vuejs是一个渐进式框架
    - 渐进式意味着可以将Vuejs作为应用的一部分嵌入其中，并逐步的将其他框架的应用改造成Vuejs的应用
    - 如果希望将更多的业务逻辑或使用Vue实现，可以考虑Vue核心库及其生态系统
        - 如: Core + Vuejs + Vuex

- Vue的特点和web开发场景中的高级功能
    - 解耦试图和数据
    - 可复用的组件
    - 前端路由技术
    - 状态管理
    - 虚拟DOM

- Vue提供响应式编程
    - 页面后台的数据发生修改时，可以立刻反应到页面
    - 页面中修改值时，也会立刻赋值到后台

# Vuejs安装
[top](#catalog)
- 安装方式
    - CDN引入
    - 下载和引入
    - **NPM安装管理**

- CDN引入
    - 开发环境引入
        ```html
        <script src='https://cdn.jsdelivr.net/npm/vue/dist/vue.js'></script>
        ```
    - 生产环境引入
        ```html
        <script src='https://cdn.jsdelivr.net/npm/vue'></script>
        ```

- 下载和引入
    - 开发环境: https://vuejs.org/js/vue.js
    - 生产环境: https://vuejs.org/js/vue.min.js

- NPM安装
    - 安装包: `npm i vue --save`
    - 一般vue需要在页面中使用，是**运行时依赖**，所以直接安装到 `dependencies` 下


# helloworld程序
## helloworld
[top](#catalog)
- 需要从 https://vuejs.org/js/vue.js 下载开发环境的vuejs
- 参考代码
    - [src/helloworld/hello.html](src/helloworld/hello.html)
- html内容
    - 在控制台，通过 `app.message`、`app.name`，修改数据后，即可通过双向绑定立刻显示到页面中
    ```html
    <!DOCTYPE HTML>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>hello</title>
        </head>
        <body>
            <!-- 绑定数据 -->
            <div id="app">{{message}} : {{name}}</div>
            <script type='text/javascript' src="./js/vue.js"></script>
            <script>
                // 1. 创建一个vue实例，并传入一些参数
                let app = new Vue({
                    el:'#app', // 2. 挂载要管理的元素
                    data: { // 3. 定义数据
                            message: 'test msg',
                            name: 'helloworld',
                    }
                });
            </script>
        </body>
    </html>
    ```


## 列表展示示例
[top](#catalog)
- 需要从 https://vuejs.org/js/vue.js 下载开发环境的vuejs
- 参考代码
    - [src/helloworld/show_list.html](src/helloworld/show_list.html)
- html内容
    ```html
    <!DOCTYPE HTML>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>show list</title>
        </head>
        <body>
            <div id="mylist">
                <!-- 显示方式1：手动循环 -->
                <section>show list method01:</section>
                <ul>
                    <li>{{tlist[0]}}</li>
                    <li>{{tlist[1]}}</li>
                    <li>{{tlist[2]}}</li>
                    <li>{{tlist[3]}}</li>
                </ul>

                <!-- 显示方式2：使用vue指令，自动遍历数组 -->
                <section>show list method02:</section>
                <ul>
                    <li v-for='item in tlist'>{{item}}</li>
                </ul>
            </div>
            <script type='text/javascript' src='./js/vue.js'></script>
            <script type='text/javascript'>
                let app = new Vue({
                    el: '#mylist',
                    data:{
                        tlist:[
                            'aaaaa',
                            'bbbb',
                            'cccc',
                            'ddddd',
                        ]
                    }
                });
            </script>
        </body>
    </html>
    ```
## 计数器示例
[top](#catalog)
- 需要从 https://vuejs.org/js/vue.js 下载开发环境的vuejs
- 参考代码
    - [src/helloworld/counter.html](src/helloworld/counter.html)
- html内容
    ```html
    <!DOCTYPE HTML>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>counter</title>
        </head>
        <body>
            <div id="counter">
                <div>{{count}}</div>
                <!-- 绑定指令监听的事件 -->
                <!-- 1. 直接修改变量 -->
                <button v-on:click="count++">+</button>
                <button v-on:click="count--">-</button>
                <br>
                <!-- 2. 调用方法 -->
                <button v-on:click="incr">+</button>

                <!-- 3. 绑定方法的简写方式 -->
                <button @click="sub">-</button>
            </div>

            <script type='text/javascript' src='./js/vue.js'></script>
            <script type='text/javascript'>
                let counter = document.querySelector('#counter');

                // 将data从Vue对象中抽取处理，单独设置，形成model
                // 在Vue对象内部，通过代理的方式，使得 this 对象可以访问到内部的属性
                let app_data = { count:0, };

                // 通过 Vue对象构造 ViewModel
                let app = new Vue({
                    el: counter,// 直接挂载DOM对象
                    data: app_data,
                    methods:{
                        incr: function(){this.count++},
                        sub: function(){this.count--},
                    }
                });
            </script>
        </body>
    </html>
    ```

# vue中的MVVM
[top](#catalog)
- 什么是MVVM
    - View
    - Model
    - ViewModel

- MVVM各层的功能

    |层|层的类型|功能|
    |-|-|-|
    |View|视图层，对于前端就是DOM层|给用户展示各种信息|
    |Model|数据层|数据的来源<ul><li>固定的死数据</li><li>来自服务器，从网络上请求下来的数据</li></ul>|
    |ViewModel|视图模型层|相当于 View 和 Model 之间的桥梁，使 View 和 Model 之间能够通行<ul><li>负责 <span style="color:red">数据绑定（Data Binding）</span>，将Model的改变实时的反应到 View</li><li>负责 <span style="color:red">DOM监听（DOM Listener）</span>，当DOM发生一些事件时(点击、滚动、touch等)，可以监听到，并在需要的情况下改变对应的data</li></ul>|

- [计数器示例](#计数器示例) 中的MVVM
    - View层
        - 可以理解为html
            ```html
            <div id="counter">
                <div>{{count}}</div>
            </div>
            ```
        - 也可以理解为DOM对象
            ```js
            let counter = document.querySelector('#counter');
            ```

    - Model层
        ```js
        let app_data = { count:0, };
        ```

    - ViewModel层
        ```js
        // 1. 在创建 Vue 对象时，填充了数据，完成了【初始的 Data Binding】
        new Vue({...})
        ```
        ```html
        <!-- 2. html中的指令由 Vue 对象负责解析，并开始执行【DOM Listener】-->
        <button v-on:click="count++">+</button>
        <button v-on:click="count--">-</button>
        <button v-on:click="incr">+</button>
        <button @click="sub">-</button>
        ```
        ```js
        let app = new Vue({
            // ...
            // 3. DOM Listener 发现事件被触发，Vue 对象调用对应的回调函数
            methods:{
                // 4. 触发事件结束后，将数据重新绑定到页面，完成了【二次 Data Binding】
                incr: function(){this.count++},
                sub: function(){this.count--},
            }
        });
        ```

# Vue的生命周期
[top](#catalog)
- mounted: template 挂载到 DOM 之后执行
- updated: 在页面发生更新后
    - 什么时候会刷新
        - 数据发生更新
        - 路由切换
- created: 进入路由创建组件之后，一般会做网络请求
    - 路由被包在 keep-alive中后不会重复创建销毁
- destroyed: 离开路由，销毁组件
    - 路由被包在 keep-alive中后不会重复创建销毁
- actived: 路由被包在 keep-alive 后，每次进入路由时触发
- deactived: 路由被包在 keep-alive 后，每次离开路由时触发
????

# Vue基本指令
## Vue插值指令
### Mustache语法
[top](#catalog)
- 语法
    <div>{{变量名}}</div>
- 功能
    1. 动态（双向）绑定**content部分**数据
    2. 可以同时应用多个Mustache语法来绑定多个数据
        ```html
        <div>{{param1}} - {{param2}}</div>
        ```
    3. 可以绑定表达式
        ```html
        <!-- 拼接内容 -->
        <div>{{param1 + ' ' + param2}}</div>
        <!-- 显示count的2倍 -->
        <div>{{count * 2}}</div>
        ```

- 使用时，相当于省略了 `this`
    - vue内部会通过代理，来直接操纵 `data` 中的数据

- 示例
    - 参考代码
        - [src/base_cmd/insert_data/mustache.html](src/base_cmd/insert_data/mustache.html)
    - 代码内容
        ```html
        <div id="app">
            <!-- 拼接内容 -->
            <div>{{param1}} - {{param2}}</div>
            <div>{{param1 + ' ' + param2}}</div>
            <!-- 显示count的2倍 -->
            <div>{{count * 2}}</div>
        </div>
        ```
        ```js
        let app = new Vue({
            el:"#app",
            data:{
                count:4,
                param1:'12345',
                param2:'qwert',
            }
        });
        ```

### v-once指令--一次性渲染
[top](#catalog)
- 功能
    - 元素和组件**只渲染一次**，不会随着数据的改变而改变

- 使用方法
    - 该指令后面不需要任何表达式
    ```html
    <div v-once>{{text}}</div>
    ```

- 示例
    - 参考代码
        - [src/base_cmd/insert_data/v-once.html](src/base_cmd/insert_data/v-once.html)

    - 代码内容
        ```html
        <div id="app">
            <div>text01: {{msg}}</div>

            <!-- 1. 直接显示html源代码 -->
            <div v-once>text02: {{msg}}</div>
            <!-- 2. 使浏览器加载html -->
            <button v-on:click='change'>change</button>
        </div>
        ```
        ```js
        let app = new Vue({
            el:"#app",
            data:{
                msg:'testmsg'
            },
            methods:{
                change:function(){
                    this.msg='123456';
                }
            }
        });
        ```

### v-html指令--渲染html字符串
[top](#catalog)
- 功能
    - 使浏览器加载html代码并渲染
        - 如果通过 `{{}}` 语法，会直接显示变量内容，不会被渲染。v-html可以避免这个问题
    - 标签内部如果已经有其他信息，会被该指令覆盖

- 使用方法
    ```html
    <div v-html='变量名'></div>
    ```

- 示例
    - 参考代码
        - [src/base_cmd/insert_data/v-html.html](src/base_cmd/insert_data/v-html.html)

    - 代码内容
        ```html
        <div id="app">
            <div>show text: {{htmlData}}</div>

            <!-- 最终会被翻译为 <div><a></a></div> 的形式 -->
            show html: <div v-html='htmlData'></div>

        </div>
        ```
        ```js
        let app = new Vue({
            el:"#app",
            data:{
                // 模拟从服务器获取一段html代码
                htmlData:'<a href="https://www.baidu.com/">baidu</a>'
            }
        });
        ```

### v-text指令--绑定数据
[top](#catalog)
- 功能
    - 与mustache类似，动态（双向）绑定**content部分**的数据

- 使用方法
    ```html
    <div v-text='变量名'></div>
    ```

- 缺点
    - 不如mustache灵活
    - 会覆盖标签内已有的数据

- 示例
    - 参考代码
        - [src/base_cmd/insert_data/v-text.html](src/base_cmd/insert_data/v-text.html)
    - 代码内容
        ```html
        <div id="app">
            <div v-text='msg'>msg =</div>
            <button v-on:click='change'>change</button>
        </div>
        ```
        ```js
        let app = new Vue({
            el:"#app",
            data:{
                msg:'testmsg'
            },
            methods: {
                change: function(){
                    this.msg="changed msg";
                }
            }
        });
        ```

### v-pre指令--显示原始代码
[top](#catalog)
- 功能
    - 不对元素进行编译，直接显示数据内容
    - 常用于显示mustache语法

- 使用方法
    ```html
    <div v-pre>{{msg}}</div>
    ```

- 示例
    - 参考代码
        - [src/base_cmd/insert_data/v-text.html](src/base_cmd/insert_data/v-text.html)
    - 代码内容
        ```html
        <div id="app">
            <!-- 1. 执行数据绑定 -->
            <div>{{msg}}</div>
            <!-- 2. 跳过编译，直接在页面中显示 {{msg}} -->
            <div v-pre>{{msg}}</div>
        </div>
        ```

### v-cloak指令--临时属性遮盖
[top](#catalog)
- 功能
    - 隐藏未编译的 `{{}}` 标签直到 Vue对象准备完成
    - 可以防止页面加载时 `{{}}` 标签的闪烁

- 使用方法
    - html部分，将 `v-cloak` 作为标签的无属性值属性使用
        ```html
        <div id="app" v-cloak>
            {{msg}}
        </div>
        ```
    - css部分
        ```css
        [v-cloak]{
            display: none;
        }
        ```

- 语法的执行流程
    1. 在vue解析之前，标签中有 `v-cloak` 属性
    2. 在html中应用css，在页面中不显示
    3. 在vue解析之后，`v-cloak` 属性被删除，显示数据绑定后的结果，**避免了刷新页面时，屏幕闪烁的问题**

- 一般开发时，不会直接使用 v-cloak，而是使用 **虚拟DOM**

- 示例
    - 参考代码
        - [src/base_cmd/insert_data/v-cloak.html](src/base_cmd/insert_data/v-cloak.html)
    - 代码内容
        ```html
        <head>
            <meta charset='UTF-8'>
            <title>v-cloak</title>
            <style>
                /* 设置不显示包含 v-cloak 的属性 */
                [v-cloak]{
                    display: none;
                }
            </style>
        </head>
        <body>
            <div id="app" v-cloak>
                {{msg}}
            </div>
            <script src='../js/vue.js'></script>
            <script>
                let app = new Vue({
                    el:"#app",
                    data:{
                        msg:'testmsg'
                    }
                });
            </script>
        </body>
        ```

## v-bind--绑定属性
### v-bind的基本用法
[top](#catalog)
- 功能
    - 用于动态绑定一个或多个属性值
    - 向某个组件的props值传递数据
        - 参考: [component.md--组件化开发--props的设置与传值方法](component.md#props的设置与传值方法)

- 使用方法
    - 基本写法
        ```html
        <div v-bind:属性名='变量名'></div>
        ```
    - 简化写法
        ```html
        <div :属性名='变量名'></div>
        ```

- 常用的需要绑定属性值的属性
    ```html
    <!-- 1. 图片的链接 -->
    <img v-bind:src='动态绑定值'>
    <!-- 2. 超链接地址 -->
    <a   v-bind:href='动态绑定值'></a>
    <!-- 3. 动态绑定一些类、样式 -->
    <div v-bind:class='动态绑定值'></div>
    ```

- 示例
    - 参考代码
        - [src/base_cmd/v-bind/v-bind_base.html](src/base_cmd/v-bind/v-bind_base.html)
    - 代码内容
        ```css
        .box01{
            height: 20px;
            width: 20px;
            background-color: #aaa;
        }
        .box02{
            height: 20px;
            width: 20px;
            background-color: #bfa;
        }
        ```
        ```html
        <div id="app">
            <!-- 1. 使用 v-bind 的基本语法 -->
            <!-- 1.1. 为 class 属性动态绑定属性值 -->
            <div v-bind:class='boxType'></div>
            <!-- 1.2. 为按钮绑定click事件，单击后切换 div 的 class -->
            <button @click='changeBoxType'>change div</button>

            <br><br>

            <!-- 2. 使用 v-bind 的简写语法 -->
            <a :href="dynLink.href">{{dynLink.content}}</a>
        </div>
        ```
        ```js
        let app = new Vue({
            el:"#app",
            data:{
                boxType:'box01',
                dynLink:{
                    href:'https://www.baidu.com/',
                    content:'baidu'
                }
            },
            methods:{
                changeBoxType: function(){
                    if (this.boxType === 'box01'){
                        this.boxType = 'box02'
                    } else {
                        this.boxType = 'box01'
                    }
                }
            }
        });
        ```

### 通过对象语法绑定class
[top](#catalog)
- 对象语法
    - 单独使用
        - 直接使用对象
            ```html
            <div :class='{key1: value1, key2: value2, .....}'></div>
            ```
        - 使用某个 methods。调用的方法需要返回一个对象，并且调用时需要加`()`
            ```html
            <div :class='method()'></div>
            ```
        - 使用 computed--计算属性，不需要添加括号
            ```html
            <div :class='计算属性名'></div>
            ```
    - 和普通 class 共存
        ```html
        <div class='otherClass' :class='{key1: value1, key2: value2, .....}'></div>
        ```

- 功能
    - 每个 `key` 是一个 `class` 名
    - 每个 `value` 是一个 `boolean` 型的值
    - 当 `value == true` 时，对应的 `key` 生效
    - 如果有多个 key 生效，则按照 css 的编码顺序加载

- 注意事项
    - 如果 key 中包含 `-` 字符，需要用双引号包起来，如:
        ```html
        <div :class='{"aaa--bbb": value1}'></div>
        ```

- 示例
    - 参考代码
        - [src/base_cmd/v-bind/bind_class_by_obj.html](src/base_cmd/v-bind/bind_class_by_obj.html)
    - 代码内容
        ```css
        .text__active{
            color:red;
            font-size: 20px;
        }
        .text__line{
            color:black;
            font-size: 15px;
        }
        .bcbox{
            background-color: #aaa;
        }
        ```
        ```html
        <div id="app">
            <!-- 通过 对象语法 来设置属性值，
                 value 为 true 的属性会生效
            -->

            <!-- 1. 直接使用对象 -->
            <div>1. 直接使用对象</div>
            <div :class='{text__line: isLine, text__active: isActive}'>test content</div>

            <!-- 2. 使用某个 method -->
            <div>2. 使用某个 method</div>
            <div :class='setClass()'>test content</div>

            <!-- 3. 和其他 class 共存 -->
            <div>3. 和其他 class 共存</div>
            <div class='bcbox' :class='{text__line: isLine, text__active: isActive}'>test content</div>

            <button @click='changeClass'>change class</button>
        </div>
        ```
        ```js
        let app = new Vue({
            el:"#app",
            data:{
                isActive: false,
                isLine: true,
            },
            methods:{
                changeClass: function(){
                    this.isActive = !this.isActive;
                    this.isLine = !this.isLine;
                },
                setClass: function(){
                    return {text__line: this.isLine, text__active: this.isActive};
                }
            }
        });
        ```

### 通过数组语法绑定class
[top](#catalog)
- 数组语法
    - 单独使用
        - 直接使用对象
            ```html
            <div :class='[c1, c2, c3,...]'></div>
            ```
        - 使用某个 methods。调用的方法需要返回一个数组，并且调用时需要加`()`
            ```html
            <div :class='method()'></div>
            ```
        - 使用 computed--计算属性，不需要添加括号
            ```html
            <div :class='计算属性名'></div>
            ```
    - 和普通 class 共存
        ```html
        <div class='otherClass' :class='[c1, c2, c3,...]'></div>
        ```
- 示例
    - 参考代码
        - [src/base_cmd/v-bind/bind_class_by_list.html](src/base_cmd/v-bind/bind_class_by_list.html)
    - 代码内容
        ```css
        .bcbox{
            background-color: #aaa;
        }
        .active{
            color: red;
        }
        .small_font{
            font-size: 10px;
        }
        .big_font{
            font-size: 20px;
        }
        ```
        ```html
        <div id="app">
            <!-- 通过 数组语法 来设置属性值 -->

            <!-- 1. 直接设置数组 -->
            <div> 1. 直接设置数组 </div>
            <div :class="['small_font', 'active']">test content</div>

            <!-- 2. 使用 methods 设置 -->
            <div :class='setClass()'>test content</div>

            <!-- 3. 和普通class共存 -->
            <div class='bcbox' :class="['small_font', 'active']">test content</div>
        </div>
        ```
        ```js
        let app = new Vue({
            el:"#app",
            methods:{
                changeClass: function(){
                    this.isActive = !this.isActive;
                    this.isLine = !this.isLine;
                },
                setClass: function(){
                    return ["big_font", 'bcbox']
                }
            }
        });
        ```

### 通过对象语法绑定style
[top](#catalog)
- 对象语法
    - key 表示: 样式名
        - 与js中使用样式名的方式类似，如果样式名中包含 `-`，需要将样式名转换为**驼峰命名**
    - value 表示: 样式值
    - 单独使用
        - 直接使用对象
            ```html
            <div v-bind:style='{key1: value1, key2: value2, .....}'></div>
            <div :style='{key1: value1, key2: value2, .....}'></div>
            ```
        - 使用某个 methods。调用的方法需要返回一个对象，并且调用时需要加`()`
            ```html
            <div :style='method()'></div>
            ```
        - 使用 computed--计算属性，不需要添加括号
            ```html
            <div :class='计算属性名'></div>
            ```
    - 和普通 class 共存
        ```html
        <div style='otherClass' :style='{key1: value1, key2: value2, .....}'></div>
        ```
- 示例
    - 参考代码
        - [src/base_cmd/v-bind/bind_style_by_obj.html](src/base_cmd/v-bind/bind_style_by_obj.html)
    - 代码内容
        ```html
        <div id="app">
            <!-- 直接设置需要加单引号 -->
            <div :style="{fontSize: '10px'}">{{msg}}</div>

            <!-- 使用变量 -->
            <div :style="{fontSize: finalSize}">{{msg}}</div>
            <!-- 使用方法 -->
            <div :style="getStyle()">{{msg}}</div>
            <!-- 与其他style共存 -->
            <div style='background-color: #aaa;' :style="getStyle()">{{msg}}</div>
        </div>
        <script src='../js/vue.js'></script>
        <script>
            let app = new Vue({
                el:'#app',
                data:{
                    msg:'testMsg',
                    finalSize: '10px',
                },
                methods:{
                    getStyle:function(){
                        return {fontSize: this.finalSize}
                    }
                }
            });
        </script>
        ```

### 通过数组语法绑定style
[top](#catalog)
- 数组语法
    - 单独使用
        - 直接使用对象
            ```html
            <div :style='[{样式名1: 样式值1}, {样式名2: 样式值2},...]'></div>
            ```
        - 使用某个 methods。调用的方法需要返回一个数组，并且调用时需要加`()`
            ```html
            <div :style='method()'></div>
            ```
        - 使用 computed--计算属性，不需要添加括号
            ```html
            <div :style='计算属性名'></div>
            ```
    - 和普通 class 共存
        ```html
        <div style='otherClass' :style='[{样式名1: 样式值1}, {样式名2: 样式值2},...]'></div>
        ```
- 示例
    - 参考代码
        - [src/base_cmd/v-bind/bind_style_by_list.html](src/base_cmd/v-bind/bind_style_by_list.html)
    - 代码内容
        ```html
        <div id="app">
            <!-- 直接设置需要加单引号 -->
            <div :style="[{fontSize: '10px'},{color: 'red'}]">{{msg}}</div>

            <!-- 使用变量 -->
            <div :style="[style1, style2]">{{msg}}</div>
            <!-- 使用方法 -->
            <div :style="getStyle()">{{msg}}</div>
            <!-- 与其他style共存 -->
            <div style='color:#47e' :style="getStyle()">{{msg}}</div>
        </div>
        <script src='../js/vue.js'></script>
        <script>
            let app = new Vue({
                el:'#app',
                data:{
                    msg:'testMsg',
                    style1: {finalSize: '15px'},
                    style2: {backgroundColor: '#aaa'},
                },
                methods:{
                    getStyle:function(){
                        return [this.style1, this.style2]
                    }
                }
            });
        </script>
        ```

### 值绑定--v-bind在表单项中的应用
[top](#catalog)
- 设置页面数据时，数据不是固定的，需要根据从服务器获取的数据来动态绑定
- 示例：设置 多选checkbox
    - 参考代码
        - [src/base_cmd/v-bind/v-bind_input.html](src/base_cmd/v-bind/v-bind_input.html)
    - html代码
        ```html
        <div id="app">
            <!-- 使用数据，动态绑定 id、value、for属性 -->
            <label v-for='item in typeList' :for='item'>
                <input type="checkbox" :id='item' :value='item' v-model='types'>{{item}}
            </label>
            <div>seletedType: {{types}}</div>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el:'#app',
            data:{
                // types用于双向绑定数据
                types:["type01"],
                // 通过该数据，来实现展示数据的动态绑定
                typeList:["type01", "type02", "type03", "type04"]
            }
        });
        ```


## v-on--事件监听
### v-on的基本用法
[top](#catalog)
- 作用
    - 为DOM元素的某个事件绑定 事件监听器/事件处理函数
- v-on 可以使用的值: `Function | Inline Statement | Object`

- 使用方法
    - Function，使用methods中的函数名
        1. 使用无参函数
            ```html
            <!-- 调用函数时，不需要加括号 -->
            <div v-on:事件名='<methods>中的函数名'></div>
            ```
        2. 使用带参数的函数
            ```html
            <!-- 1. 传递参数正常调用 -->
            <div v-on:事件名='<methods>中的函数名(参数)'></div>
            <!-- 2. 只添加了括号，没有传参，则参数默认为 undefined -->
            <div v-on:事件名='<methods>中的函数名()'></div>
            <!-- 3. 如果只写了函数名，则 Vue 会将 event 对象作为第一个参数传入方法 -->
            <div v-on:事件名='<methods>中的函数名'></div>
            ```
        3. **只使用** event 对象的函数
            ```html
            <!-- 直接通过函数名调用，Vue会自动将event对象做为参数传给方法 -->
            <div v-on:事件名='<methods>中的函数名'></div>
            ```
        4. 使用参数中包含**普通参数和event对象**的函数
            ```html
            <!-- 通过 $event，将event对象传入函数 -->
            <div v-on:事件名='<methods>中的函数名(其他参数, $event)'></div>
            ```
    - Inline Statement，内联语句
        ```html
        <div v-on:事件名='自定义语句'></div>
        ```
    - Object
        - Object类型很少使用

- v-on 的简写: `@`
    ```html
    <div @事件名='...'></div>
    ```

- 示例
    - 参考代码
        - [src/base_cmd/v-on/v-on_base.html](src/base_cmd/v-on/v-on_base.html)
    - html代码
        ```html
        <div id="app">
            <div>{{count}}</div>
            <!-- 1. Function -->
            <!-- 1.1 使用无参函数 -->
            <button v-on:click='add'>add1</button>
            <!-- 1.2 使用有参函数，并传递参数 -->
            <button v-on:click='addN(3)'>add + 3</button>
            <!-- 1.3 使用有参函数，但是没有传递参数
                参数的值默认为 undefined，0 + undefined = NaN
            -->
            <button v-on:click='addN()'>addN undefined</button>
            <!-- 1.4 只通过函数名使用有参函数 -->
            <button v-on:click='addN'>addN event</button>

            <!-- 2. 简写语法 -->
            <button @click='sub'>sub1</button>
            <br>
            <!-- 3. Inline Statement，内联语句 -->
            <button v-on:click='count=0'>clear</button>

            <br>
            <br>
            <!-- 1.5 使用参数中包含普通参数和event对象的函数 -->
            <button v-on:click='eventTest("clickTest", $event)'>eventTest</button>

        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el:'#app',
            data:{
                count:0,
            },
            methods:{
                add(){
                    this.count++;
                },
                sub(){
                    this.count--;
                },
                addN(n){
                    this.count += n;
                },
                eventTest(p, event){
                    console.log(`p=${p}，event=${event}`)
                }
            }
        });
        ```

### v-on的修饰符
[top](#catalog)
- 修饰符的作用
    - 在某些情况下，拿到event对象的目的是进行一些事件处理
    - Vue提供了修饰符来辅助开发者方便的处理一些事件

        |修饰符|作用|
        |-|-|
        |`.stop`|调用 event.stopPropagation()，可用于阻止事件的冒泡|
        |`.prevent`|调用 event.preventDefault()，用于阻止浏览器的默认事件|
        |`.[keyCode|keyAlias]`|当事件是由特定按键触发时，才触发回调|
        |`.native`|监听组件根元素的原生事件 ?????|
        |`.once`|只触发一次回调，类似于jquery的once|

- 示例
    - 参考代码
        - [src/base_cmd/v-on/v-on_modifier.html](src/base_cmd/v-on/v-on_modifier.html)
    - html代码
        ```html
        <div id="app">
            <section>1. .stop，取消事件冒泡</section>
            <div class='boxA' @click='divClick01'>
                <button @click.stop='btnClick01'>btn01</button>
            </div>
            <br>

            <section>2. .prevent，取消submit的默认提交行为</section>
            <form action="">
                <input type="submit" value="submit" @click.prevent='submitClick'>
            </form>

            <br>
            <section>3. .[keyCode|keyAlias] 监听 enter事件</section>
            <input type="text" @keyup.13='inputEnter'>

            <br>
            <br>
            <section>4. .once 只触发一次监听事件</section>
            <button @click.once='onceClick'>oncebtn</button>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el: '#app',
            methods:{
                divClick01(){
                    console.log('call divClick01');
                },
                btnClick01(){
                    console.log('call btnClick01');
                },
                submitClick(){
                    console.log('call submitClick');
                },
                inputEnter(event){
                    console.log(event.target.value);
                },
                onceClick(){
                    console.log('call onceClick');
                }
            }
        })
        ```

## 条件判断指令
### 条件判断指令的基本使用
[top](#catalog)
- 3种条件判断指令
    - v-if
    - v-else
    - v-else-if

- 作用
    - 根据变量或者表达式的结果，在DOM中渲染或销毁对象
    - 当变量或者表达式是false是，会将整个DOM元素从html中删除

- 使用方法
    1. 单独使用 `v-if`
        ```html
        <div v-if='boolean型变量'>...</div>
        <!-- 或者 -->
        <div v-if='表达式'>...</div>
        ```
    2. `v-else` 需要和 `v-if`一起使用
        ```html
        <div v-if='变量/表达式'>...</div>
        <!-- 当 v-if 条件不满足时，渲染 v-else 标识的元素 -->
        <div v-else>...</div>
        ```
    3. `v-else-if` 需要和 `v-if` 一起使用
        ```html
        <div v-if='条件1'>...</div>
        <div v-else-if='条件2'>...</div>
        <div v-else-if='条件3'>...</div>
        ...

        <!-- 如果没有 v-else，当所有条件都不满足时，不会渲染任何元素
             如果有   v-else，当所有条件都不满足时，渲染 v-else 标识的元素
          -->
        <div v-else>...</div>
        ```

- 一般不推荐直接在html中使用 `v-else-if`
    - 多个 `v-else-if` 会导致html代码混乱，逻辑不清晰
    - 如果逻辑判读过多，应该选择使用 **计算属性**，来遮盖负责的逻辑，使html代码更加清晰
- 示例
    - 参考代码
        - [src/base_cmd/v-if/v-if.html](src/base_cmd/v-if/v-if.html)
    - html代码
        ```html
        <div id="app">
            <div>{{count}}</div>
            <button @click='add'>add</button>
            <button @click='sub'>sub</button>
            <br>

            <!-- 1. 使用 v-if/ v-else， 通过 boolean型变量 控制元素是否显示 -->
            <div v-if='flg'>count > 5</div>
            <div v-else>count &lt;= 5</div>

            <br>
            <!-- v-if，v-else-if，v-else 的组合使用，根据count所处的范围，显示不同的等级 -->
            <div v-if='count>= 8'>A</div>
            <div v-else-if='count>= 6'>B</div>
            <div v-else-if='count>= 3'>C</div>
            <div v-else>D</div>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el: '#app',
            data:{
                count: 0,
                flg: false,
            },
            methods:{
                add(){
                    this.count++;
                    this.flg = this.count > 5;
                },
                sub(){
                    this.count--;
                    this.flg = this.count > 5;
                },
            }
        });
        ```

### 条件判断指令示例-登录切换
[top](#catalog)
- 需求
    - 点击 `login by name` 按钮，切换为用户名登录
    - 点击 `login by email` 按钮，切换为邮箱登陆
- <span style='color:red'>登录切换操作中会出现的问题</span>
    - 如果 input 输入框中已经输入了数据，再点击按钮切换时，输入框中的数据不会被清空
    - 问题的原因
        1. vue在将DOM渲染到浏览器之前，处于性能的考虑，会尽可能的复用已经存在的元素，而不是创建新的元素
        2. 在示例中，Vue内部发现原来的input元素不再使用，直接作为v-else中的input来使用了
    - **解决方法**
        - 在input中添加**不同的key**，来避免DOM元素的复用
        - vue底层在发现key不同时，不会复用元素

- 参考代码
    - [src/base_cmd/v-if/demo_login_switch.html](src/base_cmd/v-if/demo_login_switch.html)
- html代码
    ```html
    <div id="app">
        <!-- 根据 boolean型变量来控制登录模式 -->
        <span v-if='loginType'>
            <label for="username">username: </label>
            <!-- 设置 key='username'，来防止DOM元素复用 -->
            <input type="text" id="username" key='username'>
        </span>
        <span v-else>
            <label for="useremail">useremail: </label>
            <!-- 设置 key='useremail'，来防止DOM元素复用 -->
            <input type="email" id="useremail" key='useremail'>
        </span>

        <br>
        <!-- 通过按钮来切换 boolean型变量的值 -->
        <button @click='loginByName'>login by name</button>
        <button @click='loginByEmail'>login by email</button>
    </div>
    ```
- js代码
    ```js
    const app = new Vue({
        el:'#app',
        data:{
            loginType: 'true', // true=username, false=useremail
        },
        methods:{
            loginByName(){
                this.loginType=true;
            },
            loginByEmail(){
                this.loginType=false;
            }
        }
    })
    ```

## v-show--控制元素渲染
[top](#catalog)
- 作用
    - 根据变量或表达式结果，来控制元素是否渲染
    - 当变量或表达式为 false 时，会为元素添加行内样式: `style='display:none'`，来隐藏元素

- 使用方法
    ```html
    <div v-show='boolean变量/表达式'></div>
    ```

- `v-show` 与 `v-if` 的异同
    - 相同点
        - 都可以控制元素是否渲染
    - 不同点
        - 不渲染元素时，v-if会删除DOM元素，v-show会使用行内样式隐藏元素

- 开发中如何选择 `v-show` 和 `v-if`
    - 当DOM元素的显示与隐藏切换比较频繁时，使用 `v-show`
        - `v-show` 通过内联样式控制元素，性能比较好
        - 如果使用 `v-if`，会造成重复的DOM元素的添加与删除，性能不好
    - 当只有一次切换时，可以使用 `v-if`

- 示例
    - 参考代码
        - [src/base_cmd/v-show/v-show.html](src/base_cmd/v-show/v-show.html)
    - 代码内容
        ```html
        <div id="app">
            <div v-show='flg'>v-show test</div>
            <button @click='changFlg'>change flg</button>
        </div>
        <script type='text/javascript' src='../js/vue.js'></script>
        <script type='text/javascript'>
            const app = new Vue({
                el: '#app',
                data:{
                    flg: true
                },
                methods:{
                    changFlg(){ this.flg = !this.flg; }
                }
            })
        </script>
        ```

## v-for--遍历数组和对象
### v-for的基本使用方法
[top](#catalog)
- 作用
    - 遍历数组和对象
- 使用方法
    - 遍历数组
        ```html
        <!-- 1. 只使用 数组元素 -->
        <li v-for='item in 数组变量名' :key=item>{{item}}</li>

        <!-- 2. 使用 数组元素 和 索引 -->
        <li v-for='(item, index) in 数组变量名' :key=item>{{item}}{{index}}</li>

        <!-- item、index 两个变量可以任意命名，但是顺序是不变的，
             元素在前，索引在后
         -->
        ```
    - 遍历对象
        ```html
        <!-- 1. 遍历时，只使用 value  -->
        <li v-for='item in 对象变量名' :key=item>{{item}}</li>

        <!-- 2. 遍历时，同时使用 value 和 key -->
        <li v-for='(item, key) in 对象变量名' :key=item>{{key}} : {{item}}</li>

        <!-- 3. 遍历时，同时使用 value、key、索引
                索引表示当前属性是对象中的第几个属性
            -->
        <li v-for='(item, key, index) in 对象变量名' :key=item>{{index}}--{{key}} : {{item}}</li>

        <!-- item、key、index 三个变量可以任意命名，但是顺序是不变的，
             属性值、属性名、属性的索引
         -->
        ```

- 为什么要添加绑定`:key`属性?
    - 为了提高更新虚拟DOM的效率
    - 当在数组中加插入一个元素时，会执行Diff算法，将原始数据向后移动然后将新数据写入到对应位置的DOM元素中
    - 绑定key之后，Diff会准确的找到数据的插入点，创建DOM元素并插入，性能更高

- 示例
    - 参考代码
        - [src/base_cmd/v-for/v-for_base.html](src/base_cmd/v-for/v-for_base.html)

    - js代码
        ```js
        const app = new Vue({
            el:'#app',
            data:{
                testList:['aaa', 'bbb', 'ccc', 'ddd'],
                testObj:{
                    k1:'v1',
                    k3:'v3',
                    k4:'v4',
                    k2:'v2',
                }
            }
        });
        ```
    - html代码
        ```html
        <div id="app">
            <section>1. 遍历数组</section>
            <ul>
                <!-- 1.1. 遍历时，只使用 value  -->
                <li v-for='item in testList' :key=item>{{item}}</li>
                <!-- 输出:
                    aaa
                    bbb
                    ccc
                    ddd
                -->
            </ul>

            <ul>
                <!-- 1.2. 遍历时，同时使用 value 和 索引 -->
                <li v-for='(item, index) in testList' :key=item>{{index}}--{{item}}</li>
                <!-- 输出:
                    0--aaa
                    1--bbb
                    2--ccc
                    3--ddd
                -->
            </ul>

            <section>2. 遍历对象</section>
            <ul>
                <!-- 2.1. 遍历时，只使用 value  -->
                <li v-for='item in testObj' :key=item>{{item}}</li>
                <!-- 输出:
                    v1
                    v3
                    v4
                    v2
                -->
            </ul>
            <ul>
                <!-- 2.2. 遍历时，同时使用 value 和 key -->
                <li v-for='(item, key) in testObj' :key=item>{{key}} : {{item}}</li>
                <!-- 输出:
                    k1 : v1
                    k3 : v3
                    k4 : v4
                    k2 : v2
                -->
            </ul>
            <ul>
                <!-- 2.3. 遍历时，同时使用 value、key、索引
                    索引表示当前属性是对象中的第几个属性
                -->
                <li v-for='(item, key, index) in testObj' :key=item>{{index}}--{{key}} : {{item}}</li>
                <!-- 输出:
                    0--k1 : v1
                    1--k3 : v3
                    2--k4 : v4
                    3--k2 : v2
                -->
            </ul>
        </div>
        ```

### 可以进行响应式更新的数组方法
[top](#catalog)
- 可以进行响应式更新的数组方法（Vue内部会进行监听）
    - push()
    - pop()
    - shift()
    - unshift()
    - splice()
    - sort()
    - reverse()
- 直接通过索引修改数组元素，不会响应式更新
- 通过Vue提供的方法实现响应式修改数组元素
    ```js
    Vue.set(this.数组变量名, index, 修改后的元素值);
    ```

### v-for列表展示示例
[top](#catalog)
- 需求  
    - 将数组中的数据以列表的形式展示
    - 页面显示后，默认第一行为红色
    - 之后，被点击的项目为红色，其他项目为黑色

- 实现方法
    - 通过 `v-for` 来实现列表展示
    - 设置一个变量，表示当前被选中元素的索引: `selectedIndex`
    - 通过v-bind 的对象绑定方式设置样式
        - 通过 `selectedIndex == 当前元素的index` 的结果来判断是否设置样式
    - 为每个元素绑定click事件，每次点击时修改 `selectedIndex`，保证每次只有被点击的元素是红色的

- 参考代码
    - [src/base_cmd/v-for/demo_show_list.html](src/base_cmd/v-for/demo_show_list.html)
- 代码内容
    ```html
    <ul>
        <!-- 通过比较当前元素的index 和被选中元素的index 是否相等来设置元素样式 -->
        <li v-for='(item, index) in testList'
            :class="{active: selectedIndex === index}"
            @click='itemClicked(index)' >
            {{item}}
        </li>
    </ul>
    ```
    ```js
    const app = new Vue({
        el: '#app',
        data: {
            testList:['aaa', 'bbb', 'ccc', 'ddd'],
            selectedIndex: 0,
        },
        methods:{
            itemClicked(index){
                this.selectedIndex = index
            }
        }
    });
    ```

## v-model--双向绑定
### v-model的基本用法
[top](#catalog)
- 作用
    - 实现（表单）元素和数据的**双向绑定**
- 使用方法
    ```html
    <input type="..." v-model='变量名'>
    ```
- 示例
    - 参考代码
        - [src/base_cmd/v-model/v-model_base.html](src/base_cmd/v-model/v-model_base.html)
    - html代码
        ```html
        <div id="app">
            <!-- 双向绑定数据: msg -->
            <input type="text" name="msg" v-model='msg'>

            <!-- 点击按钮输出当前 msg 的数据 -->
            <button @click='printMsg'>print</button>
            <!-- 点击按钮初始化 msg 的数据，页面也会同步显示 -->
            <button @click='initMsg'>init</button>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el: '#app',
            data:{
                msg: 'initMsg',
            },
            methods:{
                printMsg(){ console.log(this.msg) },
                initMsg(){ this.msg='initMsg' },
            }
        });
        ```

### v-model的实现原理
[top](#catalog)
- v-model 指令可以转换为两个其他指令的结合
    1. `v-bind:value` 指令，数据绑定方向: `model --->>> view`
        ```html
        <input type="..."  v-bind:value='变量名'  ....>
        ```
    2. `v-on:input` 指令响应页面的输入，数据绑定方向: `view --->>> model`
        ```html
        <!-- 从 event 对象中获取页面修改后的值，并绑定到变量 -->
        <input type="..."  v-on:input='变量名 = $event.target.value'  ....>
        ```

- 示例
    - 参考代码
        - [src/base_cmd/v-model/v-model_principle.html](src/base_cmd/v-model/v-model_principle.html)
    - html代码
        ```html
        <div id="app">
            <!-- 只做: 将model数据绑定到view。
            修改文本框的数据后，再点击 init 按钮，页面不会将数据初始化，
            因为数据没有发生变化，所以不会重新绑定数据
            -->
            <!-- <input type="text" v-bind:value='msg'> -->


            <!-- 1. v-bind:value，将model数据绑定到view
                 2. v-on:input，将view中修改后的数据绑定到model
            -->
            <!-- 通过事件响应函数来实现绑定 -->
            <input type="text" v-bind:value='msg' v-on:input='inputHandle'>
            <br>
            <!-- 通过内联代码来实现绑定 -->
            <input type="text" v-bind:value='msg' v-on:input='msg = $event.target.value'>

            <!-- 点击按钮输出当前 msg 的数据 -->
            <button @click='printMsg'>print</button>
            <!-- 点击按钮初始化 msg 的数据，页面也会同步显示 -->
            <button @click='initMsg'>init</button>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el: '#app',
            data:{
                msg: 'initMsg',
            },
            methods:{
                printMsg(){ console.log(this.msg) },
                initMsg(){ this.msg='initMsg' },

                // 响应输入框的输入，并将 view 的数据 绑定到 model
                // 每次按下按键都会触发 input 事件，所以可以完成实时的双向数据绑定
                inputHandle(event){
                    console.log('-----');
                    this.msg = event.target.value;
                }
            }
        });
        ```

### v-model与radio结合使用
[top](#catalog)
- 与 单选按钮 radio 结合
    - 默认情况下需要将多个 radio 的 `name` 属性相同，才能成组
    - 使用 `v-model` 绑定**相同变量**后，多个按钮会自动成组
        - 为了在submit时，能够将数据发送到服务端，`name` 仍需要保留

- 示例
    - 参考代码
        - [src/base_cmd/v-model/v-model_radio.html](src/base_cmd/v-model/v-model_radio.html)
    - html代码
        ```html
        <div id="app">
            <input type="radio" name='type' v-model='type' value='0'>type0
            <input type="radio" name='type' v-model='type' value='1'>type1
            <input type="radio" name='type' v-model='type' value='2'>type2
            <input type="radio" name='type' v-model='type' value='3'>type3
            <br>
            <div>selectedType: {{type}}</div>
            <br>

            <input type="radio" name='sex' v-model='sex' value='male'>male
            <input type="radio" name='sex' v-model='sex' value='female'>female
            <br>
            <div>selectedSex: {{sex}}</div>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el: '#app',
            data:{
                type:0,
                sex:'',
            }
        });
        ```

### v-model与checkbox结合使用
[top](#catalog)
- 与checkbox结合
    - 单选框，需要绑定一个boolean型变量，不需要设置`value`属性
    - 多选框，需要绑定一个数组型变量，并设置`value`属性
- 示例
    - 参考代码
        - [src/base_cmd/v-model/v-model_chekbox.html](src/base_cmd/v-model/v-model_chekbox.html)
    - html代码
        ```html
        <div id="app">
            <!-- 1. 单选框，绑定一个boolean变量 -->
            <label for="agree">
                <input type="checkbox" name='agree' id="agree" v-model='isAgree'>agree
            </label>
            <br>
            <button :disabled='!isAgree'>next</button>

            <br>
            <br>
            <!-- 2. checkbox多选框，绑定一个数组-->
            <input type="checkbox" name='hobbies' value="hobby01" v-model='hobbies'>hobby01
            <input type="checkbox" name='hobbies' value="hobby02" v-model='hobbies'>hobby02
            <input type="checkbox" name='hobbies' value="hobby03" v-model='hobbies'>hobby03
            <div>selectedHobied: {{hobbies}}</div>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el:'#app',
            data:{
                isAgree: false,
                hobbies: ['hobby02'], // 初始值默认会被选中
            }
        });
        ```

### v-model与select结合使用
[top](#catalog)
- 与select结合
    - 当选中一个option时，vue会将对应的value绑定到变量
    - 单选select，需要绑定到一个基本类型变量
    - 多选select，绑定到一个数组类型变量

- 示例
    - 参考代码
        - [src/base_cmd/v-model/v-model_select.html](src/base_cmd/v-model/v-model_select.html)
    - html代码
        ```html
        <div id="app">
            <!-- 1. 单选，绑定到一个基本类型 -->
            <select name="p1" v-model='p1'>
                <option value="aaaa">aaaa</option>
                <option value="bbbb">bbbb</option>
                <option value="cccc">cccc</option>
                <option value="dddd">dddd</option>
            </select>
            <div>{{p1}}</div>

            <!-- 2. 多选，绑定到一个数组 -->
            <select multiple name="p2" v-model='p2'>
                <option value="aaaa">aaaa</option>
                <option value="bbbb">bbbb</option>
                <option value="cccc">cccc</option>
                <option value="dddd">dddd</option>
            </select>
            <div>{{p2}}</div>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el: '#app',
            data: {
                p1:'bbbb',
                p2:['cccc'],
            }
        });
        ```

### v-model--修饰符
[top](#catalog)
- 常用修饰符
    - lasy
        - 默认情况下，v-model会在input事件中同步输入框的数据，即数据的更新时实时的
        - lazy可以上数据在输入框**失去焦点或者按下回车后**才更新
    - number
        - 默认情况下，在输入框中输入的字母、数字都会被转换为字符串类型
            - 即使数据初始化为 number 型，在重新绑定后，也会变为 string 型
        - number可以将输入内容**自动转换成数字类型**
    - trim
        - 用于**去除输入内容首尾的空格**
        - trim 也具有 lasy 的性质
- 修饰符可以连续调用
    ```html
    <input type="text" name="msg" v-model.lazy.trim='msg'>
    ```
- 示例
    - 参考代码
        - [src/base_cmd/v-model/modifier.html](src/base_cmd/v-model/modifier.html)
    - html内容
        ```html
        <div id="app">
            <!-- 1. lazy 失去焦点或者按下回车后才更新数据 -->
            <input type="text" name="msg" id="msg" v-model.lazy='msg'>
            <div>{{msg}}</div>

            <!-- 2. 将输入内容自动转换成数字类型 -->
            <!-- 2.1 只有初始化时，是number型，数据更新后会变为string型 -->
            <input type="number" name="num1" id="num1" v-model='num1'>
            <div>{{ num1 + '----' +  (typeof num1) }}</div>
            <!-- 2.2 数据更新时，会自动变为string型 -->
            <input type="number" name="num2" id="num2" v-model.number='num2'>
            <div>{{ num2 + '----' +  (typeof num2) }}</div>

            <!-- 3. trim，用于去除输入内容首尾的空格 -->
            <input type="text" name="str" id="str" v-model.trim='str'>
            <button @click='showStr'>show str</button>
        </div>
        ```
    - js内容
        ```js
        const app = new Vue({
            el:"#app",
            data:{
                msg:'testmsg',
                num1:0,
                num2:1,
                str:'    dddd     ',
            },
            methods:{
                showStr(){
                    console.log(this.str)
                }
            }
        });
        ```
## Vue基本指令--综合示例--图书购物车
[top](#catalog)
- 需求
    - 每一行需要显示：当前购买数据的编号、书名、出版日期、价格、购买数量、删除操作按钮
    - 购买数量可以通过`+`、`-`按钮加减。但是每本图书的数量最少为1
    - 在购物车的列表下方显示书的总价
    - 图书价格 与 总价 保留两位小数
    - 点击删除按钮可以删除当前行
    - 当所有图书被删除后，不显示界面，只显示一行文字: 购物车为空
- 实现内容
    - 参考代码
        - [src/base_cmd/exercise/demo_shopping_cart.html](src/base_cmd/exercise/demo_shopping_cart.html)
    - html代码
        ```html
        <div id='shoppingCart'>
            <div v-show='!isShowCartTable'>empty shoppingCart</div>
            <div v-show='isShowCartTable'>

                <table class="cartTable" >
                    <!-- 表头 -->
                    <tr>
                        <th class='cartTable_cell cartTable_cell--small'></th>
                        <th class='cartTable_cell'>bookName</th>
                        <th class='cartTable_cell'>time</th>
                        <th class='cartTable_cell'>price</th>
                        <th class='cartTable_cell'>count</th>
                        <th class='cartTable_cell'>action</th>
                    </tr>
                    <!-- 循环显示 每个图书的数据 -->
                    <tr v-for='(item, index) in bookDetails'>
                        <td class="cartTable_cell cartTable_cell--small cartTable_cell--center">{{index + 1}}</td>
                        <td class="cartTable_cell cartTable_cell--center">{{item.bookName}}</td>
                        <td class="cartTable_cell cartTable_cell--center">{{item.time}}</td>
                        <td class="cartTable_cell">{{item.price | priceFormat}}</td>
                        <td class="cartTable_cell cartTable_cell--center">
                            <!-- 绑定disabled属性，当数量为1时，禁止使用 `-` 按钮 -->
                            <button :disabled="item.count <= 1" @click='subBookCount(index)'>-</button>
                            {{item.count}}
                            <button @click='addBookCount(index)'>+</button>
                        </td>
                        <td class="cartTable_cell cartTable_cell--center">
                            <button @click='deleteBook(index)'>delete</button>
                        </td>
                    </tr>
                </table>
                <div>totalPrice: {{totalPrice | priceFormat}}</div>
            </div>
        </div>
        ```
    - js代码
        ```js
        const app = new Vue({
            el:'#shoppingCart',
            data:{
                bookDetails:[
                    { bookName:'aaa', time:'aaat', price:12.50, count:1 },
                    { bookName:'bbb', time:'bbbt', price:45.60, count:1 },
                    { bookName:'ccc', time:'ccct', price:23.00, count:1 },
                ],
            },
            methods:{
                // 从数组中删除指定的index上的图书数据
                deleteBook(index){
                    this.bookDetails.splice(index, 1);
                },
                // 图书数量 + 按钮事件
                addBookCount(index){
                    this.bookDetails[index].count++;
                },
                // 图书数量 - 按钮事件
                subBookCount(index){
                    let book = this.bookDetails[index]
                    if (book.count > 1 ){
                        book.count--;
                    }
                    // this.bookDetails[index].count--;
                },
            },
            computed:{
                // 如果图书全部删除，则不显示购物车；有图书时才显示
                isShowCartTable(){
                    return this.bookDetails.length != 0;
                },

                // 显示总价
                totalPrice(){
                    if (this.bookDetails.length != 0){
                        return this.bookDetails.map(item => item.price*item.count)
                                        .reduce( (prev, cur)=> prev + cur);
                    } else {
                        return 0;
                    }
                }
            },
            filters:{
                // 设置金额的显示方式
                priceFormat(price){
                    return '￥' + price.toFixed(2);
                }
            }
        });
        ```

# Vue-options中的选项
## 常用options选项
[top](#catalog)
- el
    - 类型: `String | HTMLElement`
    - 作用: 将DOM对象挂载到一个Vue实例上，有Vue对象管理DOM对象
- data
    - 类型: `Object | Function`
    - 作用: Vue实例对应的数据对象
    - 在<span style="color:red">组件化开发</span>时，data<span style="color:red">必须是</span>一个<span style="color:red">函数</span>
- methods
    - 类型: `{[key String]: Function}`
        - Function如果使用**箭头函数**，需要考虑**是否有 this 以及 this 的作用域**
    - 定义属于Vue对象的一些方法，可以在其他地方调用，也可以在指令中使用
- computed
    - 类型: `{[key String]: Function}`
    - 作用: 计算属性
- filters
    - 类型: `{[key String]: Function}`
    - 作用: 数据转换
- components
    - 类型: `{[key String]: 组件构造器}`
    - 作用: 注册只能在当前Vue实例对象内部使用的**局部组件**
    - 参考: [component.md--组件化开发--全局组件和局部组件](component.md#全局组件和局部组件)

## computed--计算属性
### 计算属性的简介
[top](#catalog)
- 什么是计算属性
    - 属性式调用
        - 以属性的方式调用方法，并在页面中显示方法的运算结果
    - 属性式命名
        - 计算属性本质仍然是**函数**，但是使用时是以属性的方式使用的。所以命名时需要注意使用名词命名
    - 缓存机制
        - 计算属性的结果会被保存到缓存中，在多次使用计算属性时，只会计算一次
        - 避免了使用插值语法，或方法时的重复计算
    - 简化html代码
        - 通过计算属性可以遮盖结果的运算逻辑，使 html 代码更加清晰
        - **避免插值操作中的复杂操作**
            - 当插值操作比较复杂时，html代码会很混乱，如: `{{firstName}}{{lastName}}`
            - 通过计算属性，可以使 html 代码更加直观

- 计算属性的定义
    - 完整定义: getter、setter定义
        - 定义的方式与 `Object.defineProperties` 方法类似
        - <span style='color:red'>一般不会实现 setter 方法，以防止从外部修改计算属性的结果</span>
        - 没有实现 setter 方法的属性一般被称为 **只读属性**
            ```js
            let app = new Vue({
                el:'#app',
                computed:{
                    属性名: {
                        get: function(){...},

                        // 一般不会实现 setter 方法，以防止从外部修改计算属性的结果
                        set: function(data){...}
                    }
                }
            });
            ```
    - 简写定义
        - 每一个定义计算都是一个函数
        - 简写定义的本质就是: 只定义了 getter 方法的**只读属性**
            ```js
            let app = new Vue({
                el:'#app',
                computed:{
                    属性名: function(){...},

                    // 等同与
                    // 属性名: {
                    //     get: function(){}
                    // }
                }
            });
            ```

- 计算属性的使用
    - 直接通过属性名调用即可，<span style='color:red'>属性名后面不需要加 `()` </span>
        ```html
        <div>{{计算属性名}}</div>
        ```

### 简写计算属性的使用示例
[top](#catalog)
- 基本使用
    - 参考代码
        - [src/base_cmd/computed/computed_base.html](src/base_cmd/computed/computed_base.html)

    - 代码内容
        ```html
        <div id="app">
            <!-- 1. 同时输出两个数据 -->
            <!-- 1.1 方法1: 使用两次 {{}} 语法 -->
            <div>{{msg01}}--{{msg02}}</div>

            <!-- 1.2 方法2: 在 {{}} 内部，使用 + 操作符来操作两个变量 -->
            <div>{{msg01 + '--' + msg02}}</div>

            <!-- 1.3 方法3: 在 {{}} 内部，调用注册的函数。
                函数调用时，函数名后面必须要加 () -->
            <div>{{getMsg()}}</div>

            <!-- 1.4 方法4: 在 {{}} 内部，使用计算属性 -->
            <div>{{fullMsg}}</div>

        </div>
        ```
        ```js
        let app = new Vue({
            el:'#app',
            data:{
                msg01:'start',
                msg02:'end'
            },
            methods:{
                getMsg: function(){
                    return this.msg01 + '--' + this.msg02;
                }
            },
            computed:{
                fullMsg: function(){
                    return this.msg01 + '--' + this.msg02;
                }
            }
        });
        ```

- 一个**必须使用计算属性的场景**
    - 数据是数组类型的，需要显示数值的合计
    - 参考代码
        - [src/base_cmd/computed/must_use_computed.html](src/base_cmd/computed/must_use_computed.html)
    - 代码内容
        ```html
        <div id="app">
            <!-- 通过计算属性获取书的总价 -->
            <div>totalPrice of books: {{booksTotalPrice}}</div>
        </div>
        ```
        ```js
        let app = new Vue({
            el:'#app',
            data:{
                books:[
                    {id:'0001', name:'book01', price:100},
                    {id:'0002', name:'book02', price:200},
                    {id:'0003', name:'book03', price:300},
                    {id:'0004', name:'book04', price:400},
                    {id:'0005', name:'book05', price:500},
                ]
            },
            computed:{
                booksTotalPrice: function(){
                    return this.books.map((item)=>item.price).reduce((prev, curr)=>prev + curr);
                }
            }
        });
        ```

### 计算属性getter和setter的示例
[top](#catalog)
- 参考代码
    - [src/base_cmd/computed/getter_setter.html](src/base_cmd/computed/getter_setter.html)

- 代码内容
    - html内容
        ```html
        <div id="app">
            <div>{{fullMsg}}</div>

            <div>{{fullName}}</div>

            <!-- 在此输入 newName -->
            newName: <input type="text" id="newName" placeholder="please write new name">
            <!-- 点击按钮后，从文本框 newName 中获取数据，并重新设置 fullName-->
            <button @click='changeName'>changeName</button>
        </div>
        ```
    - js内容
        ```js
        let app = new Vue({
            el:'#app',
            data:{
                msg1:'start',
                msg2:'end',

                firstName:'aaa',
                endName:'bbb'
            },
            computed:{
                // 1. 只包含 getter
                fullMsg:{
                    get: function(){
                        return this.msg1 + this.msg2;
                    },
                    // set: function(data){...}
                },

                // 2. 同时设置 getter、setter
                fullName:{
                    get: function(){
                        return this.firstName + ' ' + this.endName;
                    },
                    set: function(newName){
                        [this.firstName, this.endName] = newName.split(' ');
                    }
                }
            },
            methods:{
                // 重新设置 fullName
                changeName: function(){
                    this.fullName = document.querySelector('#newName').value;
                }
            }
        });
        ```

### 计算属性的缓存
[top](#catalog)
- 缓存机制
    - 计算属性在计算过一次之后，会将结果保存到缓存中
    - 如果通过方法来获取计算结果，每次调用都会重新计算，会浪费性能
- 重新计算
    - 当计算属性中使用的某个变量发生变化时，计算属性会重新计算
- 示例
    - 参考代码
        - [src/base_cmd/computed/computed_cache.html](src/base_cmd/computed/computed_cache.html)

    - html代码
        ```html
        <div id="app">
            <!-- 1. 第一次执行计算，在控制台输出: `call fullMsg` -->
            <div>{{fullMsg}}</div>

            <!-- 2. 之后的多次计算使用缓存，控制台不会有任何输出 -->
            <div>{{fullMsg}}</div>
            <div>{{fullMsg}}</div>
            <div>{{fullMsg}}</div>

            <!-- 3. 输入新的msg1，并点击按钮，会修改 msg1 的值。
                因为计算属性中使用了 this.msg1，所以会重新计算，
                并在控制台输出一次: `call fullMsg`。

                通过方法获取结果时，也会重新计算，因为没有缓存，
                方法会调用多次，在控制台会输出多次: `call getFullMsg`
            -->
            <input type="text" id='newMsg1'>
            <button @click='changeMsg1'>change msg1</button>

            <div>---------------------------------------</div>
            <!-- 4. 通过方法获取结果。方法没有缓存，每次都会重新计算
                在控制台会输出多次: `call getFullMsg`
            -->
            <div>{{getFullMsg()}}</div>
            <div>{{getFullMsg()}}</div>
            <div>{{getFullMsg()}}</div>
            <div>{{getFullMsg()}}</div>
        </div>
        ```
    - js代码
        ```js
        let app = new Vue({
            el: '#app',
            data:{
                msg1: 'start',
                msg2: 'end'
            },
            computed:{
                fullMsg: function(){
                    // 多次调用时只会打印一次
                    console.log('call fullMsg');
                    // 计算属性中使用了 data中的数据，当数据发生变化时，会重新计算
                    return this.msg1 + ' ' + this.msg2;
                }
            },
            methods:{
                // 修改 data 中的数据，来触发计算属性的重新计算
                changeMsg1: function(){
                    this.msg1 = document.querySelector('#newMsg1').value;
                },
                getFullMsg: function(){
                    console.log('call getFullMsg');
                    return this.msg1 + '---' + this.msg2;
                }
            }
        });
        ```

## filters--过滤器
[top](#catalog)
- filters 的作用，格式化数据
- filters的定义
    ```js
    const app = new Vue({
        el:'#app',
        filters:{
            // 过滤器可以有一个参数，也可以有多个参数
            过滤器名: function(参数){
                // 执行参数变换
                return 变换结果;
            },
            过滤器名: function(参数1, 参数2, 参数3, ....){
                // 执行参数变换
                return 变换结果;
            }
    });
    ```
- filters的使用方法
    - 参数 与 过滤器之间 通过 `|` 分割
    - 过滤器包含多个参数时，参数使用 逗号 分割
    ```html
    <!-- 一个参数的过滤器 -->
    <div>{{data | 过滤器名}}</div>

    <!-- 多个参数的过滤器 -->
    <div>{{data1, data2, data3,..... | 过滤器名}}</div>
    ```

- 示例
    - 参考代码
        - [src/base_cmd/filter/filter_base.html](src/base_cmd/filter/filter_base.html)
    - js代码
        ```js
        const app = new Vue({
            el:'#app',
            data:{
                testName: 'testName',
                testPrice: 1234,
            },
            filters:{
                // 一个参数的过滤器
                nameFormatter(name){
                    return 'Mr.'+name;
                },

                // 多个参数的过滤器
                priceFormatter(price, decimalSize){
                    return '￥' + price.toFixed(decimalSize);
                }
            }
        });
        ```
    - html代码
        ```html
        <div id="app">
            <!-- 输出: Mr.testName -->
            name: <p>{{testName | nameFormatter}}</p>
            <!-- 输出: ￥1234.00 -->
            price: <p>{{testPrice, 2 | priceFormatter}}</p>
        </div>
        ```
