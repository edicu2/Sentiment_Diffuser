import { HorizontalBar, mixins } from 'vue-chartjs'
import EventBus from './EventBus'
export default {
  extends: HorizontalBar,//Line
  mixins: [mixins.reactiveProp],
  props: ['chartData'],//options=""
  mounted() {
    var options ={
      tooltips: {
        caretSize:15,
        bodySpacing: 15,
        yAlign: 'right',
        callbacks: {
          labelColor: function(tooltipItem, chart) {
            return {
                  backgroundColor: chart.data.datasets[0].backgroundColor[tooltipItem.index]
                }
          }
        }
      },
      onClick: function(evt) {
        var element = this.getElementAtEvent(evt);
        if(element.length > 0)
        {
          var index = element[0]._index;
          this.aromaClick = this.data.labels[index];
          EventBus.$emit('barLabelClick-event',this.aromaClick);
          // 밑에 보여주기 리스트들
          //this.data.datasets[0].data.splice(index, 1);
          //this.data.labels.splice(index, 1);
          //this.update(this.data);
        }
      },
      scales: {
        xAxes: [{
          gridLines: {
            display: false,
          },
          ticks: {
              beginAtZero:true,
              scaleShowLabels : false,
          }
        }],
        yAxes: [{
          barThickness: 20,
          scaleShowLabels : false,

        }]
      },
      legend: {display: false},

    };
    this.renderChart(this.chartData, options)//여기에 직접 값(라라벨에 return한 값)을 넣으면 실행됨
  }
}
