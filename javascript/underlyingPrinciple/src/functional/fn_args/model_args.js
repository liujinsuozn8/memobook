console.log('--------------------1. 对象模板参数-----------------------');

// 1. 对象模板参数
function fooObj(a, { b, c, d }) {
    console.log(arguments);
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(d);
}

var obj = {
    b: 'bbb',
    c: 'ccc',
    d: 'ddd',
    e: 'eee'
}

// 1.1 向对象模板传递一个对象
fooObj('aaa', obj);
// [Arguments] {
// '0': 'aaa',
// '1': { b: 'bbb', c: 'ccc', d: 'ddd', e: 'eee' }
// }
// aaa
// bbb
// ccc
// ddd

// 1.2 通过对象字面量传递参数
fooObj('aaa', {b:1, c:2, d:3, e:4})
// [Arguments] { '0': 'aaa', '1': { b: 1, c: 2, d: 3, e: 4 } }
// aaa
// 1
// 2
// 3

console.log('-------------------2. 数组模板参数------------------------');

// 2. 数组模板参数

function fooArray(a, [b, c, d]){
    console.log(arguments);
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(d);
}

fooArray(1, [2, 3, 4, 5, 6]);
// [Arguments] { '0': 1, '1': [ 2, 3, 4, 5, 6 ] }
// 1
// 2
// 3
// 4

// 必须传递一个数组，否则会产生异常
// fooArray(1, 2);  // TypeError: undefined is not a function

console.log('-------------------3. 在数组模板中，使用剩余参数来吸收参数------------------------');

// 3. 在数组模板中，使用剩余参数来吸收参数
function fooArray02(a, [b, c, ...more]){
    console.log(arguments);
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(more);
}
fooArray02(1, [2]);
// [Arguments] { '0': 1, '1': [ 2 ] }
// 1
// 2
// undefined
// []

fooArray02(1, [2, 3, 4, 5, 6, 7]);
// [Arguments] { '0': 1, '1': [ 2, 3, 4, 5, 6, 7 ] }
// 1
// 2
// 3
// [ 4, 5, 6, 7 ]

console.log('---------------------4. 为模板整体设置默认值----------------------');
console.log('---------------------4.1 为对象模板的整体设置默认值----------------------');

// 4. 为模板整体设置默认值
// 4.1 为对象模板的整体设置默认值
var objDefault = {b: 'bbb', c:'ccc', d:'ddd'};
function objModelDefault(a, {b, c, d}=objDefault){
    console.log(arguments);
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(d);
}

// 使用模板的默认值
objModelDefault(1234);
// [Arguments] { '0': 1234 }
// 1234
// bbb
// ccc
// ddd

// 自定义模板参数的数据
objModelDefault(1234, {b: 'abc', c:'def', d:'ghi'});
// [Arguments] { '0': 1234, '1': { b: 'abc', c: 'def', d: 'ghi' } }
// 1234
// abc
// def
// ghi

console.log('---------------------4.2 为数组模板的整体设置默认值----------------------');

// 4.2 为数组模板的整体设置默认值
var arrDefault = ['bbb', 'ccc', 'ddd']
function arrModelDefault(a, [b, c, d]=arrDefault){
    console.log(arguments);
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(d);
}

// 使用模板的默认值
arrModelDefault(1234);
// [Arguments] { '0': 1234 }
// 1234
// bbb
// ccc
// ddd

// 自定义模板参数的数据
arrModelDefault(1234, ['abc', 'def', 'ghi']);
// [Arguments] { '0': 1234, '1': [ 'abc', 'def', 'ghi' ] }
// 1234
// abc
// def
// ghi

console.log('---------------------5. 为模板中的某个参数设置默认值----------------------');
console.log('---------------------5.1 为对象模板中的参数设置默认值----------------------');
// 5. 为模板中的某个参数设置默认值
// 5.1 为对象模板中的参数设置默认值
function objModelParamDefault(a, {b, c=100, d}){
    console.log(arguments);
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(d);
}

// 使用默认值
objModelParamDefault('aaa', {b:'bbb', d:'ddd'});
// [Arguments] { '0': 'aaa', '1': { b: 'bbb', d: 'ddd' } }
// aaa
// bbb
// 100
// ddd

// 使用自定义数据
objModelParamDefault('aaa', {b:'bbb', d:'ddd', c:'ccc'});
// [Arguments] { '0': 'aaa', '1': { b: 'bbb', d: 'ddd', c: 'ccc' } }
// aaa
// bbb
// ccc
// ddd

console.log('---------------------5.2 为数组模板中的参数设置默认值----------------------');

// 5.2 为数组模板中的参数设置默认值
function arrModelParamDefault(a, [b, c, d=1234]){
    console.log(arguments);
    console.log(a);
    console.log(b);
    console.log(c);
    console.log(d);
}

// 使用默认值
arrModelParamDefault('aaa', ['bbb', 'ccc']);
// [Arguments] { '0': 'aaa', '1': [ 'bbb', 'ccc' ] }
// aaa
// bbb
// ccc
// 1234

// 使用自定义数据
arrModelParamDefault('aaa', ['bbb', 'ccc', 'ddd']);
// [Arguments] { '0': 'aaa', '1': [ 'bbb', 'ccc', 'ddd' ] }
// aaa
// bbb
// ccc
// ddd