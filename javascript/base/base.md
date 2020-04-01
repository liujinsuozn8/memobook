<span id="catalog"></span>

### 目录
- [JavaSctipt简介](#JavaSctipt简介)
- [JS的编写位置](#JS的编写位置)
- [JS的基本语法规范](#JS的基本语法规范)
- [数据类型](#数据类型)
    - [数据类型分类](#数据类型分类)
    - [基本数据类型](#基本数据类型)
    - [类型转换](#类型转换)
        - [js中的类型转换](#js中的类型转换)
        - [其他类型转换为String](#其他类型转换为String)
        - [其他类型转换为Number](#其他类型转换为Number)
        - [其他类型转换为Boolean](#其他类型转换为Boolean)
    - [不同进制的数字](#不同进制的数字)
- [算数运算符](#算数运算符)
    - [二元运算符](#二元运算符)
    - [一元运算符](#一元运算符)
    - [自增自减](#自增自减)
- [](#)
- [](#)
- [反射](#反射)
- [](#)


# JavaSctipt简介
[top](#catalog)
- `ECMAScript`是`JavaSctipt`的标准，各个厂商提供实现，一般情况下这两个词的含义是相同的，但是JavaScript的含义更广泛

- <label style="color:red">一个完整的JavaScript实现应该有三部分组成</label>
    - <label style="color:red">ECMAScript：实现标准</label>
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

# JS的基本语法规范
[top](#catalog)
- js中严格区分大小写
- js的每一条语句以`;`结尾
    - 如果不写`;`，**浏览器会自动添加**，但是**会消耗一些系统资源**
    - 浏览器自动添加`;`时，也可能会加错位置，所以开发时一定要写`;`
- 变量声明
    ```js
    var k = 'v';
    ```
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

- 三种常用的输出语句，可以用于测试
    ```js
    // 弹出提示框
    alert("asdfg");
    // 向html中写入指定内容
    document.write("zxcvb");
    // 将日志输出到控制台
    console.log("qwert");
    ```

# 字面量与变量
[top](#catalog)
- 字面量
    - 字面量是指一些不可改变的值，如：数字`333`、字符串`"xdvcb"`、`null`、`undefined`
    - 字母量都可以直接使用

- 变量
    - 变量可以用来保存字面量
    - 变量的值可以任意改变

# 数据类型
## 数据类型分类
[top](#catalog)
- 数据类型指的是字面量的类型
- JS的6种数据类型
    |分类|数据类型|描述|
    |-|-|-|
    |基本数据类型|String|字符串|
    |基本数据类型|Number|数值|
    |基本数据类型|Boolean||
    |基本数据类型|Null|空值|
    |基本数据类型|Undefined|未定义|
    |引用数据类型|Object|对象|

## 基本数据类型
[top](#catalog)
- String
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
- Number
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
- Boolean
    - true/false

- Null
    - Null类型的值只有一个：`null`
    - `null`表示**空对象**
    - <label style="color:red">`typeof null`返回的是：object</label>

- Undefined
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

## 类型转换
### js中的类型转换
[top](#catalog)
- 类型转换指将一个数据类型强制转换为其他的数据类型
- js类型转换主要是将其他的数据类型转换为：String、Number、Boolean
    - 转换为：Null、Undefined没有实际的意义

### 其他类型转换为String
[top](#catalog)
- 两种转换方式
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
        - 参考：[算数运算符](#算数运算符)

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
- 两种转换方式
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
        - `parseFloat(字符串)` 从左到右读取数字，直到读取到一个数字之外的字符，并将数字转换为**浮点数**
        - 如果对非String类型数据使用这两个方法，会先转换为String，然后再操作
    
    - 方式3：对一个非 Number型 变量执行 ：`变量 - 0`、`变量 * 1`、`变量 / 1`，来将类型转换为 Number
        - 底层使用`Number()` 函数执行类型转换
        - 参考：[算数运算符](#算数运算符)
    
    - 方式4：对一个非 Number型 变量 使用**一元运算符**：`+`
        - 底层使用`Number()` 函数执行类型转换
        - 参考：[算数运算符](#算数运算符)

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

- 通过函数：`Boolean()`进行转换
- 各类型数据的转换结果

    |原始数据类型|数据内容|转换结果|
    |-|-|-|
    |Number|0、NaN|false|
    |Number|非0数字、Infinity|ture|
    |String|空字符串：`""`|false| 
    |String|非空字符串|true|
    |Null|null|false|
    |Undefined|undefined|false|
    |Object||true|

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

# 算数运算符
## 二元运算符
[top](#catalog)
- 与Number型数据的运算符
    - 正常的Number型数据之间执行正常运算
    - 对非Number类型数据进行运算时，先数据转换为对应的Number类型，然后再进行运算
        - 如：`true + false`会转换成：`1 + 0`
    - **任何数字、Null、Undefined和 NaN 进行运算，结果都是 NaN**

- `+` 运算符
    - 对于多个Number型的数据，执行加法运算
    - 对于多个String型的数据，执行字符串连接
    - 任何类型和String型的`+`运算，都会先将非String型转换为String型，然后再执行字符串连接
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

## 一元运算符
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
        g5 = -g5;
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
- 前增/减、后增/减都会**立刻使变量+1**


# 反射
[top](#catalog)
- `typeof 变量`：获取变量的类型，返回一个字符串


[top](#catalog)
