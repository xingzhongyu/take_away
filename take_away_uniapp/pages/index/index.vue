<template>
<view>
	<!-- <text>首页</text> -->
	<shop style="shop" v-for="shop in shops" :title="shop.title" :score="shop.score" :dinerId="shop.dinerId" :description="shop.description"  :imgUrl="shop.imgUrl" :key=shop.dinerId :address="shop.address">
	</shop>
	<uni-pagination style="page" :total="total"  :current="current" @change="handlePage" :pageSize="pageSize"></uni-pagination>
</view>
</template>
<style lang="less" scoped>
.shop{
}

</style>
<script>
import { reactive,computed,toRefs,onMounted } from 'vue'
import { mapState,mapMutations,useStore } from 'vuex'
import Shop from '../components/Shop.vue'
import API from '../api.js'
export default{
    name:'Index',
	components:{
		Shop
	},
	onNavigationBarSearchInputChanged(e) {
		this.searchWord=e.text
	},
	 async onNavigationBarButtonTap() {
		let data=await this.getShops(this.searchWord)
		this.current=data.pageNum
		this.pageSize=data.pageSize
		this.total=data.total
		this.shops=data.list
	},
	methods:{
		getShops:function(word=null){
			console.log(this)
			let url=API+'/TaDinerPage' 
			if(word!==null){
				url=API+'/TaDinerPage?word='+word
			}
			return new Promise((resolve,reject)=>{
				uni.request({
					method:'GET',
					url:url,
					data:{
						pageNum:this.current,
						pageSize:this.pageSize
					},
					success: (res) => {
						resolve(res.data)
					}
				})
			})
		} 
	}, 
    setup() {
		let store=useStore() 
		onMounted( async ()=>{
			let data = await getShops()
			state.shops=data.list
			pageInfo.total=data.total
			pageInfo.current=data.pageNum
			pageInfo.pageSize=data.pageSize
			getMyLocation()
		})   
		const state=reactive({
			shops:[]
		})
		const pageInfo=reactive({
			pageSize:4,
			total:0,
			current:1
		})
		let positions={ 
                longitude: 0,//经度
                latitude:0,//纬度
                city:'',
				district:'',
				province:''
                }
		const getShops= (word=null)=>{
			let url=API+'/TaDinerPage' 
			if(word!==null){
				url=API+'/TaDinerPage?word='+word
			}
			return new Promise((resolve,reject)=>{
				uni.request({
					method:'GET',
					url:url,
					data:{
						pageNum:pageInfo.current,
						pageSize:pageInfo.pageSize
					},
					success: (res) => {
						resolve(res.data)
					}
				})
			})
		}
		const  getMyLocation=()=> {
			console.log('test')
		    var geolocation = new qq.maps.Geolocation("G4MBZ-IGILU-4IDVJ-2BHBM-BWBHJ-3XFWT", "myKey");
		    geolocation.getLocation(showPosition, showErr);
		}
		const showPosition=(position)=>{
			console.log(position)
		    positions.latitude = position.lat;
		    positions.longitude = position.lng;
		    positions.city = position.city;
			positions.district=position.district;
			positions.province=position.province;
			console.log(position.district)
			if(position.city!==undefined&&position.district!==undefined&&position.province!==undefined){
				let address=position.province+'/'+position.city+'/'+position.district
				store.commit('setAddress',address)
				sessionStorage.address=address
				let address2=position.province+'/'+position.city
				sessionStorage.address2=address2
 			}else{
				sessionStorage.address='山东省/济南市/历下区'
				sessionStorage.address2='山东省/济南市'
				// let address2=position.province+'/'+position.city
				// sessionStorage.address2=address2
			}
		}
		//定位失败再请求定位，测试使用
		const showErr=(err)=>{
			console.log(err)
		    console.log("定位失败，请重试！");
		    getMyLocation();
		}
		const handlePage=async (info)=>{
			if(info.type==='next'){
				pageInfo.current=pageInfo.current+1
			}else if(info.type==='prev'){
				pageInfo.current=pageInfo.current-1
			}
			let data = await getShops()
			state.shops=data.list
			pageInfo.total=data.total
			pageInfo.current=data.pageNum
			pageInfo.pageSize=data.pageSize
		}
		
        return{
            ...toRefs(state),
			...toRefs(pageInfo),
			handlePage
        }
    }
}
</script>
