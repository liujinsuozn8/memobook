<template>
  <div>
    <!-- 插槽中的链接负责路由跳转，外部的容器负责显示路由 -->
    <router-view></router-view>
    <div class='tabbar'>
      <!-- 对外部只提供插槽 -->
      <slot></slot>
    </div>
  </div>
</template>

<style>
.tabbar{
  /* 使用弹性容器布局 */
  display: flex;
  /* 设置高度和居中 */
  justify-content: center;
  text-align: center;
  height: 49px;
  /* 设置背景颜色 */
  background-color: #ededed;

  /* 开启固定定位，定位到页面底部 */
  position:fixed;
  bottom:0px;
  /* 固定定位会脱离文档流，将左右位置设为0，
    水平等式会自动计算width
  */
  left:0px;
  right:0px;
}
</style>