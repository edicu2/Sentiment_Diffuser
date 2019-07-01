import Vue from 'vue'
import App from './App.vue'
import Router from './routes.js'
import VueResource from 'vue-resource'
import VeeValidate from 'vee-validate'

import Auth from './packages/auth/Auth.js'

Vue.use(VueResource)
Vue.use(Auth)
Vue.use(VeeValidate)

Vue.http.options.root = 'http://localhost:8000'
Vue.http.interceptors.push((request, next) => {
  request.headers.set('Authorization', 'Bearer ' + Vue.auth.getToken())
  next()
})

Vue.http.interceptors.push((request, next) => {
  next(response => {
    if(response,status == 404) {
      swal(response.status.toString(), response.body.error, "error")
    } else if(response.status == 500) {
      swal(response.status.toString(), "We are experiencing a problem in our servers!", "error")
    }
  })
})

Router.beforeEach(
  (to, from, next) => {
    if(to.matched.some(record => record.meta.forVisitors)){
      if(Vue.auth.isAuthenticated()) {
        next({
          path: '/'
        })
      } else next()
    }
    
    else if(to.matched.some(record => record.meta.forAuth)){
      if(!Vue.auth.isAuthenticated()) {
        next({
          path: '/login'
        })
      } else next()
    } else next()
  }
)

new Vue({
  el: '#app',
  render: h => h(App),
  router: Router
})