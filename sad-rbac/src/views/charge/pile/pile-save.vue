<template>
  <el-dialog :title="dialogFormTitle"
             :visible.sync="dialogFormVisible"
             class="save-form-div"
             :close-on-click-modal="$globeValue.clickModalClose"
             @closed=closed()>
    <el-form v-loading='loading'
             :model="saveForm"
             :rules="rules"
             ref="saveForm"
             label-width="100px"
             size="medium">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="pileCode"
                        label="编号">
            <el-input :disabled="saveForm.pileId!= undefined"
                      placeholder="请输入充电桩编号"
                      v-model="saveForm.pileCode"
                      maxlength=30
                      auto-complete="off">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="speed"
                        label="充电速度">
            <el-input placeholder="请输入充电速度"
                      v-model="saveForm.speed"
                      maxlength=20
                      auto-complete="off">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="requirement"
                        label="连接要求">
            <el-input placeholder="请输入连接要求"
                      v-model="saveForm.requirement"
                      maxlength=50
                      auto-complete="off">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="price"
                        label="价格(元)">
            <el-input placeholder="请输入价格"
                      v-model="saveForm.price"
                      maxlength=10
                      auto-complete="off">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="outputPower"
                        label="最大输出功率">
            <el-input placeholder="请输入最大输出功率"
                      v-model="saveForm.outputPower"
                      maxlength=10
                      auto-complete="off">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="pileSate"
                        label="状态">
            <el-radio-group v-model="saveForm.pileState"
                            disabled>
              <el-radio v-for=" (m,i) in pileSateOption"
                        :key="i"
                        :label="m.value">{{m.label}}</el-radio>

            </el-radio-group>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="installTime"
                        label="安装时间">
            <el-date-picker style="width: 100%"
                            v-model="saveForm.installTime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            type="date"
                            placeholder="请选择安装时间">
            </el-date-picker>

          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="maintenanceTime"
                        label="维修时间">
            <el-date-picker style="width: 100%"
                            disabled
                            v-model="saveForm.maintenanceTime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            type="date"
                            placeholder="请选择维修时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="maintenanceCycle"
                        label="维修周期">
            <el-input placeholder="请输入维修周期"
                      v-model="saveForm.maintenanceCycle"
                      maxlength=5
                      auto-complete="off">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="chargeStandard"
                        label="收费标准">
            <el-input placeholder="请输入收费标准"
                      v-model="saveForm.chargeStandard"
                      maxlength=4
                      auto-complete="off">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20"
              v-if="mapShow">
        <el-col :span="24">
          <div id="container"></div>
        </el-col>
      </el-row>

    </el-form>
    <div slot="footer"
         class="dialog-footer">
      <el-button type="primary"
                 @click="submitForm('saveForm')">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<style>
.save-form-div form {
  width: 100%;
}

#container {
  width: 100%;
  height: 200px;

  border: 1px solid rgb(3, 3, 3);
}
</style>
<script>
import { updatePile, addPile, getPileInfo } from '@/api/charge/pile';
// import regex from '@/utils/regex';

export default ({
  data () {

    return {
      saveForm: {
        pileId: undefined,
        pileCode: undefined,
        speed: undefined,
        requirement: undefined,
        price: undefined,
        pileState: "0",
        outputPower: undefined,
        maintenanceCycle: undefined,
        installTime: undefined,
        maintenanceTime: undefined,
        chargeStandard: undefined,
        latitude: 39.908921,
        longitude: 116.397536,
        district: "北京市"
      },
      mapShow: false,
      menuList: [],
      pileSateOption: [
        { label: '正常', value: "0" },
        { label: '故障', value: "1" },
      ],
      loading: false,
      dialogFormVisible: false,
      dialogFormTitle: undefined,
      map: null,
      geocoder: null,
      marker: null,
      rules: {
        pileCode: [
          { required: true, message: '请输入充电桩编号', trigger: 'blur' },

        ],
        chargeStandard: [
          { pattern: /^\d+(\.\d+)?$/, required: true, message: '请输入合法的收费标准', trigger: 'blur' },

        ],
      }
    }
  },
  mounted () {

  },
  methods: {
    initMap () {
      this.map = new this.$AMap.Map('container', {
        viewMode: '2D', // 默认使用 2D 模式，如果希望使用带有俯仰角的 3D 模式，请设置 viewMode: '3D',
        zoom: 11, //初始化地图层级
        center: [this.saveForm.longitude, this.saveForm.latitude],//初始化地图中心点
      });
      this.geocoder = new this.$AMap.Geocoder({
        // city: "010", //城市设为北京，默认：“全国”
        // radius: 1000 //范围，默认：500
      });



      this.marker = new this.$AMap.Marker({
        position: new this.$AMap.LngLat(this.saveForm.longitude, this.saveForm.latitude),
        anchor: 'bottom-center',
      });
      this.map.add(this.marker);
      this.map.on('click', (res) => {
        this.map.remove([this.marker])
        this.marker = new this.$AMap.Marker({
          position: [res.lnglat.lng, res.lnglat.lat],
          offset: new this.$AMap.Pixel(-10, -20)
        });
        var that = this;
        this.saveForm.latitude = res.lnglat.lat;
        this.saveForm.longitude = res.lnglat.lng;
        this.map.add([this.marker]);
        var coordinate = [res.lnglat.getLng(), res.lnglat.getLat()]
        this.geocoder.getAddress(coordinate, function (status, result) {
          console.log(result)
          if (status === 'complete' && result.regeocode) {
            that.saveForm.district = result.regeocode.addressComponent.province;
            console.log(result.regeocode.addressComponent.province)
          } else {
            console.log('根据经纬度查询地址失败')
          }
        });
      })

    },
    submitForm (formName) {

      this.$refs[formName].validate((valid) => {
        if (valid) {

          if (this.saveForm.pileId === undefined) {
            addPile(this.saveForm).then(() => {
              this.cancel();
              this.$emit('refresh-list');
              this.$message.success("保存成功");
            })

          } else {
            updatePile(this.saveForm).then(() => {
              this.cancel();
              this.$emit('refresh-list');
              this.$message.success("保存成功");
            })
          }
        }
      });
    },
    resetForm (formName) {
      this.saveForm = {
        pileId: undefined,
        pileCode: undefined,
        speed: undefined,
        requirement: undefined,
        price: undefined,
        pileState: "0",
        outputPower: undefined,
        maintenanceCycle: undefined,
        installTime: undefined,
        maintenanceTime: undefined,
        chargeStandard: undefined,
        latitude: 39.908921,
        longitude: 116.397536,
        district: "北京市"
      }
      this.$refs[formName].resetFields();

    },
    openDialog (pileId) {


      if (pileId === undefined) {
        this.dialogFormTitle = '充电桩新增';
        this.mapShow = true

        this.$nextTick(() => {

          this.initMap()
        })

      } else {
        this.dialogFormTitle = '充电桩修改';
        getPileInfo(pileId).then((resp) => {
          this.saveForm = resp.data;
          this.mapShow = true

          this.$nextTick(() => {

            this.initMap()
          })
        })
      }

      this.dialogFormVisible = true;

      this.mapShow = true

      this.$nextTick(() => {

        this.initMap()
      })



    },

    cancel () {
      this.dialogFormVisible = false;
    },
    closed () {
      this.resetForm('saveForm');
    },


  }
})
</script>
