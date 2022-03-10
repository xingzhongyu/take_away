import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import * as echarts from 'echarts'
import axios from 'axios'

axios.defaults.withCredentials=true
const app = createApp(App)
installElementPlus(app)
app.provide('echarts',echarts)
app.use(store).use(router).mount('#app')