<span id="catalog"></span>

### 目录
- [JavaSctipt简介](#JavaSctipt简介)
- [JS的编写位置](#JS的编写位置)
- [JS的基本语法规范](#JS的基本语法规范)
- [](#)
- [](#)
- [](#)
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

[top](#catalog)