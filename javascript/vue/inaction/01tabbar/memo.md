<span id="catalog"></span>

### 目录
- [开发的需求](#开发的需求)
- [开发过程中的一些注意事项](#开发过程中的一些注意事项)
- [开发过程1--组件化的开发的演进过程](#开发过程1--组件化的开发的演进过程)
    - [第1阶段--构建雏形](#第1阶段--构建雏形)
    - [第2阶段--组件拆分](#第2阶段--组件拆分)
    - [第3阶段--组件插槽化](#第3阶段--组件插槽化)
    - [第4阶段--组件的细分与组合使用](#第4阶段--组件的细分与组合使用)
    - [第5阶段--组件微调](#第5阶段--组件微调)
- [开发过程2--结合路由进行开发](#开发过程2--结合路由进行开发)
    - [使用路由](#使用路由)
    - [根据路由状态设置按钮的图片和文字颜色](#根据路由状态设置按钮的图片和文字颜色)
- [开发过程3--简化App组件](#开发过程3--简化App组件)
- [开发过程4--添加路径别名简化路径的使用](#开发过程4--添加路径别名简化路径的使用)
- [开发结果分析](#开发结果分析)


# 开发的需求
[top](#catalog)
- 开发一个移动端使用的 `tabbar`
- 需求
    - tabbar 中的按钮数量不确定，需要根据实际情况设置
    - 每个按钮中的图片，和按钮名称也不确定
- 开发方法
    - 两个组件
        - `tabbar` 组件
            - 外层组件
            - 提供一个插槽
            - 有外部组件注入需要的按钮，并显示
        - 按钮组件
            - 内层组件
            - 提供一个插槽
            - 由外部注入图片与按钮文字
    - 使用时，创建 n 个按钮组件，并注入到 `tabbar` 组件中。完成复用和动态显示


# 开发过程中的一些注意事项
[top](#catalog)
- 移动端最常用的tabbar高度: 49px
- 图片下面会多三个像素，使用middle属性值消除

# 开发过程1--组件化的开发的演进过程
## 第1阶段--构建雏形
[top](#catalog)
- 在App.vue中创建tabbar的雏形
- 第一阶段的问题
    - css、html代码全部被封装在App.vue中，复用性太差
- 需要将html、css抽出，创建Component
- 示例
    ```html
    <template>
    <div id="app">
        <div class='tabbar'>
        <div class='tabbar--item'>a</div>
        <div class='tabbar--item'>b</div>
        </div>
    </div>
    </template>

    <style>
    /* 引入公共css */
    @import './assets/css/base.css';

    .tabbar{
      /* 使用弹性容器布局 */
      display: flex;
      /* 设置高度和居中 */
      justify-content: center;
      text-align: center;
      height: 49px;
      line-height: 49px;
      /* 设置背景颜色 */
      background-color: #ededed;

      /* 开启固定定位，定位到页面底部 */
      position:fixed;
      bottom:0px;
      /* 固定定位会脱离文档流，将左右位置设为0，
          水平等式会自动计算width
      */
      left:0px;
      right:0px;
    }

    .tabbar--item{
      flex: 1;
    }
    </style>
    ```html

## 第2阶段--组件拆分
[top](#catalog)
- 步骤
    1. 将 App.vue 中的css、html抽出，封装到 TabBar.vue 中
    2. 在 TabBar.vue 中，插入按钮图片，并整理每个按钮的显示结构
    3. 在 App.vue 中，注册组件TabBar.vue，并在html中使用组件
- 第二阶段的问题
    - TabBar.vue 自身的复用性较低，不能进行按钮的复用、替换、增加按钮的数量
    - TabBar.vue 自身的逻辑开始增多，并变得复杂

- 解决方法
    - 将 TabBar.vue 中，按钮部分的代码抽出，只提供一个插槽，然其他组件来注入
    - 保留css

- 示例
    - TabBar.vue，封装整体结构
        ```html
        <template>
          <div>
            <div class='tabbar'>
              <div class='tabbar--item'>
                <img src="" alt="aaa" class='tabbar--item__img'>
                <div>a</div>
              </div>
              <div class='tabbar--item'>
                <img src="" alt="aaa" class='tabbar--item__img'>
                <div>b</div>
              </div>
            </div>
          </div>
        </template>

        <style>
        /* TabBar css ....*/
        </style>
        ```
    - App.vue，注册组件TabBar.vue，并在html中使用组件
        ```html
        <template>
          <div id="app">
            <tab-bar>
            </tab-bar>
          </div>
        </template>

        <script>
        // 引入组件并注册
        import TabBar from './components/tabbar/TabBar.vue'
        export default {
          components:{
            TabBar
          }
        }
        </script>
        <style>
        @import './assets/css/base.css';
        </style>
        ```

## 第3阶段--组件插槽化
[top](#catalog)
- 步骤
    1. 将 TabBar.vue 中的按钮代码抽出，TabBar.vue 只提供插槽
    2. 将抽出的代码转移到 App.vue 中
        - App.vue 在调用 TabBar.vue 时将按钮的html注入到插槽中
- 第三阶段的问题
    -  App.vue 中的代码结构是重复的，无法被复用

- 解决方法
    - 将按钮部分的代码继续拆分成组件
- 示例
    - TabBar.vue，只提供插槽
        ```html
        <template>
          <div>
            <div class='tabbar'>
              <!-- 对外部只提供插槽 -->
              <slot></slot>
            </div>
          </div>
        </template>

        <style>
        /* TabBar css ....*/
        </style>
        ```
    - App.vue，将按钮代码注入到 TarBar.vue 的插槽中
        ```html
        <template>
          <div id="app">
            <tab-bar>
              <!-- 将按钮代码注入到插槽中 -->
              <div class='tabbar--item'>
                <img src="" alt="aaa" class='tabbar--item__img'>
                <div>a</div>
              </div>
              <div class='tabbar--item'>
                <img src="" alt="aaa" class='tabbar--item__img'>
                <div>b</div>
              </div>
            </tab-bar>
          </div>
        </template>

        <script>
        // 引入组件并注册
        import TabBar from './components/tabbar/TabBar.vue'
        export default {
          components:{
            TabBar
          }
        }
        </script>
        <style>
        /* 引入公共css */
        @import './assets/css/base.css';
        </style>
        ```

## 第4阶段--组件的细分与组合使用
[top](#catalog)
- 步骤
    1. 将 App.vue 中的按钮代码抽出，创建一个按钮的组件 TabBarItem.vue
        - 创建时，可以从 TabBar.vue 中转移部分相关的css
    2. 按钮组件提供2个插槽，分别对应图片和按钮文字
    3. 在 App.vue 中注册 TabBarItem.vue
    4. 使用 TabBarItem.vue，并向插槽中注入图片和文字

- 第4阶段的问题
    - 只能设置按钮普通状态的代码，无法设置激活状态

- 示例
    - TabBar.vue
        ```html
        <template>
          <div>
            <div class='tabbar'>
              <!-- 对外部只提供插槽 -->
              <slot></slot>
            </div>
          </div>
        </template>

        <style>
        .tabbar{
          display: flex;
          justify-content: center;
          text-align: center;
          height: 49px;
          background-color: #ededed;
          position:fixed;
          bottom:0px;
          left:0px;
          right:0px;
        }
        </style>
        ```
    - TabBarItem.vue
        ```html
        <template>
          <div class='tabbar--item'>
            <!-- 对外部提供两个插槽，分别对应按钮图片、按钮文字 -->
            <slot name='item-url'></slot>
            <slot name='item-text'></slot>
          </div>
        </template>

        <style>
        .tabbar--item{
          flex: 1;
        }
        .tabbar--item__img{
          height:24px;
          width:24px;
        }
        </style>
        ```
    - App.vue
        ```html
        <template>
          <div id="app">
            <tab-bar>
              <!-- 将按钮组件注入到插槽中 -->
              <tab-bar-item>
                <img src="" alt="aaa" class='tabbar--item__img' slot='item-url'>
                <div slot='item-text'>a</div>
              </tab-bar-item>
              <tab-bar-item>
                <img src="" alt="aaa" class='tabbar--item__img' slot='item-url'>
                <div slot='item-text'>b</div>
              </tab-bar-item>
            </tab-bar>
          </div>
        </template>

        <script>
        import TabBar from './components/tabbar/TabBar.vue';
        import TabBarItem from './components/tabbar/TabBarItem.vue';
        export default {
          components:{
            TabBar,
            TabBarItem
          }
        }
        </script>
        <style>
        /* 引入公共css */
        @import './assets/css/base.css';
        </style>
        ```

## 第5阶段--组件微调
[top](#catalog)
- 步骤
    1. 在 TabBarItem.vue 中，添加显示激活状态图片的插槽，并通过 `v-if` 来控制显示
    2. 在 App.vue 中，向 TabBarItem.vue 的插槽注入组件时，额外添加一个激活状态的图片
- 示例
    - TabBarItem.vue
        ```html
        <template>
          <div class='tabbar--item'>
            <!-- 对外部提供三个插槽，分别对应普通按钮图片、激活的按钮图片、按钮文字
                 将slot放在div中，在div上设置绑定参数，防止slot替换时，参数失效 -->
            <div v-if='!isActive'>
              <slot name='item-icon'></slot>
            </div>
            <div v-else>
              <slot name='item-icon-active'></slot>
            </div>
            <div :class='{"tabbar--text__active": isActive}'>
              <slot name='item-text'></slot>
            </div>
          </div>
        </template>

        <script>
        export default {
          name: 'TabBarItem',
          data(){
            return {
              isActive:false,
            }
          }
        }
        </script>

        <style>
        .tabbar--item{
          flex: 1;
          font-size: 14px;
          line-height: 14px;
        }
        .tabbar--icon{
          margin:3px;
          vertical-align:middle;
          height:24px;
          width:24px;
        }

        .tabbar--text__active{
          color:red;
        }
        </style>
        ```
    - App.vue
        ```html
        <template>
          <div id="app">
            <tab-bar>
              <!-- 将按钮组件注入到插槽中 -->
              <tab-bar-item>
                <img src="./assets/img/tabbar/facebook.svg" alt="facebook"         class='tabbar--icon' slot='item-icon'>
                <!-- 注入激活状态的图片 -->
                <img src="./assets/img/tabbar/facebook_active.svg" alt="facebook"         class='tabbar--icon' slot='item-icon-active'>
                <div slot='item-text'>facebook</div>
              </tab-bar-item>
            </tab-bar>
          </div>
        </template>

        <script>
        import TabBar from './components/tabbar/TabBar.vue';
        import TabBarItem from './components/tabbar/TabBarItem.vue';
        export default {
          components:{
            TabBar,
            TabBarItem
          }
        }
        </script>
        <style>
        /* 引入公共css */
        @import './assets/css/base.css';
        </style>
        ```

# 开发过程2--结合路由进行开发
## 使用路由
[top](#catalog)
- 将路由与tabbar进行关联
    - 一个按钮对应一个页面
    - 在 TabBarItem.vue 中，将 `<div>` 换成 `<router-link>` 来进行路由跳转
    - 需要在注入组件时，通过props绑定路由路径
    - 路由跳转与路由显示的分配
        - 分配方式
            ```
            App.vue
                TabBar.vue  <<< 负责路由显示
                    TabBarItem.vue  <<< 负责路由跳转
                    TabBarItem.vue  <<< 负责路由跳转
                    TabBarItem.vue  <<< 负责路由跳转
                    TabBarItem.vue  <<< 负责路由跳转
            ```
        - 使用插槽后，App.vue、TabBar.vue、TabBarItem.vue 是一体的。所以将显示和跳转分开
- 在src/view 下开发页面组件
- 示例
    - 配置路由映射:[tabbar/src/router/index.js](tabbar/src/router/index.js)
        ```js
        Vue.use(VueRouter)
        const routes = [
          {
            path: "",
            redirect: "/facebook",
          },
          {
            path: "/others",
            component: Others
          },
          {
            path: "/browser",
            component: Browser
          },
          {
            path: "/happy",
            component: Happy
          },
          {
            path: "/facebook",
            component: Facebook
          },
        ]
        const router = new VueRouter({
          mode: 'history',
          base: process.env.BASE_URL,
          routes
        })

        export default router
        ```
    - App.vue，使用插槽时，绑定路由路径
        ```html
        <template>
          <div id="app">
            <tab-bar>
              <!-- 将按钮组件注入到插槽中 -->
              <!-- 同时绑定路由路径 -->
              <tab-bar-item path='/facebook'>
                <img src="./assets/img/tabbar/facebook.svg" alt="facebook" class='tabbar--icon' slot='item-icon'>
                <img src="./assets/img/tabbar/facebook_active.svg" alt="facebook" class='tabbar--icon' slot='item-icon-active'>
                <div slot='item-text'>facebook</div>
              </tab-bar-item>
            </tab-bar>
          </div>
        </template>
        ```
    - TabBarItem.vue，设置按钮点击事件，并根据绑定的路由路径设置路由
        ```html
        <template>
          <!-- 使用 router-link 完成路由跳转-->
          <router-link class='tabbar--item' :to='path'>
            <!-- 对外部提供三个插槽，分别对应普通按钮图片、激活的按钮图片、按钮文字
                 将slot放在div中，在div上设置绑定参数，防止slot替换时，参数失效 -->
            <div v-if='!isActive'>
              <slot name='item-icon'></slot>
            </div>
            <div v-else>
              <slot name='item-icon-active'></slot>
            </div>
            <div :class='{"tabbar--text__active": isActive}'>
              <slot name='item-text'></slot>
            </div>
          </router-link>
        </template>

        <script>
        export default {
          name: 'TabBarItem',
          props:{
            path:String,  // 在使用插槽时，设置路由路径
          },
          data(){
            return {
              isActive:false,
            }
          },
        }
        </script>
        ```
    - TabBar.vue，添加 `<router-link>` 来显示路由
        ```html
        <template>
          <div>
            <!-- 插槽中的链接负责路由跳转，外部的容器负责显示路由 -->
            <router-view></router-view>
            <div class='tabbar'>
              <!-- 对外部只提供插槽 -->
              <slot></slot>
            </div>
          </div>
        </template>
        ```

## 根据路由状态设置按钮的图片和文字颜色
[top](#catalog)
- TabBarItem.vue，修改控制颜色的方式
    - 通过计算属性来设置激活状态
        - 激活状态的判断方式
            ```js
            isActive(){
                // 使用 indexOf 来判断路劲，防止漏掉包含子路由的情况
                return this.$route.path.indexOf(this.path) != -1;
            },
            ```
    - 删除控制颜色的css，使用 动态属性绑定来设置颜色 `:style`
    - 在 `props` 添加颜色属性
    - 添加计算属性，根据激活状态将 `props` 中的属性绑定到 `style`
- TabBar.vue，在使用插槽时，设置激活状态的颜色

- 示例
    - TabBarItem.vue，
        ```html
        <template>
          <!-- 使用 router-link 完成路由跳转-->
          <router-link class='tabbar--item' :to='path'>
            <!-- 对外部提供三个插槽，分别对应普通按钮图片、激活的按钮图片、按钮文字
                 将slot放在div中，在div上设置绑定参数，防止slot替换时，参数失效 -->
            <div v-if='!isActive'>
              <slot name='item-icon'></slot>
            </div>
            <div v-else>
              <slot name='item-icon-active'></slot>
            </div>
            <!-- 根据激活状态设置属性 -->
            <div :style='activeTextColor'>
              <slot name='item-text'></slot>
            </div>
          </router-link>
        </template>

        <script>
        export default {
          name: 'TabBarItem',
          props:{
            path:String,  // 在使用插槽时，设置路由路径
            activeColor: { //在使用插槽时，设置激活状态的图片颜色
              type: String,
              default: 'red',
            }
          },
          computed:{
            isActive(){
              // console.log(this.$route);
              // 使用 indexOf 来判断路劲，防止漏掉包含子路由的情况
              return this.$route.path.indexOf(this.path) != -1;
            },
            activeTextColor(){
              // 激活状态设置颜色；非激活状态，通过 {} 消除样式
              return this.isActive? {'color': this.activeColor}: {};
            }

          }
        }
        </script>
        ```
    - App.vue，手动设置颜色
        ```html
        <template>
          <div id="app">
            <tab-bar>
              <!-- 将按钮组件注入到插槽中 -->
              <!-- 使用自定义的按钮颜色 -->
              <tab-bar-item path='/facebook' active-color='blue'>
                <img src="./assets/img/tabbar/facebook.svg" alt="facebook" class='tabbar--icon' slot='item-icon'>
                <img src="./assets/img/tabbar/facebook_active.svg" alt="facebook" class='tabbar--icon' slot='item-icon-active'>
                <div slot='item-text'>facebook</div>
              </tab-bar-item>
              <!-- 使用默认的按钮颜色 -->
              <tab-bar-item path='/happy'>
                <img src="./assets/img/tabbar/smell.svg" alt="happy" class='tabbar--icon' slot='item-icon'>
                <img src="./assets/img/tabbar/smell_active.svg" alt="happy" class='tabbar--icon' slot='item-icon-active'>
                <div slot='item-text'>happy</div>
              </tab-bar-item>
            </tab-bar>
          </div>
        </template>
        ```

# 开发过程3--简化App组件
[top](#catalog)
- 问题
    - 组件的使用、插槽内容的注入全部写在了App.vue 中，使得 App.vue 过于复杂

- 解决方法
    - 将组件、插槽的相关内容抽取成的组件: `MainTabBar.vue`

- 示例
    - MainTabBar.vue，抽取新组件
        - 参考代码
            - [tabbar/src/components/MainTabBar.vue](tabbar/src/components/MainTabBar.vue)
        - 代码内容
            ```html
            <template>
              <tab-bar>
                <!-- 将按钮组件注入到插槽中 -->
                <!-- 使用自定义的按钮颜色 -->
                <tab-bar-item path='/facebook' active-color='blue'>
                  <img src="../assets/img/tabbar/facebook.svg" alt="facebook" class='tabbar--icon' slot='item-icon'>
                  <img src="../assets/img/tabbar/facebook_active.svg" alt="facebook" class='tabbar--icon' slot='item-icon-active'>
                  <div slot='item-text'>facebook</div>
                </tab-bar-item>
                <!-- 使用默认的按钮颜色 -->
                <tab-bar-item path='/happy'>
                  <img src="../assets/img/tabbar/smell.svg" alt="happy" class='tabbar--icon' slot='item-icon'>
                  <img src="../assets/img/tabbar/smell_active.svg" alt="happy" class='tabbar--icon' slot='item-icon-active'>
                  <div slot='item-text'>happy</div>
                </tab-bar-item>
              </tab-bar>
            </template>

            <script>
            // 注册组件
            import TabBar from './tabbar/TabBar.vue';
            import TabBarItem from './tabbar/TabBarItem.vue';
            export default {
              name: 'MainTabBar',
              components: {
                TabBar,
                TabBarItem
              }
            }
            </script>
            ```
    - App.vue，使用 MainTabBar.vue 组件
        ```html
        <template>
          <div id="app">
            <main-tab-bar></main-tab-bar>
          </div>
        </template>

        <script>
        // 将TabBar的复杂设定封装到 MainTabBar.vue，然后使用该组件
        import MainTabBar from './components/MainTabBar.vue'
        export default {
          components:{
            MainTabBar
          }
        }
        </script>
        <style>
        /* 引入公共css */
        @import './assets/css/base.css';
        </style>
        ```

# 开发过程4--添加路径别名简化路径的使用
[top](#catalog)
- 开发过程1～开发过程3直接的路径问题
    - `../` 嵌套过深
        - 到项目结构比较复杂，时，通过路径引入某些内容时，`../`的嵌套非常长
    - 相关内容发生移动时，需要手动检查、修改路径

- 解决方法
    1. 在项目的**根目录**下添加webpack的配置文件: `vue.config.js`
    2. 在配置文件中添加路径的别名
    3. 修改组件、css等对路径的引用，改为通过别名来引用

- 使用路径别名时的注意事项
    - 在 css、html 中使用路径别名时，需要在前面加 `~`, 否则无法识别
    - 添加配置后，需要重新打包、启动

- 示例
    - vue.config.js，添加路径别名
        - 参考代码
            - [tabbar/vue.config.js](tabbar/vue.config.js)
        - 配置内容
            ```js
            let path = require('path')
            module.exports = {
                // 添加额外的打包配置
                chainWebpack: config => {
                    // 自定义路径
                    config.resolve.alias
                    .set('@assets', path.join(__dirname, './src/assets'))
                    .set('@components', path.join(__dirname, './src/components'))
                },
            };
            ```
    - MainTabBar.vue 修改组件、图片的路径
        ```html
        <template>
          <tab-bar>
            <!-- 将按钮组件注入到插槽中 -->
            <!-- 使用自定义的按钮颜色 -->
            <tab-bar-item path='/facebook' active-color='blue'>
                <!-- 通过别名来引入图片，在html中使用别名需要加 `~`  -->
              <img src="~@assets/img/tabbar/facebook.svg" alt="facebook" class='tabbar--icon' slot='item-icon'>
              <img src="~@assets/img/tabbar/facebook_active.svg" alt="facebook" class='tabbar--icon' slot='item-icon-active'>
              <div slot='item-text'>facebook</div>
            </tab-bar-item>
          </tab-bar>
        </template>

        <script>
        // 通过别名引入组件
        import TabBar from '@components/tabbar/TabBar.vue';
        import TabBarItem from '@components/tabbar/TabBarItem.vue';
        export default {
          name: 'MainTabBar',
          components: {
            TabBar,
            TabBarItem
          }
        }
        </script>
        ```

# 开发结果分析
[top](#catalog)
- 模块化的结构
    - ?????

[top](#catalog)