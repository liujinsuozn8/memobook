<template>
  <div class="home">
    <button @click='sub'>-</button>
    <p>{{$store.state.counter}}</p>
    <button @click='add'>+</button>
    <p>home page</p>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods:{
    add(){
      this.$store.commit('increment');
    },
    sub(){
      this.$store.commit('decrement');
    }
  }
}
</script>
