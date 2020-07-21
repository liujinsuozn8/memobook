// 配置所有与路由相关的信息

import Vue from 'vue';
// 1. 导入路由
import VueRouter from 'vue-router';
/// 导入路由所需组件
import Home from '../views/Home.vue';
import About from '../views/About.vue';

// 2. 安装插件
Vue.use(VueRouter);

// 3. 创建路由实例
// 3.1 创建多个Route，即多个组件与url路径的映射关系
const routes = [
    // 一个映射关系就是一个对象
    {
        path: '/home',
        component: Home
    },
    {
        path: '/about',
        component: About
    },
];
const router = new VueRouter({
    routes  // 设置路由映射
});

// 3.3 将路由导出
export default router;

// 4. 将路由挂载到Vue实例
// 在main.js 中导入路由对象，并挂载