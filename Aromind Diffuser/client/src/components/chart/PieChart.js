import { Pie, mixins } from 'vue-chartjs'

export default {
  extends: Pie,//Line
  mixins: [mixins.reactiveProp],
  props: ['chartData', 'options'],//options=""
  mounted() {
    this.renderChart(this.chartData, this.options)//여기에 직접 값(라라벨에 return한 값)을 넣으면 실행됨
  }
}
