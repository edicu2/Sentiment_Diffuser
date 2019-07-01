<template>
  <div class="container" style="margin-top: 6%;">
    <div class="nav" style="display:inline-block; position:absolute; left: 140px; margin-top: 95px;">
      <nav>
        <ul class="list-group">
          <li class="list-group-item" style="border-color: #ea778e; border: 2px solid #ea778e; font-size: 20px;"
          @click="clickMenu_Emotion"
          :class="{'btn': activeTab !== 1, 'is-active' : activeTab === 1}"
           v-on:click="clickMenu_Emotion">My Emotion</li>

          <li class="list-group-item" style="border-color: #ea778e; border: 2px solid #ea778e; font-size: 20px;"
          @click="clickMenu_Status"
          :class="{'btn': activeTab !== 2, 'is-active' : activeTab === 2}"
           v-on:click="clickMenu_Status" >My Status</li>
        </ul>
      </nav>
    </div>
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
    <emotion v-if="email&&activeTab == 1" :email="email"></emotion>
    <testdata v-if="email&&activeTab == 2" :email="email"></testdata>

  </div>

</template>

<script>
  import axios from 'axios'
  import Emotion from './my_page/Emotion.vue'
  import Testdata from './my_page2/Testdata.vue'

  export default {
    components: {
      'emotion': Emotion,
      'testdata': Testdata
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
        id: '',
        checkMenu: 'My_Emotion',
        activeTab: 0
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
      },

      //옆 nav바 클릭했을떄 (KSW)
      clickMenu_Emotion(){
        this.checkMenu = 'My_Emotion';
        this.activeTab = 1;
      },
      clickMenu_Status(){
        this.checkMenu = 'My_Status';
        this.activeTab = 2;
      }
    },

    beforeMount(){
      this.clickMenu_Emotion()//기본셋팅
    }
  }
</script>


/* <style>

.list-group-item:hover{
    background-color:#F87079;
    /* border:solid 1px white; */
    color:white;
    /* border-radius:10px; */
    /* padding:10px; */
    font-size:20px;
    /* margin:5px; */
}
</style> */

<style scoped>
.btn{
    background-color:white;
    border:solid 2px #F87079;
    /* border-radius:10px; */
    font-size:20px;
    /* margin:5px; */
}

.is-active {
  background-color:#F87079;
  color:white;
}

.btn:hover{
    background-color:#F87079;
    /* border:solid 1px white; */
    color:white;
    /* border-radius:10px; */
    /* padding:10px; */
    font-size:20px;
    /* margin:5px; */
}
</style>
