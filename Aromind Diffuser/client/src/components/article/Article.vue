<template>
  <div class="col-md-4" style="margin-bottom: 1%;">
    <b-card no-body style="height: 400px;">
      <router-link class="view" :to="'/articles/' + customcard_board.id">
        <!-- <img :src="'http://localhost:8000/' + article.image" alt="" style="width: 87%; padding-top: 15%; margin-bottom: 13%;" class="card-img-top rounded mx-auto d-block"> -->
        <div class="graph_container" v-if="customcard_board">
          <!-- <canvas id="chartPie" style="width: 1000px; height: 400"></canvas> -->

          <div style="position:absolute;">
            <span style="position:relative; top: -46px; left: 15px;">
              <div class="targets">
                <div id="doughnutList" :style="'background: conic-gradient('+rgb+')'"></div>
              </div>
            </span>
          </div>

          <div class="pie_layout" style="margin-top: 22%;">
            <pie-chart
                :chart-data="{
                    datasets: [
                      {
                        backgroundColor: [customcard_board.positive_color_name, customcard_board.normal_color_name, customcard_board.nagative_color_name],
                        data: [customcard_board.positive_strength, customcard_board.normal_strength, customcard_board.nagative_strength],
                        borderWidth:0
                      }
                    ]
                  }"
                :height="250"
                :options="{responsive: true, maintainAspectRatio: true}">
            </pie-chart>
          </div>
        </div>
        <div v-if="customcard_board" class="overlay" :id="customcard_board.id" @mouseover="$emit('view-article')" @mouseleave="$emit('out-article')">
          <div class="text">
            {{ customcard_board.customcard_name }}<br>
            <div v-if="customcard_board.positive_strength !== 0">
              {{ customcard_board.positive_perfume_name }}
              {{ customcard_board.positive_strength }}<br>
            </div>
            <div v-if="customcard_board.normal_strength !== 0">
              {{ customcard_board.normal_perfume_name }}
              {{ customcard_board.normal_strength }}<br>
            </div>
            <div v-if="customcard_board.nagative_strength !== 0">
              {{ customcard_board.nagative_perfume_name }}
              {{ customcard_board.nagative_strength }}<br>
            </div>
            Mood {{ customcard_board.bright }}%<br>
          </div>
          <!-- 이름/아로마이름/강도/밝기 -->
        </div>
      </router-link>
      <!-- <b-list-group flush>
        <b-list-group-item>
          <a href="#" class="btn btn-danger" role="button" @click="$emit('delete-article')" style="margin-left: 8%;width: 40%;">
            Delete
          </a>
          <router-link class="btn btn-outline-info" :to="'/article/' + article.id + '/edit'" style="width: 40%;">
            Edit
          </router-link>
        </b-list-group-item>
      </b-list-group> -->
    </b-card>
  </div>
</template>

<script>
  import PieChart from '@/components/chart/PieChart.js'
  import Chart from 'chart.js'

  export default {
    props: ['customcard_board','rgb', 'authenticatedUser'],
    components: {
      PieChart,
    },
    mounted () {
      console.log(this.rgb)
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
    color: rgb(234,119,142);
    text-decoration: none;
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

</style>
