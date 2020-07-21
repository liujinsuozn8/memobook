import Vue from 'vue'
import App from './App.vue'
// 4.1 导入自定义的路由对象
import router from './router'

Vue.config.productionTip = false

new Vue({
  router, // 4.2 将路由对象挂载到Vue实例
  render: h => h(App)
}).$mount('#app')
