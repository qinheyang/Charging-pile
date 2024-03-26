import Vue from 'vue'
import ElementUI from 'element-ui'
import '@/assets/style/theme/index.css'
import App from '@/App.vue'
import '@/assets/globe.css'
import globeValue from '@/utils/glole'
import router from './router'
import AMapLoader from '@amap/amap-jsapi-loader';
import './router/permission'
import store from './store'
import dataV from '@jiaminghi/data-view'
import * as echarts from "echarts";
window._AMapSecurityConfig = {
  securityJsCode: 'f319b0249bda525214941d0a5039515b',// 开发环境使用
}
AMapLoader.load({
  key: "043bb2e85ed992b757e3bac2de363b14",              // 申请好的Web端开发者Key，首次调用 load 时必填

  version: "2.0",   // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
  plugins: ['AMap.Geocoder', 'AMap.Geolocation'],           // 需要使用的的插件列表，如比例尺'AMap.Scale'等
  AMapUI: {
    // 是否加载 AMapUI，缺省不加载
    version: '1.1', // AMapUI 缺省 1.1
    plugins: [] // 需要加载的 AMapUI ui插件
  },
  Loca: {
    // 是否加载 Loca， 缺省不加载
    version: '2.0.0' // Loca 版本，缺省 1.3.2
  }
}).then((AMap) => {
  // 挂载AMap
  Vue.prototype.$AMap = AMap;
});

Vue.use(dataV)
Vue.prototype.echarts = echarts; // 挂载到Vue实例上，方便全局使用

Vue.use(ElementUI, { size: 'small' });
Vue.config.productionTip = false
Vue.prototype.$globeValue = globeValue;
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
