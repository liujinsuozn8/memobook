<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》

### 目录--JS的动态语言特性
- [类型转换的两个阶段](#类型转换的两个阶段)
- [对象与值类型之间的转换](#对象与值类型之间的转换)
    - [包装类--值到对象的转换](#包装类--值到对象的转换)
    - [对象到值的隐式转换](#对象到值的隐式转换)
        - [隐式转换的总体规则](#隐式转换的总体规则)
        - [null的隐式转换规则](#null的隐式转换规则)
        - [对象的隐式转换规则](#对象的隐式转换规则)
        - [转换的预期](#转换的预期)
        - [Boolean的隐式转换](#Boolean的隐式转换)
        - [Symbol.toPrimitive替换隐式转换](#Symbol.toPrimitive替换隐式转换)
        - [值类型在运算时不会调用包装类的方法](#值类型在运算时不会调用包装类的方法)
    - [显示转换](#显示转换)
    - [String()、Number()、Boolean()、Symbol()的两大功能](#String()、Number()、Boolean()、Symbol()的两大功能)
- [值类型的转换](#值类型的转换)
    - [什么时候会发生值类型的转换](#什么时候会发生值类型的转换)
    - [值类型之间的转换规则](#值类型之间的转换规则)
        - [值类型转换表](#值类型转换表)
        - [undefined的转换](#undefined的转换)
        - [number的转换](#number的转换)
        - [boolean的转换](#boolean的转换)
        - [string的转换](#string的转换)
        - [symbol的转换](#symbol的转换)
    - [值类型之间的显示转换](#值类型之间的显示转换)
        - [显示转换为number](#显示转换为number)
        - [显示转换为string](#显示转换为string)
        - [显示转换为undefined](#显示转换为undefined)
        - [显示转换为boolean](#显示转换为boolean)
- [对象与数组的动态特性](#对象与数组的动态特性)
    - [普通数组的动态特性](#普通数组的动态特性)
    - [TypedArray--类型化数组的动态特性](#TypedArray--类型化数组的动态特性)
    - [类数组对象的动态特性](#类数组对象的动态特性)
- [重写](#重写)
    - [标识符的重写](#标识符的重写)
    - [原型重写](#原型重写)
    - [构造器重写](#构造器重写)
    - [对象成员的重写](#对象成员的重写)
    - [重写的限制](#重写的限制)
- [eval--动态执行](#eval--动态执行)
- [](#)

# 类型转换的两个阶段
[top](#catalog)
1. `引用 --> 值`，将对象转换为原始值
    - 由隐式调用控制，包括方法
        - `valueOf`、`toString`
        - `Symbol.toPrimitive` （用于替换上面两个方法）
    - 在第一阶段，必须保证参与运算的是值类型
2. 将所得值数据，转换为当前运算需要的类型
    - 如 `+` 加法运算，对于左右两个操作数
        - 在第一阶段后，如果没有字符串，则全部转换为**数值**，然后运算
        - 在第一阶段后，如果有字符串，则全部转换为**字符串**，然后做字符串连接
    - 示例
        ```js
        var a;
        var b = {
            valueOf(){
                return "test str";
            }
        }

        var c = a + b;
        // 1. 先对 b 进行隐式转换，调用 valueOf 得到 `test str`
        // 2. 右操作数为字符串，加法运算
        console.log(c); // undefinedtest str
        ```

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

## 对象到值的隐式转换
### 隐式转换的总体规则
[top](#catalog)
1. 任何对象，包括 Boolean 对象，在进行布尔运算时，都作为**对象**使用
2. 进行数值、字符串运算时，使用 `valueOf`、`toString` 转换为值数据使用
3. 如果 `valueOf`、`toString` 都没有返回值数据，则抛出异常
4. 如果对象有 `Symbol.toPrimitive` 方法，将会替换 `valueOf`、`toString`
    - 如果 `Symbol.toPrimitive` 没有返回值数据，则抛出异常

### null的隐式转换规则
[top](#catalog)
- null是对象: `typeof null === 'object'`
- null 的默认转换规则
    - null 总是转换为三种`值类型`: `0`、`'null'`、`false`
- null 以外的其他对象都会转换为 `Boolean` 的 `true`

### 对象的隐式转换规则
[top](#catalog)
- 对象**隐式转换条件**
    - 运算符两侧有一个**对象类型**的数据
- 隐式转换的目标: `基本类型`

- 对象 ---> 字符串 的两种转换尝试的顺序
    1. `toString()`
    2. `valueOf()`
- 对象 ---> 数字 的两种转换尝试的顺序
    1. `valueOf()`
    2. `toString()`
- <span style='color:red'>转换的结果一定是一个 `值类型` 的数据，如果无法返回 `值类型`，会抛出异常</span>
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

### 转换的预期
[top](#catalog)
- 转换的预期，即如何确定转换为 number、string
    - 相当于如何确定 `valueOf()`、`toString()` 的隐式调用顺序

- <span style='color:red'>转换的预期只有两种，即: number、string</span>

- 部分运算符、函数调用没有预期的运算对象

- 在没有预期的情况下，数值优先:
    - 调用顺序
        1. 先使用 `valueOf()`
        2. 再使用 `toString()`
- `+` 加法运算的规则
    - 字符串优先
        - 如果操作数中包含字符串，优先进行字符串连接
    - 运算规则
        1. 将左右两个操作数当作数值
        2. 如果操作数中有对象，则优先调用对象的 `valueOf()` 方法
        3. 如果 `valueOf()` 没有返回 值数据，再调用 `toString()` 方法
        4. 如果转换结果中，包含字符串，则优先执行字符串连接
        5. 如果都是数值，则进行数值运算
    - 运算规则需要分成两部分
        1. 将操作数从对象转换为值类型
        2. 检查转换后的操作数中有没有字符串，并优先进行字符串连接
    - 特例
        - `+` 无法转换符号类型
    - 示例
        - 参考代码
            - [src/dynamic/type_conversion/plus_exception.js](src/dynamic/type_conversion/plus_exception.js)
        - 代码内容
            ```js
            var x = {
                toString() { return '23' },
                valueOf() { return 45 }
            };

            var y = {
                toString() { return '66' },
            };

            // 1. 优先对 x 调用 valueOf
            console.log(1 + x);     // 46

            // 2. 优先对 x 调用 valueOf。操作数中包含字符串，优先执行字符串连接
            console.log("1" + x);   // 145
            console.log(x + "1");   // 451

            /*
                3. 优先对 y 调用 valueOf，但是y没有 valueOf
                    会调用 toString()，返回 "66"
                    操作数中包含字符串，优先执行字符串连接
            */
            console.log(1 + y);     // 166
            ```

- `+` 符号运算的规则
    - 无法确定预期，会优先调用 `valueOf()`，然后再调用 `toString()`
    - 如果返回的结果是字符串，会尝试转换成数值
    - 示例
        - 参考代码
            - [src/dynamic/type_conversion/sign_conversion.js](src/dynamic/type_conversion/sign_conversion.js)
        - 代码内容
            ```js
            var x = {
                toString() { return '23' },
                valueOf() { return 45 }
            };

            var y = { toString() { return '66' } };

            var z = {valueOf(){ return 'abcd' }};

            var newX = +x;
            var newY = +y;
            var newZ = +z;

            console.log(`newX=${newX}，typeof newX = ${typeof newX}`);
            console.log(`newY=${newY}，typeof newY = ${typeof newY}`);
            console.log(`newZ=${newZ}，typeof newZ = ${typeof newZ}`);

            // 输出:
            // newX=45，typeof newX = number
            // newY=66，typeof newY = number
            // newZ=NaN，typeof newZ = number
            ```

### Boolean的隐式转换
[top](#catalog)
- 默认的转换结果
    - null、0、空字符串、undefined 的转换结果为 `false`
    - 其他的转换结果为 `true`
- 特例: `new Boolean(false)`
    - `!! new Boolean(false)` 会返回 `true`
        - 因为所有对象都作为对象处理，忽略的本身的boolean值，所以返回 `true`
    - 与字符串、数值进行运算时，返回 `false`
    - 示例
        ```js
        var a = new Boolean(false);
        console.log(!!a);   // true

        console.log(+a);    // 0

        console.log(1 + a); // 1

        console.log(`a = ${a}`); // a = false
        ```

### Symbol.toPrimitive替换隐式转换
[top](#catalog)
- `Symbol.toPrimitive` 的优先级比 `valueOf`、`toString` 高
- 如果为对象添加了 `Symbol.toPrimitive` 属性
    1. 不使用 `valueOf`、`toString` 方法，只使用 `Symbol.toPrimitive`
    2. 如果 `Symbol.toPrimitive` 返回了对象，会抛出异常
        - 相当于 `valueOf`、`toString` 都返回了对象

### 值类型在运算时不会调用包装类的方法
[top](#catalog)
- 值类型在运算时
    1. 不会主动调用包装类的 `valueOf`、`toString` 方法
        - 即: 不会发生隐式调用
        - 即: 隐式调用只对**包装类对象**有效
    2. 会转换为运算、操作的预期类型

- 示例
    - 参考代码
        - [src/dynamic/type_conversion/value_conversion.js](src/dynamic/type_conversion/value_conversion.js)
    - 代码内容
        ```js
        String.prototype.valueOf = function(){
            console.log('valueOf');
            return 100;
        }
        String.prototype.toString = function(){
            console.log('toString');
            return this;
        }

        // 1. 相当于执行了: var x = new String('5'); x.valueOf()
        console.log('5'.valueOf());
        // 输出:
        // valueOf
        // 100   <<<<< number 类型

        // 2. 没有隐式创建包装对象，所以不会调用 valueOf
        console.log('5');
        // 输出:
        // 5    <<<<< string 类型

        // 3. 加法运算会隐式调用包装类型对象的 valueOf
        console.log(1 + new String('6'));
        // 输出:
        // valueOf
        // 101   <<<<< number 类型

        // 4. 符号运算会隐式调用包装类型对象的 valueOf
        console.log(1 + +new String('6'));
        // 输出:
        // valueOf
        // 101   <<<<< number 类型

        // 5. +'6' 会将 '6' 转换为数值6，不会调用valueOf方法
        console.log(1 + +'6');
        // 输出:
        // 7    <<<<< number 类型


        /*
            6. '2' 值类型不会调用 valueOf 方法
                加法运算中字符串连接优先，所以会得到字符串 12
        */
        console.log(1 + '2');
        // 输出:
        // 12   <<<<< string 类型
        ```

## 显示转换
[top](#catalog)
- 通过包装类型构造函数的**函数调用**来执行显示转换，包括
    - `Object(x)`
    - `Number(x)`
    - `String(x)`
    - `Boolean(x)`
    - `Symbol(x)`

- 调用 `Object(x)` 时
    1. 如果 x 是对象，则返回 x
    2. 如果 x 是值数据，返回以 x 为值的对象
    3. 最终一定会返回一个对象

- `Number(x)`、`String(x)`、`Boolean(x)`、`Symbol(x)`
    - 这四个转换函数都有<span style='color:red'>明确的转换预期类型</span>
    - 最终一定会返回一个值类型数据
    - 如果 x 是对象，会进行<span style='color:red'>隐式转换</span>，并返回 **转换预期类型** 的值数据
        - 将会调用 `valueOf`、`toString` 方法
    - 如果 x 是值类型，进行类型转换，并返回 **转换预期类型** 的值数据
    - 示例
        ```js
        var x = Object(123);
        console.log(x);             // [Number: 123]
        console.log(String(x));     // 123
        console.log(1 + Number(x)); // 124
        console.log(Boolean(x));    //true
        console.log(Boolean(0));    // false
        ```

- `Boolean(new Boolean(false))` 特例
    - 内部的 `new Boolean(false)` 被当作对象，忽略了 `false`，所以整体返回 `true`

## String()、Number()、Boolean()、Symbol()的两大功能
[top](#catalog)
1. 显示提供了: `任意类型数据--->值类型数据` 的转换
2. 作为包装类，实现了: `值类型数据--->对象` 的转换

# 值类型的转换
## 什么时候会发生值类型的转换
[top](#catalog)
- JS中数据的类型不是由变量声明决定，数据的类型将会延迟到绑定一个数据时才能确定

- `运算符`导致类型转换
    - 最终参与运算的一定是值类型
        - 所以在运算之前会发生 `引用--->值` 的转换
    - 根据具体操作确定转换的预期类型，然后调用 `valueOf`、`toString`，或 `Symbol.toPrimitive`

- 语句或语义导致类型转换
    - `if语句`、`while()` 一定会将表达式的结果转换为boolean
    - 对象声明时的: `标识符--->字符串` 的转换
    - `obj.property` 使用对象属性时，也存在 `标识符--->字符串` 的转换
        - JS在语法解析阶段，会将 `.property` 部分转换为**字符串**，并保存在解析结果中
    - `x['property']` 不存在类型转换
        - `'property'` 的合法性将会在执行期检测
        - `['property']` 也成为 `计算属性`
    - `switch...case` 中使用 `===` 进行比较
        - 将会优先执行类型检测，不会执行类型转换

## 值类型之间的转换规则
### 值类型转换表
[top](#catalog)

|原始\转换<br>类型\目标|undefined|symbol|number|boolean|string|
|-|-|-|-|-|-|
|undefined|-|-|`NaN`|`false`|`'undefined'`|
|symbol|-|-|抛出异常<br>TypeError|symbol ---> true|symbol ---> `'Symbol(描述信息)'`|
|number|-|-|-|`0-->false`<br>`非0-->true`|Number.NEGATIVE_INFINITY --> `'-Infinity'`<br>Number.POSITIVE_INFINITY --> `'Infinity'`<br>window.Infinity、global.Infinity --> `'Infinity'`<br>window.NaN、global.NaN --> `'NaN'`<br>|
|boolean|-|-|true ---> 1<br>false ---> 0|-|true ---> `'true'`<br>false ---> `'false'`|
|string|-|-|数字、一个小数点、`E|e` --> 10进制数<br>`0x|0X` + `[0-9A-Fa-f]` --> 16进制数<br>其他 ---> NaN|`''` ---> false<br>非`''` --> true|-|

### undefined的转换
[top](#catalog)
- undefined --> N
    - <span style='color:red'>任何值都不能转换为 undefined</span>

### number的转换
[top](#catalog)
- N ---> number 时，如果无法获得有效数值，返回 `NaN`
    - `NaN` 可以进行运算
    - `NaN` 的目的是使表达式尽量求值，不再抛出异常

- number ---> boolean
    - 0 ---> false
    - 非0 ---> true

- number ---> string
    - 一些特殊值将被转换为普通字符串
        - Number.NEGATIVE_INFINITY --> `'-Infinity'`
        - Number.POSITIVE_INFINITY --> `'Infinity'`
        - window.Infinity、global.Infinity --> `'Infinity'`
        - window.NaN、global.NaN --> `'NaN'`

### boolean的转换
[top](#catalog)
- boolean ---> number
    - true ---> 1
    - false ---> 0
- boolean ---> string
    - true ---> `'true'`
    - false ---> `'false'`

### string的转换
[top](#catalog)
- string ---> boolean
    - `''` ---> false
    - 非`''` --> true
- string ---> number
    - 字符串转换成 10 进制数
        - 字符串由 : `数字 + 至多一个小数点`，指数标识`E|e` 组成
        - JS倾向与将字符串转换成数字，即使包含指数标识`E|e`
        - **字符串开头的 0 会被自动省略**
    - 字符串转换成 16 进制数
        - 字符串由 : `0x`、`0X` 开头，`0~9`、`A~F`、`a~f` 字符组成
        - 并且不包含小数点
    - 无法处理8进制数
    - 示例
        - 参考代码
            - [src/dynamic/type_conversion/number/string_to_number.js](src/dynamic/type_conversion/number/string_to_number.js)

        - 代码内容
            ```js
            // 1. 直接输出 8 进制数值字面量
            var a = 012;
            console.log(a); // 10

            // 2. 尝试将 8 进制数值字符串转换为数字
            var b = '012';
            console.log(+b);    // 12
            // 无法转换，会被当作 10 进制数处理

            // 3. 处理包含小数的 10 进制数值字符串
            // 开头的多个 0 将会被省略
            var c = '00123.45';
            console.log(+c);    // 123.45

            // 4. 处理 16 进制数值字符串
            var d = '0x1234';
            console.log(+d);    // 4660

            // 5. 处理包含指数标识的数值字符串
            // JS会将指数标识展开成普通数值
            var e = '1.23e4';
            console.log(+e);    // 12300
            ```

### symbol的转换
[top](#catalog)
- symbol ---> number
    - 无法转换
    - `Number(Symbol())` 会抛出异常
        ```
        TypeError: Cannot convert a Symbol value to a number
        ```
- symbol ---> string
    - 转换成字符串: `'Symbol(描述信息)'`
        ```js
        var a = String(Symbol('abcd'))
        console.log(a);         // Symbol(abcd)
        console.log(typeof a);  // string
        ```
- symbol ---> boolean
    - 只能转换为 `true`

- N ---> symbol
    - 所有类型都不能转换成 symbol 类型，也没有转换的方法
    - `Symbol(arg)`
        - 函数中的 `arg` 不会产生 任意类型转换为 symbol 的效果
        - `arg` 只会作为这个 symbol 数据的描述信息

- 使用 `Object()` 包装 symbol 数据
    - `Object(symbol)` 会返回一个符号对象，并且支持 `instanceof` 检测
    - 可以用 `instanceof` 检测为 `Object` 或 `Symbol` 类型
    - 包装后的数据 与 原始 symbol 数据是相等的`==`，但不全等`===`
    - 示例
        ```js
        var a = Symbol();
        var b = Object(a);
        console.log(a instanceof Object);   // false
        console.log(a instanceof Symbol);   // false
        console.log(b instanceof Object);   // true
        console.log(b instanceof Symbol);   // true

        console.log(a == b);    // true
        console.log(a === b);   // false
        ```

## 值类型之间的显示转换
### 显示转换为number
[top](#catalog)
- string ---> number
    - 两种转换方法
        - `parseInt('str', radix)`
        - `parseFloat('str', radix)`
    - `parseInt` 无法处理指数
    - radix 表示进制，可以使用 `[2~36]` 内的数
        - 如果 `radix === 16`，无论是否包含前缀`0x` 或 `0X`，都按照 16 进制处理
        - 如果 `radix === 8`，无论是否包含前缀`0`，都按照 8 进制处理
        - 如果不指定 radix
            - `0x` 或 `0X` 开头按照 16 进制处理
            - 其他按照 10 进制处理，且无法识别 8 进制
    - 只转换开头的有效部分
        - 如果字符串只有开头部分是数字，则**只处理开头的数值**，后面的部分省略
    - 如果开头不是有效数字，则返回 `NaN`
    - 示例
        - 参考代码
            - [src/dynamic/type_conversion/number/parseInt.js](src/dynamic/type_conversion/number/parseInt.js)
        - 代码内容
            ```js
            // 1. 只能处理字符串中开头部分的有效数字
            console.log( parseInt('1234px') );  // 1234

            // 2. 如果字符串开头没有有效数字，返回 NaN
            console.log( parseInt('q12345') );  // NaN

            // 3. 指定 16 进制处理，不设置 前缀 0x
            console.log( parseInt('12', 16) );  // 18

            // 4. 指定 8 进制处理，不设置 前缀 0
            console.log( parseInt('12', 8) );   // 10

            // 5. 不指定 radix，可以处理 16 进制数字
            console.log( parseInt('0x012') );   // 18

            // 6. 不指定 radix，无法处理 8 进制数字
            console.log( parseInt('012') );     // 12
            ```
- `parseInt`、`parseFloat` 的问题: **预期参数为字符串**
    - 即使参数是 number，也会先尝试将 number 转换为 string
    - 即: 先调用 `toString`，再调用 `valueOf`
    - <span style='color:red'>如果参数不是字符串，最好不要使用这两个方法</span>
    - 示例
        - 参考代码
            - [src/dynamic/type_conversion/number/parseInt_number.js](src/dynamic/type_conversion/number/parseInt_number.js)
        - 代码内容
            ```js
            var obj = {
                toString(){
                    return '2e12';
                },
                valueOf(){
                    return 3e10;
                }
            }

            // 1. parseInt、parseFloat 预期参数是字符串
            // 会先调用 toString
            console.log( parseInt(obj) );   // 2
            console.log( parseFloat(obj) ); // 2000000000000

            // 2. Number 预期类型数值，会先调用 valueOf
            console.log( Number(obj) );     // 30000000000
            ```

### 显示转换为string
[top](#catalog)
- 其他类型转换为 string 的主要方法
    1. `toXXX` 方法
        - JS中的很多对象都提供了一组 `toXXX` 方法，将当前对象转换为字符串
    2. `toJSON()` 方法
        - 调用 `JSON.stringify(obj)` 时，或自动调用该方法，获得JSON字符串
        - 所有对象都可以重写 `toJSON()`
- number ---> string
    - number类型 `toString([radix])` 方法扩展
        - 可以指定数字转换成字符串时，使用的进制
    - `toXXX` 方法

        |方法|功能|
        |-|-|
        |`toFixed(小数位数)`|设置小数位数，小数位数不足补0|
        |`toExponential(小数位数)`|使用指数计数法，并设置小数位数，小数位数不足不0|
        |`toPrecision(3)`|设置有效位数<ul><li>整数位数 < 有效位数，在添加小数位，并在小数位补0</li><li>整数位数 = 有效位数，返回原始数字的字符串</li><li>整数位数 > 有效位数，使用指数计数法</li></ul>|
    - 示例
        - 参考代码
            - [src/dynamic/type_conversion/string/exhibit_number_to_string.js](src/dynamic/type_conversion/string/exhibit_number_to_string.js)
        - 代码内容
            ```js
            // 1. 将数值转换为指定进制的字符串
            console.log( (1234).toString(8) );  // 2322

            // 2. 设置小数位数，小数位数不足补0
            console.log( (123456.7891).toFixed(5) );    // 123456.78910

            // 3. 使用指数计数法，并设置小数位数，小数位数不足不0
            console.log( (12.34).toExponential(5) );    // 1.23400e+1

            // 4. 设置有效位数
            // 4.1 整数位数 < 有效位数，在添加小数位，并在小数位补0
            console.log( (12).toPrecision(4) );     // 12.00
            // 4.2 整数位数 = 有效位数，返回原始数字的字符串
            console.log( (12).toPrecision(2) );     // 12
            // 4.3 整数位数 > 有效位数，使用指数计数法
            console.log( (1234.567).toPrecision(3) );     // 1.23e+3
            ```

- string ---> string
    - `toXXX` 方法

        |方法|功能|
        |-|-|
        |toUpperCase|大写转换|
        |toLowerCase|小写转换|
        |toLocaleUpperCase|使用本地格式转换为大写|
        |toLocaleLowerCase|使用本地格式转换为小写|
    - `toLocaleXXX` 在多语言环境、不同的内码中会有不同的结果

- Date ---> string
    - `toXXX` 方法

        |方法|功能|
        |-|-|
        |toGMTString|转换为格林尼治标准时间的日期字符串|
        |toUTCString|转换为协调世界时的日期字符串|
        |toISOString|转换为ISO 8601标准的日期字符串|
        |toDateString|将日期部分转换为**默认格式**的字符串|
        |toTimeString|将时间部分转换为**默认格式**的字符串|
        |toLocaleDateString|宿主环境下的日期|
        |toLocaleTimeString|宿主环境下的时间|
        |toJSON|使用`JSON.stringify`时，调用的方法|

    - 示例
        - 参考代码
            - [src/dynamic/type_conversion/plus_exception.js](src/dynamic/type_conversion/plus_exception.js)
        - 代码内容
            ```js
            var date = new Date();
            // 1. toGMTString，转换为格林尼治标准时间的日期字符串
            console.log( date.toGMTString() );          // Tue, 25 Aug 2020 06:53:57 GMT

            // 2. toUTCString，转换为协调世界时的日期字符串
            console.log( date.toUTCString() );          // Tue, 25 Aug 2020 06:53:57 GMT

            // 3. toISOString，转换为ISO 8601标准的日期字符串
            console.log( date.toISOString() );          // 2020-08-25T06:53:57.440Z

            // 4. toDateString，将日期部分转换为默认格式的字符串
            console.log( date.toDateString() );         // Tue Aug 25 2020

            // 5. toTimeString，将时间部分转换为默认格式的字符串
            console.log( date.toTimeString() );         // 14:53:57 GMT+0800 (GMT+08:00)

            // 6. toLocaleDateString，宿主环境下的日期
            console.log( date.toLocaleDateString() );   // 2020-8-25

            // 7. toLocaleTimeString，宿主环境下的时间
            console.log( date.toLocaleTimeString() );   // 14:53:57

            // 8. toJSON，使用`JSON.stringify`时，调用的方法
            console.log( JSON.stringify(date) );    // "2020-08-25T06:58:33.768Z"

            // 9. 重新设置 toJSON 方法
            date.toJSON = function(){
                var month = this.getMonth() + 1;
                return `${this.getFullYear()}${month < 10? '0' + month: month}${this.getDate()}`;
            }
            console.log( JSON.stringify(date) );    // 20200825
            ```

### 显示转换为undefined
[top](#catalog)
- 可以通过 `void` 将表达式的运算结果转换成 `undefined`
- 最好使用 `void( 表达式 )` 的形式，否则可能会无法得到 `undefined`
- 示例
    ```js
    var a = void (6 + 5);
    console.log(a); // undefined
    console.log(a === undefined);   // true
    ```

### 显示转换为boolean
[top](#catalog)
- 可以使用 `!! 数据` 的方式，显示转换为 boolean
- `!!` 与 `Boolean(数据)` 的功能一致，都是显示转换为boolean，但是 `Boolean` 语义更明确
- 显示转换时，最好使用 `Boolean(数据)` 的方式

# 对象与数组的动态特性
## 普通数组的动态特性
[top](#catalog)
- 索引数组与关联数组
    - 索引数组: 以`序数类型`作为下标变量，存取元素
    - 关联数组: 以`非序数类型`作为下标变量，存取元素
- 普通数组的下标必须是 `值类型`
    - 引用类型，包括object、function，会被转换为 string
    - undefined 会被转换为 string
- 普通数组**动态特性**表现为: `索引数组,` 实际是通过 `关联数组` 实现的
    - 为什么通过 `关联数组` 实现的?
        - 只有值类型: boolean **序数的**
        - 其他值类型: string、number、symbol，都是**非序数的**
            - number 类型在JS中表现为**浮点数**，所以不是序数的
        - number不是序数的，只能通过 `关联数组实现`

    - `关联数组` 的实现导致
        1. 数组不拥有一个连续分配的空间
        2. 数组的内存是动态分配的，有需求才分配
            - 即使初始化了一个很大的数组也不会有存储问题，只会在插入元素时分配空间

    - 在迭代数组元素时，会遍历每一个索引，即使当前索引上没有元素
        - 绝大多数数组方法都会迭代每一个索引

    - 在迭代对象成员时，只会访问 `自由属性表`
        - pop、push、shift、unshift 等少量方法会迭代对象成员
        - 因为这些方法只需要操作确定位置上的元素

- 普通数组类似于一个对象
    - 数组实例的 `自有属性表` 中的成员
        - 数组的每个索引
        - `length` 属性
    - 对象属性名变成了索引
        - 通过索引获取数据本质就是一个获取对象属性的过程
    - number、数字string都可以作为索引
        - 通过 `[index]`、或者 `["index"]` 都可以获取到数组成员
        - 数组对象的对索引的**预期**是 string
    - 示例
        ```js
        var a = ['a', 'b', 'c']
        var b = {
            toString(){
                console.log('toString')
                return 1;
            },
            valueOf(){
                console.log('toString')
                return 1;
            }
        }
        console.log(a['1']);    // b
        console.log(a[1]);      // b

        console.log(a[b]);
        // toString
        // b

        console.log(Object.getOwnPropertyDescriptors(a));
        // {
        //     '0': { value: 'a', writable: true, enumerable: true, configurable: true },
        //     '1': { value: 'b', writable: true, enumerable: true, configurable: true },
        //     '2': { value: 'c', writable: true, enumerable: true, configurable: true },
        //     length: { value: 3, writable: true, enumerable: false, configurable: false }
        // }
        ```

## TypedArray--类型化数组的动态特性
[top](#catalog)
- TypedArray的索引是独立的、是由可扩展的外部数据存取接口来实现的
- `length` 属性
    - 继承自原型
    - 是不可写的，所以 TypedArray 是长度固定的
    - 当类型化数组关联的 ArrayBuffer 解绑时，`length` 属性会变为0

## 类数组对象的动态特性
[top](#catalog)
- 一般对象与数组的区别: 缺少`length`属性
- 有 `length` 属性的对象被称为 `类数组对象`
- 类数组对象**的动态特性**表现为
    1. 包含 `length` 属性，并且可以进行修改
    2. 可以通过 `Array.prototype.xxx.call(类数组对象)` 的方式使用数组方法
        - 调用是，数组方法会自动维护类数组对象中的 `length` 属性
    3. 无法被迭代，除非手动添加 `Symbol.iterator`
        - 类数组对象添加了 `Symbol.iterator` 后，可以作为 Map、Set、TypedArray、ArrayBuffer 等集合的数据源
- 示例
    ```js
    // 为对象添加 length 和 Symbol.iterator，转换为类数组对象
    var obj = {
        length: 0,
        [Symbol.iterator] : Array.prototype[Symbol.iterator]
    };
    // 调用数组方法
    Array.prototype.push.call(obj, ...'abcd');

    console.log(obj);
    // 输出:
    // {
    //     '0': 'a',
    //     '1': 'b',
    //     '2': 'c',
    //     '3': 'd',
    //     length: 4,
    //     [Symbol(Symbol.iterator)]: [Function: values]
    // }

    for(let n of obj) console.log(n)
    // 输出:
    // a
    // b
    // c
    // d
    for(let i in obj) console.log(i)
    // 输出:
    // 0
    // 1
    // 2
    // 3
    // length
    ```

# 重写
## 标识符的重写
[top](#catalog)
- 重写的要点
    - JS的重写是一个**代码执行期**的行为
    - 数据绑定的规则: 执行时绑定
        - 这导致了语法分析期几乎**不会分析被操作数的类型**
    - let/const 声明的标识符，无法重写

- 标识符从声明到赋值的过程
    1. var、let、const 在语法分析期值得到标识符名
    2. 得到可访问的标识符
        - let、const 通过 `创建绑定 Create Binding` 将标识符名变成可访问的标识符
        - var 会被提升，并赋值为 `undefined`
    3. 创建绑定
        - 两种绑定方式
            - 持久绑定--const，不可重写
                - 因为 const 标识符不可重写，所以<span style='color:red'>在声明时必须赋值</span>
            - 可变绑定--let/var，可重写
    - var 本质上是赋值操作，只是为了兼容 ES6，所以也被称为绑定

- 代码执行前的重写
    1. var声明、函数声明、类声明、形参中的默认值
        - var声明绑定 undefined
        - 函数声明会被提升
        - 形参会使用默认值
    2. 模块的导入导出

- 赋值操作导致的重写
    - 两个概念
        1. 左手端表达式，lsh，left-hand side expression
        2. 右手端表达式，rsh，right-hand side expression
    - 如 `a = b`
        - 左侧的 `a` 是 lsh
        - 右侧的 `b` 是 rsh
    - 在赋值操作中
        - lsh 负责 **取引用** 操作
        - rsh 负责 **取值** 操作
    - 在赋值时，lsh需要是某个数据的引用，**不能是字面量**
        - 如果直接对某个字面量进行操作会产生异常
            ```js
            i++
            ```
- 对象内部方法对重写的影响
    - 当lhs是对象的属性时，赋值操作将调用对象的 `[[Set]]`

- 非赋值操作导致的重写
    - try...catch 中，展开异常对象时，不能重写被展开的内容
        - 因为这些展开内容相当于是用 `let` 创建的
        - 如
            ```js
            try{
                throw {msg:'msg01', code:123};
            }catch({msg, code}){
                console.log(e);
                var msg = 1234; // SyntaxError: Identifier 'msg' has already been declared
                console.log(e);
            }
            ```
    - `for(var a in obj)` 的for表达式中，不会每次循环都创建新的作用域，所以会产生重写

- 在if处理中，可以重写 var 变量和函数, 但是无法修改 `let\const\class`

- undefined 可以重写但是不会生效
    - undefined 是 global 的一个属性，null、false、true 等不是
    - undefined的属性描述符
        ```js
        console.log(Object.getOwnPropertyDescriptor(global, 'undefined'));

        {
            value: undefined,
            writable: false,
            enumerable: false,
            configurable: false
        }
        ```
    - 在非严格模式下，undefined 可以重写但是无效
        - 因为 `writable` 是 `false`

## 原型重写
[top](#catalog)
- 原型重写会导致
    1. 旧的实例使用旧的原型， 但是实例的 `constructor` 仍然指向创建它的类/构造器
    2. 新的实例使用新的原型

- 示例
    ```js
    function Foo(){}
    var x = new Foo();

    Foo.prototype = new Object();
    Foo.prototype.constructor = Foo;

    var y = new Foo();

    console.log(x instanceof Foo);  // false，Foo使用了新的原型
    console.log(y instanceof Foo);  // true

    console.log(x.constructor === Foo); // true，仍然使用旧的原型，并且 constructor 的指向不变
    console.log(y.constructor === Foo); // true
    ```

## 构造器重写
[top](#catalog)
- `Object` 可以被重写
    - 重写后不会影响 `new XXX()` 的对象创建方式
    - 因为 `Object.prototype` 是由引擎内置的原生的对象，不会受重写的影响
- **重写构造器不会影响到引擎的内置特性**
- 可以为新构造器、原生构造器建立原型链
    ```js
    var x = 'abc';
    console.log(x.name) // undefined

    // 创建新的原型
    var NewStringPrototype = {name:'new test'};
    // 获取原生原型对象
    var NativeStringPrototype = Object.getPrototypeOf(String.prototype);
    // 创建新的原型链
    // String.prototype.__proto__ ---> NewStringPrototype.__proto__ ---> NativeStringPrototype
    Object.setPrototypeOf(NewStringPrototype, NativeStringPrototype);
    Object.setPrototypeOf(String.prototype, NewStringPrototype);

    var y = '1234'
    console.log(x.name);    // new test
    console.log(y.name);    // new test
    ```

## 对象成员的重写
[top](#catalog)
- 成员重写本质上是更新自有属性描述符
- `Object.defineProperty()` 本质就是在重写属性

- 成员重写的检查方法
    - 检查成员是否是重写的
        - 检查方法
            1. 当前对象上**有**属性 `prop`
            2. 对象的原型链上**也有**属性 `prop`
        - 示例
            ```js
            function isRewrote(obj, prop) {
                // 1. 当前对象上有属性 `prop` && 2. 对象的原型链上也有属性 `prop`
                return obj.hasOwnProperty(prop) && (prop in Object.getPrototypeOf(obj));
            }
            ```
    - 检查成员是否是从原型继承来的
        - 检查方法
            1. 当前对象上**没有**属性 `prop`
            2. 对象的原型链上**有**属性 `prop`
        - 示例
            ```js
            function isInherited(obj, prop) {
                // 1. 当前对象上**没有**属性 `prop` && 2. 对象的原型链上**有**属性 `prop`
                return !obj.hasOwnProperty(prop) && (prop in Object.getPrototypeOf(obj));
            }
            ```

## 重写的限制
[top](#catalog)
- this不能被重写
- this、super、new 等关键字可以作为对象属性名
- 语句中的重写
    - `for...in` 会**暂存对象的引用**，将导致
        - 循环体中，对对象的重写不会影响`for...in`中对象的引用
            ```js
            for( var k in obj ){
                obj = '...' // 不会影响 for( var k in obj ) 中的obj引用
            }
            ```
        - 循环时，可以向对象中添加属性，但是无法保证能否遍历到
            - 因为无法确定引擎是以何种顺序来遍历的
    - `switch(obj)` 会**暂存对象的引用**，来与每个 case 进行匹配
        - 在 switch 作用域内对 obj 的修改不会影响被保存的引用
- 异常中的重写
    - 主要问题1: 在 `try` 中，如果有 `return x`，是否执行 `finally`?
        - 会执行 `finally`
        - 执行流程
            1. 执行 `try`
            2. 执行到 `return x` 时，**执行该表达式**，得到 `x`
            3. 返回 `x` 的动作被 `finally` 挂起
            4. `finally` 执行后继续执行返回处理，将 `x` 返回
    - 主要问题2: 在 try 中，如果执行 `return x`，在 finally 中能否修改 x
        - 不能修改，**值类型的值**，**引用类型的引用**都不能修改
        - 但是可以修改引用类型的成员
        - 原因
            - `finally` 会挂起返回处理，但挂起之前，`return x` 表达式已经运算完成了，`finally` 无法修改

# eval--动态执行
[top](#catalog)
- eval表示的是一个动态的语句执行系统
- 动态执行的两个阶段
    1. 动态装载
    2. 动态执行，即 eval
- `eval('字符串')`，只接受一个 `string`，不接受其他类型，包括`new String()`
    - 如果参数是 `string`，会执行代码片段，返回值是代码片段的返回值
    - 如果参数不是 `string`，会直接将参数返回
    - 示例
        ```js
        var evalResult01 = eval('var a=100');
        console.log(evalResult01);  // undefined
        console.log(a);             // 100

        // 使用字符串对象创建代码文本
        var evalResult02 = eval( new String('var b=200') );
        // eval 直接将返回了原始对象
        console.log(evalResult02);  // [String: 'var b=200']
        console.log(b);             // ReferenceError: b is not defined
        ``` 
- 当eval 作为一般函数调用时，this是当前作用域中可引用的this
    - 包括在箭头函数和 `with(obj)` 中的this
- eval总是使用当前函数的闭包
- eval的运行模式与所在的环相同，但是如果eval内使用了严格模式，则 eval 在严格模式下执行
- eval 中无法直接处理对象字面量，需要套在`()` 内部，否则`{a:b}`中的 `:` 会被当作标签声明
    ```js
    eval("( {a:111, b:222} )")
    ```

[top](#catalog)