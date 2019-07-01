
<template>
  <div v-if="direction==0&&image" class="col-lg-4 mb-2">
    <sequential-entrance fromRight tag="section" delay="0" >
        <img :src='getPic()' width="290px" height="220px" v-bind:alt="image"/>
      </sequential-entrance>
      <div id="font2" class="" align="center">
        <br>
        <h5 style="width:340px; height:10px; margin-bottom:50px">{{name}}</h5>
        <h4 style="margin-bottom:20px">{{price}}</h4>
        <router-link class="btn-view"
          :to = "'/products/' + code">詳しく
        </router-link>
      </div>
      <div class="" align="right" >
        <a href="#cardList" style="margin-top:5px; text-decoration:none; color:black; font-weight:bold" v-on:click="openNew()">+more</a>
      </div>
  </div>
  <div v-else-if="direction==1&&image" >
    <sequential-entrance fromLeft tag="section" delay="0" >
        <img :src='getPic()' width="290px" height="220px" v-bind:alt="image"/>
      </sequential-entrance>
      <div id="font2" class="" align="center">
        <br>
        <h5 style="width:340px; height:10px; margin-bottom:50px">{{name}}</h5>
        <h4>{{price}}</h4>
          <br>
          <router-link class="btn-view"
            :to = "'/products/' + code">詳しく
          </router-link>
      </div>
      <div class="" align="right" >
        <a href="#cardList" style="margin-top:5px; text-decoration:none; color:black; font-weight:bold" v-on:click="openNew()">+more</a>
      </div>
  </div>
</template>

<script>
import EventBus from './EventBus'
  export default {
    props: ['image','direction', 'price', 'name', 'code'],
    data () {
      return {
        img: ''
      }
    },
    mounted() {

    },
    created() {
      this.img =this.image;
    },
    methods: {
      getPic() {
        return this.img;
      }, //END GETpIC
      openNew(){

        EventBus.$emit('cardList', this.getPic())
        EventBus.$emit('imageClick_on', 'on')
      }//end openNew
    }//end methods
  }
</script>
<style>
.btn-view{
  background-color: #F87079;
  border:solid 2px #F87079;
  border-radius:10px;
  width: 75px;
  height: 45px;
  padding:10px;
  font-size:17px;
  margin:8px;
  color:white;
}

.btn-view:hover{
  background-color: white;
  border: #F87079 2px solid;
  color:#F87079;
  text-decoration: none;
}
</style>
