let x = 1;
function foo(){
    if (true){
        let x = 12;
        let y = 13;
        console.log(x); // 12
        function bar(){console.log('this is bar')}
    }
    bar(); // this is bar
    // 无法使用 `{}` 内部用let声明的变量
    // console.log(y); // ReferenceError: y is not defined
}

foo();
console.log(x); // 1