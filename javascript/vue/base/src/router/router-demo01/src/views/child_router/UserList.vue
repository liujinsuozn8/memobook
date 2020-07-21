<template>
    <div class='userlist--border' >
        <p>userlist</p>
        <ul>
            <!-- 1. 使用 静态路由 -->
            <li>
              <router-link to='/userlist/user-bland'>userblank</router-link>
            </li>
            <!-- 2. 遍历数据并生成 动态路由 -->
            <li v-for='userid in userList' :key='userid'>
              <!-- 嵌套路由需要从父路径开始写 -->
              <router-link :to='"/userlist/" + userid'>{{userid}}</router-link>
            </li>
        </ul>
        <router-view/>
    </div>
</template>

<script>
export default {
  name: 'UserList',
  data(){
    return {
        // 用于设置动态路由的数据
        userList:['user001', 'user002', 'user003', 'user004'],
    };
  }
}
</script>

<style scoped>
ul{
  list-style: none;
  display:inline-block;
}

ul > li{
  float: left;
  margin: 0 30px;
  position: relative;
}
.userlist--border{
  border: red 2px solid;
}
</style>