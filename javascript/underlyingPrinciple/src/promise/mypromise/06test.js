const MyPromise = require('./06race');
// const MyPromise = require('./06race02');

// 06 测试race

// 全 Promise 对象测试
const p1 = MyPromise.resolve('p1');
const p2 = MyPromise.resolve('p2');
const p3 = new MyPromise(resolve=>{
    setTimeout(()=>resolve(222), 1000);
})

const all = MyPromise.race([p3, p2, p1]);
all.then(console.log);

// 非 Promise 对象测试
setTimeout(()=>{
    MyPromise.race([1,2,3,4,5]).then(console.log);
}, 500)