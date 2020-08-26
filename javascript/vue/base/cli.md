<span id="catalog"></span>

### 目录---从webpaack到vue-cli
- [在webpack中使用Vue](#在webpack中使用Vue)
    - [Vue的webpack配置](#Vue的webpack配置)
    - [Vue构建的一些知识](#Vue构建的一些知识)
    - [webpack打包Vue后的错误](#webpack打包Vue后的错误)
    - [app代码到template的转换](#app代码到template的转换)
        - [开发与转换的基本规则](#开发与转换的基本规则)
        - [app代码到template的演进过程](#app代码到template的演进过程)
- [vue-cli](#vue-cli)
    - [vue-cli简介](#vue-cli简介)
    - [vue-cli的安装](#vue-cli的安装)
    - [vue-cli2](#vue-cli2)
        - [vue-cli2的有效化](#vue-cli2的有效化)
        - [vue-cli2创建项目与结果分析](#vue-cli2创建项目与结果分析)
        - [vue-cli2项目示例](#vue-cli2项目示例)
    - [RuntimeOnly与RuntimeCompiler的比较](#RuntimeOnly与RuntimeCompiler的比较)
    - [render函数](#render函数)
    - [vue-cli3](#vue-cli3)
        - [cli3与cli2的区别](#cli3与cli2的区别)
        - [vue-cli3创建项目与结果分析](#vue-cli3创建项目与结果分析)
        - [vue-cli3的配置](#vue-cli3的配置)


# 在webpack中使用Vue
## Vue的webpack配置
[top](#catalog)
- 需要下载loader
    ```sh
    npm i vue-loader -D
    npm i vue-style-loader -D
    npm i vue-template-compiler -D
    ```

- 添加基本配置
    ```js
    const VueLoader = require('vue-loader');
    module.exports = {
        // 其他配置
        // ...

        // 配置loader
        module: {
            rules: [
                {
                    test: /\.vue$/,
                    loader: 'vue-loader',
                },
            ]
        },

        // 添加插件
        plugins: [
            new VueLoader.VueLoaderPlugin()
        ],

        resolve: {
            alias: {
                // 指定使用 `import Vue from 'vue'` 时，引入哪一个版本
                vue$: 'vue/dist/vue.esm.js',    // 引入 runtime-compiler 版本
            }
        }
    }
    ```

## Vue构建的一些知识
[top](#catalog)
- Vue在构建最终的发布版本时，会构建两类版本
    - `runtime-only`
        - 该版本下，不能有任何的 `template`
        - 这个版本表示: **只有运行时内容，对于如何编译 `template` 内容，是忽略的**
    - `runtime-compiler`
        - 该版本下，可以有 `template`
            - 因为有 `complier`模块，可以编译 `template`

- Vue编译时，被视为 `template` 的内容
    - `<template>` 标签中的内容
    - 对于普通的Vue实例对象，`el`属性挂载的目标，也算作 `template`
        - 如:
            ```html
            <div id='app'>
                ...
            </div>
            ```

## webpack打包Vue后的错误
[top](#catalog)
- 默认情况下，不做特殊配置，直接使用Vue，两个模式都会产生异常。在开发模式下会有如下的错误
    ```
    [Vue warn]: You are using the runtime-only build of Vue where the template compiler is not available. Either pre-compile the templates into render functions, or use the compiler-included build.

    (found in <Root>)
    ```
- 该异常表示正在使用 `runtime-only` 版本的代码，无法编译 `template`
- 解决方法
    - 在 `webpack.config.js` 中，添加配置。生成模式、开发模式都有效
        ```js
        resolve: {
            alias: {
                vue$: 'vue/dist/vue.esm.js',
            }
        }
        ```
    - 配置的目的: **指定使用 `import Vue from 'vue'` 时，引入node_models/vue包下的哪一个版本**


## app代码到template的转换
### 开发与转换的基本规则
[top](#catalog)
1. 开发过程中，`#app` 部分的html代码不能随意修改，基本上只保留标签部分
    ```html
    <div id="app"></div>
    ```
2. Vue实例绑定 `#app` 后，添加 `template` 属性
    - 需要显示的内容全部写在 `template` 属性中
    - 发生代码修改时，也只修改 `template` 中的内容

3. `template` 中的内容如何显示？
    - 编译时，`template` 的内容会被拷贝到 `#app` 中

### app代码到template的演进过程
[top](#catalog)
- 演进过程
    1. 基本写法，所有内容写在html中，Vue实例负责提供数据和方法
    2. 从 `#app` 中抽取代码，保存到Vue实例的 `template` 属性中
    3. app组件化
        - 组件化方法
            1. 从Vue实例中，将 `template` 代码、数据、方法等抽取到一个组件中
            2. 在Vue实例中注册组件
            3. 在Vue实例的 `template` 中，使用组件
            4. 编译后，Vue实例 `template` 属性中的组件html会被拷贝到 `#app` 中
    4. app组件代码从`index.js`中剥离，形成模块
        - 将组件化的代码从 `index.js` 中抽取出来，单独做成一个模块，并将组件对象导出
        - 组件代码剥离后，再发生代码修改，只会修改模块，不会涉及到`index.js`
    5. **将应用模块改造成 `*.vue` 文件，进行组件化开发**
        - 将组件使用的html、js代码、css代码，分别细化到`*.vue` 文件的不同标签中
        - 将子组件也写入vue文件中，并且可以在主模块中引用

- 示例
    1. 基本写法，所有内容写在html中，Vue实例负责提供数据和方法
        - 参考代码
            - [src/vue-in-webpack/app-to-template/src/js/index_bk01.js](src/vue-in-webpack/app-to-template/src/js/index_bk01.js)

    2. 从 `#app` 中抽取代码，保存到Vue实例的 `template` 属性中
        - 参考代码
            - [src/vue-in-webpack/app-to-template/src/js/index_bk02.js](src/vue-in-webpack/app-to-template/src/js/index_bk02.js)
        - 代码内容
            ```js
            // 2. 从 #app 中抽取代码，保存到Vue实例的 template属性中
            new Vue({
                el: '#app',
                template:`
                <div>
                    msg: <p>{{msg}}</p>
                    <button @click='btnClick'>btn</button>
                </div>
                `,
                data: { msg: 'webpack test msg' },
                methods:{
                    btnClick(){
                        console.log('btn click');
                    }
                }
            });
            ```
    3. app组件化
        - 参考代码
            - [src/vue-in-webpack/app-to-template/src/js/index_bk03.js](src/vue-in-webpack/app-to-template/src/js/index_bk03.js)
        - 代码内容
            ```js
            // 1. 从Vue实例中，将 `template` 代码、数据、方法等抽取到一个组件中
            const App = {
                template:`
                <div>
                    msg: <p>{{msg}}</p>
                    <button @click='btnClick'>btn</button>
                </div>
                `,
                data(){
                    return { msg: 'webpack test msg' }
                },
                methods:{
                    btnClick(){
                        console.log('btn click')
                    }
                }
            }

            new Vue({
                el: '#app',
                // 2. 在Vue实例中注册组件
                components:{ App},

                // 3. 在Vue实例的 `template` 中，使用组件
                template: `<App></App>`
            });
            ```

    4. app组件代码从`index.js`中剥离，形成模块
        - 参考代码
            - [src/vue-in-webpack/app-to-template/src/js/app/app.js](src/vue-in-webpack/app-to-template/src/js/app/app.js)
            - [src/vue-in-webpack/app-to-template/src/js/index_bk04.js](src/vue-in-webpack/app-to-template/src/js/index_bk04.js)
        - 组件模块 `app.js`
            ```js
            // 将组件从 index.js 中剥离
            export default {
                template:`
                <div>
                    msg: <p>{{msg}}</p>
                    <button @click='btnClick'>btn</button>
                </div>
                `,
                data(){
                    return { msg: 'webpack test msg' }
                },
                methods:{
                    btnClick(){
                        console.log('btn click');
                    }
                }
            }
            ```
        - Vue实例 `index.js`
            ```js
            import Vue from 'vue';
            import App from './app/app'; // 引入组件模块

            new Vue({
                el: '#app',
                // 2. 在Vue实例中注册组件
                components:{ App },

                // 3. 在Vue实例的 `template` 中，使用组件
                template: `<App></App>`
            });
            ```
    5. **将应用模块改造成 `*.vue` 文件，进行组件化开发**
        - 参考代码
            - [src/vue-in-webpack/app-to-template/src/js/vue/App.vue](src/vue-in-webpack/app-to-template/src/js/vue/App.vue)
            - [src/vue-in-webpack/app-to-template/src/js/vue/Cpn.vue](src/vue-in-webpack/app-to-template/src/js/vue/Cpn.vue)
            - [src/vue-in-webpack/app-to-template/src/js/index.js](src/vue-in-webpack/app-to-template/src/js/index.js)
        - 子组件 `Cpn.vue`
            ```html
            <template>
                <div>
                    <p>this is Cpn</p>
                    <p>{{data}}</p>
                </div>
            </template>

            <script>
            export default {
                name:'Cpn',
                data(){
                    return { data:'test data' };
                }
            }
            </script>
            ```
        - App组件 `App.vue`
            ```html
            <!-- 将模版、js、样式分离 -->
            <template>
                <div class="msgbox">
                    msg: <p>{{msg}}</p>
                    <button @click='btnClick'>btn</button>

                    <!-- 使用子组件 -->
                    <child/>
                </div>
            </template>

            <script>
            // 引入其他的组件
            import Cpn from './Cpn.vue'
            export default {
                name:'App',
                data(){
                    return { msg: 'webpack test msg' }
                },
                methods:{
                    btnClick(){
                        console.log('btn click')
                    }
                },
                // 注册子组件
                components:{ child:Cpn }
            }
            </script>

            <style>
            .msgbox{
                color:#47e;
            }
            </style>
            ```
        - Vue实例 `index.js`
            ```js
            import Vue from 'vue';
            import App from './vue/App.vue'; // 引入vue模块

            new Vue({
                el: '#app',
                // 2. 在Vue实例中注册组件
                components:{ App },

                // 3. 在Vue实例的 `template` 中，使用组件
                template: `<App></App>`
            });
            ```

# vue-cli
## vue-cli简介
[top](#catalog)
- 什么是 vue-cli
    - CLI: Commond-Line Interface，命令行界面，俗称脚手架
    - vue-cli 是官方发布的 vue.js 项目脚手架
    - 通过 vue-cli 可以**快速搭建 Vue 开发环境**以及对应的**webpack配置**
- 如果只是用Vue写一些简单的Demo程序，可以不使用 vue-cli
- 开发大型项目时，必须要使用 vue-cli
    - 规范代码的开发结构
    - 避免做重复的配置工作

## vue-cli的安装
[top](#catalog)
- npm 8.9 以上
- 需要webpack
- 安装 vue-cli
    ```sh
    npm install -g @vue/cli

    # 检查版本
    vue -V
    ```

## vue-cli2
### vue-cli2的有效化
[top](#catalog)
- 在 vue-cli >= 3 的版本中，2.x被覆盖了，不能直接以 vue-cli2 的方式初始化项目
- **vue-cli2的有效化**
    - 如果使用旧版本的 `vue init` 功能，需要安装桥接工具
    - 相当于**拉取 2.x 的模板**
        ```sh
        npm install -g @vue/cli-init
        ```

### vue-cli2创建项目与结果分析
[top](#catalog)
- 2.x 创建项目的指令
    ```sh
    vue init webpack <project_name>
    # webpack可以换成别的工具，但是基本上都会使用webpack
    ```

- 通过指令创建项目时，会自动添加 `vue` 依赖到运行时依赖

- 创建项目时的选项分析
    - `? Project name (cli2-demo01)` 项目名 并根据这个名字创建文件夹，默认使用指令中的project_name
    - `? Project description (A Vue.js project)` 项目描述
    - `? Author` 作者
    - `? Vue build (Use arrow keys)`
        - 设置用哪一个来构建项目 Runtime + Compiler，或者 Runtime-only
        - 可选内容
            ```
            > Runtime + Compiler: recommended for most users
            Runtime-only: about 6KB lighter min+gzip, but templates (or any Vue-specific HTML) are ONLY allowed in .vue files - ren
            der functions are required elsewhere
            ```
    - `? Install vue-router? (Y/n)`, 是否要安装路由
    - `? Use ESLint to lint your code?`，是否安装eslint
        - 可选内容: 语法检查的规范
            ```
            ? Pick an ESLint preset (Use arrow keys)
            > Standard (https://github.com/standard/standard)   <<<<< eslint的标准规范
            Airbnb (https://github.com/airbnb/javascript)       <<<<< airbnb规范
            none (configure it yourself)                        <<<<< 自定义规范
            ```
    - `? Set up unit tests (Y/n)`，是否需要单元测试
    - `? Setup e2e tests with Nightwatch? (Y/n)`，是否需要 端到端测试
    - `? Should we run `npm install` for you after the project has been created? (recommended) (Use arrow keys)`
        - 确认使用哪种工具来管理: npm 或 yarn
        - 可选内容
            ```
            > Yes, use NPM
            Yes, use Yarn
            No, I will handle that myself
            ```

- 创建结果分析

    |目录/文件|功能|
    |--:|:--|
    |`build/`|webpack在dev、production下的配置内容|
    |`config/`|webpack打包时使用的一些参数|
    |`node_modules/`||
    |`src/`|开发代码|
    |`static/`|<ul> <li>静态资源，打包后会完整的拷贝到dist目录下</li> <li>目录下的内容不会被优化成其他格式</li> <li>目录下包含一个 `.gitkeep` 文件，保证无论目录是否为空，都会上传到git</li> </ul>|
    |`.babelrc`|babel转换的预设|
    |`.editorconfig`|开发规范设置|
    |`.eslintignore`|语法检查忽略配置|
    |`.eslintrc.js`|语法检查配置|
    |`.gitignore`||
    |`.postcssrc.js`|css兼容性处理配置|
    |`index.html`|根模板|
    |`package-lock.json`||
    |`package.json`||
    |`README.md`||

- 关闭eslint
    - 修改 `config/index.js` 文件
        - `dev.useEslint: false`

### vue-cli2项目示例
[top](#catalog)
- 创建指令
    ```sh
    vue init webpack cli2-demo01
    ```
- 创建选项
    ```sh
    ? Project name cli2-demo01
    ? Project description vue-cli2 test demo01
    ? Author ljs
    ? Vue build standalone
    ? Install vue-router? No
    ? Use ESLint to lint your code? Yes
    ? Pick an ESLint preset Airbnb
    ? Set up unit tests No
    ? Setup e2e tests with Nightwatch? No
    ? Should we run `npm install` for you after the project has been created? (recommended) npm
    ```
- 参考代码
    - [src/cli-usage/cli2/cli2-demo01](src/cli-usage/cli2/cli2-demo01)

- 项目打包/启动
    - 参考:
        - npm的启动指令
            - [src/cli-usage/cli2/cli2-demo01/README.md](src/cli-usage/cli2/cli2-demo01/README.md)
        - 实际的指令
            - [src/cli-usage/cli2/cli2-demo01/package.json](src/cli-usage/cli2/cli2-demo01/package.json)
            - 参考 `scripts` 属性中添加的指令

    - 启动开发模式服务器
        ```sh
        npm run dev
        ```
    - 生产模式打包
        ```sh
        npm run build
        ```

## RuntimeOnly与RuntimeCompiler的比较
[top](#catalog)
- 参考示例
    - Runtime-Only
        - [src/cli-usage/cli2/runtime-only-demo01](src/cli-usage/cli2/runtime-only-demo01)
    - Runtime-Compiler
        - [src/cli-usage/cli2/cli2-demo01](src/cli-usage/cli2/cli2-demo01)

- Runtime-Only 与 Runtime-Compiler 的区别
    - 本质区别
        - Runtime-Compiler，可以编译 template，并且会在运行时执行编译
        - Runtime-Only，无法编译 template，只有运行已经编译好的内容
    - `src/main.js` 代码上的不同
        - Runtime-Compiler
            ```js
            import Vue from 'vue';
            import App from './App';

            Vue.config.productionTip = false;

            /* eslint-disable no-new */
            new Vue({
              el: '#app',
              components: { App },
              template: '<App/>',
            });
            ```
        - Runtime-Only
            ```js
            import Vue from 'vue';
            import App from './App';

            Vue.config.productionTip = false;

            /* eslint-disable no-new */
            new Vue({
              el: '#app',
              render: h => h(App),  // <<<<<<<<<< RuntimeOnly 使用Render函数替代了template和components属性
            });
            ```
    - Vue运行 `src/main.js` 的过程不同，**Runtime-Only 需要执行的流程更少**
        - Runtime-Compiler
            1. (将template绑定到一个组件或实例后) <span style='color:red'>template被保存到Vue实例的options中</span>
            2. <span style='color:red'>解析template，并创建抽象语法树</span>
            3. 执行render函数
            4. 创建虚拟DOM树
            5. 虚拟DOM被渲染成真实的DOM
        - Runtime-Only
            1. 执行render函数
            2. 创建虚拟DOM树
            3. 虚拟DOM被渲染成真实的DOM
    - 两种模式在代码量上的不同
        - `Runtime-Only代码量 < Runtime-Compiler代码量`
        - Runtime-Only 大约少 6kb--10kb
        - Runtime-Compiler 多了什么代码？
            - 多了template的设置与解析代码
            - 即 `node_modules/vue/src/compiler` 模块

- Runtime-Only 的优点
    - 编译结果比 Runtime-Compiler 更小，小6kb
    - 执行效率更高

- 一般开发中应该使用 Runtime-Only，并使用vue文件进行开发

## render函数
[top](#catalog)
- render函数的用法
    ```js
    render: function(createElement){
        return createElement(...);
    }
    ```

- `createElement` 函数的用法
    - 用法1: 手动创建标签
        - 参数设置
            ```js
            // 参数类型: 字符串，Object，数组
            createElement('html标签名', {标签属性名:属性值, ...}, [标签内容或子标签, ...])

            // 第二个参数缺省
            // 参数类型: 字符串，数组
            createElement('html标签名', [标签内容或子标签, ...])

            // 只创建标签
            createElement('html标签名')
            ```
        - 生成的内容会直接替换 `#app`，而不是加载到 `#app` 标签内部
        - 在数组中，可以继续调用 `createElement` ，用来创建子标签
    - 用法2: 传入一个组件对象
        - 在 Runtime-Only 模式下，只能使用从vue文件导入的组件对象，不能使用手写的组件
        - 在 Runtime-Compiler 模式下，vue文件中的组件对象，手写的组件都可以使用

- vue文件中的 template 是如何处理的
    - 负责处理的模块: `vue-template-compiler`
    - 处理结果:
        - 将template解析成render函数
        - <span style='color:red'>引入vue文件时，引入的是解析后的app对象</span>
        - 解析后的对象不会包含 template

- 手动创建标签示例
    1. 创建一个标签
        - 参考代码
            - [src/cli-usage/cli2/runtime-only-demo01/src/main_bk01.js](src/cli-usage/cli2/runtime-only-demo01/src/main_bk01.js)
        - 代码内容
            ```js
            new Vue({
              el: '#app',
              render(createElement) {
                return createElement(
                  'p',
                  { style: 'color:#47e' },
                  ['render test']);
              },
            });
            ```
    2. 创建一个标签及其子标签
        - 参考代码
            - [src/cli-usage/cli2/runtime-only-demo01/src/main.js](src/cli-usage/cli2/runtime-only-demo01/src/main.js)
        - 代码内容
            ```js
            new Vue({
              el: '#app',
              render(createElement) {
                return createElement(
                  'div',
                  { style: 'color:#47e' },
                  // 创建多个子标签
                  [
                    createElement('p', 'div test'),
                    createElement('button', 'btn'),
                    createElement('br'),
                    createElement('button', 'btn'),
                  ]);
              },
            });
            ```

## vue-cli3
### cli3与cli2的区别
[top](#catalog)
- cli3 基于 webpack4, cli2 基于 webpack3
- cli3 的设计原则是 **0配置**，移除了配置目录build、config
- cli3 提供了 `vue ui` 命令，提供了可视化配置
- 移除了 static 目录，增加了 public 目录。`index.html`被移动到 public 目录下

### vue-cli3创建项目与结果分析
[top](#catalog)
- 3.x 创建项目的指令
    ```sh
    vue create <project_name>
    ```

- 创建项目时的选项分析
    - 选择使用哪些配置
        ```sh
        ? Please pick a preset: (Use arrow keys)
        > default (babel, eslint)   <<<<<<< 使用默认配置
          Manually select features  <<<<<<< 手动选择一些特性
        ```
    - 手动设置特性
        - 设置方法: 上下键移动，空格键选择/取消，回车键确认
        - 除了新增的几个配置内容，总体上与 vue-cli2 类似
        - 设置过程改成了一次性选择，更加方便
        - 设置选项
            ```sh
            ? Please pick a preset: Manually select features
            ? Check the features needed for your project: (Press <space> to select, <a> to toggle all, <i> to invert             selection)
            >(*) Babel                  <<<<<<<<< js兼容处理
             ( ) TypeScript
             ( ) Progressive Web App (PWA) Support  <<<<<<<<<是否启用渐进式web应用 PWA
             ( ) Router                 <<<<<<<<< 路由
             ( ) Vuex
             ( ) CSS Pre-processors     <<<<<<<<< css预处理器
             (*) Linter / Formatter     <<<<<<<<< Linter就是eslint
             ( ) Unit Testing           <<<<<<<<< 单元测试
             ( ) E2E Testing            <<<<<<<<< 端到端测试
            ```

    - 设置一些打包操作需要的配置保存到哪里，一般都会保存到独立文件中
        ```
        ? Where do you prefer placing config for Babel, ESLint, etc.? (Use arrow keys)
        > In dedicated config files     <<<<<<<<<<< 保存到独立文件中
          In package.json               <<<<<<<<<<< 保存到 package.json 中
        ```

    - 确认是否将当前创建项目时使用的选项保存到文档中，在创建其他项目时使用
        - 确认内容
            ```
            ? Save this as a preset for future projects? (y/N)
            ? Save preset as:            <<<<<<<<<< 设置文件的名字
            ```
        - 如何删除保存内容
            - windows，从用户目录下的 `.vuerc` 文件中删除保存的内容
            - linux ?????

- 创建结果分析

    |目录/文件|功能|
    |--:|:--|
    |`node_modules/`||
    |`src/`|开发代码|
    |`public/`|静态资源目录 ????|
    |`.browserslistrc`|css兼容性处理配置，从`package.json`移动到独立文件中了|
    |`babel.config.js`|babel预设配置|
    |`.editorconfig`|开发规范设置|
    |`.eslintrc.js`|语法检查配置|
    |`.gitignore`||
    |`package-lock.json`||
    |`package.json`||
    |`README.md`||

- `src` 目录分析
    - `assets`，保存静态资源，与`public/`不同，打包时，assets会被webpack处理
    - `components`，保存小组件，可被其他组件复用的组件
    - `router`，路由映射配置
    - `views`，页面级组件，可以引用 `components` 中的组件
    - `containers`，容器级组件，**需要手动创建**
    - `App.vue`
    - `main.js`，打包入口

### vue-cli3的配置
[top](#catalog)
- vue-cli3的配置如何管理
    - 由 `@vue/cli-service` 负责管理打包工具及配置
    - `package.json` 的配置内容会减少很多

- vue-cli3 中，配置文件的保存位置
    1. `node_modules/@vue/cli-service/webpack.config.js`
        - 打包时，会使用该配置文件，并读取`./lib/Service` 下的更多配置内容
            ```js
            if (!service || process.env.VUE_CLI_API_MODE) {
            const Service = require('./lib/Service')
            service = new Service(process.env.VUE_CLI_CONTEXT || process.cwd())
            service.init(process.env.VUE_CLI_MODE || process.env.NODE_ENV)
            }
            ```
    2. `node_modules/@vue/cli-service/lib/Service.js`


- vue-cli3修改配置的方法
    - 方法1: 启动配置服务器，并在浏览器中修改
        - 通过 `vue ui` 指令启动服务器
        - 该指令可以在任意目录下执行，它与某个具体的项目无关
        - 查看某个项目的配置
            1. 启动后选择导入
            2. 选择目标项目
            3. 点击导入项目按钮
            4. 在左侧的工具栏选择管理哪些内容
                - plugin，管理插件
                - 依赖，管理运行时依赖与开发依赖
                - 项目配置，管理webpack的一些配置
    - 方法2:在项目内，自定义配置文件
        - 在项目目录下，创建文件 `vue.config.js`
            - 文件名固定，不能随意修改
        - 在文件中通过 `module.exports` 将自定义配置导出
        - 打包时，会自动合并自定义配置
