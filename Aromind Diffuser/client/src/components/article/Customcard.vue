<template>
<div class="container">
    <br>
    <div style="display:inline-block;">
      <!-- 상품선택, Led등 색 선택 -->
      <!-- modal-1 -->
      <button class="btn btn-outline-secondary1" v-b-modal.modal-1>상품 선택</button>

      <!-- madal-2 -->
      <button class="btn btn-outline-secondary1" v-b-modal.modal-2>LED색 선택</button>

      <div class="targets" v-show="this.led_value !== ''" style="float:right;margin-left:5px;">
        <div class="target" id="target"></div>
      </div>
    </div>
    <div  v-show="this.product_send1 === this.product_send2" class="card" id="first_see">
      <div class="card-body" style="padding:145px;">
        <i class="fa fa-check" style="margin-bottom:20px;color:black;font-size:35px;" aria-hidden="true"></i>
        <h4 style="font-size:30px;">
          추가할 3개의 상품를 선택해주세요
        </h4>
      </div>
    </div>

    <!-- 설정값 조정 -->
    <div style="display:inline-block;">
    <table v-show="this.value1 !== ''" >
      <!-- 원차트, 도넛 도형 -->
      <tr>
        <td class="round" rowspan="7">
          <div class="card" style="width: 430px; height:460px; border-radius: 20px;text-align:center">
            <div class="card-body">

            <div class="graph_container">
              <canvas id="Chart1" style="margin-top:62px;"></canvas>
            </div>

            <div style="position:absolute;">
              <span style="position:relative; top: -320px; left: 13px;">
                <div class="targets">
                 <div id="doughnut" :style="test"></div>
                </div>
              </span>
            </div>
           </div>
          </div>
        </td>

        <td colspan="3" style="padding:0px">
          <div id="square"></div>
          <div v-show="this.value1 !== ''">
            <h4>커스텀 카드 이름</h4>
          </div>
          <div v-show="this.value1 !== ''">
          <div class="form-input">
            <label>
              <input class="title" required v-model="title" placeholder="  커스텀 카드 이름을 입력해주세요">
            </label>
          </div>
          </div>
        </td>
      </tr>

      <tr>
        <td colspan="2">
          <div id="square" style="margin-top: 7px"></div>
          <div v-show="this.value1 !== ''">
            <h4 style="margin-top: 7px">향 설정값</h4>
          </div>
        </td>
        <td style="padding-right:70px;"></td>
      </tr>

      <tr>
       <td>
          <div class="text1">{{this.checked[0]}}</div>
        </td>

        <td class="td_slider" @click="graph_container">
          <vue-slider
            v-model="value1"
            v-bind="options1"
            v-show="this.value1 !== ''"/>
        </td>
        <td class="td_percent">
          <div class="persent"
            v-show="this.value1 !== ''">{{this.value1}}%</div>
        </td>
      </tr>

      <tr>
        <td>
          <div class="text1">{{this.checked[1]}}</div>
        </td>
        <td class="td_slider" @click="graph_container">
          <vue-slider
            v-model="value2"
            v-bind="options2"
            v-show="this.value2 !== ''"/>
        </td>
        <td class="td_percent">
          <div class="persent"
            v-show="this.value2 !== ''">{{this.value2}}%</div>
        </td>
      </tr>

      <tr>
        <td>
          <div class="text1">{{this.checked[2]}}</div>
        </td>
        <td class="td_slider" @click="graph_container">
          <vue-slider
            v-model="value3"
            v-bind="options3"
            v-show="this.value3 !== ''"/>
        </td>
        <td class="td_percent">
          <div class="persent"
            v-show="this.value3 !== ''">{{this.value3}}%</div>
        </td>
      </tr>

      <tr>
        <td colspan="3">
          <div id="square" v-show="this.led_value !== ''" style="margin-top:15px"></div>
          <h4
            v-show="this.led_value !== ''" style="margin-top:15px">LED등 밝기</h4>

        <div class="card"
          v-show="this.okmodal == 'false'">

          <div class="card-body" style="text-align:center">
            <h4>
              <i class="fa fa-check" style="margin-right:5px;color:black;font-size:25px;" aria-hidden="true"></i>
              LED색을 선택해주세요
            </h4>
          </div>
        </div>
        </td>
      </tr>

      <tr v-show="this.led_value !== ''">
        <td class="td_slider" colspan="2">
          <vue-slider
            v-model="led_value"
            v-bind="led_options"
            v-show="this.led_value !== ''"/>
        </td>
        <td style="padding-top:'20px'">
          <div class="persent"
            v-show="this.led_value !== ''">{{this.led_value}}%</div>
        </td>
      </tr>
      </table>
    </div>

        <!-- Modal Component-1 향 선택 -->
        <b-modal id="modal-1" size="lg" title="향 선택"
            @ok="graph_container" ok-variant="outline-danger" ok-title-html="확인" cancel-variant="outline-secondary" cancel-title-html="취소">

          <div>구매한 목록</div><br>
          <div class="text-center">
            <div class="row">
              <div class="col-lg-3 mb-2" v-for="(product, index) in products" v-if="index < 8">
                  <div class="pdf-thumb-box">
                    <div
                        :class="{'pdf-thumb-box-overlay': checked,
                                'pdf-thumb-box-overlay is-active' : product_send1 == product.product_code || product_send2 == product.product_code || product_send3 == product.product_code }">
                    <span class="fa-stack fa-lg">
                      <div>
                          <input type="checkbox"
                              @click="color_product($event, product.product_color, product.product_code, product.aroma_name)" :id="'box'+index">
                          <label :for="'box'+index">
                            <i class="fa fa-check" aria-hidden="true" style="font-size:70px;margin-left:33px;margin-bottom:30px;"></i>
                          </label>
                      </div>
                    </span>
                    </div>

                    <img class="img-responsive" :src="product.product_img" alt="" weight="180" height="180" style="margin:10px 0px;">
                    <div class="card-title" style="color:black;font-size:18px" v-html="product.aroma_name"></div>
                  </div>
              </div>
                  <div class="vertical-social-box"></div>
              </div>
          </div>
        </b-modal>

         <!-- Modal Component-2 무드등 색 선택 -->
        <b-modal id="modal-2" title="무드등 색 선택" @ok="handleOK" ok-variant="outline-danger" ok-title-html="확인" cancel-variant="outline-secondary" cancel-title-html="취소">
          <!-- color gradient -->
          <div id="gradX"></div>
        </b-modal>

      <div style="display:inline-block">
        <textarea class="form-control" id="summary-ckeditor1" placeholder=""></textarea>
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
            products:[],
            checked:[],
            phoneCustoms:[],
            customs:1,
            title:"",
            product_send1:"",
            product_send2:"",
            product_send3:"",
            color1:"",
            color2:"",
            color3:"",
            value1:'',
            value2:'',
            value3:'',
            led_value:'',
            colorpicker:{},
            test:{},
            test_send:"",
            okmodal:'false',
            options1:{
              dotSize:25,
              height: 8,
              eventType: 'auto',
              processStyle:{
                backgroundColor:'#f87979'
              }
            },
          options2:{
              dotSize:25,
              height: 8,
              eventType: 'auto',
              processStyle:{
                backgroundColor:'orange'
              }
            },
          options3:{
              dotSize:25,
              height: 8,
              eventType: 'auto',
              processStyle:{
                backgroundColor:'pink'
              }
            },
          led_options:{
              dotSize:25,
              height: 8,
              eventType: 'auto',
              processStyle:{
                backgroundColor:'#00D8FF'
              }
            }
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

      $('input.title').focus(function(){
       $(this).removeAttr('placeholder');
      });
  },
    components: {
      VueSlider,
      PieChart
    },
    created(){
      axios.get('http://arominds.com:8000/api/products')
        .then(response => {
            this.products = response.data,
            this.productColor = response.product_color
      })

      axios.get('http://arominds.com:8000/api/customcard')
        .then(response=>{
          this.phoneCustoms = response.data
        })
    },
    methods: {
        check: function(e) {
        if (e.target.checked) {
            console.log(e.target.value)
        }
    },
    handleOK(bvModalEvt){
      //bvModalEvt.preventDefault();
      if(this.okmodal){
        this.colorpicker= $('#gradx_code').val();
        var start = this.colorpicker.indexOf("rgb");
        var end = this.colorpicker.indexOf(");");

        var end2 = this.colorpicker.indexOf(")");

        this.test = "background: conic-gradient("+this.colorpicker.substring(start,end)+", "+this.colorpicker.substring(start,end2)+"));";
        this.led_value = 50;
        this.okmodal = true;

        this.test_send = this.colorpicker.substring(start,end).replace(/\)/g, "").replace(/rgb\(/gi,"").replace(/ , /g,",").replace(/, /g, ".").replace(/\d{2}\%/g, "").replace(/\d{1}\%/g, "").replace(/\-/,"").replace(/(\s*)/g,"");
        //console.log(this.test_send);
      }
    },
    graph_container(){
    //chartjs
    var ctx = document.getElementById("Chart1");
    var myChart = new Chart(ctx, {
      type: 'pie',
      data: {
          datasets: [{
            data: [],
            backgroundColor: [this.color1, this.color2, this.color3],
            borderWidth:0
          }]
      },options: {responsive: true, maintainAspectRatio: false},
    });

      myChart.data.datasets[0].data[0] = this.value1;
      myChart.data.datasets[0].data[1] = this.value2;
      myChart.data.datasets[0].data[2] = this.value3;
      myChart.update();

     this.options1.processStyle.backgroundColor = this.color1;
     this.options2.processStyle.backgroundColor = this.color2;
     this.options3.processStyle.backgroundColor = this.color3;
    },
    color_product(e, description, code, aroma){
      if(this.checked[0] == null){
        this.color1 = description;
        this.product_send1 = code;
        this.checked[0] = aroma;
        this.value1 = 50;
      }else if(this.checked[1] == null){
        this.color2 = description;
        this.product_send2 = code;
        this.checked[1] = aroma;
        this.value2 = 50;
      }else{
        this.color3 = description;
        this.product_send3 = code;
        this.checked[2] = aroma;
        this.value3= 50;
      }
      //this.color += description;
    },
    create(){
      axios.post('http://arominds.com:8000/api/articles',{
        customcard_name: this.title,
        positive_perfume: this.product_send1,
        positive_strength : this.value1,
        normal_perfume: this.product_send2,
        normal_strength : this.value2,
        nagative_perfume: this.product_send3,
        nagative_strength : this.value3,
        rgb : this.test_send,
        bright : this.led_value,
        content : $("textarea#summary-ckeditor1").val()
      })
      .then(response =>{
            //this.$router.push('/feed')
            console.log(response)
            console.log("성공")
            console.log( $("textarea#summary-ckeditor1").val())
            this.$router.push('/community')
        })

    }
 }
}
</script>

<style scoped>
.text1{
  padding-left: 10px;
  font-size:20px;
  width: 160px;
}

 #square {
  width: 10px;
  height: 30px;
  background: #FA5882;
  margin-right: 10px;
  float: left;
}

#square2 {
  width: 10px;
  height: 100px;
  background: #FA5882;
  margin: 0;
  float: right;
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

#first_see{
  margin: 30px;
  width: 90%;
  height: 400px;
  border-radius: 20px;
  text-align:center;
}

/* product add averlay hover */
.pdf-thumb-box{
  height: 250px;
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
  width:100%;
  height:100%;
  text-shadow: 0 1px 2px rgba(0, 0, 0, .6);
  color:#FA5882;
}

.is-active{
  visibility:visible;
  opacity:1;
  transition-delay:0s;
  text-align:center;
  position: absolute;
  background-color: rgb(255, 255, 255, 0.58);
  color:#FA5882;
  width:100%;
  height:100%;
  text-shadow: 0 1px 2px rgba(0, 0, 0, .6);
}

.pdf-thumb-box-overlay span {
  /* margin: auto; */
  position: absolute;
  margin-top: 90px;
  top: 0; left: 0; bottom: 0; right: 0
}

/* table style */
.container{
  margin-bottom: 30px;
}

.td_slider{
  width: 300px;
  padding: 4px 17px;
}

.round{
  padding: 0px 30px 0px 0px;
}

.persent{
  font-size: 25px;
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

.layout{
  margin: 10px;
}

table{
  margin: 30px 0px;
}

/* gradX */
#target {
  border-radius: 5px;
  /* margin-top: 40px; */
  width: 130px;
  height: 38px;
  display:inline-block;
  /* padding-bottom: 35px;  */
}

/* modal gradx style */
.gradx_panel{
  width: 380px;
}

.gradx_start_sliders{
  width:385px;
}

.graph_container{
  width: 290px;
  height: 290px;
  margin-left:47px;
  margin-top: 40px;
}

/* led doughnut */
#doughnut{
  width: 360px;
  height: 360px;

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
  /* background: -webkit-conic-gradient(red, yellow, lime, aqua, blue, magenta, red); */
}

/* 커스텀 카드 이름 start */
.form-input {
  /* margin: 40px 0px; */
  width: 100%;
}
.form-input label {
  position: relative;
  display: block;
  width: 100%;
  min-height: 45px;
  margin-bottom: 5px;
}
.form-input .placeholder {
  position: absolute;
  display: block;
  top: -10px;
  z-index: 2;
  font-size: 16px;
  transition: all 200ms ease-in-out;
  width: 100%;
  cursor: text;
  font-size: 20px;
  color: gray;
}
.form-input input {
  position: absolute;
  top: 5px;
  z-index: 1;
  width: 90%;
  font-size: 16px;
  border: 0;
  border-bottom: 1px solid grey;
  transition: border-color 200ms ease-in-out;
  outline: none;
  padding: 0;
  margin-left: 20px;
}

.form-input input {
  height: 42px;
}
.form-input input:focus,
.form-input input:valid {
  font-size: 20px;
  /* margin: 5px; */
  border-bottom: 2px solid black;
}
.form-input input:focus + .placeholder,
.form-input input:valid + .placeholder {
  /* top:-20px; */
  cursor: inherit;
  font-size: 14px;
  color: #FA5882;
}
/* 커스텀 카드 이름 end */
</style>
