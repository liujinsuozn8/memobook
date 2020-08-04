// 1. 创建空对象，并添加描述符
var obj = Object.create(null, {
    x: {
        value: 10,
        writable: true,
        enumerable: true,
        configurable: true,
    },
    count:{
        // 通过 getter、setter 来操作属性 x
        get(){
            return this.x * 10
        },
        set(value){
            this.x = value / 100
        },
        enumerable: true,
        configurable: true,
    }
})

// 2. 通过 getter 取值
console.assert( obj.count === 100);
// 3. 通过 setter 设值
obj.count = 200;
console.assert( obj.count === 20);