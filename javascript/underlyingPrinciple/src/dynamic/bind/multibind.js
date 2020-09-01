// bind 多次绑定测试

function Foo() {
    console.log(this.id);

    console.log(arguments.length);
    console.log(...arguments);
}

var obj01 = { id: 'obj01' };
var obj02 = { id: 'obj02' };
var obj03 = { id: 'obj03' };

var Fn1 = Foo.bind(obj01, 'aa', 'bb');
var Fn2 = Fn1.bind(obj01, 'cc', 'dd');
var Fn3 = Fn2.bind(obj01, 'ee', 'ff');

Fn3('gg', 'hh');
// 输出
// obj01        <<<<< 第一次绑定的 this 不会被覆盖
// 8            <<<<< 多次绑定的参数会叠加
// aa bb cc dd ee ff gg hh  <<<<< 多次绑定的参数会叠加