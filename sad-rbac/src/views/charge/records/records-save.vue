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
      <el-form-item prop="pileCode"
                    label="充电桩编号">
        <el-select v-model="saveForm.pileCode"
                   clearable
                   placeholder="请选择充电桩"
                   style="width: 100%;">
          <el-option v-for="item in pileList"
                     :key="item.pileCode"
                     :label="item.pileCode"
                     :value="item.pileCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="carNumber"
                    label="车牌号">
        <el-input placeholder="请输入充电车牌号"
                  v-model="saveForm.carNumber"
                  maxlength=20
                  auto-complete="off">
        </el-input>
      </el-form-item>
      <el-form-item prop="chargeTime"
                    label="充电时间">
        <el-date-picker v-model="saveForm.chargeTime"
                        style="width: 100%;"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="carType"
                    label="车辆类型">
        <el-select v-model="saveForm.carType"
                   clearable
                   placeholder="请选择车辆类型"
                   style="width: 100%;">
          <el-option v-for="item in carTypeList"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer"
         class="dialog-footer">
      <el-button type="primary"
                 @click="submitForm('saveForm')">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<style  scoped>
.save-form-div form {
  width: 80%;
}
</style>
<script>
import { updateRecords, addRecords, getRecordsInfo } from '@/api/charge/records';
import { getPiles } from '@/api/charge/pile';

export default ({
  data () {
    return {
      saveForm: {
        recordsId: undefined,
        pileCode: undefined,
        carNumber: undefined,
        beginTime: undefined,
        endTime: undefined,
        chargeTime: null,
      },

      pileList: [],
      carTypeList: [{
        label: "私家车",
        value: "0"

      }, {

        label: "出租车",
        value: "1"

      }, {

        label: "公交车",
        value: "2"

      }],
      loading: false,
      dialogFormVisible: false,
      dialogFormTitle: undefined,
      rules: {
        pileCode: [
          { required: true, message: '请选择充电桩', trigger: 'change' },
        ],
        carNumber: [
          { required: true, message: '请输入车牌号', trigger: 'blur' },
        ],
        carType: [
          { required: true, message: '请选择车辆类型', trigger: 'change' },
        ],
        chargeTime: [
          { trigger: 'blur', required: true, message: '请选择充电时间', }
        ]

      }
    }
  },
  mounted () {

  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveForm.beginTime = this.saveForm.chargeTime[0]
          this.saveForm.endTime = this.saveForm.chargeTime[1]
          if (this.saveForm.recordsId === undefined) {
            addRecords(this.saveForm).then(() => {
              this.cancel();
              this.$emit('refresh-list');
              this.$message.success("保存成功");
            })

          } else {
            updateRecords(this.saveForm).then(() => {
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
        recordsId: undefined,
        pileCode: undefined,
        carNumber: undefined,
        beginTime: undefined,
        endTime: undefined

      };
      this.chargeTime = null
      this.$refs[formName].resetFields();
    },
    openDialog (recordsId) {
      getPiles().then((resp) => {
        this.pileList = resp.data;
      })
      if (recordsId === undefined) {
        this.dialogFormTitle = '充电记录新增';
      } else {
        this.dialogFormTitle = '充电记录修改';
        getRecordsInfo(recordsId).then((resp) => {
          this.saveForm = resp.data;
          this.saveForm.chargeTime = [this.saveForm.beginTime, this.saveForm.endTime]
        })
      }
      this.dialogFormVisible = true;
      this.mapShow = true
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
