(function (window){
    let msg = "this is other"
    function show(){
        console.log(msg)
    }

    window.other03 = {show} // 将方法封装到对象中暴露给外部使用
})(window) //注入依赖
