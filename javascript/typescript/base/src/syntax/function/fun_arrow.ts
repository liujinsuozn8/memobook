
// 1. 捕获局部的this
// 在函数内部声明的箭头函数，默认函数的上下文this
function funX(){
    // 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
    // let x = ()=>console.log(this);
    // x();
}

// 2. 捕获全局的this

// 2.1 全局的箭头函数
// 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
// let globalArrowFun = ()=> ()=>console.log(this);

// 2.2 对象内部的属性是箭头函数时，默认使用的是全局的this，在页面中是window
let objA:any = {
    // objA中没有this，继续向外搜索，使用的是全局的this
    // 编译异常: error TS7041: The containing arrow function captures the global value of 'this'.
    // fn1:()=>console.log(this),
    fn2(){
        console.log(this);
    },
}
