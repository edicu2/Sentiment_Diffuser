<template>
  <div class="container" style="margin-top: 5%;">
    <div class="row">
      <div class="col-md-6 mt-5 mx-auto">
        <form v-on:submit.prevent="register">
          <h1 class="h3 mb-3 font-weight-normal">Register</h1>
          <div class="form-group">
            <label for="name">First name</label>
            <input type="text" v-model="first_name" class="form-control" name="first_name" placeholder="Enter your first name">
          </div>
          <div class="form-group">
            <label for="name">Last name</label>
            <input type="text" v-model="last_name" class="form-control" name="last_name" placeholder="Enter your lastname name">
          </div>
          <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" v-model="email" class="form-control" name="email" placeholder="Enter email">
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" v-model="password" class="form-control" name="password" placeholder="Password">
          </div>
          <div class="form-group">
            <label for="resident_number">Resident number</label>
            <input type="text" v-model="resident_number" class="form-control" name="resident_number" placeholder="Enter resident number">
          </div>
          <div class="form-group">
            <label for="nick">Nickname</label>
            <input type="text" v-model="nick" class="form-control" name="nick" placeholder="Enter nickname">
          </div>
          <div class="form-group">
            <label for="tel">Tel</label>
            <input type="text" v-model="tel" class="form-control" name="tel" placeholder="Enter telephone">
          </div>
          <div class="form-group">
            <label for="zipcode">Zipcode</label>
            <input type="text" v-model="zipcode" class="form-control" name="zipcode" placeholder="Enter zipcode">
          </div>
          <button type="submit" class="btn btn-lg btn-primary1 btn-block">Register!</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import router from '../router'
  export default {
    data () {
      return {
        first_name: '',
        last_name: '',
        email: '',
        password: '',
        resident_number: '',
        nick: '',
        tel: '',
        zipcode: ''
      }
    },
    methods: {
      register () {
        axios.post('http://arominds.com:8000/api/register',
          {
            name: this.first_name + ' ' + this.last_name,
            email: this.email,
            password: this.password,
            resident_number: this.resident_number,
            nick: this.nick,
            tel: this.tel,
            zipcode: this.zipcode
          }
        ).then((res) => {
          console.log(res)
          router.push({ name: 'Login' })
        }).catch((err) => {
          console.log(err)
        })
      }
    }
  }
</script>
