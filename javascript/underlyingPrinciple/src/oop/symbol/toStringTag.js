// 1. 方式1: 对象字面量--属性赋值
var obj = {
    name:'testName',
    age:22,
    [Symbol.toStringTag]: 'testToStringTag',
}
console.assert(obj.toString() === '[object testToStringTag]');

// 2. 方式2: 对象字面量--使用存取描述符 getter
var obj02 = {
    name:'testName',
    age:22,
    get [Symbol.toStringTag](){
        return 'testToStringTag'
    }
}
console.assert(obj02.toString() === '[object testToStringTag]');

// 3. 方式3: 类声明--使用存取描述符 getter
class Foo{
    name
    age
    constructor(name, age){
        this.name = name;
        this.age = age;
    }
    get [Symbol.toStringTag](){
        return 'testToStringTag'
    }
}
var foo = new Foo('bob', 33);
console.assert(foo.toString() === '[object testToStringTag]');