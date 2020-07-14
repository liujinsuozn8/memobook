// 类的继承
class BasePerson{
    name:string;
    constructor(name:string){
        this.name = name;
    }

    printInfo(){
        console.log(`name=${this.name}`);
    }
}

// 1. 通过 extends 关键字继承某个类
class Student extends BasePerson{
    constructor(name:string){
        // 2. 通过super调用父类
        super(name);
    }

    // 定义 Student 自己的方法
    study():void{
        console.log(`${this.name} is studying`)
    }

    // 重写父类的方法
    printInfo():void{
        console.log(`Student name = ${this.name}`)
    }
}

let student = new Student('testName');
student.printInfo();
student.study();

