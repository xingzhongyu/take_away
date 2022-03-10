<template>
	<view>
		<uni-card v-for="(comment,index) in comments"
		:extra="comment.taComment.score.toFixed(2)+'分'" :note="'商品:'+comment.taFood.title" 
		 :title="comment.taUser.nickname"
		  :key=index>
			<text>{{comment.taComment.content}}</text>
		</uni-card>
	</view>
</template>
 <style>
	 
 </style>
 <script>
	 import API from '../api'
export default {
		 data() {
		 		return {
		 			comments:[]
		 		}
		},
		 onLoad(options) {
		 	uni.request({
		 		url:API+'/TaCommentByDinerId',
				method:'GET',
				data:{
					dinerId:options.dinerId
				},
				success: (res) => {
					this.comments=res.data
					}
				})
		 			
		 		},
		 		methods: {
		 			 
		 		}
		 	}
 </script>

		


