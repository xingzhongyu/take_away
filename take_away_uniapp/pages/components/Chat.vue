<template>
	<view>
		<view class="tips color_fff size_12 align_c" :class="{ 'show':ajax.loading }" @tap="getHistoryMsg">{{ajax.loadText}}</view>
		<view class="box-1" id="list-box">
			<view class="talk-list">
				<view v-for="(item,index) in talkList" :key="index" :id="`msg-${item.id}`">
					<view class="item flex_col" :class=" item.type == 'user' ? 'push':'pull' ">
						<image :src="re_type(item.type)" mode="aspectFill" class="pic"></image>
						<view class="content">{{item.content}}</view>
					</view>
				</view>
			</view>
		</view>
		<view class="box-2">
			<view class="flex_col">
				<view class="flex_grow">
					<input type="text" class="content" v-model="content" placeholder="请输入聊天内容" placeholder-style="color:#DDD;" :cursor-spacing="6">
				</view>
				<button class="send" @tap="send">发送</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {mapState} from "vuex"
	import {inject} from 'vue'
	import API from '../api.js'
	export default {
		
		data() {
			return {
				talkList:[],
				ajax:{
					
				},
				orderId:0,
				content:'',
				ws:null
			}
		},
		
		onLoad(options) {
			this.orderId=options.orderId
			this.websocket.socketTask.onMessage((res)=>{
				this.talkList.push(JSON.parse(res.data))
				this.$nextTick(()=>{
					this.content=''
					uni.pageScrollTo({
						scrollTop:99999,
						duration:0
					})
				})
			})
		},
		async mounted() {
			this.talkList=await this.getHistoryMsg()
			this.$nextTick(()=>{
				uni.pageScrollTo({
					scrollTop:99999,
					duration:0
				})
			})
		},
		inject:['websocket'],
		computed:{
	
		},
		methods: {
			re_type:function(type){
				if(type==='user'){
					return '/static/a1.png'
				}else if(type==='shop'){
					return '/static/a2.png'
				}else if(type==='courier'){
					return '/static/a3.png'
				}else{
					return '?' 
				}
			},
			// 获取历史消息
			async getHistoryMsg(){
				return new Promise((resolve,reject)=>{
					uni.request({
						url:API+'/TaChat',
						data:{
							orderId:this.orderId
						},
						method:'GET',
						success: (res) => {
							resolve(res.data)
						}
					})
				})
			},
			// 拼接历史记录消息，正式项目可替换为请求历史记录接口
			joinHistoryMsg(){
				
			},
			
			// 发送信息
			async send(){
			
				let data = {
					"time":new Date().getTime(),
					"content":this.content,
					"type":'user',
					"orderId":this.orderId
				}
				this.content=''
				status=await this.websocket.send(data)
			}
		}
	}
</script>

<style lang="scss">
	@import "../../static/chat.scss";
	page{
		background-color: #F3F3F3;
		font-size: 28rpx;
	}
	
	/* 加载数据提示 */
	.tips{
		position: fixed;
		left: 0;
		top:var(--window-top);
		width: 100%;
		z-index: 9;
		background-color: rgba(0,0,0,0.15);
		height: 72rpx;
		line-height: 72rpx;
		transform:translateY(-80rpx);
		transition: transform 0.3s ease-in-out 0s;
		
		&.show{
			transform:translateY(0);
		}
	}
	
	.box-1{
		width: 100%;
		height: auto;
		padding-bottom: 100rpx;
		box-sizing: content-box;
		
		/* 兼容iPhoneX */
		margin-bottom: 0;  
		margin-bottom: constant(safe-area-inset-bottom);  
		margin-bottom: env(safe-area-inset-bottom);  
	}
	.box-2{
		position: fixed;
		left: 0;
		width: 100%;
		bottom: 0;
		height: auto;
		z-index: 2;
		border-top: #e5e5e5 solid 1px;
		box-sizing: content-box;
		background-color: #F3F3F3;
		
		/* 兼容iPhoneX */
		padding-bottom: 0;  
		padding-bottom: constant(safe-area-inset-bottom);  
		padding-bottom: env(safe-area-inset-bottom);  
		
		>view{
			padding: 0 20rpx;
			height: 100rpx;
		}
		
		.content{
			background-color: #fff;
			height: 64rpx;
			padding: 0 20rpx;
			border-radius: 32rpx;
			font-size: 28rpx;
		}
		
		.send{
			background-color: #42b983;
			color: #fff;
			height: 64rpx;
			margin-left: 20rpx;
			border-radius: 32rpx;
			padding: 0;
			width: 120rpx;
			line-height: 62rpx;
			
			&:active{
				background-color: #5fc496;
			}
		}
	}
	
	.talk-list{
		padding-bottom: 20rpx;
		
		/* 消息项，基础类 */
		.item{
			padding: 20rpx 20rpx 0 20rpx;
			align-items:flex-start;
			align-content:flex-start;
			color: #333;
			
			.pic{
				width: 92rpx;
				height: 92rpx;
				border-radius: 50%;
				border: #fff solid 1px;
			}
			
			.content{
				padding: 20rpx;
				border-radius: 4px;
				max-width: 500rpx;
				word-break: break-all;
				line-height: 52rpx;
				position: relative;
			}
			
			/* 收到的消息 */
			&.pull{
				.content{
					margin-left: 32rpx;
					background-color: #fff;
					
					&::after{
						content: '';
						display: block;
						width: 0;
						height: 0;
						border-top: 16rpx solid transparent;
						border-bottom: 16rpx solid transparent;
						border-right: 20rpx solid #fff;
						position: absolute;
						top: 30rpx;
						left: -18rpx;
					}
				}
			}
			
			/* 发出的消息 */
			&.push{
				/* 主轴为水平方向，起点在右端。使不修改DOM结构，也能改变元素排列顺序 */
				flex-direction: row-reverse;
				
				.content{
					margin-right: 32rpx;
					background-color: #a0e959;
					
					&::after{
						content: '';
						display: block;
						width: 0;
						height: 0;
						border-top: 16rpx solid transparent;
						border-bottom: 16rpx solid transparent;
						border-left: 20rpx solid #a0e959;
						position: absolute;
						top: 30rpx;
						right: -18rpx;
					}
				}
			}
		}
	}
</style>