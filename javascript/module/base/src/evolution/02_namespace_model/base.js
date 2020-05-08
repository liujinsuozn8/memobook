// namespace模式，简单的对象封装

// 将相关的变量与方法添加到一个对象中
let obj = {
    msg:"qwert",
    foo(){
        console.log(this.msg)
    }
}
