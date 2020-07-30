var obj = {
    foo(){
        /* super 相当于:
           super.toString = Object.getPrototypeOf(this).toString.bind(this)
        */
        console.log(super.toString());
    },
    bar:function(){
        // 无法使用 super
    }
}

obj.foo();  // 输出: [object Object]