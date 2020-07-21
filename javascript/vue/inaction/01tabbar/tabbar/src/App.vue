<template>
  <div id="app">
    <main-tab-bar></main-tab-bar>
  </div>
</template>

<script>
// 将TabBar的复杂设定封装到 MainTabBar.vue，然后使用该组件
import MainTabBar from '@components/MainTabBar.vue'
export default {
  components:{
    MainTabBar
  }
}
</script>
<style>
/* 引入公共css */
@import '~@assets/css/base.css';
</style>
