<span id="catalog"></span>

### 目录
- [Typescript简介](#Typescript简介)
- [构建Typescript开发环境](#构建Typescript开发环境)
- [ts数据类型](#ts数据类型)
    - [ts变量的声明语法](#ts变量的声明语法)
    - [ts中可用的数据类型](#ts中可用的数据类型)
    - [布尔类型boolean](#布尔类型boolean)
    - [数字类型number](#数字类型number)
    - [字符串类型string](#字符串类型string)
    - [数组类型array](#数组类型array)
    - [元组类型tuple](#元组类型tuple)
    - [枚举类型enum](#枚举类型enum)
    - [任意类型any](#任意类型any)
        - [任意类型any的基本使用](#任意类型any的基本使用)
        - [任意类型的适用场景](#任意类型的适用场景)
    - [null和undefined](#null和undefined)
        - [null的基本特性](#null的基本特性)
        - [undefined的基本特性](#undefined的基本特性)
        - [null、undefined与其他类型混合使用](#null、undefined与其他类型混合使用)
    - [void类型](#void类型)
    - [never类型](#never类型)
- [函数](#函数)
    - [函数声明](#函数声明)
    - [函数参数](#函数参数)
        - [有参函数](#有参函数)
        - [可选参数](#可选参数)
        - [默认参数](#默认参数)
        - [可变参数](#可变参数)
        - [对象类型参数的属性及类型约束](#对象类型参数的属性及类型约束)
    - [函数重载](#函数重载)
    - [箭头函数](#箭头函数)
- [类](#类)
    - [定义类](#定义类)
    - [类的继承](#类的继承)
    - [类属性的修饰符](#类属性的修饰符)
    - [静态属性与静态方法](#静态属性与静态方法)
    - [抽象类和抽象方法](#抽象类和抽象方法)
- [接口](#接口)
    - [属性接口](#属性接口)
    - [函数类型接口](#函数类型接口)
    - [可索引接口](#可索引接口)
    - [类类型接口](#类类型接口)
    - [接口间的继承](#接口间的继承)
- [泛型](#泛型)
    - [泛型函数](#泛型函数)
    - [泛型类](#泛型类)
    - [泛型函数接口](#泛型函数接口)
    - [泛型接口的实现类](#泛型接口的实现类)
- [模块](#模块)
- [装饰器](#装饰器)
    - [装饰器说明](#装饰器说明)
    - [属性装饰器](#属性装饰器)
    - [方法装饰器](#方法装饰器)
    - [方法参数的装饰器](#方法参数的装饰器)
- [](#)

# Typescript简介
[top](#catalog)
- ts是js的超集
    ```
    ┌───────────────────────┐
    │   Typescript          │
    │    ┌───────────────┐  │
    │    │     ES6       │  │
    │    │   ┌────────┐  │  │
    │    │   │   ES5  │  │  │
    │    │   └────────┘  │  │
    │    └───────────────┘  │
    └───────────────────────┘
    ```
- ts遵循最新的ES5、ES6规范，并扩展了ES语法
- ts更像后端的**面向对象语言**
- ts是为了优化开发过程与管理，真正的问题仍然需要由js本身来解决

# 构建Typescript开发环境
[top](#catalog)
1. 安装typescript
    1. 需要安装nodejs
    2. 安装ts，**安装后最好重启terminal**
        ```sh
        # 安装包
        npm install -g typescript

        # 检查ts版本
        tsc -v
        ```

2. 将ts文件编译为es5语法
    - 如果未启动 ts 的即使编译，每次修改文件都需要手动重新编译
    - 编译指令
        ```sh
        tsc <ts文件路径>
        ```
    - 编译后，会出现一个同名的js文件

3. 开启ts文件的即使编译
    1. 在开发目录下执行指令，生成 `tsconfig.json` 文件
        ```sh
        tsc --init
        ```

    2. 修改文件的属性，保持编译后的目录结构
        ```js
        // 可以修改输出目录到 `js` 目录下
        "outDir": "./js/",

        // 输出到 js/ 目录时，保持当前代码的目录结构
        // 否则会忽略目录结构，全部编译结果都会生成到js目录下
        "rootDir": "./",
        ```

    3. 启动ts文件的即时编译
        - <label style='color:red'>直接通过指定启动</label>
            ```sh
            tsc -p <开发目录>/tsconfig.json --watch
            ```
        - 在vscode下，通过工具栏启动
            - 启动步骤
                1. 选择 `terminal`/终端
                2. 选择 `run task`/执行任务
                3. 选择 `typescript`
                4. 选择 `tsc:watch` / `tsc:监视`
                5. vscode会创建一个新的terminal，并开启即使编译
            - 如果nodejs的环境变量有问题，或者是通过zip包使用nodejs时，无法通过vscode工具栏启动

# ts数据类型
## ts变量的声明语法
[top](#catalog)
- 声明变量语法
    ```js
    // 声明变量时，必须指定类型
    // 也可以使用 var 声明变量
    let 变量名:类型 = 变量值;

    // 变量值为 undefined
    let 变量名:类型;
    ```
- 如果类型已经确定，则变量类型不能被修改

## ts中可用的数据类型
[top](#catalog)
- ts中的数据类型
    - 布尔类型, boolean
    - 数字类型, number
    - 字符串类型, string
    - 数组类型, array
    - 元组类型, tuple
    - 枚举类型, enum
    - 任意类型, any
    - null
    - undefined
    - void
    - never

## 布尔类型boolean
[top](#catalog)
- boolean 只有两种值: `true`、`false`
- 示例
    - 参考代码
        - [src/syntax/datatype/boolean.ts](src/syntax/datatype/boolean.ts)
    - 代码内容
        ```ts
        let b: boolean = true;
        ```

## 数字类型number
[top](#catalog)
- number型 同时包含整数和浮点数
- 示例
    - 参考代码
        - [src/syntax/datatype/number.ts](src/syntax/datatype/number.ts)
    - 代码内容
        ```ts
        let n1: number = 12;
        let n2: number = 12.0;
        let n3: number = 12.210;

        console.log(n1); //输出: 12
        console.log(n2); //输出: 12
        console.log(n3); //输出: 12.21
        ```

## 字符串类型string
[top](#catalog)
- 示例
    - 参考代码
        - [src/syntax/datatype/string.ts](src/syntax/datatype/string.ts)
    - 代码内容
        ```ts
        let s1: string='testStr1';
        let s2: string='testStr2';
        console.log(s1); // 输出: testStr1
        console.log(s2); // 输出: testStr2
        ```
## 数组类型array
[top](#catalog)
- 声明数组时，指定元素类型的两种方式
    ```ts
    // 方式1: 直接设置元素类型
    let 变量名:类型[] = 数组值;

    // 方式2: 通过泛型的方式设置类型
    let 变量名:Array<类型> = 数组值;
    ```
- 示例
    - 参考代码
        - [src/syntax/datatype/array.ts](src/syntax/datatype/array.ts)
    - 代码内容
        ```ts
        // 1. 直接设置元素类型
        let arr1: string[] = ['aaa', 'bbb', 'ccc'];
        console.log(arr1); // 输出: [ 'aaa', 'bbb', 'ccc' ]

        // 2. 通过泛型设置元素类型
        let arr2: Array<number> = [1,2,3,4,5,6]
        console.log(arr2); // 输出: [ 1, 2, 3, 4, 5, 6 ]
        ```

## 元组类型tuple
[top](#catalog)
- 元素的特性
    - 元素属于数组的一种
    - 元组可以指定每个元素的类型，和元素的数量
    - 元组每个位置的元素类型与该位置的声明类型必须相同
- 声明方式
    ```ts
    let a:[类型1, 类型2, ....] = [ 类型1的值, 类型2的值, ....]
    ```
- 编译时的处理
    - 元组在编译时，会检查每个元素的属性是否与声明的类型相同
    - 元素的类型固定只用于编译期
    - 在编译结果中，仍然是普通的js数组
- 示例
    - 参考代码
        - [src/syntax/datatype/tuple.ts](src/syntax/datatype/tuple.ts)
    - 代码内容
        ```ts
        let t1: [boolean, number, string] = [true, 12, 'aaa'];
        console.log(t1);    // 输出: [ true, 12, 'aaa' ]
        ```
    - 编译后的内容
        ```js
        "use strict";
        var t1 = [true, 12, 'aaa'];
        console.log(t1);
        ```

## 枚举类型enum
[top](#catalog)
- 声明枚举对象
    - 3中声明方式
        - 方式1: 为每个标识符指定具体的数值
            ```ts
            enum 枚举名{
                标识符1=整形常数x,
                标识符2=整形常数y,
            }
            ```
        - 方式2: 不为标识符设置数值，使用索引值
            ```ts
            enum 枚举名{
                标识符1, // 值为 0
                标识符2, // 值为 1
                标识符3, // 值为 2
            }
            ```
        - 方式3: 方式1、方式2 混合
            - 没有声明值的标识符，会在前一个标识符的基础上 `+ 1`
                ```ts
                enum 枚举名{
                    标识符1,            // 值为 0
                    标识符2=整形常数x,  // 值为 x
                    标识符3,            // 值为 x + 1
                    标识符4,            // 值为 x + 2
                    标识符5=整形常数y,   // 值为 y
                    标识符6,            // 值为 y + 1
                }
                ```
    - 每种声明方式中，索引是如何确定的?
        - 编译时，会确定每个标识符的索引，并写入枚举对象中
        - 编译器生成索引时的策略
            1. 如果标识符有值，则直接使用
            2. 如果标识符没有值
                1. 如果是第一个标识符，则设为 `0`
                2. 如果不是第一个标识符，则设为前一个数值 `+1`
        - 编译结果说明
            ```js
            // 一般会创建一个枚举对象，并注入到一个IIFE中，添加标识符及其索引
            var 枚举对象名;
            (function (PayStatus) {
                // 添加索引时，会按照策略生成索引
                枚举对象名[枚举对象名["标识符1"] = x] = "标识符1";
                枚举对象名[枚举对象名["标识符2"] = y] = "标识符2";
                枚举对象名[枚举对象名["标识符3"] = z] = "标识符3";
            })(枚举对象名 || (枚举对象名 = {}));
            ```

- 使用枚举类型赋值
    ```ts
    let 变量名:枚举名 = 枚举名.标识符
    ```

- 示例
    - 参考代码
        - [src/syntax/datatype/enum.ts](src/syntax/datatype/enum.ts)
    - 声明枚举对象
        - 声明方式1
            - ts代码
                ```ts
                enum PayStatus{
                    pay= 20,
                    cost= 30,
                    end= 40,
                }
                console.log(PayStatus.pay); // 输出: 20
                console.log(PayStatus.cost); // 输出: 30
                console.log(PayStatus.end); // 输出: 40
                ```
            - 枚举对象的编译结果
                ```js
                var PayStatus;
                (function (PayStatus) {
                    PayStatus[PayStatus["pay"] = 20] = "pay";
                    PayStatus[PayStatus["cost"] = 30] = "cost";
                    PayStatus[PayStatus["end"] = 40] = "end";
                })(PayStatus || (PayStatus = {}));
                ```
        - 声明方式2
            - ts代码
                ```ts
                enum Flag{
                    status01,
                    status02,
                    status03,
                }
                console.log(Flag.status01); // 输出: 0
                console.log(Flag.status02); // 输出: 1
                console.log(Flag.status03); // 输出: 2
                ```
            - 枚举对象的编译结果
                ```js
                var Flag;
                (function (Flag) {
                    // 按照索引顺序设置
                    Flag[Flag["status01"] = 0] = "status01";
                    Flag[Flag["status02"] = 1] = "status02";
                    Flag[Flag["status03"] = 2] = "status03";
                })(Flag || (Flag = {}));
                ```
        - 声明方式3: 方式1、方式2 混合
            - ts代码
                ```ts
                enum MyType{
                    type1,
                    type2,
                    type3 = 25,
                    type4,
                    type5,
                    type6 = 45,
                    type7,
                }
                console.log(MyType.type1); // 输出: 0
                console.log(MyType.type2); // 输出: 1
                console.log(MyType.type3); // 输出: 25
                console.log(MyType.type4); // 输出: 26
                console.log(MyType.type5); // 输出: 27
                console.log(MyType.type6); // 输出: 45
                console.log(MyType.type7); // 输出: 46
                ```
            - 枚举对象的编译结果
                ```js
                var MyType;
                (function (MyType) {
                    MyType[MyType["type1"] = 0] = "type1";  // 标识符没有值，从0开始
                    MyType[MyType["type2"] = 1] = "type2";  // 在上一个基础上 + 1
                    MyType[MyType["type3"] = 25] = "type3"; // 使用设置的值
                    MyType[MyType["type4"] = 26] = "type4"; // 在上一个基础上 + 1
                    MyType[MyType["type5"] = 27] = "type5"; // 在上一个基础上 + 1
                    MyType[MyType["type6"] = 45] = "type6"; // 使用设置的值
                    MyType[MyType["type7"] = 46] = "type7"; // 在上一个基础上 + 1
                })(MyType || (MyType = {}));
                ```
    - 使用枚举对象的标识符为变量赋值
        ```ts
        let e1 = MyType.type4;
        console.log( e1 );  // 输出: 26
        console.log( e1 == MyType.type4 );  // 输出: true
        ```

## 任意类型any
### 任意类型any的基本使用
[top](#catalog)
- `any` 类型的变量**不会有类型限制**，和普通的js变量用法相同,
- 示例
    - 参考代码
        - [src/syntax/datatype/any/any.ts](src/syntax/datatype/any/any.ts)
    - 代码内容
        ```ts
        // 其他类型的变量，无法修改类型
        let a1:number = 1234;
        // a = 'str test';

        // any类型变量，可以任意修改类型
        let a2:any;
        a2 = 1234;
        console.log(a2);
        a2 = 'str test';
        console.log(a2);
        ```

### 任意类型的适用场景
[top](#catalog)
- 使用场景: 获取/操作dom对象
    - dom对象在js中是 `Object` 类型的，但是 ts 中没有这种类型
    - 这种情况可以将变量指定为 `any`
- 说明示例
    - [src/syntax/datatype/any/any_dom_test.html](#src/syntax/datatype/any/any_dom_test.html)
    - [src/syntax/datatype/any/any_dom.ts](#src/syntax/datatype/any/any_dom.ts)

- 代码演进过程
    1. 不设置类型，直接使用dom对象设置样式属性
        - ts代码
            ```ts
            let box = document.getElementById('box');
            box.style.color = 'red';
            ```
        - 页面可以正常显示，但是 ts 编译器会报错
            ```sh
            - error TS2531: Object is possibly 'null'.
            ```

    2. 将box设置为 (js中的) Object类型，但是ts中没有Object类型
        - ts代码
            ```ts
            let box:Object = document.getElementById('box');
            ```

        - ts 编译器会报错
            ```sh
            - error TS2322: Type 'HTMLElement | null' is not assignable to type 'Object'.
            Type 'null' is not assignable to type 'Object'.
            ```

    3. 将dom对象的类型型设置为 any，编译正常，页面正常显示
        - ts代码
            ```ts
            let box:any = document.getElementById('box');
            box.style.color = 'red';
            ```

## null和undefined
### null的基本特性
[top](#catalog)
- null 的特性
    - 基本性质与 js 中的 null 相同
        - 表示: **变量已声明、已赋值，值是null**
    - 变量在赋值前无法使用，编译器会报错
    - 变量<label style='color:red'>只能被赋值为null</label>
- 示例
    - 参考代码
        - [src/syntax/datatype/null_undefined.ts](src/syntax/datatype/null_undefined.ts)
    - 代码内容
        ```js
        console.log('-----------4. 将变量设置为 null-----------');
        // 4. 将变量设置为 null
        let num4: null;
        // 4.1 如果未赋值就使用，编译器会报错
        // - error TS2454: Variable 'num4' is used before being assigned.
        // console.log(num4);

        // 4.2 但是只能赋值为null，赋值为其他类型的数据会报错
        // num4 = 12345;
        num4 = null;
        console.log(num4);  // 输出: null
        ```

### undefined的基本特性
[top](#catalog)
- undefined 的特性
    - 基本性质与 js 中的 undefined 相同
        - 表示: **变量已声明、未赋值**
    - 变量在赋值前就可以使用
    - 变量<label style='color:red'>只能被赋值为 undefined</label>
    - 
- 示例
    - 参考代码
        - [src/syntax/datatype/null_undefined.ts](src/syntax/datatype/null_undefined.ts)
    - 代码内容
        ```js
        // 1. 尝试在不赋值的情况下使用变量
        // let num1:number;
        // console.log(num1);
        /*
            只声明不赋值，在编译时会报错
            - error TS2454: Variable 'n' is used before being assigned.
            但是编译结果仍然可以继续使用，会输出: undefined
        */

        // 2. 将变量设置为 undefined 类型
        let num2:undefined;
        // 2.1 undefined 类型可以不赋值，直接使用
        console.log(num2);  // 输出 undefined
        // 2.2 将变量设置为 undefined
        num2 = undefined;
        console.log(num2);  // 输出 undefined
        /*
            2.3 如果将 undefined类型设置为其他类型的值
            编译器会报错
            - error TS2322: Type '1234' is not assignable to type 'undefined'.
            但是编译结果仍然可以继续使用，会输出: 1234
        */
        // num2 = 1234;
        // console.log(num2);
        ```

### null、undefined与其他类型混合使用
[top](#catalog)
- 普通类型变量不能被设置为null、undefined
- 如果有null、undefined的需要，可以混合设置类型
    ```ts
    // 表示可能是 普通类型数据，或null，或undefined
    let <变量>:<普通类型> | null | undefined;
    ```
- 示例
    - 参考代码
        - [src/syntax/datatype/null_undefined.ts](src/syntax/datatype/null_undefined.ts)
    - 普通类型与 undefined 混合使用
        ```ts
        // 3. 将变量设置为 即是 number 又是 undefined
        let num3: number | undefined;
        // 3.1 已定义、未赋值
        console.log(num3);  // 输出: undefined

        // 3.2 已定义、已赋值
        num3 = 5678;
        console.log(num3);  // 输出: 5678
        ```
    - 普通类型与 undefined、null 混合使用
        ```ts
        // 5. 将变量设置为 即是 number ，又是 undefined， 又是 null
        let num5: number | undefined | null;
        // 5.1 已定义、未赋值
        console.log(num5);  // 输出: undefined

        // 5.2 已定义、已赋值，但是值是 null
        num5 = null;
        console.log(num5);  // 输出: null

        // 5.3 已定义、已赋值
        num5 = 7890;
        console.log(num5);  // 输出: 7890
        ```

## void类型
[top](#catalog)
- void表示没有任何类型
- 适用场景
    - 定义方法时，没有返回值

- 示例
    ```ts
    function test(): void{
        console.log('run test');
    }
    ```

## never类型
[top](#catalog)
- never类型的特性
    - never 表示从不会出现的值
    - never 是其他类型（包括 undefined、null）的子类型
    - never 类型的变量只能被赋值为never类型
- never类型在开发中很少使用
- 示例
    - 参考代码
        - [src/syntax/datatype/never.ts](src/syntax/datatype/never.ts)
    - 使用never接受一个异常
        ```ts
        let p: never;

        p = (()=>{
            throw new Error('test err');
        })();
        ```

# 函数
## 函数声明
[top](#catalog)
- 参考示例
    - 示例代码
        - [src/syntax/function/fun_define.ts](src/syntax/function/fun_define.ts)
    - 编译结果
        - [src/syntax/js/function/fun_define.js](src/syntax/js/function/fun_define.js)
- 声明方式
    1. 具名函数
        ```ts
        function fun01(): string{
            return 'abc';
        }
        ```
    2. 匿名函数
        ```ts
        let fun03 = function(): string{
            return '123abc';
        }
        ```
    3. 没有返回值的函数
        ```ts
        function print(info: string): void{
            console.log(info);
        }
        ```
## 函数参数
### 有参函数
[top](#catalog)
- 定义有参函数
    ```ts
    function info2Str(name:string, age:number): string{
        return `name=${name}, age=${age}`;
    }
    console.log( info2Str('testName', 22) );
    // 实参与形参类型不一致，ts编译异常
    // console.log( info2Str('testName', "22") );
    ```

### 可选参数
[top](#catalog)
- 可选参数的定义方法
    ```ts
    function foo(参数名?:类型,...):返回值类型{}
    ```
- 注意事项
    - es里方法的实参和形参数量可以不同，但是在ts中必须相同。如果不同，必须配置可选参数
    - 可选参数必须配置到参数的最后

- 编译结果
    - 可选参数是 ts 编译器的编译约束，在编译结果中没有特殊处理

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/function/fun_params.ts](src/syntax/function/fun_params.ts)
        - 代码内容
            ```ts
            function getInfo(name:string, address?:string): string{
                // 判断可选参数是否传值
                if (address){
                    return `name=${name}, address=${address}`;
                } else {
                    return `name=${name}, address=null`;
                }
            }
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/function/fun_params.js](src/syntax/js/function/fun_params.js)
        - 编译代码
            ```js
            // 可选参数约束没有特殊处理
            function getInfo(name, address) {
                // 判断可选参数是否传值
                if (address) {
                    return "name=" + name + ", address=" + address;
                }
                else {
                    return "name=" + name + ", address=null";
                }
            }
            ```

### 默认参数
[top](#catalog)
- 可选参数的定义方法
    ```ts
    function foo(参数名:类型=默认值):返回值类型{}
    ```

- 注意事项
    - 默认参数是最后一个参数时，调用时可以省略
    - 如果参数在中间，只能通过将实参设置为 `undefined` 来省略

- 编译结果
    - 在编译结果中会添加一行代码，根据参数是否为 `undefined`，来决定是否使用默认值
        ```js
        if (参数名 === void 0) { 参数名 = 默认值; }
        ```
- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/function/fun_params.ts](src/syntax/function/fun_params.ts)
        - 代码内容
            ```ts
            // 3.1 默认参数是最后一个参数
            function getInfo02(name:string, age:number=22):string{
                return `name=${name}, age=${age}`;
            }
            console.log( getInfo02('testName') ); // 使用默认值
            console.log( getInfo02('testName', 33) );

            // 3.2 默认参数在中间
            function getInfo03(name:string, age:number=22, address:string):string{
                return `name=${name}, age=${age}, address=${address}`;
            }
            // 只能通过设置undefined来省略默认参数
            console.log( getInfo03('testName1', undefined ,'aaabbbccc') );
            console.log( getInfo03('testName2', 40 ,'ertyuu') );
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/function/fun_params.js](src/syntax/js/function/fun_params.js)
        - 编译代码
            ```js
            // 3.1 默认参数是最后一个参数
            function getInfo02(name, age) {
                // 如果参数是undefined，则使用默认值
                if (age === void 0) { age = 22; }
                return "name=" + name + ", age=" + age;
            }
            // 3.2 默认参数在中间
            function getInfo03(name, age, address) {
                // 如果参数是undefined，则使用默认值
                if (age === void 0) { age = 22; }
                return "name=" + name + ", age=" + age + ", address=" + address;
            }
            ```

### 可变参数
[top](#catalog)
- 可变参数的定义方法
    ```ts
    function foo(...参数名:类型[]):返回值类型{}
    ````
- 可变参数的本质
    - 利用解构运算符 `...` 将所有实参传递到一个数组中
- 注意事项
    - **可变参数需要放在参数的最后，且只能有一个**
- 编译结果
    - 编译后的函数的参数列表中只包含普通的参数，没有可变参数
    - 可变参数会从 `arguments` 取出所有值，并添加到一个数组中，作为可变参数使用
        ```js
        var 参数名 = [];

        // arguments 中包含所有的形参，所以需要计算普通参数的数量，
        // 定位到可变参数的开始位置，然后将所有可变参数导出
        for (var _i = 普通参数的数量; _i < arguments.length; _i++) {
            参数名[_i] = arguments[_i];
        }
        ```
- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/function/fun_params.ts](src/syntax/function/fun_params.ts)
        - 代码内容
            ```ts
            // 1. 只有可变参数
            function sum(...params:number[]):number{
                let result: number = 0;
                for(let i=0; i < params.length; i++){
                    result += params[i];
                }
                return result;
            }
            console.log( sum() );
            console.log( sum(1,2,3,4,5) );

            // 2. 包含一个普通参数，剩余参数需要放到最后
            function sum02(a:number, ...others:number[]):number{
                for(let i=0; i< others.length; i++){
                    a += others[i];
                }
                return a;
            }
            console.log( sum(1) );
            console.log( sum(1,2,3,4,5) );
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/function/fun_params.js](src/syntax/js/function/fun_params.js)
        - 编译代码
            ```js
            // 1. 只有可变参数
            function sum() {
                // 从 argument 中获取所有的可变参数
                var params = [];
                for (var _i = 0; _i < arguments.length; _i++) {
                    params[_i] = arguments[_i];
                }
                var result = 0;
                for (var i = 0; i < params.length; i++) {
                    result += params[i];
                }
                return result;
            }

            // 2. 包含一个普通参数，可变参数需要放到最后
            // 普通参数仍然保留
            function sum02(a) {

                // 从 argument 中获取所有的可变参数
                var others = [];
                // 函数包含一个普通函数，所有将 `index` 定位到 1
                for (var _i = 1; _i < arguments.length; _i++) {
                    others[_i - 1] = arguments[_i];
                }
                for (var i = 0; i < others.length; i++) {
                    a += others[i];
                }
                return a;
            }
            ```

### 对象类型参数的属性及类型约束
[top](#catalog)
- 定义方法
    ```ts
    function foo( 参数名:{属性1:类型, 属性2:类型,....} ):返回值类型{}
    ```
- 注意事项
    - 不同传参方式的问题
        1. `foo({属性1:属性值, 属性2:属性值2,....})`
            - 直接在调用时创建一个对象作为实参
            - 注意内容
                - <label style='color:red'>实参对象的属性数量、属性名、属性类型必须与函数定义匹配</label>
        2. `let obj = {属性1:属性值, 属性2:属性值2,....}; foo(obj);`
            - 先创建一个对象，然后将对象作为实参
            - 注意内容
                - <label style='color:red'>只要对象中包含函数定义中的属性，并且类型匹配即可</label>
                - 对属性的数量没有要求
    - 对象中属性声明的顺序没有要求

- 编译结果
    - 这种语法**只是编译约束**，在编译结果中没有特殊处理
    - 编译结果只是普通的函数声明

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/function/fun_params.ts](src/syntax/function/fun_params.ts)
        - 代码内容
            ```ts
            function printName(info:{name:string, age:number}):void{
                console.log(`info.name = ${info.name} info.age = ${info.age}`);
            }

            // 5.1 直接在调用时创建一个对象作为实参
            // 5.1.1 没有包含指定的属性，编译异常
            // error TS2345: Argument of type '{ name: string; }' is not assignable to parameter of type '{ name: string; age: number; }'.
            // printName({name:'testName'});

            // 5.1.2 比函数定义中的属性多，编译异常
            // Argument of type '{ name: string; age: number; adderss: string; }' is not assignable to parameter of type '{ name: string; age: number; }'.
            // let testObj = {name:'testName', age:20, adderss:'asdfgh'}
            // printName({name:'testName', age:20, adderss:'asdfgh'});

            // 5.1.3 属性数量、属性名、属性类型与定义相同，顺序可以不同
            printName({name:'testName1', age:20});
            printName({age:33, name:'testName2'});

            // 5.2 先创建一个对象，然后将对象作为实参
            // 对属性的数量没有要求
            let testObj = {name:'testName3', age:11, adderss:'zxcvbnm'}
            printName(testObj);
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/function/fun_params.js](src/syntax/js/function/fun_params.js)
        - 编译代码
            ```js
            // 没有约束处理，只是普通的函数
            function printName(info) {
                console.log("info.name = " + info.name);
            }
            printName({ name: 'testName' });
            ```

## 函数重载
[top](#catalog)
- ts为了兼容es5、es6，重载的写法与其他语言不同
- ts的重载只是利用编译器对函数的使用方式做约束，在编译结果中仍然是普通的函数

- 参考示例
    - 示例代码
        - [src/syntax/function/fun_overload.ts](src/syntax/function/fun_overload.ts)
    - 编译结果
        - [src/syntax/js/function/fun_overload.js](src/syntax/js/function/fun_overload.js)

- 重载方式1: 参数数量相同，类型不同
    - 示例
        ```ts
        function makeInfo(name:string):string;   // 重载声明
        function makeInfo(age:number):string;    // 重载声明
        function makeInfo(info:any):string{      // 重载实现
            // 通过参数类型判断是哪个重载
            if (typeof info === 'string'){
                return `name = ${info}`;
            } else {
                return `age = ${info}`;
            }
        }
        console.log(makeInfo('testName'));
        console.log(makeInfo(22));
        // 无法找到匹配的重载
        // makeInfo(true);
        ```
    - 编译结果
        ```js
        function makeInfo(info) {
            // 通过参数类型判断是哪个重载
            if (typeof info === 'string') {
                return "name = " + info;
            }
            else {
                return "age = " + info;
            }
        }
        ```

- 重载方式2: 参数数量不同
    - 示例
        ```ts
        function formatInfo(name:string):string;                // 重载声明
        function formatInfo(name:string, age:number):string;    // 重载声明
        function formatInfo(name:string, age?:number):string{   // 重载实现
            // 通过判断age是否为空，来判断使用了哪个重载
            if (age){
                return `name=${name}, age=${age}`;
            } else {
                return `name=${name}`;
            }
        }

        console.log( formatInfo('testName', 22) );
        console.log( formatInfo('testName02') );
        // 无法找到匹配的重载
        // console.log( formatInfo(123) );
        ```
    - 编译结果
        ```js
        // 保持了参数最多的声明，然后根据形参的值来做具体处理
        function formatInfo(name, age) {
            // 通过判断age是否为空，来判断使用了哪个重载
            if (age) {
                return "name=" + name + ", age=" + age;
            }
            else {
                return "name=" + name;
            }
        }
        ```

## 箭头函数
[top](#catalog)
- ts编译器默认支持 es6 的箭头函数，所有规范与 es6 相同
- 对于 `this` 对象，ts编译器的规范与es6相同
    - 即编译时，箭头函数的 this 对象就已经确定
    - this 不是函数的调用者，而是函数所属的作用域，是静态的
- 箭头函数的编译结果
    - 箭头函数的编译结果仍然是**用function声明的普通函数**
    - `this` 的处理
        - ts编译器在编译时，会捕获 `this` 所在的作用域，并将其设置为 `_this = this`
        - 如果箭头函数中使用了 `this` 对象，编译后的代码中都会改为使用 `_this`
        - 虽然箭头函数的编译结果是 普通函数，但是 `this` 的规则与 es6 保持一致
- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/function/fun_arrow.ts](src/syntax/function/fun_arrow.ts)
        - 代码内容
            ```ts
            // 1. 捕获局部的this
            // 在函数内部声明的箭头函数，默认函数的上下文this
            function funX(){
                // 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
                let x = ()=>console.log(this);
                x();
            }

            // 2. 捕获全局的this

            // 2.1 全局的箭头函数
            // 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
            let globalArrowFun = ()=> ()=>console.log(this);

            // 2.2 对象内部的属性是箭头函数时，默认使用的是全局的this，在页面中是window
            let objA:any = {
                // objA中没有this，继续向外搜索，使用的是全局的this
                // 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
                fn1:()=>console.log(this),
                fn2(){
                    console.log(this);
                },
            }
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/function/fun_arrow_bk.js](src/syntax/js/function/fun_arrow_bk.js)
        - 编译内容
            ```js
            "use strict";
            var _this = this;   // <<<<<<<<<<<<< 捕获了全局this
            // 1. 捕获局部的this
            function funX() {
                var _this = this;   // <<<<<<<<<<<<< 捕获了函数内的this
                var x = function () { return console.log(_this); }; // 使用 _this
                x();
            }
            // 2. 捕获全局的this
            // 2.1 全局的箭头函数
            var globalArrowFun = function () { return function () { return console.log(_this); }; };
            // 2.2 对象内部的属性是箭头函数时，默认使用的是全局的this，在页面中是window
            var objA = {
                fn1: function () { return console.log(_this); }, // 使用 _this
                fn2: function () {
                    console.log(this);
                },
            };
            ```

# 类
## 定义类
[top](#catalog)
- 参考示例
    - 参考代码
        - [src/syntax/class/define.ts](src/syntax/class/define.ts)
    - 编译结果
        - [src/syntax/js/class/define.js](src/syntax/js/class/define.js)
- 定义类
    - 注意事项
        - 私有属性，在外部无法直接使用，需要通过 getter、setter 方法来使用
        - 如果某个属性没有设置默认值，或者在构造器中没有初始化，会有编译异常
            ```sh
            error TS2564: Property 'address' has no initializer and is not definitely assigned in the constructor.
            ```
    - 定义方法
        ```ts
        class Person{
            name: String;               // 默认省略public关键字
            public age:number = 20;     // 为属性设置默认值
            private address: String;    // 设置私有后苏醒

            // 构造函数
            constructor(name:String){
                // 需要为属性设置，否则会有编译异常
                this.name = name;
                this.address = '';
            }

            printInfo(): void {
                console.log(`name=${this.name}, age=${this.age}, address=${this.address}`);
            }

            // 为 address 添加getter
            getAddress():String{
                return this.address;
            }
            setAddress(value:String){
                this.address = value;
            }

        }

        // 实例化对象
        let person = new Person('testName');
        person.printInfo();                     // 输出: name=testName, age=20, address=
        person.setAddress('abcdefg');
        console.log(person.getAddress());       // 输出: abcdefg
        person.age=22;
        person.printInfo();                     // 输出: name=testName, age=22, address=abcdefg

        // 无法直接访问 private 变量
        // 编译异常
        // error TS2341: Property 'address' is private and only accessible within class 'Person'.
        // console.log(person.address);
        ```


- 编译结果
    - 编译的本质
        - 通过IIFE创建类
        - 类的创建方式与 es5 相同
            - 通过构造函数创建类
            - 通过在原型上添加方法来添加类方法
        - ts中的约束只是编译约束，在编译结果中没有特殊体现
    - 编译后的代码
        ```js
        var Person = /** @class */ (function () {
            // 构造函数
            function Person(name) {
                this.age = 20; // 为属性设置默认值
                // 需要为属性设置，否则会有编译异常
                this.name = name;
                this.address = '';
            }
            Person.prototype.printInfo = function () {
                console.log("name=" + this.name + ", age=" + this.age + ", address=" + this.address);
            };
            // 为 address 添加getter
            Person.prototype.getAddress = function () {
                return this.address;
            };
            Person.prototype.setAddress = function (value) {
                this.address = value;
            };
            return Person;
        }());
        ```

## 类的继承
[top](#catalog)
- 参考示例
    - 参考代码
        - [src/syntax/class/extends.ts](src/syntax/class/extends.ts)
    - 编译结果
        - [src/syntax/js/class/extends.js](src/syntax/js/class/extends.js)
- 继承的语法
    - 注意事项
        - 需要同时使用 `extends` 和 `super` 两个关键字才能完成继承
        - ts 中<label style='color:red'>只能实现单继承</label>
    - 继承后可以有类自己的方法，也可以重写父类方法
    - 语法示例
        ```ts
        // 1. 通过 extends 关键字继承某个类
        class Student extends BasePerson{
            constructor(name:string){
                // 2. 通过super调用父类
                super(name);
            }

            // 定义 Student 自己的方法
            study():void{
                console.log(`${this.name} is studying`)
            }

            // 重写父类的方法
            printInfo():void{
                console.log(`Student name = ${this.name}`)
            }
        }
        ```

- 编译结果
    ```js
    // 将父类函数中的静态方法设置到子类函数中
    var __extends = (this && this.__extends) || (function () {
        var extendStatics = function (d, b) {
            // Object.setPrototypeOf 及其 polyfill
            // 为子类构造函数绑定父类中的【静态方法】
            extendStatics = Object.setPrototypeOf ||
                ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
                function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
            return extendStatics(d, b);
        };

        // 设置父类、子类的继承关系
        return function (d, b) {
            // 1. 将父类函数中的静态方法设置到子类函数中
            extendStatics(d, b);

            // 2. 使用寄生组合继承，构建继承关系
            // 2.1. 创建一个寄生类
            function __() { this.constructor = d; }
            // 2.2 将寄生类的原型设置为父类的原型
            // 2.3 实例化寄生类对象，并将该对象作为子类的原型
            d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
        };
    })();

    // 1. 父类。通过IIFE使用function创建普通的类
    var BasePerson = /** @class */ (function () {
        function BasePerson(name) {
            this.name = name;
        }
        BasePerson.prototype.printInfo = function () {
            console.log("name=" + this.name);
        };
        return BasePerson;
    }());

    /*
        2. 子类。通过IIFE使用function创建普通的类

        extends 关键字
            - 被转换为继承关系设置函数 __extends
            - 通过 __extends 函数完成 父类、子类原型链的设置、constructor的设置、父类静态方法的设置
        - super 关键字
            - 转换为注入到IIFE 的函数对象，即 父类 _super 参数
            - 然后在函数中，调用 call 方法被调用

        super 与 __extends 相当于 js 中的组合继承
    */
    var Student = /** @class */ (function (_super) {
        // 1. 设置继承关系
        __extends(Student, _super);
        function Student(name) {
            // 2. 通过super调用父类
            return _super.call(this, name) || this;
        }
        // 定义 Student 自己的方法
        Student.prototype.study = function () {
            console.log(this.name + " is studying");
        };
        // 重写父类的方法
        Student.prototype.printInfo = function () {
            console.log("Student name = " + this.name);
        };
        return Student;
    }(BasePerson));
    ```

## 类属性的修饰符
[top](#catalog)
- 3种修饰符及访问范围

    |修饰符|访问范围|
    |-|-|
    |`public`|在任何位置都可以访问|
    |`protected`|同包、不同包?????的子类内部|
    |`private`|当前类内部|

- 属性不加修饰符，默认为 `public`
- 修饰符的本质
    - 修饰符只是 ts 编译器的一种约束手段，只在编译器有效
    - 在编译结果中，所有的属性都是普通属性，没有特殊的处理

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/class/modifier.ts](src/syntax/class/modifier.ts)
        - 代码内容
            ```ts
            class Tool{
                name:string;
                protected introduction:string;
                private executedTime:number = 0;

                constructor(name:string, introduction:string){
                    this.name = name;
                    this.introduction = introduction
                }

                run():void{
                    console.log(`${this.name} is run, executedTime=${this.executedTime}`);
                    this.addExecutedTime();
                }

                stop():void {
                    console.log(`${this.name} is stop`);
                    this.clearExecutedTime();
                }

                protected clearExecutedTime():number{
                    return this.executedTime = 0;
                }

                private addExecutedTime():void{
                    this.executedTime += 10;
                }
            }

            let tool = new Tool('computer', 'this is a computer');
            // 调用 public 方法
            tool.run();
            tool.run();
            tool.stop();
            tool.run();

            // 无法调用 protected 方法，会有编译异常
            // error TS2445: Property 'clearExecutedTime' is protected and only accessible within class 'Tool' and its subclasses.
            // tool.clearExecutedTime();

            // 无法调用 private 方法，会有编译异常
            // error TS2341: Property 'addExecutedTime' is private and only accessible within class 'Tool'.
            // tool.addExecutedTime();
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/class/modifier.js](src/syntax/js/class/modifier.js)
        - 编译内容
            ```js
            // 全部被编译为普通js函数，没有特殊处理
            var Tool = /** @class */ (function () {
                function Tool(name, introduction) {
                    this.executedTime = 0;
                    this.name = name;
                    this.introduction = introduction;
                }
                Tool.prototype.run = function () {
                    console.log(this.name + " is run, executedTime=" + this.executedTime);
                    this.addExecutedTime();
                };
                Tool.prototype.stop = function () {
                    console.log(this.name + " is stop");
                    this.clearExecutedTime();
                };
                Tool.prototype.clearExecutedTime = function () {
                    return this.executedTime = 0;
                };
                Tool.prototype.addExecutedTime = function () {
                    this.executedTime += 10;
                };
                return Tool;
            }());
            var tool = new Tool('computer', 'this is a computer');
            tool.run();
            tool.run();
            tool.stop();
            tool.run();
            ```

## 静态属性与静态方法
[top](#catalog)
- 通过 `static` 关键字声明静态属性和静态方法
- 静态方法中无法调用非静态的属性和方法
- 静态内容的调用
    - 需要通过类来调用
    - <label style='color:red'>在静态方法内部调用静态属性，也需要通过类来获取静态属性</label>
- 编译结果
    - 静态属性和静态方法，是直接挂在类构造函数上的方法
    - 普通的属性和方法，是挂在构造函数原型对象上的方法

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/class/static.ts](src/syntax/class/static.ts)
        - 代码内容
            ```ts
            class Fruit{
                // 声明静态方法与静态变量
                static type = 'xxxxx';
                static print(){
                    console.log('this is Fruit');

                    // 在静态方法中调用静态属性时，需要通过类来调用
                    console.log(`type = ${Fruit.type}`);

                    // 无法调用非静态方法和变量，编译异常
                    // error TS2304: Cannot find name 'getName'.
                    // getName();
                }

                // 声明普通变量和方法
                name:string;
                constructor(name:string){
                    this.name = name;
                }
                getName():string{
                    return this.name;
                }
            }

            // 调用类的静态方法
            Fruit.print();
            // 调用类的静态函数
            console.log(Fruit.type);
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/class/static.js](src/syntax/js/class/static.js)
        - 编译结果
            ```js
            var Fruit = /** @class */ (function () {
                function Fruit(name) {
                    this.name = name;
                }

                // 声明静态方法
                Fruit.print = function () {
                    console.log('this is Fruit');
                    console.log("type = " + Fruit.type);
                };

                // 普通方法
                Fruit.prototype.getName = function () {
                    return this.name;
                };

                // 声明静态属性
                Fruit.type = 'xxxxx';
                return Fruit;
            }());

            // 调用类的静态方法
            Fruit.print();
            // 调用类的静态函数
            console.log(Fruit.type);
            ```

## 抽象类和抽象方法
[top](#catalog)
- 使用 `abstract` 关键字声明抽象类和抽象方法
    ```ts
    abstract class 类名{
        abstract 方法名();
    }
    ```
- 抽象类的特性
    - 抽象类不能实例化对象，只能被子类继承
    - 抽象类中如果没有抽象方法，则这个类没有实际意义
- 抽象方法的特性
    - 抽象方法只能定义在抽象类中
    - 抽象方法没有具体的实现，只有声明。需要有子类提供实现
        - 子类必须提供实现
    - <label style='color:red'>抽象方法的参数和返回值类型，无法约束子类中的方法实现</label>

- 编译结果
    - 抽象类和抽象方法只是 ts 编译器在编译时的约束，编译结果中不会有特殊处理
    - 抽象方法不会有实际的编码内容
    - 抽象方法的实现只是挂在原型对象上的普通方法

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/class/abstract.ts](src/syntax/class/abstract.ts)
        - 代码内容
            ```ts
            // 1. 定义抽象类和抽象方法
            abstract class Animal{
                private name: string;
                constructor(name:string){
                    this.name = name;
                }
                // 1. 定义抽象方法
                abstract run():any;
                // 2. 定义普通方法
                getName():string{
                    return this.name;
                }
            }

            // 2.1 子类1
            class Dog extends Animal{
                constructor(name:string){
                    super(name);
                }
                run():void{
                    console.log(`dog: ${this.getName()} is running`);
                }
            }

            // 2.2 子类2
            class Cat extends Animal{
                constructor(name:string){
                    super(name);
                }
                run():void{
                    console.log(`cat: ${this.getName()} is running`);
                }
            }

            //3.  通过子类调用方法
            let dog = new Dog('mydog');
            dog.run();

            let cat = new Cat('mycat');
            cat.run();
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/class/abstract.js](src/syntax/js/class/abstract.js)

        - 编译内容
            - 抽象类
                ```js
                // 只有普通方法的实现，没有抽象方法
                var Animal = /** @class */ (function () {
                    function Animal(name) {
                        this.name = name;
                    }
                    // 2. 定义普通方法
                    Animal.prototype.getName = function () {
                        return this.name;
                    };
                    return Animal;
                }());
                ```
            - 子类
                ```js
                // 抽象类的实现就是挂在原型对象上的普通方法
                var Dog = /** @class */ (function (_super) {
                    __extends(Dog, _super);
                    function Dog(name) {
                        return _super.call(this, name) || this;
                    }
                    Dog.prototype.run = function () {
                        console.log("dog: " + this.getName() + " is running");
                    };
                    return Dog;
                }(Animal));
                ```

# 接口
## 属性接口
[top](#catalog)
- 功能
    - 封装属性及其类型
    - 封装后，接口可以作为参数的约束来使用
- 属性接口的定义与实现
    - 定义方法
        ```ts
        interface 接口名{
            属性1:类型;     // 必须属性，对象中必须有该属性
            属性2:类型;     // 必须属性，对象中必须有该属性
            属性3?:类型;    // 可选属性，对象中可以选择是否设置该属性
            ...
        }
        ```
    - 实现方法
        - 只要一个对象中包含接口中声明的所有属性，并且类型相同，就实现了接口
- 属性接口的编译结果
    - 在编译结果中不会有任何与接口相关的定义
    - 接口只是 ts 编译器的编译约束
- 适用场景
    - 优化 [对象类型参数的属性及类型约束](#对象类型参数的属性及类型约束)
        - 函数参数中的约束无法复用，可以通过属性接口，实现约束复用

- 示例
    1. 属性接口示例
        - 示例代码
            - 参考代码
                - [src/syntax/interface/property.ts](src/syntax/interface/property.ts)
            - 代码内容
                ```ts
                // 1. 定义属性接口
                interface Info{
                    name:string;
                    age:number;
                }

                // 2. 在多个方法中复用属性接口
                // 2.1 在方法的形参中使用接口
                function printInfo(data:Info):void{
                    console.log(`data.name = ${data.name}, data.age = ${data.age}`);
                }

                function printInfo02(data:Info):void{
                    console.log(`name = ${data.name}, age = ${data.age}`);
                }

                // 2.2 直接在调用时创建一个对象作为实参
                // 属性数量比结果多，编译异常
                // error TS2345: Argument of type '{ name: string; age: number; address: string; }' is not assignable to parameter of type 'Info'.
                // printInfo({name:'testName1', age:22, address:'qwertyu'});
                printInfo({name:'testName1', age:22});
                printInfo02({name:'testName2', age:33});

                // 2.3 先创建一个对象，然后将对象作为实参
                let info = { name:'testName3', age:11, address: 'asdfghjk' };
                printInfo(info);
                printInfo02(info);
                ```
        - 编译结果
            - 参考代码
                - [src/syntax/js/interface/property.js](src/syntax/js/interface/property.js)
            - 编译内容
                ```js
                // 编译结果中只有函数部分，没有任何与接口相关的内容
                function printInfo(data) {
                    console.log("data.name = " + data.name + ", data.age = " + data.age);
                }
                function printInfo02(data) {
                    console.log("name = " + data.name + ", age = " + data.age);
                }
                ```
    2. 可选属性接口示例
        - 示例代码
            - 参考代码
                - [src/syntax/interface/optional_property.ts](src/syntax/interface/optional_property.ts)
            - 代码内容
                ```ts
                // 1. 定义属性接口
                interface Info2{
                    name:string;
                    age:number;
                    address?:string;    // 定义可选属性
                }

                // 2. 使用接口
                function printInfo03(data: Info2): void{
                    if (data.address){
                        console.log(`name = ${data.name}, age= ${data.age}, address=${data.address}`);
                    }else{
                        console.log(`name = ${data.name}, age= ${data.age}`);
                    }
                }

                // 3.1 参数中包含可选属性
                let obj1={ name:'testName4', age:15, address:'asdfghj' };
                printInfo03(obj1);

                // 3.2 参数中不包含可选属性
                let obj2={ name:'testName5', age:12 };
                printInfo03(obj2);
                ```
        - 编译结果
            - 参考代码
                - [src/syntax/js/interface/optional_property.js](src/syntax/js/interface/optional_property.js)
            - 编译内容
                ```js
                // 编译结果中只有函数部分，没有任何与接口相关的内容
                function printInfo03(data) {
                    if (data.address) {
                        console.log("name = " + data.name + ", age= " + data.age + ", address=" + data.address);
                    }
                    else {
                        console.log("name = " + data.name + ", age= " + data.age);
                    }
                }
                ```

## 函数类型接口
[top](#catalog)
- 功能:
    - 封装函数定义
    - 封装后，可以作为函数参数的约束
- 函数类型接口的定义与实现
    - 定义
        ```ts
        interface 接口名{
            (参数1:类型, 参数2:类型, ...):返回值类型;
        }
        ```
    - 接口实现
        ```ts
        let fn:接口名 = function (参数1:类型, 参数2:类型, ...):返回值类型{
            //...
        };
        ```
- 注意事项
    - 函数类型接口对接口实现具有强类型约束，参数、返回值的类型必须与定义相同
- 编译结果
    - 函数类型接口只是 ts 编译器的编译约束
    - 编译结果中，不会有任何与接口相关的内容
- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/interface/function.ts](src/syntax/interface/function.ts)
        - 代码内容
            ```ts
            // 1. 声明函数类型接口
            interface EntryFormat{
                (key:string, value:any):string;
            }

            // 2. 接口实现
            let formate01: EntryFormat = function(key:string, value:any):string{
                return key + value;
            };
            let formate02: EntryFormat = function(key:string, value:any):string{
                return `key = ${key}, value=${value}`;
            };

            console.log(formate01('aaa', 123)); // 输出: aaa123
            console.log(formate02('aaa', 123)); // 输出: key = aaa, value=123
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/interface/function.js](src/syntax/js/interface/function.js)
        - 编译内容
            ```js
            // 只包含函数定义
            var formate01 = function (key, value) {
                return key + value;
            };
            var formate02 = function (key, value) {
                return "key = " + key + ", value=" + value;
            };
            ```

## 可索引接口
[top](#catalog)
- 功能
    - 定义数组、对象的类型约束
- 定义方法
    ```ts
    interface 接口名{
        [index:索引类型]:值类型
    }
    ```
- 注意事项
    - <label style='color:red'>index 的类型只能是 number 或者 string 类型</label>

- 数组约束
    ```ts
    interface 接口名{
        [index:number]:值类型
    }
    ```
- 对象约束
    ```ts
    interface 接口名{
        [index:string]:值类型
    }
    ```

- 编译结果
    - 可索引接口只是 ts 编译器的编译约束
    - 编译结果中，不会有任何与接口相关的内容

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/interface/indexable.ts](src/syntax/interface/indexable.ts)
        - 代码内容
            ```ts
            // 1. 数组约束
            interface MyArray{
                [index:number]:string;
            }

            let arr01:MyArray = ['qwert', 'asdfg', 'zxcvb'];
            console.log(arr01);

            // 2. 对象约束
            interface MyObj{
                [index:string]:any;
            }

            let obj01:MyObj = {
                name:'testName',
                address:'asghdfgh'
            }

            console.log(obj01);

            // 3. 其他类型无法约束
            // index 的类型不对，编译异常
            // error TS1023: An index signature parameter type must be either 'string' or 'number'.
            // interface MyObj02{
            //     [index:MyArray]:string;
            // }
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/interface/indexable.js](src/syntax/js/interface/indexable.js)
        - 编译内容
            ```js
            // 编译结果中只有普通的数组和对象，没有与接口相关的内容
            var arr01 = ['qwert', 'asdfg', 'zxcvb'];

            var obj01 = {
                name: 'testName',
                address: 'asghdfgh'
            };
            ```

## 类类型接口
[top](#catalog)
- 功能
    - 整合属性接口和函数类型接口，并约束类的定义
    - 类似于抽象类和抽象方法，但是功能更强大
- 类类型接口的定义与实现
    - 定义方法
        ```ts
        interface 接口名{
            // 属性约束
            属性名1:类型;
            属性名2:类型;
            ...

            // 方法约束
            方法名1(参数名1:类型, 参数名2:类型,...):返回值类型;
            方法名2(参数名1:类型, 参数名2:类型,...):返回值类型;
        }
        ```
    - 实现方法: <label style='color:red'>接口可以单实现，也可以多实现</label>
        ```ts
        class 类名 implements 接口名1, 接口名2{
            // 1. 实现所有接口的属性约束
            属性名1:类型;
            属性名2:类型;
            ...
            constructor(...){
                // 初始化属性
            }

            // 2. 实现所有接口的方法约束
            方法名1(参数名1:类型, 参数名2:类型,...):返回值类型{
                // 方法实现
            }

            方法名2(参数名1:类型, 参数名2:类型,...):返回值类型{
                // 方法实现
            }
        }
        ```
- 编译结果
    - 接口定义 以及 `implements` 关键字都是 ts 编译器的编译约束
    - 编译结果中不包含任何与接口定义 和 `implements` 关键字相关的内容
- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/interface/class.ts](src/syntax/interface/class.ts)
        - 代码内容
            ```ts
            // 1. 定义两个接口
            interface AnimalInterface{
                name:string;
                age:number;
                getInfo():string;
                eat(food:string):void;
            }

            interface Runnable{
                run():void
            }

            // 2. 实现接口，同时实现多个
            class Bird implements AnimalInterface, Runnable{
                // 2.1 实现属性约束
                name:string;
                age:number;
                constructor(name:string, age:number){
                    this.name = name;
                    this.age = age;
                }
                // 2.2 实现方法约束
                // 2.2.1 AnimalInterface 接口的约束
                getInfo():string{
                    return `bird: name=${this.name}, age=${this.age}`;
                }
                eat(food:string):void{
                    console.log(`bird: ${this.name} is eating ${food}`);
                }

                // 2.2.2 Runnable 接口的约束
                run():void{
                    console.log(`bird is running`);
                }
            }
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/interface/class.js](src/syntax/js/interface/class.js)
        - 编译内容
            ```js
            // 编译结果中，只有实现类的代码，没有接口与implement关键字的定义
            var Bird = /** @class */ (function () {
                function Bird(name, age) {
                    this.name = name;
                    this.age = age;
                }
                Bird.prototype.getInfo = function () {
                    return "bird: name=" + this.name + ", age=" + this.age;
                };
                Bird.prototype.eat = function (food) {
                    console.log("bird: " + this.name + " is eating " + food);
                };
                Bird.prototype.run = function () {
                    console.log("bird is running");
                };
                return Bird;
            }());
            ```

## 接口间的继承
[top](#catalog)
- 接口继承的定义
    - <label style='color:red'>接口间可以单继承，也可以多继承</label>
        ```ts
        interface 接口 extends 接口1, 接口2,....
        ```
- 实现接口时，当前接口、父接口的所有方法和属性都需要实现
- 编译结果
    - 每个接口、`extends` 关键字都是 ts 编译器的编译约束
    - 编译结果中没有任何与接口、`extends` 关键字相关的内容

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/interface/interface_extends.ts](src/syntax/interface/interface_extends.ts)
        - 代码内容
            ```ts
            // 1. 创建2个接口
            interface MyInterface01{
                work():void;
            }

            interface MyInterface02{
                run():void;
            }
            // 2. 多接口继承
            interface MyInterface03 extends MyInterface01, MyInterface02{
                fly():void;
            }

            //3. 实现接口，同实现所有的约束
            class MyInstance implements MyInterface03{
                work():void{
                    console.log('instance is working');
                }
                run():void{
                    console.log('instance is running');
                }
                fly():void{
                    console.log('instance is flying');
                }
            }
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/interface/interface_extends.js](src/syntax/js/interface/interface_extends.js)
        - 编译内容
            ```js
            var MyInstance = /** @class */ (function () {
                function MyInstance() {
                }
                MyInstance.prototype.work = function () {
                    console.log('instance is working');
                };
                MyInstance.prototype.run = function () {
                    console.log('instance is running');
                };
                MyInstance.prototype.fly = function () {
                    console.log('instance is flying');
                };
                return MyInstance;
            }());
            ```

# 泛型
## 泛型函数
[top](#catalog)
- 定义方法
    ```ts
    // T, S, A等 表示泛型，声明后，可以在参数、返回值、函数体中使用
    function foo<T, S> (参数:T, 参数:S):A{
        ///...
    }
    ```
- 泛型函数的使用方法
    ```ts
    // 1. 使用时声明泛型的具体类型
    foo<泛型的具体类型>(参数...);

    // 2. 可以不做说明直接使用
    foo(参数....);
    ```
- 编译结果
    - 泛型部分只是 ts 编译器的编译约束
    - 编译结果中不会包含与泛型相关的内容

- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/generic/function.ts](src/syntax/generic/function.ts)

        - 代码内容
            ```ts
            // 1. 定义泛型函数
            function foo<T, S>(v1:T, v2:S):T{
                console.log(v2);
                return v1;
            }
            // 2. 使用泛型函数，使用时指定泛型的具体类型
            console.log(foo<number, boolean>(1234, false));
            console.log(foo<string, number>('12345', 23456));
            console.log(foo<boolean, null>(true, null));
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/generic/function.js](src/syntax/js/generic/function.js)
        - 编译内容
            ```js
            // 编译结果只是普通函数，没有与泛型相关的内容
            function foo(v1, v2) {
                console.log(v2);
                return v1;
            }
            console.log(foo(1234, false));
            console.log(foo('12345', 23456));
            console.log(foo(true, null));
            ```

## 泛型类
[top](#catalog)
- 定义方法
    ```ts
    // T, S, A等 表示泛型，声明后，可以在属性声明、构造器、函数声明中使用
    class ClassName<T, S, A>{
        data:S;

        func (参数: T, 参数:S):A{
            // ...
        }
    }
    ```
- 使用方法
    ```ts
        // 实例化类时，需要声明泛型的具体类型
    let a = ClassName<泛型的具体类型>(参数...);
    ```
- 编译结果
    - 泛型部分只是 ts 编译器的编译约束
    - 编译结果中不会包含与泛型相关的内容
- 示例
    - 示例代码
        - 参考代码
            - [src/syntax/generic/class.ts](src/syntax/generic/class.ts)

        - 代码内容
            ```ts
            // 1. 定义泛型类
            class MyArrayList<T>{
                private data:T[];
                constructor(){
                    this.data = [];
                }

                add(e:T):void{
                    this.data.push(e);
                }

                toString():string{
                    let str:string = '';
                    for (let e of this.data){
                        str += e + ' ,';
                    }
                    return str;
                }
            }

            // 2. 实例化泛型类对象，并调用方法
            let list = new MyArrayList<number>();
            list.add(123);
            list.add(3456);
            list.add(334);
            console.log(list.toString());
            ```
    - 编译结果
        - 参考代码
            - [src/syntax/js/generic/class.js](src/syntax/js/generic/class.js)
        - 编译内容
            ```js
            // 只有类的声明部分，不包括与泛型相关的内容
            var MyArrayList = /** @class */ (function () {
                function MyArrayList() {
                    this.data = [];
                }
                MyArrayList.prototype.add = function (e) {
                    this.data.push(e);
                };
                MyArrayList.prototype.toString = function () {
                    var str = '';
                    for (var _i = 0, _a = this.data; _i < _a.length; _i++) {
                        var e = _a[_i];
                        str += e + ' ,';
                    }
                    return str;
                };
                return MyArrayList;
            }());
            ```

## 泛型函数接口
[top](#catalog)
- 定义与实现
    - 在接口定义中声明泛型
        ```ts
        // 1. 定义接口
        interface 接口名<T, S, A>{
            (v1:T, v2:S):A;
        }

        // 2. 实现接口
        function 函数名<T, S, A>(v1:T, v2:S):A{
            //...
        }

        // 3. 使用接口实现
        函数名<泛型的具体类型>(...);
        ```

    - 在方法中声明泛型
        ```ts
        // 1. 定义接口
        interface 接口名{
            <T, S, A> (v1:T, v2:S):A;
        }

        // 2. 实现接口
        function 函数名<T, S, A>(v1:T, v2:S):A{
            //...
        }

        // 3. 使用接口实现
        函数名<泛型的具体类型>(...);
        ```

- 编译结果
    - 泛型部分只是 ts 编译器的编译约束
    - 编译结果中不会包含与泛型相关的内容

- 示例
    - 参考代码
        - 示例代码
            - [src/syntax/generic/interface_func.ts](src/syntax/generic/interface_func.ts)
        - 编译结果
            - [src/syntax/js/generic/interface_func.js](src/syntax/js/generic/interface_func.js)
    - 代码内容
        1. 将泛型声明在接口定义中
            - 代码实现
                ```ts
                // 1 将泛型声明在接口定义中
                interface GenericFunc<T>{
                    (value:T):T;
                }

                // 1.1 接口实现
                function getData<T>(value:T):T{
                    return value;
                }

                // 1.2 调用接口实现
                // 1.2.1 固定一种类型并赋值给变量
                let myGetData: GenericFunc<string> = getData;
                console.log(myGetData('qwert'));

                // 1.2.2 在调用时指定类型
                console.log(getData<number>(12345));
                ```
            - 编译结果
                ```js
                // 1.1 接口实现
                function getData(value) {
                    return value;
                }
                // 1.2 调用接口实现
                // 1.2.1 固定一种类型并赋值给变量
                var myGetData = getData;
                console.log(myGetData('qwert'));
                // 1.2.2 在调用时指定类型
                console.log(getData(12345));
                ```
        2. 将泛型声明在方法定义中
            - 代码实现
                ```ts
                // 2 将泛型声明在方法定义中
                interface GenericFunc02{
                    <T> (value: T):T
                }

                // 2.1 接口实现
                function getData02<T>(value: T):T{
                    return value;
                }

                // 2.2 调用接口实现
                // 2.2.1 固定一种类型并赋值给变量
                let myGetData02: GenericFunc<string> = getData02;
                console.log(myGetData02('qwert'));

                // 2.2.2 在调用时指定类型
                console.log(getData02<number>(12345));
                ```
            - 编译结果
                ```js
                // 2.1 接口实现
                function getData02(value) {
                    return value;
                }
                // 2.2 调用接口实现
                // 2.2.1 固定一种类型并赋值给变量
                var myGetData02 = getData02;
                console.log(myGetData02('qwert'));
                // 2.2.2 在调用时指定类型
                console.log(getData02(12345));
                ```

## 泛型接口的实现类
[top](#catalog)
- 定义方法
    ```ts
    // 定义接口
    interface 接口<T, S, A>{...}

    // 定义接口实现类
    //        在类名后面注明接口标识，         接口后面也需要注明接口标识
    class 类名<T, S, A> implements interface 接口<T, S, A>{...}
    ```

- 编译结果
    - 泛型部分只是 ts 编译器的编译约束
    - 编译结果中不会包含与泛型相关的内容

- 示例
    - 参考代码
        - 示例代码
            - [src/syntax/js/generic/implements.js](src/syntax/js/generic/implements.js)
        - 编译结果
            - [src/syntax/js/generic/implements.js](src/syntax/js/generic/implements.js)
    - 代码内容
        - 泛型接口声明
            ```ts
            interface DB<T>{
                add(info:T):boolean;        // 添加
                update(info:T):boolean;     // 更新
                delete(id:string):boolean;  // 删除
                get(id:string):T|null;      // 根据id获取数据
                show():void;                // 打印所有数据
            }
            ```
        - 泛型接口实现
            ```ts
            class MySqlDB<T extends HasID> implements DB<T>{
                private data:{[key:string]:T};
                constructor(){...}
                add(info: T): boolean {...}
                update(info: T): boolean {...}
                delete(id: string): boolean {...}
                get(id: string): T|null {...}
                show():void{...}
            }
            ```

# 模块
[top](#catalog)
- ts中的模块标准遵循 **ES6的模块标准**

# 装饰器
## 装饰器说明
[top](#catalog)
- 什么是装饰器
    - 一种特殊类型的声明

- 装饰器的功能
    - 可以附加到类声明、方法、属性或参数上，可以修改类的行为
    - 可以为一个目标添加多个装饰器来扩展不同的内容

- 装饰器的本质
    - 一个方法

- 常见的装饰器
    - 类装饰器
    - 属性装饰器
    - 方法装饰器
    - 参数装饰器

- 装饰器的写法
    - 普通装饰器，无法传参
        ```ts
        // param 表示被修饰的对象
        function decorator (param:any){
            ...
        }

        // 使用装饰器
        @decorator
        class XXX{...}
        ```
    - 装饰器工厂，可以传参
        ```ts
        // 通过闭包返回一个新的函数作为装饰器
        function decorator (工厂参数){
            // param 表示被修饰的对象
            return function (param:any){
                ...
            }
        }

        // 使用装饰器
        @decorator('xxxx')
        class XXX{...}
        ```

- 装饰器的执行顺序
    - 对于一个目标上的多个装饰器
        - 从下向上执行
    - 对于一个类上的所有类别的装饰器的执行顺序
        1. 属性、方法、方法参数装饰器
            - 类内部的装饰器整体上按照从上到下的方式执行
            - 对于一个目标上的多个装饰器，仍然是从下到上
        2. 类装饰器


## 类装饰器
[top](#catalog)
- 定义方式
    - 普通装饰器
        ```ts
        function 属性装饰器(target: any){
            ...
        }
        ```
    - 装饰器工厂
        ```ts
        function 属性装饰器(工厂参数){
            function (target: any){
                ...
            }
        }
        ```

- 1 个必须参数
    - `target`: 类本身
- 示例
    - 参考
        - 代码
            - [src/syntax/decorator/class.ts](src/syntax/decorator/class.ts)
        - 编译结果
            - [src/syntax/js/decorator/class.js](src/syntax/js/decorator/class.js)
    - 代码内容
        ```ts
        // 1. 定义普通装饰器
        // param 就是被装饰的类本身
        function logClass(target:any):void{
            // 为类添加额外的方法
            target.prototype.info = function(msg:string):void{
                console.log(msg);
            }
        }

        // 2. 定义装饰器工厂
        function ApiURL(url:string):any{
            url = 'http://' + url;
            return function(target:any){
                // 为类添加额外的属性
                target.prototype.apiUrl = url;
            }
        }

        // 3. 使用多个装饰器标识类
        @logClass
        @ApiURL('www.aaaa.bbb')
        class HttpClient{
            constructor(){}

            getData(){}
        }

        // 4. 使用装饰器扩展的内容
        // 默认情况下直接使用会有编译异常
        // 可以将 实例的类型声明为 any 来避免
        // let hc: HttpClient = new HttpClient();
        let hc: any = new HttpClient();
        console.log(hc.apiUrl);     // http://www.aaaa.bbb

        hc.info('test msg')         // test msg
        ```

## 属性装饰器
[top](#catalog)
- 定义方式
    - 普通装饰器
        ```ts
        function 属性装饰器(target:any, propName:any){
            ...
        }
        ```
    - 装饰器工厂
        ```ts
        function 属性装饰器(工厂参数){
            function (target:any, propName:any){
                ...
            }
        }
        ```
- 2 个必须参数
    - `target`: 对于静态成员来说是类的构造函数，对于实例成员是类的原型对象
    - `propName`: 成员的名字

- 示例
    - 参考
        - 代码
            - [src/syntax/decorator/property.ts](src/syntax/decorator/property.ts)
        - 编译结果
            - [src/syntax/js/decorator/property.js](src/syntax/js/decorator/property.js)
    - 代码内容
        ```ts
        // 1. 定义属性装饰器
        // 为属性设置属性值
        function Value(propValue:any){
            // target: 对于静态成员来说是类的构造函数，对于实例成员是类的原型对象
            // propName: 成员的名字
            return function(target:any, propName:any){
                target[propName] = propValue;
            }
        }

        // 2. 使用属性装饰器
        class StudentA{
            // 为属性设值
            @Value('www.aa.bbb')
            name:string | undefined
            constructor(){}

            printInfo():void{
                console.log(this.name);
            }
        }

        let studentA:StudentA = new StudentA();
        studentA.printInfo();   // www.aa.bbb
        ```

## 方法装饰器
[top](#catalog)
- 方法装饰器的功能
    - 方法装饰器会被应用到方法的**属性描述符上**
    - 可以用来监控、修改或替换方法的定义
- 定义方式
    - 普通装饰器
        ```ts
        function 属性装饰器(target:any, propName:any, descriptor:any){
            ...
        }
        ```
    - 装饰器工厂
        ```ts
        function 属性装饰器(工厂参数){
            function (target:any, propName:any, descriptor:any){
                ...
            }
        }
        ```

- 3 个必须参数
    - `target`: 对于静态成员来说是类的构造函数，对于实例成员是类的原型对象
    - `propName`: 成员的名字
    - `descriptor`: 成员的属性描述符

- 如何改写方法？
    1. 通过 `descriptor.value` 获取原始方法，并保存
    2. 重写 `descriptor.value`，添加自定义内容
    3. 在重写的方法中，调用原始方法，来完成原始的功能

- 示例
    - 参考
        - 代码
            - [src/syntax/decorator/method.ts](src/syntax/decorator/method.ts)
        - 编译结果
            - [src/syntax/js/decorator/method.js](src/syntax/js/decorator/method.js)
    - 代码内容
        ```ts
        // 1. 定义方法装饰器
        // 装饰方法，并将方法的所有参数转换为字符串
        function MethodParmasToString(target:any, propName:any, descriptor:any){
            // 1.1 从属性描述符中获取原始的函数，并保存
            let originFn = descriptor.value;

            // 1.2 重新设置函数
            descriptor.value = function(...args:any[]){
                // 1.3 将所有参数转换为String
                args = args.map( n => String(n) );
                // 1.4 重新调用原始方法
                originFn(...args);
            }
        }

        // 2. 使用方法装饰器
        class Logger{
            @MethodParmasToString
            info(...args:any[]):void{
                console.log(args);
            }
        }

        let log:Logger = new Logger();
        log.info(1234, 'aaaa', false);  // [ '1234', 'aaaa', 'false' ]
        ```

## 方法参数的装饰器
[top](#catalog)
- 方法参数装饰器的功能
    - 可以为类的原型增加一些**元数据**

- 定义方式
    - 普通装饰器
        ```ts
        function 属性装饰器(target:any, funcName:any, paramIndex:any){
            ...
        }
        ```
    - 装饰器工厂
        ```ts
        function 属性装饰器(工厂参数){
            function (target:any, funcName:any, paramIndex:any){
                ...
            }
        }
        ```

- 3 个必须参数
    - `target`: 对于静态成员来说是类的构造函数，对于实例成员是类的原型对象
    - `funcName`: 参数名字
    - `paramIndex`: 参数索引

- 示例
    - 参考代码
        - [src/syntax/decorator/param.ts](src/syntax/decorator/param.ts)
    - 代码内容
        ```ts
        // 1. 定义方法参数装饰器
        function Param(target:any, funcName:any, paramIndex:any){
            console.log(target);
            console.log(funcName);
            console.log(paramIndex);
        }

        // 2. 使用方法装饰器
        class LoggerB{
            info(@Param msg):void{
                console.log(msg);
            }
        }

        let logb = new LoggerB();
        logb.info('aaaa');
        ```

[top](#catalog)