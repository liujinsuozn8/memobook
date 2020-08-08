try {
    let a = 100;        // 声明重名的变量
    console.log('try a = ', a);
    throw { msg: 'test error', data: a };   // try a =  100
} catch ({ msg, data }) {   // 解构异常对象
    console.log('msg = ', msg);             // msg =  test error
    let a = 20 + data;  // 声明重名的变量
    console.log('catch a = ', a);           // catch a =  120
} finally {
    let a = 30;         // 声明重名的变量
    console.log('finally a = ', a);         // finally a =  30
}