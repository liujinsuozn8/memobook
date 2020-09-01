// 使用 `箭头函数` 尝试解决 this 丢失问题

var id = 'global';
var obj = {
    id:1234,
    foo(){
        console.log('foo:' + this.id);
        return ()=>{
            console.log("arrow:" + this.id);

        }
    }
}

// 1. 箭头函数所在的上下文丢失了 this，使箭头函数也无法维持 this
var a = obj.foo;
var inner = a();    // foo:undefined
inner();            // arrow:undefined

// 2. 箭头函数所在的上下文还保持着 this，箭头函数可以使用this
var b = obj.foo();  // foo:1234
b();                // arrow:1234