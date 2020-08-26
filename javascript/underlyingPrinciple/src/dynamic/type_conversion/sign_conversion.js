// `+` 符号运算测试

var x = {
    toString() { return '23' },
    valueOf() { return 45 }
};

var y = { toString() { return '66' } };

var z = {valueOf(){ return 'abcd' }};

var newX = +x;
var newY = +y;
var newZ = +z;

console.log(`newX=${newX}，typeof newX = ${typeof newX}`);
console.log(`newY=${newY}，typeof newY = ${typeof newY}`);
console.log(`newZ=${newZ}，typeof newZ = ${typeof newZ}`);

// 输出:
// newX=45，typeof newX = number
// newY=66，typeof newY = number
// newZ=NaN，typeof newZ = number
