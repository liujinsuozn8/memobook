<template>
  <div>
    <p>this is my page</p>
    <!-- 2. 显示query参数 -->
    <p>{{this.$route.query}}</p>
    <p>name = {{name}}</p>
    <p>age = {{age}}</p>
    <button @click='handleClick'>show route</button>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  computed:{
    // 1. 通过计算属性获取query对象的数据
    name(){
      return this.$route.query.name;
    },
    age(){
      return this.$route.query.age;
    },
  },
  methods:{
    handleClick(){
      console.log(this.$route);
    }
  }
}
</script>