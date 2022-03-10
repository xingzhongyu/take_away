
<template>
<el-form ref="form" :model="form" label-width="80px">
  <el-form-item label="商店名称" >
    <el-input class="name_input" v-model="form.name"></el-input>
  </el-form-item>
  <el-form-item label="商店地址">
   <Address @send_address="get_address" class="name_input"/>
  </el-form-item>
  <el-form-item label="商店描述">
      <el-input class="name_input" v-model="form.description"></el-input>
  </el-form-item>
  <el-form-item label="商店图片">
      <el-upload list-type="picture" action="http://123.56.43.101:8800/upload" :on-success="handleSuccess">
          <el-button type="primary" size="mini">点击上传</el-button>
        </el-upload>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">立即创建</el-button>
    <el-button type="primary" @click="onCancel">取消</el-button>
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
import {mapState} from 'vuex'

  export default {
      components:{
          Address
      },
    data() {
      return {
        form: {
          name: '',
          address:[],
          description:'',
          imgUrl:''
        }
      }
    },
    computed:mapState(['user']),
    methods: {
      onCancel(){
        this.$router.go(-1)
      },
      onSubmit() {
        let address=this.form.address
        let address_str=address[0].label+'/'+address[1].label+'/'+address[2].label
        axios({
          method:'post',
          url:'http://123.56.43.101:8800/insertDiner',
          data:JSON.stringify({
            title:this.form.name,
            address:address_str,
            description:this.form.description,
            imgUrl:this.form.imgUrl,
            userId:localStorage.userId
          }),
          headers:{
            "Content-type":"application/json"
          }
        }).then((res)=>{
        this.$router.push({path:'/Food_admin'})
          console.log(res)
        }).catch((err)=>{
          console.log(err)
        })
      },
      handleSuccess(res,file,fileList){
        this.form.imgUrl=res.picture
      },
      get_address(e){
          this.form.address=e
      }
    }
  }
</script>

<style>

</style>
