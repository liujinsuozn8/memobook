const MyPromise = require('./02constructor');

// 02 只能测试：先设置回调函数，再修改状态

// 1. resolve 测试
var p1 = new MyPromise((resolve, reject)=>{
    setTimeout(()=>{
        resolve(1234);
        console.log('qwqre');   // 测试 resolve 是异步执行的
    })
})

p1.then(
    value => console.log(`onResolved = ${value}`),
    reason => console.log(`onRejected = ${reason}`)
)

p1.then(
    value => console.log(`onResolved2 = ${value}`),
    reason => console.log(`onRejected2 = ${reason}`)
);

// 2. reject 测试
setTimeout(function(){
    var p2 = new MyPromise((resolve, reject)=>{
        setTimeout(()=>reject(1234));
    })

    p2.then(
        value => console.log(`onResolved = ${value}`),
        reason => console.log(`onRejected = ${reason}`)
    )

    p2.then(
        value => console.log(`onResolved2 = ${value}`),
        reason => console.log(`onRejected2 = ${reason}`)
    )
}, 500)