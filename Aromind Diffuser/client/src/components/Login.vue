<template>
  <div class="container" style="margin-top: 5%; margin-bottom: 20%;">
    <h1>LOGIN</h1>
    <hr>
    <div class="row">
      <div class="col-md-5 mt-5 mx-auto">
        <form v-on:submit.prevent="login">
          <h1 class="h3 mb-3 font-weight-normal">Members login</h1>
          <div class="form-group" style="margin-top: 2%;">
            <!-- <label for="email">Email address</label> -->
            <input type="email" v-model="email" class="form-control" name="email" placeholder="Email">
          </div>
          <div class="form-group">
            <!-- <label for="password">Password</label> -->
            <input type="password" v-model="password" class="form-control" name="password" placeholder="Password">
          </div>
          <div class="form-group row mb-1">
            <div class="form-group form-check" style="margin-left: 3%;">
              <input type="checkbox" class="form-check-input" id="exampleCheck1">
              <label class="form-check-label" for="exampleCheck1">Remember ID</label>
            </div>
            <a href="#" class="find" style="margin-left: 50%;">Find ID/PW></a>
          </div>
          <button type="submit" class="btn btn-lg btn-primary1 btn-block">Sign in</button>
          <div style="margin-top: 7%;">
            <h5 style="margin-bottom: 3%;">SNS Login</h5>
            <div style="margin-left: 15%;">
              <a href="#" style="margin-right: 7%;"><img src="/static/img/navericon.PNG" alt=""></a>
              <a href="#" style="margin-right: 7%;"><img src="/static/img/kakaoicon.PNG" alt=""></a>
              <a href="#" style="margin-right: 7%;"><img src="/static/img/facebookicon.PNG" alt=""></a>
              <a href="#"><img src="/static/img/googleicon.PNG" alt=""></a>
            </div>
          </div>
        </form>
      </div>
      <div class="vl"></div>
      <div class="col-md-5 mt-5 mx-auto">
        <form action="">
          <h1 class="h3 mb-3 font-weight-normal">Non-members login</h1>
          <div class="form-group form-check row">
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked>
              <label class="form-check-label" for="inlineRadio1">Lookup Order</label>
            </div>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
              <label class="form-check-label" for="inlineRadio2">To Order</label>
            </div>
          </div>
          <div class="form-group">
            <!-- <label for="email">Email address</label> -->
            <input type="order" class="form-control" name="order" placeholder="Order Number">
          </div>
          <div class="form-group">
            <!-- <label for="password">Password</label> -->
            <input type="email" class="form-control" name="email" placeholder="Email">
          </div>
          <button type="submit" class="btn btn-lg btn-primary1 btn-block">Look up</button>
          <div class="form-group" style="margin-top: 10%;">
            <p>아직 아로마인드 회원이 아니세요?</p>
            <router-link to="/register" tag="button" class="btn btn-lg btn-outline-secondary1 btn-block">Register</router-link>
          </div>
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
        axios.post('http://arominds.com:8000/api/login',
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

<style>
  .find {
    color: black;
  }

  .find:visited {
    color: black;
    text-decoration: none;
  }

  .find:hover {
    color: rgb(234,119,142);
    text-decoration: none;
  }

  .vl {
    border-left: 0.5px solid #EAEAEA;
    height: 500px;
  }
</style>
