import * as types from '@/store/types'; // 引入 Mutation 常量模块
import Vue from 'vue';
export default {
  // --------------------------------------------------------
  // 使用常量管理 Mutation
  // 1. 计数器 counter 处理
  [types.INCREMENT](state) {
    state.counter++;
  },
  [types.DECREMENT](state) {
    state.counter--;
  },

  // 检验 $store.commit() 的参数个数
  // 每次为 counter 增加 n
  [types.INCREMENTN](state, n, b, c) {
    console.log(`n=${n}, b=${b}, c=${c}`);
    state.counter += n;
  },
  // --------------------------------------------------------

  // 2. 商品列表处理
  // 2.1 添加商品， 普通提交方式
  appendGoods(state, payload){
    state.goodsList.push(payload);
  },
  // 2.2 添加商品， 通过 type 属性提交
  appendGoodsByType(state, payload){
    console.log(payload.type);
    // 使用时，需要排除 type 属性
    state.goodsList.push({
      name:payload.name,
      price:payload.price,
      count:payload.count,
    });
  },
  // 3. 添加删除 state 中的属性
  // 3.1 删除一个状态对象中的属性
  deleteAddress(state){
    Vue.delete(state.addressInfo, 'address');
  },
  // 3.2 向一个状态对象中，添加属性
  addAddress(state){
    Vue.set(state.addressInfo, 'address', 'new address');
  },

  // 3.3 添加一个新的状态
  addOther(state){
    Vue.set(state, 'other', 'otherInfo');
  }
}