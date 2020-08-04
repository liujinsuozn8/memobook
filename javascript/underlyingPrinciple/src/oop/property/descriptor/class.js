// 1. 创建类，并添加描述符
class Foo{
    // 设置普通属性，会添加到对象内
    x = 10

    // 作为 count 的属性描述符，添加到类的原型对象
    // 通过 getter、setter 来操作属性 x
    get count(){
        return this.x * 10
    }
    set count(value){
        this.x = value / 100
    }

    // 普通函数，也会添加到类的原型对象
    run(){
        console.log('running');
    }

    // 静态方法，会添加到类自身
    static work(){
        console.log('working');
    }
}

// 2. 实例化对象
var obj = new Foo();
// 3. 通过 getter 取值
console.assert( obj.count === 100);
// 4. 通过 setter 设值
obj.count = 200;
console.assert( obj.count === 20);
// 5. 调用方法
obj.run();  // running

// 6. 调用静态方法
Foo.work();

// 7. 检查当前对象的描述符
console.table( Object.getOwnPropertyDescriptors(obj));
// ┌─────────┬───────┬──────────┬────────────┬──────────────┐
// │ (index) │ value │ writable │ enumerable │ configurable │
// ├─────────┼───────┼──────────┼────────────┼──────────────┤
// │    x    │   2   │   true   │    true    │     true     │
// └─────────┴───────┴──────────┴────────────┴──────────────┘

// 8. 输出类静态成员的描述符
console.table(Object.getOwnPropertyDescriptors(Foo));
// ┌───────────┬──────────────────┬──────────┬────────────┬──────────────┐
// │  (index)  │      value       │ writable │ enumerable │ configurable │
// ├───────────┼──────────────────┼──────────┼────────────┼──────────────┤
// │  length   │        0         │  false   │   false    │     true     │
// │ prototype │      Foo {}      │  false   │   false    │    false     │
// │   work    │ [Function: work] │   true   │   false    │     true     │
// │   name    │      'Foo'       │  false   │   false    │     true     │
// └───────────┴──────────────────┴──────────┴────────────┴──────────────┘

// 9. 输入类原型上的描述符
console.table(Object.getOwnPropertyDescriptors(Foo.prototype));
// ┌─────────────┬─────────────────┬──────────┬────────────┬──────────────┬───────────────────────┬───────────────────────┐
// │   (index)   │      value      │ writable │ enumerable │ configurable │          get          │          set          │
// ├─────────────┼─────────────────┼──────────┼────────────┼──────────────┼───────────────────────┼───────────────────────┤
// │ constructor │ [Function: Foo] │   true   │   false    │     true     │                       │                       │
// │    count    │                 │          │   false    │     true     │ [Function: get count] │ [Function: set count] │
// │     run     │ [Function: run] │   true   │   false    │     true     │                       │                       │
// └─────────────┴─────────────────┴──────────┴────────────┴──────────────┴───────────────────────┴───────────────────────┘
