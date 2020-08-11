<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》

### 目录--JS的函数式语言特性
- [函数式语言](#函数式语言)
- [JS中的几种连续运算](#JS中的几种连续运算)
- [将逻辑结构转换为表达式](#将逻辑结构转换为表达式)
    - [条件语句的转换](#条件语句的转换)
    - [循环语句转换为尾递归](#循环语句转换为尾递归)
- [](#)
- [](#)


# 函数式语言
[top](#catalog)
- 函数式语言的基本特征
    - 连续求值
- 函数式语言的运算模型
    - 运算（表达式） 产生结果
    - 结果 用于 下一次（或之后的）运算
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
[top](#catalog)