<template>
	<view>
	<uni-card v-for="(food,index) in cart" :title="food.title"  :extra="'x'+food.num" :note="true" :key=index>
		 <text style="color:#DC143C;">总价：¥{{food.detail_price.toFixed(2)}}</text>
		 <template v-slot:footer>
			 <button type="warn" size="mini" @click="delete_cart(index)">删除</button>
			 <button type="default" size="mini" style="margin-left: 50rpx;" @click="onClick(food,index)">购买</button>
		 </template>
	</uni-card>
	<uni-goods-nav class="goods_nav" style="z-index: 1000;" :fill="true"  :options="options"
	 :buttonGroup="buttonGroup"  @click="onClick_nav" @buttonClick="buttonClick" />
	 </view>
</template>
<style lang="less" scoped>
	.goods_nav{
		position: fixed;
		bottom: 0;
		width: 100%;
	}
</style>
<script>
	import API from '../api.js'
import {mapState,mapMutations} from 'vuex'
export default{
    name:'Cart',
	data(){
		return{
			options:[{
				icon:'home',
				text:'首页'
			},{
				icon:'list',
				text:'订单'
			},{
				icon:'person',
				text:'我的'
			}],
			buttonGroup:[{
				text: '结算',
				backgroundColor: '#ff0000',
				color: '#fff'
			}]
		}
	},
	computed:mapState(['user','cart','address']),
	methods:{
		...mapMutations(['add_order','delete_cart','delete_all_cart']),
		async onClick(choose_food,index){
			// this.add_order(choose_food)
			let data=await this.add_order_async(choose_food)
			// console.log(data)
			this.delete_cart(index)
			uni.reLaunch({
				url:'./Orders'
			})
		}, 
		add_order_async(choose_food){
			let params={
					userId:uni.getStorageSync('userId'),
					foodId:choose_food.foodId,
					num:choose_food.num,
					address:this.address
				}
			return new Promise((resolve,reject)=>{
				uni.request({
					method:'POST',
					url:API+'/TaOrder',
					data:params, 
					success: (res) => {
						resolve(res.data)
						// console.log(res.data)
						// uni.reLaunch({
						// 	url:"./Orders"
						// })
					}
				})
			})
		},
		onClick_nav(e){
			switch(e.index){
				case 0:
				uni.switchTab({
				url:'../index/index'	
				})
				break;
				case 1:
				uni.switchTab({
					url:'./Orders'
				})
				break;
				case 2:
				uni.switchTab({
					url:'./Person'
				})
			}
		},
		async buttonClick(){
			for (let food of this.cart) {
				await this.add_order_async(food)
			}
			this.delete_all_cart()
			uni.reLaunch({
				url:'./Orders'
			})
		}
	},
	onShow() {
		if(uni.getStorageSync('userId')===undefined){
			uni.showModal({
				content:"是否登录？",
				 success: function (res) {
				        if (res.confirm) {
				            uni.redirectTo({
				            	url:"../login/login"
				            })
				        } else if (res.cancel) {
				           uni.switchTab({
				           	url:'../index/index'
				           })
				        }
				    }
			})
		}
	}
}
</script>
