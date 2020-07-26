// 1. 返回一个值类型，该值类型数据会被忽略，仍然返回this
function Fnc(){
    this.name = 'testName';
    return 'abcd';
}
let fnc = new Fnc();
console.log(fnc);   // 输出: Fnc { name: 'testName' }

// 2. 返回一个引用类型，返回该引用类型数据
function Foo(){
    this.name = 'testName';
    return {};
}

let foo = new Foo();
console.log(foo);   // 输出: {}

// 3. 没有返回值，返回this对象
function Bar(){
    this.name = 'testName';
}

let bar = new Bar();
console.log(bar);   // 输出: Bar { name: 'testName' }