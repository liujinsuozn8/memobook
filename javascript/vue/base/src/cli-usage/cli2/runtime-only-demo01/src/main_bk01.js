import Vue from 'vue';

Vue.config.productionTip = false;

// 1. 通过render函数，手动创建一个html标签
/* eslint-disable no-new */
new Vue({
  el: '#app',
  render(createElement) {
    return createElement(
      'p',
      { style: 'color:#47e' },
      ['render test']);
  },
});

// 2. runtime-only 模式下，不能使用手动创建的组件
/* eslint-disable no-new */
// new Vue({
//   el: '#app',
//   render(createElement) {
//     return createElement({
//       template: `
//         <p>{{msg}}</p>
//       `,
//       data() {
//         return { msg: 'render test data' };
//       },
//     });
//   },
// });
