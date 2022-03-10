<template>
<div>
  <el-alert type="info" title="尚无评价" v-if="isAlert"></el-alert>
<el-card class="box-card" v-for="(comment,index) in comments" :key="index">
<template #header>
<div class="clearfix card-header">
    <span>{{comment.taUser.nickname}}</span>
        <span style="float: right; padding:3px" >{{comment.taComment.score.toFixed(2)}}分</span>
</div>
    </template>
<div  class="text item">
    评价内容:{{comment.taComment.content}}
</div>
<hr>
<div  class="text item food">
    所定食物:{{comment.taFood.title}}
</div>
</el-card>
</div>
</template>

<style>
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }
.food{
  color: gray;
}
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    float: left;
    width: 480px;
  }
</style>
<script>
import {onMounted,reactive,toRefs} from 'vue'
import axios from 'axios'
import BASE_URL from './api'
import { ElMessageBox } from 'element-plus'
export default {
    setup() {
        const state=reactive({
            comments:[],
            isAlert:false
        })
        onMounted(()=>{
            axios({
                method:'GET',
                url:BASE_URL+'/TaCommentByCourierId',
                params:{
                    courierId:localStorage.userId,
                }
            }).then((res)=>{
              console.log(res)
                if(res.data.length==0||res.status==400){
                  state.isAlert=true
                }else{
                  state.comments=res.data
                }
                // console.log(state.comments)
            })
        })
        return{
            ...toRefs(state),
            onMounted
        }
    },
}
</script>