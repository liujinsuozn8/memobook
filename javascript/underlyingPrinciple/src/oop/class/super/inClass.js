// 1. 父类、普通类，相当于继承了 Object
class Foo{
    constructor(){
        // 表示方式2: 调用实例的成员时，super指向父类的原型对象
        // super 相当于: super.toString = Object.prototype.toString.bind(this)
        console.log(super.toString())
    }

    static showMe(){
        // 表示方式3: 在静态方法/存取器中，super指向父类本身
        // super 相当于: super.toString = Object.toString.bind(this)
        // 调用方式为 Foo.showMe()，调用者是类本身，所以 this 就是 Foo
        console.log(super.toString())
    }

    run(){
        console.log('this is foo run')
    }

    static do(){
        console.log('this is foo static do')
    }
}

// 2. 子类
class FooEx extends Foo{
    constructor(){
        // 表示方式1: 调用构造函数时，super指向父类构造函数
        // super 相当于: super = Foo.bind(this)
        super();
    }
    foo(){
        // 表示方式2: 调用实例的成员时，super指向父类的原型对象
        // super 相当于: super.foo = Foo.prototype.foo.bind(this);
        super.foo();
    }

    static doSomething(){
        // 表示方式3: 在静态方法中，调用静态成员，super指向父类本身
        // super 相当于: super.do = Foo.do.bind(FooEx)
        super.do()
    }

    static get name(){
        // 表示方式3: 在静态存取器中，调用静态成员，super指向父类本身
        // super 相当于: super.do = Foo.do.bind(FooEx)
        super.do()
        return 'testName';
    }
}

// 3. 实例化父类对象
var objA = new Foo();   // 输出: [object Object]
objA.run();             // 输出: this is foo run
Foo.showMe();           // 输出: class Foo{.....

// 4. 实例化子类对象
var objB = new FooEx(); // 输出: [object Object]
objB.run();             // 输出: this is foo run
FooEx.doSomething();    // 输出: this is foo static do
FooEx.name;             // 输出: this is foo static do