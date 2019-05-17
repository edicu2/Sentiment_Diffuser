<template>
  <div>

    <!-- <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
      <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
      </ol>
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="d-block w-100" src="./ex4.jpg" alt="First slide">
        </div>
        <div class="carousel-item">
          <img class="d-block w-100" src="./ex4.jpg" alt="Second slide">
        </div>
        <div class="carousel-item">
          <img class="d-block w-100" src="./ex4.jpg" alt="Third slide">
        </div>
      </div>
      <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div> -->

    <!-- <b-carousel
      id="carousel1"
      style="text-shadow: 1px 1px 2px #333;"
      controls
      indicators
      background="#ababab"
      :interval="4000"
      img-width="1024"
      img-height="480"
      v-model="slide"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    > -->
      <!-- Text slides with image -->
      <!-- <b-carousel-slide>
        <img
          slot="img"
          class="d-block img-fluid w-100"
          width="1024"
          height="480"
          src="./ex4.jpg"
          alt="image slot"
        />
      </b-carousel-slide> -->

      <!-- Slides with custom text -->
      <!-- <b-carousel-slide>
        <img
          slot="img"
          class="d-block img-fluid w-100"
          width="1024"
          height="480"
          src="./ex4.jpg"
          alt="image slot"
        />
      </b-carousel-slide> -->

      <!-- Slides with image only -->
      <!-- <b-carousel-slide>
        <img
          slot="img"
          class="d-block img-fluid w-100"
          width="1024"
          height="480"
          src="./ex4.jpg"
          alt="image slot"
        />
      </b-carousel-slide> -->

      <!-- Slides with img slot -->
      <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
      <!-- <b-carousel-slide>
        <img
          slot="img"
          class="d-block img-fluid w-100"
          width="1024"
          height="480"
          src="./ex4.jpg"
          alt="image slot"
        />
      </b-carousel-slide>
    </b-carousel> -->

    <div>
      <img src="./ex4.jpg" width="100%;">
    </div>

    <main role="main" id="main">
      <div class="container marketing">

        <!-- START THE FEATURETTES -->

        <!-- <hr class="featurette-divider">

        <div class="row featurette align-items-center">
          <div class="col-md-7">
            <h2 class="featurette-heading">First featurette heading. <br><span class="text-muted">It’ll blow your mind.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
          </div>
          <div class="col-md-5">
            <svg class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: 500x500"><title>Placeholder</title><rect width="100%" height="100%" fill="#eee"></rect><text x="50%" y="50%" fill="#aaa" dy=".3em">500x500</text></svg>
          </div>
        </div> -->

        <hr class="featurette-divider">

        <div class="row featurette align-items-center">
          <div class="col-md-7">
            <h2 class="featurette-heading">First featurette heading. <br><span class="text-muted">It’ll blow your mind.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
          </div>
          <div class="col-md-5">
            <img src="/static/img/aromind.jpg" width="452" alt="">
          </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette align-items-center">
          <div class="col-md-7 order-md-2">
            <h2 class="featurette-heading">Oh yeah, it’s that good. <br><span class="text-muted">See for yourself.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
          </div>
          <div class="col-md-5 order-md-1">
            <img src="/static/img/aromind2.jpg" style="transform: rotate(90deg);" width="453">
          </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette align-items-center">
          <div class="col-md-7">
            <h2 class="featurette-heading">And lastly, this one. <br><span class="text-muted">Checkmate.</span></h2>
            <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
          </div>
          <div class="col-md-5">
            <iframe width="560" height="315" src="https://www.youtube.com/embed/m6GFB6C_8E4" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
          </div>
        </div>

        <hr class="featurette-divider">

        <!-- /END THE FEATURETTES -->

        <br>

        <!-- Three columns of text below the carousel -->
        <div class="row text-center">
          <div class="col-lg-4">
            <img src="/static/img/oil1.jpg" alt="">
            <h2>Lavender Oil</h2>
            <p>Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.</p>
            <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
          </div><!-- /.col-lg-4 -->
          <div class="col-lg-4">
            <img src="/static/img/oil2.jpg" alt="">
            <h2>Eucalyptus Oil</h2>
            <p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh.</p>
            <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
          </div><!-- /.col-lg-4 -->
          <div class="col-lg-4">
            <img src="/static/img/oil3.jpg" alt="">
            <h2>Lavender Oil</h2>
            <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
            <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
          </div><!-- /.col-lg-4 -->
        </div><!-- /.row -->

        <hr class="featurette-divider">

        <div class="row featurette align-items-center">
          <div>
            <div id="Eanalyze">
              <h3 class="header_1fphhvm">Try examine a news article or other content</h3>
              <br>
              <div class="tab-panels" role="tabpanel">
                <b-card>
                  <div>
                    <b-button-group>
                      <b-button variant="success" class="btn-primary1 btn-outline-secondary1" style="color: white;">Text</b-button>
                      <b-button variant="warning" class="btn-outline-secondary1" style="background-color: white;">URL</b-button>
                    </b-button-group>
                  </div>
                  <div class="tab-panels--tab-content" style="margin-top: 1.4%;">
                    <div data-id="Text">
                      <div style="visibility: hidden; margin: 0rem 0rem -1rem; height: 0rem; overflow: hidden;">
                      </div>
                      <b-form-textarea
                        id="textarea-no-resize"
                        v-model="value"
                        name="textarea"
                        placeholder="textarea"
                        rows="3"
                        no-resize
                      />
                    </div>
                  </div>
                </b-card>
              </div>
              <p class="language_zy5iyl" style="visibility: hidden;"></p>
              <p class="footnote_1d0peeh"><!-- react-text: 16 -->For results unique to your business needs consider building a <!-- /react-text -->
                <a href="https://www.ibm.com/watson/services/knowledge-studio/" target="_blank" rel="noopener noreferrer">custom model</a><!-- react-text: 18 -->.<!-- /react-text -->
              </p>
              <p>* This system is for demonstration purposes only and is not intended to process Personal Data. No Personal Data is to be entered into this system as it may not have the necessary controls in place to meet the requirements of the General Data Protection Regulation (EU) 2016/679.</p>
              <!-- <button id="analyze" class="btn btn-primary1">Analyze</button> -->
              <button id="analyze" class="btn btn-primary1" v-on:click="analyze">Analyze</button>
              <br>
              <b-spinner label="Loading..." id="loading" style="margin-top: 1.5%;; width: 4.5rem; height: 4.5rem; color: darkgray;"></b-spinner>
              <div name="status" id="status" style="margin-top: 1.3%; margin-left: 0.1%;"></div>
              <canvas id="graph" style="position: relative; height:40%; width:80%"></canvas>
            </div>
          </div>
        </div>

      </div>
    </main>
  </div>
</template>

<script type="text/javascript">
  import axios from 'axios'
  import Chart from 'chart.js'

  $('#loading').hide();

  $('document').ready(function () {
    $('#loading').hide();
  });

  export default {
    data() {
      return {
        slide: 0,
        sliding: null,
        showCollapse: false,
        value: '',
      }
    },
    methods: {
      onSlideStart(slide) {
        this.sliding = true
      },
      onSlideEnd(slide) {
        this.sliding = false
      },
      create () {
        this.$validator.updateDictionary({
          'al': {
            attributes: {
              name: 'emri'
            }
          }
        })
        this.$validator.setLocale('al')
      },
      analyze() {
        $('#loading').show();
        axios.post('http://localhost:8000/api/emotions',
        {
          value: this.value
        }
        ).then((result) => {
          $('#loading').hide();
          console.log(result.data)

          console.log(result.data.sentiment.document)
          console.log(result.data.sentiment.document.score)
          console.log(result.data.sentiment.document.label)

          var label = result.data.sentiment.document.label
          var score = result.data.sentiment.document.score

          var html = '';

          html = "<div class='card' style='padding: 1%;'><h5>Overall Sentiment</h5><h5>" + label + " " + score + "</h5></div> ";
          $("#status").html(html);

          var create = document.getElementById("graph")
          var ctx = create.getContext("2d")

          var mainChart = new Chart(ctx, {
            type: 'horizontalBar',
            data: {
              datasets: [{
                data: [result.data.sentiment.document.score],
                backgroundColor: [
                  "#ccf6ec",
                  "#ff6654",
                  "#009784"
                ],
                hoverBackgroundColor: [
                  "#ccf6ec",
                  "#ff6654",
                  "#009784"
                ]
              }]
            },
            options: {
              scales: {
                xAxes: [{
                  gridLines: {
                    display: false
                  },
                  ticks: {
                    max: 1.0,
                    min: -1.0,
                    stepSize: 1
                  }
                }],
                yAxes: [{
                  gridLines: {
                    display: false
                  }
                }]
              },
              legend: {
                display: false
              },
              tooltips: {
                callbacks: {
                  label: function(tooltipItem) {
                    return tooltipItem.yLabel;
                  }
                }
              }
            }
          });

        }).catch((request,status,error) => {
          alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
        })
      }
    }
  }
</script>

<style>
  .bd-placeholder-img {
    font-size: 1.125rem;
    text-anchor: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }

  @media (min-width: 768px) {
    .bd-placeholder-img-lg {
      font-size: 3.5rem;
    }
  }

  /* #main {
    margin-top: 0%;
  } */
</style>
