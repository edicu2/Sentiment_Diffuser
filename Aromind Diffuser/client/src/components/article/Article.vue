<template>
  <div class="col-md-4" style="margin-bottom: 1%;">
    <b-card no-body style="height: 453px;">
      <router-link class="view" :to="'/articles/' + customcard_board.id">
        <img :src="customcard_board.customcard_img" alt="" style="width: 99.932%; height: 235px; padding-top: 0.03%; margin-left:0.02%; margin-right: 0.02%; margin-bottom: 1.5%;" class="card-img-top mx-auto d-block">
        <div class="graph_container" v-if="customcard_board">
          <!-- <canvas id="chartPie" style="width: 1000px; height: 400"></canvas> -->
          <!-- 커스텀카드 이미지 -->
          <!-- <img src="https://capstonearomind.s3.ap-northeast-2.amazonaws.com/customcard/customcard_01.jpg" class="card-img-top" alt=""> -->
          <!-- <img :src="customcard_board.customcard_img" alt="" class="card-img-top" style="width: 100%;"> -->

          <!-- 커스텀카드 정보 -->
          <div class="card-body align-middle" v-if="customcard_board">
            <h5 class="card-title" id="font2">{{ customcard_board.customcard_name }}</h5>
            <p class="card-text"><b>By</b> {{ customcard_board.user_id }}</p>

            <!-- 커스텀카드 정보(아로마오일) -->
            <div class="row" style="margin-top: 3%; margin-left: 0.5%;">
              <h6 class="mt-2">Aroma Oils&nbsp;&nbsp;</h6>
              <div v-if="customcard_board.positive_strength !== 0" style="width: 13%; height: 44px; border-radius: 50%; margin-right: 2%;" :style="{ background : customcard_board.positive_color_name }">
              </div>
              <div v-if="customcard_board.normal_strength !== 0" style="width: 13%; height: 44px; border-radius: 50%; margin-right: 2%;" :style="{ background : customcard_board.normal_color_name }">
              </div>
              <div v-if="customcard_board.nagative_strength !== 0" style="width: 13%; height: 44px; border-radius: 50%;" :style="{ background : customcard_board.nagative_color_name }">
              </div>
            </div>

            <!-- 커스텀카드 정보(무드등) -->
            <div v-if="rgb" class="row" style="margin-left: 0.5%; margin-top: 2%;">
              <h6 class="mt-2" style="margin-right: 14.2%;">Mood</h6>
              <div v-for="(rgb, index) in rgbs" :rgb="rgbs[index]" style="width: 13%; height: 44px; border-radius: 15%; margin-right: 2%;" :style="{ background : rgb }" ></div>
            </div>
          </div>
        </div>

        <!-- hover부분 -->
        <div v-if="customcard_board" class="overlay" :id="customcard_board.id" @mouseover="$emit('view-article')" @mouseleave="$emit('out-article')">
          <div class="text" style="width: 100%;">
            <div id="font2"><h4>{{ customcard_board.customcard_name }}</h4></div>
            <div v-if="customcard_board.positive_strength !== 0" style="font-size: 22px;">
              {{ customcard_board.positive_perfume_name }}
              {{ customcard_board.positive_strength }}<br>
            </div>
            <div v-if="customcard_board.normal_strength !== 0" style="font-size: 22px;">
              {{ customcard_board.normal_perfume_name }}
              {{ customcard_board.normal_strength }}<br>
            </div>
            <div v-if="customcard_board.nagative_strength !== 0" style="font-size: 22px;">
              {{ customcard_board.nagative_perfume_name }}
              {{ customcard_board.nagative_strength }}<br>
            </div>
            <div style="font-size: 22px;">Mood {{ customcard_board.bright }}%</div>
          </div>
        </div>
      </router-link>
    </b-card>
  </div>
</template>

<script>
  import PieChart from '@/components/chart/PieChart.js'
  import Chart from 'chart.js'

  export default {
    props: ['customcard_board','rgb', 'rgbs', 'authenticatedUser'],
    components: {
      PieChart,
    }
  }
</script>

<style>
  .view {
    color: black;
  }

  .view:visited {
    color: black;
    text-decoration: none;
  }

  .view:hover {
    /* color: rgb(234,119,142); */
    text-decoration: none;
    /* cursor: default; */
  }

  #font {
    font-family: 'Overpass', sans-serif;
  }

  .overlay {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100%;
    width: 100%;
    opacity: 0;
    transition: .5s ease;
    background-color: black;
    border-radius: 0.5%;
  }

  /* .container:hover .overlay {
    opacity: 0.6;
  } */

  .text {
    color: white;
    font-size: 20px;
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    text-align: center;
  }

/*   .overlay2 {
    background: none repeat scroll 0 0 #DFDFDF;
    border: 5px solid #DFDFDF;
    color: black;
    font-size: 13px;
    height: 30px;
    letter-spacing: 1px;
    line-height: 30px;
    margin: 0 auto;
    position: relative;
    text-align: center;
    text-transform: uppercase;
    top: -80px;
    left:-30px;
    display:none;
    padding:0 20px;
  }

  .overlay2:after {
    content:'';
    position:absolute;
    bottom:-10px;
    width:10px;
    height:10px;
    border-bottom:5px solid #dfdfdf;
    border-right:5px solid #dfdfdf;
    background:#DFDFDF;
    left:50%;
    margin-left:-5px;
    -moz-transform:rotate(45deg);
    -webkit-transform:rotate(45deg);
    transform:rotate(45deg);
  } */

  #doughnutList {
    width: 320px;
    height: 320px;

    /* creates outer circle */
    border-radius: 70%;

    /* masks the inner circle */
    -webkit-mask: radial-gradient(ellipse at center,
          rgba(0,0,0,0) 0%, rgba(0,0,0,0) 57%,
          rgba(0,0,0,1) 57%, rgba(0,0,0,1) 100%
      );
    mask: radial-gradient(ellipse at center,
          rgba(0,0,0,0) 0%, rgba(0,0,0,0) 60%,
          rgba(0,0,0,1) 60%, rgba(0,0,0,1) 100%
      );
  }

  #font2 {
    font-family: 'Kosugi Maru', sans-serif;
  }

</style>
