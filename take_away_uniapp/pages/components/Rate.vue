<template>
	<view>
		<view class="top_ta">
		<uni-rate v-model="comment_shop.value"></uni-rate>
		<uni-easyinput  v-model="comment_shop.comment" placeholder="请输入对商店的评论" />
		</view>
		<view class="middle">
		<uni-rate v-model="comment_courier.value"></uni-rate>
		<uni-easyinput  v-model="comment_courier.comment" placeholder="请输入对送餐员的评论" />
		</view>
		<view  class="foot">
		<button type="primary" @click="submit">评价</button>
		</view>
	</view>
</template>
<style lang="less" scoped>
	.top_ta{
		margin-top: 60rpx;
		padding-top: 20rpx; 
		border-top: 1rpx solid #3B4144;
	}
	.middle{
		margin-top: 60rpx; 
		margin-bottom: 60rpx; 
		padding-top: 20rpx; 
		border-top: 1rpx 
		solid #3B4144;
	}
	.foot{
		margin: 0 auto;
		width: 60%;
	}
	
</style>
<script>
	import API from '../api.js'
	export default {
		data() {
			return {
				orderId:0,
				comment_shop:{
					value:0,
					comment:''
				},
				comment_courier:{
					value:0,
					comment:''
				}
			}
		},
		onLoad(option) {
			this.orderId=option.orderId
		},
		methods: {
			submit:function(){
				
				uni.request({
					method:'POST',
					url:API+'/TaComment',
					data:{
						orderId:this.orderId,
						score:this.comment_shop.value,
						content:this.comment_shop.comment,
						type:'shop'
					},
					success: (res) => {
						uni.request({
							method:'POST',
							url:API+'/TaComment',
							data:{
								orderId:this.orderId,
								score:this.comment_courier.value,
								content:this.comment_courier.comment,
								type:'courier'
							},
							success: (res) => {
								uni.reLaunch({
									url:'./Orders'
								})
							}
						})
					}
				})
				
				
			}
		}
	}
</script>

<style>

</style>
