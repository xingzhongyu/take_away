
// #ifndef VUE3
import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
import App from './App.vue'
import store from './store'
//引入websocket文件
import wsRequest from './pages/components/webscockerUtil'


export function createApp() {
	//开启websocket
let websocket = new wsRequest("ws://123.56.43.101:8800/chat",5000)
  const app = createSSRApp(App)
  app.use(store)
  // app.config.globalProperties.$axios=axios
  app.provide("websocket",websocket) 
  return {
    app
  }
}
// #endif