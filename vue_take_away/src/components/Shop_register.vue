
<template>
<el-form ref="form" :model="form" label-width="80px">
  <el-form-item label="个人姓名">
      <el-input v-model="form.person_name" class="name_input"></el-input>
  </el-form-item>
  <el-form-item label="电话号码">
      <el-input v-model="form.phone" class="name_input"></el-input>
  </el-form-item>
  <el-form-item label="密码">
    <el-input v-model="form.password" class="name_input" type="password"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">立即创建</el-button>
  </el-form-item>
</el-form>
</template>
<style lang="less" scoped>
.name_input{
    margin: 0 auto;
    width: 60%;
}
</style>
<script>
import Address from '../components/Address.vue'
import axios from 'axios'
import BASE_URL from './api'
import {mapMutations} from 'vuex'

  export default {
      components:{
          Address
      },
    data() {
      return {
        form: {
          person_name:'',
          phone:'',
          password:''
        }
      }
    },
    methods: {
      ...mapMutations(['set_user']),
      onSubmit() {
        axios({
          method:'POST',
          url:BASE_URL+'/register',
          headers:{
            "Content-type":"application/json"
          },
          data:JSON.stringify({
            nickname:this.form.person_name,
            phone:this.form.phone,
            password:this.form.password
          })
        }).then((res)=>{
          if(res.status===200){
            this.set_user(res.data)
            this.$router.push({path:'/Shop_login/shop'})
          }
        })
      }
    }
  }
</script>

<style>

</style>
