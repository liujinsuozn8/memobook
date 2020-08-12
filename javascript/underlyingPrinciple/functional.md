<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》

### 目录--JS的函数式语言特性
- [函数式语言的特点](#函数式语言的特点)
- [JS中的几种连续运算](#JS中的几种连续运算)
- [将逻辑结构转换为表达式](#将逻辑结构转换为表达式)
    - [条件语句的转换](#条件语句的转换)
    - [循环语句转换为尾递归](#循环语句转换为尾递归)
- [函数](#函数)
    - [函数声明](#函数声明)
    - [函数参数](#函数参数)
    - [与函数相关的几个基本问题](#与函数相关的几个基本问题)
    - [回调函数](#回调函数)
    - [立即执行函数IIFE](#立即执行函数IIFE)
    - [函数中的this对象](#函数中的this对象)
    - [闭包](#闭包)
        - [利用闭包的示例-循环变量添加事件监听](#利用闭包的示例-循环变量添加事件监听)
        - [闭包的基本知识](#闭包的基本知识)
        - [常见的闭包](#常见的闭包)
        - [闭包的作用](#闭包的作用)
        - [闭包的应用-创建独立的作用域](#闭包的应用-创建独立的作用域)
        - [闭包的应用-自定义js模块](#闭包的应用-自定义js模块)
        - [闭包的缺点-内存溢出与内存泄露](#闭包的缺点-内存溢出与内存泄露)
        - [与闭包相关的问题](#与闭包相关的问题)
- [](#)
- [](#)


# 函数式语言的特点
[top](#catalog)
- 函数式语言（范型）的基本特征
    - 函数等价与一个运算符
    - 连续的求值
- 函数式语言的运算模型
    - 运算（表达式） 产生结果
    - 结果 用于 下一次（或之后的）运算
- 在函数式语言中，**函数的特性**
    1. 基本特性: 可以被调用
        - 作为运算符来调用
        - 参数就是操作数，可以是普通数据、函数
    2. 可以作为操作数
    3. 可以保存数据
    4. 函数内的运算对函数外无副作用
- 理论上，函数式语言不需要寄存器或变量赋值
    - 原因
        - 运算输入A，得到输出B，B又是下一个运算的输入
        - 在连续的运算过程中，无需中间变量来寄存
- 现实中的问题
    - 绝大多数机器是基于冯诺依曼体系的计算机（基于存储与指令系统的），并不是基于**连续运算**的
    - 大多数的函数式运算是建立在虚拟机环境中的，如JVM

# JS中的几种连续运算
[top](#catalog)
- JS中的几种连续运算
    1. 连续赋值
    2. 三元表达式连用
    3. 连续逻辑运算
    4. 逗号 `,` 运算符与连续运算
    5. 嵌套的解构赋值
    6. 函数与方法的连续调用

- 连续赋值
    - 示例: [src/functional/consecutive_operation/var_assin.js](src/functional/consecutive_operation/var_assin.js)
        ```js
        var a,b,c,d

        a = b = c = d = 100;
        console.log(a,b,c,d);
        // 输出:
        // 100 100 100 100
        ```
    - 说明
        1. 先执行 `d = 100`，d 被赋值为 100
        2. 赋值语句的返回值就是本身，所以会返回 100
        3. `c = d = 100`，被解释为: `c = 100`，c 被赋值为 100
        4. 最终所有的变量都被设置为 100

- 三元表达式连用
    - 示例: [src/functional/consecutive_operation/ternary_operation.js](src/functional/consecutive_operation/ternary_operation.js)
        ```js
        var type = 'Array';

        // 三元表达式连用
        var Class = (type === 'String') ? String
                : (type === 'Boolean') ? Boolean
                : (type === 'Number') ? Number
                : (type === 'Array') ? Array
                : Object;

        var obj = new Class();
        console.log(Object.prototype.toString.call(obj)); // [object Array]
        ```
    - 三元表达式连续运算的关键
        - 运算产生值，值参与运算

- 连续逻辑运算
    - 示例
        ```js
        var a = 1, b = 2, c = 3, d = 4, e = 0;
        var result = a && b && c && d || e;
        console.log(result);    // 4
        ```
    - 将会返回最后一个参与罗运算的语句的结果
        - 因为 JS 的逻辑运算是短路的，所以一旦为true，就会立刻返回

- 逗号 `,` 运算符与连续运算
    - 逗号运算符表示从左向右连续运算
    - 返回值是 `,` 右操作数的值
    - 示例
        - 参考代码
            - [src/functional/consecutive_operation/comma.js](src/functional/consecutive_operation/comma.js)
        - 几种使用方式
            1. 配合 `()` 做赋值
                ```js
                var a = (1,2,3,4);
                console.log(a); // 4
                ```
            2. 作为函数返回值
                ```js
                function foo() {
                    return 1, 2, 3, 4;
                }
                console.log(foo()); // 4
                ```
            3. 在箭头函数中做连续运算
                ```js
                var x = 1, y = 2, w = 3
                var myfn = () => (x += y, w += x);
                console.log(myfn());    // 6
                ```
- 嵌套的解构赋值
    - 示例
        ```js
        var {x, y:{z}}={x:100, y:{z:200}}
        console.log(x); // 100
        // console.log(y);
        console.log(z); // 200
        ```

- 函数与方法的连续调用
    - 示例: [src/functional/consecutive_operation/methods.js](src/functional/consecutive_operation/methods.js)
        ```js
        function foo(){
            console.log('foo constructor');
        }
        foo.prototype.print = function(p){
            console.log(p);
        }

        new foo().print(1234);
        // 输出
        // foo constructor
        // 1234
        ```

# 将逻辑结构转换为表达式
## 条件语句的转换
[top](#catalog)
- `if` 分支、单语句的转换
    - 转换前
        ```js
        var flag = 100;
        if (flag > 10){
            console.log(flag);
        }
        ```
    - 转换后
        ```js
        var flag = 100;
        (flag > 10) && console.log(flag);
        ```
    - 使用三元运算符转换
        ```js
        var flag = 100;
        flag > 10 ? console.log(flag) : null;
        ```
- `if...else` 分支，单语句
    - 转换前
        ```js
        var flag = 100;
        if (flag > 10){
            console.log(flag);
        } else {
            console.log(flag + 100);
        }
        ```
    - 只能使用三元运算符转换
        ```js
        var flag = 100;
        flag > 10 ? console.log(flag) : console.log(flag + 100);
        ```

- `switch`多重分支，单语句
    - 转换前
        ```js
        var flag = 100;
        switch (flag) {
            case 100:
            case 200:
                console.log(1);
                break;
            case 300:
                console.log(2);
                break;
            case 400:
                console.log(3);
                break;
            default:
                console.log('not find');
        }
        ```
    - 使用三元运算符转换
        ```js
        (flag === 100 || flag === 200) ? console.log(1)
                        : (flag === 100) ? console.log(2)
                        : (flag === 100) ? console.log(3)
                        : console.log('not find');
        ```

## 循环语句转换为尾递归
[top](#catalog)
- 循环语句可以通过递归实现
- 为什么使用尾递归？
    1. 普通递归，需要保存私有数据和上下文环境，会消耗大量的栈空间
    2. 尾递归不会占用栈
- 实现尾递归的条件
    - 在函数的执行序列中，最后一个表达式中出现的递归
    - 这相当于在尾部产生了一个<span style='color:red'>无需返回的跳转指令</span>
        - 所以无需保存栈和上下文环境
- 示例
    - 转换前
        ```js
        for (var i=0; i<100; i++){
            console.log(i);
        }
        ```
    - 使用尾递归转换
        ```js
        function foo(i){
            console.log(i);
            (++i < 100) && foo(i);
        }

        foo(0);
        ```

# 函数
## 函数声明
[top](#catalog)
- 函数声明是**变量声明**的一种特殊形式
    - 所以在**不支持**`变量作用域`的位置，会逃逸到外部的作用域中
- <span style='color:red'>在ES5以后，表达式中声明的具名函数名，只影响该函数内的代码，不会影响该表达式所在的作用域</span>
    ```js
    // 在条件表达式内部创建具名函数
    if (function foo(){console.log(foo)});

    // 不会影响条件表达式所在的作用域
    console.log(typeof foo);    // undefined
    ```

## 函数参数
[top](#catalog)
- 如果函数不声明参数，可以在函数体中通过内部对象 `arguments` 来获取形参

- 检查函数参数个数
    - 调用函数时传入的参数个数: `arguments.length`
    - 检查函数声明时的参数个数: `函数名.length`
        - 对于包含默认参数的函数，从第一个默认参数开始，后面的**所有参数**都不会计入`函数名.length`
            ```js
            function foo(a,b,c=1234,d, e, f='test'){
                console.log(`arguments.length = ${arguments.length}`);
                console.log(`foo.length = ${foo.length}`);
                console.log(`a=${a}, b=${b}, c=${c}, d=${d}, e=${e}, f=${f}`)
            }

            foo(1,2,3);
            // arguments.length = 3
            // foo.length = 2
            // a=1, b=2, c=3, d=undefined, e=undefined, f=test
            ```


- 普通参数/可变参数
    - 调用函数时参数的数量是任意
        ```js
        // 有参函数
        function foo(a, b, c){}
        // 无参函数
        function bar(){}

        foo('aaa', 'bbb', 'cccc');
        bar(1,2,3,4);
        ```
- 默认参数
    - 默认参数可以使用在任何位置
        ```js
        function foo(a, b=1234, c){}

        foo('aaa');
        foo('aaa', 2345);
        foo('aaa', 2345, 'ccc');
        ```
- 剩余参数
    ```js
    function foo(a, ...more){}
    foo('aaa', 1, 2, 3, 4)
    ```
- 剩余参数，只捕获部分参数
    ```js
    function foo(a, ...[x, y]){}
    foo('aaa', 1, 2, 3) //只捕获到： 'aaa', 1, 2
    foo('aaa', 1, 2)
    ```js
- 对象参数解构
    ```js
    function foo({a, b, c:c的别名}){
        console.log(a)
        console.log(b)
        console.log(c的别名)
    }
    ```

## 与函数相关的几个基本问题
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

## 回调函数
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

## 立即执行函数IIFE
[top](#catalog)
- IIFE，等同于**匿名函数自调用**
    - 这样的函数只需要执行一次，执行后就可以丢弃

- 调用方式分析
    - 调用方式1
        1. `(...)` 通过分组运算符，使函数表达式求值并返回函数自身的引用
        2. `()` 调用返回的函数引用
            ```js
            (function(){
                console.log(new Date());
            })()
            ```
    - 调用方式2
        - 通过外部的 `(...)`，使得内部的函数调用
            ```js
            (function(){
                console.log(new Date());
            }())
            ```
    - 调用方式3
        - `void` 使后面的函数作为表达式执行
        ```js
        void function(){
            console.log(new Date())
        }()
        ```
- 无效的调用方法
    - 错误示例
        ```js
        function(){
            ...
        }()
        ```
    - 在语义上没有问题，但是在语法上有问题，会被解析为
        ```js
        function(){
            ...
        };
        (); // 会产生编译异常
        ```
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

## 函数中的this对象
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

- 构造函数中的this
    - 如果通过 `new 构造函数(...)` 的方式来调用函数并创建对象，this是新创建的对象
    - 如果直接调用函数：`构造函数(...)`，则 this 是 window对象
        - 在这种调用方式下，**有可能会更改 window对象 中的重要属性或函数**，需要注意

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

## 闭包
### 利用闭包的示例-循环变量添加事件监听
[top](#catalog)
- 示例的功能
    - 页面上有三个button
    - 点击一个button后，显示当前button的index
- 参考代码
    - [/javascript/underlyingPrinciple/src/closure/sample.html](/javascript/underlyingPrinciple/src/closure/sample.html)

- html
    ```html
    <button>btn01</button>
    <button>btn02</button>
    <button>btn03</button>
    ```

- js
    - 实现方式1：变量时，将索引绑定到元素对象
        ```js
        var btns = document.getElementsByTagName("button")

        for(var i=0, length=btns.length; i < length; i++){
            btns[i].index = i
            btns[i].onclick = function(){
                console.log(this.index)
            }
        }
        ```
    - 实现方式2：通过IIFE利用闭包来实现
        ```js
        var btns = document.getElementsByTagName("button")

        for(var i=0, length=btns.length; i < length; i++){
            (function(i){
                btns[i].onclick = function(){
                    console.log(i)
                }
            })(i)
        }
        ```

### 闭包的基本知识
[top](#catalog)
- 如何理解闭包
    - 理解方式一：嵌套函数的内部函数
    - 理解方式二：包含被引用变量/函数的对象

- 闭包产生的条件
    1. 存在函数嵌套
    2. 内部函数引用了外部函数的变量/函数
    3. 执行了外部函数
    4. 外部函数调用后，创建了函数对象
        - 不需要调用内部函数，只要有函数对象即可
        - 只有执行外部函数，才能观测到闭包的产生
            - chrome最新版的debug模式中，如果没有使用闭包(包括`console.log()`)，控制台中没有显示

- 闭包何时销毁
    - 闭包对象没有被任何变量引用时，成为垃圾对象，将会被 gc 回收

- 不同的函数创建方式，闭包的生成时间不同

    |函数创建|闭包生成时间|说明|
    |-|-|-|
    |函数声明|闭包在准备执行上下文时创建|在外部函数执行之前，准备执行上下文对象时，函数对象就已经创建。所以闭包在准备执行上下文时就已经创建完了|
    |函数表达式|闭包在执行表达式代码后创建|<ul><li>在准备外部函数的执行上下文对象时，函数只有一个声明，没有创建函数对象</li><li>只有执行表达式后才创建了函数对象，所以闭包在执行代码表达式后创建</li></ul>|

- 示例
    - js内容
        ```js
        function fn1(){
            var a = 2
            function fn2 (){
                console.log(a)
            }
            // console.log(fn2)

            return fn2
        }

        var f = fn1()
        f()
        f = null
        ```

    - `var a = 2` 处 debug时的闭包结果
        ```
        Local
            a: undefined
            fn2: ƒ fn2()
                length: 0
                name: "fn2"
                arguments: null
                caller: null
                prototype: {constructor: ƒ}
                __proto__: ƒ ()
                [[FunctionLocation]]: Script snippet %235:3
                [[Scopes]]: Scopes[3]
                    0: Closure (fn1)                        <<------- fn 闭包
                        a: undefined
                    1: Script {animations: {…}, customize: {…}, ntpApiHandle: {…}, doodles: {…}, iframesAndVoiceSearchDisabledForTesting: false, …}
                    2: Global {parent: Window, op
        ```
    - `f = null`，执行后，闭包 fn2的引用次数为0，成为垃圾对象，将会被 gc 回收

### 常见的闭包
[top](#catalog)
- 常见的闭包
    1. 将函数作为另一个函数的返回值
    2. 将函数作为实参传递给另一个函数调用

- 将函数作为另一个函数的返回值
    - 示例
        ```js
        function fn1(){
            var a = 2
            function fn2 (){
                a++
                console.log(a)
            }

            return fn2
        }

        var f = fn1()
        f() // 输出：3
        f() // 输出：4
        f() // 输出：5
        ```
    - 示例中的输出结果一直处于累加状态，说明局部变量 a 没有消失

- 将函数作为实参传递给另一个函数调用
    - 示例
        ```js
        function fn1(msg, time){
            setTimeout(
                function(){console.log(msg)},
                time
            )
        }

        fn1("test closure", 1000)
        ```
    - 产生的闭包
        - 产生原因
            - 调用了外部函数 fn1
            - 函数内部使用了外部函数的形参 msg

        ```js
        function(){console.log(msg)},
        ```

### 闭包的作用
[top](#catalog)
- 闭包的作用
    1. 延长局部变量/函数的生命周期
        - 使函数内部的 变量/函数 在函数执行完后，仍然保存在内存中
    2. 在函数外部 读写 函数内部的 变量/函数

- 示例说明
    - 说明代码
        ```js
        function fn1(){
            var a = 2
            function fn2 (){
                a++
                console.log(a)
            }

            function fn3(){
                a--
                console.log(a)
            }

            return fn3
        }

        var f = fn1()
        f() // 输出：3
        ```
    - `var f = fn1()`执行后 fn1 内部的情况
        - 执行 fn1 时，产生了两个闭包 fn2、fn3
        - fn1 只返回了 fn3，并且fn3被引用，所以只有fn3存活，fn2被销毁
        - 局部变量 a 被 fn3引用，所以 a 也存活
        - 如果只执行了 `fn1()`，**没有对返回结果进行引用**，则 fn3也将会被销毁


- 与闭包的作用相关的两个问题
    - 函数执行后，函数内部声明的局部变量/函数是否还存在？
        - 一般不存在
        - 存在与闭包中的内容才可能存在
        - 作为返回值返回的内容，返回后如果被引用了，也保持存在，如：`var 变量 = 函数()`
    - 函数外部能直接访问函数内部的局部变量/函数吗？
        - 一般不能
        - 可以通过闭包来访问

### 闭包的应用-创建独立的作用域
[top](#catalog)
- 通过闭包函数，可创建独立的作用域。将变量保存在自身的作用域之后，既可以使用，不会受外部变量的影响

- 示例
    - 参考代码
        - [src/closure/create_scope.html](src/closure/create_scope.html)
    - js内容
        ```js
        // 1. 默认情况下 for 没有作用域，每次用的都是同一个i
        var btn01s = document.querySelectorAll('.btn01');
        for(var i = 0; i < btn01s.length; i++){
            btn01s[i].onclick = function(){ console.log(i)};
            // 每次都会输出5
        }

        // 2. 闭包函数有自己独立的作用域，可以保存变量
        var btn02s = document.querySelectorAll('.btn02');
        for (var i = 0; i < btn02s.length; i++){
            (function(index){
                btn02s[index].onclick = function(){ console.log(index)};
            })(i)
        }

        // 3. 使用let之后，每次循环都会创建一个作用域，每次使用的 i 都是自己作用域内部的变量
        var btn03s = document.querySelectorAll('.btn03');
        for (let i = 0; i < btn03s.length; i++){
            btn03s[i].onclick = function(){ console.log(i)};
        }
        ```
### 闭包的应用-自定义js模块
[top](#catalog)
- 什么是js模块
    - 具有特定功能的js文件
    - 将所有的数据和功能都封装在一个函数内部（私有的）
    - 只向外暴露一个包含n个方法的对象或函数
    - 模块的使用者，只需要通过模块暴露的对象调用方法来实现对应的功能

- 示例
    - 参考代码
        - [src/closure/custome.html](src/closure/custome.html)
        - [src/closure/custome.js](src/closure/custome.js)
    - js内容
        ```js
        // 通过IIFE执行函数，分别在内部方法中引用外部函数的局部变量，来创建闭包
        (function(window){
            var msg = "test msg";
            function showLower(){
                console.log(msg.toLocaleLowerCase())
            }

            function showUpper(){
                console.log(msg.toLocaleUpperCase())
            }

            window.msgBox = {
                showLower:showLower,
                showUpper:showUpper
            }
        })(window)
        // 传入参数window，方便代码压缩
        ```
    - html内容
        ```html
        <!doctype html>
        <html>
            <head>
                <meta charset="utf-8">
                <title>custome test</title>
                <script type="text/javascript" src="custome.js"></script>
                <script type="text/javascript">
                    msgBox.showLower()
                    msgBox.showUpper()
                </script>

            </head>
            <body>
            </body>
        </html>
        ```

### 闭包的缺点-内存溢出与内存泄露
[top](#catalog)
- 闭包的缺点
    - 函数执行完后，函数内的局部变量没有释放，占用内存的时间变长
    - 容易造成内存泄露
- 内存问题的解决方法
    - 尽量少用闭包
    - <label style="color:red">及时释放变量</label>（`闭包引用=null`）

- 内存溢出
    - 一种程序运行时出现的错误
    - 当 `运行时需要的内存 > 剩余内存` 时，就会抛出内存溢出的错误

- 内存泄漏
    - 占用的内存没有及时释放
    - 内粗泄露积累多了就容易导致内存溢出
    - 场景的内存泄漏
        - 意外的全局变量，即没有使用 `var` 来声明变量
        - 没有及时清理的计时器或回调函数
        - 闭包

### 与闭包相关的问题
[top](#catalog)
1. 代码输出什么?
    - 代码
        ```js
        var name = "the window"
        var object={
            name:"my object",
            getNameFunc:function(){
                return function(){
                    return this.name
                }
            }
        }

        console.log(object.getNameFunc()())
        ```
    - 输出结果:`the window`
    - 结果分析
        - getNameFunc中没有闭包，内部函数中没有任何变量，所以没有产生闭包
        - getNameFunc执行后返回的就是一个普通函数，内部函数直接执行，this就是window，所以显示 `the window`

2. 代码输出什么
    - 代码
        ```js
        var name = "the window"
        var object={
            name:"my object",
            getNameFunc:function(){
                var that = this;
                return function(){
                    return that.name
                }
            }
        }

        console.log(object.getNameFunc()())
        ```
    - 输出结果:`my object`
    - 结果分析
        - getNameFunc 的内部函数引用了外部函数的局部变量 that，所以产生了闭包
        - getNameFunc 执行后，将闭包对象返回，所以执行闭包对象后输出：`my object`

3. 代码输出什么
    - 代码
        ```js
        function fun(n, o){
            console.log(o)
            return {
                fun:function(m){
                    return fun(m,n)
                }
            }
        }

        var a = fun(0); a.fun(1); a.fun(2); a.fun(3)
        var b = fun(0).fun(1).fun(2).fun(3)
        var c = fun(0).fun(1); c.fun(2); c.fun(3)
        ```
    - 输出内容
        ```
        undefined    0    0    0
        undefined    0    1    2
        undefined    0    1    1
        ```

# 其他
- 系统的结果必然是值，并且可以通过一系列的运算来得到结果

[top](#catalog)