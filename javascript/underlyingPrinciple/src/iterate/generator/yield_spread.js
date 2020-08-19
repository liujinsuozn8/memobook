// yield*，展开可迭代对象测试

function* foo(){
    let a = yield* [1,2,3,4]
    console.log(`a = ${a}`);
}
var g = foo();
console.log(g.next('aaa'));
console.log(g.next('bbb'));
console.log(g.next('ccc'));
console.log(g.next('ddd'));
console.log(g.next('eee'));

// 输出
// { value: 1, done: false }
// { value: 2, done: false }
// { value: 3, done: false }
// { value: 4, done: false }
// a = undefined    // yield* 没有内部结果
// { value: undefined, done: true }