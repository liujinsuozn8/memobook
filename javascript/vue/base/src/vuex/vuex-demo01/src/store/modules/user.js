import * as types from '@/store/types'; // 引入 Mutation 常量模块

// 封装额外的 module
export default {
  state:{
    name:'bob',
    age:22,
    address:"mine house",
    score: 100
  },
  getters:{
    fullAddress(state, _, rootState){
      // 通过 rootState，访问【根模块】中的数据
      return `${rootState.addressInfo.address} + ${state.address}`;
    },
    fullInfo(state, getters){
      return `name:${state.name}, age:${state.age}, address:${getters.fullAddress}`
    },
  },
  // 修改当前模块内的 state
  mutations:{
    incrUserAge(state){
      state.age++;
    },
    decrUserAge(state){
      state.age--;
    },
    clearScore(state){
      state.score = 0;
    }
  },
  actions:{
    clearScoreDelay(context){
      setTimeout(() => {
        context.commit('clearScore');
      }, 500);
    },
    incrUserAgeDelay(context){
      console.log(context);
      setTimeout(() => {
        // 调用当前模块内的 mutation
        context.commit("incrUserAge");
        // 调用根模块内的 mutation
        context.commit(types.INCREMENT);

        // 调用当前模块内容的 action
        context.dispatch('clearScoreDelay');
        // 调用根模块中的 action
        context.dispatch(types.ADDXDELAY, 100);

        // 获取其他模块的 getters
        console.log(context.getters.powerCount);
      }, 1000);
    }
  },
}