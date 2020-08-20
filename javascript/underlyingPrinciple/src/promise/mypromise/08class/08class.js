// 07 Promise.resolveDelay、Promise.rejectDelay 实现

const status = {
    PENDING: 'pending',
    FULFILLED: 'fulfilled',
    REJECTED: 'rejected'
};


// 调用方式: new MyPromise( (resolve, reject) => {...})
class MyPromise {
    constructor(executor){
        // 1. 保存 this 对象，使得在 resolve、reject 中都可以使用
        const self = this;
        this.status = status.PENDING;// 给Promise对象指定指定初始状态
        this.data = undefined;  // 用于保存结果数据
        this.callbacks = [];    // 保存回调函数，每个元素的结构: `{onResolved(){}, onRejected(){}}`

        // 2. 定义 resolve、reject 函数
        function resolve(value) {
            if (self.status !== status.PENDING) return;  // 只能修改一次状态
            self.status = status.FULFILLED;              // 修改状态
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
            if (self.status !== status.PENDING) return;  // 只能修改一次状态
            self.status = status.REJECTED;               // 修改状态
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

    // 接收成功、失败的响应函数，并返回一个新的Promise对象
    then (onResolved, onRejected) {
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
    catch(onRejected) {
        // this.callbacks.push({ onResolved: undefined, onRejected });
        return this.then(undefined, onRejected);
    }

    // 返回一个成功的Promise对象
    static resolve (value) {
        return new MyPromise((resolve, reject) => {
            value instanceof MyPromise ? value.then(resolve, reject) : resolve(value);
        })
    }
    // 返回一个失败的Promise对象
    static reject(reason) {
        return new MyPromise((_, reject) => { reject(reason) });
    }
    // 返回一个Promise
    // 只有所有Promise都成功时，才成功；只要有一个失败，则立刻返回一个失败的Promise
    static all (promises) {
        return new MyPromise((resolve, reject) => {
            // promises是空对象，返回一个 `resolved` 状态的 Promise对象
            if (!promises || promises instanceof Array && promises.length === 0) {
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
                        reject
                    )
                })
            }
        })
    }

    // 返回一个Promise。返回第一个完成的 Promise
    static race(promises) {
        return new MyPromise((resolve, reject) => {
            // promises是空对象，返回一个 `resolved` 状态的 Promise对象
            if (!promises || promises instanceof Array && promises.length === 0) {
                resolve();
            } else {
                // 【 将所有类型的对象都包装成 Promise 对象，统一处理 】
                promises.forEach(p => MyPromise.resolve(p).then(resolve, reject));
            }
        })
    }
    static resolveDelay (value, timeout) {
        return new MyPromise((resolve, reject) => {
            // 延迟执行操作
            setTimeout(
                () => value instanceof MyPromise ? value.then(resolve, reject) : resolve(value),
                timeout
            );
        })
    }
    static rejectDelay (reason, timeout) {
        // 延迟执行操作
        return new MyPromise((_,reject)=>{
            setTimeout(() => reject(reason), timeout);
        })
    }
}


module.exports = MyPromise