function foo(){
    console.log('foo constructor');
}
foo.prototype.print = function(p){
    console.log(p);
}

// 连续调用函数与方法
new foo().print(1234);
// 输出
// foo constructor
// 1234