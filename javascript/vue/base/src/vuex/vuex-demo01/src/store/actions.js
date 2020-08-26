import * as types from '@/store/types'; // 引入 Mutation 常量模块
export default {
  [types.ADDXDELAY](context, x){
    console.log(context);

    // 1. 返回一个 Promise 对象，由调用者处理异步处理的结果
    return new Promise((resolve, reject)=>{
      setTimeout(() => {
        // 2. 通过 commit，调用 mutation 修改状态
        context.commit(types.INCREMENTN, x);
        // 3. 修改 promise 状态，并返回处理结果
        resolve('commit success');
      }, 1000);
    })
  }
}