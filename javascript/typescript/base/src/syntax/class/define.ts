// 定义类
class Person{
    name: String;               // 默认省略public关键字
    public age:number = 20;     // 为属性设置默认值
    private address: String;    // 设置私有后苏醒

    // 构造函数
    constructor(name:String){
        // 需要为属性设置，否则会有编译异常
        this.name = name;
        this.address = '';
    }

    printInfo(): void {
        console.log(`name=${this.name}, age=${this.age}, address=${this.address}`);
    }

    // 为 address 添加getter
    getAddress():String{
        return this.address;
    }
    setAddress(value:String){
        this.address = value;
    }

}

// 实例化对象
let person = new Person('testName');
person.printInfo();                     // 输出: name=testName, age=20, address=
person.setAddress('abcdefg');
console.log(person.getAddress());       // 输出: abcdefg
person.age=22;
person.printInfo();                     // 输出: name=testName, age=22, address=abcdefg

// 无法直接访问 private 变量
// 编译异常
// error TS2341: Property 'address' is private and only accessible within class 'Person'.
// console.log(person.address);