// 解构 try...catch 中的异常对象

try {
    throw {msg:'errorMsg', code:0001}
} catch({code}){
    // console.log('msg = ', msg);     // msg =  errorMsg
    console.log('code = ', code);   // code =  1
}