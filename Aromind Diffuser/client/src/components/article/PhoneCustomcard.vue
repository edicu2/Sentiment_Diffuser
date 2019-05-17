<template>
<div class="container">
    <br>
    <div id="demo" class="carousel slide" data-ride="carousel">

  <!-- Indicators -->
  <ul class="carousel-indicators">
    <!-- <li data-target="#demo" data-slide-to="0" class="active"></li> -->
    <span v-for="tep in temp_return">
      <li data-target="#demo" :data-slide-to="tep" id="slide" :class="{'active': customs == tep}"></li>
      <!-- <li data-target="#demo" :data-slide-to="tep"></li> -->
    </span>
  </ul>

  <!-- The slideshow -->
  <div class="container carousel-inner no-padding">

    <div class="carousel-item active" id="test">
      <div class="col-xs-3 col-sm-3 col-md-3" v-for="(phoneCustom, index) in phoneCustoms" v-if="index < customs*3 && index >= (customs-1)*3">
            <div class="pdf-thumb-box">
              <div class="card-title">
                <div class="circle_test" style="width:300px;text-align:center;font-size:20px;">{{phoneCustom.customcard_name}}</div>
              </div>
                    <div :class="{'pdf-thumb-box-overlay': checked,
                                'is-active' : checked[0] === phoneCustom}">
                    <span class="fa-stack fa-lg">
                      <div>
                          <input type="checkbox" :value="phoneCustom" v-model="checked"
                              @click="color_product($event, phoneCustom.product_color, phoneCustom.product_code)" :id="'boxs'+index">
                          <label :for="'boxs'+index">
                            <i class="fa fa-check" aria-hidden="true"></i>
                          </label>
                      </div>
                    </span>
                    </div>

            <div class="card-body">
            <div style="position:absolute;">
              <span style="position:relative; top: 2px; left: -1px;">
                 <div id="doughnut1" :style="'background: conic-gradient('+rgb[index]+')'"></div>
              </span>
            </div>

            <div class="pie_layout">
              <pie-chart
                  :chart-data="{
                      datasets: [
                        {
                          backgroundColor: [phoneCustom.positive_color_name, phoneCustom.normal_color_name, phoneCustom.nagative_color_name],
                          data: [phoneCustom.positive_strength, phoneCustom.normal_strength, phoneCustom.nagative_strength],
                          borderWidth:0
                        }
                      ]
                    }"
                  :height="320"
                  :options="{responsive: true, maintainAspectRatio: true}">
             </pie-chart>
             </div>

             <div style="margin:50px 0px 10px 55px;float:left;width:220px;height:118px">
              <div class="card-title" v-if="phoneCustom.positive_strength !== 0">
                <div class="circle" :style="'background-color:'+phoneCustom.positive_color_name"></div>
                <div class="circle_test">{{phoneCustom.positive_perfume_name}}</div>
              </div>
              <div class="card-title" v-if="phoneCustom.normal_strength !== 0">
                <div class="circle" :style="'background-color:'+phoneCustom.normal_color_name"></div>
                <div class="circle_test">{{phoneCustom.normal_perfume_name}}</div>
              </div>
              <div class="card-title" v-if="phoneCustom.nagative_strength !== 0">
                <div class="circle" :style="'background-color:'+phoneCustom.nagative_color_name"></div>
                <div class="circle_test">{{phoneCustom.nagative_perfume_name}}</div>
              </div>
            </div>
            <div class="card-title">
             <img src="static/img/light-bulb.png" alt="" style="width:30px; float:left;">
             <div class="progress" style="width: 80%;margin-top: 7px;font-size: 15px;height: 20px;border-radius: 10px;float: left;margin-left:31px;">
              <div class="progress-bar" role="progressbar" :aria-valuenow="Math.ceil(phoneCustom.bright/255*100)" aria-valuemin="0" aria-valuemax="100" :style="'width:'+Math.ceil(phoneCustom.bright/255*100)+'%; background-color:#00D8FF'">
                {{Math.ceil(phoneCustom.bright/255*100)}}%
              </div>
             </div>
            </div>

            </div>

          </div>
          <div class="vertical-social-box"></div>
      </div>

    </div>

    <!-- 2 -->
    <div class="carousel-item" id="test">
      <div class="col-xs-3 col-sm-3 col-md-3" v-for="(phoneCustom, index) in phoneCustoms" v-if="index < customs*3 && index >= (customs-1)*3">
            <div class="pdf-thumb-box">
              <div class="card-title">
                <div class="circle_test" style="width:300px;text-align:center;font-size:20px;">{{phoneCustom.customcard_name}}</div>
              </div>
                    <div :class="{'pdf-thumb-box-overlay': checked,
                                'is-active' : checked[0] === phoneCustom}">
                    <span class="fa-stack fa-lg">
                      <div>
                          <input type="checkbox" :value="phoneCustom" v-model="checked"
                              @click="color_product($event, phoneCustom.product_color, phoneCustom.product_code)" :id="'boxs'+index">
                          <label :for="'boxs'+index">
                            <i class="fa fa-check" aria-hidden="true"></i>
                          </label>
                      </div>
                    </span>
                    </div>

            <div class="card-body">
            <div style="position:absolute;">
              <span style="position:relative; top: 2px; left: -2px;">
                 <div id="doughnut1" :style="'background: conic-gradient('+rgb[index]+')'"></div>
              </span>
            </div>

            <div class="pie_layout">
              <pie-chart
                  :chart-data="{
                      datasets: [
                        {
                          backgroundColor: [phoneCustom.positive_color_name, phoneCustom.normal_color_name, phoneCustom.nagative_color_name],
                          data: [phoneCustom.positive_strength, phoneCustom.normal_strength, phoneCustom.nagative_strength],
                          borderWidth:0
                        }
                      ]
                    }"
                  :height="320"
                  :options="{responsive: true, maintainAspectRatio: true}">
             </pie-chart>
             </div>

             <div style="margin:50px 0px 10px 55px;float:left;width:220px;height:118px">
              <div class="card-title" v-if="phoneCustom.positive_strength !== 0">
                <div class="circle" :style="'background-color:'+phoneCustom.positive_color_name"></div>
                <div class="circle_test">{{phoneCustom.positive_perfume_name}}</div>
              </div>
              <div class="card-title" v-if="phoneCustom.normal_strength !== 0">
                <div class="circle" :style="'background-color:'+phoneCustom.normal_color_name"></div>
                <div class="circle_test">{{phoneCustom.normal_perfume_name}}</div>
              </div>
              <div class="card-title" v-if="phoneCustom.nagative_strength !== 0">
                <div class="circle" :style="'background-color:'+phoneCustom.nagative_color_name"></div>
                <div class="circle_test">{{phoneCustom.nagative_perfume_name}}</div>
              </div>
            </div>
            <div class="card-title">
             <img src="static/img/light-bulb.png" alt="" style="width:30px; float:left;">
             <div class="progress" style="width: 80%;margin-top: 7px;font-size: 15px;height: 20px;border-radius: 10px;float: left;margin-left:31px;">
              <div class="progress-bar" role="progressbar" :aria-valuenow="Math.ceil(phoneCustom.bright/255*100)" aria-valuemin="0" aria-valuemax="100" :style="'width:'+Math.ceil(phoneCustom.bright/255*100)+'%; background-color:#00D8FF'">
                {{Math.ceil(phoneCustom.bright/255*100)}}%
              </div>
             </div>
            </div>

            </div>

          </div>
          <div class="vertical-social-box"></div>
      </div>

    </div>
  </div>

  <!-- Left and right controls -->
    <a class="carousel-control-prev" href="#demo" style="visibility:hidden" data-slide="prev" @click="customminus">
      <span class="carousel-control-prev-icon"></span>
    </a>
    <a class="carousel-control-next" href="#demo" data-slide="next" @click="customplus">
      <span class="carousel-control-next-icon"></span>
    </a>
  </div>

    <div style="display:inline-block">
      <textarea class="form-control" id="summary-ckeditor" placeholder=""></textarea>
    </div>
    <form @submit.prevent="create">
      <input type="submit" class="btn btn-outline-secondary1" style="float:right;margin:10px" value="Save">
    </form>
</div>
</template>

<script>
import VueSlider from 'vue-slider-component'
import 'vue-slider-component/theme/default.css'
import PieChart from '@/components/chart/PieChart.js'
import axios from 'axios'

export default {
    data(){
        return{
          checked:[],
          phoneCustoms:[],
          rgb:[],
          customs:1,
          temp:""
        }
    },
    mounted(){
      $('textarea').ckeditor({
         width:1050,
         height:300
      });

      gradX("#gradX", {
        targets: [".target"],
        sliders: []
      });

      $('.carousel').carousel({
        interval:false
      });
  },
    components: {
      VueSlider,
      PieChart,
    //  'photoshop-picker': Photoshop
    },
    computed:{
      temp_return: function(){
        this.temp = Math.ceil(this.phoneCustoms.length/3);
        return this.temp;
      }
    },
    created(){
      axios.get('http://arominds.com:8000/api/customcard')
        .then(response=>{
          this.phoneCustoms = response.data.customcard
          this.rgb = response.data.rgb
        })
    },
    methods: {
    color_product(e, description, code){
      if(this.checked[0] == null){
        this.color1 = description;
        this.product_send1 = code;
        this.value1 = 50;
      }else if(this.checked[1] == null){
        this.color2 = description;
        this.product_send2 = code;
        this.value2 = 50;
      }else{
        this.color3 = description;
        this.product_send3 = code;
        this.value3= 50;
      }
    },
    create(){
      axios.post('http://arominds.com:8000/api/articles',{
        positive_perfume: this.checked[0].positive_perfume,
        positive_strength : this.checked[0].positive_strength,
        normal_perfume: this.checked[0].normal_perfume,
        normal_strength : this.checked[0].normal_strength,
        nagative_perfume: this.checked[0].nagative_perfume,
        nagative_strength : this.checked[0].nagative_strength,
        rgb : this.checked[0].FIRST_RGB.replace(/,/g, ".")+","+this.checked[0].SECOND_RGB.replace(/,/g, ".")+","+this.checked[0].THRID_RGB.replace(/,/g, "."),
        bright : this.checked[0].bright,
        content : $("textarea#summary-ckeditor").val()
      })
      .then(response =>{
            //this.$router.push('/feed')
            console.log("성공")
            console.log( $("textarea#summary-ckeditor").val())
            this.$router.push('/community')
        })

    },
    customplus(){
      //$('div').remove('.col-xs-3');
      this.customs++;
      this.temp = Math.ceil(this.phoneCustoms.length/3);
      //마지막 페이지 일때 (>)을 숨기기
      if(this.customs == this.temp){
        $('.carousel-control-next').attr('style', 'visibility:hidden');
      }
      if(this.customs > 1){
        $('.carousel-control-prev').attr('style', 'visibility:visible');
      }
      return this.customs;
    },
    customminus(){
      this.customs--;
      this.temp = Math.ceil(this.phoneCustoms.length/3);
      if(this.customs < this.temp){
        $('.carousel-control-next').attr('style', 'visibility:visible');
      }
      if(this.customs == 1){
        $('.carousel-control-prev').attr('style', 'visibility:hidden');
      }
      return this.customs;
    }
 }
}
</script>

<style scoped>
.carousel-item{
  width: 1100px;
}

.text1{
  padding-left: 10px;
  font-size:20px;
  width: 160px;
}

input[type=checkbox]{
  display:none;
}

input[type=checkbox] + label{
  display: inline-block;
  cursor: pointer;
  line-height: 22px;
  padding-left: 22px;
  background: url('/img/check_off.png') left/22px no-repeat;
}

input[type=checkbox]:checked + label {
  /* background-color: rgb(255, 255, 255, 0.58);  */
}

/* product add averlay hover */
.pdf-thumb-box{
  width: 20rem;
  height:34rem;
  display:inline-block !important;
  position:relative !important;
  overflow: hidden;
  border: 1px solid #EAEBEC;
  border-radius: 10px;
}
.pdf-thumb-box-overlay {
  visibility:hidden;
  opacity:0;
  transition:visibility 0s linear 0.5s,opacity 0.5s linear;
}
.pdf-thumb-box{
  color:transparent;
  /* color:black; */
}
.pdf-thumb-box:hover .pdf-thumb-box-overlay {
  visibility:visible;
  opacity:1;
  transition-delay:0s;
  text-align:center;
  position: absolute;
  background-color: rgb(255, 255, 255, 0.58);
  /* color: #fff; */
  color:#FA5882;
  font-size:100px;
  margin-left: -10px;
  margin-top: 15px;
  width: 20rem;
  height:34rem;
  text-shadow: 0 1px 2px rgba(0, 0, 0, .6);
  z-index:3;
}

/* .fa-check{
  color:#FA5882;
  font-size:130px;
  margin-left:80px;
  margin-top:80px;
  height: 100px;
} */

.is-active{
  visibility:visible;
  opacity:1;
  transition-delay:0s;
  text-align:center;
  position: absolute;
  background-color: rgb(255, 255, 255, 0.58);
  color:#FA5882;
  font-size:100px;
  margin-left: -5px;
  margin-top: 15px;
  width:100%;
  height:100%;
  text-shadow: 0 1px 2px rgba(0, 0, 0, .6);
  z-index:2;
}

.pdf-thumb-box-overlay span {
  /* margin: auto; */
  position: absolute;
  top: 0; left: 0; bottom: 0; right: 0
}

/* 상품 추가 버튼 */
.ProductaddButton{
  width: 130px;
  height: 40px;
  font-size: 21px;
  font-weight: bold;
  text-align: center;
  border-radius: 5px;
  padding-bottom: 35px;
  border:solid 2px rgb(252, 133, 143);
  background-color:white;
  color:rgb(252, 133, 143);
  margin-right: 10px;
}

.ProductaddButton:hover{
  background-color: rgb(252, 133, 143);
  color:white;
}

/* led doughnut */
#doughnut1{
  width: 280px;
  height: 280px;

  /* creates outer circle */
  border-radius: 50%;

  /* masks the inner circle */
  -webkit-mask: radial-gradient(ellipse at center,
        rgba(0,0,0,0) 0%, rgba(0,0,0,0) 57%,
        rgba(0,0,0,1) 57%, rgba(0,0,0,1) 100%
    );
  mask: radial-gradient(ellipse at center,
        rgba(0,0,0,0) 0%, rgba(0,0,0,0) 60%,
        rgba(0,0,0,1) 60%, rgba(0,0,0,1) 100%
    );
  /* background: conic-gradient(red, yellow, lime, aqua, blue, magenta, red); */
}

/* carousel */
.col-md-3{
  display: inline-block;
  margin:30px;
}
.col-md-3 img{
  display:inline-block;
  width:100%;
  height:auto;
}
body .carousel-indicators li{
  background-color:red;
}
body .carousel-control-prev-icon,
body .carousel-control-next-icon{
  background-color:red;
  border-radius: 50%;
  border: solid 1px red;
  width: 100%;
  height: 30px;
  font-size: 20px;
}
body .no-padding{
  padding-left: 0;
  padding-right: 0;
}

.carousel-indicators{
  bottom:-10px;
}

.carousel-control-prev{
  width:30px;
}

.carousel-control-next{
  width:30px;
}

.pie_layout{
  margin-top: 26px;
}

.circle{
  width: 30px;
  height: 30px;
  border-radius: 50%;
  float: left;
  margin-right: 5px;
}

.circle_test{
  width: 200px;
  text-align:left;
  margin:10px;
  color:black;
}
</style>
