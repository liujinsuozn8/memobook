import Vue from 'vue';
import App from './app/app'; // 引入组件模块

new Vue({
  el: '#app',
  // 2. 在Vue实例中注册组件
  components:{
    App
  },

  // 3. 在Vue实例的 `template` 中，使用组件
  template: `<App></App>`
});
