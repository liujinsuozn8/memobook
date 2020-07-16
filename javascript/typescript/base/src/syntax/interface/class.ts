// 类类型接口

// 1. 定义两个接口
interface AnimalInterface{
    name:string;
    age:number;
    getInfo():string;
    eat(food:string):void;
}

interface Runnable{
    run():void
}

// 2. 实现接口，同时实现多个
class Bird implements AnimalInterface, Runnable{
    // 2.1 实现属性约束
    name:string;
    age:number;
    constructor(name:string, age:number){
        this.name = name;
        this.age = age;
    }
    // 2.2 实现方法约束
    // 2.2.1 AnimalInterface 接口的约束
    getInfo():string{
        return `bird: name=${this.name}, age=${this.age}`;
    }
    eat(food:string):void{
        console.log(`bird: ${this.name} is eating ${food}`);
    }

    // 2.2.2 Runnable 接口的约束
    run():void{
        console.log(`bird is running`);
    }
}

// 3. 实例化对象并使用
let bird = new Bird('tom', 3);
console.log(bird.getInfo());
bird.eat('APPLE');
bird.run();