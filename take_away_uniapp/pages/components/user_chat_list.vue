<template>
	<view>
		<!-- 聊天时间 -->
		<view v-if="shortTime"
		class="py2 flex align-center justify-center font-sm text-light-muted">
		{{shortTime}}
		</view>
		<!-- 消息气泡 -->
		<view class="p-2 flex align-start "
		:style="isSelf? 'flex-direction: row-reverse;':''">
			
			<image :src="item.avatar" 
			class="rounded-circle"
			mode=""
			style="height: 80rpx; width: 80rpx; "></image>
			<!-- max-width属性设置元素内容的最大宽度。
			注意： max-width属性不包括填充，边框，或页边距！ -->
			<view class="px-1 mx-1 mt-2 bg-light " style="max-width: 400rpx;">
				{{item.data}}
			</view>
			
		</view>
	</view>
</template>

<script>
	// 模拟当前登录用户的userid
	const uid = 1;
	import $T from '@/common/time.js';
	export default {
		props:{
			item:Object,
			index:Number,
			pretime:[Number,String]
		},
		data() {
			return {
				
			}
		},
		computed:{
			// 是否是登陆的本人
			isSelf(){
				return uid === this.item.user_id
			},
			// 转化时间,直接拿到计算好的是否超过了3分钟的人性化时间
			shortTime(){
				return $T.getChatTime(this.item.create_time,this.pretime)
			}
			
		},
		methods: {
			
		}
	}
</script>

<style>

</style>