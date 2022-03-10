<template>
<h1>送餐系统管理</h1>
  <div class="home">
    <div class="button_group">
      <el-button  @click="to_shop_login('shop')" plain>
      店铺注册以及登录
    </el-button>
    <el-button  @click="to_shop_login('courier')" plain>
      外卖员注册以及登录
    </el-button>
    <el-button  @click="to_shop_login('admin')" plain>
      管理员登录
    </el-button>
    </div>
  </div>
</template>
<style scoped>
.home{
  position : absolute;
	width : 100%;
	height : 100%;
  background-image: url('https://img.zcool.cn/community/013d995e4d00f0a801216518f46226.jpg@1280w_1l_2o_100sh.jpg');
  background-size: cover;
}
.home::before{
   content: '';
  display: inline-block;
  height: 100%;
  vertical-align: middle;
  margin-right: -0.25em; /* Adjusts for spacing */
}
.button_group{
 display: inline-block;
  vertical-align: middle;
  width: 50%;
}
</style>
<script>
// @ is an alias to /src

export default {
  name: 'Home',
  components: {
    },
    methods:{
      to_shop_login:function(type){
        sessionStorage.type=type
        this.$router.push({path:`/Shop_login/${type}`})
      },
      to_courier_login:function(){
        this.$router.push({path:`/Shop_login`})
      },
      to_admin_login:function(){
        this.$router.push({path:`/Shop_login`})
      }
    }
}
</script>
