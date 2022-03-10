
<template>
<div>
<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
  <el-menu-item index="1">订单</el-menu-item>
  <el-menu-item index="2">查看对我的评价</el-menu-item >
  <el-menu-item index="3">返回首页</el-menu-item>
</el-menu>
<keep-alive>
<component :is="currentComponent"></component>
</keep-alive>
</div>
</template>

<script>
import Orders from './Orders'
import Courier_comment from './Courier_comment.vue'
  export default {
    components:{
      Courier_comment,
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
            return 'Courier_comment'
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
        }
      }
    }
  }
</script>

<style>

</style>
