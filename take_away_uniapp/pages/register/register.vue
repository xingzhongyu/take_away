<template>
	<view>
<view>
  <image style="width: 100%;height: 100px;background: #fff;" src="/static/logo.png"/>
</view>
  <view>
    <uni-forms>
		<uni-forms-item label="昵称">
			<uni-easyinput placeholder="请输入用户名" v-model="nickname"></uni-easyinput>
		</uni-forms-item>
		<uni-forms-item label="密码">
			<uni-easyinput placeholder="请输入密码" v-model="password" type="password"></uni-easyinput>
		</uni-forms-item>
		<uni-forms-item label="电话号码">
			<uni-easyinput placeholder="请输入电话号码" v-model="phone"></uni-easyinput>
		</uni-forms-item>
		<uni-forms-item label="生日">
			<uni-datetime-picker :value="birthday" type="date" @change="changeDate"/>
		</uni-forms-item>
		<uni-forms-item label="性别">
			<uni-data-checkbox v-model="gender" :localdata="range"></uni-data-checkbox>
		</uni-forms-item>
	</uni-forms>
	<button type="default" @click="toLogin">注册</button>
  </view>
  </view>
</template>

<script lang="ts">
import { reactive, toRefs,ref } from 'vue';
import {useStore} from 'vuex'
import API from '../api.js'
export default {
  name: 'Register',
  components: {
    
  },
  setup(){
	  const store=useStore()
    const user = reactive({
      nickname:'',
	  password:'',
	  birthday:'2021-5-3',
	  gender:0,
	  avatarUrl:'',
	  level:0,
	  phone:''
    });
	const changeDate=(e)=>{
		user.birthday=e
	}
	const range=reactive([
		{"value":0,"text":"男"},
		{"value":1,"text":"女"}
	])
	const toLogin=()=>{
		let params=user
		uni.request({
			url:API+'/register',
			data:params,
			method:"POST",
			success: (res) => {
				store.commit('setUser',user)
				uni.switchTab({
					url:"../index/index"
				})
			},
			fail: (err) => {
				console.log(err)
			}
		})
	}
    return {
      ...toRefs(user),
	  range,
	  toLogin,
	  changeDate
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
