// 类/对象的方法测试

var obj = {
    // 1. 具名函数
    fn1: function foo(){
        console.log('this is fn1');
    },

    // 2. 匿名函数
    fn2: function (){
        console.log('this is fn2');
    },

    // 3. ES6 风格的方法
    fn3(){
        console.log('this is fn3');
    },

    // 4. 生成器-----ES6 风格
    *fn4(){
        yield* [1,2,3,4];
    },
    // 5. 异步函数-----ES6 风格
    async fn5(){
        return Promise.resolve('fn5 resolved');
    },

    // 6. 属性读写器-----ES6 风格
    get value(){ return 'value is 100'}
}

// 1. 具名函数
obj.fn1();

// 2. 匿名函数
obj.fn2();

// 3. ES6 风格的方法
obj.fn3();

// 4. 生成器-----ES6 风格
for(let n of obj.fn4()) console.log(`fn4 n = ${n}`);

// 5. 异步函数-----ES6 风格
obj.fn5().then(console.log);

// 6. 属性读写器-----ES6 风格
console.log(obj.value);

// 输出:
// this is fn1
// this is fn2
// this is fn3
// fn4 n = 1
// fn4 n = 2
// fn4 n = 3
// fn4 n = 4
// value is 100
// fn5 resolved