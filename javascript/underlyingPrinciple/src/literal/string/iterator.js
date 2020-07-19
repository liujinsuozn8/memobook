// 字符串的可迭代性

// 1. 字符串可以被三点运算符 `...` 展开
let str = 'abvce';
let a = [...str];
console.log(a); // [ 'a', 'b', 'v', 'c', 'e' ]

// 2. 可以 `for...of` 迭代每个字符
for (let char of a){
    console.log( "char=" + char);
    // 输出:
    // char=a
    // char=b
    // char=c
    // char=d
    // char=e
}

// 3. 可以使用 `yeild*` ，从生成器内部返回一个字符
function* generatChar(str){
    yield* str;
}
let g = generatChar('abcd');
console.log(g.next());  // { value: 'a', done: false }

for(let n of g){
    console.log(n);
}
// 输出:
// b
// c
// d