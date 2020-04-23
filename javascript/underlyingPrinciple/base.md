<span id="catalog"></span>

- js基础总结
    - 数据类型
        - 数据类型的分类
        - 数据类型的判断
        - 数据类型的相关问题
    数据、变量、内存
    对象
    函数
函数
面向对象
线程机制与事件机制

### 目录
- [js基础总结](#js基础总结)
    - [数据类型](#数据类型)
        - [数据类型的分类](#数据类型的分类)
        - [数据类型的判断](#数据类型的判断)
        - [与数据类型相关的问题](#与数据类型相关的问题)
- [](#)


# js基础总结
## 数据类型
### 数据类型的分类
[top](#catalog)
- 数据类型的
- 基本数据（**值**）类型

    |类型|值|
    |-|-|
    |String|任意字符串|
    |Number|任意数字|
    |Boolean|true/false|
    |Null|null|
    |Undefined|undefined|

- 对象数据（**引用**）类型
    |类型|值|
    |-|-|
    |Object|任意对象|
    |特殊对象：Function|一种特殊的对象，**可以执行**|
    |特殊对象：Array|<ul><li>一种特殊的对象</li><li>对象中的属性是**数值下标**</li><li>内部的数值是**有序的**</li></ul>|

### 数据类型的判断
[top](#catalog)
- (反射方法)判断数据类型的方法及其使用的数据类型

    |方法|功能|适用的数据类型|备注|
    |-|-|-|-|
    |typeof|获取变量的类型，返回一个字符串|Undefined、String、Number、Boolean、Function|<ul><li>返回的结果都是对应类型，并且第一个字母小写</li><li>无法判断 Null 、Array、 Object，因为都会返回 object (对于null，这是一个bug)</li></ul>|
    |===|比较 类型 + 值|Null、Undefined|只适用与Null 和 Undefined 类型，因为这两种类型都只有一个值|
    |instanceof|用于判断对象是不是某个类的实例，即判断具体的类型||

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

## 与数据类型相关的问题
[top](#catalog)
1. undefined 与 null 的区别
    - undefined代表已定义，未赋值
    - null 代表 已定义，已赋值，且值为 null
    - 
2. 什么时候给变量赋值为null

3. 严格区分变量类型与数据类型

