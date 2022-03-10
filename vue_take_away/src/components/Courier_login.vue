<template>
<div>
    <el-form ref="form" :model="form" label-width="80">
        <el-form-item label="真实姓名">
            <el-input class="name_input"></el-input>
        </el-form-item>
        <el-form-item label="密码">
            <el-input class="name_input" type="password"></el-input>
        </el-form-item>
    </el-form>

</div>
</template>
<style lang="less" scoped>
.name_input{
    margin: 0 auto;
    width: 60%;
}
</style>
<script>
export default {
    data(){
        return{

        }
    }
}
</script>