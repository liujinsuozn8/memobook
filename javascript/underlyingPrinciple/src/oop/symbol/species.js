// 1. 函数--直接设置在函数对象上
// 1.1 设置构造函数原型继承 Array
function Bar(...args){
    Array.call(this);
    for(let n of args){
        this.push(n);
    }
}
Object.setPrototypeOf(Bar.prototype, Array.prototype);
Bar[Symbol.species] = function(){
    // 1.2 隐式创建对象时，使用 Array 作为构造器创建对象
    return Array;
}
var bar = new Bar(1,2,3,4,5);
console.log(bar);   // Bar { '0': 1, '1': 2, '2': 3, '3': 4, '4': 5, length: 5 }
// 1.3 调用map方法，触发隐式创建对象，返回一个由 Array 作为构造函数创建的对象
var barArr = bar.map(v=>v);
console.log(barArr);    // [ 1, 2, 3, 4, 5 ]
console.log(barArr instanceof Bar);     // false
console.log(barArr instanceof Array);   // true

// 2. 类声明--设置静态的getter
// 2.1 创建一个继承 Array 的子类
class Foo extends Array{
    static get [Symbol.species](){
        // 2.1 隐式创建对象时，使用 Array 作为构造器创建对象
        return Array;
    }
}

var foo = new Foo(1, 2, 3, 4);
// 2.2 调用map方法，触发隐式创建对象，返回一个由 Array 作为构造函数创建的对象
var newArr = foo.map(v=>v)
console.log(newArr)  // [ 1, 2, 3, 4 ]
console.log(newArr instanceof Foo);     // false
console.log(newArr instanceof Array);   // true