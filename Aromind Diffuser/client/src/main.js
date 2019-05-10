// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
require('../node_modules/bootstrap/dist/css/bootstrap.css')
require('../node_modules/bootstrap-vue/dist/bootstrap-vue.css')
import BootstrapVue from 'bootstrap-vue'
import axios from 'axios'
import VeeValidate from 'vee-validate'
import Auth from './packages/auth/Auth.js'
import cors from 'cors'
import VueMqtt from 'vue-mqtt';
Vue.use(VueMqtt, 'ws://172.26.1.15:1884');

Vue.use(Auth)
Vue.use(BootstrapVue)
Vue.use(VeeValidate)
Vue.use(cors)

Vue.config.productionTip = false
const base = axios.create({
  //baseURL: 'http://localhost:8000'
  baseURL: 'http://arominds.com:8000'
})
Vue.prototype.$http = base;

//axios.defaults.headers.common['Authorization'] = 'Bearer ' + Vue.auth.getToken()

//axios.defaults.baseURL = 'http://arominds.com:8000'
//axios.defaults.headers['Authorization'] = 'Bearer ' + Vue.auth.getToken()

//Vue.config.productionTip = false

/* axios.defaults.headers.common = {
  'X-Requested-With': 'XMLHttpRequest',
  'X-CSRF-TOKEN': window.csrf_token
}; */

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
