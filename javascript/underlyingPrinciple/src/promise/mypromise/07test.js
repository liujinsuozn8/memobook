const MyPromise = require('./07delay');

// 07 delay 延迟方法测试
const p1 = MyPromise.resolveDelay(123, 1000);
p1.then(console.log);

const p2 = MyPromise.rejectDelay('error', 2000);
p2.catch(console.log);