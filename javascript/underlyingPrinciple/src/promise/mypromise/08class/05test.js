const MyPromise = require('./08class');

// 05 测试all

// 全 Promise 对象测试
const p1 = MyPromise.resolve(4);
const p2 = MyPromise.resolve(3);
const p3 = new MyPromise(resolve=>{
    setTimeout(()=>resolve(222), 1000);
})

const all = MyPromise.all([p3, p2, p1]);
all.then(console.log);

// 非 Promise 对象测试
setTimeout(()=>{
    MyPromise.all([1,2,3,4,5]).then(console.log);
}, 500)