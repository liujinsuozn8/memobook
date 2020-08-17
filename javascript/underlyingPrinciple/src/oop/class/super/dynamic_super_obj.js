// 为 `Object.defineProperty` 动态添加的方法设置 `super`

// 1. 创建一个父类对象
var superObj = {
    foo(){
        console.log('superObj foo');
    }
}

// 2. 创建一个新对象，并设置原型对象
var obj = {};
Object.setPrototypeOf(obj, superObj);

// 3. 设置【具名的配置对象】
var configObj = {
    value(){
        super.foo();
        console.log('obj foo');
    }
};
// 4. 为子类对象【动态添加方法】
Object.defineProperty(obj, 'foo', configObj);

// 5. 为配置对象【设置原型对象 为父类对象】
Object.setPrototypeOf(configObj, superObj);

// 4. 执行方法，并尝试调用 super
obj.foo();
// 输出
// superObj foo
// obj foo