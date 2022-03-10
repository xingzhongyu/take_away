
<template>
<div>
<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
  <el-menu-item index="1">订单</el-menu-item>
  <el-menu-item index="2">管理已上架商品</el-menu-item >
  <el-menu-item index="3">返回首页</el-menu-item>
</el-menu>
<keep-alive>
<component :is="currentComponent"></component>
</keep-alive>
</div>
</template>

<script>
import Orders from './Orders'
import Food_admin from './Food_admin.vue'
  export default {
    components:{
      Food_admin,
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
            return 'Food_admin'
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
        if(this.activeIndex==='3'){
          this.$router.push({path:'/'})
          localStorage.removeItem("userId")
        }
      }
    }
  }
</script>

<style>

</style>
