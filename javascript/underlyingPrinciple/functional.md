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
- [函数声明](#函数声明)
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
        - 

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

## 闭包的基本知识
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