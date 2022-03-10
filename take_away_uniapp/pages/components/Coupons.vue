<template>
	<view>
		<uni-card v-for="(coupon,index) in coupons" :key=index :title="coupon.title"  :extra="'起始日期:'+coupon.startTime+' 有效期:'+coupon.days+'天'" >
			<text style="color: #DD524D;">方式:{{coupon.type}}</text>
			<br>
			<text style="color: #DD524D;">优惠:{{coupon.type==='打折'?"x"+coupon.discount:"¥"+coupon.discount}}</text>
			<br>
			<text style="color: #DD524D;">范围:{{coupon.category}}</text>
			<br>
			<text style="color: #DD524D;">满减:¥{{coupon.least}}</text>
		</uni-card>
	</view>
</template>

<script>
	import {mapState,mapMutations} from 'vuex'
	import API from '../api.js'
	export default {
		data() {
			return {
				coupons:[]
			}
		},
		mounted() {
			uni.request({
				method:'GET',
				url:API+'/TaCouponByUserId', 
				data:{
					userId:uni.getStorageSync('userId')
				},
				success: (res) => {
					this.coupons=res.data
					console.log(res.data)
				}
			})
		},
		onShow() {
			
		}, 
	}
</script>

<style>

</style>
