// 1. 配合 `()` 做赋值
var a = (1, 2, 3, 4);
console.log(a); // 4

// 2. 作为函数返回值
function foo() {
    return 1, 2, 3, 4;
}
console.log(foo()); // 4

// 3. 在箭头函数中做连续运算
var x = 1, y = 2, w = 3
var myfn = () => (x += y, w += x);
console.log(myfn());    // 6