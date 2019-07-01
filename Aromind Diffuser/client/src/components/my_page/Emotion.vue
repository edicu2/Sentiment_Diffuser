<template>
<div>
  <h2 id="font" style="font-weight: normal;">My Emotion</h2>
    <hr>
<div class="back">
  <br>
    <div class="profile">
      <img src="static/img/background8.png" alt="" width="100%" height="170px" style="background">
      <!-- <div style="width:100%; height:150px; background-color:#EBF6FA"> -->
      <div>
        <div class="email_name">
          <div style="font-size:35px">GoldSangWon</div>
          <div>GoldSangWon@gmail.com</div>
        </div>
      </div>

      <img src="static/img/boss.png" alt="" weight="50px" height="50px" class="user">
    </div>

    <div>
      <div class="row">
        <div class="col-8">
        <div>
           <div class="btn-group mr-2" role="group" aria-label="First group">
           <button class="btn btn-active"
              @click="day"
              :class="{'btn': activeTab !== 1, 'is-active' : activeTab === 1}">
              <span>Day</span>
           </button>

           <button class="btn btn-active"
              @click="week"
              :class="{'btn': activeTab !== 2, 'is-active' : activeTab === 2}"
              style="border-left: solid 1px #F87079;">
              <span>Week</span>
            </button>

           <button class="btn btn-active"
              @click="month"
              :class="{'btn': activeTab !== 3, 'is-active' : activeTab === 3}"
              style="border-left: solid 1px #F87079;">
              <span>Month</span>
           </button>
           </div>
        </div>
        <br>
        <div class="chart">
          <line-chart :chart-data="data"
                      :height="150"
                      :options="{
                        responsive: true,
                        maintainAspectRatio: true,
                        scales:{
                          yAxes:[{
                              ticks:{
                                max:1,
                                min:-1,
                                stepSize:4,
                                display:false
                              }
                            }],
                          xAxes:[{
                            gridLines:{color: '#EEEEEE'}
                          }]
                        },
                        legend: {display: false}}">
          </line-chart>
        </div>
         <div style="position:absolute;">
          <p style="position:relative; top: -270px; left: -14px;">
           <img src="static/img/smaile.png" alt="" weight="30" height="30">
          </p>
        </div>

        <div style="position:absolute;">
          <p style="position:relative; top: -160px; left: -13px;">
           <img src="static/img/normal.png" alt="" weight="30" height="30">
          </p>
        </div>

        <div style="position:absolute;">
          <p style="position:relative; top: -55px; left: -12px;">
           <img src="static/img/sad.png" alt="" weight="30" height="30">
          </p>
        </div>

    <br>
    <div class="card text-center">
      <div class="bubble">
        <h2 id="feedback" class="font2"><q>&nbsp; {{this.feedback}}&nbsp; </q></h2>
      </div>
    </div>
   </div>

  <div class="col-4" style="position:relative;left:30px;">
    <div class="row">
      <div class="col-6" id="col">
        <img src="static/img/smile2.png" alt="" weight="45%" height="45%" class="emotion">
        <div id="font2" style="font-size:25px;margin-top:15px;">ポジティブ</div>
        <div class="percent">70%</div>
      </div>
      <div class="col-6" id="col">
        <img src="static/img/sad3.png" alt="" weight="45%" height="45%" class="emotion">
        <div id="font2" style="font-size:25px;margin-top:15px;">ネガチブ</div>
        <div class="percent">30%</div>
      </div>
    </div>
      <div class="grap">
        <canvas id="Chart2"></canvas>
      </div>
     </div>
   </div>
  </div>
    <!-- 메뉴 선택가능 -->
 <div>
    <hr>
    <br>
    <div class="row">
      <p id="triangle-right"></p>
      <h3 id="font2">GoldSangWon様と似た感情の人が買った商品</h3>
      <div class="select_box" id="font2">
         <b-form-select calss="mb-3" v-model="selected_gender" :options="gender" @change="select_eventbus">
          <template slot="first">
            <option :value="null" disabled>&nbsp;性別&nbsp;&nbsp;</option>
          </template>
         </b-form-select>
      </div>

      <div class="select_box" id="font2">
          <b-form-select calss="mb-3" v-model="selected_age" :options="age" @change="select_eventbus">
            <template slot="first">
              <option :value="null" disabled>&nbsp;年齢&nbsp;&nbsp;</option>
            </template>
         </b-form-select>
      </div>

      <div class="select_box" id="font2">
          <b-form-select calss="mb-3" v-model="selected_emotion" :options="emotion" @change="select_eventbus">
            <template slot="first">
              <option :value="null" disabled>&nbsp;感情&nbsp;&nbsp;</option>
            </template>
         </b-form-select>
      </div>
    </div>

    <br>
    <!-- 슬라이더 뷰 -->
    <div class="row" style="margin-left:10px height:300px width:300px">
      <emotionTestdata v-show="this.selected_emotion !== null || this.selected_age !== null || this.selected_gender !== null"></emotionTestdata>

        <div class="card text-center" v-show="this.selected_emotion == null && this.selected_age == null && this.selected_gender == null">
          <div class="row">
            <div class="col-lg-3 mb-2" v-for="(product, index) in productItems" @key="index" :key="index">
              <div class="card h-100" v-if="index < 4">
                <div class="card-body">

                  <div style="position:absolute;">
                    <p class="card-text" style="position: relative; top: -20px; left: 145px;">
                      <img :src="'static/img/'+index+'.png'" alt="" weight="110" height="110">
                    </p>
                  </div>
                </div>
                <router-link
                    :to = "'/products/' + product.product_code">
                  <img :src="product.product_img" alt="" weight="290" height="270">
                </router-link>
                <br>
                <h5 id="font2" class="card-title" v-html="product.product_name"></h5>
                <h4 id="font2" class="card-title" v-html="product.product_price+'円'"></h4>
                <div class="card-title" id="font2">
                  <br>
                  <router-link class="btn-view"
                    :to = "'/products/' + product.product_code">詳しく</router-link>
                    <!-- product.id -->
                </div>
              </div>
            </div>
          </div>
        </div>
      <!-- /.row -->
    </div>
   </div>
  </div>
  </div>
</template>

<script>
import LineChart from '@/components/chart/LineChart.js'
import PieChart from '@/components/chart/PieChart.js'
import BarChart from '@/components/chart/BarChart.js'
import _ from 'lodash';
import axios from 'axios'

import EventBus from '@/components/my_page2/EventBus'
import Emotion_Testdata from '@/components//my_page/Emotion_Testdata.vue'

export default {
  props:['email'],
    components:{
      LineChart,
      PieChart,
      BarChart,
      'emotionTestdata':Emotion_Testdata
    },
    data(){
        return {
            feedback: "",
            data:[],
            products:[],
            gender: [
              {text:'女性',value:'woman'},
              {text:'男性',value:'man'}
            ],

            age: [
              {text:'20代', value:'20'},
              {text:'30代', value:'30'},
              {text:'40代', value:'40'}
            ],//value에 값이 같은 경우 다른 것도 따라서 적용됨

            emotion: [
              {text:'悲しみ', value:'sad'},
              {text:'喜び', value:'happy'},
              {text:'普通', value:'normal'}
            ],
            selected_gender: null,
            selected_age: null,
            selected_emotion: null,
            activeTab: 0
        }
    },
    beforeMount(){//mounted()
        axios.get('http://arominds.com:8000/api/products')
            .then(response => {
                this.products = response.data
            })
            .catch(error => {
                console.error(error);
            })

      this.day()//기본세팅
     },
     mounted(){
        var ctx = document.getElementById("Chart2");
        var myChart = new Chart(ctx, {
          type: 'pie',

          data: {
              datasets: [{
                data: [30,70],
                backgroundColor: ['#EC6C5A', '#4BB3E3'],
                border:0
              }]
          },options: {responsive: true, maintainAspectRatio: false},
        });
     },
     computed:{
       productItems(){
         if(this.selected == null){
          //  this.axios.get('api/woman')
          //   .then(response =>{
          //     this.products = response.data
          //   })
           return _.orderBy(this.products, '')//빈 값으로 넣으면 for문에서 나오는 순서대로 나옴, 나중에 product테이블에 감정 수치별로 저장된 열 이름있으면 그냥 넣기만하면 됨
         }else if(this.selected == 'woman'){
           axios.get('http://arominds.com:8000/api/woman')
            .then(response =>{
              this.products = response.data
            })
            return this.products
            // return _.orderBy(this.products, '')
         }else if(this.selected == 'man'){
           axios.get('http://arominds.com:8000/api/man')
            .then(response =>{
              this.products = response.data
            })
            return this.products
          //  return _.orderBy(this.products, this.selected)
         }
       }
     },
    methods:{
        day(){
            axios.get('http://arominds.com:8000/api/day/' + this.email)
              .then(response=>{
                  this.data = response.data
                  //this.row = response.data.rows
                  //this.label = response.data.labels
                  this.activeTab = 1;
                  this.feedback = response.data.temp
                  console.log(this.data)
              }).catch((err)=> {
                console.log(err)
              })
        },
        week(){
            axios.get('http://arominds.com:8000/api/week/' + this.email)
              .then(response=>{
                  this.activeTab = 2;
                  this.data = response.data
                  this.feedback = response.data.temp
              }).catch((err)=> {
                console.log(err)
              })
        },
        month(){
            axios.get('http://arominds.com:8000/api/month/' + this.email)
              .then(response=>{
                  this.activeTab = 3;
                  this.data = response.data
                  this.feedback = response.data.temp
              })
        },
        year(){
           axios.get('http://arominds.com:8000/api/year/' + this.email)
              .then(response=>{
                  this.activeTab = 4;
                  this.data = response.data
                  this.feedback = response.data.temp
              })
         },
       select_eventbus(){
        this.$nextTick(function() {
          if(this.selected_emotion !== null){
            EventBus.$emit('select_emotion', this.selected_emotion)
          }else if(this.selected_age !== null){
            EventBus.$emit('select_age', this.selected_age)
          }else{
            EventBus.$emit('select_gender', this.selected_gender)
          }

        });
       }
    }
}
</script>

<style scoped>
.btn{
    background-color:white;
    border:solid 2px #F87079;
    /* border-radius:10px; */
    width: 80px;
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

.btn-view{
  background-color: #F87079;
  border:solid 2px #F87079;
  border-radius:10px;
  width: 115px;
  height: 50px;
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
/* select */
/* select option[selected]{
    background-color: #F87079;
} */

.card{
  border: solid 1px white;
}

#feedback{
  text-align:center;
  margin:40px;
}

h3{
  width: 66%;
  float: left;
  font-weight: bold;
}

#triangle-right {
  width: 0;
  height: 0;
  float: left;
  margin-right: 15px;
  border-top: 20px solid transparent;
  border-left: 30px solid #F87079;
  border-bottom: 20px solid transparent;
}

.select_box{
  float:right;
  margin: 5px;
}

.flex{
  width: 150px;
  margin: 5px;
}

/* 말풍선 */
.bubble
{
position: relative;
width: 750px;/*750px*/
height: 120px;
padding: 0px;
margin: 0 auto;
background:
linear-gradient(white, white) padding-box,
linear-gradient(to right, #FB9077, rgb(255, 60, 103)) border-box;
-webkit-border-radius: 31px;
-moz-border-radius: 31px;
border-radius: 31px;
border: transparent solid 3px;

}

.bubble:after
{
content: '';
position: absolute;
border-style: solid;
border-width: 0 15px 15px;
border-color: #FFFFFF transparent;
display: block;
width: 0;
z-index: 1;
top: -15px;
left: 360px;
}

.bubble:before
{
content: '';
position: absolute;
border-style: solid;
border-width: 0 17px 17px;
border-color: #FD636F transparent;
display: block;
width: 0;
z-index: 0;
top: -20px;
left: 358px;
}

.row{
  margin-left: 0px;
  margin-right: 0px;
}

.profile{
  /* width: 100%; */
  height: 210px;
  margin-bottom: 20px;
  margin-top: 20px;
}

.user{
  background-color: white;
  /* border: solid 5px lightgray; */
  border-radius: 50%;
  width: 130px;
  height: 130px;
  padding: 10px;
  position: relative;
  top: -230px;
  left: 130px;
}

.email_name{
  width: 400px;
  position: relative;
  left: 310px;
  top: -130px;
  color: white;
}

.grap{
  width: 280px;
  height: 250px;
  margin: 0 auto;
  /* margin-left: 58px; */
}

#col{
  border: 2px solid white;
  border-radius: 30px;
  text-align: center;
  background-color: #EBF6FA;
  height: 270px;
}

.emotion{
  margin-top: 10px;
}

.percent{
  font-size: 45px;
  margin:0px 0px 10px 5px;
}
.chart{
  margin-left: 20px;
}
</style>
