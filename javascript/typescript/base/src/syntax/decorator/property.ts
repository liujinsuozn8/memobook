// 属性装饰器

// 1. 定义属性装饰器
// 为属性设置属性值
function Value(propValue:any){
    // target: 对于静态成员来说是类的构造函数，对于实例成员是类的原型对象
    // propName: 成员的名字
    return function(target:any, propName:any){
        target[propName] = propValue;
    }
}
// function Value(target:any, propName:any){
//     target[propName] = 'wwwwwww';
// }

// 2. 使用属性装饰器
class StudentA{
    // 为属性设值
    @Value('www.aa.bbb')
    name:string | undefined
    constructor(){}

    printInfo():void{
        console.log(this.name);
    }
}

let studentA:StudentA = new StudentA();
studentA.printInfo();   // www.aa.bbb