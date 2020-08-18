function* foo(){
    yield 1;
    yield 2;
    return 'x';
}

// 1. 通过 `next()` 获取数据
var f = foo();
console.log(f.next());  // { value: 1, done: false }
console.log(f.next());  // { value: 2, done: false }
console.log(f.next());  // { value: 'x', done: true }  <<<< 迭代结束，第一次会获得返回值
console.log(f.next());  // { value: undefined, done: true } <<<< 迭代结束，第二次之后，无法获得返回值

// 2. for...of 无法获取生成器函数的返回值
for(let n of foo()) console.log(n);
// 1
// 2


