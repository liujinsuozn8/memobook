<template>
  <div>
    <!-- 使用 module 中的 state -->
    <p>name: {{$store.state.user.name}}</p>
    <p>score: {{$store.state.user.score}}</p>

    <p>age: {{$store.state.user.age}}</p>
    <!-- 调用 module 中的 mutation -->
    <button @click='incrAge'>+</button>
    <button @click='decrAge'>-</button>

    <!-- 调用 module 中的 action -->
    <button @click='incrAgeDelay'>+ delay</button>

    <!-- 调用 module 中的 getter -->
    <p>fullInfo: {{$store.getters.fullInfo}}</p>
  </div>
</template>

<script>
export default {
  name:'UserInfo',
  methods:{
    // 调用 mutation
    incrAge(){
      this.$store.commit('incrUserAge')
    },
    decrAge(){
      this.$store.commit('decrUserAge')
    },
    // 调用 action
    incrAgeDelay(){
      this.$store.dispatch('incrUserAgeDelay');
    }
  }
}
</script>
<style scoped>
button{
  margin: 0 10px;
}
</style>