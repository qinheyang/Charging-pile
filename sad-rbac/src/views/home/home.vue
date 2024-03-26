<template>
  <el-carousel indicator-position="outside">
    <el-carousel-item v-for="item in imageList"
                      :key="item">
      <el-image style="width:100%;height:100%"
                :src="require(`@/assets/piles/${item}`)"></el-image>
    </el-carousel-item>
  </el-carousel>
</template>
<script>
export default {
  data () {
    return {
      imageList: ["1.jpg", "2.jpg", "3.jpg", "4.jpg"]
    }
  }
}
</script>


<style>
.el-carousel__container {
  height: 590px;
}
/* .el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
} */

/* .el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
} */

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>