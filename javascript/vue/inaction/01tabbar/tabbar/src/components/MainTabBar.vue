<template>
  <tab-bar>
    <!-- 将按钮组件注入到插槽中 -->
    <!-- 使用自定义的按钮颜色 -->
    <tab-bar-item path='/facebook' active-color='blue'>
      <img src="~@assets/img/tabbar/facebook.svg" alt="facebook" class='tabbar--icon' slot='item-icon'>
      <img src="~@assets/img/tabbar/facebook_active.svg" alt="facebook" class='tabbar--icon' slot='item-icon-active'>
      <div slot='item-text'>facebook</div>
    </tab-bar-item>
    <!-- 使用默认的按钮颜色 -->
    <tab-bar-item path='/happy'>
      <img src="~@assets/img/tabbar/smell.svg" alt="happy" class='tabbar--icon' slot='item-icon'>
      <img src="~@assets/img/tabbar/smell_active.svg" alt="happy" class='tabbar--icon' slot='item-icon-active'>
      <div slot='item-text'>happy</div>
    </tab-bar-item>
    <tab-bar-item path='/browser'>
      <img src="~@assets/img/tabbar/browser.svg" alt="browser" class='tabbar--icon' slot='item-icon'>
      <img src="~@assets/img/tabbar/browser_active.svg" alt="browser" class='tabbar--icon' slot='item-icon-active'>
      <div slot='item-text'>browser</div>
    </tab-bar-item>
    <tab-bar-item path='/others'>
      <img src="~@assets/img/tabbar/others.svg" alt="others" class='tabbar--icon' slot='item-icon'>
      <img src="~@assets/img/tabbar/others_active.svg" alt="others" class='tabbar--icon' slot='item-icon-active'>
      <div slot='item-text'>others</div>
    </tab-bar-item>
  </tab-bar>
</template>

<script>
// 注册组件
import TabBar from '@components/tabbar/TabBar.vue';
import TabBarItem from '@components/tabbar/TabBarItem.vue';
export default {
  name: 'MainTabBar',
  components: {
    TabBar,
    TabBarItem
  }
}
</script>