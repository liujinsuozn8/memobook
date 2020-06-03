// 定义没有依赖的模块
define(function() {
    let msg = "this is m1"
    function show(){
        console.log(msg)
    }

    return {show}
});
