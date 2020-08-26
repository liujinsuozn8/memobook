<template>
  <div id="app">
    <div id="nav">
      <!-- <router-link to="/">Home</router-link> | -->
      <router-link class='router-link' to="/stateCount">stateCount</router-link>
      <router-link class='router-link' to="/goodsList">goodsList</router-link>
      <router-link class='router-link' to="/userInfo">userInfo</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script>
// import UseCount from '@components/UseCount'
export default {
  // data(){
  //   return { count:0 }
  // },
  // components:{
  //   'use-count':UseCount
  // }
}
</script>


<style>
.router-link{
  padding-right: 15px;
}
</style>
