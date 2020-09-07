const MyPromise = require('./08class');

// finally 测试: 没有出现特殊情况

new MyPromise((resolve, reject)=>{
    var num = Math.floor(Math.random()* 21);
    if (num <=10){
        resolve(num);
    } else{
        reject(num);
    }
}).then(value=>console.log(`value=${value}`))
.catch(reason=>console.log(`reason=${reason}`))
.finally(()=>console.log('end'));


