// 1. 创建父类
function Foo(name){
    this.name = name;
}
Foo.prototype.run = function(){
    console.log(`this is ${this.name} run`);
}

// 2. 创建子类
function FooEx(name){
    Foo.call(this, name);
}

// 3. 创建一个空的类作为寄生类
function Super(){}

// 4. 将寄生类对象的原型设置为父类的原型
Super.prototype = Foo.prototype;

// 5. 创建寄生类对象
var superObj = new Super();

// 6. 设置寄生类对象和当前类的原型关系
FooEx.prototype = superObj;
FooEx.prototype.constructor = FooEx;

// 7.实例化对象，并调用父类的方法
var fooex = new FooEx('fooexName');
fooex.run();    // 输出: this is fooexName run