var separator = {};
separator[Symbol.split] = function(str, limit){
    // 该函数的返回值做为 split() 的返回值
    return str.split(' ', limit);
}

var str = 'hello world';
console.log(str.split(separator));