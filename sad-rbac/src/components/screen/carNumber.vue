<template>
  <div ref="carNumber"
       style="width: 100%; height: 90%"></div>
</template>
<script>
import { getLineChart } from "@/api/charge/records"
import * as echarts from "echarts/core";
export default {
  components: {},
  data () {
    return {
      option: {
        xAxis: {
          type: "category",
          data: [],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: [0, 0, 0, 0, 0, 0, 0],
            type: "line",
          },
        ],
      },
    };
  },
  mounted () {
    this.drawChart();
  },
  methods: {
    formatDate (date) {
      var date1 = new Date(date);
      var MM = (date1.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
      var DD = (date1.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
      return MM + "-" + DD;
    },
    drawChart () {
      var myDate = new Date(); //获取今天日期
      var dateMap = new Map();
      this.option.xAxis.data[6] = this.formatDate(myDate)
      dateMap.set(this.option.xAxis.data[6], 6)
      myDate.setDate(myDate.getDate() - 6);
      var dateTemp;
      var flag = 1;
      for (var i = 0; i < 6; i++) {
        dateTemp = this.formatDate(myDate)
        this.option.xAxis.data[i] = dateTemp;
        myDate.setDate(myDate.getDate() + flag);
        dateMap.set(dateTemp, i)
      }
      getLineChart().then((resp) => {

        for (let d of resp.data) {

          this.option.series[0].data[dateMap.get(d.dateTime.slice(5, 10))] = d.value
        }
        var myChart = echarts.init(this.$refs.carNumber, null);
        myChart.setOption(this.option);
      })
    },
  },
};
</script>
