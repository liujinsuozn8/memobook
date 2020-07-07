import Vue from 'vue';

// 1. 从Vue实例中，将 `template` 代码、数据、方法等抽取到一个组件中
const App = {
  template:`
  <div>
    msg: <p>{{msg}}</p>
    <button @click='btnClick'>btn</button>
  </div>
  `,
  data(){
    return {
      msg: 'webpack test msg',
    }
  },
  methods:{
    btnClick(){
      console.log('btn click');
    }
  }
}


new Vue({
  el: '#app',
  // 2. 在Vue实例中注册组件
  components:{
    App
  },

  // 3. 在Vue实例的 `template` 中，使用组件
  template: `<App></App>`
});
