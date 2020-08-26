<span id="catalog"></span>

### 目录---Vuex--状态管理
- [Vuex](#Vuex)
    - [Vuex的功能](#Vuex的功能)
    - [哪些内容可以由Vuex进行管理](#哪些内容可以由Vuex进行管理)
    - [引入Vuex](#引入Vuex)
    - [Vuex管理状态的图例说明](#Vuex管理状态的图例说明)
    - [单页面的状态管理](#单页面的状态管理)
    - [修改Vuex中管理的状态--多页面的状态管理](#修改Vuex中管理的状态--多页面的状态管理)
    - [Vuex核心概念](#Vuex核心概念)
        - [Vuex核心概念](#Vuex核心概念)
        - [State](#State)
        - [Getters](#Getters)
        - [Mutations](#Mutations)
        - [响应式的添加与删除State中的状态](#响应式的添加与删除State中的状态)
        - [Mutation常量](#Mutation常量)
        - [Actions](#Actions)
        - [Modules](#Modules)
    - [Vuex的推荐目录结构](#Vuex的推荐目录结构)
- [](#)

# Vuex的功能
[top](#catalog)
- Vuex是Vue应用程序开发的状态管理模式
    - Vuex采用<span style='color:red'>集中式存储管理</span>应用的所有组件的状态，并以相应的规则保证状态以一种可预测的方式发生变化
    - Vuex也集成到VUe的官方调试工具 `devtools extension`，提供了如零配置的 time-travel 调试，状态快照导入导出等高级调试

- 状态管理是什么?
    - 将**多组件间**的`共享变量`存储在一个单例对象中进行管理
    - 将这个对象放在顶层的Vue实例中，可以让其他组件共享

- 一种手动实现的方式 -- 通过 `Vue.prototype` 手动设置共享变量
    - 将变量添加到 `Vue.prototype` 后，所有示例就可以共享该变量的
    - 缺点
        - 修改变量时，**无法响应式的刷新页面**

# 哪些内容可以由Vuex进行管理
[top](#catalog)
- 如: 用户的登录状态，包括
    - 用户名称、头像、地理位置信息等
- 如: 商品的收藏信息、购物车信息等
- 不适合放入 Vuex 中的内容
    - 如: 父子组件间共享的数据
        - 这种数据直接通过 `props` 等数据传递方式来共享即可

# 引入Vuex
[top](#catalog)
1. 安装 Vuex: `npm install vuex --save`
2. 在src目录下建立store目录，在store目录下添加 `index.js` 来导入 vuex
    ```js
    import Vue from 'vue'
    import Vuex from 'vuex'

    Vue.use(Vuex)

    export default new Vuex.Store({
        state: {
        },
        mutations: {
        },
        actions: {
        },
        modules: {
        },
        getters:{

        }
    })
    ```
3. 在 `main.js` 中导入store
4. 导入后会为 `Vue.prototype` 添加一个 `$store` 属性
5. 在各个组件中就可以通过 `$store` 来访问被管理的状态

# Vuex管理状态的图例说明
[top](#catalog)
- Vue状态管理图例
    - ![](?????)

- Vuex通过 `state` 来管理状态
    - 使用被管理的状态数据: `$store.state.状态名`

- 修改被管理的状态的方式
    1. 直接修改: `$store.state.状态名 = value;`
        - <span style='color:red'>不应该在组件中直接通过 `$store.state` 修改状态，即使这种方式可以</span>
        - 直接需要**vue内部 及 devtools 无法跟踪哪些组件产生了修改state的行为**
    2. - 通过 `mutations` 来**同步的**修改状态
    3. 通过 `action ---> mutations` 来**异步的**修改状态
        - 一般的异步修改，是指向服务端发送异步请求的情况

- `devtools`
    - 是vue的浏览器插件，可以记录每次修改 `$store.state` 时的行为快照
        - 包括: 哪个组件发起的修改行为，数据是任何变化的等

- `mutations`、`devtools`无法处理异步操作，如果有异步的修改，必须使用 `action`

# 单页面的状态管理
[top](#catalog)
- 三个角色
    ```js
    actions ─────>>> state ─────>>> view
     ^                               │
     ^                               │
     │                               │
     │                               │
     └───────────────────────────────┘
    ```
- 三个角色的变化
    - state
        - 表示状态，相当于组件中的 `data`
        - state可以保存多个状态（变量）
        - 设置之后，在`View`中进行显示，即: state--->view
    - view
        - 表示视图层
        - 针对state的变化，显示不同的信息
        - 在view中可以由操作产生事件，即: view--->action
    - action
        - 事件发生后，调用相应事件，然后修改状态，即: action--->state
- 在代码上的反应
    ```js
    export default {
      data(){
        return { count:0 }  // 1. state
      }
    }
    ```
    ```html
    <div id="app">
      <!-- 2. view: 将state显示在页面中 -->
      <p>{{count}}</p>
      <!-- 3. action: 监听click事件，并修改state -->
      <button @click='count++'>+</button>
      <button @click='count--'>-</button>
    </div>
    ```

# 修改Vuex中管理的状态--多页面的状态管理
[top](#catalog)
1. 定义状态数据，和统一状态修改方法
    - 参考代码
        - [src/vuex/vuex-demo01/src/router/index.js](src/vuex/vuex-demo01/src/router/index.js)
    - 代码内容
        ```js
        export default new Vuex.Store({
          state: {
            counter:1   // 1. 定义需要管理的状态
          },
          mutations: {  // 2. 定义修改状态的方法
            // counter 的增加
            increment(state){
              state.counter++;
            },
            // counter 的减少
            decrement(state){
              state.counter--;
            }
          },
        })
        ```
2. 在空间中使用并修改状态
    - 参考代码
        - [src/vuex/vuex-demo01/src/views/Home.vue](src/vuex/vuex-demo01/src/views/Home.vue)
    - 代码内容
        ```html
        <template>
          <div class="home">
              <!-- 2.监听按钮事件 -->
            <button @click='sub'>-</button>
            <!-- 1. 使用 状态 -->
            <p>{{$store.state.counter}}</p>
            <button @click='add'>+</button>
            <p>home page</p>
          </div>
        </template>

        <script>
        export default {
          name: 'Home',
          methods:{
            // 3. 在按钮事件内，通过 commit ("mutations中的方法名") 来修改状态
            add(){
              this.$store.commit('increment');
            },
            sub(){
              this.$store.commit('decrement');
            }
          }
        }
        </script>
        ```

# Vuex核心概念
## Vuex核心概念
[top](#catalog)
- State
- Getters
- Mutations
- Actions
    - 负责异步操作。异步操作结束后，再调用 mutations
- Modules

## State
[top](#catalog)
- State `单一状态树`
    - Single Source of Truth，可以翻译成 `单一数据源`
    - 什么是单一状态树?
        - 即: **无论有多少数据源都最好使用一个 `Vuex.store` 对象**
        - 本质就是一个: **全局单例模式**的对象
- 单一状态树的必要性
    - 如果状态被保存到多个 `Vuex.store` 对象，管理和维护会变得困难
    - 单一状态树可以以最直接的方式找到某个状态的片段，在维护和调试上比较容易

- 在组件中使用 `state`
    - `this.$store.state.xxx` 获取 `state` 中保存的某个状态数据
    - `this.$store.state` 获取整个状态数据源
        - 只能获取，不应该直接修改

## Getters
[top](#catalog)
- 类似于计算属性，可以在获取状态时，附加额外的处理
- 获取 getters 的方式
    - 在组件中直接通过**属性**的方式访问
        ```js
        this.getters.method
        ```
- getters 的参数设置
    1. `method(state, getters)`
        - `state` 就是 `$store.state`
        - `getters` 就是 `$store.getters`
        - 在组件中调用 `this.getters.method` 时，会自动传入这两个参数
        - 在 `method` 内部
            - 可以返回 `state` 中的状态，或附加额外的逻辑
            - 可以通过 `getters` 获取其他 getter，进行逻辑复用
    2. `method(state)`
        - 1 的一种特殊情况，不需要复用其他 getter
    3. 返回一个**闭包函数**，用来处理**额外的参数**
        - getters中的方法，只能接受两个参数: `state`、`getters`
        - 通过返回闭包函数, 在组件中可以调用闭包函数来处理**额外的参数**
        - 如:
            ```js
            Vuex.Store({
                getters: {
                    method(state, getters){
                        // 返回一个函数给外部， 通过 `args` 来处理额外的函数
                        return function(...args){
                            // 引用某些需要使用的state，构成一个闭包函数
                            let obj = state.xxx;
                            //... 其他处理
                        }
                    }
                }
            }
            ```
- 示例
    - 浏览器入口
        - http://localhost:8080/goodsList
    - getters 设置
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/index_bk.js](src/vuex/vuex-demo01/src/store/index_bk.js)
        - 代码内容
            ```js
            export default new Vuex.Store({
              state: {
                // 2. 商品列表
                goodsList: [
                  { name: 'goods---aaa', price: 11, count: 1 },
                  { name: 'goods---bbb', price: 22, count: 2 },
                  { name: 'goods---ccc', price: 33, count: 3 },
                  { name: 'goods---ddd', price: 55, count: 4 },
                ]
              },
              getters: {
                // 方式2 : `method(state)`
                // 2. 商品列表 处理
                // 2.1 价格求和
                goodsTotalPrice(state) {
                  return state.goodsList.map(n => n.price * n.count).reduce((prev, cur) => prev + cur);
                },
                // 2.2 商品总数
                goodsCount(state) {
                  return state.goodsList.map(n => n.count).reduce((prev, cur) => prev + cur);
                },

                // 方式1 : `method(state, getters)`
                // 2.3 平均价格
                goodsAvgPrice(state, getters){
                  return getters.goodsTotalPrice / getters.goodsCount;
                },

                // 方式3 : 返回一个闭包函数，用来处理额外的参数
                // 2.4 过滤条件
                goodsFilter(state){
                  return function(minPrice=0, minCount=0){
                    return state.goodsList.filter(n => n.price > minPrice && n.count > minCount);
                  }
                }
              },
            })
            ```
    - 方式1: `method(state, getters)`，方式2:`method(state)` 的使用
        - 参考代码
            - [src/vuex/vuex-demo01/src/views/goodsList/GoodsShow.vue](src/vuex/vuex-demo01/src/views/goodsList/GoodsShow.vue)
        - 代码内容
            ```html
            <p>total Price: {{$store.getters.goodsTotalPrice.toFixed(2)}}</p>
            <p>goods count: {{$store.getters.goodsCount}}</p>
            <p>average price: {{$store.getters.goodsAvgPrice.toFixed(2)}}</p>
            <ul>
                <li>index--name--price--count</li>
                <li v-for='(item, index) in $store.state.goodsList' :key='index'>{{index+1}}--{{item.name}}--{{item.price.toFixed(2)}}--{{item.count}}</li>
            </ul>
            ```
    - 方式3: 闭包函数的使用
        - 参考代码
            - [src/views/goodsList/GoodsFilter.vue](src/views/goodsList/GoodsFilter.vue)
        - 代码内容
            ```html
            <!-- 输出过滤条件 -->
            minPrice: <input type="number" name="minPrice" v-model="minPrice">
            minCount: <input type="number" name="minCount" v-model="minCount">
            <!-- 点击按钮，从getters 中获取闭包函数，并进行过滤处理 -->
            <button @click='search'>search</button>
            <p v-show='!isShowResult'>no goods</p>
            <!-- 显示处理结果 -->
            <ul v-show="isShowResult">
                <li v-for='(item, index) in filterResult' :key='index'>{{index+1}}--{{item.name}}--{{item.price.toFixed(2)}}--{{item.count}}</li>
            </ul>
            ```
            ```js
            export default {
                name: 'GoodsFilter',
                data(){
                    return {
                        minPrice:0,         // 过滤条件
                        minCount:0,         // 过滤条件
                        filterResult:null,  // 保存过滤结果
                    }
                },
                methods:{
                    search(){
                        // 1. 通过 getters 获取闭包函数
                        const filter = this.$store.getters.goodsFilter;
                        // 2. 通过闭包函数处理过滤参数，并保存结果
                        this.filterResult = filter(this.minPrice, this.minCount);
                    }
                },
                computed:{
                    // 根据过滤结果的数量来决定显示内容
                    isShowResult(){
                        return this.filterResult && this.filterResult.length > 0;
                    }
                }
            }
            ```

## Mutations
[top](#catalog)
- Mutations 是 Vuex中，state的**唯一更新方式**
- Mutation 函数可以视作两部分
    1. `回调函数`，该函数的第一个参数是 `$store.state`
    2. 字符串类型的`事件类型`，在组件中可以通过 `$store.commit('事件名')` 来修改数据

- 在 Mutations 中，只能在**同步**处理中修改状态
    - 如果需要在**异步**操作中修改状态，都交给 Actions 处理

- Mutation 的定义
    - 定义方式: `method(state, payload){...}`
    -  `state` 就是 `$store.state`
    - `payload` 是提交时，传递的外部数据
        - 可以省略
    - mutation**只支持两个参数**
        - 如果需要传递**多个参数**，需要**封装成对象**
    - 如:
        ```js
        export default new Vuex.Store({
            state: {
                stateA: '...',
            },
            mutations: {
                method(state, payload) {
                    state.stateA = '...';
                },
            }
        }
        ```
- Mutation 的提交
    - 通过指定 `Mutation` 事件名来提交 `Mutation`
        ```js
        // 1. 使用外部参数
        this.$store.commit('method', payload);

        // 2. 没有外部参数可以省略
        this.$store.commit('method');
        ```
    - 通过指定 `type` 属性来提交 `Mutation`
        - 提交方式
            ```js
            this.$store.commit({
                type: mutation事件名,
                params: 其他外部参数,
                ...
            })
            ```
        - 该方式下
            1. 能识别`mutation`方法
            2. `payload` 中<span style='color:red'>会包含 `type` 属性</span>，使用时需要注意

- 示例
    - 浏览器入口
        - http://localhost:8080/goodsList
    - Mutation 配置
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/index_bk.js](src/vuex/vuex-demo01/src/store/index_bk.js)
        - 代码内容
            ```js
            export default new Vuex.Store({
              state: {
                // 2. 商品列表
                goodsList: [
                  { name: 'goods---aaa', price: 11, count: 1 },
                  { name: 'goods---bbb', price: 22, count: 2 },
                  { name: 'goods---ccc', price: 33, count: 3 },
                  { name: 'goods---ddd', price: 55, count: 4 },
                ]
              },
              mutations: {
                // 2.1 添加商品， 普通提交方式
                appendGoods(state, payload){
                  state.goodsList.push(payload);
                },
                // 2.2 添加商品， 通过 type 属性提交
                appendGoodsByType(state, payload){
                  console.log(payload.type);
                  // 使用时，需要排除 type 属性
                  state.goodsList.push({
                    name:payload.name,
                    price:payload.price,
                    count:payload.count,
                  });
                }
              },
            }
            ```
    - Mutation 的使用
        - 参考代码
            - [src/vuex/vuex-demo01/src/views/goodsList/GoodsShow.vue](src/vuex/vuex-demo01/src/views/goodsList/GoodsShow.vue)
        - 代码内容
            ```js
            export default {
              name: "GoodsShow",
              data() {
                return {
                  goodsName: "",
                  goodsPrice: 0,
                  goodsCount: 0
                };
              },
              methods: {
                // 通过普通方式提交
                append() {
                  // 调用 mutations，传递商品对象，并执行添加操作
                  this.$store.commit("appendGoods", {
                    name: this.goodsName,
                    price: Number(this.goodsPrice),
                    count: Number(this.goodsCount)
                  });

                  this.dataInit();
                },
                // 通过 type 属性提交
                appendByType() {
                  // 调用 mutations，传递商品对象，并执行添加操作
                  this.$store.commit({
                    type: "appendGoodsByType",
                    name: this.goodsName,
                    price: Number(this.goodsPrice),
                    count: Number(this.goodsCount)
                  });
                  this.dataInit();
                },
                dataInit(){
                  this.goodsName = "";
                  this.goodsPrice = 0;
                  this.goodsCount = 0;
                }
              }
            };
            ```

## 响应式的添加与删除State中的状态
[top](#catalog)
- `Vuex.store` 中的 `state` 是响应式的
    - 当 `state` 中的数据发生变化时，Vue组件会自动更新
    - 这些状态数据将会添加到**Vue的响应式系统**中，Vue会观察这些数据是否发生变化

- `state` 的响应式处理需要遵守的规则
    - 提前在 `store` 中初始化所需的属性
    - 如果直接删除或增加数据，会导致:
        1. Vue无法进行观察
        2. 无法做响应式处理
        3. 无法实时刷新页面
- 响应式的操作 `state` 的方式
    - 添加方法
        - 两种添加方法
            1. 方式1: `Vue.set(obj, '属性名', 123)`
            2. 方式2: 用新对象给旧对象重新赋值
        - 数组的某些方法默认是响应式的，所以直接操作即可
    - 删除方法
        - `Vue.delete(obj, '属性名')`
    - 参数中的 `obj` 可以是
        1. `$store.state`，即直接添加新的状态
        2. `$store.state.obj`，即修改某个 `Object` 类型的状态对象中的属性

- 示例
    - 浏览器入口
        - http://localhost:8080/goodsList
    - mutations 设置
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/index_bk.js](src/vuex/vuex-demo01/src/store/index_bk.js)
        - 代码内容
            ```js
            export default new Vuex.Store({
              state: {
                addressInfo:{
                  address: 'test house',
                }
              },
              mutations: {
                // 3. 添加删除 state 中的属性
                // 3.1 删除一个状态对象中的属性
                deleteAddress(state){
                  Vue.delete(state.addressInfo, 'address');
                },
                // 3.2 向一个状态对象中，添加属性
                addAddress(state){
                  Vue.set(state.addressInfo, 'address', 'new address');
                },
                // 3.3 添加一个新的状态
                addOther(state){
                  Vue.set(state, 'other', 'otherInfo');
                }
              },
            }
            ```
    - Mutation 的使用
        - 参考代码
            - [src/vuex/vuex-demo01/src/views/goodsList/GoodsShow.vue](src/vuex/vuex-demo01/src/views/goodsList/GoodsShow.vue)
        - 代码内容
            ```html
            <div>
              <p>address: {{$store.state.addressInfo.address}}</p>
              <p>other: {{$store.state.other}}</p>
              <button @click='deleteAddress'>deleteAddress</button>
              <button @click='addAddress'>addAddress</button>
              <button @click='addother'>addother</button>
            </div>
            ```
            ```js
            export default {
              name: "GoodsShow",
              methods: {
                // 响应式的向 state 中添加、删除属性
                deleteAddress(){
                  this.$store.commit('deleteAddress');
                },
                addAddress(){
                  this.$store.commit('addAddress');
                },
                addother(){
                  this.$store.commit('addOther');
                }
              }
            };
            ```

## Mutation常量
[top](#catalog)
- 当mutation中的状态增多时，mutation中的方法也会增多，增加管理和维护的难度
- 可以用常量来管理 `Mutation` 中的方法名
    1. 在 `store` 目录下创建额外的 js 文件作为 Mutation 常量模块
    2. 在 `store/index_bk.js` 中引入常量模块，通过**ES的计算属性**应用常量，来创建 mutation 方法
        ```js
        mutations: {
            // 通过 ES的计算属性，将常量作为方法名
            [常量名](){..}
        }
        ```
    3. 在组件中，引入常量模块，并通过常量来使用 mutation 方法
        ```js
        this.$store.commit(常量名);
        ```

- 示例
    - 浏览器入口
        - http://localhost:8080/stateCount/mutation
    - Mutation 常量模块
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/types.js](src/vuex/vuex-demo01/src/store/types.js)
        - 代码内容
            ```js
            export const INCREMENT = 'increment';
            export const DECREMENT = 'decrement';
            export const INCREMENTN = 'incrementN';
            ```
    - 在 `store/index_bk.js` 中引入常量模块
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/index_bk.js](src/vuex/vuex-demo01/src/store/index_bk.js)
        - 代码内容
            ```js
            import * as types from './types';
            export default new Vuex.Store({
              state: {
                // 1. 计数器
                counter: 1,
              },
              mutations: {
                // --------------------------------------------------------
                // 使用常量管理 Mutation
                // 1. 计数器 counter 处理
                [types.INCREMENT](state) {
                  state.counter++;
                },
                [types.DECREMENT](state) {
                  state.counter--;
                },

                // 每次为 counter 增加 n
                [types.INCREMENTN](state, n, b, c) {
                  console.log(`n=${n}, b=${b}, c=${c}`);
                  state.counter += n;
                },
              }
            }
            ```
    - 在组件中，使用常量模块
        - 参考代码
            - [src/vuex/vuex-demo01/src/views/stateCount/countMutation.vue](src/vuex/vuex-demo01/src/views/stateCount/countMutation.vue)
        - 代码内容
            ```js
            import {INCREMENT, DECREMENT, INCREMENTN} from '@/store/types'
            export default {
              name: 'CountMutation',
              methods:{
                // 通过 Mutation 常量来执行对应的 Mutation 方法
                add(){
                  this.$store.commit(INCREMENT);
                },
                sub(){
                  this.$store.commit(DECREMENT);
                },
                add10(){
                  this.$store.commit(INCREMENTN, 10, 'bbb', 'ccc');
                },
              }
            }
            ```

## Actions
[top](#catalog)
- Actions 用于在异步处理中修改状态
- Actions 只用于处理异步修改，不能替代 Mutation
    - 在 Actions 内部处理异步操作，最终仍然需要通过 `commit` 调用 Mutation 来修改状态
- Actions 定义
    - 定义方式
        ```js
        export default new Vuex.Store({
            actions: {
                method(context, payload){
                    // 调用某个 Mutation 来修改状态
                    context.commit('mutation方法', payload);

                    return ...;
                }
            }
        }
        ```
    - 参数 `context` 相当于 `$store`，可以直接执行 `commit` 来调用 Mutation
    - 参数 `payload` 与 Mutation 中的用法相同
    - **返回值**将会返回给 action 的调用者
- Actions 的使用
    ```js
    // 可以获取 action 返回的结果
    const result = this.$store.dispatch('atcions方法名', payload);
    ```

- 使用 Promise 处理异步处理的结果
    1. 在定义 action 时，返回一个 Promise 对象
        ```js
        method(context, payload){
            // 返回一个 Promise 对象
            return new Promise((resolve, reject)=>{
                // 调用某个 Mutation 来修改状态
                context.commit('mutation方法', payload);
                // 返回异步处理的结果
                resolve(...);  // reject(...)
            });
        }
        ```
    2. 在组件中调用时，接受 `dispatch` 的返回值
    3. 该返回值就是 Promise 对象，再调用 `then`、`catch` 方法来处理异步操作的结果
        ```js
        this.$store
            .dispatch('action方法名', payload)
            .then(onResolve, onRejected)
            .catch(onRejected);
        ```

- 参数 `context` 中的可用属性，相当于 `$store`，可以访问vuex的核心属性

    |属性|说明|
    |-|-|
    |commit|提交函数，用于调用**当前store的** mutation|
    |dispatch|用于调用**当前store的**其他 action|
    |getters|获取getters|
    |rootGetters|指向自身的 getters|
    |rootState|指向自身的 state|
    |state|获取 state|

- 示例
    - 浏览器入口
        - http://localhost:8080/stateCount/mutation
    - action 配置
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/index_bk.js](src/vuex/vuex-demo01/src/store/index_bk.js)
        - 代码内容
            ```js
            export default new Vuex.Store({
              state: {
                // 1. 计数器
                counter: 1,
              },
              mutations: {
                // 1. 计数器 counter 处理
                [types.INCREMENTN](state, a, b, c) {
                  console.log(`a=${a}, b=${b}, c=${c}`);
                  state.counter += a;
                }
              },
              // 在异步操作中修改状态
              actions: {
                [types.ADDXDELAY](context, x){
                  // 1. 返回一个 Promise 对象，由调用者处理异步处理的结果
                  return new Promise((resolve, reject)=>{
                    setTimeout(() => {
                      // 2. 通过 commit，调用 mutation 修改状态
                      context.commit(types.INCREMENTN, x);
                      // 3. 修改 promise 状态，并返回处理结果
                      resolve('commit success');
                    }, 1000);
                  })
                }
              },
            })
            ```
    - action 的调用
        - 参考代码
            - [src/vuex/vuex-demo01/src/views/stateCount/countMutation.vue](src/vuex/vuex-demo01/src/views/stateCount/countMutation.vue)
        - 代码内容
            ```js
            // 通过 action 异步修改状态
            add3Delay(){
              this.$store
                  .dispatch(ADDXDELAY, 3)             // 1. 通过 action 异步修改状态
                  .then(value=>console.log(value));   // 2. 处理异步的返回结果
            }
            ```

## Modules
[top](#catalog)
- Modules 的功能
    1. 将相关的 state 及其操作封装到一个模块里
    2. 降低**根模块**的复杂度
- 推荐的模块嵌套层次: **最多一层**
    ```js
    export default new Vuex.Store({
        modules:{
            moduleA:{
                state:{},
                mutations:{},
                getters:{},
                actions:{},
            }
            moduleB:{
                state:{},
                mutations:{},
                getters:{},
                actions:{},
            }
        }
    })
    ```
- 相关内容封装到 Module 后，产生的**行为变化**
    - state
        - 调用方式改变: `this.$store.state.模块名.状态名`
    - getters
        - 调用方式不变: `this.$store.getters.getter方法名`
        - getter定义
            - 定义方式
                ```js
                getters: {
                    method(state, getters, rootState){...}
                }
                ```
            - 增加了第三个参数 `rootState`，表示根模块中的 `state`
                - 通过该参数可以访问根模块中的 `state`
    - mutations
        - 调用方式不变: `this.$store.commit('mutation方法名')`
        - 多个模块内的方法**不要同名**
        - mutation 方法的搜索过程
            1. `commit` 时，会先在根模块下搜索
            2. 如果根模块下没有，再到子模块中搜索
    - actions
        - `method(context, payload)` 中的 `context` 参数内的可用属性

            |属性|说明|
            |-|-|
            |commit|提交函数，可以访问**当前模块和其他模块**的 mutation|
            |dispatch|可以调用**当前模块和其他模块**的 action|
            |getters|访问**当前模块和其他模块**的 getters|
            |rootGetters|指向**根模块**的 getters|
            |rootState|指向**根模块**的 state|
            |state|获取**当前module的** state|

- 示例
    - 浏览器入口
        - http://localhost:8080/userInfo
    - module 配置
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/modules/user.js](src/vuex/vuex-demo01/src/store/modules/user.js)
        - 代码内容
            ```js
            export default {
              state:{
                name:'bob',
                age:22,
                address:"mine house",
                score: 100
              },
              getters:{
                fullAddress(state, _, rootState){
                  // 通过 rootState，访问【根模块】中的数据
                  return `${rootState.addressInfo.address} + ${state.address}`;
                },
                fullInfo(state, getters){
                  return `name:${state.name}, age:${state.age}, address:${getters.fullAddress}`
                },
              },
              // 修改当前模块内的 state
              mutations:{
                incrUserAge(state){
                  state.age++;
                },
                decrUserAge(state){
                  state.age--;
                },
                clearScore(state){
                  state.score = 0;
                }
              },
              actions:{
                clearScoreDelay(context){
                  setTimeout(() => {
                    context.commit('clearScore');
                  }, 500);
                },
                incrUserAgeDelay(context){
                  console.log(context);
                  setTimeout(() => {
                    // 调用当前模块内的 mutation
                    context.commit("incrUserAge");
                    // 调用根模块内的 mutation
                    context.commit(types.INCREMENT);

                    // 调用当前模块内容的 action
                    context.dispatch('clearScoreDelay');
                    // 调用根模块中的 action
                    context.dispatch(types.ADDXDELAY, 100);

                    // 获取其他模块的 getters
                    console.log(context.getters.powerCount);
                  }, 1000);
                }
              },
            }
            ```
    - 在根模块中导入
        - 参考代码
            - [src/vuex/vuex-demo01/src/store/index_bk.js](src/vuex/vuex-demo01/src/store/index_bk.js)
        - 代码内容
            ```js
            import user from './modules/user';
            export default new Vuex.Store({
              modules: {
                user    // 导入其他 module
              }
            })
            ```
    - 在组件中使用 module 中的内容
        - 参考代码
            - [src/vuex/vuex-demo01/src/views/userinfo/UserInfo.vue](src/vuex/vuex-demo01/src/views/userinfo/UserInfo.vue)
        - 代码内容
            ```html
            <!-- 使用 module 中的 state -->
            <p>name: {{$store.state.user.name}}</p>
            <p>score: {{$store.state.user.score}}</p>

            <p>age: {{$store.state.user.age}}</p>
            <!-- 调用 module 中的 mutation -->
            <button @click='incrAge'>+</button>
            <button @click='decrAge'>-</button>

            <!-- 调用 module 中的 action -->
            <button @click='incrAgeDelay'>+ delay</button>

            <!-- 调用 module 中的 getter -->
            <p>fullInfo: {{$store.getters.fullInfo}}</p>
            ```
            ```js
            export default {
              name:'UserInfo',
              methods:{
                // 调用 mutation
                incrAge(){
                  this.$store.commit('incrUserAge')
                },
                decrAge(){
                  this.$store.commit('decrUserAge')
                },
                // 调用 action
                incrAgeDelay(){
                  this.$store.dispatch('incrUserAgeDelay');
                }
              }
            }
            ```

# Vuex的推荐目录结构
[top](#catalog)
- 推荐结构
    ```
    store
      ├─ index.js       <<<<< 管理根模块 Vuex
      ├─ actions.js     <<<<< 管理根模块的 actions
      ├─ mutations.js   <<<<< 管理根模块的 mutations
      └─ modules.js
           ├─ mA.js
           ├─ mB.js
           └─ mC.js
    ```
- 可以再额外添加一个 `types.js` 文件，用于管理 `mutation` 常量