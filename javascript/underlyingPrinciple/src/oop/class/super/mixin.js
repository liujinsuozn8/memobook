// 1. 创建父类
class Foo{
    constructor(){
        console.log('this is Foo')
    }
}
// 2. 创建子类，使用原型继承 Foo
function FooEx(){
    console.log('this is FooEx')
}
FooEx.prototype = new Foo();    // 此时Foo的对象已经构造完成，不会被 super回溯
FooEx.prototype.constructor = FooEx;

// 3. 使用类创建FooEx的子类
class FooNext extends FooEx{
    constructor(){
        super() // 即使父类是通过原型继承方式创建的也需要显示的调用super
        console.log('this is FooNext')
    }
}

var a = new FooNext();

// 输出:
// this is Foo
// this is FooEx
// this is FooNext