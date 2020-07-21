<template>
  <div id="app">
    <div id="nav">
      <!-- 1.1 设置路由的路径 -->
      <router-link to="/home">Home</router-link> |
      <router-link to="/about">About</router-link> |

      <!-- 2 router-link中的可用属性 -->
      <router-link to="/work" tag='button' replace active-class='myactive'>Work</router-link> |
      <router-link to="/sleep" tag='button' active-class='myactive'>Sleep</router-link> |

      <!-- 3. 手动执行路由跳转---1.在标签中通过 `v-on` 监听某个事件 -->
      <button @click="foodClick">Food</button> |

      <br>
      <!-- 3. 动态路由 -->
      <router-link to='/parambar/testdata'>ParamBar-testdata</router-link> |
      <router-link to='/parambar/12345'>ParamBar-12345</router-link> |

      <!-- 4. 路由的嵌套 -->
      <router-link to='/userlist' tag='button'>UserList</router-link> |

      <!-- 5. 懒加载路由 -->
      <router-link to='/lazy' tag='button'>Lazy</router-link> |

      <!-- 6. 设置url的query参数 -->
      <!-- <router-link to='/profile' tag='button'>Profile</router-link> | -->
      <router-link :to="{path:'/profile', query:{name:'testName', age:22}}" tag='button'>Profile</router-link> |

      <!-- 7. 路由独享守卫 -->
      <router-link to='/guard01' tag='button'>Guard01</router-link> |

      <!-- 8. keep-alive 测试 -->
      <router-link to='/keep' tag='button'>Keep</router-link> |
      <router-link to='/nokeep' tag='button'>NoKeep</router-link> |
      <router-link to='/nokeep02' tag='button'>NoKeep02</router-link> |

      <!-- 1.2 设置路由组件的显示位置 -->
      <!-- 设置 keep-alive。排除 `NoKeep,NoKeep02`，多个排除目标之间只能有 逗号，不能有空格  -->
      <keep-alive exclude='NoKeep,NoKeep02'>
        <router-view/>
      </keep-alive>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  methods:{
    // 3. 手动执行路由跳转---2.在Vue实例的 `methods` 属性中注册事件响应函数
    foodClick(){
      // 3. 手动执行路由跳转---3.在事件响应函数内部获取路由对象，并控制路由跳转
      this.$router.push('/food');     // 生成历史记录
      // this.$router.replace('/food');  // 不生成历史记录
    }
  }
}
</script>

<style>
/* 自定义 alive-class 的样式 */
.myactive{
  color:red;
  font-size: 20px;
}

/* 自定义 路由对象参数中的 alive-class 的样式 */
.activateState{
  color:#47e;
  font-size: 20px;
  font-weight: bold;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

/* #nav a {
  font-weight: bold;
  color: #2c3e50;
} */

/* #nav a.router-link-exact-active {
  color: #42b983;
} */
</style>
