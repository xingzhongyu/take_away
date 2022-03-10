<template>
	<view>
	 <view>
	        <button @click="showDrawer" type="primary">显示分类</button>
	        <uni-drawer ref="showRight" mode="right" :mask-click="false">
	            <scroll-view style="height: 100%;" scroll-y="true">
	                <button @click="closeDrawer" type="primary">关闭Drawer</button>
	                <button size="mini" v-for="(category,index) in categories" :key="index" @click="click_category(category.name)">{{category.name}}</button>
					<!-- <view v-for="item in 60">{{item}}</view> -->
	            </scroll-view>
	        </uni-drawer>
	</view>
	<view v-for="(food,index) in Foods" class="food" :key=index >
		<uni-card :animation="false" class="card show_card" :title="food.title" :extra="'销量:'+food.sales"  @click="showFood(food.foodId)" subTitle="副标题" mode="basic"   :class="{show_card_selected:food.isShadow}">
			<view>
				<view  >
					<image  class="img" :src="food.imgUrl" />
				</view>
			</view>
		</uni-card>
	</view>
	<uni-popup ref="food" type="center" >
		<uni-card  v-if="choose_food!=null" :animation="false" :title="choose_food.title" :extra="''+choose_food.sales" :isShadow="true"  subTitle="副标题" mode="basic" >
			<view>
				<view >
					<image mode="aspectFill" :src="choose_food.imgUrl"/>
				</view>
				<view class="content-box">
					<view>
						<uni-number-box  style="float: left;" v-model = "choose_value"></uni-number-box>
						<text  class="text">¥{{choose_food.price.toFixed(2)}}</text>
					</view>
					<view>
						<text style="font-size: 20rpx;">{{choose_food.description}}</text>
					</view>
				</view>
			</view>
		</uni-card> 
	</uni-popup>
	<uni-goods-nav class="goods_nav"  :fill="true"  :options="options" :buttonGroup="buttonGroup"  @click="onClick" @buttonClick="buttonClick" />
</view>
</template>
<style lang="less" scoped>
	// .popup{
	// 	z-index: 1000;
	// }
	.food{
		display: inline-block;
		width: 50%;
	}
	.text{
		font-size: 30rpx; 
		margin-left: 300rpx;
	}
	.img{
		height: 260rpx;
		width: 260rpx;
	}
	.goods_nav {
				position: fixed;
				display: flex;
	            bottom: 0;
	            width: 100%;
				z-index: 1000;
	}
	.card{
		display: inline-block;
	}
	.uni-border{
		// z-index: auto;
	}
	.show_card{
		height: 400rpx; 
		border: 1rpx solid #3B4144;
	}
	.show_card_selected{
		background-color: #dcdcdc;
	}
	
</style>
<script>
	import {mapState,mapMutations} from 'vuex'
	import {toRaw} from 'vue'
	import API from '../api.js'
export default {
    data () {
      return {
        options: [{
            icon: 'headphones',
            text: '客服'
        }, {
            icon: 'shop',
            text: '评价',
            infoBackgroundColor:'#007aff',
            infoColor:"red"
        }, {
            icon: 'cart',
            text: '购物车',
        }],
        buttonGroup: [{
          text: '加入购物车',
          backgroundColor: '#ff0000',
          color: '#fff'
        },
        {
          text: '立即购买',
          backgroundColor: '#ffa200',
          color: '#fff'
        }
        ],
		
		choose_value:1,
		shop:'',
		choose_food:null,
		shopAddress:'',
		Foods:[],
		choose_index:0, 
		categories:[
			{
				name:'素菜',
				description:'素菜'
			},
			{
				name:'肉菜',
				description:'肉菜'
			},
			{
				name:'面食',
				description:'面食'
			},{
				name:'全部',
				description:'全部'
			}
		]
      }
    },
	computed:{
		...mapState(['user','cart','address'])
	},
	onLoad(options) {
		this.shop=options.dinerId
		this.shopAddress=options.address
		uni.request({
			method:'GET',
			url:API+'/TaFoodByDinerId',
			data:{
				dinerId:this.shop
			},
			success: (res) => {
				this.Foods=res.data
			}
		})
	},
    methods: {
		...mapMutations(['add_order','add_cart','delete_all_cart']),
      onClick (e) {
		  
		  if(e.index==1){
			  uni.navigateTo({
			  	url:'./Show_comment?dinerId='+this.shop
			  })
		  }
       if(e.index==2){
		   uni.navigateTo({
		   	url:'./Cart'
		   })
	   }
      },
      buttonClick (e) {
		  const chooseFood=this.Foods.filter((food)=>food.foodId===this.choose_index)[0]
		  this.$refs['food'].close()
		  let cart_food={
			foodId:chooseFood.foodId,
		  	title:chooseFood.title,
		  	note:chooseFood.description,
		  	num:this.choose_value,
		  	shop:this.shop,
		  	detail_price:this.choose_value*chooseFood.price
		  } 
        if(e.index==0){
				this.add_cart(cart_food)
				uni.navigateTo({
					url:'./Cart'
				})
		}else if(e.index==1){
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
				
			}else{
			// this.add_order(cart_food)
			let arr=this.shopAddress.split('/');
			let shopAddress2=arr[0]+'/'+arr[1]
			console.log(shopAddress2)
			let params={
						userId:uni.getStorageSync('userId'),
						foodId:chooseFood.foodId,
						num:this.choose_value,
						address:sessionStorage.address
					}
				uni.request({
					method:'POST',
					url:API+'/TaOrder',
					data:params,
					success: (res) => {
						uni.reLaunch({
							url:"./Orders"
						})
					}
				})
			}
		}
      },
	
	  showFood(foodId){
		  this.choose_index=foodId
		  let chooseFood=this.Foods.filter((food)=>food.foodId===foodId)[0]
		  for(let food of this.Foods){
			  food.isShadow=false
		  }
		  chooseFood.isShadow=true
		  this.choose_food=chooseFood
		  this.$refs['food'].open()
	  },
	  showDrawer(){
	    this.$refs.showRight.open();
	   },
	  closeDrawer(){
	    this.$refs.showRight.close();
	   },
	   numberChange(value){
		   console.log(this.choose_value)
	   },
	   click_category(category){
		   let foods=[]
		   let url=API
		   let params={
			   dinerId:this.shop
		   }
		   if(category==='全部'){
			   url=url+'/TaFoodByDinerId'
			   
		   }else{
			   url=url+'/TaFoodByCategory'
				params.category=category
		   }
		   uni.request({
		   	method:'GET',
			url:url,
			data:params,
			success: (res) => {
				this.Foods=res.data
				this.closeDrawer()   
			}
		   })
	   }
    }
}
</script>
