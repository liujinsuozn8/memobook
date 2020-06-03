// webpack的入口文件

// 引入js文件
import hello from './hello'
// 引入css文件
// import '../css/hello.css'
import '../css/hello.less'
// 引入字体
import '../font/fa/css/all.css'
// 引入json文件
import testJson from '../json/test.json'

function add(x, y){
    return x + y
}

console.log(add(5,3))

// 输出json文件内容
console.log(testJson)

// js文件的HMR有效化
// 在当前模块内搜索module变量，然后在module内搜索hot属性
// 如果 module.hot 存在则说明，开启了HMR功能
if (module.hot){
    // 让HMR代码生效
    // 方法会监听 hello.js 文件，如果发生变化，会执行回调函数
    module.hot.accept('./hello.js', function(){
        // 重新执行模块中的某个方法
        hello()
    })
}

hello()