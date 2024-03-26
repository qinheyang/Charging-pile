<template>
  <div class="module-box">
    <div style="flex: 0 1 50%">
      <dv-border-box-9 style="width: 100%; height: 50px">
        <!-- <dv-capsule-chart :config="config7" style="width: 100%; height: 250px"/> -->
        <!-- echarts表 -->
        <LineChart :typeChart="line" />
      </dv-border-box-9>
    </div>
    <div style="flex: 0 1 25%">
      <dv-border-box-12 style="width: 100%; height: 250px"><dv-charts :option="option" />
      </dv-border-box-12>
    </div>
    <div style="flex: 0 1 25%">
      <dv-border-box-10 style="width: 100%; height: 250px"><dv-scroll-board :config="config9"
                         style="width: 100%; height: 250px" /></dv-border-box-10>
    </div>
  </div>
</template>
<script>
import LineChart from "./lineChart.vue";

export default {
  components: { LineChart },
  data () {
    return {
    };
  },
};
</script>
