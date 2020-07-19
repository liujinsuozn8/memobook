// 1. 定义返回字符串的标签函数
// 1.1 定义标签函数
function tagFn01(strs, ...exps){
    // 获取两个数组中的最大值
    let minLength = strs.length > exps.length ? exps.length : strs.length;
    // 循环拼接
    let result = '';
    let i;
    for (i= 0; i<minLength; i++){
        result += strs[i];
        if (typeof exps[i] === 'number'){ // 拼接字符串时，执行相关处理
            if (exps[i] > 0){
                result += '正数';
            } else {
                result += '非正数';
            }
            
        } else {
            result += exps[i];
        }
    }

    // 将剩余的部分添加到结果字符串
    if (i < strs.length){
        for (; i<strs.length; i++){
            result += strs[i];
        }
    }
    if (i < exps.length){
        for (; i<str.length; i++){
            if (typeof exps[i] === 'number'){
                if (exps[i] > 0){
                    result += '正数';
                } else {
                    result += '非正数';
                }
                
            } else {
                result += exps[i];
            }
        }
    }

    // 将拼接结果返回
    return result;
}

// 1.2 调用标签函数
let num1 = 1234;
let num2 = -1234;
let num3 = 0;
let str1 = tagFn01`num1 is ${num1}, num2 is ${num2}, num3 is ${num3}`;
console.log(str1);  // num1 is 正数, num2 is 非正数, num3 is 非正数

// 2. 定义返回函数的标签函数
// 参考  https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals#Tagged_template_literals
function template(strings, ...keys) {
    // 返回一个函数。在复用时，传递需要捕获的参数，并拼接字符串
    return function(...values) {
        let dict = values[values.length - 1] || {}; // 最后一个参数需要是对象
        let result = [strings[0]];
        keys.forEach(function(key, i) {   // 开始拼接
            let value = Number.isInteger(key) ? values[key] : dict[key];
            result.push(value, strings[i + 1]);
        });
        return result.join('');
    };
}

// 2.1 在模版中通过index来捕获参数
let t1Closure = template`${0}${1}${0}!`;
//let t1Closure = template(["","","","!"],0,1,0);
console.log(t1Closure('Y', 'A'));                           // "YAY!"

// 2.2 在模版中同时使用index 和 对象属性名的方式来捕获参数
let t2Closure = template`${0} ${'foo'}!`;
//let t2Closure = template(["","","!"],0,"foo");
console.log(t2Closure('Hello', {foo: 'World'}));            // "Hello World!"

// 2.3 
let t3Closure = template`I'm ${'name'}. I'm almost ${'age'} years old.`;
//let t3Closure = template(["I'm ", ". I'm almost ", " years old."], "name", "age");
console.log(t3Closure('foo', {name: 'MDN', age: 30}));      //"I'm MDN. I'm almost 30 years old."
console.log(t3Closure({name: 'MDN', age: 30}));             //"I'm MDN. I'm almost 30 years old."