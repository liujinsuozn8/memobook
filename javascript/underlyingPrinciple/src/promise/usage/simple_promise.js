// Promise 解构嵌套的异步调用
// new Promise((resolve, reject) => {
//     setTimeout(() => {
//         resolve('nest1');
//     }, 1000);   // 延迟1s，模拟异步操作
// }).then(data => {
//     // 第 1 层嵌套
//     console.log(data);

//     return new Promise((resolve, reject) => {
//         resolve('nest2');
//     });
// }).then(data => {
//     // 第 2 层嵌套
//     console.log(data);

//     return new Promise((resolve, reject) => {
//         resolve('nest3');
//     });
// }).then(data => {
//     // 第 3 层嵌套
//     console.log(data);
//     return new Promise((resolve, reject) => {
//         reject('error from nest3');   // 手动创建一个异常
//         // resolve();
//     });
// }).then(data => {
//     // 第 4 层嵌套
//     console.log(data);
//     return new Promise((resolve, reject) => {
//         resolve('nest5');
//     });
// }).catch(error => {
//     // 处理异常
//     console.log(error);
//     // 创建新的Promise对象
//     return new Promise((resolve, reject) => {
//         resolve('catch error data');
//     });
// }).then(data => {
//     // 第 5 层嵌套
//     console.log(data);
// });

// 1. 使用 Promise.resolve、Promise.reject 来简化只有同步代码的Promise对象
// new Promise((resolve, reject) => {
//     setTimeout(() => {
//         resolve('nest1');
//     }, 1000);   // 延迟1s，模拟异步操作
// }).then(data => {
//     // 第 1 层嵌套
//     console.log(data);

//     return Promise.resolve('nest2');
// }).then(data => {
//     // 第 2 层嵌套
//     console.log(data);

//     return Promise.resolve('nest3');
// }).then(data => {
//     // 第 3 层嵌套
//     console.log(data);

//     // return Promise.resolve('nest4');
//     return Promise.reject('error from nest3');
// }).then(data => {
//     // 第 4 层嵌套
//     console.log(data);

//     return Promise.resolve('nest5');
// }).catch(error => {
//     // 处理异常
//     console.log(error);

//     // 创建新的Promise对象
//     return Promise.resolve('catch error data');
// }).then(data => {
//     // 第 5 层嵌套
//     console.log(`nest 5: ${data}`);
// });


// 2. 使用 return、throw 来简化只有同步代码的Promise对象
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