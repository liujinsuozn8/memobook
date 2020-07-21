<template>
  <!-- 使用 router-link 完成路由跳转-->
  <router-link class='tabbar--item' :to='path'>
    <!-- 对外部提供三个插槽，分别对应普通按钮图片、激活的按钮图片、按钮文字
         将slot放在div中，在div上设置绑定参数，防止slot替换时，参数失效 -->
    <div v-if='!isActive'>
      <slot name='item-icon'></slot>
    </div>
    <div v-else>
      <slot name='item-icon-active'></slot>
    </div>
    <!-- 根据激活状态设置属性 -->
    <div :style='activeTextColor'>
      <slot name='item-text'></slot>
    </div>
  </router-link>
</template>

<script>
export default {
  name: 'TabBarItem',
  props:{
    path:String,  // 在使用插槽时，设置路由路径
    activeColor: { //在使用插槽时，设置激活状态的图片颜色
      type: String,
      default: 'red',
    }
  },
  computed:{
    isActive(){
      // console.log(this.$route);
      // 使用 indexOf 来判断路劲，防止漏掉包含子路由的情况
      return this.$route.path.indexOf(this.path) != -1;
    },
    activeTextColor(){
      // 激活状态设置颜色；非激活状态，通过 {} 消除样式
      return this.isActive? {'color': this.activeColor}: {};
    }

  }

  // 使用计算属性替代
  // data(){
  //   return {
  //     isActive:false,
  //   }
  // },
}
</script>

<style>
.tabbar--item{
  flex: 1;
  font-size: 14px;
  line-height: 14px;
  /* 去除超链接的下划线和颜色 */
  text-decoration: none;
  color:black;
}
.tabbar--icon{
  margin:3px;
  vertical-align:middle;
  height:24px;
  width:24px;
}

/* 使用计算属性替换 */
/* .tabbar--text__active{
  color:red;
} */
</style>