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
        - [显示原型与隐式原型](#显示原型与隐式原型)
        - [原型链](#原型链)
        - [instanceof原理](#instanceof原理)
        - [与原型相关的问题](#与原型相关的问题)
    - [执行上下文与执行上下文栈](#执行上下文与执行上下文栈)
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
    - 任何函数本质上都是通过某个对象来调用的，如果没有显示指定，就是 window 对象

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
    - 测试：没有显示指定调用对象时，调用对象是 window 对象
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
    - 每个函数都有一个prototype属性，默认指向一个Object**空对象**，即原型对象
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


### 显示原型与隐式原型
[top](#catalog)
- 显示原型（属性）：每个**函数function**中的`prototype`属性
- 隐式原型（属性）：每个**实例对象**的`__proto__`属性
- 隐式原型 与 显示原型的关系
    - `实例对象.__proto__ === (构造)函数.prototype`
    - 两者指向相同的对象

- 原型对象由js引擎自动添加啊
- 原型对象的添加时间
    - 显示原型对象：创建函数对象时，创建一个Object空对象并赋值
        - 相对与在**创建函数对象时**执行了
            ```js
            this.prototype = {};
            ```
    - 隐式原型对象：通过构造函数创建实例化对象时，使用显示原型对象进行赋值
        - 相当于在**构造函数内部**执行了
            ```js
            function 构造函数(...){
                // js引擎隐式执行
                this.__proto__ = 构造函数.prototype

                // 其他业务
                ...
            }
            ```
- 内存结构
    ```
    ```


### 原型链
[top](#catalog)

### instanceof原理
[top](#catalog)

### 与原型相关的问题
[top](#catalog)



## 执行上下文与执行上下文栈
[top](#catalog)

## 作用域与作用域栈
[top](#catalog)


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
