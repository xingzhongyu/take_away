<template>
<div>
    <el-table :data="table_data" style="width:100%">
        <el-table-column prop="taOrder.orderId" label="订单编号" width="180"></el-table-column>
        <el-table-column prop="taOrder.userId" label="用户编号" width="180"></el-table-column>
        <el-table-column prop="taFood.title" label="商品名称" width="180"></el-table-column>
        <el-table-column align="left">
            <template #header>
                <p>店铺名称</p>
                <el-select v-if="type==='shop'" placeholder="请选择" v-model="diner_name" @change="filterHandler">
                    <el-option v-for="(diner,index) in dinersFilter" :key="index"
                      :label="diner.label"
                    :value="diner.value">
                    </el-option>
                </el-select>
            </template>
            <template #default="scope">
                <p>{{scope.row.taDiner.title+'('+scope.row.taDiner.address+')'}}</p>
            </template>
        </el-table-column>
        <el-table-column prop="taOrder.courierId" label="快递员编号" width="180"></el-table-column>
        <el-table-column  label="订单状态" width="180">
            <template #default="scope">
                <p>{{get_order_status(scope.row.taOrder.status)}}</p>
            </template>
        </el-table-column>
        <el-table-column label="总价" width="60">
            <template #default="scope">
                {{scope.row.taOrder.num*scope.row.taFood.price}}
            </template>
        </el-table-column>
        <el-table-column prop="taOrder.finalTotalPrice" label="折后总价" width="60"></el-table-column>
        <el-table-column prop="taOrder.address" label="地址" width="180"></el-table-column>
        <el-table-column label="聊天">
            <template #default="scope">
                <el-button size="mini" type="primary" @click="open(scope.$index,scope.row)">聊天</el-button>
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
    <!-- <el-button type="primary" v-if="type==='admin'" @click="toAdmin">管理优惠券</el-button>
    <el-button type="primary" v-if="type==='admin'" @click="toAdminDiner">查看商店</el-button>
    <el-button type="primary" v-if="type==='shop'" @click="toFood">管理已上架商品</el-button>
    <el-button type="primary" v-if="type==='courier'" @click="toComment">查看对我的评价</el-button> -->
    <el-dialog title="聊天"  v-model="dialog_visible"  class="dialog" >
    <div class="chat-content">
     <!-- recordContent 聊天记录数组-->
     <div class="content">
     <div v-for="(chat,index) in chats" :key="index">
          <!-- 对方 -->
          <div class="word" v-if="chat.type!==type">
               <el-avatar>{{chat.type}}</el-avatar>
               <div class="info">
                    <p class="time">{{chat.type}}{{getId(chat.type)}}</p>
                    <div class="info-content">{{chat.content}}</div>
               </div>
          </div>
          <!-- 我的 -->
          <div class="word-my" v-else>
               <div class="info">
                    <p class="time">{{chat.type}}{{getId(chat.type)}}</p>
                    <div class="info-content">{{chat.content}}</div>
               </div>
              <el-avatar>{{chat.type}}</el-avatar> 
           </div>
      </div>
      </div>
      <el-input type="textarea" v-model="textarea2"></el-input>
      <el-button type="success" class="send" @click="add_chat">发送</el-button>
      <div style="clear:both;height:0"></div>
</div>
    </el-dialog>
    </div>
</template>
<style lang="less" scoped>
.send{
    float: right;
    margin-top: 20px;
}
.content{
    height: 600px;
    overflow: auto;
}
.chat-content{
  width: 100%;
  padding: 10px;
  .word{
     display: flex;
     margin-bottom: 20px;
     img{
       width: 40px;
       height: 40px;
       border-radius: 50%;
     }
     .info{
       margin-left: 10px;
       .time{
         font-size: 12px;
         color: rgba(51,51,51,0.8);
         margin: 0;
         height: 20px;
         line-height: 20px;
         margin-top: -5px;
       }
       .info-content{
          padding: 10px;
          font-size: 14px;
          background: #fff;
          position: relative;
          margin-top: 8px;
        }
        //小三角形
        .info-content::before{
            position: absolute;
            left: -8px;
            top: 8px;
            content: '';
            border-right: 10px solid #FFF;
            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
        }
     }
  }
 
.word-my{
    display: flex;
    justify-content:flex-end;
    margin-bottom: 20px;
    img{
       width: 40px;
       height: 40px;
       border-radius: 50%;
     }
     .info{
       width: 90%;
       margin-left: 10px;
       text-align: right;
       .time{
         font-size: 12px;
         color: rgba(51,51,51,0.8);
         margin: 0;
         height: 20px;
         line-height: 20px;
         margin-top: -5px;
         margin-right: 10px;
       }
       .info-content{
          max-width: 70%;
          padding: 10px;
          font-size: 14px;
          float: right;
          margin-right: 10px;
          position: relative;
          margin-top: 8px;
          background: #A3C3F6;
          text-align: left;
        }
        //小三角形
        .info-content::after{
            position: absolute;
            right: -8px;
            top: 8px;
            content: '';
            border-left: 10px solid #A3C3F6;
            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
        }
     }
  }
}

</style>
<script>
import {computed, reactive,toRefs,onMounted,onBeforeUnmount,nextTick} from 'vue'
import {useStore} from 'vuex'
import {useRouter} from 'vue-router'
import axios from 'axios'
import BASE_URL from './api'
import {sendWebsocket,closeWebsocket} from './websocket'
export default {
    setup() {
        const store=useStore()
        const router=useRouter()
        const username=computed(()=>store.state.username)
         const pageInfo=reactive({
            pageNum:1,
            pageSize:5,
            size: 0,
            startRow: 0,
            endRow: 0,
            total: 0,
            pages: 0,
        })
        
        onMounted( async ()=>{
            let data=await getOrders(localStorage.userId)
            state.table_data=data
            axios({
                method:'GET',
                url:BASE_URL+'/getTaDiner',
                params:{
                    userId:localStorage.userId
                }
            }).then((res)=>{
                state.diners=res.data
                state.dinersFilter=res.data.map((item)=>{
                    return{
                        label:item.title+'('+item.address+')',
                        value:item.dinerId
                    }
                })
                state.dinersFilter.push({
                    label:'全部',
                    value:null
                })
                
            })
        })
        onBeforeUnmount(()=>{
            closeWebsocket()
        })
        const getId=(chatType)=>{
            if(chatType==='user'){
                return state.choose_order.taOrder.userId
            }
            if(chatType==='courier'){
                return state.choose_order.taOrder.courierId
            }
            if(chatType==='shop'){
                return state.choose_order.taOrder.dinerId
            }
        }
         const wsMessage =(data)=> {
            state.chats.push(data)
            nextTick(()=>{
                let sco=document.getElementsByClassName('content')[0]
                sco.scrollTop=sco.scrollHeight
            })

      // 这里写拿到数据后的业务代码
        }
    // ws连接失败，组件要执行的代码
        const wsError= ()=> {
        // 比如取消页面的loading
        }
        const requestWs=(data)=>{
            closeWebsocket()
            sendWebsocket("ws://123.56.43.101:8800/chat",data,wsMessage,wsError)
        }

        const state=reactive({
            table_data:[],
            chats:[],
            choose_order:null,
            dialog_visible:false,
            textarea2:'',
            dinersFilter:[],
            diner_name:null,
            diners:[],
            type:sessionStorage.type
        })
        const getOrders= (id)=>{
            return new Promise((resolve,reject)=>{
                axios({
                    method:'GET',
                    url:BASE_URL+'/TaOrderPage',
                    params:{
                        id:id,
                        type:state.type,
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
                    resolve(res.data.list)
                })
            })
        }
        const get_chats=()=>{
            return new Promise((resolve,reject)=>{
                axios({
                    method:'GET',
                    url:BASE_URL+'/TaChat',
                    params:{
                        orderId:state.choose_order.taOrder.orderId
                    }
                }).then((res)=>{
                    resolve(res)
                })
            })
        }
       
        const get_order_status=(num)=>{
            if(num===0){
                return "未支付未评价"
            }else if(num===1){
                return "已支付未评价"
            }else if(num===2){
                return "已支付已评价"
            }else if(num===3){
                return "未支付已退订"
            }else if(num===4){
                return "已支付已退款"
            }
        }
        const open=async (index,row)=>{
            state.dialog_visible=true
            state.choose_order=row
            let res=await get_chats()
            // console.log(res.data)
            state.chats=res.data
            nextTick(()=>{
                let sco=document.getElementsByClassName('content')[0]
                sco.scrollTop=sco.scrollHeight
            })
        }
         const toAdminDiner=()=>{
            router.push({path:`/Admin_diner`,query:{pageNum:1}})
        }
        const toFood=()=>{
            router.push({path:'/Food_admin'})
        }
         const getDate=()=>{
            let aData=new Date()
            return aData.getFullYear() + "-" + (aData.getMonth() + 1) + "-" + aData.getDate();
        }
        const toAdmin=()=>{
            router.push({path:'/Admin_coupons'})
        }
        const toComment=()=>{
            router.push({path:'/Courier_comment'})
        }
        const filterHandler=async()=>{
            let data=null
            let diner_name=state.diner_name
           if(diner_name!==null){
               state.type="singleShop"//change
           data=await getOrders(diner_name)
           let choose_diner=state.diners.filter((diner)=>diner.dinerId===diner_name)[0]
           data=data.map((item)=>{
               item['taDiner']=choose_diner
               return item
           })
           }else{
                data=await getOrders(localStorage.userId)
           }
           state.type=sessionStorage.type
           state.table_data=data
           console.log(value,data)
        }
        const add_chat=()=>{
            let data={
                content:state.textarea2,
                time:new Date().getTime(),
                type:sessionStorage.type,
                orderId:state.choose_order.taOrder.orderId
            }
            requestWs(data)
            state.textarea2=""
        }
       
    const handleSizeChange=async (pageSize)=> {
        // console.log(`每页 ${val} 条`);
        pageInfo.pageSize = pageSize;
        state.table_data=await getOrders(localStorage.userId)
      }
      
      /**
       * 第n页
       * @param pageNum
       */
      const handleCurrentChange=async (pageNum)=>{
        // console.log(`当前页: ${val}`);
        pageInfo.pageNum = pageNum;
        state.table_data=await getOrders(localStorage.userId)
      }
       return{
            ...toRefs(state),
            ...toRefs(pageInfo),
            get_order_status,
            open,
            toFood,
            username,
            add_chat,
            toAdmin,
            toComment,
            getId,
            filterHandler,
            toAdminDiner,
            handleSizeChange,
            handleCurrentChange
        }
    },
}
</script>