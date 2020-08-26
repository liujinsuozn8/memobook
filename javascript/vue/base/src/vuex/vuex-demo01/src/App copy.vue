<template>
  <div id="app">
    <p>{{count}}</p>
    <button @click='count++'>+</button>
    <button @click='count--'>-</button>

    <use-count :count='count'></use-count>
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view/> -->
  </div>
</template>

<script>
import UseCount from '@components/UseCount'
export default {
  data(){
    return { count:0 }
  },
  components:{
    'use-count':UseCount
  }
}
</script>


<style>

</style>
