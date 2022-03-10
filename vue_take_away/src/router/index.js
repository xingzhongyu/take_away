import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path:'/Shop_login/:type',
    name:'shop_login',
    component:()=>import('../components/Shop_login')
  },{
    path:'/Courier_login',
    name:'courier_login',
    component:()=>import('../components/Courier_login')
  },{
    path:'/Admin_login',
    name:'admin_login',
    component:()=>import('../components/Admin_login')
  },{
    path:'/Shop_register',
    name:'shop_register',
    component:()=>import('../components/Shop_register')
  },{
    path:'/Courier_register',
    name:'courier_register',
    component:()=>import('../components/Courier_register')
  },{
    path:'/Admin_register',
    name:'admin_register',
    component:()=>import('../components/Admin_register')
  },{
    path:'/Food_admin',
    name:'food_admin',
    component:()=>import('../components/Food_admin')
  },{
    path:'/Orders',
    name:'orders',
    component:()=>import('../components/Orders')
  },{
    path:'/Admin_coupons',
    name:'admin_coupons',
    component:()=>import('../components/Admin_coupons')
  },{
    path:'/Diner_register',
    name:'diner_register',
    component:()=>import('../components/Diner_register')
  },{
    path:'/Courier_comment',
    name:'courier_comment',
    component:()=>import('../components/Courier_comment')
  },{
    path:'/Admin_diner',
    name:'admin_diner',
    component:()=>import('../components/Admin_diner')
  },{
    path:'/Admin_bar',
    name:'admin_bar',
    component:()=>import('../components/Admin_bar')
  },{
    path:'/Shop_bar',
    name:'shop_bar',
    component:()=>import('../components/Shop_bar')
  },{
    path:'/Courier_bar',
    name:'courier_bar',
    component:()=>import('../components/Courier_bar')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
