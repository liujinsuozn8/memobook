// 1. 对象字面量--属性赋值
var obj = {
    name: 'testName',
    age: 22,
    [Symbol.toPrimitive](hint) {
        console.log(hint)
        switch (hint) {
            case 'string':
                return this.name;
            case 'number':
                return this.age;
            default:
                return `name:${this.name},age:${this.age}`
        }
    }
}

console.log(+obj);          // number, 22
console.log(`${obj}`);      // string, testName
console.log('1234' + obj);  // default, 1234name:testName,age:22
console.log(234 + obj);     // default, 234name:testName,age:22


// 2. 类声明--实例方法
class Foo{
    name
    age
    constructor(name, age){
        this.name = name;
        this.age = age;
    }
    // 声明类方法
    [Symbol.toPrimitive](hint) {
        console.log(hint)
        switch (hint) {
            case 'string':
                return this.name;
            case 'number':
                return this.age;
            default:
                return `name:${this.name},age:${this.age}`
        }
    }
}

var foo = new Foo('bob', 33)
console.log(+foo);          // number, 33
console.log(`${foo}`);      // string, bob
console.log('1234' + foo);  // default, 1234name:bob,age:33
console.log(234 + foo);     // default, 234name:bob,age:33