import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import axios from "axios";      //引入axios
import * as echarts from "echarts";

import VueSession from 'vue-session'  //引入session
Vue.use(VueSession)  

axios.defaults.baseURL = 'https://5164qd7666.oicp.vip/';
Vue.prototype.$axios = axios;


import 'element-ui/lib/theme-chalk/index.css';
Vue.prototype.$echarts = echarts;
Vue.config.productionTip = false
Vue.use(ElementUI);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
