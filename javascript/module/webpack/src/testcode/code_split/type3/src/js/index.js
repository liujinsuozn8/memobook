import $ from 'jquery'
import {others} from './others'
console.log($)
others()

// 使用import动态引入
// import('./test') // 自动命名
import(/* webpackChunkName: 'test' */'./test') // 手动命名
    .then(({mul, minus})=>{
        // 执行包内部的方法
        console.log(mul(1, 2))
        console.log(minus(3, 4))
    }).catch(()=>{
      console.log('import test failure')
    })

function add(x, y) {
  return x + y;
}

console.log(add(5, 3));
