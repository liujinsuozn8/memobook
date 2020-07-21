<template>
  <div>
    <p>this is keep page</p>
    <router-link to='/keep/first'>first</router-link> |
    <router-link to='/keep/second'>second</router-link>

    <router-view></router-view>

  </div>
</template>

<script>
export default {
  data(){
    return {
      path:'/keep/first'
    }
  },
  created(){
    console.log("this is keep created");
  },
  destroyed(){
    console.log("this is keep destroyed");
  },

  /*
    进入路由时，设置默认路由
    如果是第一次进入，则使用默认的 path
    如果不是第一次进入，则使用由组件路由守卫 `beforeRouteLeave` 事实更新的路由路径
  */
  activated(){
    this.$router.push(this.path);
    console.log('keep page activated');
  },
  // deactivated(){
  //   console.log(this.$route);
  //   // console.log(this.path);
  //   console.log('keep page deactivated');
  // },
  beforeRouteLeave(to, from ,next){
    // 在路由离开前，将当前的活动路由的路径保存到 path 属性中
    this.path = this.$route.path;
    next();
  }
}
</script>