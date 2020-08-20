const MyPromise = require('./08class');

// 03 测试then

// 1. then 返回普通数据测试
new MyPromise((resolve, reject) => {
    setTimeout(() => {
        resolve('first');
        console.log('qwqre');   // 测试 resolve 是异步执行的
    })
}).then(
    value => {
        console.log(`then01 onResolved1 = ${value}`);
        return 'second success';
    },
    reason => {
        console.log(`then01 onRejected1 = ${reason}`);
        return 'second failed';
    },
).then(
    value => {
        console.log(`then01 onResolved2 = ${value}`);
    },
    reason => {
        console.log(`then01 onRejected2 = ${reason}`);
    },
);

// 2. then 返回promise测试
setTimeout(() => {
    new MyPromise((resolve, reject) => {
        setTimeout(() => {
            resolve('first');
        })
    }).then(
        value => {
            console.log(`then02 onResolved1 = ${value}`);
            return new MyPromise(resolve => resolve('second success'));
        },
        reason => {
            console.log(`then02 onRejected1 = ${reason}`);
            return 'second failed';
        },
    ).then(
        value => {
            console.log(`then02 onResolved2 = ${value}`);
        },
        reason => {
            console.log(`then02 onRejected2 = ${reason}`);
        },
    );
}, 500);

// 3. then 返回异常测试
setTimeout(() => {
    new MyPromise((resolve, reject) => {
        setTimeout(() => {
            resolve('first');
        })
    }).then(
        value => {
            console.log(`then03 onResolved1 = ${value}`);
            throw 1234
        },
        reason => {
            console.log(`then03 onRejected1 = ${reason}`);
            return 'second failed';
        },
    ).then(
        value => {
            console.log(`then03 onResolved2 = ${value}`);
        },
        reason => {
            console.log(`then03 onRejected2 = ${reason}`);
        },
    );
}, 1000);

// 4. 异常穿透测试
setTimeout(function () {
    new MyPromise((resolve, reject) => {
        setTimeout(() => reject(1234));
    }).then(
        value => console.log(`onResolved1 = ${value}`),
    ).then(
        value => console.log(`onResolved2 = ${value}`),
    ).catch(
        reason => console.log(`catch = ${reason}`)
    )
}, 1500)

// 5. promise链中断测试
setTimeout(function () {
    new MyPromise((resolve, reject) => {
        setTimeout(() => resolve(1234));
    }).then(
        value => console.log(`promise down 01 = ${value}`)
    ).then(
        value => {
            console.log(`promise down 02 = ${value}`);
            return new MyPromise(() => { });   // 中断promise链
        }
    ).then(
        value => console.log(`promise down 03 = ${value}`)
    )
}, 2000)

// 6. 先加回调函数，后修改状态
setTimeout(function () {
    const p = new MyPromise((resolve, reject) => {
        setTimeout(() => resolve('first'))
    });
    var p2 = p.then(
        value => {
            console.log(`then04 onResolved1 = ${value}`);
            return 'second success';
        },
        reason => {
            console.log(`then04 onRejected1 = ${reason}`);
            return 'second failed';
        },
    );
    var p3 = p.then(
        value => {
            console.log(`then04 onResolved2 = ${value}`);
        },
        reason => {
            console.log(`then04 onRejected2 = ${reason}`);
        },
    );
}, 2500)


// 7. catch后继续链式调用
setTimeout(function () {
    new MyPromise((resolve, reject) => {
        setTimeout(() => reject('first'))
    }).catch(
        reason=>{
            console.log(reason)
            return 'second';
        }
    ).then(console.log)

}, 3000)