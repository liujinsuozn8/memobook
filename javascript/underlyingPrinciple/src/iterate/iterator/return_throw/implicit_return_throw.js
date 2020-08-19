// 生成器 return / throw 隐式调用 测试

// 1. 创建生成器对象通用的 return / throw 方法
var monitor = {
    return(value){
        console.log('>> return ',this&& this.name || '');
        return {value, done:true};
    },

    throw(err){
        console.log('>> throw', this && this.name || '');
        return {value:err, done:true};
    }
}

// 2. 设置 [[Iterator]]
var arr = [100, 200, 300];
arr[Symbol.iterator] = function(){
    // 创建一个迭代器对象
    var iter = Array.prototype[Symbol.iterator].call(this)
    // 将 return / throw 方法抄写到 生成器对象中
    return Object.assign(iter, monitor, {name:'target'});
}

// 3. 创建一个展开 arr 的生成器
function* MyGenerator(){
    yield 'first';
    yield 'second';
    yield* arr;
    yield 'third';
}

// 4. MyGenerator抛出异常时，会隐式调用 arr 的 throw
var g1 = MyGenerator();
for(let i of g1) {
    console.log(`g1=${i}`);
    if (i===100){
        console.log(g1.throw());
    }
}
// 输出:
// g1=first
// g1=second
// g1=100
// >> throw target
// { value: 'third', done: false }

// 5. MyGenerator迭代时使用 break，会隐式调用 arr 的 return
var g2 = MyGenerator();
for(let i of g2) {
    console.log(`g2=${i}`);
    if (i===100){
        break;
    }
}
// 输出:
// g2=first
// g2=second
// g2=100
// >> return  target
