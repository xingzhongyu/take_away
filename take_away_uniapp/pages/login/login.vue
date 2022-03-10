<template>
	<view>
<view>
  <image style="width: 100%;height: 100px;background: #fff;" src="/static/logo.png "  />
</view>
  <view>
	  <uni-forms>
		  <uni-forms-item label="用户名">
			  <uni-easyinput v-model="val1" placeholder="请输入用户名"></uni-easyinput>
		  </uni-forms-item>
		  <uni-forms-item label="密码" >
			  <uni-easyinput v-model="val2" placeholder="请输入密码" type="password"></uni-easyinput>
		  </uni-forms-item>
	  </uni-forms>
    <button @click="login" type="primary">登录</button>
    <text @click="toRegister" class="register">尚未注册？点击注册</text>
  </view>
  </view>
</template>
<style>
	.kaptcha{
		width: 200rpx;
		height: 100rpx;
	}
	.register{
		margin-left: 30%;
		color: #0000FF;
	}
</style> 
<script lang="ts">
import Cookies from 'js-cookie'
import { reactive, toRefs,getCurrentInstance } from 'vue';
import {useStore} from 'vuex';
import Taro from '@tarojs/taro'
import API from '../api.js'
export default {
  name: 'Login',
  components: {
    
  },
  setup(){
	  let { proxy } = getCurrentInstance();
	 let store=useStore()
    const state = reactive({
      val1:'',
      val2:'',
	  val3:null,
	  kaptcha:"http://123.56.43.101:8800/kaptcha/getKaptchaImage"
    });
    const toRegister= ()=>{
      uni.navigateTo({
        url:'../register/register'
      })
    }
    const login=()=>{
		let params={
			"nickname":state.val1,
			"password":state.val2,
			"code":state.val3
		}
		uni.request({
		    url: API+'/login', 
			method:"POST",
		    data:params,
		    success: (res) => {
		        if(res.statusCode===400){
					uni.showModal({
						content:"用户名或密码错误",
						showCancel:false,
						buttonText:'确定',
						success: (res) => {
							state.val1=''
							state.val2=''
						    if (res.confirm) {
						              console.log('用户点击确定');
						    } else if (res.cancel) {
						              console.log('用户点击取消');
						   }
						}
					})
				}else if(res.statusCode===200){
					store.commit('setUser',res.data)
					uni.setStorageSync('userId',res.data.userId)
					  uni.switchTab({
					    url:"../index/index" 
					  })
				}
		    },
			fail:(err)=>{
				console.log(err)
			}
		});
		
	
    }
	const updateKaptcha=()=>{
		let time=new Date().getTime()
		state.kaptcha="http://123.56.43.101:8800/kaptcha/getKaptchaImage?date="+time
	}
    return {
      ...toRefs(state),
      toRegister,
	  login,
	  updateKaptcha
    }
	}
}
</script>

<style lang="scss">
.index {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
</style>
