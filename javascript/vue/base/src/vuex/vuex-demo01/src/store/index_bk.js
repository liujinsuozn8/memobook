import Vue from 'vue'
import Vuex from 'vuex'
import * as types from './types'; // 引入 Mutation 常量模块
import userModule from './modules/user';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 1. 计数器
    counter: 1,
    // 2. 商品列表
    goodsList: [
      { name: 'goods---aaa', price: 11, count: 1 },
      { name: 'goods---bbb', price: 22, count: 2 },
      { name: 'goods---ccc', price: 33, count: 3 },
      { name: 'goods---ddd', price: 55, count: 4 },
    ],
    // userInfo
    addressInfo:{
      address: 'test house',
    }
  },
  mutations: {
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
  },
  getters: {
    powerCount(state) {
      return state.counter * state.counter;
    },

    // 2. 商品列表 处理
    // 2.1 价格求和
    goodsTotalPrice(state) {
      return state.goodsList.map(n => n.price * n.count).reduce((prev, cur) => prev + cur);
    },
    // 2.2 商品总数
    goodsCount(state) {
      return state.goodsList.map(n => n.count).reduce((prev, cur) => prev + cur);
    },
    // 2.3 平均价格
    goodsAvgPrice(state, getters){
      return getters.goodsTotalPrice / getters.goodsCount;
    },
    // 2.4 过滤条件
    goodsFilter(state){
      return function(minPrice=0, minCount=0){
        return state.goodsList.filter(n => n.price > minPrice && n.count > minCount);
      }
    },
  },
  // 在异步操作中修改状态
  actions: {
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
  },
  modules: {
    user: userModule
  }
})
