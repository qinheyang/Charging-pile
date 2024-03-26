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
      <el-form-item label="充电桩编号">
        <el-select disabled
                   v-model="saveForm.pileCode"
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
      <el-form-item label="车辆类型">
        <el-select disabled
                   v-model="saveForm.faultType"
                   clearable
                   placeholder="请选择故障类型"
                   style="width: 100%;">
          <el-option v-for="item in faultTypeList"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="维护描述">
        <el-input type="textarea"
                  :rows="4"
                  disabled
                  maxlength=500
                  placeholder="请填写故障描述"
                  v-model="saveForm.faultDescription">
        </el-input>
      </el-form-item>
      <el-form-item prop="repairDescription"
                    label="维护描述">
        <el-input type="textarea"
                  :rows="4"
                  maxlength=500
                  placeholder="请填写维护描述"
                  v-model="saveForm.repairDescription">
        </el-input>
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
import { getReportInfo } from '@/api/repair/report';
import { addRepair } from '@/api/repair/repair';

import { getPiles } from '@/api/charge/pile';


export default ({
  data () {
    return {
      saveForm: {

        pileCode: undefined,
        faultType: undefined,
        faultDescription: undefined,
        repairDescription: undefined

      },
      pileList: [],
      faultTypeList: [{ value: "0", label: "插头损坏" },
      { value: "1", label: "线路断裂" },
      { value: "2", label: "接触器故障" },
      { value: "3", label: "电源失效" },
      { value: "4", label: "其它" }
      ],
      loading: false,
      dialogFormVisible: false,
      dialogFormTitle: undefined,
      rules: {
        reportName: [
          { required: true, message: '请输入充电桩名', trigger: 'blur' },
        ],
      }
    }
  },
  mounted () {

  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          addRepair(this.saveForm).then(() => {
            this.cancel();
            this.$emit('refresh-list');
            this.$message.success("保存成功");
          })


        }
      });
    },
    resetForm (formName) {
      this.saveForm = {
        pileCode: undefined,
        faultType: undefined,
        faultDescription: undefined,
        repairDescription: undefined
      };
      this.chargeTime = []
      this.$refs[formName].resetFields();
    },
    openDialog (reportId) {
      getPiles().then((resp) => {
        this.pileList = resp.data;
      })

      this.dialogFormTitle = '提交维修记录';
      getReportInfo(reportId).then((resp) => {
        this.saveForm = resp.data;
      })

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
