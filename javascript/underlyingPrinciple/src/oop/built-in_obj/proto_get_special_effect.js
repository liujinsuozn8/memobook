function foo(...args){
    // 2. 获取父类的构造函数
    var Base = Object.getPrototypeOf(foo.prototype).constructor;
    // 3. 使用父类的构造函数创建对象，来获得内置对象的特殊效果
    // 4. 修改实例对象的原型，让它成为 子类的实例
    var self = Object.setPrototypeOf(new Base(...args), foo.prototype);
    // 5. 将对象返回，替换函数上下文中生成的 this 对象
    return self;
}

// 1. 设置子类 与 父类的原型关系
Object.setPrototypeOf(foo.prototype, Function.prototype);

// 6. 实例化子类对象，并调用
var newFn = new foo('console.log("this is new Fn")')
newFn();    // this is new Fn