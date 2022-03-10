<template>
    <el-form  label-width="80px"> 
        <el-form-item label="用户名">
            <el-input class="name_input" v-model="nickname" ></el-input>
        </el-form-item>
        <el-form-item label="密码">
            <el-input class="name_input" v-model="password" type="password" ></el-input>
        </el-form-item>
        <el-form-item label="验证码" v-if="type==='shop'">
            <el-input class="kaptcha" v-model="code"></el-input>
            <el-image :src="src" class="img"></el-image>
            <p @click="updateSrc">看不清楚，换一张</p>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">
                登录
            </el-button>
        </el-form-item>
    </el-form>
<div @click="toRegister" style="margin 0 auto;"  v-if="type!=='admin'">
    <p class="register">点击注册</p>
</div> 
</template>
<style lang="less" scoped>
.img{
    width: 10%;
    margin-left: 10px;
}
.name_input{
    margin: 0 auto;
    width: 30%;
}
.kaptcha{
    width: 15%;
}
.register{
    color: blue;
}
</style>
<script>
import {onMounted,toRefs,reactive, computed} from 'vue'
import {useStore} from 'vuex'
import  axios from 'axios'
import BASE_URL from './api'
import {useRoute,useRouter} from 'vue-router'
import { ElMessageBox } from 'element-plus';
export default {
   setup(){
        const router=useRouter()
        const store=useStore()
        let route=useRoute()
        const state=reactive({
            nickname:'',
            password:'',
            type:'',
            code:null,
            src:'http://123.56.43.101:8800/kaptcha/getKaptchaImage'
        })
        onMounted(()=>{
            state.type=route.params.type
            store.commit('set_type',state.type)
        })
        const toRegister=()=>{
            state.type=sessionStorage.type
            router.push({name:state.type+'_register'})
        }
        const updateSrc=()=>{
            let time=new Date().getTime()
            state.src='http://123.56.43.101:8800/kaptcha/getKaptchaImage?time='+time
        }
        const  onSubmit=()=>{
            let data={
                nickname:state.nickname,
			    password:state.password,
                type:sessionStorage.type
            }
            let url=BASE_URL+"/login"
            if(sessionStorage.type==="courier"){
                url=BASE_URL+"/loginCourier"
            }else{
                data['code']=state.code
            }
                
		 let headers={
		    "Content-Type":"application/json"
        }
        const currentComponent=computed(()=>{
            let component=sessionStorage.type
            return '/'+component.replace(component[0],component[0].toUpperCase())+'_bar'
        })
        axios({
            method:'POST',
            url:url,
            data:JSON.stringify(data),
            headers
           })
           .then((res)=>{
               if(res.status===200){
                    if(sessionStorage.type==='courier'){
                        store.commit('set_user',res.data)
                        localStorage.userId=res.data.courierId
                    }else{
                        store.commit('set_user',res.data)
                    localStorage.userId=res.data.userId
                    }
                    router.push({path:currentComponent.value})
               }
            }).catch((err)=>{
                console.log(err)
                if(err.response.status===400){
                    ElMessageBox.alert(
                        '用户名或密码错误','提示',{
                            confirmButtonText:'确定'
                        }
                    )
                }
            })
        }
        return{
            toRegister,
            onSubmit,
            ...toRefs(state),
            updateSrc

        }
   },
}
</script>
