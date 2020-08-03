class MyFunction extends Function{}
class MyArray extends Array{}
class MyDate extends Date{}

// 1. 实例化 Function 的子类对象，并作为方法调用
var func = new MyFunction('console.log("this is MyFunction")');
func();

// 2. 创建 Array 的子类，并通过设置元素，来修改length属性
var arr = new MyArray(10);
arr[20] = 0;
console.assert(arr.length === 20);

// 3. 实例化 Date 的子类，可以打印子类对象代表的时间
var d = new MyDate();
console.log(d);