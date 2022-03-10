<template>
	<view>
		<!-- <uni-list>
			<uni-list-item class="item" link :to="order.shop" v-for="order in orders" :title="order.title" :note="order.total_price" :rightText="order.num">
			</uni-list-item>
		</uni-list> -->
			<uni-card  v-for="(order,index) in orders" :title="order.taFood.title" note="true" :extra="'x'+order.taOrder.num" :key=index>
			    <text style="color:#DC143C;">总价：
				<text style="text-decoration: line-through;">¥{{final(order.taOrder.num,order.taFood.price).toFixed(2)}}</text>
				<text>¥{{final(order.taOrder.finalTotalPrice,1.0).toFixed(2)}}</text>
				</text>
				<view>
					<Address :init="order.taOrder.address" :index="order.taOrder.orderId" @sendAddress='getAddress' :disabled="order.taOrder.status!==0"></Address>
				</view>
			    <template v-slot:footer>
			        <view class="footer-box">
						<uni-row>
							<uni-col>
						<button class="order_bu" :type="order.taOrder.status===0?'primary':'default'" size="mini" @click="pay(order.taOrder)">{{(order.taOrder.status>0)?'已':''}}支付</button>
			            <button class="order_bu" :type="(order.taOrder.status<2)?'primary':'default'" size="mini" @click="comment(order.taOrder)">{{order.taOrder.status>=2?'已':''}}评价</button> 
			            <button class="order_bu" type="primary" size="mini" @click="toShop(order.taFood.dinerId)">进店</button>
						<button class="order_bu" type="primary" size="mini"  @click="toChat(order.taOrder.orderId)">聊天</button>
						<button class="order_bu" :type="(order.taOrder.status<2)?'primary':'default'" size="mini"  @click="refund(order.taOrder)">{{order.taOrder.status>=3?'已':''}}退订</button>
						</uni-col>
						</uni-row>
			        </view>
			    </template>
			</uni-card>
			<uni-pagination style="page" :total="total"  :current="current" @change="handlePage" :pageSize="pageSize"></uni-pagination>
	</view>
</template>
<style lang="less">
	.order_bu{
		margin-left: 10rpx;
	}
</style>
<script>
	 
	import { mapState,mapMutations } from 'vuex'
	import API from '../api.js'
	import Address from './Address'
	export default {
		data(){
			return{
				orders:[],
				current:1,
				pageSize:3,
				total:0
				
			}
		}, 
		components:{
			Address
		},
		async mounted() {
			let data=await this.getOrders()
			this.current=data.pageNum
			this.pageSize=data.pageSize
			this.total=data.total
			this.orders=data.list
		}, 
		
		computed: {...mapState(['user']),
		},
		methods:{
		getAddress:function(address,id){
			const choose_order=this.orders.filter((order)=>order.taOrder.orderId===id)[0].taOrder;
			choose_order.address=address
			console.log(choose_order)
		},
		getOrders:function(){
				return new Promise((resolve,reject)=>{
					uni.request({
						method:'GET',
						url:API+'/TaOrderPage',
						data:{
							id:uni.getStorageSync('userId'),
							type:'user',
							pageNum:this.current,
							pageSize:this.pageSize
						},
						success: (res) => {
							resolve(res.data)
							// console.log(res.data)
						}
					})
				})
			},
			handlePage: async function(info){
				if(info.type==='next'){
					this.current=this.current+1
				}else if(info.type==='prev'){
					this.current=this.current-1
				}
				let data=await this.getOrders()
				this.current=data.pageNum
				this.pageSize=data.pageSize
				this.total=data.total
				this.orders=data.list
				
			},
		toShop:function(shop){
			uni.navigateTo({
				url:'/pages/components/Food?dinerId='+shop
			})
		},
		toChat(orderId){
			uni.navigateTo({
				url:'./Chat?orderId='+orderId
			})
		},
		final:function(num,price){
			return num*price
		},
		async pay(order){
			console.log(order)
			if(order.status===0){
				order.status=1
				uni.request({
					method:'PUT',
					url:API+'/TaOrder',
					data:order,
					success: async (res) => {
						let data=await this.getOrders()
						this.current=data.pageNum
						this.pageSize=data.pageSize
						this.total=data.total
						this.orders=data.list
						uni.showModal({
							showCancel:false,
							content:"调用支付接口"
						})
					}
				})
				
			}
		},
		refund:function(order){
			if(order.status===0){
				order.status=3
			}else if(order.status===1){
				order.status=4
			}else if(order.status===2){
				uni.showModal({
					showCancel:false,
					content:'已评价'
				})
				return;
			}
			uni.request({
				method:'PUT',
				url:API+'/TaOrder',
				data:order,
				success: async (res) => {
					let data=await this.getOrders()
					this.current=data.pageNum
					this.pageSize=data.pageSize
					this.total=data.total
					this.orders=data.list
					uni.showModal({
						showCancel:false,
						content:"已退款"
					})
				}
			})
		},
		comment:function(order){
			if(order.status===1){
				uni.navigateTo({
					url:'./Rate?orderId='+order.orderId
				})
			}else{
				let msg=''
				if(order.status===0){
					msg='尚未支付'
				}else if(order.status===2){
					msg='已评价'
				}
				uni.showModal({
					showCancel:false,
					content:msg
				})
			}
		}
		},
		onLoad() {
			
		},
		onShow() {
			if(uni.getStorageSync('userId')===''){
				uni.showModal({
					content:"是否登录？",
					 success: function (res) {
					        if (res.confirm) {
					            uni.redirectTo({
					            	url:"../login/login"
					            })
					        } else if (res.cancel) {
					            uni.switchTab({
					            	url:"../index/index"
					            })
					        }
					    }
				})
			}
	}
	}
</script>

<style>

</style>
