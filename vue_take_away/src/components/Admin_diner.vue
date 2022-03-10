<template>
<div>
    <el-table :data="tableData">
        <el-table-column label="名称">
            <template #default="scope">
                {{scope.row.title+'('+scope.row.address+')'}}
            </template>
        </el-table-column>
        <el-table-column prop="score" label="评分" width="180"></el-table-column>
        <el-table-column prop="description" label="描述" width="180"></el-table-column>
        <el-table-column prop="commentNum" label="评论数" width="180"></el-table-column>
        <el-table-column prop="address" label="地址" width="180"></el-table-column>
        <el-table-column label="图片">
            <template #default="scope">
                <el-image :src="scope.row.imgUrl" class="img"></el-image>
            </template>
        </el-table-column>
         
    </el-table>
   
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2,10, 50, 100, 500]"
        :page-size="pageSize"
        layout="prev, pager, next"
        :total="total">
      </el-pagination>
      <Echarts/>
    </div>
</template>
<style lang="less" scoped>
.img{
    width: 100px;
    height: 100px;
}
</style>
<script>
import axios from 'axios'
import {onMounted,reactive,toRefs,toRef} from 'vue'
import BASE_URL from './api'
import {useRoute} from 'vue-router'
import Echarts from './Echarts.vue'
export default {
  components: { Echarts },
    setup() {
        const state=reactive({
            tableData:[],
            
        })
        const pageInfo=reactive({
            pageNum:1,
            pageSize:2,
            size: 0,
          startRow: 0,
          endRow: 0,
          total: 0,
          pages: 0,
        })
        const route=useRoute()
        onMounted(()=>{
                getDiner()
        })
        const getDiner=()=>{
            axios({
                    method:'GET',
                    url:BASE_URL+'/TaDinerPage',
                    params:{
                        pageNum:pageInfo.pageNum,
                        pageSize:pageInfo.pageSize
                    }
                }).then((res)=>{
                    let data=res.data
                    pageInfo.pageNum=data.pageNum
                    pageInfo.pageSize=data.pageSize
                    pageInfo.pages=data.pages
                    pageInfo.startRow=data.startRow
                    pageInfo.endRow=data.endRow
                    pageInfo.total=data.total
                    state.tableData=data.list
                })
        }
        const handleSizeChange=(pageSize)=> {
        // console.log(`每页 ${val} 条`);
        pageInfo.pageSize = pageSize;
        getDiner()
      }
      
      /**
       * 第n页
       * @param pageNum
       */
      const handleCurrentChange=(pageNum)=>{
        // console.log(`当前页: ${val}`);
        pageInfo.pageNum = pageNum;
        getDiner()
      }
        return{
            ...toRefs(state),
            ...toRefs(pageInfo),
            handleSizeChange,
            handleCurrentChange
        }
    },
}
</script>