<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》

### 目录--JS的动态语言特性
- [对象与值类型之间的转换](#对象与值类型之间的转换)
    - [包装类](#包装类)
- [](#)

# 对象与值类型之间的转换
## 包装类
[top](#catalog)
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

- 包装类对象的特性
    1. `typeof(obj) === 'function' / 'object'`
    2. `obj instanceof Object === true`