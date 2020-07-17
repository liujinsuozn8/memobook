// 泛型类

// 1. 定义泛型类
class MyArrayList<T>{
    private data:T[];
    constructor(){
        this.data = [];
    }

    add(e:T):void{
        this.data.push(e);
    }

    toString():string{
        let str:string = '';
        for (let e of this.data){
            str += e + ' ,';
        }
        return str;
    }
}

// 2. 实例化泛型类对象，并调用方法
let list = new MyArrayList<number>();
list.add(123);
list.add(3456);
list.add(334);
console.log(list.toString());

// 没有调用点，会导致方法调用失败
// let toStringFn = list.toString;
// toStringFn();