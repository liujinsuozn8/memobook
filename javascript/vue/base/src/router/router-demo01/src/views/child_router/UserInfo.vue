<template>
  <div>
    <p>{{userid}} is show</p>
  </div>
</template>

<script>
export default {
  name: 'UserInfo',
  computed:{
    userid(){
      return this.$route.params.userid;
    }
  }

}
</script>