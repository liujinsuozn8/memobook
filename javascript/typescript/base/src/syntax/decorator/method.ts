// 方法装饰器

// 1. 定义方法装饰器
// 装饰方法，并将方法的所有参数转换为字符串
function MethodParmasToString(target:any, propName:any, descriptor:any){
    // 1.1 从属性描述符中获取原始的函数，并保存
    let originFn = descriptor.value;

    // 1.2 重新设置函数
    descriptor.value = function(...args:any[]){
        // 1.3 将所有参数转换为String
        args = args.map( n => String(n) );
        // 1.4 重新调用原始方法
        originFn(...args);
    }
}

// 2. 使用方法装饰器
class Logger{
    @MethodParmasToString
    info(...args:any[]):void{
        console.log(args);
    }
}

let log:Logger = new Logger();
log.info(1234, 'aaaa', false);  // [ '1234', 'aaaa', 'false' ]