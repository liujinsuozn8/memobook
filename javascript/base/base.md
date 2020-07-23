<span id="catalog"></span>

### 目录
- [JavaSctipt简介](#JavaSctipt简介)
- [JS的编写位置](#JS的编写位置)
- [JS基本语法规范](#JS基本语法规范)
- [变量声明](#变量声明)
- [赋值语句](#赋值语句)
    - [基本赋值语句](#基本赋值语句)
    - [连续运算并赋值](#连续运算并赋值)
    - [逻辑运算结果赋值](#逻辑运算结果赋值)
- [代码块](#代码块)
- [流程控制语句](#流程控制语句)
    - [分支控制](#分支控制)
    - [循环语句](#循环语句)
    - [标签语句](#标签语句)
    - [返回值控制](#返回值控制)
    - [异常控制](#异常控制)
        - [try_catch_finally捕获与处理异常](#try_catch_finally捕获与处理异常)
- [数据类型](#数据类型)
    - [字面量与变量](#字面量与变量)
    - [数据类型分类--值类型与引用类型](#数据类型分类--值类型与引用类型)
    - [值类型与引用数据类型的特性](#值类型与引用数据类型的特性)
    - [值类型](#值类型)
    - [null类型](#null类型)
    - [类型转换](#类型转换)
        - [js中的类型转换](#js中的类型转换)
        - [其他类型转换为String](#其他类型转换为String)
        - [其他类型转换为Number](#其他类型转换为Number)
        - [其他类型转换为Boolean](#其他类型转换为Boolean)
    - [不同进制的数字](#不同进制的数字)
- [运算符](#运算符)
    - [二元算数运算符](#二元算数运算符)
    - [一元算数运算符](#一元算数运算符)
    - [自增自减](#自增自减)
    - [逻辑运算符](#逻辑运算符)
    - [赋值运算符](#赋值运算符)
    - [相等运算符](#相等运算符)
    - [运算符的优先级](#运算符的优先级)
- [对象](#对象)
    - [对象的基本概念](#对象的基本概念)
    - [对象的基本操作](#对象的基本操作)
    - [对象字面量](#对象字面量)
    - [对象的方法](#对象的方法)
    - [遍历对象属性](#遍历对象属性)
    - [构造函数](#构造函数)
    - [原型对象](#原型对象)
    - [对象的toString方法](#对象的toString方法)
- [this对象](#this对象)
- [函数](#函数)
    - [函数的基本概念](#函数的基本概念)
    - [创建与使用函数的方式](#创建与使用函数的方式)
    - [函数的返回值](#函数的返回值)
    - [函数的参数](#函数的参数)
    - [匿名函数与立即执行函数iife](#匿名函数与立即执行函数iife)
    - [函数对象的方法-call和apply](#函数对象的方法-call和apply)
    - [eval执行字符串中的代码](#eval执行字符串中的代码)
    - [arguments](#arguments)
- [作用域](#作用域)
    - [js作用域的基本概念](#js作用域的基本概念)
    - [全局作用域与window对象](#全局作用域与window对象)
    - [函数作用域](#函数作用域)
- [包装类](#包装类)
- [内建对象-数组](#内建对象-数组)
    - [数组的基本知识](#数组的基本知识)
    - [数组的常用方法](#数组的常用方法)
    - [数组去重](#数组去重)
    - [数组的遍历](#数组的遍历)
- [内建对象-Date](#内建对象-Date)
- [内建对象-Math](#内建对象-Math)
    - [Math对象简介](#Math对象简介)
    - [Math中的方法](#Math中的方法)
    - [Math随机函数的实际应用](#Math随机函数的实际应用)
- [内建对象-正则表达式](#内建对象-正则表达式)
- [内建对象-String](#内建对象-String)
    - [String的基本原理和基本方法](#String的基本原理和基本方法)
    - [String与正则表达式相关的方法](#String与正则表达式相关的方法)
- [垃圾回收gc](#垃圾回收gc)
- [弹出框](#弹出框)
- [反射](#反射)
- [JSON](#JSON)
    - [JSON的基本知识](#JSON的基本知识)
    - [通过工具类对象JSON来转换json与js对象](#通过工具类对象JSON来转换json与js对象)
- [注意事项](#注意事项)

# JavaSctipt简介
[top](#catalog)
- `ECMAScript`是`JavaSctipt`的标准，各个厂商提供实现，一般情况下这两个词的含义是相同的，但是JavaScript的含义更广泛

- <label style="color:red">一个完整的JavaScript实现应该有三部分组成</label>
    - <label style="color:red">ECMAScript：实现标准 (简称ES)</label>
    - <label style="color:red">DOM：文档对象模型</label>
    - <label style="color:red">BOM：浏览器对象模型</label>

# JS的编写位置
[top](#catalog)
- 3种JS编写位置
    1. 内联js
        - 将js写到标签的事件函数中。触发事件时，js会执行
        - 将js写在超链接的href属性中，如`href="javascript:alert('click a tag');"`。点击超链接时，会执行js
    2. 内部js
        - 将js写到`<head>`下的`<script type="text/javascript">`标签中中
    3. 外部js（推荐使用）
        - 使用方法
            1. 将js代码保存到外部的`*.js`文件中
            2. 在html中，通过`<script type="text/javascript" src="外部js路径">`的`src`属性引入外部js
        - 将js保存成外部js文件之后，可以:
            - 可以在不同的页面中进行引用
            - <label style="color:red">可以利用浏览器缓存</label>
        - 当某个`<script>`标签用来引入外部js后，就不能在该被标签中编写js代码。即使编写了浏览器也会忽略

- 示例
    - html
        - 参考代码
            - [/javascript/base/src/usage/usage.html](/javascript/base/src/usage/usage.html)
        - 代码内容
            ```html
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>usagetest</title>
                <!-- 2. 使用内部js -->
                <script>
                    alert("js in script");
                </script>

                <!-- 3. 使用外部js -->
                <script type="text/javascript" src="./test.js">

                    // 4. 在引入外部js的<script>标签中编写js代码，会被浏览器忽略
                    document.write("other js write");
                </script>
            </head>
            <body>
                <!-- 1. 内联js -->
                <!-- 1.1. js的编写位置1:将js写到标签的事件函数中。触发事件时，js会执行 -->
                <button onclick="alert('do click')">please click</button>
                <br>

                <!-- 1.2. JS的编写位置2:将js写在超链接的href属性中。点击超链接时，会执行js -->
                <a href="javascript:alert('click a tag');">test a</a>
            </body>
            </html>
            ```
    - 外部js
        - 文件路径
            - [/javascript/base/src/usage/test.js](/javascript/base/src/usage/test.js)
        - 代码内容
            ```js
            document.write("text from test.js");
            ```

# JS基本语法规范
[top](#catalog)
- js中严格区分大小写
- js的每一条语句以`;`结尾
    - 如果不写`;`，**浏览器会自动添加**，但是**会消耗一些系统资源**
    - 浏览器自动添加`;`时，也可能会加错位置，所以开发时一定要写`;`
- js会忽略多个空格和换行
- js注释
    - 多行注释：`/* */`
        ```js
        /*
            多行js注释
        */
        ```
    - 单行注释：`//`
        ```js
        /单行js注释
        ```

- 标识符：在js中所有可以自主命名的都可以称为标识符
    - 如：变量名、函数名、属性名
    - 标识符的规则
        1. 可以包含：字母、数字、`_`、`$`
        2. 标识符不能以数字开头
        3. 标识符不能是ES中的关键字或保留字
        4. 标识符一般都采用驼峰命名法
    - js底层采用Unicode编码来保存变量，所以理论上，素有utf-8中含有的内容都可以作为标识符

# 变量声明
[top](#catalog)
- `var`声明一个变量，变量可以重复声明
    ```js
    var k = 'v';
    var k = 'x';
    ```
- 使用`let` 声明一个变量，并且该变量不能重复声明
    ```js
    let k = 'v';
    // let 声明遍历时不能重复
    // let k = 'v';
    ```
- 使用 `const` 声明一个常量，常量无法修改
    ```js
    const a = 1234;
    // 无法修改常量
    // a = 2345;
    ```
- 同时声明多个变量，并赋值
    ```js
    var a = 1, b = 2, c = 3;
    let x = 1, y = 2, z = 3;
    const o = 1, p = 2, q = 3;
    ```

# 赋值语句
## 基本赋值语句
[top](#catalog)
- 示例
    ```js
    let param = 变量;
    let param = 函数;
    let param = 函数(); // 调用函数
    ```

## 连续运算并赋值
[top](#catalog)
- 借助 `()` 和 连续运算符，即:逗号 `,` , 连续运算多个语句，然后赋值
    - 通过 连续运算符 `,`，来分割、执行多条语句
    - 返回值
        - 最后一个表达式的返回值
    - 通过 `()` 内部用逗号分割的语句强制执行
    -
- 示例
    ```js
    let a = 1;
    let b = 2;
    let c =( a += 2, b += 3, a + b );
    /*
        相当于执行了:
        a += 2;
        b += 3;
        let c = a + b;
    */
    console.log(c) // 输出: 8
    ```

## 逻辑运算结果赋值
[top](#catalog)
- 运算之前，需要将逻辑运算符的左值和右值转换为 boolean 类型
    - 参考: [其他类型转换为Boolean](#其他类型转换为Boolean)
- 判断返回值的方法
    - 判断的前提: js的与、或运算符都是**短路**的
    - 返回值是左值、右值中，**最后一个计算**的boolean值的变量
- 与运算 `A && B`

    |运算过程|返回值|
    |-|-|
    |`false && false`|A|
    |`false && true`|A|
    |`true && false`|B|
    |`true && true`|B|

- 或运算 `A || B`

    |运算过程|返回值|
    |-|-|
    |`false || false`|B|
    |`false || true`|B|
    |`true || false`|A|
    |`true || true`|A|

- 示例
    - 参考代码
        - [src/assignment/assign_by_logic.html](src/assignment/assign_by_logic.html)
    - 代码内容
        ```js
        // 1.与运算符
        var a1 = 0;
        var b1 = '';
        var c1 = a1 && a1;          // false && false
        console.log('c1 = ' + c1);  // c1 = 0，返回的结果是 a1

        var a2 = 0;
        var b2 = 2;
        var c2 = a2 && b2;          // false && true
        console.log('c2 = ' + c2);  // c2 = 0，返回的结果是 a2

        var a3 = 'aaa';
        var b3 = 0;
        var c3 = a3 && a3;          // true && false
        console.log('c2 = ' + c2);  // c3 = 0，返回的结果是 b3

        var a4 = 'aaa';
        var b4 = 'bbb';
        var c4 = a4 && b4;          // true && true
        console.log('c4 = ' + c4);  // c4 = bbb，返回的结果是 b4


        // 2.或运算符
        var x1 = null;
        var y1 = 0;
        var z1 = x1 || y1;          // false || false
        console.log('z1 = ' + z1);  // z1 = 0，返回的结果是 y1

        var x2 = 0;
        var y2 = 'yyy';
        var z2 = x2 || y2;          // false || true;
        console.log('z2 = ' + z2);  // z2 = yyy，返回的结果是 y2

        var x3 = 'xxx';
        var y3 = 0;
        var z3 = x3 || y3;          // true || false;
        console.log('z3 = ' + z3);  // z3 = xxx，返回的结果是 x3

        var x4 = 'xxx';
        var y4 = 'yyy';
        var z4 = x4 || y4;          // true || true;
        console.log('z4 = ' + z4);  // z4 = xxx ，返回的结果是 x4
        ```

# 代码块
[top](#catalog)
- `{...}` 中的内容是一个代码块
- js中的代码块只负责进行代码的分组。每次执行时，代码块中的代码全部执行
- 代码块内部的变量，在外部是可见的
    ```js
    {
        var a = 13;
        console.log(a); // 输出：13
        a++; // 自增
    }

    console.log(a);// 输出：14
    ```

# 流程控制语句
## 分支控制
[top](#catalog)
- if else
    ```js
    if (表达式){
        ...
    }

    if (表达式){
        ...
    } else {
        ...
    }

    if (表达式){
        ...
    } else if (表达式){
        ...
    } else if (表达式){
        ...
    } else {
        ...
    }
    ```
- switch
    ```js
    switch (条件表达式) {
        case 表达式:
            语句
            break;
        case 表达式:
            语句
            break;
        case 表达式:
            语句
            break;
        default:
            语句
    }
    ```

## 循环语句
[top](#catalog)
- while
    - `条件表达式 = true` 时，执行循环体
    ```js
    while (条件表达式) {
        循环体
        break;
    }
    ```

- do while
    ```js
    do {
        循环体
        break;
    } while (条件表达式)
    ```

- for
    ```js
    for (初始化表达式; 条件表达式; 更新表达式){
        循环体
    }

    for (var i=0; i<10; i++){
        ...
    }
    ```

- for...in。用于遍历数组索引，对象的key
    ```js
    for (var 变量 in 对象){
        循环体;
    }
    ```
- for...in。用于遍历数组元素，对象的value
    ```js
    for (var 变量 in 对象){
        循环体;
    }
    ```

## 标签语句
[top](#catalog)
- 标签的声明方法
    - 只负责单一语句的开始到结束，结束位置包括: 分号、行末、`}`结束
        ```js
        标签名: 
            语句;
        ```    
        ```js
        // 这里只负责语句1
        标签名: 语句1;语句2;语句3;...
        ```
        ```js
        // 这里将if...else 作为一个语句，以 { 开头，以 } 结尾
        标签名:
        if (...) {
            ...
        } else {
            ...
        }
        ```
        ```js
        // 这里将for循环作为一个语句， 以 { 开头，以 } 结尾
        标签名:
        for (...){

        }
        ```

    - 负责一个代码快
        ```js
        标签名:{
            语句;
        }
        ```
- 标签的作用
    - 标识一个语句/语句块的范围
    - 进行流程控制
- 标签不能作用于
    - 注释
    - 模块导入导出语句
    - 函数声明
    - 类声明
- 标签的使用方法
    - 只能被 `continue`、`break` 使用
        - `continue 标签名`，表示停止当前循环，跳转到标签负责的范围的**起点**
        - `break 标签名`，表示跳转到标签负责的范围之外
    - 在标签语句内的**非循环逻辑中**使用 `break` 时，后面**必须加标签名**，否则会有编译异常
        - 循环内的 break 表示退出循环，非循环内的 break 单纯的表示要退出标签语句
        ```js
        标签名:{
            break 标签名
        }
        ```
    - `continue 标签名` 只适用于**直接负责 for 的标签语句**，否则会产生编译编译异常
        - 原因
            - <labe style='color:red'>contiune 关键字只能跳转到：当前层、外层(需要加标签) 循环的起始位置</label>
            - `标签名: {}` 这样的标签语句无法被 continue 视作一个循环的起始位置
        - 适用的写法
            ```js
            标签名:
            for (...){
                continue 标签名; // 从标签名开始，执行下一次循环
            }
            ```
        - 不适用的写法
            ```js
            // 在此处，标签只负责一个 块，而不是一个循环
            标签名:{    // 虽然内部有循环，但是标签本身不被视作一个循环
                for (...){
                    continue 标签名;
                }
            }
            ```

- 示例
    - 参考代码
        - [src/flow_control/tag.js](src/flow_control/tag.js)
    - 中断循环
        1. break + 标签语句
            ```js
            break_start:
            for (let i = 1; i < 5; i++) {
                for (let j = 1; j < 5; j++) {
                    if (i == 3 & j == 3){
                        // 如果满足条件，则直接退出双重循环
                        // 普通的 break 只能退出内层循环，不能退出外部循环
                        break break_start; 
                    }
                    
                    console.log(i * j);
                }
            }
            /* 输出
            1 2 3 4
            2 4 6 8
            3 6
            break到标签后，剩余的循环被取消了
            */
            ```
        2. continue + 标签语句
            ```js
            continue_start:
            for (let i = 1; i < 5; i++) {
                for (let j = 1; j < 5; j++) {
                    if (i == 3 & j == 3){
                        // 如果满足条件，跳出当前内层循环，从下一次外层循环开始继续执行
                        continue continue_start; 
                    }
                    
                    console.log(i * j);
                }
            }

            /* 输出: 
            1 2 3  4
            2 4 6  8
            3 6
            4 8 12 16
            满足 i=3、j=3时，直接跳出内层循环，继续下一次外层循环，
            所以第三次循环应该输出的 9、12 被跳过了
            */
            ```
    - break 直接跳出标签语句
        1. 使用 break 跳出标签语句
            ```js
            let str = 'abcd';
            break_tag01:{
                console.log('before');  // 输出: before
                if (str === 'abcd'){
                    break break_tag01;    // 直接跳出标签
                }
                console.log('after');   // 在break处跳出标签，不会有机会执行
            }
            ```
        2. 将标签语句执行完
            ```js
            let num = 222;
            break_tag02:{
                console.log('before');  // 输出: before
                if (num === 33){        // 不满足条件不会跳出标签
                    break break_tag02;    
                }
                console.log('after');   // 输出: after
            }
            ```
        3. 标签语句后面没有加标签名，产生编译异常
            ```js
            let mark = 'abcd'
            break_tag02:{
                console.log('before');
                if (num === 'abcd'){
                    break;              // 编译异常，标签语句内的 break 后，不能省略当前标签名
                }
                console.log('after');
            }
            ```

## 返回值控制
[top](#catalog)
- 通过 `return` 关键字控制**函数**的返回
- `return` 只能用于函数内
- 遇到逻辑上的第一个 `return` 后会返回
- 如果整个函数都没有 `return`，会执行到最后，并返回 `undefined`

## 异常控制
### throw抛出异常
[top](#catalog)
- 抛出异常的方式
    - 关键字式抛出
        ```js
        throw 'xxx';
        ```
    - 函数式抛出
        ```js
        throw('xxx');
        ```
- 可抛出的内容
    - 应该是一个错误对象，即 `Error()` 构造函数的实例对象
    - 实际使用时，可以是任意类型的数据

- throw 会阻止后续的代码执行，只能被 `try...catch` 捕获

### try_catch_finally捕获与处理异常
[top](#catalog)
- `try...catch` 的功能
    - 用于捕获一个代码块中的可能发生的异常，并使用exception来执行该异常的引用
- `try...catch` 的定义
    - 基本定义
        ```js
        try{
            // 可能会发生异常的语句
        } catch (exception){
            // 异常处理
        } finally{  // finally 必须在 catch 后面声明
            // 最终处理，无论有没有异常，最终都会执行
        }
        ```
    - 省略 exception 对象
        ```js
        try{
            // 可能会发生异常的语句
        } catch{
            // 异常处理
        } finally{
            // 最终处理，无论有没有异常，最终都会执行
        }
        ```
    - catch、finally 只保留一个
        ```js
        try{
            // 可能会发生异常的语句
        } catch{
            // 异常处理
        } 
        ```        
        ```js
        try{
            // 可能会发生异常的语句
        }finally{
            // 最终处理，无论有没有异常，最终都会执行
        }
        ```

- `finally` 中的代码一定会被执行，除了以下两种情况
    - 在 try 中使用 `return` 返回
        ```js
        try{
            return ...;
        } finally{...}
        ```
    - 在标签语句中，通过 break 跳出标签语句
        ```js
        标签名:
        try{
            break 标签名;
        } finally{...}
        ```
- 捕获时的执行顺序
    1. try
    2. catch
    3. finally
    4. 如果还有错误继续抛出到上一层 try...catch
    5. 异常别捕获后，`try...catch...finally` <label style='color:red'>后面的代码都不会被执行</label>
- 注意事项
    - catch、finally 都是可选的，但是**必须至少存在一个**
    - 如果 catch、finally 内部的代码发生了异常，异常会被抛出到更外层的 `try...catch`


# 数据类型
## 字面量与变量
[top](#catalog)
- 字面量
    - 字面量是指一些不可改变的值，如：数字`333`、字符串`"xdvcb"`、`null`、`undefined`
    - 字母量都可以直接使用

- 变量
    - 变量可以用来保存字面量
    - 变量的值可以任意改变

## 数据类型分类--值类型与引用类型
[top](#catalog)
- 数据类型指的是字面量的类型
- JS的7种数据类型
    |分类|数据类型|描述|
    |-|-|-|
    |值类型|string|字符串|
    |值类型|number|数值|
    |值类型|boolean||
    |值类型|undefined|未定义|
    |值类型|symbol|Es6增加的符号类型|
    |**引用类型**|object|对象|
    |**引用类型**|null|空值，但是null是属于object类型的|
    |**引用类型**|function|函数|

## 值类型与引用数据类型的特性
[top](#catalog)
- 特性
    - 值类型
        - 数据存储在**栈**
        - 赋值给其他变量时，会直接拷贝一份新的数据
        - 变量间的修改是独立的，不会影响其他被赋值的变量

    - 引用数据类型
        - 数据存储在**堆**，引用（数据存储的地址）保存在**栈**
        - 赋值给其他变量时，只会拷贝数据的地址
        - 修改属性时，会影响其他的变量
        - 通过`变量 = null;`，来清除变量对数据的引用
            - 只会清除当前变量，不会影响其他变量
- 数据的比较
    - 基本数据类型：比较栈中保存的数据
    - 引用数据类型：只比较栈中保存的地址

- 示例
    - 参考代码
        - [/javascript/base/src/datatype/baseDataAndReference.html](/javascript/base/src/datatype/baseDataAndReference.html)
    - js内容
        ```js
        // 1. 基本数据类型的存储
        var a = 12;
        var b = a;
        b++;
        console.log("a = ", a); //输出：a =  12
        console.log("b = ", b); //输出：b =  13

        // --------------------------------------------------------------
        // 2. 引用数据类型的存储
        // 2.1 修改数据会影响其他变量
        var obj01 = new Object();
        obj01.name = "name01";
        var obj02 = obj01;
        obj01.name = "name02";
        console.log("obj01 = ", obj01); //输出：obj01 =  {name: "name02"}
        console.log("obj02 = ", obj02); //输出：obj02 =  {name: "name02"}

        // 2.2 通过 null 清除变量对数据的引用
        var obj03 = new Object();
        obj03.name = "name03";
        var obj04 = obj03;
        console.log("obj03 = ", obj03); //输出：obj03 =  {name: "name03"}
        console.log("obj04 = ", obj04); //输出：obj04 =  {name: "name03"}

        //  清除引用
        obj03 = null;
        console.log("obj03 = ", obj03); //输出：obj03 =  null
        console.log("obj04 = ", obj04); //输出：obj04 =  {name: "name03"}

        // --------------------------------------------------------------
        // 3. 数据的比较
        var x = 12;
        var y = 12;
        console.log("x == y：", x == y);
        // x == y： true

        // 创建两个相同引用的变量
        var obj05 = new Object();
        obj05.name = "testname";
        var obj06 = obj05;
        console.log("obj05 == obj06：", obj05 == obj06);
        //输出：obj05 == obj06： true

        // 创建一个数据内容和obj05相同的变量
        var obj07 = new Object();
        obj07.name = "testname";
        console.log("obj05 == obj07：", obj05 == obj07);
        //输出：obj05 == obj07： false
        ```

## 值类型
[top](#catalog)
- <label style="color:red">无法给值类型 添加方法和属性，只能添加给对象</label>
- string
    - 需要使用引号 `"` 或 `'` 括起来
        ```js
        var a="abc";
        var b='abc';
        ```
    - 同类型的引号不能嵌套
    - 字符串过长需要换行时，可以通过 `+` 来连接
        - 如：
            ```js
            var x = "aaaaaaa"+
                    "bbbbbbb"+
                    "ccccccc";
            ```
        - 如果不使用 `+` 来连接，则默认只能使用 `=`后面到这一行结束的内容
            ```js
            var x = "aaaaaaa"
                    "bbbbbbb"
                    "ccccccc";

            console.log(x);
            // 输出：aaaaaaa
            ```
    - <label style="color:red">使用Unicode的方法</label>
        - 语法：`"\u16进制Unicode编码"`
    - **可以通过 索引来获取字符**: `字符串[index]`
        - 本质是将索引作为属性来使用
- number
    - 在js中所有的数值都是Number类型，包括整数、浮点数
    - 特殊数字
        - `Number.MAX_VALUE`，JS中的最大值：`1.7976931348623157e+308`
        - `Number.MIN_VALUE`，JS中的最小的**正小数**：`5e-324`
        - `Infinity`，表示正无穷
            - 在运算过程中如果数值超过了最大值，会返回，`Infinity`
        - `NaN`，表示: Not A Number
            - 如非法的数值运算
                ```js
                var a = "aaa" * "bbb";
                ```
            - `typeof NaN`的返回值是：Number
    - 数值运算的精确度
        - 整数运算可以保证绝对精确
        - 浮动数运算可能会得到一个**不精确的结果**，所以尽量不要使用js进行精确度要求比较高的运算
            ```js
            var a = 0.1 +0.2;
            console.log(a);
            // 输出：0.30000000000000004
            ```
- boolean
    - true/false

- undefined
    - Undefined类型只有一个值：`undefined`
    - `undefined`表示**未定义**
    - **只声明变量，但是没有给变量赋值时**，变量的值是`undefined`。如：
        ```js
        var c;
        console.log("c = ", c);
        console.log(typeof c);
        ```

- 示例
    - 参考代码
        - [/javascript/base/src/datatype/basetype.html](/javascript/base/src/datatype/basetype.html)
    - 代码内容
        ```js
        // JS中的最大值
        console.log("Number.MAX_VALUE = ", Number.MAX_VALUE);
        // Number.MAX_VALUE =  1.7976931348623157e+308

        // JS中的最小的正小数
        console.log("Number.MIN_VALUE = ", Number.MIN_VALUE);
        // Number.MIN_VALUE =  5e-324

        // 溢出运算
        console.log("Number.MAX_VALUE * Number.MAX_VALUE = ", Number.MAX_VALUE * Number.MAX_VALUE);
        // Number.MAX_VALUE * Number.MAX_VALUE =  Infinity

        // 非法运算
        var a = "aaa" * "bbb";
        console.log("aaa * bbb = ", a);
        // aaa * bbb =  NaN

        // NaN类型测试
        console.log(typeof NaN);
        // number

        // 小数运算的精确度
        var b = 0.1 +0.2;
        console.log(b);
        // 0.30000000000000004

        // 测试null的类型
        console.log(typeof null);
        // object

        // 未定义测试
        var c;
        console.log("c = ", c);
        // c =  undefined

        // 输出未定义类型
        console.log(typeof c);
        // undefined
        ```

## null类型
[top](#catalog)
- Null类型的值只有一个：`null`
- `null`表示**空对象**
- <label style="color:red">`typeof null` 返回的是：object</label>


## 类型转换
### js中的类型转换
[top](#catalog)
- 类型转换指将一个数据类型强制转换为其他的数据类型
- js类型转换主要是将其他的数据类型转换为：String、Number、Boolean
    - 转换为：Null、Undefined没有实际的意义

### 其他类型转换为String
[top](#catalog)
- 几种转换方式
    - 方式1：调用变量的 `toString()` 方法
        - 该方法会返回一个新的变量，不会影响原始变量，如：
            ```js
            var a1 = 123;
            var a2 = a1.toString();
            ```
        - Number中的 `NaN` 和 `Infinity` 会被转换为String型的：`"NaN"` 和 `"Infinity"`

        - `null`，`undefined`没有 `toString()` 方法，直接调用会导致异常：
            - `Uncaught TypeError: Cannot read property 'toString' of null`
            - `Uncaught TypeError: Cannot read property 'toString' of undefined`

    - 方式2：调用 `String()` 函数
        - 底层原理
            - 对于 Number 和 Boolean 类型，本质上是通过调用变量的 `toString()` 方法实现的
            - 对于 Null ，会转换为：`"null"`
            - 对于 Undefined ，会转换为：`"undefined"`

    - 方式3：**比较常用的方式**, 与空字符串 `""` 进行 `+` 运算
        - 这是一种**隐式类型转换**，由浏览器自动完成
        - 底层也是对非字符串类型调用了 `String()`
        - 参考：[二元算数运算符](#二元算数运算符)

- 示例
    - 参考代码
        - [/javascript/base/src/datatype/anyToString.html](/javascript/base/src/datatype/anyToString.html)
    - js内容
        ```js
        // 其他类型转换为String

        // 1 方式1 toString(): 
        // 1.1 方式1 toString(): Number -->> String
        var a1 = 123;
        console.log("a1 = ", a1, ", typeof a1 = ", typeof a1);
        // 输出：a1 =  123 , typeof a1 =  number

        var a2 = a1.toString();
        console.log("a2 = ", a2, ", typeof a2 = ", typeof a2);
        // 输出：a2 =  123 , typeof a2 =  string

        var a3 = NaN.toString();
        console.log("a3 = ", a3, ", typeof a3 = ", typeof a3);
        // 输出：a3 =  NaN , typeof a3 =  string

        var a4 = Infinity.toString();
        console.log("a4 = ", a4, ", typeof a4 = ", typeof a4);
        // 输出：a4 =  Infinity , typeof a4 =  string


        // 1.2 方式1 toString(): Boolean -->> String
        var b1 = true;
        console.log("b1 = ", b1, ", typeof b1 = ", typeof b1);
        // 输出：b1 =  true , typeof b1 =  boolean

        var b2 = b1.toString();
        console.log("b2 = ", b2, ", typeof b2 = ", typeof b2);
        // 输出：b2 =  true , typeof b2 =  string

        // 1.3 方式1 toString(): 无法转换null
        var c1 = null;
        console.log("c1 = ", c1, ", typeof c1 = ", typeof c1);
        // 输出：c1 =  null , typeof c1 =  object

        // 无法转换，会引发异常：
        // Uncaught TypeError: Cannot read property 'toString' of null
        // var c2 = c1.toString();
        // console.log("c2 = ", c2, "typeof c2 = ", typeof c2);

        // 1.4 方式1 toString(): 无法转换undefined
        var d1 = undefined;
        console.log("d1 = ", d1, ", typeof d1 = ", typeof d1);
        // 输出：d1 =  undefined , typeof d1 =  undefined

        // 无法转换，会引发异常：
        // Uncaught TypeError: Cannot read property 'toString' of undefined
        // var d2 = d1.toString();
        // console.log("d2 = ", d2, "typeof d2 = ", typeof d2);

        // --------------------------------------------------------------------
        // 2 方式2 调用 String() 函数
        // 2.1 方式2 调用 String(): Number -->> String
        var e1 = 123;
        console.log("e1 = ", e1, ", typeof e1 = ", typeof e1);
        // 输出：e1 =  123 , typeof e1 =  number

        var e2 = String(e1);
        console.log("e2 = ", e2, ", typeof e2 = ", typeof e2);
        // 输出：e2 =  123 , typeof e2 =  string

        var e3 = String(NaN);
        console.log("e3 = ", e3, ", typeof e3 = ", typeof e3);
        // 输出：e3 =  NaN , typeof e3 =  string

        var e4 = String(Infinity);
        console.log("e4 = ", e4, ", typeof e4 = ", typeof e4);
        // 输出：e4 =  Infinity , typeof e4 =  string

        // 2.2 方式2 调用 String(): Boolean -->> String
        var f1 = true;
        console.log("f1 = ", f1, ", typeof f1 = ", typeof f1);
        // 输出：f1 =  true , typeof f1 =  boolean

        var f2 = String(f1);
        console.log("f2 = ", f2, ", typeof f2 = ", typeof f2);
        // 输出：f2 =  true , typeof f2 =  string

        // 2.3 方式2 调用 String(): Null --> "null"
        var g1 = null;
        console.log("g1 = ", g1, ", typeof g1 = ", typeof g1);
        // 输出：g1 =  null , typeof g1 =  object

        var g2 = String(g1);
        console.log("g2 = ", g2, ", typeof g2 = ", typeof g2);
        // 输出：g2 =  null , typeof g2 =  string

        // 2.4 方式2 调用 String(): Undefined --> "undefined"
        var h1 = undefined;
        console.log("h1 = ", h1, ", typeof h1 = ", typeof h1);
        // 输出：h1 =  undefined , typeof h1 =  undefined

        var h2 = String(h1);
        console.log("h2 = ", h2, ", typeof h2 = ", typeof h2);
        // 输出：h2 =  undefined , typeof h2 =  string
        ```

### 其他类型转换为Number
[top](#catalog)
- 几种转换方式
    - 方式1：调用 `Number()` 函数
        - 各类型数据的转换结果

            |原始数据类型|数据内容|转换结果|
            |-|-|-|
            |String|纯数字字符串，如：`"1234"`|会直接转换为数字|
            |String|包含非数字内容，如：`"1234abc"`|会转换为 `NaN`|
            |String|空字符串：`""`|会转换为 `0`|
            |String|全部是空格，如：`"   "`|会转换为 `0`|
            |Boolean|true|1|
            |Boolean|false|0|
            |Null|null|0|
            |Undefined|undefined|`NaN`|

    - 方式2：字符串转换专用函数：`parseInt(字符串 [, 进制])`、`parseFloat(字符串 [, 进制])`
        - `Number()`的问题
            - 函数需要字符串是数字字符串，有其他字符就无法正常转换
            - 实际开发中经常会从html中取值，如像素大小：`"100px"`，就无法直接使用`Number()`
        - `parseInt(字符串)` 从左到右读取数字，直到读取到一个数字之外的字符，并将数字转换为**整数**
            - <label style='color:red'>如果是小数，会向下取整</label>
        - `parseFloat(字符串)` 从左到右读取数字，直到读取到一个数字之外的字符，并将数字转换为**浮点数**
        - 如果对非String类型数据使用这两个方法，会先转换为String，然后再操作

    - 方式3：对一个非 Number型 变量执行 ：`变量 - 0`、`变量 * 1`、`变量 / 1`，来将类型转换为 Number
        - 底层使用`Number()` 函数执行类型转换
        - 参考：[二元算数运算符](#二元算数运算符)

    - 方式4：对一个非 Number型 变量 使用**一元算数运算符**：`+`
        - 底层使用`Number()` 函数执行类型转换
        - 参考：[一元算数运算符](#一元算数运算符)

- 示例
    - 参考代码
        - [/javascript/base/src/datatype/anyToNumber.html](/javascript/base/src/datatype/anyToNumber.html)
    - js内容
        ```js
        // 1 方式1 调用 Number() 函数
        // 1.1 方式1 调用 Number() 函数: String ---> Number
        // 1.1.1 纯数字字符串
        var a1 = "1234";
        console.log("a1 = ", a1, ", typeof a1 = ", typeof a1);
        // 输出：a1 =  1234 , typeof a1 =  string

        var a2 = Number(a1);
        console.log("a2 = ", a2, ", typeof a2 = ", typeof a2);
        // 输出：a2 =  1234 , typeof a2 =  number

        // 1.1.2 字符串中包含非数字字符
        var b1 = "1234a";
        console.log("b1 = ", b1, ", typeof b1 = ", typeof b1);
        // 输出：b1 =  1234a , typeof b1 =  string

        var b2 = Number(b1);
        console.log("b2 = ", b2, ", typeof b2 = ", typeof b2);
        // 输出：b2 =  NaN , typeof b2 =  number

        // 1.1.3 空字符串
        var c1 = "";
        console.log("c1 = '", c1, "', typeof c1 = ", typeof c1);
        // 输出：c1 = '  ', typeof c1 =  string

        var c2 = Number(c1);
        console.log("c2 = ", c2, ", typeof c2 = ", typeof c2);
        // 输出：c2 =  0 , typeof c2 =  number

        // 1.1.4 全部是空格字符
        var d1 = "      ";
        console.log("d1 = '", d1, "', typeof d1 = ", typeof d1);
        // 输出：d1 = '        ', typeof d1 =  string

        var d2 = Number(d1);
        console.log("d2 = ", d2, ", typeof d2 = ", typeof d2);
        // 输出：d2 =  0 , typeof d2 =  number

        // --------------------------------------------------------------------
        // 1.2 方式1 调用 Number() 函数: Boolean ---> Number
        // 1.2.1  true ---> 1
        var e1 = true;
        console.log("e1 = ", e1, ", typeof e1 = ", typeof e1);
        // 输出：e1 =  true , typeof e1 =  boolean

        var e2 = Number(e1);
        console.log("e2 = ", e2, ", typeof e2 = ", typeof e2);
        // 输出：e2 =  1 , typeof e2 =  number

        // 1.2.2  false ---> 0
        var f1 = false;
        console.log("f1 = ", f1, ", typeof f1 = ", typeof f1);
        // 输出：f1 =  false , typeof f1 =  boolean

        var f2 = Number(f1);
        console.log("f2 = ", f2, ", typeof f2 = ", typeof f2);
        // 输出：f2 =  0 , typeof f2 =  number

        // 1.3 方式1 调用 Number() 函数: Null ---> Number
        var g1 = null;
        console.log("g1 = ", g1, ", typeof g1 = ", typeof g1);
        // 输出：g1 =  null , typeof g1 =  object

        var g2 = Number(g1);
        console.log("g2 = ", g2, ", typeof g2 = ", typeof g2);
        // 输出：g2 =  0 , typeof g2 =  number


        // 1.4 方式1 调用 Number() 函数: Undefined ---> Number
        var h1;
        console.log("h1 = ", h1, ", typeof h1 = ", typeof h1);
        // 输出：h1 =  undefined , typeof h1 =  undefined

        var h2 = Number(h1);
        console.log("h2 = ", h2, ", typeof h2 = ", typeof h2);
        // 输出：h2 =  NaN , typeof h2 =  number

        // --------------------------------------------------------------------
        // 2 方式2，字符串转换专用函数：
        // 2.1 字符串转换专用函数：parseInt()
        var i1 = parseInt("1234.3abc789epx");
        console.log("i1 = ", i1, ", typeof i1 = ", typeof i1);
        // 输出：i1 =  1234 , typeof i1 =  number

        var i2 = parseInt("qwec1234px");
        console.log("i2 = ", i2, ", typeof i2 = ", typeof i2);
        // 输出：i2 =  NaN , typeof i2 =  number

        // 2.2 字符串转换专用函数：parseFloat()
        var j1 = parseFloat("1234.3abc789epx");
        console.log("j1 = ", j1, ", typeof j1 = ", typeof j1);
        // 输出：j1 =  1234.3 , typeof j1 =  number

        var j2 = parseFloat("qwec1234px");
        console.log("j2 = ", j2, ", typeof j2 = ", typeof j2);
        // 输出：i2 =  NaN , typeof i2 =  number

        // 2.3 对String以外的数据使用 parseInt 、parseFloat
        var k1 = parseInt(true);
        console.log("k1 = ", k1, ", typeof k1 = ", typeof k1);
        // 输出：k1 =  NaN , typeof k1 =  number

        var k2 = parseFloat(true);
        console.log("k2 = ", k2, ", typeof k2 = ", typeof k2);
        // 输出：k2 =  NaN , typeof k2 =  number

        // --------------------------------------------------------------------
        // 方式3：与空字符串 `""` 进行 `+` 运算
        var i1 = 1234 + "";
        console.log("i1 = ", i1, ", typeof i1 = ", typeof i1);
        // 输出：i1 =  1234 , typeof i1 =  string

        var i2 = false + "";
        console.log("i2 = ", i2, ", typeof i2 = ", typeof i2);
        // 输出：i2 =  false , typeof i2 =  string

        var i3 = true + "";
        console.log("i3 = ", i3, ", typeof i3 = ", typeof i3);
        // 输出：i3 =  true , typeof i3 =  string

        var i4 = NaN + "";
        console.log("i4 = ", i4, ", typeof i4 = ", typeof i4);
        // 输出：i4 =  NaN , typeof i4 =  string

        var i5 = Infinity + "";
        console.log("i5 = ", i5, ", typeof i5 = ", typeof i5);
        // 输出：i5 =  Infinity , typeof i5 =  string

        var i6 = null + "";
        console.log("i6 = ", i6, ", typeof i6 = ", typeof i6);
        // 输出：i6 =  null , typeof i6 =  string

        var i7 = undefined + "";
        console.log("i7 = ", i7, ", typeof i7 = ", typeof i7);
        // 输出：i7 =  undefined , typeof i7 =  string
        ```

### 其他类型转换为Boolean
[top](#catalog)
- 几种转换方式
    - 方式1：通过函数：`Boolean()`进行转换
        - 各类型数据的转换结果

            |原始数据类型|数据内容|转换结果|
            |-|-|-|
            |Number|0、NaN|false|
            |Number|非0数字、Infinity|true|
            |String|空字符串：`""`|false|
            |String|非空字符串|true|
            |Null|null|false|
            |Undefined|undefined|false|
            |Object||true|

    - 方式2：对一个非Boolean型数据使用两次非运算符
        - 如：`a = !!a;`
        - 这是一种**隐式转换**
        - 参考：[逻辑运算符](#逻辑运算符)

- 示例
    - 参考代码
        - [/javascript/base/src/datatype/anyToBoolean.html](/javascript/base/src/datatype/anyToBoolean.html)
    - js内容
        ```js
        // 1. Number 转 Boolean
        // 1.1 非空数字、 Infinity
        var a1 = Boolean(123);
        console.log("a1 = ", a1);
        // 输出：a1 =  true

        var a2 = Boolean(-1);
        console.log("a2 = ", a2);
        // 输出：a4 =  true

        var a3 = Boolean(0.1);
        console.log("a3 = ", a3);
        // 输出：a3 =  true

        var a4 = Boolean(Infinity);
        console.log("a4 = ", a4);
        // 输出：a2 =  true

        // 1.2 NaN、0
        var a5 = Boolean(NaN);
        console.log("a5 = ", a5);
        // 输出：a5 =  false

        var a6 = Boolean(0);
        console.log("a6 = ", a6);
        // 输出：a6 =  false

        // 2. String转Boolean
        // 2.1 空字符串
        var b1 = Boolean("");
        console.log("b1 = ", b1);
        // 输出：b1 =  false

        // 2.2 非空字符串
        var b2 = Boolean("   ");
        console.log("b2 = ", b2);
        // 输出：b2 =  true

        var b3 = Boolean("   ");
        console.log("b3 = ", b3);
        // 输出：b3 =  true

        var b4 = Boolean("true");
        console.log("b4 = ", b4);
        // 输出：b4 =  true

        var b5 = Boolean("false");
        console.log("b5 = ", b5);
        // 输出：b5 =  true

        var b6 = Boolean("dfggrtyr");
        console.log("b6 = ", b6);
        // 输出：b6 =  true

        // 3. Null 转 Boolean
        var c1 = Boolean(null);
        console.log("c1 = ", c1);
        // 输出：c1 =  false

        // 4. Undefined
        var d1 = Boolean(undefined);
        console.log("d1 = ", d1);
        // 输出：d1 =  false
        ```

## 不同进制的数字
[top](#catalog)
- 16进制数字
    - 以 `0x` 开头。如：`var a = 0x12;`

- 8进制数字
    - 以 `0` 开头。如：`var b = 012;`

- 2进制数字
    - 以 `0b` 开头。如：`var c = 0b1110;`
    - 不是所有的浏览器都支持2进制数字

- 无论是哪一种进制的数字，打印时都会转换为10进制输出。如：`console.log(0x12);`，输出：18

- 8进制数字的字符串
    - 如：`"070"`
    - 对8进制数字使用`parseInt(字符串)`时，不同的浏览器会有不同的解析方式，有些会按照10进制解析，有些会按照8进制解析
    - 为了避免转换时的问题，一般会使用：`parseInt(字符串, 10)` 的方式，指定所有浏览器都将字符串解析为10进制

- 示例
    - 参考代码
        - [/javascript/base/src/datatype/.html](/javascript/base/src/datatype/.html)
    - js内容
        ```js
        // 16进制数字
        var a = 0x12;
        console.log(a);
        // 输出:18

        // 8进制数字
        var b = 012;
        console.log(b);
        // 输出:10

        // 2进制数字
        var c = 0b1110;
        console.log(c);
        // 输出:14

        // 8 进制数字字符串
        var d1 = "070";
        console.log("d1 = ", d1, ", typeof d1 = ", typeof d1);
        // 输出:d1 =  070 , typeof d1 =  string

        var d2 = parseInt(d1);
        console.log("d2 = ", d2, ", typeof d2 = ", typeof d2);
        // chrome输出: d2 =  70 , typeof d2 =  number
        ```

# 运算符
## 二元算数运算符
[top](#catalog)
- 与Number型数据的运算符
    - 正常的Number型数据之间执行正常运算
    - 对非Number类型数据进行运算时，先数据转换为对应的Number类型，然后再进行运算
        - 如：`true + false`会转换成：`1 + 0`
    - **任何数字、Null、Undefined和 NaN 进行运算，结果都是 NaN**

- `+` 运算符
    - 对于多个Number型的数据，执行加法运算
    - 对于多个String型的数据，执行字符串连接
    - <label style="color:red">任何类型和String型的`+`运算，都会先将非String型转换为String型，然后再执行字符串连接</label>
        - 扩展：任意类型与空字符串 `""` 进行 `+` 运算都将类型转换为字符串
            - 这是一种**隐式类型转换**，由浏览器自动完成
            - 底层也是对非字符串类型调用了 `String()`
            - 如:
                ```js
                var a = 1
                a = a + "";
                // 相当于
                // a = String(a) + "";
                ```
        - <label stlye="color:red">多个其他类型与String类型执行 `+` 运算时，从左至右，依次进行每一个部分的运算</label>
            - `var a = 1 + 2 + "3";`
                1. 先计算： `1 + 2`，得到 `3`
                2. 再执行：`3 + "3"`，得到：`"33"`
            - `var a = "3" + 1 + 2;`
                1. 先执行： `"3" + 1`，数字 `1` 会隐式转换为字符串 `"1"`，然后执行字符串连接，得到 `"31"`
                2. 再执行：`"31" + 2`，数字 `2` 会隐式转换为字符串 `"2"`，然后执行字符串连接，得到 `"312"`

- `-`、`/`、`*`
    - 任何非Number型数据都会先转换为Number型，再进行运算
        - 底层使用 `Number()`函数进行类型转换
    - 扩展： 可以对一个非 Number型 变量执行 ：`变量 - 0`、`变量 * 1`、`变量 / 1`，来将类型转换为 Number
        - 这是一种**隐式类型转换**，由浏览器自动完成
- `%`：取余数

- 示例
    - 参考代码
        - [/javascript/base/src/operator/unaryOperator.html](/javascript/base/src/operator/unaryOperator.html)

    - js内容
        ```js
        // 算数运算符
        // 1. boolean值参与运算
        var a1 = true + 1;
        console.log("a1 = ", a1, ", typeof a1 = ", typeof a1);
        // 输出：a1 =  2 , typeof a1 =  number

        var a2 = true + false;
        console.log("a2 = ", a2, ", typeof a2 = ", typeof a2);
        // 输出：a2 =  1 , typeof a2 =  number

        // --------------------------------------------------
        // 2. Null 与 Number的运算
        var b1 = 2 + null;
        console.log("b1 = ", b1, ", typeof b1 = ", typeof b1);
        // 输出：b1 =  2 , typeof b1 =  number

        // --------------------------------------------------
        // 3. 非字符串 与 NaN的运算
        var c1 = 3 + NaN;
        console.log("c1 = ", c1, ", typeof c1 = ", typeof c1);
        // 输出：c1 =  NaN , typeof c1 =  number

        var c2 = undefined + NaN;
        console.log("c2 = ", c2, ", typeof c2 = ", typeof c2);
        // 输出：c2 =  NaN , typeof c2 =  number

        var c3 = null + NaN;
        console.log("c3 = ", c3, ", typeof c3 = ", typeof c3);
        // 输出：c3 =  NaN , typeof c3 =  number

        var c4 = NaN - 1;
        console.log("c4 = ", c4, ", typeof c4 = ", typeof c4);
        // 输出：c4 =  NaN , typeof c4 =  number

        // --------------------------------------------------
        // 4. 字符串参与运算
        var d1 = "1234" + 2345;
        console.log("d1 = ", d1, ", typeof d1 = ", typeof d1);
        // 输出：d1 =  12342345 , typeof d1 =  string

        var d2 = 2345 + "1234";
        console.log("d2 = ", d2, ", typeof d2 = ", typeof d2);
        // 输出：d2 =  23451234 , typeof d2 =  string

        var d3 = NaN + "1234";
        console.log("d3 = ", d3, ", typeof d3 = ", typeof d3);
        // 输出：d3 =  NaN1234 , typeof d3 =  string

        // --------------------------------------------------
        // 5.多个其他类型与String类型执行 + 运算
        var e1 = 1 + 2 + "3";
        console.log("e1 = ", e1, ", typeof e1 = ", typeof e1);
        // 输出：e1 =  33 , typeof e1 =  string

        var e2 = "3" + 1 + 2;
        console.log("e2 = ", e2, ", typeof e2 = ", typeof e2);
        // 输出：e2 =  312 , typeof e2 =  string

        var e3 = 1 + "3" + 2;
        console.log("e3 = ", e3, ", typeof e3 = ", typeof e3);
        // 输出：e3 =  132 , typeof e3 =  string

        // --------------------------------------------------
        // 6. -、/、* 运算
        var f1 = 1234 - "234";
        console.log("f1 = ", f1, ", typeof f1 = ", typeof f1);

        var f2 = "1234" - "234";
        console.log("f2 = ", f2, ", typeof f2 = ", typeof f2);
        // 输出：f2 =  1000 , typeof f2 =  number

        var f3 = "1234" - true;
        console.log("f3 = ", f3, ", typeof f3 = ", typeof f3);
        // 输出：f3 =  1233 , typeof f3 =  number
        ```

## 一元算数运算符
[top](#catalog)
- `+` 正号、`-` 符号
- 对于Number型数据，会改变数值的符号
- 对于非Number型数据，会先转换为Number型，再使用一元运算符
    - 底层使用`Number()`进行里诶下那个转换
- 扩展：对于非Number类型，使用 `+` 会将数据转换为Number型
    - 这是一个**隐式类型转换**，由浏览器自动完成
    - 如：
        ```js
        var g5 = false;
        g5 = +g5;
        console.log(g5); // g5 = 0
        ```

- 示例
    - 参考代码
        - [/javascript/base/src/operator/binaryOperator.html](/javascript/base/src/operator/binaryOperator.html)
    - js内容
        ```js
        // 1 对于Number型数据，会改变数据的符号
        var a1 = 1234;
        a1 = -a1;
        console.log("a1 = ", a1, ", typeof a1 = ", typeof a1);
        // 输出：a1 =  -1234 , typeof a1 =  number

        // ----------------------------------------------------------
        // 2 对于非Number型数据，会先转换为Number型，再使用一元运算符
        var a2 = "sdfd";
        a2 = -a2;
        console.log("a2 = ", a2, ", typeof a2 = ", typeof a2);
        // 输出：a2 =  NaN , typeof a2 =  number

        var a3 = "123";
        a3 = -a3;
        console.log("a3 = ", a3, ", typeof a3 = ", typeof a3);
        // 输出：a3 =  -123 , typeof a3 =  number

        var a4 = true;
        a4 = -a4;
        console.log("a4 = ", a4, ", typeof a4 = ", typeof a4);
        // 输出：a4 =  -1 , typeof a4 =  number

        var a5 = false;
        a5 = -a5;
        console.log("a5 = ", a5, ", typeof a5 = ", typeof a5);
        // 输出：a5 =  -0 , typeof a5 =  number

        // ----------------------------------------------------------
        // 3 对于非Number类型，使用 `+` 会将数据转换为Number型
        var a6 = "5678";
        a6 = +a6;
        console.log("a6 = ", a6, ", typeof a6 = ", typeof a6);
        // 输出：a6 =  5678 , typeof a6 =  number

        var a7 = true;
        a7 = +a7;
        console.log("a7 = ", a7, ", typeof a7 = ", typeof a7);
        // 输出：a7 =  1 , typeof a7 =  number

        var a8 = false;
        a8 = +a8;
        console.log("a8 = ", a8, ", typeof a8 = ", typeof a8);
        // 输出：a8 =  0 , typeof a8 =  number

        var a9 = null;
        a9 = +a9;
        console.log("a9 = ", a9, ", typeof a9 = ", typeof a9);
        // 输出：a9 =  0 , typeof a9 =  number

        var a10;
        a10 = +a10;
        console.log("a10 = ", a10, ", typeof a10 = ", typeof a10);
        // 输出：a10 =  NaN , typeof a10 =  number

        var a11 = "abcdef";
        a11 = +a11;
        console.log("a11 = ", a11, ", typeof a11 = ", typeof a11);
        // 输出：a11 =  NaN , typeof a11 =  number
        ```

## 自增自减
[top](#catalog)
- `++` 自增，`--`自减
- 自增自减分为两种
    - 后增/后减：`a++;`
    - 前增/前减：`a++;`

- 自家自减运算，本质是一个**表达式**，所有会有返回值

- 运算符在前、后的区别
    - `a++` 表达式的值是： `a`(自增/减前的值)，运算后 a 的值是：`a = a + 1`
    - `++a` 表达式的值是：`a + 1`(自增/减后的值)，运算后 a 的值是：：`a = a + 1`
    - **结果的使用 与 运算 是一个连贯的动作，无法被其他运算符分离**

- **复杂自增/减运算的分析**
    - 示例1:
        - 计算式
            ```js
            var a = 20;
            var result = a++ + ++a + a;
            console.log(result); // result=64
            ```
        - 分析
            1. `a++`，表达式的值是：`20`，运算后a的值是：`a = 20 + 1 = 21`, 计算式: `var result = 20`
            2. `++a`，表达式的值是：`20 + 1 = 21`，运算后a的值是：`a = 21 + 1 = 22`，计算式：`var result = 20 + 22`
            3. `a`，直接参与运算：`var result = 20 + 22 + 22`
            4. 得到：`result = 64`
    - 示例2：
        - 计算式
            ```js
            var b = 10;
            var result = ++b + b++ +b;
            console.log(result); // result = 34
            ```
        - 分析
            1. `++b`，表达式的值是：`10 + 1 = 11`，运算后b的值是：`b = 10 + 1 = 11`，计算式：`var result = 11`
            2. `b++`，表达式的值是：`11`，运算后b的值是：`b = 12`，计算式：`var result = 11 + 11`
            3. `b`，直接参与运算：`var result = 11 + 11 + 12`
            4. 得到：`result = 34`
    - 示例3：
        - 计算式
            ```js
            var c = 5;
            c = c++;
            console.log(c); // c = 5;
            ```
        - 分析
            1. `c++`，表达式的值是：`5`，运算后b的值是：`b = 5 + 1 =6`，计算式：`c = 5`
            2. 得到`c = 5`

- 示例：
    - 参考代码
        - [/javascript/base/src/operator/increDecre.html](/javascript/base/src/operator/increDecre.html)
    - js内容
        ```js
        // 1. 基本的自增/减表达式分析
        var n1 = 10, n2 = 20;
        var n = n1++; //n = 10, n1 = 11
        console.log("n = ", n);// 输出：n = 10
        console.log("n1 = ", n1);// 输出：n1 = 11

        n = ++n1; // n=12, n1= 12
        console.log("n = ", n);// 输出：n = 12
        console.log("n1 = ", n1);// 输出：n1 = 12

        n = n2--; // n = 20, n2 = 19
        console.log("n = ", n);// 输出：n = 20
        console.log("n2 = ", n2);// 输出：n2 = 19

        n = --n2; // n = 18, n2 = 18
        console.log("n = ", n);// 输出：n = 18
        console.log("n2 = ", n2);// 输出：n2 = 18

        // -------------------------------------------------
        // 2. 复杂自增/减运算的分析
        var a = 20;
        var result01 = a++ + ++a + a;
        console.log("result01 = ", result01);// 输出：result01 =  64

        var b = 10;
        var result02 = ++b + b++ +b;
        console.log("result02 = ", result02);// 输出：result02 =  34

        var c = 5;
        c = c++;
        console.log("c = ", c); // 输出：c =  5
        ```

## 逻辑运算符
[top](#catalog)
- 非：`!`
    - 对于非Boolean型数据使用 `!` 时，会先将非Boolean转换为 Boolean型 数据，然后再进行运算
        - 本质是一种**隐式类型转换**
    - **扩展**：可以对一个非Boolean型数据使用两次非运算符，将数据转换成Boolean型数据，如
        ```js
        var a = 1;
        a = !!a;
        console.log(a);// a= true
        ```

- 与: `&&`，或: `||`
    - 特性
        1. 不会改变操作数的数据类型
            - 如果`&&`左侧是false、`||`左侧是true，将不会计算后面的表达式
        2. 数据类型都可以作为运算结果
        3. 会将操作数隐式转换为布尔值，来进行布尔运算
        4. 运算过程是短路的
    - 特性分析
        ```js
        let str = 'hello'
        let obj = {};
        
        let a = str || obj; // a = hello
        // 1. 对 str 应用特性3，执行隐式转换，获得一个布尔值 true
        // 2. 执行判断是 true ，则应用特性4，短路当前的执行。
        //    此时运算的位置停留在 str 处
        // 3. 应用特性1，不改变操作数的数据类型，str 保留字符串类型
        // 4. 应用特性2，将字符串类型的 str 作为结果返回

        let b = str && obj; // b = obj
        // 1. 对 str 应用特性3，执行隐式转换，获得一个布尔值 true
        // 2. 执行判断是 true，继续执行下一个判断
        // 3. 对 obj 应用特性3，执行隐式转换，获得一个布尔值 true
        // 4. 所有操作数判断完成
        //    此时运算的位置停留在 obj 处
        // 5. 应用特性1，不改变操作数的数据类型，str、obj 保留字符串类型
        // 6. 应用特性2，将最后一个操作数 obj 作为结果返回
        ```

- 连续的逻辑运算可以用来替代语句
    - 参考: [逻辑运算结果赋值](逻辑运算结果赋值)

- 示例
    - 参考代码
        - [/javascript/base/src/operator/ogicOperator.html](/javascript/base/src/operator/ogicOperator.html)
    - js内容
        ```js
        // 1. 对非Boolean型数据使用 非运算符 !
        var a1 = 2;
        a1 = !a1;
        console.log("a1 = ", a1, ", typeof a1 = ", typeof a1);
        // 输出：a =  false , typeof a =  boolean

        var a2 = 0;
        a2 = !a2;
        console.log("a2 = ", a2, ", typeof a2 = ", typeof a2);
        // 输出：a2 =  true , typeof a2 =  boolean

        // -----------------------------------------------------------------
        // 2. 扩展：对非Boolean型数据使用 `!!`，将数据转换为 Boolean型数据
        var b1 = 1;
        b1 = !!b1;
        console.log("b1 = ", b1, ", typeof b1 = ", typeof b1);
        // 输出：b1 =  true , typeof b1 =  boolean

        var b2 = "qwer";
        b2 = !!b2;
        console.log("b2 = ", b2, ", typeof b2 = ", typeof b2);
        // 输出：b2 =  true , typeof b2 =  boolean

        // -----------------------------------------------------------------
        // 3. 非Boolean型数据的运算规则，返回值是运算过程中最后一个计算的表达式的值
        // 3.1 a && b
        // 3.1.1 a && b = true && true  --->b
        var c1 = 1 && 2;
        console.log("c1 = ", c1, ", typeof c1 = ", typeof c1);
        // 输出：c1 =  2 , typeof c1 =  number

        // 3.1.2 a && b = false && true --->a
        var c2 = NaN && 2;
        console.log("c2 = ", c2, ", typeof c2 = ", typeof c2);
        // 输出：c2 =  NaN , typeof c2 =  number

        // 3.1.3 a && b = true && false --->b
        var c3 = 2 && NaN;
        console.log("c3 = ", c3, ", typeof c3 = ", typeof c3);
        // 输出：c3 =  NaN , typeof c3 =  number

        // 3.1.4 a && b = false && false--->a
        var c4 = 0 && NaN;
        console.log("c4 = ", c4, ", typeof c4 = ", typeof c4);
        // 输出：c4 =  0 , typeof c4 =  number

        // 3.2 a || b
        // 3.2.1 a || b = true || true  --->a
        var d1 = 1 || 2;
        console.log("d1 = ", d1, ", typeof d1 = ", typeof d1);
        // 输出：d1 =  1 , typeof d1 =  number

        // 3.2.2 a || b = false || true --->b
        var d2 = NaN || 2;
        console.log("d2 = ", d2, ", typeof d2 = ", typeof d2);
        // 输出：d2 =  2 , typeof d2 =  number

        // 3.2.3 a || b = true || false --->a
        var d3 = 2 || NaN;
        console.log("d3 = ", d3, ", typeof d3 = ", typeof d3);
        // 输出：d3 =  2 , typeof d3 =  number

        // 3.2.4 a || b = false || false--->b
        var d4 = 0 || NaN;
        console.log("d4 = ", d4, ", typeof d4 = ", typeof d4);
        // 输出：d4 =  NaN , typeof d4 =  number
        ```

## 赋值运算符
[top](#catalog)

|运算符|计算式|含义|
|-|-|-|
|=|`a = 1234;`|将1234赋给 a|
|+=|`a += 1;`|`a = a + 1`|
|-=|`a -= 1;`|`a = a - 1`|
|*=|`a *= 1;`|`a = a * 1`|
|/=|`a /= 1;`|`a = a / 1`|
|%=|`a %= 1;`|`a = a % 1`|

## 关系运算符
[top](#catalog)
- 可用关系运算符：`>`、`>=`、`<`、`<=`、
- 无论关系运算符两侧是什么类型的数据，返回值都是：true / false

- Number类型与其他类型之间的关系运算
    - 两个Number类型比较时，直接按数值关系进行比较
    - **任何值和NaN进行比较的结果都是 false**
    - Number类型与非Number类型比较时，先将非Number型转换为Number型，然后再进行关系比较
        - Number类型和非数字字符串进行比较时，由于非数字字符串会**隐式转换为 NaN**，所以比较结果永远都是 NaN

- String类型之间的关系运算
    - 两个String类型数据比较时，会逐个字符的比较Unicode编码的大小
        - 即使两个字符串都是数字字符串，也不会转换为Number型

- 示例
    - 参考代码
        - [/javascript/base/src/operator/relationOperator.html](/javascript/base/src/operator/relationOperator.html)
    - js内容
        ```js
        // 1. Number类型与其他类型之间的关系运算
        // 1.1 两个Number类型，直接按数值关系进行比较
        var a1 = 3 > 2;
        console.log("a1 = ", a1);
        // 输出：a1 =  true

        // 1.2 任何值和NaN进行比较的结果都是 false
        var b1 = 3 > NaN;
        console.log("b1 = ", b1);
        // 输出：b1 =  false

        var b2 = 3 < NaN;
        console.log("b2 = ", b2);
        // 输出：b2 =  false

        // 1.3 Number类型与非Number类型比较时，
        //     先将非Number型转换为Number型，然后再进行关系比较
        // 1.3.1 Number 与 Boolean 进行比较
        var c1 = 1 > true;
        console.log("c1 = ", c1);
        // 输出：c1 =  false

        var c2 = 1 >= true;
        console.log("c2 = ", c2);
        // 输出：c2 =  true

        // 1.3.2 Number 与 数字字符串 进行比较
        var d1 = 2 > "1234";
        console.log("d1 = ", d1);
        // 输出：d1 =  false

        var d2 = 2345 > "1234";
        console.log("d2 = ", d2);
        // 输出：d2 =  true

        // 1.3.3 Number 与 非数字字符串 进行比较
        // 非数字字符串 会隐式转换为 NaN，所以比较结果永远都是NaN
        var e1 = 2345 < "xxxxx";
        console.log("e1 = ", e1);
        // 输出：e1 =  false

        var e2 = 546 > "xxxxx";
        console.log("e2 = ", e2);
        // 输出：e2 =  false

        // 1.3.4 Number 与 Null的比较
        var f1 = 1 > null;
        console.log("f1 = ", f1);
        // 输出：f1 =  true

        var f2 = 0 > null;
        console.log("f2 = ", f2);
        // 输出：f2 =  false

        // 1.3.5 Number 与 Undefined的比较
        var g1 = 1 > undefined;
        console.log("g1 = ", g1);
        // 输出：g1 =  false

        var g2 = 0 > undefined;
        console.log("g2 = ", g2);
        // 输出：g2 =  false

        // --------------------------------------------------------------
        // 2. String类型之间的关系运算
        // 2.1 两个String类型数据比较时，会逐个字符的比较Unicode编码的大小
        var h1 = "abcd" > "bcde";
        console.log("h1 = ", h1);
        // 输出：h1 =  false

        var h2 = "abcd" < "bcde";
        console.log("h2 = ", h2);
        // 输出：h2 =  true

        // 2.2 即使两个字符串都是数字字符串，也不会转换为Number型
        var i1 = "1234" < "5";
        console.log("i1 = ", i1);
        // 输出：i1 =  true
        ```

## 相等运算符
[top](#catalog)
- `==`，判断相等；`!=`，判断不相等
    - 数据类型的自动转换
        - 如果数据类型不同，会自动进行类型转换，转换成相同类型，然后再比较
        - 数据转换的目标类型不确定，但一般会转换为 Number型

- `===`，全等判断；`!==`，不全等判断
    - 和相等/不相等类似，如果两个数据的类型不同，则直接返回 false；如果数据类型相同，再进行比较

- 对于对象类型，比较的是对象的地址；对于基本数据类型，比较的是变量中保存的值

- 特殊的判断
    - <label style="color:red">Undefined衍生自Null</label>，两者的相等与全等判断如下：
        - `Undefined == Null`，返回true
        - `Undefined === Null`，返回false
    - Null自身不会转换成Number，除了Null和Undefined，与其他的类型进行判断都返回 false
    - `console.log(0 == -0)`、`console.log(0 === -0)` 的结果都是 `true`
    - NaN 不和任何值相等，**包括其自身**
        - 由于 NaN 判断的特殊性，js提供了函数：`isNaN()`来判断一个值是否为NaN

- 示例
    - 参考代码
        - [/javascript/base/src/operator/equalOperator.html](/javascript/base/src/operator/equalOperator.html)
    - js内容
        ```js
        // 1. 不同数据类型 的相等判断
        // 1.1 String --> Number
        var a1 = "1" == 1;
        console.log("a1 = ", a1);
        // 输出：a1 =  true

        // 1.2 符行两边的数据同时转换为Number
        var b1 = true == "1";
        console.log("b1 = ", b1);
        // 输出：b1 =  true

        var b2 = true == "asfd";
        console.log("b2 = ", b2);
        // 输出：b2 =  false

        // ------------------------------------------------
        // 2. 全等判断
        var d1 = "1234" === 1234;
        console.log("d1 = ", d1);
        // 输出：d1 =  false

        var d2 = 1234 === 1234;
        console.log("d2 = ", d2);
        // 输出：d2 =  true

        // ------------------------------------------------
        // 3. 特殊的判断
        // 3.1 Undefined与Null的
        // 3.1.1 Undefined与Null的 相等比较
        var e1 = undefined == null;
        console.log("e1 = ", e1);
        // 输出：e1 =  true

        // 3.1.2 Undefined与Null的 全相等比较
        var e2 = undefined == null;
        console.log("e2 = ", e2);
        // 输出：e2 =  false

        // 3.2 Null不会转换成Number
        var c1 = null == 0;
        console.log("c1 = ", c1);
        // 输出：c1 =  false

        var c2 = null == null;
        console.log("c2 = ", c2);
        // 输出：c2 =  true

        var c3 = null === null;
        console.log("c3 = ", c3);
        // 输出：c3 =  true

        // 3.3 NaN 不和任何值相等，包括其自身
        var f1 = NaN == "234";
        console.log("f1 = ", f1);
        // 输出：f1 =  false

        var f2 = NaN === NaN;
        console.log("f2 = ", f2);
        // 输出：f2 =  false

        var f3 = NaN != "234";
        console.log("f3 = ", f3);
        // 输出：f3 =  true

        var f4 = NaN != NaN;
        console.log("f4 = ", f4);
        // 输出：f4 =  true

        // 3.4 0 与 -0 的比较
        console.log("0 == -0: ", 0 == -0)
        // 输出：true
        console.log("0 === -0: ", 0 === -0)
        // 输出：true

        // ------------------------------------------------
        // 4 通过 isNaN 来判断一个值是否为NaN
        var g1 = NaN
        var g2 = isNaN(g1);
        console.log("g2 = ", g2);
        // 输出：g2 =  true

        var g3 = 1234;
        var g4 = isNaN(g4);
        console.log("g4 = ", g4);
        // 输出：g4 =  false
        ```

## 运算符的优先级
[top](#catalog)
- 运算规则：
    - 优先级越高，越优先计算
    - 优先级相同，从左至右执行

- ?????优先级表


# 对象
## 对象的基本概念
[top](#catalog)
- 对象是引用类型
- 对象是一种符合的数据类型，在对象中可以保存多个不同数据类型的属性
- <label style="color:red">所有对象都是Object的后代</label>
- 对象的分类

    |类别|提供者|例子|
    |-|-|-|
    |内建对象|ES标准中定义的对象，在任何的ES实现中都可以使用|如：Math、String、Number、Boolean、Function、Object 等等|
    |宿主对象|由js运行时环境提供的对象，主要指有浏览器提供的对象|如：BOM、DOM|
    |自定义对象|开发者自己定义的对象||

## 对象的基本操作
[top](#catalog)
- 创建对象
    - 创建对象的语法: 创建一个**空的对象**
        ```js
        var obj = new Object();
        ```
    - 使用 `new` 关键字调用的函数，是构造函数 constructor
    - 构造函数是专门用来创建对象的函数
    - 使用 `typeof` 检查一个对象时，返回的结果是 `object`

- 对象属性的操作
    - 操作属性的两种方式
        - `.` 操作符，如：`对象名.属性名`
        - `[]` 操作符，如：`对象名["属性名"]`，`对象名[String型变量]`
            - `[]` 操作符比 `.` 操作符 **更加灵活**
            - 可以通过：`对象名[String型变量]`的方式，动态的操作不同的属性
    - 属性的增删改查操作（以 `.` 操作符说明）
        - 添加属性：`对象名.属性名 = 属性值`
        - 修改属性：`对象名.属性名 = 属性值`
        - 读取属性：`对象名.属性名`
            - 读取属性时，如果属性不存在，会返回 `undefined`
        - 删除属性：`delete 对象名.属性名`

    - 对象属性的属性名**不强制遵守基本语法中的标识符规范**
        - 对于一般的属性名，使用：`对象名.属性名` 的方式即可
        - 对于特殊的属性名，如：数字、js保留字等，需要使用：`对象名["属性名"]` 的方式

- 属性值可以是任意类型的对象

- `in` 运算符
    - 检查对象中是否存在某个属性，包括[原型对象](#原型对象)。存在返回 `true`，不存在返回 `false`

    - 示例：
        ```js
        var obj = new Object();
        obj.name = "testName";
        // 检查属性是否存在
        console.log("name" in obj);
        console.log("xxx" in obj);
        ```

- `对象.hasOwnProperty("属性名")`
    - 只检查对象自身是否包含某个属性
    - 参考：[原型对象](#原型对象)

- 示例
    - 参考代码
        - [/javascript/base/src/object/baseOperator.html](/javascript/base/src/object/baseOperator.html)
    - js内容
        ```js
        // 1. 创建对象
        var obj = new Object();

        // 2. 添加对象属性
        obj.name = "testName";
        obj.age = 12;

        // 3. 读取对象属性
        console.log(obj);       //输出：{name: "testName", age: 12}
        console.log(obj.name);  //输出：testName
        console.log(obj.xxxx);  //输出：undefined

        // 4. 更新对象属性
        obj.name = "newName";
        console.log(obj);       //输出：{name: "newName", age: 12}

        // 5. 删除对象属性
        delete obj.name;
        console.log(obj);       //输出：{age: 12}
        console.log(obj.name);  //输出：undefined

        // 6. 不遵守标识符规范的属性
        // 添加
        obj["1234"] = "asdf";
        console.log(obj);       //输出：{1234: "asdf", age: 12}
        // 读取
        console.log(obj["1234"]);   //输出：asdf

        // 7. 通过String型变量来操作属性
        var param = "age";
        console.log(obj[param]);    //输出：12

        // 8. in 运算符
        console.log("age" in obj);  //输出：true
        console.log("name" in obj); //输出：false

        // 9. hasOwnProperty只检查当前对象中属性是否存在，不检查原型
        function Person(){}
        Person.prototype.age = 16;
        var p = new Person();
        p.name = "test";
        console.log(p.hasOwnProperty("age")); //输出：false
        console.log(p.hasOwnProperty("name")); //输出：true
        ```

## 对象字面量
[top](#catalog)
- 通过 `new Object()` 创建对象比较麻烦，一般都会使用对象字面量来创建对象
- 语法
    - 创建空对象
        ```js
        var 变量名 = {};
        ```
    - 创建对象并设置属性
        - 属性值可以直接设置，也可以用 `""` 包裹
            - **一般不用 `""`，但是一些特殊的名字必须加 `""`**
        
        ```js
        var 变量名 = {
            "属性名1":属性值1,
            "属性名2":属性值2,
            属性名3:属性值3,
            属性名4:属性值4,
            ...
            "属性名n":属性值n
        }
        ```

## 对象的方法
[top](#catalog)
- 可以将函数赋值给一个对象的属性，作为对象的方法
- js中的对象方法和函数没有本质的区别，因为所有函数默认都是全局对象`window`的方法，所以都可以理解为函数，也都可以理解为方法
    - 参考：?????
- 赋值方式
    - 匿名函数
    - 函数名
- 可以给对象的属性单独赋值函数，也可以在创建对象字面量时赋值

- 示例
    - 参考代码
        - [/javascript/base/src/object/objectMethod.html](/javascript/base/src/object/objectMethod.html)

    - js内容
        ```js
                    var a = new Object();

            // 1. 将匿名函数赋值给对象属性
            a.func01 = function(){
                console.log("this is func01");
            }

            a.func01();
            // 输出：this is func01

            // 2. 将函数名赋值给对象属性
            function funcOutter(param){
                console.log("param = ", param);
            }

            a.func02 = funcOutter;
            a.func02("this si func02");
            // 输出：param =  this si func02

            // 3. 通过对象字母量来设置方法
            var b = {
                func01:function(){
                    console.log("this is func01 from obj_bb");
                },

                func02:funcOutter
            };

            b.func01();
            // 输出：this is func01 from obj_bb
            b.func02("this is func02 from obj_b");
            // 输出：param =  this is func02 from obj_b
        ```

## 遍历对象属性
[top](#catalog)
- 通过`for...in`来遍历对象的每个属性
    ```js
    for(var 变量 in 对象){
        循环体;
    }
    ```
- 每一次循环时，会将对象中的一个属性名赋值给变量
- 通过`对象[变量]`的方式来获取属性值

- 示例
    ```js
    var obj = {
        a:"aaa",
        b:"bbbb",
        c:"cccc",
        d:1234,
        e:5678
    }

    // 遍历对象的属性
    for(var key in obj){
        console.log("key =", key, ",value =", obj[key]);
    }

    // 输出：
    // key = a ,value = aaa
    // key = b ,value = bbbb
    // key = c ,value = cccc
    // key = d ,value = 1234
    // key = e ,value = 5678
    ```

## 构造函数
[top](#catalog)
- 通过 `new Object()` 和 对象字面量创建的对象的缺点
    - 型都是 Object，无法很好的区分每个对象是什么

- 什么是构造函数
    - 构造就是一个普通的函数，创建方式和普通函数没有区别
    - 构造函数习惯上**首字母大写**
    - 调用构造函数时使用`new`关键字来调用：`new 构造函数();`
    - 如果直接调用构造函数：`构造函数()`，与调用普通函数相同

- 使用同一个构造函数创建的对象，称为一类对象，也将**构造函数称为类**， 通过构造函数创建的对象称为该类的**实例**

- 构造函数的执行流程
    1. 调用构造函数时，立刻创建一个新的对象
    2. 将新建的对象设置为函数中的this
        - 可以通过 `this` 来设置对象的属性及方法
    3. 执行函数体
    4. 将 `this` 对象返回
        - 如果构造函数中有返回值，将会返回指定的返回值，**而不返回 this 对象**

- 可以通过 `变量 instanceof 类`，检查变量是不是某个类的实例
    - 因为所有对象都是Object的后代，所以任何对象执行：`对象 instanceof Object`，返回值都是true

- 构造函数会出现的问题：函数表达式不能共享对象
    - 问题构造函数分析
        - 有如下的构造函数
            ```js
            function Xxx(){
                this.printToConsole = function(){...};
            }
            ```
        - 构造函数中通过函数表达式来创建方法，但是每次执行构造函数时，都会创建一个新的函数对象，多次调用构造函数时，会创建多个不同的函数对象
        - 实际使用时没有必要每次都创建不同的函数对象，使用通用的函数对象即可
        - 多个函数对象会浪费内存
    - 解决方法
        - 在全局作用域中声明函数，然后在构造函数中引用
            - 这种方法会影响全局作用域的命名空间，很不安全
            - 多人开发时，可能会被同名函数覆盖

- 示例
    - 参考代码
        - [/javascript/base/src/function/constructor.html](/javascript/base/src/function/constructor.html)

    - 测试构造函数的执行流程
        ```js
        // 返回一个指定的值
        function Person01(){
            this.name = "this is p01";
            this.age = 16;
            return {name:"bbbb", age:18};
        }

        // 默认返回 this 对象
        function Person02(){
            this.name = "this is p02";
            this.age = 16;
        }
        
        // 返回指定值后，返回结果的类型与类不相同
        var p01 = new Person01();
        console.log(p01); // {name: "bbbb", age: 18}
        console.log(p01 instanceof Person01); // false

        // 默认返回this对象，类型与类相同
        var p02 = new Person02();
        console.log(p02); // Person02 {name: "this is p02", age: 16}
        console.log(p02 instanceof Person02); // true
        ```

## 原型对象
[top](#catalog)
- 原型对象：`prototype`
- 什么是原型对象
    - 原型对象相当于一个公共的区域，同一个类的所有实例都可以访问到这个原型对象
    - 可以将对象中公有的内容，统一设置到原型对象中

- 原型对象的存储位置
    1. `prototype`
        - 每个函数对象中都会有一个属性：`prototype`，来指向一个原型对象
            - 每一个`prototype`都是独立的
        - 类的实例中有一个隐含属性：`__proto__`，和构造函数指向相同的原型对象
        - `prototype`只对**构造函数(类)**，及**类的实例** 有效。对于普通函数，虽然有该对象，但是没有任何用
    2. `prototype.__proto__`
        - 原型对象`prototype`也是对象，它的原型对象保存在内部的隐藏属性：`__proto__`中

- 原型对象的存储关系
    ```
                            Object.原型对象
                                      ^
                                      │
                                      │
                                     ...
                                     ...
                                     ...
                                 原型对象的原型
                                      ^
                                      │
                                      │
                          原型对象.__proto__
                             ^
                             │
            ┌────────────────┴──────┬───────────────┐
            │                       │               │
    构造函数对象.prototype   对象2.__proto__   对象3.__proto__
    ```
    
- 原型对象的应用
    - 将对象公有的属性和方法，统一添加到构造函数的原型对象中。这样不用为每一个对象分别添加，也不会影响到全局作用域，并且方法对象是共享的不会浪费内存

- 使用对象的属性或方法时，目标的查找方法
    1. 在当前对象自身查找，如果有则直接使用，否则执行2
    2. 在原型对象中查找，如果有则直接使用，否则执行3
    3. 在原型的原型中查找，直到查到Object对象的原型为止，如果有就使用，没有返回 `undefined`


- 注意事项
    - 使用 `in` 检查对象中是否包含某个属性时，如果对象中没有，而原型中有，也会返回true
    - 只检查当前对象中是否包含某个属性，需要使用：`对象.hasOwnProperty("属性名")`

- 示例
    - 参考代码
        - [/javascript/base/src/object/prototype.html](/javascript/base/src/object/prototype.html)

    - js内容
        ```js
        function Student(){
        }

        Student.prototype.age = 16;
        Student.prototype.printAge = function(){
            console.log("printAge = ", this.age);
        }

        // 1. 使用原型中的属性和方法
        var s1 = new Student();
        s1.printAge();          //输出：printAge =  16
        console.log(s1.age);    //输出：16

        // 2. 覆盖原型中的属性
        var s2 = new Student();
        s2.age = 18;
        s2.printAge();          //输出：printAge =  18
        console.log(s2.age);    //输出：18

        // 3. 使用 in 操作符检查属性是否存在
        console.log("age" in s1);   //输出：true


        // 4. hasOwnProperty只检查当前对象中属性是否存在，不检查原型
        s1.name = "s1_name";
        console.log(s1.hasOwnProperty("age")); //输出：false
        console.log(s1.hasOwnProperty("name")); //输出：true


        // 5. 检查 hasOwnProperty的所在位置：在原型的原型中
        console.log(s1.__proto__.hasOwnProperty("hasOwnProperty")); //输出：false
        console.log(s1.__proto__.__proto__.hasOwnProperty("hasOwnProperty")); //输出：true
        ```

## 对象的toString方法
[top](#catalog)
- 默认情况下，在页面中打印一个对象时，实际上是调用了对象的`toString()`方法
    - 如：`console.log(xxx)`，等同于：`console.log(xxx.toString())`
    - 默认打印一个对象会输出：`[object Object]`
- 如果需要定制打印内容，需要为类实现`toString()`方法

- 示例
    - 参考代码
        - [/javascrip/base/src/object/toString.html](/javascrip/base/src/object/toString.html)
    - js内容
        ```js
        function Person(name, age,gender){
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        Person.prototype.toString = function(){
            return "name = " + this.name + ", age = " + String(this.age) +
                    ", gender = " + this.gender;
        }

        var p = new Person("aaa", 16, false);

        // document.write(p);
        console.log(p);
        // 输出：name = aaa, age = 16, gender = false
        ```


# this对象
[top](#catalog)
- 解析器在调用函数时，每次都会向函数内部传递一个隐含的参数 `this`
- `this` 对象也被称为**函数上下文对象**

- `this` 对象一般指向调用方法的那个对象

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
    ```js
    var name = "name01";

    function test(){
        console.log(this.name);
    }

    var obj = {
        func : test,
        name : "name02"
    }

    // 调用对象相当于 widnow
    test();     // 输出：name01

    // 调用对象是obj，所以使用obj中的name属性
    obj.func(); // 输出：name02
    ```


# 函数
## 函数的基本概念
[top](#catalog)
- <label style="color:red">js的函数本身也是一个对象</label>
- 通过函数封装一些功能，来进行代码复用
- 对函数使用 `typeof` 关键字会返回 `function`

## 创建与使用函数的方式
[top](#catalog)
1. 函数对象--通过构造函数
    - 创建 与 使用 
        ```js
        // 创建一个空的函数对象
        var 变量名 = new Function();
        // 创建函数对象是，封装代码
        var 变量名 = new Function("代码字符串");

        // 执行函数
        变量名();
        ```
    - 可以将需要封装的代码以**字符串**的形式传递给构造函数
    - 这种方式在实际开发时，基本不会使用

2. 函数声明
    - 创建 与 使用
        ```js
        // 创建函数
        function 函数名([参数列表]) {
            函数体;
        }

        // 执行函数
        var 结果变量 = 函数名([参数值类表]);
        ```

3. 函数表达式
    - 创建 与 使用
        ```js
        // 创建函数
        var 函数名 = function([参数列表]){
            函数体;
        };

        // 执行函数
        var 结果变量 = 函数名([参数值类表]);
        ```
    - 函数表达式的本质是：**创建一个匿名函数，然后赋值给一个变量**
    - 函数表达式是一个**赋值语句**，不要遗漏最后的 `;`

4. 在函数内部再创建函数
    - 因为函数本质是对象，所以在函数内部创建函数，相当于创建了一个对象
        ```js
        function 函数名([参数列表]){
            // 在内部创建新的函数
            function 函数名2([参数列表]){
                函数体;
            }

            //可以执行内部函数
            函数名2([参数列表]);

            //或者将内部函数作为返回值返回
            return 函数名2;
        }
        ```
    - 对于多层嵌套的函数，在外部可以通过连续的`()`操作符来调用多层函数，如：
        ```js
        function layer01(){
            // 第一层
            function layer02(){
                // 第二层
                function layer03(param){
                    console.log(param);
                }

                return layer03
            }

            return layer02;
        }

        // 通过连续的 () 操作符来调用多层函数
        layer01()()("test msg"); // 输出： test msg
        ```


## 函数的返回值
[top](#catalog)
- 函数的返回值可以是**任意类型的值，包括函数**
- 使用 `return` 关键字来设置函数的返回值
    - `return 返回值;`，指定函数的具体返回值
    - `return ;`，返回 `undefined`
    - <label style="color:red">如果不指定返回值，默认也会返回 `undefined`</label>
- `return` 之后的语句都不会执行
- 通过`var 结果变量 = 函数名([参数值类表]);` 的方式，来接收函数的返回值

- 示例
    - 参考代码
        - [/javascript/base/src/function/return.html](/javascript/base/src/function/return.html)
    - js内容
        ```js
        // 1. 函数没有返回值
        function test01(){
            console.log("this is function");
        }

        var result01 = test01();
        console.log("result01 = ", result01, ", typeof result01 = ", typeof result01);
        // 输出：result01 =  undefined , typeof result01 =  undefined

        // 2. 返回undefined
        function test02(){
            return ;
        }

        var result02 = test02();
        console.log("result02 = ", result02, ", typeof result02 = ", typeof result02);
        // 输出：result02 =  undefined , typeof result02 =  undefined

        // 3. 设置返回值
        function test03(){
            return 10;
        }

        var result03 = test03();
        console.log("result03 = ", result03, ", typeof result03 = ", typeof result03);
        // 输出：result03 =  10 , typeof result03 =  number
        ```

## 函数的参数
[top](#catalog)
- 函数的参数可以是**任意类型的值，包括函数**
- **定义函数参数相当于在函数作用域中声明了变量**
- 有一些情况需要在使用参数之前，对参数的类型进行检查，以防止异常
- 可以传递函数作为参数，并且可以在内部进行调用

## 匿名函数与立即执行函数iife
[top](#catalog)
- js的匿名函数不能单独存在。如果匿名函数单独存在，执行时会引发异常
- 两种解决匿名函数异常的方式
    1. 函数表达式：即创建匿名函数（本质上就是一个匿名对象），然后赋值给一个变量，通过变量进行函数调用，如
        ```js
        var func = function(){...};
        func();
        ```
    2. 通过：`(匿名函数)` 的方式，表示这匿名函数的代码是一个整体
        - 由于运行时环境中只有一个匿名的对象，没有函数名，所以这种函数的调用只能依靠**立即执行函数**

- 立即执行函数 iife
    - 使用场景： 函数只会使用一次
    - 立即执行函数，在（匿名）函数定义完之后，立即被调用

- 示例
    - 参考代码
        - [/javascript/base/src/function/iief.html](/javascript/base/src/function/iief.html)
    - js内容
        ```js
        // 无参的立即执行函数
        (function(){
            console.log("test msg");
        })();

        // 含参的立即执行函数
        (function(a, b){
            console.log("a = ", a);
            console.log("b = ", b);
        })("aaa", "bbb");
        ```

## 函数对象的方法-call和apply
[top](#catalog)
- 函数对象的两个方法：`call`、`apply`，需要通过函数对象来调用
- 通过 `call`、`apply` 重新设置 `this` 对象爱你个
    - 在调用 call 和 apply 可以将一个对象指定为第一个参数，函数会将对象绑定到`this`
    - 如果没有指定参数，则将 window对象绑定到 `this`
- `call`、`apply`的区别
    - 如果函数对象有参数
        - `call` 需要从第二个参数开始依次传递参数
        - `apply` 需要将函数所需的所有参数放在一个数组中作为第二个参数传递给`apply`

- 示例
    - 参考代码
        - /javascript/base/src/function/funcObjMethod.html
    - js内容
        ```js
        // 1. 无参函数
        function test01(){
            console.log("func test01，this =", this);
        }

        // 1.1 空参调用
        test01();
        // 输出：func test01，this = Window 
        test01.call();
        // 输出：func test01，this = Window 
        test01.apply();
        // 输出：func test01，this = Window 

        // 1.2 指定一个对象来调用
        var obj01 = {};
        test01.call(obj01);
        // 输出：func test01，this = {}
        test01.apply(obj01);
        // 输出：func test01，this = {}

        // 2. 有参函数
        function test02(a, b){
            console.log("this.name =", this.name, ", a =", a, ", b=", b);
            
        }

        var obj0201 = {name:"obj0201_name"};
        var obj0201 = {name:"obj0201_name"};
        test02.call(obj0201, 11, 12);
        // 输出：this.name = obj0201_name , a = 11 , b= 12
        test02.apply(obj0201, [11, 12]);
        // 输出：this.name = obj0201_name , a = 11 , b= 12
        ```

## eval执行字符串中的代码
[top](#catalog)
- 使用方法
    - `eval("代码字符串1; 代码字符串2;...")`
- `eval`的功能
    - 用来执行一段字符串形式的JS代码，并返回执行结果
    - 如果字符串中包含 `{}`，该函数会将 `{}` 当成代码块
        - 可以在字符串前后添加 `(`、`)`，将字符串转换为一个整体，来避免这个问题
            ```js
            var a = "{...}";
            eval("(" + a + ")")
            ```
- `eval`的返回值
    - 当eval执行一批语句时，将<label style='color:red'>返回最后执行的、有返回值的那条语句的值</label>
        ```js
        // 只有 1+2有返回值，其他语句没有返回值
        let result = eval('1+2; var a = 5; ; ; ; ; function fn(){};');
        console.log(result); // 3
        ```
- 注意事项
    1. 该函数的性能比较差
    2. 有安全问题
        - 如果从软件外部输入了一个恶意的js代码字符串，可能会导致敏感信息泄露

## arguments
[top](#catalog)
- 调用函数时，js引擎每次都会传递两个隐含的参数
    - 函数的上下文对象 `this`
    - 封装实参的对象 `arguments`
- `arguments` 对象
    - 在调用函数时，传递的**实参**都会保存到 `arguments`
        - 只看实参，与形参无关
        - 即使函数没有定义形参，也可以通过 `arguments` 来获取参数
    - `arguments`是一个类数组对象（ 类型是对象 ）
        - `arguments[index]`，通过索引来操作参数，index 从 0 开始
        - `arguments.length`, 获取参数长度

- `arguments.callee`属性
    - 该属性指向一个函数对象，就是当前正在执行的函数对象

- 示例
    - 参考代码
        - [/javascript/base/src/function/arguments.html](/javascript/base/src/function/arguments.html)
    - js内容
        ```js
        // 1. 输出函数的arguments对象
        function test01(){
            console.log(arguments);
        }

        test01();
        // 输出：Arguments [callee: ƒ, Symbol(Symbol.iterator): ƒ]

        // 2. 测试arguments对象的类型
        function test0201(){
            console.log(Array.isArray(arguments));
        }

        test0201();
        // 输出：false

        function test0202(){
            console.log(arguments instanceof Object);
        }

        test0202();
        // 输出：true


        // 3. 从arguments中获取信息
        function test0301(a, b, c){
            console.log("arguments.length =", arguments.length);
            for(var i = 0; i < arguments.length; i++) {
                console.log("arguments[", arguments[i] ,"] =", arguments[i]);
            }
        }
        test0301('aaaa',true,'zxcv',345);
        // 输出：
        // arguments.length = 4
        // arguments[ aaaa ] = aaaa
        // arguments[ true ] = true
        // arguments[ zxcv ] = zxcv
        // arguments[ 345 ] = 345

        // 4. arguments中的callee属性
        function test0401(){
            console.log(arguments.callee);
        }
        test0401();
        // 输出：
        // ƒ test0401(){
        //     console.log(arguments.callee);
        // }
        ```


# 作用域
## js作用域的基本概念
[top](#catalog)
- 作用域是指：一个变量的作用范围
- js中的两种作用域
    1. 全局作用域
    2. 函数作用域

## 全局作用域与window对象
[top](#catalog)
- 什么是`window`对象？
    - 全局作用域中有一个全局对象 `window`，可以直接使用
    - 可以理解为 `window`对象 代表的就是全局作用域
    - `window`对象 代表的是一个浏览器的窗口，**由浏览器创建**

- `window`对象 的生命周期
    - 页面打开时创建
    - 页面关闭时销毁

- `window`对象中的内容
    - `document`等对象
    - 编写在 `<script>` 标签中的js代码
    - <label style="color:red">在全局作用域中创建的变量，都会作为 window 对象的属性</label>
    - <label style="color:red">在全局作用域中创建的函数，都会作为 window 对象的方法</label>
    - 不使用 `var` 声明的变量
        - 声明变量时，如果不使用 `var 变量` 的方式来创建变量，相当于：`window.变量`，但是这样变量无法被 [提升](#提升)
        ```js
        a = 10;
        // 相当于
        window.a = 10;    
        ```

- `window`对象 中的方法和属性都属于全局作用域，<label style="color:red">在页面任何部分都可以访问，并且可以直接通过名字访问</label>


- 示例
    - 参考代码
        - [/javascript/base/src/scope/global.html](/javascript/base/src/scope/global.html)

    - js内容
        ```js
        var a = 111;
        var b = 123;

        function test01(){
            console.log("this is test01");
        }

        console.log(window.a); //输出：111
        console.log(window.b); //输出：123
        console.log("a" in window); //输出：true
        console.log("b" in window); //输出：true
        console.log(window.test01()); //输出：this is test01
        ```

## 函数作用域
[top](#catalog)
- 生命周期
    - 调用函数时创建
    - 函数执行完后销毁
    - 每次调用函数，都会创建一个新的作用域。新、旧作用域是相互独立的
        ```js
        function test(){...}
        test(); // 创建作用域1
        test(); // 创建作用域2
        test(); // 创建作用域3
        // 3个作用域之间相互独立
        ```
- 在函数作用域可以访问全局作用域的变量
- 在函数作用域中使用变量时的搜索过程
    1. 先在当前函数作用域中查找，如果有就直接使用
    2. 如果当前作用域没有，到上一级作用域中查找，直到全局作用域
    3. 如果全局作用域中也没有找到，则引发异常：`Uncaught ReferenceError: xxxx is not defined`
- 在函数内部，如果需要直接访问全局作用域的变量，可以通过：`window.变量名` 的方式使用

# 内建对象-数组
## 数组的基本知识
[top](#catalog)
- 数组也是一个对象
- 数组与普通对象的异同
    - 和普通对象的功能类似，都是用来保存数据
    - 普通对象使用字符串作为属性名，数组使用数字作为索引来操作元素
    - 数组的性能比对象更好

- 数组的创建方法
    1. 使用构造函数创建数组
        - 创建空数组
            ```js
            var a = new Array();
            ...

        - 创建指定长度的数组
            ```js
            var a = new Array(数组长度);
            ```
        - 创建数组，同时添加元素
            - **这种方法必须要有2个及以上的参数，才能正常创建。如果只有一个参数，该参数会被当作数组长度使用**
            ```js
            var a = new Array(1, 2, 3, 4);
            ```

    2. 使用数组字面量
        ```js
        // 创建空数组
        var a = [];

        // 创建时指定数组元素
        var a = [1, 2, 3, 4, 5];
        ```

- 数组的index
    - 数组通过:`变量[index]` 的方式来设值和取值，`index`从 0 开始。如果index不存在，不会报错，会返回 `undefined`
    - js数组在设值时，**index可以不连续**

- `数组.length` 属性
    - 该属性会返回数组的长度，即`最大index + 1`（无论数组是否连续）
    - 该属性可以手动修改
        - 如果修改后的length大于原长度，会自动对数组扩容
        - 如果修改后的length小于原长度，数组中多余的元素会被删除
    - 为了防止数组不连续，**可以通过length属性来保证每次都在数组的最后添加元素** : `数组[数组.length] = 元素值`

- 数组的元素
    - 数组的元素可以是任意类型，包含对象和函数


## 数组的常用方法
[top](#catalog)
- 尾部元素操作
    - `push()`，向数组的末尾添加一个或多个元素，并返回数组的长度
    - `pop()`，删除数组**最后一个**元素，并返回该元素
- 头部元素操作
    - `unshift()`，向数组开头添加一个或多个元素，并返回数组的长度
    - `shift()`，删除数组的**第一个**元素，并返回该元素

- `slice(start_index, [end_index])`，切片操作
    - 该方法不会改变原数组，会返回一个新的数组
    - 参数说明
        - start_index 是开始位置的索引
        - end_index 是结束位置的索引
            - end_index可以不指定。不指定时，获取从start开始到数组末尾的所有元素
        - start_index、end_index可以指定负数。index相当于：`index = 数组.length + index`
    - 截取范围
        - 截取元素的范围：`[start_index, end_index)`
        - 如果 `start_index >= end_index`，则会返回一个空数组：`[]`

- `splice(start_index, count [, 需要添加的元素...])`，删除元素并返回、添加元素
    - 该方法会直接修改原始数组
    - 参数说明
        - start_index 是开始位置的索引
        - count 是删除元素的个数
            - 如果 `count = 0`，则不会删除任何元素，并返回空数组：`[]`

        - `需要添加的元素...`
            - 如果发生了元素删除，会从原始数组的`start_index`处添加元素
            - 如果没有发生元素删除，会从原始数组的`start_index`处添加元素，原来的元素会自动后移
            - 添加元素时，原始数组会**自动进行扩容和元素移动**

- `concate(数组1[,数组2....])`，连接两个或更多的数组，并返回一个新的数组
    - 该方法不会修改多个原始数组

- `join(["连接符"])`，用指定连接符将数组中的所有元素连接成一个字符串
    - 参数说明
        - 使用指定的连接符连接每个元素
        - 如果不指定连接符，则默认使用 `,` 作为连接符
    - 底层会调用元素的`toString()`方法
        - 对于Object类型的数组元素，如果需要正常输出，类/对象需要实现`toString()`方法

- `reverse()`，反转数组
    - 该方法会直接修改原始数组

- `sort([比较函数])`，数组排序
    - 该方法会直接修改原始数组
    - 默认会按照Unicode进行排序
    - 默认是升序排序
    - 参数说明
        - `比较函数`
            - 通过设置比较函数来定制排序的方式
            - 比较函数需要有两个形参，表示两个参与比较的数组元素
            - 定制排序的规则
                - 比较函数的返回值 大于0时，表示两个元素需要交换位置
                - 比较函数的返回值 小于0时，表示两个元素位置不变
                - 比较函数的返回值 等于0时，表示两个元素位置不变
            - 示例
                - 升序排列
                    ```js
                    数组.sort(function(a, b){
                        return a-b;
                    });
                    ```
                - 降序排列
                    ```js
                    数组.sort(function(a, b){
                        return b-a;
                    });
                    ```

- `Array.isArray(参数)`，判断参数的类型是否是数组

- 示例
    - 参考代码
        - [/javascript/base/src/array/arrayMethod.html](/javascript/base/src/array/arrayMethod.html)

    - 尾部元素操作
        ```js
        var a1 = [1,2,3];

        // 1.1. push
        var a1_length01 = a1.push("abc", "def", true);
        console.log("a1 = ", a1);
        // 输出：a1 =  (6) [1, 2, 3, "abc", "def", true]
        console.log("a1_length01 = ", a1_length01);
        // 输出：a1_length01 =  6

        // 1.2. pop
        var node = a1.pop();
        console.log("a1 = ", a1);
        // 输出：a1 =  (5) [1, 2, 3, "abc", "def"]
        console.log("node = ", node);
        // 输出：node =  true
        ```

    - 头部元素操作
        ```js
        var a2 = [1,2,3];
        // 2.1. unshift()
        var a2_length02 = a2.unshift("adsdg", 345);
        console.log("a2 = ", a2);
        // 输出：a2 =  (5) ["adsdg", 345, 1, 2, 3]
        console.log("a2_length02 = ", a2_length02);
        // 输出：a2_length02 =  5

        // 2.2. shift()
        var node02 = a2.shift();
        console.log("a2 = ", a2);
        // 输出：a2 =  (4) [345, 1, 2, 3]
        console.log("node02 = ", node02);
        // 输出：node02 =  adsdg
        ```
    - slice 切片操作
        ```js
        var a3 = ['a','b','c','d','e','f'];

        // 3.1 不指定end
        var slice0301 = a3.slice(1);
        console.log("slice0301 = ", slice0301);
        // 输出：slice0301 =  (5) ["b", "c", "d", "e", "f"]

        // 3.2 end是正数
        var slice0302 = a3.slice(1,3);
        console.log("slice0302 = ", slice0302);
        // 输出：slice0302 =  (2) ["b", "c"]

        // 3.3 start 小于 end
        var slice0303 = a3.slice(3, 1);
        console.log("slice0303 = ", slice0303);
        // 输出：slice0303 =  []

        // 3.4 start 等于 end
        var slice0304 = a3.slice(1, 1);
        console.log("slice0304 = ", slice0304);
        // 输出：slice0304 =  []

        // 3.5 end是负数
        var slice0305 = a3.slice(1,-1);
        console.log("slice0305 = ", slice0305);
        // 输出：slice0305 =  (4) ["b", "c", "d", "e"]

        // 3.6 end是负数，并且在start的前面
        var slice0306 = a3.slice(3, -5);
        console.log("slice0306 = ", slice0306);
        // 输出：slice0306 =  []

        console.log("a3 = ", a3);
        // 输出：a3 =  (6) ["a", "b", "c", "d", "e", "f"]
        ```
    - splice
        ```js
        var a4 = ['a','b','c','d','e','f'];

        // 4.1 不删除元素
        var deleted_nodes01 = a4.splice(2,0);
        console.log("deleted_nodes01 = ", deleted_nodes01);
        // 输出：deleted_nodes01 =  []

        // 4.2 删除多个元素
        var deleted_nodes02 = a4.splice(2,3);
        console.log("deleted_nodes02 = ", deleted_nodes02);
        // 输出：deleted_nodes02 =  (3) ["c", "d", "e"]
        console.log("a4 = ", a4);
        // 输出：a4 =  ["a", "b", "f"]

        // 4.3 删除并添加多个元素
        var a402 = ['a','b','c','d','e','f'];
        var deleted_nodes03 = a402.splice(2, 3, "xcvcbv", "rty", 567);
        console.log("deleted_nodes03 = ", deleted_nodes03);
        // 输出：deleted_nodes03 =  (3) ["c", "d", "e"]
        console.log("a402 = ", a402);
        // 输出：a402 =  (6) ["a", "b", "xcvcbv", "rty", 567, "f"]

        // 4.4. 不删除元素，只添加元素
        var a403 = ['a','b','c','d','e','f'];
        var deleted_nodes04 = a403.splice(2, 0, "zzzzz", "yyyyy");
        console.log("deleted_nodes04 = ", deleted_nodes04);
        // 输出：deleted_nodes04 =  []
        console.log("a403 = ", a403);
        // 输出：a403 =  (8) ["a", "b", "zzzzz", "yyyyy", "c", "d", "e", "f"]
        ```
    
    - concate连接两个或更多的数组，并返回一个新的数组
        ```js
        var a0501 = [1,2,3,4,5];
        var a0502 = [6,7,89];
        var a0503 = ["sdf","xv"];
        var a0504 = a0501.concat(a0502, a0503);
        console.log("a0501 = ", a0501);
        // 输出：a0501 =  (5) [1, 2, 3, 4, 5]
        console.log("a0502 = ", a0502);
        // 输出：a0501 =  (5) [1, 2, 3, 4, 5]
        console.log("a0503 = ", a0503);
        // 输出：a0501 =  (5) [1, 2, 3, 4, 5]
        console.log("a0504 = ", a0504);
        // 输出：a0504 =  (10) [1, 2, 3, 4, 5, 6, 7, 89, "sdf", "xv"]

        // 6. join 将数组中的所有元素连接成一个字符串
        // 6.1. 连接基本数据类型，指定连接符
        var a0601 = [1, true, "aaaa", 65];
        var a0601_str = a0601.join("--");
        console.log("a0601_str = ", a0601_str);
        // 输出：a0601_str =  1--true--aaaa--65

        // 6.2. 连接基本数据类型，不指定连接符
        var a0602 = [1, true, "aaaa", 65];
        var a0602_str = a0602.join();
        console.log("a0602_str = ", a0602_str);
        // 输出：a0602_str =  1,true,aaaa,65

        // 6.3. 连接Object类型，类没有toString方法
        function Persion01(name, age){
            this.name = name;
            this.age = age;
        }

        var p0601 = new Persion01("aaa",10);
        var p0602 = new Persion01("aaa",11);
        var p0603 = new Persion01("aaa",12);

        var a0603 = [p0601, p0602, p0603];
        var a0603_str = a0603.join();
        console.log("a0603_str = ", a0603_str);
        // 输出：a0603_str =  [object Object],[object Object],[object Object]

        // 6.4. 连接Object类型，类有toString方法
        function Persion02(name, age){
            this.name = name;
            this.age = age;
        }

        Persion02.prototype.toString = function(){
            return 'Persion02[ name = ' + this.name + ', age = ' + this.age + ']';
        }

        var p0604 = new Persion02("zxcv",11);
        var p0605 = new Persion02("qwer",22);
        var p0606 = new Persion02("yhnm",33);

        var a0604 = [p0604, p0605, p0606];
        var a0604_str = a0604.join();
        console.log("a0604_str = ", a0604_str);
        // 输出：a0604_str =  Persion02[ name = zxcv, age = 11],Persion02[ name = qwer, age = 22],Persion02[ name = yhnm, age = 33]
        ```
    - reverse 反转数组
        ```js
        var a0701 = [1,2,3,4,5,6,7];
        a0701.reverse();
        console.log("a0701 = ", a0701);
        // 输出：a0701 =  (7) [7, 6, 5, 4, 3, 2, 1]
        ```
    - sort 对数组进行排序
        ```js
        // 8.1. 默认排序
        var a0801 = [11,1,4,3,5,2,5,3];
        a0801.sort();
        console.log("a0801 = ", a0801);
        // 输出：a0801 =  (8) [1, 11, 2, 3, 3, 4, 5, 5]

        // 8.2 定制排序:升序排序
        var a0802 = [11,1,4,3,5,2,5,3];
        a0802.sort(function(a, b){
        //     if (a > b){
        //         return 1;
        //     }else {
        //         return -1;
        //     }
            return a-b;
        });
        console.log("a0802 = ", a0802);
        // 输出：a0802 =  (8) [1, 2, 3, 3, 4, 5, 5, 11]

        // 8.2 定制排序:降序排序
        var a0803 = [11,1,4,3,5,2,5,3];
        a0803.sort(function(a, b){
        //     if (a > b){
        //         return -1;
        //     }else if (a < b) {
        //         return 1;
        //     }else {
        //         return 0;
        //     }
            return b-a;
        });
        console.log("a0803 = ", a0803);
        // 输出：a0803 =  (8) [11, 5, 5, 4, 3, 3, 2, 1]
        ```

## 数组去重
[top](#catalog)
```js
var a = [1, 2, 3, 2, 2, 1, 3, 4, 2, 5];
console.log("a = ", a);
for (var i = 0; i<a.length - 1; i++){
    var temp = a[i];
    for(var j=i+1; j<a.length; j++){
        if (temp == a[j]){
            a.splice(j,1);
            
            // j 减1，防止删除后向前移动的一位没有被检查
            j--;
        }
    }
}

console.log("a = ", a);
```

## 数组的遍历
[top](#catalog)
- for遍历
    ```js
    var a = [1,2,3,4,5];
    for(var i = 0; i<a.length; i++){
        a[i];
        ...
    }
    ```

# 包装类
[top](#catalog)
- **包装类自身有一些问题，导致其行为与基本数据类型不一致，一般开发中尽量不要使用包装类**
- js中提供3个包装类，来将基本数据类型转换为对象
    - `String()`
    - `Number()`
    - `Boolean()`

- 包装类的问题
    - 包装类对象，在进行比较时，比较的是对象的地址，可能会产生预期外的结果
    - 对于`var obj= new Boolean(false)`，虽然对象内部的值是false，但是本身是包装类对象，所以进行 `if (obj){...}` 的判断时，对象自动转换为true，导致返回值永远都是 true

- js内部使用包装类进行自动的类型转换
    - 当通过基本数据类型调用方法或属性时，js引擎会自动将基本数据类型转换为对应的包装类对象，并调用对象的方法或属性，调用完后，再还原为基本类型
- 示例
    - 参考代码
        - [/javascript/base/src/wrapperClass/base.html](#/javascript/base/src/wrapperClass/base.html)
    - js内容
        ```js
        // 1. 创建包装类对象
        var a1 = new String("abcd");
        var a2 = new Number(1234);
        var a3 = new Boolean(true);
        console.log("a1 =", a1);
        // 输出：a1 = String {"abcd"}
        console.log("a2 =", a2);
        // 输出：a2 = Number {1234}
        console.log("a3 =", a3);
        // 输出：a3 = Boolean {true}

        // 2. 包装类对象的比较
        var b0101 = new String('abcd');
        var b0102 = new String('abcd');
        console.log("b0101==b0102 : ", b0101==b0102);
        // 输出：b0101==b0102 :  false

        var b0103 = new Number(1234);
        var b0104 = new Number(1234);
        console.log("b0103==b0104 : ", b0103==b0104);
        // 输出：b0103==b0104 :  false

        var b0105 = new Boolean(false);
        if (b0105){
            console.log("aaaaa"); 
        }else {
            console.log("bbbbb");
        }
        // 输出：aaaaa

        // 3. 自动类型转换
        var c0101 = "1234";
        var c0102 = c0101.toString();
        console.log("c0102 = ", c0102);
        // 输出：c0102 =  1234
        console.log("typeof c0102 = ", typeof c0102);
        // 输出：typeof c0102 =  string
        ```


# 内建对象-Date
[top](#catalog)
- js中使用Date对象表示一个时间
- 创建时间对象的方式
    1. 空参构造函数。时间为创建对象的时间
        ```js
        var a = new Date();
        ```
    2. 时间字符串
        - 字符串格式：`MM/dd/yyyy HH:mm:ss`
            ```js
            var b = new Date("11/22/2008 12:34:56");
            ```
- Date 对象的常用方法
    - `getDate()`，返回几号，即`dd`
    - `getDay()`，返回周几，返回值: 0 ～ 6，0表示周日
    - `getMonth()`，返回月份，返回值：0 ～ 11
    - `getFullYear()`，返回年份
    - `getTime()`，获取当前时间对象的时间戳
    - `Date.now()`，获取执行时的时间戳
- 时间戳
    - 从格林威治标准时间：1970/01/01 00:00:00到当前日期所花费的**毫秒数**

- 示例
    - 参考代码
        - [/javascript/base/src/innerObject/date/date.html](#/javascript/base/src/innerObject/date/date.html)
    - js内容
        ```js
                // 1. 创建Date对象
        var a01 = new Date();
        console.log(a01);

        var a02 = new Date("11/22/2008 12:34:56");
        console.log(a02);
        // 输出：Sat Nov 22 2008 12:34:56 GMT+0800

        // 2. Date对象的方法
        var b01 = new Date("11/22/2008 12:34:56");

        // 2.1 获取几号
        console.log("b01.getDate() =", b01.getDate());
        // 输出：b01.getDate() = 22

        // 2.2 周几
        console.log("b01.getDay() =", b01.getDay());
        // 输出：b01.getDay() = 6

        //2.3 月份
        console.log("b01.getMonth() =", b01.getMonth());
        // 输出：b01.getMonth() = 10

        // 2.4 年份
        console.log("b01.getFullYear() =", b01.getFullYear());
        // 输出：b01.getFullYear() = 2008

        // 2.5获取当前时间的时间戳
        console.log(Date.now());
        ```

# 内建对象-Math
## Math对象简介
[top](#catalog)
- Math不是一个构造函数，它是一个工具类
- Math中分装了与数学运算相关的属性和方法

## Math中的方法
[top](#catalog)
- 常用方法

    |方法|功能|备注|
    |-|-|-|
    |`ceil(x)`|向上取整||
    |`floor(x)`|向下取整|`parseInt`也有相同的功能|
    |`round(x)`|四舍五入||
    |`random()`|返回 `[0, 1)` 区间的一个随机数||
    |`abs(x)`|取x的绝对值||
    |`max(x,y)`|返回 x 和 y 中的最大值||
    |`min(x,y)`|返回 x 和 y 中的最小值||

- 数学计算

    - 幂运算、对数运算
        |方法|功能|
        |-|-|
        |`pow(x,y)`|求 `x^y`|
        |`sqrt(x)`|求x的平方根|
        |`exp(x)`|求`e^x`|
        |`log(x)`|求x的自然对数，即:`log_e_X`|

    - 三角函数
        |方法|功能|
        |-|-|
        |`sin(x)`|x的正弦|
        |`cos(x)`|x的余弦|
        |`tan(x)`|x的正切|
        |`acos(x)`|	x的反余弦值|
        |`asin(x)`|	x的反正弦值|
        |`atan(x)`|	以介于 -PI/2 与 PI/2 弧度之间的数值来返回 x 的反正切值|
        |`atan2(y,x)`|	返回从 x 轴到点 (x,y) 的角度（介于 -PI/2 与 PI/2 弧度之间）|

- 示例
    - 参考代码
        - [src/innerObject/math/method.html](src/innerObject/math/method.html)
    - 代码内容
        ```js
        // 常用方法
        // 1. Math.ceil(x) 向上取整
        let a01 = Math.ceil(2.5);
        console.log('a01 = ' + a01);    // 输出: a01 = 3
        let a02 = Math.ceil(2.4);
        console.log('a02 = ' + a02);    // 输出: a02 = 3

        // 2. Math.floor(x) 向下取整
        let b01 = Math.floor(2.5);
        console.log('b01 = ' + b01);    // 输出: b01 = 2
        let b02 = Math.floor(2.4);
        console.log('b02 = ' + b02);    // 输出: b02 = 2

        // 3. Math.round(x) 四舍五入
        let c01 = Math.round(2.5);
        console.log('c01 = ' + c01);    // 输出: c01 = 3
        let c02 = Math.round(2.4);
        console.log('c02 = ' + c02);    // 输出: c02 = 2

        // 4 Math.random() 返回 [0, 1) 区间的一个随机数
        let d01 = Math.random();
        console.log('d01 = ' + d01);

        // 5. Math.abs(x) 返回x的绝对值
        let e01 = Math.abs(2);
        console.log('e01 = ' + e01);    // 输出: e01 = 2
        let e02 = Math.abs(0);
        console.log('e02 = ' + e02);    // 输出: e02 = 0
        let e03 = Math.abs(-3);
        console.log('e03 = ' + e03);    // 输出: e03 = 3

        // 6. Math.max(x,y) 返回 x 和 y 中的最大值
        let f01 = Math.max(100, 20);
        console.log('f01 = ' + f01);    // 输出: f01 = 100

        // 7. Math.min(x,y) 返回 x 和 y 中的最小值
        let g01 = Math.min(100, 20);
        console.log('g01 = ' + g01);    // 输出: g01 = 20
        ```

## Math随机函数的实际应用
[top](#catalog)
- 参考：https://www.cnblogs.com/starof/p/4988516.html

- 随机函数的基本应用方法
    - 应用方法

        |应用方法|功能|备注|
        |-|-|-|
        |`Math.ceil(Math.random()*10)`|均衡获取 `[1, 10]` 间的随机整数|<ul><li>因为使用了向下取整，所以不会取10</li><li>有取0的可能，但是概率极小</li></ul>|
        |`Math.round(Math.random())`|均衡获取 `[0, 1]` 间的随机整数||
        |`Math.floor(Math.random()*10)`<br>`parseInt(Math.random()*10, 10)`|均衡获取 `[0, 9]` 间的随机整数|<ul><li>因为使用了向下取整，所以不会取10</li> <li>每个`[i, i+1)`区间的概率相等所以是均衡的</li></ul>|
        |`Math.round(Math.random()*10)`|基本均衡获取 `[0, 10]`间的随机整数|获取 `0`、`10` 的几率比其他整数少一半|

    - `Math.round(Math.random()*10)` 中，`0`、`10` 的几率比其他整数少一半的原因
        - 数字的分布区间
            - `0: [0,0.5)`
            - `10: [9.5, 10)`
            - 其他数字，如 `1: [0.5, 1.5]`
        - 0、10 的分布区间只有其他数字的一般，所以几率会少一半

    - 获取随机数时，`Math.floor()` 更有优势
        1. 比`Math.round`更加均衡，每个整数的概率都相同
        2. 比`Math.ceil`更容易处理上下区间

- 应用：获取区间中的随机数
    - 获取 `[min, max]` 区间的随机整数
        - 获取方式
            - `Math.floor(Math.random() * (max - min + 1) + min)`
            - `parseInt(Math.random() * (max - min + 1) + min)`
        - 计算步骤
            1. `Math.random()`，获取 `[0, 1)` 的随机数
            2. `Math.random() * (max - min + 1)`，计算在随机数 `[min, max]` 区间上的一个偏移量
            3. `Math.random() * (max - min + 1) + min`，从最小值开始，移动随机偏移量的长度，即获得区间内的随机数
            4. 通过向下取整:`Math.floor()`，将区间固定在 `[min, max]`
    - 获取 `[0, max]` 区间的随机整数
        - `Math.floor(Math.random() * (max+1))`
            - 相当于: `Math.floor(Math.random() * (max - 0 + 1) + 0)`
    - 获取 `[1, max]` 区间的随机整数
        - `Math.floor(Math.random() * max + 1)`
            - 相当于: `Math.floor(Math.random() * (max - 1 + 1) + 1)`
        - `Math.ceil(Math.random() * max)`
            - `Math.ceil()` 会将默认的左区间 `0` 去掉，自动变成`1`，所以不需哟啊特殊的处理

- 应用示例
    - 获取指定范围的随机数
        - 参考代码
            - [src/innerObject/math/random.html](src/innerObject/math/random.html)
        - 代码内容
            ```js
            function rangeRandom(min, max){
                // 如果最大最小值都省略了，则返回0
                if (!min && !max){
                    return 0;
                }
                // 如果省略了max，则返回 [0, min] 区间的随机数
                if (!max){
                    max = min;
                    min = 0;
                }
                return Math.floor(Math.random() * (max - min + 1) + min);
            }

            console.log( "[,] = " + rangeRandom() );
            console.log( "[0, 5] = " + rangeRandom(5) );
            console.log( "[6, 20] = " + rangeRandom(6, 20) );
            ```


# 内建对象-正则表达式
[top](#catalog)
- 创建正则表达式对象
    - 使用构造函数
        - 创建方式
            ```js
            var reg = new RegExp("正则表达式" [, "匹配模式"]);
            ```
        - <label style="color:red">使用正则表达式元字符时的注意事项</label>
            - 当使用：`\s`,`\w`,`\b`等元字符时，需要将`\`转义为：`\\`
            - 示例
                ```js
                // 匹配字符串，并区分单词的开头与结束
                var reg = new RegExp("\\b" + xx + "\\b");
                ```
    - 使用字面量。字面量两边没有引号
        - 使用方法
            ```js
            // 使用匹配模式
            var reg = /正则表达式/匹配模式;
            // 不使用匹配模式
            var reg = /正则表达式/;
            ```
        - 在这种方式下，可以直接使用正则表达式的元字符，不需要转义

- 匹配模式
    - `i`，忽略大小写
    - `g`，全局匹配模式

- 正则表达式对象的常用方法
    - `test("被检查的字符串")`，检查字符串是否符合正则表达式的规则

- 示例
    - 参考代码
        - [/javascript/base/src/innerObject/regEpx/regEpx.html](/javascript/base/src/innerObject/regEpx/regEpx.html)
    - js内容
        ```js
        // 1. 使用构造函数创建
        // 1.1 不使用匹配模式
        var reg0101 = new RegExp('ab');
        var result010101 = reg0101.test("qweAbzxc");
        var result010102 = reg0101.test("qweabzxc");
        console.log("result010101 =", result010101);
        // 输出：result010101 = false
        console.log("result010102 =", result010102);
        // 输出：result010102 = true

        // 1.2. 使用匹配模式：i，忽略大小写
        var reg0102 = new RegExp('ab', 'i');
        var result010201 = reg0102.test("qweAbzxc");
        var result010202 = reg0102.test("qweabzxc");
        console.log("result010201 =", result010201);
        // 输出：result010201 = true
        console.log("result010202 =", result010202);
        // 输出：result010202 = true

        // 2. 使用字面量创建
        var reg0201 = /a/i;
        var result020101 = reg0201.test("qweAbzxc");
        console.log("result020101 =", result020101);
        // 输出：result020101 = true
        ```


# 内建对象-String
## String的基本原理和基本方法
[top](#catalog)
- **String对象本身属于包装类**
- 字符串底层是以字符数组的形式保存的，所以可以通过索引来获取字符：`字符串[index]`、通过`length`属性来获取字符串的长度

- String对象属性
    - `length`，获取字符串的长度
- String对象的基本方法
    - 字符操作
        - `charAt(index)`，通过索引获取字符，与`字符串[index]`的方式相同
        - `charCodeAt(index)`，通过索引获取字符的Unicode值
        - `String.fromCharCode(2/8/10/16进制数字)`，通过字符编码创建字符
    - 字符串连接
        - `concate(...)`，连接字符串，与 `+` 运算符的操作相同
    - 字符串搜索
        - `indexOf("目标字符串" [, start_index])`，从指定位置开始，检索目标字符串第一次出现的位置
            - 从左向右搜索
            - 如果不设置 start_index， 则默认为0
            - 如果目标字符串存在，则返回第一次出现的索引
            - 如果目标字符串不存在，则返回 **-1**
        - `lastIndexOf("目标字符串" [, start_index])`
            - 与 `indexOf` 的用法基本相同
            - 从右向左搜索

    - 字符串截取
        - `slice(start_index [, end_index])`
            - 截取指定范围的字符串，并返回新的字符串
            - 该方法不会改变原字符串，会返回一个新的字符串
            - 参数说明
                - start_index 是开始位置的索引
                - end_index 是结束位置的索引
                    - end_index可以不指定。不指定时，获取从start开始到数组末尾的所有元素
                - start_index、end_index可以指定负数。index相当于：`index = 数组.length + index`
            - 截取范围
                - 截取元素的范围：`[start_index, end_index)`
                - 如果 `start_index >= end_index`，则会返回一个空字符串：`""`

        - `substring(start_index [, end_index])`
            - 截取指定范围的字符串，并返回新的字符串
            - 该方法不会改变原字符串，会返回一个新的字符串
            - 参数说明
                - start_index ，开始位置的索引
                - end_index ，结束位置的索引
                    - 如果不指定，截取从start开始到字符串末尾的所有元素
            - 截取范围
                - 截取元素的范围：`[start_index, end_index)`
                - 如果 `start_index = end_index`，则会返回一个空字符串：`""`
                - 如果 `start_index > end_index`，方法会自动调整参数的大小关系，转换为 `[end_index, start_index)`
            - 使用方法与 `slice` 类似
            - 与 `slice` 的不同点
                1. 不接受负数作为参数。如果有负数参数，会自动转换为0
                2. 如果 `start_index > end_index` , 方法会自动调整参数的顺序

        - `substr(start_index [, char_count])`
            - <label style="color:red">ECMAScript并没有对该方法进行标准化定义，所以尽量少用</label>
            - 从start_index开始截取指定数量的字符，并返回新的字符串
            - 参数说明
                - start_index ，开始位置的索引
                - char_count ，截取字符的个数
                    - 如果不指定，截取从start开始到字符串末尾的所有元素
    - 大小写转换
        - `toUpperCase()`，大写转换
        - `toLowerCase()`，小写转换

- 示例
    - 参考代码
        - [/javascript/base/src/innerObject/string/base.html](/javascript/base/src/innerObject/string/base.html)
    - js内容
        ```js
        // 1. 字符串对象的属性
        // 1.1 获取字符串的长度
        var a0101 = "abcdefg";
        console.log("a0101.length =", a0101.length);
        // 输出：a0101.length = 7

        // 2. 字符串对象的方法
        // 2.1 通过索引获取字符--数组方式
        var b0101 = "abcdefg";
        console.log("b0101[2] =", b0101[2]);
        // 输出：b0101[2] = c

        // 2.2 通过索引获取字符
        var b0201 = "abcdefg";
        console.log("b0201.charAt(3) = ", b0201.charAt(3));
        // 输出：b0201.charAt(3) =  d

        // 2.3 通过索引获取字符的Unicode值
        var b0301 = "abcdefg";
        console.log("b0301.charCodeAt(0) =", b0301.charCodeAt(0));
        // 输出：b0301.charCodeAt(0) = 97

        // 2.4 通过字符编码创建字符
        var b0401 = String.fromCharCode(97);
        console.log("b0401 =", b0401);
        // 输出：b0401 = a
        var b0105 = String.fromCharCode(0x1112);
        console.log("b0105 =", b0105);

        // 2.5 连接字符串
        var b0501 = "qwer";
        var b0502 = b0501.concat(1234, "vcbcn", true);
        console.log("b0501 =", b0501);
        // 输出：b0501 = qwer
        console.log("b0502 =", b0502);
        // 输出：b0502 = qwer1234vcbcntrue

        // 2.6 搜索字符串
        // 未搜索到指定字符串
        var b0601 = "asff";
        var result0601 = b0601.indexOf("cd");
        console.log("result0601 =", result0601);
        // 输出：result0601 = -1

        // 从左向右，不指定开始位置
        var b0602 = "abcdefg";
        var result0602 = b0602.indexOf("cd");
        console.log("result0602 =", result0602);
        // 输出：result0602 = 2

        // 从左向右，指定开始位置
        var b0603 = "abcdefg sdfcdwqw";
        var result0603 = b0603.indexOf("cd", 7);
        console.log("result0603 =", result0603);
        // 输出：result0603 = 11

        // 从右向左，不指定开始位置
        var b0604 = "abcdefg sdfcdwqw";
        var result0604 = b0604.lastIndexOf("cd");
        console.log("result0604 =", result0604);
        // 输出：result0604 = 11

        // 从右向左，指定开始位置
        var b0605 = "abcdefg sdfcdwqw";
        var result0605 = b0605.lastIndexOf("cd", 10);
        console.log("result0605 =", result0605);
        // 输出：result0605 = 2

        // 2.7 字符串截取
        // 2.7.1 slice 截取字符串: start_index ~ 末尾
        var b0701 = "abcdefghijk";
        var result0701 = b0701.slice(2);
        console.log("result0701 =", result0701);
        // 输出：result0701 = cdefghijk

        // 2.7.2 slice 截取字符串: start_index < end_index
        var b0702 = "abcdefghijk";
        var result0702 = b0702.slice(2,6);
        console.log("result0702 =", result0702);
        // 输出：result0702 = cdef

        // 2.7.3 slice 截取字符串: start_index >= end_index
        var b0703 = "abcdefghijk";
        var result0703 = b0703.slice(6,2);
        console.log("result0703 =", result0703);
        // 输出：result0703 = 

        // 2.7.4 slice 截取字符串: end_index 指定为负数
        var b0704 = "abcdefghijk";
        var result0704 = b0704.slice(2, -3);  //(2, 8)
        console.log("result0704 =", result0704);
        // 输出：result0704 = cdefgh

        // 2.7.5 slice 截取字符串: start_index, end_index 指定为负数
        var b0705 = "abcdefghijk";
        var result0705 = b0705.slice(-5, -3);  //(6, 8)
        console.log("result0705 =", result0705);
        // 输出：result0705 = gh

        // 2.7.6 substring 截取字符串：start_index < 0
        // start_index 将会被调整为 0 
        var b0706 = "abcdefghijk";
        var result0706 = b0706.substring(-5, 8);
        console.log("result0706 =", result0706);
        // 输出：result0706 = abcdefgh

        // 2.7.7 substring 截取字符串：start_index > end_index
        var b0707 = "abcdefghijk";
        var result0707 = b0707.substring(5, 2); // (2, 5)
        console.log("result0707 =", result0707);
        // 输出：result0707 = cde

        // 2.7.8 substr 截取字符串
        var b0708 = "abcdefghijk";
        var result0708 = b0708.substr(5, 2);
        console.log("result0708 =", result0708);
        // 输出：result0708 = fg

        // 2.8 大小写转换
        var b0801 = "abcDeFghiJ";
        var resutl0801 = b0801.toUpperCase();
        console.log("resutl0801 =", resutl0801);
        // 输出：resutl0801 = ABCDEFGHIJ

        var b0802 = "abcDeFghiJ";
        var resutl0802 = b0802.toLowerCase();
        console.log("resutl0802 =", resutl0802);
        // 输出：resutl0802 = abcdefghij
        ```

## String与正则表达式相关的方法
[top](#catalog)
- `新字符串 = 字符串.split("分割字符串"/正则表达式)`
    - 用途：根据分割字符串或正则表达式，将字符串拆分为数组
- `新字符串 = 字符串.search(字符串/正则表达式)`
    - 用途：搜索字符串，或匹配正则表达式，类似于`indexOf`
    - 如果搜索到了指定内容，则返回第一次出现的索引
    - 如果没有搜索到则返回 -1

- `新字符串 = 字符串.match()`
    - 用途：根据正则表达式，从字符串中提取内容
    - 默认情况下，该方法找到第一个匹配的内容后，就会停止并返回
    - 可以设置全局匹配模式：`/正则表达式/g`，来匹配所有内容
    - 返回结果是一个 Array 对象

- `新字符串 = 字符串.replace("被替换字符串"/正则表达式, "新字符串")`
    - 用途：替换指定字符串
    - 使用`"被替换字符串"`时，默认情况下，该方法只会替换第一个匹配结果
    - 可以通过 `/正则表达式/g`，来进行全局替换

- 示例
    - 参考代码
        - [/javascript/base/src/innerObject/string/regEpxMethod.html](/javascript/base/src/innerObject/string/regEpxMethod.html)
    - js内容
        ```js
        // 1. split 拆分数组
        // 1.1 根据分割字符串，将字符串拆分为数组
        var a01 = "asdf,etr,xb";
        var resultA01 = a01.split(',');
        console.log("typeof resultA01 =", typeof resultA01);
        // 输出：
        console.log("resultA01 =", resultA01);
        // 输出：

        // 1.2 根据正则表达式，将字符串拆分为数组
        var a02 = "sdf2bvn4gth6";
        // 用数字来拆分字符串
        var resultA02 = a02.split(/\d/g);
        console.log("resultA02 =", resultA02);
        // 输出：

        // 2. search
        // 2.1 search 字符串搜索
        var b01 = "abcdefg";
        var resultB01 = b01.search("de");
        console.log("resultB01 =", resultB01);
        // 输出：

        // 2.2 search 正则表达式匹配
        var b02 = "et gh abcd afc aecdfg";
        var resultB02 = b02.search(/a.c/);
        console.log("resultB02 =", resultB02);
        // 输出：

        // 3. match
        // 3.1 默认的匹配模式
        var c01 = "et gh abcd afc aecdfg";
        var resultC01 = c01.match(/a.c/);
        console.log("resultC01 =", resultC01);
        // 输出：resultC01 = ["abc", index: 6, input: "et gh abcd afc aecdfg", groups: undefined]

        // 3.2 全局匹配，并且忽略大小写
        var c02 = "et gh aBcd afc aecdfg s4avc3r ";
        var resultC02 = c02.match(/a.c/ig);
        console.log("resultC02 =", resultC02);
        // 输出：resultC02 = (4) ["aBc", "afc", "aec", "avc"]

        // 4. replace
        // 4.1 替换字符串
        var d01 = "abcdeadfgawax";
        var resultD01 = d01.replace("a","@");
        console.log("resultD01 =", resultD01);
        // 输出：resultD01 = @bcdeadfgawax

        // 4.2 使用正则表达式进行全局替换
        var d02 = "abcdeadfgawax";
        var resultD02 = d02.replace(/a/g,"@");
        console.log("resultD02 =", resultD02);
        // 输出：resultD02 = @bcde@dfg@w@x
        ```


# 垃圾回收gc
[top](#catalog)
- 当一个对象没有被任何变量或属性引用时，将无法继续操作该对象，会变为垃圾
- js中有自动垃圾回收机制，会自动将垃圾对象从内存中清除，不需要手动执行
- 对于一些**只使用一次的对象**，该对象将会一直被引用，导致无法被垃圾回收。在这种情况下，可以手动执行: `变量 = null`，切断变量与对象之间的引用关系，使变量被回收
    ```js
    // a 只使用一次
    var a = new Object();
    a.XXX();

    // 手动切断 引用关系，使变量被回收
    a = null
    ```


# 弹出框
[top](#catalog)
- 提示框：`alert("asdfg");`
    - `alert` 函数没有返回值
- 可输入提示框：`var 接受输入值的参数 = prompt("提示信息");`

# 反射
[top](#catalog)
- `typeof 变量`：获取变量的类型，返回一个字符串
- `in` 运算符
    - 检查对象中是否存在某个属性。存在返回 `true`，不存在返回 `false`

- `变量 instanceof 类`，检查变量是不是类的实例
- `对象.hasOwnProperty("属性名")`
    - 只检查对象自身是否包含某个属性
    - 参考：[原型对象](#原型对象)

- 获取类名：`instance.constructor.name`
- 三种常用的输出语句，可以用于测试
    ```js
    // 向html中写入指定内容
    document.write("zxcvb");
    // 将日志输出到控制台
    console.log("qwert");
    ```

# JSON
## JSON的基本知识
[top](#catalog)
- json：JavaScript Object Notation，JS对象表示法
- json的本质
    - json就是特殊格式的**字符串**，这个字符串可以被任意的语言所识别

- json的用途
    - 做为一种数据交换格式
        - js中的对象只有js语言自身能够识别，其他语言都无法识别。需要使用json，然后其他语言解析json并生成对象
    - json在开发中主要用来做数据的交互/传递

- json和js对象的格式一样，只是JSON字符串中的属性名必须添加**双引号**
- json的两种格式
    - json对象：`{}`
        ```js
        var a = '{"name":"aaa", "age":10}';
        ```
    - json数组：`[]`
        ```js
        var b = '[1, 2, 3, 4]';
        var c = '[{"name":"aaa", "age":10}, {"name":"bbb", "age":11}, {"name":"ccc", "age":12}]';
        ```
- json中合法的值类型
    1. 字符串，String
    2. 数值，Number
    3. 布尔值，Boolean
    4. 空值，Null
    5. 对象，Object
    6. 数组，Array

## 通过工具类对象JSON来转换json与js对象
[top](#catalog)
- js中json的工具类对象：`JSON`
    - 通过该对象可以在json与js对象之间进行相互转换
    - JSON对象在IE7及以下的浏览器中不支持
    - 转换方法
        - json -->> js对象：`var js对象 = JSON.parse("json字符串")`
            ```js
            var a = '[1,2,3,4,5]';
            var b = JSON.parse(a);
            console.log(b); // (5) [1, 2, 3, 4, 5]
            console.log(typeof b); // object
            console.log(b instanceof Array); // true

            var c = '{"name":"aaa", "age":10}';
            var d = JSON.parse(c);
            console.log(typeof d); // object
            console.log(d instanceof Object); // true
            console.log("d.name=",d.name, ", d.age=", d.age);
            // d.name= aaa , d.age= 10
            ```

        - js对象 -->> json：`var json字符串 = JSON.stringify(js对象)`
            ```js
            var a = {
                name: "abcd",
                age:16,
                address:"xxxxx"
            };

            var b = JSON.stringify(a);
            console.log(b);
            // {"name":"abcd","age":16,"address":"xxxxx"}
            console.log(typeof b);
            // string
            ```

- `JSON`对象如何兼容IE7及以下的浏览器
    - 引入一个外部的JS文件：`json2.js`
        ```html
        <script type="text/javascript" src=".../json2.js"></script>
        ```


# 注意事项
[top](#catalog)
- JS实现的三大部分
    - ECMAScript：实现标准 (简称ES)
    - DOM：文档对象模型
    - BOM：浏览器对象模型
- 字符串
    - 使用Unicode的方法：`"\u16进制Unicode编码"`
    - 任何类型和String型的`+`运算，都会先将非String型转换为String型，然后再执行字符串连接
- 对象
    - 无法给基本数据类型 添加方法和属性，只能添加给对象
    - 所有对象都是Object的后代，所有对象执行：`对象 instanceof Object`，返回值都是true
    - 在全局作用域中创建的函数，都会作为：window对象的方法

- Undefined衍生自Null，两者的相等与全等判断如下：
    - `Undefined == Null`，返回true
    - `Undefined === Null`，返回false

- 函数
    - 函数如果不指定返回值，默认也会返回 `undefined`
    - 使用**函数表达式** `var 变量名 = function([参数列表]){...}`创建的函数
            - 函数表达式<label style="color:red">不会提升</label>，所以不要函数表达式声明之前使用函数

- 通过构造函数创建正则表达式的时候，如果使用了：`\s`,`\w`,`\b`等元字符时，需要将`\`转义为：`\\`
    ```js
    // 匹配字符串，并区分单词的开头与结束
    var reg = new RegExp("\\b" + xx + "\\b");
        ```
