<template>
  <div class="module-box"
       style="height: 100%">
    <!-- 左 -->
    <div style="flex: 0 1 25%">
      <defaultBorder style="width: 100%; height: 30%; margin-top: 30px"
                     :title="districtTitle">
        <dv-active-ring-chart :config="dataActive"
                              style="width: 100%; height: 100%" />
      </defaultBorder>

      <defaultBorder style="width: 100%; height: 40%; margin-top: 30px "
                     :title="carCountTitle">
        <carState style="margin-top: 20px"></carState>
      </defaultBorder>
    </div>
    <!-- 中 -->
    <div style="flex: 0 1 50%">

      <defaultBorder style="width: 95%; height: 73%; margin-top:30px;margin-left:20px;magrin-right:20px;"
                     :title="pileMapTitle">
        <pileMap></pileMap>
      </defaultBorder>

    </div>
    <!-- 右边 -->
    <div style="flex: 0 1 25%">
      <defaultBorder :title="rankTitle"
                     style="
          width: 100%;
          height: 30%;
          margin-top: 30px;
        ">
        <dv-scroll-ranking-board :config="rank"
                                 style="width: 350px; height: 220px" />
      </defaultBorder>
      <defaultBorder :title="earnTitle"
                     style="width: 100%; height: 40%; margin-top: 30px">
        <carNumber style="margin-top: 20px"></carNumber>
      </defaultBorder>
    </div>
  </div>
</template>
<script>
import defaultBorder from "@/components/screen/defaultBorder.vue";
import carState from "@/components/screen/carState.vue";
import carNumber from "@/components/screen/carNumber.vue";
import pileMap from "@/components/screen/pileMap.vue";
import { getRingNum } from '@/api/charge/pile';
import { getRank } from '@/api/charge/records';
export default {
  components: {
    defaultBorder,
    carState,
    carNumber,
    pileMap

  },
  mounted () {
    getRingNum().then((resp) => {
      this.dataActive = {
        data: resp.data
      }
    })
    getRank().then((resp) => {
      this.rank = {
        data: resp.data
      }
    })
  },
  data () {
    return {
      districtTitle: "各地区充电桩比例图",
      carCountTitle: "充电车辆统计",
      pileMapTitle: "充电桩分布图",
      rankTitle: "各地区收益排行",
      earnTitle: "近一周收益趋势",
      colors: ["#f40", "yellow"],
      // 动态环图

      dataActive: {
        data: [
        ],
      },
      rank: {
      },

      config2: {
        data: [
          {
            name: "周口",
            value: 55,
          },
          {
            name: "南阳",
            value: 120,
          },
          {
            name: "西峡",
            value: 71,
          },
          {
            name: "驻马店",
            value: 66,
          },
          {
            name: "新乡",
            value: 80,
          },
          {
            name: "信阳",
            value: 35,
          },
          {
            name: "漯河",
            value: 15,
          },
        ],
        img: [
          "/img/conicalColumnChart/1st.png",
          "/img/conicalColumnChart/2st.png",
          "/img/conicalColumnChart/3st.png",
          "/img/conicalColumnChart/4st.png",
          "/img/conicalColumnChart/5st.png",
          "/img/conicalColumnChart/6st.png",
          "/img/conicalColumnChart/7st.png",
        ],
      },
      // 飞线图
      centerPoint: [0.48, 0.35],
      points: [
        [0.52, 0.23],
        [0.43, 0.29],
        [0.59, 0.35],
        [0.53, 0.47],
        [0.45, 0.54],
        [0.36, 0.38],
        [0.62, 0.55],
        [0.56, 0.56],
        [0.37, 0.66],
        [0.55, 0.81],
        [0.55, 0.67],
        [0.37, 0.29],
        [0.2, 0.36],
        [0.76, 0.41],
        [0.59, 0.18],
        [0.68, 0.17],
        [0.59, 0.1],
      ],
      // 胶囊柱图2
      dataChart2: [
        {
          name: "南阳",
          value: 167,
        },
        {
          name: "周口",
          value: 123,
        },
        {
          name: "漯河",
          value: 98,
        },
        {
          name: "郑州",
          value: 75,
        },
        {
          name: "西峡",
          value: 66,
        },
      ],
      colorsChart2: ["#e062ae", "#fb7293", "#e690d1", "#32c5e9", "#96bfff"],
      // 排名轮播表
      dataScoll: [
        {
          name: "周口",
          value: 55,
        },
        {
          name: "南阳",
          value: 120,
        },
        {
          name: "西峡",
          value: 78,
        },
        {
          name: "驻马店",
          value: 66,
        },
        {
          name: "新乡",
          value: 80,
        },
        {
          name: "信阳",
          value: 45,
        },
        {
          name: "漯河",
          value: 29,
        },
      ],
    };
  },
};
</script>
