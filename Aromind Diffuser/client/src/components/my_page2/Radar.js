import { Radar, mixins } from 'vue-chartjs'

export default {
  extends: Radar,//Line
  mixins: [mixins.reactiveProp],
  props: ['chartData', 'options'],//options=""
  mounted() {
    var options ={
      scale: {
        gridLines: {
          color: '#c9c9c9',
        },
        angleLines: {
          color: '#c9c9c9'
        },
        scaleLineWidth :16,
        ticks: {
          backdropColor: 'rgba(0,0,0,0)',
          fontColor: '#fff',
          beginAtZero: true,
          stepSize: 30,
          scaleShowLabels : false,
        },
        pointLabels: {
          fontSize: 15,
          fontColor:'black',
          fontStyle: 'bold'
        }
      },legend: {display: false},
    };
    this.renderChart(this.chartData, options)//여기에 직접 값(라라벨에 return한 값)을 넣으면 실행됨
  }
}
