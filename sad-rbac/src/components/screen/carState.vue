<template>
  <div ref="carState"
       style="width: 100%; height:90%"></div>
</template>
<script>
import * as echarts from "echarts/core";
import { getBarChart } from "@/api/charge/records"
export default {
  components: {},
  data () {
    return {
      option: {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        //私家车0，出租车1，公交车2
        legend: {
          data: ["私家车", "出租车", "公交车"],
        },
        grid: {
          top: "5%",
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "value",
        },
        yAxis: {
          type: "category",
          data: [],
        },
        series: [
          {
            name: "私家车",
            type: "bar",
            stack: "总量",
            label: {
              show: true,
              position: "insideRight",
            },
            data: [],
          },
          {
            name: "出租车",
            type: "bar",
            stack: "总量",
            label: {
              show: true,
              position: "insideRight",
            },
            data: [],
          },
          {
            name: "公交车",
            type: "bar",
            stack: "总量",
            label: {
              show: true,
              position: "insideRight",
            },
            data: [],
          }
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
      this.option.yAxis.data[0] = this.formatDate(myDate)
      dateMap.set(this.option.yAxis.data[0], 0)
      myDate.setDate(myDate.getDate() - 6);
      var dateTemp;
      var flag = 1;
      for (var i = 6; i > 0; i--) {
        dateTemp = this.formatDate(myDate)
        this.option.yAxis.data[i] = dateTemp;
        myDate.setDate(myDate.getDate() + flag);
        dateMap.set(dateTemp, i)
      }
      getBarChart().then((resp) => {
        for (let d of resp.data) {
          this.option.series[d.name].data[dateMap.get(d.dateTime.slice(5, 10))] = d.value
        }
        var myChart = echarts.init(this.$refs.carState, null);
        myChart.setOption(this.option);
      })

    },
  },
};
</script>
