<template>
  <div class="module-box1">
    <!-- 三等分 -->
    <!-- 左 -->
    <div style="flex: 0 1 30%">
      <dv-decoration-10 style="height: 5px" />
    </div>
    <!-- 中 -->
    <div style="flex: 0 1 40%">
      <div class="d-flex">
        <dv-decoration-8 style="height: 50px; flex: 1" />
        <dv-decoration-11 style="height: 50px; flex: 1">充电桩监控平台</dv-decoration-11>
        <dv-decoration-8 :reverse="true"
                         style="height: 50px; flex: 1" />
      </div>
    </div>
    <!-- 右 -->
    <div style="flex: 0 1 30%">
      <dv-decoration-10 style="height: 5px; transform: rotateY(180deg)" />
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {

    }
  }
};
</script>
<style  scoped>
.module-box1 {
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
