module.exports={
    devServer: {
        proxy: {
            '/apis': {
                target: 'https://sm.ms/api/v2',//后端接口地址
                changeOrigin: true,//是否允许跨越
                pathRewrite:{
                    '^/apis':''
                }
            }
        }
    }
}