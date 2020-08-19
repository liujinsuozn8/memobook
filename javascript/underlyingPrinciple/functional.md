<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》
    - https://www.bilibili.com/video/BV14s411E7qf

### 目录--JS的函数式语言特性
- [函数式语言的特点](#函数式语言的特点)
- [JS中的几种连续运算](#JS中的几种连续运算)
- [将逻辑结构转换为表达式](#将逻辑结构转换为表达式)
    - [条件语句的转换](#条件语句的转换)
    - [循环语句转换为尾递归](#循环语句转换为尾递归)
- [函数的特殊成员](#函数的特殊成员)
- [函数声明](#函数声明)
- [函数表达式的形式](#函数表达式的形式)
- [函数参数](#函数参数)
    - [普通参数/可变参数](#普通参数/可变参数)
    - [默认参数](#默认参数)
    - [剩余参数](#剩余参数)
    - [模板参数](#模板参数)
    - [arguments--参数对象](#arguments--参数对象)
    - [非简单参数](#非简单参数)
    - [参数的数量](#参数的数量)
    - [非惰性求值---函数参数的特性](#非惰性求值---函数参数的特性)
    - [传值参数与引用求值](#传值参数与引用求值)
- [函数的名字](#函数的名字)
- [函数与一般对象的区别](#函数与一般对象的区别)
- [JS中支持的函数](#JS中支持的函数)
    - [JS中的10种函数](#JS中的10种函数)
    - [一般函数--具名函数与匿名函数](#一般函数--具名函数与匿名函数)
    - [生成器函数](#生成器函数)
    - [类](#类)
    - [类/对象的方法](#类/对象的方法)
    - [箭头函数](#箭头函数)
    - [bind--绑定函数](#bind--绑定函数)
    - [代理函数](#代理函数)
- [函数的数据性质](#函数的数据性质)
- [函数与逻辑结构](#函数与逻辑结构)
    - [函数与递归](#函数与递归)
- [函数的行为](#函数的行为)
    - [构造](#构造)
    - [函数调用](#函数调用)
    - [方法调用](#方法调用)
    - [调用时的this](#调用时的this)
    - [迭代](#迭代)
- [与函数相关的几个基本问题](#与函数相关的几个基本问题)
- [回调函数](#回调函数)
- [立即执行函数IIFE](#立即执行函数IIFE)
- [函数中的this对象](#函数中的this对象)
- [闭包](#闭包)
    - [利用闭包的示例-循环变量添加事件监听](#利用闭包的示例-循环变量添加事件监听)
    - [函数实例](#函数实例)
    - [闭包的基本知识](#闭包的基本知识)
    - [常见的闭包](#常见的闭包)
    - [闭包的作用](#闭包的作用)
    - [闭包的应用-创建独立的作用域](#闭包的应用-创建独立的作用域)
    - [闭包的应用-自定义js模块](#闭包的应用-自定义js模块)
    - [闭包的缺点-内存溢出与内存泄露](#闭包的缺点-内存溢出与内存泄露)
    - [与闭包相关的问题](#与闭包相关的问题)
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

# 函数的特殊成员
[top](#catalog)

|成员|类型|分类|含义|
|-|-|-|-|
|call|function|动态特性，继承自 `Function.prototype`||
|apply|function|动态特性，继承自 `Function.prototype`||
|bind|function|动态特性，继承自 `Function.prototype`|创建绑定函数|
|name|string|函数式语言，总是重写为自有的成员|函数名|
|arguments|object|函数式语言，总是重写为自有的成员|函数的形参对象|
|length|number|函数式语言，总是重写为自有的成员|函数的形参数量|
|caller|function|函数式语言，总是重写为自有的成员|函数自身，严格模式下无法使用|
|prototype|object|对象系统:原型|函数的原型|

# 函数声明
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
# 函数表达式的形式
[top](#catalog)
- `var a = fucntion(){}`
- `obj = {a :function(){}}`
- `dom.onclick = function(){}`
- `(function foo(){})()`
- `return function xxx(){}`
    - **这种形式容易被当作函数声明，实际是函数表达式**

# 函数参数
## 普通参数/可变参数
[top](#catalog)
- 调用函数时参数的数量是任意
    ```js
    // 有参函数
    function foo(a, b, c){}
    // 无参函数
    function bar(){}

    foo('aaa', 'bbb', 'cccc');
    bar(1,2,3,4);
    ```

## 默认参数
[top](#catalog)
- 默认参数可以使用在任何位置
    ```js
    function foo(a, b=1234, c){}

    foo('aaa');
    foo('aaa', 2345);
    foo('aaa', 2345, 'ccc');
    ```
## 剩余参数
[top](#catalog)
- 剩余参数只能**声明在参数列表的最后**
- 剩余参数最终是一个**数组变量**
- <span style='color:red'>剩余参数与展开运算没有关系，只是运算符相同</span>
- 如果**没有传入剩余参数**，剩余参数将会是**一个空数组对象**
- 使用方式
    - 参考代码
        - [src/functional/fn_args/rest.js](src/functional/fn_args/rest.js)
    - 基本使用
        ```js
        function foo01(a, ...more){
            console.log(a);
            console.log(more);
        }
        foo01('aaa', 1, 2, 3, 4);
        // aaa
        // [ 1, 2, 3, 4 ]
        ```
    - 用模板捕获部分参数
        ```js
        function foo02(a, ...[x, y]){
            console.log(`a = ${a}`);
            console.log(`x = ${x}`);
            console.log(`y = ${y}`);
        }
        foo02('aaa', 1, 2, 3);
        // a = aaa
        // x = 1
        // y = 2
        ```
    - 在模板中，继续捕获剩余参数
        ```js
        function foo03(a, ...[x, y, ...z]) {
            console.log(`a = ${a}`);
            console.log(`x = ${x}`);
            console.log(`y = ${y}`);
            console.log(`z = ${z}`);
        }
        foo03('aaa', 1, 2, 3, 4, 5, 6, 7, 8);
        // a = aaa
        // x = 1
        // y = 2
        // z = 3,4,5,6,7,8
        ```
    - 如果**没有传入剩余参数**，剩余参数将会是**一个空数组对象**
        ```js
        function foo04(a, ...b){
            console.log(a);
            console.log(b);
        }

        foo04('aaa');
        // aaa
        // []
        ```

## 模板参数
[top](#catalog)
- 不能传入null 或 undefined
- 调用函数时，会通过解构赋值的方式将实参绑定到多个标识符中
- 参数的书写
    - 对象模板
        - 需要传入一个对象
        - 如果全部是普通变量，需要按照对象字面量的形式传递参数
    - 数组模板
        - 需要传入一个数组
        - 如果全部是普通变量，需要按照数组字面量的形式传递参数
        - 如果传递一个**值数据类型**作为参数，会产生异常
        - 可以在数组模板中添加**剩余参数**来吸收参数
            - 如果剩余参数没有吸收到任何参数，则是一个 **空数组对象**
    - 可以为**模板整体**，或者**模板中的某个参数** 设置默认值
- 使用方式
    - 参考代码
        - [src/functional/fn_args/model_args.js](src/functional/fn_args/model_args.js)
    - 对象模板参数
        ```js
        function fooObj(a, { b, c, d }) {
            console.log(arguments);
            console.log(a);
            console.log(b);
            console.log(c);
            console.log(d);
        }

        var obj = {
            b: 'bbb',
            c: 'ccc',
            d: 'ddd',
            e: 'eee'
        }

        // 1.1 向对象模板传递一个对象
        fooObj('aaa', obj);
        // [Arguments] {
        // '0': 'aaa',
        // '1': { b: 'bbb', c: 'ccc', d: 'ddd', e: 'eee' }
        // }
        // aaa
        // bbb
        // ccc
        // ddd

        // 1.2 通过对象字面量传递参数
        fooObj('aaa', {b:1, c:2, d:3, e:4})
        // [Arguments] { '0': 'aaa', '1': { b: 1, c: 2, d: 3, e: 4 } }
        // aaa
        // 1
        // 2
        // 3
        ```
    - 数组模板参数
        ```js
        function fooArray(a, [b, c, d]){
            console.log(arguments);
            console.log(a);
            console.log(b);
            console.log(c);
            console.log(d);
        }

        fooArray(1, [2, 3, 4, 5, 6]);
        // fooArray(1, 2);  // TypeError: undefined is not a function
        ```
    - 在数组模板中，使用**剩余参数**来吸收参数
        ```js
        function fooArray02(a, [b, c, ...more]){
            console.log(arguments);
            console.log(a);
            console.log(b);
            console.log(c);
            console.log(more);
        }
        fooArray02(1, [2]);
        // [Arguments] { '0': 1, '1': [ 2 ] }
        // 1
        // 2
        // undefined
        // []

        fooArray02(1, [2, 3, 4, 5, 6, 7]);
        // [Arguments] { '0': 1, '1': [ 2, 3, 4, 5, 6, 7 ] }
        // 1
        // 2
        // 3
        // [ 4, 5, 6, 7 ]
        ```
    - 为**模板整体**设置默认值
        - 为对象模板的整体设置默认值
            ```js
            var objDefault = {b: 'bbb', c:'ccc', d:'ddd'};
            function objModelDefault(a, {b, c, d}=objDefault){
                console.log(arguments);
                console.log(a);
                console.log(b);
                console.log(c);
                console.log(d);
            }

            // 使用模板的默认值
            objModelDefault(1234);
            // [Arguments] { '0': 1234 }
            // 1234
            // bbb
            // ccc
            // ddd

            // 自定义模板参数的数据
            objModelDefault(1234, {b: 'abc', c:'def', d:'ghi'});
            // [Arguments] { '0': 1234, '1': { b: 'abc', c: 'def', d: 'ghi' } }
            // 1234
            // abc
            // def
            // ghi
            ```

        - 为数组模板的整体设置默认值
            ```js
            var arrDefault = ['bbb', 'ccc', 'ddd']
            function arrModelDefault(a, [b, c, d]=arrDefault){
                console.log(arguments);
                console.log(a);
                console.log(b);
                console.log(c);
                console.log(d);
            }

            // 使用模板的默认值
            arrModelDefault(1234);
            // [Arguments] { '0': 1234 }
            // 1234
            // bbb
            // ccc
            // ddd

            // 自定义模板参数的数据
            arrModelDefault(1234, ['abc', 'def', 'ghi']);
            // [Arguments] { '0': 1234, '1': [ 'abc', 'def', 'ghi' ] }
            // 1234
            // abc
            // def
            // ghi
            ```
    - 为**模板中的某个参数** 设置默认值
        - 为对象模板中的参数设置默认值
            ```js
            function objModelParamDefault(a, {b, c=100, d}){
                console.log(arguments);
                console.log(a);
                console.log(b);
                console.log(c);
                console.log(d);
            }

            objModelParamDefault('aaa', {b:'bbb', d:'ddd'});
            // [Arguments] { '0': 'aaa', '1': { b: 'bbb', d: 'ddd' } }
            // aaa
            // bbb
            // 100
            // ddd

            objModelParamDefault('aaa', {b:'bbb', d:'ddd', c:'ccc'});
            // [Arguments] { '0': 'aaa', '1': { b: 'bbb', d: 'ddd', c: 'ccc' } }
            // aaa
            // bbb
            // ccc
            // ddd
            ```
        - 为数组模板中的参数设置默认值
            ```js
            function arrModelParamDefault(a, [b, c, d=1234]){
                console.log(arguments);
                console.log(a);
                console.log(b);
                console.log(c);
                console.log(d);
            }

            // 使用默认值
            arrModelParamDefault('aaa', ['bbb', 'ccc']);
            // [Arguments] { '0': 'aaa', '1': [ 'bbb', 'ccc' ] }
            // aaa
            // bbb
            // ccc
            // 1234

            // 使用自定义数据
            arrModelParamDefault('aaa', ['bbb', 'ccc', 'ddd']);
            // [Arguments] { '0': 'aaa', '1': [ 'bbb', 'ccc', 'ddd' ] }
            // aaa
            // bbb
            // ccc
            // ddd
            ```

## arguments--参数对象
[top](#catalog)
- `arguments` 是一个类数组对象
    - 可以对 `arguments` 使用绝大多数数组的操作，包括:
        - 模板赋值
        - 展开
        - 解构
- `arguments` <span style='color:red'>严格等于</span>调用函数时，传入的参数
    - `arguments` 不会受 非简单参数的影响
    - `arguments` 只是用来反映: 调用函数时，传递了什么数据
- <span style='color:red'>除了箭头函数</span>，每个函数<span style='color:red'>被调用时</span>，都会创建 `arguments`
- `arguments.length`，可以获取实参数量
- 不存在 `Arguments` 类/构造函数
- 手动构造一个与 `arguments` 类似的对象
    - 参考代码
        - [src/functional/fn_args/create_arguments.js](src/functional/fn_args/create_arguments.js)

    - 代码内容
        ```js
        // 1. 创建一个 arguments 的构造器
        // 2. 通过剩余参数的方式，吸收所有参数到一个【数组】中
        function Arguments(...args){
            // 3. 直接将数组 args 的原型设置为 Arguments，使 args 成为 Arguments 的实例
            // 4. 将设置后的 args 对象【作为实例化结果】返回，替代默认生成的 this
            return Object.setPrototypeOf(args, Arguments.prototype);
        }
        /* 5. 为 Arguments 添加 和 Array 相同的迭代方法
            使 Arguments 可以具有和数组类似的操作
        */
        Arguments.prototype[Symbol.iterator] = Array.prototype[Symbol.iterator];

        // 6. 实例化对象
        var args = new Arguments('aaa', 'bbb', 'ccc', 'ddd');

        // 7. 以数组的方式操作 args
        console.log(args.length);
        console.log(args[1]);
        ```

- <span style='color:red'>（简单参数）</span> 形参 与 `arguments[x]` 是绑定的
    - <span style='color:red'>非简单参数 无法 与 arguments绑定</span>
    - 直接修改形参的值，会影响 `arguments[x]`; 修改 `arguments[x]` 也会影响形参
    - 参考代码
        - [src/functional/fn_args/arguments.js](src/functional/fn_args/arguments.js)
    - 代码内容
        ```js
        function foo(p1, p2){
            // 1.1 初始值
            console.log(arguments);
            console.log(`p1 = ${p1}`);
            console.log(`p2 = ${p2}`);

            // 1.2 修改形参 p1
            p1 = p1 + '12345';
            console.log(`arguments[0] = ${arguments[0]}`);
            console.log(`p1 = ${p1}`);

            // 1.3 修改 arguments[1]
            arguments[1] = arguments[1] + 'abcde';
            console.log(`arguments[1] = ${arguments[1]}`);
            console.log(`p2 = ${p2}`);
        }

        foo('aaa', 'bbb');
        // [Arguments] { '0': 'aaa', '1': 'bbb' }
        // p1 = aaa
        // p2 = bbb
        // arguments[0] = aaa12345  <<<< 直接修改形参
        // p1 = aaa12345
        // arguments[1] = bbbabcde  <<<< 直接修改 arguments[1]
        // p2 = bbbabcde
        ```

- 对 `arguments` 解构得到的变量，与 `arguments[x]` **不是绑定的**
    - 无论解构得到的是一个变量，还是剩余参数，与 `arguments[x]` 都不绑定
    - 参考代码
        - [src/functional/fn_args/arguments.js](src/functional/fn_args/arguments.js)
    - 代码内容
        - 普通的解构
            ```js
            function foo2(p1, p2){
                // 解构获取参数
                var [x1, x2] = arguments
                console.log(`x1 = ${x1}`)
                console.log(`x2 = ${x2}`)

                // 1.2 修改解构得到的变量
                x1 = x1 + '12345';
                console.log(`arguments[0] = ${arguments[0]}`);
                console.log(`x1 = ${x1}`);

                // 1.3 修改 arguments[1]
                arguments[1] = arguments[1] + 'abcde';
                console.log(`arguments[1] = ${arguments[1]}`);
                console.log(`x2 = ${x2}`);
            }

            foo2('aaa', 'bbb');
            // x1 = aaa                     <<<<< 解构的结果
            // x2 = bbb
            // arguments[0] = aaa           <<<<< 修改解构得到的变量，不会影响arguments
            // x1 = aaa12345
            // arguments[1] = bbbabcde      <<<<< 修改 arguments[1]，不会影响解构得到的变量
            // x2 = bbb
            ```
        - 包含剩余参数的解构
            ```js
            function foo3(p1){
                // 解构获取参数
                var [x1, ...more] = arguments
                console.log(`x1 = ${x1}`);
                console.log(`more = ${more}`);

                // 1.2 修改解构得到的变量
                more[0] = 1000;
                console.log(`arguments[1] = ${arguments[1]}`);
                console.log(`more = ${more}`);

                // 1.3 修改 arguments[1]
                arguments[1] = arguments[1] + 'abcde';
                console.log(`arguments[1] = ${arguments[1]}`);
                console.log(`more = ${more}`);
            }

            foo3('aaa', 'bbb', 'ccc','ddd','eee');
            // x1 = aaa                     <<<<< 解构的结果
            // more = bbb,ccc,ddd,eee
            // arguments[1] = bbb           <<<<< 修改剩余参数，不会影响arguments
            // more = 1000,ccc,ddd,eee
            // arguments[1] = bbbabcde      <<<<< 修改arguments，不会影响剩余参数
            // more = 1000,ccc,ddd,eee
            ```

## 非简单参数
[top](#catalog)
- 非简单参数包括
    - 默认参数
    - 剩余参数
    - 模板参数
- 非简单参数会产生 3 种限制
    1. 无法直接使用严格模式
        - 函数内，不能通过 `use strict` 切换到严格模式
        - 可以将函数包含在一个 **严格模式的环境内**，来使用严格模式
    2. 不接受重名参数
    3. 形参 与 `arguments` 自动解除绑定关系

- 调用函数时的参数设置方式
    - 两种参数设置方式
        1. **非简单参数**用: 初始器赋值
        2. **简单参数**与参数对象 `arguments` 绑定

    - `初始器` 如何为 非简单参数赋值？
        - 前提
            - 非简单参数中包含默认值，如 `x=100`
            - 必须在实参传入之间，执行设置默认值的表达式
        - 何时设置？
            - 在创建执行上下文时
            - 先执行表达式，并用表达式的返回值作为该形参的初始值
    - 形参与参数对象 `arguments` 绑定的方式
        - 直接将形参，<span style='color:red'>按顺序</span>与`arguments`中的每一个实参进行绑定

- 非简单参数产生的**限制**基本都**来自**: `初始器`
    1. 如果重名，初始器无法正常赋值
    2. 用初始器赋值就已经脱离了 `arguments`，无法再实现绑定

- <span style='color:red'>如果使用了非简单参数，则不应该再使用 arguments</span>
    - 保证参数的使用、修改是有效的

- 示例
    - 非简单参数与 `arguments` 脱离绑定
        - 参考代码
            - [src/functional/fn_args/unsimple_args.js](src/functional/fn_args/unsimple_args.js)
        - 代码内容
            ```js
            function foo (a, {b, c, d}){
                console.log(arguments);
                console.log(a, b, c, d);

                a = 100;
                console.log(a);
                console.log(arguments);

                b = 200;
                console.log(b);
                console.log(arguments);
            }

            foo(1, {b:2, c:3, d:4});
            // [Arguments] { '0': 1, '1': { b: 2, c: 3, d: 4 } }
            // 1 2 3 4
            // 100      <<<< 修改普形参
            // [Arguments] { '0': 1, '1': { b: 2, c: 3, d: 4 } }    <<<< 无法影响arguments
            // 200      <<<< 修改模板内的形参
            // [Arguments] { '0': 1, '1': { b: 2, c: 3, d: 4 } }    <<<< 无法影响arguments
            ```


## 参数的数量
[top](#catalog)
- 如果函数不声明参数，可以在函数体中通过内部对象 `arguments` 来获取形参

- 实参数量: `arguments.length`
    - 实参数量与函数声明中的形参数量没有任何关系
    - **实参的数量只取决于调用函数时，传递的参数数量**
- 形参数量: `函数名.length`
    - 对于包含默认参数的函数，从第一个默认参数开始，后面的**所有参数**都不会计入形参数量
        ```js
        function fooDefault(a,b,c=1234,d, e, f='test'){
            console.log(`arguments.length = ${arguments.length}`);
            console.log(`fooDefault.length = ${fooDefault.length}`);
            console.log(`a=${a}, b=${b}, c=${c}, d=${d}, e=${e}, f=${f}`)
        }

        fooDefault(1,2,3);
        // arguments.length = 3
        // fooDefault.length = 2
        // a=1, b=2, c=3, d=undefined, e=undefined, f=test
        ```
    - 剩余参数会计入形参数量中，但是**只能算 1 个**
        ```js
        function fooRest(a, b, other) {
            console.log(`a = ${a}, b = ${b}`);
            console.log(`other = ${other}`);
            console.log(`arguments.length = ${arguments.length}`)
            console.log(`fooRest.length = ${fooRest.length}`)
        }

        fooRest(1, 2, 3, 4, 5, 6);
        // a = 1, b = 2
        // other = 3
        // arguments.length = 6
        // fooRest.length = 3
        ```
    - 模板参数会计入形参数量中，但是无论模板内部有多少个变量，整体上**只能算 1 个**
        ```js
        function fooModel(a, { b, c, d }) {
            console.log(`arguments.length=${arguments.length}`);
            console.log(`fooModel.length=${fooModel.length}`);
        }

        fooModel('aaa', {b: 'bbb', c:'ccc', d:'ddd', e:'eee'});
        // arguments.length=2
        // fooModel.length=2
        ```

## 非惰性求值---函数参数的特性
[top](#catalog)
- 参数的`非惰性求值`特性
    - 如果参数中包含表达式，在调用函数之前，将会从左向右，计算所有的表达式计算
    - 真正发起函数调用是，<span style='color:red'>实参都会变成表达式的计算结果</span>

- `非惰性求值` 的最终目的
    - 完成**传值**操作
    - 而不是传递表达式、操作数等，ES规范类型中的**引用**

- 每个实参是对应位置的表达式计算结果
    - 表达式之间可以通过使用相同的变量而互相影响，但是最后的实参不会被影响
    - 示例
        1. 如函数
            ```js
            function print(msg){}
            ```
        2. 当使用多个表达式作为参数时
            ```js
            var a = 20;
            print(a+=20, a*=2, 'value:'+a);
            ```
        3. 表达式全部运算后的结果是
            ```js
            a+=20   // 赋值表达式，返回 40
            a*=2    // 赋值表达式，返回 80
            'value:'+a  // 单值表达式语句，返回 'value:80'
            ```
        4. 最终发起调用的实参
            ```js
            print(40, 80, 'value:80');
            ```
        5. 函数的 `arguments` 会包含全部的参数
        6. 因为 `print(msg)` 只有一个形参，所以 ，但是形参 `msg` 只会<span style='color:red'>按顺序绑定</span>第一个实参 `40`

- 示例
    - 参考代码
        - [src/functional/fn_args/unlazy.js](src/functional/fn_args/unlazy.js)
    - 代码内容
        1. 函数只有一个参数
            ```js
            function print(msg){
                console.log(arguments);
                console.log(msg);
            }
            var a = 20;

            // 向只有一个参数的函数传递多个表达式实参
            print(a+=20, a*=2, 'value:'+a);
            // 输出:
            // [Arguments] { '0': 40, '1': 80, '2': 'value:80' } <<<< 表达式全部计算完成
            // 40       <<<<< 但是只有第一个参数有效
            ```
        2. 函数有多个参数
            ```js
            function restPrint(a, b, c){
                console.log(arguments);
                console.log(a, b, c);
            }

            var b = 20;

            // 向函数传递多个表达式实参
            restPrint(b+=20, b*=2, 'value:'+b);

            // 输出:
            // [Arguments] { '0': 40, '1': 80, '2': 'value:80' } <<<< 表达式全部计算完成
            // 40 80 value:80
            ```

## 传值参数与引用求值
[top](#catalog)
- 什么是传值参数？
    - 调用函数时，传递的是值，而不是表达式、操作数等ES规范类型中的引用
    - 通过使用传值参数，可以隔离函数的内部与外部，使两部分作用域不会互相干扰
        - 即使直接操作 `arguments` 也无法干扰函数外部
    - `非惰性求值` 本身就是对 传值参数的一种实现

- 什么是 引用求值？
    - 一个引用同时包括: 值 和 引用 的含义
        - 即传递某个<span style='color:red'>表达式运算结果的非值信息</span>
    - 引用求值 与 传值参数 的**作用相反**，函数内的运算可能会影响函数外部
        - 因为缺失了 `非值的信息`，所以无法到达函数外部
    - 如 `obj.methods()` 中的 `obj.methods`
        - `obj.methods` 是从对象中获取一个方法成员
        - `obj.methods` 操作的结果在后续运算中会作为<span style='color:red'>引用</span>使用
            - 这使得 `obj` 可以当作 `this` 对象

- 涉及到**引用求值**的操作

    |使用引用求值的代码|引用求值的部分|操作内容|
    |-|-|-|
    |`obj.methods()`|`obj.methods`|对象方法获取|
    |`(obj.methods)()`|`(obj.methods)`|分组运算符不会影响内部的运算结果，相当于`obj.methods`|

- 涉及到**传值参数**的操作
    - `(1, 2, 3.., obj.methods)()`，`()`中使用 `,` 进行多次运算
        - 最终返回的是 `obj.methods`
        - 但是 `,`的连续运算结果是最后一个表达式的<span style='color:red'>值</span>
        - 运算结果只有值，缺失了`非值的信息`，导致 `obj` 无法被当作 `this` 对象
    - `fn(obj.method)`，将对象的方法作为函数的参数
        - 如
            ```js
            function fn (cb){
                cb();   // 只有函数对象本身，无法使用 this
            }

            fn(obj.method);
            ```
        - 执行 `fn(obj.method)` 时，实参表达式的运算结果是<span style='color:red'>值</span>，缺失了`非值的信息`，导致 `obj` 无法被当作 `this` 对象
    - 普通的对象调用
        - 如
            ```js
            var obj = {name:'bob'}

            function foo(o){
                // 虽然看起来改变了函数外部，但实际是改变了 o 自身的内存空间
                o.name='test';
            }

            foo(obj);   // 传递的仍然是值
            ```

# 函数的名字
[top](#catalog)
- 函数的名字: `函数.name`
    - 该属性是不可靠的，可修改、可删除
        - 函数名可以和函数声明时的标识符不同
    - `name`属性是每个函数的`自有属性`
    - **匿名函数的函数名**等于赋值表达式的**标识符名**
- 函数名的默认数据描述符
    ```js
    { value: '函数名', writable: false, enumerable: false, configurable: true }
    ```
- 函数名的操作
    - 查询
        - `函数.name`，直接通过属性名访问
        - 通过  `Object.getOwnPropertyDescriptor(函数, 'name')` 获取数据描述符
    - 删除
        - `delete 函数.name`
            - 因为默认数据描述符中 `configurable === true`, 所以属性名可以删除
    - 修改
        - `Object.defineProperty(函数, 'name', {...})`
            - 只能通过覆盖描述符的方式来修改函数名
        - 无法通过**属性赋值**的方式来修改函数名

- 示例
    - 参考代码
        - [src/functional/fn_type/fn_name.js](src/functional/fn_type/fn_name.js)
    - 代码内容
        ```js
        // 0. 创建一个匿名函数、一个具名函数
        var x = function(){};
        var y = function foo(){};

        // 1. 输出函数名
        console.log(x.name);    // x
        console.log(y.name);    // foo

        // 2. name 属性是函数的【自有属性】
        console.log(x.hasOwnProperty('name'));

        // 3. 输出函数名的数据描述符
        // { value: 'x', writable: false, enumerable: false, configurable: true }
        console.log(Object.getOwnPropertyDescriptor(x, 'name'));
        // { value: 'foo', writable: false, enumerable: false, configurable: true }
        console.log(Object.getOwnPropertyDescriptor(y, 'name'));

        // 4. 删除函数名
        delete x.name;
        delete y.name;
        console.log(Object.getOwnPropertyDescriptor(x, 'name'));    // undefined
        console.log(Object.getOwnPropertyDescriptor(y, 'name'));    // undefined

        // 5. 无法通过属性赋值的方式来修改函数名
        x.name = 'aaa';
        console.log( x.hasOwnProperty('name') ); // false，不包含 name 属性

        // 6. 通过数据描述符来修改函数名
        Object.defineProperty(x, 'name', {value:'aaa', writable:false, enumerable:false, configurable:true});
        console.log(x.name);    // aaa
        // { value: 'aaa', writable: false, enumerable: false, configurable: true }
        console.log(Object.getOwnPropertyDescriptor(x, 'name'));
        ```

# 函数与一般对象的区别
[top](#catalog)
- 函数的内部初始化了两个内部槽
    1. `[[Construct]]`，决定了 `new` 的构造行为
    2. `[[Call]]`，决定了函数调用的行为
- 初始化了两个或其中一个
- 类中虽然有 `[[Call]]`，但是无法作为函数来调用
    - 如果以函数的方式调用会产生异常
    - 可以通过Proxy为类添加 apply，来进行函数调用

# JS中支持的函数
## JS中的10种函数
[top](#catalog)

|函数|是否有<br>声明语法|能否用于<br>表达式|是否支持<br>函数名|限制|
|-|-|-|-|-|
|一般函数: `function xxx(){}`|Y|Y|可选|-|
|生成器:  `function* xxx(){}`|Y|Y|可选|不能用作`构造器`|
|类: `class XXX{}`|Y|Y|可选|不能用作函数，如`Class()`<br>只能通过 `new` 调用|
|异步函数: `async function xxx(){}`|Y|Y|可选|不能用作`构造器`|
|异步生成器函数: `async function* xxx(){}`|Y|Y|可选|不能用作`构造器`|
|异步箭头函数: `async x=>y`|N|Y|N|不能用作`构造器`|
|方法: `obj.xxx()`|Y|N|N|不能用作`构造器`|
|箭头函数: `x=> y`|X|Y|N|不能用作`构造器`|
|绑定函数: `fn.bind(thisArgs)`|N|N|N|取决于被绑定的函数|
|代理函数: ?????|N|N|N|取决于被代理的函数|

- 有且只有通过<span style='color:red'>声明方式</span>得到的函数<span style='color:red'>才可以是具名的</span>

## 一般函数--具名函数与匿名函数
[top](#catalog)
- 具名函数与匿名函数的区别

    ||是否有一个可影响当前作用域的标识符|是否可以作为声明语句|
    |-|-|-|
    |具名函数|Y|Y|
    |匿名函数|N|N|

## 生成器函数
- 参考
    - [iterator.md---生成器](iterator.md#生成器)

## 类
[top](#catalog)
- 类本身就是构造函数，因为
    ```js
    Class === Class.prototype.constructor
    ```
- 类只能通过 `new` 调用，**无法执行函数调用**
- 类可以以声明形式存在，也可以以表达式的形式存在
- 类可以赋值给对象成员，但是仍然需要通过 `new` 来执行
- 示例
    - 声明形式
        ```js
        class Foo{}
        ```
    - 表达式形式---作为对象的成员
        ```js
        var obj = {
            cls:class{
                print(){
                    console.log('cls');
                }
            }
        }

        var c = new obj.cls();
        c.print();  // cls
        ```

## 类/对象的方法
[top](#catalog)
- 方法的存在方式
    - 存在方式
        1. 具名函数
        2. 匿名函数
        3. ES6 风格的函数
        4. 生成器
        5. 异步函数
        6. 属性读写器
    - 示例
        - 参考代码
            - [src/functional/fn_type/obj_methods.js](src/functional/fn_type/obj_methods.js)
        - 代码内容
            ```js
            // 类/对象的方法测试

            var obj = {
                // 1. 具名函数
                fn1: function foo(){
                    console.log('this is fn1');
                },

                // 2. 匿名函数
                fn2: function (){
                    console.log('this is fn2');
                },

                // 3. ES6 风格的方法
                fn3(){
                    console.log('this is fn3');
                },

                // 4. 生成器-----ES6 风格
                *fn4(){
                    yield* [1,2,3,4];
                },
                // 5. 异步函数-----ES6 风格
                async fn5(){
                    return Promise.resolve('fn5 resolved');
                },

                // 6. 属性读写器-----ES6 风格
                get value(){ return 'value is 100'}
            }

            // 1. 具名函数
            obj.fn1();

            // 2. 匿名函数
            obj.fn2();

            // 3. ES6 风格的方法
            obj.fn3();

            // 4. 生成器-----ES6 风格
            for(let n of obj.fn4()) console.log(`fn4 n = ${n}`);

            // 5. 异步函数-----ES6 风格
            obj.fn5().then(console.log);

            // 6. 属性读写器-----ES6 风格
            console.log(obj.value);

            // 输出:
            // this is fn1
            // this is fn2
            // this is fn3
            // fn4 n = 1
            // fn4 n = 2
            // fn4 n = 3
            // fn4 n = 4
            // value is 100
            // fn5 resolved
            ```

- **ES6 风格的方法** 与 **一般函数** 的不同
    - 3点不同
        1. 不能作为构造器
            - 引擎没有为方法初始化 `[[Construct]]` 内部槽
        2. 没有 `prototype` 属性，除了生成器
            - 但仍然是 `Function` 的实例
        3. 方法不能具名
    - 这些方法的方法名，在方法内部不可见
        - 因为ES6风格的方法**只是声明，不是函数**，不能在声明内引用自己
        - <span style='color:red'>即这些声明都不是上下文中的标识符</span>
        - 参考
            - [JS的面向对象特性](oop.md#类声明是静态声明)

## 箭头函数
[top](#catalog)
- 箭头函数的特性
    - 箭头函数使用字面量方式声明，但是<span style='color:red'>箭头函数不是字面量</span>
    - **只能作为表达式的操作数**，并将表达式所在的上下文作为执行环境
    - **无法声明标识符**，所以**总是匿名的**
    - 可以是异步的，函数体中可以包含 `await`
    - 没有 `this` 对象
    - 没有 `arguments` 参数对象
- 注意事项
    - 箭头函数不能作为**构造器**
    - 函数体中的 `this`，总会访问到函数所在**上下文**的 `this`
        - 如果在全局作用域声明，则 `this` 是 `window`，或 `global`
        - 如果声明在函数/方法中，则 `this` 是函数/方法的**调用者**
    - 无法重新设置箭头函数的 `this`
        - `apply`、`call` 无法为箭头函数设置 `this` 对象
        - `bind` 无法为箭头函数绑定 `this` 对象
        - 示例
            - 参考代码
                - [src/functional/fn_type/arrow_this.js](src/functional/fn_type/arrow_this.js)
            - 代码内容
                ```js
                function foo(){
                    var fn = ()=>this.name;
                    var obj = {name:'tom'};
                    // 1. 将箭头函数绑定为函数内部的 obj
                    console.log('call: ', fn.call(obj) );
                    console.log('bind: ', fn.bind(obj)() );
                }

                var other = {name:'bob'};

                // 2. 将执行 foo 时的 this 替换为 other
                foo.call(other);

                // 输出
                // call:  bob       <<<< 绑定失败，仍然使用了上下文中的this
                // bind:  bob       <<<< 绑定失败，仍然使用了上下文中的this
                ```

## bind--绑定函数
[top](#catalog)
- `bind()`
    - 语法
        ```js
        var 绑定函数 = 目标函数.bind(thisArgs [, args....]);
        ```
    - bind 的功能
        - 绑定目标函数的 `this`对象 和 参数，并返回一个`绑定函数`

- bind 绑定的内容
    - `this`
        - 如果 `thisArgs` 是`null`/`undefined`
            - 普通模式: 使用当前执行作用域中的`this`
            - 严格模式: 使用 `null`
        - 对绑定函数调用 `call`、`apply` 时，**不会修改已绑定的** `this`
    - 参数 `args`
        - 被绑定的 `args` 是**不可替换的**
            - 调用`绑定函数`时传递的参数，会追加到 `args` 的后面
    - 因为类本质也是函数，所以可以为类绑定 `this`

- `绑定函数` 的特性
    1. 隐式原型与目标函数相同
        - 相当于执行了
            ```js
            Object.setPrototypeOf(绑定函数, Object.getPrototypeOf(目标函数));
            ```
        - 所以可以访问目标函数继承自原型的成员，<span style='color:red'>不能访问目标函数的自由成员</span>
            - 也导致了: 在类声明中，绑定函数**不能**替代目标函数**作父类**
    2. 没有自有的 `prototype` 属性 （虽然它也是一个函数）
        - 动态添加`prototype`是**无效的**

- `绑定函数` 作为 构造器
    - 已绑定的 `args` **有效**
    - 已绑定的 `thisArg` **无效**
    - `绑定函数`作为构造器的执行流程
        1. `new` 运算符创建 `this` 对象
        2. 将对象的原型设置为 **目标函数**的原型
        3. 将 `new.target` 设为目标函数
    - 构造器的执行流程是`绑定函数`**固有的**

- 因为 `绑定函数` 作为构造器、普通函数时都有自己的处理逻辑，所以**可以被当作一类独立的函数**
    - 主要是对 `this`、`args`、原型的处理

- 示例
    1. bind 绑定的内容
        - 参考代码
            - [src/functional/fn_type/bind_args_this.js](src/functional/fn_type/bind_args_this.js)
        - 参数绑定、与调用时附加参数
            ```js
            function foo() {
                'use strict';
                console.log('this: ', this);
                console.log('args: ', ...arguments);
            }

            var newFoo = foo.bind(null, 1, 2, 3);
            newFoo(4, 5, 6);

            // 输出
            // this:  null
            // args:  1 2 3 4 5 6
            ```

    2. `绑定函数` 的特性: 可以使用目标函数继承自原型的方法
        - 参考代码
            - [src/functional/fn_type/bind_proto_method.js](src/functional/fn_type/bind_proto_method.js)
        - 代码内容
            ```js
            // 0 通过类继承构造继承关系
            // 0.1 基类
            class Foo{
                static run(){
                    console.log('this is foo');
                    console.log(`this === Foo : ${this === Foo}`);
                    console.log(this);
                }
            }

            // 0.2 子类
            class FooEx extends Foo{
                static run(){
                    console.log('this is FooEx');
                    console.log(`this === FooEx : ${this === FooEx}`);
                    console.log(this);
                }
            }

            /*
                0.3 此时相当于 Object.getPrototypeOf(FooEx) === Foo
                    Foo 相当与 FooEx 的原型，所以 FooEx 的绑定函数可以使用 Foo.run 方法
            */

            // 1. 创建 FooEx 的绑定函数
            var f = FooEx.bind();

            // 2. 直接执行类的静态函数
            FooEx.run();
            // this is FooEx
            // this === FooEx : true
            // [Function: FooEx]

            // 3. 通过绑定函数来执行静态函数
            f.run();
            // this is foo
            // this === Foo : false
            // [Function: bound FooEx]
            ```

    3. `绑定函数` 作为构造器
        - 参考代码
            - [src/functional/fn_type/bind_as_constructor.js](src/functional/fn_type/bind_as_constructor.js)
        - 代码内容
            ```js
            // 0. 创建构造函数
            function Foo(arg){
                console.log(`arg = ${arg}`);
                console.log(Object.getPrototypeOf(this) === Foo.prototype);
                console.log(new.target === Foo);
            }

            // 1. 直接只用构造函数创建对象
            new Foo(12345);
            // arg = 12345
            // true
            // true

            // 2. 通过绑定函数创建对象
            var newFoo = Foo.bind(null, 2345);
            new newFoo('abcd');
            // arg = 2345
            // true
            // true
            ```

## 代理函数
[top](#catalog)
- 使用 `Proxy` 创建函数的代理对象也具有函数的性质
- 代理的情况
    - 两种情况
        1. 定制过的 apply/constructor 行为的对象
        2. 没有使用陷阱而直接穿透到源对象
    - 代码无法检测这两种情况
- 如果没有定制 apply/constructor 行为，代理函数的调用、构造行为与源对象一致
    - 如果函数没有 `[[Construct]]`，constructor 陷阱不会被触发
    - 如果对象没有 `[[Call]]` 内部槽，apply 陷阱也不会被触发
        - class 声明的类有 `[[Call]]`，但是不能类不能当作函数来调用
        - 但是可以为类设置 apply 陷阱

# 函数的数据性质
[top](#catalog)
- 函数的数据态
    - 函数是值，可以作为数据保存到变量中
- 函数的类与对象态
    - 对象态的表现
        - JS将所有函数的原型链顶端都设为`Function.prototype`
        - 不是所有函数都是由`Function()`创建的
        - 由于`Function.prototype`，所有函数看起来都是 `Function` 的示例
    - 创建函数的构造函数
        - `Function`，普通函数
        - 继承自`Function`的构造函数，**但是无法直接使用**
            1. `GeneratorFunction`，生成器函数
            2. `AsyncFunction`，异步函数
            3. `AsyncGeneratorFunction`，异步生成器函数
        - 检测构造函数
            ```js
            // [Function: GeneratorFunction]
            console.log( (function*(){}).constructor );
            // [Function: AsyncFunction]
            console.log( (async x=>x).constructor );
            // [Function: AsyncGeneratorFunction]
            console.log( (async function* (){}).constructor );
            ```
    - 对象态函数的性质

        |函数（obj）|函数的构造器<br>`obj = new C`|函数的父类<br>`obj instanceif Cls`|能否作为构造器<br>`x = new obj`|
        |-|-|-|-|
        |一般函数: `function xxx(){}`|Function|Function|Y|
        |类: `class XXX{}`|-|Function|Y|
        |生成器:  `function* xxx(){}`|GeneratorFunction|GeneratorFunction|N|
        |异步函数: ` async function xxx(){}`|AsyncFunction|AsyncFunction|N|
        |异步生成器函数: `async function* xxx(){}`|AsyncGeneratorFunction|AsyncGeneratorFunction|N|
        |异步箭头函数: `async x=>y`|-|Function|N|
        |箭头函数: `x=> y`|-|Function|N|
        |方法: `obj.xxx()`|-|Function|N|

- 函数的代理态

# 函数与逻辑结构
## 函数与递归
[top](#catalog)
- 函数递归的关键问题
    - 在函数内如何识别函数自身
- 不同的函数内，如何识别自身
    - 具名函数，直接使用函数名
        ```js
        function foo(){
            return foo();
        }
        ```
    - 匿名函数
        - 非严格模式下
            - 通过 `arguments.callee()` 访问函数自身
                ```js
                (function (x){
                    console.log(x);
                    // 通过 arguments.callee() 调用自身
                    return x && arguments.callee(x-1);
                })(9)
                ```
        - 严格模式下
            - 无法使用 `arguments.callee()`
                - 10种JS函数中的前8种的匿名函数都不能使用 `callee`
            - 在外部作用域中，使用 `const` 为匿名函数声明一个标识符
                - 为什么使用 `const`？
                    1. 可以创建块级作用域
                    2. 声明不会重复
                    3. 不可写
                - 通过 `const` 创建标识后，在块作用域内不会发生修改，可以在匿名函数内访问到
                    - 匿名函数内找不到标识符，将会顺着作用域链到外部的块级作用域中搜索
                - 示例
                    ```js 
                    const fact = x => x && x * fact(x-1) || 1;
                    console.log( fact(9) ); // 362880
                    ```
    - 类/对象的方法进行递归
        - 方法迭代时的问题: 如何维护 `this`
            - 在递归时，无法传递 `this`
        - 使用**箭头函数**，在箭头函数内部使用 `this`
            - 因为箭头函数没有this，使用的是上下文中的`this`
            - 所以箭头函数无论在哪里执行，都可以通过上下文来维护`this`
        - 在何处使用箭头函数维护 `this` ？
            1. `get` 方法中
            2. 普通的方法中
        - 示例
            1. `get` 方法中，返回一个箭头函数
                ```js
                var obj = {
                    power: 100,
                    get fact(){
                        const fact = x => x && x * fact(x-1) || this.power || 1;
                        // 将箭头函数返回
                        return fact;
                    }
                }

                // 函数返回后通过闭包维持 this
                console.log(obj.fact(9));   // 36288000
                ```
            2. 普通方法中，递归调用箭头函数
                ```js
                var obj = {
                    power: 100,
                    fact(...args){
                        const fn = x => x && x * fn(x-1) || this.power || 1;
                        return fn(...args);
                    }
                }
                console.log(obj.fact(9));   // 36288000
                ```
    - 对绑定函数进行递归调用
        - 因为绑定函数没有函数体，所以不能在绑定函数内调用自身
        - 可以通过 `const` 声明标识符，并在绑定函数内部使用来进行递归
        - 示例
            ```js
            var obj = {power:100};
            // 使用const创建一个标识符来保存绑定函数
            const fact = (function(x){
                // 使用标识符fact，来递归调用绑定函数
                return  x && x*fact(x-1) || this.power|| 1;
            }).bind(obj);

            console.log(fact(9));   // 36288000
            ```

# 函数的行为
## 构造
[top](#catalog)
- `new fn()` 运算的两个操作
    1. 创建`this`
    2. 调用 `fn()`，将 `new.target` 设为 `fn`
- 一般函数、生成器、异步函数的构造过程不同，但是`new`**运算过程中的逻辑是相同的**
- `this` 的**标准构建过程**
    - `this`的标准构建过程被设置在 `[[Construct]]` 内部槽中
    - 两种标准构建过程
        - 函数
            1. 以 `fn.prototype`, 或 `Object.prototype` 为原型，创建 `this` 对象
            2. 初始化实例 `fn.call(this)`，其结果为 `result`
            3. 将 `result`、`this` 中的有效值作为 `new` 的运算结果返回
                - 如果 `result` 不是 Object，则返回 `this`
                - 如果 `result` 是 Object，则返回 `result`
        - 类
            1. 通过`super()`调用父类的构造方法产生`this`
            2. **后两步与函数构造相同**
    - 类中 `this` 对象的几种来源: <span style='color:red'>当前类或基类通过`super()`创建了`this`，或者手动返回一个对象作为 `this`</spam>
        1. 非派生类，来源: `fn.prototype`
            ```js
            class MyClass
            ```
        2. 其他类的派生: 通过 `super()` 回溯到基类来创建 `this`
            ```js
            class MyClass extends ParentClass{}
            ```
        3. 父类是 Object，来源: `new Object()`
            ```js
            class MyClass extends Object{}
            ```
        4. 父类是 null，需要在构造函数中，手动创建实例并返回
            ```js
            class MyClass extends null{
                constructor(){
                    return ...;
                }
            }
            ```
        5. 在constructor中，手动返回一个对象，作为 `this`
            ```js
            class MyClass ...{
                constructor(){
                    return {...};   // 手动返回一个对象
                }
            }
            ```
- 谁、什么时候调用基类
    - 在创建 `this` 时，由 `super()` 回溯到基类，调用基类的构造器函数

- **类声明与一般函数在构造时的不同**
    - 对于类
        - 如果基类没有创建 `this`，则类的构造器必须返回一个对象，否则会触发异常
    - 对于一般函数
        - 总会有new创建一个 `this`
            - 即使原型对象是null，也会由Object来创建 `this`
        - 可以手动返回一个对象，来替换 `this`

## 函数调用
[top](#catalog)
- <span style='color:red'>`调用函数` 这个行为由三个主要步骤构成</span>
    - 3个主要步骤
        1. 预处理
            - 包括创建 arguments、设置 callee
            - 设置调用栈 caller
        2. 绑定this
        3. 执行函数体
    - 这3个步骤被写在了函数的 `[[Call]]` 内部槽中
- 函数的调用方式
    1. `()` 调用: `fn()`
    2. 使用约定的调用界面来**隐式调用函数**
        - 模版函数调用
            ```js
            fn`xxx ${param}...`;
            ```
        - 属性读取器
            ```js
            obj = {
                get xxx(){}
                set xxx(value){}
            }
            ```
        - `bind`创建的绑定函数，隐式调用原函数
        - Proxy创建的代理对象，隐式调用原函数
        - `new` 变相的调用函数
        - 将符号属性设置为函数，在触发相应行为时，执行函数
        - 某些语句的隐式调用
            - 如 `for...of` 调用迭代器
        - 某些运算符的隐式调用
            - 如 `yield *` 调用迭代器
- `arguments.callee`: 表明是哪个函数
    - arguments.callee 的指向
        - 创建 `arguments` 的函数
    - callee 的功能
        1. 保持匿名函数和函数可被重写的特性
        2. 匿名函数递归，即在匿名函数内部调用函数自身
    - 与callee相关的等式
        ```js
        function myFn(){
            console.log( arguments.callee === myFn.arguments.callee );  // true

            // callee 指向的就是函数本身
            console.log( arguments.callee === myFn );   // true
        }

        myFn();
        ```
    - 向 `arguments` 中添加 `callee` 的时间
        - 执行函数调用时: `fn()`，会创建 arguments，同时添加 `callee`
    - 不同模式下的 `arguments.callee`
        - 普通模式
            - callee 是一个`数据描述符`，value 指向函数本身
            - 获取示例
                ```js
                function foo(){
                    console.log( Object.getOwnPropertyDescriptor(arguments, 'callee'));
                }

                foo();
                ```
            - `arguments.callee` 的标识符
                ```js
                {
                    value: [Function: foo],
                    writable: true,     // 普通模式下可以修改 callee
                    enumerable: false,
                    configurable: true
                }
                ```
        - 严格模式
            - callee 是一个`存取描述符`
            - 严格模式下，无法获取 callee，否则会抛出异常
            - 获取示例
                ```js
                function foo(){
                    'use strict';
                    console.log( Object.getOwnPropertyDescriptor(arguments, 'callee'));

                    // TypeError: 'caller', 'callee', and 'arguments' properties may not be accessed on strict mode functions or the arguments objects for calls to them
                    arguments.callee;
                }

                foo();
                ```
            - `arguments.callee` 的标识符
                ```js
                {
                    get: [Function],
                    set: [Function],
                    enumerable: false,
                    configurable: false // 严格模式下无法修改callee
                }
                ```

- caller: <span style='color:red'>谁调用我</span>
    - 功能
        - 保存函数的调用信息，即哪个函数在调用当前函数
        - 即栈顶中的调用者
    - caller的访问方式
        - `arguments.callee.caller`
        - `函数名.cller`
    - 通过 `caller` 遍历调用栈
        - 参考代码
            - [src/functional/fn_behaviour/enum_stack.js](src/functional/fn_behaviour/enum_stack.js)
        - 代码内容
            ```js
            // 显示函数名
            var showIt = f => console.log('->' + f.name);

            // 遍历调用栈
            function enumStack(callback){
                var f = arguments.callee;
                // 通过 callee 遍历调用栈
                while(f.caller){
                    callback(f = f.caller);
                }
            }

            // 创建一个调用关系
            // test --> level_2 --> level_n --> enumStack
            function level_n(){
                enumStack(showIt);
            }

            function level_2(){
                level_n();
            }

            function test(){
                level_2();
            }

            test();
            // ->level_n
            // ->level_2
            // ->test
            // ->
            ```
    - caller、arguments无法准确的处理递归，会产生死循环
        - 虽然递归有停止条件，但是每个 caller 的执行都是相同的，会产生死循环
        - 参考代码
            - [src/functional/fn_behaviour/enum_stack_dead.js](src/functional/fn_behaviour/enum_stack_dead.js)
        - 代码内容
            ```js
            // 显示函数名
            var showIt = f => console.log('->' + f.name);

            // 遍历调用栈
            function enumStack(callback){
                var f = arguments.callee;
                // 通过 callee 遍历调用栈
                while(f.caller){
                    callback(f = f.caller);
                }
            }

            // 创建一个掉用关系
            // <---------- max = 1 ----------><----------- max > 1 ---------->
            // test --> level_2 --> level_n --> level_2 --> level_n...
            var i = 0, max = 0;
            function level_n(){
                if ( ++i < max){
                    level_2();
                }
                enumStack(showIt);
            }

            function level_2(){
                level_n();
            }

            function test(){
                level_2();
            }

            /*
                1. max = 1 不会出现死循环
                - level_n.caller ---> level_2
                - level_2.test ---> test
            */
            // max = 1;
            // test();

            // max > 1，会出现死循环
            /*
                2. max > 1，会出现死循环
                - 顶层部分是:
                    - level_n.caller ---> level_2
                    - level_2.caller ---> level_n
                - 最底层是： level_2.test ---> test

                - 栈顶的 level_2、level_n 已经出现了死循环，所以无法遍历到栈底
            */
            max = 2;
            test();
            ```

## 方法调用
[top](#catalog)
- 方法调用与函数调用的区别
    - 方法调用会**持有有效的 this 对象**

## 调用时的this
[top](#catalog)
- 作为函数调用时的`this`
    1. 默认将 `undefined` 传入函数
    2. 处理 `this` 对象的引用
        - 严格模式下，`this = undefined`
        - 普通模式下，`this = window/global`，即使用全局对象
- 作为方法调用时的`this`
    - `this`取决与调用者
    - 或者通过 apply、call、bind、Reflect.apply 传入的有效`this`
- 箭头函数的`this`
    - 在绑定`this`时，将忽略传入的this，而直接执行函数体，所以箭头函数只能使用上下文中的 `this`
    - bind等方法无法影响箭头函数的这种特性

## 迭代
[top](#catalog)
- 参考
    - [iterator.md---可迭代对象](iterator.md#可迭代对象)

# 与函数相关的几个基本问题
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

# 回调函数
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

# 立即执行函数IIFE
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

# 函数中的this对象
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

# 闭包
## 利用闭包的示例-循环变量添加事件监听
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

# 函数实例
[top](#catalog)
- 函数只是一段代码文本，在运行环境中，需要变成可处理的对象
- `代码 --->>> 对象` 的变化行为**细分**
    - 两种创建函数的方式
        - 对于`函数声明`: `function method(args){...}`，是实例化
        - 对于`函数表达式`: `let val = function(args){...}`，是创建函数的实例
    - 两种方式的操作结构是相同的
- 变换的结果
    - 获得一个**可参与运算的函数实例**
- 示例
    - 函数中返回一个函数实例，每次调用返回的都是新的实例
    ```js
    function foo(){
        return function test(){}
    }
    var f1 = foo();
    var f2 = foo();
    console.log(f1 === f2); // false
    ```

## 闭包的基本知识
[top](#catalog)
- 闭包是什么
    - 闭包是一种数据结构
        - 一种用于记录`函数实例`在运行期的`可访问标识符`的**结构**
- 闭包的功能
    - 为每个函数维护其执行期的信息，指代<span style='color:red'>一个函数实例在运行期的作用域</span>
    - 通过闭包可以获取执行期的信息
        - 可以在: 函数再次被执行时获取
        - 可以在: 通过某种方法进行函数体时获取
- 闭包的产生
    - 一个函数实例的一次执行，就会创建一个新的执行期作用域，即创建一个闭包
        - 即: 只要**函数被调用**，就会产生一个新的闭包
- 闭包的销毁
    - **闭包中的数据**被有没引用时，函数实例与闭包将被同时回收

- 如何理解闭包的持续存在
    - 理解方式一：嵌套函数的内部函数
    - 理解方式二：包含被引用变量/函数的对象

- 闭包的持续条件
    1. 存在函数嵌套
    2. 内部函数引用了外部函数的变量/函数
    3. 执行了外部函数
    4. 外部函数调用后，创建了函数对象
        - 不需要调用内部函数，只要有函数对象即可
        - 只有执行外部函数，才能观测到闭包的产生
            - chrome最新版的debug模式中，如果没有使用闭包(包括`console.log()`)，控制台中没有显示

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

## 常见的闭包
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

## 闭包的作用
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

## 闭包的应用-创建独立的作用域
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
## 闭包的应用-自定义js模块
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

## 闭包的缺点-内存溢出与内存泄露
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

## 与闭包相关的问题
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