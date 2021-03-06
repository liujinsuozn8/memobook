<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》
    - https://www.bilibili.com/video/BV14s411E7qf
    - https://segmentfault.com/a/1190000016278115

### 目录
- [声明标识符](#声明标识符)
- [数据类型系统](#数据类型系统)
    - [JS和ES类型系统的划分与关联](#JS和ES类型系统的划分与关联)
    - [JavaScript中的两套类型系统](#JavaScript中的两套类型系统)
    - [ECMAScript的类型系统](#ECMAScript的类型系统)
        - [ES语言类型](#ES语言类型)
        - [ES规范类型](#ES规范类型)
    - [ES语言类型与JS类型的部分区别](#ES语言类型与JS类型的部分区别)
- [字符串](#字符串)
    - [字符串的特殊性](字符串的特殊性)
    - [字符串的可索引性](#字符串的可索引性)
    - [字符串的可迭代性](#字符串的可迭代性)
    - [多行声明时的折行符](#多行声明时的折行符)
    - [NUL字符串](#NUL字符串)
    - [UTF-16的支持](#UTF-16的支持)
    - [空字符串](#空字符串)
- [模版字面量](#模版字面量)
    - [模版字面量--基本使用与注意实现](#模版字面量--基本使用与注意实现)
    - [模版字面量的本质](#模版字面量的本质)
- [标签函数](#标签函数)
- [数值--数字字面量](#数值--数字字面量)
    - [进制的表示](#进制的表示)
    - [10进制数的存储与计算性能](#10进制数的存储与计算性能)
- [数据类型的操作](#数据类型的操作)
    - [数据类型的判断](#数据类型的判断)
    - [值类型与引用类型表示数据使用方式](#值类型与引用类型表示数据使用方式)
    - [与数据类型相关的问题](#与数据类型相关的问题)
- [数据、变量、内存](#数据、变量、内存)
    - [三者之间的关系](#三者之间的关系)
    - [与数据、变量、内存相关的问题](#与数据、变量、内存相关的问题)
- [比较运算](#比较运算)
    - [JS中的两个特殊约定](#JS中的两个特殊约定)
    - [数据类型不同时的变换规则](#数据类型不同时的变换规则)
    - [NaN的特殊性](#NaN的特殊性)
    - [相等比较](#相等比较)
    - [大小比较](#大小比较)
- [运算符的二义性](#运算符的二义性)
    - [逗号的二义性](#逗号的二义性)
    - [方括号的二义性](#方括号的二义性)
- [线程机制与事件机制](#线程机制与事件机制)
    - [浏览器中的进程与线程](#浏览器中的进程与线程)
    - [定时器的问题](#定时器的问题)
    - [JS是单线程执行的](#JS是单线程执行的)
    - [JS引擎执行代码的流程](#JS引擎执行代码的流程)
    - [浏览器的事件循环模型](#浏览器的事件循环模型)
        - [事件循环模型的重要概念](#事件循环模型的重要概念)
        - [事件循环模型原理图及说明](#事件循环模型原理图及说明)
        - [浏览器触发事件和js触发事件的不同点](#浏览器触发事件和js触发事件的不同点)
        - [与事件循环模型相关的问题](#与事件循环模型相关的问题)
    - [H5_Web_Workers多线程](#H5_Web_Workers多线程)
- [其他](#其他)
    - [代码结尾的分号](#代码结尾的分号)
    - [类数组对象的for遍历的性能问题](#类数组对象的for遍历的性能问题)
    - [alert](#alert)
    - [变量函数的目标查找方法](#变量函数的目标查找方法)
- [](#)

# 声明标识符
[top](#catalog)
- js中的6中声明标识符的方法，它们在语法分析阶段就能被识别
    - 变量 var
    - 常量 const
    - 块作用域 let
    - 函数 function
    - 类 class
    - 模块 import

# 数据类型系统
## JS和ES类型系统的划分与关联
[top](#catalog)
- JS和ES的语言类型概念
    - JavaScript有自己的类型系统
    - ECMAScript也有自己的类型系统
    - ES规范中描述的语言，严格来说并不是JS
        - JS甚至可以理解为ES的方言

- JS类型系统的两种划分
    - 基础类型系统: 7 种基本类型
        - 其中对象类型中又包含对象类型系统，即基础类型的包装类
    - 值类型与引用类型

- ES类型系统的两种划分
    - ES语言类型，ECMAScript language types
    - ES规范类型，ECMAScript specification types

- ES类型系统与JS类型系统的关系
    - ES规范类型**实现了**ES语言类型
    - ES语言类型**描述了**JS语言规范

## JavaScript中的两套类型系统
[top](#catalog)
- JavaScript类型系统可以分为两套
    1. 基础类型系统: 7 种基本类型
        - 其中对象类型中又包含对象类型系统，即基础类型的包装类
    2. 值类型与引用类型

- 在js中使用字符串作为类型名，首字母小写

- 类型分类: 7种基本类型+值/引用的划分

    |数据类型|分类|备注|
    |-|-|-|
    |undefined|值类型||
    |string|值类型|字符串在赋值运算中会按引用类型的方式来处理|
    |number|值类型||
    |boolean|值类型||
    |symbol|值类型|符号是一种与对象有些相似的`值类型`数据|
    |object|引用类型|基于原型继承与类继承的面向对象模型。包含null、正则表达式对象|
    |function|引用类型|函数。js中的函数包含多种含义，包括函数、方法、构造器、生成器、类、函数对象等|

- 特殊Object类型---Array
    - Array 是一种特殊的对象
    - 对象中的属性是**数值下标**
    - 内部的数值是**有序的**

- 基础类型及其包装类

    |分类|基础类型|字面量|包装类|
    |-|-|-|-|
    |值类型|undefined|undefined|不需要包装类|
    |值类型|boolean|true，false|Boolean|
    |值类型|number|数值|Number|
    |值类型|string|`'...'`，`"..."`，模版字符串|String|
    |值类型|symbol|无|Symbol|
    |引用类型|function|`function(){...}`|自身相当于包装类|
    |引用类型|object|`{...}`|自身相当于包装类|


## ECMAScript的类型系统
### ES语言类型
[top](#catalog)
- ES规范中用首字母大写的单词作为数据类型
- ES语言类型中的7种数据类型
    - Undefined
    - Null
    - Boolean
    - String
    - Symbol
    - Number
    - Object
-  注意事项
    - ES的语言类型不完全是JS的语言类型
    - 不应该用ES语言类型来映射JS类型
        - 这样将会引入更多的不可解释的问题
    - ES语言规范只是为了编写ECMAScirpt规范本身

### ES规范类型
[top](#catalog)
- ES规范类型与ES语言类型、JS类型系统没有任何重叠的内容
- ES规范类型的用途
    - 为了实现ES语言类型

- 10中规范类型
    - List、Record、Set、Relation
        - 主要使用的是 List、Record 类型，是整个ES规范实现中采用最多的数据类型
    - Completion Record、Reference、Property Descriptor
        - 主要用来作为运算的结果、中间结果
        - Property Descriptor 属性描述符，是实现 JS对象 时使用的核心组件
    - Data Blocks
        - 主要用在内存、共享数据等的描述
    - Lexical Enviroments、Enviroment Record
        - 主要用在词法和运行期环境等的描述

## ES语言类型与JS类型的部分区别
[top](#catalog)
- 两种的区别

    ||ES|JS|
    |-|-|-|
    |null    |`Null`是一种类型，只有一个值`null`|没有null类型，null值是对象类型的一个特殊示例|
    |function|没有函数类型，函数是对象类型的一个**变体**，即对象类型的一种实现|包含fucntion|

- ES中的**变体**
    - ES中最基础的对象称为普通对象， Ordinary object
    - 在普通对象上添加了定制行为的被称为变体，Exotic object

# 字符串
## 字符串的可索引性
[top](#catalog)
- **可以通过 索引来获取字符**: `字符串[index]`
- 本质是将索引作为属性来使用
- 可以用 `for...of` 来遍历每个字符
- 示例
    - 参考代码
        - [src/literal/string/indexable.js](src/literal/string/indexable.js)
    - 代码内容
        ```js
        // 字符串的可索引性
        let a = 'abcde';

        // 1. 通过索引访问某个字符
        console.log( "a[2]=" + a[2] );  // a[2]=c
        ```
## 字符串的可迭代性
[top](#catalog)
- 从ES6开始，字符串被添加的 `Symbol.iterator` 属性，可以被迭代
- 迭代性操作
    - 字符串可以被三点运算符 `...` 展开
    - 可以 `for...of` 迭代每个字符
    - 可以使用 `yeild*` ，从生成器内部返回一个字符

- 示例
    - 参考代码
        - [src/literal/string/iterator.js](src/literal/string/iterator.js)
    - 代码内容
        1. 使用三点运算符 `...` 展开
            ```js
            let str = 'abvce';
            let a = [...str];
            console.log(a); // [ 'a', 'b', 'v', 'c', 'e' ]
            ```
        2. `for...of` 迭代每个字符
            ```js
            for (let char of a){
                console.log( "char=" + char);
                // 输出:
                // char=a
                // char=b
                // char=c
                // char=d
                // char=e
            }
            ```
        3. 通过 `yeild*` ，从生成器内部返回一个字符
            ```js
            function* generatChar(str){
                yield* str;
            }
            let g = generatChar('abcd');
            console.log(g.next());  // { value: 'a', done: false }

            for(let n of g){
                console.log(n);
            }
            // 输出:
            // b
            // c
            // d
            ```
## 多行声明时的折行符
[top](#catalog)
- 多行声明时的折行符 `\`
    - **在一行的末尾**，用于表示文本的**折行**
    - 多行声明字符串时，换行是有效的。`\`可以避免换行符
    - 如果字符串过长可以用 `\` 折行
- 注意事项
    -  `\` 后面不能有注释
- 示例
    - 参考代码
        - [src/literal/string/multiline.js](src/literal/string/multiline.js)
    - 代码内容
        ```js
        let a = '\
        qwertyy\
        qeerty\
        '
        console.log(a);     // qwertyyqeerty
        ```

## NUL字符串
[top](#catalog)
- `\0` 表示NUL字符串
- 输出时，仍然是空，但是length能测试出来
- 声明方法
    1. `String.fromCharCode(0)`
    2. 字面量: `\0`
- 示例
    - 参考代码
        - [src/literal/string/NUL_str.js](src/literal/string/NUL_str.js)
    - 代码内容
        ```js
        // 1 声明方式1
        str1 = String.fromCharCode(0, 0, 0, 0, 0);
        console.log(`str1="${str1}"`);                  // str1=""
        console.log(`str1.length=${str1.length}`);      // str1.length=5

        // 2 声明方式2
        str2 = '\0\0\0\0';
        console.log(`str2="${str2}"`);                  // str2=""
        console.log(`str2.length=${str2.length}`);      // str2.length=4
        ```

## UTF-16的支持
[top](#catalog)
- js字符串对 UTF-16 的支持
    - es6中增加了 `"\u{nnnnn}"`，表示码值大于 `0xFFFFF` 的Unicode字符的语法
    - 在当前js字符集使用UTF-8时，`"\u{nnnnn}"`的长度为2
- 正确判断 UTF-16 字符的长度
    - ?????
- 示例
    - 参考代码
        - [src/literal/string/utf16.js](src/literal/string/utf16.js)
    - 代码内容
        ```js
        // '\u{nnnnn}' 使用UTF-16字符
        let str3 = '\u{20BB7}'
        console.log(`str3 = ${str3}`);                  // str3 = 𠮷
        console.log(`str3.length = ${str3.length}`);    // str3.length = 2
        ```

## 空字符串
[top](#catalog)
- 用 `''`、`""` 表示空字符串
- 空字符串的长度为0
- 空字符串可以用作对象的属性名
- 示例
    - 参考代码
        - [src/literal/string/empty_str.js](src/literal/string/empty_str.js)
    - 代码内容
        ```js
        let obj = {
            '':100
        }
        console.log(`obj[''] = ${obj['']}`);  // obj[''] = 100
        ```

# 模版字面量
## 模版字面量--基本使用与注意实现
[top](#catalog)
- 用法
    - 通过 ` 符号来标识
    - 内部使用 `${}` 来捕获当前上下文中的变量、常量、字面量、对象成员属性
- 可以理解为一种增强的字符串声明
    - 它的返回值总是一个字符串，但是严格来说并没有模版字符串这种说法
- 一些特殊的字符与多行声明
    - 换行有效
    - 折行符 `\` 有效
    - 需要转译才能显示的字符 \、`、$
    - 双引号和单引号可以直接写，不需要转译

- 示例
    - 参考代码
        - [src/literal/template/usage.js](src/literal/template/usage.js)
    - 代码内容
        ```js
        let str1 = `
        "abcd \
        "\\"
        "\`"
        "\$"`

        console.log(str1);
        // 输出:
        //           <<<< 包含一个换行，即使左侧 ` 后面的换行
        // "abcd' "\"
        // "`"
        // "$"
        ```

## 模版字面量的本质
[top](#catalog)
- 本质是一个数组，然后由标签函数调用
    ```js
    let a = `aaaa${param1}bbbbb${parma2}`;
    // 可以分解为
    let list = ['aaaa', 'bbbbb'];
    param1
    param2
    ```
- `String.raw()` 是一个模版字面量的**标签函数**
- `String.raw()` 函数的调用
    - `String.raw({ raw: [...] }, ....)`，通过数组定义插入点
        - 示例
            ```js
            // 相当于 let a = `e1${插入参数1}e2${插入参数2}e3...`
            let a = String.raw( {raw: [e1, e2, e3,...]}, 插入参数1, 插入参数2,.....)
            ```
        - 插入参数必须要插在两个元素中间，否则无法插入
            ```js
            // 相当于 let a = `'aa${12}bb`
            // 因为缺少一个元素，无法插入34，
            let a = String.raw( {raw: ['aa', 'bb']}, 12, 34);
            ```
    - `String.raw({ raw: '...' }, ....)`，通过字符串定义插入点
        ```js
        //  相当于
        let a = String.raw( {raw: 'abcde'}, 插入参数1, 插入参数2,.....)
        ```
    - String.raw 的**标签函数式**调用
        ```js
        // let rawStr03 = String.raw`...`
        // 转义字符不会生效，但是捕获遍历仍然有效
        let param03 = 12345
        console.log(String.raw`aaaa\nbbb, num=${param03}`);  // aaaa\nbbb, num=12345
        ```
    - 将 String.raw 单独声明为**标签函数**来复用
        - 复用时，会**按照参数的顺序自动填充到模版中，**与模版中的捕获声明无关
        ```js
        let tagFn = 模版 => String.raw(模版, 参数,...);
        tagFn`模版内容`;
        ```
- 示例
    - 参考代码
        - [src/literal/template/underlying.js](src/literal/template/underlying.js)
    - 代码内容
        ```js
        // 2.1 使用模版字面量
        let param1 = 1234;
        let param2 = 'qwer';
        let originStr = `number=${param1}, string=${param2}`;
        console.log(originStr); // number=1234, string=qwer

        // 2.2 使用String.raw 的函数形式
        // 2.2.1 通过数组定义插入点
        // 相当于 let rawStr01 = `number=${param1}string=${param1}`
        let rawStr01 = String.raw({raw:['number=', ', string=','']}, param1, param2);
        console.log("rawStr01= " + rawStr01)        // number=1234, string=qwer

        // 2.2.2 如果 raw 数组中没有三个元素 ''， param2 无法被插入
        // 相当于 let rawStr02 = `number=${param1}string=`
        let rawStr02 = String.raw({raw:['number=', ', string=']}, param1, param2);
        console.log("rawStr02= " + rawStr02)        // number=1234, string=

        // 2.2.3 通过字符串定义插入点
        // 相当于 let rawStr03 = `a${111}b${222}c${333}d`
        let rawStr03 = String.raw({raw: 'abcd'}, 111, 222, 333);
        console.log("rawStr03= " + rawStr03);       // rawStr03= a111b222c333d

        // 2.3 String.raw 的运算符式调用
        // let rawStr03 = String.raw`...`
        // 转义字符不会生效，但是捕获遍历仍然有效
        let param03 = 12345
        console.log(String.raw`aaaa\nbbb, num=${param03}`);  // aaaa\nbbb, num=12345

        // 2.4 将 String.raw 单独声明为 标签函数 来复用
        // 声明函数
        let foo = template => String.raw(template, 'v1', 'v2', 'v3');
        // 复用
        // console.log(foo`k1=${}, k2=${}, k3=${}`);        // 必须声明捕获的变量
        console.log(foo`k1=${0}, k2=${1}, k3=${2}`);        // k1=v1, k2=v2, k3=v3
        ```

# 标签函数
[top](#catalog)
- 参考文档：https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals#Tagged_template_literals
- 功能
    - 用函数解析模版字符串
- 调用方式
    - 在标签函数后面加一个模版字符串来调用
        ```js
        标签函数`模版字符串`
        ```
    - 这是一种隐式的函数调用
- 标签函数的定义
    - ts的定义方式
        - 接收任意数量的捕获参数
            ```ts
            function tagFn(strings:string[], ...exps:any[]):any {
                return ...;
            }
            ```
        - 接收指定数量的捕获参数
            ```ts
            function tagFn(strings:string[], exp1:any, exp2:any):any {
                return ...;
            }
            ```
    - 相当于 `String.raw({ raw: [...] }, ....)`，通过数组定义插入点
    - 参数`strings`，就是固定的字符串部分，这些字符串被 `${}` 操作符分割
    - exps，就是 `${}` 内部参数的集合
- 标签函数的返回值类型
    - 可以直接返回字符串
    - 可以返回一个函数，再复用
- 示例
    - 参考代码
        - [src/literal/tag_function/define.js](src/literal/tag_function/define.js)
    - 代码内容
        1. 定义返回字符串的标签函数
            ```js
            // 1.1 定义标签函数
            function tagFn01(strs, ...exps){
                // 获取两个数组中的最大值
                let minLength = strs.length > exps.length ? exps.length : strs.length;
                // 循环拼接
                let result = '';
                let i;
                for (i= 0; i<minLength; i++){
                    result += strs[i];
                    if (typeof exps[i] === 'number'){ // 拼接字符串时，执行相关处理
                        if (exps[i] > 0){
                            result += '正数';
                        } else {
                            result += '非正数';
                        }

                    } else {
                        result += exps[i];
                    }
                }

                // 将剩余的部分添加到结果字符串
                // ...
                // 将拼接结果返回
                return result;
            }

            // 1.2 调用标签函数
            let num1 = 1234;
            let num2 = -1234;
            let num3 = 0;
            let str1 = tagFn01`num1 is ${num1}, num2 is ${num2}, num3 is ${num3}`;
            console.log(str1);
            ```
        2. 定义返回函数的标签函数
            - 参考实现
                - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals#Tagged_template_literals
            - 实现内容
            ```js
            function template(strings, ...keys) {
                // 返回一个函数。在复用时，传递需要捕获的参数，并拼接字符串
                return function(...values) {
                    let dict = values[values.length - 1] || {}; // 最后一个参数需要是对象
                    let result = [strings[0]];
                    keys.forEach(function(key, i) {   // 开始拼接
                        let value = Number.isInteger(key) ? values[key] : dict[key];
                        result.push(value, strings[i + 1]);
                    });
                    return result.join('');
                };
            }

            // 2.1 在模版中通过index来捕获参数
            let t1Closure = template`${0}${1}${0}!`;
            //let t1Closure = template(["","","","!"],0,1,0);
            console.log(t1Closure('Y', 'A'));                           // "YAY!"

            // 2.2 在模版中同时使用index 和 对象属性名的方式来捕获参数
            let t2Closure = template`${0} ${'foo'}!`;
            //let t2Closure = template(["","","!"],0,"foo");
            console.log(t2Closure('Hello', {foo: 'World'}));            // "Hello World!"

            // 2.3
            let t3Closure = template`I'm ${'name'}. I'm almost ${'age'} years old.`;
            //let t3Closure = template(["I'm ", ". I'm almost ", " years old."], "name", "age");
            console.log(t3Closure('foo', {name: 'MDN', age: 30}));      //"I'm MDN. I'm almost 30 years old."
            console.log(t3Closure({name: 'MDN', age: 30}));             //"I'm MDN. I'm almost 30 years old."
            ```

# 数值--数字字面量
## 进制的表示
[top](#catalog)
- 16进制
    - `0x1234`、`0X1234`， 由 `0x`、`0X` 开头
    - 16进制数由：0～9、A～F 组成
- 8进制
    - `0o1234`、`0O1234`， 由 `0o`、`0O` 开头
    - 8进制数由：0～7 组成
- 2进制
    - `0b1234`、`0B1234`， 由 `0b`、`0B` 开头
    - 2进制数由：0、1 组成
    - `1234`，10进制 1234
- 10进制
    - 可以由：0～9、至多一个 `.` 、e、E 组成
        ```js
        1.2345
        .1234       以 `.` 字符开始的是10进制浮点数
        .0e8        // 仍然是0
        1.23E-45    // 负号用来表示 负指数
        1.23E+45    // 正号用来表示 正指数
        ```
## 10进制数的存储与计算性能
[top](#catalog)
- 10进制数的存储
    - 对于10进制**整数**，不同的引擎实现不同，可能是整数、可能是浮点数
    - 由于整数存储的不确定性，<label style='color:red'>不能期望JS中的整数会有较高的运算性能</lable>
    - 如果数中包含: `.`、`e`、`E`，则数字字面量总会被存储为**浮点数**
- 提升数值运算型能
    - 用**位运算**替代算数运算
    - 位运算总是以整型数的形式来运算，即使操作数是一个浮点数

# 数据类型的操作
## 数据类型的判断
[top](#catalog)
- (反射方法)判断数据类型的方法
    - `typeof`
        - `typeof` 是一个运算符，不是函数，并且是js内部的保留字
        - 功能:
            - 获取变量的类型，返回一个字符串
        - 返回
            - 包括: undefined、string、number、boolean、function, symbol, object
            - **返回结果都是对应类型**，并且**第一个字母小写**
            - 无法判断 null 、Array、 Object，因为都会返回 object (对于null，这是一个bug)
        - 调用方式
            ```js
            // 运算符式用法
            typeof xxx
            // 函数式用法
            typeof(xxx)
            ```
    - `===` 严格相等
        - 功能
            - 比较值类型/引用类型的实际数据
        - 规则
            - 值与引用、值与值之间即使相等(==，默认的类型转换)，也不一定严格相等
            - 两个引用如果相等，则一定严格相等
                - 可以理解为：地址相等
    - `instanceof`
        - 用于判断对象是不是某个类的实例，即判断具体的类型

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

## 值类型与引用类型表示数据使用方式
[top](#catalog)
- 参考代码
    - [src/datatype/value_and_refenence.html](src/datatype/value_and_refenence.html)

- 值类型与引用类型可以表示运算时使用数据的方式：参与运算的是值还是引用
    ```js
    var str = 'abcde';
    var obj = new String(str);

    function newToString(){
        return 'hello world';
    }
    function func(val){
        // 修改toString函数
        val.toString = newToString;
    }

    // 使用值
    func(str);          // 值传入了值，所以无法修改toString属性
    console.log(str); // 输出: abcde

    // 使用引用
    func(obj);
    console.log(obj);   // [String: 'abcde'] { toString: [Function: newToString] }
    console.log(String(obj)); // hello world
    ```

## 与数据类型相关的问题
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

# 数据、变量、内存
## 三者之间的关系
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

## 与数据、变量、内存相关的问题
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
    5. 自动释放与 gc 回收
        - 自动释放的内容是：栈空间中的变量，在函数执行完之后，自动释放
        - gc 回收的内容是：堆空间中的变量/对象，需要变量/对象没有引用，并且需要等待 gc回收启动

# 比较运算
## JS中的两个特殊约定
[top](#catalog)
- 两个特殊约定
    1. `-0 === 0`
    2. `NaN !== NaN`
- ES6 对 JS 两个特殊约定的处理方法： `Object.is()`
    ```js
    Object.is(-0, +0) === false;
    Object.is(NaN, NaN) === true;
    ```

## 数据类型不同时的变换规则
[top](#catalog)
- 比较时的数据变换规则
    1. 如果包含一个 number，则另一个操作数也会转换为 number
    2. 如果有 boolean，转换为number
        - 因为规则 1，转换后公式中出现了 number，所其他变量也会转换为number
    3. 如果有object，调用`valueOf()`转换为值数据
- <label style='color:red'>JS总是尽量用number比较来实现相等比较</label>
    - JS内部的数据存储格式适合该操作
- 字符串
    - 对于 string，只有两个操作数都是 string，才能依次按照字符比较
    - 如果只有一个操作数是 string，需要转换为 number

## NaN的特殊性
[top](#catalog)
- NaN和自身永远不相等，只有`Object.is(NaN, NaN)` 是 true
- 示例
    - 参考代码
        - [src/compare/NaN.js](src/compare/NaN.js)
    - 代码内容
        ```js
        // 1. 相等比较
        console.log( NaN == NaN);   // false
        console.log( NaN != NaN);   // true

        // 2. 严格相等比较
        console.log( NaN === NaN);  // false
        console.log( NaN !== NaN);  // true

        // 3. 大小比较
        console.log( NaN < NaN);    // false
        console.log( NaN <= NaN);   // false
        console.log( NaN > NaN);    // false
        console.log( NaN >= NaN);   // false

        // 4. 使用 Object.is 进行比较
        console.log( Object.is(NaN, NaN) ); // true
        ```

## 相等比较
[top](#catalog)
- `Object.is()`的注意事项
    - 任意两个对象都是不等的（至少地址不等），所以不能替换对象间的 `==` 和 `===` 操作
- `相等`与`严格相等`

    |名称|运算符|比较内容|
    |-|-|-|
    |相等|==|值相等|
    |不等|!=|值不等|
    |严格相等|==|数据类型相等，并且值相等|
    |严格不等|!=|数据类型不等，或这值不等|

- `相等` 的运算规则
    - 整体规则

        |比较类型|规则|
        |-|-|
        |值与引用|将引用类型转换为与值类型相等的类型，然后比较数据是否相等|
        |值与值|统一数据类型后，比较数据内容|
        |引用与引用|比较地址|

    - 字符串比较会有比较大的开销
        - 需要对字符串中的每个字符进行比较
        - 引擎通常会对优化，将多个字符串指向同一个引用。<label style='color:red'>即在赋值时，按照引用类型的方式处理</label>
        - 因为存在优化，所以不是每次都要对每个字符进行比较
- `严格相等` 的运算规则
    - 整体规则
        |比较类型|规则|
        |-|-|
        |值与引用|一定不相等|
        |值与值|数据类型相同，则比较数据是否相等；数据类型不同，一定不相等|
        |引用与引用|比较地址|
- 相等比较中的特例
    - `NaN` 和自身永远不相等
    - Symbol可以转换为 true，但是值不等于 true
        - `Boolean(Symbol()) === true`
        - `!Symbol() === false`
        - `Symbol() === false`
    - 字面量相同的引用类型，也是不严格相等的
        ```js
        {} !== {}
        /./ !== /./
        function(){} !== function(){}
        ```

## 大小比较
[top](#catalog)
- 比较运算符
    - `>`，`>=`
    - `<`，`<=`
- 运算规则
    |比较类型|规则|
    |-|-|
    |值与引用|将引用类型转换为与值类型相等的类型，然后比较数据大小|
    |值与值|比较数据的大小|
    |引用与引用|`>`、`<`，总是返回 false<br>`>=`、`<=`，总是返回 true|

- 当两个字符串
- 示例
    - 不同类型之间的大小比较
        - 参考代码
            - [src/compare/different_type.js](src/compare/different_type.js)
        - 代码内容
            ```js
            let num1 = 1;
            let num2 = 23;
            let num3 = 24;
            let str = '23';
            let b0 = false;
            let b1 = true;
            let o1 = {name:'aaa', age:22};
            let o2 = {name:'aaa', age:22};
            let o3 = new Object();
            o3.name = 'aaa';
            o3.age = 22;
            let o4 = {name:'aaa', age:23};

            // num 与 string比较，str转换为 number
            console.log( `num1 < str: ${num1 < str}` );  //true
            console.log( `num2 <= str: ${num2 <= str}` ); //true
            console.log( `num3 <= str: ${num3 <= str}` ); //false

            // true、false 比较，boolean转换为 number
            console.log( `b1 > b0: ${b1 > b0}` );     //true

            // boolean 与 number 比较，boolean、str转换为 number
            console.log( `num1 <= b1: ${num1 <= b1}` );  //true
            console.log( `num1 <= b0: ${num1 <= b0}` );  //false

            // object 之间比较，object的属性、属性值相等
            console.log( `o1 < o2: ${o1 < o2}` );       // false
            console.log( `o1 > o2: ${o1 > o2}` );       // false
            console.log( `o1 <= o2: ${o1 <= o2}` );     // true
            console.log( `o1 >= o2: ${o1 >= o2}` );     // true

            // 对象字面量与new Object 比较
            console.log( `o1 < o3: ${o1 < o3}` );       // false
            console.log( `o1 > o3: ${o1 > o3}` );       // false
            console.log( `o1 <= o3: ${o1 <= o3}` );     // true
            console.log( `o1 >= o3: ${o1 >= o3}` );     // true

            // object 之间比较，object的属性、属性值不相等
            console.log( `o1 < o4: ${o1 < o4}` );       // false
            console.log( `o1 > o4: ${o1 > o4}` );       // false
            console.log( `o1 <= o4: ${o1 <= o4}` );     // true
            console.log( `o1 >= o4: ${o1 >= o4}` );     // true

            // 空对象比较
            console.log( {} < {} );     // false
            console.log( {} > {} );     // false
            console.log( {} <= {} );    // true
            console.log( {} >= {} );    // true
            ```
    - 字符串的比较
        - 参考代码
            - [src/compare/string.js](src/compare/string.js)
        - 代码内容
            ```js
            let str1 = 'abc';
            let str2 = 'ab';
            let str3 = '200';
            let num = 100;

            // 1. 两个字符串的比较，依次比较每个字符
            console.log( str1 >= str2 );    // true

            // 2. 非数字字符串与数字比较
            // str转换为NaN，与任何值比较都是false
            console.log( str1 <= num );     // false
            console.log( str1 >= num );     // false

            // 3. 数值字符串与数字比较
            console.log( str3 <= num );     // false
            console.log( str3 >= num );     // true
            ```

# 运算符的二义性
## 逗号的二义性
[top](#catalog)
- 逗号 `,` 既可以用作**语法分隔符**， 也可以是**连续运算符**
- 语法分隔符
    ```js
    // 声明了三个变量
    var a=1, b=2, c=3;
    ```
- 连续运算符，需要配合 `()` 来调整优先级
    - 连续运算的结果是最后一个表达式的结果
    ```js
    var a (1, 2, 3);
    // a = 3
    ```

- 产生二义性的情况
    - 产生二义性的代码
        ```js
        var i = 100;
        var print = x=>console.log(x);
        print( (i+=20, i*=2, 'value=' + i) );   // 输出: value=240

        print( i+=20, i*=2, 'value=' + i );   // 输出: 120
        ```
    - `print( (i+=20, i*=2, 'value=' + i) );`
        - 该语句内部通过第二个 `()`，将逗号变为了**连续运算符**
        - 分别执行了
            ```js
            i+=20;  // i= 120
            i*=2;   // i = 240
            'value=' + i    // value=240
            ```
        - 连续运算执行完成后，整体的值是最后一个表达式的值，所以执行的语句相当于
            ```js
            print('value=240');
            ```
    - `print( i+=20, i*=2, 'value=' + i );`
        1. 括号内的 3 个连续语句会先运算
            ```js
            print( 120, 240, 'value=240' );
            ```
        2. 因为 print 只有一个参数，所以相当于执行
            ```js
            print(120)
            ```
        3. 最终输出 120

## 方括号的二义性
[top](#catalog)
- 方括号`[]`，可以用于数组的字面量、存取数组下标的运算符
- 一个二义性语句示例
    - 示例代码
        ```js
        var a = [ [1] [1]];
        // a = [undefined]
        ```
    - 该代码没有具体的含义，并且没有语法错误
    - 编译器对该代码的理解
        1. 第一个 `[1]`，被编译器作为一个数组声明
            ```js
            var array = [1];
            ```
        2. 在 1 的基础上，`[1] [1]` 被进一步转换为
            ```js
            array[1]
            ```
        3. `array` 中只有一个元素，所以 `array[1]` 为 `undefined`
        4. 最终，表达式转换为
            ```js
            var a = [undefined];
            ```
    - 通过推导，可以对该过程进行扩展
        1. 扩展示例1
            ```js
            var a = [ [1,2,3,4][3]]; // a= [4]
            ```
        2. 扩展示例2
            ```js
            var b = [ [] [] ] ; // 语法错误

            // 相当于执行
            var array = [];
            b= [ array[] ]; // 因为没有索引，所以产生了语法错误
            ```
        3. 扩展示例3
            ```js
            var c = [ []['lenght'] ];

            // 相当于执行
            var array = [];
            // array['length'] array 的长度是0
            c = [ array['length'] ];    // c = [0]
            ```

- 一个特殊的示例
    - 示例代码
        ```js
        var a = [
            ['A', 1, 2, 3]  //<<<<<缺少一个 逗号
            ['B', 2, 3, 4],
            ['C', 3, 4, 5]
        ]

        /* a = [
            undefined,
            ['C', 3, 4, 5]
        ]
        */
        ```
    - 编译对代码的理解
        1. 缺少逗号的部分创建了一个数组
            ```js
            var array = ['A', 1, 2, 3];
            ```
        2. 运算 `array['B', 2, 3, 4]`
            - 此时，逗号产生了二义性，不是数组元素的分割符，变成了**连续运算符**
            - `['B', 2, 3, 4]`，返回最后一个表达式的值: `4`
            - 整体相当于运算: `array[4];`
            - 超出了 array 的索引范围，所以得到undefined
        3. 逗号部分运算完，可以转换为
            ```js
            var a = [
                undefined,
                ['C', 3, 4, 5]
            ];
            ```
    - 与特例类似的代码
        - 类似代码1
            ```js
            var a = [
                ['A', 1, 2, 3]  //<<<<<缺少一个 逗号
                ['B', 0, 1, 2], // 相当于 array[1]
                ['C', 3, 4, 5]
            ]

            // 执行过程:
            // a = [ ['A', 1, 2, 3][1], ['C', 3, 4, 5] ];
            // a = [ 1, ['C', 3, 4, 5] ]
            ```
        - 类似代码2
            ```js
            var a = [
                ['A', 1, 2, 3]  //<<<<<缺少一个 逗号
                ['B', 'length'], // 相当于 array['length']
                ['C', 3, 4, 5]
            ]
            // 执行过程:
            // a = [ ['A', 1, 2, 3]['length'], ['C', 3, 4, 5] ];
            // a = [ 4, ['C', 3, 4, 5] ]
            ```

# 线程机制与事件机制
## 浏览器中的进程与线程
[top](#catalog)
- JS是单线程执行的，并且<label style="colro:red">只运行在主线程中，并且只有主线程能够操作DOM</label>
- 通过 H5 中的 `Web Workers` 可以多线程运行
- 浏览器是多线程运行的
- 浏览器有多进程的，也有单进程的

## 定时器的问题
[top](#catalog)
- 定时器是定时执行的吗?
    - **定时器不保证定时执行**
    - 一般会有一点延迟，也有可能延长很长时间
- 定时器回调函数是在分线程执行的吗?
    - 在主线程执行，因为js是单线程的
- 定时器是如何实现的?
    - 事件循环模型

- 示例：定时器的延迟
    - 测试方法
        - 点击按钮触发定时器
        - 定时器结束时，输出消耗的时间
        - 启动定时器后，执行一个比较耗时的操作，如循环
        - 通过控制循环的次数来干扰定时器的执行
    - 参考代码
        - [src/thread/settimeout.html](src/thread/settimeout.html)
    - 代码内容
        ```js
        var button = document.querySelector("button")
        button.onclick = function(){
            // 按钮按下之后执行一个定时器
            var start = Date.now()
            setTimeout(
                function(){
                    console.log(Date.now() - start);
                }
            )

            // 定时器开始执行后，再执行一个耗时较长的任务来干扰定时器
            for(var i=i; i<1000000000;i++){}
        }
        ```


## JS是单线程执行的
[top](#catalog)
- 为什么js要单线程执行，而不用多线程?
    - 与js的用途有关
    - 作为浏览器脚本语言，js的主要用途是与用户交互，以及dom操作
    - 这决定了js只能是单线程的，否则**会带来复杂的同步问题**

- 如果js是单线程的会出现什么样的问题?
    - 如：两个线程A、B同时操作页面的 x 元素
        - 线程A 负责更新 x 的数据
        - 线程B 负责删除 x
    - 假设线程的执行流程
        1. A 先执行，检查 x 存在，准备开始修改 x
        2. 在 A 开始修改 x 前，线程切换到 B
        3. B 检查 x 存在，将 x 直接删除。B 执行结束，切换到 A
        4. A 继续执行更新操作，但是不会再次检查 x 是否存在，因为之前已经检查过了
        5. 应为 x 不存在， A的操作异常，导致页面出现问题

- 如何证明js是单线程执行的
    - 设置 setTimeout，并且添加 alert 操作，来暂停主线程的执行
    - 关闭 alert 的弹出框，setTimeout开始执行，并在控制台输出相关的内容
    - 示例
        - 参考代码
            - [src/thread/js_single_thread.html](src/thread/js_single_thread.html)
        - 代码内容
            ```js
            setTimeout(
                function(){console.log("timout 02")},
                2000
            )

            setTimeout(
                function(){console.log("timout 01")},
                1000
            )

            setTimeout(
                function(){console.log("timout 00")},
                0
            )

            function fn(){
                console.log("this is fn")
            }

            fn()

            // 暂停主线程执行和定时器
            console.log("before alert")
            alert("   ")
            console.log("after alert")
            ```
        - 输出内容
            ```
            this is fn
            before alert
            after alert
            timout 00
            timout 01
            timout 02
            ```

## JS引擎执行代码的流程
[top](#catalog)
- 代码分类
    - 两种类别
        1. 初始化代码，可以理解为同步代码
        2. 回调代码，可以理解为异步代码

    - 代码分类说明
        ```js
        // setTimeout是初始化代码
        setTimeout(
            // 回调函数
            function(){console.log("timout 02")},
            2000
        )
        ```

- js引擎执行代码的基本流程
    1. 先执行初始化代码
        - 包含一些特殊的代码，如
            - 设置定时器
            - 绑定监听
            - 发送ajax请求
    2. 初始化代码执行完成之后，**在某个时刻**，开始执行回调函数
        - 初始化代码执行完成之后，**不能保证立即执行**回调函数
    3. 事件监听会在事件触发后执行

- 回调函数--异步执行
    - 开发时，需要某些代码在其他代码执行完成之后再执行，称为异步执行

## 浏览器的事件循环模型
### 事件循环模型的重要概念
[top](#catalog)
- 执行栈：`call stack`，也成为 `execution stack`
    - 所有的代码都是在此空间执行的
    - 执行栈拥有最高的执行权限，只有执行栈中的方法全部执行完成后，才会启动时间轮询

- 帧 `Frame`
    - 被压入 `call stack` 的函数称为，即栈帧
    - 函数执行完后，会从 调用栈 中弹出

- 浏览器内核：`browser core`
    - js引擎模块，由主线程负责
    - `WebAPIs` 中的各事件管理模块
        - 3个主要的模块
            - DOM模块
            - ajax模块
            - 定时器 setTimeout 模块

- 回调队列：callback queue
    - 消息队列：message queue，也称作任务队列: task queue，或宏队列: macrotask queue
    - 微任务队列: microTask queue，也成为 jobs

- 可进入`消息队列`的异步函数

    |浏览器|nodejs|
    |-|-|
    |setTimeout|setTimeout|
    |setInterval|setInterval|
    |I/O|I/O|
    |requestAnimationFrame|-|
    |UI rendering|-|
    |页面事件（DOM事件）|-|
    |ajax请求|-|
    |-|setImmediate|

- 可进入`微任务队列`的异步函数
    - 可进入的异步函数
        |浏览器|nodejs|
        |-|-|
        |`resolve` 或 `reject` 状态的Promise的回掉函数|`resolve` 或 `reject` 状态的Promise的回掉函数|
        |Object.observe|Object.observe|
        |MutationObserver|MutationObserver|
        |-|process.nextTick|

- 事件轮询，event loop
    - 从回调队列中循环取出函数放到**执行栈**中执行
    - 回调队列中的调用顺序
        1. 微任务队列，队列全部执行完成
        2. 消息队列，每次执行一个

- 微任务队列的问题
    - 事件轮询在访问微任务队列时，需要将微任务队列全部执行完成才能继续其他任务
    - 如果微任务队列在执行过程中不断的添加新任务，将会导致整个画面不可用

### 事件循环模型原理图及说明
[top](#catalog)
- 模型的运行流程
    1. **主线程**负责: 执行初始化代码，将事件、回调函数、ajax请求等交给 `WebAPIs` 中相应的事件管理模块
    2. **分线程**负责: 事件发生时，将处理函数、回调函数等添加到 `callback queue` 中
    3. **主线程**负责: 事件轮询，将 `callback queue` 中的回调函数放入**执行栈**中执行
        - 轮询的流程
            1. 确认 <label style="color:red">执行栈 为空</label>
            1. 先访问 微任务队列，并按顺序执行
            2. 如果执行微任务时，又产生了新的微任务，则添加到微任务队列中
            3. 当微任务队列全部执行完成后，开始访问消息队列
            4. 从消息队列中取出一个回调函数并放入执行栈执行
            5. 执行完成后，再次访问微任务队列，并充分执行 1-5

- 事件代码执行到触发的分析
    - 绑定的事件监听如何启动
        1. 主线程开始执行
            1. `xxx.onYYY = function(){}`，作为初始化代码执行
            2. 执行之后将绑定的处理函数交给 `WebAPIs` 中的DOM事件管理模块
        2. 主线程继续执行其他初始化代码，分线程接管事件
            1. 等待事件触发
            2. 事件触发后，将事件处理函数 入队到 `message queue` 中
        3. 等待主线程执行结束
        4. 等待 `event loop` 事件轮询触发
        5. 由 `event loop` 接管执行
            1. 事件处理函数 处于待执行状态，等待队列前面没有其他任务
            2. 事件处理函数 可以执行，将函数出队并放入执行栈中
        6. 由主线程接管执行
            1. 函数放入执行栈，并开始执行

    - 定时器如何启动
        1. 主线程开始执行
            1. `setTimeout()` 函数作为初始化代码被执行
            2. 代码执行之后，将函数参数中的`回调函数`和`时间`交给 `WebAPIs` 中的定时器管理模块
        2. 主线程继续执行其他初始化代码，分线程接管定时器的触发
            1. 等待时间
            2. 时间到了之后，由定时器管理模块将回调函数 入队到 `message queue` 中
        3. 等待主线程执行结束
        4. 等待 `event loop` 事件轮询触发
        5. 由 `event loop` 接管执行
            1. 回调函数 处于待执行状态，等待队列前面没有其他任务
            2. 回调函数 可以执行，将函数出队并放入执行栈中
        6. 由主线程接管执行
            1. 函数放入执行栈，并开始执行

- **模型原理图**
    ```
            主线程负责                        分线程：由浏览器负责
    ┌───────── JS ─────────┐            ┌───────  WebAPIs  ────────┐
    │            execution │            │                          │
    │   heap       stack   │            │ ┌──────────────────────┐ │
    │ ┌───────┐ ┌────────┐ │            │ │    DOM (document)    │ │
    │ │       │ │ ...... │ │            │ └──────────────────────┘ │
    │ │       │ ├────────┤ │            │ ┌──────────────────────┐ │
    │ │       │ │   fn2  │ ├────────>>> │ │ ajax (XMLHttpRequest)│ │
    │ │ param │ ├────────┤ │            │ └──────────────────────┘ │
    │ │       │ │   fn1  │ │            │      .........           │
    │ │       │ ├────────┤ │            │ ┌──────────────────────┐ │
    │ │       │ │ window │ │            │ │      setTimeout      │ │
    │ └───────┘ └────────┘ │            │ └─────────┬────────────┘ │
    └───────────────┬─  ^  ┘            └───────────┼──────────────┘
                    │   ^                           │
                    │   │                           │
                    │   │ 1. callback pop           │
        event loop  │   │ 2. push callback to       │
                    │   │    execution stack        │
                    V   │ 3. execution callback     V
                    V   │                           V
                   ┌────┴───────────────────────────────────────────────┐
                   │        message queue                               │
                   │  ┌─────────────────────────────────────────┐       │
        callback   │  │ ┌─────────┐ ┌────────┐  ┌────────────┐  │       │
        queue      │  │ │ onClick │ │ onload │  │ setTimeout │  │       │
                   │  │ └─────────┘ └────────┘  └────────────┘  │       │
                   │  └─────────────────────────────────────────┘       │
                   │                                                    │
                   │        microTask queue                             │
                   │  ┌──────────────────────────────────────────────┐  │
                   │  │ ┌────────────────┐  ┌─────────────────┐      │  │
                   │  │ │ Promise.then() │  │ Promise.error() │ ...  │  │
                   │  │ └────────────────┘  └─────────────────┘      │  │
                   │  └──────────────────────────────────────────────┘  │
                   └────────────────────────────────────────────────────┘
    ```

### 浏览器触发事件和js触发事件的不同点
[top](#catalog)
- 浏览器触发事件和js触发事件的不同点
    - 浏览器触发事件后，绑定的事件响应函数直接放入 **消息队列**，并等待事件轮询
    - js触发事件后，如: `domObj.click()`
        - `domObj.click()` 代码会被**放入执行栈**执行，并<label style="color:red">一直在栈底</label>
        - 内部调用绑定的响应函数，并依照绑定顺序，放入执行栈执行
        - 多个响应函数，就相当与多个内部函数，依次入栈执行，执行后出栈
        - 当所有事件的**初始化代码**执行完成后，`domObj.click()` 执行完成，出栈。此时执行栈清空

- 示例参考
    - 参考代码
        - [src/event_model/event_trigger_type.html](src/event_model/event_trigger_type.html)

    - 不同触发方式的结果

        |触发方式|结果|
        |-|-|
        |点击 `btn1` |`1 2 3 4`|
        |执行js代码：`btn1.click()`|`1 3 2 4`|

    - 代码内容
        - html内容
            ```html
            <button id='btn1'>btn1</button>
            <button id='btn2'>btn2</button>
            ```
        - js内容
            ```js
            let btn1 = document.getElementById('btn1')
            btn1.addEventListener('click', ()=>{
                Promise.resolve().then(()=>{console.log(2)})
                console.log(1)
            })
            btn1.addEventListener('click', ()=>{
                Promise.resolve().then(()=>{console.log(4)})
                console.log(3)
            })

            let btn2 = document.getElementById('btn2')
            btn2.addEventListener('click', ()=>{
                // 1. 通过js代码触发点击事件
                btn1.click()
            })
            ```

- 点击 `btn1` 来触发事件
    - 输出结果: `1 2 3 4`
    - 结果分析
        1. 事件轮询 在后台搜索各个队列
        2. 点击按钮后，事件触发。事件管理模块将两个事件的回调函数放入 消息队列
        3. **事件轮询搜索微任务队列**，微任务队列为空
        4. **事件轮询搜索消息队列**， 消息队列中有两个的回调函数
        5. 从消息队列中取出第一个回调函数，放入执行栈
        6. 执行栈执行 `onclick` 的第一个回调函数
            - `Promise.resolve().then(()=>{console.log(2)})`: then中的函数添加到微任务队列
            - `console.log(1)`: **输出 1**
        7. `onclick` 的第一个回调函数执行结束，执行栈清空
        8. **事件轮询搜索微任务队列**，发现有一个 Promise对象 的回调函数，取出并放入执行栈
        9. `()=>{console.log(2)}` 执行，**输出 2**
        10. Promise对象 的回调函数执行结束，执行栈清空
        11. **事件轮询搜索微任务队列**，微任务队列为空
        12. **事件轮询搜索消息队列**，发现 `onclick` 的第二个回调函数，取出并放入执行栈
        13. 执行栈执行 `onclick` 的第二个回调函数
            - `Promise.resolve().then(()=>{console.log(4)})`: then中的函数添加到微任务队列
            - `console.log(3)`: **输出 3**
        14. `onclick` 的第二个回调函数执行结束，执行栈清空
        15. **事件轮询搜索微任务队列**，发现有一个 Promise对象 的回调函数，取出并放入执行栈
        16. `()=>{console.log(4)}` 执行，**输出 4**
        17. Promise对象 的回调函数执行结束，执行栈清空
        18. 所有队列与执行栈清空，代码执行完毕，事件轮询继续搜索

- 点击 btn2，通过js代码：`btn1.click()` 来触发 `onclick` 事件
    - 输出结果: `1 3 2 4`
    - 结果分析
        1. 事件轮询 在后台搜索各个队列
        2. `btn1.click()` 执行，click()函数被放入调用栈
        3. `btn1.click()`在执行栈的栈底，将第一个回调函数放入执行栈
        4. 执行第一个回调函数
            - `Promise.resolve().then(()=>{console.log(2)})`: then中的函数添加到微任务队列
            - `console.log(1)`: **输出 1**
        5. 第一个回调函数执行完毕，出栈
        6. `btn1.click()`在执行栈的栈底，将第二个回调函数放入执行栈
        7. 执行第二个回调函数
            - `Promise.resolve().then(()=>{console.log(4)})`: then中的函数添加到微任务队列
            - `console.log(3)`: **输出 3**
        8. 第二个回调函数执行完毕，出栈
        9. `btn1.click()`执行完毕，出栈
        10. 执行栈清空
        11. 事件队列搜索微任务队列，发现有两个回调函数，取出第一个并放入执行栈
        12. `()=>{console.log(2)}` 执行，**输出 2**
        13. Promise对象 的回调函数执行结束，执行栈清空
        13. 事件队列从微任务队列取出第二个回调函数并放入执行栈
        14. `()=>{console.log(4)}` 执行，**输出 4**
        15. Promise对象 的回调函数执行结束，执行栈清空
        16. 事件轮询发现微任务队列已清空
        17. 事件轮询搜索消息队列，消息队列为空
        18. 代码执行完毕，事件轮询继续搜索

### 与事件循环模型相关的问题
[top](#catalog)
- 函数执行后，如何输出?
    - 代码
        ```js
        for(var i=0; i<10; i++){
            setTimeout(() => {
                console.log("this is 2")
            }, 1000);
        }
        ```
    - 输出结果：同时输出10次，并且输出没有之间没有时间间隔
    - 结果分析
        - for循环 和 setTimeout 作为初始化代码，在js引擎启动后，立刻执行
        - 循环10次，将10个定时器交给 定时器管理模块
        - 初始代码只相当于10次循环并且没有什么延迟，所以10个定时器几乎是同时到达定时器管理模块
        - 时间到达之后，10个定时器几乎同时触发，无间隔的打印10次

- 如何理解定时器的延迟?
    - 参考代码
        - [src/event_model/timer_delay.html](src/event_model/timer_delay.html)
    - 代码内容
        ```js
        var button = document.querySelector("button")
        button.onclick = function(){
            // 一个非常耗时的循环操作
            for(var i=1; i<10000000000; i++){

            }
            console.log("loop end")
        }

        setTimeout(
            function(){console.log("timer end")},
            3000
        )
        ```
    - 执行分析
        1. js引擎启动后，执行初始化代码
            - 执行事件绑定，并将处理函数交给 事件管理模块，等待事件触发
            - 执行定时器代码，并将定时器交给 定时器管理模块，等待3s
        2. 在2s左右点击按钮，触发按钮的`onclick`事件
        3. 事件管理模块将 处理函数 入队到 `callback queue`
        4. 主线程中空闲，`event loop` 发现 `callback queue` 中有一个onclick事件处理函数
        5. onclick事件处理函数被取出，放入执行栈中执行
            - onclick事件非常耗时
        6. 3s时间到，定时器管理模块将回调函数 入队到 `callback queue`
        7. onclick事件非常耗时一直占用执行栈，导致主线程繁忙，定时器回调函数开始等待`event loop` 的发现
        8. onclick事件执行完毕，`event loop` 的发现 `callback queue` 有一个定时器回调函数，将其出队并放入执行栈中执行
        9. 定时器回调函数 执行，但是已经超过了3s
    - 结果分析
        - 因为所有的回调函数、事件响应函数都要放到 `callback queue` 中，并等待 `event loop` 的发现
        - 所以如果在定时器处理函数之前，有一个很耗时的操作，那么定时器本身将会产生很大的延迟
        - 本质上不是定时器管理模块的问题，该模块几乎不会有延迟。是 `callback queue` 中其他任何的耗时操作导致 定时器处理函数 一直在等待执行机会，在外部看来就像定时器产生了延迟

## H5_Web_Workers多线程
[top](#catalog)
- Web Workers 是 HTML5 提供的一个javascript多线程解决方案
- 可以将一些耗时的运算交给 Web Workers 运行而不冻结用户界面
- 产生的子线程仍然完全受主线程控制，并且**不能操作DOM**，所以新的标准并没有改变JS单线程执行的本质
    - 因为在分线程中无法看到 window对象
    - 如果分线程可以操作DOM，则说明浏览器支持多线程操作页面，那样又会产生 [JS是单线程执行的](#JS是单线程执行的) 中描述的问题

- Web Workers 的优点
    - 不会冻结页面

- Web Workers 的缺点
    - 无法操作 window对象
    - 无法操作DOM
    - 无法跨域加载js代码
    - 速度慢

- 使用方法
    - 创建在分线程中执行的js文件
        1. 创建一个 `onmessage` 函数对象
        2. 在函数内部，通过 `event.data` 获取主线程发送的数据
        3. 执行相关代码
        4. 将运行结果通过 `postMessage` 将结果发送给主线程
    - 在线程中的js中发消息并设置回调函数
        1. 将需要运算的代码移动到某个js文件中
        2. 创建 `Worker` 对象：`var worker = new Worker("js文件路径")`
        3. 向分线程发送消息：`worker.postMessage(参数)`
        4. 注册分线程的结果响应函数`worker.onmessage = function(event){...}`

- 在主线程和分线程中，都可以通过 `event.data` 属性来获取数据

- 示例
    - 参考代码
        - 主线程：[src/web_workers/base.html](src/web_workers/base.html)
        - 分线程：[src/web_workers/feibo.js](src/web_workers/feibo.js)
    - 主线程
        ```js
        var button = document.querySelector("button")
        button.onclick = function(){
            var numInput = document.getElementById("numInput")
            var value = parseInt(numInput.value)
            if (value === Number.NaN){
                return
            }

            console.log("click")
            // 3. 创建一个Worker对象，即创建一个分线程
            var worker = new Worker("feibo.js")
            // 4. 向分线程发送参数
            worker.postMessage(value)
            // 5. 注册响应处理函数
            worker.onmessage = function(event){
                console.log(event.data)
            }
        }
        ```
    - 分线程
        ```js
        function feibo(n){
            return n <=2? 1: feibo(n-1) + feibo(n-2)
        }

        var onmessage = function (event){
            var num = event.data
            console.log("this is onmessage")
            var result = feibo(num)
            postMessage(result)
        }
        ```

# 其他
## 代码结尾的分号
[top](#catalog)
- 每条js语句结尾可以不加分号
- 称谓
    - 语句结尾加分号 `;`，称为: **表达式语句**
    - 语句结尾不加分号 `;`，称为: **表达式**
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

## 类数组对象的for遍历的性能问题
[top](#catalog)
- 示例
    ```js
    var list = document.getElementsByName("...")
    for (var i = 0; i<list.length; i++){
        ...
    }
    ```
- 遍历时产生的问题
    - 类数组对象中实际上没有 `lenght` 属性，**每次获取时都要重新计算**
        - 按照示例的编码方式，会重新计算多次，导致额外的性能消耗
    - Array实例对象与类数组对象不同，内部真实存在 `length` 属性，并且是定值。
        - 每次获取时，不需要重新计算
- 解决方法
    - 通过 for 来遍历类数组对象之前，将 `length` 保存在变量中
    - 示例
        ```js
        var list = document.getElementsByName("...")
        for (var i = 0, length = list.length; i<length; i++){
            ...
        }
        ```

## alert
[top](#catalog)
- 可以暂停当前主线程的执行
- 点击弹出框的按钮后，恢复主线程的执行

## 变量函数的目标查找方法
[top](#catalog)
- 查找前要分清this、执行
- 由this，或者对象调用，则在this、对象及其原型链上查找中查找
- 直接调用
    1. 先看当前的执行上下文对象中有哪些内容
    2. 如果没有找到，则根据作用域链向上找，直到window对象
