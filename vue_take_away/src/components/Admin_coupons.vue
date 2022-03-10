<template>
    <el-table :data="coupons" style="width:100%">
        <el-table-column prop="title" label="优惠券名称" width="180"></el-table-column>
        <el-table-column prop="days" label="有效期(天)" width="180"></el-table-column>
        <el-table-column prop="type" label="优惠方式" width="180"></el-table-column>
        <el-table-column label="优惠范围">
          <template #default="scope">
            {{scope.row.category==null?'全部':scope.row.category}}
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始日期" width="180"></el-table-column>
        <el-table-column prop="description" label="描述" width="180"></el-table-column>
        <el-table-column label="优惠(x表示打折)">
            <template #default="scope">
                <p>{{scope.row.type==='打折'?'x':''}}{{scope.row.discount}}</p>
            </template>
        </el-table-column>
        <el-table-column prop="least" label="满足金额(¥)"></el-table-column>
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
        <el-button type="success" @click="add_coupon">添加</el-button>
      </template>
    </el-table-column>
    </el-table>
     <el-dialog
    title="提示"
     v-model="dialog_visible"
    width="30%"
    :before-close="handleClose">
    <el-form label-width="80px" ref="choose_coupon" :model="choose_coupon">
        <el-form-item label="优惠券名称">
            <el-input v-model="choose_coupon.title"></el-input>
        </el-form-item>
        <el-form-item label="优惠券描述">
            <el-input v-model="choose_coupon.description"></el-input>
        </el-form-item>
        <el-form-item label="有效期">
            <el-input type="number" v-model="choose_coupon.days"></el-input>
        </el-form-item>
        <el-form-item label="优惠方式">
            <el-select v-model="choose_coupon.type">
                <el-option v-for="(item,index) in options" :key="index" :label="item.label" :value="item.value"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="优惠范围">
          <el-select v-model="choose_coupon.category">
            <el-option v-for="(item,index) in options2" :key="index" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="满足金额">
            <el-input v-model="choose_coupon.least" type="number"></el-input>
        </el-form-item>
         <el-form-item label="开始时间">
             <el-date-picker
           v-model="choose_coupon.startTime"
      type="datetime"
      placeholder="选择日期时间"
      value-format="YYYY-MM-DD HH:mm:ss"
      >
    </el-date-picker>
        </el-form-item>
        <el-form-item label="优惠(x表示打折)">
            {{choose_coupon.type==='打折'?'x':''}}<el-input v-model="choose_coupon.discount" style="width:60%"></el-input>
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
<script>
import axios from 'axios'
import BASE_URL from './api'
export default {
    data() {
        return{
            options:[{
                value:'满减',
                label:'满减'
            },{
                value:'打折',
                label:'打折'
            }],
             coupons:[
            ],
        options2:[
          {
            value:'素菜',
            label:'素菜'
          },{
            value:'肉菜',
            label:'肉菜'
          },{
            value:'面食',
            label:'面食'
          },{
            value:null,
            label:'全部'
          }
        ],
            dialog_visible:false,
            choose_index:0,
            type:''
        }
    },
    computed:{
        choose_coupon:function(){
            return this.coupons[this.choose_index]
        }
    },
    async mounted(){
      let coupons=await this.get_coupons()
      this.coupons=coupons
      console.log(this.coupons)
    },
    methods:{
        handleEdit(index, row) {
        this.type='edit'
        this.set_coupon(index)
      },
      async handleDelete(index, row) {
        axios({
          method:'DELETE',
          url:BASE_URL+'/TaCoupon',
          params:{
            couponId:row.couponId
          }
        }).then(async(res)=>{
          this.coupons=await this.get_coupons()
        })
      },
      get_coupons(){
        return new Promise((resolve,reject)=>{
          axios({
            method:'GET',
            url:BASE_URL+'/TaCoupon'
          }).then((res)=>{
            resolve(res.data)
          })
        })
      },
      add_coupon(){
        this.type='add'
        this.set_coupon(this.coupons.length)
      },
      set_coupon(index){
          // console.log(index)
        this.choose_index=index
        this.dialog_visible=true
        console.log(this.choose_index)
        console.log(this.coupons.length)
        if(this.choose_index===this.coupons.length){
            this.coupons.push({})
            console.log(this.coupons)
        }
      },
      onCancel(){
        this.dialog_visible=false
        console.log(this.coupons)
        if(this.type==='add'){
          this.coupons.pop()
        }
      },
      async onConfirm(){
        this.dialog_visible = false
        let temp=this.coupons[this.choose_index]
        let url=BASE_URL+'/TaCoupon'
        let method='POST'
        if(this.type==='edit'){
          method='PUT'
        }
        // console.log(temp)
        axios({
          method,
          url,
          data:temp
        }).then(async (res)=>{
          this.coupons=await this.get_coupons()
        })
      }
    }
}
</script>