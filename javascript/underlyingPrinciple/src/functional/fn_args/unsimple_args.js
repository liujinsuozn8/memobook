function foo (a, {b, c, d}){
    console.log(arguments);
    console.log(a, b, c, d);

    a = 100;
    console.log(a);
    console.log(arguments);

    b = 200;
    console.log(b);
    console.log(arguments);
}

foo(1, {b:2, c:3, d:4});
// [Arguments] { '0': 1, '1': { b: 2, c: 3, d: 4 } }
// 1 2 3 4
// 100      <<<< 修改普形参
// [Arguments] { '0': 1, '1': { b: 2, c: 3, d: 4 } }    <<<< 无法影响arguments
// 200      <<<< 修改模板内的形参
// [Arguments] { '0': 1, '1': { b: 2, c: 3, d: 4 } }    <<<< 无法影响arguments