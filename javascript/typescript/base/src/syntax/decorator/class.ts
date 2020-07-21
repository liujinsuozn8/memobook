// 类装饰器

// 1. 定义普通装饰器
// param 就是被装饰的类本身
function logClass(target:any):void{
    // 为类添加额外的方法
    target.prototype.info = function(msg:string):void{
        console.log(msg);
    }
}

// 2. 定义装饰器工厂
function ApiURL(url:string):any{
    url = 'http://' + url;
    return function(target:any){
        // 为类添加额外的属性
        target.prototype.apiUrl = url;
    }
}

// 3. 使用装饰器标识类，类会自动作为装饰器函数的参数
@logClass
@ApiURL('www.aaaa.bbb')
class HttpClient{
    constructor(){}

    getData(){}
}


// 4. 使用装饰器扩展的内容
// 默认情况下直接使用会有编译异常
// 可以将 实例的类型声明为 any 来避免
// let hc: HttpClient = new HttpClient();
let hc: any = new HttpClient();
console.log(hc.apiUrl);     // http://www.aaaa.bbb

hc.info('test msg')         // test msg