import { Bar, mixins } from 'vue-chartjs'

export default {
  extends: Bar,//Line
  mixins: [mixins.reactiveProp],
  props: ['chartData', 'options'],//options=""
  mounted() {
    this.$refs.canvas.getContext('2d').createLinearGradient(0, 0, 0, 450)

    this.renderChart(this.chartData, this.options)//여기에 직접 값(라라벨에 return한 값)을 넣으면 실행됨
  }
}
