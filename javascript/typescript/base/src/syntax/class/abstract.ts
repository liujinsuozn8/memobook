// 1. 定义抽象类和抽象方法
abstract class Animal{
    private name: string;
    constructor(name:string){
        this.name = name;
    }
    // 1. 定义抽象方法
    abstract run():any;
    // 2. 定义普通方法
    getName():string{
        return this.name;
    }
}

// 2.1 子类1
class Dog extends Animal{
    constructor(name:string){
        super(name);
    }
    run():void{
        console.log(`dog: ${this.getName()} is running`);
    }
}

// 2.2 子类2
class Cat extends Animal{
    constructor(name:string){
        super(name);
    }
    run():void{
        console.log(`cat: ${this.getName()} is running`);
    }
}

//3.  通过子类调用方法
let dog = new Dog('mydog');
dog.run();

let cat = new Cat('mycat');
cat.run();