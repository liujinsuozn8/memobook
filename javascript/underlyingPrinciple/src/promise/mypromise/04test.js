// const MyPromise = require('./03then');
const MyPromise = require('./04resolve');

// 04 测试resolve

const p1 = MyPromise.resolve(4);
p1.then(console.log);

const p2 = Promise.resolve(new Promise((resolve)=>{
    resolve(1234)
}));
p2.then(console.log);

const p3 = Promise.resolve(Promise.reject('reject test'));
p3.catch(console.log);