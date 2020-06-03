// IIFE模式：匿名闭包
(function(){
    let msg = "asdfgh"
    function foo(){
        console.log(msg)
    }

    // 创建对象来封装暴露给外部使用的内容
    window.module3 = {foo}
})()
