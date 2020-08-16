<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》
    - https://www.bilibili.com/video/BV14s411E7qf

### 目录--JS的结构化特性
- [JS的结构化特性的知识梳理](#JS的结构化特性的知识梳理)
- [JS是多范型语言](#JS是多范型语言)
- [JS的基本组织元素](#JS的基本组织元素)
    - [语法元素及其组织含义](#语法元素及其组织含义)
    - [Tokens的概念](#Tokens的概念)
    - [标识符](#标识符)
    - [表达式、字面量、初始器](#表达式、字面量、初始器)
    - [语句](#语句)
    - [模块](#模块)
    - [组织的原则](#组织的原则)
- [声明](#声明)
    - [JS中的声明](#JS中的声明)
    - [顶层声明](#顶层声明)
- [语句与代码分块](#语句与代码分块)
    - [代码分块](#代码分块)
    - [if语句中的代码分块](#if语句中的代码分块)
    - [switch中的代码分块](#switch中的代码分块)
    - [for循环中的代码分块](#for循环中的代码分块)
    - [异常中的代码分块](#异常中的代码分块)
- [语句的值](#语句的值)
    - [不同的语句及其返回值](#不同的语句及其返回值)
    - [语句的执行状态](#语句的执行状态)
    - [语句有值与无值](#语句有值与无值)
- [作用域--组织代码分块的方式](#作用域--组织代码分块的方式)
    - [作用域的基本概念](#作用域的基本概念)
    - [词法作用域--模块化层次的主要体现之一](#词法作用域--模块化层次的主要体现之一)
        - [js中的词法作用域](#js中的词法作用域)
        - [词法作用域之间的相关性](#词法作用域之间的相关性)
    - [变量作用域--模块化层次的主要体现之一](#变量作用域--模块化层次的主要体现之一)
        - [js中的变量作用域](#js中的变量作用域)
        - [变量作用域的特性](#变量作用域的特性)
        - [模块的变量作用域](#模块的变量作用域)
    - [作用域与执行上下文](#作用域与执行上下文)
    - [作用域链](#作用域链)
    - [与作用域相关的问题](#与作用域相关的问题)
- [提升](#提升)
    - [提升的本质](#提升的本质)
    - [变量提升](#变量提升)
    - [函数的提升](#函数的提升)
    - [代码块内的提升](#代码块内的提升)
    - [与提升相关的问题](#与提升相关的问题)
- [执行上下文](#执行上下文)
    - [执行上下文](#执行上下文)
    - [执行上下文栈](#与执行上下文相关的问题)
    - [与执行上下文相关的问题](#与执行上下文相关的问题)
- [](#)

# JS的结构化特性的知识梳理
[top](#catalog)
1. 使用 [JS的基本组织元素](#JS的基本组织元素)  产生 [声明](#声明) 和 [语句与代码分块](#语句与代码分块)
2. [语句与代码分块](#语句与代码分块) 形成了  [作用域](#作用域--组织代码分块的方式)，也造成了:
    - [词法作用域](#词法作用域--模块化层次的主要体现之一)
    - [变量作用域](#变量作用域--模块化层次的主要体现之一)

3. 不同的作用域在 **语法分析阶段** 创建 [顶层声明](#顶层声明)
4. [顶层声明](#顶层声明) 导致了变量作用域内的 [提升](#提升)
    - [提升](#提升)的前提，[执行上下文](#执行上下文)
5. 运行期，执行代码，得到 [语句的值](#语句的值)

# JS是多范型语言
[top](#catalog)
- JS是有交叉分类特性的语言，通常被称为: 多范型语言
    - JS同时是`说明式`和`命令式`的
        - `说明式` 侧重于: 基于算法的实现
        - `命令式` 侧重于: 基于结构的运算
    - JS同时兼具串行、并行语言的特征

# JS的基本组织元素
## 语法元素及其组织含义
[top](#catalog)

|元素|物理形态|静态含义|动态含义|
|-|-|-|-|
|标识符|-|let/const/var<br>函数声明<br>类声明|非严格模式下的var<br>非严格模式下的函数声明|
|表达式|模板字符串?????|值<br>箭头函数体|通过eval执行表达式语句来实现|
|语句|.js文件|块与块级作用域|eval()|
|模块|.mjs文件|import/export<br>require()|import().then()|

## Tokens的概念
[top](#catalog)
- Tokens 是代码静态语法概念
- Tokens 的功能
    1. 将整个代码文本解析成一系列符号
    2. 将代码文本中的程序语义映射成机器逻辑可以处理的对象
        - 如: 抽象语法树 AST

## 标识符
[top](#catalog)
- 在Tokens的层面上，一行语句可以被拆分成:
    1. 标识符
        - 包括 变量名、函数名、类名、模块名等
    2. 标点符号
    3. 字面量
    4. 模板
- 表达的内容

    |元素|表达内容|具体内容|
    |-|-|-|
    |标识符|名字|表示一个名字|
    |字面量|值|表示有字面含义决定的值|
    |模板|值|表示一个可计算的字符串值|

- 既是保留字，也是字面量的标识符
    - null
    - true
    - false
- `undefined` <span style='color:red'>既不是保留字，也不是标识符</span>

## 表达式、字面量、初始器
[top](#catalog)
- 什么是表达式
    - 0～1个运算符、至少 1 个操作数的 **有序书写**
    - 运算符可以是:
        1. 表达符号
        2. 关键字
    - 操作数可以是:
        1. 标识符
        2. 字面量
        3. 模板
- 运算符表达的本质: 引用、值之间的运算，并返回引用、值
    - 因为操作数只能是: 引用、值
- 表达式的形态
    - 0个运算符 + 1个操作数 --- 单值表达式
        ```js
        1
        ```
    - 1个运算符 + 1个操作数
        ```js
        -true; // -1
        -1;    // -1
        ```
    - 1个运算符 + 两个操作数
        ```js
        1 + 1;   // 2
        ```
    - 1个运算符 + 三个操作数
        ```js
        true ? 1 : 2; // 1
        ```
    - 1个运算符 + 未知数量的操作数
        ```js
        function foo(...args){}
        foo(1,2,3,4,5);
        ```
- 表达式的连续计算
    - 运算的顺序由每个独立表达式的运算符优先级决定
    - 普通的连续运算
        ```js
        1 + 2 * 3;
        ```
    - 逗号 `,` 的连续运算，其返回值是最后一个表达式的结果
        - 如果用来赋值，需要和 `()` 一起使用
        - 示例
            ```js
            function foo(...args){}
            var a = (1, 2, 3, 4, 5, foo(1, 2, 3, 4, 5));
            // a = undefined
            ```
- JS表达式的注意事项
    - 表达式不能独立于语句存在
        - 单个表达式，可以被称为 `表达式语句`
        - 单个字面量的操作数，可以被称为 `字面量表达式语句`
    - 表达式结果的用途
        1. 用于持续的表达式计算
            - 如表达式的连续运算
        2. 通过一些语句来展示计算结果
        3. 将计算结果赋值给变量

- 表达式特性与注意事项产生的原因
    - <span style='color:red'>JS的语言引擎被设计为一个按语句行、顺序执行的`行处理器`</span>

- 字面量
    - 对象字面量
        ```js
        {k1: v1, k2: v2}
        ```
    - 数组字面量
        ```js
        [e1, e2, e3]
        ```
    - 正则表达式字面量
        ```js
        /../ig
        ```
- 初始器
    - 初始器只针对: 数组、对象
    - 字面量与初始器的区别
        - 字面量不包括运算过程
        - 初始器可以包括运算过
    - ES5中只明确了5种字面量语法，这些字面量的值是在出现时就可以确定的，包括
        - null
        - true/false
        - 数值
        - 字符串
        - 正则表达式
    - 为什么ES5的字面量不包括 数组 和 对象?
        - 数组、对象出现时，不能完全确定，**必须进行运算**
        - <span style='color:red'>所以所有的数组、对象字面量都是初始器</span>
    - 实际使用的时候，可以**不用严格区分两者**

## 语句
[top](#catalog)
- Tokens的两个演进方式
    1. `函数式语言特性`: 表达式中通过名字来引用值，并进一步地做运算
    2. `命令式语言特性`: 通过语句来串联名字与值，并最终表述为赋值操作

- JS中语句的划分
    - 声明语句
    - 非声明语句

- 声明语句
    - 声明语句都是静态词法分析的
    - 声明的内容
        - 标识符的名字
        - 名字与值的关系，也称为绑定
            - 包括：赋值、函数对象声明
    - 示例
        ```js
        var x; // 声明标识符
        var y = 100; // 赋值
        function foo(){} // 函数声明
        ```
- 非声明语句
    - 非声明语句都是动态执行的
- 非声明语句的作用
    - 描述一个过程
        - 描述被组织的元素
        - 描述上述元素间的结构方法，即<span style='color:red'>代码分块</span>
    - 表达经过上述语句陈述过程之后的结果值

## 模块
[top](#catalog)
- 模块的两个语义
    1. 导出
        - 给外部提供一个可用的导出列表
        - 模块的使用者只能使用导出内容，隐藏了内部的一些实现
    2. 导入
        - 读取模块的导出列表，并使用导出内容

## 组织的原则
[top](#catalog)
- 3 个原则
    1. 控制数据的可变性
    2. 最小逻辑和最大复用
    3. 语法在形式上的清晰与语义一致性

- 原则1: 控制数据的可变性
    - 数据定义行为
        - 数据定义行为可以拆分成4个具体步骤/状态
            1. 命名/声明
            2. 设值前
            3. 设值
            4. 设置后
        - 由数据定义行为产生的变量绑定方式
            - 动态绑定、非动态绑定
            - 每种绑定都存在: 初始化/未初始化，两种状态
    - 可以通过 **减少数据定义的状态数量** 来简化系统
        - 如 `Promise` 的并行特性，本质就是一个**三状态数据**的可编程系统
            - pending、resolved、rejected
        - 如 推荐使用 let/const 来替代 var，本质就是减少数据的状态

- 原则2: 最小逻辑和最大复用
    - 最小逻辑的两种趋势
        1. 非结构化
            - 如生成器、Promise
            - 抽离结构化控制结构:顺序、分支、循环，将系统的控制变成了非结构化
        2. 逻辑内聚
            - 创建更大颗粒度的复用单元
            - 对外暴露一致性接口
            - 示例
                - 代码内容
                    ```js
                    Promise.resolve('hello world')
                        .then(console.log);
                    ```
                - Promise的处理
                    - 向外统一暴露接口: resolve, then
                    - 每个接口内部封装了包括: 并行语义、执行结构等全部逻辑

# 声明
## JS中的声明
[top](#catalog)
- <span style='color:red'>JS的声明总体上有两类</span>
    - 声明标识符
    - 声明块
        - 包括代码块、模块

- JS约定: 所有的声明都必须在<span style='color:red'>语法分析期</span>处理
    - 这也表示JS在结构化方向上，更偏向于实现为静态语言
    - 这样更容易做类型化、静态语义分析，更容易将类型推导、预编译、JIT等特性引入JS

- <span style='color:red'>JS中的声明方式</span>
    - 变量声明: `var`，`function xxx(...args){...}`
        - 会创建变量作用域
        - 这两者会产生提升效果
    - 词法声明: `let`、`const`
        - 会创建词法作用域

- 声明可以用来表明标识符的3种性质
    - 标识
    - 值
    - 确定性

- <span style='color:red'>字面量不是声明</span>
     - 字面量是由词法规则表示的**数据、值**
     - 严格来说，字面量属于**记法**，不属于声明

- JS中的声明

    |数据|名称|示例|说明|
    |-|-|-|-|
    |数据|变量|var x...<br>let x...|var变量语句与其他词法声明，包括`let`和`const`，在概念上有着不同的语义|
    |数据|常量|const x=...||
    |块|函数声明|function x(){}<br>async function x(){}<br>function * x(){}|函数表达式: `let x = function(){}`、箭头函数: `x=>{...}`，**不是声明，是字面量**|
    |块|类声明|class x...{...}|类表达式: `let x = class ...{...}`，**不是声明，是字面量**|
    |块|模块导入|import * as x ...<br>import ...|导出语句|
    |其他|异常|try {...} catch(x){...}|异常语句**本身不是声明语句**，但是catch 块中允许声明名字|
    |其他|循环|for (var/let/const x...)<br>for aqait(var.let.const x...|循环语句**本身不是声明语句**，但是循环条件中允许使用var/let/const声明名字|

- 函数表达式是字面量风格的值，不是声明语句

- 标识与值之间的确定性
    - 确定性表明了标识与值之间是否存在持续绑定关系
    - 确定性表现为: 变量、常量
    - ES规范中，用`MutableBinding`、`ImmutableBinding`来区分
        - `MutableBinding`，可以多次设值，所以是不确定的
        - `ImmutableBinding`，只能设一次值，所以是确定的
    - 使用 `ImmutableBinding` 创建的变量
        - const 变量
        - namespace
        - 严格模式下的函数参数集合 `arguments`
        - 作为独立语法元素使用的``函数表达式`
            - 示例
                ```js
                (function foo (){
                    foo = 100;
                    // 无法再次设置 foo 的值，会输出 foo 本身
                    console.log(foo) // [Function: foo]
                })()

                function bar (){
                    bar = 100;
                    // 函数声明中，可以修改函数对象的值
                    console.log(bar) // 100
                }

                bar()
                ```

## 顶层声明
[top](#catalog)
- 什么是顶层声明？
    - 指一个块中的**所有声明语句**，或**第一层具名函数**中的声明
- 顶层声明只在**语法分析阶段**有意义
- 因为存在动态作用域 `eval`，顶层声明实际影响了所有声明语句的运行期效果
- 两种顶层声明
    - 分类
        1. 顶层词法声明，负责 `let`、`const` 声明
        2. 顶层变量声明，负责 `var`、函数声明
    - 运行时，每个块按照相同的规则扫描两类声明，来获得词法声明列表与变量声明列表

# 语句与代码分块
## 代码分块
[top](#catalog)
- 代码分块的最终目的: <span style='color:red'>顺序执行</span>
- <span style='color:red'>代码分块是语句的唯一结构方法</span>
    - 被组织的元素只有:标签声明、标识符声明、表达式、语句
- 语句的结构方法
    - 非代码分块的结构---简单语句
        - 表达式语句
            - 表达式本身的计算环境依赖于它所在的语句，因此表达式本身不需要分块
        - 空语句
        - debugger
        - if语句

    - 代码分块结构
        - 逻辑结构
            - 循环语句，块的数量 = 2
                - `for(let/const...)`
                - `for await(let/const...)`
            - 多重分支语句，块的数量 = 1
                - `switch`
        - 控制结构
            - 异常语句，块的数量 = 2～3
                ```js
                try{...}
                catch(...){...}
                finally{...}
                ```
        - 其他
            - 块语句，块的数量 = 1
                - `{}`
            - with语句，块的数量 = 1

- 代码分块的作用
    - 声明标识符、创建**多语句**的结构
    - 维持变量或成员的可见性，不同分块的同名变量不会冲突

## if语句中的代码分块
[top](#catalog)
- <span style='color:red'>if语句自身没有代码分块</span>
    - 无论条件分支有没有 `{}`，<span style='color:red'>if本身都是一个简单语句</span>
    - 如果条件分支有 `{}`，<span style='color:red'>这个代码分块是由块语句创建的，不属于if语句</span>
- if的形式
    - 条件分支的是**块语句**
        ```js
        if (condition1){ // 块语句创建的代码分块
            statements1
        } else if (condition2){ // 块语句创建的代码分块
            statements2
        } else { // 块语句创建的代码分块
            statements3
        }
        ```
    - 条件分支是一个表达式
        ```js
        if (condition) statements
        ```

- 在 if 的`{}`内，用 `let` 声明的变量属于 `{}`；而函数声明等同于 `var` 声明，都会自动提升到 if 之外的环境中
    - 参考代码
        - [src/structured/block/if_block.js](src/structured/block/if_block.js)
    - 代码内容
        ```js
        let x = 1;
        function foo(){
            if (true){
                let x = 12;
                let y = 13;
                console.log(x); // 12
                function bar(){console.log('this is bar')}
            }
            bar();
            // 无法使用 `{}` 内部用let声明的变量
            // console.log(y); // ReferenceError: y is not defined
        }

        foo(); // this is bar
        console.log(x); // 1
        ```

## switch中的代码分块
[top](#catalog)
- swtich的形式
    ```js
    switch (expression){
        case value1:
            statements
            [break;]
        case value2:
            statements
            [break;]
        default:
            statements
    }
    ```
- 注意事项
    - case内，如果不用 break 中断，会继续执行下一个 statements

- expression 是执行在**外部的代码块**中的
- switch只有 1 个代码分块
    - 所有 value 和 statements 都执行在同一个块中
    - case **不会产生**一个子级的代码分块，它只是**标识一行代码的起始位置**
    - 每个 case 中的内容之间，共享变量名，也会受到块之间执行效果的影响
        1. 如果用 let 声明了同名的变量，会产生编译异常
            ```js
            switch (x){
                case 100:
                    let y = x;
                case 200:
                    let y = x*2; // SyntaxError: Identifier 'y' has already been declared
            }
            ```
        2. 需要注意变量在整个块中的**声明与使用顺序**
            ```js
            var x = 100, y = 3;
            switch (x){
                case 100:
                    // 因为 case 200 已经通过 let 做了变量y的声明与初始化
                    // 所以不能在声明之前使用 y
                    // 也不会使用外部作用域中的 y
                    console.log(y); // ReferenceError: Cannot access 'y' before initialization
                case 200:
                    let y = x * 2;
                    console.log(y);
            }
            ```
        3. 如果 let 变量声明在未执行的 case 中，则无法被使用
            ```js
            var x = 200, y = 3;
            switch (x){
                case 100:
                    let y = 100;
                case 200:
                    // 因为没有执行 case 100 分支，所以没有初始化变量 y，所以会有异常
                    console.log(y); // ReferenceError: Cannot access 'y' before initialization
            }
            ```
- 可以在各个 case 中**用块语句创建独立的代码分块**
    ```js
    var x = 100;
    var y = 3;
    switch (x){
        case 100:{
            let y = 50;
            // 使用当前块内部的变量
            console.log(y); // 50
        }
        case 200:{ // case 100 没有break，所以会执行这个分支
            let y = x * 2;
            // 使用当前块内部的变量
            console.log(y); // 200
        }
    }

    console.log(y)  // 3
    ```

## for循环中的代码分块
[top](#catalog)
- for循环的形式及其代码分块
    ```js
    for (let x...; condition; final-expression){ // 代码分块1: LoopEnv
        statement   // 代码分块2: ForBody
    }
    ```
    ```js
    for (let x ... in ...){  // 代码分块1: IteratorEnv
        statement   // 代码分块2: ForBody
    }
    ```
    ```js
    for (let x ... of ...){  // 代码分块1: IteratorEnv
        statement   // 代码分块2: ForBody
    }
    ```

- `循环表达式`的形式。这种形式下，**statement不是代码分块，内部不能使用 let/const 声明变量**
    ```js
    // 代码分块1: LoopEnv
    for (let x...; condition; final-expression) statement
    ```
    ```js
    // 代码分块: IteratorEnv
    for (let x ... in ...) statement
    ```
    ```js
    // 代码分块: IteratorEnv
    for (let x ... of ...) statement
    ```

- 在for循环条件中，使用let/const声明变量时，会出现代码分块
    - 无论statement是不是块，for都会有一个自己的代码分块
- 不同形式间的区别
    - `for (let x...; condition; final-expression)`
        - 只会用 let 声明一次变量，**只会产生一个LoopEnv代码分块**
    - `for...in`、`for...of`
        - **每次循环**都会**创建一个新的 `IteratorEnv` 代码分块** 作为执行环境
            - 为了避免每次循环时，使用let创建重名变量
            - 所以这种for循环会存在**与迭代次数**相等的 `IteratorEnv` 代码分块
        - 每次创建 `IteratorEnv` 时，会复制上一个分块中 let/const 声明的变量值
            - 因为在循环中，下一次循环的值，应该是上一次的结果
            - 通过复制的方式，维持循环
- `for...in`、`for...of` 的<span style='color:red'>性能问题</span>
    - 每次迭代都会创建一个新的 `IteratorEnv` 分块，并复制值，<span style='color:red'>可能会</span>降低性能
    - 实际上，`for...in` 是最慢的，for 和 `for...of`相差不是很多
    - 示例
        - 参考代码
            - [src/structured/block/fortest.js](src/structured/block/fortest.js)
        - 代码内容
            ```js
            function fortime(length) {
                var x = new Array(length).fill(0);  // 构造length长度的数组用于遍历

                console.time('a');
                for (let i = 0; i < length; i++) { }
                console.timeEnd('a');

                console.time('b');
                for (let i in x) { }
                console.timeEnd('b');

                console.time('c');
                for (let i of x) { }
                console.timeEnd('c');
            }

            fortime(10);
            // a: 0.104ms
            // b: 0.022ms
            // c: 0.008ms

            fortime(100);
            // a: 0.005ms
            // b: 0.018ms
            // c: 0.022ms

            fortime(1000);
            // a: 0.015ms
            // b: 0.265ms
            // c: 0.065ms

            fortime(10000);
            // a: 0.14ms
            // b: 1.147ms
            // c: 3.853ms

            fortime(100000);
            // a: 3.406ms
            // b: 7.744ms
            // c: 0.141ms

            fortime(1000000);
            // a: 0.633ms
            // b: 159.481ms
            // c: 0.62ms

            fortime(10000000);
            // a: 5.156ms
            // b: 6.407s
            // c: 8.121ms
            ```

## 异常中的代码分块
[top](#catalog)
- try的语法
    ```js
    try{
        statements
    } catch (parameter){
        statements
    } finally{
        statements
    }
    ```
- try、catch、finally，每一个都是一个代码分块
    - 因此每个块中可以用`let`声明同名的变量
- catch 的特殊性
    - catch在执行时会创建一个与一般语句相同的环境
    - 块中的 parameter 是标识符声明，会绑定异常对象，所以可以使用模版赋值

- 示例
    - 参考代码
        - [src/structured/block/try_block.js](src/structured/block/try_block.js)
    - 代码内容
        ```js
        try {
            let a = 100;        // 声明重名的变量
            console.log('try a = ', a);
            throw { msg: 'test error', data: a };   // try a =  100
        } catch ({ msg, data }) {   // 解构异常对象
            console.log('msg = ', msg);             // msg =  test error
            let a = 20 + data;  // 声明重名的变量
            console.log('catch a = ', a);           // catch a =  120
        } finally {
            let a = 30;         // 声明重名的变量
            console.log('finally a = ', a);         // finally a =  30
        }
        ```

# 语句的值
## 不同的语句及其返回值
[top](#catalog)

|类型|子类型|语法|返回值|
|-|-|-|-|
|声明语句|数据声明语句|`var p1;`, `let p1;`, `const p1;`|**无返回值**|
|声明语句|函数声明语句|function<br>function*<br>|**无返回值**|
|声明语句|类声明语句|class className{...}|**无返回值**|
|表达式语句|变量赋值语句|variable = value|表达式的值|
|表达式语句|函数调用语句|foo()|表达式的值|
|表达式语句|属性赋值语句|object.property = value|表达式的值|
|表达式语句|方法调用语句|object.method()|表达式的值|
|表达式语句|单值表达式语句|value;|表达式的值|
|块|块/复合语句|{ statements }|最后语句的值。如果没有任何语句生成值，则按空语句处理|
|块|标签化语句|labelname: statements|最后语句的值。如果没有任何语句生成值，则按空语句处理|
|块|with语句|with (object) statements;|最后语句的值。如果没有任何语句生成值，则按空语句处理|
|分支语句|条件分支语句|if(condition) statements|返回最后产生的值，如果没有语句产生值，则返回undefined|
|分支语句|多重分支语句|switch case|返回最后产生的值，如果没有语句产生值，则返回undefined|
|循环语句||for|返回最后产生的值，如果没有语句产生值，则返回undefined|
|循环语句||for...in|返回最后产生的值，如果没有语句产生值，则返回undefined|
|循环语句||for...of|返回最后产生的值，如果没有语句产生值，则返回undefined|
|循环语句||while|返回最后产生的值，如果没有语句产生值，则返回undefined|
|循环语句||do...while|返回最后产生的值，如果没有语句产生值，则返回undefined|
|控制结构|异常捕获处理|try...catch|无|
|控制结构|函数返回子句|return [expression]<br>yield [expression]|返回值|
|控制结构|异常抛出语句|throw exception|异常对象|
|控制结构|继续执行子句|continue [label]|无|
|控制结构|中断执行子句|break [label]|无|
|模块化|导入|import...|无|
|模块化|导出|export...|无|
|其他|调试语句|debugger|无|
|其他|空语句|`;`<br>`{}`|无|

## 语句的执行状态
[top](#catalog)
- 代码中的任何异常都可以归纳为**4种状态**
    1. throw: 执行失败，并抛出异常
    2. continue: 继续，语句循环中，并被continue子句指示返回到指定位置继续
    3. break: 中断，语句在循环或switch中，被break子句终止
    4. return: 返回，语句从函数中返回并带有某个值Value，包括默认返回的undefined

- 所有语句都是用称为 `normal` 状态的结果来返回自身的值
    - `normal` 只表示语句正常结束，但不代表是否返回一个可计算的值

## 语句有值与无值
[top](#catalog)
- 语句最终都是有值的，包括undefined
- ES规定: 所有逻辑语句、有代码分块的语句中
    - 子句或块无值时，将返回undefined

# 作用域--组织代码分块的方式
## 作用域的基本概念
[top](#catalog)
- 什么是作用域？
    - 作用域来源于`代码分块`
        - `代码分块`导致了: 变量或成员的可见性，这个可见性区域，就是作用域
    - 本质就是: 一段代码所在的区域
    - 相对于执行上下文，<span style='color:red'>作用域是静态的，它在编写代码的时候就已经确定了</span>

- 作用域的作用（就是代码分块的作用）
    - 声明标识符、创建**多语句**的结构
    - 维持变量或成员的可见性，不同分块的同名变量不会冲突

- 两种作用域
    - 词法作用域
        - 使用 `let`、`const` 固化代码分块
        - 相当于通过 `let`、`const` 将 `{...}` 部分变成了块级作用域
    - 变量作用域
        - 使用 `var`、函数声明

## 词法作用域--模块化层次的主要体现之一
### js中的词法作用域
[top](#catalog)
- 什么是词法作用域？
    - 通过静态词法分析得到的是词法作用域
- 代码分块与词法作用域的区别

    |分类|状态|创建时期|实现方式|执行结果|
    |-|-|-|-|-|
    |代码分块|**物理上的**/静态的|在语法分析阶段识别|`{ statements }`|-|
    |词法作用域|**逻辑上的**/动态的|在执行期动态创建|`词法环境`|将`代码分块`**映射**为`词法环境记录`，实例化块级别的词法作用域|

- 词法作用域的基础是代码分块，即: `{ statements }`
    - 能够使 `{ statements }` 产生作用域效果的前提是
        - 使用 `let`，或`const` 固化代码分块
    - 相当于<span style='color:red'>通过 `let`、`const` 将 `{...}` 部分变成了块级作用域</span>

- JS中的词法作用域

    |级别|作用域|分类|示例|说明|
    |-|-|-|-|:--|
    |1|表达式|-|eval()|不存在表达式作用域，eval是一个特例|
    |2|语句|数据声明|var...<br>let...<br>const...||
    |2|语句|一般语句|if ()...else...;<br>for()...;<br>do...while...;<br>with()...;|被省略的部分既可以是单行语句，也可以是用`{}`标识的块|
    |2|语句|块|{...};<br>switch(){...};<br>try{...}<br>catch(){...}<br>finally{...};|这些语法关键字本身也是语句，并且用`{}`来限制一个新的代码分块，并作为它的**子级**词法作用域|
    |3|函数|-|function x(){...}|以函数声明的代码分块为作用域。<br>函数调用时`()`，JS会为函数创建一个词法环境，将代码分块实例化为一个函数级的词法作用域|
    |4|模块|-|export...;||
    |5|全局|-|依赖于HOST的实现||

- 在 if 中无法声明函数
    - 参考代码
        - [src/structured/block/if_block.js](src/structured/block/if_block.js)
    - 代码内容
        ```js
        let x = 1;
        function foo(){
            if (true){
                let x = 12;
                let y = 13;
                console.log(x); // 12
                function bar(){console.log('this is bar')}
            }
            bar(); // this is bar
            // 无法使用 `{}` 内部用let声明的变量
            // console.log(y); // ReferenceError: y is not defined
        }

        foo();
        console.log(x); // 1
        ```
    - 对于语句内的函数声明，其名字将提升到语句之外的函数或全局作用域
    - 函数作用域可以接受函数声明，但是**语句级的作用域无法接受函数声明** 的根本原因是
        - <span style='color:red'>函数的名字是一个变量名(var)，而不是一个词法名字(let)</span>
        - 语句级的作用域无法保存变量名

- 语句作用域
    - 是指: 变量在脱离了创建它的表达式之后，仍然可以在<span style='color:red'>创建语句的作用域</span>中被访问
    - 如: `for( let i=0; i < 10; i++){...}`
        - `i` 在脱离 `for` 之后，就无法再使用了
        - `i` 只能在创建它的 `for` 中使用
    - 一种不易识别的语句作用域
        - 示例
            ```js
            // var、let都可以
            // 1. 创建函数表达式
            var myfn = function foo(){ console.log(foo) };

            // 2. 通过变量访问函数
            myfn(); // [Function: foo]

            // 3. 直接访问被创建的具名函数 foo
            console.log(typeof foo);    // undefined
            ```
        - `var myfn = function foo(){ console.log(foo) };` 创建了一个语句作用域
            - 创建 `foo` 之后，因为赋值操作，将一直保持在 `myfn` 的作用域中
            - 所以，当使用 `myfn()` 时，可以通过 `foo` ，即自身的整个标识符来访问
            - 但是当在全局作用域中访问时，无法访问到
                1. 因为全局作用域中没有这个 标识符
                2. `foo` 仍然处在 `myfn` 的语句作用域中，外部无法访问

- 模块作用域的特殊性
    - 模块的代码分块如何产生？
        - 由模块的export声明决定
    - 模块的词法作用域如何产生？
        - 执行环境将预先扫描所有声明并进行词法分析，然后在实例化时创建一个自有的词法环境来登记声明
        - 实例化过程只在执行 import 导入模块时才会发生，用来绑定全部的顶层声明
    - 模块中的代码只会执行一次，如果模块中的名字被导出，那么所有导入它的模块将共享同一个实例
    - 所有 import 的名字会使用`本地名字`在当前的词法作用域中登记
        - 这些`本地名字`都是只读的
    - 在模块词法作用域中，访问`this`时，总会返回`undefined`

- 全局作用域
    - 全局环境中，3种用来登记名字的组件
        1. 变量作用域
            - 保存 `var` 声明的内容，和函数声明: `function xxx(...args){...}`
        2. 词法作用域
            - 保存 `let`、`const` 声明的内容
        3. 全局对象，window/global
            - 未通过 `var`、`let`、`const`声明的内容
            - 访问**不存在的变量名**时，导致创建的名字
    - 获取全局对象的方法（如果运行环境不提供全局对象）
        ```js
        var global = Function('return false')();
        ```

### 词法作用域之间的相关性
[top](#catalog)
- 作用域是互不相交的
- 作用域只有**平行**、**嵌套**两种相关性
- 词法作用域的**嵌套规则**
    - 相同级别的词法作用域可以相互嵌套
    - **高级**词法作用域可以包含**低级**词法作用域
    - **低级**词法作用域不能包含**高级**词法作用域
        - 因为无法包含，所以语言实现时，一般处理成语法上的违例或强制解释为平行关系
- **低级**词法作用域包含**高级**词法作用域时，转换为平行关系
    - 转换前
        ```js
        if (true){  // 作用域1: 级别2
            function foo(){ // 作用域2: 级别3}
        }
        ```
    - 转换后，转换为平行关系
        ```js
        if (true){  // 作用域1: 级别2
        }

        function foo(){ // 作用域2: 级别3}
        ```

## 变量作用域--模块化层次的主要体现之一
### js中的变量作用域
[top](#catalog)

|级别|作用域|示例|变量作用域|
|-|-|-|-|
|1|表达式|-|不存在|
|2|语句|-|不存在|
|3|函数|function x(){...}<br>function* x(){...}<br>...|支持|
|4|模块|var x = 100;|支持|
|5|全局|var x = 100;<br>y = 100;|支持|

### 变量作用域的特性
[top](#catalog)
- 会进入变量作用域的语法
    - `var` 声明的变量
    - 具名函数: `function foo(..args){...}`

- 变量作用域的特性
    - 声明的名字都是变量名，登记在变量作用域中
    - 变量名可以多次声明，来重新设置变量

- 变量作用域也被称为`变量的可见性`，包括
    - 局部可见性
    - 全局可见性

- 严格模式下，变量作用域将实现为词法作用域的一部分
    - 变量将直接创建在词法作用域中
    - 但是会保留**提升**的效果

- js中的变量作用域缺乏: 表达式 和 语句 级别的变量作用域，是导致 **js中全局命名污染的原因之一**

- 在**不存在变量作用域**的位置声明变量时，变量会逃逸到更外层
    - 如 `if`
        - `if`是语句
        - 所以在`if`内部声明的函数、变量会逃逸到`if`外部

### 模块的变量作用域
[top](#catalog)
- 变量声明在模块的变量作用域，如果变量被export，将会有一个同名的项被登记在名字空间中
- 因为模块运行在严格模式，模块内部无法通过 `eval()` 动态的在模块外部环境创建变量
    - `eval()` 的行为只会**影响**模块**自己的变量作用域**
- ES模块标准
    - ES使用静态模块装载机制
    - 主模块拥有自己的**模块级别的**变量作用域
    - 所有模块是相互依赖的，不会嵌套
    - 模块间只能使用 export/import 来相互引用，无法影响其他模块的变量作用域
- Nodejs模块标准
    - Nodejs的模块是**使用函数模拟的**
    - 即Nodejs的**模块使用的是函数的变量作用域**
    - 可以通过命令行参数 `--experimental-modules` 使用ES模块装载机制

## 作用域与执行上下文
[top](#catalog)
- 作用域与执行上下文<label style="color:red">完全是两个概念，在搜索属性时，需要进行区分</label>
- 作用域与执行上下文的区别：静态与动态
    - 作用域是静态的，定义之后不会变化
    - 执行上下文是动态的，执行函数体之前创建，函数执行结束后被销毁

- <label style="color:red">搜索属性时的注意事项</label>
    1. 如果属性前没有 `this`，先在上下文对象中查找，没有再依照[作用域链](#作用域链)一层一层查找
    2. 如果属性前有 `this`，先确定 `this`对象 是什么，即调用者是谁，然后在调用者内部进行搜索

- 全局执行上下文的特殊性
    - 函数执行上下文是在函数体执行前创建的
    - 全局执行上下文是在**全局作用域确定之后**，js代码执行之前创建的

## 作用域链
[top](#catalog)
- 什么是作用域链
    - 多个**在编码上嵌套**的作用域形成的链
    - 方向 : 从内到外
    - 搜索变量等内容时，会沿着作用域链搜索

- 作用域链产生的条件
    - 存在函数嵌套

- 作用域 数量的计算准则：`n + 1`
    - `n` 表示函数定义的数量
    - `1` 表示全局作用域

- 变量、函数、对象的搜索方式
    1. 在当前作用域对应的上下文对象中搜索，如果有则直接使用
    2. 如果没有，则到上一级作用域对应的上下文对象中搜索，如果有则直接使用
    3. 如果没有，则重复 2，直到 全局作用域
    4. 如果 全局作用域 中也没有，则产生异常

## 与作用域相关的问题
[top](#catalog)
1. 代码输出什么？
    - 代码内容
        ```js
        var x = 10;
        function fn(){
            console.log(x)
        }

        function show(f){
            var x = 20
            f()
        }

        show(fn)
        ```

    - 输出结果：10
    - 结果分析
        - fn的作用域链是：fn-->全局作用域
        - 虽然 show 与 fn 是调用与被调用的关系，但是在作用域上没有关联
        - 执行到fn时，先在 fn 内部查找，没有找到 x，则到全局作用域中查找，最终 x=10

2. 代码输出什么?
    - 代码内容
        ```js
        var fn = function(){
            console.log(fn)
        }

        fn()

        var obj = {
            fn2:function(){
                console.log(fn2)
                // console.log(this.fn2)
            }
        }

        obj.fn2()
        ```

    - 输出结果
        ```
        fn函数自身
        异常 ReferenceError: fn2 is not defined
        ```
    - 结果分析
        - 执行 `fn()` 时，fn已经存在于全局作用域，fn启动之后，可以在全局作用域找到自身，并输出
        - 执行 `obj.fn2()` 的分析
            - 作用域划分
                ```js
                ┌─────────── window ─────────┐
                │var obj = {                 │
                │    ┌────── function ─────┐ │
                │    │fn2:function(){      │ │
                │    │    console.log(fn2) │ │
                │    │}                    │ │
                │    └─────────────────────┘ │
                │}                           │
                └────────────────────────────┘
            - 函数执行时，在作用域链：function-->window 中，无法搜索到 fn2 的定义，所以产生了异常
                ```
        - 如果 `console.log(fn2)` 修改为 `console.log(this.fn2)`
            - 输出：`obj.fn2自身`
            - 通过 `this`对象 调用时，有了确切的调用对象，会到 this 中搜索属性

3. 代码输出什么?
    - 代码内容
        ```js
        var val = 1;

        var obj = {
            val:2,
            dbl:function(){
                console.log(this)
                this.val*=2
                console.log(val)
                console.log(this.val)
            }
        }

        var ff = obj.dbl()
        var fn = obj.dbl
        fn()
        ```
    - 输出结果
        ```
        obj
        1
        4
        window
        2
        2
        ```
    - 结果分析
        - 执行 `obj.dbl()`
            - console.log(this)：调用对象是 obj，所以会输出 obj
            - this.val*=2      ：通过this，使用 obj.val，并设置为4
            - console.log(val) ：在当前作用域中没有找到val，使用全局作用域的val，输出1
            - console.log(this.val) ： ：通过this，使用obj.val，输出 4
        - 执行 `fn()`
            - console.log(this)：调用对象是 window，所以会输出 window
            - this.val*=2      ：通过this，使用 window.val，并设置为2
            - console.log(val) ：在当前作用域中没有找到val，使用全局作用域的val，输出2
            - console.log(this.val) ： ：通过this，使用 window.val，并设置为2

# 提升
## 提升的本质
[top](#catalog)
- 什么是变量提升
    - 在当前作用域的任意位置，用`var`声明的变量，其标识符会被提升到当前作用域或其外层的、最低级别的`变量作用域`的开始处
    - 从外层的`变量作用域`开始的全部代码都可以访问变量
    - `变量作用域`参考
        - [变量作用域--模块化层次的主要体现之一](#变量作用域--模块化层次的主要体现之一)

- 提升只针对变量作用域的声明

- 函数提升的特殊性
    - 因为函数声明等同于 var 声明，所以也会有提升
    - 函数声明是**静态绑定**的，所以在提升时，函数对象已经创建完成
- 提升的流程
    - 前提
        - JS采用基于语句的静态语法分析
    - 流程
        1. JS在静态语法分析时，扫描所有顶层的变量声明
        2. 扫描后，在引擎的全局，或者函数与模块的最外层，构建包含变量名的**环境**
        3. 当程序开始在这些**环境**中运行时，环境中已经具有相应的变量名
    - 构建爱你的这些环境，就是[执行上下文](#执行上下文)

## 变量提升
[top](#catalog)
- 使用 `var 变量` 方式声明变量
    - 这种方式创建的变量会被提升到当前作用域的起始位置，执行**声明**
        - 赋值操作将在赋值代码处执行
    - 在变量声明之前使用变量，会得到 `undefined`
        - 因为变量被提升，只有声明没有值，所以得到 `undefined`
        - 示例
            ```js
            console.log(param);
            // 输出：undefined
            var param = 1234;
            ```
    - 在全局作用域中，`var` 声明的变量会添加到 `window` 对象
        ```js
        var x = 'abcd';
        console.log(window.x === x); // true
        ```
- 不使用 `var` 关键字声明变量
    - 如果没有使用 `var` 关键字，则变量不会提升
    - <label style="color:red">所有没有使用 `var` 声明的变量，无论代码写在什么位置，都会变为全局变量，相当于做了：`window.变量 = 变量值` </label>
    - 在变量声明之前使用这种变量，会引发异常
        - 因为变量没有被提升，所以在变量声明之前使用时，会引发变量未定义的异常
        - 示例
            ```js
            // Uncaught ReferenceError: param is not defined
            console.log(param);
            param = 1234;
            ```

- 示例
    1. 函数作用域中不使用 `var` 关键字声明变量
        ```js
        var a = 10;
        function test(){
            // 1. 因为内部的变量a没有使用 var声明，所以函数内部没有提升后的变量a

            // 2. 此处使用的是全局作用域中的a，所以输出10
            console.log("inner =", a);//输出：inner = 10

            // 3. 此处使用的是全局作用域中的a，a被该成了20
            a = 20;
        }

        test();

        // 4. a在test()内部被修改了，所以输出20
        console.log("outter = ", a); //输出：outter =  20
        ```
    2. 函数作用域中使用 `var` 关键字声明变量
        ```js
        var a = 10;
        function test(){
            // 1. 因为内部的变量a 使用 var声明，所以此处会有提升
            // var a;       // 被提升的 变量a

            // 2. 输出时，在当前函数作用域找到了变量a，但是变量a只有声明还没有赋值
            // 所以输出：undefined
            console.log("inner =", a);//输出：inner = undefined

            // 3. 此处使用的是当前函数作用域内部的 变量a，所以不会影响全局作用域中的变量
            var a = 20;
        }

        test();

        // 4. 全局变量a 没有被函数修改，所以输出的仍然是a
        console.log("outter = ", a); //输出：outter =  10
        ```
    3. 函数参数与全局变量重名
        ```js
        var a = 10;

        function test(a){
            // 2. 函数参数 相当于在函数作用域中声明的变量，所以当前作用域中有 变量a
            // var a;           // 函数参数的效果

            // 3. 调用函数时，没有传递参数，所以 a=undefined
            console.log("inner =", a);//输出：inner = undefined

            // 4. 此处修改函数作用域内部的变量a
            a = 20;
        }

        // 1. 函数参数与全局变量重名，并且不输入任何参数
        test();

        // 5. 函数内部没有修改变量a，所以输出的仍然是 10
        console.log("outter = ", a); //输出：outter =  10
        ```

    4. 在函数内部不使用 `var` 声明变量
        ```js
        var a = 10;
        function test(){
            // 1. 函数内部没有 变量a 的声明，所以使用全局变量

            // 2. 输出全局变量 a
            console.log("inner =", a);//输出：inner = 10

            // 3. 没有使用 var 声明变量，相当于 window.b = 20;
            // 创建了全局变量 b
            b = 20;
        }

        test();

        // 4. 在函数中创建了全局变量 b，此处输出 20
        console.log("outter = ", b); //输出：outter =  20
        ```

## 函数的提升
[top](#catalog)
- 使用**函数声明** `function 函数名([参数列表]){...}`创建的函数
    - 函数声明<label style="color:red">会提升</label>，在当前作用域的起始位置被**声明并创建**
    - 因为存在函数提升，所以可以在函数声明之前使用函数
    - 在声明之前使用函数
        - 在执行前，整个函数对象被提升并创建，所以可以正常执行
            ```js
            func();
            // 输出：this is func

            function func (){
                console.log("this is func");
            }
            ```

- 使用**函数表达式** `var 变量名 = function([参数列表]){...}`创建的函数
    - 函数表达式<label style="color:red">不会提升</label>，所以不要在函数表达式声明之前使用函数
    - 两个阶段
        1. 这样创建的函数，在执行前，只有变量部分：`var 变量名` 会被提升
        2. 执行函数表达式时，才会将函数赋值给变量
    - 在声明之前执行函数，会引发异常
        - func不是一个函数，因为只有变量名被提升了，函数对象还没有创建，所以执行时无法识别
            ```js
            // Uncaught TypeError: func is not a function
            func();

            var func = function(){
                console.log("this is func");
            }
            ```
## 代码块内的提升
[top](#catalog)
- 应为js没有块级的`变量作用域`，所以函数内的代码块中的函数也会被提升

- 示例
    - if块中的函数被当作函数表达式，所以 x 的标识符被提升
        1. 完整的if块
            ```js
            var x='outerx', y='outery'
            function foo (){
                // 这里没有使用 全局变量中的 x，说明x已经被提升
                console.log([x, y]); //[ undefined, 'outery' ]
                if (true){
                    console.log([x, y]);    // [ [Function: x], 'outery' ]
                    function x(){}
                }
            }
            foo();
            ```
        2. 简写的if块
            ```js
            var x = 'outer';
            function foo (){
                // 这里没有使用 全局变量中的 x，说明x已经被提升
                console.log(x); // undefined
                if (true) function x(){}; // if块中的函数被当作函数表达式，所以 x 的标识符被提升
                console.log(x); // [Function: x]
            }

            foo();
            ```

## 与提升相关的问题
[top](#catalog)
- 代码输出什么?
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

# 执行上下文
## 执行上下文
[top](#catalog)
- 执行上下文可以理解为 一种预处理技术。**正因为这种预处理才会产生提升的现象**

- 两种执行上下文
    - 全局执行上下文
    - 函数代码上下文

- 全局执行上下文
    - 如何准备执行上下文
        1. 执行全局代码之前，将 window对象 确定为全局执行上下文
        2. 对全局数据进行预处理，<label style="color:red">需要注意预处理顺序</label>
            1. 提升使用 `var` 定义的全局变量，相当于：`var 变量名 = undefined`，然后添加为 window对象 的属性
                - 如果作用域中有多个同名变量，统一使用一个。并在执行代码时，依照赋值语句一一赋值
            2. 提升使用 `函数声明` 方式创建的函数，并为其创建函数对象，然后添加为 window对象 的方法
                - 如果作用域中创建了多个同名函数，则后面的会覆盖前面的
            3. 将 window对象 赋值给 this对象，相当于：`this = window`
        3. 开始执行全局代码
    - 开始执行代码后，使用属性、方法时，会到 全局执行上下文 中搜索
    - 如下所示的代码，在 debug 模式下第二行代码不会执行，因为在准备全局上下文时，已经被提升并**执行过了**
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
            5. 将 **调用函数的对象** 赋值给 this对象
        3. 开始执行函数体
    - 不调用函数，不会创建函数执行上下文，并且这是一个一次性的对象

- 注意事项
    - **执行上下文 不是 this对象，这是两个完全不同的概念**，但是创建上下文时会涉及到this的操作
    - **上下文中，函数声明与变量的特点**
        - 函数声明无论写在作用域的哪里、同样的声明有几次，都会执行，并且会按顺序执行
        - 通过 `var` 声明的变量 无论有多少个，提升后都是：`var 变量名 = undefinde`。然后在执行时按照赋值语句一一赋值

- 执行上下文 数量的计算准则：`n + 1`
    - `n` 表示执行函数的次数，每次执行某个函数都会产生一个执行上下文
    - `1` 表示 window对象

## 执行上下文栈
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

## 与执行上下文相关的问题
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



[top](#catalog)