// 接口间的继承

// 1. 创建2个接口
interface MyInterface01{
    work():void;
}

interface MyInterface02{
    run():void;
}
// 2. 多接口继承
interface MyInterface03 extends MyInterface01, MyInterface02{
    fly():void;
}

//3. 实现接口，同实现所有的约束
class MyInstance implements MyInterface03{
    work():void{
        console.log('instance is working');
    }
    run():void{
        console.log('instance is running');
    }
    fly():void{
        console.log('instance is flying');
    }
}

let a = new MyInstance();
a.work();
a.run();
a.fly();