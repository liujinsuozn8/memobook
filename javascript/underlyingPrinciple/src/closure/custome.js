// 通过IIFE执行函数，分别在内部方法中引用外部函数的局部变量，来创建闭包
(function(window){
    var msg = "test msg";
    function showLower(){
        console.log(msg.toLocaleLowerCase())
    }

    function showUpper(){
        console.log(msg.toLocaleUpperCase())
    }

    window.msgBox = {
        showLower:showLower,
        showUpper:showUpper
    }
})(window)
// 传入参数window，方便代码压缩
