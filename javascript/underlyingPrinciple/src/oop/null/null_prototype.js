// 1. 创建构造函数
function foo(){
    this.run = function(){
        console.log('this is foo run')
    }
}
// 2. 将构造函数的原型设为null
foo.prototype = null;
// 3. 实例化对象
var instance = new foo();
// 4. foo.prototype 是 null，所以无法正常的执行类型判断
try{
    console.log(instance instanceof foo);    
} catch (e){
    console.log(e.message); // Function has non-object prototype 'null' in instanceof check
}

// 5. foo 的实例是 Object() 的实例
console.log(instance instanceof Object); // true

// 6. 实例的原型不是 null
console.log(Object.getPrototypeOf(instance) === null); // false
// 7. 实例原型是一个由 Object() 创建的对象
console.log(Object.getPrototypeOf(instance)); // {}
// 8. 实例的构造器是 Object()
console.log(Object.getPrototypeOf(instance).constructor === Object); // true
// 9. 构造函数的原型是 null，但是实例的原型已经变成的Object，因为实例是由 Object() 创建的
console.log(Object.getPrototypeOf(instance) === foo.prototype); // false
// 10. 实例的原型变成了Object.prototype
console.log(Object.getPrototypeOf(instance) === Object.prototype);  // true
// 11. 有 Object() 创建的对象会作为this对象，执行构造函数，所以实例可以执行构造函数内添加的方法
instance.run(); // this is foo run