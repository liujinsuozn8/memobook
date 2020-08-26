import Vue from 'vue'
import Vuex from 'vuex'
import mutations from './mutations';
import actions from './actions'

// 引入其他 module
import user from './modules/user';

Vue.use(Vuex)

const state = {
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
}

const getters = {
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
}

export default new Vuex.Store({
  state,
  getters,
  mutations,
  // 在异步操作中修改状态
  actions,
  modules: {
    user,
  }
})
