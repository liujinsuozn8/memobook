// Promise 解构嵌套的异步调用

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