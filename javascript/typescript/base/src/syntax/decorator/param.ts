// 方法参数装饰器

// 1. 定义方法参数装饰器
function Param(target:any, funcName:any, paramIndex:any){
    console.log(target);
    console.log(funcName);
    console.log(paramIndex);
}

// 2. 使用方法装饰器
class LoggerB{
    info(@Param msg):void{
        console.log(msg);
    }
}

let logb = new LoggerB();
logb.info('aaaa');