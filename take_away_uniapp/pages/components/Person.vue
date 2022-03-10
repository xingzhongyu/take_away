<template>
	<view>
    <uni-list>
    	<uni-list-item title="优惠券"  link="navigateTo"  to="./Coupons"></uni-list-item>
    </uni-list>
	<button type="default" @click="exit">退出登录</button>
	<text>默认地址</text>
	<test></test>
	</view>
</template>
<script>
import { mapState } from 'vuex'
import test from './test/test.vue'
import Address from './Address.vue'
export default{
    name:'Person',
    computed:mapState(['user']),
		
	components:{
		test,
		Address
	},
	methods:{
		exit:function(){
			uni.removeStorageSync('userId')
			uni.switchTab({
				url:'../index/index'
			})
		}
	},
onShow() {
	let userId=uni.getStorageSync('userId')
	if(userId===''){
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
