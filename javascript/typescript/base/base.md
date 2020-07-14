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
    - [函数重载](#函数重载)
    - [箭头函数](#箭头函数)
- [类](#类)
    - [定义类](#定义类)
    - [类的继承](#类的继承)
    - [类属性的修饰符](#类属性的修饰符)
    - [静态属性与静态方法](#静态属性与静态方法)
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
    let <变量名>:<类型> = 变量值;

    // 变量值为 undefined
    let <变量名>:<类型>;
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
    let <变量名>:<类型>[] = 数组值;

    // 方式2: 通过泛型的方式设置类型
    let <变量名>:Array<<类型>> = 数组值;
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
    let a:[<类型1>, <类型2>, ....] = [<类型1的值>, <类型2的值>, ....]
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
    let <变量名>:<枚举名> = <枚举名>.<标识符>
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
[top](#catalog)
- 参考示例
    - 示例代码
        - [src/syntax/function/fun_params.ts](src/syntax/function/fun_params.ts)
    - 编译结果
        - [src/syntax/js/function/fun_params.js](src/syntax/js/function/fun_params.js)

- 定义有参函数
    ```ts
    function info2Str(name:string, age:number): string{
        return `name=${name}, age=${age}`;
    }
    console.log( info2Str('testName', 22) );
    // 实参与形参类型不一致，ts编译异常
    // console.log( info2Str('testName', "22") );
    ```

- 可选参数， `参数名?:类型`
    - es里方法的实参和形参数量可以不同，但是在ts中必须相同。如果不同，必须配置可选参数
    - 可选参数必须配置到参数的最后
    - 示例
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
        - 编译结果与普通js没有差别，只是通过ts编译器限制了类型
        ```js
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

- 默认参数，`参数名:类型=默认值`
    - 示例
        ```ts
        function getInfo02(name:string, age:number=22):string{
            return `name=${name}, age=${age}`;
        }
        console.log( getInfo02('testName') ); // 使用默认值
        console.log( getInfo02('testName', 33) );
        ```
    - 编译结果
        ```js
        function getInfo02(name, age) {
            // 如果参数是undefined，则使用默认值
            if (age === void 0) { age = 22; }
            return "name=" + name + ", age=" + age;
        }
        ```

- 可变参数，`...参数名:类型[]`
    - 本质就是利用解构运算符 `...` 将所有实参传递到一个数组中
    - **可变参数需要放在参数的最后，且只能有一个**
    - 示例
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
            for (var _i = 1; _i < arguments.length; _i++) {
                others[_i - 1] = arguments[_i];
            }
            for (var i = 0; i < others.length; i++) {
                a += others[i];
            }
            return a;
        }
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
    - 在ts中使用箭头函数
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
    - 代码位置
        - 参考代码
            - [src/syntax/class/extends.ts](src/syntax/class/extends.ts)
        - 编译结果
            - [src/syntax/js/class/extends.js](src/syntax/js/class/extends.js)
    - 示例代码
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
        - 全部被编译为普通js函数，没有特殊处理
            ```js
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
    - 代码位置
        - 参考代码
            - [src/syntax/class/static.ts](src/syntax/class/static.ts)
        - 编译结果
            - [src/syntax/js/class/static.js](src/syntax/js/class/static.js)
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

[top](#catalog)