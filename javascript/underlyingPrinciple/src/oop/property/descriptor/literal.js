// 1. 创建字面量，使用默认描述符
var obj = {
    x: 10,
    // 通过 getter、setter 来操作属性 x
    get count(){
        return this.x * 10
    },
    set count(value){
        this.x = value / 100
    }
};

// 2. 通过 getter 取值
console.assert( obj.count === 100);
// 3. 通过 setter 设值
obj.count = 200;
console.assert( obj.count === 20);