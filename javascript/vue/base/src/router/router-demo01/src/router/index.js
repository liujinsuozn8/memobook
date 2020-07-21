// 配置所有与路由相关的信息

import Vue from 'vue';
// 1. 导入路由
import VueRouter from 'vue-router';
/// 导入路由所需组件
import Home from '../views/Home.vue';
import About from '../views/About.vue';
import Work from '../views/Work.vue';
import Sleep from '../views/Sleep.vue';
import Food from '../views/Food.vue';
import UserList from '../views/child_router/UserList.vue';
import UserInfo from '../views/child_router/UserInfo.vue';
import UserBlank from '../views/child_router/UserBlank.vue';
import ParamBar from '../views/dynamic_router/ParamBar.vue';
import Guard01 from '../views/router_guard/Guard01.vue';
import Keep from '../views/keep_alive/Keep.vue';
import NoKeep from '../views/keep_alive/NoKeep.vue';
import NoKeep02 from '../views/keep_alive/NoKeep02.vue';
import KeepFirst from '../views/keep_alive/First.vue';
import KeepSecond from '../views/keep_alive/Second.vue';

// 配置懒加载路由
const Lazy = ()=> import(/* webpackChunkName: "lazy" */'../views/lazy/Lazy.vue');
const Profile = ()=> import(/* webpackChunkName: "profile" */'../views/router_query/Profile.vue');

// 2. 安装插件
Vue.use(VueRouter);

// 3. 创建路由实例
// 3.1 创建组件与url路径的映射关系
const routes = [
  // 一个映射关系就是一个对象
  {
    // path: '',
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
    meta:{
      title:'home'
    }
  },
  {
    path: '/about',
    component: About,
    meta:{
      title:'About Page'
    }
  },
  {
    path: '/work',
    component: Work,
    meta:{
      title:'Work Page'
    }
  },
  {
    path: '/sleep',
    component: Sleep,
    meta:{
      title:'Sleep Page'
    }
  },
  {
    path: '/food',
    component: Food,
    meta:{
      title:'Food Page'
    }
  },

  // 动态路由
  {
    path: '/parambar/:dydata',
    component: ParamBar,
    meta:{
      title:'ParamBar Page'
    }
  },

  // 懒加载路由
  {
    path: '/lazy',
    component: Lazy,
    meta:{
      title:'Lazy Page'
    }
  },

  // 嵌套路由
  {
    path: '/userlist',        // 配置父路由
    component: UserList,
    children:[                // 配置嵌套路由
      {
        path: ':userid',      // 配置动态子路由
        component: UserInfo,
        meta:{
          title:'SubUser Page'
        }
      },
      {
        path: 'user-blank',   // 配置静态子路由
        component: UserBlank
      },
      {
        path: '',             // 配置默认子路由
        redirect: 'user-blank'
      }
    ],
    meta:{
      title:'UserList Page'
    }
  },

  // 路由参数的传递方式: query
  // query 方式的参数传递，路由配置与普通的路由相同
  {
    path: '/profile',
    component: Profile,
    meta:{
      title:'Profile Page'
    }
  },

  // 设置路由独享守卫
  {
    path: '/guard01',
    component: Guard01,
    beforeEnter: (to, from, next) => {
      console.log('this is guard01');
      next();     // 需要手动调用next，跳转到下一个路由
    }
  },

  // 配置keep-alive页面路由
  {
    path: '/keep',
    component: Keep,
    children: [
      // 默认路由改为由 activated 方法来设定
      // {
      //   path: '',
      //   redirect: 'first'
      // },
      {
        path: 'first',
        component: KeepFirst
      },
      {
        path: 'second',
        component: KeepSecond
      },
    ]
  },
  { // 需要从 keep-alive 中排出的组件
    path: '/nokeep',
    component: NoKeep
  },
  { // 需要从 keep-alive 中排出的组件
    path: '/nokeep02',
    component: NoKeep02
  },
];
const router = new VueRouter({
  mode: 'history',  // 开启h5的history，默认使用hash
  routes,  // 设置路由映射
  linkActiveClass: 'activateState'  // 统一设置 router-link 的 active-class 属性
});


// 5. 配置全局路由守卫
// 5.1 前置路由守卫
router.beforeEach((to, from, next) =>{
  // 通过路由配置的 meta 来设置页面标题
  document.title = to.meta.title || document.title;
  // 在方法内部必须手动调用 next，否则路由不会跳转
  next();
});

// 5.1 后置路由守卫
router.afterEach((to, from)=>{
  // console.log('222222222')
});

// 3.3 将路由导出
export default router;

// 4. 将路由挂载到Vue实例
// 在main.js 中导入路由对象，并挂载