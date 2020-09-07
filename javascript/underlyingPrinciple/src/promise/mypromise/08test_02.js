const MyPromise = require('./08finally');

// finally 测试: 没有出现特殊情况

MyPromise.resolve(1234)
    .then(value => {
        console.log(`then A: ${value}`);
        return Promise.resolve('abcd');
    })
    .finally(() => {
        console.log('end');
        // 尝试返回数据
        throw 'wxyz';
    })
    .then(
        value => console.log(`then B: ${value}`),
        reason => {
            console.log(`reason B: ${typeof reason}`)
            console.log(`reason B: ${reason}`)
        }
    )


