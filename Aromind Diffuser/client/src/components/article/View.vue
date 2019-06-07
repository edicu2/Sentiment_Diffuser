<template>
  <div>
    <div class="container" id="view">
      <h2 id="font">Community-Blended Aroma</h2>
      <hr>
      <div class="row" style="margin-left: 0.3%; margin-top: 6%; margin-bottom: 6%;">
        <div>
          <b-media no-body class="align-items-center">
            <b-media-aside vertical-align="center" class="card" style="padding-top: 3%; padding-bottom: 3%; padding-left: 2%; padding-right: 2%;">

              <div>
                <div class="pdf-thumb-box">
                <div class="card-body" style="width: 410px;">
                <!-- <div style="position:absolute;">
                  <span style="position:relative; top: 2px; left: -1px;">
                    <div id="doughnut1" :style="'background: conic-gradient('+rgb[index]+')'"></div>
                  </span>
                </div> -->

                <div class="graph_container">
                  <canvas id="chartPie" style="width: 330px; height: 330px"></canvas>
                </div>

                <div style="position:absolute;">
                  <span style="position:relative; top: -369px; left: -25px;">
                    <div class="targets">
                      <div id="doughnut" :style="'background: conic-gradient('+rgb+')'"></div>
                    </div>
                  </span>
                </div>

                <div class="card-title">
                  <!-- <div v-if="customcard_board[0]" class="progress" style="width: 80%;margin-top: 7px;font-size: 15px;height: 20px;border-radius: 10px;float: left;margin-left:3px;">
                      {{Math.ceil(customcard_board[0].bright/255*100)}}%
                  </div> -->
                </div>

                </div>

              </div>
              <div class="vertical-social-box"></div>
          </div>

            </b-media-aside>

            <b-media-body class="ml-5">
              <h3 style="color: white;">dddddddddddddddddddddddddddddDDDdddd</h3>
              <div v-if="customcard_board[0]" style="margin-bottom: 10%;">
                <h1 id="font2">{{ customcard_board[0].customcard_name }}</h1>
                <h5><i class="fas fa-hat-wizard"></i> {{ customcard_board[0].user_id }} ></h5>
              </div>
              <div>
                <div v-if="customcard_board[0]">
                  <div class="row" v-if="customcard_board[0].positive_strength !== 0">
                    <h6 class="col-sm-3">{{ customcard_board[0].positive_perfume_name }}</h6>
                    <div class="col-sm-9">
                      <div class="progress" style="height: 25px;">
                        <div class="progress-bar" role="progressbar" :style="{ width : positive_strength + '%', background : customcard_board[0].positive_color_name }" v-bind:aria-valuenow="positive_strength" aria-valuemin="0" aria-valuemax="100">{{ this.positive_strength }}%</div>
                      </div>
                      <br>
                    </div>
                  </div>
                </div>
                <div v-if="customcard_board[0]">
                  <div class="row" v-if="customcard_board[0].normal_strength !== 0">
                    <h6 class="col-sm-3">{{ customcard_board[0].normal_perfume_name }}</h6>
                    <div class="col-sm-9">
                      <div class="progress" style="height: 25px;">
                        <div class="progress-bar" role="progressbar" :style="{ width : normal_strength + '%', background : customcard_board[0].normal_color_name }" v-bind:aria-valuenow="normal_strength" aria-valuemin="0" aria-valuemax="100">{{ this.normal_strength }}%</div>
                      </div>
                      <br>
                    </div>
                  </div>
                </div>
                <div v-if="customcard_board[0]">
                  <div class="row" v-if="customcard_board[0].nagative_strength !== 0">
                    <h6 class="col-sm-3">{{ customcard_board[0].nagative_perfume_name }}</h6>
                    <div class="col-sm-9">
                      <div class="progress" style="height: 25px;">
                        <div class="progress-bar" role="progressbar" :style="{ width : nagative_strength + '%', background : customcard_board[0].nagative_color_name}" v-bind:aria-valuenow="nagative_strength" aria-valuemin="0" aria-valuemax="100">{{ this.nagative_strength }}%</div>
                      </div>
                      <br>
                    </div>
                  </div>
                </div>
              </div>
              <div v-if="rgb" class="row" style="margin-top: 1%;">
                <h6 class="col-sm-2 pt-2" style="margin-bottom: 5%; margin-right: 10%;">Mood</h6>
                <div v-for="(rgb, index) in rgbs" :rgb="rgbs[index]" style="width: 6.3%; height: 40px; border-radius: 15%; margin-left: 0.7%;" :style="{ background : rgb }" ></div>
              </div>
              <!-- <div class="row">
                <h6 class="col-sm-2" style="margin-bottom: 15%;">Mood</h6>
                <div v-if="customcard_board[0]" id="mood" style="width: 78.5%; height: 25px; margin-left: 2.4%; text-align: center;" :style="'background: linear-gradient(to left,'+rgb+')'">
                  {{Math.ceil(customcard_board[0].bright/255*100)}}%
                </div>
              </div> -->
              <div v-if="customcard_board[0]">
                <div class="row" v-if="customcard_board[0].nagative_strength !== 0">
                  <h6 class="col-sm-3">Bright</h6>
                  <div class="col-sm-9">
                    <div class="progress" style="height: 25px;">
                      <div class="progress-bar" role="progressbar" :style="{ width : bright + '%', background : 'linear-gradient(to left,'+rgb+')' }" v-bind:aria-valuenow="nagative_strength" aria-valuemin="0" aria-valuemax="100">{{ customcard_board[0].bright }}%</div>
                    </div>
                    <br>
                  </div>
                </div>
              </div>
              <button v-on:click="download" class="btn btn-primary1" style="float: right; width: 23%; height: 42px;"><i class="fas fa-file-download"></i> Download</button>
            </b-media-body>
          </b-media>
        </div>
      </div>
      <hr>
      <div v-if="customcard_board[0]">
        <!-- <h5 style="float: left; color: rgb(234,119,142);">▶</h5>
          <h5 style="margin-top: 3%;">{{ customcard_board[0].title }}</h5> -->
        <h4 id="font2"><pre v-if="customcard_board[0]" v-html="rawHtml">{{ customcard_board[0].content }}</pre></h4>
      </div>
      <br>
      <div>
        <router-link class="view btn btn-outline-secondary1" id="list" :to="'/community/'">List</router-link>
      </div>

    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import PieChart from '@/components/chart/PieChart.js'
  import Chart from 'chart.js'

  export default {
    data () {
      return {
        customcard_board: {},
        rgbs:[],
        rgb:[],
        test: {},
        positive_strength: 0,
        val: '%'+this.$route.params.customcard_board,
        bright: '',
        rawHtml: ''
      }
    },

    components: {
      PieChart
    },

     mqtt: {
        '/demd': function(val) {
          console.log('param/param/param/test')
        },
      },

    created () {
      axios.get('http://arominds.com:8000/api/articles/' + this.$route.params.customcard_board)
      .then(response => {
        this.customcard_board = response.data.customcard
        this.rgb = response.data.rgb
        this.positive_strength = this.customcard_board[0].positive_strength
        this.normal_strength = this.customcard_board[0].normal_strength
        this.nagative_strength = this.customcard_board[0].nagative_strength
        this.mood_rgb = this.customcard_board[0].rgb
        this.rgbs = response.data.rgbs
        this.bright = this.customcard_board[0].bright
        this.rawHtml = this.customcard_board[0].content

        /* let regex = /(<([^>]+)>)/ig;
        return this.customcard_board[0].content.replace(regex, ""); */

        /* console.log(this.rgbs)
        console.log('tttt')
        console.log(this.customcard_board[0].id)
        console.log(this.rgb)
        console.log(response)
        console.log(response.data)
        console.log(this.rgb)
        console.log(this.positive_strength)
        console.log(this.normal_strength)
        console.log(this.nagative_strength) */

        /* var beforRgb = this.mood_rgb;
        var moodRgb = beforRgb.split(',');
        var dot = "."; */
        //var moodRgb1 = moodRgb[0].replace("/" + dot + "/g", ",");

        /* var moodRgb1 = moodRgb[0].replace(/\./g,',');
        var moodRgb2 = moodRgb[1].replace(/\./g,',');
        var moodRgb3 = moodRgb[2].replace(/\./g,',');
 */
        //console.log(moodRgb1)

       /*  var mood1 = document.getElementById("mood1");
        mood1.style.backgroundColor = 'rgb(' + moodRgb1 + ')';
        var mood2 = document.getElementById("mood2");
        mood2.style.backgroundColor = 'rgb(' + moodRgb2 + ')';
        var mood3 = document.getElementById("mood3");
        mood3.style.backgroundColor = 'rgb(' + moodRgb3+ ')'; */

        /* var mood1_1 = document.getElementById("mood1-1");
        mood1_1.style.backgroundColor = 'rgb(' + moodRgb1 + ')';
        var mood2_1 = document.getElementById("mood2-1");
        mood2_1.style.backgroundColor = 'rgb(' + moodRgb2 + ')';
        var mood3_1 = document.getElementById("mood3-1");
        mood3_1.style.backgroundColor = 'rgb(' + moodRgb3+ ')'; */


        var ctx = document.getElementById("chartPie");
        var myChart = new Chart(ctx, {
          type: 'pie',
          data: {
              datasets: [
                {
                  backgroundColor: [this.customcard_board[0].positive_color_name, this.customcard_board[0].normal_color_name, this.customcard_board[0].nagative_color_name],
                  data: [this.customcard_board[0].positive_strength, this.customcard_board[0].normal_strength, this.customcard_board[0].nagative_strength],
                  borderWidth:0
                }
              ]
          },
          options: {responsive: true, maintainAspectRatio: false}
        });
      })
    },

    methods: {
      clickSub: function(val) {
        this.$mqtt.subscribe('/demd')
    },
    clickPub: function(val) {
        this.$mqtt.publish('/demd', this.val)
    },

      download () {
        axios.post('http://arominds.com:8000/api/mobiledownload', this.customcard_board[0])
        .then(response =>{
          console.log(response)
          console.log("성공")

          this.clickPub("dfasdff")
        }).catch((err) => {
          console.log(err.response)
        })

      }
    },
    /* computed: {
      strippedContent() {
        let regex = /(<([^>]+)>)/ig;
        return this.customcard_board[0].content.rendered.replace(regex, "");
      }
    } */

  }
</script>

<style>
  #view {
    margin-top: 6%;
  }

  #list {
    color: rgb(234,119,142);
    width: 11%;
    float: right;
  }

  #list:hover {
    color: white;
  }

  #font {
    font-family: 'Rubik', sans-serif;
  }

  #name {
    font-family: 'Rubik', sans-serif;
    font-weight: bold;
  }

  #menu {
    font-family: 'Noto Sans KR', sans-serif;
    font-weight: bold;
  }

  #progress {
    color: rgb(234,119,142);
  }

  /* #mood {
    background: rgb(0,251,56);
    background: linear-gradient(90deg, rgba(0,251,56,1) 0%, rgba(0,254,26,1) 33%, rgba(0,245,142,1) 67%, rgba(0,238,243,1) 100%);
  } */

/*   #mood1 {
    background: #00fb38;
  }

  #mood2 {
    background: #00f58e;
  }

  #mood3 {
    background: #00eef3;
  } */

  #mood1-1 {
    /* background: #00fb38; */
    border-radius: 15%;
  }

  #mood2-1 {
    /* background: #00f58e; */
    border-radius: 15%;
  }

  #mood3-1 {
    /* background: #00eef3; */
    border-radius: 15%;
  }

/*   .graph_container{
  width: 290px;
  height: 290px;
} */

#doughnut{
  width: 420px;
  height: 420px;

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
</style>
