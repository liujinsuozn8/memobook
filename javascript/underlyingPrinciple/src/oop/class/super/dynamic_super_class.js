// 为 `Object.defineProperty` 动态添加的方法设置 `super`

// 1. 创建父类
class Foo {
    foo(){
        console.log('Foo');
    }
}

// 2. 创建一个子类
class FooEx extends Foo{
    foo(){
        super.foo();
        console.log('FooEx')
    }
}

// 3. 设置【具名的配置对象】
var configObj = {
    value(){
        super.foo();
        console.log('this is configObj');
    }
}
// 4. 为子类对象【动态添加方法】
Object.defineProperty(FooEx.prototype, 'newFoo', configObj);

// 5. 为配置对象【设置原型对象 为父类对象】
Object.setPrototypeOf(configObj, Foo.prototype);

// 6. 调用方法
var obj = new FooEx();
obj.foo();
// 输出:
// Foo
// FooEx

obj.newFoo();
// 输出:
// Foo
// this is configObj