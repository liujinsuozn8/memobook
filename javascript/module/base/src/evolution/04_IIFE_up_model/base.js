// IIFE模式增强：在IIFE的基础上，注入依赖
(function(window, other){
    let msg="module4"

    function foo(){
        console.log(msg)
        // 调用外部依赖的功能
        other.show()
    }

    window.module4 = foo
})(window, other03) //注入依赖
