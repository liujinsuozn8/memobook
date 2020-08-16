// yield 测试

function* myGenerator(a){
    console.log('first run');
    let v2 = yield;
    console.log('second run, v2 = ', v2);
    let v3 = yield a + 1
    console.log('third run, v3 = ', v3);
}

var g = myGenerator(100);
console.log('next 01', g.next('aaaa'));
console.log('next 02', g.next('bbbb'));
console.log('next 03', g.next('cccc'));

// 输出
// first run                                    // 第一次调用 next()，第一次的参数无效
// next 01 { value: undefined, done: false }    // 第一次调用 next() 的返回
// second run, v2 =  bbbb                       // 第二次调用 next()
// next 02 { value: 101, done: false }          // 第二次调用 next() 的返回
// third run, v3 =  cccc                        // 第三次调用 next()
// next 03 { value: undefined, done: true }     // 第三次调用 next() 的返回
//                                   ^^^^ 生成器执行结束