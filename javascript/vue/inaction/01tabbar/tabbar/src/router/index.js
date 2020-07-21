import Vue from 'vue'
import VueRouter from 'vue-router'

const Others = ()=>import(/* webpackChunkName: "others" */'../views/others/Others.vue')
const Browser = ()=>import(/* webpackChunkName: "browser" */'../views/browser/Browser.vue')
const Happy = ()=>import(/* webpackChunkName: "happy" */'../views/happy/Happy.vue')
const Facebook = ()=>import(/* webpackChunkName: "facebook" */'../views/facebook/Facebook.vue')

Vue.use(VueRouter)

const routes = [
  {
    path: "",
    redirect: "/facebook",
  },
  {
    path: "/others",
    component: Others
  },
  {
    path: "/browser",
    component: Browser
  },
  {
    path: "/happy",
    component: Happy
  },
  {
    path: "/facebook",
    component: Facebook
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
