// super 表达多态性

// 1. 创建两个父类
class Foo{
    work(){
        console.log('foo is working');
    }
}

class Bar{
    work(){
        console.log('bar is working');
    }
}

// 2. 创建一个继承关系
class MyObj extends Bar{}

var obj1 = new MyObj();
obj1.work();    // bar is working

// 3. 修改继承关系
Object.setPrototypeOf(MyObj.prototype, Foo.prototype);
var obj2 = new MyObj();
obj2.work();    // foo is working