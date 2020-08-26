<template>
  <div class="countMutation">
    <p>countMutation page</p>
    <p>{{$store.state.counter}}</p>
    <button @click='add'>+</button>
    <button @click='sub'>-</button>
    <button @click='add10'>+10</button>
    <button @click='add3Delay'>addDelay + 3</button>
  </div>
</template>

<script>
import {INCREMENT, DECREMENT, INCREMENTN, ADDXDELAY} from '@/store/types'

export default {
  name: 'CountMutation',
  methods:{
    // 通过 Mutation 常量来执行对应的 Mutation 方法
    add(){
      this.$store.commit(INCREMENT);
    },
    sub(){
      this.$store.commit(DECREMENT);
    },
    add10(){
      this.$store.commit(INCREMENTN, 10, 'bbb', 'ccc');
    },

    // 通过 action 异步修改状态
    add3Delay(){
      this.$store
          .dispatch(ADDXDELAY, 3)             // 1. 通过 action 异步修改状态
          .then(value=>console.log(value));   // 2. 处理异步的返回结果
    }
  }
}
</script>
<style scoped>
button{
  margin: 0 10px;
}
</style>