<span id="catalog"></span>
- 参考
    - 《JavaScript语言精髓与编程实战》
    - https://zhuanlan.zhihu.com/p/120383551

### 目录--JS的面向对象特性
- [可以被符号属性影响的行为](#可以被符号属性影响的行为)
    - [已公布的符号属性](#已公布的符号属性)
    - [隐式创建对象--有影响的符号属性](#隐式创建对象--有影响的符号属性)
    - [通用行为--有影响的符号属性](#通用行为--有影响的符号属性)
    - [集合对象--有影响的符号属性](#集合对象--有影响的符号属性)
    - [字符串--有影响的符号属性](#字符串--有影响的符号属性)


# 可以被符号属性影响的行为
[top](#catalog)
- Symbol.species
    - 功能
        - 在一些方法执行时，会创建一个新的对象
    - 隐式创建对象的过程
        1. 创建对象时，会找到对象`constructor`
        2. 然后找到 `constructor` 上的 `Symbol.species`
        3. 尝试获取 `Symbol.species` 的返回值
            - 应该返回一个对象的构造函数获取类
        4. 用返回值创建对象
    - 属性的设置方法
        - 函数--直接设置在函数对象上
            ```js
            function Foo(){}
            Foo[Symbol.species]
            ```
        - 类声明--设置静态的getter
            ```js
            class Foo{
                static get [Symbol.species](){
                    return ...
                }
            }
            ```
    - 支持该属性的类

        |类|影响的对象方法/行为|基于的对象|
        |-|-|-|
        |RegExp|str.split(rx, ...)|rx|
        |Promise|promise.then()|promise|
        |Array|arr.concat()|arr|
        |Array|arr.filter()|arr|
        |Array|arr.map()|arr|
        |Array|arr.slice()|arr|
        |Array|arr.splice()|arr|
        |TypedArray|new TypedArray(srcTypedArray)|srcTypedArray|
        |TypedArray|x.set(srcTypedArray)|srcTypedArray|
        |TypedArray|typedArr.subarray()|typedArr|
        |TypedArray|typedArr.filter()|typedArr|
        |TypedArray|typedArr.map()|typedArr|
        |TypedArray|typedArr.slice()|typedArr|
        |TypedArray|typedArr.splice()|typedArr|
        |ArrayBuffer|buff.slice()|buff|
        |SharedArrayBuffer|buff.slice()|buff|
    - 示例
        - 参考代码
            - [src/oop/property/symbol/species.js](src/oop/property/symbol/species.js)
        - 代码内容
            1. 函数--直接设置在函数对象上
                ```js
                // 1.1 设置构造函数原型继承 Array
                function Bar(...args){
                    Array.call(this);
                    for(let n of args){
                        this.push(n);
                    }
                }
                Object.setPrototypeOf(Bar.prototype, Array.prototype);
                Bar[Symbol.species] = function(){
                    // 1.2 隐式创建对象时，使用 Array 作为构造器创建对象
                    return Array;
                }
                var bar = new Bar(1,2,3,4,5);
                console.log(bar);   // Bar { '0': 1, '1': 2, '2': 3, '3': 4, '4': 5, length: 5 }
                // 1.3 调用map方法，触发隐式创建对象，返回一个由 Array 作为构造函数创建的对象
                var barArr = bar.map(v=>v);
                console.log(barArr);    // [ 1, 2, 3, 4, 5 ]
                console.log(barArr instanceof Bar);     // false
                console.log(barArr instanceof Array);   // true
                ```
            2. 类声明--设置静态的getter
                ```js
                // 2.1 创建一个继承 Array 的子类
                class Foo extends Array{
                    static get [Symbol.species](){
                        // 2.1 隐式创建对象时，使用 Array 作为构造器创建对象
                        return Array;
                    }
                }

                var foo = new Foo(1, 2, 3, 4);
                // 2.2 调用map方法，触发隐式创建对象，返回一个由 Array 作为构造函数创建的对象
                var newArr = foo.map(v=>v)
                console.log(newArr)  // [ 1, 2, 3, 4 ]
                console.log(newArr instanceof Foo);     // false
                console.log(newArr instanceof Array);   // true
                ```js

## 已公布的符号属性
[top](#catalog)
- JS公布了一组面向内部机制的符号属性，来访问内部槽
- 符号属性被声明在对象的 `自有属性表`
- 通过符号属性来影响内部机制**有很大的局限性**
- 已公布的11个符号属性
    - 影响通用行为的符号属性

        |Symbol.xxx属性|影响行为|属性值|
        |-|-|-|
        |species|隐式创建对象|function|
        |hasInstance|obj instance target|function(obj){}|
        |unscopables|with(target)|{keyName:booleanValue,...}|
        |toStringTag|target.toString()|string|
        |toPrimitive|值运算|function(hint){}|
        |iterator|for...of<br>new CollectionTypes(target)<br>Promise.allOrRace(target)<br>arr.from(target)|迭代函数?????|

    - 与数组、字符串相关的属性

        |Symbol.xxx属性|影响行为|属性值|
        |-|-|-|
        |isConcatSpreadable|arr.concat(target)|true/false|
        |mathc|str.match(target)<br>str.endsWith(target,...)<br>str.includes(target,...)<br>str.startWith(target,...)<br>new RegExp(target,...)|function(str){}|
        |replace|str.replace(target,newString)|function(str,newString){}|
        |search|str.search(target)|function(str){}|
        |split|str.split(target, limit)|function(str,limit){}|

## 隐式创建对象--有影响的符号属性
[top](#catalog)



## 通用行为--有影响的符号属性
[top](#catalog)
- Symbol.toStringTag
    - 只对原生的 `String.prototype.toString()` 有效
    - 声明方式
        - 方式1: 对象字面量--属性赋值
            ```js
            {
                [Symbol.toStringTag]: '.....'
            }
            ```
        - 方式2: 对象字面量--使用存取描述符 getter
            ```js
            {
                get [Symbol.toStringTag](){
                    return '...';   // 返回一个字符串
                }
            }
            ```
        - 方式3: 类声明--使用存取描述符 getter
            ```js
            class Foo{
                get [Symbol.toStringTag](){
                    return '...'
                }
            }
            ```
    - 示例
        - 参考代码
            - [src/oop/property/symbol/toStringTag.js](src/oop/property/symbol/toStringTag.js)
        - 代码内容
            ```js
            // 1. 方式1: 直接在字面量中声明属性
            var obj = {
                name:'testName',
                age:22,
                [Symbol.toStringTag]: 'testToStringTag',
            }
            console.assert(obj.toString() === '[object testToStringTag]');

            // 2. 方式2: 使用存取描述符 getter 来声明属性，并返回一个字符串
            var obj02 = {
                name:'testName',
                age:22,
                get [Symbol.toStringTag](){
                    return 'testToStringTag'
                }
            }
            console.assert(obj02.toString() === '[object testToStringTag]');

            // 3. 方式3: 类声明--使用存取描述符 getter
            class Foo{
                name
                age
                constructor(name, age){
                    this.name = name;
                    this.age = age;
                }
                get [Symbol.toStringTag](){
                    return 'testToStringTag'
                }
            }
            var foo = new Foo('bob', 33);
            console.assert(foo.toString() === '[object testToStringTag]');
            ```

- Symbol.toPrimitive
    - toPrimitive 指向的函数在对象转换为值数据时被调用
    - `function(hint){}` 中的 `hint` 表示需要转换的值类型
        - `hint`的值及其触发条件

            |值|触发条件|
            |-|-|
            |`'number'`|符号运算: `+obj`, `-obj`|
            |`'string'`|模板字符串 : console.log(`${obj}`)|
            |`'default'`|空字符串: `''+obj`, 字符串`'asdf'+obj`, 数字`1234+obj`|

        - `default` 表示 number、string 都可以
        - `hint`不需要手动传递。在转换时，会根据所处的环境自动传递参数
    - 声明方式
        - 对象字面量--属性赋值
            ```js
            var obj = {
                [Symbol.toPrimitive](hint) {
                }
            }
            ```
        - 类声明--实例方法
            ```js
            class Foo{
                [Symbol.toPrimitive](hint) {
                }
            }
            ```
    - 示例
        - 参考代码
            - [src/oop/property/symbol/toPrimitive.js](src/oop/property/symbol/toPrimitive.js)
        - 代码内容
            ```js
            // 1. 对象字面量--属性赋值
            var obj = {
                name: 'testName',
                age: 22,
                [Symbol.toPrimitive](hint) {
                    console.log(hint)
                    switch (hint) {
                        case 'string':
                            return this.name;
                        case 'number':
                            return this.age;
                        default:
                            return `name:${this.name},age:${this.age}`
                    }
                }
            }

            console.log(+obj);          // number, 22
            console.log(`${obj}`);      // string, testName
            console.log('1234' + obj);  // default, 1234name:testName,age:22
            console.log(234 + obj);     // default, 234name:testName,age:22

            // 2. 类声明--实例方法
            class Foo{
                name
                age
                constructor(name, age){
                    this.name = name;
                    this.age = age;
                }
                // 声明类方法
                [Symbol.toPrimitive](hint) {
                    console.log(hint)
                    switch (hint) {
                        case 'string':
                            return this.name;
                        case 'number':
                            return this.age;
                        default:
                            return `name:${this.name},age:${this.age}`
                    }
                }
            }

            var foo = new Foo('bob', 33)
            console.log(+foo);          // number, 33
            console.log(`${foo}`);      // string, bob
            console.log('1234' + foo);  // default, 1234name:bob,age:33
            console.log(234 + foo);     // default, 234name:bob,age:33
            ```

- Symbol.hasInstance
    - haInstance 指向一个函数
    - 执行 `obj instanceof target` 时会自动调用
    - hasInstance 的返回值就是 `instanceof` 操作的返回值
    - `Function.prototype` 上默认包含该属性，所以所有函数、类可以执行 `instanceof` 操作
    - 声明方式
        - 对象--对象属性
        - 函数--需要通过 `Object.defineProperties()`来覆盖原型对象的属性描述符
            - `Function.prototype[Symbol.hasInstance]` 的属性描述符
                ```js
                  [Symbol(Symbol.hasInstance)]: {
                    value: [Function: [Symbol.hasInstance]],
                    writable: false,
                    enumerable: false,
                    configurable: false
                }
                ```
            - 函数原型对象上的属性无法修改，无法添加，所以只能在子类对象上覆盖
        - 类声明--静态函数
    - 示例
        - 参考代码
            - [src/oop/property/symbol/haInstance.js](src/oop/property/symbol/haInstance.js)
        - 代码内容
            1. 对象--对象属性
                ```js
                var a = {};
                a[Symbol.hasInstance] = function (obj) {
                    console.log('Object hasInstance');
                    // 检查对象是否包含 className 属性，并且是否等于 MyFn
                    return obj.className === 'FnA';
                }

                var b = { className: 'FnA' };
                console.log(b instanceof a);    // true
                ```
            2. 函数--需要通过 `Object.defineProperties()`来覆盖原型对象的属性描述符
                ```js
                // 检查原型对象的属性描述符
                // console.log(Object.getOwnPropertyDescriptors(Function.prototype))
                function MyFn() {}
                // 添加符号属性的属性描述符
                Object.defineProperties(MyFn, {
                    [Symbol.hasInstance]:{
                        value: function (obj) {
                            console.log('MyFn hasInstance')
                            // 检查对象是否包含 className 属性，并且是否等于 MyFn
                            return obj.className === 'MyFn';
                        }
                    }
                });
                var myfn = new MyFn();
                console.log(myfn instanceof MyFn);  // false
                var other = { className: 'MyFn' };
                console.log(other instanceof MyFn); // true

            3. 类声明--静态函数
                ```js
                class Foo {
                    static [Symbol.hasInstance](obj) {
                        console.log('Foo hasInstance')
                        // 如果是 Foo 的对象则返回 true
                        // 或者对象中包含 className 自动并且等于 Foo
                        return super[Symbol.hasInstance](obj) || (obj && obj['className'] === 'Foo')
                    }
                }

                // 3.1 测试类示例
                var foo = new Foo();
                console.log(foo instanceof Foo);    // true

                // 3.2 测试对象中包含 className 属性，并且等于 Foo
                var obj = { className: 'Foo' };
                console.log(obj instanceof Foo);   // true

                // 3.3 测试对象中包含 className 属性，但是不等于 Foo
                var obj2 = { className: 'Other' };
                console.log(obj2 instanceof Foo);  // false
                ```

## 集合对象--有影响的符号属性
[top](#catalog)
- Symbol.isConcatSpreadable
    - isConcatSpreadable 需要是一个 true/false 的值
    - 只有对象是一个数组或类数组的[集合对象](#集合对象)时，该属性才有意义
    - 该属性决定了arr.concat 的处理方式
        - true: 将当前集合对象**循环取出、循环添加** 到另一个集合中
        - false: 将当前集合对象**视为一个整体** 添加到另一个集合中
    - isConcatSpreadable 与 展开运算符 `...` 的区别
        1. isConcatSpreadable 只作用于 `arr.concat` 方法
        2. `arr.concat` 的过程是对目标对象的下标存取
        3. 展开运算符 `...` 只对有迭代器的对象有效
    - 示例
        - 参考代码
            - [src/oop/property/symbol/isConcatSpreadable.js](src/oop/property/symbol/isConcatSpreadable.js)
        - 代码内容
            ```js
            var obj = [1,2,3,4];
            // 默认会展开元素
            var arr1 = [0].concat(obj);
            console.log(arr1);  // [ 0, 1, 2, 3, 4 ]
            console.log(arr1.length);   // 5

            // 设置为不展开元素
            obj[Symbol.isConcatSpreadable] = false;
            var arr2 = [0].concat(obj);
            console.log(arr2);   // [ 0, [ 1, 2, 3, 4, [Symbol(Symbol.isConcatSpreadable)]: false ] ]
            console.log(arr2.length);   // 2
            ```

## 字符串--有影响的符号属性
[top](#catalog)
- Symbol.split
    - 用于修改字符串 `split` 方法的行为
    - 需要添加到 `split(separator)` 的 `separator` 参数对象中才能生效
    - 当拥有 `Symbol.split` 属性的对象作为`split()`的参数时，会改为调用 `Symbol.split` 指向的函数
        - 该函数的返回值会作为 `split()` 的返回值
    - 示例
        - 参考代码
            - [src/oop/property/symbol/split.js](src/oop/property/symbol/split.js)
        - 代码内容
            ```js
            var separator = {};
            separator[Symbol.split] = function(str, limit){
                // 该函数的返回值做为 split() 的返回值
                return str.split(' ', limit);
            }

            var str = 'hello world';
            console.log(str.split(separator));
            ```

- Symbol.match, Symbol.replace, Symbol.search
    - 与`Symbol.split` 类似
    - 需要添加到方法的参数对象中，在方法待用时，改为执行符号属性指定的方法
- Symbol.match 的特殊性 ?????
    - 当 Symbol.match 不是 undefined，并且可以转换为 true 时，会影响 `str.match()` 以外的其他行为
        - 这种情况下，JS尝试将对象理解为一个正则表达式
        - `endsWith()`、`includes()`、`startWith()`，将不接受这样的对象作为第一个参数
            - 这些方法无法接受正则表达式
    - `new RegExp()` 可以将包含 Symbol.match 属性的普通对象模拟为正则表达式
        - 示例
            - 参考代码
                - [src/oop/property/symbol/match_RegExp.js](src/oop/property/symbol/match_RegExp.js)
            - 代码内容
                ```js
                var obj = {
                    source: 'abc.*',
                    flags: 'i',
                    [Symbol.match]: true
                }

                // 1. 将普通对象模拟为正则表达式
                var rx = new RegExp(obj);
                // 2. 以正则表达式的方式使用对象
                console.log(rx) // /abc.*/i
                console.log(rx.test('12345abcde')); // true
                ```

- Symbol.species

- <span style='color:red'>为什么 `str.split(正则表达式对象)` 可以正常执行? </span>
    - `RegExp.prototype` 上有一些符号属性，包括
        ```js
        // console.log(Object.getOwnPropertyDescriptors(RegExp.prototype));

        [Symbol(Symbol.match)]: {
            value: [Function: [Symbol.match]],
            writable: true,
            enumerable: false,
            configurable: true
        },
        [Symbol(Symbol.matchAll)]: {
            value: [Function: [Symbol.matchAll]],
            writable: true,
            enumerable: false,
            configurable: true
        },
        [Symbol(Symbol.replace)]: {
            value: [Function: [Symbol.replace]],
            writable: true,
            enumerable: false,
            configurable: true
        },
        [Symbol(Symbol.search)]: {
            value: [Function: [Symbol.search]],
            writable: true,
            enumerable: false,
            configurable: true
        },
        [Symbol(Symbol.split)]: {
            value: [Function: [Symbol.split]],
            writable: true,
            enumerable: false,
            configurable: true
        }
        ```
    - RegExp.prototype 上的符号属性改变了字符串方法的行为，所以正则表达式对象可以改变字符串方法的行为