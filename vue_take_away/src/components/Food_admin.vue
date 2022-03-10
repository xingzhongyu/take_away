<template>
<el-tabs v-model="activeName" @tab-click="tabClick">
  <el-tab-pane v-for="(item,index) in shops" :key="index" :label="item.title+'('+item.address+')'" :name="''+item.dinerId">
      <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="title"
      label="名称"
      width="180">
    </el-table-column>
    <el-table-column prop="category" label="种类" width="180"></el-table-column>
    <el-table-column
      label="图片"
      width="180">
      <template #default="scope">
       <el-image :src="scope.row.imgUrl" class="img"></el-image>
      </template>
    </el-table-column>
    <el-table-column prop="description" label="描述" width="180"></el-table-column>
    <el-table-column prop="sales" label="销量" width="180"></el-table-column>
    <el-table-column prop="price" label="价格" width="180"></el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
   
    <el-table-column
      align="right">
      <template #header>
        <el-button type="success" @click="add_food">添加</el-button>
      </template>
    </el-table-column>
  </el-table>
  </el-tab-pane>
</el-tabs>
  <el-button @click="onCreateShop">创建商店</el-button>
   <el-dialog
    title="提示"
     v-model="dialog_visible"
    width="30%"
    >
    <el-form ref="choose_food" :model="choose_food" label-width="80px">
      <el-form-item label="商品名称">
        <el-input v-model="choose_food.title"></el-input>
      </el-form-item>
      <el-form-item label="商品种类">
        <el-select v-model="choose_food.category">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品描述">
        <el-input v-model="choose_food.description"></el-input>
      </el-form-item>
      <el-form-item label="商品图片">
        <el-upload list-type="picture" action="http://123.56.43.101:8800/upload" :on-success="handleSuccess">
          <el-button type="primary" size="mini">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input type="number" v-model="choose_food.price"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
      <el-button @click="onCancel">取 消</el-button>
      <el-button type="primary" @click="onConfirm">确 定</el-button>
    </span>
  </template>
</el-dialog>
</template>
<style lang="less" scoped>
.img{
  width: 160px;
  height: 160px;
}
</style>

<script>
import axios from 'axios'
import BASE_URL from './api'
import {mapState} from 'vuex'
  export default {
    data() {
      return {
        shops:[],
        options:[
          {
            value:'素菜',
            label:'素菜'
          },{
            value:'肉菜',
            label:'肉菜'
          },{
            value:'面食',
            label:'面食'
          }
        ],
        activeName:'',
        tableData: [],
        dialog_visible:false,
        choose_index:0,
        type:''
      }
    },
    mounted:function(){
      if(localStorage.userId!==undefined){
        axios({
        method:'GET',
        url:BASE_URL+'/getTaDiner',
        params:{
          userId:localStorage.userId
        }
      }).then((res)=>{
        this.shops=res.data
        if(this.shops!=[]){
            this.activeName=''+this.shops[0].dinerId
            axios({
            method:"GET",
            url:BASE_URL+'/TaFoodByDinerId',
            params:{
              dinerId:parseInt(this.activeName)
            }
          }).then((res2)=>{
            console.log(res2)
            this.tableData=res2.data   
           this.dialog_visible = false
            
          }).catch((err2)=>{
            console.log(err2)
          })
        }
      }).catch((err)=>{
        console.log(err)
      })
      }else{ 
        this.$alert('您尚未登录','提示',{
            confirmButtonText: '确定',
            callback:action=>{
            this.$router.push({path:'/Shop_login/shop'})
            }
        })
      }

      // if(this.activeName==='1'){
      //   this.tableData=[
      //     {
      //     name: '王小虎',
      //     description: '上海市普陀区金沙江路 1518 弄',
      //     img:'https://tse1-mm.cn.bing.net/th/id/OIP-C.XEnx5nt5boRpKuyLPOkrEAHaE8?pid=ImgDet&rs=1',
      //     sales:0,
      //     price:0
      //   }
      //   ]
      // }
    },
    computed:{
      ...mapState(['user']),
      choose_food:function(){
        return this.tableData[this.choose_index]
      }
    },
    methods: {
      handleEdit(index, row) {
        this.type='edit'
        this.set_food(index)
      },
      onCreateShop(){
        this.$router.push({path:'/Diner_register'})
      },
      handleDelete(index, row) {
        axios({
          method:'DELETE',
          url:"http://123.56.43.101:8800/TaFood",
          params:{
            foodId:row.foodId
          }
        }).then((res)=>{
          axios({
            method:"GET",
            url:BASE_URL+'/TaFoodByDinerId',
            params:{
              dinerId:parseInt(this.activeName)
            }
          }).then((res2)=>{
            console.log(res2)
            this.tableData=res2.data   
           this.dialog_visible = false
            
          }).catch((err2)=>{
            console.log(err2)
          })
        })
      },
      handleSuccess(res){
        this.choose_food.imgUrl=res.picture
      },
      add_food(){
        this.type='add'
        this.set_food(this.tableData.length)
      },
      set_food(index){
        this.choose_index=index
        this.dialog_visible=true
        if(this.choose_index==this.tableData.length){
            this.tableData.push({})
        }
      },
      tabClick(tab,event){
         axios({
            method:"GET",
            url:BASE_URL+'/TaFoodByDinerId',
            params:{
              dinerId:parseInt(this.activeName)
            }
          }).then((res)=>{
            this.tableData=res.data   
           this.dialog_visible = false
            
          }).catch((err)=>{
            console.log(err)
          })
      },
      onCancel(){
        if(this.type==='add'){
          this.tableData.pop()
        }
        this.dialog_visible=false
      },
      onConfirm(){
        console.log(this.choose_food)
        let url=BASE_URL
        let method='POST'
        let data={
            price:this.choose_food.price,
            title:this.choose_food.title,
            description:this.choose_food.description,
            imgUrl:this.choose_food.imgUrl,
            sales:this.choose_food.sales,
            category:this.choose_food.category,
            dinerId:parseInt(this.activeName)
          }
          console.log(data)
        if(this.type==='add'){
          url=url+'/insertTaFood'
        }else if(this.type==='edit'){
          url=url+'/TaFood'
          method='PUT'
          data.foodId=this.choose_food.foodId
        }
          axios({
          method:method,
          url:url,
          headers:{
            "Content-type":"application/json"
          },
          data:data
        }).then((res)=>{
          axios({
            method:"GET",
            url:BASE_URL+'/TaFoodByDinerId',
            params:{
              dinerId:parseInt(this.activeName)
            }
          }).then((res2)=>{
            console.log(res2)
            this.tableData=res2.data   
           this.dialog_visible = false
            
          }).catch((err2)=>{
            console.log(err2)
          })
        }).catch((err)=>{
          console.log(err)
        })
        // let temp=this.tableData[this.choose_index]
        // this.tableData[this.choose_index]={...this.choose_food,sales:temp.sales==null?0:temp.sales}
      }
    }
  }
</script>

<style>

</style>
