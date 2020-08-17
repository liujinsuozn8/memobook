// `Object.defineProperty` 动态添加的方法无法使用 `super` 的示例

// 1. 创建一个父类对象
var superObj = {
    foo(){
        console.log('superObj foo');
    }
}

// 2. 创建一个新对象，并设置原型对象
var obj = {
    test(){
        super.foo();    // 在对象内直接声明的中，可以使用super，
        console.log('test');
    }
};
Object.setPrototypeOf(obj, superObj);

// 3. 为子类对象【动态添加方法】
Object.defineProperty(obj, 'foo', {
    value(){
        /*
            无法访问super，因为super使用的 `[[HomeObject]]` 是当前对象，
            脱离了 obj 对象，所以无法访问到 superObj
            抛出异常:
            TypeError: (intermediate value).foo is not a function
        */
        super.foo();
        console.log('obj foo');
    }
});

// 4. 执行方法，并尝试调用 super
// 4.1
obj.test();
// 输出
// superObj foo
// test

// 4.2 抛出异常，无法找到super.foo
obj.foo();