<!-- 将模版、js、样式分离 -->
<template>
    <div class="msgbox">
        msg: <p>{{msg}}</p>
        <button @click='btnClick'>btn</button>

        <!-- 使用子组件 -->
        <child/>
    </div>
</template>

<script>
// 引入其他的组件
import Cpn from './Cpn.vue'
export default {
  name:'App',
  data(){
      return {
      msg: 'webpack test msg',
      }
  },
  methods:{
      btnClick(){
        console.log('btn click')
      }
  },
  // 注册子组件
  components:{
    child:Cpn
  }
}
</script>

<style>
  .msgbox{
    color:#47e;
  }
</style>