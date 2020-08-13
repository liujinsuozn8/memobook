
console.log('------------------1. arguments 和形参 绑定-------------------------');


// 1. 直接修改形参的值，会影响 `arguments[x]`; 修改 `arguments[x]` 也会影响形参
function foo(p1, p2){
    // 1.1 初始值
    console.log(arguments);
    console.log(`p1 = ${p1}`);
    console.log(`p2 = ${p2}`);

    // 1.2 修改形参 p1
    p1 = p1 + '12345';
    console.log(`arguments[0] = ${arguments[0]}`);
    console.log(`p1 = ${p1}`);

    // 1.3 修改 arguments[1]
    arguments[1] = arguments[1] + 'abcde';
    console.log(`arguments[1] = ${arguments[1]}`);
    console.log(`p2 = ${p2}`);
}

foo('aaa', 'bbb');
// [Arguments] { '0': 'aaa', '1': 'bbb' }
// p1 = aaa
// p2 = bbb
// arguments[0] = aaa12345  <<<< 直接修改形参
// p1 = aaa12345
// arguments[1] = bbbabcde  <<<< 直接修改 arguments[1]
// p2 = bbbabcde


console.log('------------------2. arguments 和 解构得到的变量 不绑定-------------------------');

// 2. 对 `arguments` 使用解构赋值后，**解构得到的变量** 和 `arguments` 之间不会互相影响
function foo2(p1, p2){
    // 解构获取参数
    var [x1, x2] = arguments
    console.log(`x1 = ${x1}`);
    console.log(`x2 = ${x2}`);

    // 1.2 修改解构得到的变量
    x1 = x1 + '12345';
    console.log(`arguments[0] = ${arguments[0]}`);
    console.log(`x1 = ${x1}`);

    // 1.3 修改 arguments[1]
    arguments[1] = arguments[1] + 'abcde';
    console.log(`arguments[1] = ${arguments[1]}`);
    console.log(`x2 = ${x2}`);
}

foo2('aaa', 'bbb');
// x1 = aaa                     <<<<< 解构的结果
// x2 = bbb
// arguments[0] = aaa           <<<<< 修改解构得到的变量，不会影响arguments
// x1 = aaa12345
// arguments[1] = bbbabcde      <<<<< 修改 arguments[1]，不会影响解构得到的变量
// x2 = bbb

console.log('------------------3. arguments 和 解构得到剩余参数 不绑定-------------------------');

function foo3(p1){
    // 解构获取参数
    var [x1, ...more] = arguments
    console.log(`x1 = ${x1}`);
    console.log(`more = ${more}`);

    // 1.2 修改解构得到的变量
    more[0] = 1000;
    console.log(`arguments[1] = ${arguments[1]}`);
    console.log(`more = ${more}`);

    // 1.3 修改 arguments[1]
    arguments[1] = arguments[1] + 'abcde';
    console.log(`arguments[1] = ${arguments[1]}`);
    console.log(`more = ${more}`);
}

foo3('aaa', 'bbb', 'ccc','ddd','eee');
// x1 = aaa                     <<<<< 解构的结果
// more = bbb,ccc,ddd,eee
// arguments[1] = bbb           <<<<< 修改剩余参数，不会影响arguments
// more = 1000,ccc,ddd,eee
// arguments[1] = bbbabcde      <<<<< 修改arguments，不会影响剩余参数
// more = 1000,ccc,ddd,eee