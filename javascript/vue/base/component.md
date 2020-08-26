<span id="catalog"></span>

### 目录---组件化开发
- [Vue组件简介](#Vue组件简介)
- [组件的基本使用步骤](#组件的基本使用步骤)
- [全局组件和局部组件](#全局组件和局部组件)
- [父组件和子组件](#父组件和子组件)
- [组件的注册方法](#组件的注册方法)
- [组件模板抽离](#组件模板抽离)
- [组件的数据](#组件的数据)
    - [组件数据的基本使用](#组件数据的基本使用)
    - [为什么组件的data必须是Function](#为什么组件的data必须是Function)
- [组件的搜索与渲染过程](#组件的搜索与渲染过程)
- [父子组件通信](#父子组件通信)
    - [父子组件通信的方法与应用场景](#父子组件通信的方法与应用场景)
    - [props--父级向子级传递](#props--父级向子级传递)
        - [props基本介绍](#props基本介绍)
        - [props的设置与传值方法](#props的设置与传值方法)
        - [使用字符串数组设置props](#使用字符串数组设置props)
        - [使用对象设置props](#使用对象设置props)
        - [处理子组件中使用驼峰命名的props名](#处理子组件中使用驼峰命名的props名)
    - [emit自定义事件--子级向父级传递](#emit自定义事件--子级向父级传递)
    - [props与emit的综合使用](#props与emit的综合使用)
        - [props属性与双向绑定](#props属性与双向绑定)
        - [props双向绑定同步修改父组件数据--手动实现](#props双向绑定同步修改父组件数据--手动实现)
        - [props双向绑定同步修改父组件数据--watch属性实现](#props双向绑定同步修改父组件数据--watch属性实现)
    - [父子组件间的访问](#父子组件间的访问)
        - [父子组件访问的本质](#父子组件访问的本质)
        - [父组件访问子组件--$children与$refs](#父组件访问子组件--$children与$refs)
        - [子组件访问父组件--$parent与$root](#子组件访问父组件--$parent与$root)
- [插槽slot](#插槽slot)
    - [插槽slot简介](#插槽slot简介)
    - [插槽的基本使用](#插槽的基本使用)
    - [具名插槽](#具名插槽)
    - [编译作用域](#编译作用域)
    - [作用域插槽](#作用域插槽)


# Vue组件简介
[top](#catalog)
- Vue的组件化
    - Vue组件化提供一种抽象，使开发者可以开发出可复用的组件来构成复杂的应用
    - 任何的应用都会被抽象成一颗: **组件树**
        - ![conponent_tree](imgs/component/conponent_tree.png)

    - 通过组件化，可以让代码便于组织和管理，扩展型更强

- 组件化的应用
    - 开发时，将页面尽可能的拆分成多个小的、可复用的组件

- 组件与Vue实例
    - 组件中包含了template、data、methods 等属性，与Vue实例类似
    - 组件的原型是指向 `Vue` 的

# 组件的基本使用步骤
[top](#catalog)
- 组件使用的3个步骤
    1. 创建组件构造器，调用 `Vue.extend()`
    2. 注册组件，调用 `Vue.component()`
    3. 在Vue实例的作用范围内使用组件

- `Vue.extend()`
    - 调用 `Vue.extend()`，会创建一个 `组件构造器`
    - 通常在创建组件构造器时，会传入 `template`，即自定义的组件模板
        - `template` 是使用组件时，需要显示的html
    - 在Vue2.x以后，更推荐使用语法糖来创建组件构造器，但是底层调用的仍然是 `Vue.extend()`
        - 参考: [组件的注册方法](#组件的注册方法)

- `Vue.component('组件名', 组件构造器对象)`
    - 用于将 `组件构造器` 注册为一个全局组件，并为组件设置组件名

- 示例
    - 参考代码
        - [src/component/base/base.html](src/component/base/base.html)
    - 代码内容
        1. 创建组件构造器
            ```js
            const cpn = Vue.extend({
                template:`
                    <div>
                        <p>test1</p>
                        <p>test2</p>
                        <p>test3</p>
                    </div>
                `
            });
            ```
        2. 注册组件
            ```js
            Vue.component('new-cpn', cpn);

            const app = new Vue({
                el: '#app'
            });
            ```
        3. 使用组件
            ```html
            <div id="app">
                <!-- 3. 在Vue实例的作用范围，多次内使用组件 -->
                <new-cpn></new-cpn>
                <new-cpn></new-cpn>
            </div>
            ```

# 全局组件和局部组件
[top](#catalog)
- 全局组件
    - 在全局作用域中，通过 `Vue.component()` 注册的组件
    - 可以在多个Vue实例中使用
- 局部组件
    - 在某个Vue实例的 `components` 属性中**注册**的组件
    - 只能在Vue实例内部使用，外部无法使用
    - 注册方法
        ```js
        const app = new Vue({
            el: '#app1',
            // 在 components 属性内注册局部组件
            components: {
                // 组件名: 组件构造器
                '组件名': Vue.extend({
                        template:`...`
                    }),
            }
        });
        ```
- **局部组件和Vue实例可以看作一种特殊的父子组件**

- 示例
    - 参考代码
        - [src/component/base/global_loacl.html](src/component/base/global_loacl.html)
    - 代码内容
        - js代码
            ```js
            // 1. 创建全局组件，并注册
            const globalCpn = Vue.extend({
                template:`
                    <div>
                        <p>global test1</p>
                        <p>global test2</p>
                    </div>`
            });

            Vue.component('global-cpn', globalCpn);

            const app1 = new Vue({
                el: '#app1',
                // 2. 在 components 属性内注册局部组件
                components: {
                    // 组件名: 组件构造器
                    'local-cpn': Vue.extend({
                            template:`
                                <div>
                                    <p>local test1</p>
                                    <p>local test2</p>
                                </div>`
                        }),
                }
            });

            const app2 = new Vue({
                el: '#app2'
            });
            ```
        - html代码
            ```html
            <!-- 3. Vue实例1 -->
            <div id="app1">
                <div>---------this is app1---------</div>
                <!-- 使用全局组件 -->
                <global-cpn></global-cpn>
                <!-- 使用局部组件 -->
                <local-cpn></local-cpn>
            </div>

            <!-- 4. Vue实例2 -->
            <div id="app2">
                <div>---------this is app2---------</div>
                <!-- 使用全局组件 -->
                <global-cpn></global-cpn>
                <!-- 使用 app1 的局部组件。（浏览器无法识别标签，无法渲染 ）-->
                <local-cpn></local-cpn>
            </div>
            ```

# 父组件和子组件
[top](#catalog)
- 使用 `Vue.extend()` 创建组件构造器时，可以通过 `components` 属性注册子组件
    ```js
    Vue.extend({
        tempalte:`...`,
        components: {
            '子组件名': 子组件构造器
        }
    })
    ```
- 普通的Vue实例也可以**视作一种父组件---根组件**。它内部包含了其他组件
- 对于Vue实例，在使用父组件时，父组件的模板已经确定下来了，所以Vue实例无法感知到子组件的存在
    - 如果在Vue实例中使用了子组件，会报错

- 示例
    - 参考代码
        - [src/component/base/parent_child.html](src/component/base/parent_child.html)
    - js代码
        ```js
        // 创建一个子组件
        const child = Vue.extend({
            template:`
                <div>
                    <p>this is child</p>
                    <p>child test</p>
                </div>`
        })
        const parent = Vue.extend({
            template:`
                <div>
                    <p>this is parent</p>
                    <p>parent test</p>

                    <!-- 使用子组件 -->
                    <child-cpn></child-cpn>
                </div>`,
            // 在父组件中，注册子组件
            components:{
                'child-cpn':child,
            }
        })

        const app = new Vue({
            el:"#app",
            data:{
                msg:'testmsg'
            },
            // 注册父组件
            components:{
                'parent-cpn': parent,
            }
        });
        ```
    - html代码
        ```html
        <div id="app">
            <!-- 使用父组件 -->
            <parent-cpn></parent-cpn>
        </div>
        ```

# 组件的注册方法
[top](#catalog)
- 两种注册方法
    1. 原生方法：创建构造器对象--> 注册
        ```js
        // 创建组件构造器对象
        const 组件构造器对象 = Vue.extend({
            template: `...`,
            ...
        })
        // 注册
        Vue.component('组件名', 组件构造器对象)
        ```
    2. `Vue.component` 的语法糖：直接将`extend()` 方法中的对象作为参数注册组件
        - 底层使用的仍然是 `Vue.extend()`
        - 全局组件
            ```js
            Vue.component('组件名', {
                template: `...`,
                ...
            });
            ```
        - 局部组件
            ```js
            new Vue({
                el:'#app',
                components:{
                    '组件名':{
                        template: `...`,
                        ...
                    }
                }
            });
            ```
        - 子组件
            ```js
            new Vue({
                template:`...`,
                component:{
                    '组件名':{
                        template: `...`,
                        ...
                    }
                }
            });
            ```

- 示例
    - 参考代码
        - [src/component/base/registe_component.html](src/component/base/registe_component.html)
    - js代码
        ```js
        // 1. 原生方法创建并注册组件
        const cpn1 = Vue.extend({
            template:`
                <div>
                    <p>this is cpn1</p>
                </div>
            `
        });

        Vue.component('cpn1', cpn1);

        // 2. 使用语法糖创建并注册全局组件
        Vue.component('cpn2', {
            template:`
                <div>
                    <p>this is cpn2</p>
                    <cpn2-child></cpn2-child>
                </div>
            `,
            // 注册子组件
            components: {
                'cpn2-child':{
                    template:`
                        <div>
                            <p>this is cpn2 child</p>
                        </div>
                    `
                }
            }
        });

        // 3. 使用语法糖创建并注册局部组件
        const app = new Vue({
            el: '#app',
            components:{
                'main-cpn':{
                    template:`
                        <div>
                            <p>this is main cpn</p>
                        </div>
                    `
                }
            }
        })
        ```
    - html代码
        ```html
        <div id="app">
            <!-- 原生方法创建并注册组件 -->
            <cpn1></cpn1>
            <!-- 使用语法糖注册的全局组件 -->
            <cpn2></cpn2>

            <!-- 使用在Vue实例内部注册的局部组件 -->
            <main-cpn></main-cpn>
        </div>
        ```

# 组件模板抽离
[top](#catalog)
- 两种定义html模板的方式
    - 使用 script 标签
        ```html
        <script type='text/x-template' id='模板id'>
            模板内容...
        </script>
        <script type='text/javascript'>
            Vue.component('组件id', {
                template: '#模板id' // 通过 css选择器语法，关联模板和组件
            })
        </script>
        ```
    - 使用 template 标签，**推荐使用**。在template内部，<span style='color:red'>最好将html都写在 `div` 之内</span>
        ```html
        <template id='模板id'>
            <div>
                模板内容...
            </div>
        </template>
        <script type='text/javascript'>
            Vue.component('组件id', {
                template: '#模板id' // 通过 css选择器语法，关联模板和组件
            })
        </script>
        ```
- 示例
    - 参考代码
        - [src/component/base/exract_template.html](src/component/base/exract_template.html)
    - 代码内容
        ```html
        <div id="app">
            <!-- 4. 使用组件 -->
            <cpn01></cpn01>
            <cpn01></cpn01>
            <cpn02></cpn02>
            <cpn02></cpn02>
        </div>

        <!-- 1. 使用 script 标签创建模板 -->
        <script type='text/x-template' id='cpn01-template'>
            <div>
                <p>this is cpn01</p>
                <p>this is cpn01 test</p>
            </div>
        </script>

        <!-- 2. 使用 template 标签创建模板 -->
        <template id='cpn02-template'>
            <div>
                <p>this is cpn02</p>
                <p>this is cpn02 test</p>
            </div>
        </template>

        <script type='text/javascript' src='../js/vue.js'></script>
        <script type='text/javascript'>
            const app = new Vue({
                el: '#app',
                // 3. 关联模板和组件
                components:{
                    cpn01:{ template: '#cpn01-template'},
                    cpn02:{ template: '#cpn02-template'},
                }
            });
        </script>
        ```

# 组件的数据
## 组件数据的基本使用
[top](#catalog)
- 组件无法访问: Vue实例 或 父组件 中的data
    - 如果组件可以访问那些数据，Vue实例 或 父组件 会变得不好维护
- 组件数据的保存
    - 组件的 `data` 属性用于保存组件数据
- 组件 `data` 属性的类型
    - **必须是 `Function` 类型**，并返回一个实例对象，这个实例对象中保存数据
    - 实例对象中的**属性**可以通过 `{{}}` 语法插入到页面

- 示例
    - 参考代码
        - [src/component/data/component_data.html](src/component/data/component_data.html)
    - 代码内容
        ```html
        <div id="app">
            <div>{{msg}}</div>
            <cpn1></cpn1>
        </div>

        <!-- 组件模板，变量名与Vue实例同名，但是不会冲突 -->
        <template id='cpn1-template'>
            <div>{{msg}}</div>
        </template>

        <script type='text/javascript' src='../js/vue.js'></script>
        <script type='text/javascript'>
            // 关联组件与模板，并设置数据
            Vue.component('cpn1', {
                template: '#cpn1-template',
                data(){
                    return {
                        msg:'con1msg'
                    }
                }
            })
            const app = new Vue({
                el: '#app',
                data:{
                    msg:'appmsg',
                }
            });
        </script>
        ```

## 为什么组件的data必须是Function
[top](#catalog)
- <span style='color:red'>为什么组件的data必须是 Function</span>
    - 页面中，相同组件可以使用多次
        - 即: 相同组件可以有多个实例
    - 如果 data 是 Object类型，会导致**相同组件间共享一份数据，操作时会互相干扰**
    - 每个组件实例都通过调用 Function 返回一个 Object ，可以使组件间的数据相互隔离

- 相同组件的多个实例共享数据的方法
    - 共享方法
        1. 在组件的外部作用域创建一个通用的数据对象
        2. 在data中，每次调用都返回这个通用对象
    - 写法
        ```js
        const obj = {...};
        Vue.component('组件名', {
            template:'...',
            data(){ return obj; } // 每次调用都返回 obj，来共享数据
        })
        ```
    - 一般不推荐共享数据的写法，会导致组件间相互干扰

- 示例
    - 参考代码
        - [src/component/data/component_data_must_function.html](src/component/data/component_data_must_function.html)
    - 代码内容
        ```html
        <div id="app">
            <!-- 3. 多次使用相同组件，组件的数据不会互相干扰 -->
            <counter></counter>
            <counter></counter>
        </div>
        <!-- 1. 设置组件模板 -->
        <template id='counter-template'>
            <div>
                <p>{{count}}</p>
                <button @click='add'>+</button>
                <button @click='sub'>-</button>
            </div>
        </template>
        <script type='text/javascript' src='../js/vue.js'></script>
        <script type='text/javascript'>
            // 2. 创建组件，并设置组件的数据和方法
            Vue.component('counter', {
                template: '#counter-template',
                data(){
                    return {count:0}
                },
                methods:{
                    add(){ this.count++; },
                    sub(){ this.count--; }
                }
            })

            const app = new Vue({
                el:'#app',
                data:{},
            });
        </script>
        ```

# 组件的搜索与渲染过程
[top](#catalog)
- 对于Vue实例和父组件
    - 先在内部的 `components` 属性中搜索已注册的组件
    - 如果内部没有，则开始搜索全局组件
    - 如果全局组件也没有，会报错

# 父子组件通信
## 父子组件通信的方法与应用场景
[top](#catalog)
- 父子组件通信 的应用场景
    - 组件的数据一般是来自网络或者向服务器请求
    - 当多个组件都需要数据时，会将请求数据的操作封装到父组件中
    - 由父组件统一获取数据，然后交给子组件，减少网络请求数据量
- 父子组件通信的方法
    1. 通过 `props` 向子组件传递数据
    2. 通过 `emit event` 事件向父组件发送消息
        ```
               传送 Props
          ┌──────────────────────┐
          │                      │
          │                      V
        Parent(父组件)      Child(子组件)
          ^                      │
          ^                      │
          └──────────────────────┘
                emit event
        ```
- 在实际开发中，Vue实例与子组件(局部组件)通信、父组件和子组件的通信过程是一样的

## props--父级向子级传递
### props基本介绍
[top](#catalog)
- 在组件中，使用选项: `props`，来声明需要从父组件接收的数据
- props的值有两种方式
    1. 字符串数组
        - 每个字符串都是传递参数时的名称
    2. 对象
        - 最常用的方式，主要用于设置数据的验证方式

- props的使用方式
    - 在模版中，可以使用插值语法插入某个props
    - 通过 `this.props属性名` 来使用接收的数据

### props的设置与传值方法
[top](#catalog)
1. 在模版中定义需要使用的props
    ```html
    <!-- 在子组件模板中使用 props -->
    <template id='子组件模板'>
        <div>
            <p>{{props1}}</p>
            <p>{{props2}}</p>
        </div>
    </template>
    ```
2. 定义子组件构造器，可以直接在Vue实例或父组件中直接定义
    ```js
    const 子组件构造器 = {
        template: '#子组件模板',
        // 使用字符串数组设置 props
        props: ['参数1', '参数2'],
        // 或者使用对象设置 props
        // props: {参数1: 类型, 参数2: 类型},
    }
    ```
3. 在父组件中，设置数据、设置子组件
    ```js
    const app = new Vue({
        el: '#app',
        // 设置数据
        data:{
            父组件参数1: ...,
            父组件参数2: ...,
        },
        // 定义子组件
        components:{
            子组件名: 子组件构造器
        }
    });
    ```
4. 传递数据---在父组件的模板中，通过 props 向子组件传递数据
    - 通过 `v-bind` 将父组件数据传给子组件的 props
        ```html
        <!-- 父组件 -->
        <div id="app">
            <!-- 将父组件的数据传给子组件的props -->
            <子组件名 v-bind:参数1='父组件参数1' v-bind:参数1='父组件参数2'>
            </子组件名>
        </div>
        ```
    - 直接传递一个字符串，**一般不推荐这样使用**
        ```html
        <!-- 父组件 -->
        <div id="app">
            <!-- 使用字符串的方式传值 -->
            <子组件名 参数1='abcde' 参数1='12345'>
            </子组件名>
        </div>
        ```

### 使用字符串数组设置props
[top](#catalog)
- 字符串数组
    - 每个字符串都是传递参数时的名称
- 示例
    - 参考代码
        - [src/component/props/props_string_array.html](src/component/props/props_string_array.html)
    - 代码内容
        1. 在模版中定义需要使用的props
            ```html
            <template id='child-template'>
                <div>
                    <p>{{title}}</p>
                    <ul>
                        <li v-for='item in showlist'>{{item}}</li>
                    </ul>
                    <p>{{end}}</p>
                </div>
            </template>
            ```
        2. 定义子组件构造器
            ```js
            const child = {
                template: '#child-template',
                // 通过 字符串数组 设置子组件的 props，
                props:['showlist', 'title', 'end'],
            };
            ```
        3. 在父组件中，设置数据、注册子组件
            ```js
            const app = new Vue({
                el: '#app',
                data:{  // 设置父组件的数据
                    typeList:['aaa', 'bbb', 'ccc'],
                    title:'this is type list'
                },
                components:{    // 注册子组件
                    'child-cpn': child,
                }
            });
            ```
        4. 传递数据
            ```html
            <div id="app">
                <!-- 4. 使用子组件，依照 props 中设置的名字
                        4.1 通过 v-bind 的方式传递数据
                            将 Vue实例的 typeList 传递到子组件的 showlist
                            将 Vue实例的 title 传递到子组件的 title
                        4.2 不使用 v-bind，直接传递一个字符串
                            向 end 传递一个字符串
                -->
                <child-cpn v-bind:showlist='typeList' :title='title' end='this is end'></child-cpn>
            </div>
            ```

### 使用对象设置props
[top](#catalog)
- 对象设置 props
    - 通过对象可以设置传递时的类型，主要用于**类型验证**
        - 类型验证默认支持的数据类型
            - String
            - Number
            - Boolean
            - Array
            - Object
            - Date
            - Function
            - Symbol
        - 当有自定义**构造函数**时，验证也支持自定义的类型
    - 对象中的props可以设置默认值

- 对象的几种设置方法
    <span id='props-object-setting'></span>
    ```js
    // 自定义类型
    function Person(name, age){
        this.name = name;
        this.age = age;
    }

    const child = {
        template: '#child-template',
        props:{
            prop1: String,          // 方式1: 设置单一类型
            prop2: [String, Number],// 方式2: 设置多个可能的类型
            prop3: {                // 方式3: 使用对象设置某个属性的验证方式
                type: String,
                required: true,     // 必须传递数据
            },
            prop4: {
                type:Number,
                default: 123,       // 设置默认值
            },
            // Object类型的默认值，必须使用Function，并返回一个对象，防止多个组件实例相互干扰
            prop5: {
                type: Object,
                default(){
                    return {msg:'this is default prop5'};
                }
            },
            // Array类型的默认值，必须使用Function，并返回一个数组，防止多个组件实例相互干扰
            prop6: {
                type: Array,
                default(){
                    return ['pro6-1', 'pro6-2', 'pro6-3'];
                }
            },
            prop7: {                // 方式4: 使用自定义验证函数
                validator: function(value){
                    return ['yes', 'no', 'giveup'].indexOf(value) !== -1;
                }
            },
            prop8: Person,          // 方式5: 使用自定义类型
        },
        computed: {
            // 在计算属性中利用 props
            personStr(){
                return `name=${this.prop8.name}, age=${this.prop8.age}`
            }
        }
    };
    ```

- 示例
    - 参考代码
        - [src/component/props/props_obj.html](src/component/props/props_obj.html)
    - 说明内容
        1. 在模版中定义需要使用的props
            ```html
            <template id='child-template'>
                <div>
                    <p>prop1 : {{prop1}}</p>
                    <p>prop2 : {{prop2}}</p>
                    <p>prop3 : {{prop3}}</p>
                    <p>prop4 : {{prop4}}</p>
                    <p>prop5 : {{prop5}}</p>
                    <p>prop6 : {{prop6}}</p>
                    <p>prop7 : {{prop7}}</p>
                    <p>prop8 : {{personStr}}</p> <!-- 调用计算属性 -->
                </div>
            </template>
            ```
        2. 定义子组件构造器
            - 参考上面的: [对象的几种设置方法](#props-object-setting)
        3. 在父组件中，设置数据、设置子组件
            ```js
            const app = new Vue({
                el: '#app',
                data:{          // 设置父组件的数据
                    prop1Value: 'this is prop1Value',
                    prop2Value: 888,
                    prop3Value: 'abcde',
                    prop4Value: 5678,
                    prop5Value: {p1:'aaa', p2:'bbb', p3:'ccc'},
                    prop6Value: ['item1', 'item2', 'item3', 'item4'],
                    prop7Value: 'no',
                    prop8Value: new Person('testPerson', 22),
                },
                components:{    // 设置子组件
                    'child-cpn': child,
                }
            });
            ```
        4. 传递数据
            ```html
            <div id="app">
                <!-- 4. 父组件给子组件传递数据 -->
                <child-cpn :prop1='prop1Value' :prop2='prop2Value' :prop3='prop3Value'
                :prop4='prop4Value' :prop5='prop5Value' :prop6='prop6Value'
                :prop7='prop7Value' :prop8='prop8Value'>
                </child-cpn>

                <!-- prop3不传值，prop7传一个不符合规则的值
                    控制台会报错，但是页面仍然可以正常显示
                -->
                <child-cpn :prop1='prop1Value' prop2='prop2Value'
                :prop4='prop4Value' :prop5='prop5Value' :prop6='prop6Value'
                prop7='aaas' :prop8='prop8Value'>
                </child-cpn>
            </div>
            ```

### 处理子组件中使用驼峰命名的props名
[top](#catalog)
- 默认情况下，父组件**不能**为直接为子组件中使用驼峰命名的props传值

- 子组件中用驼峰命名的 props名，通过 `v-bind` 传递数据时，需要**将驼峰分解，并用 `-` 连接**
- 示例
    - 参考代码
        - [src/component/props/props_camel.html](src/component/props/props_camel.html)
    - 代码内容
        1. 创建子组件模板
            ```html
            <template id='cpn-template'>
                <div>
                    <!-- 在子组件中，仍然通过驼峰命名的方式使用props -->
                    <div>childObj: {{childObj}}</div>
                    <div>childCpnMsg: {{childCpnMsg}}</div>
                </div>
            </template>
            ```
        2. 创建子组件构造器
            ```js
            const childCpn = {
                template: '#cpn-template',
                props:{
                    // 在子组件中，使用驼峰命名的方式声明 props
                    childObj: {
                        type:Object
                    },
                    childCpnMsg:{
                        type: String
                    }
                }
            }
            ```
        3. 在父组件中注册子组件
            ```js
            const app = new Vue({
                el: '#app',
                data:{
                    obj: {name:'testName', age:22},
                    msg: 'testMsg'
                },
                components:{
                    child: childCpn,
                }
            });
            ```
        4. 父组件给子组件中 **驼峰命名的props** 传递数据
            ```html
            <div id="app">
                <!-- 将子组件中的驼峰命名的props名分解，并用 `-` 连接 -->
                <child :child-obj='obj' :child-cpn-msg='msg'></child>
            </div>
            ```

## emit自定义事件--子级向父级传递
[top](#catalog)
- 什么时候需要自定义事件
    - 最常见的传递：子组件产生某个事件时，希望父组件能够监听到，并响应
    - 子组件需要向父组件传递数据

- 如何自定义事件
    ```js
    this.$emit('自定义事件名' [, 数据参数])
    ```
- 通过 `v-on` 监听自定义事件
    ```html
    <div>
        <子组件 v-on:自定义事件名='组件的响应函数'></子组件>
    </div>
    ```

- <span style='color:red'>在不通过Vue脚手架，而是手写代码时，`自定义事件名` 不要使用驼峰命名，应该分解命名并使用 `-` 连接</span>

- 基本使用步骤
    1. 在子组件中的某个方法中，创建自定义事件。（方法可以是子组件的某个事件监听函数）
        ```js
        methods:{
            方法名(item){
                // 通过自定义事件，向父组件发射一个事件
                // 事件名称, 事件参数
                this.$emit('自定义事件名' [, 数据参数]);
            }
        }
        ```
    2. 在父组件中监听
        ```html
        <div>
            <子组件 v-on:自定义事件名='组件的响应函数'></子组件>
        </div>
        ```
    3. 在父组件中定义方法来处理子组件发射的事件
        ```js
        methods:{
            组件的响应函数([数据参数,...]){
                // ...
            }
        }
        ```

- 示例
    - 参考代码
        - [src/component/emit/emit_event.html](src/component/emit/emit_event.html)
    - 代码内容
        1. 定义子组件的模板
            ```html
            <template id='child-template'>
                <div>
                    <button v-for='item in categories' @click='btnClick(item)'>{{item.name}}</button><br>
                </div>
            </template>
            ```
        2. 创建子组件的组件构造器
            ```js
            const childCpn = {
                template: '#child-template',
                // 设置模板中按钮的显示数据
                data(){
                    return {
                        categories: [
                            {id:'01', name:'aaa'},
                            {id:'02', name:'bbb'},
                            {id:'03', name:'ccc'},
                            {id:'04', name:'ddd'},
                            {id:'05', name:'eee'},
                        ]
                    };
                },
                methods:{
                    // 设置按钮的Click事件，并在click事件内，向父对象发射一个自定义事件
                    btnClick(item){
                        console.log(`btnClick: item.id = ${item.id}, item.name = ${item.name}`);
                        // 向父组件发射一个自定义事件
                        this.$emit('item-click', item)
                    }
                }
            }
            ```
        3. 在父组件中注册子组件
            ```js
            const app = new Vue({
                el: '#app',
                components:{
                    child: childCpn,
                },
                methods:{
                    // 设置子组件自定义事件的响应函数，并接受自定义事件发送的数据
                    childItemClick(item){
                        console.log(`childItemClick: item.id = ${item.id}, item.name = ${item.name}`);
                    }
                }
            });
            ```
        4. 使用子组件并监听子组件的事件
            ```html
            <div id="app">
                <!-- 父组件通过 v-on 监听子组件发射的事件 -->
                <!-- 需要将驼峰命名的事件名分解，并用 `-` 连接 -->
                <child @item-click='childItemClick'></child>
            </div>
            ```

## props与emit的综合使用
### props属性与双向绑定
[top](#catalog)
- **不能直接对 `props` 做双向绑定。修改数据时，控制台会报错**
- `props` 属性值做双向绑定的方法
    1. 方法1: 将属性值设置到 `data` 里的属性，在对data中的属性做双向绑定
    2. 方法2: 使用计算属性，并同时设置getter和setter，来操作 props中的数据

- 绑定过程的示意图
    - ![props_vmodel](imgs/component/props_vmodel.png)

- 示例
    - 参考代码
        - [src/component/props/vmodel_of_props.html](src/component/props/vmodel_of_props.html)
    - 代码内容
        1. 在父组件中设置数据
            ```js
            const app = new Vue({
                el: '#app',
                data:{
                    param1:'1234',
                    param2:'5678',
                },
                components:{
                    child: childCpn
                }
            });
            ```
        2. 在父组件将数据传递到子组件的 props 中
            ```html
            <div id="app">
                <child :num1='param1' :num2='param2'></child>
            </div>
            ```
        3. 在子组件中，将 props 的数据设置到data中
            ```js
            const childCpn = {
                template: '#child-template',
                props:{
                    num1: String,
                    num2: String,
                },
                data(){
                    return {
                        dnum1:this.num1,
                        dnum2:this.num2,
                    }
                }
            }
            ```
        4. 在子组件模板中，双向绑定属性
            ```html
            <template id='child-template'>
                <div>
                    <!-- 0. 如果直接对 props属性 做双向绑定，修改数据时，控制台会报错 -->
                    <!-- <p>num1: {{num1}}</p> -->
                    <!-- <input type="text" name="num1" id="num1" v-model="num1"> -->
                    <!-- <p>num2: {{num2}}</p> -->
                    <!-- <input type="text" name="num2" id="num2" v-model="num2"> -->

                    <!-- 2. 通过 data 属性的双向绑定，来操作父组件传给props的数据 -->
                    <p>num1: {{dnum1}}</p>
                    <input type="text" name="num1" id="num1" v-model="dnum1">
                    <p>num2: {{dnum2}}</p>
                    <input type="text" name="num2" id="num2" v-model="dnum2">
                </div>
            </template>
            ```

### props双向绑定同步修改父组件数据--手动实现
[top](#catalog)
- props双向绑定修改父组件数据的方法
    1. 在子组件中，将 v-model 拆分成 v-bind 和 v-on
        - `v-bind` 负责初期事件绑定
        - 在 `v-on` 的监听事件中，修改组件内和父组件的数据
            ```html
            <input type='...' :value="data中的属性名" @input='响应函数名'>
            ```
    2. 在响应函数中，将修改后的数据同步更新到: 当前组件内部 和 父组件
        ```js
        methods:{
            响应函数名(event){
                // 1. 将数据同步到当前组件内部
                this.data中的属性名 = event.target.value;
                // 2. 发射 emit 事件，将数组作为事件参数，同步到父组件中
                this.$emit('emit事件名', this.data中的属性名);
            },
        }
        ```
    3. 在父组件中，监听子组件的emit事件
        ```html
        <div id="app">
            <子组件 :props名='父组件中的数据' @emit事件名='emit事件响应函数'></子组件>
        </div>
        ```
    4. 在父组件中设置子组件emit事件的响应函数，将数据同步到父组件的 data 中
        ```js
        methods: {
            emit事件响应函数( [子组件发射的数据] ){
                // ...
            },
        }
        ```

- 绑定过程的示意图
    - ![props_vmodel_data_sync](imgs/component/props_vmodel_data_sync.png)

- 示例
    - 参考代码
        - [src/component/props/vmodel_of_props&emit.html](src/component/props/vmodel_of_props&emit.html)
    - 代码内容
        1. 在子组件中，将 v-model 拆分成 v-bind 和 v-on
            ```html
                <template id='child-template'>
                <div>
                    <!-- 1. 在子组件中，将 v-model 拆分成 v-bind 和 v-on
                        在 v-on 的监听事件中，修改组件内和父组件的数据
                    -->
                    <p>num1: {{dnum1}}</p>
                    <input type="text" name="num1" id="num1" :value="dnum1" @input='num1Change'>
                    <p>num2: {{dnum2}}</p>
                    <input type="text" name="num2" id="num2" :value="dnum2" @input='num2Change'>
                </div>
            </template>
            ```
        2. 在响应函数中，将修改后的数据同步更新到: 当前组件内部 和 父组件
            ```js
            const childCpn = {
                template: '#child-template',
                props:{
                    num1: Number,
                    num2: Number,
                },
                // 将 props数据设置到 data 中，通过双向绑定来操作数据
                data(){
                    return {
                        dnum1:this.num1,
                        dnum2:this.num2,
                    }
                },
                methods:{
                    // 2. 监听输入框修改事件，将修改后的数据同步更新到当前组件内部 和 父组件
                    num1Change(event){
                        // 同步组件内部的数据
                        this.dnum1 = event.target.value;
                        // 发射 emit 事件，将数组同步到父组件
                        this.$emit('num1-change', this.dnum1);
                    },
                    num2Change(event){
                        this.dnum2 = event.target.value;
                        this.$emit('num2-change', this.dnum2);
                    },
                }
            }
            ```
        3. 在父组件中，监听子组件的emit事件
            ```html
            <div id="app">
                <p>param1: {{param1}}</p>
                <p>param2: {{param2}}</p>
                <!-- 3. 在父组件中，监听子组件的emit事件 -->
                <child :num1='param1' :num2='param2'
                    @num1-change='param1Change' @num2-change='param2Change'>
                </child>
            </div>
            ```
        4. 在父组件中设置子组件emit事件的响应函数，将数据同步到父组件的 data 中
            ```js
            const app = new Vue({
                el: '#app',
                data:{
                    param1: 1234,
                    param2: 5678,
                },
                components:{
                    child: childCpn
                },
                methods: {
                    // 4. 在父组件中设置子组件emit事件的响应函数
                    // 事件接受到的是字符串，需要手动执行类型转换
                    param1Change(data){
                        this.param1 = parseFloat(data);
                    },
                    param2Change(data){
                        this.param2 = parseFloat(data);
                    },
                }
            });
            ```
- 示例扩展
    - 需求: 两个变量: `dnum1`、`dnum2` 互为倍数关系，一个值被修改时，需要同步修改另一个变量
    - 参考代码

        - [src/component/props/vmodel_of_props&emit_data_sync.html](src/component/props/vmodel_of_props&emit_data_sync.html)
    - 扩展部分的代码
        ```js
        // 子组件构造器
        const childCpn = {
            template: '#child-template',
            props:{
                num1: Number,
                num2: Number,
            },
            data(){
                return {
                    dnum1:this.num1,
                    dnum2:this.num2,
                }
            },
            // 2. 监听输入框修改事件，将修改后的数据同步更新到当前组件内部 和 父组件
            methods:{
                num1Change(event){
                    this.dnum1 = event.target.value;
                    this.$emit('num1-change', this.dnum1);

                    // 同时修改另一个变量的值
                    this.dnum2 = this.dnum1/100;
                    this.$emit('num2-change', this.dnum2);
                },
                num2Change(event){
                    this.dnum2 = event.target.value;
                    this.$emit('num2-change', this.dnum2);

                    // 同时修改另一个变量的值
                    this.dnum1 = this.dnum2 * 100;
                    this.$emit('num1-change', this.dnum1);
                },
            }
        }
        ```

### props双向绑定同步修改父组件数据--watch属性实现
[top](#catalog)
- 通过 `v-model` 和 组件构造器的`watch`属性，监听view层的变化，并同步到父组件
- 使用方法
    1. 在子组件的`watch`中，创建和 `data` 属性同名的方法，来监听数据的修改，并发射 emit事件
        ```js
        const 子组件构造器 = {
            template: '...',
            data(){
                return {
                    属性名1: ...
                }
            },

            // 在watch中创建和data中属性同名的方法
            watch:{
                // 每个方法可以有两个参数，分别表示：修改后的数据、修改前的数据
                属性名1(newValue [, oldValue]){
                    // 业务操作
                    // ...

                    // 发射emit事件，将新数据发送到父组件的响应函数
                    this.$emit('emit事件名', newValue);
                }
            }
        };
        ```
    2. 在组件模板中，通过 `v-model` 来绑定数据
        ```html
        <input type="..." v-model='属性名1'>
        ```

- watch 的执行流程
    - ?????

- 示例
    - 参考代码
        - [src/component/props/props_watch.html](src/component/props/props_watch.html)
    - 代码内容
        1. 在子组件模板中，使用 v-model 完成数据的双向绑定
            ```html
            <template id='child-template'>
                <div>
                    <p>dnum1: {{dnum1}}</p>
                    <input type="text" name="dnum1" id="dnum1" v-model='dnum1'>
                    <p>dnum2: {{dnum2}}</p>
                    <input type="text" name="dnum2" id="dnum2" v-model='dnum2'>
                </div>
            </template>
            ```
        2. 在watch中创建和data中属性同名的方法
            ```js
            const childCpn = {
                template: '#child-template',
                props:{
                    num1:Number,
                    num2:Number,
                },
                data(){
                    return {
                        dnum1: this.num1,
                        dnum2: this.num2,
                    }
                },

                // 2. 在watch中创建和data中属性同名的方法
                watch:{
                    dnum1(newValue){
                        console.log('this is dnum1');
                        this.dnum2 = newValue / 100;
                        this.$emit('num1-change', newValue);
                        // 每次修改时，会输出:  ?????
                        // this is dnum1
                        // this is dnum2
                        // this is dnum1
                    },
                    dnum2(newValue){
                        console.log('this is dnum2');
                        this.dnum1 = newValue * 100;
                        this.$emit('num2-change', newValue);
                    }
                }
            };
            ```
        3. 在父组件中，监听子组件的emit事件
            ```html
            <div id="app">
                <p>param1: {{param1}}</p>
                <p>param2: {{param2}}</p>
                <child :num1='param1' :num2='param2'
                    @num1-change='param1Change' @num2-change='param2Change'
                ></child>
            </div>
        4. 在父组件中设置子组件emit事件的响应函数，将数据同步到父组件的 data 中
            ```js
            const app = new Vue({
                el: '#app',
                data:{
                    param1: 12345,
                    param2: 23456,
                },
                components:{ child: childCpn},
                // 4. 在父组件中设置子组件emit事件的响应函数，将数据同步到父组件的 data 中
                methods:{
                    param1Change(newValue){
                        this.param1 = parseFloat(newValue);
                    },
                    param2Change(newValue){
                        this.param2 = parseFloat(newValue);
                    }
                }
            });
            ```

## 父子组件间的访问
### 父子组件访问的本质
[top](#catalog)
- 本质1: 实例对象的获取
    - 父组件可以获取子组件的实例对象
    - 子组件可以获取父组件的实例对象
- 本质2:
    - 可以通过实例对象，调用组件内部方法、属性

### 父组件访问子组件--$children与$refs
[top](#catalog)
- 访问方式

    |访问方式/属性|类型|获取指定子组件|备注|
    |-|-|-|-|
    |`this.$children`|数组|`this.$children[子组件的索引]`|**不推荐**使用该属性<br>只能通过索引来获取具体的子组件。当组件顺序发生变化时，会增加维护的难度|
    |`this.$refs`|对象（默认为空对象）|`this.$refs.子组件的refs属性值`|<span style='color:red'>只有添加 `ref` 属性</span>的子组件才能通过该属性获取<br>如果没有标注 `ref` 属性的子组件，则 `this.$refs` 是一个**空对象**|


- 示例
    - 参考代码
        - [src/component/visit/$children&$refs.html](src/component/visit/$children&$refs.html)
    - 子组件
        ```html
        <template id='child-template'>
            <div>
                <p>child: {{id}}</p>
            </div>
        </template>
        ```
        ```js
        const childCpn = {
            template: '#child-template',
            data(){
                return {
                    id: Math.round(Math.random()*10),
                }
            },
            methods:{
                // 1. 创建子组件的方法
                showId(){
                    console.log('this is childCpn, childId = ' + this.id);
                }
            }
        };
        ```
    - 父组件，在父组件中获取子组件，并调用其方法和属性
        ```html
        <div id="app">
            <!-- 分别声明各个组件的 ref 属性 -->
            <child ref='child01'></child>
            <child ref='child02'></child>
            <child ref='child03'></child>
            <button @click='callChildren'>show by children</button>
            <button @click='callRefs'>show child02 by refs</button>
        </div>
        ```
        ```js
        const app = new Vue({
            el: '#app',
            components: { child: childCpn },
            methods:{
                callChildren(){
                    for(let c of this.$children){   // 获取所有的子组件并遍历
                        console.log(c.showId());    // 调用子组件的方法
                        console.log(c.id);          // 访问子组件的属性
                    }
                },
                callRefs(){
                    // 通过 ref属性值 访问指定的子组件
                    console.log(this.$refs.child02.showId());

                    // 输出属性本身：{child01: VueComponent, child02: VueComponent, child03: VueComponent}
                    console.log(this.$refs);
                }
            }
        });
        ```

### 子组件访问父组件--$parent与$root
[top](#catalog)
- 开发中很少使用到这个功能，并且也不推荐子组件访问父组件
- 子组件访问父组件的缺点
    - 使子组件与父组件产生耦合
    - 降低子组件的复用性
        - 如果子组件中调用了父组件中的某个方法或属性，当换成其他父组件时，这个子组件就不适用了

- 访问方式

    |访问方式|访问内容|返回值类型|
    |-|-|-|
    |`this.$parent`|上一级的父组件|<ul>  <li>`Vue`<ul><li>父组件是Vue实例</li></ul></li>   <li>`VueComponent`<ul><li>父组件是普通组件</li></ul></li>  </ul>|
    |`this.$root`|根组件，即最外层的Vue实例对象|`Vue`|

- 全局组件的父组件与父组件类型
    - 需要根据全局组件**所处的环境**来判断父组件即父组件的类型
    - 如果所在环境是**普通组件**，则 `this.$parent` 是 `VueComponent` 类型
    - 如果所在环境是**Vue实例对象**，则 `this.$parent` 是 `Vue` 类型

- 示例
    - 参考代码
        - [src/component/visit/$parent.html](src/component/visit/$parent.html)

# 插槽slot
## 插槽slot简介
[top](#catalog)
- 组件插槽的作用
    - 使组件更具有扩展性，防止组件部分的硬编码
    - 让使用者决定组件内部的一些内容

- 如何封装 插槽slot 和 组件component
    - 抽取通用部分，封装为组件component
    - 将一个组件中会经常发生变化，或可能会发生变化的部分向外暴露为插槽slot

## 插槽的基本使用
[top](#catalog)
- 插槽的使用方法
    1. 定义插槽
        - 在组件模板中定义插槽
            ```html
            <template>
                <div>
                    <!-- 定义插槽 -->
                    <slot></slot>
                </div>
            </template>
            ```
    2. 注入插槽的替换内容
        - 在父组件模板中，向子组件注入组件/html标签。注入的内容会自动替换插槽
            - 可以注入一个，也可以注入多个
            - 注入的内容会被视为一个整体替换插槽标签
        - 使用时，如果没有注入任何内容，则`<slot></slot>`不会被解析成html代码
            ```html
            <子组件名>
                <!-- 注入一个或多个。可以注入组件，也可以注入html标签 -->
                <需要注入的其他组件></需要注入的其他组件>
                <需要注入的html标签></需要注入的html标签>
            </子组件名>
            ```
    4. 设置插槽的默认内容
        - 声明插槽时，可以设置默认组件/html标签
        - 如果注入了自定义的组件/html标签，会使用注入内容
        - 如果没有注入，将使用默认组件/html标签
            ```html
            <template>
                <div>
                    <!-- 定义插槽 -->
                    <slot>
                        <默认组件/html标签></默认组件/html标签>
                    </slot>
                </div>
            </template>
            ```

- 这种基本定义方式的缺点
    - 对于子组件: 多插槽无法区分替换内容
        - 如果有多个插槽，在注入时，无法区分希望替换哪一个插槽
        - 最终注入内容会视为一个整体，替换所有的插槽
    - 对于父组件: 无法指定具体替换那个插槽

- 示例
    - 参考代码
        - [src/component/slot/slot_base.html](src/component/slot/slot_base.html)
    - 代码内容
        1. 定义插槽
            ```html
            <template id='cpn01-template'>
                <div>
                    <p>this is cpn01</p>
                    <slot><button>defaultBtn</button></slot>
                    <!-- <slot></slot> -->
                </div>
            </template>
            ```

            ```js
            const cpn01Constructor = {
                template: '#cpn01-template',
            };
            ```

        2. 使用组件，并注入相关内容
            ```html
            <div id="app">
                <p>------1. 向插槽中注入一个按钮----------</p>
                <cpn01><button>testBtn</button></cpn01>

                <p>------2. 向插槽中注入多个组件/标签-----</p>
                <cpn01>
                    <p>multi 01</p>
                    <p>multi 02</p>
                    <p>multi 03</p>
                </cpn01>

                <p>------3. 使用插槽的默认组件------------</p>
                <cpn01></cpn01>
            </div>
            ```

            ```js
            const app = new Vue({
                el: '#app',
                components: { cpn01: cpn01Constructor, }
            });
            ```

## 具名插槽
[top](#catalog)
- 什么是具名插槽？
    - 声明插槽时，添加`name`属性，来区分不同的插槽
    - 使用时，可以通过`name`指定需要替换的插槽
- 具名插槽的使用方法
    1. 定义插槽
        ```html
        <template>
            <div>
                <!-- 定义具名插槽 -->
                <slot name='插槽名1'></slot>
                <slot name='插槽名2'></slot>
                ...
                <!-- 普通插槽 -->
                <slot></slot>
            </div>
        </template>
        ```
    2. 通过插槽名向插槽注入内容
        ```html
        <子组件名>
            <!-- 注入一个或多个 -->
            <注入内容 slot='插槽名1'></注入内容>
            <注入内容 slot='插槽名2'></注入内容>
            ...
            <!-- 不指定插槽名，会自动替换普通插槽 -->
            <注入内容></注入内容>
        </子组件名>
        ```

- 示例
    - 参考代码
        - [src/component/slot/named_slot.html](src/component/slot/named_slot.html)
    - 代码内容
        1. 在子组件中定义插槽
            ```html
            <template id='cpn01-template'>
                <div>
                    <!-- 1.1 定义具名插槽 -->
                    <slot name='left'>
                        <p>left</p>
                    </slot>
                    <slot name='center'>
                        <p>center</p>
                    </slot>
                    <slot name='right'>
                        <p>right</p>
                    </slot>

                    <!-- 1.2 定义普通插槽 -->
                    <slot><p>default slot</p></slot>
                </div>
            </template>
            ```
            ```js
            const cpn01Constructor = {
                template: '#cpn01-template'
            };
            ```

        2. 在父组件中，注入插槽的替换内容
            ```html
            <div id="app">
                <p>------------1. 向插槽注入自定义内容------------</p>
                <cpn01>
                    <!-- 2.1 注入具名查插槽的内容 -->
                    <button slot='left'>leftBtn</button>
                    <input  slot='center' type=text name='inbox' style='width: 100px;'>
                    <button slot='right'>rightBtn</button>

                    <!-- 2.2 注入普通插槽的内容 -->
                    <button>btn01</button>
                    <button>btn02</button>
                </cpn01>

                <p>------------2. 使用插槽的默认内容------------</p>
                <cpn01></cpn01>
            </div>
            ```
            ```js
            const app = new Vue({
                el: '#app',
                components:{ cpn01: cpn01Constructor }
            });
            ```

## 编译作用域
[top](#catalog)
- 什么是编译作用域
    - 每个组件都有自己的作用域，只会到自己的实例对象中搜索数据和方法
    - 每个组件**不会主动跨作用域**，不会主动到子组件中搜索任何内容
        - 可以通过 `$children`、`$refs`，手动获取子组件实例对象，并使用子组件的数据或方法

- 示例
    - 参考代码
        - [src/component/slot/compile_scope.html](src/component/slot/compile_scope.html)
    - 代码内容
        1. 创建子组件构造器 和 一个Vue实例，两者都包含一个数据: `isShow`
            ```js
            const childCpn = {
                template: '#child-template',
                data(){
                    return { isShow:false }
                }
            };

            const app = new Vue({
                el: '#app',
                data: { isShow:true },
                components: { child: childCpn }
            });
            ```
        2. Vue实例模板
            ```html
            <div id="app">
                <!-- 2. 当前模板属于 Vue实例 的作用域 -->
                <div>
                    <p>this is app</p>
                    <!-- isShow将会使用 Vue实例 中的数据 -->
                    <child v-show='isShow'></child>
                </div>
            </div>
            ```
        3. Vue组件模板
            ```html
            <template id='child-template'>
                <!-- 3. 当前模板属于 组件child 的作用域 -->
                <div>
                    <p>this is child cpn</p>
                    <!-- isShow将会使用组件构造器 childCpn 中返回数据 -->
                    <button v-show='isShow'>childBtn</button>
                </div>
            </template>
            ```

## 作用域插槽
[top](#catalog)
- 什么是作用域插槽
    - 两个要点
        1. 数据由子组件提供
        2. 数据的使用方式由父组件提供，编译后替换插槽
    - 与一般的使用方式完全相反，一般的方式是:
        - 父组件提供数据，子组件提供数据的使用方式
        - 父组件需要通过 `v-bind` 向子组件的 `props` 传递数据
    - 为了完成这种使用方式，需要在父组件的作用域中获取子组件的数据

- 使用场景示例
    - 子组件中包含一些数据，如：`dataList:['aaa', 'bbb', 'ccc']`
    - 需要在多个界面进行展示子组件的数据，但是展示方式不同
        - 某些界面是水平方向展示的
        - 某些界面是列表形式展示的
        - 某些界面直接显示数组对象的字符串
    - 内容在子组件，需要父组件提供展示方式
        - 可以使用 `作用域插槽` 来实现

- 作用域插槽的使用方法
    1. 在子组件中创建作用域插槽
        - 最好使用具名插槽
        - 通过插槽向外部暴露数据
            1. 使用 `v-bind` 将子组件的数据绑定到 **插槽的某个属性**
            2. 绑定后就可以在父组件中使用子组件的数据
            ```html
            <template id='子组件模板id'>
                <div>
                    <slot name='插槽名' :插槽数据名='子组件数据名'>
                        <!-- 可以提供默认的插槽实现 -->
                    </slot>
                </div>
            </template>
            ```
    2. 在父组件模板中，使用作用域插槽
        - 作用域插槽是Vue 2.5.x之后出现的，所以最好使用 `<template>` 包裹自定义内容
            - `<template>` 和 `<div>` 都可以使用，但是 `<template>` 的兼容性更好
        - 是否指定插槽名
            - 使用具名插槽时，需要通过 `slot` 属性指定插槽名
            - 未指定插槽名时，自动适配普通插槽
        - `自定义作用域对象`
            - 对象名可以随意设置
            - 只能通过该对象来使用插槽暴露的数据

            ```html
            <div id="app">
                <!-- 使用子组件的默认实现 -->
                <子组件></子组件>

                <子组件>
                    <!-- 使用 template 标签包裹，并获取插槽暴露的数据 -->
                    <template slot='插槽名' slot-scope='自定义作用域对象名'>
                        <!-- 使用插槽数据 -->
                        自定义作用域对象名.插槽数据名
                    </template>
                </子组件>
            </div>
            ```

- 数据传递关系图
    - ![slot_scope_dataflow](imgs/component/slot_scope_dataflow.png)

- 示例
    - 参考代码
        - [src/component/slot/slot_scope.html](src/component/slot/slot_scope.html)
    - 代码内容
        1. 在子组件中创建作用域插槽
            ```html
            <!-- 1. 创建子组件 -->
            <template id='child-template'>
                <div>
                    <!-- 将子组件数据绑定到插槽数据: slist 中，同时暴露给外部使用 -->
                    <slot name='showBar' :slist='dataList'>
                        <ul>
                            <li v-for='item in dataList'>{{item}}</li>
                        </ul>
                    </slot>
                </div>
            </template>
            ```
            ```js
            const childCpn = {
                template: '#child-template',
                data(){
                    return {
                        dataList:['aaa', 'bbb', 'ccc', 'ddd']
                    }
                }
            };
            ```
        2. 在父组件模板中，使用作用域插槽
            ```html
            <!-- 2. 在父组件中使用 作用域插槽 -->
            <div id="app">
                <p>----- 1. 使用插槽的默认展示方式-----</p>
                <child></child>

                <p>----- 2. 使用子组件数据，自定义展示方式-----</p>
                <child>
                    <!-- 通过 slot-scope 属性，获取作用域对象，并通过该对象使用插槽数据 -->
                    <div slot='showBar' slot-scope='cdx'>
                        <span >{{cdx.slist.join(' | ')}}</span>
                    </div>
                </child>
            </div>
            ```
            ```js
            const app = new Vue({
                el: '#app',
                components: { child: childCpn },
            });
            ```