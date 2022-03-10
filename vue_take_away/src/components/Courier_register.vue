<template>
    <el-form  label-width="80">
        <el-form-item label="真实姓名" >
            <el-input class="name_input" v-model="username">

            </el-input>
        </el-form-item>
        <el-form-item label="密码" >
                <el-input class="name_input" v-model="password" type="password">

                </el-input>
        </el-form-item>
        <el-form-item label="地址">
            <Address @send_address="get_address" class="name_input" />
        </el-form-item>
        <el-form-item label="电话号码">
            <el-input class="name_input" v-model="phone">

            </el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">注册</el-button>
        </el-form-item>
    </el-form>
</template>
<style lang="less" scoped>
.name_input{
    margin: 0 auto;
    width: 60%;
}
</style>
<script>
import {reactive,toRefs} from 'vue'
import Address from './Address.vue'
import BASE_URL from './api'
import axios from 'axios'
import {useRouter} from 'vue-router'
export default {
    setup() {
        const router=useRouter()
        const state=reactive({
                username:'',
                password:'',
                address:[],
                phone:''
        })
        const get_address=(e)=>{
            state.address=e
        }
        const onSubmit=()=>{
            let address_str=state.address[0].label+'/'+state.address[1].label+'/'+state.address[2].label
            axios({
                method:'POST',
                url:BASE_URL+'/registerCourier',
                data:{
                    username:state.username,
                    password:state.password,
                    location:address_str,
                    phone:state.phone
                }
            }).then((res)=>{
                console.log(res.data)
                router.push({path:'/Shop_login/courier'})
            })      
        }
        return{
            ...toRefs(state),
            get_address,
            onSubmit
        }
    },
    components:{
        Address
    }
}
</script>