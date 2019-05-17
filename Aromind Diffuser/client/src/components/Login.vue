<template>
  <div class="container" style="margin-top: 5%; margin-bottom: 20%;">
    <div class="row">
      <div class="col-md-6 mt-5 mx-auto">
        <form v-on:submit.prevent="login">
          <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
          <hr>
          <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" v-model="email" class="form-control" name="email" placeholder="Enter email">
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" v-model="password" class="form-control" name="password" placeholder="Password">
          </div>
          <button type="submit" class="btn btn-lg btn-primary1 btn-block">Sign in</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import router from '../router'
  import EventBus from './EventBus'

  export default {
    data () {
      return {
        email: '',
        password: ''
      }
    },
    methods: {
      login () {
        //axios.defaults.headers.common['Authorization'] = 'Bearer ' + Vue.auth.getToken()
        axios.post('http://localhost:8000/api/login',
          {
            email: this.email,
            password: this.password
          }
        ).then((res) => {
          localStorage.setItem('usertoken', res.data.token)
          this.email = ''
          this.password = ''
          //this.axios.defaults.headers.common['Authorization'] = 'Bearer ' + this.$auth.getToken()
          router.push({ name: 'Home' })
          console.log(res)
        }).catch((err) => {
          console.log(err)
          console.log(err.response)
        })
        this.emitMethod()
      },
      emitMethod () {
        EventBus.$emit('logged-in', 'loggedin')

      }
    }
  }
</script>
