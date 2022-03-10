<template>
  <el-button type="primary" @click="toHome" class="but">返回首页</el-button>
    <component :is="currentComponent"></component>
</template>
<style lang="less" scoped>
.but{
  margin-left: 90%;
}
</style>
<script>
import { computed } from '@vue/runtime-core'
export default {
    setup() {
        const currentComponent=computed(()=>{
            let component=sessionStorage.type
            return component.replace(component[0],component[0].toUpperCase())+'_Bar'
        })
        const router=useRouter()
        const toHome=()=>{
            router.push({path:'/'})
        }
        return{
            currentComponent,
            toHome
        }
    },
}
</script>