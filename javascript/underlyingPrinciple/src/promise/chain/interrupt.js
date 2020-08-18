// Promise链中断测试

Promise.reject(2)
.then(
    value=>onsole.log(`onResolve = ${value}`),
    // reason=>{throw reason},
    reason=>Promise.reject(reason)
)
.then(
    value=>console.log(`onResolve2 = ${value}`),
    // reason=>{throw reason},
    reason=>Promise.reject(reason)
)
.catch(
    reason=>{
        console.log(`catch = ${reason}`);
        return new Promise(()=>{}); // 返回一个空Promise对象，中断Promise链
    }
).then(
    value => console.log(`end value: ${value}`),
    reason => console.log(`end reason: ${reason}`),
)