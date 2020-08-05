// 1. 对象--对象属性
var a = {};
a[Symbol.hasInstance] = function (obj) {
    console.log('Object hasInstance');
    // 检查对象是否包含 className 属性，并且是否等于 MyFn
    return obj.className === 'FnA';
}

var b = { className: 'FnA' };
console.log(b instanceof a);    // true

// 2. 函数--需要通过 `Object.defineProperties()`来覆盖原型对象的属性描述符
// 检查原型对象的属性描述符
// console.log(Object.getOwnPropertyDescriptors(Function.prototype))
function MyFn() {}
// 添加符号属性的属性描述符
Object.defineProperties(MyFn, {
    [Symbol.hasInstance]:{
        value: function (obj) {
            console.log('MyFn hasInstance')
            // 检查对象是否包含 className 属性，并且是否等于 MyFn
            return obj.className === 'MyFn';
        }
    }
});
var myfn = new MyFn();
console.log(myfn instanceof MyFn);  // false
var other = { className: 'MyFn' };
console.log(other instanceof MyFn); // true

// 3. 类声明--静态函数
class Foo {
    static [Symbol.hasInstance](obj) {
        console.log('Foo hasInstance')
        // 如果是 Foo 的对象则返回 true
        // 或者对象中包含 className 自动并且等于 Foo
        return super[Symbol.hasInstance](obj) || (obj && obj['className'] === 'Foo')
    }
}

// 3.1 测试类示例
var foo = new Foo();
console.log(foo instanceof Foo);    // true

// 3.2 测试对象中包含 className 属性，并且等于 Foo
var obj = { className: 'Foo' };
console.log(obj instanceof Foo);   // true

// 3.3 测试对象中包含 className 属性，但是不等于 Foo
var obj2 = { className: 'Other' };
console.log(obj2 instanceof Foo);  // false