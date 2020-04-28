<span id="catalog"></span>


面向对象
线程机制与事件机制

### 目录
- [js基础总结](#js基础总结)
    - [基础总结-数据类型](#基础总结-数据类型)
        - [数据类型的分类](#数据类型的分类)
        - [数据类型的判断](#数据类型的判断)
        - [与数据类型相关的问题](#与数据类型相关的问题)
    - [数据、变量、内存](#数据、变量、内存)
        - [三者之间的关系](#三者之间的关系)
        - [与数据、变量、内存相关的问题](#与数据、变量、内存相关的问题)
    - [基础总结-对象](#基础总结-对象)
    - [基础总结-函数](#基础总结-函数)
        - [与函数相关的几个基本问题](#与函数相关的几个基本问题)
        - [回调函数](#回调函数)
        - [立即执行函数IIFE](#立即执行函数IIFE)
        - [函数中的this对象](#函数中的this对象)
- [函数](#函数)
    - [原型与原型链](#原型与原型链)
        - [原型prototype](#原型prototype)
        - [显式原型与隐式原型](#显式原型与隐式原型)
        - [原型链](#原型链)
        - [原型中的特殊点](#原型中的特殊点)
        - [自定义函数、Function、Object之间的原型关系](#自定义函数、Function、Object之间的原型关系)
        - [原型的继承](#原型的继承)
        - [属性问题](#属性问题)
        - [instanceof原理](#instanceof原理)
        - [与原型相关的问题](#与原型相关的问题)
    - [执行上下文与执行上下文栈](#执行上下文与执行上下文栈)
        - [变量提升与函数提升](#变量提升与函数提升)
        - [执行上下文](#执行上下文)
        - [执行上下文栈](#执行上下文栈)
        - [与执行上下文相关的问题](#与执行上下文相关的问题)
    - [作用域与作用域栈](#作用域与作用域栈)
    - [闭包](#闭包)
- [其他](#其他)
    - [代码结尾的分号](#代码结尾的分号)
- [](#)


# js基础总结
## 基础总结-数据类型
### 数据类型的分类
[top](#catalog)
- 数据类型的
- 基本数据（**值**）类型

    |类型|值|
    |-|-|
    |String|任意字符串|
    |Number|任意数字|
    |Boolean|true/false|
    |Null|null|
    |Undefined|undefined|

- 对象数据（**引用**）类型
    |类型|值|
    |-|-|
    |Object|任意对象|
    |特殊对象：Function|一种特殊的对象，**可以执行**|
    |特殊对象：Array|<ul><li>一种特殊的对象</li><li>对象中的属性是**数值下标**</li><li>内部的数值是**有序的**</li></ul>|

### 数据类型的判断
[top](#catalog)
- (反射方法)判断数据类型的方法及其使用的数据类型

    |方法|功能|适用的数据类型|备注|
    |-|-|-|-|
    |typeof|获取变量的类型，返回一个字符串|Undefined、String、Number、Boolean、Function|<ul><li>返回的结果都是对应类型，并且第一个字母小写</li><li>无法判断 Null 、Array、 Object，因为都会返回 object (对于null，这是一个bug)</li></ul>|
    |===|比较 类型 + 值|Null、Undefined|只适用与Null 和 Undefined 类型，因为这两种类型都只有一个值|
    |instanceof|用于判断对象是不是某个类的实例，即判断具体的类型||

- 示例
    - 参考代码
        - [/javascript/underlyingPrinciple/src/datatype/checkDatatype.html](/javascript/underlyingPrinciple/src/datatype/checkDatatype.html)
    - js内容
        ```js
                // 1. 基本数据类型的判断
        // 判断 undefined
        var a;
        console.log(typeof a === 'undefined');
        console.log(a === undefined);

        // 判断 null
        var b = null;
        console.log("typeof b =", typeof b);
        console.log(b === null);

        // 判断String
        var c = "abcde";
        console.log(typeof c === 'string');

        // 判断Number
        var d = 1234;
        console.log(typeof d === 'number');

        // 判断Boolean
        var e = true;
        console.log(typeof e === 'boolean');

        // 2. 判断对象类型
        // 判断函数
        var f = function(){};
        console.log(typeof f === 'function');

        // 判读数组
        var g = [1,2,3,4];
        console.log(typeof g); // object,数组类型无法使用 typeof
        console.log(g instanceof Array); // 只能通过 instanceof 来判断
        ```

### 与数据类型相关的问题
[top](#catalog)
1. <label style="color:red">undefined 与 null 的区别</label>
    
    ||定义状体|赋值状态|
    |-|-|-|
    |undefined|已定义|未赋值|
    |null|已定义|已赋值，且值为 null|

2. 什么时候给变量赋值为null
    - 赋值时间：初始赋值、业务结束
    - 原因
        - `typeof null`为object，表明null是为了对象准备的
        - 初始赋值为null，表明声明时还没有产生对象，在后续的业务中产生数据后，将会赋值为对象
        - 业务结束时赋值为null，来释放对对象的引用，使对象可以尽快被 gc 回收
    - 示例
        ```js
        var a = null; //为某种对象准备一个变量
        // ... 业务代码
        a = new XXX(...); // 业务中产生了数据，创建对象并赋值给 a
        // ... 业务代码
        a = null;  // 业务结束时，赋值为null，使对象尽快被 gc 回收
        ```

3. 严格区分变量类型与数据类型
    - 数据类型（数据在内存中的形式）
        - 基本类型
        - 对象类型
    - 变量类型（变量内存中保存的值的类型）
        - 基本类型：保存基本类型的数据
        - 引用类型：保存一个地址值

## 数据、变量、内存
### 三者之间的关系
[top](#catalog)
- 什么是数据？
    - 存储在内存中代表特定信息的一串二进制数
    - 数据的特点
        - 可传递
        - 可运算
            - 算数运算
            - 逻辑运算
            - 赋值运算
            - 运行函数
    - 内存中所有操作的目标：数据

- 什么是内存？
    - 内存条通电后产生的、临时的、可存储数据的空间
    - 这个空间是临时的，断电后消失
    - 内存分类
        - 栈空间：保存 全局变量/局部变量
        - 堆空间：保存 任意对象
    - 一块内存包含 2个 数据
        1. 实际保存的数据
        2. 指向该内存的首地址

- 什么是变量？
    - 可变，由变量名和变量值组成
        - 变量名用来标识内存区域
        - 变量值就是内存中保存的数据
    - 每个变量都对应一块内存

- 内存、数据、变量三者之间的关系
    - 内存是用来存储数据的空间
    - 变量名标识内存

### 与数据、变量、内存相关的问题
[top](#catalog)
- 关于赋值与内存的问题
    1. 赋值的本质是什么？
        - 拷贝目标变量内存中的保存的内容
            - 这里的内存一般情况特指：**栈空间**
            - 拷贝的内容：保存的是基本数据就拷贝数据，保存的是对象地址就拷贝地址

    2. `var a = xxx;`，变量 a 标识的内存中保存的是什么？
        - `var a = 10;`，xxx 是基本类型时，保存的是数据本身
        - `var a = {};`，xxx 是对象类型时，保存的是对象在堆空间中的首地址
        - xxx是一个变量
            - 如果变量保存的是基本类型，则 b 中保存的是数据本身，即1234
                ```js
                var a = 1234;
                var b = a;
                ```
            - 如果变量保存的是对象类型，则 b 中保存的是对象的地址
                ```js
                var a = {};
                var b = a;
                ```

- 关于引用变量赋值的问题
    - 两个引用变量指向同一个对象时，一个变量修改数据时，两个变量中都能识别
        ```js
        var a = {name:"abcd", age:16};
        var b = a
        b.name = "qwer";
        console.log(a.name);
        console.log(b.name);
        ```
    - 赋值一个新对象时，会使两个变量的**指向不同的对象**
        ```js
        var a = {name:"abcd", age:16};
        var b = a
        b = {name:"asdf", age:18}
        console.log(a.name); // abcd
        console.log(b.name); // asdf
        ```
    - 将对象作为函数参数，并在函数内部修改对象形参的指向时，**只会影响形参自身，不会影响实参**
        ```js
        function fn(obj){
            obj = {name:"asdf", age:18};
        }

        var a = {name:"abcd", age:16};
        fn(a);
        console.log(a.name); // abcd
        ```

- 关于数据传递的问题
    - 在js中调用函数传递变量参数时，是值传递还是引用传递？
        - 无论是何种数据类型，**都是值传递**
        - 对于对象类型，将对象的地址拷贝给形参

- JS引擎如何管理内存？
    1. 内存生命周期
        - 分配空间
        - 存储数据到空间、操作空间中的数据
        - 释放空间
    
    2. js何时分配内存
        - 分配时间
            - 声明变量
            - 声明函数
            - 创建对象时
        - js引擎会自动分配一定大小的内存来保存数据
        

    3. 下面的代码执行后，占用了几个内存空间?
        - 3个内存空间：变量a、变量b、对象 `{}`
            ```js
            var a = 123;
            var b = {};
            ```
        - 2个内存空间：变量a、变量b
            ```js
            var a = 123;
            var b = {};
            b = null; // 执行后，b指向的对象被 gc 回收，但是 b 本身还在占用空间
            b = undefined; // 这种赋值方式的结果与 null 相同
            ```

    4. 释放内存:
        - 局部变量：函数执行结束后，**自动释放**
        - 对象数据：
            - 首先需要成为垃圾对象，即没有被任何变量引用
            - 然后在之后的某个时刻，由 gc 来回收内存空间
        - 全局变量：不会自动释放
        - 示例
            ```js
            function fn(){
                var a = {};
            }

            var x = 1234; // 全局变量，将会一直占用内存空间

            fn();
            // fn 执行结束后，局部变量 a 立即释放
            // a 引用的对象 {} 会在后面的某个时刻由 gc 回收
            ```
    5. 自动释放与 gc 回收
        - 自动释放的内容是：栈空间中的变量，在函数执行完之后，自动释放
        - gc 回收的内容是：堆空间中的变量/对象，需要变量/对象没有引用，并且需要等待 gc回收启动


## 基础总结-对象
[top](#catalog)
- 什么是对象
    - 多个数据的封装
    - 一个对象往往代表现实世界的一个实体
- 为什么要使用对象
    - 统一管理多个数据
- 对象的组成
    - 属性：有属性名和属性值组成
        - 属性名本质是**字符串**，但是书写比较麻烦，一般开发中都会省略引号
            ```js
            // 一般开发中的写法
            var b = {name:"qwer", age:16};

            // 直接使用字符串作为属性名
            var a = {"name":"abcd", "age":16};
            ```
    - 方法：一种特殊的属性，属性值是函数
- 如何访问对象内部的数据
    - `obj.属性名`，书写简洁，但是有些场景无法使用
    - `obj["属性名"]`，书写麻烦，但是所有场景通用
        - 属性名中包含特殊字符
        - 变量名不确定
            - 如：属性名保存在变量中，只能通过：`obj[变量]`的方式来获取属性值

## 基础总结-函数
### 与函数相关的几个基本问题
[top](#catalog)
- 什么是函数
    - 实现了特定功能的多行代码的封装
    - 只有函数类型可以执行，其他类型无法执行

- 为什么要用函数
    - 提高代码复用率
    - 便于阅读

- 如何定义函数
    - 构造函数 : `var 变量名 = new Function('函数代码字符串')`
    - 函数声明 : `function 函数名(){...}`
    - 函数表达式 : `var 变量名 = function(){};`

- 如何调用/执行函数
    
    |调用方式|说明|
    |-|-|
    |`test()`|直接调用|
    |`obj.test()`|调用对象的方法。调用时，会自动绑定this对象|
    |`new test()`|以构造函数的方式调用|
    |`test.call(obj, param1, param2...)`|临时绑定this对象调用|
    |`test.apply(obj, [param1, param2])`|临时绑定this对象调用|

- 形参的本质是什么？
    - 局部变量
    - 形参的值是实参值的拷贝

### 回调函数
[top](#catalog)
- 回调函数的特征
    - 自定义函数
    - 没有主动调用函数
    - 函数最终被执行了

- 常见的回调函数
    - dom事件回调函数
    - 定时器/延时器的回调函数
    - ajax的回调函数
    - 生命周期回调函数

### 立即执行函数IIFE
[top](#catalog)
- IIFE，等同于**匿名函数自调用**
    - 这样的函数只需要执行一次，执行后就可以丢弃

- 调用方式分析
    - 调用方法
        ```js
        (function(){
            console.log(new Date());
        })()    
        ```
    - 错误的调用方法
        - 错误示例
            ```js
            function(){
                console.log(new Date());
            }()
            ```
        - 在语义上没有问题，但是在语法上有问题
        - `function(){}`部分，编译器无法将其看作一个整体，所以需要使用括号包起来，如：`(function(){})`

- 为什么需要IIFE
    - 隐藏实现
    - 变量都包含在临时作用于中。执行后，不会影响外部作用域，可以减少作用域冲突

- 示例
    - 全局作用域和临时作用域中，都包含变量 a。通过IIFE隔离不同的作用域
        ```js
        var a = 1234;

        // 通过IIFE隔离作用域，对a的操作不会互相干扰
        (function(){
            var a = 1;
            function test(){
                console.log(++a);
            }

            window.$ = function(){
                return {test:test};
            }
        })()

        $().test(); // 2
        $().test(); // 3
        $().test(); // 4
        console.log(a); // 1234
        ```

### 函数中的this对象
[top](#catalog)
- this对象是什么
    - 所有函数内部都有一个this对象
    - this对象指向调用函数的对象
    - 任何函数本质上都是通过某个对象来调用的，如果没有显式指定，就是 window 对象

- 如何确定this的指向

    |调用方式|说明|this指向|
    |-|-|-|
    |`test()`|直接调用|window 对象|
    |`obj.test()`|调用对象的方法。调用时，会自动绑定this对象|obj|
    |`new test()`|以构造函数的方式调用|新创建的类对象|
    |`test.call(obj, param1, param2...)`|临时绑定this对象调用|obj|
    |`test.call()`|临时绑定this对象调用|window 对象|
    |`test.apply(obj, [param1, param2])`|临时绑定this对象调用|obj|
    |`test.apply()`|临时绑定this对象调用|window 对象|

- 示例
    - 测试：this对象的指向
        ```js
        function Person(color){
            console.log("this01 =", this);
            this.color = color;
            this.getColor = function(){
                console.log("this02 =", this);
                return this.color;
            };

            this.setColor = function(color){
                console.log("this03 =", this);
                this.color = color;
            };
        }

        Person("blue"); // this01 = Window

        var p = new Person("orange"); // this01 = Person {}
        p.getColor();   // this02 = Person {color: "orange", getColor: ƒ, setColor: ƒ}

        var obj = {};
        p.setColor.call(obj, "green");  // this03 = {}
        console.log("obj =", obj);      // obj = {color: "green"}

        var test = p.setColor;
        test();         //this03 = Window 
        ```
    - 测试：没有显式指定调用对象时，调用对象是 window 对象
        ```js
        function fn1(){
            console.log("fn1 this =", this);
            function fn2(){
                console.log("fn2 this =", this);
            }

            fn2();
        }

        fn1();

        // fn1 this = Window
        // fn2 this = Window
        ```

# 函数
## 原型与原型链
### 原型prototype
[top](#catalog)
- 函数的protytype属性
    - 每个函数都有一个prototype属性，默认指向一个**空的Object实例对象**，即原型对象
        - 验证方法
            ```js
            function Fn(){}
            console.log(Fn.prototype instanceof Object) // 输出true
            ```
    - 原型对象中默认有一个属性：`constructor`，它指向函数对象
        - `constructor`属性是 原型对象 与 空对象的**本质区别**，空对象中默认不会有该属性
    - 示例
        ```js
        function fn(){};

        console.log(fn.prototype)
        // 输出：
        // {constructor: ƒ}
        // constructor: ƒ fn()
        // __proto__: Object

        console.log(typeof fn.prototype)
        // 输出：object

        // constructor属性 指向 函数对象
        console.log(fn.prototype.constructor === fn);
        // 输出：true
        ```
    
    - 属性关系图：constructor 与其 类型相互引用
        ```
        ┌─────────────────┐      
        │       Type      │ <<───────────────────────────┐
        ├───┬─────────┬───┤      ┌───────────────────┐   │
        │   │prototype├───┼───>> │ Prototype of Type │   │
        └───┴─────────┴───┘      ├──┬─────────────┬──┤   │
                                 │  │ constructor ├──┼───┘
                                 └──┴─────────────┴──┘
        ```

- 为原型对象添加属性，可以在实例对象中共享
    - 示例
        ```js
        function Person(name, age){
            this.name = name
            this.age = age
        }

        Person.prototype.print = function(){
            console.log("name =", this.name, "age =", this.age);
        }

        var p1 = new Person("tom", 18);
        p1.print();
        // 输出：name = tom age = 18

        var p2 = new Person("jery", 20);
        p2.print();
        // 输出：name = jery age = 20
        ```


### 显式原型与隐式原型
[top](#catalog)

- 原型之间的关系
    - 显式原型（属性）：每个**函数对象**中的`prototype`属性
    - 隐式原型（属性）：每个**实例对象**的`__proto__`属性
    - 隐式原型 与 显式原型的关系
        - `实例对象.__proto__ === (构造)函数.prototype`
        - 两者指向相同的对象

    - js中的一个对象如果有显式原型对象，则它可以作为一个构造函数；如果有隐式对象，则它是某个构造函数的实例

- 原型对象是如何添加的？
    - 原型对象由js引擎自动添加
    - 原型对象的添加时间
        - 显式原型对象：创建函数对象时，创建一个空Object实例对象并赋值
            - 相对于在**创建函数对象时**执行了
                ```js
                函数对象.prototype = {};
                ```
        - 隐式原型对象：通过构造函数创建实例化对象时，使用显式原型对象进行赋值
            - 相当于在**构造函数内部**执行了
                ```js
                function 构造函数(...){
                    // js引擎隐式执行
                    this.__proto__ = 构造函数.prototype

                    // 其他业务
                    ...
                }
                ```

- 内存结构说明
    - 示例代码
        ```js
        function Fn (){}
        var myfn = new Fn()

        Fn.prototype.test = function(){
            console.log("this is test")
        }

        // 0x0005 --> 0x0004 --> 0x0001 --> 0x0001 --> test
        myfn.test()
        ```
    - 内存结构示意图
        ```
            内存：栈                内存：堆
        ┌────────────┐    ┌───────────────────────────────────────────────┐
        │            │    │     ┌────────────────┐                        │
        │ ┌────────┐ │    │     │  Fn instance   │                        │
        │ │ myfn:  │ │    │     ├────────────────┤                        │
        │ │ 0x0004 ├─┼────┼──>> │ __proto__:     ├────────────┐           │
        │ └────────┘ │    │     │    0x0001      │            │           │
        │   0x0005   │    │     └────────────────┘            V           │
        │            │    │         0x0004             ┌────────────────┐ │
        │            │    │                            │     Object     │ │
        │            │    │                            │ (Null Instance)│ │
        │            │    │                            ├────────────────┤ │
        │            │    │                            │ test=function()│ │
        │ ┌────────┐ │    │     ┌──────────────────┐   └────────────────┘ │
        │ │  Fn:   │ │    │     │  Fn function obj │        0x0001        │
        │ │ 0x0002 ├─┼────┼──>> ├──────────────────┤          ^           │
        │ └────────┘ │    │     │ prototype:       │          │           │
        │   0x0003   │    │     │     0x0001       ├──────────┘           │
        └────────────┘    │     └──────────────────┘                      │
                          │          0x0002                               │
                          └───────────────────────────────────────────────┘
        ```


### 原型链
[top](#catalog)
- 如何**读取**一个对象的属性/如何在原型链上**读取**属性？
    1. 先在当前对象自身的属性中查找，找到则返回
    2. 如果自身没有，再沿着 `__proto__` 链向上查找，找到则返回
    3. 如果查找到`Object函数的原型对象.__proto__ `，则返回 `undefined`

- 原型链的尽头: `Object函数的原型对象.__proto__ `，该值为 `null`，找到此处会返回 `undefined`
- 原型链的本质: **隐式原型**
    - 在原型链中搜索属性时，一般是通过类的实例对象启动的，所以搜索用的原型**基本都是隐式原型**

- 原型链说明
    - 说明代码
        ```js
        // js启动后，js引擎默认创建 Object 函数 及其 原型对象

        function Fn(){
            this.test01 = function(){console.log("test01")}
        }

        Fn.prototype.test02 = function(){console.log("test02")}
        var myfn = new Fn();
        
        console.log(Fn.prototype);

        // 搜索顺序：myfn 内部存在 -- > 直接使用
        myfn.test01();

        // 搜索顺序：myfn 内部不存在 --> 原型对象Object中存在 --> 直接使用
        myfn.test02();

        // 搜索顺序：myfn 内部不存在 --> 原型对象Object中不存在 
        //           --> Object的原型对象中存在 --> 直接使用
        myfn.toString();

        // 搜索顺序：myfn 内部不存在 --> 原型对象Object中不存在
        //        --> Object的原型对象中不存在 --> Object原型对象.__proto__ = null
        //        --> 所有位置都不存在 ---> 返回undefined
        myfn.test03();
        ```

    - 内存结构
        ```
            内存：栈                内存：堆
        ┌────────────┐    ┌──────────────────────────────────────────────────────────────┐
        │            │    │     ┌─────────────────────┐        ┌─────────────────────┐   │
        │ ┌────────┐ │    │     │ Object Function obj │        │ Prototype of Object │   │
        │ │ Object:│ │    │     ├─────────────────────┤        ├─────────────────────┤   │
        │ │ 0x0002 ├─┼────┼──>> │ prototype:    ───>>─┼────>>  │ __proto__:          │   │
        │ └────────┘ │    │     │    0x0001           │        │     null            │   │
        │   0x0003   │    │     └─────────────────────┘        ├─────────────────────┤   │
        │            │    │         0x0002                     │ Method:             │   │
        │            │    │                                    │   toString()        │   │
        │            │    │                                    │   hasOwnProperty()  │   │
        │            │    │                                    │   valueOf()         │   │
        │            │    │                                    │   ......            │   │
        │            │    │                                    │                     │   │
        │            │    │                                    └─────────────────────┘   │
        │            │    │                                        0x0001      ^         │
        │            │    │                                                    ^         │
        │            │    │                                                    │         │
        │ ┌────────┐ │    │     ┌──────────────────────┐                       └──────┐  │
        │ │  Fn:   │ │    │     │  Fn function obj     │                              │  │
        │ │ 0x0005 ├─┼────┼──>> ├──────────────────────┤                              │  │
        │ └────────┘ │    │     │ prototype:           │       ┌───────────────────┐  │  │
        │   0x0006   │    │     │     0x0004     ───>>─┼────>> │     Object        │  │  │
        │            │    │     └──────────────────────┘       │ (Null Instance)   │  │  │
        │            │    │            0x0005                  ├───────────────────┤  │  │
        │            │    │                                    │ __proto__ :       │  │  │
        │            │    │     ┌──────────────────────┐       │     0x0001     ───┼──┘  │
        │            │    │     │  Fn instance         │       ├───────────────────┤     │
        │            │    │     ├──────────────────────┤       │ Method:           │     │
        │            │    │     │ __proto__:           │       │   test2=function()│     │
        │            │    │     │    0x0004      ───>>─┼────>> │                   │     │
        │ ┌────────┐ │    │     ├──────────────────────┤       └───────────────────┘     │          
        │ │  myfn: │ │    │     │ Method:              │             0x0004              │
        │ │ 0x0007 ├─┼────┼──>> │   test01()=function()│                                 │
        │ └────────┘ │    │     └──────────────────────┘                                 │    
        │   0x0008   │    │           0x0007                                             │
        └────────────┘    └──────────────────────────────────────────────────────────────┘
        ```

### 原型中的特殊点
[top](#catalog)
- 函数对象的特殊性
    - 函数对象中分别包含了显式原型和隐式原型
    - `函数对象.prototype = Object`
    - `函数对象._proto__ = Function`
        - 隐式原型来源于 `Function` ，因为所有的函数都可以改成
            ```js
            function Foo(){...}

            // 可以转换为
            var Foo = new Function("...")
            ```

- `Function` 函数对象的特殊性
    - 该对象的创建方式属于**自己创建自己**，可以理解为：
        ```js
        var Function = Function()
        ```
    - 这中特殊性的验证方式
        ```js
        console.log(Function.prototype === Function.__proto__)
        ```
    - Function 函数对象 与 Function函数的原型对象都相同，即
        - `Function对象.__proto__ === Function函数.__proto__`
        - `Function对象.prototype === Function函数.prototype`
        - `Function.prototype === Function.__proto__`
    - Function 函数对象的显式原型是 Object 函数对象的实例对象
        ```js
        console.log(Function.prototype instanceof Object) // 输出 true
        ```
    - 通过这个特殊性可以引申出：
        - <label style="color:red">任何函数对象都是 Function 的实例，包括Object函数对象和 Function函数对象自身</label>

- `Object` 函数对象的特殊性
    - `Object` 函数对象的原型**不是Object的实例**
        ```js
        console.log(Object.prototype instanceof Object) // 输出：false

        function Foo(){}
        console.log(Foo.prototype instanceof Object) // 输出：true
        ```

    - 由于这个特殊性，需要修改 prototype 的定义
        - <label style="color:red">每个函数对象都有一个prototype属性，默认指向一个空的Object实例对象，即原型对象。但是Object不满足</label>



### 自定义函数、Function、Object之间的原型关系
[top](#catalog)
- 自定义函数、Function、Object之间的原型关系图
    - `No.1`、`No.2` : Object函数对象.__proto__ === Function函数对象.prototype
        - 这两条线的组合表明： 任何函数对象都是 Function 的实例，包括Object函数对象
    - `No.3`: Function.prototype.__proto__ === Object.prototype
        - 这条线表明：每个函数对象都有一个prototype属性，默认指向一个空的Object实例对象，即原型对象。包括Function 函数对象
    - 图中包含了显式和隐式原型，看图时，需要分清当前要查询的对象类型是构造函数，还是实例对象。
        - 构造函数走显式原型：prototype的所有路径
        - 实例对象走隐式原型：__proto__的所有路径
    ```
    ┌─────────────────┐       __proto__
    │var f1 = new Foo ├──────────────────────────────┐
    └─────────────────┘                              │
                                                     V
          ┌───────────┐      prototype      ┌────────────────────────┐       
          │ function  │   ───────────────>> │ Foo.prototype          │
    ┌──<<─┤ Foo()     │   <<─────────────── │ (Object Null Instance) │
    │     └───────────┘      constructor    └──────────────┬─────────┘
    │                                                      │
    V                                                      │ 
    V                                           __proto__  │
    │                                                      │        
    │                                                      │          
    │     ┌──────────────────────┐  __proto__              │         null
    │     │var obj = new Object()├──────────────────┐      │          ^
    │     └──────────────────────┘                  │      │          │
    │                                               │      │          │
    │                                               V      V      __proto__      
    │                                               V      V          ^
    │     ┌───────────┐   [No.4]  prototype      ┌────────────┐       │ 
    │     │ function  │  ────────────────────>>  │ Object.    │       │
    │     │ Object()  │  <<────────────────────  │ prototype  ├───────┘
    │     └────┬──────┘         constructor      └────────────┘
    │          │                                      ^
    │          │                                      ^
    │          │                                      │  [No.3]
    V          │        __proto__                     │
    V          └──────────────────────────────────┐   │ __proto__
    │                      [No.1]                 │   │
    │                                             V   │
    │                                             V   │
    │     ┌────────────┐  [No.2]  prototype     ┌────────────────────────┐
    │     │ function   │  ───────────────────>> │ Function.prototype     │
    │     │ Function() │  <<─────────────────── │ (Object Null Instance) │
    │     └─────┬──────┘         constructor    └────────────────────────┘
    │           │                                  ^      ^
    │           │                                  ^      ^
    │           │        [No.5]   __proto__        │      │
    │           └──────────────────────────────────┘      │
    │                                                     │
    │                   __proto__                         │
    └────────>>─────────────────────────────>>────────────┘
                        ^ ^
                        ^ ^
                        │ │
                        │ │
            这条线表明：任何函数对象都是 Function 的实例
    ```

- 与原型关系图相关的 4 个判断
    ```js
    // 应用 隐式：No.1 ，显式：No.2
    // 任何函数对象都是 Function函数对象的实例对象
    console.log(Object instanceof Function) // true

    // 应用 隐式：No.1 + No.3 ，显式：No.4
    console.log(Object instanceof Object) // true

    // 应用 隐式：No.5 ，显式：No.2
    // Function函数对象是其自身的实例对象
    // 任何函数对象都是 Function函数对象的实例对象，包括其自身
    console.log(Function instanceof Function) // true

    // 应用 隐式：No.5 + No.3 ，显式：No.4
    // 
    console.log(Function instanceof Object) // true
    ```

### 原型的继承
[top](#catalog)
- 构造函数的实例对象自动拥有构造函数原型对象的属性与方法
- 继承的本质仍然是原型链的应用

### 属性问题
[top](#catalog)
- 只有读取对象属性时，会搜索原型链
- 设置对象的属性值时，不会查找原型链，只在当前对象内部进行操作
    - 如果对象内部有某个属性，则进行修改；如果没有则添加该属性
- 由于属性的读写问题，隐身出一个设计的原则
    - <label style="color:red">方法定义在原型中、属性在构造函数中设置</label>
- 示例
    ```js
    function Foo(){}
    Foo.prototype.param = "this is param";
    
    var f1 = new Foo();
    console.log("f1.param =", f1.param);
    // 输出：f1.param = this is param

    var f2 = new Foo();
    console.log("f2.param =", f2.param);
    // 输出：f2.param = this is param

    f2.param = "new param";
    console.log("f2.param =", f2.param);
    // 输出：f2.param = new param

    // 打印原型中的属性
    console.log("f2.__proto__.param =", f2.__proto__.param);
    // 输出：f2.__proto__.param = this is param
    ```

### instanceof原理
[top](#catalog)
- `A instanceof B` 是如何处理的？
    - 如果 B函数 的显式原型对象在 A对象 的原型链上，则返回 true，否则返回 false
        - 本质上还是通过：构造函数的显式原型 === 实例对象的隐式原型这一标准来判断
        - 构造函数和实例对象**唯一的关联便是原型对象的指向**，指向相同，则一定是对应的类与实例


### 与原型相关的问题
[top](#catalog)
1. 代码的输出内容是什么?
    - 代码
        ```js
        function A(){}
        A.prototype.n = 1
        var b = new A();

        A.prototype = {n:2, m:3}
        var c = new A();

        console.log(b.n, b.m, c.n, c.m)
        ```
    - 输出结果：1 undefined 2 3
    - 结果分析
        - 执行 `A.prototype = {n:2, m:3}` 时，只是修改了A的显式原型的指向，但是没有修改 b 的隐式原型的指向。所以 b.n仍然是 1，b.m是undefined
        - c 在 A 修改显式原型后创建，使用新的原型对象
    
2. 代码的输出内容是什么?
    - 代码
        ```js
        var F = function(){}
        Object.prototype.a = function(){
            console.log('a()')
        }

        Function.prototype.b = function(){
            console.log('b()')
        }
        
        var f = new F()
        f.a() 
        f.b() 
        F.a() 
        F.b() 
        ```
    - 输出结果：
        - a()
        - f.b = undefined，出现执行异常
        - a()
        - b()


## 执行上下文与执行上下文栈
### 变量提升与函数提升
[top](#catalog)
- 参考：[js基本知识提升](../base/base.md#提升)
- 经典问题：代码输出什么?
    - 代码内容
        ```js
        var a = 3
        function fn(){
            console.log(a)
            var a = 4;
        }

        fn()

        console.log(b)
        var b = 10

        console.log(c)
        c = 20

        fn2()
        function fn2(){
            console.log("this is fn2")
        }

        fn3()
        var fn3 = function fn3(){
            console.log("this is fn3")
        }
        ```
    - 输出结果：
        1. `fn()`: undefined
        2. `console.log(b)`: undefined
        3. `console.log(c)`: 异常，未定义
        4. `fn2()`: this is fn2
        5. `fn3()`: 异常，TypeError: fn3 is not a function
    - 输出分析
        1. `fn()`
            - fn函数内使用了 `var` 声明变量a，代码执行时，a 会被提升
            - 输出 a 时，fn的函数作用域中已经存在 a 的定义，但是还没有赋值，所以输出 undefined
        2. `console.log(b)`
            - 在全局作用域内，使用 var 声明变量b，所以可以在 window 对象中找到，但是只有定义还未赋值，所以是undefined
        3. `console.log(b)`
            - 变量c 未使用 var 声明，所以不会有提升，window对象中不会有该对象，所以出现异常
        4. `fn2()`
            - 使用函数声明的方式创建函数，js执行时，会自动提升。fn2在执行前已经被创建完成，所以可以正常输出
        5. `fn3()`
            - 使用函数表达式的方式创建函数，js执行时，只有变量 fn3部分被提升，并且只有定义部分，值是undefined，所以无法执行函数


### 执行上下文
[top](#catalog)
- 根据作用域进行代码分类
    - 全局代码
    - 函数代码（局部代码）

- 执行上下文可以理解为 一种预处理技术。正因为这种预处理才会产生提升的现象

- 全局执行上下文
    - 如何准备执行上下文
        1. 执行全局代码之前，将 window对象 确定为全局执行上下文
        2. 对全局数据进行预处理，<label style="color:red">需要注意预处理顺序</label>
            1. 提升使用 `var` 定义的全局变量，相当于：`var 变量名 = undefined`，然后添加为 window对象 的属性
                - 如果作用域中有多个同名变量，统一使用一个。并在执行代码时，依照赋值语句一一赋值
            2. 提升使用 `函数声明` 方式创建的函数，并为其创建函数对象，然后添加为 window对象 的方法
                - 如果作用域中创建了多个同名函数，则后面的会覆盖前面的
            3. 将 this对象 绑定到 window对象，相当于：`this = window`
        3. 开始执行全局代码
    - 开始执行代码后，使用属性、方法时，会到 全局执行上下文 中搜索
    - 如下所示的代码，在 debug 模式下不会执行，应为在准备全局上下文时，已经被提升并**执行过了**
        ```js
        var a = 3

        function foo(){}

        foo()
        ```

- 函数执行上下文
    - 如何准备函数执行上下文
        1. 在准备执行**函数体**之前，创建对应的函数执行上下文对象
        2. 对局部数据进行预处理，<label style="color:red">需要注意预处理顺序</label>
            1. 为形参赋值：`形参 = 实参`，然后添加为执行上下文的属性
            2. 为 arguments对象 赋值：`arguments = 实参列表`，然后添加为执行上下文的属性
            3. 提升使用 `var` 定义的局部变量，相当于：`var 变量名 = undefined`，然后添加为执行上下文的属性
                - 如果作用域中有多个同名变量，统一使用一个。并在执行代码时，依照赋值语句一一赋值
                - <label style="color:red">如果局部变量与参数同名</label>，则相当于在环境中该变量已存在，<label style="color:red">则不会再次提升</label>，会直接使用参数的值，直到执行局部变量的赋值语句
            4. 提升使用 `函数声明` 方式创建的函数，并为其创建函数对象，然后添加为执行上下文的属性
                - 如果作用域中创建了多个同名函数，则后面的会覆盖前面的
                - 如果作用域中的<label style="color:red">局部函数与参数同名</label>，无论参数是什么类型，<label style="color:red">参数都会被局部函数覆盖</label>
            5. 将 this对象 绑定到 **调用函数的对象**
        3. 开始执行函数体
    - 不调用函数，不会创建函数执行上下文，并且这是一个一次性的对象

- **上下文中，函数声明与变量的特点**
    - 函数声明无论写在作用域的哪里、同样的声明有几次，都会执行，并且会按顺序执行
    - 通过 var 声明的变量 无论有多少个，提升后都是：`var 变量名 = undefinde`。然后在执行时按照赋值语句一一赋值

- 执行上下文 数量的计算准则：`n + 1`
    - `n` 表示执行函数的次数，每次执行某个函数都会产生一个执行上下文
    - `1` 表示 window对象

### 执行上下文栈
[top](#catalog)
- 什么是执行上下文栈
    - 在全局代码执行前，js引擎会创建一个栈来存储、管理所有的执行上下文对象
- 执行上下文栈的管理、存储方法
    1. js执行后，确定全局执行上下文： window对象，然后将其压入执行上下文栈
    2. 在 某个函数 的执行前，创建函数上下文对象，然后压入执行上下文栈
        - 如果内部还有函数调用，则继续创建上下文对象，并压入栈中
    3. 函数 执行结束后，从栈中弹出一个对象，这个对象就是 函数 的执行上下文对象
    4. 当所有代码执行完成之后，栈中只剩下 window对象

- 栈顶与栈底的特殊性
    - **栈顶**的对象，永远都是当前正在执行的上下文对象
    - **栈底**的对象，永远都是 window对象

- 执行上下文栈的分析
    ```js
                            // 1. 确认全局上下文对象 window，并压入栈
    var a = 10
    var bar = function(x){  // 2. 创建 bar 的执行上下文对象，并压入栈
        var b = 5
        foo(x + b)          // 3. 启动 foo 
                            // 6. 再次回到 bar 的执行上下文，继续执行
    }                       // 7. 执行结束，bar 从栈中弹出

    var foo = function(y){  // 4. 创建 foo 的执行上下文对象，并压入栈
        var c = 5
        console.log(a + c + y)
    }                       // 5. 执行结束，foo 从栈中弹出

    bar(10)                 // 0. 启动 bar
                            // 7. 执行完毕，栈中只剩 window对象

    // 执行过程
    // foo(10 + 5)
    // a + c + y = 10 + 5 + 15 = 30
    ```

### 与执行上下文相关的问题
[top](#catalog)
1. 代码输出什么? 整个执行过程中产生了几个执行上下文
    - 代码内容
        ```js
        console.log("global begin:" + i)
        var i = 1
        foo(1)
        function foo(i){
            if (i == 4){
                return;
            }
            console.log("foo() begin:" + i)
            foo(i + 1)
            console.log("foo() end:" + i)
        }

        console.log("global end:" + i);
        ```
    - 输出内容
        ```
        global begin: undefined
        foo() begin: 1
        foo() begin: 2
        foo() begin: 3
        foo() end: 3
        foo() end: 2
        foo() end: 1
        global end: 1
        ```
    - 共创建了5个执行上下文 ： 4个foo + window

2. 代码输出什么?
    - 代码内容
        ```js
        function a(){}
        var a
        console.log(typeof a)
        ```
    - 输出结果：function
    - 结果分析
        - 创建上下文对象时，先创建变量，再创建函数
        - 函数a 和变量a 同名，根据上下文准备的顺序，最终a是函数对象

3. 代码输出什么？
    - 代码内容
        ```js
        if(!(b in window)){
            var b = 1
        }
        console.log(b)
        ```
    - 输出结果：undefined
    - 结果分析
        - `var b=1` 是全局变量，虽然在 if 中，但是仍然会被提升。所以在全局代码被执行前，b 已经是window对象的属性了
        - 执行判断时，由于提升，b 已经是 window对象 的属性了，所以不会进入 if 判断
        - 跳过 if 判断，直接执行 `console.log(b)` ，但是 b 只有定义，没有赋值，所以输出 undefined

4. 代码输出什么？
    - 代码内容
        ```js
        var c = 1;
        function c(c){
            console.log(c);
        }

        c(2);
        ```
    - 输出结果： TypeError: c is not a function
    - 结果分析
        - 全局代码执行前，先处理 变量c，再处理函数c。所以代码执行前，c 是函数
        - 开始执行代码，执行到 `c = 1` 时，c 被赋值为1，从函数对象变为了基本数据
        - 执行到 `c(2);` 时，c 已经不是函数了，所以不能被调用

5. 代码输出什么？
    - 代码内容
        ```js
        function foo(a){
            console.log(a);
            var a = 10
            console.log(a);
        }

        foo(2);
        ```
    - 输出内容
        ```
        2
        10
        ```
    - 结果分析
        - foo 中，局部变量与参数同名，但是不会重复提升
        - 所以第一次输出实参的值，第二次输出赋值语句的值

6. 代码输出什么?
    - 代码内容
        ```js
        function f1(fn){
            function fn(){
            console.log("this is inner fn")
            }

            console.log(typeof fn)
        }

        function fn2(){
            console.log("this is fn2")
        }

        f1(fn2)
        f1(2)
        ```

    - 输出结果
        ```
        function
        function
        ```
    - 结果分析
        - f1 内部的局部函数与形参相同，提升后将形参覆盖
        - 所以无论实参传递什么，实际使用的都是 局部函数 fn，输出结果永远都是 function


## 作用域
### 作用域的基本概念
[top](#catalog)
- 什么是作用域
    - 作用域就是一段代码所在的区域
    - 相对于执行上下文，作用域是静态的，它在编写代码的时候就已经确定了

- 作用域分类
    - 全局作用域
    - 函数作用域
    - ES6中新增加的通过 `let` 关键字实现的块级作用域

- 作用域 数量的计算准则：`n + 1`
    - `n` 表示函数定义的数量
    - `1` 表示全局作用域

- 作用域的作用
    - 隔离变量：不同作用域的同名变量不会冲突

### 作用域与执行上下文
[top](#catalog)
- 作用域与执行上下文的区别
    - 静态与动态
        - 作用域是静态的，定义之后不会变化
        - 执行上下文是动态的，执行函数体之前创建，函数执行结束后被销毁

- 因为作用域的静态性，它与函数的调用顺序无关
    - 如下代码，虽然 fn2 内部调用了 fn1，但是fn1的作用域仍然是 全局作用域
        ```js
        var x= 12;
        function fn1(){
            console.log(x)
        }

        function fn2(){
            var x = 13;
            fn1()
        }

        fn2() // 输出12
        ```

- 全局执行上下文的特殊性
    - 函数执行上下文是在函数体执行前创建的
    - 全局执行上下文是在**全局作用域确定之后**，js代码执行之前创建的

### 变量、函数、对象的搜索方式
[top](#catalog)
- 

### 与作用域相关的问题
[top](#catalog)
1. 


## 闭包
[top](#catalog)




# 其他
## 代码结尾的分号
[top](#catalog)
- 每条js语句结尾可以不加分号
- 加不加分号只是编码风格的问题

- **必须加分号的情况**
    - 两种情况
        1. 小括号开头的前一条语句
            - IIFE 的前一条语句
                ```js
                var a = "asdfg"
                ;(function(){
                    console.log("zxcvb");
                })()
                ```
        2. 中括号开头的前一条语句 
            - 数组字面量遍历
                ```js
                var a = "asdfg"
                ;[2, 4, 6, 8].forEach(function(node){console.log(node)})
                ```
    - 解决方法
        1. 在前一条语句末尾添加分号
        2. **在当前语句的前面添加分号 (推荐)**
