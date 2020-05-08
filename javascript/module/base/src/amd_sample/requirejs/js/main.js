// 聚合模块

(function(){
    requirejs.config({
        paths: {
            module1: './modules/module1',
            module2: './modules/module2'
        }
    });

    requirejs(['module2'], function(module2){
        module2.foo()
    })
})()
