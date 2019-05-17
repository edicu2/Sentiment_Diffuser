<template>
  <div class="container" style="margin-top: 6%;">
    <h2 id="font" style="font-weight: normal;">My Emotion</h2>
    <hr>
    <br>
    <!-- <div class="jumbotron mt-5">
      <div class="col-sm-8 mx-auto">
        <h1 class="text-center">PROFILE</h1>
      </div>
      <table class="table col-md-6 mx-auto">
        <tbody>
          <tr>
            <td>Name</td>
            <td>{{whole_name}}</td>
          </tr>
          <tr>
            <td>Email</td>
            <td>{{email}}</td>
          </tr>
        </tbody>
      </table>
    </div> -->
    <emotion v-if="email" :email="email"></emotion>
  </div>
</template>

<script>
  import axios from 'axios'
  import Emotion from './my_page/Emotion.vue'

  export default {
    components: {
      'emotion': Emotion
    },

    data () {
      this.getUser().then(res => {
        //console.log(res)
        this.whole_name = res.user.name
        this.email = res.user.email
        this.id = res.user.id

        console.log(this.id)
        return res
      })
      return {
        whole_name: '',
        email: '',
        id: ''
      }
    },
    methods: {
      getUser () {
        return axios.get('http://arominds.com:8000/api/profile', {
          headers: { Authorization: `Bearer ${localStorage.usertoken}` }
        }
        ).then(res => {
          console.log(res.data)
          return res.data
        })
        .catch(err => {
          console.log(err)
        })
      }
    }
  }
</script>
