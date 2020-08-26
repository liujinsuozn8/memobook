import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

  const routes = [
  // {
  //   path: '/',
  //   name: 'Home',
  //   component: Home
  // },
  {
    path:'/stateCount',
    name:'StateCount',
    component: () => import(/* webpackChunkName: "stateCount" */ '../views/stateCount/StateCount.vue'),
    children:[
      {
        path: '/',
        redirect: 'mutation',
      },
      {
        path: 'getter',
        name: 'CountGetter',
        component: () => import(/* webpackChunkName: "countGetter" */ '../views/stateCount/countGetter.vue')
      },
      {
        path: 'mutation',
        name: 'CountMutation',
        component: () => import(/* webpackChunkName: "countMutation" */ '../views/stateCount/countMutation.vue')
      },
    ]
  },
  {
    path:'/goodsList',
    name:'GoodsList',
    component: () => import(/* webpackChunkName: "goodsList" */ '../views/goodsList/GoodsList.vue'),
    children:[
      {
        path: '/',
        name: 'GoodsShow',
        component: () => import(/* webpackChunkName: "goodsShow" */ '../views/goodsList/GoodsShow.vue')
      },
      {
        path: 'show',
        name: 'GoodsShow',
        component: () => import(/* webpackChunkName: "goodsShow" */ '../views/goodsList/GoodsShow.vue')
      },
      {
        path: 'filter',
        name: 'GoodsFilter',
        component: () => import(/* webpackChunkName: "goodsFilter" */ '../views/goodsList/GoodsFilter.vue')
      },
    ]
  },
  {
    path:'/userInfo',
    name:'UserInfo',
    component: () => import(/* webpackChunkName: "userInfo" */ '../views/userinfo/UserInfo.vue'),
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
