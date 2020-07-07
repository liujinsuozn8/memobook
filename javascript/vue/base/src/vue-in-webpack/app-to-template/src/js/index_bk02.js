import Vue from 'vue';

// 2. 从 #app 中抽取代码，保存到Vue实例的 template属性中
new Vue({
  el: '#app',
  template:`
  <div>
    msg: <p>{{msg}}</p>
    <button @click='btnClick'>btn</button>
  </div>
  `,
  data: {
    msg: 'webpack test msg',
  },
  methods:{
    btnClick(){
      console.log('btn click');
    }
  }
});
