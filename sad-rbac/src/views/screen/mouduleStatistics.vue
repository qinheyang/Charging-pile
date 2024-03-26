<template>
  <div class="module-box">
    <div class="number-statisics-box box-default"
         style="flex: 0 0 40%;">
      <ul class="statisics-ul">
        <li>
          <img class="statisics-icon"
               src="@/assets/img/车辆里程.png" />
        </li>
        <li>
          <div>充电桩总数</div>
          <div class="statisics-num">{{pileNormalNum+pileFailNum}}</div>
        </li>
      </ul>
      <ul class="statisics-ul">
        <li>
          <img class="statisics-icon"
               src="@/assets/img/车辆里程.png" />
        </li>
        <li>
          <div>可用充电桩数量</div>
          <div class="statisics-num">{{pileNormalNum}}</div>
        </li>
      </ul>
      <ul class="statisics-ul">
        <li>
          <img class="statisics-icon"
               src="@/assets/img/车辆里程.png" />
        </li>
        <li>
          <div>故障充电桩数量</div>
          <div class="statisics-num">{{pileFailNum}}</div>
        </li>
      </ul>
    </div>

    <div class="number-statisics-box box-default"
         style="flex-grow: 2">
      <ul class="statisics-ul">
        <li>
          <img class="statisics-icon"
               src="@/assets/img/车辆里程.png" />
        </li>
        <li>
          <div>今日充电流量（次）</div>
          <div class="statisics-num">{{recordsTodayNum}}</div>
        </li>
      </ul>
      <ul class="statisics-ul">
        <li>
          <img class="statisics-icon"
               src="@/assets/img/车辆里程.png" />
        </li>
        <li>
          <div>历史充电流量（次）</div>
          <div class="statisics-num">{{recordsHistoryNum}}</div>
        </li>
      </ul>
    </div>

    <div class="number-statisics-box box-default"
         style="flex-grow: 2">
      <ul class="statisics-ul">
        <li>
          <img class="statisics-icon"
               src="@/assets/img/车辆里程.png" />
        </li>
        <li>
          <div>今日收益（元）</div>
          <div class="statisics-num">{{earnToday}}</div>
        </li>
      </ul>
      <ul class="statisics-ul">
        <li>
          <img class="statisics-icon"
               src="@/assets/img/车辆里程.png" />
        </li>
        <li>
          <div>历史收益（元）</div>
          <div class="statisics-num">{{earnHistory}}</div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import { getPileNum } from '@/api/charge/pile';
import { getRecordsNum } from '@/api/charge/records';
export default {
  data () {
    return {

      pileNormalNum: 0,
      pileFailNum: 0,
      recordsTodayNum: 0,
      recordsHistoryNum: 0,
      earnToday: 0,
      earnHistory: 0,
    };
  },
  mounted () {
    getPileNum().then((resp) => {
      for (const o of resp.data) {
        if (o.name == '0') {
          this.pileNormalNum = o.value
        } else if (o.name == '1') {
          this.pileFailNum = o.value
        }
      }
    })
    getRecordsNum().then((resp) => {
      for (const o of resp.data) {
        if (o.name == '0') {
          this.recordsTodayNum = o.value
          this.earnToday = o.dvalue
        } else if (o.name == '1') {
          this.recordsHistoryNum = o.value
          this.earnHistory = o.dvalue
        }
      }
    })
  }
};

</script>
<style scoped>
.module-box {
  display: flex;
  justify-content: space-between;
  margin: 5px;
}
.d-flex {
  display: flex;
}
.box-middle {
  align-self: center;
}
.box-default {
  width: 100%;
  height: 100;
  background-color: rgb(7 115 155 / 63%);
}
</style>
<style scoped >
.module-box {
  height: 100px;
  display: flex;
  gap: 20px;

  padding: 0 5px;
}
.module-box .border-box-content {
  text-align: center;
}
.number-statisics-box {
  height: 100%;
  display: flex;

  font-size: 13px;
  gap: 10px;
  align-items: center;
  padding: 2px 30px;
  justify-content: space-between;
}
.statisics-icon {
  width: 40px;
  height: 40px;
}
.statisics-ul {
  margin: 0;
  padding: 0;
  list-style: none;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: space-between;

  gap: 10px;
}
.statisics-num {
  font-size: 25px;
  font-weight: 900;
  color: #f4ea2a;
}
</style>
