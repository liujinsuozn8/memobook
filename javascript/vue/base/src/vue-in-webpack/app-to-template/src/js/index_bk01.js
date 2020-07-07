import Vue from 'vue';


// 1. 直接将html代码写在 #app 内部，Vue实例负责提供数据和方法
new Vue({
  el: '#app',
  data: {
    msg: 'webpack test msg',
  },
  methods:{
    btnClick(){
      console.log('btn click');
    }
  }
});
