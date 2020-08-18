// promise all
// 1. 全部 Promise对象是 resolved 状态
Promise.all([
    new Promise((resolve, reject)=>{
        setTimeout(()=>resolve(2), 2000);
    }),
    new Promise((resolve, reject)=>{
        setTimeout(()=>resolve('abcd'), 1000);
    })
]).then(data=>{
    console.log(data);  // 输出: [2, "abcd"]
})

// 2. 存在 Promise对象是 rejected 状态
Promise.all([
    new Promise((resolve, reject)=>{
        setTimeout(()=>reject(2), 2000);
    }),
    new Promise((resolve, reject)=>{
        setTimeout(()=>resolve('abcd'), 1000);
    })
]).then(data=>{
    console.log(data);
}).catch(error=>{
    console.log(`error = ${error}`);    // 输出: error = 2
})