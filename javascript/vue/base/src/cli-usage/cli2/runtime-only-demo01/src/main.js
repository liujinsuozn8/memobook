import Vue from 'vue';

Vue.config.productionTip = false;

// 1. 通过render函数，手动创建一个嵌套的html标签
/* eslint-disable no-new */
new Vue({
  el: '#app',
  render(createElement) {
    return createElement(
      'div',
      { style: 'color:#47e' },
      // 创建多个子标签
      [
        createElement('p', 'div test'),
        createElement('button', 'btn'),
        createElement('br'),
        createElement('button', 'btn'),
      ]);
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
