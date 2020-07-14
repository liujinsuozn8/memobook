// 1. 在类中声明不同修饰符的属性
class Tool{
    name:string;
    protected introduction:string;
    private executedTime:number = 0;

    constructor(name:string, introduction:string){
        this.name = name;
        this.introduction = introduction
    }

    run():void{
        console.log(`${this.name} is run, executedTime=${this.executedTime}`);
        this.addExecutedTime();
    }

    stop():void {
        console.log(`${this.name} is stop`);
        this.clearExecutedTime();
    }

    protected clearExecutedTime():number{
        return this.executedTime = 0;
    }

    private addExecutedTime():void{
        this.executedTime += 10;
    }
}

let tool = new Tool('computer', 'this is a computer');
// 调用 public 方法
tool.run();
tool.run();
tool.stop();
tool.run();

// 无法调用 protected 方法，会有编译异常
// error TS2445: Property 'clearExecutedTime' is protected and only accessible within class 'Tool' and its subclasses.
// tool.clearExecutedTime();

// 无法调用 private 方法，会有编译异常
// error TS2341: Property 'addExecutedTime' is private and only accessible within class 'Tool'.
// tool.addExecutedTime();

// 2. 在继承类中使用父类中不同修饰符的属性
class SubTool extends Tool{
    constructor(){
        super('subtool', 'this is subtool');
    }

    work(){
        // 调用 protected 方法
        this.clearExecutedTime();
        // 调用 public 方法
        this.run();
        this.run();
        this.run();

        // 无法调用 private 方法，会有编译异常
        // error TS2341: Property 'addExecutedTime' is private and only accessible within class 'Tool'.
        // this.addExecutedTime();
    }
}

let subtool = new SubTool();
subtool.work();