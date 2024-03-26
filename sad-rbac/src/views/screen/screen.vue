<template>
  <div class="content bg">
    <!-- 全屏容器 -->
    <dv-full-screen-container>
      <!-- 第一行 -->
      <ModuleTitle />
      <MouduleStatistics />
      <!-- 第二行 -->
      <ModuleMain />
      <!-- 第三行 -->
      <!-- <ModuleThird /> -->
    </dv-full-screen-container>
  </div>
</template>
<style scoped>
body,
input,
button,
a,
textarea,
select {
  margin: 0;
  font-size: 1.4rem;
  line-height: 1.5;
  color: #333;
}
html,
body {
  width: 100%;
  height: 100%;
  margin: 0;
}
a {
  text-decoration: none;
}
a:hover,
a:focus {
  text-decoration: none;
}
a:hover {
  filter: alpha(opacity=90);
  opacity: 0.9;
}
/* 文本对齐 */
.tc {
  text-align: center;
}
.tr {
  text-align: right;
}
.tl {
  text-align: left;
}
.content {
  color: #fff;
  background: #000;
  width: 100%;
  height: 100vh;
}
.bg {
  background-color: #081832;
  /* background: url("../img/bg.jpg") no-repeat center center; */
  background-size: cover;
}
.module-box {
  display: flex;
  justify-content: space-between;
  margin: 5px;
}


</style>
<script>
import ModuleTitle from "./moduleTitle.vue"; //第一行
import MouduleStatistics from "./mouduleStatistics.vue"; //第一行
import ModuleMain from "./moduleMain.vue"; //第二行
// import ModuleThird from "./moduleThird.vue"; //第三行
export default {
  name: "screen",
  components: {
    ModuleTitle,
    MouduleStatistics,
    ModuleMain,
    // ModuleThird,
  },
  data () {
    return {};
  },
};
</script>
