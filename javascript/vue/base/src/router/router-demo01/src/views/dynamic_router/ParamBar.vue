<template>
    <div>
      <!-- 2. 通过计算属性在模板中使用路由参数 -->
      <p>param is {{dydata}}</p>
    </div>
</template>

<script>
export default {
  name:'ParamBar',
  computed: {
    dydata(){   // 1. 获取路由参数，并banding到计算属性
      return this.$route.params.dydata;
    }
  }
  // data无法实时刷新
  // data(){
  //   return {
  //     userid: this.$route.params.dydata,
  //   };
  // }
}
</script>