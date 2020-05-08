// 定义一个有依赖的模块
define(['module1'], function(module1) {
    let msg = "this is m2"
    function foo(){
        console.log(msg)
        module1.show()
    }

    return {foo}
});
