
<template>
<div>
<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
  <el-menu-item index="1">订单</el-menu-item>
  <el-menu-item index="2">管理优惠券</el-menu-item >
  <el-menu-item index="3">查看商店</el-menu-item>
  <el-menu-item index="4">返回首页</el-menu-item>
</el-menu>
<keep-alive>
<component :is="currentComponent"></component>
</keep-alive>
</div>
</template>

<script>
import Orders from './Orders'
import Admin_coupons from './Admin_coupons'
import  Admin_diner from './Admin_diner'
  export default {
    components:{
      Admin_coupons,
      Admin_diner,
      Orders
    },
    data() {
      return {
        activeIndex: '1'
      };
    },
    computed:{
      currentComponent(){
        switch(this.activeIndex){
          case '1':
            return 'Orders'
          case '2':
            return 'Admin_coupons'
          case '3':
            return 'Admin_diner'
          default:
            console.log(this.activeIndex)
            break
        }
      }
    },

    methods: {
      handleSelect(key, keyPath) {
        this.activeIndex=key;

        console.log(this.currentComponent)
        if(this.activeIndex==='4'){
          this.$router.push({path:'/'})
        }
      }
    }
  }
</script>

<style>

</style>
