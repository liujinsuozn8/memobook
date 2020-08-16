// `绑定函数` 的特性: 可以使用目标函数继承自原型的方法

// 0 通过类继承构造继承关系
// 0.1 基类
class Foo{
    static run(){
        console.log('this is foo');
        console.log(`this === Foo : ${this === Foo}`);
        console.log(this);
    }
}

// 0.2 子类
class FooEx extends Foo{
    static run(){
        console.log('this is FooEx');
        console.log(`this === FooEx : ${this === FooEx}`);
        console.log(this);
    }
}

/*
    0.3 此时相当于 Object.getPrototypeOf(FooEx) === Foo
        Foo 相当与 FooEx 的原型，所以 FooEx 的绑定函数可以使用 Foo.run 方法
*/

// 1. 创建 FooEx 的绑定函数
var f = FooEx.bind();

// 2. 直接执行类的静态函数
FooEx.run();
// this is FooEx
// this === FooEx : true
// [Function: FooEx]

// 3. 通过绑定函数来执行静态函数
f.run();
// this is foo
// this === Foo : false
// [Function: bound FooEx]