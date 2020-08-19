// return / throw 方法如何设置？

function* foo(){
    yield 1;
    yield 2;
    yield 3;
    yield 4;
}
// 1. 将方法设置在生成器函数的原型上
foo.prototype.return = function(value){
    console.log('function return');
    return {value, done:true};
}

for(let n of foo()){
    if (n === 3) break;
    console.log(n);
}
// 输出:
// 1
// 2
// function return

// 2. 将方法设置在生成器对象上
var f = foo();
f.return = function(value){
    console.log('obj return');
    return {value, done:true};
}
for(let n of f){
    if(n === 3) break;
    console.log(n);
}
// 输出
// 1
// 2
// obj return