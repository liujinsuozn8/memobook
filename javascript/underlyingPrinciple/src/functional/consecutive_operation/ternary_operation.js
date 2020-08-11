var type = 'Array';

// 三元表达式连用
var Class = (type === 'String') ? String
        : (type === 'Boolean') ? Boolean
        : (type === 'Number') ? Number
        : (type === 'Array') ? Array
        : Object;

var obj = new Class();
console.log(Object.prototype.toString.call(obj)); // [object Array]
