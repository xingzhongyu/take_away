<template>
	<view class="card_shop">
   <uni-card   :isShadow="true" :title="title" subTitle="副标题" mode="basic" :extra="''+score" @click="clickCard">
   			<view>
   				<view >
   					<image mode="aspectFill" :src="imgUrl" class='img'/>
   				</view>
   				<view class="content-box"><text style="font-size: 12px;">{{description}}</text></view>
   			</view>
   </uni-card> 
   </view>
</template>
<style lang="less" scoped>
	.img{
		height: 200rpx;
		width: 200rpx;
	}
	.card_shop{
		display: inline-block;
		width: 50%; 
		height: 500rpx;
	}
</style>
<script>
import { toRefs } from 'vue'
export default{
    name:'Shop',
	props:{
		dinerId:Number,
		title:String,
		score:Number,
		imgUrl:String,
		description:String,
		address:String
	},
    setup(props) {
		const clickCard=()=>{
			uni.navigateTo({
				url:"/pages/components/Food?dinerId="+props.dinerId+'&address='+props.address
			})
		}
        return {
			...toRefs(props),
			clickCard
		}
    },
}
</script>
