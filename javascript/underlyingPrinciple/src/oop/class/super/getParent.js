// 1. 创建通用函数，解析创建实例对象的类
// 因为父类和子类实在一条原型链上，所以通过子类可以访问到父类中的静态成员
function getClass(instance){
    return Object.getPrototypeOf(instance).constructor;
}

// 2. 创建父类
class Foo{
    // 设置父类的静态成员
    static get count(){
        return 123;
    }

}

// 3. 创建子类
class FooEx extends Foo{
    // 设置子类的静态成员
    static print(){
        console.log('this is FooEX');
    }
    // 访问父类中的静态成员
    printParentStaticProperty(){
        console.log('count = '+ getClass(this).count);
    }

    // 访问当前类中的静态成员
    printSelfStaticProperty(){
        getClass(this).print()
    }
}

// 4. 实例化子类对象，并访问静态成员
var fooEx = new FooEx();
fooEx.printParentStaticProperty();  // 输出: count = 123
fooEx.printSelfStaticProperty();    // 输出: this is FooEX