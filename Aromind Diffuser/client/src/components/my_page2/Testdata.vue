<template>
  <div class="container">

    <h2 id="font" style="font-weight: normal;">My Status</h2>
    <hr>

    <div class="row" style="margin-bottom: 70px">
      <div class="col-md-5" style="height:400px; margin-right:10px">
        <canvas id="circle" style="height:400px; position:absolute;"></canvas>
        <radar-chart style=" position:absolute"
        :chart-data="{
          labels: adjLabels,
          datasets: [{
            label: 'Text',
            data: adjDatas,
            backgroundColor:'rgba(31, 151, 205, 0.3)',
            borderColor: 'rgba(31, 151, 205, 0.9)',
          }],
          fontSize: 30
        }">
        </radar-chart>
      </div>

      <div class="col-md-6">
        <bar-chart style="width:630px;" :width="600"
         :chart-data="{
          labels: adjLabels,
          datasets: [{
            label: 'Text',
            hoverBackgroundColor: 'rgba(81,84,86,0.7)',
            data: adjDatas,
            backgroundColor:adjBarBackground,
          }]
        }">
        </bar-chart>
      </div>
    </div>

    <!-- 슬라이더 뷰 -->
    <div class="row" style="margin-left:10px height:300px width:300px">
      <div class="col-md-12">
        <span class="btn  btn-success" style="margin-bottom:20px; width:90px; backgroundColor:#F87079; borderColor:#F87079; color:#FFFFFF; font-weight:bold;"><b>{{ adjLabels[0] }}</b></span>
      </div>
      <image-slider style="height:250px" v-if="currentNum+3>index&&currentNum<=index"
                    v-for="(product, index) in products" @key="index" :key="index"
                    :image="product.product_img" :direction="direction" :price="product.product_price+'円'" :name="product.product_name" :code="product.product_code" class="col-md-4" >
      </image-slider>
      <div class="col-md-12 " style=" position:absolute; padding-top:150px">
        <!-- <img class="pre" src="@/assets/arrowhead-thin-outline-to-the-left.png" style="margin-left:-60px" width="40px" @click="prev"/>
        <img class="nex" src="@/assets/arrow-point-to-right.png" style="margin-left:1120px" width="40px" @click="next"/> -->
        <img class="pre" src="@/assets/left.png" style="margin-left:-60px" width="40px" @click="prev" alt="What the Image"/>
        <img class="nex" src="@/assets/right.png" style="margin-left:1145px" width="40px" @click="next" alt="What the Image"/>
      </div>

      <div class="col-md-12" id="cardList" style="margin-top:200px">

        <my-article>

        </my-article>
      </div>
    </div>
    <div style="height:30px">
    </div>
  </div>
</template>

<script>
import RadarChart from './Radar.js'
import BarChart from './Bar.js'
import ImageSlider from './ImageSlider.vue'
import EventBus from './EventBus'
import { PassThrough } from 'stream';
import Articles from './Articles2.vue'
import axios from 'axios'
export default {
  data () {
    return {
      email: '',
      password: '',
      direction:0,
      // adj:[],
      adjLabels:[],
      adjDatas:[],
      adjBarBackground: ['rgba(39,170,227,0.6)','rgba(222,226,2,0.6)','rgba(244,154,0,0.6)','rgba(144,196,30,0.6)',
                         'rgba(39,170,227,0.6)','rgba(222,226,2,0.6)','rgba(244,154,0,0.6)','rgba(144,196,30,0.6)'],
      // images:['https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_18.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_04.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_05.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_06.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_07.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_08.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_09.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_10.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_11.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_12.png',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_13.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_14.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_15.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_16.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_17.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_18.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_19.jpg',
      //         'https://s3.ap-northeast-2.amazonaws.com/capstonearomind/img/aroma_20.jpg'],
      currentNum: 0,
      imageClick_on: 'no',
      txtLabel: '',
      products:[],
      products2:[]
    }
  },
  created () {
    EventBus.$on('barLabelClick-event',this.reset);
    // axios.get('/api/adj')
    // .then((result) => {
    //       this.adj = result.data
    //       this.adj.forEach(function (item, index, array) {
    //         if(index <8) this.adjLabels.push(item.adj)
    //       },this);
    //       this.adj.forEach(function (item, index, array) {
    //         if(index <8) this.adjDatas.push(item.count)
    //       },this);
    this.adjLabels = ['우울증','두통','감기','피로','불면증','공포','탈모','분노']
    this.adjDatas = [80,74,52,50,40,30,30,22]
    EventBus.$on('imageClick_on', status => {
      this.imageClick_on = status
    })//end EventBus
    // })
  },
  beforeMount(){
    axios.get('http://arominds.com:8000/api/products')
            .then(response => {
                this.products.push(response.data[2],response.data[3])
                this.products2 = response.data

                //이걸로 어떤 향을 내보낼지 정할수 있게 하자
                console.log(this.products2[0]);
            })
            .catch(error => {
                console.error(error);
            })
  },
  mounted(){
    this.circleDraw()
  }, //end mounted
  components:{
        RadarChart,
        BarChart,
        ImageSlider,
        'my-article': Articles
  },
  methods: {
    //그림그리기
    circleDraw: function() {
      var canvas = document.getElementById('circle');
      if (canvas.getContext)
      {
      canvas.width = canvas.clientWidth;
      canvas.height = canvas.clientHeight;
      var ctx = canvas.getContext('2d');
      var X = canvas.width;
      var Y = canvas.height;
      ctx.beginPath();
      ctx.moveTo(X-600, 40);
      ctx.lineTo(X-483,87);
      ctx.lineTo(X-436,204);
      ctx.lineTo(X-483,321);
      ctx.lineTo(X-600,370);
      ctx.lineTo(X-716,321);
      ctx.lineTo(X-764,204);
      ctx.lineTo(X-716,87);
      ctx.fillStyle = "#4c4c4c";
      ctx.fill();
      }
    },
    //슬라이더 부분
    next: function() {
      if(this.currentNum < this.products.length-3){
        this.currentNum += 3
        this.direction=1
      }
    },
    prev: function() {
      if(this.currentNum >=3){
        this.currentNum -= 3
        this.direction=0
      }
    },
    reset: function(val) {
      console.log(val);
      $('span.btn.btn-success').text(val);
      if(this.currentNum != 0){
        this.currentNum = 0}
      // }else{
      //   this.currentNum = 1
      // }

      // this.products2[0] <-이걸배열에 넣어보자
      if (val == '우울증') {
        this.products=[this.products2[2],this.products2[3]];
      }else if (val == '두통') {
        this.products=[this.products2[2],this.products2[15],this.products2[10]];
      }else if (val == '감기') {
        this.products=[this.products2[5],this.products2[10]];
      }else if(val == '파로'){
        this.products=[this.products2[2],this.products2[14]];
      }else if(val == '불면증'){
        this.products=[this.products2[3]];
      }else if(val == '탈모'){
        this.products=[this.products2[2],this.products2[3],this.products2[8]];
      }else if (val == '공포') {
        this.products=[this.products2[2],this.products2[14]];
      }else if (val == '분노'){
        this.products=[this.products2[15],this.products2[10]];
      }
      // this.images=['https://sanaulla.files.wordpress.com/2019/04/image-9.png',
      //              'https://sanaulla.files.wordpress.com/2019/04/image-10.png?w=829'];
      console.log(this.currentNum)
    }
  },
}

</script>
<style>

img.pre:hover, img.nex:hover {
  opacity:0.5
}
</style>
