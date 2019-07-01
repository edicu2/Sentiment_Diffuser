<template>
  <div class="container" id="font2">
    <!-- 슬라이더 뷰 -->
    <div class="row" style="margin-left:10px height:300px width:300px">
      <div class="col-md-12" id="font2">
        <span class="btn btn-success" v-show="this.selected_emotion !== null" style="margin-bottom:20px; width:80px; backgroundColor:#F87079; borderColor:#F87079; color:#FFFFFF;"><b>{{this.selected_emotion}}</b></span>
        <span class="btn btn-info" v-show="this.selected_age !== null" style="margin-bottom:20px; width:80px"><b>{{this.selected_age}}代</b></span>
        <span class="btn btn-danger" v-show="this.selected_gender !== null" style="margin-bottom:20px; width:80px"><b>{{this.selected_gender}}</b></span>
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
import RadarChart from '@/components/my_page2/Radar.js'
import BarChart from '@/components/my_page2/Bar.js'
import ImageSlider from '@/components/my_page2/ImageSlider.vue'
import EventBus from '@/components/my_page2/EventBus'
import { PassThrough } from 'stream';
import Articles from '@/components/my_page2/Articles2.vue'
import axios from 'axios'
export default {
  data () {
    return {
      selected_gender: null,
      selected_age: null,
      selected_emotion: null,

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
    EventBus.$on('select_emotion',this.reset);
    EventBus.$on('select_age',this.reset);
    EventBus.$on('select_gender',this.reset);
    // EventBus.$on('select_emotion', selected => {
    //    this.selected = selected
    // })
    this.adjLabels = ['悲しみ','幸せ','cold','fatigue','insomnia','hairLoss','fear','anger']
    this.adjDatas = [80,74,52,50,40,30,30,22]
    EventBus.$on('imageClick_on', status => {
      this.imageClick_on = status
    })//end EventBus
    // })
  },　
  beforeMount(){
    axios.get('http://arominds.com:8000/api/products')
            .then(response => {
                this.products2 = response.data

                //이걸로 어떤 향을 내보낼지 정할수 있게 하자
                console.log(this.products2[0]);
            })
            .catch(error => {
                console.error(error);
            })
  },
  components:{
        RadarChart,
        BarChart,
        ImageSlider,
        'my-article': Articles
  },
  methods: {
    next: function() {
      if(this.currentNum < this.images.length-3){
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
      //$('span.btn.btn-success').text(val);
      if(this.currentNum != 0){
        this.currentNum = 0
      }
      // }else{
      //   this.currentNum = 1
      // }
      if (val == 'sad') {
        this.products=[this.products2[2],this.products2[3]];
        this.selected_emotion = '悲しみ';
      }else if (val == 'happy') {
        this.products=[this.products2[15],this.products2[2],this.products2[10]];
        this.selected_emotion = 'happy';
      }else if (val == 'normal') {
        this.products=[this.products2[10],this.products2[5]];
        this.selected_emotion = 'normal';
      }else if(val == 'woman'){
        this.products=[this.products2[2],this.products2[14]];
        this.selected_gender = '女性';
      }else if(val == '20'){
        this.products=[this.products2[3]];
        this.selected_age = '20';
      }else if(val == 'hairLoss'){
        this.products=[this.products2[2],this.products2[3],this.products2[8]];

      }else if (val == 'fear') {
        this.products=[this.products2[2],this.products2[14]];
      }else if (val == 'anger'){
        this.products=[this.products2[15],this.products2[10]];
      }
      console.log(this.currentNum)
    }
  }
}
</script>
<style>

img.pre:hover, img.nex:hover {
  opacity:0.5
}
</style>
