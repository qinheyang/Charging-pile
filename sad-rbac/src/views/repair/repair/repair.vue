<template>
  <div v-loading="loading">
    <el-form :inline="true"
             ref="selectForm"
             :model="params"
             label-width="60px"
             class="list-form">

      <el-form-item prop="reportCode"
                    label="编号">
        <el-input placeholder="请输入充电桩编号"
                  v-model="params.pileCode">
        </el-input>
      </el-form-item>
      <el-form-item prop="faultType"
                    label="故障类型">
        <el-select v-model="params.faultType"
                   clearable
                   placeholder="请选择故障类型"
                   style="width: 92%;">
          <el-option v-for="item in faultTypeList"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="nickName"
                    label="上报人">
        <el-input placeholder="请输入上报人姓名"
                  v-model="params.nickName">
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
              height="490px">
      <!-- <el-table-column align="center"
                       type="selection">
      </el-table-column> -->
      <el-table-column align="center"
                       prop="pileCode"
                       label="故障充电桩编号">
      </el-table-column>
      <el-table-column align="center"
                       label="故障类型">
        <template slot-scope="scope">
          {{typeMap[scope.row.faultType]}}
        </template>

      </el-table-column>
      <el-table-column align="center"
                       prop="nickName"
                       label="故障提交人">
      </el-table-column>

      <el-table-column align="center"
                       prop="createTime"
                       label="故障上报时间"
                       :formatter="formatTime">
      </el-table-column>
      <el-table-column align="center"
                       label="操作">

        <template slot-scope="scope">
          <el-button type="text"
                     icon="el-icon-edit-outline"
                     @click="handleEdit(scope.$index, scope.row)">提交维护</el-button>
        </template>
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
    <repairSave ref='repairSave'
                @refresh-list="refreshList"></repairSave>
  </div>
</template>
<script>
import { listReport, exportReport } from '@/api/repair/report';
import repairSave from './repair-save';
import { formatTime } from '@/utils/elementui-util'

export default {
  components: {
    repairSave
  },
  data () {
    return {
      typeMap: {
        "0": "插头损坏",
        "1": "线路断裂",
        "2": "接触器故障",
        "3": "电源失效",
        "4": "其它"
      },
      faultTypeList: [{ value: "0", label: "插头损坏" },
      { value: "1", label: "线路断裂" },
      { value: "2", label: "接触器故障" },
      { value: "3", label: "电源失效" },
      { value: "4", label: "其它" }
      ],
      tableData: null,
      table: null,
      total: null,
      loading: true,
      params: {
        pageNum: 1,
        pageSize: 10,
        carNumber: null,
        repaired: "0",
      },
      reportIds: [],
      deleteDisable: true,

    };
  },
  created () {
    this.searchList();
  },
  methods: {
    formatTime,
    handleSizeChange (val) {
      this.params.pageSize = val;
      this.searchList();

    },
    handleCurrentChange (val) {
      this.params.pageNum = val;
      this.searchList();
    },
    searchList () {
      this.loading = true;
      listReport(this.params).then(resp => {
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.$globeValue.loadingDelay(this);
      });
    },
    refresh (formName) {
      this.$refs[formName].resetFields();
    },
    showDialogForm (reportId) {
      this.$refs.repairSave.openDialog(reportId);
    },
    refreshList (reportId) {
      if (reportId === undefined) {
        this.params.pageNum = 1;
      }
      this.searchList();
    },
    handleEdit (index, row) {
      this.showDialogForm(row.reportId);
    },
    exportExecl () {
      exportReport(this.params).then((resp) => {
        let url = process.env.VUE_APP_BASE_API + process.env.VUE_APP_BASE_URL + process.env.VUE_APP_BASE_PROFILE + process.env.VUE_APP_BASE_DOWNLOAD + "/" + resp.msg;
        window.open(url, "_self")
      });
    },




  },
};
</script>
