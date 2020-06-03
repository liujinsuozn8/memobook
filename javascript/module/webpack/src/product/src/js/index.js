// webpack的入口文件
import hello from './hello'; // 1. 引入js文件
// import '../css/hello.css';   // 2. 引入css文件
import '../css/hello.less'; // 3. 引入less文件
// import '../font/fa/css/all.css'; // 4. 引入字体

import testJson from '../json/test.json'; // 5. 输出json文件内容
// import '@babel/polyfill';                // 8. 引入 @babel/polyfill
import { mul } from './tree_shaking'; // 10. tree shaking测试，只引入一个方法
// import { mul, minus } from './tree_shaking'; // 10. tree shaking测试，只引入一个方法

console.log(testJson); // 5. 引入json文件

function add(x, y) {
  // 6. 自定义函数
  return x + y;
} // eslint-disable-next-line


console.log(add(5, 3)); // 7. 忽略 eslint 检查

const es6Foo = function es6Foo() {
  // 8. js兼容性 测试
  return console.log('this is es6_foo');
};

es6Foo();
new Promise(((resolve) => {
  setTimeout(() => {
    resolve(5000);
  }, 2000);
})).then((data) => console.log('resolve = '.concat(data)));

// 9. babel 缓存测试：修改hello方法，并重新编译来测试 babel缓存
hello();

// 10. tree shaking测试
console.log(mul(2, 5));
// console.log(minus());

// 11. 懒加载、预加载测试
// document.querySelector('#lazybtn').onclick=function(){
//     import(/* webpackChunkName: 'lazybtn', webpackPrefetch: true */'./lazybtn')
//         .then(({lazyBtnPrint})=>{
//             lazyBtnPrint()
//         }
//     )
// }

// 12. 配置 PWA --- 注册 serviceWorker
// 处理兼容性问题：
//    如果浏览器有 serviceWorker 属性就配置，没有就不配置
if ('serviceWorker' in navigator) {
  // 在所有资源加载完成之后，配置 serviceWorker
  window.addEventListener('load', () => {
    navigator.serviceWorker
      .register('/service-worker.js')
      .then(() => {
        console.log('sw regist success'); // 配置成功
      })
      .catch(() => {
        console.log('sw regist failure'); // 配置失败
      });
  });
}
