<template>
  <div ref="carMap"
       id="container"
       style="width: 100%; height: 100%">

  </div>
</template>
<script>
import { getPileMap } from "@/api/charge/pile"
import AMapLoader from '@amap/amap-jsapi-loader';
export default {
  components: {},
  data () {
    return {
      map: null,
      mapShow: false,
      infoWindow: null
    };
  },
  mounted () {
    var that = this
    //this.mapShow = true
    this.$nextTick(() => {
      that.initMap();
    })



  },
  methods: {
    initMap () {


      AMapLoader.load({
        "key": "043bb2e85ed992b757e3bac2de363b14",     // 申请好的Web端开发者Key，首次调用 load 时必填
        "version": "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        "plugins": []  //插件列表
      }).then((AMap) => {
        getPileMap().then((resp) => {
          var markers = []
          this.map = new AMap.Map('container', {
            mapStyle: 'amap://styles/blue', //设置地图的显示样式
            viewMode: '2D', // 默认使用 2D 模式，如果希望使用带有俯仰角的 3D 模式，请设置 viewMode: '3D',
            zoom: 5, //初始化地图层级
            center: [116.397536, 39.908921],//初始化地图中心点
          });
          for (let d of resp.data) {
            var icon = require("@/assets/img/normal.png")
            if (d.pileState == "1") {
              icon = require("@/assets/img/fail.png")
            }
            var marker = new AMap.Marker({
              position: new AMap.LngLat(d.longitude, d.latitude),
              icon: icon,
              size: new AMap.Size(34, 34), //图标尺寸
              anchor: 'bottom-center',
            });
            this.infoWindow = new AMap.InfoWindow({ offset: new AMap.Pixel(0, -30) });

            marker.content = d.pileCode;
            marker.on('click', (e) => {
              this.infoWindow.setContent(
                e.target.content);
              this.infoWindow.open(this.map, e.target.getPosition());
            });
            markers.push(marker)
          }
          this.map.add(markers);

        })
      }).catch(e => {
        console.log(e);
      })
    },

  },
};
</script>
<style>
.amap-info-content {
  background: #000000bd !important;
}
.bottom-center,
.amap-info-sharp {
  border-top: 8px solid #000000bd !important;
}
</style>
