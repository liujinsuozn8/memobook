<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》

### 目录--JS的动态语言特性
- [对象与值类型之间的转换](#对象与值类型之间的转换)
    - [包装类--值到对象的转换](#包装类--值到对象的转换)
    - [对象到值的转换](#对象到值的转换)
- [](#)

# 对象与值类型之间的转换
## 包装类--值到对象的转换
[top](#catalog)
- 包装类存在的意义
    - 用于实现: <span style='color:red'>在值类型上调用对象方法</span>

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

- 值类型与对应的包装类型**不是对象与类型的关系**
    ```js
    'abcd' instanceof String === false
    ```

- `new Boolean()`、`new Number()`、`new String()` 可以创建对应类型的包装类
    - 无法直接使用 `new Symbol()`，只能通过 `Symbol()` 创建符号数据
- 显示包装
    - `new Object(...)`，可以将: boolean、number、string、symbol 包装成对应的包装类型
        ```js
        var arr = [true, 22, 'abc', Symbol()];
        arr.map(n => new Object(n)).forEach(n=>console.log(typeof obj, obj));
        // object [Boolean: true]
        // object [Number: 22]
        // object [String: 'abc']
        // object [Symbol: Symbol()]
        ```

- 隐式包装
    - 隐式包装的触发条件
        - 通过存取运算符: `[]`、`.` 使用属性，如
            ```js
            var a = 1234;
            a.toString();
            ```
        - `for...of/in`、`with`
    - 隐式包装触发时，引擎会用值数据创建一个临时包装对象；在使用结束后，会自动销毁临时包装对象
        - 因为存在临时对象，所以值类型的方法调用被<span style='color:red'>临时隔离在另外的一个对象中完成</span>
        - 对临时对象的操作不会影响原来的值，如
            ```js
            var str = 'abcd';
            // 1. 执行 .toString 时，会创建临时包装对象
            str.toString = function(){
                return `this is toString`;
            };

            // 2. console.log内部会调用 toString 方法
            // 3. 再次调用 toString 会创建新的临时包装对象，与 1 中创建的对象完全无关
            console.log(str);   // 输出: abcd
            console.log(str.toString());    // 输出: abcd
            ```

## 对象到值的转换
[top](#catalog)
- 对象到值的隐式转换规则
    - null
        - null是对象: `typeof null === 'object'`
        - null 的默认转换规则
            - null 总是转换为三种`值类型`: `0`、`'null'`、`false`
    - null 以外的其他对象都会转换为 `Boolean` 的 `true`
    - 对象 ---> 字符串数字的转换
        - 对象 ---> 字符串 的两种转换尝试的顺序
            1. `toString()`
            2. `valueOf()`
        - 对象 ---> 数字 的两种转换尝试的顺序
            1. `valueOf()`
            2. `toString()`
        - 转换的结果一定是一个 `值类型` 的数据
            - 如果无法返回 `值类型`，会抛出异常
        - 如何尝试两种方法？
            1. 第一次尝试时，如果返回了一个 `非值类型的对象`，会尝试第二个方法
            2. 如果第二个方法也返回了一个 `非值类型的对象`，则抛出异常
        - 抛出异常的示例
            ```js
            var objStr = new String('abcd');
            objStr.toString = function() {
                console.log('run toString');
                return {}
            };
            objStr.valueOf = function() {
                console.log('run valueOf');
                return {}
            };

            // 通过 + 运算符，将对象转换为数值
            console.log(+objStr)
            // 输出:
            // run valueOf
            // run toString

            // 抛出异常
            // TypeError: Cannot convert object to primitive value
            ```
        - `toString`、`valueOf` 可以返回 null，默认会被转换为 `0`
            ```js
            var objStr = new String('abcd');
            objStr.toString = function() {
                console.log('run toString');
                return null;
            };
            objStr.valueOf = function() {
                console.log('run valueOf');
                return null;
            };

            console.log(+objStr)
            // 输出:
            // run valueOf
            // 0
            ```
        - 对象 ---> 值 的**隐式转换条件**
            - 运算符两侧有一个对象类型的数据
- 对于值类型，如果运算符两侧的数据类型不同，不会隐式调用 `toString`、`valueOf`
    - 即隐式调用只对包装类有效
    - 示例
        ```js
        String.prototype.valueOf = function(){
            console.log('valueOf');
            return 0;
        }
        String.prototype.toString = function(){
            console.log('toString');
            return this;
        }

        // 相当于执行了: var x = new String('5'); x.valueOf()
        console.log('5'.valueOf()); 
        // valueOf
        // 输出number 0
        console.log('5');   // 输出string 5
        // 没有隐式创建包装对象，所以不会调用 valueOf

        console.log(1 + new String('6'));
        // valueOf
        // 输出number 1
        console.log(1 + +'6');  // 输出number 7
        // +'6' 会将 '6' 转换为数值6 

        console.log(1 + '2'); // 输出string 12
        ```
- 特例
    - `+` 无法转换符号类型
