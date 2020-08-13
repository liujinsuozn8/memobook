// 函数的实参个数 和 形参个数

console.log('------------------1. 包含默认参数的函数-------------------------');


// 1. 对于包含默认参数的函数，从第一个默认参数开始，后面的**所有参数**都不会计入形参数量
function fooDefault(a,b,c=1234,d, e, f='test'){
    console.log(`arguments.length = ${arguments.length}`);
    console.log(`fooDefault.length = ${fooDefault.length}`);
    console.log(`a=${a}, b=${b}, c=${c}, d=${d}, e=${e}, f=${f}`)
}

fooDefault(1,2,3);
// arguments.length = 3
// fooDefault.length = 2
// a=1, b=2, c=3, d=undefined, e=undefined, f=test

console.log('-------------------2. 包含剩余参数------------------------');

// 2. 剩余参数会计入形参数量中，但是**只能算 1 个**
function fooRest(a, b, other) {
    console.log(`a = ${a}, b = ${b}`);
    console.log(`other = ${other}`);
    console.log(`arguments.length = ${arguments.length}`)
    console.log(`fooRest.length = ${fooRest.length}`)
}

fooRest(1, 2, 3, 4, 5, 6);
// a = 1, b = 2
// other = 3
// arguments.length = 6
// fooRest.length = 3

console.log('-------------------3. 包含模板参数------------------------');
function fooModel(a, { b, c, d }) {
    console.log(`arguments.length=${arguments.length}`);
    console.log(`fooModel.length=${fooModel.length}`);
}

fooModel('aaa', {b: 'bbb', c:'ccc', d:'ddd', e:'eee'});
// arguments.length=2
// fooModel.length=2