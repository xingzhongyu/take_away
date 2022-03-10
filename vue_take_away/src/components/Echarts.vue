<template>
<h2>商家评分</h2>
    <div id="container"></div>
</template>
<style lang="less" scoped>
#container{
    width: 500px;
    height: 300px;
    margin-left: 50%;
}
</style>
<script>
import { inject, onMounted } from '@vue/runtime-core'
import axios from 'axios'
import BASE_URL from './api'
export default{
    setup() {
        let echarts=inject('echarts')
        onMounted(()=>{
            echartsInit()
        })
        const getTaDiners=()=>{
            return new Promise((resolve,reject)=>{
                axios({
                method:'GET',
                url:BASE_URL+'/getTaDiner'
            }).then((res)=>{
                resolve(res.data)
            })
            })
        }
        const echartsInit=async ()=>{
            let data=await getTaDiners()
            let titles=data.map((item)=>item.title)
            let scores=data.map((item)=>item.score)
            console.log(titles,scores)
        echarts.init(document.getElementById('container')).setOption({
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: titles,
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '评分',
            type: 'bar',
            barWidth: '60%',
            data: scores
        }
    ]
})
        }
    },
}
</script>
