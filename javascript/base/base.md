<span id="catalog"></span>

### 目录
- [JavaSctipt简介](#JavaSctipt简介)
- [JS的编写位置](#JS的编写位置)
- [JS的基本语法规范](#JS的基本语法规范)
- [数据类型](#数据类型)
    - [数据类型分类](#数据类型分类)
    - [基本数据类型](#基本数据类型)
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
    - 字面量是指一些不可改变的值，如：数字`333`、字符串`"xdvcb"`
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
[top](#catalog)
- 强制类型转换
    - 指将一个数据类型强制转换为其他的数据类型
    - 类型转换主要指，将其他的数据类型转换为：String、Number、Boolean
        - 转换为：Null、Undefined没有实际的意义
- 其他类型转换为String
    - 


# 反射
[top](#catalog)
- `typeof a`：获取变量a的类型


[top](#catalog)
