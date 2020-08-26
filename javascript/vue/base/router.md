<span id="catalog"></span>

### 目录---Vue-Router--vue路由
- [cli创建项目时的路由选项](#cli创建项目时的路由选项)
- [路由的实现方式](#路由的实现方式)
- [vue-router简介](#vue-router简介)
- [vue-router的基本使用方法](#vue-router的基本使用方法)
- [Route--路由映射的配置方法](#Route--路由映射的配置方法)
    - [Route的使用说明](#Route的使用说明)
    - [Route的属性说明](#Route的属性说明)
    - [普通路由](#普通路由)
    - [路由的重定向](#路由的重定向)
    - [默认路由](#默认路由)
    - [懒加载路由](#懒加载路由)
    - [动态路由](#动态路由)
    - [嵌套路由](#嵌套路由)
- [VueRouter的选项设置](#VueRouter的选项设置)
    - [mode--历史记录的模式](#mode--历史记录的模式)
    - [linkActiveClass--激活状态下的链接样式](#linkActiveClass--激活状态下的链接样式)
- [router-link的属性](#router-link的属性)
- [路由中的参数传递方式](#路由中的参数传递方式)
    - [两种参数传递方式](#两种参数传递方式)
    - [动态路由的开发与使用](#动态路由的开发与使用)
    - [设置url中的请求参数](#设置url中的请求参数)
- [嵌套路由的开发与使用](#嵌套路由的开发与使用)
- [手动跳转路由](#手动跳转路由)
- [$route和$router的区别与分析](#$route和$router的区别与分析)
- [路由守卫](#路由守卫)
    - [路由守卫简介](#路由守卫简介)
    - [全局路由守卫](#全局路由守卫)
    - [路由独享守卫?????](#路由独享守卫?????)
    - [组件内的守卫?????](#组件内的守卫?????)
- [keep-alive](#keep-alive)
    - [默认设置下的路由跳转分析](#默认设置下的路由跳转分析)
    - [keep-alive简介](#keep-alive简介)
    - [keep-alive与router-view结合使用](#keep-alive与router-view结合使用)


# cli创建项目时的路由选项
[top](#catalog)
- 创建项目时，需要选择使用 `Vue-Router`
- Vue-Router 的创建选项
    - 选项信息
        ```sh
        ? Use history mode for router? (Requires proper server setup for index fallback in production) (Y/n)
        ```
    - 该选项确认: 路由是否使用历史模式
        - 历史模式会使用 `history.pushState API` 实现 URL 跳转，不会重新加载页面

# 路由的实现方式
[top](#catalog)
- 前端路由的核心
    - url改变，但是页面不做整体刷新
    - 维护一套路由映射规则:
        - 前端事件产生的url与页面组件的映射关系

- `url改变，但是页面不做整体刷新` 应该如何实现？
    - 三种方案
        1. URL的hash
            - URL的hash就是锚点，本质上是改变 `window.location` 的 href 属性
            - 可以直接通过**地址赋值**操作来改变href，但不刷新页面
                 ```js
                 window.location.hash = 目标url
                 ```
        2. html5 的 `history.pushState`，将url操作压入栈中
            ```js
            history.pushState({}, '', 'test')
            ```
        3. html5 的 `history.replaceState`，直接替换url，相当于刷新了栈顶
            ```js
            history.replaceState({}, '', 'test')
            ```

# vue-router简介
[top](#catalog)
- router**默认使用 hash 方式，可以选择 history**
- vue-router是由Vue.js官方提供的，与vue.js深度集成，适合构建单页面应用
- vue-router 是基于路由和组件的
    - 路由用于设置、保存访问路径和组件的映射
    - 在 vue-router 的单页面应用中，页面路径的改变就是组件间的切换

# vue-router的基本使用方法
[top](#catalog)
- 构建 vue-router 的使用框架
    1. 安装vue-router，vue-cli3以上可以忽略
        ```sh
        npm install vue-router --save
        ```
    2. 在模块化工程中使用 vue-router
        - 因为 vue-router 是一个插件，所以可以通过 `Vue.use()` 来安装路由功能
        - 使用步骤

            |No|目的|操作|
            |-|-|-|
            |1|导入路由     |从包 `vue-router` 导入路由插件|
            |2|安装路由     |调用 `Vue.use(VueRouter)` 方法，安装插件|
            |3|实例化`VueRouter` |实例化`VueRouter`，并传入路由映射: `Router`对象|
            |4|挂载路由     |在Vue实例中挂载 No.2 中创建的路由实例|

- vue-router 的使用方法
    1. 创建并**导出**路由所需的组件
    2. 配置路由映射: `Router`对象，即组件与路径的映射关系
    3. 将多个 `Router`对象 组装成数组，作为 `VueRouter` 的 options
    3. 通过 `<router-link>`、`<router-view>` 标签，来使用路由
        - `<router-link>`、`<router-view>` 是由 vue-router 注册的全局组件，可以直接使用
        - `<router-link>` 会被渲染成 `<a>`，用于路径的跳转
        - `<router-view>` 用于显示组件，决定组件在页面的显示位置

- 路由一般配置在 `src/router/index.js` 下
    - 引入时，直接通过 `router/` 目录引入即可，nodejs会自动读取 `index.js` 文件中的内容
    - `src/router/index.js` 文件，在vue-cli3下会自动生成

- 示例
    - 主要工程结构
        ```
        ├── public
        │      └── index.html       // 包含 #app，用于设置Vue实例
        └── src
              ├── router
              │     └── index.js    // 导入vue-router，创建、配置路由对象，并导出
              ├── view
              │     ├── Home.vue    // 路由需要的组件，创建并导出
              │     └── About.vue   // 路由需要的组件，创建并导出
              ├── App.vue           // Vue实例组件，在 template 中使用路由
              └── main.js           // 创建Vue实例，导入路由对象并挂载到Vue实例上
        ```
    - 构建vue-router使用环境
        1. 在模块化工程中使用 vue-router，[src/router/index_bk01.js](src/router/router-demo01/src/router/index_bk01.js)
            ```js
            // 配置所有与路由相关的信息

            import Vue from 'vue';
            // 1. 导入路由
            import VueRouter from 'vue-router';
            /// 导入路由所需组件
            import Home from '../views/Home.vue';
            import About from '../views/About.vue';

            // 2. 安装插件
            Vue.use(VueRouter);

            // 3. 创建路由实例
            // 3.1 创建多个Route，即多个组件与url路径的映射关系
            const routes = [
                // 一个映射关系就是一个对象
                {
                    path: '/home',
                    component: Home
                },
                {
                    path: '/about',
                    component: About
                },
            ];
            const router = new VueRouter({
                routes  // 设置路由映射
            });

            // 3.3 将路由导出
            export default router;

            // 4. 将路由挂载到Vue实例
            // 在main.js 中导入路由对象，并挂载
            ```
        2. 在 Vue 实例中挂载路由对象 `VueRouter`，[src/main.js](src/router/router-demo01/src/main.js)
            ```js
            import Vue from 'vue'
            import App from './App.vue'
            // 4.1 导入自定义的路由对象
            import router from './router'

            Vue.config.productionTip = false

            new Vue({
                router, // 4.2 将路由对象挂载到Vue实例
                render: h => h(App)
            }).$mount('#app')
            ```
    - 使用 vue-router
        1. 创建并导出路由所需的组件 `Home.vue`, `About.vue`
            - <span style='color:red'>可以不用手动添加导出代码，编译后会自动导出</span>
            - [src/views/Home.vue](src/router/router-demo01/src/views/Home.vue)
                ```js
                // Home.vue
                // 导出当前组件
                export default {
                    name: 'Home',
                }
                ```
            - [src/views/About.vue](src/router/router-demo01/src/views/About.vue)
                ```js
                // About.vue
                // 导出当前组件
                export default {
                    name: 'About',
                }
                ```
        2. 配置路由映射 `Route`:  [src/router/index.js](src/router/router-demo01/src/router/index.js)
            ```js
            // 导入路由所需组件
            import Home from '../views/Home.vue';
            import About from '../views/About.vue';

            // 3.1 创建组件与url路径的映射关系
            const routes = [
                // 一个映射关系就是一个对象
                {
                    path: '/home',
                    component: Home
                },
                {
                    path: '/about',
                    component: About
                },
            ];
            ```
        3. 使用路由，[src/App.vue](src/router/router-demo01/src/App.vue)
            ```html
            <template>
            <div id="app">
                <div id="nav">
                <!-- 设置路由的路径 -->
                <router-link to="/home">Home</router-link> |
                <router-link to="/about">About</router-link>
                <!-- 设置路由组件的显示位置 -->
                <router-view/>
                </div>
            </div>
            </template>
            ```

# Route--路由映射的配置方法
## Route的使用说明
[top](#catalog)
- 配置 `Route` 时 只要创建**普通对象**即可，Vue底层会自动装配成 `Route` 对象
- 一个 `Route` 负责一个url与一个组件之间的映射关系
- 多个 `Route` 组成一个数组，作为 `VueRouter` 实例化时的 options
    ```js
    // 1. 创建多个Route，即多个组件与url路径的映射关系
    const routes = [
        {
            path: '/home',
            component: Home
        },
        {
            path: '/about',
            component: About
        },
    ];
    const router = new VueRouter({
        routes  // 2.创建路由实例，并设置路由映射
    });
    ```

## Route的属性说明
[top](#catalog)
```js
{
    path: '跳转路径',           // 跳转路径，在 router-link 中需要使用该路径
    component: path所需的组件,  // path 对应的组件

    redirect: '重定向路径',     // 将路径重定向到指定路径。需要是一个已存在的 Route 中的path

    children:{                  // 配置嵌套路由，即子路由
        Router,
        Router,
        ...
    },

    meta:{                      // 自定义元数据
        ...
    }

    // 路由独享的守卫
    beforeEnter: (to, from, next) =>{...},   // 前置路由守卫
    afterEnter: (to, from) =>{...},          // 后置路由守卫
}
```

## 普通路由
[top](#catalog)
```js
{
    path: '/home',  // 配置跳转路径，在 router-link 中需要使用该路径
    component: Home // 路径对应的组件，在 router-view 中被渲染
},
```

## 路由的重定向
[top](#catalog)
```js
{
    path:'/xxxx',       // 当需要跳转到 /xxxx 时，自动重定向到指定的路径
    redirect: '/home'   // 设置重定向的路径，需要指定一个存在的路径
}
```

## 默认路由
[top](#catalog)
- 默认路由主要用于初始化时，主动将路径重定向到主页
- 使用时，需要配合重定向，指定一个默认的路径
    ```js
    {
        // path:'',          // 空字符串与 '/' 的路径相同
        path:'/',
        redirect: '/home'   // 设置重定向的路径，需要指定一个存在的路径
    }
    ```

## 懒加载路由
[top](#catalog)
- 通过懒加载，在路由跳转时，加载所需的组件，减少第一次进入页面的等待时间
- vue的路由懒加载的本质
    - **就是webpack的懒加载，每个vue被当作一个chunk**
- 路由懒加载的配置方式
    ```js
    // 将组件的引入改为函数，可以降低修改量。也可以直接写在配置对象中
    const 组件 = () => import(/* webpackChunkName: "打包后的文件名" */ '组件路径')
    const routes = [
        {
            path: '路径',
            component: 组件
        }
    ]
    ```

- 示例
    - 参考代码
        - [src/router/router-demo01/src/views/lazy/Lazy.vue](#src/router/router-demo01/src/views/lazy/Lazy.vue)
        - [src/router/router-demo01/src/router/index.js](#src/router/router-demo01/src/router/index.js)
    - 代码内容
        - Route 配置
            ```js
            const Lazy = ()=> import(/* webpackChunkName: "lazy" */'../views/lazy/Lazy.vue');

            const routes = [
              // 配置懒加载路由映射
              {
                path: '/lazy',
                component: Lazy,
              },
            ]
            ```

## 动态路由
[top](#catalog)
```js
{
    path: '/固定路由/:参数名', // 通过 `:参数名` 的方式设置路由的动态部分
    component: 组件                   // 设置动态路由需要的组件
},
```

## 嵌套路由
[top](#catalog)
- Route配置
    ```js
    {
      path: '/父路由',          // 先配置父路由
      component: 父路由的组件,
      children:[                // 在 children 属性中，配置子路由
        {
          path: '子路由1',        // 路径中不需要写父路径
          component: 子路由1的组件
        },
        {
          path: '子路由2',
          component: 子路由2的组件
        },
          //.....
      ]
    }
    ```
- 嵌套路由的调用路径
    - 必须从父路径开始
        ```html
        <router-link to='/父路径/子路径'>xxx</router-link>
        ```

# VueRouter的选项设置
## mode--历史记录的模式
[top](#catalog)
- 历史记录的模式需要在实例化 VueRouter 时指定
- 使用 html5 的 history 对象
    ```js
    import VueRouter from 'vue-router';
    const router = new VueRouter({
        mode: 'history' // 使用 html5 的 history 对象
        routes
    });
    ```
- 默认使用 Location 对象来控制
    - 该模式的路径会有 `#`，看起来不像标准路径，如: `http://localhost:8080/#/home`
        ```js
        import VueRouter from 'vue-router';
        const router2 = new VueRouter({
            // 默认使用 Location 对象来控制
            routes
        });
        ```

## linkActiveClass--激活状态下的链接样式
[top](#catalog)
- `linkActiveClass` 属性可以统一设置所有 `router-link` 在激活状态下自动生成的样式名
- 样式需要添加到 `App.vue` 中
- 示例
    - 参考代码
        - [src/router/router-demo01/src/router/index.js](src/router/router-demo01/src/router/index.js)
    - 代码内容
        ```js
        const router = new VueRouter({
            mode: 'history',
            routes,
            // 统一设置 router-link 的 active-class 属性
            linkActiveClass: 'activateState'
        });
        ```

# router-link的属性
[top](#catalog)
- 参考示例
    - [src/router/router-demo01/src/App.vue](src/router/router-demo01/src/App.vue)

- `to`
    - 功能: 用于设置跳转的路径
    - 属性值: `Route` 中**已存在**的 `path` 属性
    - 示例
        ```html
        <router-link to="/home">Home</router-link>
        ```
- `tag`
    - 功能: 指定渲染时，翻译成什么标签，默认翻译成 `<a>`
    - 属性值: html标签名
    - 示例
        ```html
        <router-link to="/home" tag='button'>Home</router-link>
        ```
- `replace`
    - 功能: 路径跳转时，不会生成前一个路径的历史记录
        - `history`模式下，调用了 `history.replaceState`，覆盖了上一个路径的历史记录
        - 默认的`location`模式下，`location.replace`，覆盖了上一个路径的历史记录
    - 无属性值属性
    - 示例
        ```html
         <router-link to="/home" replace>Home</router-link>
        ```
- `alive-class`
    - 前提
        - 当`<router-link>`对应的路由匹配成功时，自动为当前元素添加一个 class: `router-link-active`
            - 通过该类可以设置连接激活状态的样式
    - 功能
        - 路由匹配成功时的样式默认为: `router-link-active`，可以通过`alive-class`来修改自动添加的样式名
    - 属性值: 样式名
    - 示例
        ```html
        <router-link to="/home" active-class='myactive'>Home</router-link>
        ```
        ```css
        /* 自定义 alive-class 的样式 */
        .myactive{
        color:red;
        font-size: 20px;
        }
        ```
    - 该属性的缺点
        - 需要手动为每个连接单独设置该属性，无法统一修改
        - 可以通过 `VueRouter`实例 的 `linkActiveClass` 来统一设置该属性

# 路由中的参数传递方式
## 两种参数传递方式
[top](#catalog)
- 可用的参数传递方式
    1. `params`，动态路由传参
    2. `query`，设置url中的请求参数
- **两种传递方式可以组合使用**
- parmas 方式
    - 路由路径配置: `/固定路径/:参数名`
    - 参数传递的方式: 直接在路径中设定具体的值
        - 使用`<router-link>`
            ```html
            <router-link to='/固定路径/具体参数'>
            ```
        - 手动跳转
            ```js
            this.$router.push('/固定路径' + 具体参数);
            ```
    - 参数传递时形成的路径: `/固定路径/具体数据`
    - 参数的获取方式: `this.$route.params.参数名`
- query 方式
    - 路由路径配置: `/路径名`，就是普通的配置
    - 参数传递方式
        - 使用步骤
            1. 在 `query对象` 中封装参数
            2. 将路径参数也转换为对象，并装载 `query对象`
        - 使用`<router-link>`
            ```html
            <router-link :to="{path:'/固定路径', query:{key1:value1, ket2:value2,...}}">
            ```
        - 手动跳转
            ```js
            this.$router.push({
                path:'/固定路径',
                query:{key1:value1, ket2:value2,...}
            })
            ```
    - 参数传递时形成的路径: `/路径名?key1=value1&key2=value2`
    - 参数的获取方式: `this.$route.query.参数名`

## 动态路由的开发与使用
[top](#catalog)
- 什么是动态路由？
    - `path`和`Component`的匹配关系，称为动态路由
    - 即通过路由传递数据
- 动态路由的适用场景
    - 路径中的部分内容不确定
    - 如不同用户通过用户id进入页面时，路径会不同
        - 用户a: `/usr/aaa`
        - 用户b: `/usr/bbb`
    - 用户id相当于数据，需要通过路由传递到组件中，或者根据id判断使用什么样的组件
- 动态路由的设置与使用步骤
    1. 配置动态路由的 `Router`对象
        ```js
        {
          path: '/固定路由/:参数名', // 通过 `:参数名` 的方式设置路由的动态部分
          component: 组件               // 设置动态路由需要的组件
        },
        ```
    2. 在动态路由的目标组件中通过 `this.$route.params.路由参数` 来获取url中的参数
    3. <span style='color:red'>如果需要在模板中使用数据，只能将路由参数绑定到计算属性</span>
        - 为什么只能通过计算属性在模板中使用路由参数？
            - 前提
                - 动态路由的组件只会创建一次
                - 动态路由的切换不会创建新的组件对象
            - 如果通过`data`来使用路由参数
                1. 因为只创建了一次组件，所以将会一直使用第一次跳转时的参数
                2. 参数无法实时刷新
                    - 因为没有重新创建路由对象
                    - 因为没有实时更新参数的方法
    4. 如果不使用计算属性，可以直接将`this.$route.params.路由参数` 写在 `{{}}` 中实时显示
    5. 使用动态路由的页面，直接通过 `/固定路由/具体数据` 的方式执行跳转

- 示例
    - 参考代码
        - [src/router/router-demo01/src/router/index.js](src/router/router-demo01/src/router/index.js)
        - [src/router/router-demo01/src/views/dynamic_router/ParamBar.vue](src/router/router-demo01/src/views/dynamic_router/ParamBar.vue)
        - [src/router/router-demo01/src/App.vue](src/router/router-demo01/src/App.vue)

    - 代码内容
        1. 配置动态路由的 `Route`对象
            ```js
            // index.js
            {
              path: '/parambar/:dydata',
              component: ParamBar
            },
            ```
        2. 在动态路由的目标组件中通过 `this.$route.params.路由参数` 获取数据
            ```html
            <!-- ParamBar.vue -->
            <template>
                <div>
                  <!-- 2. 通过计算属性在模板中使用路由参数 -->
                  <p>param is {{dydata}}</p>
                </div>
            </template>

            <script>
            export default {
              name:'ParamBar',
              computed: {
                dydata(){   // 1. 获取路由参数，并banding到计算属性
                  return this.$route.params.dydata;
                }
              }
            }
            </script>
            ```
        3. 使用动态路由的页面，直接通过 `/固定路由/具体数据` 的方式执行跳转
            ```html
            <!-- App.vue -->
            <router-link to='/parambar/testdata'>ParamBar-testdata</router-link> |
            <router-link to='/parambar/12345'>ParamBar-12345</router-link> |
            ```

## 设置url中的请求参数
[top](#catalog)
- 使用场景
    - 参数数据量较大的请求
- 设置请求参数的使用步骤
    1. 配置 `Route`对象，**配置方式与普通的路由没有区别**
    2. 在父页面设置路由，及其参数
        ```html
        <router-link :to="{path:'/固定路径', query:{key1:value1, ket2:value2,...}}">
        ```
    3. 在组件中
        - 通过 `this.$route.query` 获取 query对象
        - 通过 `this.$route.query.参数名` 获取 query对象中保存的参数
- 示例
    - 参考代码
        - [src/router/router-demo01/src/App.vue](src/router/router-demo01/src/App.vue)
        - [src/router/router-demo01/src/router/index.js](src/router/router-demo01/src/router/index.js)
        - [src/router/router-demo01/src/views/router_query/Profile.vue](src/router/router-demo01/src/views/router_query/Profile.vue)
    - 代码内容
        1. 配置 `Route`对象，`index.js`
            ```js
            {
              path: '/profile',
              component: Profile,
            }
            ```
        2. 在父页面设置路由，及其参数，`App.vue`
            ```html
            <router-link :to="{path:'/profile', query:{name:'testName', age:22}}" tag='button'>Profile</router-link> |
            ```
        3. 在组件中获取query对象，`Profile.vue`
            ```html
            <template>
              <div>
                <p>this is my page</p>
                <!-- 2. 显示query参数 -->
                <p>{{this.$route.query}}</p>
                <p>name = {{name}}</p>
                <p>age = {{age}}</p>
              </div>
            </template>

            <script>
            export default {
              name: 'Profile',
              computed:{
                // 1. 通过计算属性获取query对象的数据
                name(){
                  return this.$route.query.name;
                },
                age(){
                  return this.$route.query.age;
                },
              }
            }
            </script>
            ```

# 嵌套路由的开发与使用
[top](#catalog)
- 嵌套路由的实现步骤
    1. 创建对应的子组件，并且父路由配置对象的 `children` 属性中配置子路由映射
    2. 在组件内部使用 `<router-view/>` 来显示组件

- 嵌套的子路由与普通路由的相同点
    - 嵌套的子路由可以是
        - 动态路由
        - 静态路由
        - 可以设置默认路由的重定向
    - 嵌套的子路由可以拥有属于自己的 `meta` 数据

- 注意事项
    - 配置子路由时， `path` 直接写子路径，不需要父路径，也不需要 `/`
    - `<router-link/>`中，需要写完整的路径，即: `/父路径/嵌套的子路径`

- 示例
    - 参考代码
        - [src/router/router-demo01/src/router/index.js](src/router/router-demo01/src/router/index.js)
        - [src/router/router-demo01/src/views/child_router/UserList.vue](src/router/router-demo01/src/views/child_router/UserList.vue)
    - 配置 `Router`对象
        ```js
        {
          path: '/userlist',        // 配置父路由
          component: UserList,
          children:[                // 配置嵌套路由
            {
              path: ':userid',      // 配置动态子路由
              component: UserInfo,
              meta:{
                title:'SubUser Page'
              }
            },
            {
              path: 'user-blank',   // 配置静态子路由
              component: UserBlank
            },
            {
              path: '',             // 配置默认子路由
              redirect: 'user-blank'
            }
          ],
          meta:{
            title:'UserList Page'
          }
        },
        ```
    - 父路径的写法
        ```html
        <ul>
            <!-- 1. 使用 静态路由 -->
            <li>
              <router-link to='/userlist/user-bland'>userblank</router-link>
            </li>
            <!-- 2. 遍历数据并生成 动态路由 -->
            <li v-for='userid in userList' :key='userid'>
              <!-- 嵌套路由需要从父路径开始写 -->
              <router-link :to='"/userlist/" + userid'>{{userid}}</router-link>
            </li>
        </ul>
        ```

# 手动跳转路由
[top](#catalog)
- 注意事项
    - 不要使用 history.pushState，或者 location.href 来控制路由跳转
    - 所有的路由跳转都应该通过 `vue-router` 组件中的功能来来实现
- 如何实现手动跳转
    1. `this.$router`，通过该属性获取挂载在 Vue 实例上的 `VueRouter` 对象
        - `vue-router` 安装之后，会自动向每个组件中添加 `$router` 属性
    2. 使用 `this.$router` 的路由跳转方法控制跳转

        |方法|功能|备注|
        |-|-|-|
        |`this.$router.push('路由映射中的路径')`|跳转路由，生成历史记录||
        |`this.$router.replace('路由映射中的路径')`|跳转路由，不生成历史记录|相当于使用了 `<router-link>`中的`replace`属性|

- 手动实现路由的方法
    1. 在标签中通过 `v-on` 监听某个事件
    2. 在Vue实例的 `methods` 属性中注册事件响应函数
    3. 在事件响应函数内部通过 `this.$router` 来控制路由

- 手动跳转路由 与 `router-link` 及其相关属性的对应关系

    |手写内容|`router-link`及其标签|
    |-|-|
    |手写的标签|`tag` 属性|
    |事件监听方法 + `this.$router`对象|`to` 属性|
    |`this.$router.replace`|`replace` 属性|

- 不同的参数传递方式
    - parmas 方式
        ```js
        this.$router.push('/固定路径' + 具体参数);
        ```
    - query 方式
        ```js
        this.$router.push({
            path:'/固定路径',
            query:{key1:value1, ket2:value2,...}
        })
        ```

- 示例
    - 参考代码
        - [src/router/router-demo01/src/App.vue](src/router/router-demo01/src/App.vue)
    - 代码内容
        1. 在标签中通过 `v-on` 监听某个事件
            ```html
            <button @click="foodClick">Food</button>
            ```
        2. 注册响应函数，并控制路由跳转
            ```js
            methods:{
                // 2.在Vue实例的 `methods` 属性中注册事件响应函数
                foodClick(){

                // 3.在事件响应函数内部获取路由对象，并控制路由跳转
                // this.$router.push('/food');     // 生成历史记录
                this.$router.replace('/food');  // 不生成历史记录
                }
            }
            ```

# $route和$router的区别与分析
[top](#catalog)
- $route和$router的区别

    ||表示内容|功能|
    |-|-|-|
    |`$router`|就是 `VueRouter` 的实例对象，即挂载在Vue实例上的对象|用于导航到不同的路由路径|
    |`$route`|表示当前被激活的 `Route`对象|通过 `$route` 可以获取到 `parmas`、`query` 对象，并从中获取通过路由传递的参数|
- `$route`和`$router` 在Vue底层的设置策略
    - 如果当前Vue实例（包括组件实例）中如果挂载了 `VueRouter`对象，则直接使用
    - 如果没有挂载，则使用父组件中的 `VueRouter`对象
    - 设置时，自顶向下设置，所以在所有的组件中都可以访问到 `$route` 和 `$router`

- 源码分析
    1. 自定义代码，创建Vue实例时，挂载路由对象
        ```js
        new Vue({
            router, // <<<<<< 挂载 `VueRouter`对象
            render: h => h(App)
        }).$mount('#app')

        ```
    2. Vue底层在执行实例化时，会执行 `VueRouter.install();`，将` VueRouter`对象安装到Vue实例中
        - 源码路径
            - [vue-router/src/index.js](vue-router/src/index.js)
        - 源码分析
            ```js
            import { install } from './install'
            export default class VueRouter {
                // install 是一个静态方法
                static install: () => void;
                // 其他代码.....
            }

            // 重新设置install
            VueRouter.install = install
            ```
    3. `install` 方法分析
        - 源码路径
            - [vue-router/src/install.js](vue-router/src/install.js)
        - 源码分析
            ```js
            export function install (Vue) {
              if (install.installed && _Vue === Vue) return
              install.installed = true

              _Vue = Vue

              const isDef = v => v !== undefined

              const registerInstance = (vm, callVal) => {
                let i = vm.$options._parentVnode
                if (isDef(i) && isDef(i = i.data) && isDef(i = i.registerRouteInstance)) {
                  i(vm, callVal)
                }
              }

              // 1. 为当前的Vue实例 设置 $router、$route 的具体内容
              Vue.mixin({
                beforeCreate () {
                  // 如果当前实例中包含 router属性，则进行绑定
                  if (isDef(this.$options.router)) {
                    this._routerRoot = this
                    // VVVVVVV 创建Vue实例时挂载的 `VueRouter`对象，即$router
                    this._router = this.$options.router
                    this._router.init(this)
                    // VVVVVVV 响应式的设置 _route，即$route
                    // VVVVVVV 设置的结果是当前激活的 `Router`对象
                    Vue.util.defineReactive(this, '_route', this._router.history.current)
                  } else {
                    // 如果当前实例中不包含 router属性，则使用父类的 $router 和 $route
                    // 所以只要Vue实例中挂载了 `VueRouter`对象，所有的组件就都可以使用路由了
                    this._routerRoot = (this.$parent && this.$parent._routerRoot) || this
                  }
                  registerInstance(this, this)
                },
                destroyed () {
                  registerInstance(this)
                }
              })

              // 2. 在Vue的原型上，定义 this.$router。所需内容来自 Vue.mixin() 中执行的设置

              // 2.2 所有组件都是继承Vue，所以在所有的实例中都可以使用
              Object.defineProperty(Vue.prototype, '$router', {
                  // 在Vue.mixin 方法中设置的 `VueRouter`对象
                  get () { return this._routerRoot._router }
              })

              // 2.3 在Vue的原型上，定义 this.$route
              // 所有组件都是继承Vue，所以在所有的实例中都可以使用
              Object.defineProperty(Vue.prototype, '$route', {
                get () { return this._routerRoot._route }
              })

              // 3. 注册组件 <router-link> 和 <router-view>
              // 注册时使用驼峰，使用时既爱那个驼峰分解，并用 `-` 连接
              Vue.component('RouterView', View)
              Vue.component('RouterLink', Link)

              const strats = Vue.config.optionMergeStrategies
              // use the same hook merging strategy for route hooks
              strats.beforeRouteEnter = strats.beforeRouteLeave = strats.beforeRouteUpdate = strats.created
            }
            ```

# 路由守卫
## 路由守卫简介
[top](#catalog)
- 路由守卫的功能
    - 监听路由A到路由B的跳转，并在跳转的过程中执行一些操作
- 路由守卫的适用场景
    - 在SPA应用中，根据不同的页面显示不同的title
        - 网页标题通过 `<title>` 显示，但是SPA只有一个固定的HTML，切换页面时，标题不会改变
        - 可以通过 `document.title` 来设置标题
        - 需要在每次切换路由时，重新设置标题
        - 通过路由守卫，监听路由跳转，来重新设置标题

## 全局路由守卫
[top](#catalog)
- 功能
    - 监听所有路由的跳转，并执行相关处理
- 使用路由守卫的方法
    1. 通过 `VueRouter` 对象调用守卫函数来执行相关处理
    2. 守卫函数
        - `beforeEach`，前置守卫函数
        - `afterEach`，后置守卫函数
- 守卫函数说明
    - `beforeEach` 前置守卫函数
        - 响应时间: 离开当前路由之前
        - 函数定义
            ```ts
            // 守卫函数定义
            beforeEach(guard: NavigationGuard): Function

            // 参数定义
            export type NavigationGuard<V extends Vue = Vue> = (
              // 将要进入的路由
              to: Route,
              // 将要离开的路由
              from: Route,
              // 手动调用next后，才能进入下一个路由。Vue的默认实现会主动调用next，所以才能正常执行路由跳转
              next: NavigationGuardNext<V>
            ) => any
            ```
        - 使用时需要传递包含 3 个参数的函数，并调用 `next` 方法
            ```js
            vueRouter.beforeEach((to, from, next)=>{
                // 相关处理
                //...

                // 主动调用 next()，跳转到下一个路由
                next();
            });
            ```
    - `afterEach` 后置守卫
        - 响应时间: 跳转到新路由之后
        - 函数定义
            ```ts
            afterEach(hook: (to: Route, from: Route) => any): Function
            ```
        - 使用时需要传递包含 2 个参数的函数，并调用 `next` 方法
            ```js
            vueRouter.afterEach((to, from)=>{
                // 相关处理
                //...
            });
            ```
- 注意事项
    - 如果需要使用与组件相关的数据，可以通过 `Route` 的`meta` 属性设置
    - 对于嵌套路由如果要使用父路径的数据，需要通过 `matched` 属性获取
        - 该属性是一个数组，按照路由的嵌套顺序排列

- 示例
    - 参考配置
         - [src/router/router-demo01/src/router/index.js](src/router/router-demo01/src/router/index.js)
    - 全局路由守卫的配置内容
        ```js
        // 5. 配置全局路由守卫
        // 5.1 前置路由守卫
        router.beforeEach((to, from, next) =>{
          // 通过路由配置的 meta 来设置页面标题
          document.title = to.meta.title || document.title;
          // 在方法内部必须手动调用 next，否则路由不会跳转
          next();
        });

        // 5.1 后置路由守卫
        router.afterEach((to, from)=>{
          console.log('222222222')
        });
        ```

## 路由独享守卫?????
[top](#catalog)
?????

## 组件内的守卫?????
[top](#catalog)
?????

# keep-alive
## 默认设置下的路由跳转分析
[top](#catalog)
- 默认设置下，每次跳转路由时，Vue对组件的操作
    1. 重新实例化新路由的组件
    2. 调用新路由对应的组件的生命周期方法: `created`
    3. 销毁旧路由的组件对象
    4. 调用旧路由对应的组件的生命周期方法: `destroyed`
- 默认设置下，路由跳转的缺点
    - 重复的创建和销毁组件
    - 组件中的数据和状态无法被保存

## keep-alive简介
[top](#catalog)
- `keep-alive`
    - vue**内置**的组件
    - 功能
        - 可以使被包含的组件保留状态，或避免重新渲染
- 使用 `keep-alive` 产生的两个**生命周期**函数
    - `activated`，组件被激活时触发
    - `deactivated`，离开组件时触发
- 两个重要属性
    - 2个属性
        - include，字符串或正则表达式，只有匹配的组件会被缓存
        - exclude，字符串或正则表达式，任何匹配的组件都不会被缓存
    - 需要组件对象中声明了name属性，用于属性的匹配

## keep-alive与router-view结合使用
[top](#catalog)
- `<router-view/>` 如果直接被包在`<keep-alive>` 里面，所有路由匹配到的视图组件都会被缓存
    - 对于嵌套路由，路由内部的子路由的状态也会被保存
    - 在重新回到嵌套路由的页面时，还能够恢复离开时的状态
- 示例
    - 参考代码
        - vue代码
            - [src/router/router-demo01/src/App.vue](src/router/router-demo01/src/App.vue)
            - [src/router/router-demo01/src/views/keep_alive/Keep.vue](src/router/router-demo01/src/views/keep_alive/Keep.vue)
        - 路由配置
            - [src/router/router-demo01/src/router/index.js](src/router/router-demo01/src/router/index.js)

    - 为 `router-view` 设置 keep-alive，`App.vue`
        ```html
        <!-- 设置 keep-alive。排除 `NoKeep,NoKeep02`，多个排除目标之间只能有 逗号，不能有空格  -->
        <keep-alive exclude='NoKeep,NoKeep02'>
          <router-view/>
        </keep-alive>
        ```
    - 处理带有默认路由的嵌套路由
        1. 关闭路由配置中的默认路由, `index.js`
            ```js
            {
              path: '/keep',
              component: Keep,
              children: [
                // 默认路由改为由 activated 方法来设定
                // {
                //   path: '',
                //   redirect: 'first'
                // },
                {
                  path: 'first',
                  component: KeepFirst
                },
                {
                  path: 'second',
                  component: KeepSecond
                },
              ]
            },
            ```
        2. 将路径保存在变量中，通过 `activated` 事件来设置路由、通过 `beforeRouteLeave` 在离开路由前保存路由。 `Keep.vue`
            ```js
            export default {
              data(){
                return {
                  path:'/keep/first'
                }
              },
              created(){
                console.log("this is keep created");
              },
              destroyed(){
                console.log("this is keep destroyed");
              },

              /*
                1. 进入路由时，设置默认路由
                如果是第一次进入，则使用默认的 path
                如果不是第一次进入，则使用由组件路由守卫 `beforeRouteLeave` 事实更新的路由路径
              */
              activated(){
                this.$router.push(this.path);
                console.log('keep page activated');
              },
              beforeRouteLeave(to, from ,next){
                // 2. 在路由离开前，将当前的活动路由的路径保存到 path 属性中
                this.path = this.$route.path;
                next();
              }
            }
            ```