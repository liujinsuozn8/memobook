<span id="catalog"></span>
- 参考

### 目录--Promise
- [基本知识](#基本知识)
    - [回调函数](#回调函数)
    - [常见的内置错误](#常见的内置错误)
- [Promise说明](#Promise说明)
- [为什么要使用Promise](#为什么要使用Promise)
- [Promise的API](#Promise的API)
- [Promise的使用方法](#Promise的使用方法)
    - [Promise的基本使用](#Promise的基本使用)
    - [解构嵌套的异步调用](#解构嵌套的异步调用)
    - [简化只有数据的Promise对象](#简化只有数据的Promise对象)
    - [Promise.all--等待所有异步调用完成](#Promise.all--等待所有异步调用完成)
- [Promise的几个关键问题](#Promise的几个关键问题)
    - [如何改变Promise的状态](#如何改变Promise的状态)
    - [多个回调函数的执行顺序](#多个回调函数的执行顺序)
    - [改变Promise的状态和指定回调函数的顺序是什么](#改变Promise的状态和指定回调函数的顺序是什么)
    - [promise.then()返回的新Promise的结果状态由什么决定](#promise.then()返回的新Promise的结果状态由什么决定)
    - [Promise如何串联多个操作任务](#Promise如何串联多个操作任务)
    - [Promise异常穿透](#Promise异常穿透)
    - [如何中断Promise链](#如何中断Promise链)
    - [resolve、reject内是异步执行回调函数的](#resolve、reject内是异步执行回调函数的)
- [Promise输出分析](#Promise输出分析)
- [手写Promise](#手写Promise)
    - [定义Promise的整体结构](#定义Promise的整体结构)
    - [Promise构造函数的实现](#Promise构造函数的实现)
    - [promise.then、promise.catch的实现](#promise.then、promise.catch的实现)
    - [Promise.resolve、Promise.reject的实现](#Promise.resolve、Promise.reject的实现)
    - [Promise.all的实现](#Promise.all的实现)
    - [Promise.race的实现](#Promise.race的实现)
    - [自定义实现---resolveDelay、rejectDelay](#自定义实现---resolveDelay、rejectDelay)
- [async、await](#async、await)

# 基本知识
## 回调函数
[top](#catalog)
- 回调函数的定义
    1. 自己定义的
    2. 不会手动调用
    3. 最终会被执行
- 两种类型的回调函数
    - 同步调用的回调函数
        ```js
        const arr = [1, 3, 5];
        // 在 forEach 中声明一个回调函数
        const arrEx = arr.forEach(v => console.log(v));
        console.log('after forEach');
        // 输出:
        // 1    <<<<< 先执行 forEach 的回调
        // 3
        // 5
        // after forEach <<<<< 再执行后面的代码
        ```
    - 异步调用的回调函数
        ```js
        setTimeout(()=> console.log('time end'), 2000);
        console.log('after setTimeout');
        // 输出:
        // after setTimeout <<<< 先执行 普通代码
        // time end         <<<< 再异步执行消息队列中的 setTimeout
        ```
## 常见的内置错误
[top](#catalog)
- 错误的类型
    - Error: 所有错误的父类型
    - ReferenceError: 引用的变量不存在
    - TypeError: 数据类型不正确
    - RangeError: 值不再合法范围内，如递归的栈溢出
    - SyntaxError: 语法错误
- 错误处理
    - 捕获异常
        ```js
        try{
            statements
        } catch(e){
            statements
        } finally{
            statements
        }
        ```
    - 抛出异常
        ```js
        throw error
        ```
- 错误对象中的属性
    - message: 异常信息
    - stack: 函数调用栈信息

# Promise说明
[top](#catalog)
- Promise 是什么
    - Promise 是JS中进行异步编程的解决方案
        - `Promise对象` 代表了未来某个将要发生的事件，通常是一个异步操作
        - 有了 `Promise对象`，可以用同步的编码方式来处理异步操作，避免多层嵌套的回调函数
    - 在语法上，Promise 是一个构造函数
    - 在功能上，Promise 对象封装了一个异步操作并可以获取其结果

- 异步处理的场景--网络请求
    - 请求的发送与异步相应过程
        1. 封装网络请求函数，并执行
        2. 因为不能立即返回结果，所以会在调用时传入另一个回调函数
        3. 当请求返回时，调用回调函数，处理返回的数据
    - 对于简单的网络请求，使用回调函数比较容易
    - 当网络请求比较复杂时，可能会产生多层嵌套的回调，此时可以使用 `Promise` 来处理

- Promise对象的三种状态
    1. pending，等待状态
    2. fulfilled，成功状态
        - 记做fulfilled，控制台会输出resolved
    3. rejected，失败状态
- Promise对象的状态变化，<span style='color:red'>一个 Promise 对象的状态只能改变一次</span>
    - pending ---> resolve(...) ---> fulfilled
    - pending ---> reject(...) ---> rejected
    - 抛出异常时，pending ---> rejected
        - Promise对象返回的数据是抛出的异常数据
- Promise对象的结果
    - 无论成功还是失败，都会有一个结果数据
    - 成功的数据一般称为 value，失败的结果数据一般称为reason
- Pomise执行流程   ?????
    ```js
                                                                    then
                                ---> 成功 Promise对象 fulfilled --> 回调OnResolved()
    new Promise ---> 执行异步操作                                                   ---> 新的Promise对象
                                ---> 失败 Promise对象 rejected  --> 回调OnRejected()
                                                执行时，抛出了异常      then/catch

    ```


# 为什么要使用Promise
[top](#catalog)
- 指定回调函数的方式更加灵活
    - 旧的方式需要启动异步任务之前指定
    - Promise的回调函数设置
        1. 整个异步请求的内容都被包含在Promise对象中，**随时可以设置响应函数**
        2. Promise对象可以被传递到任意的处理函数中
        3. Promise使回调的设置脱离的固定的位置，所以可以解决回调地狱的问题
            - 回调地狱本身就是由<span style='color:red'>必须在启动异步任务之前指定回调函数造成的</span>
                - 因为回调的设置位置固定，所以无论如何修改，都无法解决问题
- Promise支持链式调用
    - 每一个回调都是以前一个回调为基础或自行的
    - 解决了回调地狱中的一些问题
        1. 函数的连续嵌套问题
        2. 理解困难的问题
        3. 异常处理困难的问题

- 异步处理最优的解决方案: `async/await`
    - `async/await` 中不需要回调函数，全部都是同步的
        - 但是 Promise 仍然需要设置回调函数
    - `async/await` 也需要Promise的配合

# Promise的API
[top](#catalog)
- Promise构造函数: `new Promise(executor){}`
    - executor: `(resolve, reject)=>{...}`
    - resolve = `value=>{}`: 内部处理成功时，需要手动调用该函数
    - reject = `value=>{}`: 内部处理失败时，需要手动调用该函数
    - resolve，reject <span style='color:red'>都只接受第一个有效的参数</span>
- Promise.prototype.then: `(onResolved, onRejected) => {}`
    - onResolved，`value=>{}`: 成功时的回调函数
    - onRejected，`reason=>{}`: 失败时的回调函数
    - 指定成功和失败的回调，如果要继续执行，需要返回一个新的Promise对象
        - 这也是链式调用的基础
    - 如果没有返回一个 Promise 对象，则下一个 `then/catch` 将会接受到 `undefined`
- Promise.prototype.catch: `(onRejected) => {}`
    - onRejected，`reason=>{}`: 失败时的回调函数
    - 本质是 `then()` 的语法糖，相当于: `then(undefined, onRejected)`
- Promise.resolve: `(value) => {}`
    - value: 成功的数据，或者Promise对象
    - 返回一个成功/失败的 Promise 对象
- Promise.reject: `(reason) => {}`
    - reason: 失败的原因
        - 不能是一个 Promise
    - 返回一个失败的 Promise 对象
- Promise.all: `([promise01, promise02,...])=>{}`
    - `[promise01, promise02,...]`: 包含 n 个 Promise 对象的数组
    - 返回一个新的 Promise 对象
        - 对象中包含n个请求的结果，并且顺序与 Promise 数组顺序相同
        - 只有所有的请求都成功时，才返回
        - 只要有一个失败了，则立刻返回失败，不会再等待没有完成的异步请求
- Promise.race: `([promise01, promise02,...])=>{}`
    - `[promise01, promise02,...]`: 包含 n 个 Promise 对象的数组
    - 返回一个新的 Promise 对象
        - <span style='color:red'>第一个完成的Promise</span>，就是最终的结果
        - 如果第一个完成的Promise成功了，结果就是成功的
        - 如果第一个完成的Promise失败了，结果就是失败的

# Promise的使用方法
## Promise的基本使用
[top](#catalog)
- 创建Promise对象
    - `new Promise((resolve, reject)=>{...})`
    - 传入的回调函数
        - 需要有两个参数: `resolve`、`reject`
        - 在函数中进行初始化，将状态修改为 `pending`
        - <span style='color:red'>函数的执行是同步的，不会作为回调函数使用</span>
        - 函数内部的异步操作，仍然是异步的
    - 在函数内部调用异步操作，并通过 resolve、reject 来修改 Promise对象的状态
    - 如果没有调用 resolve、reject，则 Promise 对象的状态将一直是 `pending`

- 修改 `Promise对象` 的状态
    - `resolve(data)`
        - 异步任务成功 时执行
        - 修改promise的状态为`fulfilled`
        - 通过data参数向外部传值
    - `reject(data)`
        - 异步任务失败 时执行
        - 修改promise的状态为`rejected`
        - 通过data参数向外部传值

- 异步处理的方法
    - `then(OnResolved, OnRejected)`
        - 处理方式方式
            ```js
            new Promise(...).then(
                data=>{...},    // fulfilled 状态的响应函数, 执行 resolve(data) 后触发
                error=>{...}    // rejected 状态的响应函数, 执行 reject(error) 后触发
            )
            ```
        - 响应的触发条件
            - Promise对象的状态从 `pending` 变为 `fulfilled` 或 `rejected`
                - 即: 在`Promise对象`中主动调用 `resolve()` 或 `reject()`
            - 如果 `Promise对象` 的状态将一直是 `pending`，将会一直等待
        - 一般开发中，不会将 `rejected`状态 的响应写在 `then()` 中，会通过 `catch()` 来处理

    - `catch(OnRejected)`，捕获 `reject` 的异常

- 如果为一个 Promise 对象设置了多个回调`then`、`catch`，会按照设置顺序依次调用
    ```js
    var p = Promise.resolve(123456);
    // 将会顺序执行两个回调函数
    p.then(value=>console.log(`first value= ${value}`));
    p.then(value=>console.log(`second value= ${value}`));

    // 输出:
    // first value= 123456
    // second value= 123456
    ```

- 示例

    - 参考代码
        - [src/promise/usage/base.js](src/promise/usage/base.js)
    - 代码内容
        ```js
        let promise = new Promise((resolve, reject)=>{
            // 初始化promise， 状态：pending
            console.log("promise init start")

            // 执行异步操作，通常是发送ajax请求、开启定时器
            setTimeout(()=>{
                console.log("promise inner timer")
                let data = "timer data"
                // 根据异步任务的返回结果去修改promise的状态
                // 异步任务成功
                resolve(data) //修改promise的状态为resolved
                // 异步任务失败
                // reject(data) //修改promise的状态为rejected
            }, 2000)
        })

        console.log("promise init end")

        promise.then(
            data=>console.log("success " + data),  // 成功的回调
            error=>console.log("failure " + error)   // 失败的回调
        )

        console.log("promise end")

        // 输出:
        // promise init start
        // promise init end
        // promise end
        // promise inner timer
        // success timer data
        ```

## 解构嵌套的异步调用
[top](#catalog)
- 解构嵌套的异步调用，并处理异常
    - 解构方式
        - 通过 `then()`、`catch()` 的链式调用，用同步式的代码来解构嵌套回调
    - <span style='color:red'>如何完成链式调用？</span>
        - 在每一层解构出来的嵌套中，创建并返回新的 `Promise对象`
    - 基本写法
        ```js
        new Promise( (resolve, reject) =>{
            ...
        }).then( data=>{
            //... 业务处理
            // 创建新的 Promise 对象，相当于第一层嵌套
            return new Promise((resolve, reject) =>{...});
        }).then( data=>{
            //... 业务处理
            // 创建新的 Promise 对象，相当于第二层嵌套
            return new Promise((resolve, reject) =>{...});
        }).catch( error=>{
            // 异常处理
        });
        ```
    - `catch()`，捕获到异常后的执行顺序
        - `catch()` 前面没有执行的 `then()` 会被忽略
        - `catch()` 内部如果返回了新的 `Promise对象`，则后面的 `then` 将会继续执行。否则整个链式调用会停止
            ```js
            new Promise( (resolve, reject) =>{
                ...             //<<<<<<< 1. 执行异步操作
            }).then( data=>{
                return new Promise((resolve, reject) =>{
                    reject()    //<<<<<<< 2. 抛出异常
                });
            }).then( data=>{
                return new Promise((resolve, reject) =>{
                    resolve()   //<<<<<<< 上一个执行抛出了异常，将被跳过
                });
            }).catch( error=>{
                // 异常处理     //<<<<<<< 3. 捕获异常
                return new Promise((resolve, reject) =>{
                    resolve()   //<<<<<<< 4. 继续执行
                });
            }).then(data=>{
                ...             //<<<<<<< 5. 继续执行，响应的是上面的 catch 中返回的 Promise对象
            });
            ```

- 示例
    - 参考代码
        - [src/promise/usage/nest_promise.js](src/promise/usage/nest_promise.js)
    - 代码内容
        ```js
        new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve();
            }, 1000);   // 延迟1s，模拟异步操作
        }).then(data => {
            // 第 1 层嵌套
            console.log('nest 1');

            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    resolve();
                }, 1000);   // 延迟1s，模拟异步操作
            });
        }).then(data => {
            // 第 2 层嵌套
            console.log('nest 2');

            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    resolve();
                }, 1000);   // 延迟1s，模拟异步操作
            });
        }).then(data => {
            // 第 3 层嵌套
            console.log('nest 3');
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    reject('out from nest3');   // 手动创建一个异常
                    // resolve();
                }, 1000);   // 延迟1s，模拟异步操作
            });
        }).then(data => {
            // 第 4 层嵌套
            console.log('nest 4');
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    resolve();
                }, 1000);
            });
        }).catch(error => {
            // 处理异常
            console.log(`err = ${error}`);
            // 创建新的异步请求
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    resolve('catch error data');
                }, 1000);
            });
        }).then(data => {
            // 第 5 层嵌套
            console.log(`nest 5: ${data}`);
        });

        /* 输出:
            nest 1
            nest 2
            nest 3
            err = out from nest3
            nest 5: catch error data

            因为在第 3 层出现了异常，被 catch捕获了，所以不会执行第4层嵌套
            catch 中，又返回了新的 Promise对象，所以会继续执行第5层
        */
        ```

## 简化只有数据的Promise对象
[top](#catalog)
- 简化的条件
    - Promise 中只有数据，没有逻辑处理
    - 在链式调用中，为了完成后续的链式调用，必须要返回一个 `Promise对象`
        - 即使只有同步代码，也要返回 `Promise对象`，否则链式调用会停止

- 通过 `Promise` 的静态方法实现简写
    - 静态方法
        - `Promise.resolve(data)`
            - data的类型
                - 基本数据类型
                - 另一个 Promise对象
                - `thenable` ?????
            - 返回值
                - Promise对象
                - 如果 data 是另一个 Promise对象，则不做封装，直接返回该对象
        - `Promise.reject(error)`
            - error的类型
                - `Error()` 构造函数的示例
                - 基本数据类型
            - 返回值
                - Promise对象
    - 简化过程
        - 简化前
            ```js
            new Promise( (resolve, reject) =>{
                ...             //<<<<<<< 1. 执行异步操作
            }).then( data=>{
                return new Promise((resolve, reject) =>{
                    resolve()   //<<<<<<< 2. 继续执行
                });
            }).then( data=>{
                return new Promise((resolve, reject) =>{
                    reject()    //<<<<<<< 3. 抛出异常
                });
            }).catch( error=>{
                // 异常处理     //<<<<<<< 3. 捕获异常
                return new Promise((resolve, reject) =>{
                    resolve()   //<<<<<<< 4. 继续执行
                });
            }).then( data=>{
                ....
            });
            ```
        - 简化后
            ```js
            new Promise( (resolve, reject) =>{
                ...             //<<<<<<< 1. 执行异步操作
            }).then( data=>{
                return Promise.resolve(...);    //<<<<<<< 2. 继续执行
            }).then( data=>{
                return Promise.reject(...);    //<<<<<<< 3. 抛出异常
            }).catch( error=>{
                // 异常处理     //<<<<<<< 3. 捕获异常
                return Promise.resolve(...);   //<<<<<<< 4. 继续执行
            }).then( data=>{
                ....
            });
            ```

- 使用 `return` 和 `throw` 进一步简化
    - 两个语句的简化关系
        - `return data` 简化 `Promise.resolve(data);`
        - `throw error` 简化 `Promise.reject(error);`
    - 数据返回、异常抛出时，Promise底层会封装成 Promise对象
    - 简化过程
        - 简化前
            ```js
            new Promise( (resolve, reject) =>{
                ...             //<<<<<<< 1. 执行异步操作
            }).then( data=>{
                return Promise.resolve(...);    //<<<<<<< 2. 继续执行
            }).then( data=>{
                return Promise.reject(...);    //<<<<<<< 3. 抛出异常
            }).catch( error=>{
                // 异常处理     //<<<<<<< 3. 捕获异常
                return Promise.resolve(...);   //<<<<<<< 4. 继续执行
            }).then( data=>{
                ....
            });
            ```
        - 简化后
            ```js
            new Promise( (resolve, reject) =>{
                ...             //<<<<<<< 1. 执行异步操作
            }).then( data=>{
                return ...;     //<<<<<<< 2. 继续执行
            }).then( data=>{
                throw ...;      //<<<<<<< 3. 抛出异常
            }).catch( error=>{
                // 异常处理     //<<<<<<< 3. 捕获异常
                return ...;     //<<<<<<< 4. 继续执行
            }).then( data=>{
                ....
            });
            ```

- 示例
    - 参考代码
        - [src/promise/usage/simple_promise.js](src/promise/usage/simple_promise.js)
    - 使用 return、throw 来简化只有同步代码的Promise对象
        ```js
        new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve('nest1');
            }, 1000);   // 延迟1s，模拟异步操作
        }).then(data => {
            // 第 1 层嵌套
            console.log(data);
            return 'nest2';
        }).then(data => {
            // 第 2 层嵌套
            console.log(data);
            return 'nest3';
        }).then(data => {
            // 第 3 层嵌套
            console.log(data);

            // return Promise.resolve('nest4');
            throw 'error from nest3';   // 抛出一个异常
        }).then(data => {
            // 第 4 层嵌套
            console.log(data);
            return 'nest5';
        }).catch(error => {
            // 处理异常
            console.log(error);
            // 创建新的Promise对象
            return 'catch error data';
        }).then(data => {
            // 第 5 层嵌套
            console.log(`nest 5: ${data}`);
        });

        /* 输出:
            nest 1
            nest 2
            nest 3
            error from nest3
            nest 5: catch error data
        */
        ```

## Promise.all--等待所有异步调用完成
[top](#catalog)
- `Promise.all(iterator)`
    - 参数 iterator
        - 需要是一个可迭代对象，包括: `Array`、`String`
        - 迭代对象内部可以包含: Promise对象、普通数据类型
    - 返回值

        |参数内容|返回值|
        |-|-|
        |iterator是空对象|一个 `fulfilled` 状态的 Promise对象|
        |iterator中不包含 Promise对象|一个 `fulfilled` 状态的 Promise对象|
        |iterator中包含 Promise对象|一个 `pending` 状态的 Promise对象|
    - Promise对象整体的状态
        - 所有Promise对象都变成 `fulfilled` 状态，则整体为 `resolve` 状体
        - 当有一个 Promise对象变成 `rejected` 状态时，立刻将整体设为 `rejected` 状态，并忽略未完成的异步操作
- 异步结束后，返回的数据
    - 当整体为 `resolve` 状态
        - 永远都会返回一个数组
        - 数组中元素的顺序 与 参数iterator中的顺序相同
    - 当整体为 `rejected` 状态
        - 返回 `rejected` 状态的 Promise对象的数据
- 示例
    - 参考代码
        - [src/promise/usage/all.js](src/promise/usage/all.js)
    - 代码内容
        1. 全部 Promise对象是 fulfilled 状态
            ```js
            Promise.all([
                new Promise((resolve, reject)=>{
                    setTimeout(()=>resolve(2), 2000);
                }),
                new Promise((resolve, reject)=>{
                    setTimeout(()=>resolve('abcd'), 1000);
                })
            ]).then(data=>{
                console.log(data);  // 输出: [2, "abcd"]
            })
            ```
        2. 存在 Promise对象是 rejected 状态
            ```js
            Promise.all([
                new Promise((resolve, reject)=>{
                    setTimeout(()=>reject(2), 2000);
                }),
                new Promise((resolve, reject)=>{
                    setTimeout(()=>resolve('abcd'), 1000);
                })
            ]).then(data=>{
                console.log(data);
            }).catch(error=>{
                console.log(`error = ${error}`);    // 输出: error = 2
            })
            ```

# Promise的几个关键问题
## 如何改变Promise的状态
[top](#catalog)
- 两种改变方法
    1. 通过 resolve、reject
        - resolve(value): pending-->fulfilled
        - reject(reason): pending-->rejected
    2. 执行时，抛出了异常: pending-->rejected
        ```js
        new Promise((resolve, reject)=>{
            console.log('start');
            throw new Error('test error');
        }).then(console.log)
        .catch(reason=>console.log(`reason =${reason}`));
        // start
        // reason =Error: test error
        ```

## 多个回调函数的执行顺序
[top](#catalog)
- 如果为一个 Promise 对象设置了多个回调`then`、`catch`，会按照设置顺序依次调用

## 改变Promise的状态和指定回调函数的顺序是什么
[top](#catalog)
- 两个操作的顺序取决于代码，都有可能先执行
- 可能的操作顺序
    1. 常规顺序: 先指定回调函数、再改变状态
        ```js
        const p1 = new Promise((resolve, reject)=>{
            setTimeout(
                ()=>{ resolve() },  // 2. 再改变状态
                2000
            );
        })

        // 1. 先指定回调函数
        p1.then(
            value=>{},
            error=>{}
        )
        ```
    2. 先改变状态，再指定回调函数
        ```js
        new Promise((resolve, reject)=>{
            resolve(123);  // 1. 先改变状态（这里是同步执行的）
        }).then(
            value=>{},  // 2. 再指定回调函数
            error=>{}
        )
        ```

## promise.then()返回的新Promise的结果状态由什么决定
[top](#catalog)
- 简单表达: 由 `then()` 指定的回调函数的执行结果决定
- 详细表达:
    1. 如果抛出异常，新 Promise 对象变为rejected，reason为抛出的异常
    2. 如果返回的是非 Promise 的任意值，新 Promise 变为**fulfilled**，value为返回值
        - 如果没有返回值，则 value 为 `undefined`
    3. 如果返回了一个新的 Promise，则结果为新 Promise 的结果
- 示例
    - 初始状态为rejected。在`then`中，返回一个undefined，状态为resolve
        ```js
        Promise.reject(2)
        .then(
            // 返回一个undefined，状态为resolve
            value=>console.log(`onResolve = ${value}`),
            reason=>console.log(`onReject = ${reason}`),
        )
        .then(
            value=>console.log(`onResolve2 = ${value}`),
            reason=>console.log(`onReject2 = ${reason}`),
        )
        // 输出:
        // onReject = 2             <<<<<< 初始状态为 rejected
        // onResolve2 = undefined   <<<<<< 返回一个undefined，状态为resolve
        ```
    - 初始状态为fulfilled。在`then`中，返回一个undefined，状态为resolve
        ```js
        Promise.resolve(2)
        .then(
            // 返回一个undefined，状态为resolve
            value=>console.log(`onResolve = ${value}`),
            reason=>console.log(`onReject = ${reason}`),
        )
        .then(
            value=>console.log(`onResolve2 = ${value}`),
            reason=>console.log(`onReject2 = ${reason}`),
        )
        // 输出:
        // onResolve = 2            <<<<<< 初始状态为 fulfilled
        // onResolve2 = undefined   <<<<<<  返回一个undefined，状态为resolve
        ```
    - 初始状态为fulfilled。在`then`中，返回一个rejected状态的promise
        ```js
        Promise.resolve(2)
        .then(
            // 返回一个rejected状态的promise
            value=>(console.log(`onResolve = ${value}`), Promise.reject(5)),
            reason=>console.log(`onReject = ${reason}`),
        )
        .then(
            value=>console.log(`onResolve2 = ${value}`),
            reason=>console.log(`onReject2 = ${reason}`),
        )
        // 输出:
        // onResolve = 2    <<<<<< 初始状态为 fulfilled
        // onReject2 = 5    <<<<<< 返回一个rejected状态的promise
        ```

## Promise如何串联多个操作任务
[top](#catalog)
- 同步操作: 可以直接计算出结果，并返回
- 异步操作:
    - 需要创建一个新的 Promise 对象来处理异步操作
    - 并将新的 Promise 对象返回
    - 新 Promise 传递的数据作为下一个 `then/catch` 的数据

## Promise异常穿透
[top](#catalog)
- 通过 `then()` 进行链式调用时，可以在最后通过 `catch()` 指定失败的回调
- 出现异常时，会跳过多个 `then()`，由 `catch()` 捕获异常并处理
    ```js
    Promise.reject(2)   // 1. 创建一个异常
    .then(
        value=>onsole.log(`onResolve1 = ${value}`),
    )
    .then(
        value=>console.log(`onResolve2 = ${value}`),
    )
    .catch(
        // 2. 该异常将会直接跳过前面所有的 then，由 catch 捕获并处理
        reason=>console.log(`catch = ${reason}`)
    );
    // 输出:
    // catch = 2
    ```

- 异常是如何穿透的？
    - 可以实现穿透的两种默认实现

        |方法|穿透方式|
        |-|-|
        |在每个`then`中执行 `reason=>{throw reason}`|通过抛出异常，来连续执行每个`then`中的 `onRejected` 方法|
        |在每个`then`中执行 `reason=>Promise.reject(reason)`|手动返回一个`rejected`状态的promise，来连续执行每个`then`中的 `onRejected` 方法|

    - 相当于
        ```js
        Promise.reject(2)
        .then(
            value=>onsole.log(`onResolve = ${value}`),
            reason=>{throw reason}, // 将异常抛出到下一层
            // reason=>Promise.reject(reason);  // 通过返回异常promise来跳过异常
        )
        .then(
            value=>console.log(`onResolve2 = ${value}`),
            reason=>{throw reason}, // 将异常抛出到下一层
            // reason=>Promise.reject(reason);  // 通过返回异常promise来跳过异常
        )
        .catch(
            reason=>console.log(`catch = ${reason}`)
        )
        ```

- 异常穿透的前提
    1. 在Promise的默认实现上: <span style='color:red'>抛出异常时的处理</span>
        - 返回结果是异常数据
        - 状态的变化: pending ---> rejected
            - 使得可以由下一层的`onRejected`处理
    2. 在代码上: 链式调用的 `then` 中，不能出现 `onRejected` 函数
        - 如果有，异常会被提前捕获


## 如何中断Promise链
[top](#catalog)
- 通过返回一个`pending`状态的、不会改变状态的 空Promise 对象来处理
    - 即不使用 resolve、reject，让 Promise 的状态一直维持在 `pending`
        ```js
        return new Promise(()=>{})
        ```
    - 通过状态的限制来中断Promise 链
    - 这种方式可以中断 Promise 链，并且不会阻塞程序的执行
- 示例
    - 参考代码
        - [src/promise/chain/interrupt.js](src/promise/chain/interrupt.js)
    - 代码内容
        ```js
        Promise.reject(2)
        .then(
            value=>onsole.log(`onResolve = ${value}`),
            // reason=>{throw reason},
            reason=>Promise.reject(reason)
        )
        .then(
            value=>console.log(`onResolve2 = ${value}`),
            // reason=>{throw reason},
            reason=>Promise.reject(reason)
        )
        .catch(
            reason=>{
                console.log(`catch = ${reason}`);
                return new Promise(()=>{}); // 返回一个空Promise对象，中断Promise链
            }
        ).then(
            value => console.log(`end value: ${value}`),
            reason => console.log(`end reason: ${reason}`),
        )
        ```

## resolve、reject内是异步执行回调函数的
[top](#catalog)
- 在resolve、reject内
    - 修改状态时同步的
    - 回调函数的执行时异步的

- resolve、reject的执行只是启动 `then`、`catch`
- `then`、`catch` 的真实调用，需要等到Promise中的逻辑执行完成之后
    - 无论Promise中有多少延迟

- 示例
    ```js
    new Promise(resolve=>{
        setTimeout(()=>{
            resolve('fulfilled');
            for(let i = 0 ;i < 1000000000; i++);    // 创建一个延迟
            console.log('after resolve');
        }, 2000)
    }).then(console.log)

    console.log('end');

    // 输出:
    // end
    // after resolve <<<< 先输出了延时器中的代码
    // fulfilled      <<<< 然后执行了 resolve
    ```

# Promise输出分析
[top](#catalog)
- 代码内容
    ```js
    new Promise((resolve,reject)=>{
        console.log('1');
        resolve();
    }).then(()=>{
        console.log('2');
        new Promise((resolve, reject)=>{
            console.log('3');
            resolve();
        }).then(()=>{
            console.log('4');
        }).then(()=>{
            console.log('5');
        })
    }).then(()=>{
        console.log('6')
    })

    new Promise((resolve, reject)=>{
        console.log('7');
        resolve()
    }).then(()=>{
        console.log('8');
    })
    ```
- 执行过程fx
    1. 输出内容: `1`
        - 执行内容
            ```js
            new Promise((resolve,reject)=>{ // 1. 创建 【Promise--01】
                console.log('1');           // 2. 同步输出 1
                resolve();                  // 3. 同步执行，并且还没有回调函数
            }).then(()=>{                   // 4. 创建【Promise--02】，fulfilled状态，进入微任务队列等待
                console.log('2');
                new Promise((resolve, reject)=>{
                    console.log('3');
                    resolve();
                }).then(()=>{
                    console.log('4');
                }).then(()=>{
                    console.log('5');
                })
            }).then(()=>{                   // 5. 创建【Promise--03】pending状态
                console.log('6')
            })
            ```
        - 微任务队列: `[Promise--02]`

    2. 输出内容: `1 7`
        - 执行内容
            ```js
            new Promise((resolve, reject)=>{// 1. 创建【Promise--04】
                console.log('7');           // 2. 同步输出 7
                resolve();                  // 2. 同步执行，并且还没有回调函数
            }).then(()=>{                   // 3. 创建【Promise--05】，状态fulfilled，进入微任务对类
                console.log('8');
            })
            ```
        - 微任务队列: `[Promise--02, Promise--05]`

    3. 从微任务队列取出: `[Promise--02]`, 输出内容: `1 7 2 3`
        - 执行内容
            ```js
            }).then(()=>{                           // 1. 取出执行 Promise--02
                console.log('2');                   // 2. 同步输出 2
                new Promise((resolve, reject)=>{    // 3. 创建【Promise--06】
                    console.log('3');               // 4. 同步输出 3
                    resolve();                      // 5. 同步执行，并且还没有回调函数
                }).then(()=>{                       // 6. 创建【Promise--07】fulfilled状态，进入微任务队列
                    console.log('4');
                }).then(()=>{                       // 7. 创建【Promise--08】pending状态
                    console.log('5');
                })
                                                    // 8. 此时这个then的回调已经执行完成
                                                    //    Promise--07、08 都在等待执行

                                                    // 9. 返回 undefined，状态为 fulfilled
            }).then(()=>{                           // 10 创建 【Promise--09】，状态为fulfilled，进入微任务队列
                console.log('6')
            })
            ```
        - 微任务队列: `[Promise--05, Promise--07, Promise--09]`
    4. 从微任务队列取出: `[Promise--05]`, 输出内容: `1 7 2 3 8`
        - 执行内容
            ```js
            }).then(()=>{
                console.log('8');   // 1. 同步输出 8
            })
            ```
        - 微任务队列: `[Promise--07, Promise--09]`
    5. 从微任务队列取出: `[Promise--07]`, 输出内容: `1 7 2 3 8 4`
        - 执行内容
            ```js
            .then(()=>{
                console.log('2');
                new Promise((resolve, reject)=>{
                    console.log('3');
                    resolve();
                }).then(()=>{
                    console.log('4');   // 1. 同步输出 4，状态 fulfilled
                }).then(()=>{           // 2. 内部准备调用这个回调，【Promise--08】进入微任务队列
                    console.log('5');
                })
            })
            ```
        - 微任务队列: `[Promise--09, Promise--08]`
    6. 从微任务队列取出: `[Promise--09]`, 输出内容: `1 7 2 3 8 4 6`
        - 执行内容
            ```js
            }).then(()=>{           // 1. 同步输出 6
                console.log('6')
            })
            ```
        - 微任务队列: `[Promise--08]`
    7. 从微任务队列取出: `[Promise--08]`, 输出内容: `1 7 2 3 8 4 6 5`
        - 执行内容
            ```js
            }).then(()=>{
                console.log('2');
                new Promise((resolve, reject)=>{
                    console.log('3');
                    resolve();
                }).then(()=>{
                    console.log('4');
                }).then(()=>{           // 1. 同步输出 5
                    console.log('5');
                })
            })
            ```
        - 微任务队列: `[Promise--08]`

# 手写Promise
## 定义Promise的整体结构
[top](#catalog)
- 参考代码
    - [src/promise/mypromise/01structure.js](src/promise/mypromise/01structure.js)
- 代码内容
    ```js
    // Promise构造函数的实现
    function MyPromise(executor){
        executor(resolve, reject)
    }

    // 接收成功、失败的响应函数，并返回一个新的Promise对象
    MyPromise.prototype.then = function(onResolved, onRejected){}

    // 接收失败的响应函数，并返回一个新的Promise对象
    MyPromise.prototype.catch = function(onRejected){}

    // 返回一个成功的Promise对象
    MyPromise.resolve = function(value){}
    // 返回一个失败的Promise对象
    MyPromise.reject = function(reason){}

    // 返回一个Promise
    // 只有所有Promise都成功时，才成功；只要有一个失败，则立刻返回一个失败的Promise
    MyPromise.all = function(promises){}

    // 返回一个Promise。返回第一个完成的 Promise
    MyPromise.race = function(promises){}

    module.exports = MyPromise
    ```

## Promise构造函数的实现
[top](#catalog)
- 实现的要点
    1. 需要在构造函数内，用<span style='color:red'>同步的方式</span>执行 `executor`
    2. 执行 `executor` 前，需要创建 `resolve`、`reject` 函数
    3. `resolve`、`reject` 内部，用<span style='color:red'>异步的方式</span>执行所有的回调函数
        - 通过 `setTimeout` 异步执行所有的回调函数
    4. 需要将状态保存在promise对象中，如`status`
        - 执行 `resolve`、`reject` 时需要修改状态
        - <span style='color:red'>promise只能修改一次状态</span>，所以需要在`resolve`、`reject`内检查状态是由已被修改
    5. 回调函数需要保存在一个数组中
        - 保存的格式: `{onResolved(){}, onRejected(){}}`

- 保留的问题
    - `then`、`catch` 无法链式调用

- 参考代码
    - [src/promise/mypromise/02constructor.js](src/promise/mypromise/02constructor.js)
- 测试代码
    - [src/promise/mypromise/02test.js](src/promise/mypromise/02test.js)
- 实现内容
    ```js
    function MyPromise(executor) {
        // 1. 保存 this 对象，使得在 resolve、reject 中都可以使用
        const self = this;
        // 或者为 resolve、reject 绑定this为当前对象
        // resolve = resolve.bind(this);
        // reject = reject.bind(this);

        this.status = 'pending';// 给Promise对象指定指定初始状态
        this.data = undefined;  // 用于保存结果数据
        this.callbacks = [];    // 保存回调函数，每个元素的结构: `{onResolved(){}, onRejected(){}}`

        // 2. 定义 resolve、reject 函数
        function resolve(value) {
            if (self.status !== 'pending') return;  // 只能修改一次状态
            self.status = 'fulfilled';              // 修改状态
            self.data = value;                      // 保存value数据

            if (self.callbacks.length > 0) {
                // 4. 如果 callbacks 中已经有了回调函数
                // （常规顺序：先设置回调函数，再改变状态)
                // 通过 setTimeout，【异步执行】所有成功的回调
                setTimeout(
                    () => self.callbacks.forEach(cbObj => cbObj.onResolved(value))
                );
            }
        }
        function reject(reason) {
            // 判断状态，并且只能修改一次状态
            if (self.status !== 'pending') return;  // 只能修改一次状态
            self.status = 'rejected';               // 修改状态
            self.data = reason;                     // 保存value数据

            if (self.callbacks.length > 0) {
                // 4. 如果 callbacks 中已经有了回调函数
                // （常规顺序：先设置回调函数，再改变状态)
                // 通过 setTimeout，【异步执行】所有失败的回调
                setTimeout(
                    () => self.callbacks.forEach(cbObj => cbObj.onRejected(reason))
                );
            }
        }

        // 3. 同步调用 executor
        // 为了实现抛出异常时，状态变成 rejected，并且传递异常数据，
        // 需要在 try...catch 内执行 executor
        try {
            executor(resolve, reject);
        } catch (e) {
            reject(e);
        }
    }
    ```

## promise.then、promise.catch的实现
[top](#catalog)
- `then` 的实现要点
    1. `then`中要返回一个 Promise 对象，这样才能链式调用
    2. 回调函数 `onResolved`、`onRejected` 的执行方法
        - 如果回调函数返回的是 Promise 对象
            - 当前返回的结果与状态，由回调函数的结果决定
            - `result.then(新Promise--resolve, 新Promise--reject)`
        - 如果回调函数返回的不是 Promise 对象
            - 返回 `fulfilled` 状态的 Promise 对象
            - 回调函数的返回值就是 Promise 对象返回的数据
            - `新Promise的resolve(result)`
        - 如果回调函数抛出异常
            - 返回 `rejected` 状态的 promise
            - reason就是error
            - `新Promise--reject(error)`
    3. 如果当前状态已经变成: `fulfilled`、`rejected`，则需要**异步调用**回调函数
    4. 如果当前状态是: `pending`，需要将 `onResolved`、`onRejected` 添加到回到函数列表中
        - 回调函数列表中的函数都是异步执行的，所以不需要做异步处理
        - 最终需要返回一个新的 Promise 对象，而原始的回调函数没有这种功能，需要按照 `要点2` 对回调函数进行包装
    5. 需要设置默认的回调函数
        - onResolve: `value => value`
            - 未设置 `onResolve` 时，直接将数据传递给下一层 Promsie
        - onRejected: `error => { throw error }`
            - 未设置 `onRejected` 时，需要有异常穿透的能力
                - 参考: [Promise异常穿透](#Promise异常穿透)

- `catch` 的实现要点
    - 直接使用 `then` 实现
    - 将 `onResolved` 设为 `undefined`，来使用 `then` 中的默认回调函数

- `then`的执行流程图
    - ![图](?????)

- 参考代码
    - [src/promise/mypromise/03then.js](src/promise/mypromise/03then.js)
- 测试代码
    - [src/promise/mypromise/03test.js](src/promise/mypromise/03test.js)
- 实现内容
    ```js
    // 接收成功、失败的响应函数，并返回一个新的Promise对象
    MyPromise.prototype.then = function (onResolved, onRejected) {
        const self = this;

        // 设置默认回调函数
        onResolved = typeof onResolved === 'function' ? onResolved : value => value;
        onRejected = typeof onRejected === 'function' ? onRejected : error => { throw error };

        // 返回一个 Promise 对象，其结果由回调函数 onResolved, onRejected 的执行结果决定
        return new MyPromise((resolve, reject) => {
            function handle(callback) {
                try {
                    /*
                        - 如果回调函数返回的是 promise 对象
                            - 当前返回的结果与状态，由回调函数的结果决定
                            - result.then(resolve, reject);

                        - 如果回调函数返回的不是promise
                            - 返回fulfilled状态的promise，value就是回调函数的返回值
                            - resolve(result);
                    */
                    const result = callback(self.data);
                    result instanceof MyPromise ? result.then(resolve, reject) : resolve(result);
                } catch (e) {
                    reject(e);  // 如果抛出异常，返回rejected状态的promise，reason就是error
                }
            }

            // 1. 如果状态是 `pending`，说明还没有得到异步的结果，先添加到回调函数
            if (self.status === status.PENDING) {
                // 需要用 handle 来包装回调函数，根据返回结果来修改promise的状态
                self.callbacks.push({
                    onResolved() { handle(onResolved) },
                    onRejected() { handle(onRejected) }
                });
            } else if (self.status === status.FULFILLED) {
                // 2. 如果状态是 `fulfilled`，说明异步调用成功，异步执行 onResolved
                setTimeout(() => handle(onResolved));
            } else {
                // 3. 状态是 `rejected`，说明异步调用失败，异步执行 onRejected
                setTimeout(() => handle(onRejected));
            }
        });

    }

    // 接收失败的响应函数，并返回一个新的Promise对象
    MyPromise.prototype.catch = function (onRejected) {
        return this.then(undefined, onRejected);
    }
    ```

## Promise.resolve、Promise.reject的实现
[top](#catalog)
- `resolve` 的实现要点
    - 原生 Promise 对象的处理分析
        ```js
        const p1 = Promise.resolve(1234);   // 数据是一个非 Promise 数据，
        p1.then(console.log);   // 1234

        const p2 = Promise.resolve(Promise.resolve(2345)); // 数据是一个成功的Promise，
        p2.then(console.log);   // 2345

        const p3 = Promise.resolve(Promise.reject('err')); // 数据是一个失败的Promise
        p3.then(value => console.log('then:', value))
            .catch(reason => console.log('catch:', reason));
            // catch: err，失败的Promise，需要由onRejected处理
        ```
    - 原生 `resolve` 的特点，与 `then` 的 `fulfilled` 处理类似
        1. 如果参数是 Promise 对象
            - 当前返回的结果与状态，由参数自身决定
            - `result.then(新Promise--resolve, 新Promise--reject)`
        2. 如果参数不是 Promise 对象
            - 返回 `fulfilled` 状态的 Promise 对象
            - 将参数作为 Promise 对象向外传递的数据
    - 需要返回一个新的 Promise 对象

- `reject` 的实现要点
    - 需要创建一个 `rejected` 状态的 Promise 对象
    - `reason` 作为 Promise 对象向外传递的数据
    - 需要返回一个新的 Promise 对象

- 参考代码
    - [src/promise/mypromise/04resolve.js](src/promise/mypromise/04resolve.js)
- 测试代码
    - [src/promise/mypromise/04test.js](src/promise/mypromise/04test.js)
- 实现内容
    ```js
    // 返回一个成功的Promise对象
    MyPromise.resolve = function (value) {
        return new MyPromise((resolve, reject)=>{
            value instanceof MyPromise? value.then(resolve, reject):resolve(value);
        })
    }

    // 返回一个失败的Promise对象
    MyPromise.reject = function (reason) {
        return new MyPromise((_, reject)=>{reject(reason)});
    }
    ```

## Promise.all的实现
[top](#catalog)
- 实现要点
    1. 需要接受一个数组类型的参数: `promises`
    2. 需要返回一个新的 Promise 对象
    3. 对于 `promises` 中，不同参数类型的处理
        1. 如果 `promises` 是空对象，返回一个 `fulfilled` 状态的新 Promise对象
        2. 如果不是 Promise 对象，返回一个 `fulfilled` 状态的新 Promise对象
        3. 如果是 Promise 对象，则返回结果是这个对象的结果
    4. 需要创建一个与 `promises` 长度相同数组:`results`，来保存每个异步操作返回的数据
        - 保存的 index 需要与 `promises` 中的位置对应
    5. 如果发生异常，则立刻返回 `rejected` 状态
    6. 需要使用一个变量: `successCount`，来记录已经正常结束操作的数量
        - 当`successCount == 数组参数.length` 时，将保存结果的数组: `results` 返回

- 处理非Promise对象的两种方式
    1. 通过 `xxx instanceof MyPromise` 的方式，检查对象类型
        - 如果是普通函数，就直接执行 `resolve(xxx)`，返回正常的数据
    2. 通过 `MyPromise.resolve(xxx)`，将对象包装成 Promise 对象，统一处理

- 参考代码
    - [src/promise/mypromise/05all.js](src/promise/mypromise/05all.js)
    - [src/promise/mypromise/05all02.js](src/promise/mypromise/05all02.js)
- 测试代码
    - [src/promise/mypromise/05test.js](src/promise/mypromise/05test.js)
- 实现内容
    - 方式1: 分开处理 Promise 对象 和 非 Promise 对象
        ```js
        MyPromise.all = function (promises) {
            return new MyPromise((resolve, reject) => {
                // 1. promises是空对象，或空数组，返回一个 `resolved` 状态的 Promise对象
                if (!promises) {
                    resolve();
                } else {
                    // 创建一个数组，保存每个 Promise 的返回结果
                    let results = new Array(promises.length);
                    // 通过变量记录已经成功的 Promise 数量
                    let successCount = 0;
                    promises.forEach((p, idx) => {
                        if (p instanceof MyPromise) {
                            // 3. 如果是 Promise 对象，则返回结果是这个对象的结果
                            p.then(
                                value => {
                                    successCount++;
                                    results[idx] = value;

                                    // 当成功的结果数量与 Promise对象的数量相等时，返回所有结果
                                    if (successCount === promises.length) resolve(results);
                                },
                                // 如果出现异常请求，立刻失败
                                reject  // reason => reject(reason)
                            )
                        } else {
                            // 2. 如果不是 Promise 对象，则直接返回 fulfilled 状态的新 Promise 对象
                            successCount++;
                            results[idx] = p;

                            // 当成功的结果数量与 Promise对象的数量相等时，返回所有结果
                            if (successCount === promises.length) resolve(results);
                        }
                    })
                }
            })
        }
        ```
    - 方式2: 将所有类型的对象都包装成 Promise 对象，统一处理
        ```js
        MyPromise.all = function (promises) {
            return new MyPromise((resolve, reject) => {
                // promises是空对象，或空数组，返回一个 `resolved` 状态的 Promise对象
                if (!promises) {
                    resolve();
                } else {
                    // 创建一个数组，保存每个 Promise 的返回结果
                    let results = new Array(promises.length);
                    // 通过变量记录已经成功的 Promise 数量
                    let successCount = 0;
                    promises.forEach((p, idx) => {
                        // 【 将所有类型的对象都包装成 Promise 对象，统一处理 】
                        MyPromise.resolve(p).then(
                            value => {
                                successCount++;
                                results[idx] = value;

                                // 当成功的结果数量与 Promise对象的数量相等时，返回所有结果
                                if (successCount === promises.length) resolve(results);
                            },
                            // 如果出现异常请求，立刻失败
                            reject  // reason => reject(reason)
                        )
                    })
                }
            })
        }
        ```

## Promise.race的实现
[top](#catalog)
- 实现要点
    1. 需要接受一个数组类型的参数: `promises`
    2. 需要返回一个新的 Promise 对象
    3. 对于 `promises` 中，不同参数类型的处理
        1. 如果 `promises` 是空对象，返回一个 `fulfilled` 状态的新 Promise对象
        2. 如果不是 Promise 对象，返回一个 `fulfilled` 状态的新 Promise对象
        3. 如果是 Promise 对象，则返回结果是这个对象的结果
    4. 只要有一个操作结束，就立刻返回

- 处理非Promise对象的两种方式
    1. 通过 `xxx instanceof MyPromise` 的方式，检查对象类型
        - 如果是普通函数，就直接执行 `resolve(xxx)`，返回正常的数据
    2. 通过 `MyPromise.resolve(xxx)`，将对象包装成 Promise 对象，统一处理

- 参考代码
    - [src/promise/mypromise/06race.js](src/promise/mypromise/06race.js)
    - [src/promise/mypromise/06race02.js](src/promise/mypromise/06race.js)
- 测试代码
    - [src/promise/mypromise/06test.js](src/promise/mypromise/06test.js)
- 实现内容
    - 方式1: 分开处理 Promise 对象 和 非 Promise 对象
        ```js
        MyPromise.race = function (promises) {
            return new MyPromise((resolve, reject) => {
                // 1. promises是空对象，或空数组，返回一个 `resolved` 状态的 Promise对象
                if (!promises) {
                    resolve();
                } else {
                    // 2. 如果不是 Promise 对象，则直接返回 fulfilled 状态的新 Promise 对象
                    // 3. 如果是 Promise 对象，则返回结果是这个对象的结果
                    promises.forEach(
                        p => p instanceof MyPromise ? p.then(resolve, reject) : resolve(p)
                    );
                }
            })
        }
        ```
    - 方式2: 将所有类型的对象都包装成 Promise 对象，统一处理
        ```js
        MyPromise.race = function (promises) {
            return new MyPromise((resolve, reject) => {
                // promises是空对象，或空数组，返回一个 `resolved` 状态的 Promise对象
                if (!promises) {
                    resolve();
                } else {
                    // 【 将所有类型的对象都包装成 Promise 对象，统一处理 】
                    promises.forEach(p => MyPromise.resolve(p).then(resolve, reject));
                }
            })
        }
        ```

## 自定义实现---resolveDelay、rejectDelay
[top](#catalog)
- 功能
    - 延迟指定的时间后，返回结果
    - 相当于
        ```js
        new Promise((resolve, reject)=>{
            setTimeout(()=>{
                resolve(...);
            }, 1000)
        })
        ```

- 参考代码
    - [src/promise/mypromise/07delay.js](src/promise/mypromise/07delay.js)
- 测试代码
    - [src/promise/mypromise/07test.js](src/promise/mypromise/07test.js)
- 实现内容
    ```js
    MyPromise.resolveDelay = function (value, timeout) {
        return new MyPromise((resolve, reject) => {
            // 延迟执行操作
            setTimeout(
                () => value instanceof MyPromise ? value.then(resolve, reject) : resolve(value),
                timeout
            );
        })
    }

    MyPromise.rejectDelay = function (reason, timeout) {
        // 延迟执行操作
        return new MyPromise((_,reject)=>{
            setTimeout(() => reject(reason), timeout);
        })
    }
    ```

## class版本
[top](#catalog)
- 参考代码
    - [src/promise/mypromise/08class/08class.js](src/promise/mypromise/08class/08class.js)

# async、await
[top](#catalog)
- async 函数
    - 函数返回值是 Promise 对象
    - 如果返回的不是 Promise 对象，会自动包装成 Promise 对象
    - 如果抛出异常，会包装一个 `rejected` 状态的 Promise 对象
- await 表达式
    - await 右侧一般是 Promise 对象，也可以是其他类型的数据
        - 如果是 Promise 对象，await返回的是 Promise 对象成功的值
        - 如果是其他类型，await的返回值就是该数据
    - <span style='color:red'>await必须写在async函数中</span>
    - 如果 await 右侧的 Promise 对象失败了，会抛出异常，可以通过 `try...catch` 捕获


[top](#catalog)