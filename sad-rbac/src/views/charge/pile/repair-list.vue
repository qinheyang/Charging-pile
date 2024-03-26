<template>
  <el-dialog :title="dialogFormTitle"
             :visible.sync="dialogFormVisible"
             class="save-form-div"
             :close-on-click-modal="$globeValue.clickModalClose">
    <el-form :inline="true"
             ref="selectForm"
             :model="params"
             label-width="60px"
             class="list-form">
      <el-form-item prop="reportName"
                    label="上报人">
        <el-input placeholder="请输入上报人姓名"
                  v-model="params.reportName">
        </el-input>
      </el-form-item>
      <el-form-item prop="reportName"
                    label="维修人">
        <el-input placeholder="请输入维修人姓名"
                  v-model="params.reportName">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   icon="el-icon-search"
                   @click="searchList">搜索</el-button>
        <el-button icon="el-icon-refresh"
                   @click="refresh('selectForm')">重置</el-button>
      </el-form-item>
      <div class="list-btn-box">
        <el-button plain
                   type="warning"
                   icon="el-icon-download"
                   @click="exportExecl()">导出</el-button>
      </div>
    </el-form>
    <el-table :data="tableData"
              stripe
              height="300px">
      <el-table-column align="center"
                       label="故障类型">
        <template slot-scope="scope">
          {{typeMap[scope.row.faultType]}}
        </template>
      </el-table-column>

      <el-table-column align="center"
                       prop="reportName"
                       label="上报人">
      </el-table-column>
      <el-table-column align="center"
                       prop="reportTime"
                       label="上报时间"
                       :formatter="formatTime">
      </el-table-column>
      <el-table-column align="center"
                       prop="repairName"
                       label="维修人">
      </el-table-column>
      <el-table-column align="center"
                       prop="repairTime"
                       label="维修时间"
                       :formatter="formatTime">
      </el-table-column>
    </el-table>
    <el-pagination background
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="params.pageNum"
                   :page-sizes="[10, 20, 30, 40]"
                   :page-size="params.pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
  </el-dialog>
</template>
<style>
</style>
<script>
import { listRepair, exportRepair } from '@/api/repair/repair';
import { formatTime } from '@/utils/elementui-util'
export default ({
  data () {
    return {
      params: {
        pageNum: 1,
        pageSize: 10,
        pileCode: null
      },
      typeMap: {
        "0": "插头损坏",
        "1": "线路断裂",
        "2": "接触器故障",
        "3": "电源失效",
        "4": "其它"
      },
      tableData: null,
      table: null,
      total: null,

      loading: false,
      dialogFormVisible: false,
      dialogFormTitle: "维修记录",
    }
  },
  mounted () {

  },
  methods: {
    formatTime,
    openDialog (pileCode) {
      this.dialogFormVisible = true;
      this.params.pileCode = pileCode;
      this.searchList();
    },
    searchList () {
      this.loading = true;
      listRepair(this.params).then(resp => {
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.$globeValue.loadingDelay(this);
      });
    },
    exportExecl () {
      exportRepair(this.params).then((resp) => {
        let url = process.env.VUE_APP_BASE_API + process.env.VUE_APP_BASE_URL + process.env.VUE_APP_BASE_PROFILE + process.env.VUE_APP_BASE_DOWNLOAD + "/" + resp.msg;
        window.open(url, "_self")
      });
    },
    handleSizeChange (val) {
      this.params.pageSize = val;
      this.searchList();

    },
    handleCurrentChange (val) {
      this.params.pageNum = val;
      this.searchList();
    },
    refresh (formName) {
      this.$refs[formName].resetFields();
    },

  }
})
</script>
